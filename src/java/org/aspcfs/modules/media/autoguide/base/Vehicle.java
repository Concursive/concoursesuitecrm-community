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
package org.aspcfs.modules.media.autoguide.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ObjectUtils;

/**
 *  A year, make, and model determine a vehicle
 *
 *@author     matt
 *@created    May 17, 2002
 *@version    $Id$
 */
public class Vehicle {

  private int id = -1;
  private int year = -1;
  private int makeId = -1;
  private int modelId = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private Make make = null;
  private Model model = null;


  /**
   *  Constructor for the Vehicle object
   */
  public Vehicle() { }


  /**
   *  Constructor for the Vehicle object
   *
   *@param  year   Description of Parameter
   *@param  make   Description of Parameter
   *@param  model  Description of Parameter
   */
  public Vehicle(int year, int make, int model) {
    this.setYear(year);
    this.makeId = make;
    this.modelId = model;
  }


  /**
   *  Constructor for the Vehicle object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Vehicle(ResultSet rs) throws SQLException {
    buildRecord(rs);
    make = new Make(rs);
    model = new Model(rs);
  }


  /**
   *  Sets the id attribute of the Vehicle object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    id = tmp;
  }


  /**
   *  Sets the id attribute of the Vehicle object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the year attribute of the Vehicle object
   *
   *@param  inYear  The new year value
   */
  public void setYear(int inYear) {
    if (inYear < 100) {
      if (inYear < 20) {
        inYear += 2000;
      } else {
        inYear += 1900;
      }
    }
    this.year = inYear;
  }


  /**
   *  Sets the year attribute of the Vehicle object
   *
   *@param  tmp  The new year value
   */
  public void setYear(String tmp) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Vehicle-> setYear(" + tmp + ")");
    }
    this.setYear(Integer.parseInt(tmp));
  }


  /**
   *  Sets the makeId attribute of the Vehicle object
   *
   *@param  tmp  The new makeId value
   */
  public void setMakeId(int tmp) {
    this.makeId = tmp;
  }


  /**
   *  Sets the makeId attribute of the Vehicle object
   *
   *@param  tmp  The new makeId value
   */
  public void setMakeId(String tmp) {
    this.makeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modelId attribute of the Vehicle object
   *
   *@param  tmp  The new modelId value
   */
  public void setModelId(int tmp) {
    this.modelId = tmp;
  }


  /**
   *  Sets the modelId attribute of the Vehicle object
   *
   *@param  tmp  The new modelId value
   */
  public void setModelId(String tmp) {
    this.modelId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the Vehicle object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Vehicle object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Vehicle object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Vehicle object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Vehicle object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Vehicle object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Vehicle object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Vehicle object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the make attribute of the Vehicle object
   *
   *@param  tmp  The new make value
   */
  public void setMake(Make tmp) {
    this.make = tmp;
  }


  /**
   *  Sets the model attribute of the Vehicle object
   *
   *@param  tmp  The new model value
   */
  public void setModel(Model tmp) {
    this.model = tmp;
  }


  /**
   *  Gets the id attribute of the Vehicle object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the year attribute of the Vehicle object
   *
   *@return    The year value
   */
  public int getYear() {
    return year;
  }


  /**
   *  Gets the makeId attribute of the Vehicle object
   *
   *@return    The makeId value
   */
  public int getMakeId() {
    return makeId;
  }


  /**
   *  Gets the modelId attribute of the Vehicle object
   *
   *@return    The modelId value
   */
  public int getModelId() {
    return modelId;
  }


  /**
   *  Gets the entered attribute of the Vehicle object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Vehicle object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Vehicle object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Vehicle object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the guid attribute of the Vehicle object
   *
   *@return    The guid value
   */
  public String getGuid() {
    //return ObjectUtils.generateGuid(entered, enteredBy, id);
    return String.valueOf(id);
  }


  /**
   *  Gets the make attribute of the Vehicle object
   *
   *@return    The make value
   */
  public Make getMake() {
    if (make == null) {
      make = new Make();
    }
    return make;
  }


  /**
   *  Gets the model attribute of the Vehicle object
   *
   *@return    The model value
   */
  public Model getModel() {
    if (model == null) {
      model = new Model();
    }
    return model;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO autoguide_vehicle " +
        "(year, make_id, model_id, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, year);
    pst.setInt(++i, makeId);
    pst.setInt(++i, modelId);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "autoguide_vehicl_vehicle_id_seq");
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID was not specified");
    }

    PreparedStatement pst = null;
    //Delete related records (mappings)

    //Delete the record
    int recordCount = 0;
    pst = db.prepareStatement(
        "DELETE FROM autoguide_vehicle " +
        "WHERE vehicle_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();

    if (recordCount == 0) {
      //errors.put("actionError", "Record could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    if (id == -1) {
      throw new SQLException("Record ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE autoguide_vehicle " +
        "SET year = ?, make_id = ?, model_id = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE vehicle_id = ? " +
        "AND modified = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, year);
    pst.setInt(++i, makeId);
    pst.setInt(++i, modelId);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, id);
    pst.setTimestamp(++i, this.getModified());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int generateId(Connection db) throws SQLException {
    String sql =
        "SELECT vehicle_id " +
        "FROM autoguide_vehicle " +
        "WHERE year = ? " +
        "AND make_id = ? " +
        "AND model_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, year);
    pst.setInt(2, makeId);
    pst.setInt(3, modelId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      id = rs.getInt("vehicle_id");
    }
    rs.close();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Vehicle-> Looking up id for: year(" + year + ") make(" + makeId + ") model(" + modelId + ") = " + id);
    }
    return id;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("vehicle_id");
    year = rs.getInt("year");
    makeId = rs.getInt("vehicle_make_id");
    modelId = rs.getInt("vehicle_model_id");
    entered = rs.getTimestamp("vehicle_entered");
    enteredBy = rs.getInt("vehicle_enteredby");
    modified = rs.getTimestamp("vehicle_modified");
    modifiedBy = rs.getInt("vehicle_modifiedby");
  }
}

