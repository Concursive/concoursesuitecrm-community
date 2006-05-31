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
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.components.EmailDigestUtil;
import org.aspcfs.modules.components.SendUserNotification;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

 /*
  Using a supplied user name, group, department, role, build the list of users
  and send notifications based on the process parameters.
 */
public class SendEmailNotification extends ObjectHookComponent implements ComponentInterface {

  public final static String HOST = "notification.host";
  public final static String MODULE = "notification.module";
  public final static String ITEM_ID = "notification.itemId";
  public final static String ITEM_MODIFIED = "notification.itemModified";
  public final static String USER_TO_NOTIFY = "notification.userToNotify";
  public final static String SUBJECT = "notification.subject";
  public final static String FROM = "notification.from";
  public final static String REPLY_TO = "notification.replyTo";
  public final static String BODY = "notification.body";
  public final static String SKIP_USERS = "notification.skipUsers";
  //Comma-separated email addresses
  public final static String EMAIL_TO = "notification.to";
  //Comma-separated user ids
  public final static String USER_TO = "notification.users.to";
  //Comma-separated contact ids
  public final static String CONTACT_TO = "notification.contacts.to";
  //Comma-separated department ids
  public final static String DEPARTMENT_TO = "notification.departments.to";
  //Comma-separated group ids
  public final static String GROUP_TO = "notification.userGroupToNotify";
  //Comma-separated role ids
  public final static String ROLE_TO = "notification.roleTo";

  public String getDescription() {
    return "Sends notifications to specified users with the specified parameters";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Connection db = null;
    HashMap mailList = new HashMap();
    try {
      db = this.getConnection(context);
      //Get the message and the subject
      String thisMessage ="";// context.getParameter(SendEmailNotification.BODY);
      String thisSubject = context.getParameter(SendEmailNotification.SUBJECT);
      //emailTo gets all the messages
      EmailDigestUtil.appendEmailAddresses(
          mailList, context.getParameter(SendEmailNotification.EMAIL_TO), thisMessage, thisSubject);
      //Users: Lookup each user id specified
      String usersTo = context.getParameter(SendEmailNotification.USER_TO);
      if (usersTo == null || "".equals(usersTo.trim())) {
        usersTo = context.getParameter(SendEmailNotification.USER_TO_NOTIFY);
      }
      EmailDigestUtil.appendEmailUsers(db, mailList, usersTo, thisMessage, thisSubject);
      //Contacts: Lookup each contact id specified
      EmailDigestUtil.appendEmailContacts(db, mailList, context.getParameter(SendEmailNotification.CONTACT_TO), thisMessage, thisSubject);
      //Departments: Get list of enabled users in the specified department, lookup each user id
      EmailDigestUtil.appendEmailDepartmentUsers(db, mailList, context.getParameter(SendEmailNotification.DEPARTMENT_TO), thisMessage, thisSubject);
      //UserGroups : Get the list of enabled users in the user group, lookup each user id
      EmailDigestUtil.appendEmailGroupUsers(db, mailList, context.getParameter(SendEmailNotification.GROUP_TO), thisMessage, thisSubject);
      //Role : Get the list of enabled users in the role, lookup each user id
      EmailDigestUtil.appendEmailRoleUsers(db, mailList, context.getParameter(SendEmailNotification.ROLE_TO), thisMessage, thisSubject);
      //Set the skipped users
      EmailDigestUtil.skipEmailForUsers(db, mailList, context.getParameter(SendEmailNotification.SKIP_USERS));
      //Send the compiled messages
      EmailDigestUtil.sendMail(context, mailList);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}

