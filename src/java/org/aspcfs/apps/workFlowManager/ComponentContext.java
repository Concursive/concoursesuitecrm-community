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

import java.util.*;
import org.aspcfs.utils.*;

/**
 *  When a BusinessProcess is executed, a ComponentContext is created for that
 *  process only to hold data that can be used between components.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ComponentContext.java,v 1.5 2003/01/13 21:41:16 mrajkowski
 *      Exp $
 */
public class ComponentContext extends HashMap {
  public final static int TEXT_ENCODING = 0;
  public final static int XML_ENCODING = 1;
  public final static int HTML_ENCODING = 2;

  private WorkflowManager manager = null;
  private String processName = null;
  private BusinessProcess process = null;
  private Object previousObject = null;
  private Object thisObject = null;
  private AbstractList objects = null;


  /**
   *  Constructor for the ComponentContext object
   */
  public ComponentContext() { }


  /**
   *  Set the name of the Process that should be executed
   *
   *@param  tmp  The new processName value
   */
  public void setProcessName(String tmp) {
    this.processName = tmp;
  }


  /**
   *  Sets the process attribute of the ComponentContext object
   *
   *@param  tmp  The new process value
   */
  public void setProcess(BusinessProcess tmp) {
    this.process = tmp;
  }


  /**
   *  Sets the previousObject attribute of the ComponentContext object
   *
   *@param  tmp  The new previousObject value
   */
  public void setPreviousObject(Object tmp) {
    this.previousObject = tmp;
  }


  /**
   *  Sets the thisObject attribute of the ComponentContext object
   *
   *@param  tmp  The new thisObject value
   */
  public void setThisObject(Object tmp) {
    this.thisObject = tmp;
  }


  /**
   *  Sets the objects attribute of the ComponentContext object
   *
   *@param  tmp  The new objects value
   */
  public void setObjects(AbstractList tmp) {
    this.objects = tmp;
  }


  /**
   *  Sets the manager attribute of the ComponentContext object
   *
   *@param  tmp  The new manager value
   */
  public void setManager(WorkflowManager tmp) {
    this.manager = tmp;
  }


  /**
   *  Gets the processName attribute of the ComponentContext object
   *
   *@return    The processName value
   */
  public String getProcessName() {
    return processName;
  }


  /**
   *  Gets the process attribute of the ComponentContext object
   *
   *@return    The process value
   */
  public BusinessProcess getProcess() {
    return process;
  }


  /**
   *  Gets the previousObject attribute of the ComponentContext object
   *
   *@return    The previousObject value
   */
  public Object getPreviousObject() {
    return previousObject;
  }


  /**
   *  Gets the thisObject attribute of the ComponentContext object
   *
   *@return    The thisObject value
   */
  public Object getThisObject() {
    return thisObject;
  }


  /**
   *  Gets the objects attribute of the ComponentContext object
   *
   *@return    The objects value
   */
  public AbstractList getObjects() {
    return objects;
  }


  /**
   *  Sets the parameter attribute of the ComponentContext object
   *
   *@param  parameterName  The new parameter value
   *@param  value          The new parameter value
   */
  public void setParameter(String parameterName, String value) {
    if (value != null) {
      this.put(parameterName, value);
    }
  }


  /**
   *  Returns whether the process was triggered by updating an object
   *
   *@return    The update value
   */
  public boolean isUpdate() {
    return (thisObject != null && previousObject != null);
  }


  /**
   *  Returns whether the process was triggered by inserting an object
   *
   *@return    The insert value
   */
  public boolean isInsert() {
    return (thisObject != null && previousObject == null);
  }


  /**
   *  Returns whether the process was triggered by deleting an object
   *
   *@return    The delete value
   */
  public boolean isDelete() {
    return (thisObject == null && previousObject != null);
  }


  /**
   *  Gets the className attribute of the ComponentContext object
   *
   *@return    The className value
   */
  public String getClassName() {
    if (thisObject != null) {
      return thisObject.getClass().getName();
    } else if (previousObject != null) {
      return previousObject.getClass().getName();
    } else {
      return null;
    }
  }


  /**
   *  Gets the parameterAsInt attribute of the ComponentContext object
   *
   *@param  parameterName  Description of the Parameter
   *@return                The parameterAsInt value
   */
  public int getParameterAsInt(String parameterName) {
    try {
      return Integer.parseInt(this.getParameter(parameterName));
    } catch (Exception e) {
      return -1;
    }
  }


  /**
   *  Gets the parameterAsBoolean attribute of the ComponentContext object
   *
   *@param  parameterName  Description of the Parameter
   *@return                The parameterAsBoolean value
   */
  public boolean getParameterAsBoolean(String parameterName) {
    try {
      return (Boolean.valueOf(this.getParameter(parameterName))).booleanValue();
    } catch (Exception e) {
      return false;
    }
  }


