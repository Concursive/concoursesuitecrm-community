package com.darkhorseventures.controller;

import org.w3c.dom.Element;
import com.darkhorseventures.utils.*;

/**
 *  When a hook is triggered, the ObjectHookManager must check to see if
 *  a correspending mapping to the action that triggered the event occurs.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
 */
public class ObjectHookAction {
  /**
   *  Description of the Field
   */
  public final static int UNDEFINED = -1;
  /**
   *  Description of the Field
   */
  public final static int INSERT = 1;
  /**
   *  Description of the Field
   */
  public final static int UPDATE = 2;
  /**
   *  Description of the Field
   */
  public final static int DELETE = 3;

  private int typeId = -1;
  private String processName = null;
  private boolean enabled = true;


  /**
   *  Constructor for the ObjectHookAction object
   */
  public ObjectHookAction() { }


  /**
   *  Constructor for the ObjectHookAction object
   *
   *@param  actionElement  Description of the Parameter
   */
  public ObjectHookAction(Element actionElement) {
    this.setType((String) actionElement.getAttribute("type"));
    this.setProcessName((String) actionElement.getAttribute("process"));
    this.setEnabled((String) actionElement.getAttribute("enabled"));
  }


  /**
   *  Sets the typeId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = DatabaseUtils.parseInt(tmp, UNDEFINED);
  }


  /**
   *  Sets the type attribute of the ObjectHookAction object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.typeId = parseAction(tmp);
  }


  /**
   *  Sets the processName attribute of the ObjectHookAction object
   *
   *@param  tmp  The new processName value
   */
  public void setProcessName(String tmp) {
    this.processName = tmp;
  }


  /**
   *  Sets the enabled attribute of the ObjectHookAction object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ObjectHookAction object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = (DatabaseUtils.parseBoolean(tmp) || tmp == null || "".equals(tmp));
  }


  /**
   *  Gets the typeId attribute of the ObjectHookAction object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the processName attribute of the ObjectHookAction object
   *
   *@return    The processName value
   */
  public String getProcessName() {
    return processName;
  }


  /**
   *  Gets the enabled attribute of the ObjectHookAction object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Description of the Method
   *
   *@param  actionText  Description of the Parameter
   *@return             Description of the Return Value
   */
  public static int parseAction(String actionText) {
    if ("insert".equals(actionText)) {
      return INSERT;
    } else if ("update".equals(actionText)) {
      return UPDATE;
    } else if ("delete".equals(actionText)) {
      return DELETE;
    } else {
      return UNDEFINED;
    }
  }
}

