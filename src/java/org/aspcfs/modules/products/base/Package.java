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
package org.aspcfs.modules.products.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created April 12, 2004
 */
public class Package extends GenericBean {
  private int id = -1;
  private int categoryId = -1;
  private String name = null;
  private String abbreviation = null;
  private String shortDescription = null;
  private String longDescription = null;
  private int thumbnailImageId = -1;
  private int smallImageId = -1;
  private int largeImageId = -1;
  private int listOrder = -1;
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private Timestamp startDate = null;
  private Timestamp expirationDate = null;
  private boolean enabled = false;


  /**
   * Sets the categoryId attribute of the Package object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the Package object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the categoryId attribute of the Package object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the id attribute of the Package object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Package object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the name attribute of the Package object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the abbreviation attribute of the Package object
   *
   * @param tmp The new abbreviation value
   */
  public void setAbbreviation(String tmp) {
    this.abbreviation = tmp;
  }


  /**
   * Sets the shortDescription attribute of the Package object
   *
   * @param tmp The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   * Sets the longDescription attribute of the Package object
   *
   * @param tmp The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }


  /**
   * Sets the thumbnailImageId attribute of the Package object
   *
   * @param tmp The new thumbnailImageId value
   */
  public void setThumbnailImageId(int tmp) {
    this.thumbnailImageId = tmp;
  }


  /**
   * Sets the thumbnailImageId attribute of the Package object
   *
   * @param tmp The new thumbnailImageId value
   */
  public void setThumbnailImageId(String tmp) {
    this.thumbnailImageId = Integer.parseInt(tmp);
  }


  /**
   * Sets the smallImageId attribute of the Package object
   *
   * @param tmp The new smallImageId value
   */
  public void setSmallImageId(int tmp) {
    this.smallImageId = tmp;
  }


  /**
   * Sets the smallImageId attribute of the Package object
   *
   * @param tmp The new smallImageId value
   */
  public void setSmallImageId(String tmp) {
    this.smallImageId = Integer.parseInt(tmp);
  }


  /**
   * Sets the largeImageId attribute of the Package object
   *
   * @param tmp The new largeImageId value
   */
  public void setLargeImageId(int tmp) {
    this.largeImageId = tmp;
  }


  /**
   * Sets the largeImageId attribute of the Package object
   *
   * @param tmp The new largeImageId value
   */
  public void setLargeImageId(String tmp) {
    this.largeImageId = Integer.parseInt(tmp);
  }


  /**
   * Sets the listOrder attribute of the Package object
   *
   * @param tmp The new listOrder value
   */
  public void setListOrder(int tmp) {
    this.listOrder = tmp;
  }


