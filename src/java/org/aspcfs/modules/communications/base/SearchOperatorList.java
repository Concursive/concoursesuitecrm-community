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
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author Wesley S. Gillette
 * @version $Id: SearchOperatorList.java,v 1.6 2003/03/07 14:13:39 mrajkowski
 *          Exp $
 * @created November 1, 2001
 */
public class SearchOperatorList extends ArrayList {
  public final static String tableName = "field_types";
  public final static String uniqueField = "id ";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;

  /**
   * Constructor for the SearchOperatorList object
   */
  public SearchOperatorList() {
  }

  /**
   * Sets the lastAnchor attribute of the SearchOperatorList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the SearchOperatorList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the SearchOperatorList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the SearchOperatorList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the SearchOperatorList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the SearchOperatorList object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the SearchOperatorList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the SearchOperatorList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildOperatorList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlSelect = new StringBuffer();
    sqlSelect.append(
        "SELECT id, data_typeid, data_type, operator, display_text " +
            "FROM field_types WHERE enabled = ? ");
    pst = db.prepareStatement(sqlSelect.toString());
    pst.setBoolean(1, true);
    rs = pst.executeQuery();
    while (rs.next()) {
      SearchOperator thisSearchOperator = new SearchOperator(rs);
      this.add(thisSearchOperator);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db     Description of Parameter
   * @param typeID Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildOperatorList(Connection db, int typeID) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlSelect = new StringBuffer();

    sqlSelect.append(
        "SELECT id, data_typeid, data_type, operator, display_text " +
            "FROM field_types " +
            "WHERE data_typeid = ? and enabled = ? ");
    pst = db.prepareStatement(sqlSelect.toString());
    pst.setInt(1, typeID);
    pst.setBoolean(2, true);
    rs = pst.executeQuery();
    while (rs.next()) {
      SearchOperator thisSearchOperator = new SearchOperator(rs);
      this.add(thisSearchOperator);
    }
    rs.close();
    pst.close();
  }

}

