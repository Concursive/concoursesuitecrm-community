//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.database.Connection;
import com.darkhorseventures.framework.beans.*;
import org.aspcfs.modules.utils.DatabaseUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class PermissionCategory extends GenericBean {

  private int id = -1;
  private String category = null;
  private String description = null;
  private int level = -1;
  private boolean enabled = false;
  private boolean active = false;
  private boolean lookups = false;
  private boolean folders = false;

  public final static int PERMISSION_CAT_LEADS = 4;
  public final static int LOOKUP_LEADS_STAGE = 1;
  public final static int LOOKUP_LEADS_TYPE = 2;

  public final static int PERMISSION_CAT_ACCOUNTS = 1;
  public final static int LOOKUP_ACCOUNTS_TYPE = 1;
  public final static int LOOKUP_ACCOUNTS_REVENUE_TYPE = 2;
  public final static int LOOKUP_ACCOUNTS_CONTACTS_TYPE = 3;

  public final static int PERMISSION_CAT_CONTACTS = 2;
  public final static int LOOKUP_CONTACTS_TYPE = 1;
  public final static int LOOKUP_CONTACTS_EMAIL = 2;
  public final static int LOOKUP_CONTACTS_ADDRESS = 3;
  public final static int LOOKUP_CONTACTS_PHONE = 4;
  public final static int LOOKUP_CONTACTS_DEPT = 5;

  public final static int PERMISSION_CAT_TICKETS = 8;
  public final static int LOOKUP_TICKETS_SOURCE = 1;
  public final static int LOOKUP_TICKETS_SEVERITY = 2;
  public final static int LOOKUP_TICKETS_PRIORITY = 3;


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
   *  Description of the Method
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
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Permission category not found");
    }
    rs.close();
    pst.close();
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO permission_category (category, description, " +
        "level, enabled, active, lookups, folders) " +
        "VALUES ( ?, ?, ?, ?, ?, ?, ? ) ");
    int i = 0;
    pst.setString(++i, category);
    pst.setString(++i, description);
    pst.setInt(++i, level);
    pst.setBoolean(++i, enabled);
    pst.setBoolean(++i, active);
    pst.setBoolean(++i, lookups);
    pst.setBoolean(++i, folders);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "permission_cate_category_id_seq");
    return true;
  }


  /**
   *  Description of the Method
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
  }

}

