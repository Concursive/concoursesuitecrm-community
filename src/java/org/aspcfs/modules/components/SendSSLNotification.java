//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;
import java.sql.*;
import com.darkhorseventures.utils.StringUtils;

public class SendSSLNotification extends ObjectHookComponent implements ComponentInterface {
  public static final String HOST = "notification.host";
  public static final String PORT = "notification.port";
  public static final String BODY = "notification.body";
  
  public String getDescription() {
    return "Post text data to a remote system using SSL.";
  }
  
  public boolean execute(ComponentContext context) {
    boolean result = false;
    try {
      Notification thisNotification = new Notification(Notification.SSL);
      thisNotification.setHost(context.getParameter(SendSSLNotification.HOST));
      thisNotification.setPort(context.getParameterAsInt(SendSSLNotification.PORT));
      thisNotification.setMessageToSend(StringUtils.toHtml(context.getParameter(SendSSLNotification.BODY)));
      thisNotification.setContext(context);
      thisNotification.notifySystem();
      result = true;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return result;
  }
}
