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
   *  Closes and removes the attributes that will need to be reloaded when
   *  the framework starts back up, working backwards from the InitHook.
   *
   *@return    Description of the Returned Value
   */
  public String executeControllerDestroy() {
    if (config != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DestroyHook-> Shutting down");
      }
      //Remove the workflow manager
      WorkflowManager wfManager = 
        (WorkflowManager)config.getServletContext().getAttribute("WorkflowManager");
      if (wfManager != null) {
        config.getServletContext().removeAttribute("WorkflowManager");
      }
      
      config.getServletContext().removeAttribute("SystemStatus");
      config.getServletContext().removeAttribute("DynamicFormList");
      config.getServletContext().removeAttribute("DynamicFormConfig");
      config.getServletContext().removeAttribute("ContainerMenuConfig");
      
      //Shutdown the ConnectionPool
      ConnectionPool cp =
        (ConnectionPool)config.getServletContext().getAttribute("ConnectionPool");
      if (cp != null) {
        cp.closeAllConnections();
        cp.destroy();
        config.getServletContext().removeAttribute("ConnectionPool");
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DestroyHook-> Shutdown complete");
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DestroyHook-> Could not execute");
      }
    }
    return null;
  }

}

