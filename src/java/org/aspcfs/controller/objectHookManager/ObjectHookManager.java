//Copyright 2002 Dark Horse Ventures
package com.darkhorseventures.controller;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.Constants;
import java.io.*;
import org.theseus.actions.*;

/**
 *  Manages hooks within the application and attaches to a WorkflowManager
 *  to execute BusinessProcess objects as defined by ObjectHook objects.
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


  /**
   *  Description of the Method
   *
   *@param  hookXML  Description of the Parameter
   */
  public void initializeObjectHookList(String hookXML) {
    hookList = new ObjectHookList();
    hookList.parse(hookXML);
    //TODO: remove test code
    hookList.buildListTest();
  }


  /**
   *  Description of the Method
   *
   *@param  processXML  Description of the Parameter
   */
  public void initializeBusinessProcessList(String processXML) {
    processList = new BusinessProcessList();
    processList.parse(processXML);
    //TODO: remove test code
    processList.buildListTest();
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
      if (wfManager != null) {
        if ((object != null && hookList.has(object)) ||
            (previousObject != null && hookList.has(previousObject))) {
          ComponentContext context = new ComponentContext();
          context.setPreviousObject(previousObject);
          context.setThisObject(object);
          context.setParameter("FileLibraryPath", fileLibraryPath);
          context.setAttribute("ConnectionPool", sqlDriver);
          context.setAttribute("ConnectionElement", ce);
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

