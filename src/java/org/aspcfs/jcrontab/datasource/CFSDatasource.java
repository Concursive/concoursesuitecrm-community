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
package org.aspcfs.jcrontab.datasource;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.jcrontab.Crontab;
import org.jcrontab.data.*;
import org.jcrontab.log.Log;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class generates a list of events for jcrontab using a CFS
 * ConnectionPool. The events are gathered from the system level gatekeeper
 * database and from any enabled site databases within the gatekeeper.<p>
 * <p/>
 * Adapted from org.jcrontab.data.GenericSQLSource by Israel Olalla, LGPL to
 * use ConnectionPool and iterate multiple event tables.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 13, 2003
 */
public class CFSDatasource implements DataSource {

  private CrontabParser cp = new CrontabParser();

  /**
   * This is the database driver being used.
   */
  private static Object dbDriver = null;
  private static CFSDatasource instance;

  /**
   * This Query gets all the Crontab entries from the events table but
   * searching by the name
   */
  public static String querySearching =
      "SELECT \"second\", \"minute\", \"hour\", dayofmonth, " +
      "\"month\", dayofweek, \"year\", task, extrainfo, businessDays " +
      "FROM events " +
      "WHERE task = ? ";

  /**
   * This Query stores the Crontab entries
   */
  public static String queryStoring =
      "INSERT INTO events(" +
      "\"second\", \"minute\", \"hour\", dayofmonth, " +
      "\"month\", dayofweek, \"year\", " +
      "task, extrainfo, businessDays) " +
      "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

  /**
   * This Query removes the given Crontab Entries
   */
  public static String queryRemoving =
      "DELETE FROM events WHERE " +
      "\"second\" = ? AND " +
      "\"minute\" = ? AND " +
      "\"hour\" = ? AND " +
      "dayofmonth = ? AND " +
      "\"month\" = ? AND " +
      "dayofweek = ? AND " +
      "\"year\" = ? AND " +
      "task = ? AND " +
      "extrainfo = ? AND " +
      "businessDays = ?";

  private static String DEFAULT_TABLE = "events";


  /**
   * Constructor for the CFSDatasource object
   */
  public CFSDatasource() {
  }


  /**
   * This method grants this class to be a singleton and grants data access
   * integrity
   *
   * @return returns the instance
   */
  public DataSource getInstance() {
    if (instance == null) {
      instance = new CFSDatasource();
    }
    return instance;
  }


  /**
   * This method searches the Crontab Entry that the class has the given name
   *
   * @param ceb Description of the Parameter
   * @return Description of the Return Value
   * @throws DataNotFoundException  Description of the Exception
   * @throws CrontabEntryException  when it can't parse the line correctly
   * @throws ClassNotFoundException cause loading the driver can throw an
   *                                ClassNotFoundException
   * @throws SQLException           Yep can throw an SQLException too
   */
  public CrontabEntryBean find(CrontabEntryBean ceb) throws CrontabEntryException,
      ClassNotFoundException, SQLException, DataNotFoundException {
    CrontabEntryBean[] cebra = findAll();
    for (int i = 0; i < cebra.length; i++) {
      if (cebra[i].equals(ceb)) {
        return cebra[i];
      }
    }
    throw new DataNotFoundException("Unable to find :" + ceb);
  }


