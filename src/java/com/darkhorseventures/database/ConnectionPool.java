package com.darkhorseventures.utils;

import java.sql.*;
import java.util.*;

/**
 *  A class for preallocating, recycling, and managing JDBC connections. <P>
 *
 *  Taken from Core Servlets and JavaServer Pages http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted. <P>
 *
 *  Store busy connections with a connection key and conn element value
 *  .free(connection) removes the connection from busy and then puts it on the
 *  avail list with a new conn element. <P>
 *
 *  Store available connections with a conn element and connection value
 *  .getConnection(url, user, pw) returns the connection, removes from avail
 *  conn, and then puts it on the busy list with a new connection element.
 *
 *@author     Matt Rajkowski
 *@created    December 12, 2000
 *@version    $Id$
 */
public class ConnectionPool implements Runnable {

  //5 minutes then connection is closed

  //private String baseURL = "jdbc:postgresql://12.101.73.29:5432/databasename";
  //private String baseURL = "jdbc:postgresql://127.0.0.1:5432/databasename";
  //private String username = "postgres";
  //private String password = "";

  String url = "";
  String username = "";
  String password = "";
  private java.util.Date startDate = new java.util.Date();
  private Hashtable availableConnections;
  private Hashtable busyConnections;
  private boolean connectionPending = false;
  private boolean requireParameters = true;
  private boolean debug = false;
  private String driver = "org.postgresql.Driver";

  private int maxConnections = 10;
  private boolean waitIfBusy = true;
  private boolean allowShrinking = true;
  private boolean forceClose = false;
  private Timer cleanupTimer = null;
  //60 seconds then connection is closed and no longer available
  private int maxIdleTime = 60000;
  //5 minutes then connection is shutdown and is no longer busy
  private int maxDeadTime = 300000;


  /**
   *  Constructor for the ConnectionPool object. <p>
   *
   *  Since connection parameters have not been defined, every getConnection
   *  statement will require a URL, username, and password to be specified.
   *
   *@exception  SQLException  Description of Exception
   *@since                    1.2
   */
  public ConnectionPool() throws SQLException {
    this.requireParameters = true;
    availableConnections = new Hashtable();
    busyConnections = new Hashtable();
    initializeCleanupTimer();
  }


  /**
   *  Standard constructor that specifies the url, username, password, the
   *  number of inital connections, and the number of max connections allowed
   *
   *@param  min               Description of Parameter
   *@param  max               Description of Parameter
   *@param  thisUrl           Description of Parameter
   *@param  thisUsername      Description of Parameter
   *@param  thisPassword      Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.0
   */
  public ConnectionPool(String thisUrl, String thisUsername,
      String thisPassword, int min, int max) throws SQLException {

    this.url = thisUrl;
    this.username = thisUsername;
    this.password = thisPassword;
    this.requireParameters = false;
    this.maxConnections = max;

    if (min > max) {
      min = max;
    }
    availableConnections = new Hashtable(min);
    busyConnections = new Hashtable();
    for (int i = 0; i < min; i++) {
      Connection thisConnection = makeNewConnection(this.url, this.username,
          this.password);
      if (thisConnection != null) {
        availableConnections.put(new ConnectionElement(), thisConnection);
      }
    }
    initializeCleanupTimer();
  }

  public void setDriver(String tmp) {
    this.driver = tmp;
  }

  /**
   *  Sets the Url attribute of the ConnectionPool object
   *
   *@param  tmp  The new Url value
   *@since       1.0
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Username attribute of the ConnectionPool object
   *
   *@param  tmp  The new Username value
   *@since       1.0
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   *  Sets the Password attribute of the ConnectionPool object
   *
   *@param  tmp  The new Password value
   *@since       1.0
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Sets the Debug attribute of the ConnectionPool object
   *
   *@param  tmp  The new Debug value
   *@since       1.0
   */
  public void setDebug(boolean tmp) {
    this.debug = tmp;
  }


  /**
   *  Sets the ForceClose attribute of the ConnectionPool object
   *
   *@param  tmp  The new ForceClose value
   *@since       1.1
   */
  public void setForceClose(boolean tmp) {
    this.forceClose = tmp;
  }


