/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.sql.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.actions.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 7, 2003
 *@version    $Id$
 */
public class Thumbnail extends GenericBean {

  public final static String fs = System.getProperty("file.separator");

  private int id = -1;
  private String filename = null;
  private int size = 0;
  private double version = 0;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;


  /**
   *  Constructor for the Thumbnail object
   */
  public Thumbnail() { }


  /**
   *  Sets the id attribute of the Thumbnail object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Thumbnail object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the filename attribute of the Thumbnail object
   *
   *@param  tmp  The new filename value
   */
  public void setFilename(String tmp) {
    this.filename = tmp;
  }


  /**
   *  Sets the size attribute of the Thumbnail object
   *
   *@param  tmp  The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the size attribute of the Thumbnail object
   *
   *@param  tmp  The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   *  Sets the version attribute of the Thumbnail object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(double tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the entered attribute of the Thumbnail object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Thumbnail object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Thumbnail object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Thumbnail object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Thumbnail object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Thumbnail object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Thumbnail object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Thumbnail object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the Thumbnail object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the filename attribute of the Thumbnail object
   *
   *@return    The filename value
   */
  public String getFilename() {
    return filename;
  }


  /**
   *  Gets the size attribute of the Thumbnail object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the version attribute of the Thumbnail object
   *
   *@return    The version value
   */
  public double getVersion() {
    return version;
  }


  /**
   *  Gets the entered attribute of the Thumbnail object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Thumbnail object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Thumbnail object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Thumbnail object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }



  /**
   *  Gets the relativeSize attribute of the Thumbnail object
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO project_files_thumbnail " +
        "(item_id, filename, version, size, " +
        (entered != null ? "entered, " : "") +
        (modified != null ? "modified, " : "") +
        "enteredBy, modifiedBy) " +
        "VALUES (?, ?, ?, ?, " +
        (entered != null ? "?, " : "") +
        (modified != null ? "?, " : "") +
        "?, ?) ");
    int i = 0;
    pst.setInt(++i, id);
    pst.setString(++i, filename);
    pst.setDouble(++i, version);
    pst.setInt(++i, size);
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
   *@param  db                Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    //Need to delete the actual file
    String filePath = baseFilePath + FileItem.getDatePath(this.getEntered()) + this.getFilename();
    java.io.File fileToDelete = new java.io.File(filePath);
    if (!fileToDelete.delete()) {
      System.err.println("Thumbnail-> Tried to delete file: " + filePath);
    }
    //Delete database record
    String sql =
        "DELETE FROM project_files_thumbnail " +
        "WHERE item_id = ? " +
        "AND version = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, this.getId());
    pst.setDouble(2, this.getVersion());
    pst.execute();
    pst.close();
    return true;
  }

}

