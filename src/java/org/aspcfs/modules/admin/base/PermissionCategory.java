//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.beans.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  Represents a module category that has capabilities (lists, reports, etc.)
 *  and contains permissions for various actions
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id: PermissionCategory.java,v 1.9 2003/03/07 14:05:30 mrajkowski
 *      Exp $
 */
public class PermissionCategory extends GenericBean {

  //PermissionCategory properties
  private int id = -1;
  private String category = null;
  private String description = null;
  private int level = -1;
  private boolean enabled = false;
  private boolean active = false;
  private boolean lookups = false;
  private boolean folders = false;
  private boolean viewpoints = false;
  private boolean categories = false;
  private boolean scheduledEvents = false;
  private boolean objectEvents = false;
  private boolean reports = false;

  //Constants for working with lookup lists
  //NOTE: currently all editable lookup lists need to be defined here
  public final static int PERMISSION_CAT_LEADS = 4;
  public final static int LOOKUP_LEADS_STAGE = 1;
  public final static int LOOKUP_LEADS_TYPE = 2;

  public final static int PERMISSION_CAT_ACCOUNTS = 1;
  public final static int LOOKUP_ACCOUNTS_TYPE = 1;
  public final static int LOOKUP_ACCOUNTS_REVENUE_TYPE = 2;
  public final static int LOOKUP_ACCOUNTS_CONTACTS_TYPE = 3;

  // Service Contracts
  public final static int PERMISSION_CAT_SERVICE_CONTRACTS = 130041100;
  public final static int LOOKUP_SERVICECONTRACT_CATEGORY = 130041305;
  public final static int LOOKUP_SERVICECONTRACT_TYPE = 130041306;
  public final static int LOOKUP_RESPONSE_TIME_MODEL = 116041409;
  public final static int LOOKUP_PHONE_RESPONSE_MODEL = 116041410;
  public final static int LOOKUP_ONSITE_RESPONSE_TIME_MODEL = 116041411;
  public final static int LOOKUP_EMAIL_RESPONSE_TIME_MODEL = 116041412;
  public final static int LOOKUP_HOURS_REASON = 308041546;

  // Assets
  public final static int PERMISSION_CAT_ASSETS = 130041000;
  public final static int LOOKUP_ASSET_STATUS = 130041304;
  public final static int MULTIPLE_CATEGORY_ASSET = 202041400;

  public final static int PERMISSION_CAT_CONTACTS = 2;
  public final static int LOOKUP_CONTACTS_TYPE = 1;
  public final static int LOOKUP_CONTACTS_EMAIL = 2;
  public final static int LOOKUP_CONTACTS_ADDRESS = 3;
  public final static int LOOKUP_CONTACTS_PHONE = 4;

  public final static int PERMISSION_CAT_EMPLOYEES = 1111031131;
  public final static int LOOKUP_CONTACTS_DEPT = 1111031132;

  public final static int PERMISSION_CAT_TICKETS = 8;
  public final static int LOOKUP_TICKETS_SOURCE = 1;
  public final static int LOOKUP_TICKETS_SEVERITY = 2;
  public final static int LOOKUP_TICKETS_PRIORITY = 3;
  public final static int LOOKUP_TICKET_FORM = 127041246;
  public final static int MULTIPLE_CATEGORY_TICKET = 202041401;


  /**
   *  Constructor for the PermissionCategory object
   */
  public PermissionCategory() { }


  /**
   *  Constructor for the PermissionCategory object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public PermissionCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the PermissionCategory object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public PermissionCategory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Populates the permission category from the database with the specified id
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Permission Category ID");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM permission_category " +
        "WHERE category_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Permission category not found");
    }
  }


  /**
   *  Returns a permission category id for the specified category name by lookup
   *  up the value in the database and returning the first id found.
   *
   *@param  db                Description of the Parameter
   *@param  name              Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static int lookupId(Connection db, String name) throws SQLException {
    if (name == null) {
      throw new SQLException("Invalid Permission Category Name");
    }
    int i = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT category_id " +
        "FROM permission_category " +
        "WHERE category = ? ");
    pst.setString(1, name);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      i = rs.getInt("category_id");
    }
    rs.close();
    pst.close();
    return i;
  }


  /**
   *  Sets the id attribute of the PermissionCategory object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PermissionCategory object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the category attribute of the PermissionCategory object
   *
   *@param  tmp  The new category value
   */
  public void setCategory(String tmp) {
    this.category = tmp;
  }


  /**
   *  Sets the description attribute of the PermissionCategory object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the level attribute of the PermissionCategory object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the PermissionCategory object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the PermissionCategory object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the PermissionCategory object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the active attribute of the PermissionCategory object
   *
   *@param  tmp  The new active value
   */
  public void setActive(boolean tmp) {
    this.active = tmp;
  }


