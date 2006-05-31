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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A group contains SearchCriteriaElements which specify the search criteria
 * for a query.
 *
 * @author mrajkowski
 * @version $Id: SearchCriteriaGroup.java,v 1.2 2002/04/24 15:39:44 mrajkowski
 *          Exp $
 * @created November 13, 2001
 */
public class SearchCriteriaGroup extends ArrayList {

  SearchField groupField = new SearchField();


  /**
   * Constructor for the SearchCriteriaGroup object
   *
   * @since 1.1
   */
  public SearchCriteriaGroup() {
  }


  /**
   * Sets the GroupField attribute of the SearchCriteriaGroup object
   *
   * @param tmp The new GroupField value
   * @since 1.1
   */
  public void setGroupField(SearchField tmp) {
    this.groupField = tmp;
  }


  /**
   * Sets the Id attribute of the SearchCriteriaGroup object
   *
   * @param tmp The new Id value
   * @since 1.1
   */
  public void setId(int tmp) {
    groupField.setId(tmp);
  }


  /**
   * Gets the GroupField attribute of the SearchCriteriaGroup object
   *
   * @return The GroupField value
   * @since 1.1
   */
  public SearchField getGroupField() {
    return groupField;
  }


  /**
   * Retrieves descriptor information from the field_types table that pertains
   * to the operator that is associated with the object's SearchField
   *
   * @param db db connection
   * @throws SQLException SQL Exception
   * @since 1.1
   */
  public void buildFieldData(Connection db) throws SQLException {
    Statement st = db.createStatement();
    if (groupField != null && groupField.getId() > -1) {
      ResultSet rs = st.executeQuery(
          "SELECT * " +
          "FROM search_fields " +
          "WHERE id = " + groupField.getId());
      if (rs.next()) {
        groupField.buildRecord(rs);
      }
      rs.close();
      st.close();
    }
  }
}

