package org.aspcfs.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;
import java.util.Hashtable;
import com.darkhorseventures.database.*;
import org.aspcfs.apps.workFlowManager.*;
import org.jcrontab.Crontab;

/**
 *  Responsible for initialization and cleanup when the web-app is
 *  loaded/reloaded
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ContextListener.java,v 1.4 2002/12/23 19:59:31 mrajkowski Exp
 *      $
 */
public class ContextListener implements ServletContextListener {
  public static final String fs = System.getProperty("file.separator");
  
  /**
   *  Constructor for the ContextListener object
   */
  public ContextListener() { }


  /**
   *  Code initialization for global objects like ConnectionPools
   *
   *@param  event  Description of the Parameter
   */
  public void contextInitialized(ServletContextEvent event) {
    ServletContext context = event.getServletContext();
    System.out.println("ContextListener-> Initializing");

    try {
      ConnectionPool cp = new ConnectionPool();
      cp.setDebug(true);
      cp.setTestConnections(true);
      context.setAttribute("ConnectionPool", cp);
    } catch (SQLException e) {
      System.err.println(e.toString());
    }

    //All virtual hosts will have an entry in SystemStatus
    context.setAttribute("SystemStatus", new Hashtable());

    try {
      WorkflowManager wfManager = new WorkflowManager();
      context.setAttribute("WorkflowManager", wfManager);
    } catch (Exception e) {
      System.err.println(e.toString());
    }
    
    System.out.println("ContextListener-> Initialized");
  }


  /**
   *  All objects that should not be persistent can be removed from the context
   *  before the next reload
   *
   *@param  event  Description of the Parameter
   */
  public void contextDestroyed(ServletContextEvent event) {
    ServletContext context = event.getServletContext();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ContextListener-> Shutting down");
    }

    //Stop the cron first
    Crontab crontab = (Crontab)context.getAttribute("Crontab");
    if (crontab != null) {
      crontab.uninit(100);
      context.removeAttribute("Crontab");
      System.out.println("ContextListener-> CRON stopped");
    }
    
    WorkflowManager wfManager = (WorkflowManager) context.getAttribute("WorkflowManager");
    if (wfManager != null) {
      context.removeAttribute("WorkflowManager");
    }

    Hashtable systemStatusList = (Hashtable) context.getAttribute("SystemStatus");
    if (systemStatusList != null) {
      systemStatusList.clear();
    }
    context.removeAttribute("SystemStatus");
    context.removeAttribute("DynamicFormList");
    context.removeAttribute("DynamicFormConfig");
    context.removeAttribute("ContainerMenu");
    context.removeAttribute("ContainerMenuConfig");

    ConnectionPool cp = (ConnectionPool) context.getAttribute("ConnectionPool");
    if (cp != null) {
      cp.closeAllConnections();
      cp.destroy();
      context.removeAttribute("ConnectionPool");
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("DestroyHook-> Shutdown complete");
    }
  }

}

