package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.CustomLookupElement;
/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class CustomLookupList extends LookupList {

  ArrayList fields = new ArrayList();


  /**
   *  Constructor for the CustomLookupList object
   */
  public CustomLookupList() {
    super();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    int items = -1;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    Iterator i = fields.iterator();
    while (i.hasNext()) {
      sql.append((String) i.next());
      if (i.hasNext()) {
        sql.append(",");
      }
      sql.append(" ");
    }
    sql.append("FROM " + tableName);
    PreparedStatement pst = db.prepareStatement(sql.toString());

    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      CustomLookupElement thisElement = new CustomLookupElement(rs);
      this.add(thisElement);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Adds a feature to the Field attribute of the CustomLookupList object
   *
   *@param  fieldName  The feature to be added to the Field attribute
   */
  public void addField(String fieldName) {
    if (fields == null) {
      fields = new ArrayList();
    }
    fields.add(fieldName);
  }

}

