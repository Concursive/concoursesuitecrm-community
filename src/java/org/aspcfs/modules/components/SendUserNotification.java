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

import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.modules.base.Notification;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id: SendUserNotification.java,v 1.9 2004/05/13 20:37:23
 *      mrajkowski Exp $
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
  //Comma-separated email addresses
  public final static String EMAIL_TO = "notification.to";
  //Comma-separated user ids
  public final static String USER_TO = "notification.users.to";
  //Comma-separated contact ids
  public final static String CONTACT_TO = "notification.contacts.to";
  //Comma-separated department ids
  public final static String DEPARTMENT_TO = "notification.departments.to";


  /**
   *  Gets the description attribute of the SendUserNotification object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Send an email notification to a user";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      Notification thisNotification = new Notification();
      thisNotification.setUserToNotify(context.getParameterAsInt(SendUserNotification.USER_TO_NOTIFY));
      thisNotification.setModule(context.getParameter(SendUserNotification.MODULE));
      thisNotification.setItemId(context.getParameterAsInt(SendUserNotification.ITEM_ID));
      //thisNotification.setItemModified(context.getParameter(SendUserNotification.ITEM_MODIFIED));
      thisNotification.setItemModified(null);
      thisNotification.setSubject(StringUtils.toHtml(context.getParameter(SendUserNotification.SUBJECT)));
      String from = StringUtils.toHtmlValue(context.getParameter(SendUserNotification.FROM));
      if (from != null && !"".equals(from)) {
        thisNotification.setFrom(from);
      } else {
        thisNotification.setFrom(context.getParameter("EMAILADDRESS"));
      }
      thisNotification.setMessageToSend(StringUtils.toHtml(context.getParameter(SendUserNotification.BODY)));
      thisNotification.setType(Notification.EMAIL);
      String host = context.getParameter(HOST);
      if (host != null && !"".equals(host)) {
        thisNotification.setHost(host);
      } else {
        thisNotification.setHost(context.getParameter("MAILSERVER"));
      }
      thisNotification.notifyUser(db);
      result = true;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}

