package com.darkhorseventures.utils;

/**
 *  A Connection Element has three purposes (so far).<p>
 *
 *  (1) The ConnectionPool associates each connection with a ConnectionElement
 *  object. This includes the connection string information, along with
 *  additional properties.<p>
 *
 *  (2) This object can be stored in a User's session and then supplied to the
 *  ConnectionPool for getting the appropriate connection.
 *
 *@author     mrajkowski
 *@created    July 10, 2001
 *@version    $Id$
 */
public class ConnectionElement {

  private String url = "jdbc:postgresql://127.0.0.1:5432/database";
  private String dbName = "";
  private String username = "";
  private String password = "";
  private java.util.Date activeDate = new java.util.Date();
  private boolean allowCloseOnIdle = true;


  /**
   *  Constructor for the ConnectionElement object
   *
   *@since    1.0
   */
  public ConnectionElement() { }


  /**
   *  Constructor for the ConnectionElement object
   *
   *@param  thisUrl       Description of Parameter
   *@param  thisUsername  Description of Parameter
   *@param  thisPassword  Description of Parameter
   *@since                1.0
   */
  public ConnectionElement(String thisUrl, String thisUsername,
      String thisPassword) {
    this.url = thisUrl;
    this.username = thisUsername;
    this.password = thisPassword;
  }


  /**
   *  Sets the Url attribute of the ConnectionElement object
   *
   *@param  tmp  The new Url value
   *@since       1.0
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Username attribute of the ConnectionElement object
   *
   *@param  tmp  The new Username value
   *@since       1.0
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   *  Sets the Password attribute of the ConnectionElement object
   *
   *@param  tmp  The new Password value
   *@since       1.0
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Sets the AllowCloseOnIdle attribute of the ConnectionElement object. <p>
   *
   *  Setting to false will prevent the Connection Pool from closing this
   *  connection when it appears to be broken.
   *
   *@param  tmp  The new AllowCloseOnIdle value
   *@since       1.0
   */
  public void setAllowCloseOnIdle(boolean tmp) {
    this.allowCloseOnIdle = tmp;
  }

  public void setDbName(String tmp) { this.dbName = tmp; }


  /**
   *  Gets the Url attribute of the ConnectionElement object. <p>
   *
   *  The format is "jdbc:postgresql://127.0.0.1:5432/databasename";
   *
   *@return    The Url value
   *@since     1.0
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the Username attribute of the ConnectionElement object
   *
   *@return    The Username value
   *@since     1.0
   */
  public String getUsername() {
    return username;
  }


  /**
   *  Gets the Password attribute of the ConnectionElement object
   *
   *@return    The Password value
   *@since     1.0
   */
  public String getPassword() {
    return password;
  }


  /**
   *  Gets the ActiveDate attribute of the ConnectionElement object
   *
   *@return    The ActiveDate value
   *@since     1.0
   */
  public java.util.Date getActiveDate() {
    return activeDate;
  }


  /**
   *  Gets the AllowCloseOnIdle attribute of the ConnectionElement object, if
   *  false then the connection will not be closed by the Connection Pool when
   *  it appears this connection is broken.
   *
   *@return    The AllowCloseOnIdle value
   *@since     1.0
   */
  public boolean getAllowCloseOnIdle() {
    return allowCloseOnIdle;
  }
  
  public String getDbName() { return dbName; }


}

