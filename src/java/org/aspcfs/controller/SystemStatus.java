package org.aspcfs.controller;

import java.util.Date;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.controller.SessionManager;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.admin.base.CategoryEditor;
import java.io.File;
import org.w3c.dom.*;
import javax.servlet.ServletContext;

/**
 *  System status maintains global values for a shared group of users. This is
 *  based on the database that the user is connecting to.<p>
 *
 *  When a user logs in, permissions and hierarchies are read in. If someone
 *  changes user or role data then the user's permissions and hierarchies will
 *  be read in during the Security Check.
 *
 * @author     mrajkowski
 * @created    October 10, 2001
 * @version    $Id$
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
  private Map preferences = new LinkedHashMap();
  private int sessionTimeout = 5400;

  //Object Hook to Workflow Manager
  private ObjectHookManager hookManager = new ObjectHookManager();

  //Session Manager
  private SessionManager sessionManager = new SessionManager();

  //Category Editor
  private CategoryEditor categoryEditor = new CategoryEditor();


  /**
   *  Constructor for the SystemStatus object
   *
   * @since    1.1
   */
  public SystemStatus() { }


  /**
   *  Constructor for the SystemStatus object
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   * @since                    1.3
   */
  public SystemStatus(Connection db) throws SQLException {
    queryRecord(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db) throws SQLException {
    buildHierarchyList(db);
    buildPreferences(db);
    buildRolePermissions(db);
    buildCategoryLists(db);
  }


  /**
   *  Sets the PermissionCheck attribute of the SystemStatus object
   *
   * @param  tmp  The new PermissionCheck value
   * @since       1.1
   */
  public void setPermissionCheck(Date tmp) {
    this.permissionCheck = tmp;
  }


  /**
   *  Sets the HierarchyCheck attribute of the SystemStatus object
   *
   * @param  tmp  The new HierarchyCheck value
   * @since       1.1
   */
  public void setHierarchyCheck(Date tmp) {
    this.hierarchyCheck = tmp;
  }


  /**
   *  Sets the connectionElement attribute of the SystemStatus object
   *
   * @param  tmp  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }


  /**
   *  Sets the fileLibraryPath attribute of the SystemStatus object
   *
   * @param  tmp  The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   *  SessionManager manages the sessions active in the system
   *
   * @param  sessionManager  The new sessionManager value
   */
  public void setSessionManager(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }


  /**
   *  Sets the sessionTimeout attribute of the SystemStatus object
   *
   * @param  sessionTimeout  The new sessionTimeout value
   */
  public void setSessionTimeout(int sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }


  /**
   *  Sets the categoryEditor attribute of the SystemStatus object
   *
   * @param  categoryEditor  The new categoryEditor value
   */
  public void setCategoryEditor(CategoryEditor categoryEditor) {
    this.categoryEditor = categoryEditor;
  }


  /**
   *  Gets the categoryEditor attribute of the SystemStatus object
   *
   * @return    The categoryEditor value
   */
  public CategoryEditor getCategoryEditor() {
    return categoryEditor;
  }


  /**
   *  Gets the sessionTimeout attribute of the SystemStatus object
   *
   * @return    The sessionTimeout value
   */
  public int getSessionTimeout() {
    return sessionTimeout;
  }


  /**
   *  Gets the sessionManager attribute of the SystemStatus object
   *
   * @return    The sessionManager value
   */
  public SessionManager getSessionManager() {
    return sessionManager;
  }


  /**
   *  Gets the PermissionCheck attribute of the SystemStatus object
   *
   * @return    The PermissionCheck value
   * @since     1.1
   */
  public Date getPermissionCheck() {
    while (permissionUpdating) {
    }
    return permissionCheck;
  }


  /**
   *  Gets the HierarchyCheck attribute of the SystemStatus object
   *
   * @return    The HierarchyCheck value
   * @since     1.1
   */
  public Date getHierarchyCheck() {
    while (hierarchyUpdating) {
    }
    return hierarchyCheck;
  }


  /**
   *  Gets the hierarchyList attribute of the SystemStatus object
   *
   * @return    The hierarchyList value
   */
  public UserList getHierarchyList() {
    while (hierarchyUpdating) {
    }
    return hierarchyList;
  }


  /**
   *  Gets the userList attribute of the SystemStatus object
   *
   * @return    The userList value
   */
  public Hashtable getUserList() {
    while (hierarchyUpdating) {
    }
    return userList;
  }


  /**
   *  Gets the label attribute of the SystemStatus object
   *
   * @param  thisLabel  Description of Parameter
   * @return            The label value
   */
  public String getLabel(String thisLabel) {
    return this.getValue("system.fields.label", thisLabel);
  }


  /**
   *  Gets the connectionElement attribute of the SystemStatus object
   *
   * @return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the fileLibraryPath attribute of the SystemStatus object
   *
   * @return    The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   *  Gets the hookManager attribute of the SystemStatus object
   *
   * @return    The hookManager value
   */
  public ObjectHookManager getHookManager() {
    return hookManager;
  }


  /**
   *  Generates a list of all users in the system for the given database
   *  connection
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   * @since                    1.3
   */
  public void buildHierarchyList(Connection db) throws SQLException {
    //NOTE: The UserList does a joined query that gets the user and contact
    //data at the same time.  That's why the buildContact is disabled.
    hierarchyList.clear();
    userList.clear();
    //Get the top level managers
    UserList tmpListA = new UserList();
    tmpListA.setBuildContact(false);
    tmpListA.setBuildContactDetails(false);
    tmpListA.setBuildHierarchy(false);
    tmpListA.setTopLevel(true);
    tmpListA.setIncludeUsersWithRolesOnly(false);
    tmpListA.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildHierarchyList: A " + tmpListA.size());
    }
    //Get everyone
    UserList tmpListB = new UserList();
    tmpListB.setBuildContact(false);
    tmpListB.setBuildContactDetails(false);
    tmpListB.setBuildHierarchy(false);
    tmpListB.setTopLevel(false);
    tmpListB.setIncludeUsersWithRolesOnly(false);
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
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildCategoryLists(Connection db) throws SQLException {
    categoryEditor.build(db);
  }


  /**
   *  A method to reload the user hierarchy, typically used when a user is added
   *  or changed in the hierarchy.
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   */
  public void updateHierarchy(Connection db) throws SQLException {
    java.util.Date checkDate = new java.util.Date();
    if (checkDate.after(this.getHierarchyCheck())) {
      synchronized (this) {
        try {
          hierarchyUpdating = true;
          if (checkDate.after(hierarchyCheck)) {
            this.buildHierarchyList(db);
            this.setHierarchyCheck(new java.util.Date());
          }
        } catch (SQLException e) {
          throw e;
        } finally {
          hierarchyUpdating = false;
        }
      }
    }
  }


  /**
   *  Reloads role permissions that have been cached. Typically used when roles
   *  are modified or created.
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updateRolePermissions(Connection db) throws SQLException {
    java.util.Date checkDate = new java.util.Date();
    if (checkDate.after(this.getPermissionCheck())) {
      synchronized (this) {
        try {
          permissionUpdating = true;
          if (checkDate.after(permissionCheck)) {
            this.buildRolePermissions(db);
            this.setPermissionCheck(new java.util.Date());
          }
        } catch (SQLException e) {
          throw e;
        } finally {
          permissionUpdating = false;
        }
      }
    }
  }


  /**
   *  Loads the preferences for this specific system. Preference files are
   *  stored as XML in the system's fileLibrary.
   */
  public void buildPreferences(Connection db) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> Loading system preferences: " + fileLibraryPath + "system.xml ");
    }
    //Build the system preferences
    preferences.clear();
    try {
      if (fileLibraryPath != null) {
        File prefsFile = new File(fileLibraryPath + "system.xml");
        if (prefsFile.exists()) {
          XMLUtils xml = new XMLUtils(prefsFile);
          //Traverse the prefs and add the config nodes to the LinkedHashMap,
          //then for each config, add the param nodes into a child LinkedHashMap.
          //This will provide quick access to the values, and will allow an
          //editor to display the fields as ordered in the XML file
          NodeList configNodes = xml.getDocumentElement().getElementsByTagName("config");
          for (int i = 0; i < configNodes.getLength(); i++) {
            Node configNode = configNodes.item(i);
            if (configNode != null &&
                configNode.getNodeType() == Node.ELEMENT_NODE &&
                "config".equals(((Element) configNode).getTagName())) {
              //Each each config name, create a map for each of the params
              String configName = ((Element) configNode).getAttribute("name");
              Map preferenceGroup = null;
              if (configName != null) {
                if (preferences.containsKey(configName)) {
                  preferenceGroup = (LinkedHashMap) preferences.get(configName);
                } else {
                  preferenceGroup = new LinkedHashMap();
                  preferences.put(configName, preferenceGroup);
                }
                //Process the params for this config
                NodeList paramNodes = ((Element) configNode).getElementsByTagName("param");
                for (int j = 0; j < paramNodes.getLength(); j++) {
                  Node paramNode = paramNodes.item(j);
                  if (paramNode != null &&
                      paramNode.getNodeType() == Node.ELEMENT_NODE &&
                      "param".equals(((Element) paramNode).getTagName())) {
                    String paramName = ((Element) paramNode).getAttribute("name");
                    if (System.getProperty("DEBUG") != null) {
                      System.out.println("SystemStatus-> Added pref " + configName + ":" + paramName);
                    }
                    if (paramName != null) {
                      preferenceGroup.put(paramName, paramNode);
                    }
                  }
                }
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.out.println("SystemStatus-> Preferences Error: " + e.getMessage());
    }
    //Build the workflow manager preferences
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> Loading workflow processes: " + fileLibraryPath + "workflow.xml");
    }
    try {
      //Build processes from database
      hookManager.setFileLibraryPath(fileLibraryPath);
      hookManager.initializeBusinessProcessList(db);
      hookManager.initializeObjectHookList(db);
      if (hookManager.getProcessList().size() == 0 || hookManager.getHookList().size() == 0) {
        //Build processes from file (backwards compatible)
        //NOTE: The file is fine, but it should be imported into the database so
        //users can use the web editor
        if (fileLibraryPath != null) {
          File prefsFile = new File(fileLibraryPath + "workflow.xml");
          if (prefsFile.exists()) {
            XMLUtils xml = new XMLUtils(prefsFile);
            hookManager.setFileLibraryPath(fileLibraryPath);
            if (hookManager.getProcessList().size() == 0) {
              hookManager.initializeBusinessProcessList(xml.getDocumentElement());
            }
            if (hookManager.getHookList().size() == 0) {
              hookManager.initializeObjectHookList(xml.getDocumentElement());
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.out.println("SystemStatus-> Workflow Error: " + e.getMessage());
    }
  }


  /**
   *  Initializes the permissions cache.
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
   * @param  db                Description of the Parameter
   * @param  listName          DB Table name.
   * @return                   The lookupList value
   * @exception  SQLException  Description of the Exception
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
   *  Gets the categoryEditor attribute of the SystemStatus object
   *
   * @param  db  Description of the Parameter
   * @return     The categoryEditor value
   */
  public CategoryEditor getCategoryEditor(Connection db) {
    return categoryEditor;
  }


  /**
   *  A presentation object (.jsp) can see if a field should be ignored in the
   *  output
   *
   * @param  thisField  Description of Parameter
   * @return            Description of the Returned Value
   */
  public boolean hasField(String thisField) {
    Map ignoredFieldsGroup = (Map) preferences.get("system.fields.ignore");
    if (ignoredFieldsGroup != null) {
      return ignoredFieldsGroup.containsKey(thisField);
    }
    return false;
  }


  /**
   *  Adds a feature to the ChildUsers attribute of the SystemStatus object
   *
   * @param  thisUser  The feature to be added to the ChildUsers attribute
   * @param  addFrom   The feature to be added to the ChildUsers attribute
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
   *  Activates the object hook manager with the specified objects to see if a
   *  business process can execute
   *
   * @param  context         Description of the Parameter
   * @param  action          Description of the Parameter
   * @param  previousObject  Description of the Parameter
   * @param  object          Description of the Parameter
   * @param  sqlDriver       Description of the Parameter
   * @param  ce              Description of the Parameter
   */
  public void processHook(ActionContext context, int action, Object previousObject, Object object, ConnectionPool sqlDriver, ConnectionElement ce) {
    hookManager.process(context, action, previousObject, object, sqlDriver, ce);
  }


  /**
   *  Activates the specified business process through the object hook manager
   *
   * @param  context      Description of the Parameter
   * @param  processName  Description of the Parameter
   * @param  sqlDriver    Description of the Parameter
   * @param  ce           Description of the Parameter
   */
  public void processEvent(ServletContext context, String processName, ConnectionPool sqlDriver, ConnectionElement ce) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> processEvent: " + processName + " " + ce.getUrl());
    }
    hookManager.process(context, processName, sqlDriver, ce);
  }


  /**
   *  Gets the user attribute of the SystemStatus object
   *
   * @param  id  Description of the Parameter
   * @return     The user value
   */
  public User getUser(int id) {
    while (hierarchyUpdating) {
    }
    return (User) userList.get(new Integer(id));
  }


  /**
   *  Method checks the cached role permissions to see if the user has the
   *  specified permission.
   *
   * @param  userId          Description of the Parameter
   * @param  thisPermission  Description of the Parameter
   * @return                 Description of the Return Value
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


  /**
   *  Returns whether this system has any permissions loaded
   *
   * @return    Description of the Return Value
   */
  public boolean hasPermissions() {
    return rolePermissions.size() > 0;
  }


  /**
   *  Forces the cached contact information to reload from the database
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updateUserContact(Connection db, int id) throws SQLException {
    synchronized (this) {
      User thisUser = this.getUser(id);
      if (thisUser != null) {
        thisUser.getContact().build(db);
      }
    }
  }


  /**
   *  Gets the preferences value for this SystemStatus object. If the value is
   *  not found, then null is returned.
   *
   * @param  section    Description of the Parameter
   * @param  parameter  Description of the Parameter
   * @return            The value value
   */
  public String getValue(String section, String parameter) {
    Map prefGroup = (Map) preferences.get(section);
    if (prefGroup != null) {
      Node param = (Node) prefGroup.get(parameter);
      if (param != null) {
        return XMLUtils.getNodeText(XMLUtils.getFirstChild((Element) param, "value"));
      }
    }
    return null;
  }


  /**
   *  Gets the preferences value for this SystemStatus object. If the value is
   *  not found, then -1 is returned.
   *
   * @param  section    Description of the Parameter
   * @param  parameter  Description of the Parameter
   * @return            The valueAsInt value
   */
  public int getValueAsInt(String section, String parameter) {
    String intValue = this.getValue(section, parameter);
    if (intValue == null) {
      return -1;
    } else {
      return Integer.parseInt(intValue);
    }
  }
}

