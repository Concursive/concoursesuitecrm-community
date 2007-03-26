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
import org.aspcfs.modules.actionplans.base.ActionStepList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * When a user goes to the "Folders" section of a module, there are several
 * categories of folders. Each of these CustomFieldCategory objects then has
 * groups of fields.
 *
 * @author mrajkowski
 * @version $Id: CustomFieldCategory.java,v 1.3 2002/03/15 22:29:34
 *          mrajkowski Exp $
 * @created January 16, 2002
 */
public class CustomFieldCategory extends ArrayList {

  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected HashMap errors = new HashMap();
  protected HashMap warnings = new HashMap();
  protected boolean onlyWarnings = false;
  //Properties of a Category
  private int id = -1;
  private int moduleId = -1;
  private String name = null;
  private String description = null;
  private int level = -1;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private boolean defaultItem = false;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = false;
  private boolean allowMultipleRecords = false;
  private boolean readOnly = false;

  //Properties for building a list
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int recordId = -1;
  private int includeEnabled = -1;
  private int includeScheduled = -1;
  private boolean buildResources = false;
  private boolean canNotContinue = false;

  //Resources
  private int numberOfRecords = -1;

  //Number of Records for a specific linkItem

  /**
   * Gets the onlyWarnings attribute of the CustomFieldCategory object
   *
   * @return The onlyWarnings value
   */
  public boolean getOnlyWarnings() {
    return onlyWarnings;
  }


  /**
   * Sets the onlyWarnings attribute of the CustomFieldCategory object
   *
   * @param tmp The new onlyWarnings value
   */
  public void setOnlyWarnings(boolean tmp) {
    this.onlyWarnings = tmp;
  }


