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

import java.util.*;
import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.sql.*;

/**
 *  A LibraryComponent is a categorized business process component that can be
 *  added to a business process
 *
 *@author     matt rajkowski
 *@created    June 6, 2003
 *@version    $Id: LibraryComponent.java,v 1.1.2.2 2003/06/12 20:50:35
 *      mrajkowski Exp $
 */
public class LibraryComponent {

  private int id = -1;
  private String name = null;
  private int typeId = -1;
  private String className = null;
  private String description = null;
  private boolean enabled = false;


  /**
   *  Constructor for the LibraryComponent object
   */
  public LibraryComponent() { }


  /**
   *  Constructor for the LibraryComponent object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public LibraryComponent(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the LibraryComponent object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the LibraryComponent object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the LibraryComponent object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the typeId attribute of the LibraryComponent object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the LibraryComponent object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the className attribute of the LibraryComponent object
   *
   *@param  tmp  The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
  }


  /**
   *  Sets the description attribute of the LibraryComponent object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the enabled attribute of the LibraryComponent object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the LibraryComponent object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the LibraryComponent object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the LibraryComponent object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the typeId attribute of the LibraryComponent object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the className attribute of the LibraryComponent object
   *
   *@return    The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   *  Gets the description attribute of the LibraryComponent object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the enabled attribute of the LibraryComponent object
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
    name = rs.getString("component_name");
    typeId = rs.getInt("type_id");
    className = rs.getString("class_name");
    description = rs.getString("description");
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
        "SELECT component_id " +
        "FROM business_process_component_library " +
        "WHERE component_name = ? " +
        "AND type_id = ?");
    pst.setString(1, name);
    pst.setInt(2, typeId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      id = rs.getInt("component_id");
    }
    rs.close();
    pst.close();
    //Only insert if there is no duplicate
    if (id == -1) {
      pst = db.prepareStatement(
          "INSERT INTO business_process_component_library " +
          "(component_name, type_id, class_name, description, enabled) " +
          "VALUES " +
          "(?, ?, ?, ?, ?)");
      int i = 0;
      pst.setString(++i, name);
      pst.setInt(++i, typeId);
      pst.setString(++i, className);
      pst.setString(++i, description);
      pst.setBoolean(++i, enabled);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "business_process_com_lb_id_seq");
    }
  }
}

