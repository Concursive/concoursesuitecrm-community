//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.DateUtils;


public class AccessLog extends GenericBean {

  private int id = -1;
  private int userId = -1;
  private String username = "";
  private String ip = "";
  private String browser = "";

  private java.sql.Timestamp entered = null;

  public AccessLog() { }

  public AccessLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  public AccessLog(Connection db, int id) throws SQLException {
          queryRecord(db, id);
  }
  
  public AccessLog(Connection db, String id) throws SQLException {
          queryRecord(db, Integer.parseInt(id));
  }  
  
  public void queryRecord(Connection db, int id) throws SQLException {

    if (id == -1) {
      throw new SQLException("Invalid Access Log ID");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT a.* " +
        "FROM access_log a " +
        "WHERE a.id = " + id + " ");
    Statement st = null;
    ResultSet rs = null;
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Access Log not found");
    }
    rs.close();
    st.close();
  }
  
public int getId() { return id; }
public int getUserId() { return userId; }
public String getUsername() { return username; }
public String getIp() { return ip; }
public String getBrowser() { return browser; }
public void setId(int tmp) { this.id = tmp; }
public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
public void setUserId(int tmp) { this.userId = tmp; }
public void setUserId(String tmp) { this.userId = Integer.parseInt(tmp); }
public void setUsername(String tmp) { this.username = tmp; }
public void setIp(String tmp) { this.ip = tmp; }
public void setBrowser(String tmp) { this.browser = tmp; }


  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }
  

  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }

  public void insert(Connection db) throws SQLException {

    if (userId == -1) {
      throw new SQLException("Log Entry must be associated to a CFS User");
    }

    StringBuffer sql = new StringBuffer();

    try {

      db.setAutoCommit(false);

      sql.append(
          "INSERT INTO access_log (user_id, username, ip, browser) " +
          "VALUES ( ?, ?, ?, ? ) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getUserId());
      pst.setString(++i, this.getUsername());
      pst.setString(++i, this.getIp());
      pst.setString(++i, this.getBrowser());
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "access_log_id_seq");

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }

  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Access Log ID not specified.");
    }

    Statement st = db.createStatement();

    try {
      db.setAutoCommit(false);
      st.executeUpdate("DELETE FROM access_log WHERE id = " + this.getId());
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }

  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    userId = rs.getInt("user_id");
    username = rs.getString("username");
    ip = rs.getString("ip");
    browser = rs.getString("browser");
    entered = rs.getTimestamp("entered");
  }

}

