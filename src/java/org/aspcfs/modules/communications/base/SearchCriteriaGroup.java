//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.sql.*;

/**
 *  A group contains SearchCriteriaElements which provide the search criteria
 *  for a query.
 *
 *@author     mrajkowski
 *@created    November 13, 2001
 *@version    $Id$
 */
public class SearchCriteriaGroup extends Vector {

  SearchField groupField = new SearchField();


  /**
   *  Constructor for the SearchCriteriaGroup object
   *
   *@since    1.1
   */
  public SearchCriteriaGroup() { }


  /**
   *  Sets the GroupField attribute of the SearchCriteriaGroup object
   *
   *@param  tmp  The new GroupField value
   *@since       1.1
   */
  public void setGroupField(SearchField tmp) {
    this.groupField = tmp;
  }


  /**
   *  Sets the Id attribute of the SearchCriteriaGroup object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(int tmp) {
    groupField.setId(tmp);
  }


  /**
   *  Gets the GroupField attribute of the SearchCriteriaGroup object
   *
   *@return    The GroupField value
   *@since     1.1
   */
  public SearchField getGroupField() {
    return groupField;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public void buildFieldData(Connection db) throws SQLException {
    Statement st = db.createStatement();
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

