//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Contains a list of SearchField objects
 *
 *@author     Wesley S. Gillette
 *@created    November 1, 2001
 *@version    $Id$
 */
public class SearchFieldList extends Vector {

  /**
   *  Constructor for the SearchFieldList object
   *
   *@since    1.1
   */
  public SearchFieldList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public void buildFieldList(Connection db) throws SQLException {
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT * " +
        "FROM search_fields " +
        "WHERE searchable = " + DatabaseUtils.getTrue(db) + " ");
    while (rs.next()) {
      SearchField thisSearchField = new SearchField(rs);
      this.addElement(thisSearchField);
    }
    rs.close();
    st.close();
  }
}

