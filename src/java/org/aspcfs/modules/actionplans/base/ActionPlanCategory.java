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
package org.aspcfs.modules.actionplans.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    September 6, 2005
 * @version    $Id: ActionPlanCategory.java,v 1.1.2.1 2005/09/09 19:16:16 partha
 *      Exp $
 */
public class ActionPlanCategory extends GenericBean {

  private int id = -1;
  private int categoryLevel = -1;
  private int parentCode = -1;
  private String description = "";
  private boolean enabled = true;
  private int level = -1;
  private TicketCategoryDraftList shortChildList = new TicketCategoryDraftList();
  private int siteId = -1;



  /**
   *  Constructor for the ActionPlanCategory object
   */
  public ActionPlanCategory() { }


  /**
   *  Constructor for the ActionPlanCategory object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionPlanCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionPlanCategory object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionPlanCategory(Connection db, int id) throws SQLException {
    if (id < 0) {
      throw new SQLException("Action Plan Category not specified");
    }
    String sql =
        "SELECT tc.* " +
        "FROM action_plan_category apc " +
        "WHERE apc.id > -1 " +
        "AND apc.id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Action Plan Category record not found.");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      id = DatabaseUtils.getNextSeq(db, "action_plan_category_id_seq");
      sql.append(
          "INSERT INTO action_plan_category " +
          "(" + (id > -1 ? "id, " : "") + "cat_level, parent_cat_code, description, " + DatabaseUtils.addQuotes(db, "level") +
          ", enabled, site_id) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getCategoryLevel());
      if (parentCode > 0) {
        pst.setInt(++i, this.getParentCode());
      } else {
        pst.setInt(++i, 0);
      }
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getLevel());
      pst.setBoolean(++i, this.getEnabled());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "action_plan_category_id_seq", id);
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
      throw new SQLException("Id not specified");
    }
    int i = 0;
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE action_plan_category " +
          "SET description = ?, cat_level = ?, parent_cat_code = ?, " +
          DatabaseUtils.addQuotes(db, "level") + " = ?, enabled = ? " +
          "WHERE  id = ? ");
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getCategoryLevel());
      pst.setInt(++i, this.getParentCode());
      pst.setInt(++i, this.getLevel());
      pst.setBoolean(++i, this.getEnabled());
      pst.setInt(++i, this.getId());
      count = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
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
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    categoryLevel = rs.getInt("cat_level");
    parentCode = rs.getInt("parent_cat_code");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    siteId = DatabaseUtils.getInt(rs, "site_id");
  }


  /*
   *  Get and Set Methods
   */
  /**
   *  Gets the id attribute of the ActionPlanCategory object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryLevel attribute of the ActionPlanCategory object
   *
   * @return    The categoryLevel value
   */
  public int getCategoryLevel() {
    return categoryLevel;
  }


  /**
   *  Sets the categoryLevel attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(int tmp) {
    this.categoryLevel = tmp;
  }


  /**
   *  Sets the categoryLevel attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(String tmp) {
    this.categoryLevel = Integer.parseInt(tmp);
  }


  /**
   *  Gets the parentCode attribute of the ActionPlanCategory object
   *
   * @return    The parentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   *  Sets the parentCode attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new parentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the parentCode attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new parentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Gets the description attribute of the ActionPlanCategory object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the enabled attribute of the ActionPlanCategory object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the level attribute of the ActionPlanCategory object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Gets the shortChildList attribute of the ActionPlanCategory object
   *
   * @return    The shortChildList value
   */
  public TicketCategoryDraftList getShortChildList() {
    return shortChildList;
  }


  /**
   *  Sets the shortChildList attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new shortChildList value
   */
  public void setShortChildList(TicketCategoryDraftList tmp) {
    this.shortChildList = tmp;
  }


  /**
   *  Gets the siteId attribute of the ActionPlanCategory object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the ActionPlanCategory object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }
}