  /**
   * Sets the listOrder attribute of the Package object
   *
   * @param tmp The new listOrder value
   */
  public void setListOrder(String tmp) {
    this.listOrder = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the Package object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the Package object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the Package object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Package object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Package object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the Package object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Package object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Package object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the startDate attribute of the Package object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   * Sets the startDate attribute of the Package object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the expirationDate attribute of the Package object
   *
   * @param tmp The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   * Sets the expirationDate attribute of the Package object
   *
   * @param tmp The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enabled attribute of the Package object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Package object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the Package object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the name attribute of the Package object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the abbreviation attribute of the Package object
   *
   * @return The abbreviation value
   */
  public String getAbbreviation() {
    return abbreviation;
  }


  /**
   * Gets the shortDescription attribute of the Package object
   *
   * @return The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   * Gets the longDescription attribute of the Package object
   *
   * @return The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }


  /**
   * Gets the thumbnailImageId attribute of the Package object
   *
   * @return The thumbnailImageId value
   */
  public int getThumbnailImageId() {
    return thumbnailImageId;
  }


  /**
   * Gets the smallImageId attribute of the Package object
   *
   * @return The smallImageId value
   */
  public int getSmallImageId() {
    return smallImageId;
  }


  /**
   * Gets the largeImageId attribute of the Package object
   *
   * @return The largeImageId value
   */
  public int getLargeImageId() {
    return largeImageId;
  }


  /**
   * Gets the listOrder attribute of the Package object
   *
   * @return The listOrder value
   */
  public int getListOrder() {
    return listOrder;
  }


  /**
   * Gets the entered attribute of the Package object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the Package object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the Package object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the Package object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the startDate attribute of the Package object
   *
   * @return The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   * Gets the expirationDate attribute of the Package object
   *
   * @return The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   * Gets the enabled attribute of the Package object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Constructor for the Package object
   */
  public Package() {
  }


  /**
   * Constructor for the Package object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Package(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the Package object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Package(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Package ID specified");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT " +
        "   package_id, category_id, package_name, abbreviation, short_description, long_description " +
        "   thumbnail_image_id, small_image_id, large_image_id, list_order, " +
        "   entered, enteredby, modified, modifiedby, start_date, expiration_date, enabled " +
        " FROM package " +
        " WHERE package_id = ? " +
        " ORDER BY package_name ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Package not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // order_entry table
    this.setId(rs.getInt("package_id"));
    categoryId = DatabaseUtils.getInt(rs, "category_id");
    name = rs.getString("package_name");
    abbreviation = rs.getString("abbreviation");
    shortDescription = rs.getString("short_description");
    longDescription = rs.getString("long_description");
    thumbnailImageId = DatabaseUtils.getInt(rs, "thumbnail_image_id");
    smallImageId = DatabaseUtils.getInt(rs, "small_image_id");
    largeImageId = DatabaseUtils.getInt(rs, "large_image_id");
    listOrder = DatabaseUtils.getInt(rs, "list_order");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    startDate = rs.getTimestamp("start_date");
    expirationDate = rs.getTimestamp("expiration_date");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Package ID not specified.");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM package " +
        "WHERE package_id = ? ");
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      id = DatabaseUtils.getNextSeq(db, "package_package_id_seq");
      sql.append(
          "INSERT INTO package (" + (id > -1 ? "package_id, " : "") +
          "   category_id, package_name, abbreviation, short_description, " +
          "   long_description, thumbnail_image_id, " +
          "   small_image_id, large_image_id, list_order, " +
          "   enteredby, ");
      sql.append("entered, ");
      sql.append("modifiedBy, ");
      sql.append("modified, ");
      sql.append("start_date, expiration_date, enabled) ");
      sql.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ");
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      DatabaseUtils.setInt(pst, ++i, this.getCategoryId());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getAbbreviation());
      pst.setString(++i, this.getShortDescription());
      pst.setString(++i, this.getLongDescription());
      DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
      DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
      DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
      DatabaseUtils.setInt(pst, ++i, this.getListOrder());
      pst.setInt(++i, this.getEnteredBy());
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setInt(++i, this.getModifiedBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
      pst.setBoolean(++i, this.getEnabled());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "package_package_id_seq", id);
      db.commit();
      result = true;
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE package " +
        "SET category_id = ?, " +
        "     abbreviation = ?, " +
        "     short_description = ?, " +
        "     long_description = ?, " +
        "     thumbnail_image_id = ?, " +
        "     small_image_id = ?, " +
        "     large_image_id = ?, " +
        "     list_order = ?, " +
        "     modifiedBy = ? , " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     start_date = ?, " +
        "     expiration_date = ?, " +
        "     enabled = ? ");
    sql.append("WHERE package_id = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getCategoryId());
    pst.setString(++i, this.getAbbreviation());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
    DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
    DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
    DatabaseUtils.setInt(pst, ++i, this.getListOrder());
    pst.setInt(++i, this.getModifiedBy());
    pst.setTimestamp(++i, this.getStartDate());
    pst.setTimestamp(++i, this.getExpirationDate());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getId());
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

}

