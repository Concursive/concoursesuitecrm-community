package com.darkhorseventures.utils;

import java.sql.*;
import org.theseus.actions.*;

public class AuthenticationItem {

  private String id = null;
  private String code = null;

  public AuthenticationItem() { }
  
  public void setId(String tmp) { id = tmp; }
  public void setCode(String tmp) { code = tmp; }

  public String getId() { return id; }
  public String getCode() { return code; }
  
  public Connection getConnection(ActionContext context) throws SQLException {
    String gkHost = (String)context.getServletContext().getAttribute("GKHOST");
    String gkUser = (String)context.getServletContext().getAttribute("GKUSER");
    String gkUserPw = (String)context.getServletContext().getAttribute("GKUSERPW");
    String siteCode = (String)context.getServletContext().getAttribute("SiteCode");
    String serverName = context.getRequest().getServerName();
    if (System.getProperty("DEBUG") != null) System.out.println("AuthenticationItem-> ServerName: " + serverName);
    
    if (id.equals(serverName)) {
      String authCode = null;
      ConnectionPool sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
      ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
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
        authCode = rs.getString("code");
        String dbName = rs.getString("dbname");
        ce = new ConnectionElement(
            rs.getString("dbhost") + ":" + rs.getInt("dbport") +
            "/" + dbName,
            rs.getString("dbuser"),
            rs.getString("dbpw")
            );
        ce.setDbName(dbName);
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

