//Copyright 2002 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;

/**
 *  A BusinessProcessComponent is a definition of where and when a class will be
 *  executed within a BusinessProcess hierarchy.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: BusinessProcessComponent.java,v 1.3 2003/01/13 21:41:16
 *      mrajkowski Exp $
 */
public class BusinessProcessComponent {

  private int id = -1;
  private int parentId = -1;
  private int parentResult = -1;
  private String className = null;
  private String description = null;
  private boolean enabled = true;
  private ArrayList parameters = null;
  //private ObjectLayout layout = null;
  private ArrayList trueChildren = null;
  private ArrayList falseChildren = null;


  /**
   *  Constructor for the BusinessProcessComponent object
   */
  public BusinessProcessComponent() { }


  /**
   *  Constructor for the BusinessProcessComponent object
   *
   *@param  componentElement  Description of the Parameter
   *@param  enabled           Description of the Parameter
   */
  public BusinessProcessComponent(Element componentElement, int enabled) {
    //Set the base data
    this.setId((String) componentElement.getAttribute("id"));
    this.setParentId((String) componentElement.getAttribute("parent"));
    this.setParentResult((String) componentElement.getAttribute("if"));
    this.setClassName((String) componentElement.getAttribute("class"));
    this.setDescription((String) componentElement.getAttribute("description"));
    this.setEnabled((String) componentElement.getAttribute("enabled"));
    //Add the enabled parameters
    Element parameters = XMLUtils.getFirstElement(componentElement, "parameters");
    if (parameters != null) {
      ArrayList parameterNodes = XMLUtils.getElements(parameters, "parameter");
      Iterator parameterElements = parameterNodes.iterator();
      while (parameterElements.hasNext()) {
        Element parameterElement = (Element) parameterElements.next();
        String parameterEnabled = (String) parameterElement.getAttribute("enabled");
        if (enabled == Constants.TRUE && parameterEnabled != null && "false".equals(parameterEnabled)) {
          break;
        }
        ComponentParameter thisParameter = new ComponentParameter(parameterElement, enabled);
        if (this.parameters == null) {
          this.parameters = new ArrayList();
        }
        this.parameters.add(thisParameter);
      }
    }
  }


  /**
   *  Sets the id attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = DatabaseUtils.parseInt(tmp, -1);
  }


  /**
   *  Sets the parentId attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = DatabaseUtils.parseInt(tmp, -1);
  }


  /**
   *  Sets the parentResult attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new parentResult value
   */
  public void setParentResult(int tmp) {
    this.parentResult = tmp;
  }


  /**
   *  Sets the parentResult attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new parentResult value
   */
  public void setParentResult(String tmp) {
    if ("false".equals(tmp)) {
      this.parentResult = Constants.FALSE;
    } else if ("true".equals(tmp)) {
      this.parentResult = Constants.TRUE;
    } else {
      this.parentResult = -1;
    }
  }


  /**
   *  Sets the className attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
  }


  /**
   *  Sets the description attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the enabled attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = (DatabaseUtils.parseBoolean(tmp) || tmp == null || "".equals(tmp));
  }


  /**
   *  Sets the parameters attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new parameters value
   */
  public void setParameters(ArrayList tmp) {
    this.parameters = tmp;
  }


  /**
   *  Sets the trueChildren attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new trueChildren value
   */
  public void setTrueChildren(ArrayList tmp) {
    this.trueChildren = tmp;
  }


  /**
   *  Sets the falseChildren attribute of the BusinessProcessComponent object
   *
   *@param  tmp  The new falseChildren value
   */
  public void setFalseChildren(ArrayList tmp) {
    this.falseChildren = tmp;
  }


  /**
   *  Gets the id attribute of the BusinessProcessComponent object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the BusinessProcessComponent object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the parentResult attribute of the BusinessProcessComponent object
   *
   *@return    The parentResult value
   */
  public int getParentResult() {
    return parentResult;
  }


  /**
   *  Gets the className attribute of the BusinessProcessComponent object
   *
   *@return    The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   *  Gets the description attribute of the BusinessProcessComponent object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the enabled attribute of the BusinessProcessComponent object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the parameters attribute of the BusinessProcessComponent object
   *
   *@return    The parameters value
   */
  public ArrayList getParameters() {
    return parameters;
  }


  /**
   *  Gets the trueChildren attribute of the BusinessProcessComponent object
   *
   *@return    The trueChildren value
   */
  public ArrayList getTrueChildren() {
    return trueChildren;
  }


  /**
   *  Gets the falseChildren attribute of the BusinessProcessComponent object
   *
   *@return    The falseChildren value
   */
  public ArrayList getFalseChildren() {
    return falseChildren;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasParameters() {
    return (parameters != null && parameters.size() > 0);
  }


  /**
   *  Adds a feature to the TrueChild attribute of the BusinessProcessComponent
   *  object
   *
   *@param  childComponent  The feature to be added to the TrueChild attribute
   */
  public void addTrueChild(BusinessProcessComponent childComponent) {
    if (trueChildren == null) {
      trueChildren = new ArrayList();
    }
    trueChildren.add(childComponent);
  }


  /**
   *  Adds a feature to the FalseChild attribute of the BusinessProcessComponent
   *  object
   *
   *@param  childComponent  The feature to be added to the FalseChild attribute
   */
  public void addFalseChild(BusinessProcessComponent childComponent) {
    if (falseChildren == null) {
      falseChildren = new ArrayList();
    }
    falseChildren.add(childComponent);
  }
}