  /**
   *  Gets the parameter attribute of the ComponentContext object
   *
   *@param  parameterName  Description of the Parameter
   *@return                The parameter value
   */
  public String getParameter(String parameterName) {
    return getParameter(parameterName, thisObject, previousObject);
  }


  /**
   *  Gets the parameter attribute of the ComponentContext object
   *
   *@param  parameterName   Description of the Parameter
   *@param  thisObject      Description of the Parameter
   *@param  previousObject  Description of the Parameter
   *@return                 The parameter value
   */
  public String getParameter(String parameterName, Object thisObject, Object previousObject) {
    String param = (String) this.get(parameterName);
    if (param != null) {
      if (param.indexOf("${") > -1) {
        Template template = new Template();
        template.setText(param);
        ArrayList templateVariables = template.getVariables();
        Iterator i = templateVariables.iterator();
        while (i.hasNext()) {
          String variable = (String) i.next();
          String value = retrieveContextValue(variable, thisObject, previousObject);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ComponentContext-> " + parameterName + ": ${" + variable + "} = " + value);
          }
          template.addParseElement("${" + variable + "}", value);
        }
        return template.getParsedText();
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ComponentContext-> " + parameterName + ": " + param);
        }
        return param;
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ComponentContext-> " + parameterName + ": not found");
      }
      return null;
    }
  }


  /**
   *  Sets the attribute attribute of the ComponentContext object
   *
   *@param  parameterName  The new attribute value
   *@param  value          The new attribute value
   */
  public void setAttribute(String parameterName, Object value) {
    this.put(parameterName, value);
  }


  /**
   *  Gets the attribute attribute of the ComponentContext object
   *
   *@param  parameterName  Description of the Parameter
   *@return                The attribute value
   */
  public Object getAttribute(String parameterName) {
    if (this.containsKey(parameterName)) {
      return (Object) this.get(parameterName);
    } else {
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  param  Description of the Parameter
   *@return        Description of the Return Value
   */
  public String retrieveContextValue(String param) {
    return retrieveContextValue(param, thisObject, previousObject);
  }


  /**
   *  Description of the Method
   *
   *@param  param           Description of the Parameter
   *@param  thisObject      Description of the Parameter
   *@param  previousObject  Description of the Parameter
   *@return                 Description of the Return Value
   */
  public String retrieveContextValue(String param, Object thisObject, Object previousObject) {
    //Get the parameter
    int encoding = TEXT_ENCODING;
    if (param.indexOf(":xml") > -1) {
      encoding = XML_ENCODING;
      param = param.substring(0, param.indexOf(":xml"));
    } else if (param.indexOf(":html") > -1) {
      encoding = HTML_ENCODING;
      param = param.substring(0, param.indexOf(":html"));
    }
    //Return it with the specified encoding, default to plain text
    String value = null;
    if (param.indexOf(".") > -1) {
      if (param.indexOf("process.") == 0) {
        //process.[name] is a special case in which the process has defined parameters
        value = (String) this.getAttribute(param);
      } else if (param.indexOf("objects.size") == 0) {
        //built in parameter cases
        if (objects != null) {
          value = String.valueOf(objects.size());
        } else {
          value = "0";
        }
      } else if (param.indexOf("this.") == 0) {
        //this.[name] means the current object
        value = ObjectUtils.getParam(thisObject, param.substring(5));
      } else if (param.indexOf("previous.") == 0) {
        //previous.[name] means the previous object
        value = ObjectUtils.getParam(previousObject, param.substring(9));
      } else {
        //otherwise, try to return the property of the specified object
        Object paramObject = this.getAttribute(param.substring(0, param.indexOf(".")));
        value = ObjectUtils.getParam(paramObject, param.substring(param.indexOf(".") + 1));
      }
    } else {
      value = ObjectUtils.getParam(thisObject, param);
    }
    if (value != null) {
      switch (encoding) {
          case XML_ENCODING:
            value = XMLUtils.toXMLValue(value);
            break;
          case HTML_ENCODING:
            value = HTTPUtils.toHtmlValue(value);
            break;
          default:
            break;
      }
    }
    return value;
  }


  /**
   *  Description of the Method
   *
   *@param  parameterName  Description of the Parameter
   *@return                Description of the Return Value
   */
  public boolean hasParameter(String parameterName) {
    return this.containsKey(parameterName);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasObjects() {
    return (objects != null);
  }


  /**
   *  Description of the Method
   *
   *@param  component  Description of the Parameter
   *@return            Description of the Return Value
   */
  public boolean execute(String component) {
    try {
      System.out.println("ComponentContext-> NOT IMPLEMENTED");
      return false;
      //return manager.executeComponent(this, component);
    } catch (Exception e) {
      return false;
    }
  }
}

