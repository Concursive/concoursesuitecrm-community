package org.aspcfs.modules.products.base;

import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    April 20, 2004
 *@version    $Id: CustomerProductList.java,v 1.1.2.7 2004/05/20 00:05:49 ananth
 *      Exp $
 */
public class CustomerProductList extends ArrayList {
  private PagedListInfo pagedListInfo = null;
  private int orgId = -1;
  private int orderId = -1;
  private int orderItemId = -1;
  private int statusId = -1;
  private int enabled = Constants.UNDEFINED;
  private int productId = -1;
  private boolean matchProduct = true;
  private boolean buildFileList = false;
  private boolean buildHistoryList = false;
  private boolean svgProductsOnly = false;


  /**
   *  Sets the svgProductsOnly attribute of the CustomerProductList object
   *
   *@param  tmp  The new svgProductsOnly value
   */
  public void setSvgProductsOnly(boolean tmp) {
    this.svgProductsOnly = tmp;
  }


  /**
   *  Sets the svgProductsOnly attribute of the CustomerProductList object
   *
   *@param  tmp  The new svgProductsOnly value
   */
  public void setSvgProductsOnly(String tmp) {
    this.svgProductsOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the svgProductsOnly attribute of the CustomerProductList object
   *
   *@return    The svgProductsOnly value
   */
  public boolean getSvgProductsOnly() {
    return svgProductsOnly;
  }


  /**
   *  Sets the matchProduct attribute of the CustomerProductList object
   *
   *@param  tmp  The new matchProduct value
   */
  public void setMatchProduct(boolean tmp) {
    this.matchProduct = tmp;
  }


  /**
   *  Sets the matchProduct attribute of the CustomerProductList object
   *
   *@param  tmp  The new matchProduct value
   */
  public void setMatchProduct(String tmp) {
    this.matchProduct = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the matchProduct attribute of the CustomerProductList object
   *
   *@return    The matchProduct value
   */
  public boolean getMatchProduct() {
    return matchProduct;
  }


  /**
   *  Sets the buildHistoryList attribute of the CustomerProductList object
   *
   *@param  tmp  The new buildHistoryList value
   */
  public void setBuildHistoryList(boolean tmp) {
    this.buildHistoryList = tmp;
  }


  /**
   *  Sets the buildHistoryList attribute of the CustomerProductList object
   *
   *@param  tmp  The new buildHistoryList value
   */
  public void setBuildHistoryList(String tmp) {
    this.buildHistoryList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildHistoryList attribute of the CustomerProductList object
   *
   *@return    The buildHistoryList value
   */
  public boolean getBuildHistoryList() {
    return buildHistoryList;
  }


  /**
   *  Sets the buildFileList attribute of the CustomerProductList object
   *
   *@param  tmp  The new buildFileList value
   */
  public void setBuildFileList(boolean tmp) {
    this.buildFileList = tmp;
  }


  /**
   *  Sets the buildFileList attribute of the CustomerProductList object
   *
   *@param  tmp  The new buildFileList value
   */
  public void setBuildFileList(String tmp) {
    this.buildFileList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildFileList attribute of the CustomerProductList object
   *
   *@return    The buildFileList value
   */
  public boolean getBuildFileList() {
    return buildFileList;
  }


  /**
   *  Sets the productId attribute of the CustomerProductList object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the CustomerProductList object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the productId attribute of the CustomerProductList object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Sets the pagedListInfo attribute of the CustomerProductList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the orgId attribute of the CustomerProductList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the CustomerProductList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderId attribute of the CustomerProductList object
   *
   *@param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the orderId attribute of the CustomerProductList object
   *
   *@param  tmp  The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderItemId attribute of the CustomerProductList object
   *
   *@param  tmp  The new orderItemId value
   */
  public void setOrderItemId(int tmp) {
    this.orderItemId = tmp;
  }


  /**
   *  Sets the orderItemId attribute of the CustomerProductList object
   *
   *@param  tmp  The new orderItemId value
   */
  public void setOrderItemId(String tmp) {
    this.orderItemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the CustomerProductList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the CustomerProductList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the CustomerProductList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the CustomerProductList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the CustomerProductList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the orgId attribute of the CustomerProductList object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the orderId attribute of the CustomerProductList object
   *
   *@return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the orderItemId attribute of the CustomerProductList object
   *
   *@return    The orderItemId value
   */
  public int getOrderItemId() {
    return orderItemId;
  }


  /**
   *  Gets the statusId attribute of the CustomerProductList object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the enabled attribute of the CustomerProductList object
   *
   *@return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Constructor for the CustomerProductList object
   */
  public CustomerProductList() { }


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
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM customer_product cp " +
        " WHERE cp.customer_product_id > -1 ");

    createFilter(sqlFilter);
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
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
        items = prepareFilter(pst);
        //pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("cp.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY cp.order_id");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " cp.* " +
        " FROM customer_product cp " +
        " WHERE cp.customer_product_id > -1 "
        );

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
      CustomerProduct product = new CustomerProduct(rs);
      this.add(product);
    }
    rs.close();
    pst.close();
    if (buildFileList) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomerProduct thisProduct = (CustomerProduct) i.next();
        thisProduct.buildFileList(db);
      }
    }
    if (buildHistoryList) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomerProduct thisProduct = (CustomerProduct) i.next();
        thisProduct.buildHistoryList(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (orgId > -1) {
      sqlFilter.append("AND cp.org_id = ? ");
    }
    if (orderId > -1) {
      sqlFilter.append("AND cp.order_id = ? ");
    }
    if (orderItemId > -1) {
      sqlFilter.append("AND cp.order_item_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND cp.status = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND cp.enabled = ? ");
    }
    if (productId > -1) {
      if (matchProduct) {
        sqlFilter.append("AND order_item_id IN (SELECT item_id FROM order_product WHERE product_id = ? ) ");
      } else {
        sqlFilter.append("AND order_item_id IN (SELECT item_id FROM order_product WHERE product_id <> ? ) ");
      }
    }
    if (svgProductsOnly) {
      sqlFilter.append("AND cp.customer_product_id IN (SELECT link_item_id FROM project_files WHERE client_filename LIKE '%.svg') ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (orderId > -1) {
      pst.setInt(++i, orderId);
    }
    if (orderItemId > -1) {
      pst.setInt(++i, orderItemId);
    }
    if (statusId > -1) {
      pst.setInt(++i, statusId);
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


  /**
   *  Gets the customerProductIdFromOrderProductId attribute of the
   *  CustomerProductList object
   *
   *@param  id                Description of the Parameter
   *@return                   The customerProductIdFromOrderProductId value
   *@exception  SQLException  Description of the Exception
   */
  public int getCustomerProductIdFromOrderProductId(int id) throws SQLException {
    int result = -1;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      CustomerProduct customerProduct = (CustomerProduct) iterator.next();
      if (customerProduct.getOrderItemId() == id) {
        result = customerProduct.getId();
        break;
      }
    }
    return result;
  }
}