  /**
   * Description of the Method
   *
   * @param connectionPool Description of the Parameter
   * @return Description of the Return Value
   * @throws CrontabEntryException  Description of the Exception
   * @throws ClassNotFoundException Description of the Exception
   * @throws SQLException           Description of the Exception
   * @throws DataNotFoundException  Description of the Exception
   */
  public CrontabEntryBean[] findAll(Object connectionPool) throws CrontabEntryException,
      ClassNotFoundException, SQLException, DataNotFoundException {
    if (connectionPool == null) {
      return findAll();
    }
    ConnectionPool cp = (ConnectionPool) connectionPool;
    ArrayList events = new ArrayList();
    Crontab crontab = Crontab.getInstance();
    //Get gatekeeper connection, scan events
    String dbUrl = crontab.getProperty(
        "org.jcrontab.data.GenericSQLSource.url");
    String dbUser = crontab.getProperty(
        "org.jcrontab.data.GenericSQLSource.username");
    String dbPwd = crontab.getProperty(
        "org.jcrontab.data.GenericSQLSource.password");
    ConnectionElement gk = new ConnectionElement(dbUrl, dbUser, dbPwd);
    gk.setDriver(
        crontab.getProperty("org.jcrontab.data.GenericSQLSource.driver"));
    Connection db = null;
    SiteList siteList = new SiteList();
    siteList.setEnabled(Constants.TRUE);
    try {
      db = cp.getConnection(gk);
      findAll(events, db, DEFAULT_TABLE, null);
      siteList.buildList(db);
    } catch (Exception e) {
    } finally {
      cp.free(db);
    }
    //For each qualifying database in gatekeeper, get events
    Iterator sites = siteList.iterator();
    while (sites.hasNext()) {
      Site thisSite = (Site) sites.next();
      ConnectionElement ce = new ConnectionElement(
          thisSite.getDatabaseUrl(),
          thisSite.getDatabaseUsername(),
          thisSite.getDatabasePassword());
      ce.setDriver(thisSite.getDatabaseDriver());
      ce.setDbName(thisSite.getDatabaseName());
      try {
        db = cp.getConnection(ce);
        findAll(events, db, "business_process_events", ce);
      } catch (Exception e) {
      } finally {
        cp.free(db);
      }
    }
    //Convert the arrayList to a CEB array
    return buildCrontabArray(events);
  }


  /**
   * Returns an empty list of events
   *
   * @return Description of the Return Value
   * @throws CrontabEntryException  Description of the Exception
   * @throws ClassNotFoundException Description of the Exception
   * @throws SQLException           Description of the Exception
   * @throws DataNotFoundException  Description of the Exception
   */
  public CrontabEntryBean[] findAll() throws CrontabEntryException,
      ClassNotFoundException, SQLException, DataNotFoundException {
    //Return no events...must be shutting down or not configured for
    //the connection pool
    ArrayList events = new ArrayList();
    return buildCrontabArray(events);
  }


  /**
   * This method searches all the CrontabEntries from the given Connection
   *
   * @param list              Description of the Parameter
   * @param conn              Description of the Parameter
   * @param table             Description of the Parameter
   * @param connectionElement Description of the Parameter
   * @throws CrontabEntryException  Description of the Exception
   * @throws ClassNotFoundException Description of the Exception
   * @throws SQLException           Description of the Exception
   * @throws DataNotFoundException  Description of the Exception
   */
  public void findAll(ArrayList list, Connection conn, String table, ConnectionElement connectionElement) throws CrontabEntryException,
      ClassNotFoundException, SQLException, DataNotFoundException {
    java.sql.PreparedStatement pst = null;
    java.sql.ResultSet rs = null;
    try {
      pst = conn.prepareStatement(
          "SELECT \"second\", \"minute\", \"hour\", dayofmonth, " +
          "\"month\", dayofweek, \"year\", task, extrainfo, businessDays " +
          "FROM " + table + " " +
          "WHERE enabled = ?");
      pst.setBoolean(1, true);
      rs = pst.executeQuery();
      while (rs.next()) {
        boolean[] bSeconds = new boolean[60];
        boolean[] bYears = new boolean[10];
        String second = rs.getString("second");
        String minute = rs.getString("minute");
        String hour = rs.getString("hour");
        String dayofmonth = rs.getString("dayofmonth");
        String month = rs.getString("month");
        String dayofweek = rs.getString("dayofweek");
        String year = rs.getString("year");
        String task = rs.getString("task");
        String extrainfo = rs.getString("extrainfo");
        String line =
            minute + " " + hour + " " + dayofmonth
            + " " + month + " "
            + dayofweek + " " + task + " " + extrainfo;
        boolean businessDays = rs.getBoolean("businessDays");
        //Process the data into a bean
        CrontabEntryBean ceb = cp.marshall(line);
        cp.parseToken(year, bYears, false);
        ceb.setBYears(bYears);
        ceb.setYears(year);
        cp.parseToken(second, bSeconds, false);
        ceb.setBSeconds(bSeconds);
        ceb.setSeconds(second);
        ceb.setBusinessDays(businessDays);
        ceb.setConnectionContext(connectionElement);
        //Add to list
        list.add(ceb);
      }
      rs.close();
      pst.close();
    } catch (Exception e) {
      //Ignore this... it could be temporary and we don't want this to crash
    }
  }


