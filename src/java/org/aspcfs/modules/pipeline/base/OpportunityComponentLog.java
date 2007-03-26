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
package org.aspcfs.modules.pipeline.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Srini
 * @created February 1, 2006
 */
public class OpportunityComponentLog extends GenericBean {
  protected int id = -1;
  protected int componentId = -1;
  protected int headerId = -1;
  protected String description = null;
  protected double closeProb = 0;
  protected java.sql.Timestamp closeDate = null;
  protected double terms = 0;
  protected String units = null;
  protected double low = 0;
  protected double guess = 0;
  protected double high = 0;
  protected int stage = -1;
  protected String stageName = null;
  protected int owner = -1;
  protected java.sql.Timestamp modified = null;
  protected java.sql.Timestamp entered = null;
  protected int enteredBy = -1;
  protected String closeDateTimeZone = null;
  protected java.sql.Timestamp closed = null;


  /**
   * Gets the id attribute of the OpportunityComponentLog object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the OpportunityComponentLog object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the OpportunityComponentLog object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the componentId attribute of the OpportunityComponentLog object
   *
   * @return The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   * Sets the componentId attribute of the OpportunityComponentLog object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   * Sets the componentId attribute of the OpportunityComponentLog object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   * Gets the headerId attribute of the OpportunityComponentLog object
   *
   * @return The headerId value
   */
  public int getHeaderId() {
    return headerId;
  }


  /**
   * Sets the headerId attribute of the OpportunityComponentLog object
   *
   * @param tmp The new headerId value
   */
  public void setHeaderId(int tmp) {
    this.headerId = tmp;
  }


  /**
   * Sets the headerId attribute of the OpportunityComponentLog object
   *
   * @param tmp The new headerId value
   */
  public void setHeaderId(String tmp) {
    this.headerId = Integer.parseInt(tmp);
  }


  /**
   * Gets the description attribute of the OpportunityComponentLog object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the OpportunityComponentLog object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the closeProb attribute of the OpportunityComponentLog object
   *
   * @return The closeProb value
   */
  public double getCloseProb() {
    return closeProb;
  }


  /**
   * Sets the closeProb attribute of the OpportunityComponentLog object
   *
   * @param tmp The new closeProb value
   */
  public void setCloseProb(double tmp) {
    this.closeProb = tmp;
  }


  /**
   * Sets the closeProb attribute of the OpportunityComponentLog object
   *
   * @param tmp The new closeProb value
   */
  public void setCloseProb(String tmp) {
    this.closeProb = Double.parseDouble(tmp);
  }


  /**
   * Gets the closeDate attribute of the OpportunityComponentLog object
   *
   * @return The closeDate value
   */
  public java.sql.Timestamp getCloseDate() {
    return closeDate;
  }


  /**
   * Sets the closeDate attribute of the OpportunityComponentLog object
   *
   * @param tmp The new closeDate value
   */
  public void setCloseDate(java.sql.Timestamp tmp) {
    this.closeDate = tmp;
  }


