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
package org.aspcfs.modules.documents.base;

import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author
 * @version $Id$
 * @created
 */
public class DocumentStorePermissionLookup extends GenericBean {

  private int id = -1;
  private int categoryId = -1;
  private String permission = null;
  private String description = null;
  private int defaultRole = -1;


  /**
   * Constructor for the DocumentStorePermissionLookup object
   */
  public DocumentStorePermissionLookup() {
  }


  /**
   * Constructor for the DocumentStorePermissionLookup object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public DocumentStorePermissionLookup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the permission attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   * Sets the description attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the defaultRole attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new defaultRole value
   */
  public void setDefaultRole(int tmp) {
    this.defaultRole = tmp;
  }


  /**
   * Sets the defaultRole attribute of the DocumentStorePermissionLookup object
   *
   * @param tmp The new defaultRole value
   */
  public void setDefaultRole(String tmp) {
    this.defaultRole = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the DocumentStorePermissionLookup object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the categoryId attribute of the DocumentStorePermissionLookup object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the permission attribute of the DocumentStorePermissionLookup object
   *
   * @return The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   * Gets the description attribute of the DocumentStorePermissionLookup object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the defaultRole attribute of the DocumentStorePermissionLookup object
   *
   * @return The defaultRole value
   */
  public int getDefaultRole() {
    return defaultRole;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("code");
    categoryId = rs.getInt("category_id");
    permission = rs.getString("permission");
    description = rs.getString("description");
    defaultRole = rs.getInt("default_role");
  }
}

