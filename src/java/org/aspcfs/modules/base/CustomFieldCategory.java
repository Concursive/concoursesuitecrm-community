//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.*;

/**
 *  When a user goes to the "Folders" section of a module, there are several
 *  categories of folders. Each of these CustomFieldCategory objects then has
 *  groups of fields.
 *
 *@author     mrajkowski
 *@created    January 16, 2002
 *@version    $Id: CustomFieldCategory.java,v 1.3 2002/03/15 22:29:34 mrajkowski
 *      Exp $
 */
public class CustomFieldCategory extends ArrayList {

  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected HashMap errors = new HashMap();

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


  /**
   *  Constructor for the CustomFieldCategory object
   *
   *@since
   */
  public CustomFieldCategory() { }


  /**
   *  Constructor for the CustomFieldCategory object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public CustomFieldCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the CustomFieldCategory object
   *
   *@param  db                Description of Parameter
   *@param  categoryId        Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public CustomFieldCategory(Connection db, int categoryId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
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
   *  Sets the Id attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CategoryId attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new CategoryId value
   *@since
   */
  public void setCategoryId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModuleId attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new ModuleId value
   *@since
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   *  Sets the ModuleId attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new ModuleId value
   *@since
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Name attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Name value
   *@since
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Description attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Description value
   *@since
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Level attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Level value
   *@since
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the StartDate attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new StartDate value
   *@since
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the EndDate attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new EndDate value
   *@since
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the DefaultItem attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new DefaultItem value
   *@since
   */
  public void setDefaultItem(boolean tmp) {
    this.defaultItem = tmp;
  }


  /**
   *  Sets the defaultItem attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new defaultItem value
   */
  public void setDefaultItem(String tmp) {
    this.defaultItem = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the Entered attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the EnteredBy attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Modified attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Modified value
   *@since
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the LinkModuleId attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new LinkModuleId value
   *@since
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the LinkItemId attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new LinkItemId value
   *@since
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the RecordId attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new RecordId value
   *@since
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   *  Sets the IncludeEnabled attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new IncludeEnabled value
   *@since
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
  }


  /**
   *  Sets the IncludeScheduled attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new IncludeScheduled value
   *@since
   */
  public void setIncludeScheduled(int tmp) {
    this.includeScheduled = tmp;
  }


  /**
   *  Sets the BuildResources attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new BuildResources value
   *@since
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the Errors attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Errors value
   *@since
   */
  public void setErrors(HashMap tmp) {
    this.errors = tmp;
  }


