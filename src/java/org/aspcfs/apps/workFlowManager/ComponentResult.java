//Copyright 2003 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.sql.*;

/**
 *  A ComponentResult represents a valid return value of an executed component.
 *  Many components will return a Yes/No or True/False, but some may have a
 *  list.
 *
 *@author     matt rajkowski
 *@created    June 6, 2003
 *@version    $Id: ComponentResult.java,v 1.1.2.1 2003/06/11 18:34:18 mrajkowski
 *      Exp $
 */
public class ComponentResult {

  private int id = -1;
  private int componentId = -1;
  private int returnId = -1;
  private String description = null;
  private int level = -1;
  private boolean enabled = false;


  /**
   *  Constructor for the ComponentResult object
   */
  public ComponentResult() { }


  /**
   *  Constructor for the ComponentResult object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ComponentResult(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the ComponentResult object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ComponentResult object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the componentId attribute of the ComponentResult object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   *  Sets the componentId attribute of the ComponentResult object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the returnId attribute of the ComponentResult object
   *
   *@param  tmp  The new returnId value
   */
  public void setReturnId(int tmp) {
    this.returnId = tmp;
  }


  /**
   *  Sets the returnId attribute of the ComponentResult object
   *
   *@param  tmp  The new returnId value
   */
  public void setReturnId(String tmp) {
    this.returnId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the ComponentResult object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the level attribute of the ComponentResult object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the ComponentResult object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the ComponentResult object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ComponentResult object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the ComponentResult object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the componentId attribute of the ComponentResult object
   *
   *@return    The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   *  Gets the returnId attribute of the ComponentResult object
   *
   *@return    The returnId value
   */
  public int getReturnId() {
    return returnId;
  }


  /**
   *  Gets the description attribute of the ComponentResult object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the level attribute of the ComponentResult object
   *
   *@return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the enabled attribute of the ComponentResult object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Populates this object from a database recordset
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("result_id");
    componentId = rs.getInt("component_id");
    returnId = rs.getInt("return_id");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    PreparedStatement pst;
    pst = db.prepareStatement(
        "SELECT result_id " +
        "FROM business_process_component_result_lookup " +
        "WHERE component_id = ? " +
        "AND return_id = ?");
    pst.setInt(1, componentId);
    pst.setInt(2, returnId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      id = rs.getInt("result_id");
    }
    rs.close();
    pst.close();
    //Only insert if there is no duplicate
    if (id == -1) {
      pst = db.prepareStatement(
          "INSERT INTO business_process_component_result_lookup " +
          "(component_id, return_id, description, level, enabled) " +
          "VALUES " +
          "(?, ?, ?, ?, ?)");
      int i = 0;
      pst.setInt(++i, componentId);
      pst.setInt(++i, returnId);
      pst.setString(++i, description);
      pst.setInt(++i, level);
      pst.setBoolean(++i, enabled);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "business_process_comp_re_id_seq");
    }
  }
}

