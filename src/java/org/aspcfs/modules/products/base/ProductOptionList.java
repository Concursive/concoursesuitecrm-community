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
package org.aspcfs.modules.products.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    March 19, 2004
 *@version    $Id: ProductOptionList.java,v 1.1.2.2 2004/03/19 20:46:00 partha
 *      Exp $
 */
public class ProductOptionList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int parentId = -1;
  private String name = null;
  private int configuratorId = -1;
  private int enabled = Constants.UNDEFINED;
  //other supplimentary fields
  private int productId = -1;
  private String productName = null;
  //resources
  private boolean buildResources = false;
  private boolean buildConfigDetails = false;
  

  /**
   *  Gets the buildConfigDetails attribute of the ProductOptionList object
   *
   *@return    The buildConfigDetails value
   */
  public boolean getBuildConfigDetails() {
    return buildConfigDetails;
  }


  /**
   *  Sets the buildConfigDetails attribute of the ProductOptionList object
   *
   *@param  tmp  The new buildConfigDetails value
   */
  public void setBuildConfigDetails(boolean tmp) {
    this.buildConfigDetails = tmp;
  }


  /**
   *  Sets the buildConfigDetails attribute of the ProductOptionList object
   *
   *@param  tmp  The new buildConfigDetails value
   */
  public void setBuildConfigDetails(String tmp) {
    this.buildConfigDetails = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the ProductOptionList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the ProductOptionList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ProductOptionList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the ProductOptionList object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ProductOptionList object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the ProductOptionList object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the configuratorId attribute of the ProductOptionList object
   *
   *@param  tmp  The new configuratorId value
   */
  public void setConfiguratorId(int tmp) {
    this.configuratorId = tmp;
  }


  /**
   *  Sets the configuratorId attribute of the ProductOptionList object
   *
   *@param  tmp  The new configuratorId value
   */
  public void setConfiguratorId(String tmp) {
    this.configuratorId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the ProductOptionList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductOptionList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productId attribute of the ProductOptionList object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the ProductOptionList object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productName attribute of the ProductOptionList object
   *
   *@param  tmp  The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   *  Sets the buildResources attribute of the ProductOptionList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the ProductOptionList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the ProductOptionList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the ProductOptionList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the ProductOptionList object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the name attribute of the ProductOptionList object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the configuratorId attribute of the ProductOptionList object
   *
   *@return    The configuratorId value
   */
  public int getConfiguratorId() {
    return configuratorId;
  }


  /**
   *  Gets the enabled attribute of the ProductOptionList object
   *
   *@return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the productId attribute of the ProductOptionList object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Gets the productName attribute of the ProductOptionList object
   *
   *@return    The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   *  Gets the buildResources attribute of the ProductOptionList object
   *
   *@return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Constructor for the ProductOptionList object
   */
  public ProductOptionList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM product_option AS popt " +
        "LEFT JOIN product_option_configurator AS poptconf ON ( popt.configurator_id = poptconf.configurator_id ) " +
        "LEFT JOIN product_option AS popt2 ON ( popt.parent_id = popt2.option_id ) " +
        "WHERE popt.option_id > 0 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      // Determine column to sort by
      pagedListInfo.setDefaultSort("popt.option_id", null);
      boolean flag = true;
      if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL) {
        if (pagedListInfo.getColumnToSortBy().equals("popt.short_description")) {
          sqlOrder.append("ORDER BY CONVERT(VARCHAR(2000),popt.short_description) ");
          flag = false;
        }
      }
      if (flag) {
        pagedListInfo.appendSqlTail(db, sqlOrder);
      }
    } else {
      sqlOrder.append("ORDER BY popt.option_id ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        "popt.*, " +
        "poptconf.result_type as result_type, " +
        "poptconf.configurator_name as conf_name, " +
        "popt2.option_name as parent_name " +
        "FROM product_option AS popt " +
        "LEFT JOIN product_option_configurator AS poptconf ON ( popt.configurator_id = poptconf.configurator_id ) " +
        "LEFT JOIN product_option AS popt2 ON ( popt.parent_id = popt2.option_id ) " +
        "WHERE popt.option_id > 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      ProductOption productOption = new ProductOption(rs);
      this.add(productOption);
    }
    rs.close();
    pst.close();
    // Each option's value list is generated
    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ProductOption thisOption = (ProductOption) i.next();
        thisOption.buildOptionValues(db);
      }
    }
    if (buildConfigDetails) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ProductOption thisOption = (ProductOption) i.next();
        thisOption.buildConfigDetails(db);
      }
    }
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator options = this.iterator();
    while (options.hasNext()) {
      ProductOption productOption = (ProductOption) options.next();
      productOption.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   *@param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (id > -1) {
      sqlFilter.append("AND popt.option_id = ? ");
    }
    if (name != null) {
      sqlFilter.append("AND popt.option_name = ? ");
    }
    if (parentId > -1) {
      sqlFilter.append("AND popt.parent_id = ? ");
    }
    if (productName != null) {
      sqlFilter.append("AND pctlg.product_name = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND popt.enabled = ? ");
    }
    if (productId > -1) {
      sqlFilter.append("AND popt.option_id IN (SELECT option_id FROM product_option_map WHERE product_id = ?) ");
    }
    if (configuratorId > -1) {
      sqlFilter.append("AND popt.configurator_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }

    if (name != null) {
      pst.setString(++i, name);
    }

    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }

    if (productName != null) {
      pst.setString(++i, productName);
    }

    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }

    if (productId > -1) {
      pst.setInt(++i, productId);
    }

    if (configuratorId > -1) {
      pst.setInt(++i, configuratorId);
    }
    return i;
  }


  /**
   *  Adds a feature to the ProductMapping attribute of the ProductOptionList
   *  object
   *
   *@param  db                The feature to be added to the ProductMapping
   *      attribute
   *@param  productId         The feature to be added to the ProductMapping
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  public void addProductMapping(Connection db, int productId) throws SQLException {
    if (productId == -1) {
      throw new SQLException("Invalid Product ID specified");
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProductOption thisOption = (ProductOption) i.next();
      thisOption.addProductMapping(db, productId);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  productId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void removeProductMapping(Connection db, int productId) throws SQLException {
    if (productId == -1) {
      throw new SQLException("Invalid Product ID specified");
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProductOption thisOption = (ProductOption) i.next();
      thisOption.removeProductMapping(db, productId);
    }
  }


  /**
   *  Adds a feature to the ProductMapping attribute of the ProductOptionList
   *  object
   *
   *@param  db                The feature to be added to the ProductMapping
   *      attribute
   *@param  oldList           The feature to be added to the ProductMapping
   *      attribute
   *@param  productId         The feature to be added to the ProductMapping
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  public void addProductMapping(Connection db, ProductOptionList oldList, int productId) throws SQLException {
    try {
      db.setAutoCommit(false);
      // Remove the mappings of elements present in the oldList and not this list
      Iterator i = oldList.iterator();
      while (i.hasNext()) {
        ProductOption oldOption = (ProductOption) i.next();
        boolean exists = false;
        Iterator j = this.iterator();
        while (j.hasNext()) {
          ProductOption thisOption = (ProductOption) j.next();
          if (oldOption.getId() == thisOption.getId()) {
            exists = true;
            j.remove();
            break;
          }
        }
        if (!exists) {
          // old option does not exist. hence remove the values and mappings
          //oldOption.removeProductValues(db, productId);
          oldOption.removeProductMapping(db, productId);
        }
      }

      // Add mappings for the elements now present in this list
      this.addProductMapping(db, productId);
      db.commit();
    } catch (Exception e) {
      e.printStackTrace(System.out);
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Gets the optionFromId attribute of the ProductOptionList object
   *
   *@param  id                Description of the Parameter
   *@return                   The optionFromId value
   *@exception  SQLException  Description of the Exception
   */
  public ProductOption getOptionFromId(int id) throws SQLException {
    ProductOption result = null;
    if (this.size() > 0) {
      Iterator iterator = (Iterator) this.iterator();
      while (iterator.hasNext()) {
        ProductOption option = (ProductOption) iterator.next();
        if (option.getId() == id) {
          result = option;
          break;
        }
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasValues() {
    int counter = 0;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ProductOption option = (ProductOption) iterator.next();
      counter += option.getOptionValuesList().size();
    }
    if (counter == 0) {
      return false;
    }
    return true;
  }
}

