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
package com.darkhorseventures.database;

import org.apache.log4j.Logger;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class for recycling, and managing JDBC connections. <P>
 * <p/>
 * Taken from Core Servlets and JavaServer Pages http://www.coreservlets.com/.
 * &copy; 2000 Marty Hall; may be freely used or adapted. <P>
 * <p/>
 * The ConnectionPool class requires a ConnectionElement to be supplied for
 * each request to getConnection(connectionElement). This allows the
 * ConnectionPool to work in an ASP setting in which multiple databases are
 * cached by the pool.<P>
 * <p/>
 * Busy connections are stored with a connection key and a connection element
 * value.<br>
 * .free(connection) removes the connection from busy and then puts it on the
 * available list with a new connection element. <P>
 * <p/>
 * Available connections are stored with a connection element and connection
 * value.<br>
 * .getConnection(connectionElement) returns the connection, removes it from
 * the available connections, and then puts it on the busy list with a new
 * connection element.
 *
 * @author Matt Rajkowski
 * @version $Id: ConnectionPool.java,v 1.11 2003/01/13 14:42:24 mrajkowski Exp
 *          $
 * @created December 12, 2000
 */
public class ConnectionPool implements Runnable {

  private final static Logger log = Logger.getLogger(com.darkhorseventures.database.ConnectionPool.class);

  //Internal Constants
  public final static int BUSY_CONNECTION = 1;
  public final static int AVAILABLE_CONNECTION = 2;
  //Thread properties for creating a new connection
  private String url = null;
  private String username = null;
  private String password = null;
  private String driver = null;
  //Connection Pool Properties
  private java.util.Date startDate = new java.util.Date();
  private Hashtable availableConnections;
  private Hashtable busyConnections;
  private boolean connectionPending = false;
  //Connection Pool Settings
  private boolean debug = false;
  private int maxConnections = 10;
  private boolean waitIfBusy = true;
  private boolean allowShrinking = false;
  private boolean testConnections = false;
  private boolean forceClose = false;
  private int maxIdleTime = 60000;
  private int maxDeadTime = 300000;
  private Timer cleanupTimer = null;


  /**
   * Constructor for the ConnectionPool object. <p>
   * <p/>
   * Instantiates the pool and background timer with default settings.
   *
   * @throws SQLException Description of Exception
   * @since 1.2
   */
  public ConnectionPool() throws SQLException {
    availableConnections = new Hashtable();
    busyConnections = new Hashtable();
    initializeCleanupTimer();
  }


  /**
   * Sets whether connection pool statistics and connection information are
   * output
   *
   * @param tmp The new debug value
   */
  public void setDebug(boolean tmp) {
    this.debug = tmp;
  }


  /**
   * Sets the debug attribute of the ConnectionPool object
   *
   * @param tmp The new debug value
   */
  public void setDebug(String tmp) {
    this.debug = "true".equals(tmp);
  }


  /**
   * Sets the behavior of connection requests. If all connections are busy,
   * then the thread requesting the connection will either wait or throw an
   * exception if waitIfBusy is false
   *
   * @param tmp The new waitIfBusy value
   */
  public void setWaitIfBusy(boolean tmp) {
    this.waitIfBusy = tmp;
  }


  /**
   * Sets whether a background process is allowed to close unused connections
   * after the maxIdleTime has been reached
   *
   * @param tmp The new allowShrinking value
   */
  public void setAllowShrinking(boolean tmp) {
    this.allowShrinking = tmp;
  }


  /**
   * Sets the allowShrinking attribute of the ConnectionPool object
   *
   * @param tmp The new allowShrinking value
   */
  public void setAllowShrinking(String tmp) {
    this.allowShrinking = "true".equals(tmp);
  }


  /**
   * Sets whether connections will be tested, by executing a simple query,
   * before being handed out. If the test fails, then a new connection is
   * attempted without throwing an exception.
   *
   * @param tmp The new testConnections value
   */
  public void setTestConnections(boolean tmp) {
    this.testConnections = tmp;
  }


  /**
   * Sets the testConnections attribute of the ConnectionPool object
   *
   * @param tmp The new testConnections value
   */
  public void setTestConnections(String tmp) {
    this.testConnections = "true".equals(tmp);
  }


  /**
   * Sets whether connections are immediately closed, instead of pooled for
   * later use
   *
   * @param tmp The new ForceClose value
   * @since 1.1
   */
  public void setForceClose(boolean tmp) {
    this.forceClose = tmp;
  }


  /**
   * Sets the maximum number of connections that can be open at once, if the
   * max is reached, then behavior is determined by the waitIfBusy property
   *
   * @param tmp The new maxConnections value
   */
  public void setMaxConnections(int tmp) {
    this.maxConnections = tmp;
  }


