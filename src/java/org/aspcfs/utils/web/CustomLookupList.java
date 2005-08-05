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
package org.aspcfs.utils.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id$
 * @created January 14, 2003
 */
public class CustomLookupList extends LookupList {

  ArrayList fields = new ArrayList();


  /**
   * Constructor for the CustomLookupList object
   */
  public CustomLookupList() {
    super();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
   * Adds a feature to the Field attribute of the CustomLookupList object
   *
   * @param fieldName The feature to be added to the Field attribute
   */
  public void addField(String fieldName) {
    if (fields == null) {
      fields = new ArrayList();
    }
    fields.add(fieldName);
  }

}

