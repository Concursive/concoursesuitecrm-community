package com.darkhorseventures.utils;

import java.sql.*;
import org.theseus.actions.*;

public class AuthenticationItem {

  private String id = null;
  private String code = null;
  private String system = null;
  private int systemId = -1;
  private int clientId = -1;

  public AuthenticationItem() { }
  
  public void setId(String tmp) { id = tmp; }
  public void setCode(String tmp) { code = tmp; }
  public void setClientId(int tmp) { clientId = tmp; }
  public void setClientId(String tmp) { clientId = Integer.parseInt(tmp); }
  public void setClient(String tmp) { system = tmp; }
  public void setSystem(String tmp) { this.system = tmp; }
  public void setSystemId(int tmp) { this.systemId = tmp; }
  public void setSystemId(String tmp) { this.systemId = Integer.parseInt(tmp); }

  public String getId() { return id; }
  public String getCode() { return code; }
  public int getClientId() { return clientId; }
  public String getClient() { return system; }
  public String getSystem() { return system; }
  public int getSystemId() { return systemId; }

  public Connection getConnection(ActionContext context) throws SQLException {
    String gkHost = (String)context.getServletContext().getAttribute("GKHOST");
    String gkUser = (String)context.getServletContext().getAttribute("GKUSER");
    String gkUserPw = (String)context.getServletContext().getAttribute("GKUSERPW");
    String siteCode = (String)context.getServletContext().getAttribute("SiteCode");
    String gkDriver = (String)context.getServletContext().getAttribute("GKDRIVER");
    String serverName = context.getRequest().getServerName();
    if (System.getProperty("DEBUG") != null) System.out.println("AuthenticationItem-> ServerName: " + serverName);
    
    if (id.equals(serverName)) {
      String authCode = null;
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
      
      if (code.equals(authCode)) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("AuthenticationItem-> Site: " + id + "/" + code);
        }
        return sqlDriver.getConnection(ce);
      }
    }
    return null;
  }
  
}

