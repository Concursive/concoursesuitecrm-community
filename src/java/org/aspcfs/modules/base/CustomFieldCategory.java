//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import org.theseus.actions.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 16, 2002
 *@version    $Id$
 */
public class CustomFieldCategory extends Vector {
  protected Hashtable errors = new Hashtable();

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

  //Properties for building a list
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int recordId = -1;
  private int includeEnabled = -1;
  private int includeScheduled = -1;
  private boolean buildResources = false;

  public final static int TRUE = 1;
  public final static int FALSE = 0;


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
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT * " +
        "FROM custom_field_category cfc " +
        "WHERE cfc.category_id = " + categoryId + " ");
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    st.close();
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
   *  Sets the StartDate attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new StartDate value
   *@since
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
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
   *  Sets the DefaultItem attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new DefaultItem value
   *@since
   */
  public void setDefaultItem(boolean tmp) {
    this.defaultItem = tmp;
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
   *  Sets the EnteredBy attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
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
   *  Sets the Enabled attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the CustomFieldCategory object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(String tmp) {
    this.enabled = ("ON").equalsIgnoreCase(tmp);
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
  public void setErrors(Hashtable tmp) {
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
      CustomFieldGroup thisGroup = (CustomFieldGroup)i.next();
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
  public Hashtable getErrors() {
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
      CustomFieldGroup thisGroup = (CustomFieldGroup)i.next();
      if (thisGroup.getId() == tmp) {
        return thisGroup;
      }
    }
    return new CustomFieldGroup();
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

    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY level, group_id, group_name ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      CustomFieldGroup thisGroup = new CustomFieldGroup(rs);
      this.addElement(thisGroup);
    }
    rs.close();
    pst.close();

    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldGroup thisGroup = (CustomFieldGroup)i.next();
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
        CustomFieldGroup thisGroup = (CustomFieldGroup)i.next();
        int groupResult = thisGroup.insert(db);
        if (groupResult != 1) catResult = groupResult;
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
        CustomFieldGroup thisGroup = (CustomFieldGroup)i.next();
        thisGroup.setRecordId(thisRecord.getId());
        int groupResult = thisGroup.insert(db);
        if (groupResult != 1) catResult = groupResult;
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
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);

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

    String sql =
        "INSERT INTO custom_field_category " +
        "(module_id, category_name, description, enabled) VALUES (?, ?, ?, ?)";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getModuleId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
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
        "SET category_name = ?, description = ?, enabled = ? " +
        "WHERE module_id = ? AND category_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
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
      thisRecord.setLinkModuleId(linkModuleId);
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
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);

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
    id = rs.getInt("category_id");
    moduleId = rs.getInt("module_id");
    name = rs.getString("category_name");
    level = rs.getInt("level");
    description = rs.getString("description");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    defaultItem = rs.getBoolean("default_item");
    entered = rs.getTimestamp("entered");
    enabled = rs.getBoolean("enabled");
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

    if (includeEnabled == TRUE) {
      sqlFilter.append("AND cfg.enabled = true ");
    } else if (includeEnabled == FALSE) {
      sqlFilter.append("AND cfg.enabled = false ");
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
    return i;
  }

}

