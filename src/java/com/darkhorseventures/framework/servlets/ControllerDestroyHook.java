package com.darkhorseventures.framework.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Interface
 *
 *@author     mrajkowski
 *@created    July 30, 2001
 *@version    $Id: ControllerDestroyHook.java,v 1.1.1.1 2002/01/14 19:49:29
 *      mrajkowski Exp $
 */
public interface ControllerDestroyHook {

  /**
   *  Description of the Method
   *
   *@param  config  Description of Parameter
   *@return         Description of the Returned Value
   *@since
   */
  public String executeControllerDestroyInit(ServletConfig config);


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public String executeControllerDestroy();
}

