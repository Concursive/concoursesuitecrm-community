//Copyright 2002 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.aspcfs.utils.*;

/**
 *  When a BusinessProcess is executed, a ComponentContext is created for that
 *  process only to hold data that can be used between components.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
 */
public class ComponentContext extends HashMap {
  public static final int TEXT_ENCODING = 0;
  public static final int XML_ENCODING = 1;
  public static final int HTML_ENCODING = 2;

  private String processName = null;
  private BusinessProcess process = null;
  private Object previousObject = null;
  private Object thisObject = null;


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
   *  Sets the parameter attribute of the ComponentContext object
   *
   *@param  parameterName  The new parameter value
   *@param  value          The new parameter value
   */
  public void setParameter(String parameterName, String value) {
    this.put(parameterName, value);
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
   *  Gets the parameter attribute of the ComponentContext object
   *
   *@param  parameterName  Description of the Parameter
   *@return                The parameter value
   */
  public String getParameter(String parameterName) {
    String param = (String) this.get(parameterName);
    if (param != null) {
      if (param.indexOf("${") > -1) {
        Template template = new Template();
        template.setText(param);
        ArrayList templateVariables = template.getVariables();
        Iterator i = templateVariables.iterator();
        while (i.hasNext()) {
          String variable = (String) i.next();
          String value = retrieveContextValue(variable);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ComponentContext-> " + parameterName + ": ${" + variable + "} = " + value);
          }
          template.addParseElement("${" + variable + "}", value);
        }
        return template.getParsedText();
      } else {
        System.out.println("ComponentContext-> " + parameterName + ": " + param);
        return param;
      }
    } else {
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
    int encoding = TEXT_ENCODING;
    if (param.indexOf(":xml") > -1) {
      encoding = XML_ENCODING;
      param = param.substring(0, param.indexOf(":xml"));
    } else if (param.indexOf(":html") > -1) {
      encoding = HTML_ENCODING;
      param = param.substring(0, param.indexOf(":html"));
    }
    
    String value = null;
    if (param.indexOf(".") > -1) {
      if (param.indexOf("this.") == 0) {
        value = ObjectUtils.getParam(thisObject, param.substring(5));
      } else if (param.indexOf("previous.") == 0) {
        value = ObjectUtils.getParam(previousObject, param.substring(9));
      } else {
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
        default: break;
      }
    }
    return value;
  }
}

