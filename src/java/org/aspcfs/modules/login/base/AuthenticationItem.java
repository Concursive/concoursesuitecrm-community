package com.darkhorseventures.utils;

import java.sql.*;
import org.theseus.actions.*;

public class AuthenticationItem {

  private String id = null;
  private String code = null;
  private int systemId = -1;
  private int clientId = -1;
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private String authCode = "unset";

  public AuthenticationItem() { }
  
  public void setId(String tmp) { id = tmp; }
  public void setCode(String tmp) { code = tmp; }
  public void setClientId(int tmp) { clientId = tmp; }
  public void setClientId(String tmp) { clientId = Integer.parseInt(tmp); }
  public void setSystemId(int tmp) { this.systemId = tmp; }
  public void setSystemId(String tmp) { this.systemId = Integer.parseInt(tmp); }
  public void setLastAnchor(java.sql.Timestamp tmp) { this.lastAnchor = tmp; }
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }
  public void setNextAnchor(java.sql.Timestamp tmp) { this.nextAnchor = tmp; }
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }

  public String getId() { return id; }
  public String getCode() { return code; }
  public int getClientId() { return clientId; }
  public int getSystemId() { return systemId; }
  public java.sql.Timestamp getLastAnchor() { return lastAnchor; }
  public java.sql.Timestamp getNextAnchor() { return nextAnchor; }

  public Connection getConnection(ActionContext context) throws SQLException {
	  return getConnection(context, true);
  }
  
  public ConnectionElement getConnectionElement(ActionContext context) throws SQLException {
    String gkHost = (String)context.getServletContext().getAttribute("GKHOST");
    String gkUser = (String)context.getServletContext().getAttribute("GKUSER");
    String gkUserPw = (String)context.getServletContext().getAttribute("GKUSERPW");
    String siteCode = (String)context.getServletContext().getAttribute("SiteCode");
    String gkDriver = (String)context.getServletContext().getAttribute("GKDRIVER");
    String serverName = context.getRequest().getServerName();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AuthenticationItem-> GateKeeper: " + gkHost);
      System.out.println("AuthenticationItem-> ServerName: " + serverName);
      System.out.println("AuthenticationItem-> SiteCode: " + siteCode);
    }
    ConnectionPool sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
    ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
    gk.setDriver(gkDriver);
    ConnectionElement ce = null;
    
    String sql = 
      "SELECT * FROM sites " +
      "WHERE sitecode = ? " +
      "AND vhost = ? ";
    Connection db = sqlDriver.getConnection(gk);
    PreparedStatement pst = db.prepareStatement(sql);
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
    sqlDriver.free(db);
    return ce;
  }
  
  public Connection getConnection(ActionContext context, boolean checkCode) throws SQLException {
    String serverName = context.getRequest().getServerName();
    if ( (id != null && id.equals(serverName)) || !checkCode ) {
      ConnectionElement ce = this.getConnectionElement(context);
      if (ce != null) {
        if (!checkCode || (code != null && authCode != null && code.equals(authCode))) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("AuthenticationItem-> Site: " + serverName + "/" + ce.getDbName());
          }
          ConnectionPool sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
          return sqlDriver.getConnection(ce);
        }
      }
    }
    return null;
  }
  
}

