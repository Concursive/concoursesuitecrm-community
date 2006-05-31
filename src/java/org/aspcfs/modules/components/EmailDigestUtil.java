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
package org.aspcfs.modules.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Notification;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 *  Description of the Class
 *
 * @author     matt rajkowski
 * @created    September 16, 2004
 * @version    $Id: EmailDigestUtil.java,v 1.6.12.1 2005/11/07 15:11:32
 *      mrajkowski Exp $
 */
public class EmailDigestUtil {
  /**
   *  This method separates out one or more specified email addresses in which a
   *  message will be associated with and eventually sent to.
   *
   * @param  mailList  Description of the Parameter
   * @param  emails    Description of the Parameter
   * @param  message   Description of the Parameter
   * @param  prefix    Description of the Parameter
   */
  public static void appendEmailAddresses(HashMap mailList, String emails, String message, String prefix) {
    if (emails != null) {
      if (emails.indexOf(",") > -1) {
        //multiple
        StringTokenizer st = new StringTokenizer(emails, ",");
        while (st.hasMoreTokens()) {
          String thisEmail = st.nextToken();
          addMessage(mailList, thisEmail.trim(), message, prefix);
        }
      } else {
        //single
        addMessage(mailList, emails, message, prefix);
      }
    }
  }


  /**
   *  This method separates out one or more specified user ids, looks up the
   *  contact record, gets the email address in which a message will be
   *  associated with and eventually sent to.
   *
   * @param  db             Description of the Parameter
   * @param  mailList       Description of the Parameter
   * @param  userIds        Description of the Parameter
   * @param  message        Description of the Parameter
   * @param  prefix         Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void appendEmailUsers(Connection db, HashMap mailList, String userIds, String message, String prefix) throws SQLException {
    if (userIds != null) {
      if (userIds.indexOf(",") > -1) {
        //multiple
        StringTokenizer st = new StringTokenizer(userIds, ",");
        while (st.hasMoreTokens()) {
          String thisId = st.nextToken();
          int id = -1;
          try {
            id = Integer.parseInt(thisId);
          } catch (Exception e) {
          }
          if (id > -1) {
            User thisUser = new User(db, id);
            thisUser.setBuildContact(true);
            thisUser.setBuildContactDetails(true);
            thisUser.buildResources(db);
            addMessage(mailList, thisUser.getContact().getPrimaryEmailAddress(), message, prefix);
          }
        }
      } else {
        //single
        int id = -1;
        try {
          id = Integer.parseInt(userIds);
        } catch (Exception e) {
        }
        if (id > -1) {
          User thisUser = new User(db, id);
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildResources(db);
          addMessage(mailList, thisUser.getContact().getPrimaryEmailAddress(), message, prefix);
        }
      }
    }
  }


  /**
   *  This method separates out one or more specified contact ids, looks up the
   *  contact record, gets the email address in which a message will be
   *  associated with and eventually sent to.
   *
   * @param  db             Description of the Parameter
   * @param  mailList       Description of the Parameter
   * @param  contactIds     Description of the Parameter
   * @param  message        Description of the Parameter
   * @param  prefix         Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void appendEmailContacts(Connection db, HashMap mailList, String contactIds, String message, String prefix) throws SQLException {
    if (contactIds != null) {
      if (contactIds.indexOf(",") > -1) {
        //multiple
        StringTokenizer st = new StringTokenizer(contactIds, ",");
        while (st.hasMoreTokens()) {
          String thisId = st.nextToken();
          int id = -1;
          try {
            id = Integer.parseInt(thisId);
          } catch (Exception e) {
          }
          if (id > -1) {
            Contact thisContact = new Contact(db, id);
            addMessage(mailList, thisContact.getPrimaryEmailAddress(), message, prefix);
          }
        }
      } else {
        //single
        int id = -1;
        try {
          id = Integer.parseInt(contactIds);
        } catch (Exception e) {
        }
        if (id > -1) {
          Contact thisContact = new Contact(db, id);
          addMessage(mailList, thisContact.getPrimaryEmailAddress(), message, prefix);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  mailList          Description of the Parameter
   * @param  departmentId      Description of the Parameter
   * @param  message           Description of the Parameter
   * @param  prefix            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void appendEmailDepartmentUsers(Connection db, HashMap mailList, String departmentId, String message, String prefix) throws SQLException {
    if (departmentId != null && !"".equals(departmentId) && !"-1".equals(departmentId) && !"null".equals(departmentId)) {
      UserList users = new UserList();
      users.setDepartment(departmentId);
      users.setHidden(Constants.FALSE);
      users.setBuildContact(true);
      users.setBuildContactDetails(true);
      users.setRoleType(Constants.ROLETYPE_REGULAR);
      users.buildList(db);
      Iterator iter = (Iterator) users.iterator();
      while (iter.hasNext()) {
        User user = (User) iter.next();
        addMessage(mailList, user.getContact().getPrimaryEmailAddress(), message, prefix);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  mailList          Description of the Parameter
   * @param  roleId            Description of the Parameter
   * @param  message           Description of the Parameter
   * @param  prefix            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void appendEmailRoleUsers(Connection db, HashMap mailList, String roleId, String message, String prefix) throws SQLException {
    if (roleId != null && !"".equals(roleId) && !"-1".equals(roleId) && !"null".equals(roleId)) {
      UserList users = new UserList();
      users.setRoleId(Integer.parseInt(roleId));
      users.setHidden(Constants.FALSE);
      users.setBuildContact(true);
      users.setBuildContactDetails(true);
      users.setRoleType(Constants.ROLETYPE_REGULAR);
      users.buildList(db);
      Iterator iter = (Iterator) users.iterator();
      while (iter.hasNext()) {
        User user = (User) iter.next();
        addMessage(mailList, user.getContact().getPrimaryEmailAddress(), message, prefix);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  mailList          Description of the Parameter
   * @param  groupId           Description of the Parameter
   * @param  message           Description of the Parameter
   * @param  prefix            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void appendEmailGroupUsers(Connection db, HashMap mailList, String groupId, String message, String prefix) throws SQLException {
    if (groupId != null && !"".equals(groupId.trim())) {
      if (groupId.indexOf(",") > -1) {
        //multiple
        StringTokenizer st = new StringTokenizer(groupId, ",");
        while (st.hasMoreTokens()) {
          String thisId = st.nextToken();
          int id = -1;
          try {
            id = Integer.parseInt(thisId);
          } catch (Exception e) {
          }
          if (id > -1) {
            UserList users = new UserList();
            users.setUserGroupId(id);
            users.setHidden(Constants.FALSE);
            users.setBuildContact(true);
            users.setBuildContactDetails(true);
            users.setRoleType(Constants.ROLETYPE_REGULAR);
            users.buildList(db);
            Iterator iter = (Iterator) users.iterator();
            while (iter.hasNext()) {
              User user = (User) iter.next();
              addMessage(mailList, user.getContact().getPrimaryEmailAddress(), message, prefix);
            }
          }
        }
      } else {
        //single
        int id = -1;
        try {
          id = Integer.parseInt(groupId);
        } catch (Exception e) {
        }
        if (id > -1) {
          UserList users = new UserList();
          users.setUserGroupId(id);
          users.setHidden(Constants.FALSE);
          users.setBuildContact(true);
          users.setBuildContactDetails(true);
          users.setRoleType(Constants.ROLETYPE_REGULAR);
          users.buildList(db);
          Iterator iter = (Iterator) users.iterator();
          while (iter.hasNext()) {
            User user = (User) iter.next();
            addMessage(mailList, user.getContact().getPrimaryEmailAddress(), message, prefix);
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  mailList          Description of the Parameter
   * @param  contactIds        Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void skipEmailForUsers(Connection db, HashMap mailList, String userIds) throws SQLException {
    if (userIds != null) {
      if (userIds.indexOf(",") > -1) {
        //multiple
        StringTokenizer st = new StringTokenizer(userIds, ",");
        while (st.hasMoreTokens()) {
          String thisId = st.nextToken();
          int id = -1;
          try {
            id = Integer.parseInt(thisId);
          } catch (Exception e) {
          }
          if (id > -1) {
            User thisUser = new User();
            thisUser.setBuildContact(true);
            thisUser.setBuildContactDetails(true);
            thisUser.buildRecord(db, id);
            removeMessage(mailList, thisUser.getContact().getPrimaryEmailAddress());
          }
        }
      } else {
        //single
        int id = -1;
        try {
          id = Integer.parseInt(userIds);
        } catch (Exception e) {
        }
        if (id > -1) {
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildRecord(db, id);
          removeMessage(mailList, thisUser.getContact().getPrimaryEmailAddress());
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  mailList      Description of the Parameter
   * @param  emailAddress  Description of the Parameter
   */
  public static void removeMessage(HashMap mailList, String emailAddress) {
    if (emailAddress != null) {
      Map emailToList = (Map) mailList.get(emailAddress);
      if (emailToList != null) {
        mailList.remove(emailAddress);
      }
    }
  }


