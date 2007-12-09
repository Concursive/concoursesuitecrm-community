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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 *  Class for reading the database version information
 *
 * @author     matt rajkowski
 * @version    $Id: DatabaseVersion.java,v 1.1 2003/07/31 20:38:12 mrajkowski
 *      Exp $
 * @created    July 31, 2003
 */
public class DatabaseVersion {

  private int id = -1;
  private String scriptFilename = null;
  private String scriptVersion = null;
  private Timestamp entered = null;


  /**
   *  Constructor for the DatabaseVersion object
   */
  public DatabaseVersion() { }


  /**
   *  Gets the id attribute of the DatabaseVersion object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the DatabaseVersion object
   *
   * @param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Gets the scriptFilename attribute of the DatabaseVersion object
   *
   * @return    The scriptFilename value
   */
  public String getScriptFilename() {
    return scriptFilename;
  }


  /**
   *  Sets the scriptFilename attribute of the DatabaseVersion object
   *
   * @param  scriptFilename  The new scriptFilename value
   */
  public void setScriptFilename(String scriptFilename) {
    this.scriptFilename = scriptFilename;
  }


  /**
   *  Gets the scriptVersion attribute of the DatabaseVersion object
   *
   * @return    The scriptVersion value
   */
  public String getScriptVersion() {
    return scriptVersion;
  }


  /**
   *  Sets the scriptVersion attribute of the DatabaseVersion object
   *
   * @param  scriptVersion  The new scriptVersion value
   */
  public void setScriptVersion(String scriptVersion) {
    this.scriptVersion = scriptVersion;
  }


  /**
   *  Gets the entered attribute of the DatabaseVersion object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the DatabaseVersion object
   *
   * @param  entered  The new entered value
   */
  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }


  /**
   *  Constructor for the DatabaseVersion object
   *
   * @param  db                Description of the Parameter
   * @param  versionId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public DatabaseVersion(Connection db, int versionId) throws SQLException {
    queryRecord(db, versionId);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  versionId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int versionId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT dv.* " +
        "FROM database_version dv " +
        "WHERE dv.version_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Record Not Found");
    }
  }


  /**
   *  Constructor for the DatabaseVersion object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public DatabaseVersion(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("version_id");
    scriptFilename = rs.getString("script_filename");
    scriptVersion = rs.getString("script_version");
    entered = rs.getTimestamp("entered");
  }


  /**
   *  Description of the Method
   *
   * @param  db                      Description of the Parameter
   * @throws  SQLException           Description of the Exception
   * @throws  java.sql.SQLException  Description of the Exception
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
   *  Gets the latestVersion attribute of the DatabaseVersion class
   *
   * @param  db             Description of the Parameter
   * @return                The latestVersion value
   * @throws  SQLException  Description of the Exception
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


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  typeName       Description of the Parameter
   * @param  version        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void insertVersion(Connection db, String typeName, String version) throws SQLException {
    DatabaseVersion thisVersion = new DatabaseVersion();
    thisVersion.setScriptFilename(typeName);
    thisVersion.setScriptVersion(version);
    thisVersion.insert(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static ArrayList recordList(Connection db) throws SQLException {
    ArrayList records = new ArrayList();
    PreparedStatement pst = db.prepareStatement(
        "SELECT * FROM database_version " +
        "WHERE version_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      DatabaseVersion version = new DatabaseVersion(rs);
      records.add(version);
    }
    rs.close();
    pst.close();
    return records;
  }
}

