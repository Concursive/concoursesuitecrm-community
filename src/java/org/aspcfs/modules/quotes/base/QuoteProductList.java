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
package org.aspcfs.modules.quotes.base;

import java.sql.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.products.base.*;

/**
 *  This represents a list of quote products
 *
 *@author     ananth
 *@created    March 24, 2004
 *@version    $Id: QuoteProductList.java,v 1.1.2.2 2004/04/24 14:37:47 partha
 *      Exp $
 */
public class QuoteProductList extends ArrayList {
  private PagedListInfo pagedListInfo = null;
  private int quoteId = -1;
  private int statusId = -1;
  private boolean buildResources = false;


  /**
   *  Sets the pagedListInfo attribute of the QuoteProductList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteProductList object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteProductList object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the QuoteProductList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the QuoteProductList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildResources attribute of the QuoteProductList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the QuoteProductList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the QuoteProductList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the quoteId attribute of the QuoteProductList object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Gets the statusId attribute of the QuoteProductList object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the buildResources attribute of the QuoteProductList object
   *
   *@return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Constructor for the QuoteProductList object
   */
  public QuoteProductList() { }


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
        "SELECT COUNT(*) AS recordcount " +
        "FROM quote_product qp " +
        "WHERE qp.item_id > -1 ");
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
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY status_date");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " qp.* " +
        " FROM quote_product qp " +
        " WHERE qp.item_id > -1 ");

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
      QuoteProduct thisQuoteProduct = new QuoteProduct(rs);
      this.add(thisQuoteProduct);
    }
    rs.close();
    pst.close();
    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        QuoteProduct thisQuoteProduct = (QuoteProduct) i.next();
        thisQuoteProduct.buildProductOptions(db);
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

    if (quoteId > -1) {
      sqlFilter.append("AND qp.quote_id = ? ");
    }

    if (statusId > -1) {
      sqlFilter.append("AND qp.status_id = ? ");
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
    if (quoteId > -1) {
      pst.setInt(++i, quoteId);
    }

    if (statusId > -1) {
      pst.setInt(++i, statusId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      QuoteProduct thisProduct = (QuoteProduct) i.next();
      thisProduct.insert(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      QuoteProduct thisProduct = (QuoteProduct) i.next();
      thisProduct.delete(db);
    }
  }


  /**
   *  Gets the quoteProductFromProductId attribute of the QuoteProductList
   *  object
   *
   *@param  id                Description of the Parameter
   *@return                   The quoteProductFromProductId value
   *@exception  SQLException  Description of the Exception
   */
  public QuoteProduct getQuoteProductFromProductId(int id) throws SQLException {
    QuoteProduct result = null;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      QuoteProduct quoteProduct = (QuoteProduct) iterator.next();
      if (quoteProduct.getProductId() == id) {
        result = quoteProduct;
        break;
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void populate(Connection db, ActionContext context) throws SQLException {
    for (int i = 1; ; i++) {
      String productIdString = (String) context.getRequest().getParameter("product_" + i);
      String quantityString = (String) context.getRequest().getParameter("qty_" + i);
      if (productIdString != null && !"".equals(productIdString)) {
        if (quantityString != null && !"".equals(quantityString)) {
          ProductCatalog product = new ProductCatalog(db, Integer.parseInt(productIdString));
          product.setBuildProductOptions(true);
          product.buildProductOptions(db);
          QuoteProduct quoteProduct = new QuoteProduct();
          quoteProduct.setProductCatalog(product);
          quoteProduct.setProductId(product.getId());
          quoteProduct.setQuantity(Integer.parseInt(quantityString));
          this.add(quoteProduct);
        }
      } else {
        break;
      }
    }
  }
}

