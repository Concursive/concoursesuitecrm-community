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
  private String sitecode = null;
  private String driver = null;
  private String url = null;
  private String user = null;
  private String password = null;
  private String baseFile = null;
  private String servletJar = null;


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
   *  This method is called by Ant when the upgradeDatabaseTask is used
   *
   *@exception  BuildException  Description of the Exception
   */
  public void execute() throws BuildException {
    String fsEval = System.getProperty("file.separator");
    if ("\\".equals(fsEval)) {
      fsEval = "\\\\";
      servletJar = StringUtils.replace(servletJar, "\\", "\\\\");
    }
    System.out.println("Checking databases to upgrade");
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
      //If a script exists for the gatekeeper, run it
      executeScript(db, baseFile + (baseFile.indexOf(".") > -1 ? "" : "gk.bsh"), fsEval);
      executeSql(db, baseFile + (baseFile.indexOf(".") > -1 ? "" : "gk.sql"), fsEval);
      //Run the rest of the databases
      PreparedStatement pst = db.prepareStatement(
          "SELECT DISTINCT dbhost, dbuser, dbpw, driver " +
          "FROM sites " +
          "WHERE sitecode = ? ");
      pst.setString(1, sitecode);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        HashMap siteInfo = new HashMap();
        siteInfo.put("url", rs.getString("dbhost"));
        siteInfo.put("user", rs.getString("dbuser"));
        siteInfo.put("password", rs.getString("dbpw"));
        siteInfo.put("driver", rs.getString("driver"));
        siteList.add(siteInfo);
      }
      rs.close();
      pst.close();
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
        db = sqlDriver.getConnection(ce);
        //Try to run a specified bean shell script if found
        executeScript(db, baseFile + (baseFile.indexOf(".") > -1 ? "" : ".bsh"), fsEval);
        //Try to run the specified sql file
        executeSql(db, baseFile + (baseFile.indexOf(".") > -1 ? "" : ".sql"), fsEval);
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
   *@param  db          Description of the Parameter
   *@param  scriptFile  Description of the Parameter
   */
  private void executeScript(Connection db, String scriptFile, String fsEval) throws Exception {
    if (scriptFile.endsWith(".bsh") && new File(scriptFile).exists()) {
      Interpreter script = new Interpreter();
      script.eval("addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "aspcfs.jar\")");
      script.eval("addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "darkhorseventures.jar\")");
      script.eval("addClassPath(\"" + servletJar + "\")");
      script.set("db", db);
      System.out.println("Executing: " + scriptFile);
      script.source(scriptFile);
    }
  }


  /**
   *  Executes the specified sql file on the given database connection
   *
   *@param  db             Description of the Parameter
   *@param  sqlFile        Description of the Parameter
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
}

