package com.darkhorseventures.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.servlets.ControllerDestroyHook;
import com.darkhorseventures.utils.*;
import java.sql.*;

/**
 *  Last chance to do something before the servlet is shut down
 *
 *@author     mrajkowski
 *@created    July 30, 2001
 *@version    $Id$
 */
public class DestroyHook implements ControllerDestroyHook {

  ServletConfig config = null;


  /**
   *  When the ServletController is initialized, this code creates a reference
   *  to the servlet config.
   *
   *@param  config  The new Config value
   *@since          1.0
   */
  public String executeControllerDestroyInit(ServletConfig config) {
    this.config = config;
    return null;
  }


  /**
   *  Closes the connection pool when the servlet controller is shut down
   *
   *@return    Description of the Returned Value
   *@since     1.0
   */
  public String executeControllerDestroy() {
    if (config != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DestroyHook-> Shutting down");
      }
      ConnectionPool cp =
          (ConnectionPool)config.getServletContext().getAttribute("ConnectionPool");
      cp.closeAllConnections();
      cp.destroy();
      cp = null;
    }
    return null;
  }

}

