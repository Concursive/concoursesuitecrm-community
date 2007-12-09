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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * List class for a Option Configurator
 *
 * @author ananth
 * @version $Id$
 * @created September 2, 2004
 */
public class ProductOptionConfiguratorList extends ArrayList {
  public final static String tableName = "product_option_configurator";
  public final static String uniqueField = "configurator_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  //other supplimentary fields
  private String optionName = null;
  private String productName = null;

  /**
   * Sets the lastAnchor attribute of the ProductOptionConfiguratorList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ProductOptionConfiguratorList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ProductOptionConfiguratorList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ProductOptionConfiguratorList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ProductOptionConfiguratorList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the ProductOptionConfiguratorList object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the ProductOptionConfiguratorList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ProductOptionConfiguratorList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Constructor for the ProductOptionConfiguratorList object
   */
  public ProductOptionConfiguratorList() {
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
            "FROM product_option_configurator poptconfig " +
            "WHERE poptconfig.configurator_id > 0");

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
        "conf.* " +
            "FROM product_option_configurator conf " +
            "WHERE conf.configurator_id > -1 ");
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
      ProductOptionConfigurator productOptionConfigurator = new ProductOptionConfigurator(
          rs);
      this.add(productOptionConfigurator);
    }
    rs.close();
    pst.close();

  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param basePath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db, String basePath) throws SQLException {
    Iterator configurators = this.iterator();
    while (configurators.hasNext()) {
      ProductOptionConfigurator productOptionConfigurator = (ProductOptionConfigurator) configurators.next();
      productOptionConfigurator.delete(db, basePath);
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
      sqlFilter.append(" AND poptconfig.configurator_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
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
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }

    return i;
  }


  /**
   * Gets the htmlSelect attribute of the ProductOptionConfiguratorList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect select = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProductOptionConfigurator config = (ProductOptionConfigurator) i.next();
      select.addItem(config.getId(), config.getConfiguratorName());
    }
    return select.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the htmlSelect attribute of the ProductOptionConfiguratorList object
   *
   * @return The htmlSelect value
   */
  public HtmlSelect getHtmlSelect() {
    HtmlSelect select = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProductOptionConfigurator config = (ProductOptionConfigurator) i.next();
      select.addItem(config.getId(), config.getConfiguratorName());
    }
    return select;
  }


  /**
   * Gets the configuratorFromId attribute of the ProductOptionConfiguratorList object
   *
   * @param id Description of the Parameter
   * @return The configuratorFromId value
   * @throws SQLException Description of the Exception
   */
  public ProductOptionConfigurator getConfiguratorFromId(int id) throws SQLException {
    ProductOptionConfigurator result = null;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ProductOptionConfigurator configurator = (ProductOptionConfigurator) iterator.next();
      if (configurator.getId() == id) {
        result = configurator;
        break;
      }
    }
    return result;
  }
}

