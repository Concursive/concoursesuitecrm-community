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
package org.aspcfs.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Hashtable;
import com.darkhorseventures.database.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.admin.base.User;

/**
 *  Listener for monitoring session changes
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ContextSessionListener.java,v 1.2 2002/11/14 13:32:16
 *      mrajkowski Exp $
 */
public class ContextSessionListener implements HttpSessionAttributeListener, HttpSessionListener {

  /**
   *  This method is invoked when a session is created
   *
   *@param  event  Description of the Parameter
   */
  public void sessionCreated(HttpSessionEvent event) {
    //System.out.println("HttpSession object has been created");
  }


  /**
   *  This method is invoked when a session is destroyed
   *
   *@param  event  Description of the Parameter
   */
  public void sessionDestroyed(HttpSessionEvent event) {

  }


  /**
   *  This method is invoked when an attribute is added to the ServletContext
   *  object
   *
   *@param  se  Description of the Parameter
   */
  public void attributeAdded(HttpSessionBindingEvent se) {
    //System.out.println("An attribute was added to the ServletContext object");
  }


  /**
   *  This method is invoked when an attribute is removed from the
   *  ServletContext object
   *
   *@param  se  Description of the Parameter
   */
  public void attributeRemoved(HttpSessionBindingEvent se) {
    ServletContext context = se.getSession().getServletContext();
    try {
      if (se.getName().equals("User")) {
        UserBean thisUser = (UserBean) se.getValue();
        if (thisUser != null) {
          int userId = thisUser.getActualUserId();
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ContextSessionListener-> Session for user " + userId + " ended ");
          }
          SessionManager thisManager = ((SystemStatus) ((Hashtable) context.getAttribute("SystemStatus")).get(thisUser.getConnectionElement().getUrl())).getSessionManager();
          UserSession thisSession = thisManager.getUserSession(userId);
          if (thisSession.getId().equals(se.getSession().getId())) {
            thisManager.removeUser(userId);
            if (System.getProperty("DEBUG") != null) {
              System.out.println("ContextSessionListener-> User removed from valid user list");
            }
          }
        }
      }
    } catch (Exception e) {
      //System.out.println("ContextSessionListener-> Error: " + e.toString());
    }
  }


  /**
   *  This method is invoked when an attribute is replaced in the ServletContext
   *  object
   *
   *@param  se  Description of the Parameter
   */
  public void attributeReplaced(HttpSessionBindingEvent se) {
    //System.out.println("An attribute was replaced in the ServletContext object");
  }

}

