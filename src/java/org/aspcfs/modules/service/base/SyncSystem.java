/*
 *  Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.service.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents an entry in the sync_system database table, used for mapping
 * requests to use the XML API to applicable classes
 *
 * @author matt rajkowski
 * @version $Id$
 * @created June 9, 2005
 */
public class SyncSystem extends GenericBean {

  public final static int HTTP_XML_API = 4;
  
  private int id = -1;
  private String applicationName = null;
  private boolean enabled = false;


  /**
   * Constructor for the SyncSystem object
   */
  public SyncSystem() {
  }


  /**
   * Constructor for the SyncSystem object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public SyncSystem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the id attribute of the SyncSystem object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the SyncSystem object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Gets the applicationName attribute of the SyncSystem object
   *
   * @return The applicationName value
   */
  public String getApplicationName() {
    return applicationName;
  }


  /**
   * Sets the applicationName attribute of the SyncSystem object
   *
   * @param applicationName The new applicationName value
   */
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }


  /**
   * Gets the enabled attribute of the SyncSystem object
   *
   * @return The enabled value
   */
  public boolean isEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the SyncSystem object
   *
   * @param enabled The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("system_id");
    applicationName = rs.getString("application_name");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "sync_system_system_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO sync_system " +
            "(" + (id > -1 ? "system_id, " : "") + "application_name, enabled" + ") " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, applicationName);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "sync_system_system_id_seq", id);
  }


  /**
   * Gets the systemId attribute of the SyncSystem class
   *
   * @param db              Description of the Parameter
   * @param applicationName Description of the Parameter
   * @return The systemId value
   * @throws SQLException Description of the Exception
   */
  public static int getSystemId(Connection db, String applicationName) throws SQLException {
    int systemId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT system_id " +
            "FROM sync_system " +
            "WHERE application_name = ? ");
    pst.setString(1, applicationName);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      systemId = rs.getInt("system_id");
    }
    rs.close();
    pst.close();
    return systemId;
  }
}

