package org.aspcfs.controller.objectHookManager;

import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import java.sql.*;
import org.aspcfs.modules.admin.base.PermissionCategory;

/**
 *  When a hook is triggered, the ObjectHookManager must check to see if a
 *  correspending mapping to the action that triggered the event occurs.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ObjectHookAction.java,v 1.2 2002/11/14 13:32:16 mrajkowski
 *      Exp $
 */
public class ObjectHookAction {
  //Action types
  public final static int UNDEFINED = -1;
  public final static int INSERT = 1;
  public final static int UPDATE = 2;
  public final static int DELETE = 3;

  //Base properties
  private int id = -1;
  private int hookId = -1;
  private int triggerId = -1;
  private int processId = -1;
  private int linkModuleId = -1;
  private int typeId = -1;
  private boolean enabled = true;
  //References
  private String className = null;
  private String processName = null;
  //Key for XML Import
  private String linkModule = null;


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
   *  Constructor for the ObjectHookAction object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ObjectHookAction(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the ObjectHookAction object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ObjectHookAction object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the hookId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new hookId value
   */
  public void setHookId(int tmp) {
    this.hookId = tmp;
  }


  /**
   *  Sets the hookId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new hookId value
   */
  public void setHookId(String tmp) {
    this.hookId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the triggerId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new triggerId value
   */
  public void setTriggerId(int tmp) {
    this.triggerId = tmp;
  }


  /**
   *  Sets the triggerId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new triggerId value
   */
  public void setTriggerId(String tmp) {
    this.triggerId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the processId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new processId value
   */
  public void setProcessId(int tmp) {
    this.processId = tmp;
  }


  /**
   *  Sets the processId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new processId value
   */
  public void setProcessId(String tmp) {
    this.processId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkModuleId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the ObjectHookAction object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the className attribute of the ObjectHookAction object
   *
   *@param  tmp  The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
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
   *  Sets the linkModule attribute of the ObjectHookAction object
   *
   *@param  tmp  The new linkModule value
   */
  public void setLinkModule(String tmp) {
    this.linkModule = tmp;
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
    if (tmp == null || "".equals(tmp)) {
      this.enabled = true;
    } else {
      this.enabled = DatabaseUtils.parseBoolean(tmp);
    }
  }


  /**
   *  Gets the id attribute of the ObjectHookAction object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the hookId attribute of the ObjectHookAction object
   *
   *@return    The hookId value
   */
  public int getHookId() {
    return hookId;
  }


  /**
   *  Gets the triggerId attribute of the ObjectHookAction object
   *
   *@return    The triggerId value
   */
  public int getTriggerId() {
    return triggerId;
  }


  /**
   *  Gets the processId attribute of the ObjectHookAction object
   *
   *@return    The processId value
   */
  public int getProcessId() {
    return processId;
  }


  /**
   *  Gets the linkModuleId attribute of the ObjectHookAction object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the className attribute of the ObjectHookAction object
   *
   *@return    The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   *  Gets the baseClassName attribute of the ObjectHookAction object, just the
   *  class name without the package
   *
   *@return    The baseClassName value
   */
  public String getBaseClassName() {
    return className.substring(className.lastIndexOf(".") + 1);
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


  /**
   *  Gets the typeText attribute of the ObjectHookAction object
   *
   *@return    The typeText value
   */
  public String getTypeText() {
    switch (typeId) {
        case (INSERT):
          return "Insert";
        case (UPDATE):
          return "Update";
        case (DELETE):
          return "Delete";
        default:
          return "Undefined";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //business_process_hook
    id = rs.getInt("id");
    hookId = rs.getInt("hook_id");
    processId = rs.getInt("process_id");
    enabled = rs.getBoolean("enabled");
    //business_process_hook_library
    className = rs.getString("hook_class");
    //business_process_hook_trigger
    typeId = rs.getInt("action_type_id");
    //business_process
    processName = rs.getString("process_name");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    boolean autoCommit = db.getAutoCommit();
    try {
      if (autoCommit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst;
      ResultSet rs;
      //Get a linkModuleId
      if (linkModuleId == -1 && linkModule != null && !linkModule.equals("")) {
        linkModuleId = PermissionCategory.lookupId(db, linkModule);
      }
      //Get a hook_id
      if (hookId == -1) {
        pst = db.prepareStatement(
            "SELECT hook_id " +
            "FROM business_process_hook_library " +
            "WHERE link_module_id = ? " +
            "AND hook_class = ? ");
        pst.setInt(1, linkModuleId);
        pst.setString(2, className);
        rs = pst.executeQuery();
        if (rs.next()) {
          hookId = rs.getInt("hook_id");
        }
        rs.close();
        pst.close();
        if (hookId == -1) {
          //Insert new hook library item
          pst = db.prepareStatement(
              "INSERT INTO business_process_hook_library " +
              "(link_module_id, hook_class, enabled) VALUES " +
              "(?, ?, ?)");
          pst.setInt(1, linkModuleId);
          pst.setString(2, className);
          pst.setBoolean(3, true);
          pst.execute();
          pst.close();
          hookId = DatabaseUtils.getCurrVal(db, "business_process_hl_hook_id_seq");
        }
      }
      //Get the triggerId
      if (triggerId == -1) {
        pst = db.prepareStatement(
            "SELECT trigger_id " +
            "FROM business_process_hook_triggers " +
            "WHERE action_type_id = ? " +
            "AND hook_id = ? ");
        pst.setInt(1, typeId);
        pst.setInt(2, hookId);
        rs = pst.executeQuery();
        if (rs.next()) {
          triggerId = rs.getInt("trigger_id");
        }
        rs.close();
        pst.close();
        if (triggerId == -1) {
          //Insert the action_type_id into the trigger list
          pst = db.prepareStatement(
              "INSERT INTO business_process_hook_triggers " +
              "(action_type_id, hook_id, enabled) VALUES " +
              "(?, ?, ?)");
          pst.setInt(1, typeId);
          pst.setInt(2, hookId);
          pst.setBoolean(3, true);
          pst.execute();
          pst.close();
          triggerId = DatabaseUtils.getCurrVal(db, "business_process_ho_trig_id_seq");
        }
      }
      if (processId == -1 && processName != null) {
        pst = db.prepareStatement(
            "SELECT process_id " +
            "FROM business_process " +
            "WHERE process_name = ?");
        pst.setString(1, processName);
        rs = pst.executeQuery();
        if (rs.next()) {
          processId = rs.getInt("process_id");
        }
        rs.close();
        pst.close();
      }
      //Insert the hook with the corresponding references
      pst = db.prepareStatement(
          "INSERT INTO business_process_hook " +
          "(trigger_id, process_id, enabled) VALUES " +
          "(?, ?, ?)");
      pst.setInt(1, triggerId);
      pst.setInt(2, processId);
      pst.setBoolean(3, enabled);
      pst.execute();
      id = DatabaseUtils.getCurrVal(db, "business_process_ho_hook_id_seq");
      if (autoCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (autoCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (autoCommit) {
        db.setAutoCommit(true);
      }
    }
  }
}