  /**
   *  Sets the active attribute of the PermissionCategory object
   *
   *@param  tmp  The new active value
   */
  public void setActive(String tmp) {
    this.active = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the categories attribute of the PermissionCategory object
   *
   *@param  categories  The new categories value
   */
  public void setCategories(boolean categories) {
    this.categories = categories;
  }


  /**
   *  Sets the categories attribute of the PermissionCategory object
   *
   *@param  categories  The new categories value
   */
  public void setCategories(String categories) {
    this.categories = DatabaseUtils.parseBoolean(categories);
  }


  /**
   *  Gets the categories attribute of the PermissionCategory object
   *
   *@return    The categories value
   */
  public boolean getCategories() {
    return categories;
  }


  /**
   *  Gets the id attribute of the PermissionCategory object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the category attribute of the PermissionCategory object
   *
   *@return    The category value
   */
  public String getCategory() {
    return category;
  }


  /**
   *  Gets the description attribute of the PermissionCategory object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the level attribute of the PermissionCategory object
   *
   *@return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the enabled attribute of the PermissionCategory object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the active attribute of the PermissionCategory object
   *
   *@return    The active value
   */
  public boolean getActive() {
    return active;
  }


  /**
   *  Gets the lookups attribute of the PermissionCategory object
   *
   *@return    The lookups value
   */
  public boolean getLookups() {
    return lookups;
  }


  /**
   *  Gets the folders attribute of the PermissionCategory object
   *
   *@return    The folders value
   */
  public boolean getFolders() {
    return folders;
  }


  /**
   *  Gets the viewpoints attribute of the PermissionCategory object
   *
   *@return    The viewpoints value
   */
  public boolean getViewpoints() {
    return viewpoints;
  }


  /**
   *  Gets the reports attribute of the PermissionCategory object
   *
   *@return    The reports value
   */
  public boolean getReports() {
    return reports;
  }


  /**
   *  Gets the scheduledEvents attribute of the PermissionCategory object
   *
   *@return    The scheduledEvents value
   */
  public boolean getScheduledEvents() {
    return scheduledEvents;
  }


  /**
   *  Gets the objectEvents attribute of the PermissionCategory object
   *
   *@return    The objectEvents value
   */
  public boolean getObjectEvents() {
    return objectEvents;
  }


  /**
   *  Sets the lookups attribute of the PermissionCategory object
   *
   *@param  tmp  The new lookups value
   */
  public void setLookups(boolean tmp) {
    this.lookups = tmp;
  }


  /**
   *  Sets the lookups attribute of the PermissionCategory object
   *
   *@param  tmp  The new lookups value
   */
  public void setLookups(String tmp) {
    this.lookups = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the folders attribute of the PermissionCategory object
   *
   *@param  tmp  The new folders value
   */
  public void setFolders(boolean tmp) {
    this.folders = tmp;
  }


  /**
   *  Sets the folders attribute of the PermissionCategory object
   *
   *@param  tmp  The new folders value
   */
  public void setFolders(String tmp) {
    this.folders = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the viewpoints attribute of the PermissionCategory object
   *
   *@param  tmp  The new viewpoints value
   */
  public void setViewpoints(boolean tmp) {
    this.viewpoints = tmp;
  }


  /**
   *  Sets the viewpoints attribute of the PermissionCategory object
   *
   *@param  tmp  The new viewpoints value
   */
  public void setViewpoints(String tmp) {
    this.viewpoints = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the reports attribute of the PermissionCategory object
   *
   *@param  tmp  The new reports value
   */
  public void setReports(boolean tmp) {
    this.reports = tmp;
  }


  /**
   *  Sets the reports attribute of the PermissionCategory object
   *
   *@param  tmp  The new reports value
   */
  public void setReports(String tmp) {
    this.reports = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the scheduledEvents attribute of the PermissionCategory object
   *
   *@param  tmp  The new scheduledEvents value
   */
  public void setScheduledEvents(boolean tmp) {
    this.scheduledEvents = tmp;
  }


  /**
   *  Sets the scheduledEvents attribute of the PermissionCategory object
   *
   *@param  tmp  The new scheduledEvents value
   */
  public void setScheduledEvents(String tmp) {
    this.scheduledEvents = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the objectEvents attribute of the PermissionCategory object
   *
   *@param  tmp  The new objectEvents value
   */
  public void setObjectEvents(boolean tmp) {
    this.objectEvents = tmp;
  }


  /**
   *  Sets the objectEvents attribute of the PermissionCategory object
   *
   *@param  tmp  The new objectEvents value
   */
  public void setObjectEvents(String tmp) {
    this.objectEvents = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Inserts a permission category object into the database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO permission_category (category, description, " +
        "level, enabled, active, lookups, folders, viewpoints, categories, scheduled_events, " +
        "object_events, reports) " +
        "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setString(++i, category);
    pst.setString(++i, description);
    pst.setInt(++i, level);
    pst.setBoolean(++i, enabled);
    pst.setBoolean(++i, active);
    pst.setBoolean(++i, lookups);
    pst.setBoolean(++i, folders);
    pst.setBoolean(++i, viewpoints);
    pst.setBoolean(++i, categories);
    pst.setBoolean(++i, scheduledEvents);
    pst.setBoolean(++i, objectEvents);
    pst.setBoolean(++i, reports);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "permission_cate_category_id_seq");
    return true;
  }


  /**
   *  Populates a permission category object from a database resultset
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("category_id");
    category = rs.getString("category");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    active = rs.getBoolean("active");
    folders = rs.getBoolean("folders");
    lookups = rs.getBoolean("lookups");
    viewpoints = rs.getBoolean("viewpoints");
    categories = rs.getBoolean("categories");
    scheduledEvents = rs.getBoolean("scheduled_events");
    objectEvents = rs.getBoolean("object_events");
    reports = rs.getBoolean("reports");
  }


  /**
   *  Enables or disables the permission report attribute based on the specified
   *  information
   *
   *@param  db                Description of the Parameter
   *@param  categoryId        Description of the Parameter
   *@param  enabled           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void updateReportAttribute(Connection db, int categoryId, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE permission_category " +
        "SET reports = ? " +
        "WHERE category_id = ? ");
    pst.setBoolean(1, enabled);
    pst.setInt(2, categoryId);
    pst.execute();
    pst.close();
  }
}

