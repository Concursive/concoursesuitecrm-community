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
package org.aspcfs.modules.login.base;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  When a module needs to get a connection to the database, it must first be
 *  authenticated. Used by the Login module, XML transactions, and any of the
 *  Process modules that do not go through login.
 *
 * @author     matt rajkowski
 * @version    $Id: AuthenticationItem.java,v 1.13 2002/11/14 13:30:31
 *      mrajkowski Exp $
 * @created    November 11, 2002
 */
public class AuthenticationItem {
  //HTTP-XML API Authentication Header Types
  public final static int ASPMODE = 1;
  public final static int SYNC_CLIENT = 2;
  public final static int CENTRIC_USER = 3;
  public final static int SYNC_CLIENT_USER_BASED = 4;

  private String id = null;
  private String code = null;
  private int systemId = -1;
  private int clientId = -1;
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private String authCode = "unset";
  private String encoding = "UTF-8";
  private String username = null;
  private String url = null;


  /**
   *  Constructor for the AuthenticationItem object
   */
  public AuthenticationItem() { }


  /**
   *  Gets the url attribute of the AuthenticationItem object
   *
   * @return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Sets the url attribute of the AuthenticationItem object
   *
   * @param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the id attribute of the AuthenticationItem object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    id = tmp;
  }


  /**
   *  Sets the code attribute of the AuthenticationItem object, this is manually
   *  set by the module
   *
   * @param  tmp  The new code value
   */
  public void setCode(String tmp) {
    code = tmp;
  }


  /**
   *  Sets the clientId attribute of the AuthenticationItem object
   *
   * @param  tmp  The new clientId value
   */
  public void setClientId(int tmp) {
    clientId = tmp;
  }


  /**
   *  Sets the clientId attribute of the AuthenticationItem object
   *
   * @param  tmp  The new clientId value
   */
  public void setClientId(String tmp) {
    clientId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the systemId attribute of the AuthenticationItem object
   *
   * @param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the systemId attribute of the AuthenticationItem object
   *
   * @param  tmp  The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }




  /**
   *  Sets the authCode attribute of the AuthenticationItem object
   *
   * @param  tmp  The new authCode value
   */
  public void setAuthCode(String tmp) {
    this.authCode = tmp;
  }


  /**
   *  Sets the XML encoding attribute of the AuthenticationItem object. The
   *  encoding determines the encoding for all XML that will be returned.
   *
   * @param  tmp  The new encoding value
   */
  public void setEncoding(String tmp) {
    this.encoding = tmp;
  }


  /**
   *  Gets the username attribute of the AuthenticationItem object
   *
   * @return    The username value
   */
  public String getUsername() {
    return username;
  }


  /**
   *  Sets the username attribute of the AuthenticationItem object
   *
   * @param  tmp  The new username value
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   *  Gets the id attribute of the AuthenticationItem object
   *
   * @return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Gets the code attribute of the AuthenticationItem object
   *
   * @return    The code value
   */
  public String getCode() {
    return code;
  }


  /**
   *  Gets the clientId attribute of the AuthenticationItem object
   *
   * @return    The clientId value
   */
  public int getClientId() {
    return clientId;
  }


  /**
   *  Gets the systemId attribute of the AuthenticationItem object
   *
   * @return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   *  Gets the lastAnchor attribute of the AuthenticationItem object
   *
   * @return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Sets the lastAnchor attribute of the AuthenticationItem object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the AuthenticationItem object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the nextAnchor attribute of the AuthenticationItem object
   *
   * @return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Sets the nextAnchor attribute of the AuthenticationItem object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the AuthenticationItem object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }



  /**
   *  Gets the authCode attribute of the AuthenticationItem object
   *
   * @return    The authCode value
   */
  public String getAuthCode() {
    return authCode;
  }


  /**
   *  Gets the XML encoding attribute of the AuthenticationItem object. The
   *  encoding specifies the preferred XML encoding for the client.
   *
   * @return    The encoding value
   */
  public String getEncoding() {
    return encoding;
  }


  /**
   *  Determines if the HTTP-XML API authentication header contains regular
   *  centric user information or a sync client's information. More user types
   *  can be added
   *
   * @return    The type value
   */
  public int getType() {
    if (username != null && clientId > -1 && code != null) {
      return AuthenticationItem.SYNC_CLIENT_USER_BASED;
    }
    if (username != null && clientId > -1) {
      return -1;//can't determine if it is a user or a sync client
    }
    if (username != null) {
      return AuthenticationItem.CENTRIC_USER;
    }
    if (clientId > -1) {
      return AuthenticationItem.SYNC_CLIENT;
    }

    return -1;
  }


