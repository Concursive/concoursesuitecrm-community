//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;

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
  
  public Vehicle() { }

  public Vehicle(int year, int make, int model) {
    this.setYear(year);
    this.makeId = make;
    this.modelId = model;
  }

  public Vehicle(ResultSet rs) throws SQLException {
    buildRecord(rs);
    make = new Make(rs);
    model = new Model(rs);
  }

  public void setId(int tmp) { id = tmp; }
  public void setId(String tmp) { id = Integer.parseInt(tmp); }
  public void setYear(int inYear) { 
    if (inYear < 20) {
      inYear += 2000;
    } else {
      inYear += 1900;
    }
    this.year = inYear;
  }
  public void setYear(String tmp) { this.setYear(Integer.parseInt(tmp)); }
  public void setMakeId(int tmp) { this.makeId = tmp; }
  public void setMakeId(String tmp) { this.makeId = Integer.parseInt(tmp); }
  public void setModelId(int tmp) { this.modelId = tmp; }
  public void setModelId(String tmp) { this.modelId = Integer.parseInt(tmp); }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setEntered(String tmp) {
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setEnteredBy(String tmp) { this.enteredBy = Integer.parseInt(tmp); }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
  }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public void setModifiedBy(String tmp) { this.modifiedBy = Integer.parseInt(tmp); }
  public void setMake(Make tmp) { this.make = tmp; }
  public void setModel(Model tmp) { this.model = tmp; }

  public int getId() { return id; }
  public int getYear() { return year; }
  public int getMakeId() { return makeId; }
  public int getModelId() { return modelId; }
  public java.sql.Timestamp getEntered() { return entered; }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public int getModifiedBy() { return modifiedBy; }
  public String getGuid() {
    //return ObjectUtils.generateGuid(entered, enteredBy, id);
    return String.valueOf(id);
  }
  public Make getMake() { return make; }
  public Model getModel() { return model; }

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

