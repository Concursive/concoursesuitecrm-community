//Copyright 2002 Dark Horse Ventures

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
 *@version    $Id$
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
      thisNotification.setFrom(StringUtils.toHtml(context.getParameter(SendUserNotification.FROM)));
      thisNotification.setMessageToSend(StringUtils.toHtml(context.getParameter(SendUserNotification.BODY)));
      thisNotification.setType(Notification.EMAIL);
      String host = context.getParameter(HOST);
      if (host != null && !"".equals(host)) {
        thisNotification.setHost(host);
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

