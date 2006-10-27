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
package org.aspcfs.apps.transfer.writer.cfsdatabasewriter;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.zeroio.webdav.base.WebdavModule;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.PlanEditor;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.ModuleFieldCategoryLink;
import org.aspcfs.modules.reports.base.Report;
import org.aspcfs.utils.web.LookupListElement;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Inserts related PermissionCategory data into a database
 *
 * @author matt rajkowski
 * @version $Id: PermissionsAndRolesWriter.java,v 1.13 2003/12/02 22:28:24
 *          mrajkowski Exp $
 * @created January 23, 2003
 */
public class PermissionsAndRolesWriter
    implements DataWriter {
  private ConnectionPool sqlDriver = null;
  private Connection db = null;
  private int id = -1;

  private String driver = null;
  private String url = null;
  private String user = null;
  private String pass = null;


  /**
   * Sets the driver attribute of the PermissionsAndRolesWriter object
   *
   * @param tmp The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   * Sets the url attribute of the PermissionsAndRolesWriter object
   *
   * @param tmp The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   * Sets the user attribute of the PermissionsAndRolesWriter object
   *
   * @param tmp The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   * Sets the pass attribute of the PermissionsAndRolesWriter object
   *
   * @param tmp The new pass value
   */
  public void setPass(String tmp) {
    this.pass = tmp;
  }


  /**
   * Sets the autoCommit attribute of the PermissionsAndRolesWriter object
   *
   * @param flag The new autoCommit value
   */
  public void setAutoCommit(boolean flag) {
  }


  /**
   * Gets the driver attribute of the PermissionsAndRolesWriter object
   *
   * @return The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   * Gets the url attribute of the PermissionsAndRolesWriter object
   *
   * @return The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   * Gets the user attribute of the PermissionsAndRolesWriter object
   *
   * @return The user value
   */
  public String getUser() {
    return user;
  }


  /**
   * Gets the pass attribute of the PermissionsAndRolesWriter object
   *
   * @return The pass value
   */
  public String getPass() {
    return pass;
  }


  /**
   * Gets the version attribute of the PermissionsAndRolesWriter object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the PermissionsAndRolesWriter object
   *
   * @return The name value
   */
  public String getName() {
    return "Permissions and Roles Writer";
  }


  /**
   * Gets the description attribute of the PermissionsAndRolesWriter object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Inserts data directly into a Centric CRM database";
  }


  /**
   * Sets the db attribute of the PermissionsAndRolesWriter object
   *
   * @param db The new db value
   */
  public void setDb(Connection db) {
    this.db = db;
  }


  /**
   * Gets the lastResponse attribute of the PermissionsAndRolesWriter object
   *
   * @return The lastResponse value
   */
  public String getLastResponse() {
    return String.valueOf(id);
  }


  /**
   * Gets the configured attribute of the PermissionsAndRolesWriter object
   *
   * @return The configured value
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
      ConnectionElement thisElement = new ConnectionElement(url, user, pass);
      thisElement.setDriver(driver);
      db = sqlDriver.getConnection(thisElement);
    } catch (SQLException e) {
      logger.info("Could not get database connection" + e.toString());
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param record Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean save(DataRecord record) {
    try {
      if (record.getName().equals("permissionCategory")) {
        PermissionCategory thisCategory = new PermissionCategory();
        thisCategory.setConstant(record.getValue("constant"));
        thisCategory.setCategory(record.getValue("category"));
        thisCategory.setLevel(Integer.parseInt(record.getValue("level")));
        thisCategory.setEnabled(record.getValue("enabled"));
        thisCategory.setActive(record.getValue("active"));
        thisCategory.setFolders(record.getValue("folders"));
        thisCategory.setLookups(record.getValue("lookups"));
        thisCategory.setViewpoints(record.getValue("viewpoints"));
        thisCategory.setReports(record.getValue("reports"));
        thisCategory.setCategories(record.getValue("categories"));
        thisCategory.setScheduledEvents(record.getValue("scheduledEvents"));
        thisCategory.setObjectEvents(record.getValue("objectEvents"));
        thisCategory.setWebdav(record.getValue("webdav"));
        thisCategory.setLogos(record.getValue("logos"));
        thisCategory.setActionPlans(record.getValue("actionPlans"));
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
        ModuleFieldCategoryLink categoryLink = new ModuleFieldCategoryLink();
        categoryLink.setModuleId(record.getIntValue("moduleId"));
        categoryLink.setCategoryId(record.getIntValue("categoryId"));
        categoryLink.setLevel(record.getIntValue("level"));
        categoryLink.setDescription(record.getValue("description"));
        categoryLink.insert(db);
        return true;
      }

      if (record.getName().equals("planEditor")) {
        PlanEditor editor = new PlanEditor();
        editor.setModuleId(record.getIntValue("moduleId"));
        editor.setConstantId(ActionPlan.getMapIdGivenConstantId(db, record.getIntValue("constantId")));
        editor.setLevel(record.getIntValue("level"));
        editor.setDescription(record.getValue("description"));
        editor.setCategoryId(record.getIntValue("categoryId"));
        editor.insert(db);
        return true;
      }

      if (record.getName().equals("lookup")) {
        LookupListElement lookupList = new LookupListElement();
        lookupList.setModuleId(record.getIntValue("moduleId"));
        lookupList.setLookupId(record.getIntValue("lookupId"));
        lookupList.setClassName(record.getValue("class"));
        lookupList.setTableName(record.getValue("table"));
        lookupList.setLevel(record.getIntValue("level"));
        lookupList.setDescription(record.getValue("description"));
        lookupList.setCategoryId(record.getIntValue("categoryId"));
        lookupList.insert(db);
        id = lookupList.getId();
        return true;
      }

      if (record.getName().equals("report")) {
        Report report = new Report();
        report.setCategoryId(record.getIntValue("categoryId"));
        report.setPermissionId(record.getIntValue("permissionId"));
        report.setFilename(record.getValue("file"));
        report.setType(record.getIntValue("type"));
        report.setTitle(record.getValue("title"));
        report.setDescription(record.getValue("description"));
        report.setEnteredBy(record.getIntValue("enteredBy"));
        report.setModifiedBy(record.getIntValue("modifiedBy"));
        report.insert(db);
        id = report.getId();
        return true;
      }

      if (record.getName().equals("multipleCategory")) {
        CategoryEditor categoryEditor = new CategoryEditor();
        categoryEditor.setModuleId(record.getIntValue("moduleId"));
        categoryEditor.setConstantId(record.getIntValue("constantId"));
        categoryEditor.setTableName(record.getValue("table"));
        categoryEditor.setLevel(record.getIntValue("level"));
        categoryEditor.setDescription(record.getValue("description"));
        categoryEditor.setCategoryId(record.getIntValue("categoryId"));
        categoryEditor.setMaxLevels(record.getIntValue("maxLevels"));
        categoryEditor.insert(db);
        return true;
      }

      if (record.getName().equals("customListViewCategory")) {
        CustomListViewEditor listViewEditor = new CustomListViewEditor();
        listViewEditor.setModuleId(record.getIntValue("moduleId"));
        listViewEditor.setConstantId(record.getIntValue("constantId"));
        listViewEditor.setDescription(record.getValue("description"));
        listViewEditor.setCategoryId(record.getIntValue("categoryId"));
        listViewEditor.insert(db);
        return true;
      }

      if (record.getName().equals("role")) {
        Role thisRole = new Role();
        thisRole.setRole(record.getValue("role"));
        thisRole.setDescription(record.getValue("description"));
        thisRole.setRoleType(record.getValue("type"));
        thisRole.setEnteredBy(0);
        thisRole.setModifiedBy(0);
        thisRole.setEnabled(record.getValue("enabled"));
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

      if (record.getName().equals("webdav")) {
        try {
          WebdavModule webdav = new WebdavModule();
          webdav.setCategoryId(record.getIntValue("categoryId"));
          webdav.setClassName(record.getValue("class"));
          webdav.setEnteredBy(0);
          webdav.setModifiedBy(0);
          webdav.insert(db);
          id = webdav.getId();
        } catch (SQLException e) {
          e.printStackTrace(System.out);
          return false;
        }
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
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean commit() {
    return true;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean rollback() {
    return true;
  }


  /**
   * Description of the Method
   *
   * @param record Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean load(DataRecord record) {
    return false;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
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


  /**
   * Description of the Method
   */
  public void initialize() {
  }
}

