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
package org.aspcfs.modules.media.autoguide.utils;

import java.sql.*;
import java.util.*;
import java.io.*;
import org.aspcfs.modules.media.autoguide.base.*;
import com.darkhorseventures.database.*;
import java.util.logging.*;

/**
 *  This application is responsible for processing a .csv textfile and importing
 *  into a database.<p>
 *
 *  The file is in either format:<br>
 *  model,make,year1,year2,year3<br>
 *  model,make,year1 year2 year3
 *
 *@author     matt rajkowski
 *@created    April 18, 2002
 *@version    $Id$
 */
public class ParseKBB {

  private String file = null;
  private String driver = null;
  private String url = null;
  private String user = null;
  private String pass = null;


  /**
   *  Constructor for the ParseKBB object
   */
  public ParseKBB() { }


  /**
   *  The main program for the ParseKBB class
   *
   *@param  args  The command line arguments
   */
  public static void main(String[] args) {
    ParseKBB thisApp = new ParseKBB();
    thisApp.setFile(System.getProperty("file"));
    thisApp.setDriver(System.getProperty("driver"));
    thisApp.setUrl(System.getProperty("url"));
    thisApp.setUser(System.getProperty("user"));
    thisApp.setPass(System.getProperty("pass"));
    thisApp.process();
    System.exit(0);
  }


  /**
   *  Sets the file attribute of the ParseKBB object
   *
   *@param  tmp  The new file value
   */
  public void setFile(String tmp) {
    this.file = tmp;
  }


  /**
   *  Sets the driver attribute of the ParseKBB object
   *
   *@param  tmp  The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   *  Sets the url attribute of the ParseKBB object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the user attribute of the ParseKBB object
   *
   *@param  tmp  The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the pass attribute of the ParseKBB object
   *
   *@param  tmp  The new pass value
   */
  public void setPass(String tmp) {
    this.pass = tmp;
  }


  /**
   *  Gets the file attribute of the ParseKBB object
   *
   *@return    The file value
   */
  public String getFile() {
    return file;
  }


  /**
   *  Gets the driver attribute of the ParseKBB object
   *
   *@return    The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   *  Gets the url attribute of the ParseKBB object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the user attribute of the ParseKBB object
   *
   *@return    The user value
   */
  public String getUser() {
    return user;
  }


  /**
   *  Gets the pass attribute of the ParseKBB object
   *
   *@return    The pass value
   */
  public String getPass() {
    return pass;
  }


  /**
   *  Connects to the destination database and reads the file
   */
  public void process() {
    Logger logger = Logger.getLogger("org.aspcfs.modules.media.autoguide");
    if (file == null || url == null) {
      logger.info("Not configured");
      return;
    }

    java.util.Date startDate = new java.util.Date();
    long startMs = System.currentTimeMillis();
    ConnectionPool sqlDriver = null;
    logger.info("Starting...");
    int recordCount = 0;
    try {
      sqlDriver = new ConnectionPool();
    } catch (SQLException e) {
    }
    Connection db = null;
    try {
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(1);
      ConnectionElement thisElement = new ConnectionElement(url, user, pass);
      thisElement.setDriver(driver);
      db = sqlDriver.getConnection(thisElement);
      String line = null;
      try {
        BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
          processLine(db, line);
        }
        in.close();
      } catch (IOException ex) {
        logger.log(Level.SEVERE, "Error reading file", ex);
      }
      sqlDriver.free(db);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }
    sqlDriver.destroy();
    sqlDriver = null;
    java.util.Date endDate = new java.util.Date();
    long endMs = System.currentTimeMillis();
    logger.info("Start : " + startDate.toString());
    logger.info("End   : " + endDate.toString());
    logger.info("Millis: " + (endMs - startMs) + "ms");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  line              Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void processLine(Connection db, String line) throws SQLException {
    Logger logger = Logger.getLogger("org.aspcfs.modules.media.autoguide");
    StringTokenizer st = new StringTokenizer(line, ",");
    String modelText = st.nextToken().trim();
    String makeText = st.nextToken().trim();

    Make make = new Make(makeText);
    if (!make.exists(db)) {
      make.insert(db);
      logger.fine("Inserting Make-> " + make.getName());
    }

    Model model = new Model(modelText);
    model.setMakeId(make.getId());
    if (!model.exists(db)) {
      logger.fine("Inserting Model-> " + model.getName());
      model.insert(db);
    }

    //Process the years, could be in one of two formats
    //Ex. 97,98,99 or 97 98 99
    while (st.hasMoreTokens()) {
      String yearString = st.nextToken().trim();
      if (yearString.indexOf(" ") > -1) {
        //If multiple years are listed, process them all
        //Ex. 97 98 99
        StringTokenizer multipleYears = new StringTokenizer(yearString, " ");
        while (multipleYears.hasMoreTokens()) {
          String thisYear = multipleYears.nextToken().trim();
          int year = Integer.parseInt(thisYear);
          Vehicle thisVehicle = new Vehicle(year, make.getId(), model.getId());
          int id = thisVehicle.generateId(db);
          if (id == -1) {
            thisVehicle.insert(db);
            logger.fine("Inserting Year-> " + thisVehicle.getYear());
          }
        }
      } else {
        //Single year in this token: 97
        int year = Integer.parseInt(yearString);
        Vehicle thisVehicle = new Vehicle(year, make.getId(), model.getId());
        int id = thisVehicle.generateId(db);
        if (id == -1) {
          thisVehicle.insert(db);
          logger.fine("Inserting Year-> " + thisVehicle.getYear());
        }
      }
    }
  }
}

