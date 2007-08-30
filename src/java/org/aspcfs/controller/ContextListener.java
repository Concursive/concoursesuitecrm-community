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

import com.darkhorseventures.database.ConnectionPool;

import org.apache.log4j.Logger;
import org.apache.pluto.PortletContainer;
import org.apache.pluto.PortletContainerFactory;
import org.aspcfs.apps.workFlowManager.WorkflowManager;
import org.aspcfs.modules.website.framework.IceletContainerServices;
import org.aspcfs.modules.website.framework.IceletManager;
import org.jcrontab.Crontab;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Responsible for initialization and cleanup when the web-app is
 * loaded/reloaded
 *
 * @author matt rajkowski
 * @version $Id: ContextListener.java,v 1.4 2002/12/23 19:59:31 mrajkowski Exp
 *          $
 * @created November 11, 2002
 */
public class ContextListener implements ServletContextListener {
  
  private final static Logger log = Logger.getLogger(org.aspcfs.controller.ContextListener.class);

  public final static String fs = System.getProperty("file.separator");


  /**
   * Constructor for the ContextListener object
   */
  public ContextListener() {
  }


  /**
   * Code initialization for global objects like ConnectionPools, the
   * initParameters from the servlet context are NOT available for use here.
   *
   * @param event Description of the Parameter
   */
  public void contextInitialized(ServletContextEvent event) {
    ServletContext context = event.getServletContext();
    log.info("Initializing");
    //Start the ConnectionPool with default params, these can be adjusted
    //in the InitHook
    try {
      ConnectionPool cp = new ConnectionPool();
      cp.setTestConnections(false);
      cp.setAllowShrinking(true);
      cp.setMaxConnections(10);
      cp.setMaxIdleTime(60000);
      cp.setMaxDeadTime(300000);
      context.setAttribute("ConnectionPool", cp);
    } catch (SQLException e) {
      log.error(e.toString());
    }
    //All virtual hosts will have an entry in SystemStatus, so this needs
    //to be reset when the context is reset
    Hashtable systemStatus = new Hashtable();
    context.setAttribute("SystemStatus", systemStatus);
    //The work horse for all objects that must go through a designed process,
    //reload here as well
    try {
      WorkflowManager wfManager = new WorkflowManager();
      context.setAttribute("WorkflowManager", wfManager);
    } catch (Exception e) {
      log.error(e.toString());
    }
    // Setup scheduler
    try {
      SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
      Scheduler scheduler = schedulerFactory.getScheduler();
      context.setAttribute("Scheduler", scheduler);
      scheduler.getContext().put("SystemStatus", systemStatus);
      log.info("Scheduler added");
    } catch (Exception e) {
      log.error(e.toString());
    }
    // Portlet container
    try {
      IceletContainerServices containerServices = new IceletContainerServices();
      PortletContainerFactory portletFactory = PortletContainerFactory.getInstance();
      PortletContainer portletContainer =
          portletFactory.createContainer("PortletContainer", containerServices, containerServices);
      portletContainer.init(context);
      context.setAttribute("PortletContainer", portletContainer);
    } catch (Exception e) {
      log.error(e.toString());
    }
    log.info("Initialized");
  }


  /**
   * All objects that should not be persistent can be removed from the context
   * before the next reload
   *
   * @param event Description of the Parameter
   */
  public void contextDestroyed(ServletContextEvent event) {
    ServletContext context = event.getServletContext();
    log.debug("Shutting down");
    //Stop the cron first so that nothing new gets executed
    Crontab crontab = (Crontab) context.getAttribute("Crontab");
    if (crontab != null) {
      crontab.uninit(200);
      context.removeAttribute("Crontab");
      log.info("CRON stopped");
    }
    // Remove scheduler
    try {
      Scheduler scheduler = (Scheduler) context.getAttribute("Scheduler");
      if (scheduler != null) {
        scheduler.shutdown(true);
        // TODO: check to see if db connections need to be closed here if
        // scheduler is using its own ConnectionPool
        context.removeAttribute("Scheduler");
        log.info("Scheduler stopped");
      }
    } catch (Exception e) {
      log.error(e.toString());
    }
    //Stop the work flow manager
    WorkflowManager wfManager = (WorkflowManager) context.getAttribute(
        "WorkflowManager");
    if (wfManager != null) {
      context.removeAttribute("WorkflowManager");
    }
    // Stop the portlet container
    PortletContainer container = (PortletContainer) context.getAttribute("PortletContainer");
    if (container != null) {
      try {
        container.destroy();
      } catch (Exception e) {
        log.error(e.toString());
      }
      context.removeAttribute("PortletContainer");
    }
    IceletManager.destroy(context);
    //Remove the SystemStatus -> this will force a rebuild of any systems that
    //may have been cached
    Hashtable systemStatusList = (Hashtable) context.getAttribute(
        "SystemStatus");
    if (systemStatusList != null) {
      Iterator i = systemStatusList.values().iterator();
      while (i.hasNext()) {
        SystemStatus thisSystem = (SystemStatus) i.next();
        thisSystem.stopServers();
      }
      systemStatusList.clear();
    }
    context.removeAttribute("SystemStatus");
    //Remove the dynamic items, forcing them to rebuild
    context.removeAttribute("DynamicFormList");
    context.removeAttribute("DynamicFormConfig");
    context.removeAttribute("ContainerMenuConfig");

    //Unload the connection pool
    ConnectionPool cp = (ConnectionPool) context.getAttribute(
        "ConnectionPool");
    if (cp != null) {
      cp.closeAllConnections();
      cp.destroy();
      context.removeAttribute("ConnectionPool");
    }
    log.debug("Shutdown complete");
  }
}

