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
 * Represents a key=value pair preference that will be used for a
 * BusinessProcess.
 *
 * @author matt rajkowski
 * @version $Id: ProcessParameter.java,v 1.2 2003/06/19 20:50:05 mrajkowski
 *          Exp $
 * @created June 6, 2003
 */
public class ProcessParameter {

  private int id = -1;
  private int processId = -1;
  private String name = null;
  private String value = null;
  private boolean enabled = true;


  /**
   * Constructor for the ProcessParameter object
   */
  public ProcessParameter() {
  }


  /**
   * Constructor for the ProcessParameter object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProcessParameter(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the ProcessParameter object
   *
   * @param parameterElement Description of the Parameter
   */
  public ProcessParameter(Element parameterElement) {
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
   * Sets the id attribute of the ProcessParameter object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ProcessParameter object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the processId attribute of the ProcessParameter object
   *
   * @param tmp The new processId value
   */
  public void setProcessId(int tmp) {
    this.processId = tmp;
  }


  /**
   * Sets the processId attribute of the ProcessParameter object
   *
   * @param tmp The new processId value
   */
  public void setProcessId(String tmp) {
    this.processId = Integer.parseInt(tmp);
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
   * Gets the id attribute of the ProcessParameter object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the processId attribute of the ProcessParameter object
   *
   * @return The processId value
   */
  public int getProcessId() {
    return processId;
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
   * Populates this object from a database record
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    processId = rs.getInt("process_id");
    name = rs.getString("param_name");
    value = rs.getString("param_value");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Inserts this object into a database
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "business_process_param_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO business_process_parameter " +
        "(" + (id > -1 ? "id, " : "") + "process_id, param_name, param_value, enabled) " +
        "VALUES " +
        "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, processId);
    pst.setString(++i, name);
    pst.setString(++i, value);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "business_process_param_id_seq", id);
  }


  /**
   * Deletes this object into a database
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM business_process_parameter " +
        "WHERE id = ? ");
    pst.setInt(1, this.id);
    pst.execute();

    pst.close();
  }

}

