//Copyright 2002 Dark Horse Ventures
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
/**
 *  Manages hooks within the application and attaches to a WorkflowManager to
 *  execute BusinessProcess objects as defined by ObjectHook objects.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
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

  public BusinessProcessList getProcessList() {
    return processList;
  }

  /**
   *  Description of the Method
   *
   *@param  hookXML  Description of the Parameter
   */
  public void initializeObjectHookList(String hookXML) {
    hookList = new ObjectHookList();
    hookList.parse(hookXML);
  }


  /**
   *  Description of the Method
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
   *@param  processXML  Description of the Parameter
   */
  public void initializeBusinessProcessList(String processXML) {
    processList = new BusinessProcessList();
    processList.parse(processXML);
  }


  /**
   *  Description of the Method
   *
   *@param  documentElement  Description of the Parameter
   */
  public void initializeBusinessProcessList(Element documentElement) {
    processList = new BusinessProcessList();
    processList.parse(documentElement);
  }


  /**
   *  Description of the Method
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
   *  Description of the Method
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
          ComponentContext context = new ComponentContext();
          context.setPreviousObject(previousObject);
          context.setThisObject(object);
          context.setParameter("FileLibraryPath", fileLibraryPath);
          context.setAttribute("ConnectionPool", sqlDriver);
          context.setAttribute("ConnectionElement", ce);
          context.setAttribute("ClientSSLKeystore", actionContext.getServletContext().getAttribute("ClientSSLKeystore"));
          context.setAttribute("ClientSSLKeystorePassword", actionContext.getServletContext().getAttribute("ClientSSLKeystorePassword"));
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ObjectHookList-> Hook thread start");
          }
          ObjectHook thisHook = new ObjectHook(context);
          thisHook.setActionId(action);
          thisHook.setObjectHookList(hookList);
          thisHook.setBusinessProcessList(processList);
          thisHook.setManager(wfManager);
          thisHook.start();
        }
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ObjectHookManager-> WorkflowManager not found");
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace(System.out);
    }
  }
}

