package org.aspcfs.modules.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Vector;
import org.aspcfs.utils.web.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    February 21, 2002
 *@version    $Id$
 */
public final class ForwardNote extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandShowForm(ActionContext context) {
    Exception errorMessage = null;

    String linkModId = (String) context.getRequest().getParameter("linkModuleId");
    String linkRecId = (String) context.getRequest().getParameter("linkRecordId");

    CFSNote thisNote = new CFSNote();
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    UserList list = new UserList();
    list.setEnabled(UserList.TRUE);
    list.setBuildContact(false);
    list.setBuildHierarchy(false);

    Connection db = null;

    try {
      db = getConnection(context);

      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "-- Select Department --");
      context.getRequest().setAttribute("DepartmentList", departmentList);

      if (linkModId != null && linkRecId != null) {
        //forwarding a call
        if (Integer.parseInt(linkModId) == Constants.CONTACTS_CALLS) {
          Call thisCall = new Call(db, linkRecId);
          thisNote.setBody("Contact Name: " + thisCall.getContactName() + "\nType: " + thisCall.getCallType() + "\nLength: " + thisCall.getLengthText() +
              "\nSubject: " + thisCall.getSubject() + "\nNotes: " + thisCall.getNotes() + "\nEntered: "
               + thisCall.getEnteredName() + " - " + thisCall.getEnteredString() + "\nModified: " +
              thisCall.getModifiedName() + " - " + thisCall.getModifiedString());
          context.getRequest().setAttribute("CallDetails", thisCall);
        }
        //forwarding a note
        else if (Integer.parseInt(linkModId) == Constants.CFSNOTE) {
          thisNote = new CFSNote(db, linkRecId, getUserId(context), "none");
          thisNote.setBody("----Original Message----\nFrom: " + thisNote.getSentName() + "\nSent: " + thisNote.getEnteredDateTimeString() + "\nTo: " +
              thisUser.getNameFirst() + " " + thisUser.getNameLast() +
              "\nSubject: " + thisNote.getSubject() + "\n\n" + thisNote.getBody());

          if (!(thisNote.getSubject().startsWith("Fwd:"))) {
            thisNote.setSubject("Fwd: " + thisNote.getSubject());
          }
        }
      }

      list.buildList(db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("UserList", list);
      context.getRequest().setAttribute("NoteDetails", thisNote);
      if (context.getRequest().getParameter("popup") != null) {
        return ("PopupOK");
      } else {
        return ("ProjectCenterOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateUserList(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      String deptId = context.getRequest().getParameter("deptId");
      db = this.getConnection(context);
      UserList userList = new UserList();
      if (deptId != null) {
        userList.setDepartment(deptId);
      }
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("MakeUserListOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandForward(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
    int k = 0;

    UserList list = new UserList();
    list.setEnabled(UserList.TRUE);
    list.setBuildContact(false);
    list.setBuildHierarchy(false);

    CFSNote thisNote = (CFSNote) context.getFormBean();
    thisNote.setEnteredBy(getUserId(context));
    thisNote.setModifiedBy(getUserId(context));
    thisNote.setReplyId(getUserId(context));
    thisNote.setType(CFSNote.CALL);

    User thisRecord = (User) ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    thisRecord.setBuildContact(true);

    Connection db = null;

    try {
      db = this.getConnection(context);
      list.buildList(db);
      context.getRequest().setAttribute("NoteDetails", thisNote);
      thisRecord.buildResources(db);
      recordInserted = thisNote.insert(db);
      if (!recordInserted) {
        processErrors(context, thisNote.getErrors());
      }

      String replyAddr = thisRecord.getContact().getEmailAddress("Business");
      String[] params = context.getRequest().getParameterValues("selectedList");
      for (k = 0; k < params.length; k++) {
        thisNote.setSentTo(Integer.parseInt(params[k]));
        recordInserted = thisNote.insertLink(db, true);

        if (!recordInserted) {
          processErrors(context, thisNote.getErrors());
        } else if (recordInserted && context.getRequest().getParameter("email1") != null) {
          User tempUser = new User(db, thisNote.getSentTo());
          tempUser.setBuildContact(true);
          tempUser.buildResources(db);

          SMTPMessage mail = new SMTPMessage();
          mail.setHost((String) System.getProperty("MailServer"));
          mail.setFrom("cfs-messenger@darkhorseventures.com");
          if (replyAddr != null && !(replyAddr.equals(""))) {
            mail.addReplyTo(replyAddr);
          }
          mail.setType("text/html");
          mail.setTo(tempUser.getContact().getEmailAddress("Business"));
          mail.setSubject(thisNote.getSubject());
          mail.setBody("The following message was sent to your CFS Inbox by " + thisNote.getSentName() + ".  This copy has been sent to your email account at the request of the sender.<br><br>--- Original Message ---<br><br>" + toHtml(thisNote.getBody()));
          if (mail.send() == 2) {
            System.out.println("Send error: " + mail.getErrorMsg() + "<br><br>");
            System.err.println(mail.getErrorMsg());
          } else {
            System.out.println("Error sending message to " + tempUser.getContact().getEmailAddress("Business"));
          }
        }
      }

    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("UserList", list);
      if (!recordInserted) {
        return ("PopupOK");
      } else {
        return ("PopupCloseOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String toHtml(String s) {
    if (s != null) {
      if (s.trim().equals("")) {
        return ("&nbsp;");
      } else {
        return HTTPUtils.toHtmlValue(s);
      }
    } else {
      return ("&nbsp;");
    }
  }
}