  /**
   * Sets the onlyWarnings attribute of the CustomFieldCategory object
   *
   * @param tmp The new onlyWarnings value
   */
  public void setOnlyWarnings(String tmp) {
    this.onlyWarnings = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the numberOfRecords attribute of the CustomFieldCategory object
   *
   * @param tmp The new numberOfRecords value
   */
  public void setNumberOfRecords(int tmp) {
    this.numberOfRecords = tmp;
  }


  /**
   * Sets the numberOfRecords attribute of the CustomFieldCategory object
   *
   * @param tmp The new numberOfRecords value
   */
  public void setNumberOfRecords(String tmp) {
    this.numberOfRecords = Integer.parseInt(tmp);
  }


  /**
   * Gets the numberOfRecords attribute of the CustomFieldCategory object
   *
   * @return The numberOfRecords value
   */
  public int getNumberOfRecords() {
    return numberOfRecords;
  }


  /**
   * Constructor for the CustomFieldCategory object
   */
  public CustomFieldCategory() {
  }


  /**
   * Constructor for the CustomFieldCategory object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of Exception
   */
  public CustomFieldCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the CustomFieldCategory object
   *
   * @param db         Description of Parameter
   * @param categoryId Description of Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of Exception
   */
  public CustomFieldCategory(Connection db, int categoryId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        " SELECT " +
            " cfc.module_id as module_id, cfc.category_id as category_id, cfc.category_name as category_name, cfc." + DatabaseUtils.addQuotes(db, "level") + " AS " + DatabaseUtils.addQuotes(db, "level") + ",  " +
            " cfc.description as description, cfc.start_date as start_date, cfc.end_date as end_date, " +
            " cfc.default_item as default_item, cfc.entered as entered, cfc.enabled as enabled, " +
            " cfc.multiple_records as multiple_records, cfc.read_only as read_only, cfc.modified " +
            "FROM custom_field_category cfc " +
            "WHERE cfc.category_id = ? ");
    pst.setInt(1, categoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   * Sets the Id attribute of the CustomFieldCategory object
   *
   * @param tmp The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CustomFieldCategory object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the CategoryId attribute of the CustomFieldCategory object
   *
   * @param tmp The new CategoryId value
   */
  public void setCategoryId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the ModuleId attribute of the CustomFieldCategory object
   *
   * @param tmp The new ModuleId value
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   * Sets the ModuleId attribute of the CustomFieldCategory object
   *
   * @param tmp The new ModuleId value
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the Name attribute of the CustomFieldCategory object
   *
   * @param tmp The new Name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the Description attribute of the CustomFieldCategory object
   *
   * @param tmp The new Description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the Level attribute of the CustomFieldCategory object
   *
   * @param tmp The new Level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the CustomFieldCategory object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Sets the StartDate attribute of the CustomFieldCategory object
   *
   * @param tmp The new StartDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   * Sets the startDate attribute of the CustomFieldCategory object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the EndDate attribute of the CustomFieldCategory object
   *
   * @param tmp The new EndDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   * Sets the endDate attribute of the CustomFieldCategory object
   *
   * @param tmp The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the DefaultItem attribute of the CustomFieldCategory object
   *
   * @param tmp The new DefaultItem value
   */
  public void setDefaultItem(boolean tmp) {
    this.defaultItem = tmp;
  }


  /**
   * Sets the defaultItem attribute of the CustomFieldCategory object
   *
   * @param tmp The new defaultItem value
   */
  public void setDefaultItem(String tmp) {
    this.defaultItem = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(
        tmp));
  }


  /**
   * Sets the Entered attribute of the CustomFieldCategory object
   *
   * @param tmp The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the CustomFieldCategory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modified attribute of the CustomFieldCategory object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the EnteredBy attribute of the CustomFieldCategory object
   *
   * @param tmp The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the CustomFieldCategory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the Modified attribute of the CustomFieldCategory object
   *
   * @param tmp The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the ModifiedBy attribute of the CustomFieldCategory object
   *
   * @param tmp The new ModifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the CustomFieldCategory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the Enabled attribute of the CustomFieldCategory object
   *
   * @param tmp The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the CustomFieldCategory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Sets the LinkModuleId attribute of the CustomFieldCategory object
   *
   * @param tmp The new LinkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the LinkItemId attribute of the CustomFieldCategory object
   *
   * @param tmp The new LinkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the RecordId attribute of the CustomFieldCategory object
   *
   * @param tmp The new RecordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   * Sets the IncludeEnabled attribute of the CustomFieldCategory object
   *
   * @param tmp The new IncludeEnabled value
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
  }


  /**
   * Sets the IncludeScheduled attribute of the CustomFieldCategory object
   *
   * @param tmp The new IncludeScheduled value
   */
  public void setIncludeScheduled(int tmp) {
    this.includeScheduled = tmp;
  }


  /**
   * Sets the BuildResources attribute of the CustomFieldCategory object
   *
   * @param tmp The new BuildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   * Sets the Errors attribute of the CustomFieldCategory object
   *
   * @param tmp The new Errors value
   */
  public void setErrors(HashMap tmp) {
    this.errors = tmp;
  }


  /**
   * Sets the Parameters attribute of the CustomFieldCategory object
   *
   * @param context The new Parameters value
   */
  public void setParameters(ActionContext context) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) i.next();
      thisGroup.setParameters(context);
    }
  }


  /**
   * Sets the warnings attribute of the CustomFieldCategory object
   *
   * @param tmp The new warnings value
   */
  public void setWarnings(HashMap tmp) {
    this.warnings = tmp;
  }


  /**
   * Sets the canNotContinue attribute of the CustomFieldCategory object
   *
   * @param tmp The new canNotContinue value
   */
  public void setCanNotContinue(boolean tmp) {
    this.canNotContinue = tmp;
  }


  /**
   * Sets the canNotContinue attribute of the CustomFieldCategory object
   *
   * @param tmp The new canNotContinue value
   */
  public void setCanNotContinue(String tmp) {
    this.canNotContinue = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the canNotContinue attribute of the CustomFieldCategory object
   *
   * @return The canNotContinue value
   */
  public boolean getCanNotContinue() {
    return canNotContinue;
  }


  /**
   * Gets the warnings attribute of the CustomFieldCategory object
   *
   * @return The warnings value
   */
  public HashMap getWarnings() {
    return warnings;
  }


  /**
   * Gets the linkModuleId attribute of the CustomFieldCategory object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the linkItemId attribute of the CustomFieldCategory object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Gets the Id attribute of the CustomFieldCategory object
   *
   * @return The Id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the allowMultipleRecords attribute of the CustomFieldCategory object
   *
   * @param tmp The new allowMultipleRecords value
   */
  public void setAllowMultipleRecords(boolean tmp) {
    this.allowMultipleRecords = tmp;
  }


  /**
   * Sets the allowMultipleRecords attribute of the CustomFieldCategory object
   *
   * @param tmp The new allowMultipleRecords value
   */
  public void setAllowMultipleRecords(String tmp) {
    this.allowMultipleRecords = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(
        tmp));
  }


  /**
   * Gets the allowMultipleRecords attribute of the CustomFieldCategory object
   *
   * @return The allowMultipleRecords value
   */
  public boolean getAllowMultipleRecords() {
    return allowMultipleRecords;
  }


  /**
   * Sets the readOnly attribute of the CustomFieldCategory object
   *
   * @param readOnly The new readOnly value
   */
  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }


  /**
   * Sets the readOnly attribute of the CustomFieldCategory object
   *
   * @param tmp The new readOnly value
   */
  public void setReadOnly(String tmp) {
    this.readOnly = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(
        tmp));
  }


  /**
   * Gets the readOnly attribute of the CustomFieldCategory object
   *
   * @return The readOnly value
   */
  public boolean getReadOnly() {
    return readOnly;
  }


  /**
   * Gets the ModuleId attribute of the CustomFieldCategory object
   *
   * @return The ModuleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   * Gets the RecordId attribute of the CustomFieldCategory object
   *
   * @return The RecordId value
   */
  public int getRecordId() {
    return recordId;
  }


  /**
   * Gets the Name attribute of the CustomFieldCategory object
   *
   * @return The Name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the Description attribute of the CustomFieldCategory object
   *
   * @return The Description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the Level attribute of the CustomFieldCategory object
   *
   * @return The Level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Gets the StartDate attribute of the CustomFieldCategory object
   *
   * @return The StartDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   * Gets the EndDate attribute of the CustomFieldCategory object
   *
   * @return The EndDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   * Gets the DefaultItem attribute of the CustomFieldCategory object
   *
   * @return The DefaultItem value
   */
  public boolean getDefaultItem() {
    return defaultItem;
  }


  /**
   * Gets the Entered attribute of the CustomFieldCategory object
   *
   * @return The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the EnteredString attribute of the CustomFieldCategory object
   *
   * @return The EnteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the EnteredBy attribute of the CustomFieldCategory object
   *
   * @return The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the Modified attribute of the CustomFieldCategory object
   *
   * @return The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the ModifiedDateTimeString attribute of the CustomFieldCategory
   * object
   *
   * @return The ModifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the ModifiedBy attribute of the CustomFieldCategory object
   *
   * @return The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the Enabled attribute of the CustomFieldCategory object
   *
   * @return The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the Errors attribute of the CustomFieldCategory object
   *
   * @return The Errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   * Gets the Group attribute of the CustomFieldCategory object
   *
   * @param tmp Description of Parameter
   * @return The Group value
   */
  public CustomFieldGroup getGroup(int tmp) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) i.next();
      if (thisGroup.getId() == tmp) {
        return thisGroup;
      }
    }
    return new CustomFieldGroup();
  }


  /**
   * Gets the fieldValue attribute of the CustomFieldCategory object
   *
   * @param fieldId Description of Parameter
   * @return The fieldValue value
   */
  public String getFieldValue(int fieldId) {
    Iterator groups = this.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
      Iterator fields = thisGroup.iterator();
      while (fields.hasNext()) {
        CustomField thisField = (CustomField) fields.next();
        if (thisField.getId() == fieldId) {
          return thisField.getEnteredValue();
        }
      }
    }
    return null;
  }


  /**
   * Gets the firstFieldValue attribute of the CustomFieldCategory object
   *
   * @return The firstFieldValue value
   */
  public String getFirstFieldValue() {
    Iterator groups = this.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
      Iterator fields = thisGroup.iterator();
      if (fields.hasNext()) {
        CustomField thisField = (CustomField) fields.next();
        return thisField.getEnteredValue();
      }
    }
    return null;
  }


  /**
   * Checks if the group contains any fields.
   *
   * @return The empty value
   */
  public boolean isEmpty() {
    boolean empty = true;
    Iterator groups = this.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
      Iterator fields = thisGroup.iterator();
      if (fields.hasNext()) {
        empty = false;
        break;
      }
    }
    return empty;
  }


  /**
   * Description of the Method
   *
   * @param fieldId Description of Parameter
   * @return Description of the Returned Value
   */
  public boolean hasField(int fieldId) {
    Iterator groups = this.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
      Iterator fields = thisGroup.iterator();
      while (fields.hasNext()) {
        CustomField thisField = (CustomField) fields.next();
        if (thisField.getId() == fieldId) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildResources(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomFieldCategory-> buildResources");
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
            "FROM custom_field_group cfg " +
            "WHERE cfg.category_id = " + id + " ");

    createFilter(db, sqlFilter);
    sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", group_id, group_name ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      CustomFieldGroup thisGroup = new CustomFieldGroup(rs);
      this.add(thisGroup);
    }
    rs.close();
    pst.close();

    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldGroup thisGroup = (CustomFieldGroup) i.next();
        thisGroup.setLinkModuleId(this.linkModuleId);
        thisGroup.setLinkItemId(this.linkItemId);
        thisGroup.setRecordId(this.recordId);
        thisGroup.setIncludeEnabled(this.includeEnabled);
        thisGroup.setIncludeScheduled(this.includeScheduled);
        thisGroup.setBuildResources(this.buildResources);
        thisGroup.buildResources(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecordId(Connection db) throws SQLException {
    String sql =
        "SELECT record_id " +
            "FROM custom_field_record " +
            "WHERE link_module_id = ? " +
            "AND link_item_id = ? " +
            "AND category_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, linkModuleId);
    pst.setInt(2, linkItemId);
    pst.setInt(3, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordId = rs.getInt("record_id");
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int update(Connection db) throws SQLException {
    int resultId = -1;

    try {
      int catResult = 1;
      db.setAutoCommit(false);
      Statement st = db.createStatement();

      st.executeUpdate(
          "UPDATE custom_field_record " +
              "SET modifiedBy = " + modifiedBy + ", " +
              "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
              "WHERE link_module_id = " + linkModuleId + " " +
              "AND link_item_id = " + linkItemId + " " +
              "AND record_id = " + recordId + " ");

      //TODO: return a 0 if record was modified by someone else

      st.executeUpdate(
          "DELETE FROM custom_field_data " +
              "WHERE record_id = " + recordId + " ");
      st.close();

      if (!this.getCanNotContinue()) {
        catResult = this.insertGroup(db, -1);
      }

      if (catResult == 1) {
        db.commit();
        resultId = 1;
      } else {
        db.rollback();
        resultId = catResult;
      }
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }

    return resultId;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param recordId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int insertGroup(Connection db, int recordId) throws SQLException {
    int catResult = 1;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) i.next();
      if (recordId != -1) {
        thisGroup.setRecordId(recordId);
      }
      int groupResult = thisGroup.insert(db);
      if (groupResult != 1) {
        catResult = groupResult;
      }
    }
    return catResult;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int insert(Connection db) throws SQLException {
    /*
     *  A duplicate record for this category cannot be inserted
     *  if someone else already inserted one.
     *  TODO: Return an error message
     */
    if (!allowMultipleRecords) {
      synchronized (this) {
        this.buildRecordId(db);
        if (recordId > -1) {
          return -1;
        } else {
          return doInsert(db);
        }
      }
    } else {
      return doInsert(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int doInsert(Connection db) throws SQLException {
    int resultId = -1;
    try {
      int catResult = 1;
      db.setAutoCommit(false);
      CustomFieldRecord thisRecord = new CustomFieldRecord();
      thisRecord.setLinkModuleId(linkModuleId);
      thisRecord.setLinkItemId(linkItemId);
      thisRecord.setCategoryId(id);
      thisRecord.setEnteredBy(enteredBy);
      thisRecord.setModifiedBy(modifiedBy);
      thisRecord.insert(db);
      this.setRecordId(thisRecord.getId());

      if (!this.getCanNotContinue()) {
        catResult = this.insertGroup(db, thisRecord.getId());
      }

      if (catResult == 1) {
        db.commit();
        resultId = 1;
      } else {
        db.rollback();
        resultId = catResult;
      }
    } catch (Exception e) {
      db.rollback();
      this.setRecordId(-1);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    //if (resultId == -1) {  //QUESTION: what is this?
    if (this.getRecordId() != -1) {
      update(db);
    }
    return resultId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insertCategory(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "custom_field_ca_category_id_seq");
    sql.append("INSERT INTO custom_field_category ");
    sql.append("(module_id, category_name, description, ");
    sql.append("multiple_records, read_only, ");
    if (id > -1) {
      sql.append("category_id, ");
    }
    sql.append("entered, modified, ");
    sql.append("enabled ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?,");
    }
    if (entered != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    if (modified != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?) ");

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getModuleId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());

    pst.setBoolean(++i, this.getAllowMultipleRecords());
    pst.setBoolean(++i, this.getReadOnly());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }

    pst.setBoolean(++i, this.getEnabled());

    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "custom_field_ca_category_id_seq", id);

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean toggleEnabled(Connection db) throws SQLException {
    if (id == -1) {
      return false;
    }

    String sql =
        "UPDATE custom_field_category " +
            "SET enabled = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE category_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setBoolean(++i, !this.getEnabled());
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    this.enabled = !enabled;
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean updateCategory(Connection db) throws SQLException {
    if (id == -1) {
      return false;
    }
    String sql =
        "UPDATE custom_field_category " +
            "SET category_name = ?, description = ?, enabled = ?, " +
            "multiple_records = ?, read_only = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE module_id = ? AND category_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
    pst.setBoolean(++i, this.getAllowMultipleRecords());
    pst.setBoolean(++i, this.getReadOnly());
    pst.setInt(++i, this.getModuleId());
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean deleteCategory(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CustomFieldCategory-> Delete Category: Mod(" + moduleId + ") Cat " + id);
    }
    if (moduleId == -1 || id == -1) {
      System.out.println("CustomFieldCategory-> Form Error");
      return false;
    }

    try {
      db.setAutoCommit(false);
      ActionStepList stepList = new ActionStepList();
      stepList.setCategoryId(this.getId());
      stepList.buildList(db);
      stepList.resetFolderInformation(db);

      //Delete CustomFields and CustomFieldRecords that use this folder (associated data)
      CustomFieldRecord thisRecord = new CustomFieldRecord();
      thisRecord.setLinkModuleId(moduleId);
      thisRecord.setCategoryId(id);
      thisRecord.delete(db);

      //Delete CustomFields and CustomFieldGroups that are in the groups (info definition)
      CustomFieldGroup thisGroup = new CustomFieldGroup();
      thisGroup.setLinkModuleId(linkModuleId);
      thisGroup.setCategoryId(id);
      thisGroup.delete(db);

      //Delete the category (info)
      String sql =
          "DELETE FROM custom_field_category " +
              "WHERE module_id = ? " +
              "AND category_id = ? ";
      PreparedStatement pst = db.prepareStatement(sql);
      int i = 0;
      pst.setInt(++i, moduleId);
      pst.setInt(++i, id);
      pst.execute();
      pst.close();

      db.commit();
    } catch (Exception e) {
      e.printStackTrace();
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    moduleId = rs.getInt("module_id");
    id = rs.getInt("category_id");
    name = rs.getString("category_name");
    level = rs.getInt("level");
    description = rs.getString("description");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    defaultItem = rs.getBoolean("default_item");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");;
    enabled = rs.getBoolean("enabled");
    allowMultipleRecords = rs.getBoolean("multiple_records");
    readOnly = rs.getBoolean("read_only");
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (includeScheduled == TRUE) {
      sqlFilter.append(
          "AND " + DatabaseUtils.getCurrentTimestamp(db) + " > cfg.start_date AND (" + DatabaseUtils.getCurrentTimestamp(db) + " < cfg.end_date OR cfg.end_date IS NULL) ");
    } else if (includeScheduled == FALSE) {
      sqlFilter.append(
          "AND (" + DatabaseUtils.getCurrentTimestamp(db) + " < cfg.start_date OR (" + DatabaseUtils.getCurrentTimestamp(db) + " > cfg.end_date AND cfg.end_date IS NOT NULL)) ");
    }

    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND cfg.enabled = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
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
   * return the unique CF ids of the fields on the form page for inserting a
   * new record in this category.
   *
   * @return The formFieldIds value
   */
  public ArrayList getFormFieldIds() {
    ArrayList a = new ArrayList();
    Iterator groups = this.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
      Iterator fields = thisGroup.iterator();
      if (fields.hasNext()) {
        while (fields.hasNext()) {
          CustomField thisField = (CustomField) fields.next();
          a.add(new Integer(thisField.getId()));
        }
      }
    }
    return a;
  }


  /**
   * Gets the category_id provided the category_name attribute of the
   * CustomFieldCategory class
   *
   * @param db           Description of the Parameter
   * @param moduleId     Description of the Parameter
   * @param categoryName Description of the Parameter
   * @return The idFromName value
   * @throws SQLException Description of the Exception
   */
  public static int getIdFromName(Connection db, int moduleId, String categoryName) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        " SELECT category_id " +
            " FROM custom_field_category " +
            " WHERE module_id = ? " +
            " AND category_name = ? " +
            " AND category_id IN " +
            " (SELECT max(category_id) " +
            " FROM custom_field_category " +
            " WHERE module_id = ? " +
            " AND category_name = ?) ");
    pst.setInt(1, moduleId);
    pst.setString(2, categoryName);
    pst.setInt(3, moduleId);
    pst.setString(4, categoryName);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = rs.getInt("category_id");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   * Gets the idFromRecord attribute of the CustomFieldCategory class
   *
   * @param db       Description of the Parameter
   * @param recordId Description of the Parameter
   * @return The idFromRecord value
   * @throws SQLException Description of the Exception
   */
  public static int getIdFromRecord(Connection db, int recordId) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        " SELECT category_id " +
            "FROM custom_field_record " +
            "WHERE record_id = ?");
    pst.setInt(1, recordId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = rs.getInt("category_id");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param linkModuleId Description of the Parameter
   * @param linkItemId   Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void determineNumberOfRecords(Connection db, int linkModuleId, int linkItemId)
      throws SQLException {
    CustomFieldRecordList recordList = new CustomFieldRecordList();
    recordList.setLinkModuleId(linkModuleId);
    recordList.setLinkItemId(linkItemId);
    recordList.setCategoryId(this.getId());
    recordList.buildList(db);
    numberOfRecords = recordList.size();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean hasMultipleRecords(Connection db) throws SQLException {
    boolean hasMultiple = false;
    if (this.getId() == -1) {
      throw new SQLException("Custom Field Category ID not specified");
    }
    if (this.getModuleId() == -1) {
      throw new SQLException("Module ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT link_item_id, count (record_id) AS records " +
            "FROM custom_field_record " +
            "WHERE link_module_id = ? AND category_id = ? " +
            "GROUP BY link_item_id ");
    pst.setInt(1, this.getModuleId());
    pst.setInt(2, this.getId());
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      if (rs.getInt("records") > 1) {
        hasMultiple = true;
      }
    }
    rs.close();
    pst.close();
    return hasMultiple;
  }


  /**
   * Gets the modules attribute of the CustomFieldCategory object
   *
   * @param db Description of the Parameter
   * @return The modules value
   * @throws SQLException Description of the Exception
   */
  public static ArrayList getModules(Connection db) throws SQLException {
    ArrayList modules = new ArrayList();
    PreparedStatement pst = db.prepareStatement(
        "SELECT category_id " +
            "FROM module_field_categorylink ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      modules.add(
          String.valueOf(
              rs.getInt("category_id")));
    }
    rs.close();
    pst.close();
    return modules;
  }
}

