/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.sql.*;
import java.util.*;
import java.text.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    February 8, 2002
 *@version    $Id$
 */
public class FileItem extends GenericBean {

  public final static String fs = System.getProperty("file.separator");

  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int id = -1;
  private int folderId = -1;
  private String subject = "";
  private String clientFilename = "";
  private String filename = "";
  private String directory = "";
  private int size = 0;
  private double version = 0;
  private String image = null;
  private boolean enabled = false;

  private boolean doVersionInsert = true;
  private int downloads = 0;

  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;

  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;

  private FileItemVersionList versionList = null;

  private String thumbnailFilename = null;


  /**
   *  Constructor for the FileItem object
   */
  public FileItem() { }


  /**
   *  Constructor for the FileItem object
   *
   *@param  db                Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public FileItem(Connection db, int itemId) throws SQLException {
    queryRecord(db, itemId);
  }


  /**
   *  Constructor for the FileItem object
   *
   *@param  db                Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@param  moduleItemId      Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public FileItem(Connection db, int itemId, int moduleItemId, int moduleId) throws SQLException {
    this.linkModuleId = moduleId;
    this.linkItemId = moduleItemId;
    queryRecord(db, itemId);
  }


  /**
   *  Constructor for the FileItem object when the linkItemId is programmed to
   *  have only ONE fileItem
   *
   *@param  db                Description of the Parameter
   *@param  moduleItemId      Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public FileItem(Connection db, int moduleItemId, int moduleId) throws SQLException {
    this.linkModuleId = moduleId;
    this.linkItemId = moduleItemId;
    queryRecord(db, -1);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void queryRecord(Connection db, int itemId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT f.*, t.filename AS thumbnail " +
        "FROM project_files f " +
        "LEFT JOIN project_files_thumbnail t ON (f.item_id = t.item_id AND f.version = t.version) " +
        "WHERE f.item_id > 0 ");
    if (itemId > -1) {
      sql.append("AND f.item_id = ? ");
    }
    if (linkModuleId > -1) {
      sql.append("AND f.link_module_id = ? ");
    }
    if (linkItemId > -1) {
      sql.append("AND f.link_item_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    if (itemId > -1) {
      pst.setInt(++i, itemId);
    }
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs, false);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("File record not found.");
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
  public FileItem(ResultSet rs) throws SQLException {
    buildRecord(rs, false);
  }


  /**
   *  Constructor for the FileItem object
   *
   *@param  rs                Description of Parameter
   *@param  isVersion         Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public FileItem(ResultSet rs, boolean isVersion) throws SQLException {
    buildRecord(rs, isVersion);
  }


  /**
   *  Gets the datePath attribute of the FileItem class
   *
   *@param  fileDate  Description of Parameter
   *@return           The datePath value
   */
  public final static String getDatePath(java.sql.Timestamp fileDate) {
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(fileDate);
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(fileDate);
    return datePathToUse1 + fs + datePathToUse2 + fs;
  }


  /**
   *  Gets the datePath attribute of the FileItem class
   *
   *@param  filenameDate  Description of the Parameter
   *@return               The datePath value
   */
  public static String getDatePath(String filenameDate) {
    if (filenameDate.length() > 7) {
      return (filenameDate.substring(0, 4) + fs +
          filenameDate.substring(4, 8) + fs);
    } else {
      return null;
    }
  }


  /**
   *  Gets the fullFilePath attribute of the FileItem object
   *
   *@return    The fullFilePath value
   */
  public String getFullFilePath() {
    if ("".equals(directory)) {
      return filename;
    } else {
      if (modified != null) {
        return directory + getDatePath(modified) + filename;
      } else {
        return directory + getDatePath(filename) + filename;
      }
    }
  }


  /**
   *  Sets the linkModuleId attribute of the FileItem object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the FileItem object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkItemId attribute of the FileItem object
   *
   *@param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    linkItemId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the FileItem object
   *
   *@param  tmp  The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    linkItemId = Integer.parseInt(tmp);
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
   *  Sets the id attribute of the FileItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the folderId attribute of the FileItem object
   *
   *@param  tmp  The new folderId value
   */
  public void setFolderId(int tmp) {
    this.folderId = tmp;
  }


