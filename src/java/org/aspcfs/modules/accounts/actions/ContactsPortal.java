package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.DateUtils;

/**
 *  Action class to view, add, edit, disable and enable a portal user
 *
 *@author     kbhoopal
 *@created    April 7, 2004
 *@version    $Id: ContactsPortal.java,v 1.1.2.2 2004/04/12 14:18:21 kbhoopal
 *      Exp $
 */
public final class ContactsPortal extends CFSModule {

  /**
   *  Prepares the view page to display portal user information
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "portal-user-view")) {
      return ("PermissionError");
    }

    Connection db = null;
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, id);
      User thisPortalUser = new User();
      if (thisContact.getUserId() > -1) {
        thisPortalUser.buildRecord(db, thisContact.getUserId());
      }
      setOrganization(context, db, thisContact.getOrgId());
      context.getRequest().setAttribute("ContactDetails", thisContact);
      context.getRequest().setAttribute("portalUserDetails", thisPortalUser);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return ("ViewContactPortalOK");
  }


  /**
   *  Prepares the add page to add a portal user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "portal-user-add")) {
      return ("PermissionError");
    }

    Connection db = null;
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("contactId");
      if (id != null) {
        thisContact = new Contact(db, id);
      } else {
        thisContact = (Contact) context.getFormBean();
      }
      setOrganization(context, db, thisContact.getOrgId());
      ContactEmailAddressList emailList = thisContact.getEmailAddressList();
      if (emailList.size() == 0) {
        context.getRequest().setAttribute("ContactDetails", thisContact);
        return ("ContactPortalError");
      }

      RoleList roleList = new RoleList();
      roleList.setExcludeRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.buildList(db);
      roleList.setEmptyHtmlSelectRecord("--None--");
      context.getRequest().setAttribute("roleList", roleList);

      thisContact.getEmailAddressList().setEmptyHtmlSelectRecord("--None--");
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return ("AddContactPortalOK");
  }


  /**
   *  Saves portal user information and mails the information to the portal user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "portal-user-add")) {
      return ("PermissionError");
    }

    Connection db = null;
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, id);
      insertUser(context, db, thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return executeCommandView(context);
  }


  /**
   *  Prepares the modify page to modify portal user information
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "portal-user-edit")) {
      return ("PermissionError");
    }

    Connection db = null;
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, id);

      //Cannot change portal login information if the
      //user contact does not have email
      ContactEmailAddressList emailList = thisContact.getEmailAddressList();
      if (emailList.size() == 0) {
        context.getRequest().setAttribute("ContactDetails", thisContact);
        return ("ContactPortalError");
      }

      User thisPortalUser = new User();
      thisPortalUser.buildRecord(db, thisContact.getUserId());

      setOrganization(context, db, thisContact.getOrgId());

      RoleList roleList = new RoleList();
      roleList.setExcludeRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.buildList(db);
      roleList.setEmptyHtmlSelectRecord("--None--");
      context.getRequest().setAttribute("roleList", roleList);

      thisContact.getEmailAddressList().setEmptyHtmlSelectRecord("--None--");
      context.getRequest().setAttribute("ContactDetails", thisContact);
      context.getRequest().setAttribute("portalUserDetails", thisPortalUser);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return ("ModifyContactPortalOK");
  }


  /**
   *  Saves portal user information and mails the information to the portal user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "portal-user-edit")) {
      return ("PermissionError");
    }

    Connection db = null;
    Contact oldContactInfo = null;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("contactId");
      oldContactInfo = new Contact(db, id);

      User oldUserInfo = new User();
      oldUserInfo.buildRecord(db, oldContactInfo.getUserId());
      updateUser(context, db, oldContactInfo, oldUserInfo);
      setOrganization(context, db, oldContactInfo.getOrgId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandView(context);
  }


  /**
   *  Deletes a portal user //NOT USED
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "portal-user-delete")) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return ("DeleteContactPortalOK");
  }


  /**
   *  Disables a portal user and sends mail to the user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDisable(ActionContext context) {
    if (!hasPermission(context, "portal-user-delete")) {
      return ("PermissionError");
    }

    Connection db = null;
    User thisUser = null;
    Contact thisContact = null;
    boolean recordCount = false;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, id);
      setOrganization(context, db, thisContact.getOrgId());      
      //Cannot change portal login information if the
      //user contact does not have email
      ContactEmailAddressList emailList = thisContact.getEmailAddressList();
      if (emailList.size() == 0) {
        context.getRequest().setAttribute("ContactDetails", thisContact);
        return ("ContactPortalError");
      }

      thisUser = new User(db, context.getRequest().getParameter("userId"));
      recordCount = thisUser.delete(db);

      if (recordCount) {
        ContactEmailAddress emailAddress = getAddressToEmail(thisContact);

        SMTPMessage mail = new SMTPMessage();
        mail.setHost(ApplicationPrefs.getPref(context.getServletContext(), "MAILSERVER"));
        mail.setFrom(ApplicationPrefs.getPref(context.getServletContext(), "EMAILADDRESS"));
        mail.setType("text/html");
        mail.setTo(emailAddress.getEmail());
        mail.setSubject("Login information");
        mail.setBody("Your self-service account has been disabled<br /><br />");
        if (mail.send() == 2) {
          System.err.println(mail.getErrorMsg());
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return executeCommandView(context);
  }


  /**
   *  Enables a portal user sends mail to the user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    if (!hasPermission(context, "portal-user-edit")) {
      return ("PermissionError");
    }

    Connection db = null;
    User thisUser = null;
    Contact thisContact = null;
    boolean recordCount = false;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, id);
      //Cannot change portal login information if the
      //user contact does not have email
      ContactEmailAddressList emailList = thisContact.getEmailAddressList();
      if (emailList.size() == 0) {
        context.getRequest().setAttribute("ContactDetails", thisContact);
        return ("ContactPortalError");
      }

      thisUser = new User(db, context.getRequest().getParameter("userId"));
      recordCount = thisUser.enable(db);

      if (recordCount) {
        ContactEmailAddress emailAddress = getAddressToEmail(thisContact);

        SMTPMessage mail = new SMTPMessage();
        mail.setHost(ApplicationPrefs.getPref(context.getServletContext(), "MAILSERVER"));
        mail.setFrom(ApplicationPrefs.getPref(context.getServletContext(), "EMAILADDRESS"));
        mail.setType("text/html");
        mail.setTo(emailAddress.getEmail());
        mail.setSubject("Login information");
        mail.setBody("Your self-service account has been enabled.<br><br>");
        if (mail.send() == 2) {
          System.err.println(mail.getErrorMsg());
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return executeCommandView(context);
  }


  /**
   *  Sets the organization attribute of the ContactsPortal object
   *
   *@param  context           The new organization value
   *@param  db                The new organization value
   *@param  tmpOrgId          The new organization value
   *@exception  SQLException  Description of the Exception
   */
  private void setOrganization(ActionContext context, Connection db, int tmpOrgId) throws SQLException {
    Organization thisOrganization = null;
    thisOrganization = new Organization(db, tmpOrgId);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }


  /**
   *  Gets the addressToEmail attribute of the ContactsPortal object
   *
   *@param  thisContact  Description of the Parameter
   *@return              The addressToEmail value
   */
  private ContactEmailAddress getAddressToEmail(Contact thisContact) {
    ContactEmailAddressList emailList = thisContact.getEmailAddressList();
    Iterator itr = emailList.iterator();
    ContactEmailAddress emailAddress = null;
    while (itr.hasNext()) {
      emailAddress = (ContactEmailAddress) itr.next();
      if (emailAddress.getPrimaryEmail()) {
        break;
      }
    }

    return emailAddress;
  }


  /**
   *  Inserts portal user information and mails the portal login information to the user
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  thisContact       Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private boolean insertUser(ActionContext context, Connection db, Contact thisContact) throws SQLException {

    boolean recordInserted = false;

    User newUser = new User();
    newUser.setContactId(thisContact.getId());
    newUser.setRoleId(context.getRequest().getParameter("roleId"));
    String username = thisContact.getNameLast().toLowerCase();
    int n = User.getNumberOfSimilarUsernames(db, username);
    newUser.setUsername(username + String.valueOf(n + 1));
    String password = thisContact.getNameLast().toLowerCase() + String.valueOf(StringUtils.rand(1, 9999));
    newUser.setPassword1(password);
    newUser.setPassword2(password);

    java.sql.Timestamp tmpDate = null;
    String tmpExpires = context.getRequest().getParameter("expires");
    tmpDate = DateUtils.parseTimestampString(tmpExpires, "M/d/yy");
    if (tmpDate == null) {
      tmpDate = DateUtils.parseTimestampString(tmpExpires, "M-d-yy");
    }

    newUser.setExpires(tmpDate);
    newUser.setEnteredBy(getUserId(context));
    newUser.setModifiedBy(getUserId(context));
    newUser.setTimeZone((String) context.getServletContext().getAttribute("SYSTEM.TIMEZONE"));
    recordInserted = newUser.insert(db);

    //subsequently use this email address to email the user
    //of the portal access information
    int emailAddressId = Integer.parseInt(context.getRequest().getParameter("emailAddressId"));
    ContactEmailAddress emailAddress = null;
    if (emailAddressId != -1){
       emailAddress = new ContactEmailAddress(db, emailAddressId);
    }else{
      emailAddress = getAddressToEmail(thisContact);
    }
    if (recordInserted) {
      addRecentItem(context, newUser);
      context.getRequest().setAttribute("UserRecord", newUser);
      updateSystemHierarchyCheck(db, context);
      //send email
      SMTPMessage mail = new SMTPMessage();
      mail.setHost(ApplicationPrefs.getPref(context.getServletContext(), "MAILSERVER"));
      mail.setFrom(ApplicationPrefs.getPref(context.getServletContext(), "EMAILADDRESS"));
      mail.setType("text/html");
      mail.setTo(emailAddress.getEmail());
      mail.setSubject("Login information");
      mail.setBody(
          "A self-service account has been created for you.<br />" +
          "<br />" +
          "Your account username is: " + newUser.getUsername() + "<br />" +
          "Your password is: " + password + "<br />");
      if (mail.send() == 2) {
        System.err.println(mail.getErrorMsg());
      }
    } else {
      processErrors(context, newUser.getErrors());
      return false;
    }
    return true;
  }


  /**
   *  Updates portal user information and mails the portal login information to the user
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  thisContact       Description of the Parameter
   *@param  thisUser          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private boolean updateUser(ActionContext context, Connection db, Contact thisContact, User thisUser) throws SQLException {

    boolean roleChanged = false;
    boolean expirationDateChanged = false;
    boolean newPassword = false;
    User newUser = new User();

    //has the portal role been changed?
    int newRoleId = Integer.parseInt((String) context.getRequest().getParameter("roleId"));
    if (thisUser.getRoleId() != newRoleId) {
      roleChanged = true;
      newUser.setRoleId(newRoleId);
      System.out.println("New role");
    } else {
      newUser.setRoleId(thisUser.getRoleId());
    }

    //has the expiration date been changed?
    java.sql.Timestamp tmpDate = null;
    String tmpExpires = context.getRequest().getParameter("expires");
    tmpDate = DateUtils.parseTimestampString(tmpExpires, "M/d/yy");
    if (tmpDate == null) {
      tmpDate = DateUtils.parseTimestampString(tmpExpires, "M-d-yy");
    }
    if ((thisUser.getExpires() == null) && (tmpDate != null)) {
      expirationDateChanged = true;
      newUser.setExpires(tmpDate);
    } else if ((thisUser.getExpires() != null) && (tmpDate == null)) {
      expirationDateChanged = true;
      newUser.setExpires(tmpDate);
    } else if ((thisUser.getExpires() != null) && (tmpDate != null)) {
      if (!thisUser.getExpires().equals(tmpDate)) {
        expirationDateChanged = true;
        newUser.setExpires(tmpDate);
      } else {
        newUser.setExpires(thisUser.getExpires());
      }
    }

    //has password been generated?
    String password = null;
    if ("on".equals(context.getRequest().getParameter("autoGenerate"))) {
      newPassword = true;
      password = thisContact.getNameLast().toLowerCase() + String.valueOf(StringUtils.rand(1, 9999));
      newUser.setPassword1(password);
      newUser.setPassword2(password);
    }

    if ((roleChanged = false) && (expirationDateChanged = false) && (newPassword = false)) {
      return true;
    }

    newUser.setModifiedBy(getUserId(context));
    newUser.setModified(context.getRequest().getParameter("modified"));
    newUser.setUsername(thisUser.getUsername());

    int tmpUserId = 0;
    tmpUserId = newUser.updatePortalUser(db, context);
    newUser = new User(db, tmpUserId);

    //subsequently use this email address to email the user
    //of the portal access information
    int emailAddressId = Integer.parseInt(context.getRequest().getParameter("emailAddressId"));
    ContactEmailAddress emailAddress = null;
    if (emailAddressId != -1){
       emailAddress = new ContactEmailAddress(db, emailAddressId);
    }else{
      emailAddress = getAddressToEmail(thisContact);
    }
    if (tmpUserId > 0) {
      context.getRequest().setAttribute("UserRecord", newUser);
      updateSystemHierarchyCheck(db, context);

      //send email
      SMTPMessage mail = new SMTPMessage();
      mail.setHost(ApplicationPrefs.getPref(context.getServletContext(), "MAILSERVER"));
      mail.setFrom(ApplicationPrefs.getPref(context.getServletContext(), "EMAILADDRESS"));
      mail.setType("text/html");
      mail.setTo(emailAddress.getEmail());
      mail.setSubject("Login information");
      String mailBody = "";
      mailBody = 
          "Your self-service login information has changed.<br />" +
          "<br />";
      if (roleChanged) {
        mailBody = mailBody + "Your account has been updated so that you now have access to login.<br /><br />";
      }
      if (expirationDateChanged) {
        if ("".equals(tmpExpires.trim())) {
          mailBody = mailBody + "The expiration date on your account has been removed.<br /><br />";
        } else {
          mailBody = mailBody + "Your account expires on " + tmpExpires + ".<br /><br />";
        }
      }
      if (newPassword) {
        mailBody = mailBody + "Your new password is: " + password + "<br /><br />";
      }
      mail.setBody(mailBody);
      if (mail.send() == 2) {
        System.err.println(mail.getErrorMsg());
      }
    } else {
      return false;
    }
    return true;
  }
}

