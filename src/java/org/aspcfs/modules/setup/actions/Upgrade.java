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

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Actions that facilitate upgrading an installation of Centric CRM
 *
 * @author matt rajkowski
 * @version $Id$
 * @created June 16, 2004
 */
public class Upgrade extends CFSModule {

  /**
   * Prepares instruction page
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    addModuleBean(context, null, "Upgrade");
    return "NeedUpgradeOK";
  }


  /**
   * Checks to see what version is installed and what the version should be
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCheck(ActionContext context) {
    if (!isAdministrator(context)) {
      return "NeedUpgradeOK";
    }
    addModuleBean(context, null, "Upgrade");
    // Check version info
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs.isUpgradeable()) {
      // Something needs updating
      context.getRequest().setAttribute("status", "1");
    } else {
      // All ok
      context.getRequest().setAttribute("status", "0");
    }
    context.getRequest().setAttribute(
        "installedVersion", ApplicationVersion.getInstalledVersion(prefs));
    context.getRequest().setAttribute(
        "newVersion", ApplicationVersion.VERSION);
    return "CheckOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public synchronized String executeCommandPerformUpgrade(ActionContext context) {
    if (!isAdministrator(context)) {
      return "NeedUpgradeOK";
    }
    addModuleBean(context, null, "Upgrade");
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs.isUpgradeable()) {
      // Create a log of upgrade events
      ArrayList installLog = new ArrayList();
      context.getRequest().setAttribute("installLog", installLog);
      // Display upgrade information
      context.getRequest().setAttribute(
          "installedVersion", ApplicationVersion.getInstalledVersion(prefs));
      context.getRequest().setAttribute(
          "newVersion", ApplicationVersion.VERSION);
      Connection db = null;
      String versionToInstall = null;
      try {
        boolean buildHelp = false;
        // Get a connection from the connection pool for this user
        db = this.getConnection(context);
        // BEGIN DHV CODE ONLY
        // Determine if an upgrade needs to be executed (2.9)
        versionToInstall = "2004-06-15";
        if (!isInstalled(db, versionToInstall)) {
          upgradeSQL(context, db, "2004-06-15.sql");
          upgradeBSH(context, db, prefs, "2004-06-15.bsh");
          installLog.add(versionToInstall + " database changes installed");
          buildHelp = true;
        }
        // Determine if an upgrade needs to be executed (2.9.1)
        versionToInstall = "2004-08-30";
        if (!isInstalled(db, versionToInstall)) {
          upgradeSQL(context, db, "2004-08-30.sql");
          upgradeBSH(context, db, prefs, "2004-08-19.bsh");
          upgradeBSH(context, db, prefs, "2004-08-20.bsh");
          upgradeBSH(context, db, prefs, "2004-08-31.bsh");
          installLog.add(versionToInstall + " database changes installed");
          buildHelp = true;
        }
        // Determine if an upgrade needs to be executed (2.9.2)
        versionToInstall = "2005-01-14";
        if (!isInstalled(db, versionToInstall)) {
          upgradeSQL(context, db, "2005-01-14.sql");
          installLog.add(versionToInstall + " database changes installed");
        }
        // Determine if an upgrade needs to be executed (3.0)
        versionToInstall = "2005-03-30";
        if (!isInstalled(db, versionToInstall)) {
          String setupPath =
              context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs;
          //FileUtils.copyFile(new File(setupPath + "templates.xml"), new File(getDbNamePath(context) + "templates.xml"), true);
          //installLog.add(versionToInstall +" files installed");
          upgradeSQL(context, db, "2005-03-30.sql");
          upgradeBSH(context, db, prefs, "2005-03-30-01.bsh");
          upgradeBSH(context, db, prefs, "2005-03-30-02.bsh");
          installLog.add(versionToInstall + " database changes installed");
        }
        // Determine if an upgrade needs to be executed (3.0.1)
        versionToInstall = "2005-05-02";
        if (!isInstalled(db, versionToInstall)) {
          upgradeBSH(context, db, prefs, "2005-05-02-01.bsh");
          upgradeSQL(context, db, "2005-05-02.sql");
          installLog.add(versionToInstall + " database changes installed");
        }
        // Determine if an upgrade needs to be executed (3.1)
        versionToInstall = "2005-07-08";
        if (!isInstalled(db, versionToInstall)) {
          String setupPath =
              context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs;
          // Translated template files
          FileUtils.copyFile(
              new File(setupPath + "workflow_*.xml"), new File(
                  getDbNamePath(context)), true);
          FileUtils.copyFile(
              new File(setupPath + "templates_*.xml"), new File(
                  getDbNamePath(context)), true);
          FileUtils.copyFile(
              new File(setupPath + "application.xml"), new File(
                  getDbNamePath(context) + "application.xml"), true);
          installLog.add(versionToInstall + " files installed");
          // Schema changes
          upgradeSQL(context, db, "2005-07-08.sql");
          installLog.add(versionToInstall + " database changes installed");
          // Data changes
          upgradeBSH(context, db, prefs, "2005-06-21-script02.bsh");
          upgradeBSH(context, db, prefs, "2005-06-27-script02.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script01-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script02-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script03-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script04-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script05-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script06-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script07-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script08-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script09-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script10-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script11-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script12-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script13-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-05-25-script14-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-07-01-script01.bsh");
          upgradeBSH(context, db, prefs, "2005-07-07-script01-partha.bsh");
          upgradeBSH(context, db, prefs, "2005-07-14-script01-partha.bsh");
          installLog.add(versionToInstall + " data additions installed");
          //TODO: Insert database_version here since this is end of upgrade process
        }
        // Reinstall the help file if requested...
        if (buildHelp) {
          renewConnection(context, db);
          // Install the help (blank tables should already exist)
          ImportHelp help = new ImportHelp();
          help.buildHelpInformation(
              context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "help.xml");
          help.buildExistingPermissionCategories(db);
          help.insertHelpRecords(db);
          help.buildTableOfContents();
          help.insertTableOfContents(db);
          renewConnection(context, db);
        }
        // END DHV CODE ONLY
        if (!prefs.save()) {
          context.getRequest().setAttribute(
              "errorMessage", "No write permission on file library, build.properties");
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
   * Gets the administrator attribute of the Upgrade object
   *
   * @param context Description of the Parameter
   * @return The administrator value
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
   * Gets the installedVersion attribute of the Upgrade object
   *
   * @param db Description of the Parameter
   * @return The installedVersion value
   * @throws SQLException Description of the Exception
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
   * Gets the installed attribute of the Upgrade object
   *
   * @param db      Description of the Parameter
   * @param version Description of the Parameter
   * @return The installed value
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param context  Description of the Parameter
   * @param db       Description of the Parameter
   * @param baseName Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void upgradeSQL(ActionContext context, Connection db, String baseName) throws Exception {
    renewConnection(context, db);
    switch (DatabaseUtils.getType(db)) {
      case DatabaseUtils.POSTGRESQL:
        System.out.println(
            "Upgrade-> Executing PostgreSQL script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "postgresql_" + baseName);
        break;
      case DatabaseUtils.DAFFODILDB:
        System.out.println(
            "Upgrade-> Executing DaffodilDB script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "daffodildb_" + baseName);
        break;
      case DatabaseUtils.DB2:
        System.out.println("Upgrade-> Executing DB2 script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "db2_" + baseName);
        break;
      case DatabaseUtils.FIREBIRD:
        System.out.println("Upgrade-> Executing Firebird script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "firebird_" + baseName);
        break;
      case DatabaseUtils.MSSQL:
        System.out.println("Upgrade-> Executing MSSQL script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "mssql_" + baseName);
        break;
      case DatabaseUtils.ORACLE:
        System.out.println("Upgrade-> Executing Oracle script " + baseName);
        DatabaseUtils.executeSQL(
            db,
            context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "oracle_" + baseName);
        break;
      default:
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "Upgrade-> * Database could not be determined: " + DatabaseUtils.getType(
                  db));
        }
        break;
    }

  }


  /**
   * Description of the Method
   *
   * @param context    Description of the Parameter
   * @param scriptName Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void upgradeBSH(ActionContext context, Connection db, ApplicationPrefs prefs, String scriptName) throws Exception {
    System.out.println("Upgrade-> Executing BeanShell script " + scriptName);
    renewConnection(context, db);
    // Prepare bean shell script, if needed
    Interpreter script = new Interpreter();
    script.set("db", db);
    script.set("dbFileLibraryPath", prefs.get("FILELIBRARY"));
    script.set(
        "fileLibraryPath", context.getServletContext().getRealPath("/"));
    script.set("locale", prefs.get("SYSTEM.LANGUAGE"));
    script.source(
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + scriptName);
  }
}

