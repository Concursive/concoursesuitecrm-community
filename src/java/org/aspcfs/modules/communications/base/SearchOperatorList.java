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

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Description of the Class
 *
 *@author     Wesley S. Gillette
 *@created    November 1, 2001
 *@version    $Id: SearchOperatorList.java,v 1.6 2003/03/07 14:13:39 mrajkowski
 *      Exp $
 */
public class SearchOperatorList extends ArrayList {

  /**
   *  Constructor for the SearchOperatorList object
   *
   *@since
   */
  public SearchOperatorList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  typeID            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
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

