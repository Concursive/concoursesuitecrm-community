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
        int userId = thisUser.getActualUserId();
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ContextSessionListener --> Session for user " + userId + " ended ");
        }
        SessionManager thisManager = ((SystemStatus) ((Hashtable) context.getAttribute("SystemStatus")).get(thisUser.getConnectionElement().getUrl())).getSessionManager();
        UserSession thisSession = thisManager.getUserSession(userId);
        if (thisSession.getId().equals(se.getSession().getId())) {
          thisManager.removeUser(userId);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ContextSessionListener --> User removed from Valid User List");
          }
        }
      }
    } catch (Exception e) {
      System.out.println("ContextSessionListener -- > Error " + e.toString());
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

