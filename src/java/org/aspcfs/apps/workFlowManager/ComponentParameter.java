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
package org.aspcfs.apps.workFlowManager;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a key=value pair preference that will be used for either a
 * BusinessProcess definition or a BusinessProcess component.
 *
 * @author matt rajkowski
 * @version $Id: ComponentParameter.java,v 1.3 2003/01/13 21:41:16 mrajkowski
 *          Exp $
 * @created November 11, 2002
 */
public class ComponentParameter {

  //Object properties
  private int id = -1;
  private int componentId = -1;
  private int parameterId = -1;
  private String value = null;
  private boolean enabled = true;
  //Reference properties
  private String name = null;


  /**
   * Constructor for the ComponentParameter object
   */
  public ComponentParameter() {
  }


  /**
   * Constructor for the ComponentParameter object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ComponentParameter(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the ComponentParameter object
   *
   * @param parameterElement Description of the Parameter
   */
  public ComponentParameter(Element parameterElement) {
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
   * Sets the id attribute of the ComponentParameter object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ComponentParameter object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the componentId attribute of the ComponentParameter object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   * Sets the componentId attribute of the ComponentParameter object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the parameterId attribute of the ComponentParameter object
   *
   * @param tmp The new parameterId value
   */
  public void setParameterId(int tmp) {
    this.parameterId = tmp;
  }


  /**
   * Sets the parameterId attribute of the ComponentParameter object
   *
   * @param tmp The new parameterId value
   */
  public void setParameterId(String tmp) {
    this.parameterId = Integer.parseInt(tmp);
  }


  /**
   * Sets the name attribute of the ComponentParameter object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the value attribute of the ComponentParameter object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   * Sets the enabled attribute of the ComponentParameter object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ComponentParameter object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = (DatabaseUtils.parseBoolean(tmp) || tmp == null || "".equals(
        tmp));
  }


  /**
   * Gets the id attribute of the ComponentParameter object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the componentId attribute of the ComponentParameter object
   *
   * @return The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   * Gets the parameterId attribute of the ComponentParameter object
   *
   * @return The parameterId value
   */
  public int getParameterId() {
    return parameterId;
  }


  /**
   * Gets the name attribute of the ComponentParameter object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the value attribute of the ComponentParameter object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }


  /**
   * Gets the enabled attribute of the ComponentParameter object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Populates this object from a database recordset
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //business_process_component_parameter
    id = rs.getInt("id");
    componentId = rs.getInt("component_id");
    parameterId = rs.getInt("parameter_id");
    value = rs.getString("param_value");
    enabled = rs.getBoolean("enabled");
    //business_process_parameter_library
    name = rs.getString("param_name");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    //Parameters must be registered in the library, no dupes per component
    if (parameterId == -1) {
      LibraryComponentParameter libraryParam = new LibraryComponentParameter();
      libraryParam.setComponentId(componentId);
      libraryParam.setName(name);
      libraryParam.setDescription(null);
      libraryParam.setDefaultValue(value);
      libraryParam.setEnabled(true);
      libraryParam.insert(db);
      parameterId = libraryParam.getId();
    }
    //Insert the parameter
    int i = 0;
    id = DatabaseUtils.getNextSeq(db, "business_process_comp_pa_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO " + DatabaseUtils.getTableName(db, "business_process_component_parameter") + " " +
        "(" + (id > -1 ? "id, " : "") + "component_id, parameter_id, param_value, enabled) VALUES " +
        "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?) ");
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, componentId);
    pst.setInt(++i, parameterId);
    pst.setString(++i, value);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "business_process_comp_pa_id_seq", id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {

    System.out.println(" Deleting component parameter");
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM " + DatabaseUtils.getTableName(db, "business_process_component_parameter") + " " +
        "WHERE id = ? ");

    pst.setInt(1, this.id);
    pst.execute();
    pst.close();

  }

}
