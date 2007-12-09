/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.media.autoguide.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a vehicle manufacturer
 *
 * @author matt
 * @version $Id$
 * @created May 17, 2002
 */
public class Make {
  private int id = -1;
  private String name = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;


  /**
   * Constructor for the Make object
   */
  public Make() {
  }


  /**
   * Constructor for the Make object
   *
   * @param tmp Description of Parameter
   */
  public Make(String tmp) {
    name = tmp;
  }


  /**
   * Constructor for the Make object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public Make(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Make object
   *
   * @param db         Description of Parameter
   * @param thisMakeId Description of Parameter
   * @throws SQLException Description of Exception
   */
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


  /**
   * Sets the id attribute of the Make object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Make object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the name attribute of the Make object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the entered attribute of the Make object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the Make object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the enteredBy attribute of the Make object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Make object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Make object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the Make object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Make object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Make object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the Make object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the name attribute of the Make object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the entered attribute of the Make object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the Make object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the Make object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the Make object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the guid attribute of the Make object
   *
   * @return The guid value
   */
  public String getGuid() {
    //return ObjectUtils.generateGuid(entered, enteredBy, id);
    return String.valueOf(id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean exists(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT make.make_id, make.make_name, " +
        "make.entered as make_entered, make.enteredby as make_enteredby, " +
        "make.modified as make_modified, make.modifiedby as make_modifiedby " +
        "FROM autoguide_make make " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(make_name) = ? ");
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
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    int i = 0;
    id = DatabaseUtils.getNextSeq(db, "autoguide_make_make_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO autoguide_make " +
        "(" + (id > -1 ? "make_id, " : "") + "make_name, enteredby, modifiedby) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?) ");
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, name);
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getEnteredBy());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "autoguide_make_make_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
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
   * Description of the Method
   *
   * @param db      Description of Parameter
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
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
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE make_id = ? " +
        "AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, id);
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("make_id");
    name = rs.getString("make_name");
    entered = rs.getTimestamp("make_entered");
    enteredBy = rs.getInt("make_enteredby");
    modified = rs.getTimestamp("make_modified");
    modifiedBy = rs.getInt("make_modifiedby");
  }

}

