package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.controller.*;

/**
 *  User Session record -- maintained while the user is logged in
 *
 *@author     matt
 *@created    September 21, 2001
 *@version    $Id$
 */
public class UserBean extends GenericBean {
  private int userId = -1;
  
  private SystemStatus systemStatus = null;
  private User userRecord = null;
  private String browserType = "";
  private String template = null;
  private String cssFile = "";
  private java.util.Date permissionCheck = new java.util.Date();
  private java.util.Date hierarchyCheck = new java.util.Date();
  private String idRange = "";


  /**
   *  Builds the userbean from the passed in User Record to speed up the login
   *
   *@param  thisUser  Description of Parameter
   *@since            1.10
   */
  public UserBean(SystemStatus thisSystem, int newUserId) {
    if (thisSystem == null) { System.out.println("UserBean -> SystemStatus is null error"); }
    systemStatus = thisSystem;
    userId = newUserId;
    updateUserRecord();
  }


  /**
   *  Sets the BrowserType attribute of the User object
   *
   *@param  tmp  The new BrowserType value
   *@since       1.14
   */
  public void setBrowserType(String tmp) {
    this.browserType = tmp;
  }


  /**
   *  Sets the CssFile attribute of the userBean object
   *
   *@param  tmp  The new CssFile value
   *@since       1.14
   */
  public void setCssFile(String tmp) {
    this.cssFile = tmp;
  }


  /**
   *  Sets the Template attribute of the User object
   *
   *@param  tmp  The new Template value
   *@since       1.14
   */
  public void setTemplate(String tmp) {
    this.template = tmp;
  }


  /**
   *  Sets the PermissionCheck attribute of the User object
   *
   *@param  tmp  The new PermissionCheck value
   *@since       1.12
   */
  public void setPermissionCheck(java.util.Date tmp) {
    this.permissionCheck = tmp;
  }


  /**
   *  Sets the HierarchyCheck attribute of the User object
   *
   *@param  tmp  The new HierarchyCheck value
   *@since       1.12
   */
  public void setHierarchyCheck(java.util.Date tmp) {
    this.hierarchyCheck = tmp;
  }


  /**
   *  Sets the User attribute of the User object, added to support the
   *  SecurityCheck
   *
   *@param  tmp  The new User value
   *@since       1.13
   */
  public void setUserRecord(User tmp) {
    this.userRecord = tmp;
  }



  /**
   *  Gets the User attribute of the User object
   *
   *@return    The User value
   *@since     1.10
   */
  public User getUserRecord() {
    if (userRecord == null) {
      updateUserRecord();
    }
    return userRecord;
  }



  /**
   *  Returns the user's identifier
   *
   *@return    String
   *@since     1.14
   */
  public String getUsername() {
    return userRecord.getUsername();
  }


  /**
   *  Gets the UserId attribute of the userBean object
   *
   *@return    The UserId value
   *@since     1.14
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the BrowserType attribute of the User object
   *
   *@return    The BrowserType value
   *@since     1.14
   */
  public String getBrowserType() {
    return browserType;
  }


  /**
   *  Gets the Role attribute of the userBean object
   *
   *@return    The Role value
   *@since     1.14
   */
  public int getRoleId() {
    return getUserRecord().getRoleId();
  }


  /**
   *  Gets the Role attribute of the User object
   *
   *@return    The Role value
   *@since     1.9
   */
  public String getRole() {
    return getUserRecord().getRole();
  }


  /**
   *  Gets the CssFile attribute of the userBean object
   *
   *@return    The CssFile value
   *@since     1.14
   */
  public String getCssFile() {
    return cssFile;
  }


  /**
   *  Gets the FirstName attribute of the User object
   *
   *@return    The FirstName value
   *@since     1.14
   */
  public String getNameFirst() {
    return getUserRecord().getContact().getNameFirst();
  }


  /**
   *  Gets the LastName attribute of the User object
   *
   *@return    The LastName value
   *@since     1.14
   */
  public String getNameLast() {
    return getUserRecord().getContact().getNameLast();
  }


  /**
   *  Gets the Template attribute of the User object
   *
   *@return    The Template value
   *@since     1.14
   */
  public String getTemplate() {
    return template;
  }


  /**
   *  Gets the comma separated IdRange attribute of the User object. Each id
   *  represents a user id in which this user can access data for, including the
   *  user's own id.
   *
   *@return    The comma separated IdRange attribute
   *@since     1.12
   */
  public String getIdRange() {
    return idRange;
  }


  /**
   *  Gets the PermissionCheck attribute of the User object
   *
   *@return    The PermissionCheck value
   *@since     1.12
   */
  public java.util.Date getPermissionCheck() {
    return permissionCheck;
  }


  /**
   *  Gets the HierarchyCheck attribute of the User object
   *
   *@return    The HierarchyCheck value
   *@since     1.12
   */
  public java.util.Date getHierarchyCheck() {
    return hierarchyCheck;
  }


  /**
   *  Method that returns whether the user has the specified permission.
   *
   *@param  thisPermission  Description of Parameter
   *@return                 Description of the Returned Value
   *@since                  1.14
   */
  public boolean hasPermission(String thisPermission) {
    return (getUserRecord().hasPermission(thisPermission));
  }
  
  public void updateUserRecord() {
    System.out.println("UserBean-> Check 0");
    userRecord = systemStatus.getHierarchyList().getUser(userId);
    System.out.println("UserBean-> Check 1");
    UserList shortChildList = userRecord.getShortChildList();
    System.out.println("UserBean-> Check 2");
    UserList fullChildList = userRecord.getFullChildList(shortChildList, new UserList());
    System.out.println("UserBean-> Check 3");
    idRange = fullChildList.getUserListIds(userRecord.getId());
    System.out.println("UserBean-> Check 4");
  }
}

