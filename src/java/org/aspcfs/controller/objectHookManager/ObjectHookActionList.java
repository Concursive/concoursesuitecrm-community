//Copyright 2002 Dark Horse Ventures
package org.aspcfs.controller.objectHookManager;

import java.util.*;
import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.sql.*;

/**
 *  Maintains a list of action mappings for the class trigger... Ex. A ticket
 *  can be triggered by at least the following actions: insert, update, delete
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ObjectHookActionList.java,v 1.3 2003/01/13 21:41:16
 *      mrajkowski Exp $
 */
public class ObjectHookActionList extends HashMap {
  private String className = null;
  private int linkModuleId = -1;
  private String linkModule = null;


  /**
   *  Constructor for the ObjectHookActionList object
   */
  public ObjectHookActionList() { }


  /**
   *  Sets the className attribute of the ObjectHookActionList object
   *
   *@param  tmp  The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the ObjectHookActionList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the ObjectHookActionList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }



  /**
   *  Gets the className attribute of the ObjectHookActionList object
   *
   *@return    The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   *  Gets the linkModuleId attribute of the ObjectHookActionList object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Constructor for the ObjectHookActionList object
   *
   *@param  hookElement  Description of the Parameter
   */
  public ObjectHookActionList(Element hookElement) {
    className = (String) hookElement.getAttribute("class");
    linkModule = (String) hookElement.getAttribute("module");
    Element actions = XMLUtils.getFirstElement(hookElement, "actions");
    if (actions != null) {
      ArrayList actionNodes = XMLUtils.getElements(actions, "action");
      Iterator actionElements = actionNodes.iterator();
      while (actionElements.hasNext()) {
        Element actionElement = (Element) actionElements.next();
        ObjectHookAction thisAction = new ObjectHookAction(actionElement);
        thisAction.setClassName(className);
        thisAction.setLinkModule(linkModule);
        this.put(new Integer(thisAction.getTypeId()), thisAction);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator i = this.values().iterator();
    while (i.hasNext()) {
      ObjectHookAction thisAction = (ObjectHookAction) i.next();
      thisAction.insert(db);
    }
  }
}

