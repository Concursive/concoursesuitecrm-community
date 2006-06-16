/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    February 10, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */

public class SiteLog extends GenericBean {

  private int id = -1;
  private int siteId = -1;
  private int userId = -1;
  private String ip = "";
  private java.sql.Timestamp entered = null;
  private String browser = "";


  /**
   *  Constructor for the SiteLog object
   */
  public SiteLog() { }


  /**
   *  Constructor for the SiteLog object
   *
   *@param  db                Description of the Parameter
   *@param  tmpSiteLogId      Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public SiteLog(Connection db, int tmpSiteLogId) throws SQLException {
    queryRecord(db, tmpSiteLogId);
  }


  /**
   *  Constructor for the Site object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public SiteLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the SiteLog object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the SiteLog object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the site_id attribute of the SiteLog object
   *
   *@param  tmp  The new site_id value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the site_id attribute of the SiteLog object
   *
   *@param  tmp  The new site_id value
   */
  public void setSite_id(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the userId attribute of the SiteLog object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   *  Sets the userId attribute of the SiteLog object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ip attribute of the SiteLog object
   *
   *@param  tmp  The new ip value
   */
  public void setIp(String tmp) {
    this.ip = tmp;
  }


  /**
   *  Sets the entered attribute of the SiteLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the SiteLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the browser attribute of the SiteLog object
   *
   *@param  tmp  The new browser value
   */
  public void setBrowser(String tmp) {
    this.browser = tmp;
  }


  /**
   *  Gets the id attribute of the SiteLog object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the site_id attribute of the SiteLog object
   *
   *@return    The site_id value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Gets the userId attribute of the SiteLog object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the ip attribute of the SiteLog object
   *
   *@return    The ip value
   */
  public String getIp() {
    return ip;
  }


  /**
   *  Gets the entered attribute of the SiteLog object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the browser attribute of the SiteLog object
   *
   *@return    The browser value
   */
  public String getBrowser() {
    return browser;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@param  tmpSiteLogId   Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpSiteLogId) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT wsal.* " +
        "FROM web_site_access_log wsal " +
        "WHERE site_log_id = ? ");
    pst.setInt(1, tmpSiteLogId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {

    id = DatabaseUtils.getNextSeq(db, "web_site_access_log_site_log_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_site_access_log " +
        "(" + (id > -1 ? "site_log_id, " : "") +
        "site_id , " +
        "user_id , " +
        "ip , " +
        "browser ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, siteId);
    DatabaseUtils.setInt(pst, ++i, userId);
    pst.setString(++i, ip);
    pst.setString(++i, browser);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_site_access_log_site_log_id_seq", id);
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_site_access_log " +
          "WHERE site_log_id = ? ");

      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs  Description of the Parameter
   */
  public void buildRecord(ResultSet rs) throws SQLException{
    id = rs.getInt("site_log_id");
    siteId = rs.getInt("site_id");
    userId = rs.getInt("user_id");
    ip = rs.getString("ip");
    entered = rs.getTimestamp("entered");
    browser = rs.getString("browser");
  }
}

