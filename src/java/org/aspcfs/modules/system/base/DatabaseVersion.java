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
package org.aspcfs.modules.system.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Class for reading the database version information
 *
 * @author matt rajkowski
 * @version $Id: DatabaseVersion.java,v 1.1 2003/07/31 20:38:12 mrajkowski Exp
 *          $
 * @created July 31, 2003
 */
public class DatabaseVersion {

  private int id = -1;
  private String scriptFilename = null;
  private String scriptVersion = null;
  private Timestamp entered = null;

  /**
   * Constructor for the DatabaseVersion object
   */
  public DatabaseVersion() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getScriptFilename() {
    return scriptFilename;
  }

  public void setScriptFilename(String scriptFilename) {
    this.scriptFilename = scriptFilename;
  }

  public String getScriptVersion() {
    return scriptVersion;
  }

  public void setScriptVersion(String scriptVersion) {
    this.scriptVersion = scriptVersion;
  }

  public Timestamp getEntered() {
    return entered;
  }

  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("version_id");
    scriptFilename = rs.getString("script_filename");
    scriptVersion = rs.getString("script_version");
    entered = rs.getTimestamp("entered");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "database_version_version_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO database_version " +
        "(" + (id > -1 ? "version_id, " : "") + "script_filename, " +
        "script_version) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, scriptFilename);
    pst.setString(++i, scriptVersion);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "database_version_version_id_seq", id);
  }


  /**
   * Gets the latestVersion attribute of the DatabaseVersion class
   *
   * @param db Description of the Parameter
   * @return The latestVersion value
   * @throws SQLException Description of the Exception
   */
  public static String getLatestVersion(Connection db) throws SQLException {
    String version = "";
    PreparedStatement pst = db.prepareStatement(
        "SELECT max(script_version) AS version " +
        "FROM database_version ");
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      version = rs.getString("version");
    }
    rs.close();
    pst.close();
    return version;
  }

  public static void insertVersion(Connection db, String typeName, String version) throws SQLException {
    DatabaseVersion thisVersion = new DatabaseVersion();
    thisVersion.setScriptFilename(typeName);
    thisVersion.setScriptVersion(version);
    thisVersion.insert(db);
  }
}

