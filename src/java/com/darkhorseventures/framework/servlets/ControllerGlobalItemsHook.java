package org.theseus.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Interface
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 */
public interface ControllerGlobalItemsHook {
  /**
   *  Description of the Method
   *
   *@param  request  Description of Parameter
   *@return          Description of the Returned Value
   *@since 1.0
   */
  public String generateItems(Servlet servlet, HttpServletRequest request);
}

