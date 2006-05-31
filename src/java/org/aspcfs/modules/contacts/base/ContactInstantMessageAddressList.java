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

import org.aspcfs.modules.base.InstantMessageAddressList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created April 21, 2005
 */
public class ContactInstantMessageAddressList extends InstantMessageAddressList {
  //Html drop-down helper properties
  private String emptyHtmlSelectRecord = null;
  private String jsEvent = null;

  /**
   * Gets the emptyHtmlSelectRecord attribute of the
   * ContactInstantMessageAddressList object
   *
   * @return The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the
   * ContactInstantMessageAddressList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Gets the jsEvent attribute of the ContactInstantMessageAddressList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Sets the jsEvent attribute of the ContactInstantMessageAddressList object
   *
   * @param tmp The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }

  /**
   * Constructor for the ContactInstantMessageAddressList object
   */
  public ContactInstantMessageAddressList() {
  }


  /**
   * Constructor for the ContactInstantMessageAddressList object
   *
   * @param request Description of the Parameter
   */
  public ContactInstantMessageAddressList(HttpServletRequest request) {
    int i = 0;
    int primaryIMAddress = -1;
    if (request.getParameter("primaryIM") != null) {
      primaryIMAddress = Integer.parseInt(
          (String) request.getParameter("primaryIM"));
    }

    while (request.getParameter("instantmessage" + (++i) + "type") != null) {
      ContactInstantMessageAddress thisAddress = new ContactInstantMessageAddress();
      thisAddress.buildRecord(request, i);
      if (primaryIMAddress == i) {
        thisAddress.setPrimaryIM(true);
      }
      if (thisAddress.isValid()) {
        this.addElement(thisAddress);
      }
    }
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
        "SELECT cim.*, " +
        "lims.description AS service_description, limt.description AS type_description " +
        "FROM contact_imaddress cim " +
        "LEFT JOIN lookup_im_services lims ON (cim.imaddress_service = lims.code) " +
        "LEFT JOIN lookup_im_types limt ON (cim.imaddress_type = limt.code) " +
        "WHERE cim.address_id > -1 ");
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM contact_imaddress cim " +
        "LEFT JOIN lookup_im_services lims ON (cim.imaddress_service = lims.code) " +
        "LEFT JOIN lookup_im_types limt ON (cim.imaddress_type = limt.code) " +
        "WHERE cim.address_id > -1 ");

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
            "AND " + DatabaseUtils.toLowerCase(db) + "(imaddress) < ? ");
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
            "ORDER BY " + pagedListInfo.getColumnToSortBy() + ", imaddress ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals(
            "")) {
          sqlOrder.append(pagedListInfo.getSortOrder() + " ");
        }
      } else {
        sqlOrder.append("ORDER BY imaddress ");
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
      ContactInstantMessageAddress thisAddress = new ContactInstantMessageAddress(
          rs);
      this.addElement(thisAddress);
    }
    rs.close();
    pst.close();
  }


  /**
   * Gets the emptyHtmlSelect attribute of the ContactInstantMessageAddressList
   * object
   *
   * @param selectName Description of the Parameter
   * @return The emptyHtmlSelect value
   */
  public String getEmptyHtmlSelect(String selectName) {
    HtmlSelect select = new HtmlSelect();
    select.addItem(-1, "-- None --");
    return select.getHtml(selectName);
  }


  /**
   * Gets the htmlSelect attribute of the ContactInstantMessageAddressList
   * object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect select = new HtmlSelect();
    select.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      select.addItem(-1, emptyHtmlSelectRecord);
    }

    Iterator i = this.iterator();
    while (i.hasNext()) {
      ContactInstantMessageAddress thisAddress = (ContactInstantMessageAddress) i.next();
      select.addItem(
          thisAddress.getId(),
          thisAddress.getAddressIM() + (thisAddress.getPrimaryIM() ? "*" : ""));
    }
    return select.getHtml(selectName, defaultKey);
  }
}

