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
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 9, 2005
 */
public class CustomListView extends GenericBean {
  private int id = -1;
  private int editorId = -1;
  private String name = null;
  private String description = null;
  private boolean isDefault = false;
  private Timestamp entered = null;
  //resources
  private boolean buildFields = false;
  private CustomListViewFieldList fieldList = null;


  /**
   *  Gets the buildFields attribute of the CustomListView object
   *
   * @return    The buildFields value
   */
  public boolean getBuildFields() {
    return buildFields;
  }


  /**
   *  Sets the buildFields attribute of the CustomListView object
   *
   * @param  tmp  The new buildFields value
   */
  public void setBuildFields(boolean tmp) {
    this.buildFields = tmp;
  }


  /**
   *  Sets the buildFields attribute of the CustomListView object
   *
   * @param  tmp  The new buildFields value
   */
  public void setBuildFields(String tmp) {
    this.buildFields = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the fieldList attribute of the CustomListView object
   *
   * @return    The fieldList value
   */
  public CustomListViewFieldList getFieldList() {
    return fieldList;
  }


  /**
   *  Sets the fieldList attribute of the CustomListView object
   *
   * @param  tmp  The new fieldList value
   */
  public void setFieldList(CustomListViewFieldList tmp) {
    this.fieldList = tmp;
  }



  /**
   *  Gets the entered attribute of the CustomListView object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the CustomListView object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the CustomListView object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }



  /**
   *  Gets the id attribute of the CustomListView object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the CustomListView object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CustomListView object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the editorId attribute of the CustomListView object
   *
   * @return    The editorId value
   */
  public int getEditorId() {
    return editorId;
  }


  /**
   *  Sets the editorId attribute of the CustomListView object
   *
   * @param  tmp  The new editorId value
   */
  public void setEditorId(int tmp) {
    this.editorId = tmp;
  }


  /**
   *  Sets the editorId attribute of the CustomListView object
   *
   * @param  tmp  The new editorId value
   */
  public void setEditorId(String tmp) {
    this.editorId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the name attribute of the CustomListView object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the CustomListView object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the description attribute of the CustomListView object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the CustomListView object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the isDefault attribute of the CustomListView object
   *
   * @return    The isDefault value
   */
  public boolean getIsDefault() {
    return isDefault;
  }


  /**
   *  Sets the isDefault attribute of the CustomListView object
   *
   * @param  tmp  The new isDefault value
   */
  public void setIsDefault(boolean tmp) {
    this.isDefault = tmp;
  }


  /**
   *  Sets the isDefault attribute of the CustomListView object
   *
   * @param  tmp  The new isDefault value
   */
  public void setIsDefault(String tmp) {
    this.isDefault = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Constructor for the CustomListView object
   */
  public CustomListView() { }


  /**
   *  Constructor for the CustomListView object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CustomListView(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the CustomListView object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CustomListView(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Custom List View ID");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT clv.* " +
        "FROM custom_list_view clv " +
        "WHERE clv.view_id = ? ");
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
    if (buildFields) {
      this.buildFields(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildFields(Connection db) throws SQLException {
    fieldList = new CustomListViewFieldList();
    fieldList.setViewId(id);
    fieldList.buildList(db);
  }


  /**
   *  Gets the fieldCount attribute of the CustomListView object
   *
   * @return    The fieldCount value
   */
  public int getFieldCount() {
    if (fieldList != null) {
      return fieldList.size();
    }
    return 0;
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("view_id");
    editorId = rs.getInt("editor_id");
    name = rs.getString("name");
    description = rs.getString("description");
    isDefault = rs.getBoolean("is_default");
    entered = rs.getTimestamp("entered");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "custom_list_view_view_id_seq");
    sql.append(
        "INSERT INTO custom_list_view (editor_id, name, description, ");
    sql.append("entered, ");
    sql.append("is_default ) ");
    sql.append("VALUES (?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("? ) ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, editorId);
    pst.setString(++i, name);
    pst.setString(++i, description);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    pst.setBoolean(++i, isDefault);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "custom_list_view_view_id_seq", id);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE custom_list_view " +
        "SET name = ? , description = ?, is_default = ? " +
        "WHERE view_id = ? ");
    int i = 0;
    pst.setString(++i, name);
    pst.setString(++i, description);
    pst.setBoolean(++i, isDefault);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }
}

