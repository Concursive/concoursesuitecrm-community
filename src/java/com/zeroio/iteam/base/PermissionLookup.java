/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.beans.*;
import org.aspcfs.utils.DatabaseUtils;
import java.sql.*;
import java.text.*;
import java.util.Calendar;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    August 11, 2003
 *@version    $Id: PermissionLookup.java,v 1.1.2.1 2004/03/19 21:00:50 rvasista
 *      Exp $
 */
public class PermissionLookup extends GenericBean {

  private int id = -1;
  private int categoryId = -1;
  private String permission = null;
  private String description = null;
  private int defaultRole = -1;


  /**
   *  Constructor for the PermissionLookup object
   */
  public PermissionLookup() { }


  /**
   *  Constructor for the PermissionLookup object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public PermissionLookup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the PermissionLookup object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PermissionLookup object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryId attribute of the PermissionLookup object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the PermissionLookup object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the permission attribute of the PermissionLookup object
   *
   *@param  tmp  The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   *  Sets the description attribute of the PermissionLookup object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the defaultRole attribute of the PermissionLookup object
   *
   *@param  tmp  The new defaultRole value
   */
  public void setDefaultRole(int tmp) {
    this.defaultRole = tmp;
  }


  /**
   *  Sets the defaultRole attribute of the PermissionLookup object
   *
   *@param  tmp  The new defaultRole value
   */
  public void setDefaultRole(String tmp) {
    this.defaultRole = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the PermissionLookup object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the categoryId attribute of the PermissionLookup object
   *
   *@return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the permission attribute of the PermissionLookup object
   *
   *@return    The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   *  Gets the description attribute of the PermissionLookup object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the defaultRole attribute of the PermissionLookup object
   *
   *@return    The defaultRole value
   */
  public int getDefaultRole() {
    return defaultRole;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("code");
    categoryId = rs.getInt("category_id");
    permission = rs.getString("permission");
    description = rs.getString("description");
    defaultRole = rs.getInt("default_role");
  }
}

