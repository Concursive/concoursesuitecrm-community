package com.darkhorseventures.autoguide.utils;

import java.sql.*;
import java.util.*;
import java.io.*;
import com.darkhorseventures.autoguide.base.*;
import com.darkhorseventures.utils.ConnectionPool;
import com.darkhorseventures.utils.ConnectionElement;
import java.util.logging.*;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    April 18, 2002
 *@version    $Id$
 */
public class ParseKBB {

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
    String fileName = null;
    if (args.length > 0) {
      fileName = args[0];
    } else {
      fileName = "autoguide.csv";
    }
    thisApp.process(fileName);
    System.exit(0);
  }


  /**
   *  Description of the Method
   */
  public void process(String fileName) {
    Logger logger = Logger.getLogger("com.darkhorseventures.autoguide");
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
      ConnectionElement thisElement = new ConnectionElement(
          "jdbc:microsoft:sqlserver://216.54.81.105:1433;DatabaseName=cdb_cfs;SelectMethod=cursor",
          "postgres",
          "p0stgres");
      thisElement.setDriver("com.microsoft.jdbc.sqlserver.SQLServerDriver");
      db = sqlDriver.getConnection(thisElement);

      String line = null;
      try {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
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
    StringTokenizer st = new StringTokenizer(line, ",");
    String modelText = st.nextToken().trim();
    String makeText = st.nextToken().trim();

    Make make = new Make(makeText);
    if (!make.exists(db)) {
      make.insert(db);
      Logger logger = Logger.getLogger("com.darkhorseventures.autoguide");
      logger.fine("Inserting Make-> " + make.getName());
    }

    Model model = new Model(modelText);
    if (!model.exists(db)) {
      model.setMakeId(make.getId());
      model.insert(db);
    }

    while (st.hasMoreTokens()) {
      int year = Integer.parseInt(st.nextToken());
      Vehicle thisVehicle = new Vehicle(year, make.getId(), model.getId());
      thisVehicle.insert(db);
    }
  }
}

