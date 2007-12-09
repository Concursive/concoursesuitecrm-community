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
package org.aspcfs.apps.test;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

import java.sql.*;

/**
 * Application to test threaded connections
 *
 * @author mrajkowski
 * @version $Id$
 * @created July 10, 2001
 */
public class Database {

  private int runningConnections = 0;
  private int connectionsToExecute = 0;


  /**
   * Constructor for the Database object
   */
  public Database() {
  }


  /**
   * Description of the Method
   */
  public synchronized void runTests() {
    java.util.Date startDate = new java.util.Date();
    long startMs = System.currentTimeMillis();
    ConnectionPool sqlDriver = null;
    System.out.println("Starting...");
    try {
      sqlDriver = new ConnectionPool();
    } catch (SQLException e) {
    }
    Connection db = null;
    try {
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(5);
      //Test a single connection
      ConnectionElement thisElement = new ConnectionElement(
          "jdbc:postgresql://127.0.0.1:5432/cdb_ds21",
          "postgres",
          "");
      thisElement.setDriver("org.postgresql.Driver");
      db = sqlDriver.getConnection(thisElement);
      PreparedStatement pst = db.prepareStatement(
          "SELECT COUNT(*) AS contactcount FROM contact WHERE type_id not in (?) and namelast < ?");
      pst.setInt(1, 0);
      pst.setString(2, "b");

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        System.out.println("Records: " + rs.getInt("contactcount"));
      }
      rs.close();
      pst.close();
      sqlDriver.free(db);

      if (1 == 1) {
        return;
      }

      connectionsToExecute = 50;
      for (int i = 0; i < connectionsToExecute; i++) {
        ConnectionThread p = new ConnectionThread(
            "cdb_ds21", sqlDriver, i, "D");
        p.start();

        ConnectionThread q = new ConnectionThread(
            "cdb_ds21", sqlDriver, i, "B");
        q.start();

//        ConnectionThread r = new ConnectionThread("cdb_ds21", sqlDriver, i, "C");
//        r.start();

        /*
         *  ConnectionThread s = new ConnectionThread("cdb_dhv", sqlDriver, i, "A");
         *  s.start();
         */
      }

      wait();
      while (runningConnections != 0) {

      }
      System.out.println("Thead loop complete: 100 connections and queries");
      System.out.println(sqlDriver.toString());

    } catch (SQLException e) {
      System.out.println("Error: " + e.toString());
    } catch (java.lang.InterruptedException ie) {
      System.out.println("IE: " + ie.toString());
    }
    sqlDriver.destroy();
    sqlDriver = null;
    java.util.Date endDate = new java.util.Date();
    long endMs = System.currentTimeMillis();
    System.out.println("Start : " + startDate.toString());
    System.out.println("End   : " + endDate.toString());
    System.out.println("Millis: " + (endMs - startMs) + "ms");
  }


  /**
   * Description of the Method
   */
  public synchronized void endThreads() {
    notifyAll();
  }


  /**
   * The main program for the Database class
   *
   * @param args The command line arguments
   */
  public static void main(String[] args) {
    Database thisApp = new Database();
    thisApp.runTests();
  }


  /**
   * Description of the Class
   *
   * @author mrajkowski
   * @version $Id$
   * @created July 10, 2001
   */
  class ConnectionThread extends Thread {

    private ConnectionPool sqlDriver;
    private String database;
    private int count = 0;
    private String item = null;


    /**
     * Constructor for the ConnectionThread object
     *
     * @param tmp       Description of the Parameter
     * @param sqlDriver Description of the Parameter
     * @param runCount  Description of the Parameter
     * @param item      Description of the Parameter
     */
    ConnectionThread(String tmp, ConnectionPool sqlDriver, int runCount, String item) {
      this.database = tmp;
      this.sqlDriver = sqlDriver;
      this.count = runCount;
      this.item = item;
    }


    /**
     * Main processing method for the ConnectionThread object
     */
    public void run() {
      try {
        ++runningConnections;
        ConnectionElement thisElement = new ConnectionElement(
            "jdbc:postgresql://127.0.0.1:5432/" + this.database,
            "postgres",
            "");
        Connection db = sqlDriver.getConnection(thisElement);
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYEE");
        while (rs.next()) {

        }
        rs.close();
        st.close();
        /*
         *  try {
         *  this.sleep(50);
         *  } catch (InterruptedException e) {
         *  }
         */
        sqlDriver.free(db);
        --runningConnections;
        if (this.count == (connectionsToExecute - 1) && this.item.equals("D")) {
          endThreads();
        }
      } catch (SQLException e) {
      }
    }
  }
}

