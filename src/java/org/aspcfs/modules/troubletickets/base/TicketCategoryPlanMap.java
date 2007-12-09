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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    October 17, 2005
 * @version    $Id: TicketCategoryPlanMap.java,v 1.1.2.3 2005/11/14 21:15:37
 *      partha Exp $
 */
public class TicketCategoryPlanMap extends GenericBean {
  private int id = -1;
  private int categoryId = -1;
  private int planId = -1;
  private Timestamp entered = null;
  private Timestamp modified = null;


  /**
   *  Constructor for the TicketCategoryPlanMap object
   */
  public TicketCategoryPlanMap() { }


  /**
   *  Constructor for the TicketCategoryPlanMap object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryPlanMap(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TicketCategoryPlanMap object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryPlanMap(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id < 0) {
      throw new SQLException("Ticket Category Plan Map not specified");
    }
    String sql = new String(
        "SELECT tcpm.* " +
        "FROM ticket_category_plan_map tcpm " +
        "WHERE tcpm.map_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Ticket Category Plan Map record not found.");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("map_id");
    planId = rs.getInt("plan_id");
    categoryId = rs.getInt("category_id");
    setEntered(rs.getTimestamp("entered"));
    setModified(rs.getTimestamp("modified"));
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      id = DatabaseUtils.getNextSeq(db, "ticket_category_plan_map_map_id_seq");
      StringBuffer sql = new StringBuffer();
      
      sql.append(
          "INSERT INTO ticket_category_plan_map " +
          "(" + (id > -1 ? "map_id, " : "") + "category_id, plan_id");
      sql.append(", entered, modified");
      sql.append(") VALUES (" + (id > -1 ? "?, " : "") + "?, ?");
      if(this.getEntered() != null){
        sql.append(", ?");
      } else {
        sql.append(", " + DatabaseUtils.getCurrentTimestamp(db));
      }
      if(this.getModified() != null){
        sql.append(", ?");
      } else {
        sql.append(", " + DatabaseUtils.getCurrentTimestamp(db));
      }
      sql.append(") ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getCategoryId());
      pst.setInt(++i, this.getPlanId());
      if(this.getEntered() != null){
        DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
      }
      if(this.getModified() != null){
        DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
      }

      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "ticket_category_plan_map_map_id_seq", id);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Ticket Category Plan Map Id not specified");
    }
    int i = 0;
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE ticket_category_plan_map " +
          "SET category_id = ?, plan_id = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
          "WHERE  map_id = ? ");
      pst.setInt(++i, this.getCategoryId());
      pst.setInt(++i, this.getPlanId());
      pst.setInt(++i, this.getId());
      count = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Ticket Category Draft Plan Map Id not specified");
    }
    PreparedStatement pst = null;

    int recordCount = 0;

    pst = db.prepareStatement(
        "DELETE FROM ticket_category_plan_map " +
        "WHERE map_id = ? ");
    pst.setInt(1, id);
    pst.execute();
    pst.close();
    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  planMap  Description of the Parameter
   * @return          Description of the Return Value
   */
  public TicketCategoryPlanMap clonePlanMap(Connection db, TicketCategoryPlanMap planMap, int siteId) throws SQLException {
    ActionPlan plan = new ActionPlan(db, this.getPlanId());
    if (plan.getSiteId() == -1 || plan.getSiteId() == siteId) {
      planMap.setPlanId(this.getPlanId());
    } else {
      return (TicketCategoryPlanMap) null;
    }
    return planMap;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public TicketCategoryPlanMap clonePlanMap(Connection db, int siteId) throws SQLException {
    TicketCategoryPlanMap planMap = new TicketCategoryPlanMap();
    ActionPlan plan = new ActionPlan(db, this.getPlanId());
    if (plan.getSiteId() == -1 || plan.getSiteId() == siteId) {
      planMap.setPlanId(this.getPlanId());
    } else {
      return (TicketCategoryPlanMap) null;
    }
    return planMap;
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the TicketCategoryPlanMap object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the TicketCategoryPlanMap object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCategoryPlanMap object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryId attribute of the TicketCategoryPlanMap object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryPlanMap object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryPlanMap object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planId attribute of the TicketCategoryPlanMap object
   *
   * @return    The planId value
   */
  public int getPlanId() {
    return planId;
  }


  /**
   *  Sets the planId attribute of the TicketCategoryPlanMap object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(int tmp) {
    this.planId = tmp;
  }


  /**
   *  Sets the planId attribute of the TicketCategoryPlanMap object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(String tmp) {
    this.planId = Integer.parseInt(tmp);
  }

  /**
   * @return the entered
   */
  public Timestamp getEntered() {
    return entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(String entered) {
    this.entered = DatabaseUtils.parseTimestamp(entered);
  }

  /**
   * @return the modified
   */
  public Timestamp getModified() {
    return modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }
}

