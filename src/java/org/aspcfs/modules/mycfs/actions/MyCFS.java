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
    String returnUrl = "";
    String sendUrl = "";
    Exception errorMessage = null;
    Connection db = null;
    context.getSession().removeAttribute("selectedContacts");
    context.getSession().removeAttribute("finalContacts");
    context.getSession().removeAttribute("contactListInfo");
    try {
      db = this.getConnection(context);
      CFSNote newNote = new CFSNote();
      context.getRequest().setAttribute("Note", newNote);
      returnUrl = context.getRequest().getParameter("return");
      sendUrl = context.getRequest().getParameter("sendUrl");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (returnUrl != null) {
      context.getRequest().setAttribute("returnUrl", returnUrl);
      context.getRequest().setAttribute("sendUrl", sendUrl);
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
  public String executeCommandContactList(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
      return ("PermissionError");
    }

    PagedListInfo contactListInfo = this.getPagedListInfo(context, "ContactListInfo");
    Exception errorMessage = null;
    Connection db = null;
    ContactList contactList = null;
    contactListInfo.setLink("MyCFSInbox.do?command=ContactList");
    contactListInfo.setEnableJavaScript(true);
    String firstFilter = "";
    String secondFilter = "";
    //Parent form needs contact name to be displayed and not email id
    Vector parentFieldType = new Vector();
    parentFieldType.addElement("single");
    parentFieldType.addElement("contactsingle");

    HashMap selectedList = (HashMap) context.getSession().getAttribute("selectedContacts");

    /*
     *  Flush the selectedList if its a new selection
     */
    if (context.getRequest().getParameter("flushtemplist") != null) {
      if (((String) context.getRequest().getParameter("flushtemplist")).equalsIgnoreCase("true")) {
        if (context.getSession().getAttribute("finalContacts") != null) {
          selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalContacts")).clone();
        }
      }
    }

    if (!contactListInfo.hasListFilters()) {
      //filter for departments & project teams
      contactListInfo.addFilter(1, "0");
    }

    HashMap finalContactList = (HashMap) context.getSession().getAttribute("finalContacts");
    boolean listDone = false;
    try {
      db = this.getConnection(context);

      //parentFieldType is the type of selection required i.e list or single
      if (context.getRequest().getParameter("parentFieldType") != null) {
        contactListInfo.setParentFieldType(context.getRequest().getParameter("parentFieldType"));
      }
      //Form on the page which pops up contactList
      if (context.getRequest().getParameter("parentFormName") != null) {
        contactListInfo.setParentFormName(context.getRequest().getParameter("parentFormName"));
      }
      //Build Department List if empty
      if (context.getSession().getAttribute("DepartmentList") == null) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(-1, "--All Departments--");
        context.getSession().setAttribute("DepartmentList", departmentList);
      }
      //Build Project List if empty
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

      firstFilter = contactListInfo.getListView();
      contactList = new ContactList();

      /*
       *  Collect the selected entries in the contactList & store it in the session's HashMap i.e checkcontact
       *  checkcontact+rowCount is the checkbox name (value is  the contact_id)
       *  Single Email   : email as a hidden value contactemail+rowCount
       *  Multiple Emails: email as a value of selected entry from comboBox i.e contactemail_rowCount
       */
      int rowCount = 1;
      if (!parentFieldType.contains(contactListInfo.getParentFieldType())) {
        while (context.getRequest().getParameter("hiddencontactid" + rowCount) != null) {
          int contactId = 0;
          String emailAddress = "";
          contactId = Integer.parseInt(context.getRequest().getParameter("hiddencontactid" + rowCount));
          if (context.getRequest().getParameter("checkcontact" + rowCount) != null) {
            if (context.getRequest().getParameter("contactemail" + rowCount) != null) {
              emailAddress = context.getRequest().getParameter("contactemail" + rowCount);
            }

            //If User does not have a emailAddress replace with Name(LastFirst)
            if (emailAddress.equals("") || parentFieldType.contains(contactListInfo.getParentFieldType())) {
              if (context.getRequest().getParameter("hiddenname" + rowCount) != null) {
                emailAddress = "P:" + context.getRequest().getParameter("hiddenname" + rowCount);
              }
            }

            if (selectedList.get(new Integer(contactId)) == null) {
              selectedList.put(new Integer(contactId), emailAddress);
            } else {
              selectedList.remove(new Integer(contactId));
              selectedList.put(new Integer(contactId), emailAddress);
            }
          } else {
            selectedList.remove(new Integer(contactId));
          }
          rowCount++;
        }
      }

      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase("true")) {
          //If single selection then get count of row selected & fill HashMap with name & contactId
          if (parentFieldType.contains(contactListInfo.getParentFieldType())) {
            rowCount = Integer.parseInt(context.getRequest().getParameter("rowcount"));
            String emailAddress = context.getRequest().getParameter("hiddenname" + rowCount);
            int contactId = Integer.parseInt(context.getRequest().getParameter("hiddencontactid" + rowCount));
            selectedList.clear();
            selectedList.put(new Integer(contactId), emailAddress);
          }
          listDone = true;
          finalContactList = (HashMap) selectedList.clone();
        }
      }

      if (context.getRequest().getParameter("listFilter1") != null) {
        secondFilter = context.getRequest().getParameter("listFilter1");
      }

      //  set Filter for retrieving addresses depending on typeOfContact
      if ((firstFilter == null || firstFilter.equals(""))) {
        firstFilter = "all";
      }
      if (firstFilter.equalsIgnoreCase("all")) {
        contactList.setPersonalId(getUserId(context));
        contactList.setOwnerIdRange(this.getUserRange(context));
      }
      if (firstFilter.equalsIgnoreCase("employees")) {
        contactList.setTypeId(Contact.EMPLOYEE_TYPE);
        if (!secondFilter.equals("")) {
          contactList.setDepartmentId(Integer.parseInt(secondFilter));
        }
      }
      if (firstFilter.equalsIgnoreCase("mycontacts")) {
        contactList.setPersonalId(getUserId(context));
        contactList.setOwner(getUserId(context));
      }
      if (firstFilter.equalsIgnoreCase("accountcontacts")) {
        contactList.setWithAccountsOnly(true);
      }
      if (firstFilter.equalsIgnoreCase("myprojects")) {
        contactList.setWithProjectsOnly(true);
        if (!secondFilter.equals("")) {
          contactList.setProjectId(Integer.parseInt(secondFilter));
        }
      }
      contactListInfo.setListView(firstFilter);
      contactList.setPagedListInfo(contactListInfo);
      contactList.setCheckUserAccess(true);
      contactList.setBuildDetails(true);
      contactList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("ContactList", contactList);
      context.getSession().setAttribute("selectedContacts", selectedList);
      if (listDone) {
        context.getSession().setAttribute("finalContacts", finalContactList);
      }
      return ("CFSContactListOK");
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

  public String executeCommandSendMessage(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;
    boolean savecopy = false;
    boolean copyrecipients = false;
    HashMap selectedList = (HashMap) context.getSession().getAttribute("finalContacts");
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

          thisContact = new Contact(db, hashKey.toString());
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
            } else {
              if (System.getProperty("DEBUG") != null) {
                System.out.println("MyCFS-> Sending message to " + email);
              }
            }
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
      context.getRequest().setAttribute("returnUrl", context.getRequest().getParameter("return"));
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
    String returnUrl = context.getRequest().getParameter("return");
    String sendUrl = context.getRequest().getParameter("sendUrl");

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
      } else if (noteType == Constants.CONTACTS_CALLS) {
        Call thisCall = new Call(db, msgId);
        //add Contact Info for trails
        context.getRequest().setAttribute("ContactDetails", new Contact(db, thisCall.getContactId()));
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
      } else {
        addModuleBean(context, "My Tasks", "");
      }
      context.getRequest().setAttribute("Note", newNote);
      context.getRequest().setAttribute("returnUrl", returnUrl);
      context.getRequest().setAttribute("sendUrl", sendUrl);
      return ("ForwardMessageOK");
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
    newUserList.setMyValue(thisUser.getUserRecord().getContact().getNameLastFirst());
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
        AlertType thisAlert = (AlertType)alertTypes.get(i);
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
        AlertType thisAlert = (AlertType)alertTypes.get(i);
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

    if (!(hasPermission(context, "myhomepage-profile-personal-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
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
    if (!(hasPermission(context, "myhomepage-profile-personal-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

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

    if (!(hasPermission(context, "myhomepage-profile-password-edit"))) {
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

    if (!(hasPermission(context, "myhomepage-profile-settings-view"))) {
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

