//Copyright 2002 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;

/**
 *  Contains a mapping of BusinessComponent objects that can be executed in a
 *  hierarchical order by the WorkflowManager
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: BusinessProcess.java,v 1.3 2003/01/13 21:41:16 mrajkowski Exp
 *      $
 */
public class BusinessProcess extends HashMap {
  private String name = null;
  private String description = null;
  private int startId = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private ArrayList parameters = null;


  /**
   *  Constructor for the BusinessProcess object
   */
  public BusinessProcess() { }


  /**
   *  Constructor for the BusinessProcess object
   *
   *@param  processElement  Description of the Parameter
   *@param  enabled         Description of the Parameter
   */
  public BusinessProcess(Element processElement, int enabled) {
    //Set the base data
    this.setName((String) processElement.getAttribute("name"));
    this.setDescription((String) processElement.getAttribute("description"));
    this.setStartId((String) processElement.getAttribute("startId"));
    this.setEntered((String) processElement.getAttribute("entered"));
    this.setModified((String) processElement.getAttribute("modified"));
    //Add all the enabled components
    Element components = XMLUtils.getFirstElement(processElement, "components");
    if (components != null) {
      ArrayList componentNodes = XMLUtils.getElements(components, "component");
      Iterator componentElements = componentNodes.iterator();
      while (componentElements.hasNext()) {
        Element componentElement = (Element) componentElements.next();
        String componentEnabled = (String) componentElement.getAttribute("enabled");
        if (enabled == Constants.TRUE && componentEnabled != null && "false".equals(componentEnabled)) {
          break;
        }
        BusinessProcessComponent thisComponent = new BusinessProcessComponent(componentElement, enabled);
        this.put(new Integer(thisComponent.getId()), thisComponent);
      }
      //Link the components together
      Iterator cList = this.values().iterator();
      while (cList.hasNext()) {
        BusinessProcessComponent thisComponent = (BusinessProcessComponent) cList.next();
        if (thisComponent.getParentId() > 0) {
          //Locate the parent
          BusinessProcessComponent parent = (BusinessProcessComponent) this.get(new Integer(thisComponent.getParentId()));
          if (parent != null) {
            //The parent will execute this child if the child accepts true, false, or neither
            if (thisComponent.getParentResult() == Constants.TRUE || thisComponent.getParentResult() == -1) {
              parent.addTrueChild(thisComponent);
            }
            if (thisComponent.getParentResult() == Constants.FALSE || thisComponent.getParentResult() == -1) {
              parent.addFalseChild(thisComponent);
            }
          }
        }
      }
    }
    //Add the globally enabled parameters for this process
    Element parameters = XMLUtils.getFirstElement(processElement, "parameters");
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
   *  Sets the name attribute of the BusinessProcess object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the BusinessProcess object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the startId attribute of the BusinessProcess object
   *
   *@param  tmp  The new startId value
   */
  public void setStartId(int tmp) {
    this.startId = tmp;
  }


  /**
   *  Sets the startId attribute of the BusinessProcess object
   *
   *@param  tmp  The new startId value
   */
  public void setStartId(String tmp) {
    this.startId = DatabaseUtils.parseInt(tmp, -1);
  }


  /**
   *  Sets the entered attribute of the BusinessProcess object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the BusinessProcess object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modified attribute of the BusinessProcess object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the BusinessProcess object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the parameters attribute of the BusinessProcess object
   *
   *@param  tmp  The new parameters value
   */
  public void setParameters(ArrayList tmp) {
    this.parameters = tmp;
  }


  /**
   *  Gets the name attribute of the BusinessProcess object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the BusinessProcess object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the startId attribute of the BusinessProcess object
   *
   *@return    The startId value
   */
  public int getStartId() {
    return startId;
  }


  /**
   *  Gets the entered attribute of the BusinessProcess object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the BusinessProcess object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the parameters attribute of the BusinessProcess object
   *
   *@return    The parameters value
   */
  public ArrayList getParameters() {
    return parameters;
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
   *  Gets the component attribute of the BusinessProcess object
   *
   *@param  componentId  Description of the Parameter
   *@return              The component value
   */
  public BusinessProcessComponent getComponent(int componentId) {
    return (BusinessProcessComponent) this.get(new Integer(componentId));
  }
}

