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
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.accounts.base.NewsArticleList;

import com.zeroio.iteam.base.*;
import java.sql.*;
import java.lang.reflect.*;
import java.util.*;

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

    Exception errorMessage = null;
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

      if ("sent".equals(inboxInfo.getListView())) {
        noteList.setSentMessagesOnly(true);
      }

      noteList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("CFSNoteList", noteList);
      addModuleBean(context, "MyInbox", "Inbox Home");
      return ("InboxOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
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

    /*
     *  Process all parameters
     */
    Enumeration parameters = context.getRequest().getParameterNames();
    Exception errorMessage = null;

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
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "", "Headline Delete OK");
      return ("DeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
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
    Exception errorMessage = null;
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
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "MyInbox", "Inbox Home");
      return (executeCommandInbox(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
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
    Exception errorMessage = null;
    int myId = -1;

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
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "MyInbox", "Inbox Home");
      return (executeCommandInbox(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
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

    if (!(hasPermission(context, "myhomepage-miner-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "Customize Headlines", "Customize Headlines");
    Exception errorMessage = null;

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
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OrgList", organizationList);
      return ("HeadlineOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
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
    Exception errorMessage = null;
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
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OrgDetails", newOrg);
      return (executeCommandHeadline(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
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
    Exception errorMessage = null;
    Connection db = null;
    int myId = -1;
    CFSNote newNote = null;
    try {
      int msgId = Integer.parseInt(context.getRequest().getParameter("id"));
      db = this.getConnection(context);
      /*
       *  For a sent message myId is a user_id else its a contactId
       */
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
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "MyInbox", "Inbox Details");
      context.getRequest().setAttribute("NoteDetails", newNote);
      return ("CFSNoteDetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
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
    Exception errorMessage = null;
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
    } catch (Exception e) {
      errorMessage = e;
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
    Exception errorMessage = null;
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
        recordInserted = thisNote.insert(db);
        if (!recordInserted) {
          processErrors(context, thisNote.getErrors());
        }
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
          recordInserted = thisNote.insertLink(db, thisContact.hasAccount());
          if (!recordInserted) {
            processErrors(context, thisNote.getErrors());
          }

          if ((!email.startsWith("P:")) && (copyrecipients || !thisContact.hasAccount())) {
            SMTPMessage mail = new SMTPMessage();
            mail.setHost((String) System.getProperty("MailServer"));
            mail.setFrom("cfs-messenger@darkhorseventures.com");
            if (replyAddr != null && !(replyAddr.equals(""))) {
              mail.addReplyTo(replyAddr);
            }
            mail.setType("text/html");
            mail.setTo(email);
            mail.setSubject(thisNote.getSubject());
            mail.setBody("The following message was sent to your CFS Inbox by " + thisRecord.getUsername() + ".  This copy has been sent to your email account at the request of the sender.<br><br>--- Original Message ---<br><br>" + StringUtils.toHtml(thisNote.getBody()));
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
            }
          } else if (email.startsWith("P:") && !thisContact.hasAccount()) {
            if (errors == null) {
              errors = new HashMap();
            }
            errors.put("contact" + thisContact.getId(), "Message could not be sent since contact does not have an email address");
          }
        }
      }
      context.getSession().removeAttribute("DepartmentList");
      context.getSession().removeAttribute("ProjectListSelect");
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (context.getAction().getActionName().equals("ExternalContactsCallsForward")) {
      addModuleBean(context, "External Contacts", "");
    } else if (context.getAction().getActionName().equals("MyCFSInbox")) {
      addModuleBean(context, "MyInbox", "");
    } else {
      addModuleBean(context, "My Tasks", "");
    }

    if (errorMessage == null) {
      if (errors != null) {
        processErrors(context, errors);
      }
      return ("SendMessageOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandForwardMessage(ActionContext context) {
    Connection db = null;
    Exception errorMessage = null;
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
      if (context.getSession().getAttribute("DepartmentList") == null) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(-1, "--All Departments--");
        context.getSession().setAttribute("DepartmentList", departmentList);
      }

      if (context.getSession().getAttribute("ProjectListSelect") == null) {
        ProjectList projects = new ProjectList();
        projects.setUserRange(this.getUserRange(context));
        projects.setBuildAssignments(false);
        projects.setBuildIssues(false);
        projects.setGroupId(-1);
        projects.buildList(db);
        HtmlSelect htmlSelect = projects.getHtmlSelect();
        htmlSelect.addItem(-1, "--All Projects--", 0);
        context.getSession().setAttribute("ProjectListSelect", htmlSelect);
      }
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
            "Sent: " + newNote.getEnteredDateTimeString() + "\n" +
            "To: " + recipientList.toString() + "\n" +
            "Subject: " + StringUtils.toString(newNote.getSubject()) +
            "\n\n" +
            StringUtils.toString(newNote.getBody()) + "\n\n");
      } else if (noteType == Constants.CONTACTS_CALLS || noteType == Constants.PIPELINE_CALLS) {
        Call thisCall = new Call(db, msgId);
        newNote.setBody(
            "Contact Name: " + StringUtils.toString(thisCall.getContactName()) + "\n" +
            "Type: " + StringUtils.toString(thisCall.getCallType()) + "\n" +
            "Length: " + StringUtils.toString(thisCall.getLengthText()) + "\n" +
            "Subject: " + StringUtils.toString(thisCall.getSubject()) + "\n" +
            "Notes: " + StringUtils.toString(thisCall.getNotes()) + "\n" +
            "Entered: " + StringUtils.toString(thisCall.getEnteredName()) + " - " + thisCall.getEnteredString() + "\n" +
            "Modified: " + StringUtils.toString(thisCall.getModifiedName()) + " - " + thisCall.getModifiedString());
      } else if (noteType == Constants.TASKS) {
        Task thisTask = new Task(db, Integer.parseInt(msgId));
        String userName = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getNameLastFirst();
        newNote.setBody(
            "------Task Details------\n\n" +
            "Task:" + StringUtils.toString(thisTask.getDescription()) + "\n" +
            "From: " + StringUtils.toString(userName) + "\n" +
            "Due Date: " + thisTask.getDueDateString() + "\n" +
            ("".equals(thisTask.getNotes()) ? "" : "Relevant Notes: " + StringUtils.toString(thisTask.getNotes())) + "\n\n");
      }
      context.getSession().removeAttribute("selectedContacts");
      context.getSession().removeAttribute("finalContacts");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (context.getAction().getActionName().equals("ExternalContactsCallsForward")) {
        addModuleBean(context, "External Contacts", "");
      } else if (context.getAction().getActionName().equals("MyCFSInbox")) {
        addModuleBean(context, "My Inbox", "");
      } else if (context.getAction().getActionName().equals("LeadsCallsForward")) {
        addModuleBean(context, "View Opportunities", "Opportunity Calls");
      } else {
        addModuleBean(context, "My Tasks", "");
      }
      context.getRequest().setAttribute("Note", newNote);
      return ("ForwardMessageOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Sends a reply to a message
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReplyToMessage(ActionContext context) {
    Connection db = null;
    Exception errorMessage = null;
    CFSNote newNote = null;
    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }

    try {

      String msgId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      int myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
      newNote = new CFSNote(db, Integer.parseInt(msgId), myId, "new");
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
          "Sent: " + newNote.getEnteredDateTimeString() + "\n" +
          "To: " + recipientList.toString() + "\n" +
          "Subject: " + StringUtils.toString(newNote.getSubject()) +
          "\n\n" +
          StringUtils.toString(newNote.getBody()) + "\n\n");
     
     //add the sender as a recipient
     Contact recipient = new Contact(db, newNote.getReplyId());
     context.getRequest().setAttribute("Recipient", recipient);
     
   //Add the recipient to the selectedList
   HashMap thisList = null;
    if(context.getSession().getAttribute("finalContacts") != null){
      thisList = (HashMap) context.getSession().getAttribute("finalContacts");
      thisList.clear();
    }else{
      thisList = new HashMap();
      context.getSession().setAttribute("finalContacts", thisList);
    }
     thisList.put(new Integer(newNote.getReplyId()), "");
     if(context.getSession().getAttribute("selectedContacts") != null){
      HashMap tmp = (HashMap) context.getSession().getAttribute("selectedContacts");
      tmp.clear();
     }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (context.getAction().getActionName().equals("MyCFSInbox")) {
        addModuleBean(context, "My Inbox", "");
      }
      context.getRequest().setAttribute("Note", newNote);
      return this.getReturn(context, "ReplyMessage");
    } 
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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
    Exception errorMessage = null;
    NewsArticleList newsList = null;
    Connection db = null;
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.

    User thisRec = thisUser.getUserRecord();

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
      calendarInfo.addAlertType("Task", "org.aspcfs.modules.tasks.base.TaskListScheduledActions", "Tasks");
      calendarInfo.addAlertType("Call", "org.aspcfs.modules.contacts.base.CallListScheduledActions", "Calls");
      calendarInfo.addAlertType("Project", "com.zeroio.iteam.base.ProjectListScheduledActions", "Projects");
      calendarInfo.addAlertType("Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
      calendarInfo.addAlertType("Opportunity", "org.aspcfs.modules.pipeline.base.OpportunityListScheduledActions", "Opportunity");
      context.getSession().setAttribute("CalendarInfo", calendarInfo);
    }

    String industryCheck = context.getRequest().getParameter("industry");
    PagedListInfo newsListInfo = new PagedListInfo();

    try {
      db = this.getConnection(context);

      NewsArticleList newsArticleList = new NewsArticleList();
      newsArticleList.setIndustryCode(1);

      newsArticleList.setEnteredBy(getUserId(context));
      newsArticleList.buildList(db);

      LookupList indSelect = new LookupList(db, "lookup_industry");
      indSelect.setJsEvent("onChange=\"document.forms['miner_select'].submit();\"");
      indSelect.addItem(0, "Latest News");
      context.getRequest().setAttribute("IndSelect", indSelect);

      if (newsArticleList.size() > 0) {
        indSelect.addItem(1, "My News");

        if (industryCheck == null) {
          industryCheck = "1";
        }
      }

      newsList = new NewsArticleList();
      newsList.setPagedListInfo(newsListInfo);

      if (industryCheck != null && !(industryCheck.equals("0"))) {
        newsList.setIndustryCode(industryCheck);
        if (industryCheck.equals("1")) {
          newsList.setEnteredBy(getUserId(context));
        }
      } else if (industryCheck == null || industryCheck.equals("0")) {
        newsList.setMinerOnly(false);
      }

      newsList.buildList(db);

    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("NewsList", newsList);
      return "HomeOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAlerts(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "Home", "");
    CalendarBean calendarInfo = null;

    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    CalendarView companyCalendar = new CalendarView(context.getRequest());

    try {
      db = this.getConnection(context);
      calendarInfo.update(db, context);
      companyCalendar.setCalendarInfo(calendarInfo);
      companyCalendar.updateParams();
      companyCalendar.addHolidaysByRange();

      //create events depending on alert type
      String selectedAlertType = calendarInfo.getCalendarDetailsView();
      String param1 = "org.aspcfs.utils.web.CalendarView";
      String param2 = "java.sql.Connection";
      ArrayList alertTypes = calendarInfo.getAlertTypes();
      for (int i = 0; i < alertTypes.size(); i++) {
        AlertType thisAlert = (AlertType) alertTypes.get(i);
        Object thisInstance = Class.forName(thisAlert.getClassName()).newInstance();
        if (selectedAlertType.equalsIgnoreCase("all") || selectedAlertType.toLowerCase().startsWith(((thisAlert.getName()).toLowerCase()))) {
          //set UserId
          Method method = Class.forName(thisAlert.getClassName()).getMethod("setUserId", new Class[]{int.class});
          method.invoke(thisInstance, new Object[]{new Integer(calendarInfo.getSelectedUserId())});

          //set Start and End Dates
          method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeStart", new Class[]{Class.forName("java.sql.Date")});
          method.invoke(thisInstance, new Object[]{companyCalendar.getCalendarStartDate(context)});

          method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeEnd", new Class[]{Class.forName("java.sql.Date")});
          method.invoke(thisInstance, new Object[]{companyCalendar.getCalendarEndDate(context)});

          //Add Events
          method = Class.forName(thisAlert.getClassName()).getMethod("buildAlerts", new Class[]{Class.forName(param1), Class.forName(param2)});
          method.invoke(thisInstance, new Object[]{companyCalendar, db});
          if (!selectedAlertType.equalsIgnoreCase("all")) {
            break;
          }
        }
      }
    } catch (SQLException e) {
      errorMessage = e;
    } catch (Exception e) {
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
      return "CalendarDetailsOK";
    }
    return "SystemError";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMonthView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    CalendarBean calendarInfo = null;
    CalendarView companyCalendar = null;
    addModuleBean(context, "Home", "");
    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    if (context.getRequest().getAttribute("CompanyCalendar") != null) {
      companyCalendar = (CalendarView) context.getRequest().getAttribute("CompanyCalendar");
    } else {
      companyCalendar = new CalendarView(context.getRequest());
    }

    try {
      db = this.getConnection(context);
      calendarInfo.update(db, context);
      companyCalendar.setCalendarInfo(calendarInfo);
      companyCalendar.updateParams();

      //Use reflection to invoke methods on scheduler classes

      String param1 = "org.aspcfs.utils.web.CalendarView";
      String param2 = "java.sql.Connection";
      ArrayList alertTypes = calendarInfo.getAlertTypes();
      for (int i = 0; i < alertTypes.size(); i++) {
        AlertType thisAlert = (AlertType) alertTypes.get(i);
        Object thisInstance = Class.forName(thisAlert.getClassName()).newInstance();

        //set UserId
        Method method = Class.forName(thisAlert.getClassName()).getMethod("setUserId", new Class[]{int.class});
        method.invoke(thisInstance, new Object[]{new Integer(calendarInfo.getSelectedUserId())});

        //set Start and End Dates
        method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeStart", new Class[]{Class.forName("java.sql.Date")});
        method.invoke(thisInstance, new Object[]{companyCalendar.getCalendarStartDate(context)});

        method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeEnd", new Class[]{Class.forName("java.sql.Date")});
        method.invoke(thisInstance, new Object[]{companyCalendar.getCalendarEndDate(context)});

        //Add Events
        method = Class.forName(thisAlert.getClassName()).getMethod("buildAlertCount", new Class[]{Class.forName(param1), Class.forName(param2)});
        method.invoke(thisInstance, new Object[]{companyCalendar, db});
      }
    } catch (SQLException e) {
      errorMessage = e;
    } catch (Exception e) {
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
      return "CalendarOK";
    }
    return "SystemError";
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDayView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    CalendarBean calendarInfo = null;
    addModuleBean(context, "Home", "");

    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    try {
      db = this.getConnection(context);
      calendarInfo.update(db, context);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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
  public String executeCommandWeekView(ActionContext context) {

    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "Home", "");
    CalendarBean calendarInfo = null;

    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");
    try {
      db = this.getConnection(context);
      calendarInfo.update(db, context);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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

    if (!(hasPermission(context, "myhomepage-profile-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "Home", "");
    CalendarBean calendarInfo = null;
    String returnPage = context.getRequest().getParameter("return");
    calendarInfo = (CalendarBean) context.getSession().getAttribute(returnPage != null ? returnPage + "CalendarInfo" : "CalendarInfo");

    try {
      db = this.getConnection(context);
      calendarInfo.update(db, context);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    calendarInfo.resetParams("agenda");
    calendarInfo.setAgendaView(true);
    executeCommandAlerts(context);
    return ("CalendarDetailsOK");
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
    Exception errorMessage = null;
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
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
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
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    //Process the request
    Contact thisContact = (Contact) context.getFormBean();
    thisContact.setRequestItems(context.getRequest());
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
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (resultCount == -1) {
        return (executeCommandMyCFSProfile(context));
      } else if (resultCount == 1) {
        return ("UpdateProfileOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      User thisUser = new User(db, this.getUserId(context));
      thisUser.setBuildContact(false);
      thisUser.buildResources(db);
      context.getRequest().setAttribute("User", thisUser);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
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
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    User tempUser = (User) context.getFormBean();
    try {
      db = getConnection(context);
      User thisUser = new User(db, this.getUserId(context));
      thisUser.setBuildContact(false);
      thisUser.buildResources(db);
      resultCount = tempUser.updatePassword(db, context, thisUser.getPassword());
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      processErrors(context, tempUser.getErrors());
      context.getRequest().setAttribute("NewUser", tempUser);
    }
    if (errorMessage == null) {
      if (resultCount == -1) {
        return (executeCommandMyCFSPassword(context));
      } else if (resultCount == 1) {
        return ("UpdatePasswordOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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
    if (!hasPermission(context, "myhomepage-profile-settings-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      User thisUser = new User(db, this.getUserId(context));
      thisUser.setBuildContact(true);
      thisUser.buildResources(db);
      context.getRequest().setAttribute("User", thisUser);
      context.getRequest().setAttribute("EmployeeBean", thisUser.getContact());
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
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

    if (!(hasPermission(context, "myhomepage-profile-settings-edit"))) {
      return ("PermissionError");
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

