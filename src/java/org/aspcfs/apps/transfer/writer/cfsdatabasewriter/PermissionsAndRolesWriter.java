package org.aspcfs.apps.transfer.writer.cfsdatabasewriter;

import org.aspcfs.apps.transfer.*;
import java.util.*;
import java.util.logging.*;
import org.aspcfs.utils.*;
import java.sql.*;
import org.aspcfs.modules.admin.base.*;
import com.darkhorseventures.database.*;

/**
 *  Inserts related PermissionCategory data into a database
 *
 *@author     matt rajkowski
 *@created    January 23, 2003
 *@version    $Id: PermissionsAndRolesWriter.java,v 1.13 2003/12/02 22:28:24
 *      mrajkowski Exp $
 */
public class PermissionsAndRolesWriter implements DataWriter {
  private ConnectionPool sqlDriver = null;
  private Connection db = null;
  private int id = -1;

  private String driver = null;
  private String url = null;
  private String user = null;
  private String pass = null;


  /**
   *  Sets the driver attribute of the PermissionsAndRolesWriter object
   *
   *@param  tmp  The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   *  Sets the url attribute of the PermissionsAndRolesWriter object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the user attribute of the PermissionsAndRolesWriter object
   *
   *@param  tmp  The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the pass attribute of the PermissionsAndRolesWriter object
   *
   *@param  tmp  The new pass value
   */
  public void setPass(String tmp) {
    this.pass = tmp;
  }


  /**
   *  Sets the autoCommit attribute of the PermissionsAndRolesWriter object
   *
   *@param  flag  The new autoCommit value
   */
  public void setAutoCommit(boolean flag) {
  }


  /**
   *  Gets the driver attribute of the PermissionsAndRolesWriter object
   *
   *@return    The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   *  Gets the url attribute of the PermissionsAndRolesWriter object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the user attribute of the PermissionsAndRolesWriter object
   *
   *@return    The user value
   */
  public String getUser() {
    return user;
  }


  /**
   *  Gets the pass attribute of the PermissionsAndRolesWriter object
   *
   *@return    The pass value
   */
  public String getPass() {
    return pass;
  }


  /**
   *  Gets the version attribute of the PermissionsAndRolesWriter object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the PermissionsAndRolesWriter object
   *
   *@return    The name value
   */
  public String getName() {
    return "Permissions and Roles Writer";
  }


  /**
   *  Gets the description attribute of the PermissionsAndRolesWriter object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Inserts data directly into a Dark Horse CRM database";
  }


  /**
   *  Gets the lastResponse attribute of the PermissionsAndRolesWriter object
   *
   *@return    The lastResponse value
   */
  public String getLastResponse() {
    return String.valueOf(id);
  }


