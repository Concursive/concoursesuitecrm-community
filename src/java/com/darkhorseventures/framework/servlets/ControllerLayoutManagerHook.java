package com.darkhorseventures.framework.servlets;

import javax.servlet.http.*;

/**
 *  Description of the Interface
 *
 *@version    $Id$
 */
public interface ControllerLayoutManagerHook {
  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String generateLayout(HttpServletRequest request);
}

