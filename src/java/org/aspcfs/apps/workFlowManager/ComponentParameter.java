//Copyright 2002 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import java.sql.*;

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

  //Object properties
  private int id = -1;
  private int componentId = -1;
  private int parameterId = -1;
  private String value = null;
  private boolean enabled = true;
  //Reference properties
  private String name = null;


  /**
   *  Constructor for the ComponentParameter object
   */
  public ComponentParameter() { }


  /**
   *  Constructor for the ComponentParameter object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ComponentParameter(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ComponentParameter object
   *
   *@param  parameterElement  Description of the Parameter
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
   *  Sets the id attribute of the ComponentParameter object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ComponentParameter object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the componentId attribute of the ComponentParameter object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   *  Sets the componentId attribute of the ComponentParameter object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parameterId attribute of the ComponentParameter object
   *
   *@param  tmp  The new parameterId value
   */
  public void setParameterId(int tmp) {
    this.parameterId = tmp;
  }


  /**
   *  Sets the parameterId attribute of the ComponentParameter object
   *
   *@param  tmp  The new parameterId value
   */
  public void setParameterId(String tmp) {
    this.parameterId = Integer.parseInt(tmp);
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
   *  Gets the id attribute of the ComponentParameter object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the componentId attribute of the ComponentParameter object
   *
   *@return    The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   *  Gets the parameterId attribute of the ComponentParameter object
   *
   *@return    The parameterId value
   */
  public int getParameterId() {
    return parameterId;
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


  /**
   *  Populates this object from a database recordset
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO business_process_component_parameter " +
        "(component_id, parameter_id, param_value, enabled) VALUES " +
        "(?, ?, ?, ?) ");
    pst.setInt(1, componentId);
    pst.setInt(2, parameterId);
    pst.setString(3, value);
    pst.setBoolean(4, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "business_process_comp_pa_id_seq");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {

    System.out.println(" Deleting component parameter");
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM business_process_component_parameter " +
        "WHERE id = ? ");

    pst.setInt(1, this.id);
    pst.execute();
    pst.close();

  }

}

