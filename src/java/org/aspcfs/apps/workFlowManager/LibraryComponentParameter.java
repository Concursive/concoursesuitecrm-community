//Copyright 2003 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.sql.*;

/**
 *  A LibraryComponentParameter represents a valid parameter for a component,
 *  and contains the default value. When adding components to a business
 *  process, the default parameters can be selected from and the default value
 *  can be overridden.
 *
 *@author     matt rajkowski
 *@created    June 6, 2003
 *@version    $Id: LibraryComponentParameter.java,v 1.1.2.1 2003/06/11 18:37:45
 *      mrajkowski Exp $
 */
public class LibraryComponentParameter {

  private int id = -1;
  private int componentId = -1;
  private String name = null;
  private String description = null;
  private String defaultValue = null;
  private boolean enabled = false;


  /**
   *  Constructor for the LibraryComponent object
   */
  public LibraryComponentParameter() { }


  /**
   *  Constructor for the LibraryComponent object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public LibraryComponentParameter(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the componentId attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   *  Sets the componentId attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the defaultValue attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new defaultValue value
   */
  public void setDefaultValue(String tmp) {
    this.defaultValue = tmp;
  }


  /**
   *  Sets the enabled attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the LibraryComponentParameter object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the LibraryComponentParameter object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the componentId attribute of the LibraryComponentParameter object
   *
   *@return    The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   *  Gets the name attribute of the LibraryComponentParameter object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the LibraryComponentParameter object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the defaultValue attribute of the LibraryComponentParameter object
   *
   *@return    The defaultValue value
   */
  public String getDefaultValue() {
    return defaultValue;
  }


  /**
   *  Gets the enabled attribute of the LibraryComponentParameter object
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
    id = rs.getInt("component_id");
    componentId = rs.getInt("component_id");
    name = rs.getString("param_name");
    description = rs.getString("description");
    defaultValue = rs.getString("default_value");
    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO business_process_parameter_library " +
        "(component_id, param_name, description, default_value, enabled) VALUES " +
        "(?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, componentId);
    pst.setString(++i, name);
    pst.setString(++i, description);
    pst.setString(++i, defaultValue);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "business_process_pa_lib_id_seq");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM business_process_parameter_library " +
        "WHERE component_id = ? ");

    pst.setInt(1, this.componentId);
    pst.execute();

    pst.close();

  }
}

