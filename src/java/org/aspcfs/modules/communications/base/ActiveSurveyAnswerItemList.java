//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.web.PagedListInfo;
import javax.servlet.http.*;

/**
 *  Builds list of contacts who responded for a particular Item in the ItemList
 *
 *@author     Mathur
 *@created    February 4, 2003
 *@version    $Id$
 */
public class ActiveSurveyAnswerItemList extends ArrayList {

  private int itemId = -1;
  protected PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the ActiveSurveyAnswerItemList object
   */
  public ActiveSurveyAnswerItemList() { }


  /**
   *  Sets the pagedListInfo attribute of the ActiveSurveyAnswerItemList object
   *
   *@param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the itemId attribute of the ActiveSurveyAnswerItemList object
   *
   *@param  itemId  The new itemId value
   */
  public void setItemId(int itemId) {
    this.itemId = itemId;
  }


  /**
   *  Gets the itemId attribute of the ActiveSurveyAnswerItemList object
   *
   *@return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


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
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM active_survey_responses asr, active_survey_answers asa, active_survey_answer_items asi " +
        "WHERE  asr.response_id = asa.response_id AND asa.answer_id = asi.answer_id AND asi.item_id = ? ");

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString());
      pst.setInt(1, itemId);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      pst.close();
      rs.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString());
        pst.setInt(1, itemId);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
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
        "SELECT contact_id " +
        "FROM active_survey_responses asr, active_survey_answers asa, active_survey_answer_items asi " +
        "WHERE  asr.response_id = asa.response_id AND asa.answer_id = asi.answer_id AND asi.item_id = ? ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlOrder.toString());
    pst.setInt(1, itemId);
    
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ActiveSurveyAnswerItemList Query --> " + pst.toString());
    }
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
      Contact thisContact = new Contact();
      thisContact.setId(rs.getInt("contact_id"));
      this.add(thisContact);
    }
    rs.close();
    pst.close();

    //build items & comments
    Iterator thisList = this.iterator();
    while (thisList.hasNext()) {
      Contact thisContact = (Contact) thisList.next();
      thisContact.build(db);
    }
  }
}

