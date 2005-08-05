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
package org.aspcfs.modules.contacts.base;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.TextMessageAddressList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Contains a list of email addresses... currently used to build the list from
 * the database with any of the parameters to limit the results.
 *
 * @author kailash
 * @version $Id$
 * @created January 11, 2005
 */
public class ContactTextMessageAddressList extends TextMessageAddressList {

  public final static String tableName = "contact_textmessageaddress";
  public final static String uniqueField = "address_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  //Html drop-down helper properties
  private String emptyHtmlSelectRecord = null;
  private String jsEvent = null;


  /**
   * Constructor for the ContactTextMessageAddressList object
   */
  public ContactTextMessageAddressList() {
  }


  /**
   * Constructor for the ContactTextMessageAddressList object
   *
   * @param request Description of the Parameter
   */
  public ContactTextMessageAddressList(HttpServletRequest request) {
    int i = 0;
    int primaryTextMessageAddress = -1;
    if (request.getParameter("primaryTextMessageAddress") != null) {
      primaryTextMessageAddress = Integer.parseInt(
          (String) request.getParameter("primaryTextMessageAddress"));
    }

    while (request.getParameter("textmessage" + (++i) + "type") != null) {
      ContactTextMessageAddress thisAddress = new ContactTextMessageAddress();
      thisAddress.buildRecord(request, i);
      if (primaryTextMessageAddress == i) {
        thisAddress.setPrimaryTextMessageAddress(true);
      }
      if (thisAddress.isValid()) {
        this.addElement(thisAddress);
      }
    }
  }


  /**
   * Gets the tableName attribute of the ContactTextMessageAddressList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ContactTextMessageAddressList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the lastAnchor attribute of the ContactTextMessageAddressList object
   *
   * @return The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the ContactTextMessageAddressList object
   *
   * @return The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the syncType attribute of the ContactTextMessageAddressList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   * Gets the emptyHtmlSelectRecord attribute of the
   * ContactTextMessageAddressList object
   *
   * @return The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   * Gets the jsEvent attribute of the ContactTextMessageAddressList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Sets the lastAnchor attribute of the ContactTextMessageAddressList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ContactTextMessageAddressList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the syncType attribute of the ContactTextMessageAddressList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the
   * ContactTextMessageAddressList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the jsEvent attribute of the ContactTextMessageAddressList object
   *
   * @param tmp The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
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

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM contact_textmessageaddress t, lookup_textmessage_types l " +
        "WHERE t.textmessageaddress_type = l.code ");

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM contact_textmessageaddress t, lookup_textmessage_types l " +
        "WHERE t.textmessageaddress_type = l.code ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString());
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
            "AND " + DatabaseUtils.toLowerCase(db) + "(textmessageaddress) < ? ");
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
      if (pagedListInfo.getColumnToSortBy() != null && !pagedListInfo.getColumnToSortBy().equals(
          "")) {
        sqlOrder.append(
            "ORDER BY " + pagedListInfo.getColumnToSortBy() + ", textmessageaddress ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals(
            "")) {
          sqlOrder.append(pagedListInfo.getSortOrder() + " ");
        }
      } else {
        sqlOrder.append("ORDER BY textmessageaddress ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }

      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    }

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      ContactTextMessageAddress thisAddress = new ContactTextMessageAddress(
          rs);
      this.addElement(thisAddress);
    }
    rs.close();
    pst.close();
  }


  /**
   * Gets the htmlSelect attribute of the ContactTextMessageAddressList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the emptyHtmlSelect attribute of the ContactTextMessageAddressList
   * object
   *
   * @param selectName Description of the Parameter
   * @return The emptyHtmlSelect value
   */
  public String getEmptyHtmlSelect(SystemStatus thisSystem, String selectName) {
    HtmlSelect emailListSelect = new HtmlSelect();
    emailListSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));

    return emailListSelect.getHtml(selectName);
  }


  /**
   * Gets the htmlSelect attribute of the ContactTextMessageAddressList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect emailListSelect = new HtmlSelect();
    emailListSelect.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      emailListSelect.addItem(-1, emptyHtmlSelectRecord);
    }

    Iterator i = this.iterator();
    while (i.hasNext()) {
      ContactTextMessageAddress thisAddress = (ContactTextMessageAddress) i.next();
      //emailListSelect.addItem(thisAddress.getId(), thisAddress.getEmail());
      emailListSelect.addItem(
          thisAddress.getId(),
          thisAddress.getTextMessageAddress() + (thisAddress.getPrimaryTextMessageAddress() ? "*" : ""));
    }
    return emailListSelect.getHtml(selectName, defaultKey);
  }

}

