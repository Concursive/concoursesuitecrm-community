package org.aspcfs.ant.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.database.ConnectionElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import bsh.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    April 14, 2003
 *@version    $Id$
 */
public class UpgradeDatabaseTask extends Task {
  private String sitecode = null;
  private String driver = null;
  private String url = null;
  private String user = null;
  private String password = null;
  private String source = null;


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
  public void setSource(String tmp) {
    this.source = tmp;
  }


  /**
   *  Description of the Method
   *
   *@exception  BuildException  Description of the Exception
   */
  public void execute() throws BuildException {
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
      PreparedStatement pst = db.prepareStatement(
          "SELECT DISTINCT dbhost, dbname, dbport, dbuser, dbpw, driver " +
          "FROM sites " +
          "WHERE sitecode = ? ");
      pst.setString(1, sitecode);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        HashMap siteInfo = new HashMap();
        siteInfo.put("host", rs.getString("dbhost"));
        siteInfo.put("name", rs.getString("dbname"));
        siteInfo.put("port", rs.getString("dbport"));
        siteInfo.put("user", rs.getString("dbuser"));
        siteInfo.put("password", rs.getString("dbpw"));
        siteInfo.put("driver", rs.getString("driver"));
        siteList.add(siteInfo);
      }
      rs.close();
      pst.close();
      //Iterate over the databases to upgrade and run the correct
      //sql code and bean shell scripts
      System.out.println(siteList.size() + " database" + (siteList.size() == 1?"":"s") + " to process");
      //Object result = new bsh.Interpreter().source("myscript.bsh");
    } catch (Exception e) {
      System.out.println("Script error");
    }
  }
}

