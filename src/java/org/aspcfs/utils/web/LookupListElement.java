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

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactType;
import org.aspcfs.modules.contacts.base.ContactTypeList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Represents an item from a LookupListLookup table.
 *
 * @author     Mathur
 * @created    December 18, 2002
 * @version    $Id: LookupListElement.java,v 1.2 2003/01/10 16:17:48 mrajkowski
 *      Exp $
 */

public class LookupListElement {

  protected int id = -1;
  protected int moduleId = -1;
  protected int categoryId = -1;
  protected int lookupId = -1;
  protected String tableName = null;
  protected String className = null;
  protected int level = 0;
  protected String description = "";
  protected java.sql.Timestamp entered = null;
  protected LookupList lookupList = null;


  /**
   *  Constructor for the LookupListElement object
   *
   * @param  db                Description of the Parameter
   * @param  moduleId          Description of the Parameter
   * @param  lookupId          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public LookupListElement(Connection db, int moduleId, int lookupId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM lookup_lists_lookup " +
        "WHERE module_id = ? " +
        "AND lookup_id = ? ");
    pst.setInt(1, moduleId);
    pst.setInt(2, lookupId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Constructor for the LookupListElement object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public LookupListElement(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the LookupListElement object
   */
  public LookupListElement() { }


  /**
   *  Gets the id attribute of the LookupListElement object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the moduleId attribute of the LookupListElement object
   *
   * @param  moduleId  The new moduleId value
   */
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }


  /**
   *  Sets the categoryId attribute of the LookupListElement object
   *
   * @param  categoryId  The new categoryId value
   */
  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }


  /**
   *  Sets the lookupId attribute of the LookupListElement object
   *
   * @param  lookupId  The new lookupId value
   */
  public void setLookupId(int lookupId) {
    this.lookupId = lookupId;
  }


  /**
   *  Sets the tableName attribute of the LookupListElement object
   *
   * @param  tableName  The new tableName value
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }


  /**
   *  Sets the level attribute of the LookupListElement object
   *
   * @param  level  The new level value
   */
  public void setLevel(int level) {
    this.level = level;
  }


  /**
   *  Sets the description attribute of the LookupListElement object
   *
   * @param  description  The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   *  Sets the entered attribute of the LookupListElement object
   *
   * @param  entered  The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   *  Sets the lookupList attribute of the LookupListElement object
   *
   * @param  lookupList  The new lookupList value
   */
  public void setLookupList(LookupList lookupList) {
    this.lookupList = lookupList;
  }


  /**
   *  Sets the className attribute of the LookupListElement object
   *
   * @param  className  The new className value
   */
  public void setClassName(String className) {
    this.className = className;
  }


  /**
   *  Gets the className attribute of the LookupListElement object
   *
   * @return    The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   *  Gets the lookupList attribute of the LookupListElement object
   *
   * @return    The lookupList value
   */
  public LookupList getLookupList() {
    return lookupList;
  }


  /**
   *  Gets the moduleId attribute of the LookupListElement object
   *
   * @return    The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   *  Gets the categoryId attribute of the LookupListElement object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the lookupId attribute of the LookupListElement object
   *
   * @return    The lookupId value
   */
  public int getLookupId() {
    return lookupId;
  }


  /**
   *  Gets the tableName attribute of the LookupListElement object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the level attribute of the LookupListElement object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the description attribute of the LookupListElement object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the entered attribute of the LookupListElement object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Builds the lookupList for a module
   *
   * @param  db             Description of the Parameter
   * @param  userId         Description of the Parameter
   * @param  thisSystem     Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildLookupList(SystemStatus thisSystem, Connection db, int userId) throws SQLException {
    if (className.equals("lookupList")) {
      setLookupList(new LookupList(db, getTableName()));
    } else if (className.equals("contactType")) {
      ContactTypeList contactTypeList = new ContactTypeList();
      contactTypeList.setIncludeDefinedByUser(userId);
      contactTypeList.setCategory(
          categoryId == Constants.ACCOUNTS ? ContactType.ACCOUNT : ContactType.GENERAL);
      contactTypeList.buildList(db);
      setLookupList(contactTypeList.getLookupList(thisSystem, "list", 0));
    } else {
      throw new SQLException(
          "LookupListElement class name not found: " + className);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    moduleId = rs.getInt("module_id");
    lookupId = rs.getInt("lookup_id");
    className = rs.getString("class_name");
    tableName = rs.getString("table_name");
    level = rs.getInt("level");
    description = rs.getString("description");
    entered = rs.getTimestamp("entered");
    categoryId = rs.getInt("category_id");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "lookup_lists_lookup_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO lookup_lists_lookup " +
        "(" + (id > -1 ? "id, " : "") + "module_id, lookup_id, class_name, table_name, \"level\", description, category_id) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, moduleId);
    pst.setInt(++i, lookupId);
    pst.setString(++i, className);
    pst.setString(++i, tableName);
    pst.setInt(++i, level);
    pst.setString(++i, description);
    pst.setInt(++i, categoryId);
    pst.executeUpdate();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "lookup_lists_lookup_id_seq", id);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM lookup_lists_lookup " +
        "WHERE id = ? ");
    pst.setInt(1, id);
    pst.execute();
    pst.close();
  }
}

