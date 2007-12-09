/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.controller.objectHookManager;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.apps.workFlowManager.BusinessProcessList;
import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ScheduledEventList;
import org.aspcfs.apps.workFlowManager.WorkflowManager;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.service.base.PacketContext;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manages hooks within the application and attaches to a WorkflowManager to
 * execute BusinessProcess objects as defined by ObjectHook objects.
 *
 * @author matt rajkowski
 * @version $Id: ObjectHookManager.java,v 1.9 2003/05/08 13:50:18 mrajkowski
 *          Exp $
 * @created November 11, 2002
 */
public class ObjectHookManager {

  private ObjectHookList hookList = null;
  private BusinessProcessList processList = null;
  private ScheduledEventList eventList = null;
  private String fileLibraryPath = null;


  /**
   * Constructor for the ObjectHookManager object
   */
  public ObjectHookManager() {
  }


  /**
   * Sets the fileLibraryPath attribute of the ObjectHookManager object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Gets the hookList attribute of the ObjectHookManager object
   *
   * @return The hookList value
   */
  public ObjectHookList getHookList() {
    return hookList;
  }


  /**
   * Sets the hookList attribute of the ObjectHookManager object
   *
   * @param tmp The new hookList value
   */
  public void setHookList(ObjectHookList tmp) {
    this.hookList = tmp;
  }


  /**
   * Gets the processList attribute of the ObjectHookManager object
   *
   * @return The processList value
   */
  public BusinessProcessList getProcessList() {
    return processList;
  }


  /**
   * Sets the processList attribute of the ObjectHookManager object
   *
   * @param tmp The new processList value
   */
  public void setProcessList(BusinessProcessList tmp) {
    this.processList = tmp;
  }


  /**
   * Gets the eventList attribute of the ObjectHookManager object
   *
   * @return The eventList value
   */
  public ScheduledEventList getEventList() {
    return eventList;
  }


  /**
   * Sets the eventList attribute of the ObjectHookManager object
   *
   * @param tmp The new eventList value
   */
  public void setEventList(ScheduledEventList tmp) {
    this.eventList = tmp;
  }


  /**
   * Description of the Method
   *
   * @param documentElement Description of the Parameter
   */
  public void initializeObjectHookList(Element documentElement, boolean overwrite, boolean isApplicationType) {
    if (overwrite) {
      hookList = new ObjectHookList();
    }
    hookList.parse(documentElement, isApplicationType);
  }


