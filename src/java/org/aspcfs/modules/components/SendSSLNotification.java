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
import java.sql.*;
import org.aspcfs.modules.base.Notification;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id: SendSSLNotification.java,v 1.4 2003/04/14 02:42:22 mrajkowski
 *      Exp $
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

