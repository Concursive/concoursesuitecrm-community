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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.EmailAddressList;
import org.aspcfs.utils.web.HtmlSelect;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *  Contains a list of email addresses... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    January 29, 2003
 *@version    $Id: ContactEmailAddressList.java,v 1.7.34.2 2004/04/08 18:36:20
 *      kbhoopal Exp $
 */
public class ContactEmailAddressList extends EmailAddressList {

  public final static String tableName = "contact_emailaddress";
  public final static String uniqueField = "emailaddress_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  //Html drop-down helper properties
  private String emptyHtmlSelectRecord = null;
  private String jsEvent = null;


  /**
   *  Constructor for the ContactEmailAddressList object
   */
  public ContactEmailAddressList() { }


  /**
   *  Constructor for the ContactEmailAddressList object
   *
   *@param  request  Description of the Parameter
   */
  public ContactEmailAddressList(HttpServletRequest request) {
    int i = 0;
    int primaryEmail = -1;
    if (request.getParameter("primaryEmail") != null) {
      primaryEmail = Integer.parseInt((String) request.getParameter("primaryEmail"));
    }
    while (request.getParameter("email" + (++i) + "type") != null) {
      ContactEmailAddress thisEmailAddress = new ContactEmailAddress();
      thisEmailAddress.buildRecord(request, i);
      if (primaryEmail == i) {
        thisEmailAddress.setPrimaryEmail(true);
      }
      if (thisEmailAddress.isValid()) {
        this.addElement(thisEmailAddress);
      }
    }
  }


  /**
   *  Gets the tableName attribute of the ContactEmailAddressList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the ContactEmailAddressList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the ContactEmailAddressList object
   *
   *@return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the ContactEmailAddressList object
   *
   *@return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the ContactEmailAddressList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Gets the emptyHtmlSelectRecord attribute of the ContactEmailAddressList
   *  object
   *
   *@return    The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   *  Gets the jsEvent attribute of the ContactEmailAddressList object
   *
   *@return    The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Sets the lastAnchor attribute of the ContactEmailAddressList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the ContactEmailAddressList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the syncType attribute of the ContactEmailAddressList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the ContactEmailAddressList
   *  object
   *
   *@param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the jsEvent attribute of the ContactEmailAddressList object
   *
   *@param  tmp  The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
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
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append("SELECT * " +
        "FROM contact_emailaddress e, lookup_contactemail_types l " +
        "WHERE e.emailaddress_type = l.code ");

    //Need to build a base SQL statement for counting records
    sqlCount.append("SELECT COUNT(*) AS recordcount " +
        "FROM contact_emailaddress e, lookup_contactemail_types l " +
        "WHERE e.emailaddress_type = l.code ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
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
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND lower(email) < ? ");
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
      if (pagedListInfo.getColumnToSortBy() != null && !pagedListInfo.getColumnToSortBy().equals("")) {
        sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + ", email ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
          sqlOrder.append(pagedListInfo.getSortOrder() + " ");
        }
      } else {
        sqlOrder.append("ORDER BY email ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }

      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    }

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      ContactEmailAddress thisEmailAddress = new ContactEmailAddress(rs);
      this.addElement(thisEmailAddress);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the htmlSelect attribute of the ContactEmailAddressList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the emptyHtmlSelect attribute of the ContactEmailAddressList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The emptyHtmlSelect value
   */
  public String getEmptyHtmlSelect(String selectName) {
    HtmlSelect emailListSelect = new HtmlSelect();
    emailListSelect.addItem(-1, "-- None --");
    return emailListSelect.getHtml(selectName);
  }


  /**
   *  Gets the htmlSelect attribute of the ContactEmailAddressList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect emailListSelect = new HtmlSelect();
    emailListSelect.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      emailListSelect.addItem(-1, emptyHtmlSelectRecord);
    }

    Iterator i = this.iterator();
    while (i.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress) i.next();
      //emailListSelect.addItem(thisEmailAddress.getId(), thisEmailAddress.getEmail());
      emailListSelect.addItem(thisEmailAddress.getId(),
          thisEmailAddress.getEmail() + (thisEmailAddress.getPrimaryEmail() ? "*" : ""));
    }
    return emailListSelect.getHtml(selectName, defaultKey);
  }

}

