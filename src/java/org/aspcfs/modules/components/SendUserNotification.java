//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;
import java.sql.*;

public class SendUserNotification extends ObjectHookComponent implements ComponentInterface {
  public static final String HOST = "notification.host";
  public static final String MODULE = "notification.module";
  public static final String ITEM_ID = "notification.itemId";
  public static final String ITEM_MODIFIED = "notification.itemModified";
  public static final String USER_TO_NOTIFY = "notification.userToNotify";
  public static final String SUBJECT = "notification.subject";
  public static final String FROM = "notification.from";
  public static final String REPLY_TO = "notification.replyTo";
  public static final String BODY = "notification.body";
  
  public String getDescription() {
    return "Send an email to a user, using the CFS Notification System.";
  }
  
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
      thisNotification.setSubject(context.getParameter(SendUserNotification.SUBJECT));
      thisNotification.setFrom(context.getParameter(SendUserNotification.FROM));
      thisNotification.setMessageToSend(context.getParameter(SendUserNotification.BODY));
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
