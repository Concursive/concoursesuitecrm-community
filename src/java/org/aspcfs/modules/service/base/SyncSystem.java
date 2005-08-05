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

  private int id = -1;
  private String applicationName = null;
  private boolean enabled = false;

  public SyncSystem() {
  }


  public SyncSystem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("system_id");
    applicationName = rs.getString("application_name");
    enabled = rs.getBoolean("enabled");
  }

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
    id = DatabaseUtils.getCurrVal(db, "sync_system_system_id_seq", id);
  }
}

