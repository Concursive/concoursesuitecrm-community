/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.ant.tasks;

import bsh.Interpreter;
import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.aspcfs.modules.system.base.DatabaseVersion;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This Ant Task processes all databases for the given gatekeeper database and
 * site code. The task iterates through the databases and executes BeanShell
 * Scripts and/or SQL scripts for the given base file name.<br>
 * 1. if the basefile.bsh exists, the bean shell is executed, <br>
 * 2. if the basefile.sql exists, then the sql source file is executed as a sql
 * transaction.
 *
 * @author matt rajkowski
 * @version $Id: UpgradeDatabaseTask.java,v 1.1 2003/04/14 21:30:06 mrajkowski
 *          Exp $
 * @created April 14, 2003
 */
public class UpgradeDatabaseTask extends Task {

  public final static String fs = System.getProperty("file.separator");
  private String sitecode = null;
  private String driver = null;
  private String url = null;
  private String user = null;
  private String password = null;
  private String baseFile = null;
  private String servletJar = null;
  private String fileLibraryPath = null;
  private String specificDatabase = null;
  private String locale = "";
  private String params = null;
  private String languagePath = null;
  private String prefsPath = null;
  private URL prefsURL = null;


  /**
   * Sets the sitecode attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new sitecode value
   */
  public void setSitecode(String tmp) {
    this.sitecode = tmp;
  }


  /**
   * Sets the driver attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   * Sets the url attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   * Sets the user attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   * Sets the password attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new password value
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   * Sets the source attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new source value
   */
  public void setBaseFile(String tmp) {
    this.baseFile = tmp;
  }


  /**
   * Sets the servletJar attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new servletJar value
   */
  public void setServletJar(String tmp) {
    this.servletJar = tmp;
  }


  /**
   * Sets the fileLibraryPath attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Sets the specificDatabase attribute of the UpgradeDatabaseTask object
   *
   * @param tmp The new specificDatabase value
   */
  public void setSpecificDatabase(String tmp) {
    this.specificDatabase = tmp;
  }


