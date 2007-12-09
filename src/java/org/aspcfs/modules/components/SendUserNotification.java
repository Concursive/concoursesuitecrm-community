/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Notification;
import org.aspcfs.utils.StringUtils;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: SendUserNotification.java,v 1.9 2004/05/13 20:37:23
 *          mrajkowski Exp $
 * @created January 14, 2003
 */
public class SendUserNotification extends ObjectHookComponent implements ComponentInterface {
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
  public final static String GROUP_TO = "notification.userGroupToNotify";
  public final static String EMAIL_TO_USER_AND_GROUP = "notification.emailBothUserAndGroup";
  public final static String ROLE_TO = "notification.roleTo";


  /**
   * Gets the description attribute of the SendUserNotification object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Send an email notification to a user";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    boolean both = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      String groupId = context.getParameter(SendUserNotification.GROUP_TO);
      String bothString = context.getParameter(SendUserNotification.EMAIL_TO_USER_AND_GROUP);
      if (bothString != null && "true".equals(bothString.trim())) {
        both = true;
      }
      String skipUser = context.getParameter(SendUserNotification.SKIP_USERS);
      Notification thisNotification = new Notification();
      thisNotification.setUserToNotify(
          context.getParameterAsInt(SendUserNotification.USER_TO_NOTIFY));
      thisNotification.setModule(
          context.getParameter(SendUserNotification.MODULE));
      thisNotification.setItemId(
          context.getParameterAsInt(SendUserNotification.ITEM_ID));
      //thisNotification.setItemModified(context.getParameter(SendUserNotification.ITEM_MODIFIED));
      thisNotification.setItemModified(null);
      thisNotification.setSubject(
          StringUtils.toHtml(
              context.getParameter(SendUserNotification.SUBJECT)));
      String from = StringUtils.toHtmlValue(
          context.getParameter(SendUserNotification.FROM));
      if (from != null && !"".equals(from)) {
        thisNotification.setFrom(from);
      } else {
        thisNotification.setFrom(context.getParameter("EMAILADDRESS"));
      }
      thisNotification.setMessageToSend(context.getParameter(SendUserNotification.BODY));
      thisNotification.setType(Notification.EMAIL);
      String host = context.getParameter(HOST);
      if (host != null && !"".equals(host)) {
        thisNotification.setHost(host);
      } else {
        thisNotification.setHost(context.getParameter("MAILSERVER"));
      }
      boolean userInGroup = false;
      if (groupId != null && !"".equals(groupId) && !"-1".equals(groupId)) {
        UserList users = new UserList();
        users.setUserGroupId(groupId);
        users.setEnabled(Constants.TRUE);
        users.setExpired(Constants.FALSE);
        users.setBuildContact(true);
        users.setBuildContactDetails(true);
        users.buildList(db);
        Iterator iter = (Iterator) users.iterator();
        while (iter.hasNext()) {
          User user = (User) iter.next();
          if (user.getId() == context.getParameterAsInt(SendUserNotification.USER_TO_NOTIFY)) {
            userInGroup = true;
          } else {
            if (user.getEnabled()) {
              thisNotification.setUserToNotify(user.getId());
              thisNotification.notifyUser(db);
            }
          }
        }
      }
      // check that the groupId exists and the user is not in the group
      // then check that the notification is for both user and group
      // then check that the skipUser is false
      if (((!userInGroup && groupId != null && !"".equals(groupId) && !"-1".equals(groupId)) || both)
          && !(skipUser != null && "true".equals(skipUser))) {
        thisNotification.notifyUser(db);
      }
      result = true;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}

