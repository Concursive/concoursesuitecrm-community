package org.aspcfs.modules.setup.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import org.aspcfs.modules.setup.beans.*;
import java.sql.*;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.system.base.ApplicationVersion;
import java.util.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.controller.ApplicationPrefs;
import bsh.*;

/**
 *  Actions that facilitate upgrading an installation of Dark Horse CRM
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
    Connection db = null;
    // Retrieve version info (else, default to first binary version date)
    String installedVersion = getPref(context, "VERSION");
    if (installedVersion == null) {
      installedVersion = "2004-03-16";
    }
    String newVersion = ApplicationVersion.getVersionDate();
    if (installedVersion != null && installedVersion.equals(newVersion)) {
      context.getRequest().setAttribute("status", "0");
    } else {
      context.getRequest().setAttribute("status", "1");
    }
    context.getRequest().setAttribute("installedVersion", installedVersion);
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
    // Retrieve version info (else, default to first binary version date)
    String installedVersion = getPref(context, "VERSION");
    if (installedVersion == null) {
      installedVersion = "2004-03-16";
    }
    context.getRequest().setAttribute("installedVersion", installedVersion);
    context.getRequest().setAttribute("newVersion", ApplicationVersion.VERSION);
    String newVersion = ApplicationVersion.getVersionDate();
    if (!installedVersion.equals(newVersion)) {
      ArrayList installLog = new ArrayList();
      context.getRequest().setAttribute("installLog", installLog);
      Connection db = null;
      try {
        // Get a connection from the connection pool for this user
        db = this.getConnection(context);
        // Prepare bean shell script, if needed
        Interpreter script = new Interpreter();
        script.set("db", db);
        // Determine if version 2004-06-15 (Version 2.8) needs to be executed
        if (!isInstalled(db, "2004-06-15")) {
          switch (DatabaseUtils.getType(db)) {
              case DatabaseUtils.POSTGRESQL:
                System.out.println("Upgrade-> Executing PostgreSQL script 2004-06-15");
                // SQL Script
                DatabaseUtils.executeSQL(db,
                    context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "postgresql_2004-06-15.sql");
                // Bean Shell Script
                script.source(context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "2004-06-15.bsh");
                installLog.add("2004-06-15 Database changes installed");
                break;
              case DatabaseUtils.MSSQL:
                System.out.println("Upgrade-> Executing MSSQL script 2004-06-15");
                // SQL Script
                DatabaseUtils.executeSQL(db,
                    context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "mssql_2004-06-15.sql");
                // Bean Shell Script
                script.source(context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "2004-06-15.bsh");
                installLog.add("2004-06-15 Database changes installed");
                break;
              default:
                if (System.getProperty("DEBUG") != null) {
                  System.out.println("Upgrade-> * Database could not be determined: " + DatabaseUtils.getType(db));
                }
                break;
          }
        }
        ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
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
    String installedVersion = null;
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
   *  Adds a feature to the Version attribute of the Upgrade object
   *
   *@param  db                The feature to be added to the Version attribute
   *@param  filename          The feature to be added to the Version attribute
   *@param  version           The feature to be added to the Version attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addVersion(Connection db, String filename, String version) throws SQLException {
    // Add the specified version
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO database_version " +
        "(script_filename, script_version) VALUES (?, ?) ");
    pst.setString(1, filename);
    pst.setString(2, version);
    pst.execute();
    pst.close();
  }
}

