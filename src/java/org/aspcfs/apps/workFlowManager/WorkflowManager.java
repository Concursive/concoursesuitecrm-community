package com.darkhorseventures.controller;

import java.lang.reflect.*;
import java.util.*;

public class WorkflowManager {
  Map classes = new HashMap();
  
  public WorkflowManager() {}
  
  public void execute(ComponentContext context) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("WorkflowManager-> Executing Business Process");
    }
    //Populate the global process parameters, individual components can override later
    this.populateProcessParameters(context);
    int startId = context.getProcess().getStartId();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("WorkflowManager-> Business Process Start: " + startId);
    }
    BusinessProcessComponent startComponent = context.getProcess().getComponent(startId);
    boolean startResult = this.executeComponent(context, startComponent);
    processChildren(context, startComponent, startResult);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("WorkflowManager-> Business Process End");
    }
  }
  
  private void processChildren(ComponentContext context, BusinessProcessComponent bpc, boolean previousResult) throws Exception {
    if (previousResult == true) {
      if (bpc.getTrueChildren() != null) {
        Iterator i = bpc.getTrueChildren().iterator();
        while (i.hasNext()) {
          BusinessProcessComponent child = (BusinessProcessComponent)i.next();
          boolean result = executeComponent(context, child);
          processChildren(context, child, result);
        }
      }
    } else {
      if (bpc.getFalseChildren() != null) {
        Iterator i = bpc.getFalseChildren().iterator();
        while (i.hasNext()) {
          BusinessProcessComponent child = (BusinessProcessComponent)i.next();
          boolean result = executeComponent(context, child);
          processChildren(context, child, result);
        }
      }
    }
  }
  
  private boolean executeComponent(ComponentContext context, BusinessProcessComponent component) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.print("WorkflowManager-> Executing: " + component.getClassName() + component.getDescription() + "? ");
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
        System.out.println("WorkflowManager-> Class Not Found Exception. MESSAGE = " + cnfe.getMessage());
      } catch (InstantiationException ie) {
        System.out.println("WorkflowManager-> Instantiation Exception. MESSAGE = " + ie.getMessage());
      } catch (IllegalAccessException iae) {
        System.out.println("WorkflowManager-> Illegal Argument Exception. MESSAGE = " + iae.getMessage());
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
      Method method = classRef.getClass().getMethod("execute", new Class[]{context.getClass()});
      result = method.invoke(classRef, new Object[]{context});
    } catch (Exception e) {
      System.out.println("WorkflowManager-> Exception while trying to execute component " + component.getClassName() + " error: " + e.getMessage());
    }
    if (result instanceof Boolean) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(((Boolean)result).booleanValue());
      }
      return ((Boolean)result).booleanValue();
    } else if (result instanceof Integer) {
      //NOTE: Supported, but not used yet
      if (System.getProperty("DEBUG") != null) {
        System.out.println(((Integer)result).intValue());
      }
      return (((Integer)result).intValue() == 1);
    } else {
      System.out.println("WorkflowManager-> Component did not return an acceptable result");
      return false;
    }
  }
  
  private void populateProcessParameters (ComponentContext context) {
    BusinessProcess thisProcess = context.getProcess();
    if (thisProcess.hasParameters()) {
      Iterator i = thisProcess.getParameters().iterator();
      while (i.hasNext()) {
        ComponentParameter thisParameter = (ComponentParameter)i.next();
        if (thisParameter.getEnabled()) {
          context.setParameter(thisParameter.getName(), thisParameter.getValue());
        }
      }
    }
  }
  
  private void populateComponentParameters (ComponentContext context, BusinessProcessComponent component) {
    if (component.hasParameters()) {
      Iterator i = component.getParameters().iterator();
      while (i.hasNext()) {
        ComponentParameter thisParameter = (ComponentParameter)i.next();
        if (thisParameter.getEnabled()) {
          context.setParameter(thisParameter.getName(), thisParameter.getValue());
        }
      }
    }
  }
}