  /**
   *  Sets the MaxConnections attribute of the ConnectionPool object
   *
   *@param  tmp  The new MaxConnections value
   *@since       1.1
   */
  public void setMaxConnections(int tmp) {
    this.maxConnections = tmp;
  }


  /**
   *  Gets the Connection attribute of the ConnectionPool object when the
   *  ConnectionPool was created with a default connection
   *
   *@return                   The Connection value
   *@exception  SQLException  Description of Exception
   *@since                    1.2
   */
  public Connection getConnection() throws SQLException {
    if (requireParameters) {
      throw new SQLException("Connection information not specified and no default exists");
    } else {
      return (getConnection(url, username, password));
    }
  }


  /**
   *  Gets the Connection attribute of the ConnectionPool object using the
   *  parameters from the ConnectionElement
   *
   *@param  thisCE            Description of Parameter
   *@return                   The Connection value
   *@exception  SQLException  Description of Exception
   *@since                    1.2
   */
  public Connection getConnection(ConnectionElement thisCE)
       throws SQLException {
    if (thisCE == null) {
      throw new SQLException("Connection Element cannot be null");
    } else {
      return (getConnection(thisCE.getUrl(), thisCE.getUsername(), thisCE.getPassword()));
    }
  }


  /**
   *  When a connection is needed, ask the class for the next available, if the
   *  max connections has been reached then a thread will be spawned to wait for
   *  the next available connection.
   *
   *@param  thisUrl           Description of Parameter
   *@param  thisUsername      Description of Parameter
   *@param  thisPassword      Description of Parameter
   *@return                   The Connection value
   *@exception  SQLException  Description of Exception
   *@since                    1.0
   */
  public synchronized Connection getConnection(String thisUrl,
      String thisUsername, String thisPassword) throws SQLException {

    //Get an available matching connection
    if (!availableConnections.isEmpty()) {
      Enumeration e = availableConnections.keys();
      while (e.hasMoreElements()) {
        ConnectionElement ce = (ConnectionElement)e.nextElement();
        if (ce.getUrl().equals(thisUrl)) {
          try {
            Connection existingConnection =
                (Connection)availableConnections.get(ce);
            availableConnections.remove(ce);

            if (existingConnection.isClosed()) {
              existingConnection = null;
              notifyAll();
              // Freed up a spot for anybody waiting
              return (getConnection(thisUrl, thisUsername, thisPassword));
            } else {
              busyConnections.put(existingConnection,
                  new ConnectionElement(ce.getUrl(), ce.getUsername(),
                  ce.getPassword()));
              return (existingConnection);
            }
          } catch (NullPointerException npe) {
            return (getConnection(thisUrl, thisUsername, thisPassword));
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
          ConnectionElement ce = (ConnectionElement)e.nextElement();
          availableConnections.remove(ce);
        }
        e = null;
        return (getConnection(thisUrl, thisUsername, thisPassword));
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
      makeBackgroundConnection(thisUrl, thisUsername, thisPassword);
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
    return (getConnection(thisUrl, thisUsername, thisPassword));
  }


  /**
   *  Returns whether max connections has been reached for debugging
   *
   *@return    The MaxStatus value
   *@since     1.5
   */
  public synchronized boolean getMaxStatus() {
    if (busyConnections.size() == maxConnections) {
      return (true);
    } else {
      return (false);
    }
  }


  /**
   *  Displays the date/time when the ConnectionPool was created
   *
   *@return    The StartDate value
   *@since     1.2
   */
  public String getStartDate() {
    return (startDate.toString());
  }


  /**
   *  Main processing method for the ConnectionPool object, only 1 thread runs
   *  at a time since a connection can take a few seconds. While the user waits,
   *  if a connection is freed by another task first, then they will get that
   *  connection instead.
   *
   *@since    1.0
   */
  public void run() {
    // Make the connection
    try {
      Connection connection = makeNewConnection(this.url, this.username, this.password);
      synchronized (this) {

        if (connection != null) {
          if (debug) {
            System.out.println("ConnectionPool: Thread made a new database connection");
          }
          availableConnections.put(new ConnectionElement(this.url,
              this.username, this.password), connection);
        } else {
          if (debug) {
            System.out.println("ConnectionPool: Database connection could not be created");
          }
          availableConnections.put(new ConnectionElement(this.url,
              this.username, this.password), "Database Error");
        }

        connectionPending = false;
        notifyAll();
      }
    } catch (Exception e) {
      System.err.println("Connection Pool Thread Error: " + e.toString());
      // SQLException or OutOfMemory
      // Give up on new connection and wait for existing one
      // to free up.
    }
  }


  /**
   *  When finished with the connection, don't close it, free the connection and
   *  it will be reused by another request. If it's closed then remove the
   *  reference to it and another will just have to be opened.
   *
   *@param  connection  Description of Parameter
   *@since              1.0
   */
  public synchronized void free(Connection connection) {
    
    if (connection != null) {
      ConnectionElement ce = (ConnectionElement)busyConnections.get(connection);
      busyConnections.remove(connection);

      try {
        if (forceClose) {
          if (!connection.isClosed()) {
            connection.close();
          }
        }
  
        if (connection.isClosed()) {
          connection = null;
        } else {
          availableConnections.put(new ConnectionElement(ce.getUrl(),
              ce.getUsername(), ce.getPassword()), connection);
        }
  
      } catch (SQLException e) {
        connection = null;
      }
  
      // Wake up threads that are waiting for a connection
      notifyAll();
    }
  }


  /**
   *  Returns total connections allocated
   *
   *@return    Description of the Returned Value
   *@since     1.0
   */
  public int totalConnections() {
    return (availableConnections.size() + busyConnections.size());
  }


  /**
   *  Close all the connections. Use with caution: be sure no connections are in
   *  use before calling. Note that you are not <I>required</I> to call this
   *  when done with a ConnectionPool, since connections are guaranteed to be
   *  closed when garbage collected. But this method gives more control
   *  regarding when the connections are closed.
   *
   *@since    1.1
   */
  public synchronized void closeAllConnections() {
    closeConnections(2, availableConnections);
    availableConnections = new Hashtable();
    closeConnections(1, busyConnections);
    busyConnections = new Hashtable();
  }


  /**
   *  Simple way to close just the busy connections
   *
   *@since    1.1
   */
  public synchronized void closeBusyConnections() {
    closeConnections(1, busyConnections);
    busyConnections = new Hashtable();
  }


  /**
   *  More debugging information... displays the current state of the
   *  ConnectionPool
   *
   *@return    Description of the Returned Value
   *@since     1.1
   */
  public String toString() {
    String info =
        "ConnectionPool(available=" + availableConnections.size() +
        ", busy=" + busyConnections.size() +
        ", max=" + maxConnections + ")";
    return (info);
  }


  /**
   *  Cleans up the connection pool when destroyed, it's important to remove any
   *  open timers -- this does not appear to be automatically called so the
   *  application should call this to make sure the timer is stopped.
   *
   *@since    1.1
   */
  public void destroy() {
    if (cleanupTimer != null) {
      cleanupTimer.cancel();
      cleanupTimer = null;
      if (debug) {
        System.out.println("Connection Pool: Timer shut down");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
      }
    }
    System.out.println("Connection Pool: Stopped");
  }


  /**
   *  A timer to check and close idle connections
   *
   *@since    1.1
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
           *  Main processing method for the cleanupTask object
           *
           *@since    1.1
           */
          public void run() {
            //Gets rid of idle connections
            cleanupAvailableConnections();
            //Gets rid of deadlocked connections
            cleanupBusyConnections();
          }
        };
      cleanupTimer.scheduleAtFixedRate(cleanupTask, initialDelay, period);
    }
  }


