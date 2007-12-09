/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created November 14, 2005
 */
public class CustomListViewField extends GenericBean {
  private int id = -1;
  private int viewId = -1;
  private String name = null;


  /**
   * Gets the id attribute of the CustomListViewField object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the CustomListViewField object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CustomListViewField object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the viewId attribute of the CustomListViewField object
   *
   * @return The viewId value
   */
  public int getViewId() {
    return viewId;
  }


  /**
   * Sets the viewId attribute of the CustomListViewField object
   *
   * @param tmp The new viewId value
   */
  public void setViewId(int tmp) {
    this.viewId = tmp;
  }


  /**
   * Sets the viewId attribute of the CustomListViewField object
   *
   * @param tmp The new viewId value
   */
  public void setViewId(String tmp) {
    this.viewId = Integer.parseInt(tmp);
  }


  /**
   * Gets the name attribute of the CustomListViewField object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Sets the name attribute of the CustomListViewField object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Constructor for the CustomListViewField object
   */
  public CustomListViewField() { }


  /**
   * Constructor for the CustomListViewField object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomListViewField(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the CustomListViewField object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomListViewField(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the CustomListViewField object
   *
   * @param n Description of the Parameter
   */
  public CustomListViewField(Node n) {
    String name = ((Element) n).getAttribute("name");
    if (!"".equals(StringUtils.toString(name))) {
      this.setName(name);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Custom List View Field ID");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT clvf.* " +
            "FROM custom_list_view_field clvf " +
            "WHERE clvf.field_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("field_id");
    viewId = rs.getInt("view_id");
    name = rs.getString("name");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "custom_list_view_field_field_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO custom_list_view_field (" + (id > -1 ? "field_id, " : "") + "view_id, name) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?) "
    );
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, viewId);
    pst.setString(++i, name);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "custom_list_view_field_field_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE custom_list_view_field " +
            "SET name = ? " +
            "WHERE field_id = ? ");
    int i = 0;
    pst.setString(++i, name);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   * Gets the valid attribute of the CustomListViewField object
   *
   * @return The valid value
   */
  public boolean isValid() {
    //TODO: implement this method
    return true;
  }

}

