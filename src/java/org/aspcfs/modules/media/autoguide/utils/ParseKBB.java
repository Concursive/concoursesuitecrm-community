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
    thisApp.process();
    System.exit(0);
  }


  /**
   *  Description of the Method
   */
  public void process() {
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
          "jdbc:postgresql://127.0.0.1:5432/cdb_matt",
          "postgres",
          "");
      thisElement.setDriver("org.postgresql.Driver");
      db = sqlDriver.getConnection(thisElement);

      String file = "kbb.csv";
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
    StringTokenizer st = new StringTokenizer(line, ",");
    String modelText = st.nextToken().trim();
    String makeText = st.nextToken().trim();

    Make thisMake = new Make(makeText);
    if (!thisMake.exists(db)) {
      thisMake.insert(db);
      Logger logger = Logger.getLogger("com.darkhorseventures.autoguide");
      logger.fine("Inserting Make-> " + thisMake.getName());
    }

    Model thisModel = new Model(modelText);
    thisModel.setMakeId(thisMake.getId());
    thisModel.insert(db);

    while (st.hasMoreTokens()) {
      int year = Integer.parseInt(st.nextToken());
      if (year < 5) {
        year += 2000;
      } else {
        year += 1900;
      }
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO autoguide_vehicle " +
          "(year, make_id, model_id, enteredby, modifiedby) " +
          "VALUES (?, ?, ?, ?, ?) ");
      pst.setInt(1, year);
      pst.setInt(2, thisMake.getId());
      pst.setInt(3, thisModel.getId());
      pst.setInt(4, -1);
      pst.setInt(5, -1);
      pst.execute();
      pst.close();
    }
  }
}

