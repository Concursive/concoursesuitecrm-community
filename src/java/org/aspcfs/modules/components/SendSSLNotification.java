//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.components;

import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.utils.StringUtils;
import java.sql.*;
import org.aspcfs.modules.base.Notification;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class SendSSLNotification extends ObjectHookComponent implements ComponentInterface {
  public final static String HOST = "notification.host";
  public final static String PORT = "notification.port";
  public final static String BODY = "notification.body";


  /**
   *  Gets the description attribute of the SendSSLNotification object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Post text data to a remote system using SSL";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
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

