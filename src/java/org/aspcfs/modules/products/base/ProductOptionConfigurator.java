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
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.configurator.OptionConfigurator;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created September 2, 2004
 */
public class ProductOptionConfigurator extends GenericBean {
  private int id = -1;
  private String configuratorName = null;
  private String shortDescription = null;
  private String longDescription = null;
  private String className = null;
  private int resultType = -1;


  /**
   * Sets the configuratorName attribute of the ProductOptionConfigurator
   * object
   *
   * @param tmp The new configuratorName value
   */
  public void setConfiguratorName(String tmp) {
    this.configuratorName = tmp;
  }


  /**
   * Gets the configuratorName attribute of the ProductOptionConfigurator
   * object
   *
   * @return The configuratorName value
   */
  public String getConfiguratorName() {
    return configuratorName;
  }


  /**
   * Sets the id attribute of the ProductOptionConfigurator object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ProductOptionConfigurator object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the shortDescription attribute of the ProductOptionConfigurator
   * object
   *
   * @param tmp The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   * Sets the longDescription attribute of the ProductOptionConfigurator object
   *
   * @param tmp The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }


  /**
   * Sets the className attribute of the ProductOptionConfigurator object
   *
   * @param tmp The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
  }


  /**
   * Sets the resultType attribute of the ProductOptionConfigurator object
   *
   * @param tmp The new resultType value
   */
  public void setResultType(int tmp) {
    this.resultType = tmp;
  }


  /**
   * Sets the resultType attribute of the ProductOptionConfigurator object
   *
   * @param tmp The new resultType value
   */
  public void setResultType(String tmp) {
    this.resultType = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the ProductOptionConfigurator object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the shortDescription attribute of the ProductOptionConfigurator
   * object
   *
   * @return The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   * Gets the longDescription attribute of the ProductOptionConfigurator object
   *
   * @return The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }


  /**
   * Gets the className attribute of the ProductOptionConfigurator object
   *
   * @return The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   * Gets the resultType attribute of the ProductOptionConfigurator object
   *
   * @return The resultType value
   */
  public int getResultType() {
    return resultType;
  }


  /**
   * Constructor for the ProductOptionConfigurator object
   */
  public ProductOptionConfigurator() {
  }


  /**
   * Constructor for the ProductOptionConfigurator object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductOptionConfigurator(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the ProductOptionConfigurator object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductOptionConfigurator(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Product Option Configurator Id");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT conf.* " +
        "FROM product_option_configurator conf " +
        "WHERE conf.configurator_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Product Option Configurator not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // product_option_configurator table
    this.setId(rs.getInt("configurator_id"));
    this.setShortDescription(rs.getString("short_description"));
    this.setLongDescription(rs.getString("long_description"));
    this.setClassName(rs.getString("class_name"));
    this.setResultType(DatabaseUtils.getInt(rs, "result_type"));
    this.setConfiguratorName(rs.getString("configurator_name"));
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified.");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      /*
       *  /Delete any documents
       *  FileItemList fileList = new FileItemList();
       *  fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATEGORY);
       *  fileList.setLinkItemId(this.getId());
       *  fileList.buildList(db);
       *  fileList.delete(db, baseFilePath);
       *  fileList = null;
       *  /Delete any folder data
       *  CustomFieldRecordList folderList = new CustomFieldRecordList();
       *  folderList.setLinkModuleId(Constants.FOLDERS_PRODUCT_CATEGORY);
       *  folderList.setLinkItemId(this.getId());
       *  folderList.buildList(db);
       *  folderList.delete(db);
       *  folderList = null;
       */
      //delete all the dependencies that contain the product_option_configurator id

      //delete the product_option_configurator s that have configurator_id = id
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "DELETE from product_option_configurator " +
          "WHERE configurator_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    StringBuffer sql = new StringBuffer();
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "product_option_configurator_configurator_id_seq");
      sql.append(
          "INSERT INTO product_option_configurator(" +
          (id > -1 ? "configurator_id, " : "") + "configurator_name, short_description, long_description, class_name, result_type ) ");
      sql.append("VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setString(++i, this.getConfiguratorName());
      pst.setString(++i, this.getShortDescription());
      pst.setString(++i, this.getLongDescription());
      pst.setString(++i, this.getClassName());
      DatabaseUtils.setInt(pst, ++i, this.getResultType());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(
          db, "product_option_configurator_configurator_id_seq", id);
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
        "UPDATE product_option_configurator " +
        "SET configurator_name = ?, short_description = ?, long_description = ?, class_name = ?, " +
        "result_type = ? " +
        "WHERE configurator_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getConfiguratorName());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    pst.setString(++i, this.getClassName());
    pst.setInt(++i, this.getResultType());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    // This method checks all the mappings for any product_option_configurator with the current id
    // The currently known mappings are product_option_configurator_map, product_catalog_category_map
    if (this.getId() == -1) {
      throw new SQLException("Configurator ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    /*
     *  /Check for documents
     *  Dependency docDependency = new Dependency();
     *  docDependency.setName("documents");
     *  docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_PRODUCT_CATEGORY, this.getId()));
     *  docDependency.setCanDelete(true);
     *  dependencyList.add(docDependency);
     *  /Check for folders
     *  Dependency folderDependency = new Dependency();
     *  folderDependency.setName("folders");
     *  folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.FOLDERS_PRODUCT_CATEGORY, this.getId()));
     *  folderDependency.setCanDelete(true);
     *  dependencyList.add(folderDependency);
     */
    //Check for product_option with configurator_id = id
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) AS parentcount " +
          "FROM product_option " +
          "WHERE configurator_id = ?");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int categoryCount = rs.getInt("parentcount");
        if (categoryCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("numberOfChildrenOfThisCategory");
          thisDependency.setCount(categoryCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    	throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static OptionConfigurator getConfigurator(Connection db, int id) throws SQLException {
    OptionConfigurator configurator = null;
    try {
      String className = null;
      configurator = null;
      PreparedStatement pst = db.prepareStatement(
          "SELECT class_name " +
          "FROM product_option_configurator " +
          "WHERE configurator_id = ? ");
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        className = rs.getString("class_name");
      }
      rs.close();
      pst.close();
      // Create an instance of the configurator
      Class cls = Class.forName(className);
      configurator = (OptionConfigurator) cls.newInstance();
    } catch (InstantiationException ie) {
      // Shouldn't happen
      // TODO: implement reporting these exceptions to the application
      ie.printStackTrace(System.out);
    } catch (ClassNotFoundException cnfe) {
      cnfe.printStackTrace(System.out);
    } catch (IllegalAccessException iae) {
      iae.printStackTrace(System.out);
    }
    return configurator;
  }
}