  /**
   *  Sets the Parameters attribute of the CustomFieldCategory object
   *
   *@param  context  The new Parameters value
   *@since
   */
  public void setParameters(ActionContext context) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) i.next();
      thisGroup.setParameters(context);
    }
  }


  /**
   *  Gets the Id attribute of the CustomFieldCategory object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the allowMultipleRecords attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new allowMultipleRecords value
   */
  public void setAllowMultipleRecords(boolean tmp) {
    this.allowMultipleRecords = tmp;
  }


  /**
   *  Sets the allowMultipleRecords attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new allowMultipleRecords value
   */
  public void setAllowMultipleRecords(String tmp) {
    this.allowMultipleRecords = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Gets the allowMultipleRecords attribute of the CustomFieldCategory object
   *
   *@return    The allowMultipleRecords value
   */
  public boolean getAllowMultipleRecords() {
    return allowMultipleRecords;
  }


  /**
   *  Sets the readOnly attribute of the CustomFieldCategory object
   *
   *@param  readOnly  The new readOnly value
   */
  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }


  /**
   *  Sets the readOnly attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new readOnly value
   */
  public void setReadOnly(String tmp) {
    this.readOnly = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Gets the readOnly attribute of the CustomFieldCategory object
   *
   *@return    The readOnly value
   */
  public boolean getReadOnly() {
    return readOnly;
  }



  /**
   *  Gets the ModuleId attribute of the CustomFieldCategory object
   *
   *@return    The ModuleId value
   *@since
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   *  Gets the RecordId attribute of the CustomFieldCategory object
   *
   *@return    The RecordId value
   *@since
   */
  public int getRecordId() {
    return recordId;
  }


  /**
   *  Gets the Name attribute of the CustomFieldCategory object
   *
   *@return    The Name value
   *@since
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the Description attribute of the CustomFieldCategory object
   *
   *@return    The Description value
   *@since
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Level attribute of the CustomFieldCategory object
   *
   *@return    The Level value
   *@since
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the StartDate attribute of the CustomFieldCategory object
   *
   *@return    The StartDate value
   *@since
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the EndDate attribute of the CustomFieldCategory object
   *
   *@return    The EndDate value
   *@since
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the DefaultItem attribute of the CustomFieldCategory object
   *
   *@return    The DefaultItem value
   *@since
   */
  public boolean getDefaultItem() {
    return defaultItem;
  }


  /**
   *  Gets the Entered attribute of the CustomFieldCategory object
   *
   *@return    The Entered value
   *@since
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the EnteredString attribute of the CustomFieldCategory object
   *
   *@return    The EnteredString value
   *@since
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the EnteredBy attribute of the CustomFieldCategory object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Modified attribute of the CustomFieldCategory object
   *
   *@return    The Modified value
   *@since
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedDateTimeString attribute of the CustomFieldCategory
   *  object
   *
   *@return    The ModifiedDateTimeString value
   *@since
   */
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the ModifiedBy attribute of the CustomFieldCategory object
   *
   *@return    The ModifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the Enabled attribute of the CustomFieldCategory object
   *
   *@return    The Enabled value
   *@since
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the Errors attribute of the CustomFieldCategory object
   *
   *@return    The Errors value
   *@since
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   *  Gets the Group attribute of the CustomFieldCategory object
   *
   *@param  tmp  Description of Parameter
   *@return      The Group value
   *@since
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
   *  Gets the fieldValue attribute of the CustomFieldCategory object
   *
   *@param  fieldId  Description of Parameter
   *@return          The fieldValue value
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
   *  Checks if the group contains any fields.
   *
   *@return    The empty value
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
   *  Description of the Method
   *
   *@param  fieldId  Description of Parameter
   *@return          Description of the Returned Value
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void buildResources(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      //System.out.println("CustomFieldCategory-> buildResources");
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

    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY level, group_id, group_name ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
      recordId = rs.getInt(1);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
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
          "modified = CURRENT_TIMESTAMP " +
          "WHERE link_module_id = " + linkModuleId + " " +
          "AND link_item_id = " + linkItemId + " " +
          "AND record_id = " + recordId + " ");

      //TODO: return a 0 if record was modified by someone else

      st.executeUpdate(
          "DELETE FROM custom_field_data " +
          "WHERE record_id = " + recordId + " ");
      st.close();

      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldGroup thisGroup = (CustomFieldGroup) i.next();
        int groupResult = thisGroup.insert(db);
        if (groupResult != 1) {
          catResult = groupResult;
        }
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
    } finally {
      db.setAutoCommit(true);
    }

    return resultId;
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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

      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldGroup thisGroup = (CustomFieldGroup) i.next();
        thisGroup.setRecordId(thisRecord.getId());
        int groupResult = thisGroup.insert(db);
        if (groupResult != 1) {
          catResult = groupResult;
        }
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insertCategory(Connection db) throws SQLException {
    if (!isCategoryValid()) {
      return false;
    }
    StringBuffer sql = new StringBuffer();

    sql.append("INSERT INTO custom_field_category ");
    sql.append("(module_id, category_name, description, ");
    sql.append("multiple_records, read_only, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enabled ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?) ");

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getModuleId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());

    pst.setBoolean(++i, this.getAllowMultipleRecords());
    pst.setBoolean(++i, this.getReadOnly());

    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }

    pst.setBoolean(++i, this.getEnabled());

    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "custom_field_ca_category_id_seq");

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
  public boolean toggleEnabled(Connection db) throws SQLException {
    if (id == -1) {
      return false;
    }

    String sql =
        "UPDATE custom_field_category " +
        "SET enabled = ? " +
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean updateCategory(Connection db) throws SQLException {
    if (!isCategoryValid() && id > -1) {
      return false;
    }

    String sql =
        "UPDATE custom_field_category " +
        "SET category_name = ?, description = ?, enabled = ?, " +
        "multiple_records = ?, read_only = ? " +
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean deleteCategory(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomFieldCategory-> Delete Category: Mod(" + moduleId + ") Cat " + id);
    }
    if (moduleId == -1 || id == -1) {
      System.out.println("CustomFieldCategory-> Form Error");
      return false;
    }

    try {
      db.setAutoCommit(false);

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
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Gets the CategoryValid attribute of the CustomFieldCategory object
   *
   *@return    The CategoryValid value
   *@since
   */
  private boolean isCategoryValid() {
    if (moduleId == -1) {
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
    moduleId = rs.getInt("module_id");
    id = rs.getInt("category_id");
    name = rs.getString("category_name");
    level = rs.getInt("level");
    description = rs.getString("description");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    defaultItem = rs.getBoolean("default_item");
    entered = rs.getTimestamp("entered");
    modified = entered;
    enabled = rs.getBoolean("enabled");
    allowMultipleRecords = rs.getBoolean("multiple_records");
    readOnly = rs.getBoolean("read_only");
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   *@since
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (includeScheduled == TRUE) {
      sqlFilter.append("AND CURRENT_TIMESTAMP > cfg.start_date AND (CURRENT_TIMESTAMP < cfg.end_date OR cfg.end_date IS NULL) ");
    } else if (includeScheduled == FALSE) {
      sqlFilter.append("AND (CURRENT_TIMESTAMP < cfg.start_date OR (CURRENT_TIMESTAMP > cfg.end_date AND cfg.end_date IS NOT NULL)) ");
    }

    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND cfg.enabled = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
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
   *  return the unique CF ids of the fields on the form page for inserting a
   *  new record in this category.
   *
   *@return    The formFieldIds value
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

}

