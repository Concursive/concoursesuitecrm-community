//Copyright 2001 Dark Horse Ventures
package com.darkhorseventures.controller;

import java.util.Date;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.cfsbase.*;

/**
 *  System status maintains global values for a shared group of users. This is
 *  based on the database that the user is connecting to.<p>
 *
 *  When a user logs in, permissions and hierarchies are read in. If someone
 *  changes user or role data then the user's permissions and hierarchies will
 *  be read in during the Security Check.
 *
 *@author     mrajkowski
 *@created    October 10, 2001
 *@version    $Id$
 */
public class SystemStatus {

  Date permissionCheck = new Date();
  Date hierarchyCheck = new Date();
  UserList hierarchyList = new UserList();
  boolean hierarchyUpdating = false;

  /**
   *  Constructor for the SystemStatus object
   *
   *@since    1.1
   */
  public SystemStatus() { }


  /**
   *  Constructor for the SystemStatus object
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.3
   */
  public SystemStatus(Connection db) throws SQLException {
    buildHierarchyList(db);
  }


  /**
   *  Sets the PermissionCheck attribute of the SystemStatus object
   *
   *@param  tmp  The new PermissionCheck value
   *@since       1.1
   */
  public void setPermissionCheck(Date tmp) {
    this.permissionCheck = tmp;
  }


  /**
   *  Sets the HierarchyCheck attribute of the SystemStatus object
   *
   *@param  tmp  The new HierarchyCheck value
   *@since       1.1
   */
  public void setHierarchyCheck(Date tmp) {
    this.hierarchyCheck = tmp;
  }


  /**
   *  Gets the PermissionCheck attribute of the SystemStatus object
   *
   *@return    The PermissionCheck value
   *@since     1.1
   */
  public Date getPermissionCheck() {
    return permissionCheck;
  }


  /**
   *  Gets the HierarchyCheck attribute of the SystemStatus object
   *
   *@return    The HierarchyCheck value
   *@since     1.1
   */
  public Date getHierarchyCheck() {
    while (hierarchyUpdating) {}
    return hierarchyCheck;
  }
  
  public UserList getHierarchyList() { 
    while (hierarchyUpdating) {}
    return hierarchyList; 
  }



  /**
   *  Generates a list of all users in the system for the given database
   *  connection
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.3
   */
  public void buildHierarchyList(Connection db) throws SQLException {
    hierarchyList.clear();
    
    //Get the top level managers
    UserList tmpListA = new UserList();
    tmpListA.setBuildContact(false);
    tmpListA.setBuildHierarchy(false);
    tmpListA.setBuildPermissions(false);
    tmpListA.setTopLevel(true);
    tmpListA.buildList(db);
    
    //System.out.println("SS-> tmpListA has : " + tmpListA.size());
    
    //Get everyone
    UserList tmpListB = new UserList();
    tmpListB.setBuildContact(false);
    tmpListB.setBuildHierarchy(false);
    tmpListB.setBuildPermissions(false);
    tmpListB.setTopLevel(false);
    tmpListB.buildList(db);
    
    //System.out.println("SS-> tmpListB has : " + tmpListB.size());
    
    Iterator listA = tmpListA.iterator();
    while (listA.hasNext()) {
      User thisUser = (User)listA.next(); 
      User userToAdd = tmpListB.getTopUser(thisUser.getId());
      if (userToAdd != null) {
        hierarchyList.add(userToAdd);
        //System.out.println("SS-> Manager Added: " + thisUser.getUsername() + " " + thisUser.getId());
        this.addChildUsers(userToAdd, tmpListB);
      } else {
        hierarchyList.add(thisUser);
        //System.out.println("SS-> System User Added: " + thisUser.getUsername());
      }
    }
    
    System.out.println("SS-> Top Level Users added : " + hierarchyList.size());
    
    /* hierarchyList.clear();
    hierarchyList.setBuildContact(true);
    hierarchyList.setBuildHierarchy(true);
    hierarchyList.setBuildPermissions(false);
    hierarchyList.setTopLevel(true);
    hierarchyList.buildList(db); */
  }
  
  private void addChildUsers(User thisUser, UserList addFrom) {
    Iterator i = addFrom.iterator();
    while (i.hasNext()) {
      User tmpUser = (User)i.next();
      if (thisUser.getShortChildList() == null) {
        thisUser.setChildUsers(new UserList());
      }
      if (tmpUser.getManagerId() == thisUser.getId()) {
        //System.out.println("SS-> Found a match for : " + thisUser.getUsername());
        thisUser.getShortChildList().add(tmpUser);
        tmpUser.setManagerUser(thisUser);
        this.addChildUsers(tmpUser, addFrom);
      }
    }
  }
  
  
  public void updateHierarchy(Connection db) throws SQLException {
    java.util.Date checkDate = new java.util.Date();
    if (checkDate.after(this.getHierarchyCheck())) {
      synchronized(this) {
        hierarchyUpdating = true;
        if (checkDate.after(hierarchyCheck)) {
          try {
            this.buildHierarchyList(db);
          } catch (SQLException e) {
            hierarchyUpdating = false;
            throw e;
          }
          this.setHierarchyCheck(new java.util.Date());
        }
        hierarchyUpdating = false;
      }
    }
  }

}

