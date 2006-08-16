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
package org.aspcfs.modules.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Represents a group of CustomField records
 *
 * @author     matt rajkowski
 * @created    January 11, 2002
 * @version    $Id: CustomFieldGroup.java,v 1.4 2002/01/23 16:05:11 mrajkowski
 *      Exp $
 */
public class CustomFieldGroup extends ArrayList {
  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected boolean onlyWarnings = false;
  protected HashMap errors = new HashMap();
  protected HashMap warnings = new HashMap();

  //Properties for a Group
  private int id = -1;
  private int categoryId = -1;
  private String name = null;
  private String description = null;
  private int level = -1;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private boolean enabled = false;

  //Properties for building a list
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int recordId = -1;
  private int includeEnabled = -1;
  private int includeScheduled = -1;
  private boolean buildResources = false;


  /**
   *  Constructor for the CustomFieldGroup object
   */
  public CustomFieldGroup() { }


  /**
   *  Constructor for the CustomFieldGroup object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public CustomFieldGroup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the CustomFieldGroup object
   *
   * @param  db                Description of the Parameter
   * @param  thisId            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public CustomFieldGroup(Connection db, int thisId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM custom_field_group cfg " +
        "WHERE cfg.group_id = ? ");
    pst.setInt(1, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Sets the Id attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the GroupId attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new GroupId value
   */
  public void setGroupId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CategoryId attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new CategoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the CategoryId attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new CategoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Name attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new Name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Description attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new Description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Level attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new Level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the StartDate attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new StartDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the EndDate attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new EndDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Entered attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Enabled attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "false".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the LinkModuleId attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new LinkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the LinkItemId attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new LinkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the RecordId attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new RecordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   *  Sets the IncludeEnabled attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new IncludeEnabled value
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
  }


  /**
   *  Sets the IncludeScheduled attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new IncludeScheduled value
   */
  public void setIncludeScheduled(int tmp) {
    this.includeScheduled = tmp;
  }


  /**
   *  Sets the BuildResources attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new BuildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the Parameters attribute of the CustomFieldGroup object
   *
   * @param  context  The new Parameters value
   */
  public void setParameters(ActionContext context) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomField thisField = (CustomField) i.next();
      thisField.setParameters(context);
    }
  }


  /**
   *  Sets the warnings attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new warnings value
   */
  public void setWarnings(HashMap tmp) {
    this.warnings = tmp;
  }


  /**
   *  Gets the warnings attribute of the CustomFieldGroup object
   *
   * @return    The warnings value
   */
  public HashMap getWarnings() {
    return warnings;
  }


  /**
   *  Gets the Id attribute of the CustomFieldGroup object
   *
   * @return    The Id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the CategoryId attribute of the CustomFieldGroup object
   *
   * @return    The CategoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the Name attribute of the CustomFieldGroup object
   *
   * @return    The Name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the Description attribute of the CustomFieldGroup object
   *
   * @return    The Description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Level attribute of the CustomFieldGroup object
   *
   * @return    The Level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the StartDate attribute of the CustomFieldGroup object
   *
   * @return    The StartDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the EndDate attribute of the CustomFieldGroup object
   *
   * @return    The EndDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the Entered attribute of the CustomFieldGroup object
   *
   * @return    The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the CustomFieldGroup object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the Enabled attribute of the CustomFieldGroup object
   *
   * @return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the Errors attribute of the CustomFieldGroup object
   *
   * @return    The Errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   *  Gets the onlyWarnings attribute of the CustomFieldGroup object
   *
   * @return    The onlyWarnings value
   */
  public boolean getOnlyWarnings() {
    return onlyWarnings;
  }


  /**
   *  Sets the onlyWarnings attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new onlyWarnings value
   */
  public void setOnlyWarnings(boolean tmp) {
    this.onlyWarnings = tmp;
  }


  /**
   *  Sets the onlyWarnings attribute of the CustomFieldGroup object
   *
   * @param  tmp  The new onlyWarnings value
   */
  public void setOnlyWarnings(String tmp) {
    this.onlyWarnings = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the field attribute of the CustomFieldGroup object
   *
   * @param  tmp  Description of Parameter
   * @return      The field value
   */
  public CustomField getField(int tmp) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomField thisField = (CustomField) i.next();
      if (thisField.getId() == tmp) {
        return thisField;
      }
    }
    return new CustomField();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildResources(Connection db) throws SQLException {
    this.clear();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM custom_field_info cfi " +
        "WHERE cfi.group_id = " + id + " ");

    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", field_id, field_name ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      CustomField thisField = new CustomField(rs);
      thisField.setLinkModuleId(this.linkModuleId);
      thisField.setLinkItemId(linkItemId);
      thisField.setRecordId(recordId);
      this.add(thisField);
    }
    rs.close();
    pst.close();

    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomField thisField = (CustomField) i.next();
        thisField.buildResources(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int insert(Connection db) throws SQLException {
    int result = 1;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomField thisField = (CustomField) i.next();
      thisField.setRecordId(recordId);
      int fieldResult = thisField.insert(db);
      if (fieldResult != 1) {
        result = fieldResult;
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insertGroup(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    level = this.retrieveNextLevel(db);
    id = DatabaseUtils.getNextSeq(db, "custom_field_group_group_id_seq");
    sql.append("INSERT INTO custom_field_group ");
    sql.append("(category_id, group_name, ");
    if (id > -1) {
      sql.append("group_id, ");
    }
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("description, " + DatabaseUtils.addQuotes(db, "level") + " ) ");
    sql.append("VALUES (?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getCategoryId());
    pst.setString(++i, this.getName());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, level);
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "custom_field_group_group_id_seq", id);

    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean updateGroup(Connection db) throws SQLException {
    if (id == -1) {
      return false;
    }

    String sql =
        "UPDATE custom_field_group " +
        "SET group_name = ?, description = ? " +
        "WHERE category_id = ? " +
        "AND group_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, this.getCategoryId());
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int updateLevel(Connection db) throws SQLException {
    int result = 1;
    String sql =
        "UPDATE custom_field_group " +
        "SET " + DatabaseUtils.addQuotes(db, "level") + " = ? " +
        "WHERE group_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getLevel());
    pst.setInt(++i, this.getId());
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Deletes the CustomField Group, any associated fields, and any data stored
   *  in those fields.
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean deleteGroup(Connection db) throws SQLException {
    boolean result = false;
    if (id == -1) {
      return result;
    }
    try {
      db.setAutoCommit(false);
      String sql =
          "DELETE FROM custom_field_data " +
          "WHERE field_id IN (SELECT field_id FROM custom_field_info WHERE group_id = ?) ";
      PreparedStatement pst = db.prepareStatement(sql);
      int i = 0;
      pst.setInt(++i, id);
      pst.execute();
      pst.close();

      sql =
          "DELETE FROM custom_field_lookup " +
          "WHERE field_id IN (SELECT field_id FROM custom_field_info WHERE group_id = ?) ";
      pst = db.prepareStatement(sql);
      i = 0;
      pst.setInt(++i, id);
      pst.execute();
      pst.close();

      sql =
          "DELETE FROM custom_field_info " +
          "WHERE group_id = ? ";
      pst = db.prepareStatement(sql);
      i = 0;
      pst.setInt(++i, id);
      pst.execute();
      pst.close();

      sql =
          "DELETE FROM custom_field_group " +
          "WHERE group_id = ? ";
      pst = db.prepareStatement(sql);
      i = 0;
      pst.setInt(++i, id);
      pst.execute();
      pst.close();

      db.commit();
      result = true;
    } catch (SQLException e) {
      result = false;
      System.out.println(e.toString());
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   *  Deletes the CustomField Group, any associated fields... not the associated
   *  record data.
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    String sql =
        "DELETE FROM custom_field_lookup " +
        "WHERE field_id IN (SELECT field_id FROM custom_field_info " +
        "WHERE group_id IN (SELECT group_id FROM custom_field_group WHERE category_id = ?)) ";
    PreparedStatement pst = db.prepareStatement(sql);
    int i = 0;
    pst.setInt(++i, categoryId);
    pst.executeUpdate();
    pst.close();

    sql =
        "DELETE FROM custom_field_info " +
        "WHERE group_id IN (SELECT group_id FROM custom_field_group WHERE category_id = ?) ";
    pst = db.prepareStatement(sql);
    i = 0;
    pst.setInt(++i, categoryId);
    pst.executeUpdate();
    pst.close();

    sql =
        "DELETE FROM custom_field_group " +
        "WHERE category_id = ? ";
    pst = db.prepareStatement(sql);
    i = 0;
    pst.setInt(++i, categoryId);
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   * @since                 1.1
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    categoryId = rs.getInt("category_id");
    id = rs.getInt("group_id");
    name = rs.getString("group_name");
    level = rs.getInt("level");
    description = rs.getString("description");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    entered = rs.getTimestamp("entered");
    modified = entered;
    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (includeScheduled == TRUE) {
      sqlFilter.append(
          "AND CURRENT_TIMESTAMP > cfi.start_date AND (CURRENT_TIMESTAMP < cfi.end_date OR cfi.end_date IS NULL) ");
    } else if (includeScheduled == FALSE) {
      sqlFilter.append(
          "AND (CURRENT_TIMESTAMP < cfi.start_date OR (CURRENT_TIMESTAMP > cfi.end_date AND cfi.end_date IS NOT NULL)) ");
    }

    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND cfi.enabled = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (includeEnabled == TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabled == FALSE) {
      pst.setBoolean(++i, false);
    }

    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  private int retrieveNextLevel(Connection db) throws SQLException {
    int returnLevel = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT MAX(" + DatabaseUtils.addQuotes(db, "level") + ") as " + DatabaseUtils.addQuotes(db, "level") + " " +
        "FROM custom_field_group " +
        "WHERE category_id = ? ");
    pst.setInt(1, categoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      returnLevel = rs.getInt("level");
      if (rs.wasNull()) {
        returnLevel = 0;
      }
    }
    rs.close();
    pst.close();
    return ++returnLevel;
  }
}