  /**
   * Sets the maxConnections attribute of the ConnectionPool object
   *
   * @param tmp The new maxConnections value
   */
  public void setMaxConnections(String tmp) {
    this.maxConnections = Integer.parseInt(tmp);
  }


  /**
   * Sets the maximum number of milliseconds a connection can remain idle for
   * until shrinking occurs.
   *
   * @param tmp The new maxIdleTime value
   */
  public void setMaxIdleTime(int tmp) {
    this.maxIdleTime = tmp;
  }


  /**
   * Sets the maxIdleTime attribute of the ConnectionPool object
   *
   * @param tmp The new maxIdleTime value
   */
  public void setMaxIdleTime(String tmp) {
    this.maxIdleTime = Integer.parseInt(tmp);
  }


  /**
   * Sets the maxIdleTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxIdleTimeSeconds value
   */
  public void setMaxIdleTimeSeconds(String tmp) {
    this.maxIdleTime = 1000 * Integer.parseInt(tmp);
  }


  /**
   * Sets the maxIdleTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxIdleTimeSeconds value
   */
  public void setMaxIdleTimeSeconds(int tmp) {
    this.maxIdleTime = 1000 * tmp;
  }


  /**
   * Sets the maximum number of milliseconds a connection can checked out and
   * remain busy for. If the connection is not returned, or not renewed, then
   * it will be closed. The connection might be in use, or some process may
   * have forget to return it. This prevents an application from completely
   * being unusable if the connection is not returned.
   *
   * @param tmp The new maxDeadTime value
   */
  public void setMaxDeadTime(int tmp) {
    this.maxDeadTime = tmp;
  }


  /**
   * Sets the maxDeadTime attribute of the ConnectionPool object
   *
   * @param tmp The new maxDeadTime value
   */
  public void setMaxDeadTime(String tmp) {
    this.maxDeadTime = Integer.parseInt(tmp);
  }


  /**
   * Sets the maxDeadTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxDeadTimeSeconds value
   */
  public void setMaxDeadTimeSeconds(String tmp) {
    this.maxDeadTime = 1000 * Integer.parseInt(tmp);
  }


  /**
   * Sets the maxDeadTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxDeadTimeSeconds value
   */
  public void setMaxDeadTimeSeconds(int tmp) {
    this.maxDeadTime = 1000 * tmp;
  }

  public boolean getDebug() {
    return debug;
  }

  public boolean getAllowShrinking() {
    return allowShrinking;
  }

  public boolean getTestConnections() {
    return testConnections;
  }

  /**
   * Gets the username attribute of the ConnectionPool object
   *
   * @return The username value
   */
  public String getUsername() {
    return username;
  }


  /**
   * Gets the password attribute of the ConnectionPool object
   *
   * @return The password value
   */
  public String getPassword() {
    return password;
  }


  /**
   * Gets the driver attribute of the ConnectionPool object
   *
   * @return The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   * Gets the maxConnections attribute of the ConnectionPool object
   *
   * @return The maxConnections value
   */
  public int getMaxConnections() {
    return maxConnections;
  }


  /**
   * Gets the maxIdleTime attribute of the ConnectionPool object
   *
   * @return The maxIdleTime value
   */
  public int getMaxIdleTime() {
    return maxIdleTime;
  }


  /**
   * Gets the maxDeadTime attribute of the ConnectionPool object
   *
   * @return The maxDeadTime value
   */
  public int getMaxDeadTime() {
    return maxDeadTime;
  }


