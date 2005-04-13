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
import java.text.DateFormat;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id: FileItemVersion.java,v 1.1 2003/01/15 15:52:56 mrajkowski Exp
 *      $
 */
public class FileItemVersion extends GenericBean {

  public final static String fs = System.getProperty("file.separator");

  private int id = -1;
  private String subject = "";
  private String clientFilename = "";
  private String filename = "";
  private String directory = "";
  private int size = 0;
  private double version = 0;
  private String image = null;
  private boolean enabled = false;
  private int downloads = 0;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private String enteredByString = "";
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private String modifiedByString = "";


  /**
   *  Constructor for the FileItem object
   */
  public FileItemVersion() { }


  /**
   *  Constructor for the FileItem object
   *
   *@param  db                Description of Parameter
   *@param  itemId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@deprecated
   */
  public FileItemVersion(Connection db, int itemId) throws SQLException {
    String sql =
        "SELECT v.* " +
        "FROM project_files_version v " +
        "WHERE v.item_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, itemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("File version record not found.");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Constructor for the FileItem object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public FileItemVersion(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the FileItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the FileItemVersion object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the itemId attribute of the FileItemVersion object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the itemId attribute of the FileItemVersion object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subject attribute of the FileItem object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the clientFilename attribute of the FileItem object
   *
   *@param  tmp  The new clientFilename value
   */
  public void setClientFilename(String tmp) {
    this.clientFilename = tmp;
  }


  /**
   *  Sets the filename attribute of the FileItem object
   *
   *@param  tmp  The new filename value
   */
  public void setFilename(String tmp) {
    this.filename = tmp;
  }


  /**
   *  Sets the directory attribute of the FileItem object
   *
   *@param  tmp  The new directory value
   */
  public void setDirectory(String tmp) {
    this.directory = tmp;
  }


  /**
   *  Sets the size attribute of the FileItem object
   *
   *@param  tmp  The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the size attribute of the FileItemVersion object
   *
   *@param  tmp  The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   *  Sets the version attribute of the FileItem object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(double tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the version attribute of the FileItemVersion object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(String tmp) {
    this.version = Double.parseDouble(tmp);
  }


  /**
   *  Sets the image attribute of the FileItem object
   *
   *@param  tmp  The new image value
   */
  public void setImage(String tmp) {
    this.image = tmp;
  }


  /**
   *  Sets the entered attribute of the FileItem object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the FileItemVersion object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the FileItem object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the FileItemVersion object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredByString attribute of the FileItem object
   *
   *@param  tmp  The new enteredByString value
   */
  public void setEnteredByString(String tmp) {
    this.enteredByString = tmp;
  }


  /**
   *  Sets the modified attribute of the FileItem object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the FileItemVersion object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the FileItem object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the FileItemVersion object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedByString attribute of the FileItem object
   *
   *@param  tmp  The new modifiedByString value
   */
  public void setModifiedByString(String tmp) {
    this.modifiedByString = tmp;
  }


  /**
   *  Sets the downloads attribute of the FileItemVersion object
   *
   *@param  tmp  The new downloads value
   */
  public void setDownloads(int tmp) {
    downloads = tmp;
  }


  /**
   *  Sets the downloads attribute of the FileItemVersion object
   *
   *@param  tmp  The new downloads value
   */
  public void setDownloads(String tmp) {
    downloads = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the FileItem object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the FileItemVersion object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Gets the id attribute of the FileItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the subject attribute of the FileItem object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the clientFilename attribute of the FileItem object
   *
   *@return    The clientFilename value
   */
  public String getClientFilename() {
    return clientFilename;
  }


  /**
   *  Gets the extension attribute of the FileItem object
   *
   *@return    The extension value
   */
  public String getExtension() {
    if (clientFilename.indexOf(".") > 0) {
      return clientFilename.substring(clientFilename.lastIndexOf(".")).toLowerCase();
    } else {
      return "";
    }
  }


  /**
   *  Gets the filename attribute of the FileItem object
   *
   *@return    The filename value
   */
  public String getFilename() {
    return filename;
  }


  /**
   *  Gets the directory attribute of the FileItem object
   *
   *@return    The directory value
   */
  public String getDirectory() {
    return directory;
  }


  /**
   *  Gets the size attribute of the FileItem object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the relativeSize attribute of the FileItem object
   *
   *@return    The relativeSize value
   */
  public int getRelativeSize() {
    int newSize = (size / 1000);
    if (newSize == 0) {
      return 1;
    } else {
      return newSize;
    }
  }


  /**
   *  Gets the version attribute of the FileItem object
   *
   *@return    The version value
   */
  public double getVersion() {
    return version;
  }


  /**
   *  Gets the versionNextMajor attribute of the FileItem object
   *
   *@return    The versionNextMajor value
   */
  public String getVersionNextMajor() {
    return (((int) version + 1) + ".0");
  }


  /**
   *  Gets the versionNextMinor attribute of the FileItem object
   *
   *@return    The versionNextMinor value
   */
  public String getVersionNextMinor() {
    String value = String.valueOf(version);
    if (value.indexOf(".") > -1) {
      value = value.substring(0, value.indexOf(".") + 2);
    }

    String newVersion = (new java.math.BigDecimal(value).add(new java.math.BigDecimal(".1"))).toString();
    if (Double.parseDouble(newVersion) > (Double.parseDouble(getVersionNextMajor()))) {
      return getVersionNextMajor();
    } else {
      return newVersion;
    }
  }


  /**
   *  Gets the versionNextChanges attribute of the FileItem object
   *
   *@return    The versionNextChanges value
   */
  public String getVersionNextChanges() {
    String newVersion = (new java.math.BigDecimal("" + version).add(new java.math.BigDecimal(".01"))).toString();
    return newVersion;
  }


  /**
   *  Gets the image attribute of the FileItem object
   *
   *@return    The image value
   */
  public String getImage() {
    return image;
  }


  /**
   *  Gets the imageTag attribute of the FileItem object
   *
   *@return    The imageTag value
   */
  public String getImageTag() {
    return FileItem.getImageTag(image, "", FileItem.getExtension(clientFilename));
  }


  /**
   *  Gets the entered attribute of the FileItem object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the FileItem object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(3).format(entered);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the enteredDateTimeString attribute of the FileItem object
   *
   *@return    The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(3, 3).format(entered);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the enteredBy attribute of the FileItem object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the enteredByString attribute of the FileItem object
   *
   *@return    The enteredByString value
   */
  public String getEnteredByString() {
    return enteredByString;
  }


  /**
   *  Gets the modified attribute of the FileItem object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the FileItem object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(3).format(modified);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the modifiedDateTimeString attribute of the FileItem object
   *
   *@return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(3, 3).format(modified);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the modifiedBy attribute of the FileItem object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modifiedByString attribute of the FileItem object
   *
   *@return    The modifiedByString value
   */
  public String getModifiedByString() {
    return modifiedByString;
  }


  /**
   *  Gets the downloads attribute of the FileItem object
   *
   *@return    The downloads value
   */
  public int getDownloads() {
    return downloads;
  }


  /**
   *  Gets the enabled attribute of the FileItem object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO project_files_version ");
    sql.append("(item_id, subject, client_filename, filename, version, size, ");
    sql.append("enabled, downloads, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, id);
    pst.setString(++i, subject);
    pst.setString(++i, clientFilename);
    pst.setString(++i, filename);
    pst.setDouble(++i, version);
    pst.setInt(++i, size);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, downloads);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean update(Connection db) throws SQLException {
    String sql =
        "UPDATE project_files_version " +
        "SET subject = ?, client_filename = ? " +
        "WHERE item_id = ? " +
        "AND version = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, subject);
    pst.setString(++i, clientFilename);
    pst.setInt(++i, this.getId());
    pst.setDouble(++i, this.getVersion());
    pst.execute();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public synchronized boolean updateCounter(Connection db) throws SQLException {
    FileDownloadLog thisLog = new FileDownloadLog();
    thisLog.setItemId(id);
    thisLog.setVersion(version);
    thisLog.setUserId(enteredBy);
    thisLog.setFileSize(size);
    return thisLog.updateCounter(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  baseFilePath      Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {

    //Need to delete the actual file
    String filePath = baseFilePath + FileItem.getDatePath(this.getEntered()) + this.getFilename();
    java.io.File fileToDelete = new java.io.File(filePath);
    if (!fileToDelete.delete()) {
      System.err.println("FileItemVersion-> Tried to delete file: " + filePath);
    }

    //Delete database record
    String sql =
        "DELETE FROM project_files_version " +
        "WHERE item_id = ? " +
        "AND version = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, this.getId());
    pst.setDouble(2, this.getVersion());
    pst.execute();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("item_id");
    clientFilename = rs.getString("client_filename");
    filename = rs.getString("filename");
    subject = rs.getString("subject");
    size = rs.getInt("size");
    version = rs.getDouble("version");
    enabled = rs.getBoolean("enabled");
    downloads = rs.getInt("downloads");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");
  }
}

