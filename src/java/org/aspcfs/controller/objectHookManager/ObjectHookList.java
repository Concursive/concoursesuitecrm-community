package org.aspcfs.controller.objectHookManager;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.io.*;

/**
 *  Manages a list of hooks that are available for the given system
 *
 *@author     matt rajkowski
 *@created    October 14, 2002
 *@version    $Id: ObjectHookList.java,v 1.8 2003/01/13 21:41:16 mrajkowski Exp
 *      $
 */
public class ObjectHookList extends HashMap {
  private int enabled = Constants.UNDEFINED;
  private int linkModuleId = -1;


  /**
   *  Constructor for the ObjectHookList object
   */
  public ObjectHookList() { }


  /**
   *  Sets the enabled attribute of the ObjectHookList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ObjectHookList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkModuleId attribute of the ObjectHookList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the ObjectHookList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabled attribute of the ObjectHookList object
   *
   *@return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the linkModuleId attribute of the ObjectHookList object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Loads Object Hooks from XML file
   *
   *@param  xmlFile  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean buildList(File xmlFile) {
    try {
      XMLUtils xml = new XMLUtils(xmlFile);
      return parse(xml.getDocumentElement());
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }


  /**
   *  Loads Object Hooks from XML Element
   *
   *@param  element  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean parse(Element element) {
    if (element == null) {
      return false;
    }
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
          ObjectHookActionList actionList = new ObjectHookActionList(hookElement);
          this.put(hookClass, actionList);
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   *  Returns whether the given object has a defined hook
   *
   *@param  object                                Description of the Parameter
   *@return                                       Description of the Return
   *      Value
   *@exception  java.lang.ClassNotFoundException  Description of the Exception
   */
  public boolean has(Object object) throws java.lang.ClassNotFoundException {
    return (this.get(object.getClass().getName()) != null);
  }


  /**
   *  Gets the actionsByProcess attribute of the ObjectHookList object
   *
   *@param  processName  Description of the Parameter
   *@return              The actionsByProcess value
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
   *  Load object hooks from the database using specified property filters
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlSelect.append(
        "SELECT h.id, hl.hook_id, h.process_id, h.enabled, " +
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
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
   *  Add database parameters from specified filters, used when building a list
   *
   *@param  sqlFilter  Description of the Parameter
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
   *  Add database parameters based on selected filters, used when building a
   *  list
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *  Adds a feature to the Action attribute of the ObjectHookList object
   *
   *@param  action  The feature to be added to the Action attribute
   */
  public void addAction(ObjectHookAction action) {
    ObjectHookActionList actionList = (ObjectHookActionList) this.get(action.getClassName());
    if (actionList == null) {
      actionList = new ObjectHookActionList();
      this.put(action.getClassName(), actionList);
    }
    actionList.put(new Integer(action.getTypeId()), action);
  }


  /**
   *  Inserts any loaded ObjectHooks into the database, with all related data
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
}

