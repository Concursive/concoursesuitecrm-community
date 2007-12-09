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
package org.aspcfs.modules.system.base;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a gatekeeper Site entry
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 13, 2003
 */
public class Site extends GenericBean {

  private int id = -1;
  private String siteCode = null;
  private String virtualHost = null;
  private String databaseUrl = null;
  private String databaseName = null;
  private int databasePort = -1;
  private String databaseUsername = null;
  private String databasePassword = null;
  private String databaseDriver = null;
  private String accessCode = null;
  private boolean enabled = true;
  private String language = null;


  /**
   * Constructor for the Site object
   */
  public Site() {
  }


  /**
   * Constructor for the Site object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Site(ResultSet rs) throws SQLException {
    this.buildRecord(rs);
  }


  /**
   * Constructor for the Site object, requires a Gatekeeper Connection
   *
   * @param db     Description of the Parameter
   * @param siteId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Site(Connection db, int siteId) throws SQLException {
    if (id == -1) {
      throw new SQLException("Site record not found.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM sites s " +
        "WHERE site_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Site record not found.");
    }
  }


  /**
   * Sets the id attribute of the Site object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Site object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the siteCode attribute of the Site object
   *
   * @param tmp The new siteCode value
   */
  public void setSiteCode(String tmp) {
    this.siteCode = tmp;
  }


  /**
   * Sets the virtualHost attribute of the Site object
   *
   * @param tmp The new virtualHost value
   */
  public void setVirtualHost(String tmp) {
    this.virtualHost = tmp;
  }


  /**
   * Sets the databaseUrl attribute of the Site object
   *
   * @param tmp The new databaseUrl value
   */
  public void setDatabaseUrl(String tmp) {
    this.databaseUrl = tmp;
  }


  /**
   * Sets the databaseName attribute of the Site object
   *
   * @param tmp The new databaseName value
   */
  public void setDatabaseName(String tmp) {
    this.databaseName = tmp;
  }


  /**
   * Sets the databasePort attribute of the Site object
   *
   * @param tmp The new databasePort value
   */
  public void setDatabasePort(int tmp) {
    this.databasePort = tmp;
  }


  /**
   * Sets the databasePort attribute of the Site object
   *
   * @param tmp The new databasePort value
   */
  public void setDatabasePort(String tmp) {
    this.databasePort = Integer.parseInt(tmp);
  }


  /**
   * Sets the databaseUsername attribute of the Site object
   *
   * @param tmp The new databaseUsername value
   */
  public void setDatabaseUsername(String tmp) {
    this.databaseUsername = tmp;
  }


  /**
   * Sets the databasePassword attribute of the Site object
   *
   * @param tmp The new databasePassword value
   */
  public void setDatabasePassword(String tmp) {
    this.databasePassword = tmp;
  }


  /**
   * Sets the databaseDriver attribute of the Site object
   *
   * @param tmp The new databaseDriver value
   */
  public void setDatabaseDriver(String tmp) {
    this.databaseDriver = tmp;
  }


  /**
   * Sets the accessCode attribute of the Site object
   *
   * @param tmp The new accessCode value
   */
  public void setAccessCode(String tmp) {
    this.accessCode = tmp;
  }


  /**
   * Sets the enabled attribute of the Site object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Site object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the Site object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the siteCode attribute of the Site object
   *
   * @return The siteCode value
   */
  public String getSiteCode() {
    return siteCode;
  }


  /**
   * Gets the virtualHost attribute of the Site object
   *
   * @return The virtualHost value
   */
  public String getVirtualHost() {
    return virtualHost;
  }


  /**
   * Gets the databaseUrl attribute of the Site object
   *
   * @return The databaseUrl value
   */
  public String getDatabaseUrl() {
    return databaseUrl;
  }


  /**
   * Gets the databaseName attribute of the Site object
   *
   * @return The databaseName value
   */
  public String getDatabaseName() {
    return databaseName;
  }


  /**
   * Gets the databasePort attribute of the Site object
   *
   * @return The databasePort value
   */
  public int getDatabasePort() {
    return databasePort;
  }


  /**
   * Gets the databaseUsername attribute of the Site object
   *
   * @return The databaseUsername value
   */
  public String getDatabaseUsername() {
    return databaseUsername;
  }


  /**
   * Gets the databasePassword attribute of the Site object
   *
   * @return The databasePassword value
   */
  public String getDatabasePassword() {
    return databasePassword;
  }


  /**
   * Gets the databaseDriver attribute of the Site object
   *
   * @return The databaseDriver value
   */
  public String getDatabaseDriver() {
    return databaseDriver;
  }


  /**
   * Gets the accessCode attribute of the Site object
   *
   * @return The accessCode value
   */
  public String getAccessCode() {
    return accessCode;
  }


  /**
   * Gets the enabled attribute of the Site object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String s) {
    language = s;
  }

  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //sites table
    id = rs.getInt("site_id");
    siteCode = rs.getString("sitecode");
    virtualHost = rs.getString("vhost");
    databaseUrl = rs.getString("dbhost");
    databaseName = rs.getString("dbname");
    databasePort = rs.getInt("dbport");
    databaseUsername = rs.getString("dbuser");
    databasePassword = rs.getString("dbpw");
    databaseDriver = rs.getString("driver");
    accessCode = rs.getString("code");
    enabled = rs.getBoolean("enabled");
    language = rs.getString("language");
  }

  public ConnectionElement getConnectionElement() {
    ConnectionElement ce = new ConnectionElement(
        databaseUrl, databaseUsername, databasePassword);
    ce.setDriver(databaseDriver);
    ce.setDbName(databaseName);
    return ce;
  }

}

