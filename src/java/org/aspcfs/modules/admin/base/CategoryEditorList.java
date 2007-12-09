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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides a list of items that have editable categories for the specified
 * module
 *
 * @author matt rajkowski
 * @version $Id$
 * @created February 3, 2004
 */
public class CategoryEditorList extends ArrayList {
  public final static String tableName = "category_editor_lookup";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  protected int moduleId = -1;


  /**
   * Constructor for the CategoryEditorList object
   */
  public CategoryEditorList() {
  }

  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionItemList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the ActionItemList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionItemList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * Sets the moduleId attribute of the CategoryEditorList object
   *
   * @param moduleId The new moduleId value
   */
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }


  /**
   * Sets the moduleId attribute of the CategoryEditorList object
   *
   * @param moduleId The new moduleId value
   */
  public void setModuleId(String moduleId) {
    this.moduleId = Integer.parseInt(moduleId);
  }


  /**
   * Gets the moduleId attribute of the CategoryEditorList object
   *
   * @return The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT * " +
            "FROM category_editor_lookup " +
            ((moduleId > -1) ? "WHERE module_id = ? " : "") +
            "ORDER BY " + DatabaseUtils.addQuotes(db, "level") + " ");
    if (moduleId > -1) {
      pst.setInt(1, moduleId);
    }
    rs = pst.executeQuery();
    while (rs.next()) {
      CategoryEditor thisEditor = new CategoryEditor(rs);
      this.add(thisEditor);
    }
    rs.close();
    pst.close();
  }
}
