package com.darkhorseventures.framework.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Interface
 *
 *@version    $Id$
 */
public interface ControllerInitHook {
  /**
   *  Description of the Method
   *
   *@param  config  Description of the Parameter
   *@return         Description of the Return Value
   */
  public String executeControllerInit(ServletConfig config);
}

