package com.darkhorseventures.framework.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Interface
 *
 *@version    $Id$
 */
public interface ControllerHook {
  /**
   *  Description of the Method
   *
   *@param  servlet  Description of the Parameter
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String securityCheck(Servlet servlet, HttpServletRequest request);
}

