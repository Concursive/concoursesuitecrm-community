package com.darkhorseventures.framework.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Interface
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    $Id: ControllerMainMenuHook.java,v 1.1.1.1 2002/01/14 19:49:29
 *      mrajkowski Exp $
 */
public interface ControllerMainMenuHook {
  /**
   *  Initializes the MainMenuHook at servlet startup
   *
   *@param  config  Description of Parameter
   *@return         Description of the Returned Value
   *@since          1.3
   */
  public String executeControllerMainMenu(ServletConfig config);


  /**
   *  Executes the MainMenuHook during a request
   *
   *@param  request     Description of Parameter
   *@param  actionPath  Description of Parameter
   *@since              1.0
   */
  public void generateMenu(HttpServletRequest request, String actionPath);
}

