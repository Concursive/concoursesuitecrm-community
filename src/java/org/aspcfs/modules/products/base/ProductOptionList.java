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

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import java.util.Calendar;

/**
 *  List class for a Product Option
 *
 *@author     partha
 *@created    March 19, 2004
 *@version    $Id: ProductOptionList.java,v 1.1.2.2 2004/03/19 20:46:00 partha
 *      Exp $
 */
public class ProductOptionList extends ArrayList implements SyncableList {
  //sync api
  /**
   *  Description of the Field
   */
  public final static String tableName = "product_option";
  /**
   *  Description of the Field
   */
  public final static String uniqueField = "option_id";
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private int id = -1;
  private int parentId = -1;
  private int allowCustomerConfigure = Constants.UNDEFINED;
  private int allowUserConfigure = Constants.UNDEFINED;
  private int enabled = Constants.UNDEFINED;
  private int topOnly = Constants.UNDEFINED;
  //other supplimentary fields
  private int productId = -1;
  private String productName = null;
  private int resultType = -1;
  private String parentName = null;
  //resources
  private boolean buildResources = false;


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
   *  Gets the buildResources attribute of the ProductOptionList object
   *
   *@return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
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
   *  Gets the productId attribute of the ProductOptionList object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Gets the tableName attribute of the ProductOptionList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the ProductOptionList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the ProductOptionList object
   *
   *@return    The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the ProductOptionList object
   *
   *@return    The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the ProductOptionList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
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
   *  Gets the enteredBy attribute of the ProductOptionList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
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
   *  Gets the allowCustomerConfigure attribute of the ProductOptionList object
   *
   *@return    The allowCustomerConfigure value
   */
  public int getAllowCustomerConfigure() {
    return allowCustomerConfigure;
  }