  /**
   *  Gets the connection attribute of the AuthenticationItem object using the
   *  context to validate
   *
   * @param  context        Description of the Parameter
   * @return                The connection value
   * @throws  SQLException  Description of the Exception
   */
  public Connection getConnection(ActionContext context) throws SQLException {
    return getConnection(context, true);
  }


  /**
   *  Based on the sitecode and servername (vhost) supplied in the
   *  authentication node, a connection element is returned, without performing
   *  any verification of the password
   *
   * @param  context        Description of the Parameter
   * @return                The connectionElement value
   * @throws  SQLException  Description of the Exception
   */
  public ConnectionElement getConnectionElement(ActionContext context) throws SQLException {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    //Query the gatekeeper for the connectionElement info
    String gkHost = prefs.get("GATEKEEPER.URL");
    String gkUser = prefs.get("GATEKEEPER.USER");
    String gkUserPw = prefs.get("GATEKEEPER.PASSWORD");
    String siteCode = prefs.get("GATEKEEPER.APPCODE");
    String gkDriver = prefs.get("GATEKEEPER.DRIVER");
    String serverName = context.getRequest().getServerName();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AuthenticationItem-> GateKeeper: " + gkHost);
      System.out.println("AuthenticationItem-> ServerName: " + serverName);
      System.out.println("AuthenticationItem-> SiteCode: " + siteCode);
      System.out.println("AuthenticationItem-> Driver: " + gkDriver);
    }
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
        "ConnectionPool");
    ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
    gk.setDriver(gkDriver);
    if (!"true".equals(
        (String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) {
      // This system is not configured with the sites table, must be a binary version
      gk.setDbName(prefs.get("GATEKEEPER.DATABASE"));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AuthenticationItem-> Database: " + gk.getDbName());
      }
      return gk;
    }
    Connection db = null;
    try {
      ConnectionElement ce = null;
      db = sqlDriver.getConnection(gk);
      PreparedStatement pst = db.prepareStatement(
          "SELECT * " +
          "FROM sites " +
          "WHERE sitecode = ? " +
          "AND vhost = ? ");
      pst.setString(1, siteCode);
      pst.setString(2, serverName);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        String siteDbHost = rs.getString("dbhost");
        String siteDbName = rs.getString("dbname");
        String siteDbUser = rs.getString("dbuser");
        String siteDbPw = rs.getString("dbpw");
        String siteDriver = rs.getString("driver");
        authCode = rs.getString("code");
        ce = new ConnectionElement(siteDbHost, siteDbUser, siteDbPw);
        ce.setDbName(siteDbName);
        ce.setDriver(siteDriver);
      }
      rs.close();
      pst.close();
      return ce;
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    } finally {
      if (db != null) {
        sqlDriver.free(db);
      }
    }
  }


  /**
   *  Returns a Connection, and can optionally make sure the connection is
   *  authenticated first
   *
   * @param  context        Description of the Parameter
   * @param  checkCode      Description of the Parameter
   * @return                The connection value
   * @throws  SQLException  Description of the Exception
   */
  public Connection getConnection(ActionContext context, boolean checkCode) throws SQLException {
    ConnectionElement ce = this.getConnectionElement(context);
    if (this.isAuthenticated(context, checkCode)) {
      if (ce != null) {
        ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
            "ConnectionPool");
        return sqlDriver.getConnection(ce);
      }
    }
    return null;
  }


  /**
   *  Gets the authenticated attribute of the AuthenticationItem object
   *
   * @param  context  Description of the Parameter
   * @return          The authenticated value
   */
  public boolean isAuthenticated(ActionContext context) {
    return this.isAuthenticated(context, true);
  }


  /**
   *  Gets the authenticated attribute of the AuthenticationItem object
   *
   * @param  context    Description of the Parameter
   * @param  checkCode  Description of the Parameter
   * @return            The authenticated value
   */
  public boolean isAuthenticated(ActionContext context, boolean checkCode) {
    String serverName = context.getRequest().getServerName();
    if (!checkCode) {
      return true;
    }
    if (id != null && id.equals(serverName)) {
      if (code != null && authCode != null && code.equals(authCode)) {
        return true;
      }
    }
    return false;
  }
}

