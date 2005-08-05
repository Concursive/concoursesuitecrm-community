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
package org.aspcfs.controller.objectHookManager;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Manages a list of hooks that are available for the given system
 *
 * @author matt rajkowski
 * @version $Id: ObjectHookList.java,v 1.8 2003/01/13 21:41:16 mrajkowski Exp
 *          $
 * @created October 14, 2002
 */
public class ObjectHookList extends HashMap {
  private int enabled = Constants.UNDEFINED;
  private int linkModuleId = -1;
  private boolean isApplication = false;


  /**
   * Constructor for the ObjectHookList object
   */
  public ObjectHookList() {
  }


  /**
   * Sets the enabled attribute of the ObjectHookList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ObjectHookList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkModuleId attribute of the ObjectHookList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the ObjectHookList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the enabled attribute of the ObjectHookList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Gets the linkModuleId attribute of the ObjectHookList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the isApplication attribute of the ObjectHookList object
   *
   * @return The isApplication value
   */
  public boolean getIsApplication() {
    return isApplication;
  }


  /**
   * Sets the isApplication attribute of the ObjectHookList object
   *
   * @param tmp The new isApplication value
   */
  public void setIsApplication(boolean tmp) {
    this.isApplication = tmp;
  }


  /**
   * Sets the isApplication attribute of the ObjectHookList object
   *
   * @param tmp The new isApplication value
   */
  public void setIsApplication(String tmp) {
    this.isApplication = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Loads Object Hooks from XML file
   *
   * @param xmlFile Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean buildList(File xmlFile) {
    try {
      XMLUtils xml = new XMLUtils(xmlFile);
      return parse(xml.getDocumentElement(), false);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param xmlFile       Description of the Parameter
   * @param isApplication Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean buildList(File xmlFile, boolean isApplication) {
    try {
      XMLUtils xml = new XMLUtils(xmlFile);
      return parse(xml.getDocumentElement(), isApplication);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }


  /**
   * Loads Object Hooks from XML Element
   *
   * @param element       Description of the Parameter
   * @param isApplication Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean parse(Element element, boolean isApplication) {
    if (element == null) {
      return false;
    }
    ObjectHookActionList actionList = null;
    try {
      //Process all hooks and the corresponding actions
      Element hooks = XMLUtils.getFirstElement(element, "hooks");
      if (hooks != null) {
        ArrayList hookNodes = XMLUtils.getElements(hooks, "hook");
        Iterator hookElements = hookNodes.iterator();
        while (hookElements.hasNext()) {
          Element hookElement = (Element) hookElements.next();
          String hookClass = (String) hookElement.getAttribute("class");
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ObjectHookList-> Added a hook: " + hookClass);
          }
          ObjectHookActionList oldActionList = (ObjectHookActionList) this.get(
              hookClass);
          if (oldActionList != null && oldActionList.size() > 0) {
            oldActionList.processHookElement(hookElement, isApplication);
            this.put(hookClass, oldActionList);
          } else {
            actionList = new ObjectHookActionList(hookElement, isApplication);
            this.put(hookClass, actionList);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   * Returns whether the given object has a defined hook
   *
   * @param object Description of the Parameter
   * @return Description of the Return
   *         Value
   * @throws java.lang.ClassNotFoundException
   *          Description of the Exception
   */
  public boolean has(Object object) throws java.lang.ClassNotFoundException {
    return (this.get(object.getClass().getName()) != null);
  }


  /**
   * Gets the actionsByProcess attribute of the ObjectHookList object
   *
   * @param processName Description of the Parameter
   * @return The actionsByProcess value
   */
  public ArrayList getActionsByProcess(String processName) {
    ArrayList actions = new ArrayList();
    Iterator actionLists = this.values().iterator();
    while (actionLists.hasNext()) {
      ObjectHookActionList thisList = (ObjectHookActionList) actionLists.next();
      Iterator actionList = thisList.values().iterator();
      while (actionList.hasNext()) {
        ObjectHookAction thisAction = (ObjectHookAction) actionList.next();
        if (processName.equals(thisAction.getProcessName())) {
          actions.add(thisAction);
        }
      }
    }
    return actions;
  }


  /**
   * Load object hooks from the database using specified property filters
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlSelect.append(
        "SELECT h.id, hl.hook_id, h.process_id, h.enabled, h.priority, " +
        "hl.hook_class, " +
        "t.action_type_id, " +
        "bp.process_name " +
        "FROM business_process_hook h, business_process_hook_library hl, " +
        "business_process_hook_triggers t, business_process bp " +
        "WHERE h.id > 0 " +
        "AND hl.hook_id = t.hook_id " +
        "AND h.trigger_id = t.trigger_id " +
        "AND h.process_id = bp.process_id ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY id ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ObjectHookAction thisAction = new ObjectHookAction(rs);
      this.addAction(thisAction);
    }
    rs.close();
    pst.close();
  }


  /**
   * Add database parameters from specified filters, used when building a list
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (linkModuleId > -1) {
      sqlFilter.append("AND hl.link_module_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND h.enabled = ? ");
    }
  }


  /**
   * Add database parameters based on selected filters, used when building a
   * list
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    return i;
  }


  /**
   * Adds a feature to the Action attribute of the ObjectHookList object
   *
   * @param action The feature to be added to the Action attribute
   */
  public void addAction(ObjectHookAction action) {
    ObjectHookActionList actionList = (ObjectHookActionList) this.get(
        action.getClassName());
    if (actionList == null) {
      actionList = new ObjectHookActionList();
      this.put(action.getClassName(), actionList);
    }
    if (action.getPriority() == -1) {
      action.setPriority(
          actionList.getNextPriority(
              action.getClassName(), action.getApplication(), action.getTypeId()));
    }
    actionList.put(
        new String(
            "" + action.getApplication() + "|" + action.getPriority() + "|" + action.getTypeId()), action);
  }


  /**
   * Adds a feature to the Action attribute of the ObjectHookList object
   *
   * @param action The feature to be added to the Action attribute
   * @param isApp  The feature to be added to the Action attribute
   */
  public void addAction(ObjectHookAction action, boolean isApp) {
    ObjectHookActionList actionList = (ObjectHookActionList) this.get(
        action.getClassName());
    if (actionList == null) {
      actionList = new ObjectHookActionList();
      this.put(action.getClassName(), actionList);
    }
    action.setApplication(isApp);
    if (action.getPriority() == -1) {
      action.setPriority(
          actionList.getNextPriority(
              action.getClassName(), isApp, action.getTypeId()));
    }
    actionList.put(
        new String(
            "" + action.getApplication() + "|" + action.getPriority() + "|" + action.getTypeId()), action);
  }


  /**
   * Inserts any loaded ObjectHooks into the database, with all related data
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      Iterator actions = this.values().iterator();
      while (actions.hasNext()) {
        ObjectHookActionList actionList = (ObjectHookActionList) actions.next();
        actionList.insert(db);
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   * Gets the sizeOfActions attribute of the ObjectHookList object
   *
   * @return The sizeOfActions value
   */
  public int getSizeOfActions() {
    int resultCount = 0;
    Iterator actionLists = this.values().iterator();
    while (actionLists.hasNext()) {
      ObjectHookActionList actionList = (ObjectHookActionList) actionLists.next();
      resultCount += actionList.values().size();
    }
    return resultCount;
  }
}

