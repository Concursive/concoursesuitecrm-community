/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     mrajkowski
 * @created    January 14, 2003
 * @version    $Id: CustomLookupList.java 12404 2005-08-05 17:37:07Z mrajkowski
 *      $
 */
public class CustomLookupList extends LookupList implements SyncableList{

  ArrayList fields = new ArrayList();


  /**
   *  Constructor for the CustomLookupList object
   */
  public CustomLookupList() {
    super();
  }


  /**
   *  Gets the object attribute of the CustomLookupList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public Object getObject(ResultSet rs) throws SQLException {
    CustomLookupElement thisElement = new CustomLookupElement(rs);
    thisElement.setTableName(tableName);
    thisElement.setUniqueField(uniqueField);

    return thisElement;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      CustomLookupElement thisElement = (CustomLookupElement) this.getObject(rs);
      this.add(thisElement);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    int items = -1;

    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlSelect = new StringBuffer();

    createFilter(sqlFilter);

    sqlSelect.append("SELECT ");
    Iterator i = fields.iterator();
    while (i.hasNext()) {
      String field = (String) i.next();
      sqlSelect.append(
         DatabaseUtils.parseReservedWord(db, field));
      if (i.hasNext()) {
        sqlSelect.append(",");
      }
      sqlSelect.append(" ");
    }
    sqlSelect.append("FROM " + tableName + " ");

    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   *  Sets the property attribute of the CustomLookupList object
   *
   * @param  tmp  The new property value
   */
  public void setProperty(String tmp) {
    addField(tmp);
  }


  /**
   *  Adds a feature to the Field attribute of the CustomLookupList object
   *
   * @param  fieldName  The feature to be added to the Field attribute
   */
  public void addField(String fieldName) {
    if (fields == null) {
      fields = new ArrayList();
    }
    fields.add(fieldName);
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("WHERE entered > ? ");
      }
      sqlFilter.append(lastAnchor != null ? "AND entered < ? " : "WHERE entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("WHERE modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
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
}