  /**
   *  Sets the folderId attribute of the FileItem object
   *
   *@param  tmp  The new folderId value
   */
  public void setFolderId(String tmp) {
    this.folderId = Integer.parseInt(tmp);
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
   *  Sets the size attribute of the FileItem object
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
   *  Sets the version attribute of the FileItem object
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
   *  Sets the entered attribute of the FileItem object
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
   *  Sets the enteredBy attribute of the FileItem object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
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
   *  Sets the modified attribute of the FileItem object
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
   *  Sets the modifiedBy attribute of the FileItem object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
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
   *  Sets the enabled attribute of the FileItem object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the downloads attribute of the FileItem object
   *
   *@param  tmp  The new downloads value
   */
  public void setDownloads(int tmp) {
    downloads = tmp;
  }


  /**
   *  Sets the downloads attribute of the FileItem object
   *
   *@param  tmp  The new downloads value
   */
  public void setDownloads(String tmp) {
    downloads = Integer.parseInt(tmp);
  }


  /**
   *  Sets the doVersionInsert attribute of the FileItem object
   *
   *@param  tmp  The new doVersionInsert value
   */
  public void setDoVersionInsert(boolean tmp) {
    this.doVersionInsert = tmp;
  }


  /**
   *  Sets the doVersionInsert attribute of the FileItem object
   *
   *@param  tmp  The new doVersionInsert value
   */
  public void setDoVersionInsert(String tmp) {
    this.doVersionInsert = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the thumbnailFilename attribute of the FileItem object
   *
   *@param  tmp  The new thumbnailFilename value
   */
  public void setThumbnailFilename(String tmp) {
    this.thumbnailFilename = tmp;
  }


  /**
   *  Gets the doVersionInsert attribute of the FileItem object
   *
   *@return    The doVersionInsert value
   */
  public boolean getDoVersionInsert() {
    return doVersionInsert;
  }


  /**
   *  Gets the linkModuleId attribute of the FileItem object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the linkItemId attribute of the FileItem object
   *
   *@return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
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
   *  Gets the folderId attribute of the FileItem object
   *
   *@return    The folderId value
   */
  public int getFolderId() {
    return folderId;
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
    return getExtension(clientFilename);
  }


  /**
   *  Gets the extension attribute of the FileItem class
   *
   *@param  clientFilename  Description of the Parameter
   *@return                 The extension value
   */
  public static String getExtension(String clientFilename) {
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
   *  Description of the Method
   *
   *@param  version  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean hasVersion(double version) {
    return (getVersion(version) != null ? true : false);
  }


  /**
   *  Gets the version attribute of the FileItem object
   *
   *@param  version  Description of Parameter
   *@return          The version value
   */
  public FileItemVersion getVersion(double version) {
    if (versionList != null) {
      Iterator versions = this.versionList.iterator();
      while (versions.hasNext()) {
        FileItemVersion thisVersion = (FileItemVersion) versions.next();
        if (thisVersion.getVersion() == version) {
          return thisVersion;
        }
      }
    }
    return null;
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
    return getImageTag("");
  }


  /**
   *  Gets the imageTag attribute of the FileItem object
   *
   *@param  imageExt  Description of the Parameter
   *@return           The imageTag value
   */
  public String getImageTag(String imageExt) {
    return getImageTag(image, imageExt, getExtension());
  }


  /**
   *  Gets the imageTag attribute of the FileItem object
   *
   *@param  imageExt  Description of the Parameter
   *@param  image     Description of the Parameter
   *@param  ext       Description of the Parameter
   *@return           The imageTag value
   */
  public static String getImageTag(String image, String imageExt, String ext) {
    if (image == null) {
      if (".bmp".equals(ext)) {
        image = "gnome-image-bmp";
      } else if (".dia".equals(ext)) {
        image = "gnome-application-x-dia-diagram";
      } else if (".doc".equals(ext)) {
        image = "gnome-application-msword";
      } else if (".eps".equals(ext)) {
        image = "gnome-application-encapsulated_postscript";
      } else if (".gif".equals(ext)) {
        image = "gnome-image-gif";
      } else if (".gz".equals(ext)) {
        image = "gnome-compressed";
      } else if (".gzip".equals(ext)) {
        image = "gnome-compressed";
      } else if (".html".equals(ext)) {
        image = "gnome-text-html";
      } else if (".jar".equals(ext)) {
        image = "gnome-application-x-jar";
      } else if (".java".equals(ext)) {
        image = "gnome-application-x-java-source";
      } else if (".jpeg".equals(ext)) {
        image = "gnome-image-jpeg";
      } else if (".jpg".equals(ext)) {
        image = "gnome-image-jpeg";
      } else if (".midi".equals(ext)) {
        image = "gnome-audio-midi";
      } else if (".mp3".equals(ext)) {
        image = "gnome-audio-mpg";
      } else if (".mpeg".equals(ext)) {
        image = "gnome-video-mpeg";
      } else if (".mpg".equals(ext)) {
        image = "gnome-video-mpeg";
      } else if (".pdf".equals(ext)) {
        image = "gnome-application-pdf";
      } else if (".png".equals(ext)) {
        image = "gnome-image-png";
      } else if (".ppt".equals(ext)) {
        image = "gnome-application-vnd.ms-powerpoint";
      } else if (".psd".equals(ext)) {
        image = "gnome-image-psd";
      } else if (".ps".equals(ext)) {
        image = "gnome-application-postscript";
      } else if (".sql".equals(ext)) {
        image = "gnome-text-x-sql";
      } else if (".tgz".equals(ext)) {
        image = "gnome-compressed";
      } else if (".tif".equals(ext)) {
        image = "gnome-image-tiff";
      } else if (".tiff".equals(ext)) {
        image = "gnome-image-tiff";
      } else if (".wav".equals(ext)) {
        image = "gnome-audio-x-wav";
      } else if (".xls".equals(ext)) {
        image = "gnome-application-vnd.ms-excel";
      } else if (".zip".equals(ext)) {
        image = "gnome-compressed";
      } else {
        image = "gnome-text-plain";
      }
    }
    return "<img border=\"0\" src=\"images/mime/" + image + imageExt + ".gif\" align=\"absmiddle\" alt=\"" + "" + "\">";
  }


  /**
   *  Gets the thumbnail attribute of the FileItem object
   *
   *@return    The thumbnail value
   */
  public String getThumbnail() {
    if (hasThumbnail()) {
      return "<img border=\"0\" src=\"ProjectManagementFiles.do?command=ShowThumbnail&p=" + linkItemId + "&i=" + id + "&v=" + version + "\" align=\"absmiddle\" alt=\"\">";
    } else {
      return getImageTag();
    }
  }


  /**
   *  Gets the fullImage attribute of the FileItem object
   *
   *@return    The fullImage value
   */
  public String getFullImage() {
    if (isImageFormat()) {
      return "<img border=\"0\" src=\"ProjectManagementFiles.do?command=ShowThumbnail&p=" + linkItemId + "&i=" + id + "&v=" + version + "&s=full" + "\" align=\"absmiddle\" alt=\"\">";
    } else {
      return getImageTag();
    }
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
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
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
   *  Gets the versionList attribute of the FileItem object
   *
   *@return    The versionList value
   */
  public FileItemVersionList getVersionList() {
    return versionList;
  }


  /**
   *  Gets the thumbnailFilename attribute of the FileItem object
   *
   *@return    The thumbnailFilename value
   */
  public String getThumbnailFilename() {
    return thumbnailFilename;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid()) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("FileItem-> IS INVALID");
      }
      return false;
    }

    boolean result = false;
    try {
      StringBuffer sql = new StringBuffer();
      sql.append(
          "INSERT INTO project_files " +
          "(folder_id, subject, client_filename, filename, version, size, ");
      sql.append("enabled, downloads, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append(" link_module_id, link_item_id, " +
          " enteredBy, modifiedBy ) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (folderId > 0) {
        pst.setInt(++i, folderId);
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
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
      pst.setInt(++i, linkModuleId);
      pst.setInt(++i, linkItemId);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "project_files_item_id_seq");

      //Insert the version information
      if (doVersionInsert) {
        FileItemVersion thisVersion = new FileItemVersion();
        thisVersion.setId(this.getId());
        thisVersion.setSubject(subject);
        thisVersion.setClientFilename(clientFilename);
        thisVersion.setFilename(filename);
        thisVersion.setVersion(version);
        thisVersion.setSize(size);
        thisVersion.setEnteredBy(enteredBy);
        thisVersion.setModifiedBy(modifiedBy);
        thisVersion.insert(db);
      }
      logUpload(db);
      db.commit();
      result = true;
    } catch (Exception e) {
      db.rollback();
      e.printStackTrace(System.out);
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insertVersion(Connection db) throws SQLException {
    if (!isValid()) {
      return false;
    }

    boolean result = false;
    try {
      db.setAutoCommit(false);
      //Insert a new version of an existing file
      FileItemVersion thisVersion = new FileItemVersion();
      thisVersion.setId(this.getId());
      thisVersion.setSubject(subject);
      thisVersion.setClientFilename(clientFilename);
      thisVersion.setFilename(filename);
      thisVersion.setVersion(version);
      thisVersion.setSize(size);
      thisVersion.setEnteredBy(enteredBy);
      thisVersion.setModifiedBy(modifiedBy);
      thisVersion.insert(db);

      //Update the master record
      String sql =
          "UPDATE project_files " +
          "SET subject = ?, client_filename = ?, filename = ?, version = ?, " +
          "size = ?, modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
          "WHERE item_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setString(++i, subject);
      pst.setString(++i, clientFilename);
      pst.setString(++i, filename);
      pst.setDouble(++i, version);
      pst.setInt(++i, size);
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      logUpload(db);

      db.commit();
      result = true;
    } catch (Exception e) {
      System.err.println("FileItem-> InsertVersion Error: " + e.toString());
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }

    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean update(Connection db) throws SQLException {
    if (!isValid()) {
      return false;
    }

    String sql =
        "UPDATE project_files " +
        "SET subject = ?, client_filename = ? " +
        "WHERE item_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, subject);
    pst.setString(++i, clientFilename);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();

    this.buildVersionList(db);
    Iterator iterator = (Iterator) this.versionList.iterator();
    while (iterator.hasNext()) {
      FileItemVersion latestVersion = (FileItemVersion) iterator.next();
      if (Double.toString(this.version).equals(Double.toString(latestVersion.getVersion()))) {
        latestVersion.setClientFilename(this.getClientFilename());
        latestVersion.setSubject(this.getSubject());
        latestVersion.update(db);
        break;
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  thisVersion       Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean updateVersion(Connection db, FileItemVersion thisVersion) throws SQLException {
    // Set the master record
    subject = thisVersion.getSubject();
    clientFilename = thisVersion.getClientFilename();
    filename = thisVersion.getFilename();
    version = thisVersion.getVersion();
    size = thisVersion.getSize();
    enteredBy = thisVersion.getEnteredBy();
    modifiedBy = thisVersion.getModifiedBy();
    // Update the master record
    String sql =
        "UPDATE project_files " +
        "SET subject = ?, client_filename = ?, filename = ?, version = ?, " +
        "size = ?, modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE item_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, subject);
    pst.setString(++i, clientFilename);
    pst.setString(++i, filename);
    pst.setDouble(++i, version);
    pst.setInt(++i, size);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());
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
  public boolean updateCounter(Connection db) throws SQLException {
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
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean buildVersionList(Connection db) throws SQLException {
    if (versionList == null) {
      versionList = new FileItemVersionList();
    } else {
      versionList.clear();
    }
    versionList.setItemId(this.getId());
    versionList.buildList(db);
    return true;
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
    if (!isValid()) {
      return false;
    }
    this.buildVersionList(db);
    //Need to delete the actual files
    Iterator versions = this.getVersionList().iterator();
    while (versions.hasNext()) {
      FileItemVersion thisVersion = (FileItemVersion) versions.next();
      String filePath = baseFilePath + getDatePath(thisVersion.getEntered()) + thisVersion.getFilename();
      java.io.File fileToDelete = new java.io.File(filePath);
      if (!fileToDelete.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath);
      }
      java.io.File thumbnailToDelete = new java.io.File(filePath + "TH");
      thumbnailToDelete.delete();
    }
    boolean result = false;
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //Delete the log of downloads
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM project_files_download " +
          "WHERE item_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      //Delete the thumbnail
      i = 0;
      pst = db.prepareStatement(
          "DELETE FROM project_files_thumbnail " +
          "WHERE item_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      //Delete all of the versions
      i = 0;
      pst = db.prepareStatement(
          "DELETE FROM project_files_version " +
          "WHERE item_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      //Delete the master record
      i = 0;
      pst = db.prepareStatement(
          "DELETE FROM project_files " +
          "WHERE item_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
      result = true;
    } catch (Exception e) {
      System.err.println("FileItem-> Delete All Error: " + e.toString());
      if (commit) {
        db.rollback();
      }
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return result;
  }


  /**
   *  Gets the valid attribute of the FileItem object
   *
   *@return    The valid value
   */
  private boolean isValid() {
    if (linkModuleId == -1 || linkItemId == -1) {
      errors.put("actionError", "Id not specified");
    }

    if (subject == null || subject.equals("")) {
      errors.put("subjectError", "Required field");
    }

    if (filename == null || filename.equals("")) {
      errors.put("filenameError", "Required field");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@param  isVersion         Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void buildRecord(ResultSet rs, boolean isVersion) throws SQLException {
    id = rs.getInt("item_id");
    if (!isVersion) {
      linkModuleId = rs.getInt("link_module_id");
      linkItemId = rs.getInt("link_item_id");
      folderId = DatabaseUtils.getInt(rs, "folder_id", 0);
    }
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
    if (!isVersion) {
      thumbnailFilename = rs.getString("thumbnail");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void logUpload(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO usage_log " +
        "(enteredby, action, record_id, record_size) VALUES (?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, 1);
    pst.setInt(++i, id);
    pst.setInt(++i, size);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newFolderId       Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void updateFolderId(Connection db, int newFolderId) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_files " +
        "SET folder_id = ? " +
        "WHERE item_id = ?");
    int i = 0;
    if (newFolderId > 0) {
      pst.setInt(++i, newFolderId);
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
  }


  /**
   *  Gets the imageFormat attribute of the FileItem object
   *
   *@return    The imageFormat value
   */
  public boolean isImageFormat() {
    String extension = getExtension();
    return (".gif".equals(extension) ||
        ".jpg".equals(extension) ||
        ".png".equals(extension)
        );
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasThumbnail() {
    return thumbnailFilename != null;
  }
}

