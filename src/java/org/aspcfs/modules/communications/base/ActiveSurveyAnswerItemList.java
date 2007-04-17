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
package org.aspcfs.modules.communications.base;

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
 * Builds list of contacts who responded for a particular Item in the ItemList
 *
 * @author Mathur
 * @version $Id: ActiveSurveyAnswerItemList.java,v 1.5 2003/02/17 14:39:16
 *          akhi_m Exp $
 * @created February 4, 2003
 */
public class ActiveSurveyAnswerItemList extends ArrayList {
  public final static String tableName = "active_survey_answer_items";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private int itemId = -1;
  private int contactId = -1;
  private int answerId = -1;
  protected PagedListInfo pagedListInfo = null;
  private ActiveSurveyQuestionItem item = null;

  /**
   * Sets the lastAnchor attribute of the ActiveSurveyAnswerItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActiveSurveyAnswerItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActiveSurveyAnswerItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActiveSurveyAnswerItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActiveSurveyAnswerItemList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the ActiveSurveyAnswerItemList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActiveSurveyAnswerItemList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Constructor for the ActiveSurveyAnswerItemList object
   */
  public ActiveSurveyAnswerItemList() {
  }


  /**
   * Sets the pagedListInfo attribute of the ActiveSurveyAnswerItemList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Sets the itemId attribute of the ActiveSurveyAnswerItemList object
   *
   * @param itemId The new itemId value
   */
  public void setItemId(int itemId) {
    this.itemId = itemId;
  }


  /**
   * Sets the contactId attribute of the ActiveSurveyAnswerItemList object
   *
   * @param contactId The new contactId value
   */
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }


  /**
   * Sets the answerId attribute of the ActiveSurveyAnswerItemList object
   *
   * @param answerId The new answerId value
   */
  public void setAnswerId(int answerId) {
    this.answerId = answerId;
  }


  /**
   * Gets the contactId attribute of the ActiveSurveyAnswerItemList object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the answerId attribute of the ActiveSurveyAnswerItemList object
   *
   * @return The answerId value
   */
  public int getAnswerId() {
    return answerId;
  }


  /**
   * Gets the item attribute of the ActiveSurveyAnswerItemList object
   *
   * @return The item value
   */
  public ActiveSurveyQuestionItem getItem() {
    return item;
  }


  /**
   * Gets the itemId attribute of the ActiveSurveyAnswerItemList object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Builds the response for an item
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      SurveyAnswerItem thisItem = new SurveyAnswerItem();
      thisItem.buildDetailedRecord(rs);
      this.add(thisItem);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (itemId != -1) {
      item = new ActiveSurveyQuestionItem(db, itemId);
    }
    //build items & comments
    Iterator thisList = this.iterator();
    while (thisList.hasNext()) {
      SurveyAnswerItem thisItem = (SurveyAnswerItem) thisList.next();
      thisItem.buildContactDetails(db);
    }
  }


  /**
   * Builds and Returns a SQL ResultSet
   *
   * @param db  Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM active_survey_responses asr, active_survey_answers asa, active_survey_answer_items asi " +
            "WHERE asr.response_id = asa.response_id AND asa.answer_id = asi.answer_id ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      if (pagedListInfo != null) {
        pagedListInfo.doManualOffset(db, pst);
      }
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString());
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        if (pagedListInfo != null) {
          pagedListInfo.doManualOffset(db, pst);
        }
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("asr.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY asr.entered ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "asi.item_id, asi.answer_id, asi.comments, asr.contact_id, asr.entered  " +
            "FROM active_survey_responses asr, active_survey_answers asa, active_survey_answer_items asi " +
            "WHERE asr.response_id = asa.response_id AND asa.answer_id = asi.answer_id ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);

    return pst;
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
    if (itemId != -1) {
      sqlFilter.append("AND asi.item_id = ? ");
    }
    if (contactId != -1) {
      sqlFilter.append("AND asr.contact_id = ? ");
    }
    if (answerId != -1) {
      sqlFilter.append("AND asa.answer_id = ? ");
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
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (itemId != -1) {
      pst.setInt(++i, itemId);
    }

    if (contactId != -1) {
      pst.setInt(++i, contactId);
    }

    if (answerId != -1) {
      pst.setInt(++i, answerId);
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
   * Returns the count of answers for a item
   *
   * @param db     Description of the Parameter
   * @param itemId Description of the Parameter
   * @return The itemCount value
   * @throws SQLException Description of the Exception
   */
  public static int getItemCount(Connection db, int itemId) throws SQLException {
    int recordCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS thecount " +
            "FROM active_survey_answer_items " +
            "WHERE item_id = ? ");
    pst.setInt(1, itemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = rs.getInt("thecount");
    }
    rs.close();
    pst.close();
    return recordCount;
  }


  /**
   * Checks to see if the given item is present
   *
   * @param id Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasItem(int id) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SurveyAnswerItem thisItem = (SurveyAnswerItem) i.next();
      if (thisItem.getId() == id) {
        return true;
      }
    }
    return false;
  }
}

