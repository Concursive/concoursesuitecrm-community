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
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *  Used for maintaining the work-in-progress state of a Category.<br />
 *  NOTE: This class is not dependent on tickets anymore and is used for working
 *  on drafts for Category objects.
 *
 * @author     akhi_m
 * @version    $Id$
 * @created    May 23, 2003
 */
public class TicketCategoryDraft extends GenericBean {

  private int id = -1;
  private int actualCatId = -1;
  private int categoryLevel = -1;
  private int parentCode = -1;
  private String description = "";
  private boolean enabled = true;
  private int level = 0;
  private int siteId = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private TicketCategoryDraftList shortChildList = new TicketCategoryDraftList();

  private String baseTableName = "ticket_category";


  /**
   *  Gets the baseTableName attribute of the TicketCategoryDraft object
   *
   * @return    The baseTableName value
   */
  public String getBaseTableName() {
    return baseTableName;
  }


  /**
   *  Sets the baseTableName attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new baseTableName value
   */
  public void setBaseTableName(String tmp) {
    this.baseTableName = tmp;
  }


  /**
   *  Constructor for the TicketCategoryDraft object
   */
  public TicketCategoryDraft() { }


  /**
   *  Constructor for the TicketCategoryDraft object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraft(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TicketCategoryDraft object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraft(Connection db, int id) throws SQLException {
    queryRecord(db, id, baseTableName);
  }


  /**
   *  Constructor for the TicketCategoryDraft object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @param  tableName         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraft(Connection db, int id, String tableName) throws SQLException {
    queryRecord(db, id, tableName);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @param  tableName         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id, String tableName) throws SQLException {
    if (id < 0) {
      throw new SQLException("Ticket Category not specified");
    }
    String sql =
        "SELECT tc.* " +
        "FROM " + DatabaseUtils.getTableName(db, tableName + "_draft") + " tc " +
        "WHERE tc.id > -1 " +
        "AND tc.id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Ticket Category Draft record not found.");
    }
  }


  /**
   *  Constructor for the TicketCategoryDraft object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @param  tableName         Description of the Parameter
   * @param  siteId            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraft(Connection db, int id, String tableName, int siteId) throws SQLException {
    if (id < 0) {
      throw new SQLException("Ticket Category not specified");
    }
    String sql =
        "SELECT tc.* " +
        "FROM " + DatabaseUtils.getTableName(db, tableName + "_draft") + " tc " +
        "WHERE tc.id > -1 " +
        "AND tc.id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Ticket Category Draft record not found.");
    }
  }


  /**
   *  Sets the Code attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new Code value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryLevel attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(int tmp) {
    this.categoryLevel = tmp;
  }


  /**
   *  Sets the categoryLevel attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(String tmp) {
    this.categoryLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Level attribute of the TicketCategoryDraft object
   *
   * @param  level  The new Level value
   */
  public void setLevel(int level) {
    this.level = level;
  }


