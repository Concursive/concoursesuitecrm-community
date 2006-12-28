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
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a module category that has capabilities (lists, reports, etc.)
 * and contains permissions for various actions
 *
 * @author     Mathur
 * @created    January 13, 2003
 * @version    $Id: PermissionCategory.java,v 1.9 2003/03/07 14:05:30 mrajkowski
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
  private boolean webdav = false;
  private boolean logos = false;
  private boolean actionPlans = false;
  private boolean customListViews = false;

  private int constant = -1;

  //Permission types to view/add/edit/delete records Constants
  //Always the following holds. Any new permission types must conform to the following rule
  //Delete permission > Edit permisison > Add permission > View permission > None
  public final static int NONE = -1;
  public final static int VIEW = 1;
  public final static int ADD = 2;
  public final static int EDIT = 3;
  public final static int DELETE = 4;

  //Constants for working with lookup lists
  //NOTE: currently all editable lookup lists need to be defined here
  //Leads
  public final static int PERMISSION_CAT_LEADS = 4;
  public final static int LOOKUP_LEADS_STAGE = 1;
  public final static int LOOKUP_LEADS_TYPE = 2;
  public final static int LOOKUP_OPPORTUNITY_ENVIRONMENT = 707050229;
  public final static int LOOKUP_OPPORTUNITY_COMPETITORS = 707050230;
  public final static int LOOKUP_OPPORTUNITY_COMPELLING_EVENT = 707050231;
  public final static int LOOKUP_OPPORTUNITY_BUDGET = 707050232;
  public final static int LOOKUP_CALL_TYPES = 206061500;

  //Accounts
  public final static int PERMISSION_CAT_ACCOUNTS = 1;
  public final static int LOOKUP_ACCOUNTS_TYPE = 1;
  public final static int LOOKUP_ACCOUNTS_REVENUE_TYPE = 2;
  public final static int LOOKUP_ACCOUNTS_CONTACTS_TYPE = 3;
  public final static int LOOKUP_ACCOUNTS_EMAIL = 819041648;
  public final static int LOOKUP_ACCOUNTS_ADDRESS = 819041649;
  public final static int LOOKUP_ACCOUNTS_PHONE = 819041650;
  public final static int LOOKUP_ACCOUNTS_INDUSTRY = 302051030;
  public final static int LOOKUP_ACCOUNTS_SEGMENT = 128051430;
  public final static int LOOKUP_ACCOUNTS_SUBSEGMENT = 128051431;
  public final static int LOOKUP_ACCOUNTS_SITE = 128051432;
  public final static int LOOKUP_ACCOUNTS_SIZE = 158051538;
  public final static int LOOKUP_SITE_ID = 813200314;
  public final static int LOOKUP_ACCOUNT_SIZE = 819200310;
  public final static int LOOKUP_TITLE = 904200315;
  public final static int LOOKUP_CONTACT_SOURCE = 904200316;
  public final static int LOOKUP_SEGMENTS = 722031447;
  public final static int LOOKUP_SUB_SEGMENT = 909200314;
  public final static int LOOKUP_ACCOUNTS_STAGE = 181206161;

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
  public final static int LOOKUP_ASSET_MATERIALS = 928051135;
  public final static int MULTIPLE_CATEGORY_ASSET = 202041400;
  public final static int LOOKUP_ASSET_VENDOR = 910040914;
  public final static int LOOKUP_ASSET_MANUFACTURER = 910040915;

  //Contacts
  public final static int PERMISSION_CAT_CONTACTS = 2;
  public final static int LOOKUP_CONTACTS_TYPE = 1;
  public final static int LOOKUP_CONTACTS_EMAIL = 2;
  public final static int LOOKUP_CONTACTS_ADDRESS = 3;
  public final static int LOOKUP_CONTACTS_PHONE = 4;
  public final static int LOOKUP_CONTACTS_IMTYPES = 111051308;
  public final static int LOOKUP_CONTACTS_IMSERVICES = 111051352;
  public final static int LOOKUP_CONTACTS_TEXT = 111051354;
  public final static int LOOKUP_ACCOUNTS_TITLE = 128051433;

  //Employees
  public final static int PERMISSION_CAT_EMPLOYEES = 1111031131;
  public final static int LOOKUP_CONTACTS_DEPT = 1111031132;

  //Tickets
  public final static int PERMISSION_CAT_TICKETS = 8;
  public final static int LOOKUP_TICKETS_SOURCE = 1;
  public final static int LOOKUP_TICKETS_SEVERITY = 2;
  public final static int LOOKUP_TICKETS_PRIORITY = 3;
  public final static int LOOKUP_TICKET_FORM = 127041246;
  public final static int LOOKUP_TICKET_CAUSE = 922051718;
  public final static int LOOKUP_TICKET_RESOLUTION = 922051719;
  public final static int LOOKUP_TICKET_TASK_CATEGORY = 101705190;
  public final static int MULTIPLE_CATEGORY_TICKET = 202041401;
  public final static int LOOKUP_TICKET_ESCALATION = 910040913;
  public final static int LOOKUP_TICKET_STATE = 112205113;
  public final static int CUSTOM_LIST_VIEWS_TICKET = 113051436;

  //product catalog
  public final static int PERMISSION_CAT_PRODUCT_CATALOG = 330041409;
  public final static int LOOKUP_PRODUCT_TYPE = 1017040901;
  public final static int LOOKUP_PRODUCT_FORMAT = 1017040902;
  public final static int LOOKUP_PRODUCT_SHIPPING = 1017040903;
  public final static int LOOKUP_PRODUCT_SHIP_TIME = 1017040904;
  public final static int LOOKUP_PRODUCT_CATEGORY_TYPE = 1017040905;
  public final static int LOOKUP_PRODUCT_TAX = 1017040906;
  public final static int LOOKUP_CURRENCY = 1017040907;
  public final static int LOOKUP_RECURRING_TYPE = 1017040908;
  public final static int LOOKUP_MANUFACTURER_TYPE = 1017040909;

  //quotes
  public final static int PERMISSION_CAT_QUOTES = 420041017;
  public final static int LOOKUP_QUOTE_STATUS = 1123041000;
  public final static int LOOKUP_QUOTE_TYPES = 1123041001;
  public final static int LOOKUP_QUOTE_TERMS = 1123041002;
  public final static int LOOKUP_QUOTE_SOURCE = 1123041003;
  public final static int LOOKUP_QUOTE_DELIVERY = 1123041004;
  public final static int LOOKUP_QUOTE_CONDITION = 1123041005;
  public final static int LOOKUP_QUOTE_REMARKS = 1123041006;

  //documents module
  public final static int PERMISSION_CAT_DOCUMENTS = 1202041528;

  //leads
  public final static int PERMISSION_CAT_SALES = 228051100;
  public final static int LOOKUP_SALES_STATUS = 228051101;
  public final static int LOOKUP_CONTACTS_RATING = 228051102;
  public final static int LOOKUP_CONTACTS_SOURCE = 228051103;

  //Website module
  public final static int PERMISSION_CAT_WEBSITE = 223061200;

  //NetApp
  public final static int PERMISSION_NET_APP = 1021051129;

  //Admin
  public final static int PERMISSION_CAT_ADMIN = 9;
  public final static int MULTIPLE_CATEGORY_ACTIONPLAN = 907051127;


  /**
   * Constructor for the PermissionCategory object
   */
  public PermissionCategory() {
  }


  /**
   * Constructor for the PermissionCategory object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public PermissionCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the PermissionCategory object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public PermissionCategory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Populates the permission category from the database with the specified id
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
   * Returns a permission category id for the specified category name by lookup
   * up the value in the database and returning the first id found.
   *
   * @param  db             Description of the Parameter
   * @param  name           Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
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
   *  Returns a permission category id for the specified constant by lookup up
   *  the value in the database and returning the first id found.
   *
   * @param  db             Description of the Parameter
   * @param  constantId     Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int lookupId(Connection db, int constantId) throws SQLException {
    if (constantId == -1) {
      throw new SQLException("Invalid Permission Category Name");
    }
    int i = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT category_id " +
        "FROM permission_category " +
        "WHERE constant = ? ");
    pst.setInt(1, constantId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      i = rs.getInt("category_id");
    }
    rs.close();
    pst.close();
    return i;
  }


  /**
   *  Gets the customListViews attribute of the PermissionCategory object
   *
   * @return    The customListViews value
   */
  public boolean getCustomListViews() {
    return customListViews;
  }


  /**
   *  Sets the customListViews attribute of the PermissionCategory object
   *
   * @param  tmp  The new customListViews value
   */
  public void setCustomListViews(boolean tmp) {
    this.customListViews = tmp;
  }


  /**
   *  Sets the customListViews attribute of the PermissionCategory object
   *
   * @param  tmp  The new customListViews value
   */
  public void setCustomListViews(String tmp) {
    this.customListViews = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the webdav attribute of the PermissionCategory object
   *
   * @param  tmp  The new webdav value
   */
  public void setWebdav(boolean tmp) {
    this.webdav = tmp;
  }


  /**
   * Sets the webdav attribute of the PermissionCategory object
   *
   * @param  tmp  The new webdav value
   */
  public void setWebdav(String tmp) {
    this.webdav = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the webdav attribute of the PermissionCategory object
   *
   * @return    The webdav value
   */
  public boolean getWebdav() {
    return webdav;
  }


  /**
   * Sets the logos attribute of the PermissionCategory object
   *
   * @param  tmp  The new logos value
   */
  public void setLogos(boolean tmp) {
    this.logos = tmp;
  }


  /**
   * Sets the logos attribute of the PermissionCategory object
   *
   * @param  tmp  The new logos value
   */
  public void setLogos(String tmp) {
    this.logos = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the logos attribute of the PermissionCategory object
   *
   * @return    The logos value
   */
  public boolean getLogos() {
    return logos;
  }


  /**
   * Sets the constant attribute of the PermissionCategory object
   *
   * @param  tmp  The new constant value
   */
  public void setConstant(int tmp) {
    this.constant = tmp;
  }


  /**
   * Sets the constant attribute of the PermissionCategory object
   *
   * @param  tmp  The new constant value
   */
  public void setConstant(String tmp) {
    this.constant = Integer.parseInt(tmp);
  }


  /**
   * Gets the constant attribute of the PermissionCategory object
   *
   * @return    The constant value
   */
  public int getConstant() {
    return constant;
  }


  /**
   * Sets the id attribute of the PermissionCategory object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the PermissionCategory object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the category attribute of the PermissionCategory object
   *
   * @param  tmp  The new category value
   */
  public void setCategory(String tmp) {
    this.category = tmp;
  }


  /**
   * Sets the description attribute of the PermissionCategory object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the level attribute of the PermissionCategory object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the PermissionCategory object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the PermissionCategory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the PermissionCategory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the active attribute of the PermissionCategory object
   *
   * @param  tmp  The new active value
   */
  public void setActive(boolean tmp) {
    this.active = tmp;
  }


  /**
   * Sets the active attribute of the PermissionCategory object
   *
   * @param  tmp  The new active value
   */
  public void setActive(String tmp) {
    this.active = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the categories attribute of the PermissionCategory object
   *
   * @param  categories  The new categories value
   */
  public void setCategories(boolean categories) {
    this.categories = categories;
  }


  /**
   * Sets the categories attribute of the PermissionCategory object
   *
   * @param  categories  The new categories value
   */
  public void setCategories(String categories) {
    this.categories = DatabaseUtils.parseBoolean(categories);
  }


  /**
   * Gets the categories attribute of the PermissionCategory object
   *
   * @return    The categories value
   */
  public boolean getCategories() {
    return categories;
  }


  /**
   * Gets the id attribute of the PermissionCategory object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the category attribute of the PermissionCategory object
   *
   * @return    The category value
   */
  public String getCategory() {
    return category;
  }


  /**
   * Gets the description attribute of the PermissionCategory object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the level attribute of the PermissionCategory object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Gets the enabled attribute of the PermissionCategory object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the active attribute of the PermissionCategory object
   *
   * @return    The active value
   */
  public boolean getActive() {
    return active;
  }


  /**
   * Gets the lookups attribute of the PermissionCategory object
   *
   * @return    The lookups value
   */
  public boolean getLookups() {
    return lookups;
  }


  /**
   * Gets the folders attribute of the PermissionCategory object
   *
   * @return    The folders value
   */
  public boolean getFolders() {
    return folders;
  }


  /**
   * Gets the viewpoints attribute of the PermissionCategory object
   *
   * @return    The viewpoints value
   */
  public boolean getViewpoints() {
    return viewpoints;
  }


  /**
   * Gets the reports attribute of the PermissionCategory object
   *
   * @return    The reports value
   */
  public boolean getReports() {
    return reports;
  }

  /**
   * Gets the scheduledEvents attribute of the PermissionCategory object
   *
   * @return    The scheduledEvents value
   */
  public boolean getScheduledEvents() {
    return scheduledEvents;
  }


  /**
   * Gets the objectEvents attribute of the PermissionCategory object
   *
   * @return    The objectEvents value
   */
  public boolean getObjectEvents() {
    return objectEvents;
  }


  /**
   * Sets the lookups attribute of the PermissionCategory object
   *
   * @param  tmp  The new lookups value
   */
  public void setLookups(boolean tmp) {
    this.lookups = tmp;
  }


  /**
   * Sets the lookups attribute of the PermissionCategory object
   *
   * @param  tmp  The new lookups value
   */
  public void setLookups(String tmp) {
    this.lookups = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the folders attribute of the PermissionCategory object
   *
   * @param  tmp  The new folders value
   */
  public void setFolders(boolean tmp) {
    this.folders = tmp;
  }


  /**
   * Sets the folders attribute of the PermissionCategory object
   *
   * @param  tmp  The new folders value
   */
  public void setFolders(String tmp) {
    this.folders = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the viewpoints attribute of the PermissionCategory object
   *
   * @param  tmp  The new viewpoints value
   */
  public void setViewpoints(boolean tmp) {
    this.viewpoints = tmp;
  }


  /**
   * Sets the viewpoints attribute of the PermissionCategory object
   *
   * @param  tmp  The new viewpoints value
   */
  public void setViewpoints(String tmp) {
    this.viewpoints = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the reports attribute of the PermissionCategory object
   *
   * @param  tmp  The new reports value
   */
  public void setReports(boolean tmp) {
    this.reports = tmp;
  }


  /**
   * Sets the reports attribute of the PermissionCategory object
   *
   * @param  tmp  The new reports value
   */
  public void setReports(String tmp) {
    this.reports = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the scheduledEvents attribute of the PermissionCategory object
   *
   * @param  tmp  The new scheduledEvents value
   */
  public void setScheduledEvents(boolean tmp) {
    this.scheduledEvents = tmp;
  }


  /**
   * Sets the scheduledEvents attribute of the PermissionCategory object
   *
   * @param  tmp  The new scheduledEvents value
   */
  public void setScheduledEvents(String tmp) {
    this.scheduledEvents = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the objectEvents attribute of the PermissionCategory object
   *
   * @param  tmp  The new objectEvents value
   */
  public void setObjectEvents(boolean tmp) {
    this.objectEvents = tmp;
  }


  /**
   * Sets the objectEvents attribute of the PermissionCategory object
   *
   * @param  tmp  The new objectEvents value
   */
  public void setObjectEvents(String tmp) {
    this.objectEvents = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the actionPlans attribute of the PermissionCategory object
   *
   * @return    The actionPlans value
   */
  public boolean getActionPlans() {
    return actionPlans;
  }


  /**
   *  Sets the actionPlans attribute of the PermissionCategory object
   *
   * @param  tmp  The new actionPlans value
   */
  public void setActionPlans(boolean tmp) {
    this.actionPlans = tmp;
  }


  /**
   *  Sets the actionPlans attribute of the PermissionCategory object
   *
   * @param  tmp  The new actionPlans value
   */
  public void setActionPlans(String tmp) {
    this.actionPlans = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Inserts a permission category object into the database
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (constant == -1) {
      throw new SQLException("PermissionCategory constantId cannot be -1");
    }
    id = DatabaseUtils.getNextSeq(db, "permission_cate_category_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO permission_category (" + (id > -1 ? "category_id, " : "") + "category, description, " +
        DatabaseUtils.addQuotes(db, "level") +
        ", enabled, " + DatabaseUtils.addQuotes(db, "active") +
        ", lookups, folders, viewpoints, categories, scheduled_events, " +
        "object_events, reports, webdav, logos, constant, action_plans, custom_list_views) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
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
    pst.setBoolean(++i, webdav);
    pst.setBoolean(++i, logos);
    pst.setInt(++i, constant);
    pst.setBoolean(++i, actionPlans);
    pst.setBoolean(++i, customListViews);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "permission_cate_category_id_seq", id);
    return true;
  }


  /**
   * Populates a permission category object from a database resultset
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
    webdav = rs.getBoolean("webdav");
    logos = rs.getBoolean("logos");
    constant = rs.getInt("constant");
    actionPlans = rs.getBoolean("action_plans");
    customListViews = rs.getBoolean("custom_list_views");
  }


  /**
   * Enables or disables the permission report attribute based on the specified
   * information
   *
   * @param  db             Description of the Parameter
   * @param  categoryId     Description of the Parameter
   * @param  enabled        Description of the Parameter
   * @throws  SQLException  Description of the Exception
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


  /**
   * Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  categoryId     Description of the Parameter
   * @param  enabled        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void updateWebdavAttribute(Connection db, int categoryId, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE permission_category " +
        "SET webdav = ? " +
        "WHERE category_id = ? ");
    pst.setBoolean(1, enabled);
    pst.setInt(2, categoryId);
    pst.execute();
    pst.close();
  }

  
  /**
   * Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  categoryId     Description of the Parameter
   * @param  enabled        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void updateLogosAttribute(Connection db, int categoryId, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE permission_category " +
        "SET logos = ? " +
        "WHERE category_id = ? ");
    pst.setBoolean(1, enabled);
    pst.setInt(2, categoryId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  categoryId        Description of the Parameter
   * @param  enabled           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void updateFoldersAttribute(Connection db, int categoryId, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE permission_category " +
        "SET folders = ? " +
        "WHERE category_id = ? ");
    pst.setBoolean(1, enabled);
    pst.setInt(2, categoryId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  categoryId        Description of the Parameter
   * @param  enabled           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void updateActionPlansAttribute(Connection db, int categoryId, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE permission_category " +
        "SET action_plans = ? " +
        "WHERE category_id = ? ");
    pst.setBoolean(1, enabled);
    pst.setInt(2, categoryId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  categoryId        Description of the Parameter
   * @param  enabled           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void updateCustomListViewsAttribute(Connection db, int categoryId, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE permission_category " +
        "SET custom_list_views = ? " +
        "WHERE category_id = ? ");
    pst.setBoolean(1, enabled);
    pst.setInt(2, categoryId);
    pst.execute();
    pst.close();
  }
}