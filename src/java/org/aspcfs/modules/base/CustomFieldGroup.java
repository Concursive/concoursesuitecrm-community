//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.actions.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.utils.*;

/**
 *  Represents a group of CustomField records
 *
 *@author     matt rajkowski
 *@created    January 11, 2002
 *@version    $Id: CustomFieldGroup.java,v 1.4 2002/01/23 16:05:11 mrajkowski
 *      Exp $
 */
public class CustomFieldGroup extends ArrayList {
  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected HashMap errors = new HashMap();

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
   *
   *@since
   */
  public CustomFieldGroup() { }


  /**
   *  Constructor for the CustomFieldGroup object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public CustomFieldGroup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
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
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }
  
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the GroupId attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new GroupId value
   *@since
   */
  public void setGroupId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CategoryId attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new CategoryId value
   *@since
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the CategoryId attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new CategoryId value
   *@since
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Name attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new Name value
   *@since
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Description attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new Description value
   *@since
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Level attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new Level value
   *@since
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }
  
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the StartDate attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new StartDate value
   *@since
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }
   
  public void setStartDate(String tmp) {
    this.startDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the EndDate attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new EndDate value
   *@since
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }
  
  public void setEndDate(String tmp) {
    this.endDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Entered attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }
  
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }
  
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }
  
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Enabled attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }
  
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "false".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the LinkModuleId attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new LinkModuleId value
   *@since
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the LinkItemId attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new LinkItemId value
   *@since
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the RecordId attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new RecordId value
   *@since
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   *  Sets the IncludeEnabled attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new IncludeEnabled value
   *@since
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
  }


  /**
   *  Sets the IncludeScheduled attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new IncludeScheduled value
   *@since
   */
  public void setIncludeScheduled(int tmp) {
    this.includeScheduled = tmp;
  }


  /**
   *  Sets the BuildResources attribute of the CustomFieldGroup object
   *
   *@param  tmp  The new BuildResources value
   *@since
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the Parameters attribute of the CustomFieldGroup object
   *
   *@param  context  The new Parameters value
   *@since
   */
  public void setParameters(ActionContext context) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomField thisField = (CustomField) i.next();
      thisField.setParameters(context);
    }
  }
  

  /**
   *  Gets the Id attribute of the CustomFieldGroup object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the CategoryId attribute of the CustomFieldGroup object
   *
   *@return    The CategoryId value
   *@since
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the Name attribute of the CustomFieldGroup object
   *
   *@return    The Name value
   *@since
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the Description attribute of the CustomFieldGroup object
   *
   *@return    The Description value
   *@since
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Level attribute of the CustomFieldGroup object
   *
   *@return    The Level value
   *@since
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the StartDate attribute of the CustomFieldGroup object
   *
   *@return    The StartDate value
   *@since
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the EndDate attribute of the CustomFieldGroup object
   *
   *@return    The EndDate value
   *@since
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the Entered attribute of the CustomFieldGroup object
   *
   *@return    The Entered value
   *@since
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }
  
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the Enabled attribute of the CustomFieldGroup object
   *
   *@return    The Enabled value
   *@since
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the Errors attribute of the CustomFieldGroup object
   *
   *@return    The Errors value
   *@since
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   *  Gets the field attribute of the CustomFieldGroup object
   *
   *@param  tmp  Description of Parameter
   *@return      The field value
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
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void buildResources(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      //System.out.println("CustomFieldGroup-> buildResources");
    }
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
    sqlOrder.append("ORDER BY level, field_id, field_name ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insertGroup(Connection db) throws SQLException {
    if (!isGroupValid()) {
      return false;
    }
    StringBuffer sql = new StringBuffer();
    level = this.retrieveNextLevel(db);
    sql.append("INSERT INTO custom_field_group ");
    sql.append("(category_id, group_name, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("description, level ) ");
    sql.append("VALUES (?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getCategoryId());
    pst.setString(++i, this.getName());
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    
    id = DatabaseUtils.getCurrVal(db, "custom_field_group_group_id_seq");
    
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean updateGroup(Connection db) throws SQLException {
    if (!isGroupValid() || id == -1) {
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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int updateLevel(Connection db) throws SQLException {
    int result = 1;
    String sql =
        "UPDATE custom_field_group " +
        "SET level = ? " +
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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean deleteGroup(Connection db) throws SQLException {
    boolean result = false;
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomFieldGroup-> deleteGroup Id:" + id);
    }
    if (id == -1) {
      return result;
    }

    db.setAutoCommit(false);
    try {
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
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void delete(Connection db) throws SQLException {
    String sql =
        "DELETE FROM custom_field_info " +
        "WHERE group_id IN (SELECT group_id FROM custom_field_group WHERE category_id = ?) ";
    PreparedStatement pst = db.prepareStatement(sql);
    int i = 0;
    pst.setInt(++i, categoryId);
    pst.execute();

    sql =
        "DELETE FROM custom_field_group " +
        "WHERE category_id = ? ";
    pst = db.prepareStatement(sql);
    i = 0;
    pst.setInt(++i, categoryId);
    pst.execute();
    pst.close();
  }


  /**
   *  Gets the groupValid attribute of the CustomFieldGroup object
   *
   *@return    The groupValid value
   */
  private boolean isGroupValid() {
    if (categoryId == -1) {
      errors.put("actionError", "Form data is missing");
    }
    if (name == null || name.equals("")) {
      errors.put("nameError", "Name is required");
    }
    return (errors.size() == 0);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
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
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (includeScheduled == TRUE) {
      sqlFilter.append("AND CURRENT_TIMESTAMP > cfi.start_date AND (CURRENT_TIMESTAMP < cfi.end_date OR cfi.end_date IS NULL) ");
    } else if (includeScheduled == FALSE) {
      sqlFilter.append("AND (CURRENT_TIMESTAMP < cfi.start_date OR (CURRENT_TIMESTAMP > cfi.end_date AND cfi.end_date IS NOT NULL)) ");
    }

    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND cfi.enabled = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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

  private int retrieveNextLevel(Connection db) throws SQLException {
    int returnLevel = 0;
    PreparedStatement pst = db.prepareStatement(
      "SELECT MAX(level) as level " +
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

