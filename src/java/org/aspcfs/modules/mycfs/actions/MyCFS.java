/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.mycfs.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.accounts.base.NewsArticleList;
import com.zeroio.iteam.base.*;
import java.sql.*;
import java.lang.reflect.*;
import java.util.*;
import java.text.DateFormat;
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.quotes.base.*;
/**
 *  The MyCFS module.
 *
 *@author     chris
 *@created    July 3, 2001
 *@version    $Id$
 */
public final class MyCFS extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return ("IncludeOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandInbox(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }
    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
    inboxInfo.setLink("MyCFSInbox.do?command=Inbox");

    Connection db = null;
    CFSNoteList noteList = new CFSNoteList();

    try {
      db = this.getConnection(context);
      noteList.setPagedListInfo(inboxInfo);
      noteList.setSentTo(((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId());
      noteList.setSentFrom(getUserId(context));

      if (context.getRequest().getParameter("return") != null) {
        inboxInfo.setListView("new");
      }

      if ("old".equals(inboxInfo.getListView())) {
        noteList.setOldMessagesOnly(true);
      }

      // TODO: Modify CFSNoteList so that only 1 query is always generated so
      // this doesn't have to happen...
      if ("sent".equals(inboxInfo.getListView())) {
        /*
         *  Changing sort column as  "sent_namelast" is not a column in the query
         */
        if ("sent_namelast".equals(inboxInfo.getColumnToSortBy())) {
          inboxInfo.setColumnToSortBy("m.sent");
        }
        noteList.setSentMessagesOnly(true);
      } else {
        /*
         *  Changing sort column back to requested column
         */
        if ("m.sent".equals(inboxInfo.getColumnToSortBy())) {
          inboxInfo.setColumnToSortBy("sent_namelast");
        }
      }

      noteList.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CFSNoteList", noteList);
    addModuleBean(context, "MyInbox", "Inbox Home");
    return ("InboxOK");
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDeleteHeadline(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-miner-delete"))) {
      return ("PermissionError");
    }
    Enumeration parameters = context.getRequest().getParameterNames();
    int orgId = -1;
    Connection db = null;
    try {
      db = this.getConnection(context);
      while (parameters.hasMoreElements()) {
        String param = (String) parameters.nextElement();

        if (context.getRequest().getParameter(param).equals("on")) {
          orgId = Integer.parseInt(param);
          Organization newOrg = new Organization();
          newOrg.setOrgId(orgId);
          newOrg.deleteMinerOnly(db);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("MyCFS-> " + param + ": " + context.getRequest().getParameter(param) + "<br>");
          }
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "", "Headline Delete OK");
    return ("DeleteOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandCFSNoteDelete(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int myId = -1;
    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
    try {
      db = this.getConnection(context);
      int noteId = Integer.parseInt(context.getRequest().getParameter("id"));
      /*
       *  For a sent message myId is a user_id else its a contactId
       */
      if (inboxInfo.getListView().equals("sent")) {
        myId = getUserId(context);
      } else {
        myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
      }
      CFSNote newNote = new CFSNote(db, noteId, myId, inboxInfo.getListView());
      if (System.getProperty("DEBUG") != null) {
        System.out.println("\nMYCFS view mode" + context.getRequest().getParameter("listView"));
      }
      newNote.setCurrentView(inboxInfo.getListView());
      newNote.delete(db, myId);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "MyInbox", "Inbox Home");
    return (executeCommandInbox(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandCFSNoteTrash(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }
    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
    Connection db = null;
    int myId = -1;
    try {
      db = this.getConnection(context);
      int noteId = Integer.parseInt(context.getRequest().getParameter("id"));
      //For a sent message myId is a user_id else its a contactId
      if (inboxInfo.getListView().equals("sent")) {
        myId = getUserId(context);
      } else {
        myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
      }
      CFSNote newNote = new CFSNote(db, noteId, myId, inboxInfo.getListView());

      if (System.getProperty("DEBUG") != null) {
        System.out.println("MyCFS-> Status before: " + newNote.getStatus());
      }
      if (newNote.getStatus() == 2) {
        newNote.setStatus(CFSNote.READ);
      } else {
        newNote.setStatus(CFSNote.OLD);
      }

      newNote.updateStatus(db);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("MyCFS-> Status after: " + newNote.getStatus());
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "MyInbox", "Inbox Home");
    return (executeCommandInbox(context));
  }


  /**
   *  Takes a look at the User Session Object and prepares the MyCFSBean for the
   *  JSP. The bean will contain all the information that the JSP can see.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandHeadline(ActionContext context) {
    if (!hasPermission(context, "myhomepage-miner-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Customize Headlines", "Customize Headlines");
    PagedListInfo orgListInfo = this.getPagedListInfo(context, "HeadlineListInfo");
    orgListInfo.setLink("MyCFS.do?command=Headline");
    Connection db = null;
    OrganizationList organizationList = new OrganizationList();
    try {
      db = this.getConnection(context);
      organizationList.setPagedListInfo(orgListInfo);
      organizationList.setMinerOnly(true);
      organizationList.setEnteredBy(getUserId(context));
      organizationList.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrgList", organizationList);
    return ("HeadlineOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsertHeadline(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-miner-add"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Customize Headlines", "");
    int headlines = 0;
    boolean existsAlready = false;
    Connection db = null;
    String name = context.getRequest().getParameter("name");
    String sym = context.getRequest().getParameter("stockSymbol");
    Organization newOrg = (Organization) new Organization();
    newOrg.setName(name);
    newOrg.setTicker(sym);
    newOrg.setIndustry("1");
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setOwner(-1);
    newOrg.setMiner_only(true);
    try {
      db = this.getConnection(context);
      existsAlready = newOrg.checkIfExists(db, name);
      newOrg.insert(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrgDetails", newOrg);
    return (executeCommandHeadline(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandCFSNoteDetails(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }
    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
    Connection db = null;
    int myId = -1;
    CFSNote newNote = null;
    try {
      int msgId = Integer.parseInt(context.getRequest().getParameter("id"));
      db = this.getConnection(context);
      //For a sent message myId is a user_id else its a contactId
      if (inboxInfo.getListView().equals("sent")) {
        myId = getUserId(context);
      } else {
        myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
      }
      newNote = new CFSNote(db, msgId, myId, inboxInfo.getListView());
      if (!inboxInfo.getListView().equalsIgnoreCase("sent")) {
        /*
         *  do not change status if its the OutBox
         */
        if (newNote.getStatus() == CFSNote.NEW) {
          newNote.setStatus(CFSNote.READ);
          newNote.updateStatus(db);
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "MyInbox", "Inbox Details");
    context.getRequest().setAttribute("NoteDetails", newNote);
    return ("CFSNoteDetailsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandNewMessage(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      CFSNote newNote = new CFSNote();
      context.getRequest().setAttribute("Note", newNote);

      //check if there are any recipients specified
      //Uncomment when action lists send message needs to be implemented
      /*
       *  if(recipients != null){
       *  recipientList = new ContactList();
       *  StringTokenizer tokens = new StringTokenizer(recipients, "|");
       *  while(tokens.hasMoreTokens()){
       *  String recipient = (String) tokens.nextToken();
       *  Contact thisRecipient = new Contact(db, Integer.parseInt(recipient));
       *  recipientList.add(thisRecipient);
       *  }
       *  }
       */
      context.getSession().removeAttribute("selectedContacts");
      context.getSession().removeAttribute("finalContacts");
      context.getSession().removeAttribute("contactListInfo");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "MyInbox", "");
    return ("CFSNewMessageOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */

  public String executeCommandSendMessage(ActionContext context) {
    boolean recordInserted = false;
    Connection db = null;
    boolean savecopy = false;
    boolean copyrecipients = false;
    HashMap selectedList = (HashMap) context.getSession().getAttribute("finalContacts");
    HashMap errors = null;
    if (selectedList == null) {
      selectedList = new HashMap();
    }
    CFSNote thisNote = new CFSNote();
    thisNote.setEnteredBy(getUserId(context));
    thisNote.setModifiedBy(getUserId(context));
    thisNote.setReplyId(getUserId(context));
    thisNote.setType(CFSNote.CALL);

    if (context.getRequest().getParameter("subject") != null) {
      thisNote.setSubject(context.getRequest().getParameter("subject"));
    } else {
      thisNote.setSubject("error");
    }
    if (context.getRequest().getParameter("body") != null) {
      thisNote.setBody(context.getRequest().getParameter("body"));
    } else {
      thisNote.setBody("error");
    }
    if (context.getRequest().getParameter("actionId") != null) {
      thisNote.setActionId(context.getRequest().getParameter("actionId"));
    }
    if (context.getRequest().getParameter("savecopy") != null) {
      savecopy = true;
    }
    if (context.getRequest().getParameter("mailrecipients") != null) {
      copyrecipients = true;
    }

    Contact thisContact = null;
    User thisRecord = (User) ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    thisRecord.setBuildContact(true);

    try {
      db = this.getConnection(context);
      thisRecord.buildResources(db);
      if (selectedList.size() != 0) {
        String replyAddr = thisRecord.getContact().getEmailAddress("Business");
        int count = -1;
        int contactId = -1;
        boolean firstTime = true;
        String email = "";
        Set s = selectedList.keySet();
        Iterator i = s.iterator();
        while (i.hasNext()) {
          count++;
          Integer hashKey = (Integer) i.next();
          contactId = hashKey.intValue();

          if (selectedList.get(hashKey) != null) {
            Object st = selectedList.get(hashKey);
            email = st.toString();
          }
          boolean isUser = false;

          thisContact = new Contact();
          thisContact.setId(hashKey.intValue());
          thisContact.setBuildDetails(true);
          thisContact.build(db);
          thisContact.checkUserAccount(db);
          thisNote.setSentTo(contactId);

          if ((!email.startsWith("P:")) && (copyrecipients || !thisContact.hasAccount())) {
            SMTPMessage mail = new SMTPMessage();
            mail.setHost(getPref(context, "MAILSERVER"));
            mail.setFrom(getPref(context, "EMAILADDRESS"));
            if (replyAddr != null && !(replyAddr.equals(""))) {
              mail.addReplyTo(replyAddr);
            }
            mail.setType("text/html");
            mail.setTo(email);
            mail.setSubject(thisNote.getSubject());
            mail.setBody("The following message was sent to your Dark Horse CRM inbox by " + thisRecord.getContact().getNameFirstLast() + ".  This copy has been sent to your email account at the request of the sender.<br><br>--- Original Message ---<br><br>" + StringUtils.toHtml(thisNote.getBody()));
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Sending Mail .. " + thisNote.getBody());
            }
            if (mail.send() == 2) {
              if (System.getProperty("DEBUG") != null) {
                System.out.println("MyCFS-> Send error: " + mail.getErrorMsg() + "<br><br>");
              }
              System.err.println(mail.getErrorMsg());
              if (errors == null) {
                errors = new HashMap();
              }
              errors.put("contact" + thisContact.getId(), "Following error occured while sending to this contact " + mail.getErrorMsg());
            } else {
              if (System.getProperty("DEBUG") != null) {
                System.out.println("MyCFS-> Sending message to " + email);
              }
              //Message is sent. Insert the note
              recordInserted = thisNote.insert(db);
              if (!recordInserted) {
                processErrors(context, thisNote.getErrors());
              }
              recordInserted = thisNote.insertLink(db, thisContact.hasAccount());
              if (!recordInserted) {
                processErrors(context, thisNote.getErrors());
              }
            }
          } else if (email.startsWith("P:") && !thisContact.hasAccount()) {
            if (errors == null) {
              errors = new HashMap();
            }
            errors.put("contact" + thisContact.getId(), "Message could not be sent since contact does not have an email address");
          } else if (email.startsWith("P:") && (thisContact.hasAccount() && (thisContact.getOrgId() != 0))) {
            if (errors == null) {
              errors = new HashMap();
            }
            errors.put("contact" + thisContact.getId(), "Message could not be sent since contact does not have an email address");
          }
        }
      }
      context.getSession().removeAttribute("DepartmentList");
      context.getSession().removeAttribute("ProjectListSelect");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (context.getAction().getActionName().equals("ExternalContactsCallsForward")) {
      addModuleBean(context, "External Contacts", "");
    } else if (context.getAction().getActionName().equals("MyCFSInbox")) {
      addModuleBean(context, "MyInbox", "");
    } else if (context.getAction().getActionName().equals("MyTasksForward")) {
      addModuleBean(context, "My Tasks", "");
    }
    if (errors != null) {
      processErrors(context, errors);
    }
    return this.getReturn(context, "SendMessage");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandForwardMessage(ActionContext context) {
    Connection db = null;
    int myId = -1;
    CFSNote newNote = null;
    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }

    context.getSession().removeAttribute("selectedContacts");
    context.getSession().removeAttribute("finalContacts");

    try {

      String msgId = context.getRequest().getParameter("id");
      int noteType = Integer.parseInt(context.getRequest().getParameter("forwardType"));
      db = this.getConnection(context);
      newNote = new CFSNote();
      if (noteType == Constants.CFSNOTE) {
        //For a sent message myId is a user_id else its a contactId
        if (inboxInfo.getListView().equals("sent")) {
          myId = getUserId(context);
        } else {
          myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
        }

        newNote = new CFSNote(db, Integer.parseInt(msgId), myId, inboxInfo.getListView());
        HashMap recipients = newNote.buildRecipientList(db);
        Iterator i = recipients.keySet().iterator();
        StringBuffer recipientList = new StringBuffer();
        while (i.hasNext()) {
          Object st = i.next();
          recipientList.append(st);
          if (i.hasNext()) {
            recipientList.append(",");
          }
        }
        newNote.setSubject("Fwd: " + StringUtils.toString(newNote.getSubject()));
        newNote.setBody(
            "\n\n----Original Message----\n" +
            "From: " + StringUtils.toString(newNote.getSentName()) + "\n" +
            "Sent: " + DateUtils.getServerToUserDateTimeString(this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, newNote.getEntered()) + "\n" +
            "To: " + recipientList.toString() + "\n" +
            "Subject: " + StringUtils.toString(newNote.getSubject()) +
            "\n\n" +
            StringUtils.toString(newNote.getBody()) + "\n\n");
      } else if (noteType == Constants.TASKS) {
        Task thisTask = new Task(db, Integer.parseInt(msgId));
        String userName = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getNameLastFirst();
        newNote.setBody(
            "------Task Details------\n\n" +
            "Task:" + StringUtils.toString(thisTask.getDescription()) + "\n" +
            "From: " + StringUtils.toString(userName) + "\n" +
            "Due Date: " + (thisTask.getDueDate() != null ? DateUtils.getServerToUserDateString(this.getUserTimeZone(context), DateFormat.SHORT, thisTask.getDueDate()) : "-NA-") + "\n" +
            ("".equals(thisTask.getNotes()) ? "" : "Relevant Notes: " + StringUtils.toString(thisTask.getNotes())) + "\n\n");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (context.getAction().getActionName().equals("MyCFSInbox")) {
      addModuleBean(context, "My Inbox", "");
    } else if (context.getAction().getActionName().equals("LeadsCallsForward")) {
      addModuleBean(context, "View Opportunities", "Opportunity Activities");
    } else {
      addModuleBean(context, "My Tasks", "Forward Message");
    }
    context.getRequest().setAttribute("Note", newNote);
    return ("ForwardMessageOK");
  }


  /**
   *  Sends a reply to a message
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReplyToMessage(ActionContext context) {
    Connection db = null;
    CFSNote newNote = null;
    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }
    try {
      String msgId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      int myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
      String listView = inboxInfo.getListView();
      newNote = new CFSNote(db, Integer.parseInt(msgId), myId, listView);
      HashMap recipients = newNote.buildRecipientList(db);
      Iterator i = recipients.keySet().iterator();
      StringBuffer recipientList = new StringBuffer();
      while (i.hasNext()) {
        Object st = i.next();
        recipientList.append(st);
        if (i.hasNext()) {
          recipientList.append(",");
        }
      }
      newNote.setSubject("Reply: " + StringUtils.toString(newNote.getSubject()));
      newNote.setBody(
          "\n\n----Original Message----\n" +
          "From: " + StringUtils.toString(newNote.getSentName()) + "\n" +
          "Sent: " + DateUtils.getServerToUserDateTimeString(this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, newNote.getEntered()) + "\n" +
          "To: " + recipientList.toString() + "\n" +
          "Subject: " + StringUtils.toString(newNote.getSubject()) +
          "\n\n" +
          StringUtils.toString(newNote.getBody()) + "\n\n");

      //add the sender as a recipient
      User sender = this.getUser(context, newNote.getReplyId());
      Contact recipient = new Contact(db, sender.getContactId());
      context.getRequest().setAttribute("Recipient", recipient);

      //Add the recipient to the selectedList
      HashMap thisList = null;
      if (context.getSession().getAttribute("finalContacts") != null) {
        thisList = (HashMap) context.getSession().getAttribute("finalContacts");
        thisList.clear();
      } else {
        thisList = new HashMap();
        context.getSession().setAttribute("finalContacts", thisList);
      }
      String recipientEmail = recipient.getEmailAddress("Business");
      thisList.put(new Integer(sender.getContactId()), recipientEmail);
      if (context.getSession().getAttribute("selectedContacts") != null) {
        HashMap tmp = (HashMap) context.getSession().getAttribute("selectedContacts");
        tmp.clear();
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "MyInbox", "Message Reply");
    context.getRequest().setAttribute("Note", newNote);
    return this.getReturn(context, "ReplyMessage");
  }


  /**
   *  Takes a look at the User Session Object and prepares the MyCFSBean for the
   *  JSP. The bean will contain all the information that the JSP can see.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandHome(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Home", "");
    if (getUserId(context) == 0) {
      return ("MaintenanceModeOK");
    }
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();

    //this is how we get the multiple-level heirarchy...recursive function.
    UserList shortChildList = thisRec.getShortChildList();
    UserList newUserList = thisRec.getFullChildList(shortChildList, new UserList());
    newUserList.setMyId(getUserId(context));
    newUserList.setMyValue(thisUser.getContact().getNameLastFirst());
    newUserList.setIncludeMe(true);
    newUserList.setJsEvent("onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value);javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
    HtmlSelect userListSelect = newUserList.getHtmlSelectObj("userId", getUserId(context));
    userListSelect.addAttribute("id", "userId");
    context.getRequest().setAttribute("NewUserList", userListSelect);

    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute("CalendarInfo");
    if (calendarInfo == null) {
      calendarInfo = new CalendarBean();
      if (hasPermission(context, "myhomepage-tasks-view")) {
        calendarInfo.addAlertType("Task", "org.aspcfs.modules.tasks.base.TaskListScheduledActions", "Tasks");
      }

      if (hasPermission(context, "contacts-external_contacts-calls-view") || hasPermission(context, "accounts-accounts-contacts-calls-view")) {
        calendarInfo.addAlertType("Call", "org.aspcfs.modules.contacts.base.CallListScheduledActions", "Activities");
      }
      if (hasPermission(context, "projects-projects-view")) {
        calendarInfo.addAlertType("Project", "com.zeroio.iteam.base.ProjectListScheduledActions", "Projects");
      }
      if (hasPermission(context, "accounts-accounts-view")) {
        calendarInfo.addAlertType("Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
      }
      if (hasPermission(context, "contacts-external_contacts-opportunities-view") || hasPermission(context, "pipeline-opportunities-view")) {
        calendarInfo.addAlertType("Opportunity", "org.aspcfs.modules.pipeline.base.OpportunityListScheduledActions", "Opportunities");
      }
      if (hasPermission(context, "products-view")) {
        calendarInfo.addAlertType("Quote", "org.aspcfs.modules.quotes.base.QuotesListScheduledActions", "Quotes");
      }
      if (hasPermission(context, "products-view") || hasPermission(context, "tickets-tickets-view")) {
        calendarInfo.addAlertType("Ticket", "org.aspcfs.modules.troubletickets.base.TicketListScheduledActions", "Tickets");
      }
      context.getSession().setAttribute("CalendarInfo", calendarInfo);
    }
    return "HomeOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAlerts(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    addModuleBean(context, "Home", "");
    CalendarBean calendarInfo = null;
    CalendarView companyCalendar = null;

    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");

    try {
      db = this.getConnection(context);
      calendarInfo.update(db, context);
      companyCalendar = new CalendarView(calendarInfo);
      companyCalendar.addHolidays();

      //check if the user's account is expiring
      User thisUser = this.getUser(context, this.getUserId(context));
      if (thisUser.getExpires() != null) {
        String expiryDate = DateUtils.getServerToUserDateString(this.getUserTimeZone(context), DateFormat.SHORT, thisUser.getExpires());
        companyCalendar.addEvent(expiryDate, "Your user login expires", CalendarEventList.EVENT_TYPES[9]);
      }

      //create events depending on alert type
      String selectedAlertType = calendarInfo.getCalendarDetailsView();
      String param1 = "org.aspcfs.utils.web.CalendarView";
      String param2 = "java.sql.Connection";
      ArrayList alertTypes = calendarInfo.getAlertTypes();
      for (int i = 0; i < alertTypes.size(); i++) {
        AlertType thisAlert = (AlertType) alertTypes.get(i);
        Object thisInstance = Class.forName(thisAlert.getClassName()).newInstance();
        if (selectedAlertType.equalsIgnoreCase("all") || selectedAlertType.toLowerCase().startsWith(((thisAlert.getName()).toLowerCase()))) {

          //set module
          Method method = Class.forName(thisAlert.getClassName()).getMethod("setModule", new Class[]{Class.forName("org.aspcfs.modules.actions.CFSModule")});
          method.invoke(thisInstance, new Object[]{(CFSModule) this});

          //set action context
          method = Class.forName(thisAlert.getClassName()).getMethod("setContext", new Class[]{Class.forName("com.darkhorseventures.framework.actions.ActionContext")});
          method.invoke(thisInstance, new Object[]{context});

          //set Start and End Dates
          method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeStart", new Class[]{Class.forName("java.sql.Timestamp")});
          java.sql.Timestamp startDate = DatabaseUtils.parseTimestamp(DateUtils.getUserToServerDateTimeString(calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, companyCalendar.getCalendarStartDate(context)));
          method.invoke(thisInstance, new Object[]{startDate});

          method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeEnd", new Class[]{Class.forName("java.sql.Timestamp")});
          java.sql.Timestamp endDate = DatabaseUtils.parseTimestamp(DateUtils.getUserToServerDateTimeString(calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, companyCalendar.getCalendarEndDate(context)));
          method.invoke(thisInstance, new Object[]{endDate});

          //Add Events
          method = Class.forName(thisAlert.getClassName()).getMethod("buildAlerts", new Class[]{Class.forName(param1), Class.forName(param2)});
          method.invoke(thisInstance, new Object[]{companyCalendar, db});
          if (!selectedAlertType.equalsIgnoreCase("all")) {
            break;
          }
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
    return "CalendarDetailsOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMonthView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    CalendarBean calendarInfo = null;
    CalendarView companyCalendar = null;
    addModuleBean(context, "Home", "");
    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    if (calendarInfo == null) {
      calendarInfo = new CalendarBean();
      context.getSession().setAttribute("CalendarInfo", calendarInfo);
    }

    try {
      db = this.getConnection(context);
      calendarInfo.update(db, context);
      companyCalendar = new CalendarView(calendarInfo);
      //companyCalendar.updateParams();
      //check if the user's account is expiring
      User thisUser = this.getUser(context, this.getUserId(context));
      if (thisUser.getExpires() != null) {
        String expiryDate = DateUtils.getServerToUserDateString(this.getUserTimeZone(context), DateFormat.SHORT, thisUser.getExpires());
        companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[9], expiryDate, new Integer(1));
      }
      //Use reflection to invoke methods on scheduler classes
      String param1 = "org.aspcfs.utils.web.CalendarView";
      String param2 = "java.sql.Connection";
      ArrayList alertTypes = calendarInfo.getAlertTypes();
      for (int i = 0; i < alertTypes.size(); i++) {
        AlertType thisAlert = (AlertType) alertTypes.get(i);
        Object thisInstance = Class.forName(thisAlert.getClassName()).newInstance();

        //set module
        Method method = Class.forName(thisAlert.getClassName()).getMethod("setModule", new Class[]{Class.forName("org.aspcfs.modules.actions.CFSModule")});
        method.invoke(thisInstance, new Object[]{(CFSModule) this});

        //set action context
        method = Class.forName(thisAlert.getClassName()).getMethod("setContext", new Class[]{Class.forName("com.darkhorseventures.framework.actions.ActionContext")});
        method.invoke(thisInstance, new Object[]{context});

        //set Start and End Dates
        java.sql.Timestamp startDate = DatabaseUtils.parseTimestamp(DateUtils.getUserToServerDateTimeString(calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, companyCalendar.getCalendarStartDate(context)));
        method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeStart", new Class[]{Class.forName("java.sql.Timestamp")});
        method.invoke(thisInstance, new Object[]{startDate});

        java.sql.Timestamp endDate = DatabaseUtils.parseTimestamp(DateUtils.getUserToServerDateTimeString(calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, companyCalendar.getCalendarEndDate(context)));
        method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeEnd", new Class[]{Class.forName("java.sql.Timestamp")});
        method.invoke(thisInstance, new Object[]{endDate});

        //Add Events
        method = Class.forName(thisAlert.getClassName()).getMethod("buildAlertCount", new Class[]{Class.forName(param1), Class.forName(param2)});
        method.invoke(thisInstance, new Object[]{companyCalendar, db});
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
    return "CalendarOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDayView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
      return ("PermissionError");
    }
    CalendarBean calendarInfo = null;
    addModuleBean(context, "Home", "");
    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    calendarInfo.setCalendarView("day");
    calendarInfo.resetParams("day");
    return executeCommandAlerts(context);
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandTodaysView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
      return ("PermissionError");
    }
    CalendarBean calendarInfo = null;
    addModuleBean(context, "Home", "");
    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(calendarInfo.getTimeZone());
    calendarInfo.setCalendarView("day");
    calendarInfo.resetParams("day");
    calendarInfo.setPrimaryMonth(cal.get(Calendar.MONTH) + 1);
    calendarInfo.setPrimaryYear(cal.get(Calendar.YEAR));
    return executeCommandAlerts(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandWeekView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Home", "");
    CalendarBean calendarInfo = null;
    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    calendarInfo.setCalendarView("week");
    calendarInfo.resetParams("week");
    return executeCommandAlerts(context);
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAgendaView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Home", "");
    CalendarBean calendarInfo = null;
    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    calendarInfo.setAgendaView(true);
    calendarInfo.resetParams("agenda");
    return executeCommandAlerts(context);
  }


  /**
   *  Displays a list of profile items the user can select to modify
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMyProfile(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "MyProfile", "");
    return ("MyProfileOK");
  }


  /**
   *  The user wants to modify their name, etc.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMyCFSProfile(ActionContext context) {
    if (!hasPermission(context, "myhomepage-profile-personal-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
      User thisUser = new User(db, this.getUserId(context));
      thisUser.setBuildContact(true);
      thisUser.setBuildContactDetails(true);
      thisUser.buildResources(db);
      context.getRequest().setAttribute("User", thisUser);
      context.getRequest().setAttribute("EmployeeBean", thisUser.getContact());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    }
    this.freeConnection(context, db);
    addModuleBean(context, "MyProfile", "");
    return ("ProfileOK");
  }


  /**
   *  The user's name was modified
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdateProfile(ActionContext context) {
    if (!hasPermission(context, "myhomepage-profile-personal-edit")) {
      return ("PermissionError");
    }
    //Prepare the action
    Connection db = null;
    int resultCount = 0;
    //Process the request
    Contact thisContact = (Contact) context.getFormBean();
    thisContact.setRequestItems(context);
    thisContact.setEnteredBy(getUserId(context));
    thisContact.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      resultCount = thisContact.update(db);
      if (resultCount == -1) {
        processErrors(context, thisContact.getErrors());
        buildFormElements(context, db);
      } else {
        //If the user is in the cache, update the contact record
        thisContact.checkUserAccount(db);
        this.updateUserContact(db, context, thisContact.getUserId());
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      return (executeCommandMyCFSProfile(context));
    } else if (resultCount == 1) {
      return ("UpdateProfileOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  The user wants to change the password
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMyCFSPassword(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-profile-password-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      User thisUser = new User(db, this.getUserId(context));
      thisUser.setBuildContact(false);
      thisUser.buildResources(db);
      context.getRequest().setAttribute("User", thisUser);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
    this.freeConnection(context, db);
    addModuleBean(context, "MyProfile", "");
    return ("PasswordOK");
  }


  /**
   *  The password was modified
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdatePassword(ActionContext context) {
    if (!hasPermission(context, "myhomepage-profile-password-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    User tempUser = (User) context.getFormBean();
    try {
      db = getConnection(context);
      User thisUser = new User(db, this.getUserId(context));
      thisUser.setBuildContact(false);
      thisUser.buildResources(db);
      resultCount = tempUser.updatePassword(db, context, thisUser.getPassword());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      processErrors(context, tempUser.getErrors());
      context.getRequest().setAttribute("NewUser", tempUser);
    }
    if (resultCount == -1) {
      return (executeCommandMyCFSPassword(context));
    } else if (resultCount == 1) {
      return ("UpdatePasswordOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMyCFSSettings(ActionContext context) {
    //if (!hasPermission(context, "myhomepage-profile-settings-view")) {
    if (!hasPermission(context, "myhomepage-profile-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      User thisUser = new User(db, this.getUserId(context));
      thisUser.setBuildContact(true);
      thisUser.buildResources(db);
      context.getRequest().setAttribute("User", thisUser);
      context.getRequest().setAttribute("EmployeeBean", thisUser.getContact());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
    this.freeConnection(context, db);
    addModuleBean(context, "MyProfile", "");
    return ("SettingsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdateSettings(ActionContext context) {
    //if (!(hasPermission(context, "myhomepage-profile-settings-edit"))) {
    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }
    //Process params
    String timeZone = context.getRequest().getParameter("timeZone");
    //Update the user record AND the cached record
    Connection db = null;
    try {
      db = getConnection(context);
      getUser(context, getUserId(context)).setTimeZone(timeZone);
      getUser(context, getUserId(context)).updateSettings(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return ("UpdateSettingsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */

  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    LookupList departmentList = new LookupList(db, "lookup_department");
    departmentList.addItem(0, "--None--");
    context.getRequest().setAttribute("DepartmentList", departmentList);

    LookupList phoneTypeList = new LookupList(db, "lookup_contactphone_types");
    context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

    LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    LookupList addressTypeList = new LookupList(db, "lookup_contactaddress_types");
    context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);
  }
}

