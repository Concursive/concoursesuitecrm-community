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
package org.aspcfs.modules.reports.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a set of report parameters. Each parameter has specific display
 * capabilities and requirements according to a JasperReport.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 15, 2003
 */
public class Criteria extends GenericBean {
  //properties
  private int id = -1;
  private int reportId = -1;
  private int owner = -1;
  private String subject = null;
  private ParameterList parameters = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;
  //helper properties
  private boolean save = false;
  private boolean overwrite = false;


  /**
   * Constructor for the Criteria object
   */
  public Criteria() {
  }


  /**
   * Constructor for the Criteria object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Criteria(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Criteria object
   *
   * @param db         Description of the Parameter
   * @param criteriaId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Criteria(Connection db, int criteriaId) throws SQLException {
    queryRecord(db, criteriaId);
  }


  /**
   * Populates the report criteria information for the specified criteria id
   *
   * @param db         Description of the Parameter
   * @param criteriaId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int criteriaId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT c.* " +
            "FROM report_criteria c " +
            "WHERE criteria_id = ? ");
    pst.setInt(1, criteriaId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Criteria record not found.");
    }
  }


  /**
   * Loads additional information for this criteria item when needed.
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    parameters = new ParameterList();
    parameters.setCriteriaId(id);
    parameters.buildList(db);
  }


  /**
   * Populates this object from a given result set
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //report_criteria table
    id = rs.getInt("criteria_id");
    reportId = rs.getInt("report_id");
    owner = rs.getInt("owner");
    subject = rs.getString("subject");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Sets the id attribute of the Criteria object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Criteria object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the reportId attribute of the Criteria object
   *
   * @param tmp The new reportId value
   */
  public void setReportId(int tmp) {
    this.reportId = tmp;
  }


  /**
   * Sets the reportId attribute of the Criteria object
   *
   * @param tmp The new reportId value
   */
  public void setReportId(String tmp) {
    this.reportId = Integer.parseInt(tmp);
  }


  /**
   * Sets the owner attribute of the Criteria object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the owner attribute of the Criteria object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   * Sets the subject attribute of the Criteria object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Sets the parameters attribute of the Criteria object
   *
   * @param tmp The new parameters value
   */
  public void setParameters(ParameterList tmp) {
    this.parameters = tmp;
  }


  /**
   * Sets the entered attribute of the Criteria object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the Criteria object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the Criteria object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Criteria object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Criteria object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the Criteria object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Criteria object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Criteria object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the Criteria object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Criteria object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the save attribute of the Criteria object
   *
   * @param tmp The new save value
   */
  public void setSave(boolean tmp) {
    this.save = tmp;
  }


  /**
   * Sets the save attribute of the Criteria object
   *
   * @param tmp The new save value
   */
  public void setSave(String tmp) {
    this.save = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the overwrite attribute of the Criteria object
   *
   * @param tmp The new overwrite value
   */
  public void setOverwrite(boolean tmp) {
    this.overwrite = tmp;
  }


  /**
   * Sets the overwrite attribute of the Criteria object
   *
   * @param tmp The new overwrite value
   */
  public void setOverwrite(String tmp) {
    this.overwrite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the Criteria object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the reportId attribute of the Criteria object
   *
   * @return The reportId value
   */
  public int getReportId() {
    return reportId;
  }


  /**
   * Gets the owner attribute of the Criteria object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Gets the subject attribute of the Criteria object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the parameters attribute of the Criteria object
   *
   * @return The parameters value
   */
  public ParameterList getParameters() {
    if (parameters == null) {
      parameters = new ParameterList();
    }
    return parameters;
  }


  /**
   * Gets the entered attribute of the Criteria object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the Criteria object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the Criteria object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the Criteria object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the enabled attribute of the Criteria object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the save attribute of the Criteria object
   *
   * @return The save value
   */
  public boolean getSave() {
    return save;
  }


  /**
   * Gets the overwrite attribute of the Criteria object
   *
   * @return The overwrite value
   */
  public boolean getOverwrite() {
    return overwrite;
  }


  /**
   * Saves the criteria to the database, either updating previously saved
   * criteria or inserting new criteria depending on the overwrite and save
   * properties of this object.
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean save(Connection db) throws SQLException {
    if (overwrite) {
      update(db);
    } else if (save) {
      insert(db);
    }
    return true;
  }


  /**
   * Inserts this object into the database as a new record.
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "report_criteria_criteria_id_seq");
      sql.append(
          "INSERT INTO report_criteria " +
              "(report_id, owner, subject, enteredby, modifiedby, ");
      if (id > -1) {
        sql.append("criteria_id, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enabled) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, reportId);
      pst.setInt(++i, owner);
      pst.setString(++i, subject);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setBoolean(++i, enabled);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "report_criteria_criteria_id_seq", id);
      if (parameters != null) {
        parameters.setCriteriaId(id);
        parameters.insert(db);
      }
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Updates the database with this object for an existing set of criteria.
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean update(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE report_criteria " +
              "SET subject = ?, " +
              "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
              "WHERE criteria_id = ? ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, subject);
      pst.setInt(++i, id);
      pst.executeUpdate();
      pst.close();
      if (parameters != null) {
        parameters.setCriteriaId(id);
        parameters.update(db);
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }
}

