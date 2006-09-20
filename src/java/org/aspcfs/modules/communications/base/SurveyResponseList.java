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

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Builds a list of survey recipients who responded.
 *
 * @author Mathur
 * @version $Id: SurveyResponseList.java,v 1.5 2004/10/12 13:58:36 mrajkowski
 *          Exp $
 * @created February 4, 2003
 */
public class SurveyResponseList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int surveyId = -1;
  private int addressUpdated = -1;

  private boolean onlyNotUpdated = false;
  private int contactId = -1;


  /**
   * Constructor for the SurveyResponseList object
   */
  public SurveyResponseList() {
  }


  /**
   * Gets the pagedListInfo attribute of the SurveyResponseList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the SurveyResponseList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the surveyId attribute of the SurveyResponseList object
   *
   * @param surveyId The new surveyId value
   */
  public void setSurveyId(int surveyId) {
    this.surveyId = surveyId;
  }


  /**
   * Sets the addressUpdated attribute of the SurveyResponseList object
   *
   * @param tmp The new addressUpdated value
   */
  public void setAddressUpdated(int tmp) {
    this.addressUpdated = tmp;
  }


  /**
   * Sets the addressUpdated attribute of the SurveyResponseList object
   *
   * @param tmp The new addressUpdated value
   */
  public void setAddressUpdated(String tmp) {
    this.addressUpdated = Integer.parseInt(tmp);
  }


  /**
   * Sets the onlyNotUpdated attribute of the SurveyResponseList object
   *
   * @param tmp The new onlyNotUpdated value
   */
  public void setOnlyNotUpdated(boolean tmp) {
    this.onlyNotUpdated = tmp;
  }


  /**
   * Sets the onlyNotUpdated attribute of the SurveyResponseList object
   *
   * @param tmp The new onlyNotUpdated value
   */
  public void setOnlyNotUpdated(String tmp) {
    this.onlyNotUpdated = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the contactId attribute of the SurveyResponseList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the SurveyResponseList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the surveyId attribute of the SurveyResponseList object
   *
   * @return The surveyId value
   */
  public int getSurveyId() {
    return surveyId;
  }


  /**
   * Gets the addressUpdated attribute of the SurveyResponseList object
   *
   * @return The addressUpdated value
   */
  public int getAddressUpdated() {
    return addressUpdated;
  }


  /**
   * Gets the onlyNotUpdated attribute of the SurveyResponseList object
   *
   * @return The onlyNotUpdated value
   */
  public boolean getOnlyNotUpdated() {
    return onlyNotUpdated;
  }


  /**
   * Gets the contactId attribute of the SurveyResponseList object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
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
        "FROM active_survey_responses sr " +
        "LEFT JOIN contact c ON (c.contact_id = sr.contact_id) " +
        "WHERE sr.active_survey_id > -1 ");

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
            "AND (" + DatabaseUtils.toLowerCase(db) + "(c.namelast) < ? AND c.namelast IS NOT NULL) ");
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
      pagedListInfo.setDefaultSort("c.namelast", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.namelast ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "sr.response_id, sr.active_survey_id, sr.contact_id, sr.unique_code, sr.ip_address, sr.entered " +
        "FROM active_survey_responses sr " +
        "LEFT JOIN contact c ON (c.contact_id = sr.contact_id) " +
        "WHERE sr.active_survey_id > -1 ");

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
      SurveyResponse thisResponse = new SurveyResponse(rs);
      this.add(thisResponse);
    }
    rs.close();
    pst.close();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      SurveyResponse thisResponse = (SurveyResponse) i.next();
      thisResponse.buildContact(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (surveyId != -1) {
      sqlFilter.append("AND sr.active_survey_id = ? ");
    }

    if (addressUpdated != -1) {
      sqlFilter.append("AND sr.address_updated = ? ");
    }

    if (this.onlyNotUpdated) {
      sqlFilter.append(
          "AND sr.address_updated = ? AND sr.contact_id NOT IN (SELECT contact_id FROM active_survey_responses WHERE active_survey_id = ? AND address_updated = ?) ");
    }

    if (contactId != -1) {
      sqlFilter.append("AND sr.contact_id = ? ");
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

    if (surveyId != -1) {
      pst.setInt(++i, surveyId);
    }
    if (addressUpdated != -1) {
      pst.setInt(++i, addressUpdated);
    }
    if (this.onlyNotUpdated) {
      pst.setInt(++i, SurveyResponse.ADDRESS_VALID);
      pst.setInt(++i, this.getSurveyId());
      pst.setInt(++i, SurveyResponse.ADDRESS_UPDATED);
    }
    if (contactId != -1) {
      pst.setInt(++i, this.getContactId());
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      SurveyResponse surveyResponse = (SurveyResponse) itr.next();
      surveyResponse.delete(db);
    }
    return true;
  }

}

