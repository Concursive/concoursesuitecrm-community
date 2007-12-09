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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.apps.workFlowManager.BusinessProcess;
import org.aspcfs.apps.workFlowManager.BusinessProcessComponent;
import org.aspcfs.apps.workFlowManager.BusinessProcessList;
import org.aspcfs.controller.objectHookManager.ObjectHookList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;

import java.io.File;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Submodule that generates workflow steps
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 30, 2003
 */
public final class AdminWorkflow extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Connection db = null;
    //Parameters
    String moduleId = context.getRequest().getParameter("moduleId");
    String param = context.getRequest().getParameter("process");
    if (param == null) {
      return ("SystemError");
    }
    try {
      db = this.getConnection(context);
      //Load the category for the web trails
      PermissionCategory category = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", category);
      //Load the process from the params
      BusinessProcess process = new BusinessProcess(
          db, Integer.parseInt(param));
      process.buildResources(db);
      //Take all of the components for the selected process and assign
      //a step id so that the workflow can be conveyed visually
      LinkedHashMap steps = new LinkedHashMap();
      //Put the starting component in place
      BusinessProcessComponent component = process.getComponent(
          process.getStartId());
      steps.put(new Integer(component.getId()), component);
      //Now put each child result in the list
      addChildren(steps, component);
      context.getRequest().setAttribute("steps", steps);
      context.getRequest().setAttribute("process", process);
      return "WorkflowOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Adds children to the step/component list... children are either a result
   * of the parent component being true or false after execution.
   *
   * @param map       The feature to be added to the Children attribute
   * @param component The feature to be added to the Children attribute
   */
  private void addChildren(LinkedHashMap map, BusinessProcessComponent component) {
    Iterator children = component.getAllChildren().iterator();
    while (children.hasNext()) {
      BusinessProcessComponent thisComponent = (BusinessProcessComponent) children.next();
      if (!map.containsKey(new Integer(thisComponent.getId()))) {
        map.put(new Integer(thisComponent.getId()), thisComponent);
      }
      addChildren(map, thisComponent);
    }
  }

  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    //Save the processList for the current system
    File xmlFile = new File(
        this.getPath(context) + this.getDbName(context) + fs + "workflow.xml");
    //Read in any processes
    BusinessProcessList processList = new BusinessProcessList();
    processList.buildList(xmlFile);
    //Read in any object hooks
    ObjectHookList hookList = new ObjectHookList();
    hookList.buildList(xmlFile);
    try {
      db = this.getConnection(context);
      processList.insert(db);
      hookList.insert(db);
      return "SaveOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }
}

