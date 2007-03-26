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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Represents a category in which a Ticket is classified
 *
 * @author     chris
 * @created    December 11, 2001
 * @version    $Id: TicketCategory.java,v 1.8.12.1 2005/10/14 21:12:41
 *      mrajkowski Exp $
 */
public class TicketCategory extends GenericBean {

  private int id = -1;
  private int categoryLevel = -1;
  private int parentCode = -1;
  private String description = "";
  private boolean enabled = true;
  private int level = -1;
  private int siteId = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private TicketCategoryDraftList shortChildList = new TicketCategoryDraftList();


  /**
   *  Constructor for the TicketCategory object
   */
  public TicketCategory() { }


  /**
   *  Constructor for the TicketCategory object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public TicketCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TicketCategory object
   *
   * @param  db                Description of Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public TicketCategory(Connection db, int id) throws SQLException {
    if (id < 0) {
      throw new SQLException("Ticket Category not specified");
    }
    String sql =
        "SELECT tc.* " +
        "FROM ticket_category tc " +
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
      throw new SQLException("Ticket Category record not found.");
    }
  }


  /**
   *  Sets the Code attribute of the TicketCategory object
   *
   * @param  tmp  The new Code value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCategory object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryLevel attribute of the TicketCategory object
   *
   * @param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(int tmp) {
    this.categoryLevel = tmp;
  }


  /**
   *  Sets the categoryLevel attribute of the TicketCategory object
   *
   * @param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(String tmp) {
    this.categoryLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Level attribute of the TicketCategory object
   *
   * @param  level  The new Level value
   */
  public void setLevel(int level) {
    this.level = level;
  }


  /**
   *  Sets the Level attribute of the TicketCategory object
   *
   * @param  level  The new Level value
   */
  public void setLevel(String level) {
    this.level = Integer.parseInt(level);
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategory object
   *
   * @param  tmp  The new ParentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategory object
   *
   * @param  tmp  The new ParentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Description attribute of the TicketCategory object
   *
   * @param  tmp  The new Description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Enabled attribute of the TicketCategory object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the TicketCategory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the shortChildList attribute of the TicketCategory object
   *
   * @param  shortChildList  The new shortChildList value
   */
  public void setShortChildList(TicketCategoryDraftList shortChildList) {
    this.shortChildList = shortChildList;
  }


  /**
   *  Gets the shortChildList attribute of the TicketCategory object
   *
   * @return    The shortChildList value
   */
  public TicketCategoryDraftList getShortChildList() {
    return shortChildList;
  }


  /**
   *  Gets the Level attribute of the TicketCategory object
   *
   * @return    The Level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the Code attribute of the TicketCategory object
   *
   * @return    The Code value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the categoryLevel attribute of the TicketCategory object
   *
   * @return    The categoryLevel value
   */
  public int getCategoryLevel() {
    return categoryLevel;
  }


  /**
   *  Gets the ParentCode attribute of the TicketCategory object
   *
   * @return    The ParentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   *  Gets the Description attribute of the TicketCategory object
   *
   * @return    The Description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Enabled attribute of the TicketCategory object
   *
   * @return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the siteId attribute of the TicketCategory object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the TicketCategory object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }

  /**
   *  Sets the siteId attribute of the TicketCategory object
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
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      id = DatabaseUtils.getNextSeq(db, "ticket_category_id_seq");
      sql.append(
          "INSERT INTO ticket_category " +
          "(" + (id > -1 ? "id, " : "") + "cat_level, parent_cat_code, description, " + DatabaseUtils.addQuotes(db, "level") + ", enabled, site_id");
      sql.append(", entered, modified");
      sql.append(")VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?");
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
      if(this.getEntered() != null){
        DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
      }
      if(this.getModified() != null){
        DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
      }
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "ticket_category_id_seq", id);
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
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
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
          "UPDATE ticket_category " +
          "SET description = ?, cat_level = ?, parent_cat_code = ?, " + DatabaseUtils.addQuotes(db, "level") + " = ?, enabled = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
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
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    categoryLevel = rs.getInt("cat_level");
    parentCode = rs.getInt("parent_cat_code");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    siteId = DatabaseUtils.getInt(rs,"site_id");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    //delete the draft categories
    PreparedStatement pst = db.prepareStatement("DELETE from ticket_category_draft WHERE link_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    //delete the category
    pst = db.prepareStatement("DELETE FROM ticket_category WHERE id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public TicketCategory cloneCategory() {
    TicketCategory category = new TicketCategory();
    category.setCategoryLevel(this.getCategoryLevel());
    category.setDescription(this.getDescription());
    category.setEnabled(this.getEnabled());
    category.setLevel(this.getLevel());
    return category;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  description       Description of the Parameter
   * @param  siteId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int lookupCategory(Connection db, String description, int siteId) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT id FROM ticket_category WHERE site_id = ? AND description = ? AND cat_level = ? ");
    pst.setInt(1, siteId);
    pst.setString(2, description);
    pst.setInt(3, this.getCategoryLevel());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = DatabaseUtils.getInt(rs, "id");
    }
    pst.close();
    
    return result;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    return (this.getDescription()+"("+this.getId()+")");
  }
}

