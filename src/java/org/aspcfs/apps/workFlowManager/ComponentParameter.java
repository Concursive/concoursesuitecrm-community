//Copyright 2002 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import org.w3c.dom.Element;
import org.aspcfs.utils.*;

/**
 *  Represents a key=value pair preference that will be used for either a
 *  BusinessProcess definition or a BusinessProcess component.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ComponentParameter.java,v 1.3 2003/01/13 21:41:16 mrajkowski
 *      Exp $
 */
public class ComponentParameter {

  private String name = null;
  private String value = null;
  private boolean enabled = true;


  /**
   *  Constructor for the ComponentParameter object
   */
  public ComponentParameter() { }


  /**
   *  Constructor for the ComponentParameter object
   *
   *@param  parameterElement  Description of the Parameter
   *@param  enabled           Description of the Parameter
   */
  public ComponentParameter(Element parameterElement, int enabled) {
    this.setName((String) parameterElement.getAttribute("name"));
    String newValue = (String) parameterElement.getAttribute("value");
    if (newValue == null || "".equals(newValue)) {
      newValue = XMLUtils.getNodeText(parameterElement);
      if (newValue == null) {
        newValue = "";
      }
    }
    this.setValue(newValue);
    this.setEnabled((String) parameterElement.getAttribute("enabled"));
  }


  /**
   *  Sets the name attribute of the ComponentParameter object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the value attribute of the ComponentParameter object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the enabled attribute of the ComponentParameter object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ComponentParameter object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = (DatabaseUtils.parseBoolean(tmp) || tmp == null || "".equals(tmp));
  }


  /**
   *  Gets the name attribute of the ComponentParameter object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the value attribute of the ComponentParameter object
   *
   *@return    The value value
   */
  public String getValue() {
    return value;
  }


  /**
   *  Gets the enabled attribute of the ComponentParameter object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }
}

