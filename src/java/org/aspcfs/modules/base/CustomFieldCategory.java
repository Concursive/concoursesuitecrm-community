//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import org.theseus.actions.*;

public class CustomFieldCategory extends Vector {
  protected Hashtable errors = new Hashtable();
  
  public final static int TRUE = 1;
  public final static int FALSE = 0;
  
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

  public CustomFieldCategory() { }

  public CustomFieldCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
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
  
  public void setId(int tmp) { this.id = tmp; }
  public void setCategoryId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setModuleId(int tmp) { this.moduleId = tmp; }
  public void setModuleId(String tmp) { this.moduleId = Integer.parseInt(tmp); }
  public void setName(String tmp) { this.name = tmp; }
  public void setDescription(String tmp) { this.description = tmp; }
  public void setLevel(int tmp) { this.level = tmp; }
  public void setStartDate(java.sql.Timestamp tmp) { this.startDate = tmp; }
  public void setEndDate(java.sql.Timestamp tmp) { this.endDate = tmp; }
  public void setDefaultItem(boolean tmp) { this.defaultItem = tmp; }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public void setEnabled(boolean tmp) { this.enabled = tmp; }
  public void setEnabled(String tmp) { this.enabled = ("ON").equalsIgnoreCase(tmp); }
  
  public void setLinkModuleId(int tmp) { this.linkModuleId = tmp; }
  public void setLinkItemId(int tmp) { this.linkItemId = tmp; }
  public void setRecordId(int tmp) { this.recordId = tmp; }
  public void setIncludeEnabled(int tmp) { this.includeEnabled = tmp; }
  public void setIncludeScheduled(int tmp) { this.includeScheduled = tmp; }
  public void setBuildResources(boolean tmp) { this.buildResources = tmp; }
  public void setErrors(Hashtable tmp) { this.errors = tmp; }

  public int getId() { return id; }
  public int getModuleId() { return moduleId; }
  public int getRecordId() { return recordId; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public int getLevel() { return level; }
  public java.sql.Timestamp getStartDate() { return startDate; }
  public java.sql.Timestamp getEndDate() { return endDate; }
  public boolean getDefaultItem() { return defaultItem; }
  public java.sql.Timestamp getEntered() { return entered; }
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public int getModifiedBy() { return modifiedBy; }
  public boolean getEnabled() { return enabled; }
  public Hashtable getErrors() { return errors; }
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
  
  public void buildResources(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) System.out.println("CustomFieldCategory-> buildResources");
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

  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    return i;
  }
  
  public void setParameters(ActionContext context) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup)i.next();
      thisGroup.setParameters(context);
    }
  }
  
  public int update(Connection db) throws SQLException {
    int resultId = -1;
    
    try {
      db.setAutoCommit(false);
      Statement st = db.createStatement();
      
      st.executeUpdate(
        "UPDATE custom_field_record " +
        "SET modifiedBy = " + modifiedBy + ", " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE link_module_id = " + linkModuleId + " " +
        "AND link_item_id = " + linkItemId + " " +
        "AND record_id = " + recordId + " ");
      
      st.executeUpdate(
        "DELETE FROM custom_field_data " +
        "WHERE record_id = " + recordId + " ");
      st.close();
      
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldGroup thisGroup = (CustomFieldGroup)i.next();
        thisGroup.insert(db);
      }
      
      db.commit();
      resultId = 1;
    } catch (Exception e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    
    return resultId;
  }
  
  public int insert(Connection db) throws SQLException {
    int resultId = -1;
    
    try {
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
        thisGroup.insert(db);
      }
      
      db.commit();
      resultId = 1;
    } catch (Exception e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);
    
    return resultId;
  }
  
  private boolean isCategoryValid() {
    if (moduleId == -1) errors.put("actionError", "Form data is missing");
    if (name == null || name.equals("")) errors.put("nameError", "Name is required");
    return (errors.size() == 0);
  }
  
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
  
  public boolean toggleEnabled(Connection db) throws SQLException {
    if (id == -1) return false;
    
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
  
  public boolean deleteCategory(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) System.out.println("CustomFieldCategory-> Delete Category: Mod(" + moduleId + ") Cat " + id);
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
  
}

