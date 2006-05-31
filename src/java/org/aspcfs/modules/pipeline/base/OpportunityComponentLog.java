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
 *  Description of the Class
 *
 *@author     Srini
 *@version
 *@created    February 1, 2006
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
  protected java.sql.Timestamp entered = null;
  protected int enteredBy = -1;
  protected String closeDateTimeZone = null;
  protected java.sql.Timestamp closed = null;


  /**
   *  Gets the closed attribute of the OpportunityComponentLog object
   *
   *@return    The closed value
   */
  public java.sql.Timestamp getClosed() {
    return closed;
  }


  /**
   *  Sets the closed attribute of the OpportunityComponentLog object
   *
   *@param  tmp  The new closed value
   */
  public void setClosed(java.sql.Timestamp tmp) {
    this.closed = tmp;
  }


  /**
   *  Sets the closed attribute of the OpportunityComponentLog object
   *
   *@param  tmp  The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DatabaseUtils.parseTimestamp(tmp);
  }




  /**
   *  Constructor for the OpportunityComponentLog object
   */
  public OpportunityComponentLog() { }


  /**
   *  Constructor for the OpportunityComponent object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   *@throws  SQLException     Description of Exception
   */
  public OpportunityComponentLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the OpportunityComponentLog object
   *
   *@param  db                Description of Parameter
   *@param  id                Description of Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   *@throws  SQLException     Description of Exception
   */
  public OpportunityComponentLog(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the OpportunityComponentLog object
   *
   *@param  db                Description of Parameter
   *@param  id                Description of Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   *@throws  SQLException     Description of Exception
   */
  public OpportunityComponentLog(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   *@param  oppComp
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *@return    Returns the closeDate.
   */
  public java.sql.Timestamp getCloseDate() {
    return closeDate;
  }


  /**
   *@param  closeDate  The closeDate to set.
   */
  public void setCloseDate(java.sql.Timestamp closeDate) {
    this.closeDate = closeDate;
  }


  /**
   *@return    Returns the closeDateTimeZone.
   */
  public String getCloseDateTimeZone() {
    return closeDateTimeZone;
  }


  /**
   *@param  closeDateTimeZone  The closeDateTimeZone to set.
   */
  public void setCloseDateTimeZone(String closeDateTimeZone) {
    this.closeDateTimeZone = closeDateTimeZone;
  }


  /**
   *@return    Returns the closeProb.
   */
  public double getCloseProb() {
    return closeProb;
  }


  /**
   *@param  closeProb  The closeProb to set.
   */
  public void setCloseProb(double closeProb) {
    this.closeProb = closeProb;
  }


  /**
   *@return    Returns the componentId.
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   *@param  componentId  The componentId to set.
   */
  public void setComponentId(int componentId) {
    this.componentId = componentId;
  }


  /**
   *@return    Returns the description.
   */
  public String getDescription() {
    return description;
  }


  /**
   *@param  description  The description to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   *@return    Returns the entered.
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *@param  entered  The entered to set.
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   *@return    Returns the enteredBy.
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *@param  enteredBy  The enteredBy to set.
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   *@return    Returns the guess.
   */
  public double getGuess() {
    return guess;
  }


  /**
   *@param  guess  The guess to set.
   */
  public void setGuess(double guess) {
    this.guess = guess;
  }


  /**
   *@return    Returns the headerId.
   */
  public int getHeaderId() {
    return headerId;
  }


  /**
   *@param  headerId  The headerId to set.
   */
  public void setHeaderId(int headerId) {
    this.headerId = headerId;
  }


  /**
   *@return    Returns the high.
   */
  public double getHigh() {
    return high;
  }


  /**
   *@param  high  The high to set.
   */
  public void setHigh(double high) {
    this.high = high;
  }


  /**
   *@return    Returns the id.
   */
  public int getId() {
    return id;
  }


  /**
   *@param  id  The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *@return    Returns the low.
   */
  public double getLow() {
    return low;
  }


  /**
   *@param  low  The low to set.
   */
  public void setLow(double low) {
    this.low = low;
  }


  /**
   *@return    Returns the owner.
   */
  public int getOwner() {
    return owner;
  }


  /**
   *@param  owner  The owner to set.
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *@return    Returns the stage.
   */
  public int getStage() {
    return stage;
  }


  /**
   *@param  stage  The stage to set.
   */
  public void setStage(int stage) {
    this.stage = stage;
  }


  /**
   *  Gets the stageName attribute of the OpportunityComponentLog object
   *
   *@return    The stageName value
   */
  public String getStageName() {
    if (this.getClosed() != null) {
      this.setStageName("Closed");
    }

    return stageName;
  }


  /**
   *  Sets the stageName attribute of the OpportunityComponentLog object
   *
   *@param  stageName  The new stageName value
   */
  public void setStageName(String stageName) {
    this.stageName = stageName;
  }


  /**
   *@return    Returns the terms.
   */
  public double getTerms() {
    return terms;
  }


  /**
   *@param  terms  The terms to set.
   */
  public void setTerms(double terms) {
    this.terms = terms;
  }


  /**
   *  Gets the units attribute of the OpportunityComponentLog object
   *
   *@return    The units value
   */
  public String getUnits() {
    return units;
  }


  /**
   *  Sets the units attribute of the OpportunityComponentLog object
   */
  public void setUnits() {
    this.units = units;
  }


  /**
   *  Gets the closeProbValue attribute of the Opportunity object
   *
   *@return    The closeProbValue value
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *  Inserts this object into the database, and populates this Id.
   *
   *@param  db             Description of Parameter
   *@return                Description of the Returned Value
   *@throws  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (this.getHeaderId() == -1) {
      throw new SQLException(
          "You must associate an opportunity component with an opportunity.");
    }
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
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
      if (entered != null) {
        sql.append("entered, ");
      }
      sql.append("enteredBy ) ");

      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (closed != null) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
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
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
  }
}