  /**
   * Description of the Method
   *
   * @param list Description of the Parameter
   * @return Description of the Return Value
   */
  private CrontabEntryBean[] buildCrontabArray(ArrayList list) {
    CrontabEntryBean[] result = new CrontabEntryBean[list.size()];
    for (int i = 0; i < list.size(); i++) {
      result[i] = (CrontabEntryBean) list.get(i);
      result[i].setId(i);
    }
    return result;
  }


  /**
   * This method removes the given Crontab Entries
   *
   * @param beans Description of the Parameter
   * @throws CrontabEntryException  when it can't parse the line correctly
   * @throws ClassNotFoundException cause loading the driver can throw an
   *                                ClassNotFoundException
   * @throws SQLException           Yep can throw an SQLException too
   */
  public void remove(CrontabEntryBean[] beans)
      throws CrontabEntryException,
      ClassNotFoundException, SQLException {
    Connection conn = null;
    java.sql.PreparedStatement ps = null;
    try {
      conn = getConnection();
      ps = conn.prepareStatement(queryRemoving);
      for (int i = 0; i < beans.length; i++) {
        ps.setString(1, beans[i].getSeconds());
        ps.setString(2, beans[i].getMinutes());
        ps.setString(3, beans[i].getHours());
        ps.setString(4, beans[i].getDaysOfMonth());
        ps.setString(5, beans[i].getMonths());
        ps.setString(6, beans[i].getDaysOfWeek());
        ps.setString(7, beans[i].getYear());
        if ("".equals(beans[i].getMethodName())) {
          ps.setString(8, beans[i].getClassName());
        } else {
          String classAndMethod = beans[i].getClassName() +
              "#" + beans[i].getMethodName();
          ps.setString(8, classAndMethod);
        }
        String extraInfo[] = beans[i].getExtraInfo();
        String extraInfob = new String();
        if (extraInfo.length > 0) {
          for (int z = 0; z < extraInfo.length; z++) {
            extraInfob += " " + extraInfo[z];
          }
        }
        ps.setString(9, extraInfob);
        ps.setBoolean(10, beans[i].getBusinessDays());
        ps.executeUpdate();
      }
      ps.close();
    } finally {
      try {
        conn.close();
      } catch (Exception e2) {
      }
    }
  }


  /**
   * This method saves the CrontabEntryBean the actual problem with this method
   * is that it doesn't store comments and blank lines from the original file,
   * any ideas?
   *
   * @param beans Description of the Parameter
   * @throws CrontabEntryException  when it can't parse the line correctly
   * @throws ClassNotFoundException cause loading the driver can throw an
   *                                ClassNotFoundException
   * @throws SQLException           Yep can throw an SQLException too
   */
  public void store(CrontabEntryBean[] beans) throws CrontabEntryException,
      ClassNotFoundException, SQLException {
    Connection conn = null;
    java.sql.PreparedStatement ps = null;
    try {
      conn = getConnection();
      ps = conn.prepareStatement(queryStoring);
      for (int i = 0; i < beans.length; i++) {
        ps.setString(1, beans[i].getSeconds());
        ps.setString(2, beans[i].getMinutes());
        ps.setString(3, beans[i].getHours());
        ps.setString(4, beans[i].getDaysOfMonth());
        ps.setString(5, beans[i].getMonths());
        ps.setString(6, beans[i].getDaysOfWeek());
        ps.setString(7, beans[i].getYear());
        if ("".equals(beans[i].getMethodName())) {
          ps.setString(8, beans[i].getClassName());
        } else {
          String classAndMethod = beans[i].getClassName() +
              "#" + beans[i].getMethodName();
          ps.setString(8, classAndMethod);
        }

        String extraInfo[] = beans[i].getExtraInfo();
        String extraInfob = new String();
        if (extraInfo.length > 0) {
          for (int z = 0; z < extraInfo.length; z++) {
            extraInfob += " " + extraInfo[z];
          }
        }
        ps.setString(9, extraInfob);
        ps.setBoolean(10, beans[i].getBusinessDays());
        ps.executeUpdate();
      }
      ps.close();
    } finally {
      try {
        conn.close();
      } catch (Exception e2) {
      }
    }
  }


