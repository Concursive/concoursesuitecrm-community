//Copyright 2001 Dark Horse Ventures

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
 *@version    $Id$
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

