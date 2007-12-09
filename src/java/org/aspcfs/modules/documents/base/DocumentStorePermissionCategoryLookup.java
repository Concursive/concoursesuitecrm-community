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
package org.aspcfs.modules.documents.base;

import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.aspcfs.utils.DatabaseUtils;

/**
 * Description of the Class
 *
 * @author
 * @version $Id$
 * @created
 */
public class DocumentStorePermissionCategoryLookup extends GenericBean {

  private int id = -1;
  private String description = null;
  private boolean defaultItem = false;
  private int level = -1;
  private boolean enabled = false;
  private DocumentStorePermissionLookupList permissions = null;


  /**
   * Constructor for the DocumentStorePermissionCategoryLookup object
   */
  public DocumentStorePermissionCategoryLookup() {
  }


  /**
   * Constructor for the DocumentStorePermissionCategoryLookup object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public DocumentStorePermissionCategoryLookup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the DocumentStorePermissionCategoryLookup object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the DocumentStorePermissionCategoryLookup object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the description attribute of the DocumentStorePermissionCategoryLookup object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the id attribute of the DocumentStorePermissionCategoryLookup object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the description attribute of the DocumentStorePermissionCategoryLookup object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }

  public boolean getDefaultItem() {
    return defaultItem;
  }

  public void setDefaultItem(boolean defaultItem) {
    this.defaultItem = defaultItem;
  }

  public void setDefaultItem(String defaultItem) {
    this.defaultItem = DatabaseUtils.parseBoolean(defaultItem);
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setLevel(String level) {
    this.level = Integer.parseInt(level);
  }

  public boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = DatabaseUtils.parseBoolean(enabled);
  }

  /**
   * Gets the permissions attribute of the DocumentStorePermissionCategoryLookup object
   *
   * @return The permissions value
   */
  public DocumentStorePermissionLookupList getPermissions() {
    return permissions;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
  }


  /**
   * Description of the Method
   *
   * @param db             Description of the Parameter
   * @param includeEnabled Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(Connection db, int includeEnabled) throws SQLException {
    permissions = new DocumentStorePermissionLookupList(
        db, id, includeEnabled);
  }

  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "lookup_document_store_permission_category_code_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO " + DatabaseUtils.getTableName(db, "lookup_document_store_permission_category") + " " +
            "(" + (id > -1 ? "code, " : "") + "description, default_item, " + DatabaseUtils.addQuotes(db, "level")+ ", enabled) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?)"
    );
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, description);
    pst.setBoolean(++i, defaultItem);
    pst.setInt(++i, level);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "lookup_document_store_permission_category_code_seq", id);
    return true;
  }
}

