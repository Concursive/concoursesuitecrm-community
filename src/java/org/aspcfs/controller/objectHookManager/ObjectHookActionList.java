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
package org.aspcfs.controller.objectHookManager;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Maintains a list of action mappings for the class trigger... Ex. A ticket
 * can be triggered by at least the following actions: insert, update, delete
 *
 * @author matt rajkowski
 * @version $Id: ObjectHookActionList.java,v 1.3 2003/01/13 21:41:16
 *          mrajkowski Exp $
 * @created November 11, 2002
 */
public class ObjectHookActionList extends HashMap {
  private String className = null;
  private int linkModuleId = -1;
  private String linkModule = null;
  private boolean isApplication = false;


  /**
   * Constructor for the ObjectHookActionList object
   */
  public ObjectHookActionList() {
  }


  /**
   * Sets the className attribute of the ObjectHookActionList object
   *
   * @param tmp The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the ObjectHookActionList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the ObjectHookActionList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the className attribute of the ObjectHookActionList object
   *
   * @return The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   * Gets the linkModuleId attribute of the ObjectHookActionList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the isApplication attribute of the ObjectHookActionList object
   *
   * @return The isApplication value
   */
  public boolean getIsApplication() {
    return isApplication;
  }


  /**
   * Sets the isApplication attribute of the ObjectHookActionList object
   *
   * @param tmp The new isApplication value
   */
  public void setIsApplication(boolean tmp) {
    this.isApplication = tmp;
  }


  /**
   * Sets the isApplication attribute of the ObjectHookActionList object
   *
   * @param tmp The new isApplication value
   */
  public void setIsApplication(String tmp) {
    this.isApplication = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Constructor for the ObjectHookActionList object
   *
   * @param hookElement Description of the Parameter
   * @param isApp       Description of the Parameter
   */
  public ObjectHookActionList(Element hookElement, boolean isApp) {
    processHookElement(hookElement, isApp);
  }


  /**
   * Description of the Method
   *
   * @param hookElement Description of the Parameter
   * @param isApp       Description of the Parameter
   */
  public void processHookElement(Element hookElement, boolean isApp) {
    this.setIsApplication(isApp);
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
        thisAction.setPriority(
            this.getNextPriority(
                className, this.getIsApplication(), thisAction.getTypeId()));
        thisAction.setApplication(this.getIsApplication());
        this.put(
            new String(
                "" + thisAction.getApplication() + "|" + thisAction.getPriority() + "|" + thisAction.getTypeId()), thisAction);
      }
    }
  }


  /**
   * Gets the nextPriority attribute of the ObjectHookActionList object
   *
   * @param className     Description of the Parameter
   * @param isApplication Description of the Parameter
   * @param typeId        Description of the Parameter
   * @return The nextPriority value
   */
  public int getNextPriority(String className, boolean isApplication, int typeId) {
    int result = 0;
    Iterator iterator = this.values().iterator();
    while (iterator.hasNext()) {
      ObjectHookAction thisAction = (ObjectHookAction) iterator.next();
      if (thisAction.getClassName().equals(className)) {
        if (result < thisAction.getPriority() && (isApplication == thisAction.getApplication()) && (typeId == thisAction.getTypeId())) {
          result = thisAction.getPriority();
        }
      }
    }
    return (++result);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator i = this.values().iterator();
    while (i.hasNext()) {
      ObjectHookAction thisAction = (ObjectHookAction) i.next();
      if (!thisAction.getApplication()) {
        thisAction.insert(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param userProc Description of the Parameter
   * @param applProc Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean removeProcessesFromMemory(boolean userProc, boolean applProc) {
    ArrayList keys = new ArrayList();
    Iterator iterator = (Iterator) this.keySet().iterator();
    while (iterator.hasNext()) {
      String key = (String) iterator.next();
      if (userProc && key.indexOf("false") != -1) {
        keys.add(key);
      }
      if (applProc && key.indexOf("true") != -1) {
        keys.add(key);
      }
    }
    iterator = keys.iterator();
    while (iterator.hasNext()) {
      String key = (String) iterator.next();
      ObjectHookAction hookAction = (ObjectHookAction) this.remove(key);
      hookAction = null;
    }
    return true;
  }
}

