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
package com.darkhorseventures.database;

import java.io.Serializable;

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
 *@version    $Id: ConnectionElement.java,v 1.4 2002/11/14 20:49:27 mrajkowski
 *      Exp $
 */
public class ConnectionElement implements Cloneable, Serializable {

  private String url = "jdbc:postgresql://127.0.0.1:5432/database";
  private String dbName = "";
  private String username = "";
  private String password = "";
  private String driver = null;
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


  /**
   *  Sets the dbName attribute of the ConnectionElement object
   *
   *@param  tmp  The new dbName value
   */
  public void setDbName(String tmp) {
    this.dbName = tmp;
  }


  /**
   *  Sets the driver attribute of the ConnectionElement object
   *
   *@param  tmp  The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   *  Sets the activeDate attribute of the ConnectionElement object
   *
   *@param  tmp  The new activeDate value
   */
  public void setActiveDate(java.util.Date tmp) {
    this.activeDate = tmp;
  }


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


  /**
   *  Gets the dbName attribute of the ConnectionElement object
   *
   *@return    The dbName value
   */
  public String getDbName() {
    return dbName;
  }


  /**
   *  Gets the driver attribute of the ConnectionElement object
   *
   *@return    The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   *  Description of the Method
   */
  public void renew() {
    activeDate = new java.util.Date();
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public Object clone() {
    try {
      return super.clone();
    } catch (java.lang.CloneNotSupportedException e) {
    }
    return null;
  }
}

