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

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;
import java.sql.*;
import java.text.*;
import java.util.Calendar;
import org.aspcfs.modules.actions.*;

/**
 *  Description of the Class
 *
 *@author     
 *@created    
 *@version    $Id$
 */
public class DocumentStorePermissionCategoryLookup extends GenericBean {

  private int id = -1;
  private String description = null;
  private DocumentStorePermissionLookupList permissions = null;


  /**
   *  Constructor for the DocumentStorePermissionCategoryLookup object
   */
  public DocumentStorePermissionCategoryLookup() { }


  /**
   *  Constructor for the DocumentStorePermissionCategoryLookup object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public DocumentStorePermissionCategoryLookup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the DocumentStorePermissionCategoryLookup object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the DocumentStorePermissionCategoryLookup object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the DocumentStorePermissionCategoryLookup object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the id attribute of the DocumentStorePermissionCategoryLookup object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the description attribute of the DocumentStorePermissionCategoryLookup object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the permissions attribute of the DocumentStorePermissionCategoryLookup object
   *
   *@return    The permissions value
   */
  public DocumentStorePermissionLookupList getPermissions() {
    return permissions;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  includeEnabled    Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db, int includeEnabled) throws SQLException {
    permissions = new DocumentStorePermissionLookupList(db, id, includeEnabled);
  }
}