  public void setLocale(String locale) {
    this.locale = locale;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public void setLanguagePath(String languagePath) {
    this.languagePath = languagePath;
  }

  public void setPrefsPath(String prefsPath) {
    this.prefsPath = prefsPath;
  }

  /**
   * This method is called by Ant when the upgradeDatabaseTask is used
   *
   * @throws BuildException Description of the Exception
   */
  public void execute() throws BuildException {
    String fsEval = System.getProperty("file.separator");
    if ("\\".equals(fsEval)) {
      fsEval = "\\\\";
      servletJar = StringUtils.replace(servletJar, "\\", "\\\\");
      fileLibraryPath = StringUtils.replace(fileLibraryPath, "\\", "\\\\");
      languagePath = StringUtils.replace(languagePath, "\\", "\\\\");
      prefsPath = StringUtils.replace(prefsPath, "\\", "\\\\");
    }
    if (prefsPath != null) {
      try {
        prefsURL = new File(prefsPath).toURL();
      } catch (Exception e) {
        throw new BuildException("prefsURL setter exception: " + prefsPath);
      }
    }
    try {
      //Create a Connection Pool to facilitate connections
      ConnectionPool sqlDriver = new ConnectionPool();
      sqlDriver.setDebug(true);
      sqlDriver.setTestConnections(false);
      sqlDriver.setAllowShrinking(true);
      sqlDriver.setMaxConnections(10);
      sqlDriver.setMaxIdleTime(60000);
      sqlDriver.setMaxDeadTime(600000);
      //Build list of scripts to execute
      ArrayList files = new ArrayList();
      boolean multiMode = false;
      if (baseFile.indexOf(".txt") > -1) {
        StringUtils.loadText(baseFile, files, true);
        multiMode = true;
        System.out.println("Files: " + files.size());
      } else {
        files.add(baseFile);
      }
      //Connect to gatekeeper to cache list of databases
      ConnectionElement ce = new ConnectionElement(url, user, password);
      ce.setDriver(driver);
      ce.setAllowCloseOnIdle(false);
      Connection dbGk = sqlDriver.getConnection(ce);
      ArrayList siteList = new ArrayList();

      if (params == null || params.indexOf("mode=upgrade") == -1) {
        // Installing a new database
        HashMap siteInfo = new HashMap();
        siteInfo.put("url", url);
        siteInfo.put("dbName", specificDatabase);
        siteInfo.put("user", user);
        siteInfo.put("password", password);
        siteInfo.put("driver", driver);
        siteList.add(siteInfo);
      } else {
        // Upgrading an existing database
        if (specificDatabase != null && !"".equals(specificDatabase)) {
          //Build list of sites
          SiteList dbList = new SiteList();
          dbList.setSiteCode(sitecode);
          dbList.setDatabaseName(specificDatabase);
          dbList.buildList(dbGk);
          if (dbList.size() > 0) {
            Site thisSite = (Site) dbList.get(0);
            HashMap siteInfo = new HashMap();
            siteInfo.put("url", thisSite.getDatabaseUrl());
            siteInfo.put("dbName", thisSite.getDatabaseName());
            siteInfo.put("user", thisSite.getDatabaseUsername());
            siteInfo.put("password", thisSite.getDatabasePassword());
            siteInfo.put("driver", thisSite.getDatabaseDriver());
            siteList.add(siteInfo);
          } else {
            //Run just the specified database
            HashMap siteInfo = new HashMap();
            siteInfo.put("url", url);
            siteInfo.put("dbName", specificDatabase);
            siteInfo.put("user", user);
            siteInfo.put("password", password);
            siteInfo.put("driver", driver);
            siteList.add(siteInfo);
          }
        } else {
          //Run the rest of the databases
          PreparedStatement pst = dbGk.prepareStatement(
              "SELECT DISTINCT dbhost, dbname, dbuser, dbpw, driver " +
                  "FROM sites " +
                  "WHERE sitecode = ? AND enabled = ? ");
          pst.setString(1, sitecode);
          pst.setBoolean(2, true);
          ResultSet rs = pst.executeQuery();
          while (rs.next()) {
            HashMap siteInfo = new HashMap();
            siteInfo.put("url", rs.getString("dbhost"));
            siteInfo.put("dbName", rs.getString("dbname"));
            siteInfo.put("user", rs.getString("dbuser"));
            siteInfo.put("password", rs.getString("dbpw"));
            siteInfo.put("driver", rs.getString("driver"));
            if ((specificDatabase == null || "".equals(specificDatabase)) ||
                (specificDatabase.equals((String) siteInfo.get("dbName")))) {
              siteList.add(siteInfo);
            }
          }
          rs.close();
          pst.close();
        }
      }

      // Execute the scripts
      File baseFilePath = new File(baseFile);
      Iterator fileIterator = files.iterator();
      while (fileIterator.hasNext()) {
        String thisFile = (String) fileIterator.next();
        if (multiMode) {
          thisFile = baseFilePath.getParent() + fs + thisFile;
        }
        System.out.println("File: " + thisFile);
        //If this is a gatekeeper script, run it with the gk db
        if (thisFile.indexOf("gk") > -1) {
          executeScript(dbGk, thisFile, fsEval, null);
          executeSql(dbGk, thisFile, fsEval);
        } else {
          if (siteList.size() == 0) {
            throw new BuildException("ERROR: Database not found");
          }
          //Iterate over the databases to upgrade and run the correct
          //sql code and bean shell scripts
          Iterator i = siteList.iterator();
          while (i.hasNext()) {
            HashMap siteInfo = (HashMap) i.next();
            ce = new ConnectionElement(
                (String) siteInfo.get("url"),
                (String) siteInfo.get("user"),
                (String) siteInfo.get("password"));
            ce.setDriver((String) siteInfo.get("driver"));
            ce.setAllowCloseOnIdle(false);
            Connection db = sqlDriver.getConnection(ce);
            //Try to run a specified bean shell script if found
            executeScript(
                db, thisFile, fsEval, (String) siteInfo.get("dbName"));
            //Try to run the specified sql file
            executeSql(db, thisFile, fsEval);
            updateDBVersion(db, thisFile);
            sqlDriver.free(db);
          }
        }
        System.out.println(" ");
      }
      sqlDriver.closeAllConnections();
    } catch (Exception e) {
      e.printStackTrace(System.out);
      throw new BuildException("Script Error: " + e.getMessage());
    }
  }


  /**
   * Executes the specified BeanShell script on the given database connection
   *
   * @param db             Description of the Parameter
   * @param thisScriptFile Description of the Parameter
   * @param fsEval         Description of the Parameter
   * @param dbName         Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void executeScript(Connection db, String thisScriptFile, String fsEval, String dbName) throws Exception {
    String scriptFile = thisScriptFile;
    if (scriptFile.endsWith(".bsh")) {
      if (!(new File(scriptFile).exists())) {
        // look for script stored in a common "upgrade" directory
        scriptFile = (new File(scriptFile)).getParentFile().getParentFile().getParentFile().getPath() + fs +
            "upgrade" + fs + (new File(scriptFile)).getName();
      }
      if (!(new File(scriptFile).exists())) {
        throw new Exception("BSH FILE NOT FOUND: " + thisScriptFile);
      }
      Interpreter script = new Interpreter();
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "aspcfs.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "darkhorseventures.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "zeroio-iteam.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "jcrontab.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "lib" + fsEval + "log4j-1.2.9.jar\")");
      // TODO: Add all libs to classpath...
      //File libDir = new File()
      //File[] libraries = libDir.listFiles();
      //for (int i = 0; i < libraries.length; i++) {
      //  File thisFile = libraries[i];
      //
      //}
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "lib" + fsEval + "bcprov-jdk14-121.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "lib" + fsEval + "commons-codec-1.3.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "lib" + fsEval + "commons-logging-api.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "lib" + fsEval + "commons-logging.jar\")");
      script.eval(
          "addClassPath(bsh.cwd + \"" + fsEval + "lib" + fsEval + "log4j-1.2.9.jar\")");
      script.eval("addClassPath(\"" + servletJar + "\")");
      script.set("db", db);
      script.set("fileLibraryPath", fileLibraryPath + fs);
      script.set("languagePath", languagePath + fs);
      script.set("prefsURL", prefsURL);
      script.set("locale", locale);
      // Determine if fileLibrary is part of WEB-INF path
      File directory = new File(fileLibraryPath + fs + dbName);
      if (directory.exists()) {
        script.set(
            "dbFileLibraryPath", fileLibraryPath + fs + dbName + fs);
      } else {
        script.set(
            "dbFileLibraryPath", fileLibraryPath + fs + "WEB-INF" + fs + "fileLibrary" + fs + dbName + fs);
      }
      script.set("params", params);
      System.out.println("Executing: " + scriptFile);
      script.source(scriptFile);
    }
  }


  /**
   * Executes the specified sql file on the given database connection
   *
   * @param db      Description of the Parameter
   * @param sqlFile Description of the Parameter
   * @param fsEval  Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void executeSql(Connection db, String sqlFile, String fsEval) throws Exception {
    if (sqlFile.endsWith(".sql")) {
      if (!(new File(sqlFile).exists())) {
        throw new Exception("SQL FILE NOT FOUND: " + sqlFile);
      }
      try {
        db.setAutoCommit(false);
        System.out.println("SQL Script executing: " + sqlFile);
        DatabaseUtils.executeSQL(db, sqlFile);
        db.commit();
        System.out.println("SQL Script complete: " + sqlFile);
      } catch (SQLException sq) {
        db.rollback();
        System.out.println("SQL ERROR: " + sq.getMessage());
        throw new Exception(sq);
      } finally {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   * After a file is executed, the database is updated with the file's date for
   * reference
   *
   * @param db       Description of the Parameter
   * @param baseName Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void updateDBVersion(Connection db, String baseName) throws Exception {
    if (new File(baseName).exists()) {
      try {
        boolean newFile = true;
        //Ex. /home/user/cfs2/src/sql/postgresql/upgrade/2003-07-23.bsh
        String fileName = baseFile.substring(baseFile.indexOf("upgrade") + 8);
        PreparedStatement pst = db.prepareStatement(
            "SELECT version_id " +
                "FROM database_version " +
                "WHERE script_filename = ? ");
        pst.setString(1, fileName);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
          newFile = false;
        }
        rs.close();
        pst.close();

        if (newFile) {
          //Ex. 2003-07-23.sql  2003-07-23.bsh  2003-07-23gk.sql
          String scriptVersion = fileName.substring(0, fileName.indexOf("."));
          if (scriptVersion.indexOf("gk") > 0) {
            scriptVersion = scriptVersion.substring(
                0, scriptVersion.indexOf("gk"));
          }
          DatabaseVersion databaseVersion = new DatabaseVersion();
          databaseVersion.setScriptFilename(fileName);
          databaseVersion.setScriptVersion(scriptVersion);
          databaseVersion.insert(db);
        }
      } catch (SQLException sq) {
        //Table might not exist yet
      }
    }
  }
}
