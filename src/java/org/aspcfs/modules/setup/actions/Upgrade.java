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
package org.aspcfs.modules.setup.actions;

import bsh.Interpreter;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.apps.help.ImportHelp;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.system.base.ApplicationVersion;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.FileUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.File;

/**
 *  Actions that facilitate upgrading an installation of Centric CRM
 *
 *@author     matt rajkowski
 *@created    June 16, 2004
 *@version    $Id$
 */
public class Upgrade extends CFSModule {

  /**
   *  Prepares instruction page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    addModuleBean(context, null, "Upgrade");
    return "NeedUpgradeOK";
  }


  /**
   *  Checks to see what version is installed and what the version should be
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCheck(ActionContext context) {
    if (!isAdministrator(context)) {
      return "NeedUpgradeOK";
    }
    addModuleBean(context, null, "Upgrade");
    // Check version info
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    if (prefs.isUpgradeable()) {
      // Something needs updating
      context.getRequest().setAttribute("status", "1");
    } else {
      // All ok
      context.getRequest().setAttribute("status", "0");
    }
    context.getRequest().setAttribute("installedVersion", ApplicationVersion.getInstalledVersion(prefs));
    context.getRequest().setAttribute("newVersion", ApplicationVersion.VERSION);
    return "CheckOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public synchronized String executeCommandPerformUpgrade(ActionContext context) {
    if (!isAdministrator(context)) {
      return "NeedUpgradeOK";
    }
    addModuleBean(context, null, "Upgrade");
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    if (prefs.isUpgradeable()) {
      // Create a log of upgrade events
      ArrayList installLog = new ArrayList();
      context.getRequest().setAttribute("installLog", installLog);
      // Display upgrade information
      context.getRequest().setAttribute("installedVersion", ApplicationVersion.getInstalledVersion(prefs));
      context.getRequest().setAttribute("newVersion", ApplicationVersion.VERSION);
      Connection db = null;
      String versionToInstall = null;
      try {
        boolean buildHelp = false;
        // Get a connection from the connection pool for this user
        db = this.getConnection(context);
        // BEGIN DHV CODE ONLY
        // Prepare bean shell script, if needed
        Interpreter script = new Interpreter();
        script.set("db", db);
        script.set("dbFileLibraryPath", prefs.get("FILELIBRARY"));
        // Determine if an upgrade needs to be executed (2.9)
        versionToInstall = "2004-06-15";
        if (!isInstalled(db, versionToInstall)) {
          upgradeSQL(context, db, "2004-06-15.sql");
          upgradeBSH(context, script, "2004-06-15.bsh");
          installLog.add("2004-06-15 database changes installed");
          buildHelp = true;
        }
        // Determine if an upgrade needs to be executed (2.9.1)
        versionToInstall = "2004-08-30";
        if (!isInstalled(db, versionToInstall)) {
          upgradeSQL(context, db, "2004-08-30.sql");
          upgradeBSH(context, script, "2004-08-19.bsh");
          upgradeBSH(context, script, "2004-08-20.bsh");
          upgradeBSH(context, script, "2004-08-31.bsh");
          installLog.add("2004-08-30 database changes installed");
          buildHelp = true;
        }
        // Determine if an upgrade needs to be executed (2.9.2)
        versionToInstall = "2005-01-14";
        if (!isInstalled(db, versionToInstall)) {
          upgradeSQL(context, db, "2005-01-14.sql");
          installLog.add("2005-01-14 database changes installed");
        }
        // Determine if an upgrade needs to be executed (3.0)
        versionToInstall = "2005-03-30";
        if (!isInstalled(db, versionToInstall)) {
          String setupPath =
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs;
          FileUtils.copyFile(new File(setupPath + "templates.xml"), new File(getDbNamePath(context) + "templates.xml"), false);
          installLog.add("2005-03-30 files installed");
          upgradeSQL(context, db, "2005-03-30.sql");
          installLog.add("2005-03-30 database changes installed");
        }
        // Reinstall the help file if requested...
        if (buildHelp) {
          // Install the help (blank tables should already exist)
          ImportHelp help = new ImportHelp();
          help.buildHelpInformation(context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "help.xml");
          help.buildExistingPermissionCategories(db);
          help.insertHelpRecords(db);
          help.buildTableOfContents();
          help.insertTableOfContents(db);
        }
        // END DHV CODE ONLY
        if (!prefs.save()) {
          context.getRequest().setAttribute("errorMessage", "No write permission on file library, build.properties");
          return "UpgradeERROR";
        }
      } catch (Exception e) {
        context.getRequest().setAttribute("errorMessage", e.getMessage());
        return "UpgradeERROR";
      } finally {
        //Always free the database connection
        this.freeConnection(context, db);
      }
    }
    return "UpgradeOK";
  }


  /**
   *  Gets the administrator attribute of the Upgrade object
   *
   *@param  context  Description of the Parameter
   *@return          The administrator value
   */
  private boolean isAdministrator(ActionContext context) {
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    if (thisUser != null) {
      String status = (String) context.getSession().getAttribute("UPGRADEOK");
      return "UPGRADEOK".equals(status);
    }
    return false;
  }


  /**
   *  Gets the installedVersion attribute of the Upgrade object
   *
   *@param  db                Description of the Parameter
   *@return                   The installedVersion value
   *@exception  SQLException  Description of the Exception
   */
  private String getLastVersion(Connection db) throws SQLException {
    String installedVersion = null;
    // Query the installed version
    PreparedStatement pst = db.prepareStatement(
        "SELECT script_version " +
        "FROM database_version " +
        "WHERE version_id IN " +
        "(SELECT max(version_id) " +
        " FROM database_version) ");
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      installedVersion = rs.getString("script_version");
    }
    rs.close();
    pst.close();
    return installedVersion;
  }


  /**
   *  Gets the installed attribute of the Upgrade object
   *
   *@param  db                Description of the Parameter
   *@param  version           Description of the Parameter
   *@return                   The installed value
   *@exception  SQLException  Description of the Exception
   */
  private boolean isInstalled(Connection db, String version) throws SQLException {
    boolean isInstalled = false;
    // Query the installed version
    PreparedStatement pst = db.prepareStatement(
        "SELECT script_version " +
        "FROM database_version " +
        "WHERE script_version = ? ");
    pst.setString(1, version);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      isInstalled = true;
    }
    rs.close();
    pst.close();
    return isInstalled;
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  baseName          Description of the Parameter
   *@exception  Exception     Description of the Exception
   */
  private void upgradeSQL(ActionContext context, Connection db, String baseName) throws Exception {
    switch (DatabaseUtils.getType(db)) {
        case DatabaseUtils.POSTGRESQL:
          System.out.println("Upgrade-> Executing PostgreSQL script " + baseName);
          DatabaseUtils.executeSQL(db,
              context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "postgresql_" + baseName);
          break;
        case DatabaseUtils.MSSQL:
          System.out.println("Upgrade-> Executing MSSQL script " + baseName);
          DatabaseUtils.executeSQL(db,
              context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "mssql_" + baseName);
          break;
        default:
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Upgrade-> * Database could not be determined: " + DatabaseUtils.getType(db));
          }
          break;
    }

  }


  /**
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@param  script         Description of the Parameter
   *@param  scriptName     Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void upgradeBSH(ActionContext context, Interpreter script, String scriptName) throws Exception {
    System.out.println("Upgrade-> Executing BeanShell script " + scriptName);
    script.source(context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + scriptName);
  }
}