  /**
   *  Sets the Level attribute of the TicketCategoryDraft object
   *
   * @param  level  The new Level value
   */
  public void setLevel(String level) {
    this.level = Integer.parseInt(level);
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new ParentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new ParentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Description attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new Description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Enabled attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the actualCatId attribute of the TicketCategoryDraft object
   *
   * @param  actualCatId  The new actualCatId value
   */
  public void setActualCatId(int actualCatId) {
    this.actualCatId = actualCatId;
  }


  /**
   *  Sets the actualCatId attribute of the TicketCategoryDraft object
   *
   * @param  actualCatId  The new actualCatId value
   */
  public void setActualCatId(String actualCatId) {
    this.actualCatId = Integer.parseInt(actualCatId);
  }


  /**
   *  Sets the shortChildList attribute of the TicketCategoryDraft object
   *
   * @param  shortChildList  The new shortChildList value
   */
  public void setShortChildList(TicketCategoryDraftList shortChildList) {
    this.shortChildList = shortChildList;
  }


  /**
   *  Gets the shortChildList attribute of the TicketCategoryDraft object
   *
   * @return    The shortChildList value
   */
  public TicketCategoryDraftList getShortChildList() {
    return shortChildList;
  }


  /**
   *  Gets the actualCatId attribute of the TicketCategoryDraft object
   *
   * @return    The actualCatId value
   */
  public int getActualCatId() {
    return actualCatId;
  }


  /**
   *  Gets the Level attribute of the TicketCategoryDraft object
   *
   * @return    The Level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the Code attribute of the TicketCategoryDraft object
   *
   * @return    The Code value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the categoryLevel attribute of the TicketCategoryDraft object
   *
   * @return    The categoryLevel value
   */
  public int getCategoryLevel() {
    return categoryLevel;
  }


  /**
   *  Gets the ParentCode attribute of the TicketCategoryDraft object
   *
   * @return    The ParentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   *  Gets the Description attribute of the TicketCategoryDraft object
   *
   * @return    The Description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Enabled attribute of the TicketCategoryDraft object
   *
   * @return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the siteId attribute of the TicketCategoryDraft object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the TicketCategoryDraft object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }

  /**
   * @return the entered
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(java.sql.Timestamp entered) {
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
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    return insert(db, baseTableName);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  baseTableName  Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insert(Connection db, String baseTableName) throws SQLException {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      if (baseTableName == null) {
        baseTableName = "ticket_category";
      }
      int i = 0;
      id = DatabaseUtils.getNextSeq(db, baseTableName + "_draft_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO " + DatabaseUtils.getTableName(db, baseTableName + "_draft") + " " +
          "(" + (id > -1 ? "id, " : "") + "cat_level, link_id, parent_cat_code, description, " + DatabaseUtils.addQuotes(db, "level") + ", enabled, site_id, entered, modified) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?" +
          (this.getEntered() != null?", ?":(", " + DatabaseUtils.getCurrentTimestamp(db))) +
          (this.getModified() != null?", ?":(", " + DatabaseUtils.getCurrentTimestamp(db))) +
          ") ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getCategoryLevel());
      DatabaseUtils.setInt(pst, ++i, this.getActualCatId());
      if (parentCode > 0) {
        pst.setInt(++i, this.getParentCode());
      } else {
        pst.setInt(++i, 0);
      }
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getLevel());
      pst.setBoolean(++i, this.getEnabled());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      if(this.getEntered() != null){
        DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
      }
      if(this.getModified() != null){
        DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
      }
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, baseTableName + "_draft_id_seq", id);
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
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tableName      Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int update(Connection db, String tableName) throws SQLException {
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    int i = 0;
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE " + tableName + "_draft " +
          "SET description = ?, cat_level = ?, " + DatabaseUtils.addQuotes(db, "level") + " = ?, " + (actualCatId != -1 ? "link_id = ?," : "") + " enabled = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
          "WHERE  id = ? ");
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getCategoryLevel());
      pst.setInt(++i, this.getLevel());
      if (actualCatId != -1) {
        DatabaseUtils.setInt(pst, ++i, this.getActualCatId());
      }
      pst.setBoolean(++i, this.getEnabled());
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
   * @param  db             Description of the Parameter
   * @param  tableName      Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String tableName) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Category Id not specified");
    }
    PreparedStatement pst = null;
    int recordCount = 0;
    if (tableName.equals("ticket_category")) {
      pst = db.prepareStatement(
          "DELETE FROM " + DatabaseUtils.getTableName(db, tableName + "_draft_plan_map") + " " +
          "WHERE category_id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " " +
          "WHERE category_id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();
    }

    pst = db.prepareStatement(
        "DELETE FROM " + DatabaseUtils.getTableName(db, tableName + "_draft") + " " +
        "WHERE id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
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
   * @param  childId  Description of the Parameter
   */
  public void removeChild(int childId) {
    TicketCategoryDraft child = null;
    Iterator i = shortChildList.iterator();
    while (i.hasNext()) {
      TicketCategoryDraft tmpCategory = (TicketCategoryDraft) i.next();
      if (tmpCategory.getId() == childId) {
        child = tmpCategory;
      }
    }
    if (child != null) {
      shortChildList.remove(child);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    actualCatId = rs.getInt("link_id");
    categoryLevel = rs.getInt("cat_level");
    parentCode = rs.getInt("parent_cat_code");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  planId         Description of the Parameter
   * @param  tableName      Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void insertPlan(Connection db, int planId, String tableName) throws SQLException {
    TicketCategoryDraftPlanMap draftPlanMap = null;
    int mapId = -1;
    if (tableName.equals("ticket_category")) {
      draftPlanMap = new TicketCategoryDraftPlanMap(db, this.getId(), planId);
      if (draftPlanMap.getId() != -1) {
        mapId = draftPlanMap.getId();
      }
      if (mapId == -1) {
        draftPlanMap = new TicketCategoryDraftPlanMap();
        draftPlanMap.setPlanId(planId);
        draftPlanMap.setCategoryId(this.getId());
        draftPlanMap.insert(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  fieldId        Description of the Parameter
   * @param  tableName      Description of the Parameter
   * @param  fieldName      Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void insertAssignment(Connection db, int fieldId, String tableName, String fieldName) throws SQLException {
    TicketCategoryDraftAssignment assignment = null;
    int mapId = -1;
    if (tableName.equals("ticket_category")) {
      assignment = new TicketCategoryDraftAssignment(db, this.getId(), (String) null);
      if (assignment.getId() != -1) {
        mapId = assignment.getId();
      }
      if (mapId == -1) {
        assignment = new TicketCategoryDraftAssignment();
        assignment.setCategoryId(this.getId());
        assignment.setFieldId(fieldId, fieldName);
        assignment.insert(db);
      } else {
        if (!assignment.checkField(fieldId, fieldName)) {
          assignment.update(db);
        }
      }
    }
  }


  /**
   *  Gets the planMapId attribute of the TicketCategoryDraft object
   *
   * @param  db             Description of the Parameter
   * @param  tableName      Description of the Parameter
   * @return                The planMapId value
   * @throws  SQLException  Description of the Exception
   */
  public int getPlanMapId(Connection db, String tableName) throws SQLException {
    int mapId = -1;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT map_id FROM " + DatabaseUtils.getTableName(db, tableName + "_draft_plan_map") + " " +
        "WHERE category_id = ? ");
    pst.setInt(1, this.getId());
    rs = pst.executeQuery();
    if (rs.next()) {
      mapId = rs.getInt("map_id");
    }
    rs.close();
    pst.close();
    return mapId;
  }


  /**
   *  Constructor for the TicketCategoryDraft object
   *
   * @param  db                Description of the Parameter
   * @param  tableName         Description of the Parameter
   * @param  linkId            Description of the Parameter
   * @param  buildOnLinkId     Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraft(Connection db, String tableName, int linkId, boolean buildOnLinkId) throws SQLException {
    if (linkId < 0) {
      throw new SQLException("Ticket Category not specified");
    }
    String sql =
        "SELECT tc.* " +
        "FROM " + DatabaseUtils.getTableName(db, tableName + "_draft") + " tc " +
        "WHERE tc.link_id > -1 " +
        "AND tc.link_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, linkId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Ticket Category Draft record not found.");
    }
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    return (this.getDescription());
  }
}

