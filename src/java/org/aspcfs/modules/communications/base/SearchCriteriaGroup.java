//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import java.util.ArrayList;
import java.sql.*;


/**
 *  A group contains SearchCriteriaElements which specify the search criteria
 *  for a query.
 *
 *@author     mrajkowski
 *@created    November 13, 2001
 *@version    $Id: SearchCriteriaGroup.java,v 1.2 2002/04/24 15:39:44 mrajkowski
 *      Exp $
 */
public class SearchCriteriaGroup extends ArrayList {

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
   *  Retrieves descriptor information from the field_types table that pertains
   *  to the operator that is associated with the object's SearchField
   *
   *@param  db                db connection
   *@exception  SQLException  SQL Exception
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

