package com.darkhorseventures.apps.dataimport;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.cfsmodule.*;
import com.zeroio.iteam.base.*;

/**
 *  Reads data by constructing CFS objects from a database connection.
 *  This reader will process objects in a specific order so that data integrity
 *  will be maintained during a copy process.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class CFSDatabaseReader implements DataReader {

  private String driver = null;
  private String url = null;
  private String user = null;
  private String password = null;

  public void setDriver(String tmp) { this.driver = tmp; }
  public void setUrl(String tmp) { this.url = tmp; }
  public void setUser(String tmp) { this.user = tmp; }
  public void setPassword(String tmp) { this.password = tmp; }
  public String getDriver() { return driver; }
  public String getUrl() { return url; }
  public String getUser() { return user; }
  public String getPassword() { return password; }


  /**
   *  Gets the version attribute of the CFSDatabaseReader object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the CFSDatabaseReader object
   *
   *@return    The name value
   */
  public String getName() {
    return "CFS 2.x Database Reader";
  }


  /**
   *  Gets the description attribute of the CFSDatabaseReader object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Reads data from an ASPCFS version 2.x database";
  }


  /**
   *  Gets the configured attribute of the CFSDatabaseReader object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    //Check initial settings
    if (driver == null || url == null || user == null || password == null) {
      return false;
    }

    //TODO:Read in modules and mappings to process

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  writer  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    //TODO:Connect to database
    ConnectionPool sqlDriver = null;
    Connection db = null;
    try {
      sqlDriver = new ConnectionPool();
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(2);
      ConnectionElement thisElement = new ConnectionElement(
          url, user, password);
      thisElement.setDriver(driver);
      db = sqlDriver.getConnection(thisElement);
    } catch (SQLException e) {
      logger.info("Could not get database connection" + e.toString());
      return false;
    }

    try {
      //TODO: Process all lookup lists

      //TODO: Loop through created items until complete, in the following order
      User baseUser = new User(db, "0");
      //Get all accounts user entered
      //Get all contacts user entered
      //Get all users user entered

      //Afterwards... update all owners

      DataRecord test = new DataRecord();
      test.setName("contact");
      test.addField("nameFirst", "Auto");
      test.addField("nameLast", "Matt");
      if (!writer.save(test)) {
        return false;
      }
    } catch (SQLException sqe) {
      logger.info(sqe.toString());
    } finally {
      sqlDriver.free(db);
      sqlDriver.destroy();
      sqlDriver = null;
    }

    return true;
  }
}

