//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;

public class Make {

  private int id = -1;
  private String name = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  
  public Make() { }

  public Make(String tmp) {
    name = tmp;
  }

  public Make(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public Make(Connection db, int thisMakeId) throws SQLException {
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT make.make_id, make.make_name, " +
      "make.entered as make_entered, make.enteredby as make_enteredby, " +
      "make.modified as make_modified, make.modifiedby as make_modifiedby " +
      "FROM autoguide_make make ");
    sql.append("WHERE make_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, thisMakeId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.buildRecord(rs);
    }
    rs.close();
    pst.close();
  }

  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setName(String tmp) { this.name = tmp; }
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
  
  public int getId() { return id; }
  public String getName() { return name; }
  public java.sql.Timestamp getEntered() { return entered; }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public int getModifiedBy() { return modifiedBy; }
  public String getGuid() {
    return ObjectUtils.generateGuid(entered, enteredBy, id);
  }

  public boolean exists(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "SELECT * " +
      "FROM autoguide_make " +
      "WHERE lower(make_name) = ? ");
    pst.setString(1, name.toLowerCase());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    return (id > -1);
  }

  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO autoguide_make " +
        "(make_name, enteredby, modifiedby) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getEnteredBy());
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "autoguide_make_make_id_seq");
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
        "DELETE FROM autoguide_make " +
        "WHERE make_id = ? ");
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
        "UPDATE autoguide_make " +
        "SET make_name = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE make_id = ? " +
        "AND modified = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, id);
    pst.setTimestamp(++i, this.getModified());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("make_id");
    name = rs.getString("make_name");
    entered = rs.getTimestamp("make_entered");
    enteredBy = rs.getInt("make_enteredby");
    modified = rs.getTimestamp("make_modified");
    modifiedBy = rs.getInt("make_modifiedby");
  }

}