  /**
   * Description of the Method
   */
  public void initializeObjectHookList(boolean overwrite) {
    if (overwrite) {
      hookList = new ObjectHookList();
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void initializeObjectHookList(Connection db, boolean overwrite) throws SQLException {
    if (overwrite) {
      hookList = new ObjectHookList();
    }
    hookList.buildList(db);
  }


  /**
   * Initializes the list of processes that can be executed, from an XML
   * Element
   *
   * @param documentElement Description of the Parameter
   */
  public void initializeBusinessProcessList(Element documentElement, boolean overwrite, boolean isApplicationType) {
    if (overwrite) {
      processList = new BusinessProcessList();
    }
    processList.parse(documentElement, isApplicationType);
  }


  /**
   * Initializes the list of processes that can be executed, from a database
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void initializeBusinessProcessList(Connection db, boolean overwrite) throws SQLException {
    if (overwrite) {
      processList = new BusinessProcessList();
    }
    processList.buildList(db);
  }


  /**
   * Description of the Method
   */
  public void initializeBusinessProcessList(boolean overwrite) {
    if (overwrite) {
      processList = new BusinessProcessList();
    }
  }


  /**
   * Forwards the request to execute a business process to process(...)
   *
   * @param packetContext  Description of the Parameter
   * @param action         Description of the Parameter
   * @param previousObject Description of the Parameter
   * @param object         Description of the Parameter
   */
  public void process(PacketContext packetContext, int action, Object previousObject, Object object) {
    process(
        packetContext.getActionContext(), action, previousObject, object, packetContext.getConnectionPool(), packetContext.getConnectionElement());
  }


  /**
   * Executes a business process based on the objects that are specified. The
   * objectHook must already be loaded into the hookList, with a corresponding
   * action to trigger on: insert, update, or delete. Also, the business
   * process must already be loaded into the business process list.
   *
   * @param actionContext  Description of the Parameter
   * @param action         Description of the Parameter
   * @param previousObject Description of the Parameter
   * @param object         Description of the Parameter
   * @param sqlDriver      Description of the Parameter
   * @param ce             Description of the Parameter
   */
  public void process(ActionContext actionContext, int action, Object previousObject, Object object, ConnectionPool sqlDriver, ConnectionElement ce) {
    try {
      WorkflowManager wfManager = (WorkflowManager) actionContext.getServletContext().getAttribute(
          "WorkflowManager");
      if (wfManager != null && hookList != null && processList != null) {
        if ((object != null && hookList.has(object)) ||
            (previousObject != null && hookList.has(previousObject))) {
          ApplicationPrefs prefs = (ApplicationPrefs) actionContext.getServletContext().getAttribute(
              "applicationPrefs");
          ComponentContext context = new ComponentContext();
          context.setPreviousObject(previousObject);
          context.setThisObject(object);
          context.setParameter("FileLibraryPath", fileLibraryPath);
          context.setAttribute("SERVERURL", RequestUtils.getLink(actionContext,""));
          context.setAttribute("ConnectionPool", sqlDriver);
          context.setAttribute("ConnectionElement", ce);
          context.setAttribute(
              "ClientSSLKeystore", actionContext.getServletContext().getAttribute(
                  "ClientSSLKeystore"));
          context.setAttribute(
              "ClientSSLKeystorePassword", actionContext.getServletContext().getAttribute(
                  "ClientSSLKeystorePassword"));
          context.setAttribute("MAILSERVER", prefs.get("MAILSERVER"));
          context.setAttribute("EMAILADDRESS", prefs.get("EMAILADDRESS"));
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ObjectHookList-> Hook thread start");
          }
          //Thread the business process
          ObjectHook thisHook = new ObjectHook(context);
          thisHook.setActionId(action);
          thisHook.setObjectHookList(hookList);
          thisHook.setBusinessProcessList(processList);
          thisHook.setManager(wfManager);
          thisHook.start();
        }
      } else {
        if (wfManager == null) {
          System.out.println("ObjectHookManager-> WorkflowManager not found");
        }
        if (processList == null) {
          System.out.println("ObjectHookManager-> ProcessList not found");
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   * Executes a specified business process in a new thread. The business
   * process must already be loaded into the business process list.<p>
   * <p/>
   * Commonly used for business processes that are triggered by a cron, or
   * manually triggered.
   *
   * @param servletContext Description of the Parameter
   * @param processName    Description of the Parameter
   * @param sqlDriver      Description of the Parameter
   * @param ce             Description of the Parameter
   */
  public void process(ServletContext servletContext, String processName, ConnectionPool sqlDriver, ConnectionElement ce) {
    WorkflowManager wfManager = (WorkflowManager) servletContext.getAttribute(
        "WorkflowManager");
    if (wfManager != null && processList != null) {
      ComponentContext context = new ComponentContext();
      context.setParameter("FileLibraryPath", fileLibraryPath);
      context.setAttribute("ConnectionPool", sqlDriver);
      context.setAttribute("ConnectionElement", ce);
      context.setAttribute(
          "ClientSSLKeystore", servletContext.getAttribute(
              "ClientSSLKeystore"));
      context.setAttribute(
          "ClientSSLKeystorePassword", servletContext.getAttribute(
              "ClientSSLKeystorePassword"));
      ApplicationPrefs prefs = (ApplicationPrefs) servletContext.getAttribute(
          "applicationPrefs");
      if (prefs != null) {
        context.setAttribute("MAILSERVER", prefs.get("MAILSERVER"));
        context.setAttribute("EMAILADDRESS", prefs.get("EMAILADDRESS"));
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ObjectHookList-> Hook thread start for process: " + processName);
      }
      //Thread the business process
      ObjectHook thisHook = new ObjectHook(context);
      thisHook.setBusinessProcess(processName);
      thisHook.setBusinessProcessList(processList);
      thisHook.setManager(wfManager);
      thisHook.start();
    } else {
      if (System.getProperty("DEBUG") != null) {
        if (wfManager == null) {
          System.out.println("ObjectHookManager-> WorkflowManager not found");
        }
        if (processList == null) {
          System.out.println("ObjectHookManager-> ProcessList not found");
        }
      }
    }
  }
}

