//Copyright 2001 Dark Horse Ventures
package com.darkhorseventures.controller;

import java.util.Date;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import org.w3c.dom.Element;

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
  ArrayList ignoredFields = new ArrayList();
  Hashtable fieldLabels = new Hashtable();
  ObjectHookList hooks = new ObjectHookList();


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
    buildPreferences(db);
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
    while (hierarchyUpdating) {
    }
    return hierarchyCheck;
  }


  /**
   *  Gets the hierarchyList attribute of the SystemStatus object
   *
   *@return    The hierarchyList value
   */
  public UserList getHierarchyList() {
    while (hierarchyUpdating) {
    }
    return hierarchyList;
  }


  /**
   *  Gets the label attribute of the SystemStatus object
   *
   *@param  thisLabel  Description of Parameter
   *@return            The label value
   */
  public String getLabel(String thisLabel) {
    return ((String) fieldLabels.get(thisLabel));
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
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildHierarchyList: A");
    }
    UserList tmpListA = new UserList();
    tmpListA.setBuildContact(false);
    tmpListA.setBuildHierarchy(false);
    tmpListA.setBuildPermissions(false);
    tmpListA.setTopLevel(true);
    tmpListA.buildList(db);

    //Get everyone
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildHierarchyList: B");
    }
    UserList tmpListB = new UserList();
    tmpListB.setBuildContact(false);
    tmpListB.setBuildHierarchy(false);
    tmpListB.setBuildPermissions(false);
    tmpListB.setTopLevel(false);
    tmpListB.buildList(db);

    //Combine the lists
    Iterator listA = tmpListA.iterator();
    while (listA.hasNext()) {
      User thisUser = (User) listA.next();
      User userToAdd = tmpListB.getTopUser(thisUser.getId());
      if (userToAdd != null) {
        hierarchyList.add(userToAdd);
        //System.out.println("SystemStatus-> Manager Added: " + thisUser.getUsername() + " " + thisUser.getId());
        this.addChildUsers(userToAdd, tmpListB);
      } else {
        hierarchyList.add(thisUser);
        //System.out.println("SystemStatus-> System User Added: " + thisUser.getUsername());
      }
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> Top Level Users added : " + hierarchyList.size());
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void updateHierarchy(Connection db) throws SQLException {
    java.util.Date checkDate = new java.util.Date();
    if (checkDate.after(this.getHierarchyCheck())) {
      synchronized (this) {
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


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildPreferences(Connection db) throws SQLException {
    String fieldsToIgnore = null;
    String labelsToUse = null;
    String hookData = null;
    PreparedStatement pst = db.prepareStatement(
        "SELECT category, data " +
        "FROM system_prefs ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      String category = rs.getString("category");
      if ("ignore fields".equals(category)) {
        fieldsToIgnore = rs.getString("data");
      } else if ("field labels".equals(category)) {
        labelsToUse = rs.getString("data");
      } else if ("hooks".equals(category)) {
        hookData = rs.getString("data");
      } else {
        String tmp = rs.getString("data");
      }
    }
    rs.close();
    pst.close();

    ignoredFields.clear();
    if (fieldsToIgnore != null) {
      try {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("SystemStatus-> Adding ignored fields (temporary solution)");
        }
        XMLUtils xml = new XMLUtils(fieldsToIgnore);
        xml.getAllChildrenText(xml.getDocumentElement(), "ignore", ignoredFields);
      } catch (Exception e) {
        System.out.println("SystemStatus-> Error: " + e.getMessage());
      }
    }
    
    fieldLabels.clear();
    if (labelsToUse != null) {
      try {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("SystemStatus-> Adding field labels (temporary solution)");
        }
        XMLUtils xml = new XMLUtils(labelsToUse);
        ArrayList fieldElements = new ArrayList();
        xml.getAllChildren(xml.getDocumentElement(), "label", fieldElements);
        Iterator elements = fieldElements.iterator();
        while (elements.hasNext()) {
          Element thisElement = (Element) elements.next();
          String replace = xml.getNodeText(xml.getFirstChild(thisElement, "replace"));
          String with = xml.getNodeText(xml.getFirstChild(thisElement, "with"));
          if (System.getProperty("DEBUG") != null) {
            System.out.println("SystemStatus-> Replace " + replace + " with " + with);
          }
          fieldLabels.put(replace, with);
        }
      } catch (Exception e) {
        System.out.println("SystemStatus-> Error: " + e.getMessage());
      }
    }

    hooks.clear();
    hooks.parse(hookData);
  }


  /**
   *  Description of the Method
   *
   *@param  thisField  Description of Parameter
   *@return            Description of the Returned Value
   */
  public boolean hasField(String thisField) {
    return ignoredFields.contains(thisField);
  }


  /**
   *  Adds a feature to the ChildUsers attribute of the SystemStatus object
   *
   *@param  thisUser  The feature to be added to the ChildUsers attribute
   *@param  addFrom   The feature to be added to the ChildUsers attribute
   */
  private void addChildUsers(User thisUser, UserList addFrom) {
    Iterator i = addFrom.iterator();
    while (i.hasNext()) {
      User tmpUser = (User) i.next();
      if (thisUser.getShortChildList() == null) {
        thisUser.setChildUsers(new UserList());
      }
      if (tmpUser.getManagerId() == thisUser.getId()) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("SystemStatus-> Found a match for : " + thisUser.getUsername());
        }
        thisUser.getShortChildList().add(tmpUser);
        tmpUser.setManagerUser(thisUser);
        this.addChildUsers(tmpUser, addFrom);
      }
    }
  }


  public boolean hasHook(Object object) throws java.lang.ClassNotFoundException {
    return (hooks.has(object));
  }
  
  public boolean processHook(Object object, Connection db) {
    return (hooks.process(object, db));
  }

}