  /**
   *  Gets the allowUserConfigure attribute of the ProductOptionList object
   *
   *@return    The allowUserConfigure value
   */
  public int getAllowUserConfigure() {
    return allowUserConfigure;
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
   *  Gets the productName attribute of the ProductOptionList object
   *
   *@return    The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   *  Gets the resultType attribute of the ProductOptionList object
   *
   *@return    The resultType value
   */
  public int getResultType() {
    return resultType;
  }


  /**
   *  Gets the parentName attribute of the ProductOptionList object
   *
   *@return    The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   *  Gets the topOnly attribute of the ProductOptionList object
   *
   *@return    The topOnly value
   */
  public int getTopOnly() {
    return topOnly;
  }


  /**
   *  Sets the lastAnchor attribute of the ProductOptionList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the ProductOptionList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the ProductOptionList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the ProductOptionList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the syncType attribute of the ProductOptionList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the ProductOptionList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
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
   *  Sets the enteredBy attribute of the ProductOptionList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductOptionList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
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
   *  Sets the allowCustomerConfigure attribute of the ProductOptionList object
   *
   *@param  tmp  The new allowCustomerConfigure value
   */
  public void setAllowCustomerConfigure(int tmp) {
    this.allowCustomerConfigure = tmp;
  }


  /**
   *  Sets the allowCustomerConfigure attribute of the ProductOptionList object
   *
   *@param  tmp  The new allowCustomerConfigure value
   */
  public void setAllowCustomerConfigure(String tmp) {
    this.allowCustomerConfigure = Integer.parseInt(tmp);
  }


  /**
   *  Sets the allowUserConfigure attribute of the ProductOptionList object
   *
   *@param  tmp  The new allowUserConfigure value
   */
  public void setAllowUserConfigure(int tmp) {
    this.allowUserConfigure = tmp;
  }


  /**
   *  Sets the allowUserConfigure attribute of the ProductOptionList object
   *
   *@param  tmp  The new allowUserConfigure value
   */
  public void setAllowUserConfigure(String tmp) {
    this.allowUserConfigure = Integer.parseInt(tmp);
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
   *  Sets the productName attribute of the ProductOptionList object
   *
   *@param  tmp  The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   *  Sets the resultType attribute of the ProductOptionList object
   *
   *@param  tmp  The new resultType value
   */
  public void setResultType(int tmp) {
    this.resultType = tmp;
  }


  /**
   *  Sets the resultType attribute of the ProductOptionList object
   *
   *@param  tmp  The new resultType value
   */
  public void setResultType(String tmp) {
    this.resultType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentName attribute of the ProductOptionList object
   *
   *@param  tmp  The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   *  Sets the topOnly attribute of the ProductOptionList object
   *
   *@param  tmp  The new topOnly value
   */
  public void setTopOnly(int tmp) {
    this.topOnly = tmp;
  }


  /**
   *  Sets the topOnly attribute of the ProductOptionList object
   *
   *@param  tmp  The new topOnly value
   */
  public void setTopOnly(String tmp) {
    this.topOnly = Integer.parseInt(tmp);
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
        " SELECT COUNT(popt.*) AS recordcount " +
        " FROM product_option AS popt " +
        " WHERE popt.option_id > 0"
        );

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
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        "   popt.*, " +
        "   poptconf.result_type as result_type, " +
        "   popt2.short_description as parent_name " +
        " FROM product_option AS popt " +
        " LEFT JOIN product_option_configurator as poptconf ON ( popt.configurator_id = poptconf.configurator_id ) " +
        " LEFT JOIN product_option as popt2 ON ( popt.parent_id = popt2.option_id ) " +
        " WHERE popt.option_id > -1 ");
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
        thisOption.setProductId(this.productId);
        thisOption.buildOptionValues(db);
      }
    }
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  basePath          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db, String basePath) throws SQLException {
    Iterator options = this.iterator();
    while (options.hasNext()) {
      ProductOption productOption = (ProductOption) options.next();
      productOption.delete(db, basePath);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   *@param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {

    if (enteredBy > -1) {
      sqlFilter.append(" AND popt.enteredby = ? ");
    }

    if (id > -1) {
      sqlFilter.append(" AND popt.option_id = ? ");
    }
    if (topOnly == Constants.TRUE) {
      sqlFilter.append(" AND popt.parent_id IS NULL ");
    } else if (topOnly == Constants.FALSE) {
      sqlFilter.append(" AND popt.parent_id IS NOT NULL ");
    }
    if (parentId > -1) {
      sqlFilter.append(" AND popt.parent_id = ? ");
    }
    if (allowCustomerConfigure != Constants.UNDEFINED) {
      sqlFilter.append(" AND popt.allow_customer_configure = ? ");
    }
    if (allowUserConfigure != Constants.UNDEFINED) {
      sqlFilter.append(" AND popt.allow_user_configure = ? ");
    }
    if (productName != null) {
      sqlFilter.append(" AND pctlg.product_name = ? ");
    }

    if (resultType > -1) {
      sqlFilter.append(" AND poptconf.result_type = ? ");
    }

    if (parentName != null) {
      sqlFilter.append(" AND popt2.short_description = ? ");
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND popt.entered >= ? ");
      }
      sqlFilter.append("AND popt.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND popt.modified >= ? ");
      sqlFilter.append("AND popt.entered < ? ");
      sqlFilter.append("AND popt.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND popt.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND popt.entered < ? ");
      }
    }

    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND popt.enabled = ? ");
    }
    if (productId > -1) {
      sqlFilter.append("AND popt.option_id IN (SELECT option_id FROM product_option_map WHERE product_id = ?) ");
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
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }
    if (allowCustomerConfigure == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (allowCustomerConfigure == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (allowUserConfigure == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (allowUserConfigure == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (productName != null) {
      pst.setString(++i, productName);
    }

    if (resultType > -1) {
      pst.setInt(++i, resultType);
    }

    if (parentName != null) {
      pst.setString(++i, parentName);
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      if (nextAnchor != null) {
        pst.setTimestamp(++i, nextAnchor);
      }
    }
    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (productId > -1) {
      pst.setInt(++i, productId);
    }
    return i;
  }
}