  /**
   *  Looks through the available connections and checks for anything that has
   *  been open longer than the time allowed
   *
   *@since    1.2
   */
  private synchronized void cleanupAvailableConnections() {
    Enumeration e = availableConnections.keys();
    java.util.Date currentDate = new java.util.Date();
    while (e.hasMoreElements()) {
      ConnectionElement ce = null;
      try {
        ce = (ConnectionElement)e.nextElement();
        java.util.Date testDate = ce.getActiveDate();
        Connection connection = (Connection)availableConnections.get(ce);
        if (connection.isClosed() || (testDate.getTime() < (currentDate.getTime() - maxIdleTime))) {
          connection.close();
          availableConnections.remove(ce);
          if (debug) System.out.println("Removed an idle available connection");
          notify();
        }
      } catch (Exception sqle) {
        if (ce != null) {
          availableConnections.remove(ce);
          //if (debug) System.out.println("Removed a null available connection");
        }
        notify();
        //Ignore errors; garbage collect anyhow
      }

    }
    e = null;
  }


  /**
   *  Looks through the busy connections and checks for anything that has been
   *  open longer than the time allowed
   *
   *@since    1.2
   */
  private synchronized void cleanupBusyConnections() {
    try {
      Enumeration e = busyConnections.keys();
      java.util.Date currentDate = new java.util.Date();
      while (e.hasMoreElements()) {
        Connection connection = (Connection)e.nextElement();
        ConnectionElement ce = (ConnectionElement)busyConnections.get(connection);
        java.util.Date testDate = ce.getActiveDate();
        if (connection.isClosed() ||
            (testDate.getTime() < (currentDate.getTime() - maxDeadTime)) &&
            ce.getAllowCloseOnIdle()) {
          connection.close();
          busyConnections.remove(connection);
          if (debug) {
            System.out.println("Removed a possibly dead busy connection");
          }
          notify();
        }
      }
      e = null;
    } catch (SQLException sqle) {
      // Ignore errors; garbage collect anyhow
    }
  }


