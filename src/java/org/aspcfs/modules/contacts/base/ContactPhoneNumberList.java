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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.base.PhoneNumber;
import org.aspcfs.modules.base.PhoneNumberList;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *  Contains a list of phone numbers... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @version    $Id: ContactPhoneNumberList.java,v 1.4 2003/01/15 15:51:07
 *      mrajkowski Exp $
 * @created    September 4, 2001
 */
public class ContactPhoneNumberList extends PhoneNumberList implements SyncableList{

  private static final long serialVersionUID = -3110613598478184780L;

  public final static String tableName = "contact_emailaddress";
  public final static String uniqueField = "emailaddress_id";

  /**
   *  Constructor for the ContactPhoneNumberList object
   *
   * @since    1.1
   */
  public ContactPhoneNumberList() { }


  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }
  
  /**
   *  Constructor for the ContactPhoneNumberList object
   *
   * @param  context  Description of the Parameter
   */
  public ContactPhoneNumberList(ActionContext context) {
    int i = 0;
    int primaryNumber = -1;
    if (context.getRequest().getParameter("primaryNumber") != null) {
      primaryNumber = Integer.parseInt(
          (String) context.getRequest().getParameter("primaryNumber"));
    }
    while (context.getRequest().getParameter("phone" + (++i) + "type") != null) {
      ContactPhoneNumber thisPhoneNumber = new ContactPhoneNumber();
      thisPhoneNumber.buildRecord(context, i);
      if (primaryNumber == i) {
        thisPhoneNumber.setPrimaryNumber(true);
      }
      if (thisPhoneNumber.isValid()) {
        this.addElement(thisPhoneNumber);
      }
    }
  }

  /**
   *  Gets the htmlSelect attribute of the ContactPhoneNumberList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect emailListSelect = new HtmlSelect();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      PhoneNumber thisNumber = (PhoneNumber) i.next();
      String elementText = null;

      elementText = String.valueOf(thisNumber.getTypeName().charAt(0)) + ":";
      elementText += thisNumber.getNumber();
      emailListSelect.addItem(
          thisNumber.getId(),
          elementText + (thisNumber.getPrimaryNumber() ? "*" : ""));
    }
    return emailListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM contact_phone p, lookup_contactphone_types l " +
        "WHERE p.phone_type = l.code ");

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM contact_phone p, lookup_contactphone_types l " +
        "WHERE p.phone_type = l.code ");

    createFilter(db, sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(
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
            "AND " + DatabaseUtils.toLowerCase(db) + "(phone_type) < ? ");
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
            "ORDER BY " + pagedListInfo.getColumnToSortBy() + ", phone_type ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals(
            "")) {
          sqlOrder.append(pagedListInfo.getSortOrder() + " ");
        }
      } else {
        sqlOrder.append("ORDER BY phone_type ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }
      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    } else {
      sqlOrder.append("ORDER BY phone_type ");
    }

    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   *  Builds a list of addresses based on several parameters. The parameters are
   *  set after this object is constructed, then the buildList method is called
   *  to generate the list.
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   * @since                 1.1
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      ContactPhoneNumber thisPhoneNumber = this.getObject(rs);
      this.addElement(thisPhoneNumber);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Gets the object attribute of the ContactPhoneNumberList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ContactPhoneNumber getObject(ResultSet rs) throws SQLException {
    ContactPhoneNumber thisPhoneNumber = new ContactPhoneNumber(rs);
    return thisPhoneNumber;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
}

