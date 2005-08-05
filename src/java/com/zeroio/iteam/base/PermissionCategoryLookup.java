/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: PermissionCategoryLookup.java,v 1.1.2.1 2004/03/19 21:00:50
 *          rvasista Exp $
 * @created August 11, 2003
 */
public class PermissionCategoryLookup extends GenericBean {

  private int id = -1;
  private String description = null;
  private PermissionLookupList permissions = null;


  /**
   * Constructor for the PermissionCategoryLookup object
   */
  public PermissionCategoryLookup() {
  }


  /**
   * Constructor for the PermissionCategoryLookup object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public PermissionCategoryLookup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the PermissionCategoryLookup object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the PermissionCategoryLookup object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the description attribute of the PermissionCategoryLookup object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the id attribute of the PermissionCategoryLookup object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the description attribute of the PermissionCategoryLookup object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the permissions attribute of the PermissionCategoryLookup object
   *
   * @return The permissions value
   */
  public PermissionLookupList getPermissions() {
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
    permissions = new PermissionLookupList(db, id, includeEnabled);
  }
}

