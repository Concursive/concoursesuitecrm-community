package org.aspcfs.ant.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.database.ConnectionElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.*;
import java.io.*;
import bsh.*;
import org.aspcfs.utils.StringUtils;

/**
 *  This Ant Task processes all databases for the given gatekeeper database and
 *  site code. The task iterates through the databases and executes BeanShell
 *  Scripts and/or SQL scripts for the given base file name.<br>
 *  1. if the basefile.bsh exists, the bean shell is executed, <br>
 *  2. if the basefile.sql exists, then the sql source file is executed as a sql
 *  transaction.
 *
 *@author     matt rajkowski
 *@created    April 14, 2003
 *@version    $Id: UpgradeDatabaseTask.java,v 1.1 2003/04/14 21:30:06 mrajkowski
 *      Exp $
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
  private String webPath = null;
  private String specificDatabase = null;
  private boolean processSpecifiedDatabaseOnly = false;


  /**
   *  Sets the sitecode attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new sitecode value
   */
  public void setSitecode(String tmp) {
    this.sitecode = tmp;
  }


  /**
   *  Sets the driver attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   *  Sets the url attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the user attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the password attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new password value
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Sets the source attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new source value
   */
  public void setBaseFile(String tmp) {
    this.baseFile = tmp;
  }


  /**
   *  Sets the servletJar attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new servletJar value
   */
  public void setServletJar(String tmp) {
    this.servletJar = tmp;
  }


  /**
   *  Sets the webPath attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new webPath value
   */
  public void setWebPath(String tmp) {
    this.webPath = tmp;
  }


  /**
   *  Sets the specificDatabase attribute of the UpgradeDatabaseTask object
   *
   *@param  tmp  The new specificDatabase value
   */
  public void setSpecificDatabase(String tmp) {
    this.specificDatabase = tmp;
  }


  /**
   *  Sets the processSpecifiedDatabaseOnly attribute of the UpgradeDatabaseTask
   *  object
   *
   *@param  tmp  The new processSpecifiedDatabaseOnly value
   */
  public void setProcessSpecifiedDatabaseOnly(String tmp) {
    processSpecifiedDatabaseOnly = "true".equals(tmp);
  }


  /**
   *  This method is called by Ant when the upgradeDatabaseTask is used
   *
   *@exception  BuildException  Description of the Exception
   */
  public void execute() throws BuildException {
    String fsEval = System.getProperty("file.separator");
    if ("\\".equals(fsEval)) {
      fsEval = "\\\\";
      servletJar = StringUtils.replace(servletJar, "\\", "\\\\");
      webPath = StringUtils.replace(webPath, "\\", "\\\\");
    }
    System.out.println("Checking databases to process...");
    try {
      //Create a Connection Pool to facilitate connections
      ConnectionPool sqlDriver = new ConnectionPool();
      sqlDriver.setDebug(true);
      sqlDriver.setTestConnections(false);
      sqlDriver.setAllowShrinking(true);
      sqlDriver.setMaxConnections(10);
      sqlDriver.setMaxIdleTime(60000);
      sqlDriver.setMaxDeadTime(300000);
      //Cache a list of databases to upgrade
      ArrayList siteList = new ArrayList();
      //Connect to gatekeeper to get list of databases
      ConnectionElement ce = new ConnectionElement(url, user, password);
      ce.setDriver(driver);
      Connection db = sqlDriver.getConnection(ce);
      //If this is a gatekeeper script, run it
      if (baseFile.indexOf("gk") > -1) {
        executeScript(db, baseFile, fsEval, null);
        executeSql(db, baseFile, fsEval);
      } else {
        if (processSpecifiedDatabaseOnly && specificDatabase != null && !"".equals(specificDatabase)) {
          //Run just the specified database
          HashMap siteInfo = new HashMap();
          siteInfo.put("url", url);
          siteInfo.put("dbName", specificDatabase);
          siteInfo.put("user", user);
          siteInfo.put("password", password);
          siteInfo.put("driver", driver);
          siteList.add(siteInfo);
        } else {
          //Run the rest of the databases
          PreparedStatement pst = db.prepareStatement(
              "SELECT DISTINCT dbhost, dbname, dbuser, dbpw, driver " +
              "FROM sites " +
              "WHERE sitecode = ? ");
          pst.setString(1, sitecode);
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
      sqlDriver.free(db);
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
        System.out.println("");
        db = sqlDriver.getConnection(ce);
        //Try to run a specified bean shell script if found
        executeScript(db, baseFile, fsEval, (String) siteInfo.get("dbName"));
        //Try to run the specified sql file
        executeSql(db, baseFile, fsEval);
        updateDBVersion(db, baseFile);
        sqlDriver.free(db);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Script error");
    }
  }


  /**
   *  Executes the specified BeanShell script on the given database connection
   *
   *@param  db             Description of the Parameter
   *@param  scriptFile     Description of the Parameter
   *@param  fsEval         Description of the Parameter
   *@param  dbName         Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void executeScript(Connection db, String scriptFile, String fsEval, String dbName) throws Exception {
    if (scriptFile.endsWith(".bsh") && new File(scriptFile).exists()) {
      Interpreter script = new Interpreter();
      script.eval("addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "aspcfs.jar\")");
      script.eval("addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "darkhorseventures.jar\")");
      script.eval("addClassPath(\"" + servletJar + "\")");
      script.set("db", db);
      script.set("fileLibraryPath", webPath);
      script.set("dbFileLibraryPath", webPath + fs + "WEB-INF" + fs + "fileLibrary" + fs + dbName);
      System.out.println("Executing: " + scriptFile);
      script.source(scriptFile);
    }
  }


  /**
   *  Executes the specified sql file on the given database connection
   *
   *@param  db             Description of the Parameter
   *@param  sqlFile        Description of the Parameter
   *@param  fsEval         Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void executeSql(Connection db, String sqlFile, String fsEval) throws Exception {
    if (sqlFile.endsWith(".sql") && new File(sqlFile).exists()) {
      try {
        db.setAutoCommit(false);
        Statement st = db.createStatement();
        st.execute(StringUtils.loadText(sqlFile));
        st.close();
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
   *  After a file is executed, the database is updated with the file's date for
   *  reference
   *
   *@param  db             Description of the Parameter
   *@param  baseName       Description of the Parameter
   *@exception  Exception  Description of the Exception
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
            scriptVersion = scriptVersion.substring(0, scriptVersion.indexOf("gk"));
          }
          pst = db.prepareStatement(
              "INSERT INTO database_version " +
              "(script_filename, script_version) VALUES (?, ?) ");
          pst.setString(1, fileName);
          pst.setString(2, scriptVersion);
          pst.execute();
          pst.close();
        }
      } catch (SQLException sq) {
        //Table might not exist yet
      }
    }
  }
}

