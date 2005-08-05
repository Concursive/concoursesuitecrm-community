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
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Contains a list of Survey objects. The list can be built by setting
 * parameters and then calling buildList
 *
 * @author chris price
 * @version $Id$
 * @created August 7, 2002
 */
public class SurveyList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private int itemLength = -1;
  private int type = -1;
  private int enteredBy = -1;
  private String enteredByIdRange = null;
  private String jsEvent = null;
  private int enabled = Constants.TRUE;


  /**
   * Constructor for the SurveyList object
   */
  public SurveyList() {
  }


  /**
   * Queries the database and adds Survey objects to this collection based on
   * any specified parameters.
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
        "FROM survey s " +
        "WHERE s.survey_id > -1 ");

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
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND " + DatabaseUtils.toLowerCase(db) + "(s.name) < ? ");
        items = prepareFilter(pst);
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
      pagedListInfo.setDefaultSort("s.name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY s.name ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "s.* " +
        "FROM survey s " +
        "WHERE s.survey_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      Survey thisSurvey = new Survey(rs);
      this.addElement(thisSurvey);
    }
    rs.close();
    pst.close();

    //buildResources(db);
  }


  /**
   * Gets the pagedListInfo attribute of the SurveyList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the itemLength attribute of the SurveyList object
   *
   * @return The itemLength value
   */
  public int getItemLength() {
    return itemLength;
  }


  /**
   * Gets the type attribute of the SurveyList object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the enabled attribute of the SurveyList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the SurveyList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the pagedListInfo attribute of the SurveyList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the itemLength attribute of the SurveyList object
   *
   * @param tmp The new itemLength value
   */
  public void setItemLength(int tmp) {
    this.itemLength = tmp;
  }


  /**
   * Sets the type attribute of the SurveyList object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the enteredBy attribute of the SurveyList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredByIdRange attribute of the SurveyList object
   *
   * @param tmp The new enteredByIdRange value
   */
  public void setEnteredByIdRange(String tmp) {
    this.enteredByIdRange = tmp;
  }


  /**
   * Gets the enteredBy attribute of the SurveyList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the enteredByIdRange attribute of the SurveyList object
   *
   * @return The enteredByIdRange value
   */
  public String getEnteredByIdRange() {
    return enteredByIdRange;
  }


  /**
   * Sets the enteredBy attribute of the SurveyList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the jsEvent attribute of the SurveyList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Sets the jsEvent attribute of the SurveyList object
   *
   * @param jsEvent The new jsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }


  /**
   * Appends any list filters that were specified to the SQL statement
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (enteredBy != -1) {
      sqlFilter.append("AND s.enteredby = ? ");
    }
    if (enteredByIdRange != null) {
      sqlFilter.append("AND s.enteredby IN (" + enteredByIdRange + ") ");
    }
    if (enabled != -1) {
      sqlFilter.append("AND s.enabled = ? ");
    }
  }


  /**
   * Sets the PreparedStatement parameters that were added in createFilter
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }
    if (enabled != -1) {
      if (enabled == Constants.FALSE) {
        pst.setBoolean(++i, false);
      } else {
        pst.setBoolean(++i, true);
      }
    }

    return i;
  }


  /**
   * Gets the htmlSelect attribute of the SurveyList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect surveyListSelect = new HtmlSelect();
    surveyListSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Survey thisSurvey = (Survey) i.next();
      surveyListSelect.addItem(
          thisSurvey.getId(),
          thisSurvey.getName());
    }
    return surveyListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Adds a feature to the Item attribute of the SurveyList object
   *
   * @param key  The feature to be added to the Item attribute
   * @param name The feature to be added to the Item attribute
   */
  public void addItem(int key, String name) {
    Survey thisSurvey = new Survey();
    thisSurvey.setId(key);
    thisSurvey.setName(name);
    if (this.size() == 0) {
      this.add(thisSurvey);
    } else {
      this.add(0, thisSurvey);
    }
  }


  /**
   * Checks to see if the specified surveyId is in this collection of Survey
   * objects
   *
   * @param surveyId Survey ID to look for
   * @return Returns true if found, else false
   */
  public boolean hasId(int surveyId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Survey thisSurvey = (Survey) i.next();
      if (thisSurvey.getId() == surveyId) {
        return true;
      }
    }
    return false;
  }
}

