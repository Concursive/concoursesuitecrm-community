package org.aspcfs.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.servlets.ControllerHook;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  Since the framework requires a UserBean, this hook can be used when an
 *  application has not yet had a user login and gain credentials.
 *  Primarily used for public web applications.
 *
 *@author     matt rajkowski
 *@created    March 11, 2004
 *@version    $Id$
 */
public class RequestHook implements ControllerHook {

  /**
   *  Description of the Method
   *
   *@param  servlet  Description of the Parameter
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String securityCheck(Servlet servlet, HttpServletRequest request) {
    UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
    if (thisUser == null) {
      thisUser = new UserBean();
      request.getSession().setAttribute("User", thisUser);
    }
    return null;
  }
}