  /**
   * Sets the closeDate attribute of the OpportunityComponentLog object
   *
   * @param tmp The new closeDate value
   */
  public void setCloseDate(String tmp) {
    this.closeDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the terms attribute of the OpportunityComponentLog object
   *
   * @return The terms value
   */
  public double getTerms() {
    return terms;
  }


  /**
   * Sets the terms attribute of the OpportunityComponentLog object
   *
   * @param tmp The new terms value
   */
  public void setTerms(double tmp) {
    this.terms = tmp;
  }


  /**
   * Sets the terms attribute of the OpportunityComponentLog object
   *
   * @param tmp The new terms value
   */
  public void setTerms(String tmp) {
    this.terms = Double.parseDouble(tmp);
  }


  /**
   * Gets the units attribute of the OpportunityComponentLog object
   *
   * @return The units value
   */
  public String getUnits() {
    return units;
  }


  /**
   * Sets the units attribute of the OpportunityComponentLog object
   *
   * @param tmp The new units value
   */
  public void setUnits(String tmp) {
    this.units = tmp;
  }


  /**
   * Gets the low attribute of the OpportunityComponentLog object
   *
   * @return The low value
   */
  public double getLow() {
    return low;
  }


  /**
   * Sets the low attribute of the OpportunityComponentLog object
   *
   * @param tmp The new low value
   */
  public void setLow(double tmp) {
    this.low = tmp;
  }


  /**
   * Sets the low attribute of the OpportunityComponentLog object
   *
   * @param tmp The new low value
   */
  public void setLow(String tmp) {
    this.low = Double.parseDouble(tmp);
  }


  /**
   * Gets the guess attribute of the OpportunityComponentLog object
   *
   * @return The guess value
   */
  public double getGuess() {
    return guess;
  }


  /**
   * Sets the guess attribute of the OpportunityComponentLog object
   *
   * @param tmp The new guess value
   */
  public void setGuess(double tmp) {
    this.guess = tmp;
  }


  /**
   * Sets the guess attribute of the OpportunityComponentLog object
   *
   * @param tmp The new guess value
   */
  public void setGuess(String tmp) {
    this.guess = Double.parseDouble(tmp);
  }


  /**
   * Gets the high attribute of the OpportunityComponentLog object
   *
   * @return The high value
   */
  public double getHigh() {
    return high;
  }


  /**
   * Sets the high attribute of the OpportunityComponentLog object
   *
   * @param tmp The new high value
   */
  public void setHigh(double tmp) {
    this.high = tmp;
  }


  /**
   * Sets the high attribute of the OpportunityComponentLog object
   *
   * @param tmp The new high value
   */
  public void setHigh(String tmp) {
    this.high = Double.parseDouble(tmp);
  }


  /**
   * Gets the stage attribute of the OpportunityComponentLog object
   *
   * @return The stage value
   */
  public int getStage() {
    return stage;
  }


  /**
   * Sets the stage attribute of the OpportunityComponentLog object
   *
   * @param tmp The new stage value
   */
  public void setStage(int tmp) {
    this.stage = tmp;
  }


  /**
   * Sets the stage attribute of the OpportunityComponentLog object
   *
   * @param tmp The new stage value
   */
  public void setStage(String tmp) {
    this.stage = Integer.parseInt(tmp);
  }


  /**
   * Sets the stageName attribute of the OpportunityComponentLog object
   *
   * @param tmp The new stageName value
   */
  public void setStageName(String tmp) {
    this.stageName = tmp;
  }


  /**
   * Gets the owner attribute of the OpportunityComponentLog object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Sets the owner attribute of the OpportunityComponentLog object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the owner attribute of the OpportunityComponentLog object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   * Gets the entered attribute of the OpportunityComponentLog object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the entered attribute of the OpportunityComponentLog object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the OpportunityComponentLog object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the enteredBy attribute of the OpportunityComponentLog object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the OpportunityComponentLog object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the OpportunityComponentLog object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * @return the modified
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(java.sql.Timestamp modified) {
    this.modified = modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }

  /**
   * Gets the closeDateTimeZone attribute of the OpportunityComponentLog object
   *
   * @return The closeDateTimeZone value
   */
  public String getCloseDateTimeZone() {
    return closeDateTimeZone;
  }


  /**
   * Sets the closeDateTimeZone attribute of the OpportunityComponentLog object
   *
   * @param tmp The new closeDateTimeZone value
   */
  public void setCloseDateTimeZone(String tmp) {
    this.closeDateTimeZone = tmp;
  }


  /**
   * Gets the closed attribute of the OpportunityComponentLog object
   *
   * @return The closed value
   */
  public java.sql.Timestamp getClosed() {
    return closed;
  }


  /**
   * Sets the closed attribute of the OpportunityComponentLog object
   *
   * @param tmp The new closed value
   */
  public void setClosed(java.sql.Timestamp tmp) {
    this.closed = tmp;
  }


  /**
   * Sets the closed attribute of the OpportunityComponentLog object
   *
   * @param tmp The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Constructor for the OpportunityComponentLog object
   */
  public OpportunityComponentLog() {
  }


  /**
   * Constructor for the OpportunityComponent object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of Exception
   */
  public OpportunityComponentLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the OpportunityComponentLog object
   *
   * @param db Description of Parameter
   * @param id Description of Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of Exception
   */
  public OpportunityComponentLog(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the OpportunityComponentLog object
   *
   * @param db Description of Parameter
   * @param id Description of Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of Exception
   */
  public OpportunityComponentLog(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   * @param oppComp
   * @param db      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OpportunityComponentLog(Connection db, OpportunityComponent oppComp) throws SQLException {
    //Reload the opportunity to gets its current values
    OpportunityComponent component =
        new OpportunityComponent(db, oppComp.getId());

    this.headerId = component.getHeaderId();
    this.componentId = component.getId();
    this.description = component.getDescription();
    this.closeProb = component.getCloseProb();
    this.closeDate = component.getCloseDate();
    this.terms = component.getTerms();
    this.units = component.getUnits();
    this.low = component.getLow();
    this.guess = component.getGuess();
    this.high = component.getHigh();
    this.stage = component.getStage();
    this.owner = component.getOwner();
    this.closeDateTimeZone = component.getAlertDateTimeZone();
    this.enteredBy = component.getModifiedBy();
    this.entered = component.getModified();
    this.closed = DatabaseUtils.parseTimestamp(component.getClosed());
  }


  /**
   * Gets the stageName attribute of the OpportunityComponentLog object
   *
   * @return The stageName value
   */
  public String getStageName() {
    if (this.getClosed() != null) {
      this.setStageName("Closed");
    }

    return stageName;
  }


  /**
   * Gets the closeProbValue attribute of the Opportunity object
   *
   * @return The closeProbValue value
   */
  public String getCloseProbValue() {
    double value_2dp = (double) Math.round(closeProb * 100.0 * 100.0) / 100.0;
    String toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Opportunity Component Log ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT ocl.*, " +
            "y.description as stagename " +
            "FROM opportunity_component_log ocl " +
            "LEFT JOIN lookup_stage y ON (ocl.stage = y.code ) " +
            "WHERE id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   * Inserts this object into the database, and populates this Id.
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (this.getHeaderId() == -1) {
      throw new SQLException(
          "You must associate an opportunity component with an opportunity.");
    }
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "opportunity_component_log_id_seq");

      sql.append("INSERT INTO opportunity_component_log "
          + "(component_id, header_id, description, closeprob, closedate, "
          + " terms, units, lowvalue, guessvalue, highvalue, stage, owner, "
          + " closedate_timezone, ");
      if (id > -1) {
        sql.append("id, ");
      }
      if (closed != null) {
        sql.append("closed, ");
      }
      sql.append("modified, entered, enteredBy) ");

      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (closed != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }else{
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (entered != null) {
        sql.append("?, ");
      }else{
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("? ) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getComponentId());
      pst.setInt(++i, this.getHeaderId());
      pst.setString(++i, this.getDescription());
      pst.setDouble(++i, this.getCloseProb());
      pst.setTimestamp(++i, this.getCloseDate());
      pst.setDouble(++i, this.getTerms());
      pst.setString(++i, this.getUnits());
      pst.setDouble(++i, this.getLow());
      pst.setDouble(++i, this.getGuess());
      pst.setDouble(++i, this.getHigh());
      pst.setInt(++i, this.getStage());
      pst.setInt(++i, this.getOwner());
      pst.setString(++i, this.getCloseDateTimeZone());
      if (id > -1) {
        pst.setInt(++i, this.getId());
      }
      if (closed != null) {
        pst.setTimestamp(++i, closed);
      }
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setInt(++i, this.getEnteredBy());
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "opportunity_component_log_id_seq", id);

      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // opportunity component log table
    id = rs.getInt("id");
    componentId = rs.getInt("component_id");
    headerId = rs.getInt("header_id");
    description = rs.getString("description");
    closeProb = rs.getDouble("closeprob");
    closeDate = rs.getTimestamp("closedate");
    terms = rs.getDouble("terms");
    units = rs.getString("units");
    low = rs.getDouble("lowvalue");
    guess = rs.getDouble("guessvalue");
    high = rs.getDouble("highvalue");
    stage = rs.getInt("stage");
    stageName = rs.getString("stagename");
    owner = rs.getInt("owner");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    closeDateTimeZone = rs.getString("closedate_timezone");
    closed = rs.getTimestamp("closed");
    modified = rs.getTimestamp("modified");
  }
}

