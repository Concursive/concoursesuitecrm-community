//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import java.util.ArrayList;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Contains a list of SearchField objects
 *
 *@author     Wesley S. Gillette
 *@created    November 1, 2001
 *@version    $Id: SearchFieldList.java,v 1.3 2002/04/24 15:39:44 mrajkowski Exp
 *      $
 */
public class SearchFieldList extends ArrayList {

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
      this.add(thisSearchField);
    }
    rs.close();
    st.close();
  }
}

