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

import com.darkhorseventures.framework.servlets.ControllerHook;
import org.aspcfs.modules.login.beans.UserBean;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Since the framework requires a UserBean, this hook can be used when an
 * application has not yet had a user login and gain credentials. Primarily
 * used for public web applications.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created March 11, 2004
 */
public class RequestHook implements ControllerHook {

  /**
   * Description of the Method
   *
   * @param servlet Description of the Parameter
   * @param request Description of the Parameter
   * @return Description of the Return Value
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

