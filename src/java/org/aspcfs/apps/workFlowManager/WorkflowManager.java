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
package org.aspcfs.apps.workFlowManager;

import org.aspcfs.controller.objectHookManager.ObjectHookComponent;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The WorkflowManager is responsible for executing Business Processes. During
 * execution, individual components are executed and evaluated, which in turn
 * can further execute children components.
 *
 * @author matt rajkowski
 * @version $Id: WorkflowManager.java,v 1.4 2003/01/13 21:41:16 mrajkowski Exp
 *          $
 * @created January 13, 2003
 */
public class WorkflowManager {
  Map classes = new HashMap();


  /**
   * Constructor for the WorkflowManager object
   */
  public WorkflowManager() {
  }


  /**
   * Execution of a business process begins by executing this method with a
   * valid ComponentContext.
   *
   * @param context Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void execute(ComponentContext context) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("WorkflowManager-> Executing Business Process");
    }
    //Populate the global process parameters, individual components can override later
    populateProcessParameters(context);
    int startId = context.getProcess().getStartId();
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "WorkflowManager-> Business Process Start: " + startId);
    }
    //Execute the process, starting with 1st component
    BusinessProcessComponent startComponent = context.getProcess().getComponent(
        startId);
    if (startComponent.getEnabled()) {
      if (context.getProcess().getType() == BusinessProcess.SCHEDULED_EVENT) {
        //Retrieve the lastAnchor, and set nextAnchor to current timestamp
        //based on process name
        //store in componentContext as process.lastAnchor, process.nextAnchor
        loadAnchors(context);
      }
      boolean startResult = this.executeComponent(context, startComponent);
      processChildren(context, startComponent, startResult);
      if (context.getProcess().getType() == BusinessProcess.SCHEDULED_EVENT) {
        //Store the nextAnchor as the lastAnchor
        saveAnchors(context);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("WorkflowManager-> Business Process End");
    }
  }


  /**
   * After a component is executed, the qualifying children are executed based
   * on the parent's result.
   *
   * @param context        Description of the Parameter
   * @param bpc            Description of the Parameter
   * @param previousResult Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void processChildren(ComponentContext context, BusinessProcessComponent bpc, boolean previousResult) throws Exception {
    Iterator i = bpc.getChildren(previousResult).iterator();
    while (i.hasNext()) {
      BusinessProcessComponent child = (BusinessProcessComponent) i.next();
      if (child.getEnabled()) {
        boolean result = executeComponent(context, child);
        processChildren(context, child, result);
      }
    }
  }


  /**
   * Executes a specific component and returns the component's return value.
   *
   * @param context   Description of the Parameter
   * @param component Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  private boolean executeComponent(ComponentContext context, BusinessProcessComponent component) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.print(
          "WorkflowManager-> Executing: " + component.getClassName() +" " + component.getDescription() + "? ");
    }
    //Retrieve the class from the cache, or instantiate a new one
    Object classRef = null;
    if (classes.containsKey(component.getClassName())) {
      classRef = classes.get(component.getClassName());
    } else {
      try {
        classRef = Class.forName(component.getClassName()).newInstance();
        classes.put(component.getClassName(), classRef);
      } catch (ClassNotFoundException cnfe) {
        System.out.println(
            "WorkflowManager-> Class Not Found Exception. MESSAGE = " + cnfe.getMessage());
      } catch (InstantiationException ie) {
        System.out.println(
            "WorkflowManager-> Instantiation Exception. MESSAGE = " + ie.getMessage());
      } catch (IllegalAccessException iae) {
        System.out.println(
            "WorkflowManager-> Illegal Argument Exception. MESSAGE = " + iae.getMessage());
      }
    }

    //Now we are ready to call upon the method in the component class instance we have..
    Object result = null;
    try {
      if (context == null) {
        System.out.println("WorkflowManager-> Context is null");
      }
      if (classRef == null) {
        System.out.println("WorkflowManager-> Class ref is null");
      }

      //Add configuration data from component definitions into the context,
      //overriding any global parameters
      this.populateComponentParameters(context, component);
      Method method = classRef.getClass().getMethod(
          "execute", new Class[]{context.getClass()});
      result = method.invoke(classRef, new Object[]{context});
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.out.println(
          "WorkflowManager-> Exception while trying to execute component " + component.getClassName() + " error: " + e.getMessage());
    }
    if (result instanceof Boolean) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(((Boolean) result).booleanValue());
      }
      return ((Boolean) result).booleanValue();
    } else if (result instanceof Integer) {
      //NOTE: Supported, but not used yet
      if (System.getProperty("DEBUG") != null) {
        System.out.println(((Integer) result).intValue());
      }
      return (((Integer) result).intValue() == 1);
    } else {
      System.out.println(
          "WorkflowManager-> Component did not return an acceptable result");
      return false;
    }
  }


  /**
   * Before a component is executed, global parameters are applied to the
   * component, however, local parameters for a component will override these.
   *
   * @param context Description of the Parameter
   */
  private static void populateProcessParameters(ComponentContext context) {
    BusinessProcess thisProcess = context.getProcess();
    if (thisProcess.hasParameters()) {
      Iterator i = thisProcess.getParameters().iterator();
      while (i.hasNext()) {
        ProcessParameter thisParameter = (ProcessParameter) i.next();
        if (thisParameter.getEnabled()) {
          context.setParameter(
              thisParameter.getName(), thisParameter.getValue());
        }
      }
    }
  }


