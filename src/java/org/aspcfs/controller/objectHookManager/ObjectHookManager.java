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
package org.aspcfs.controller.objectHookManager;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.apps.workFlowManager.*;
import java.io.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import org.aspcfs.modules.service.base.PacketContext;
import javax.servlet.ServletContext;
import org.aspcfs.controller.ApplicationPrefs;

/**
 *  Manages hooks within the application and attaches to a WorkflowManager to
 *  execute BusinessProcess objects as defined by ObjectHook objects.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ObjectHookManager.java,v 1.9 2003/05/08 13:50:18 mrajkowski
 *      Exp $
 */
public class ObjectHookManager {

  private ObjectHookList hookList = null;
  private BusinessProcessList processList = null;
  private String fileLibraryPath = null;


  /**
   *  Constructor for the ObjectHookManager object
   */
  public ObjectHookManager() { }


  /**
   *  Sets the fileLibraryPath attribute of the ObjectHookManager object
   *
   *@param  tmp  The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   *  Gets the hookList attribute of the ObjectHookManager object
   *
   *@return    The hookList value
   */
  public ObjectHookList getHookList() {
    return hookList;
  }


  /**
   *  Gets the processList attribute of the ObjectHookManager object
   *
   *@return    The processList value
   */
  public BusinessProcessList getProcessList() {
    return processList;
  }


  /**
   *  Initializes the list of objects that can be hooked from an XML Element
   *
   *@param  documentElement  Description of the Parameter
   */
  public void initializeObjectHookList(Element documentElement) {
    hookList = new ObjectHookList();
    hookList.parse(documentElement);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void initializeObjectHookList(Connection db) throws SQLException {
    hookList = new ObjectHookList();
    hookList.buildList(db);
  }


  /**
   *  Initializes the list of processes that can be executed, from an XML
   *  Element
   *
   *@param  documentElement  Description of the Parameter
   */
  public void initializeBusinessProcessList(Element documentElement) {
    processList = new BusinessProcessList();
    processList.parse(documentElement);
  }


  /**
   *  Initializes the list of processes that can be executed, from a database
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void initializeBusinessProcessList(Connection db) throws SQLException {
    processList = new BusinessProcessList();
    processList.buildList(db);
  }


  /**
   *  Forwards the request to execute a business process to process(...)
   *
   *@param  packetContext   Description of the Parameter
   *@param  action          Description of the Parameter
   *@param  previousObject  Description of the Parameter
   *@param  object          Description of the Parameter
   */
  public void process(PacketContext packetContext, int action, Object previousObject, Object object) {
    process(packetContext.getActionContext(), action, previousObject, object, packetContext.getConnectionPool(), packetContext.getConnectionElement());
  }


  /**
   *  Executes a business process based on the objects that are specified. The
   *  objectHook must already be loaded into the hookList, with a corresponding
   *  action to trigger on: insert, update, or delete. Also, the business
   *  process must already be loaded into the business process list.
   *
   *@param  actionContext   Description of the Parameter
   *@param  action          Description of the Parameter
   *@param  previousObject  Description of the Parameter
   *@param  object          Description of the Parameter
   *@param  sqlDriver       Description of the Parameter
   *@param  ce              Description of the Parameter
   */
  public void process(ActionContext actionContext, int action, Object previousObject, Object object, ConnectionPool sqlDriver, ConnectionElement ce) {
    try {
      WorkflowManager wfManager = (WorkflowManager) actionContext.getServletContext().getAttribute("WorkflowManager");
      if (wfManager != null && hookList != null && processList != null) {
        if ((object != null && hookList.has(object)) ||
            (previousObject != null && hookList.has(previousObject))) {
          ApplicationPrefs prefs = (ApplicationPrefs) actionContext.getServletContext().getAttribute("applicationPrefs");
          ComponentContext context = new ComponentContext();
          context.setPreviousObject(previousObject);
          context.setThisObject(object);
          context.setParameter("FileLibraryPath", fileLibraryPath);
          context.setAttribute("ConnectionPool", sqlDriver);
          context.setAttribute("ConnectionElement", ce);
          context.setAttribute("ClientSSLKeystore", actionContext.getServletContext().getAttribute("ClientSSLKeystore"));
          context.setAttribute("ClientSSLKeystorePassword", actionContext.getServletContext().getAttribute("ClientSSLKeystorePassword"));
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
   *  Executes a specified business process in a new thread. The business
   *  process must already be loaded into the business process list.<p>
   *
   *  Commonly used for business processes that are triggered by a cron, or
   *  manually triggered.
   *
   *@param  servletContext  Description of the Parameter
   *@param  processName     Description of the Parameter
   *@param  sqlDriver       Description of the Parameter
   *@param  ce              Description of the Parameter
   */
  public void process(ServletContext servletContext, String processName, ConnectionPool sqlDriver, ConnectionElement ce) {
    WorkflowManager wfManager = (WorkflowManager) servletContext.getAttribute("WorkflowManager");
    if (wfManager != null && processList != null) {
      ComponentContext context = new ComponentContext();
      context.setParameter("FileLibraryPath", fileLibraryPath);
      context.setAttribute("ConnectionPool", sqlDriver);
      context.setAttribute("ConnectionElement", ce);
      context.setAttribute("ClientSSLKeystore", servletContext.getAttribute("ClientSSLKeystore"));
      context.setAttribute("ClientSSLKeystorePassword", servletContext.getAttribute("ClientSSLKeystorePassword"));
      ApplicationPrefs prefs = (ApplicationPrefs) servletContext.getAttribute("applicationPrefs");
      if (prefs != null) {
        context.setAttribute("MAILSERVER", prefs.get("MAILSERVER"));
        context.setAttribute("EMAILADDRESS", prefs.get("EMAILADDRESS"));
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ObjectHookList-> Hook thread start for process: " + processName);
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

