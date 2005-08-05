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

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created October 28, 2004
 */
public class QuoteConditionList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int quoteId = -1;
  private int conditionId = -1;


  /**
   * Gets the pagedListInfo attribute of the QuoteConditionList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the QuoteConditionList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the QuoteConditionList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the QuoteConditionList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the QuoteConditionList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the quoteId attribute of the QuoteConditionList object
   *
   * @return The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   * Sets the quoteId attribute of the QuoteConditionList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   * Sets the quoteId attribute of the QuoteConditionList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the conditionId attribute of the QuoteConditionList object
   *
   * @return The conditionId value
   */
  public int getConditionId() {
    return conditionId;
  }


  /**
   * Sets the conditionId attribute of the QuoteConditionList object
   *
   * @param tmp The new conditionId value
   */
  public void setConditionId(int tmp) {
    this.conditionId = tmp;
  }


  /**
   * Sets the conditionId attribute of the QuoteConditionList object
   *
   * @param tmp The new conditionId value
   */
  public void setConditionId(String tmp) {
    this.conditionId = Integer.parseInt(tmp);
  }


  /**
   * Constructor for the QuoteConditionList object
   */
  public QuoteConditionList() {
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
        " SELECT COUNT(*) AS recordcount " +
        " FROM quote_condition AS qc " +
        " LEFT JOIN quote_entry AS qe ON (qc.quote_id = qe.quote_id) " +
        " LEFT JOIN lookup_quote_condition AS lqc ON (qc.condition_id = lqc.code) " +
        " WHERE qc.map_id > -1 ");
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

      //Determine column to sort by
      pagedListInfo.setDefaultSort("qc.map_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY qc.map_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "qc.*, " +
        "lqc.description AS condition_name " +
        "FROM quote_condition AS qc " +
        "LEFT JOIN quote_entry AS qe ON (qc.quote_id = qe.quote_id) " +
        "LEFT JOIN lookup_quote_condition AS lqc ON (qc.condition_id = lqc.code) " +
        " WHERE qc.map_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      QuoteCondition thisQuoteCondition = new QuoteCondition(rs);
      this.add(thisQuoteCondition);
    }
    rs.close();
    pst.close();
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
    if (id > -1) {
      sqlFilter.append("AND qc.map_id = ? ");
    }
    if (quoteId > -1) {
      sqlFilter.append(" AND qc.quote_id = ? ");
    }
    if (conditionId > -1) {
      sqlFilter.append("AND qc.condition_id = ? ");
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
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (quoteId > -1) {
      pst.setInt(++i, quoteId);
    }
    if (conditionId > -1) {
      pst.setInt(++i, conditionId);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      QuoteCondition quoteCondition = (QuoteCondition) iterator.next();
      quoteCondition.delete(db);
      iterator.remove();
    }
  }

  public boolean hasCondition(int id) {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      QuoteCondition condition = (QuoteCondition) iterator.next();
      if (condition.getConditionId() == id) {
        return true;
      }
    }
    return false;
  }

  public HashMap retrieveMap() {
    HashMap map = new HashMap();
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      QuoteCondition condition = (QuoteCondition) iterator.next();
      map.put("" + condition.getConditionId(), "" + condition.getId());
    }
    return map;
  }

  public boolean fixDeletedConditions(Connection db, HashMap map) throws SQLException {
    if (map.isEmpty()) {
      return true;
    }
    Iterator deleteIterator = (Iterator) map.keySet().iterator();
    while (deleteIterator.hasNext()) {
      String key = (String) deleteIterator.next();
      String mapId = (String) map.get(key);
      if (mapId != null || !"".equals(mapId)) {
        QuoteCondition condition = new QuoteCondition(
            db, Integer.parseInt(mapId));
        if (!condition.delete(db)) {
          return false;
        }
      }
    }
    return true;
  }
}

