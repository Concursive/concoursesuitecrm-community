/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

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
 *@author     matt rajkowski
 *@created    August 11, 2003
 *@version    $Id: PermissionCategoryLookup.java,v 1.1.2.1 2004/03/19 21:00:50
 *      rvasista Exp $
 */
public class PermissionCategoryLookup extends GenericBean {

  private int id = -1;
  private String description = null;
  private PermissionLookupList permissions = null;


  /**
   *  Constructor for the PermissionCategoryLookup object
   */
  public PermissionCategoryLookup() { }


  /**
   *  Constructor for the PermissionCategoryLookup object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public PermissionCategoryLookup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the PermissionCategoryLookup object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PermissionCategoryLookup object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the PermissionCategoryLookup object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the id attribute of the PermissionCategoryLookup object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the description attribute of the PermissionCategoryLookup object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the permissions attribute of the PermissionCategoryLookup object
   *
   *@return    The permissions value
   */
  public PermissionLookupList getPermissions() {
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
    permissions = new PermissionLookupList(db, id, includeEnabled);
  }
}

