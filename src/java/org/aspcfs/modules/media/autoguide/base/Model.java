//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;

/**
 *  Represents a specific vehicle model for a given make
 *
 *@author     matt
 *@created    May 17, 2002
 *@version    $Id$
 */
public class Model {

  private int id = -1;
  private int makeId = -1;
  private String name = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private Make make = null;


  /**
   *  Constructor for the Model object
   */
  public Model() { }


  /**
   *  Constructor for the Model object
   *
   *@param  tmp  Description of Parameter
   */
  public Model(String tmp) {
    name = tmp;
  }


  /**
   *  Constructor for the Model object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Model(ResultSet rs) throws SQLException {
    buildRecord(rs);
    make = new Make(rs);
  }


  /**
   *  Sets the id attribute of the Model object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Model object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the makeId attribute of the Model object
   *
   *@param  tmp  The new makeId value
   */
  public void setMakeId(int tmp) {
    this.makeId = tmp;
  }


  /**
   *  Sets the makeId attribute of the Model object
   *
   *@param  tmp  The new makeId value
   */
  public void setMakeId(String tmp) {
    this.makeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Model object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the entered attribute of the Model object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Model object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Model object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Model object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Model object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Model object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Model object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Model object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the make attribute of the Model object
   *
   *@param  tmp  The new make value
   */
  public void setMake(Make tmp) {
    this.make = tmp;
  }


  /**
   *  Gets the id attribute of the Model object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the makeId attribute of the Model object
   *
   *@return    The makeId value
   */
  public int getMakeId() {
    return makeId;
  }


  /**
   *  Gets the name attribute of the Model object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the entered attribute of the Model object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Model object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Model object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Model object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the make attribute of the Model object
   *
   *@return    The make value
   */
  public Make getMake() {
    return make;
  }


  /**
   *  Gets the guid attribute of the Model object
   *
   *@return    The guid value
   */
  public String getGuid() {
    //return ObjectUtils.generateGuid(entered, enteredBy, id);
    return String.valueOf(id);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean exists(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT model.model_id, model.make_id AS model_make_id, model.model_name, " +
        "model.entered, model.enteredby, " +
        "model.modified, model.modifiedby " +
        "FROM autoguide_model model " +
        "WHERE lower(model_name) = ? ");
    pst.setString(1, name.toLowerCase());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    return (id > -1);
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
        "INSERT INTO autoguide_model " +
        "(make_id, model_name, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, makeId);
    pst.setString(++i, name);
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getEnteredBy());
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "autoguide_model_model_id_seq");
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
        "DELETE FROM autoguide_model " +
        "WHERE model_id = ? ");
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
    if (this.getId() == -1) {
      throw new SQLException("Record ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE autoguide_model " +
        "SET make_id = ?, model_name = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE model_id = ? " +
        "AND modified = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, makeId);
    pst.setString(++i, name);
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
   *@exception  SQLException  Description of Exception
   */
  public void buildResources(Connection db) throws SQLException {
    make = new Make(db, makeId);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("model_id");
    makeId = rs.getInt("model_make_id");
    name = rs.getString("model_name");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }

}