  /**
   * When a connection is needed, ask the class for the next available, if the
   * max connections has been reached then a thread will be spawned to wait for
   * the next available connection.
   *
   * @param requestElement Description of the Parameter
   * @return The connection value
   * @throws SQLException Description of the Exception
   */
  public synchronized Connection getConnection(ConnectionElement requestElement) throws SQLException {
    //Get an available matching connection
    if (!availableConnections.isEmpty()) {
      Enumeration e = availableConnections.keys();
      while (e.hasMoreElements()) {
        ConnectionElement thisElement = (ConnectionElement) e.nextElement();
        if (thisElement.getUrl().equals(requestElement.getUrl())) {
          try {
            //Try to get the connection from the pool, it may have been
            //recycled before it could be retrieved
            Connection existingConnection = this.getAvailableConnection(
                thisElement);
            if (existingConnection == null) {
              notifyAll();
              return (getConnection(requestElement));
            }
            //See if connection is open, else recycle it
            if (existingConnection.isClosed()) {
              existingConnection = null;
              notifyAll();
              return (getConnection(requestElement));
            } else if (testConnections) {
              //See if connection is good, else recycle it
              try {
                String testString = "SELECT 1";
                if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.DB2) {
                  testString = "SELECT 1 FROM SYSIBM.SYSDUMMY1";
                } else if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.ORACLE) {
                  testString = "SELECT 1 FROM DUAL";
                }
                PreparedStatement pst = existingConnection.prepareStatement(
                    testString);
                ResultSet rs = pst.executeQuery();
                rs.next();
                rs.close();
                pst.close();
              } catch (SQLException sqe) {
                existingConnection = null;
                notifyAll();
                return (getConnection(requestElement));
              }
            }
            //Go with the connection
            requestElement.renew();
            busyConnections.put(existingConnection, requestElement);
            // Default to case insensitive order by clauses
            if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.ORACLE) {
              //PreparedStatement pst = existingConnection.prepareStatement(
              //      "ALTER SESSION SET nls_comp=ansi");
              //pst.execute();
              //pst.close();
              PreparedStatement pst = existingConnection.prepareStatement(
                    "ALTER SESSION SET nls_sort=binary_ci");
              pst.execute();
              pst.close();
            }
            return (existingConnection);
          } catch (NullPointerException npe) {
            return (getConnection(requestElement));
          } catch (java.lang.ClassCastException cce) {
            throw new SQLException("Database connection error");
          }
        }
      }
      // A matching connection was not found so make a new connection, or
      // remove an idle available connection then try again...
      if (!connectionPending && (totalConnections() == maxConnections)) {
        e = availableConnections.keys();
        if (e.hasMoreElements()) {
          ConnectionElement thisElement = (ConnectionElement) e.nextElement();
          try {
            Connection oldConnection = this.getAvailableConnection(
                thisElement);
            if (oldConnection != null) {
              oldConnection.close();
            }
            oldConnection = null;
          } catch (SQLException se) {
            //just don't want to return an error to the app just yet
          }
        }
        e = null;
        return (getConnection(requestElement));
      }
    }
    // Three possible cases a connection wasn't found:
    // 1) You haven't reached maxConnections limit. So
    //    establish one in the background if there isn't
    //    already one pending, then wait for
    //    the next available connection (whether or not
    //    it was the newly established one).
    // 2) You reached maxConnections limit and waitIfBusy
    //    flag is false. Throw SQLException in such a case.
    // 3) You reached maxConnections limit and waitIfBusy
    //    flag is true. Then do the same thing as in second
    //    part of step 1: wait for next available connection.
    if ((totalConnections() < maxConnections) && !connectionPending) {
      makeBackgroundConnection(requestElement);
    } else if (!waitIfBusy) {
      throw new SQLException("Connection limit reached");
    }
    // Wait for either a new connection to be established
    // (if you called makeBackgroundConnection) or for
    // an existing connection to be freed up.
    try {
      wait();
    } catch (InterruptedException ie) {
    }
    // Someone freed up a connection, so try again.
    return (getConnection(requestElement));
  }


  /**
   * Prevents multiple methods from getting the same connection
   *
   * @param thisElement Description of the Parameter
   * @return The availableConnection value
   * @throws SQLException Description of the Exception
   */
  private synchronized Connection getAvailableConnection(ConnectionElement thisElement) throws SQLException {
    Connection existingConnection =
        (Connection) availableConnections.get(thisElement);
    if (existingConnection != null) {
      availableConnections.remove(thisElement);
    }
    return existingConnection;
  }


  /**
   * Returns whether max connections has been reached for debugging
   *
   * @return The MaxStatus value
   * @since 1.5
   */
  public boolean getMaxStatus() {
    if (busyConnections.size() == maxConnections) {
      return (true);
    } else {
      return (false);
    }
  }


  /**
   * Displays the date/time when the ConnectionPool was created
   *
   * @return The StartDate value
   * @since 1.2
   */
  public String getStartDate() {
    return (startDate.toString());
  }


  /**
   * Main processing method for the ConnectionPool object, only 1 thread runs
   * at a time since a connection can take a few seconds. While the user waits,
   * if a connection is freed by another task first, then they will get that
   * connection instead.
   *
   * @since 1.0
   */
  public void run() {
    // Make the connection
    try {
      ConnectionElement thisElement = new ConnectionElement();
      thisElement.setUrl(this.url);
      thisElement.setUsername(this.username);
      thisElement.setPassword(this.password);
      thisElement.setDriver(this.driver);
      Connection connection = makeNewConnection(thisElement);
      synchronized (this) {
        if (connection != null) {
          availableConnections.put(thisElement, connection);
          log.debug("New: " + thisElement.getUrl() + " " + this.toString());
        } else {
          log.debug("Database connection could not be created: " + thisElement.getUrl() + " " + this.toString());
          //NOTE: This is currently here because the getConnection() would be broken
          //without it.  When getConnection() grabs this it will just recycle it right
          //away.  Needs to be fixed since a NullPointer exception would be raised.
          availableConnections.put(thisElement, "Database Error");
        }
        connectionPending = false;
        notifyAll();
      }
    } catch (Exception e) {
      log.error("Thread Error: " + e.toString());
      // SQLException or OutOfMemory
      // Give up on new connection and wait for existing one
      // to free up.
    }
  }


  /**
   * When finished with the connection, don't close it, free the connection and
   * it will be reused by another request. If it's closed then remove the
   * reference to it and another will just have to be opened.
   *
   * @param connection Description of Parameter
   * @since 1.0
   */
  public synchronized void free(Connection connection) {
    if (connection != null) {
      ConnectionElement thisElement = (ConnectionElement) busyConnections.get(
          connection);
      if (thisElement == null) {
        log.info("Connection has already been returned to pool");
      } else {
        busyConnections.remove(connection);
        try {
          if (forceClose) {
            if (!connection.isClosed()) {
              connection.close();
              if (!forceClose) {
                log.info("Removed a possibly dead busy connection");
              }
            }
          }
          if (connection.isClosed()) {
            connection = null;
          } else {
            availableConnections.put(
                new ConnectionElement(
                    thisElement.getUrl(),
                    thisElement.getUsername(), thisElement.getPassword()), connection);
          }
        } catch (SQLException e) {
          connection = null;
        }
        // Wake up threads that are waiting for a connection
        notifyAll();
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param connection Description of the Parameter
   */
  public void renew(Connection connection) {
    if (connection != null) {
      ConnectionElement thisElement = (ConnectionElement) busyConnections.get(
          connection);
      thisElement.renew();
    }
  }


  /**
   * Returns total connections allocated
   *
   * @return Description of the Returned Value
   * @since 1.0
   */
  public int totalConnections() {
    return (availableConnections.size() + busyConnections.size());
  }


  /**
   * Close all the connections. Use with caution: be sure no connections are in
   * use before calling. Note that you are not <I>required</I> to call this
   * when done with a ConnectionPool, since connections are guaranteed to be
   * closed when garbage collected. But this method gives more control
   * regarding when the connections are closed.
   *
   * @since 1.1
   */
  public synchronized void closeAllConnections() {
    log.debug("Status: " + this.toString());
    log.debug("Closing available connections");
    closeConnections(AVAILABLE_CONNECTION, availableConnections);
    availableConnections.clear();
    log.debug("Closing busy connections");
    closeConnections(BUSY_CONNECTION, busyConnections);
    busyConnections.clear();
  }


  /**
   * Simple way to close just the busy connections
   *
   * @since 1.1
   */
  public synchronized void closeBusyConnections() {
    closeConnections(1, busyConnections);
    busyConnections = new Hashtable();
  }


  /**
   * More debugging information... displays the current state of the
   * ConnectionPool
   *
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String toString() {
    String info =
        "(avail=" + availableConnections.size() +
        ", busy=" + busyConnections.size() +
        ", max=" + maxConnections + ")";
    return (info);
  }


  /**
   * Cleans up the connection pool when destroyed, it's important to remove any
   * open timers -- this does not appear to be automatically called so the
   * application should call this to make sure the timer is stopped.
   *
   * @since 1.1
   */
  public void destroy() {
    if (cleanupTimer != null) {
      cleanupTimer.cancel();
      cleanupTimer = null;
      if (debug) {
        log.debug("Timer shut down");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
      }
    }
    log.debug("Stopped");
  }


  /**
   * A timer to check and close idle connections
   *
   * @since 1.1
   */
  private void initializeCleanupTimer() {
    // start checking after 5 seconds
    int initialDelay = 5000;
    // repeat every 5 seconds
    int period = 5000;
    if (cleanupTimer == null) {
      cleanupTimer = new Timer();
      TimerTask cleanupTask =
          new TimerTask() {
            /**
             * Main processing method for the cleanupTask object
             *
             * @since 1.1
             */
            public void run() {
              //Gets rid of idle connections
              if (allowShrinking) {
                cleanupAvailableConnections();
              }
              //Gets rid of deadlocked connections
              cleanupBusyConnections();
            }
          };
      cleanupTimer.scheduleAtFixedRate(cleanupTask, initialDelay, period);
    }
  }


  /**
   * Looks through the available connections and checks for anything that has
   * been open longer than the time allowed
   *
   * @since 1.2
   */
  private synchronized void cleanupAvailableConnections() {
    java.util.Date currentDate = new java.util.Date();
    Enumeration e = availableConnections.keys();
    while (e.hasMoreElements()) {
      ConnectionElement thisElement = null;
      try {
        thisElement = (ConnectionElement) e.nextElement();
        java.util.Date testDate = thisElement.getActiveDate();
        Connection connection = (Connection) availableConnections.get(
            thisElement);
        if (connection.isClosed() || (testDate.getTime() < (currentDate.getTime() - maxIdleTime))) {
          Connection expiredConnection = this.getAvailableConnection(
              thisElement);
          if (expiredConnection != null) {
            expiredConnection.close();
            log.debug("Removed: " + thisElement.getUrl() + " " + this.toString());
            notify();
          }
          expiredConnection = null;
        }
        connection = null;
      } catch (Exception sqle) {
        if (thisElement != null) {
          availableConnections.remove(thisElement);
          //log.debug("Removed a null available connection");
        }
        notify();
        //Ignore errors; garbage collect anyhow
      }

    }
    e = null;
  }


  /**
   * Looks through the busy connections and checks for anything that has been
   * open longer than the time allowed
   *
   * @since 1.2
   */
  private synchronized void cleanupBusyConnections() {
    try {
      java.util.Date currentDate = new java.util.Date();
      Enumeration e = busyConnections.keys();
      while (e.hasMoreElements()) {
        Connection connection = (Connection) e.nextElement();
        ConnectionElement thisElement = (ConnectionElement) busyConnections.get(
            connection);
        if (thisElement != null) {
          java.util.Date testDate = thisElement.getActiveDate();
          if (connection.isClosed() ||
              (thisElement.getAllowCloseOnIdle() &&
              testDate.getTime() < (currentDate.getTime() - maxDeadTime))) {
            //TODO: This is not synchronized and could error if this isn't really a closed connection
            busyConnections.remove(connection);
            connection.close();
            connection = null;
          }
        }
      }
      e = null;
    } catch (Exception sqle) {
      // Ignore errors; garbage collect anyhow
    }
  }


  /**
   * Makes a connection to the database in a background thread.<p>
   * <p/>
   * You can't just make a new connection in the foreground when none are
   * available, since this can take several seconds with a slow network
   * connection. Instead, start a thread that establishes a new connection,
   * then wait. You get woken up either when the new connection is established
   * or if someone finishes with an existing connection.
   *
   * @param thisElement Description of the Parameter
   * @since 1.0
   */
  private void makeBackgroundConnection(ConnectionElement thisElement) {
    connectionPending = true;
    try {
      Thread connectThread = new Thread(this);
      //NOTE: Currently thread safe due to connectionPending property
      this.url = thisElement.getUrl();
      this.username = thisElement.getUsername();
      this.password = thisElement.getPassword();
      this.driver = thisElement.getDriver();
      connectThread.start();
    } catch (OutOfMemoryError oome) {
      // Give up on new connection
    }
  }


  /**
   * This explicitly makes a new connection. Called in the foreground when
   * initializing the ConnectionPool, and called in the background when
   * running.
   *
   * @param thisElement Description of the Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  private Connection makeNewConnection(ConnectionElement thisElement) {
    try {
      // Load database driver if not already loaded
      Class.forName(thisElement.getDriver());
      // Establish network connection to database
      Connection connection =
          DatabaseUtils.getConnection(
              thisElement.getUrl(), thisElement.getUsername(), thisElement.getPassword());
      return (connection);
    } catch (Exception cnfe) {
      // Simplify try/catch blocks of people using this by
      // throwing only one exception type.
      //throw new SQLException("Can't find class for driver: " + driver);
      cnfe.printStackTrace(System.out);
      return null;
    }
  }


  /**
   * Close connections -- useful for debugging
   *
   * @param connections    Description of Parameter
   * @param connectionType Description of Parameter
   * @since 1.1
   */
  private void closeConnections(int connectionType, Hashtable connections) {
    try {
      Enumeration e = connections.elements();
      while (e.hasMoreElements()) {
        //For each connection, attempt to close
        try {
          Connection connection = null;
          if (connectionType == AVAILABLE_CONNECTION) {
            connection = (Connection) e.nextElement();
          } else {
            ConnectionElement ce = (ConnectionElement) e.nextElement();
            connection = (Connection) connections.get(ce);
          }
          connection.close();
        } catch (SQLException sqle) {
          //Ignore close error
        }
      }
    } catch (Exception sqle) {
      // Ignore errors; garbage collect anyhow
    }
  }

}

