package com.darkhorseventures.framework.servlets;

import java.util.Date;
import java.util.*;
import java.sql.*;
import org.w3c.dom.Element;
import com.darkhorseventures.database.*;
import org.aspcfs.framework.actions.*;
import org.aspcfs.utils.web.LookupList;

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
  //Unique to this system
  private ConnectionElement connectionElement = null;
  private String fileLibraryPath = null;
  
  //Role permission cache
  private Date permissionCheck = new Date();
  private Hashtable rolePermissions = new Hashtable();
  private boolean permissionUpdating = false;
  
  //User list cache
  private Date hierarchyCheck = new Date();
  private UserList hierarchyList = new UserList();
  private Hashtable userList = new Hashtable();
  private boolean hierarchyUpdating = false;
  
  //Cached lookup tables
  private Hashtable lookups = new Hashtable();
  
  //Site Preferences
  private ArrayList ignoredFields = new ArrayList();
  private Hashtable fieldLabels = new Hashtable();
  private int sessionTimeout = 5400;
  
  //Object Hook to Workflow Manager 
  private ObjectHookManager hookManager = new ObjectHookManager();
  
  //Session Manager
  private SessionManager sessionManager = new SessionManager();


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
    queryRecord(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db) throws SQLException {
    buildHierarchyList(db);
    buildPreferences(db);
    buildRolePermissions(db);
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
   *  Sets the connectionElement attribute of the SystemStatus object
   *
   *@param  tmp  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }


  /**
   *  Sets the fileLibraryPath attribute of the SystemStatus object
   *
   *@param  tmp  The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   *  SessionManager manages the sessions active in the system
   *
   *@param  sessionManager  The new sessionManager value
   */
  public void setSessionManager(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }


  /**
   *  Sets the sessionTimeout attribute of the SystemStatus object
   *
   *@param  sessionTimeout  The new sessionTimeout value
   */
  public void setSessionTimeout(int sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }


  /**
   *  Gets the sessionTimeout attribute of the SystemStatus object
   *
   *@return    The sessionTimeout value
   */
  public int getSessionTimeout() {
    return sessionTimeout;
  }


  /**
   *  Gets the sessionManager attribute of the SystemStatus object
   *
   *@return    The sessionManager value
   */
  public SessionManager getSessionManager() {
    return sessionManager;
  }


  /**
   *  Gets the PermissionCheck attribute of the SystemStatus object
   *
   *@return    The PermissionCheck value
   *@since     1.1
   */
  public Date getPermissionCheck() {
    while (permissionUpdating) {
    }
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
   *  Gets the connectionElement attribute of the SystemStatus object
   *
   *@return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the fileLibraryPath attribute of the SystemStatus object
   *
   *@return    The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   *  Gets the hookManager attribute of the SystemStatus object
   *
   *@return    The hookManager value
   */
  public ObjectHookManager getHookManager() {
    return hookManager;
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
    userList.clear();
    
    //Get the top level managers
    UserList tmpListA = new UserList();
    tmpListA.setBuildContact(false);
    tmpListA.setBuildHierarchy(false);
    tmpListA.setTopLevel(true);
    tmpListA.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildHierarchyList: A " + tmpListA.size());
    }

    //Get everyone
    UserList tmpListB = new UserList();
    tmpListB.setBuildContact(false);
    tmpListB.setBuildHierarchy(false);
    tmpListB.setTopLevel(false);
    tmpListB.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildHierarchyList: B " + tmpListB.size());
    }

    //Combine the lists
    Iterator listA = tmpListA.iterator();
    while (listA.hasNext()) {
      User thisUser = (User) listA.next();
      User userToAdd = tmpListB.getTopUser(thisUser.getId());
      if (userToAdd != null) {
        hierarchyList.add(userToAdd);
        userList.put(new Integer(userToAdd.getId()), userToAdd);
        this.addChildUsers(userToAdd, tmpListB);
      } else {
        hierarchyList.add(thisUser);
        userList.put(new Integer(thisUser.getId()), thisUser);
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
  
  public void updateRolePermissions(Connection db) throws SQLException {
    java.util.Date checkDate = new java.util.Date();
    if (checkDate.after(this.getPermissionCheck())) {
      synchronized (this) {
        permissionUpdating = true;
        if (checkDate.after(permissionCheck)) {
          try {
            this.buildRolePermissions(db);
          } catch (SQLException e) {
            permissionUpdating = false;
            throw e;
          }
          this.setPermissionCheck(new java.util.Date());
        }
        permissionUpdating = false;
      }
    }
  }


  /**
   *  This method loads all of the preference data for this system.
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
        "FROM system_prefs " +
        "WHERE enabled = ? ");
    pst.setBoolean(1, true);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      String category = rs.getString("category");
      if ("system.fields.ignore".equals(category)) {
        fieldsToIgnore = rs.getString("data");
      } else if ("system.fields.labels".equals(category)) {
        labelsToUse = rs.getString("data");
      } else if ("system.objects.hooks".equals(category)) {
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
          System.out.println("SystemStatus-> Adding ignored fields");
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

    hookManager.setFileLibraryPath(fileLibraryPath);
    hookManager.initializeObjectHookList(hookData);
    hookManager.initializeBusinessProcessList(hookData);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRolePermissions(Connection db) throws SQLException {
    rolePermissions.clear();
    RoleList roles = new RoleList();
    roles.buildList(db);
    Iterator roleIterator = roles.iterator();
    while (roleIterator.hasNext()) {
      Role thisRole = (Role) roleIterator.next();
      ArrayList permissions = new ArrayList();
      UserPermissionList permissionList = new UserPermissionList(db, thisRole.getId());
      Iterator i = permissionList.iterator();
      while (i.hasNext()) {
        Permission thisPermission = (Permission) i.next();
        if (thisPermission.getAdd()) {
          permissions.add(thisPermission.getName() + "-add");
        }
        if (thisPermission.getView()) {
          permissions.add(thisPermission.getName() + "-view");
        }
        if (thisPermission.getEdit()) {
          permissions.add(thisPermission.getName() + "-edit");
        }
        if (thisPermission.getDelete()) {
          permissions.add(thisPermission.getName() + "-delete");
        }
      }
      rolePermissions.put(new Integer(thisRole.getId()), permissions);
    }
  }


  /**
   *  Builds the lookupList on demand and caches it in the lookups HashTable.
   *
   *@param  db                Description of the Parameter
   *@param  listName          DB Table name.
   *@return                   The lookupList value
   *@exception  SQLException  Description of the Exception
   */
  public LookupList getLookupList(Connection db, String listName) throws SQLException {
    if (!(lookups.containsKey(listName))) {
      synchronized (this) {
        if (!(lookups.containsKey(listName))) {
          lookups.put(listName, new LookupList(db, listName));
          if (System.getProperty("DEBUG") != null) {
            System.out.println("SystemStatus --> Added new LookupList object: " + listName);
          }
        }
      }
    }
    return (LookupList) lookups.get(listName);
  }



  /**
   *  A presentation object (.jsp) can see if a field should be ignored in the
   *  output
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
    if (thisUser.getShortChildList() == null) {
      thisUser.setChildUsers(new UserList());
    }
    Iterator i = addFrom.iterator();
    while (i.hasNext()) {
      User tmpUser = (User) i.next();
      if (tmpUser.getManagerId() == thisUser.getId()) {
        userList.put(new Integer(tmpUser.getId()), tmpUser);
        thisUser.getShortChildList().add(tmpUser);
        tmpUser.setManagerUser(thisUser);
        this.addChildUsers(tmpUser, addFrom);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context         Description of the Parameter
   *@param  action          Description of the Parameter
   *@param  previousObject  Description of the Parameter
   *@param  object          Description of the Parameter
   *@param  sqlDriver       Description of the Parameter
   *@param  ce              Description of the Parameter
   */
  public void processHook(ActionContext context, int action, Object previousObject, Object object, ConnectionPool sqlDriver, ConnectionElement ce) {
    hookManager.process(context, action, previousObject, object, sqlDriver, ce);
  }

  public User getUser(int id) {
    return (User)userList.get(new Integer(id));
  }

  /**
   *  Method checks the cached role permissions to see if the user
   *  has the specified permission.
   *
   *@param  userId          Description of the Parameter
   *@param  thisPermission  Description of the Parameter
   *@return                 Description of the Return Value
   */
  public boolean hasPermission(int userId, String thisPermission) {
    while (permissionUpdating) {
    }
    int roleId = this.getUser(userId).getRoleId();
    ArrayList permissions = (ArrayList) rolePermissions.get(new Integer(roleId));
    if (permissions == null) {
      return false;
    }
    return permissions.contains(thisPermission);
  }
}