  /**
   *  Appends a message to the specified email address. Each email address
   *  receives a single email that can have multiple appended messages.
   *
   * @param  mailList      The feature to be added to the Message attribute
   * @param  emailAddress  The feature to be added to the Message attribute
   * @param  thisMessage   The feature to be added to the Message attribute
   * @param  prefix        The feature to be added to the Message attribute
   */
  public static void addMessage(HashMap mailList, String emailAddress, String thisMessage, String prefix) {
    if (emailAddress != null && thisMessage != null) {
      Map emailToList = (Map) mailList.get(emailAddress);
      if (emailToList == null) {
        emailToList = new LinkedHashMap();
        mailList.put(emailAddress, emailToList);
      }
      emailToList.put(prefix, thisMessage);
    }
  }


  public static void setMessage(HashMap mailList, String emailAddress, String thisMessage, String prefix) {
    if (emailAddress != null && thisMessage != null) {
      Map emailToList = (Map) mailList.get(emailAddress);
      if (emailToList == null) {
        emailToList = new LinkedHashMap();
        mailList.put(emailAddress, emailToList);
        emailToList.put(prefix, thisMessage);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context   Description of the Parameter
   * @param  mailList  Description of the Parameter
   */
  public static void sendMail(ComponentContext context, HashMap mailList) {
    //Process the HashMap and send emails
    Iterator toList = mailList.keySet().iterator();
    while (toList.hasNext()) {
      //Create a new notification directly, configure, and execute
      StringBuffer messageDigest = new StringBuffer();
      String emailAddressTo = (String) toList.next();
      Map emailToList = (Map) mailList.get(emailAddressTo);
      Iterator messages = emailToList.values().iterator();
      while (messages.hasNext()) {
        messageDigest.append((String) messages.next());
      }
      Notification thisNotification = new Notification();
      thisNotification.setSubject(context.getParameter(SendUserNotification.SUBJECT));
      String from = context.getParameter(SendUserNotification.FROM);
      if (from != null && !"".equals(from)) {
        thisNotification.setFrom(from);
      } else {
        thisNotification.setFrom(context.getParameter("EMAILADDRESS"));
      }
      thisNotification.setType(Notification.EMAIL);
      thisNotification.setEmailToNotify(emailAddressTo);
      thisNotification.setMessageToSend(
          context.getParameter(SendUserNotification.BODY) + messageDigest.toString());
      String host = context.getParameter(SendUserNotification.HOST);
      if (host != null && !"".equals(host)) {
        thisNotification.setHost(host);
      } else {
        thisNotification.setHost(context.getParameter("MAILSERVER"));
      }
      thisNotification.notifyAddress();
    }
  }
}

