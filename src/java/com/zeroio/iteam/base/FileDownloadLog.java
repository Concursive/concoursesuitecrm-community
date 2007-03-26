/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     matt rajkowski
 * @created    January 15, 2003
 * @version    $Id: FileDownloadLog.java,v 1.1.136.1 2004/03/19 21:00:50
 *      rvasista Exp $
 */
public class FileDownloadLog extends GenericBean {

  private int id = -1;
  private int itemId = -1;
  private double version = -1;
  private int userId = -1;
  private java.sql.Timestamp downloadDate = null;
  private int fileSize = 0;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;


  /**
   *  Gets the entered attribute of the FileDownloadLog object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the FileDownloadLog object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the FileDownloadLog object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modified attribute of the FileDownloadLog object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the FileDownloadLog object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the FileDownloadLog object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }



  /**
   *  Gets the id attribute of the FileDownloadLog object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the FileDownloadLog object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the FileDownloadLog object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Constructor for the FileDownloadLog object
   */
  public FileDownloadLog() { }


  /**
   *  Constructor for the FileDownloadLog object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public FileDownloadLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the FileDownloadLog object
   *
   * @param  db                Description of the Parameter
   * @param  itemId            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public FileDownloadLog(Connection db, int itemId) throws SQLException {
    if (itemId < 1) {
      throw new SQLException("ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT item_id, " + DatabaseUtils.addQuotes(db, "version") + ", user_download_id, download_date " +
        "FROM project_files_download d " +
        "WHERE d.item_id = ? ");
    pst.setInt(1, itemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Sets the itemId attribute of the FileDownloadLog object
   *
   * @param  tmp  The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   *  Sets the itemId attribute of the FileDownloadLog object
   *
   * @param  tmp  The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the version attribute of the FileDownloadLog object
   *
   * @param  tmp  The new version value
   */
  public void setVersion(double tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the version attribute of the FileDownloadLog object
   *
   * @param  tmp  The new version value
   */
  public void setVersion(String tmp) {
    this.version = Double.parseDouble(tmp);
  }


  /**
   *  Sets the userId attribute of the FileDownloadLog object
   *
   * @param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   *  Sets the userId attribute of the FileDownloadLog object
   *
   * @param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the downloadDate attribute of the FileDownloadLog object
   *
   * @param  tmp  The new downloadDate value
   */
  public void setDownloadDate(java.sql.Timestamp tmp) {
    this.downloadDate = tmp;
  }


  /**
   *  Sets the downloadDate attribute of the FileDownloadLog object
   *
   * @param  tmp  The new downloadDate value
   */
  public void setDownloadDate(String tmp) {
    this.downloadDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the fileSize attribute of the FileDownloadLog object
   *
   * @param  tmp  The new fileSize value
   */
  public void setFileSize(int tmp) {
    this.fileSize = tmp;
  }


  /**
   *  Gets the itemId attribute of the FileDownloadLog object
   *
   * @return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Gets the version attribute of the FileDownloadLog object
   *
   * @return    The version value
   */
  public double getVersion() {
    return version;
  }


  /**
   *  Gets the userId attribute of the FileDownloadLog object
   *
   * @return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the downloadDate attribute of the FileDownloadLog object
   *
   * @return    The downloadDate value
   */
  public java.sql.Timestamp getDownloadDate() {
    return downloadDate;
  }


  /**
   *  Gets the fileSize attribute of the FileDownloadLog object
   *
   * @return    The fileSize value
   */
  public int getFileSize() {
    return fileSize;
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("download_id");
    itemId = rs.getInt("item_id");
    version = rs.getDouble("version");
    userId = rs.getInt("user_download_id");
    downloadDate = rs.getTimestamp("download_date");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "project_files_download_download_id_seq");
    sql.append("INSERT INTO project_files_download ");
    sql.append("(item_id, " + DatabaseUtils.addQuotes(db, "version") + ", user_download_id, entered, modified, download_date) ");
    sql.append("VALUES (?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }else{
      sql.append("CURRENT_TIMESTAMP, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }else{
      sql.append("CURRENT_TIMESTAMP, ");
    }
    sql.append("? ) ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, itemId);
    pst.setDouble(++i, version);
    pst.setInt(++i, userId);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setTimestamp(++i, downloadDate);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "project_files_download_download_id_seq", id);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean updateCounter(Connection db) throws SQLException {
    if (itemId < 0 || version < 0) {
      return false;
    }
    //Record the raw number of downloads
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_files " +
        "SET downloads = (downloads + 1), " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE item_id = ? ");
    pst.setInt(1, itemId);
    pst.executeUpdate();
    pst.close();
    pst = db.prepareStatement(
        "UPDATE project_files_version " +
        "SET downloads = (downloads + 1), " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE item_id = ? " +
        "AND " + DatabaseUtils.addQuotes(db, "version") + " = ? ");
    pst.setInt(1, itemId);
    pst.setDouble(2, version);
    pst.executeUpdate();
    pst.close();
    //Track bandwidth used for downloads
    int usageId = DatabaseUtils.getNextSeq(db, "usage_log_usage_id_seq");
    String sql =
        "INSERT INTO usage_log " +
        "(" + (usageId > -1 ? "usage_id, " : "") + "enteredby, action, record_id, record_size) " +
        "VALUES (" + (usageId > -1 ? "?, " : "") + "?, ?, ?, ?) ";
    int i = 0;
    pst = db.prepareStatement(sql);
    if (usageId > -1) {
      pst.setInt(++i, usageId);
    }
    DatabaseUtils.setInt(pst, ++i, userId);
    pst.setInt(++i, 2);
    pst.setInt(++i, itemId);
    pst.setInt(++i, fileSize);
    pst.execute();
    pst.close();
    //Track each download by user (if not a guest)
    if (userId < 0) {
      return false;
    }
    sql =
        "INSERT INTO project_files_download " +
        "(item_id, " + DatabaseUtils.addQuotes(db, "version") + ", user_download_id) VALUES (?, ?, ?) ";
    i = 0;
    pst = db.prepareStatement(sql);
    pst.setInt(++i, itemId);
    pst.setDouble(++i, version);
    pst.setInt(++i, userId);
    pst.execute();
    pst.close();
    return true;
  }
}

