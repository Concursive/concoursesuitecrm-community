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
package org.aspcfs.modules.accounts.base;

import org.aspcfs.modules.base.EmailAddressList;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

/**
 *  Contains a list of email addresses... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     Mathur
 * @version    $Id: OrganizationEmailAddressList.java 15647 2006-08-16 14:02:17Z
 *      matt $
 * @created    January 13, 2003
 */
public class OrganizationEmailAddressList extends EmailAddressList implements SyncableList{

  public final static String tableName = "organization_emailaddress";
  public final static String uniqueField = "emailaddress_id";

  /**
   *  Constructor for the OrganizationEmailAddressList object
   */
  public OrganizationEmailAddressList() { }

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
   *  Constructor for the OrganizationEmailAddressList object
   *
   * @param  request  Description of the Parameter
   */
  public OrganizationEmailAddressList(HttpServletRequest request) {
    int i = 0;
    int primaryEmail = -1;
    if (request.getParameter("primaryEmail") != null) {
      primaryEmail = Integer.parseInt(
          (String) request.getParameter("primaryEmail"));
    }
    while (request.getParameter("email" + (++i) + "type") != null) {
      OrganizationEmailAddress thisEmailAddress = new OrganizationEmailAddress();
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
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  pst               Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM organization_emailaddress e, lookup_orgemail_types l " +
        "WHERE e.emailaddress_type = l.code ");

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM organization_emailaddress e, lookup_orgemail_types l " +
        "WHERE e.emailaddress_type = l.code ");

    createFilter(db, sqlFilter);

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
            "AND " + DatabaseUtils.toLowerCase(db) + "(email) < ? ");
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
            "ORDER BY " + pagedListInfo.getColumnToSortBy() + ", email ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals(
            "")) {
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

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    return rs;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    ResultSet rs = queryList(db, null);
    while (rs.next()) {
      OrganizationEmailAddress thisEmailAddress = this.getObject(rs);
      this.addElement(thisEmailAddress);
    }
    rs.close();
  }


  /**
   *  Gets the object attribute of the OrganizationEmailAddressList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public OrganizationEmailAddress getObject(ResultSet rs) throws SQLException {
    OrganizationEmailAddress thisAddress = new OrganizationEmailAddress(rs);
    return thisAddress;
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

