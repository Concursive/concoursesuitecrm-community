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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Contains a list of SearchField objects
 *
 * @author Wesley S. Gillette
 * @version $Id: SearchFieldList.java,v 1.3 2002/04/24 15:39:44 mrajkowski Exp
 *          $
 * @created November 1, 2001
 */
public class SearchFieldList extends ArrayList {

  /**
   * Constructor for the SearchFieldList object
   *
   * @since 1.1
   */
  public SearchFieldList() {
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public void buildFieldList(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM search_fields " +
        "WHERE searchable = ? ");
    pst.setBoolean(1, true);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      SearchField thisSearchField = new SearchField(rs);
      this.add(thisSearchField);
    }
    rs.close();
    pst.close();
  }

  public static int queryField(Connection db, String fieldName) throws SQLException {
    int id = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT id " +
        "FROM search_fields " +
        "WHERE field = ? ");
    pst.setString(1, fieldName);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      id = rs.getInt("id");
    }
    rs.close();
    pst.close();
    return id;
  }
}

