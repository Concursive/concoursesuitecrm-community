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

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * List class for Option Values.
 *
 * @author partha
 * @version $Id: ProductOptionValuesList.java,v 1.1.2.2 2004/03/19 20:46:00
 *          partha Exp $
 * @created March 19, 2004
 */
public class ProductOptionValuesList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int optionId = -1;
  private int resultId = -1;
  private int productId = -1;


  /**
   * Sets the productId attribute of the ProductOptionValuesList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   * Sets the productId attribute of the ProductOptionValuesList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   * Gets the productId attribute of the ProductOptionValuesList object
   *
   * @return The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   * Sets the resultId attribute of the ProductOptionValuesList object
   *
   * @param tmp The new resultId value
   */
  public void setResultId(int tmp) {
    this.resultId = tmp;
  }


  /**
   * Sets the resultId attribute of the ProductOptionValuesList object
   *
   * @param tmp The new resultId value
   */
  public void setResultId(String tmp) {
    this.resultId = Integer.parseInt(tmp);
  }


  /**
   * Gets the resultId attribute of the ProductOptionValuesList object
   *
   * @return The resultId value
   */
  public int getResultId() {
    return resultId;
  }


  /**
   * Sets the pagedListInfo attribute of the ProductOptionValuesList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the id attribute of the ProductOptionValuesList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ProductOptionValuesList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the optionId attribute of the ProductOptionValuesList object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(int tmp) {
    this.optionId = tmp;
  }


  /**
   * Sets the optionId attribute of the ProductOptionValuesList object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(String tmp) {
    this.optionId = Integer.parseInt(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the ProductOptionValuesList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the id attribute of the ProductOptionValuesList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the optionId attribute of the ProductOptionValuesList object
   *
   * @return The optionId value
   */
  public int getOptionId() {
    return optionId;
  }


  /**
   * Constructor for the ProductOptionValuesList object
   */
  public ProductOptionValuesList() {
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
        "FROM product_option_values poptvalues " +
        "WHERE poptvalues.value_id > 0 ");

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
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "poptvalues.* " +
        "FROM product_option_values poptvalues " +
        "WHERE poptvalues.value_id > 0 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ProductOptionValues value = new ProductOptionValues(rs);
      this.add(value);
    }
    rs.close();
    pst.close();

  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator optionValues = this.iterator();
    while (optionValues.hasNext()) {
      ProductOptionValues value = (ProductOptionValues) optionValues.next();
      //value.delete(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (id > -1) {
      sqlFilter.append("AND poptvalues.value_id = ? ");
    }
    if (optionId > -1) {
      sqlFilter.append("AND poptvalues.option_id = ? ");
    }
    if (resultId > -1) {
      sqlFilter.append("AND poptvalues.result_id = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (optionId > -1) {
      pst.setInt(++i, optionId);
    }
    if (resultId > -1) {
      pst.setInt(++i, resultId);
    }
    return i;
  }


  /**
   * Gets the valueFromId attribute of the ProductOptionValuesList object
   *
   * @param id Description of the Parameter
   * @return The valueFromId value
   * @throws SQLException Description of the Exception
   */
  public ProductOptionValues getValueFromId(int id) throws SQLException {
    ProductOptionValues result = null;
    Iterator values = (Iterator) this.iterator();
    while (values.hasNext()) {
      ProductOptionValues value = (ProductOptionValues) values.next();
      if (value.getId() == id) {
        result = value;
        break;
      }
    }
    return result;
  }

}

