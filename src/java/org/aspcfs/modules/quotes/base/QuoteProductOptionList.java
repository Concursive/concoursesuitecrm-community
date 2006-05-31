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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: QuoteProductOptionList.java,v 1.2 2004/05/04 15:52:27
 *          mrajkowski Exp $
 * @created March 24, 2004
 */
public class QuoteProductOptionList extends ArrayList {
  private int itemId = -1;
  private int statusId = -1;
  //resources
  private boolean buildConfigDetails = false;


  /**
   * Gets the buildConfigDetails attribute of the QuoteProductOptionList object
   *
   * @return The buildConfigDetails value
   */
  public boolean getBuildConfigDetails() {
    return buildConfigDetails;
  }


  /**
   * Sets the buildConfigDetails attribute of the QuoteProductOptionList object
   *
   * @param tmp The new buildConfigDetails value
   */
  public void setBuildConfigDetails(boolean tmp) {
    this.buildConfigDetails = tmp;
  }


  /**
   * Sets the buildConfigDetails attribute of the QuoteProductOptionList object
   *
   * @param tmp The new buildConfigDetails value
   */
  public void setBuildConfigDetails(String tmp) {
    this.buildConfigDetails = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the itemId attribute of the QuoteProductOptionList object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   * Sets the itemId attribute of the QuoteProductOptionList object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusId attribute of the QuoteProductOptionList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the QuoteProductOptionList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Gets the itemId attribute of the QuoteProductOptionList object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Gets the statusId attribute of the QuoteProductOptionList object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Constructor for the QuoteProductOptionList object
   */
  public QuoteProductOptionList() {
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
    //Build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM quote_product_options opt " +
        "LEFT JOIN quote_product_option_boolean bool ON ( opt.quote_product_option_id = bool.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_float \"float\" ON ( opt.quote_product_option_id = \"float\".quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_timestamp tst ON ( opt.quote_product_option_id = tst.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_integer intr ON ( opt.quote_product_option_id = intr.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_text txt ON ( opt.quote_product_option_id = txt.quote_product_option_id ) " +
        "LEFT JOIN product_option_map pom ON (opt.product_option_id = pom.product_option_id) " +
        "LEFT JOIN product_option po ON (pom.option_id = po.option_id), " +
        "quote_product prod " +
        "WHERE opt.item_id = prod.item_id " +
        "AND opt.quote_product_option_id > -1 ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY opt.status_id");
    //Build a base SQL statement for returning records
    sqlSelect.append("SELECT ");
    sqlSelect.append(
        "opt.*, " +
        "bool.\"value\" AS boolean_value, " +
        "\"float\".\"value\" AS float_value, intr.\"value\" AS integer_value, " +
        "tst.\"value\" AS timestamp_value, txt.\"value\" AS text_value, " +
        "pom.option_id, po.configurator_id, " +
        "prod.product_id " +
        "FROM quote_product_options opt " +
        "LEFT JOIN quote_product_option_boolean bool ON ( opt.quote_product_option_id = bool.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_float \"float\" ON ( opt.quote_product_option_id = \"float\".quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_timestamp tst ON ( opt.quote_product_option_id = tst.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_integer intr ON ( opt.quote_product_option_id = intr.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_text txt ON ( opt.quote_product_option_id = txt.quote_product_option_id ) " +
        "LEFT JOIN product_option_map pom ON (opt.product_option_id = pom.product_option_id) " +
        "LEFT JOIN product_option po ON (pom.option_id = po.option_id), " +
        "quote_product prod " +
        "WHERE opt.item_id = prod.item_id " +
        "AND opt.quote_product_option_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      QuoteProductOption thisQuoteProductOption = new QuoteProductOption(rs);
      this.add(thisQuoteProductOption);
    }
    rs.close();
    pst.close();
    if (buildConfigDetails) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        QuoteProductOption thisOption = (QuoteProductOption) i.next();
        thisOption.buildConfigDetails(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (itemId > -1) {
      sqlFilter.append("AND opt.item_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND opt.status_id = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (itemId > -1) {
      pst.setInt(++i, itemId);
    }

    if (statusId > -1) {
      pst.setInt(++i, statusId);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      QuoteProductOption thisOption = (QuoteProductOption) i.next();
      thisOption.insert(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      QuoteProductOption thisOption = (QuoteProductOption) i.next();
      thisOption.delete(db);
    }
  }
}

