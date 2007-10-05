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
package org.aspcfs.modules.website.base;

import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    February 28, 2006
 * @version    $Id: Exp$
 */
public class PortfolioItem extends GenericBean {
  private int id = -1;
  private String name = null;
  private String description = null;
  private int positionId = -1;
  private int imageId = -1;
  private String caption = null;
  private int categoryId = -1;
  private boolean enabled = false;
  private Timestamp entered = null;
  private Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  //related records
  FileItem image = null;
  boolean buildResources = false;
  boolean positionIdHasChanged = false;


  /**
   *  Constructor for the PortfolioItem object
   */
  public PortfolioItem() { }


  /**
   *  Constructor for the PortfolioItem object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public PortfolioItem(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the PortfolioItem object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public PortfolioItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Portfolio Item ID");
    }
    StringBuffer sb = new StringBuffer(
        " SELECT pi.* " +
        " FROM portfolio_item pi " +
        " WHERE pi.item_id = ? ");
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    if (buildResources) {
      buildResources(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //portfolio_item table
    this.setId(rs.getInt("item_id"));
    name = rs.getString("item_name");
    description = rs.getString("item_description");
    positionId = DatabaseUtils.getInt(rs, "item_position_id");
    imageId = DatabaseUtils.getInt(rs, "image_id");
    caption = rs.getString("caption");
    categoryId = DatabaseUtils.getInt(rs, "portfolio_category_id");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "portfolio_item_item_id_seq");
      sql.append(
          "INSERT INTO portfolio_item (item_name, item_description, " +
          "item_position_id,image_id, caption, portfolio_category_id, enabled, ");
      if (id > -1) {
        sql.append("item_id, ");
      }
      sql.append("entered, ");
      sql.append("enteredby, ");
      sql.append("modified, ");
      sql.append("modifiedby )");
      sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ");
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
      sql.append("? )");
      int i = 0;

      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      DatabaseUtils.setInt(pst, ++i, this.getPositionId());
      DatabaseUtils.setInt(pst, ++i, this.getImageId());
      pst.setString(++i, this.getCaption());
      DatabaseUtils.setInt(pst, ++i, this.getCategoryId());
      pst.setBoolean(++i, this.getEnabled());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setInt(++i, this.getModifiedBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "portfolio_item_item_id_seq", id);
      if (commit) {
        db.commit();
      }
      result = true;
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    boolean commit = false;
    if (this.getId() == -1) {
      return -1;
    }
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // NOTE: For daffodil, needs to happen before the UPDATE quote_entry
      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE portfolio_item " +
          "SET item_name = ?, " +
          "item_description = ?, " +
          "item_position_id = ?, " +
          "image_id = ?, " +
          "caption = ?, " +
          "portfolio_category_id = ?, " +
          "enabled = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
          "modifiedby = ? " +
          "WHERE item_id = ? ");
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      DatabaseUtils.setInt(pst, ++i, this.getPositionId());
      DatabaseUtils.setInt(pst, ++i, this.getImageId());
      pst.setString(++i, this.getCaption());
      DatabaseUtils.setInt(pst, ++i, this.getCategoryId());
      pst.setBoolean(++i, this.getEnabled());
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      resultCount = pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Portfolio Item ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    // Check for this portfolio_item's dependencies
    //images related to the item
    Dependency docDependency = new Dependency();
    docDependency.setName("documents");
    docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_PORTFOLIO_ITEM, this.getId()));
    docDependency.setCanDelete(true);
    dependencyList.add(docDependency);

    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  baseFilePath      Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Portfolio Item ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      PreparedStatement pst = null;
      ResultSet rs = null;

      int nextPositionId = this.getNextPositionId(db);
      if (nextPositionId > -1) {
        PortfolioItem nextPosition = new PortfolioItem(db, nextPositionId);
        nextPosition.setPositionId(this.getPositionId());
        nextPosition.update(db);
      }

      // delete the item
      pst = db.prepareStatement("DELETE FROM portfolio_item WHERE item_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //Delete related files
      FileItemList fileList = new FileItemList();
      fileList.setLinkModuleId(Constants.DOCUMENTS_PORTFOLIO_ITEM);
      fileList.setLinkItemId(this.getId());
      fileList.buildList(db);
      fileList.delete(db, getFileLibraryPath(baseFilePath, "portfolioitem"));
      fileList = null;

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    if (imageId > -1) {
      image = new FileItem(db, imageId, this.getId(), Constants.DOCUMENTS_PORTFOLIO_ITEM);
    }
  }


  /**
   *  Gets the nextPositionId attribute of the PortfolioItem object
   *
   * @param  db                Description of the Parameter
   * @return                   The nextPositionId value
   * @exception  SQLException  Description of the Exception
   */
  public int getNextPositionId(Connection db) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT item_id FROM portfolio_item WHERE item_position_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = DatabaseUtils.getInt(rs, "item_id");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   *  Gets the timeZoneParams attribute of the PortfolioItem class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("entered");
    thisList.add("modified");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    return this.getName();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updateCategory(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Portfolio Item ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      int nextPositionId = this.getNextPositionId(db);
      if (nextPositionId > -1) {
        PortfolioItem nextPosition = new PortfolioItem(db, nextPositionId);
        nextPosition.setPositionId(this.getPositionId());
        nextPosition.update(db);
      }
      PreparedStatement pst = db.prepareStatement(
          "UPDATE portfolio_item set portfolio_category_id = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
          "where item_id = ? ");
      DatabaseUtils.setInt(pst, 1, this.getCategoryId());
      pst.setInt(2, this.getId());
      pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the PortfolioItem object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the PortfolioItem object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PortfolioItem object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the name attribute of the PortfolioItem object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the PortfolioItem object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the description attribute of the PortfolioItem object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the PortfolioItem object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the positionId attribute of the PortfolioItem object
   *
   * @return    The positionId value
   */
  public int getPositionId() {
    return positionId;
  }


  /**
   *  Sets the positionId attribute of the PortfolioItem object
   *
   * @param  tmp  The new positionId value
   */
  public void setPositionId(int tmp) {
    if (tmp != this.positionId) {
      this.setPositionIdHasChanged(true);
    }
    this.positionId = tmp;
  }


  /**
   *  Sets the positionId attribute of the PortfolioItem object
   *
   * @param  tmp  The new positionId value
   */
  public void setPositionId(String tmp) {
    if (this.positionId != Integer.parseInt(tmp)) {
      this.setPositionIdHasChanged(true);
    }
    this.positionId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the imageId attribute of the PortfolioItem object
   *
   * @return    The imageId value
   */
  public int getImageId() {
    return imageId;
  }


  /**
   *  Sets the imageId attribute of the PortfolioItem object
   *
   * @param  tmp  The new imageId value
   */
  public void setImageId(int tmp) {
    this.imageId = tmp;
  }


  /**
   *  Sets the imageId attribute of the PortfolioItem object
   *
   * @param  tmp  The new imageId value
   */
  public void setImageId(String tmp) {
    this.imageId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the caption attribute of the PortfolioItem object
   *
   * @return    The caption value
   */
  public String getCaption() {
    return caption;
  }


  /**
   *  Sets the caption attribute of the PortfolioItem object
   *
   * @param  tmp  The new caption value
   */
  public void setCaption(String tmp) {
    this.caption = tmp;
  }


  /**
   *  Gets the categoryId attribute of the PortfolioItem object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the PortfolioItem object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the PortfolioItem object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabled attribute of the PortfolioItem object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the PortfolioItem object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the PortfolioItem object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the entered attribute of the PortfolioItem object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the PortfolioItem object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the PortfolioItem object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modified attribute of the PortfolioItem object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the PortfolioItem object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the PortfolioItem object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the PortfolioItem object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the PortfolioItem object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the PortfolioItem object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the PortfolioItem object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the PortfolioItem object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the PortfolioItem object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the image attribute of the PortfolioItem object
   *
   * @return    The image value
   */
  public FileItem getImage() {
    return image;
  }


  /**
   *  Sets the image attribute of the PortfolioItem object
   *
   * @param  tmp  The new image value
   */
  public void setImage(FileItem tmp) {
    this.image = tmp;
  }


  /**
   *  Gets the buildResources attribute of the PortfolioItem object
   *
   * @return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Sets the buildResources attribute of the PortfolioItem object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the PortfolioItem object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the positionIdHasChanged attribute of the PortfolioItem object
   *
   * @return    The positionIdHasChanged value
   */
  public boolean getPositionIdHasChanged() {
    return positionIdHasChanged;
  }


  /**
   *  Sets the positionIdHasChanged attribute of the PortfolioItem object
   *
   * @param  tmp  The new positionIdHasChanged value
   */
  public void setPositionIdHasChanged(boolean tmp) {
    this.positionIdHasChanged = tmp;
  }


  /**
   *  Sets the positionIdHasChanged attribute of the PortfolioItem object
   *
   * @param  tmp  The new positionIdHasChanged value
   */
  public void setPositionIdHasChanged(String tmp) {
    this.positionIdHasChanged = DatabaseUtils.parseBoolean(tmp);
  }

}