  /**
   *  Makes a connection to the database in a background thread.<p>
   *
   *  You can't just make a new connection in the foreground when none are
   *  available, since this can take several seconds with a slow network
   *  connection. Instead, start a thread that establishes a new connection,
   *  then wait. You get woken up either when the new connection is established
   *  or if someone finishes with an existing connection.
   *
   *@param  thisUrl       Description of Parameter
   *@param  thisUsername  Description of Parameter
   *@param  thisPassword  Description of Parameter
   *@since                1.0
   */
  private void makeBackgroundConnection(String thisUrl,
      String thisUsername, String thisPassword) {
    connectionPending = true;
    try {
      Thread connectThread = new Thread(this);
      this.setUrl(thisUrl);
      this.setUsername(thisUsername);
      this.setPassword(thisPassword);
      connectThread.start();
    } catch (OutOfMemoryError oome) {
      // Give up on new connection
    }
  }


  /**
   *  This explicitly makes a new connection. Called in the foreground when
   *  initializing the ConnectionPool, and called in the background when
   *  running.
   *
   *@param  thisUrl       Description of Parameter
   *@param  thisUsername  Description of Parameter
   *@param  thisPassword  Description of Parameter
   *@return               Description of the Returned Value
   *@since                1.0
   */
  private Connection makeNewConnection(String thisUrl,
      String thisUsername, String thisPassword) {
    try {
      // Load database driver if not already loaded
      Class.forName(driver);
      // Establish network connection to database
      Connection connection =
          DriverManager.getConnection(thisUrl, thisUsername, thisPassword);
      return (connection);
    } catch (Exception cnfe) {
      // Simplify try/catch blocks of people using this by
      // throwing only one exception type.
      //throw new SQLException("Can't find class for driver: " + driver);
      return null;
    }
  }


  /**
   *  Close connections -- useful for debugging
   *
   *@param  connections     Description of Parameter
   *@param  connectionType  Description of Parameter
   *@since                  1.1
   */
  private void closeConnections(int connectionType, Hashtable connections) {
    try {
      Enumeration e = connections.elements();
      while (e.hasMoreElements()) {
        Connection connection = null;
        if (connectionType == 1) {
          connection = (Connection)e.nextElement();
        } else {
          ConnectionElement ce = (ConnectionElement)e.nextElement();
          connection = (Connection)connections.get(ce);
        }
        if (!connection.isClosed()) {
          connection.close();
        }
      }
    } catch (SQLException sqle) {
      // Ignore errors; garbage collect anyhow
    }
  }

}