  /**
   * This method saves the CrontabEntryBean the actual problem with this method
   * is that doesn't store comments and blank lines from the original file any
   * ideas?
   *
   * @param bean Description of the Parameter
   * @throws CrontabEntryException  when it can't parse the line correctly
   * @throws ClassNotFoundException cause loading the driver can throw an
   *                                ClassNotFoundException
   * @throws SQLException           Yep can throw an SQLException too
   */
  public void store(CrontabEntryBean bean) throws CrontabEntryException,
      ClassNotFoundException, SQLException {
    CrontabEntryBean[] list = {bean};
    store(list);
  }


  /**
   * Retrieves a connection to the database. May use a Connection Pool
   * DataSource or JDBC driver depending on the properties.
   *
   * @return a <code>Connection</code>
   * @throws SQLException if there is an error retrieving the Connection.
   */
  private Connection getConnection() throws SQLException {
    Crontab crontab = Crontab.getInstance();
    String dbUser = crontab.getProperty(
        "org.jcrontab.data.GenericSQLSource.username");
    String dbPwd = crontab.getProperty(
        "org.jcrontab.data.GenericSQLSource.password");
    String dbUrl = crontab.getProperty(
        "org.jcrontab.data.GenericSQLSource.url");
    if (dbDriver == null) {
      dbDriver = loadDatabaseDriver(
          crontab.getProperty(
              "org.jcrontab.data.GenericSQLSource.dbDataSource"));
    }
    if (dbDriver instanceof javax.sql.DataSource) {
      if (dbUser != null && dbPwd != null) {
        return ((javax.sql.DataSource) dbDriver).getConnection(dbUser, dbPwd);
      } else {
        return ((javax.sql.DataSource) dbDriver).getConnection();
      }
    } else {
      return DriverManager.getConnection(dbUrl, dbUser, dbPwd);
    }
  }


  /**
   * Initializes the database engine/data source. It first tries to load the
   * given DataSource name. If that fails it will load the database driver. If
   * the driver cannot be loaded it will check the DriverManager to see if
   * there is a driver loaded that can server the URL.
   *
   * @param srcName is the JDBC DataSource name or null to load the
   *                driver.
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private Object loadDatabaseDriver(String srcName) throws SQLException {
    String dbDataSource = srcName;
    Crontab crontab = Crontab.getInstance();

    if (dbDataSource == null) {
      String dbDriver =
          crontab.getProperty("org.jcrontab.data.GenericSQLSource.driver");
      Log.info("CFSDatasource-> Loading dbDriver: " + dbDriver);
      try {
        return Class.forName(dbDriver).newInstance();
      } catch (Exception ie) {
        Log.error("CFSDatasource-> Error loading " + dbDriver, ie);
        return DriverManager.getDriver(
            crontab.getProperty("org.jcrontab.data.GenericSQLSource.url"));
      }
    } else {
      try {
        javax.sql.DataSource dataSource = null;
        Log.info("Loading dataSource: " + dbDataSource);
        Context ctx = null;

        ctx = new InitialContext();
        try {
          dataSource =
              (javax.sql.DataSource) ctx.lookup(dbDataSource);
        } catch (NameNotFoundException nnfe) {
          Log.info(nnfe.getExplanation());
          Log.info("Checking Tomcat Context");
          Context tomcatCtx = (Context) ctx.lookup("java:comp/env");
          dataSource =
              (javax.sql.DataSource) tomcatCtx.lookup(dbDataSource);
        }
        Log.debug("DataSource loaded. ");
        return dataSource;
      } catch (Exception e) {
        String msg = e.getMessage();
        if (e instanceof NamingException) {
          msg = ((NamingException) e).getExplanation();
        }
        Log.debug(msg);
        Log.info(msg + " will try to use dbDriver...");
        return loadDatabaseDriver(null);
      }
    }
  }
}