  /**
   *  Gets the configured attribute of the PermissionsAndRolesWriter object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    String tmpUrl = System.getProperty("url");
    if (tmpUrl != null) {
      url = tmpUrl;
    }

    String tmpDriver = System.getProperty("driver");
    if (tmpDriver != null) {
      driver = tmpDriver;
    }

    String tmpUser = System.getProperty("user");
    if (tmpUser != null) {
      user = tmpUser;
    }

    String tmpPass = System.getProperty("pass");
    if (tmpPass != null) {
      pass = tmpPass;
    }

    if (url == null) {
      return false;
    }

    //Connect to database
    try {
      sqlDriver = new ConnectionPool();
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(1);
      ConnectionElement thisElement = new ConnectionElement(
          url, user, pass);
      thisElement.setDriver(driver);
      db = sqlDriver.getConnection(thisElement);
    } catch (SQLException e) {
      logger.info("Could not get database connection" + e.toString());
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  record  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean save(DataRecord record) {
    try {
      if (record.getName().equals("permissionCategory")) {
        PermissionCategory thisCategory = new PermissionCategory();
        thisCategory.setCategory(record.getValue("category"));
        thisCategory.setLevel(record.getValue("level"));
        thisCategory.setEnabled(record.getValue("enabled"));
        thisCategory.setActive(record.getValue("active"));
        thisCategory.setFolders(record.getValue("folders"));
        thisCategory.setLookups(record.getValue("lookups"));
        thisCategory.setViewpoints(record.getValue("viewpoints"));
        thisCategory.setReports(record.getValue("reports"));
        thisCategory.setCategories(record.getValue("categories"));
        thisCategory.setScheduledEvents(record.getValue("scheduledEvents"));
        thisCategory.setObjectEvents(record.getValue("objectEvents"));
        thisCategory.insert(db);
        id = thisCategory.getId();
        return true;
      }

      if (record.getName().equals("permission")) {
        Permission thisPermission = new Permission();
        thisPermission.setCategoryId(record.getValue("categoryId"));
        thisPermission.setPermissionLevel(record.getValue("permissionLevel"));
        thisPermission.setName(record.getValue("name"));
        thisPermission.setDescription(record.getValue("description"));
        thisPermission.setView(record.getValue("view"));
        thisPermission.setAdd(record.getValue("add"));
        thisPermission.setEdit(record.getValue("edit"));
        thisPermission.setDelete(record.getValue("delete"));
        thisPermission.setEnabled(record.getValue("enabled"));
        thisPermission.setActive(record.getValue("active"));
        thisPermission.setViewpoints(record.getValue("viewpoints"));
        thisPermission.insert(db);
        id = thisPermission.getId();
        return true;
      }

      if (record.getName().equals("folder")) {
        try {
          PreparedStatement pst = db.prepareStatement(
              "INSERT INTO module_field_categorylink " +
              "(module_id, category_id, level, description) " +
              "VALUES (?, ?, ?, ?) ");
          pst.setInt(1, record.getIntValue("moduleId"));
          pst.setInt(2, record.getIntValue("categoryId"));
          pst.setInt(3, record.getIntValue("level"));
          pst.setString(4, record.getValue("description"));
          pst.executeUpdate();
          pst.close();
        } catch (SQLException e) {
          e.printStackTrace(System.out);
          return false;
        }
        return true;
      }

      if (record.getName().equals("lookup")) {
        try {
          PreparedStatement pst = db.prepareStatement(
              "INSERT INTO lookup_lists_lookup " +
              "(module_id, lookup_id, class_name, table_name, level, description, category_id) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?) ");
          pst.setInt(1, record.getIntValue("moduleId"));
          pst.setInt(2, record.getIntValue("lookupId"));
          pst.setString(3, record.getValue("class"));
          pst.setString(4, record.getValue("table"));
          pst.setInt(5, record.getIntValue("level"));
          pst.setString(6, record.getValue("description"));
          pst.setInt(7, record.getIntValue("categoryId"));
          pst.executeUpdate();
          pst.close();
        } catch (SQLException e) {
          e.printStackTrace(System.out);
          return false;
        }
        return true;
      }

      if (record.getName().equals("report")) {
        try {
          PreparedStatement pst = db.prepareStatement(
              "INSERT INTO report " +
              "(category_id, permission_id, filename, type, title, description, " +
              "enteredby, modifiedby) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
          pst.setInt(1, record.getIntValue("categoryId"));
          DatabaseUtils.setInt(pst, 2, record.getIntValue("permissionId"));
          pst.setString(3, record.getValue("file"));
          pst.setInt(4, record.getIntValue("type"));
          pst.setString(5, record.getValue("title"));
          pst.setString(6, record.getValue("description"));
          pst.setInt(7, record.getIntValue("enteredBy"));
          pst.setInt(8, record.getIntValue("modifiedBy"));
          pst.executeUpdate();
          pst.close();
        } catch (SQLException e) {
          e.printStackTrace(System.out);
          return false;
        }
        return true;
      }

      if (record.getName().equals("multipleCategory")) {
        try {
          PreparedStatement pst = db.prepareStatement(
              "INSERT INTO category_editor_lookup " +
              "(module_id, constant_id, table_name, level, description, category_id, max_levels) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?) ");
          pst.setInt(1, record.getIntValue("moduleId"));
          pst.setInt(2, record.getIntValue("constantId"));
          pst.setString(3, record.getValue("table"));
          pst.setInt(4, record.getIntValue("level"));
          pst.setString(5, record.getValue("description"));
          pst.setInt(6, record.getIntValue("categoryId"));
          pst.setInt(7, record.getIntValue("maxLevels"));
          pst.executeUpdate();
          pst.close();
        } catch (SQLException e) {
          e.printStackTrace(System.out);
          return false;
        }
        return true;
      }

      if (record.getName().equals("role")) {
        Role thisRole = new Role();
        thisRole.setRole(record.getValue("role"));
        thisRole.setDescription(record.getValue("description"));
        thisRole.setEnteredBy(0);
        thisRole.setModifiedBy(0);
        thisRole.insert(db);
        id = thisRole.getId();
        return true;
      }

      if (record.getName().equals("rolePermission")) {
        RolePermission thisRolePermission = new RolePermission();
        thisRolePermission.setRoleId(record.getValue("roleId"));
        thisRolePermission.setPermissionId(record.getValue("permissionId"));
        thisRolePermission.setView(record.getValue("view"));
        thisRolePermission.setAdd(record.getValue("add"));
        thisRolePermission.setEdit(record.getValue("edit"));
        thisRolePermission.setDelete(record.getValue("delete"));
        thisRolePermission.insert(db);
        id = thisRolePermission.getId();
        return true;
      }
    } catch (Exception ex) {
      ex.printStackTrace(System.out);
      logger.info(ex.toString());
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean commit() {
    return true;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean rollback() {
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  record  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean load(DataRecord record) {
    return false;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean close() {
    if (db != null) {
      try {
        db.close();
      } catch (Exception e) {
      }
    }
    return true;
  }
}