  /**
   * Before a component is executed, local parameters for a component are
   * applied, overwriting any global parameters that may have been set.
   *
   * @param context   Description of the Parameter
   * @param component Description of the Parameter
   */
  private static void populateComponentParameters(ComponentContext context, BusinessProcessComponent component) {
    if (component.hasParameters()) {
      Iterator i = component.getParameters().iterator();
      while (i.hasNext()) {
        ComponentParameter thisParameter = (ComponentParameter) i.next();
        if (thisParameter.getEnabled()) {
          context.setParameter(
              thisParameter.getName(), thisParameter.getValue());
        }
      }
    }
  }


  /**
   * Loads the last time the process executed successfully from the database,
   *
   * @param context Description of the Parameter
   */
  private static void loadAnchors(ComponentContext context) {
    System.out.println("WorkflowManager-> loadAnchors");
    Connection db = null;
    try {
      context.setParameter(
          "process.nextAnchor", (new java.sql.Timestamp(
              new java.util.Date().getTime())).toString());
      db = ObjectHookComponent.getConnection(context);
      PreparedStatement pst = db.prepareStatement(
          "SELECT anchor " +
          "FROM business_process_log " +
          "WHERE process_name = ? ");
      pst.setString(1, context.getProcess().getName());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        context.setParameter(
            "process.lastAnchor", rs.getTimestamp("anchor").toString());
      }
      rs.close();
      pst.close();
    } catch (Exception e) {
    } finally {
      ObjectHookComponent.freeConnection(context, db);
    }
  }


  /**
   * Saves the start time when the last process executed successfully, to the
   * database
   *
   * @param context Description of the Parameter
   */
  private static void saveAnchors(ComponentContext context) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("WorkflowManager-> saveAnchors: " + context.getProcess().getName());
    }
    Connection db = null;
    try {
      db = ObjectHookComponent.getConnection(context);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE business_process_log " +
          "SET anchor = ? " +
          "WHERE process_name = ? " +
          "AND anchor = ? ");
      if (context.hasParameter("process.nextAnchor")) {
        pst.setTimestamp(
            1, java.sql.Timestamp.valueOf(
                context.getParameter("process.nextAnchor")));
      } else {
        pst.setNull(1, java.sql.Types.DATE);
      }
      pst.setString(2, context.getProcess().getName());
      if (context.hasParameter("process.lastAnchor")) {
        pst.setTimestamp(
            3, java.sql.Timestamp.valueOf(
                context.getParameter("process.lastAnchor")));
      } else {
        pst.setNull(3, java.sql.Types.DATE);
      }
      int count = pst.executeUpdate();
      pst.close();
      if (count == 0) {
        pst = db.prepareStatement(
            "INSERT INTO business_process_log " +
            "(process_name, anchor) VALUES (?, ?) ");
        pst.setString(1, context.getProcess().getName());
        if (context.hasParameter("process.nextAnchor")) {
          pst.setTimestamp(
              2, java.sql.Timestamp.valueOf(
                  context.getParameter("process.nextAnchor")));
        } else {
          pst.setNull(2, java.sql.Types.DATE);
        }
        pst.execute();
        pst.close();
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      ObjectHookComponent.freeConnection(context, db);
    }
  }
}

