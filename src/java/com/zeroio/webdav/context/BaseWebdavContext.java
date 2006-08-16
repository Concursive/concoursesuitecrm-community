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
package com.zeroio.webdav.context;

import com.zeroio.iteam.base.Project;
import com.zeroio.iteam.base.TeamMember;
import org.apache.naming.resources.Resource;
import org.apache.naming.resources.ResourceAttributes;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.DatabaseUtils;

import javax.naming.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * All module webdav contexts extend this base class. Provides common methods
 * which can be used by all its subclasses
 *
 * @author ananth
 * @version $Id: BaseWebdavContext.java,v 1.2 2005/04/13 20:04:30 mrajkowski
 *          Exp $
 * @created November 3, 2004
 */
public class BaseWebdavContext implements ModuleContext {

  protected final static String fs = System.getProperty("file.separator");
  protected String fileLibraryPath = null;
  // List of bindings for this context
  protected Hashtable bindings = new Hashtable();
  // List of attributes for each binding
  protected Hashtable attributes = new Hashtable();
  protected String contextName = null;
  private String permission = "";
  private int userId = -1;

  /**
   * Gets the contextName attribute of the BaseWebdavContext object
   *
   * @return The contextName value
   */
  public String getContextName() {
    return contextName;
  }


  /**
   * Sets the contextName attribute of the BaseWebdavContext object
   *
   * @param tmp The new contextName value
   */
  public void setContextName(String tmp) {
    this.contextName = tmp;
  }


  /**
   * Sets the userId attribute of the BaseWebdavContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the BaseWebdavContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Gets the userId attribute of the BaseWebdavContext object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Sets the permission attribute of the BaseWebdavContext object
   *
   * @param tmp The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   * Gets the permission attribute of the BaseWebdavContext object
   *
   * @return The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   * Sets the fileLibraryPath attribute of the BaseWebdavContext object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Sets the bindings attribute of the BaseWebdavContext object
   *
   * @param tmp The new bindings value
   */
  public void setBindings(Hashtable tmp) {
    this.bindings = tmp;
  }


  /**
   * Sets the attributes attribute of the BaseWebdavContext object
   *
   * @param tmp The new attributes value
   */
  public void setAttributes(Hashtable tmp) {
    this.attributes = tmp;
  }


  /**
   * Gets the fileLibraryPath attribute of the BaseWebdavContext object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   * Gets the bindings attribute of the BaseWebdavContext object
   *
   * @return The bindings value
   */
  public Hashtable getBindings() {
    return bindings;
  }


  /**
   * Gets the attributes attribute of the BaseWebdavContext object
   *
   * @return The attributes value
   */
  public Hashtable getAttributes() {
    return attributes;
  }


  /**
   * Constructor for the BaseWebdavContext object
   */
  public BaseWebdavContext() {
  }


  /**
   * Constructor for the BaseWebdavContext object
   *
   * @param name Description of the Parameter
   */
  public BaseWebdavContext(String name) {
    this.contextName = name;
  }


  /**
   * Constructor for the BaseWebdavContext object
   *
   * @param fileLibraryPath Description of the Parameter
   * @param userId          Description of the Parameter
   */
  public BaseWebdavContext(int userId, String fileLibraryPath) {
    this.userId = userId;
    this.fileLibraryPath = fileLibraryPath;
  }


  /**
   * Gets the cleanName attribute of the BaseWebdavContext object
   *
   * @param name Description of the Parameter
   * @return The cleanName value
   */
  private String decodePath(String name) {
    try {
      return java.net.URLDecoder.decode(name, "UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {
      return name;
    }
  }


  /**
   * Description of the Method
   *
   * @param path Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object lookup(String path) throws NamingException {
    if ("".equals(path.trim())) {
      return this;
    }
    path = decodePath(path);
    StringTokenizer st = new StringTokenizer(path, "/");
    Object current = this;
    String token = null;
    while (st.hasMoreTokens()) {
      token = st.nextToken();
      if (current instanceof ModuleContext) {
        current = ((ModuleContext) current).getBindings().get(token);
      }
    }
    if (current == null) {
      throw new NameNotFoundException(path + " NOT FOUND");
    }
    return current;
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param name       Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException       Description of the Exception
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public Object lookup(SystemStatus thisSystem, Connection db, String name) throws NamingException, SQLException, FileNotFoundException {
    if ("".equals(name.trim())) {
      return this;
    }
    name = decodePath(name);
    StringTokenizer st = new StringTokenizer(name, "/");
    Object current = this;
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (current instanceof ModuleContext) {
        current = ((ModuleContext) current).getBindings().get(token);

        if (current == null) {
          break;
        }

        if (current instanceof ItemContext) {
          // An ItemContext gets the path from its parent context
          ((ItemContext) current).buildResources(thisSystem, db);
        } else if (current instanceof FolderContext) {
          // A FolderContext gets the path from its parent context
          ((FolderContext) current).buildResources(thisSystem, db);
        } else if (current instanceof ModuleContext) {
          // A BaseWebdavContext or any other Top Level ModuleContext needs the
          // base fileLibrary path from the webdav manager
          ((ModuleContext) current).buildResources(
              thisSystem, db, userId, fileLibraryPath);
        }
      }
    }
    if (current == null) {
      throw new NameNotFoundException(name + " not found");
    }
    return current;
  }


  /**
   * Gets the attributes attribute of the BaseWebdavContext object
   *
   * @param path Description of the Parameter
   * @return The attributes value
   * @throws NamingException Description of the Exception
   */
  public ResourceAttributes getAttributes(String path) throws NamingException {
    if ("".equals(path.trim())) {
      return null;
    }
    path = decodePath(path);
    StringTokenizer st = new StringTokenizer(path, "/");
    Object current = this;
    Object parent = null;
    String token = null;
    while (st.hasMoreTokens()) {
      token = st.nextToken();
      if (current instanceof ModuleContext) {
        parent = current;
        current = ((ModuleContext) current).getBindings().get(token);
      }
    }
    if (current == null) {
      System.out.println(
          "naming exception while fetching attrs for token: " + token);
      throw new NameNotFoundException(path + " not found");
    }
    return (ResourceAttributes) (((ModuleContext) parent).getAttributes()).get(
        token);
  }


  /**
   * A ModuleContext has name-object bindings. It also maintains a hashtable of
   * attributes for each object available in its bindings.
   *
   * @param path       Description of the Parameter
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return The attributes value
   * @throws NamingException       Description of the Exception
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public ResourceAttributes getAttributes(SystemStatus thisSystem, Connection db, String path)
      throws NamingException, SQLException, FileNotFoundException {
    if ("".equals(path.trim())) {
      return null;
    }
    path = decodePath(path);
    StringTokenizer st = new StringTokenizer(path, "/");
    Object current = this;
    Object parent = null;
    String token = null;
    while (st.hasMoreTokens()) {
      token = st.nextToken();
      if (current instanceof ModuleContext) {
        parent = current;
        current = ((ModuleContext) current).getBindings().get(token);
        if (current instanceof ItemContext) {
          // An ItemContext gets the path from its parent context
          ((ItemContext) current).buildResources(thisSystem, db);
        } else if (current instanceof FolderContext) {
          // A FolderContext gets the path from its parent context
          ((FolderContext) current).buildResources(thisSystem, db);
        } else if (current instanceof ModuleContext) {
          // A BaseWebdavContext or any other Top Level ModuleContext needs the
          // base fileLibrary path from the webdav manager
          ((ModuleContext) current).buildResources(
              thisSystem, db, userId, fileLibraryPath);
        }
      }
    }
    if (current == null) {
      System.out.println(
          "naming exception while fetching attrs for token: " + token);
      throw new NameNotFoundException(path + " not found");
    }
    //System.out.println("parent: " + parent + ", token: " + token);
    return (ResourceAttributes) (((ModuleContext) parent).getAttributes()).get(
        token);
  }


  /**
   * Description of the Method
   *
   * @param name     Description of the Parameter
   * @param entered  Description of the Parameter
   * @param modified Description of the Parameter
   * @param length   Description of the Parameter
   */
  public void buildProperties(String name, Timestamp entered, Timestamp modified, Integer length) {
    ResourceAttributes attrs = new ResourceAttributes();
    attrs.setContentLength(length.longValue());
    attrs.setCreation(entered.getTime());
    attrs.setLastModified(modified.getTime());
    attributes.put(name, attrs);
  }


  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param fileLibraryPath Description of the Parameter
   * @param userId          Description of the Parameter
   * @param thisSystem      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException {
    this.userId = userId;
    this.fileLibraryPath = fileLibraryPath;
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public NamingEnumeration list(String name) throws NamingException {
    if ("".equals(name.trim())) {
      return new ListOfNames(bindings.keys());
    }
    try {
      Object target = lookup(name);
      if (target instanceof ModuleContext) {
        return ((ModuleContext) target).list("");
      }
    } catch (NameNotFoundException e) {
      // do nothing
    }
    return new ListOfNames(bindings.keys());
    //throw new NotContextException(name + " cannot be listed");
  }


  /**
   *  Description of the Method
   *
   * @param  db                   Description of the Parameter
   * @param  path                 Description of the Parameter
   * @return                      Description of the Return Value
   * @exception  SQLException     Description of the Exception
   * @exception  NamingException  Description of the Exception
   */

  /*
 public boolean createSubcontext(Connection db, String path) throws SQLException, NamingException {
   if ("".equals(path.trim())) {
     //Creating folder at root context not allowed
     System.out.println("Creating a Folder at root context not permitted");
     throw new NamingException("Creating a folder at root context not allowed.");
   }
   return false;
 }*/


  /**
   * Description of the Method
   *
   * @param path       Description of the Parameter
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException       Description of the Exception
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public boolean createSubcontext(SystemStatus thisSystem, Connection db, String path) throws SQLException,
      FileNotFoundException, NamingException {
    path = decodePath(path);

    if (path.endsWith("/")) {
      path = path.substring(0, path.lastIndexOf("/"));
    }

    int slash = path.lastIndexOf("/");
    String parentPath = path.substring(0, slash);
    String contextName = path.substring(slash + 1);

    if ("".equals(parentPath.trim())) {
      //Creating folder at root context not allowed
      System.out.println("Creating a Folder at root context not permitted");
      return false;
    }

    Object parent = lookup(thisSystem, db, parentPath);
    if (parent != null) {
      if (parent instanceof ModuleContext) {
        return (((ModuleContext) parent).createSubcontext(
            thisSystem, db, contextName));
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param path   Description of the Parameter
   * @param object Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public Object copyResource(SystemStatus thisSystem, Connection db, String path, Object object) throws SQLException,
      IOException, NamingException {
    System.out.println(
        "BaseWebdavContext-> COPYING RESOURCE AT PATH: " + path);
    path = decodePath(path);

    if ("".equals(path.trim())) {
      //binding an object at root context not allowed
      System.out.println(
          "binding an object at root context not allowed....returning false");
      throw new NamingException(
          "Binding an object at root context not allowed..");
    }

    //dest contains the name of the file. eg: /Accounts/Dataline/temp.dat
    int slash = path.lastIndexOf("/");
    String parentPath = path.substring(0, slash);
    String resourceName = path.substring(slash + 1);

    if (object instanceof Resource) {
      Resource resource = (Resource) object;
      resource.setName(resourceName);
    }

    Object parent = lookup(parentPath);
    if (parent != null) {
      if (parent instanceof ModuleContext) {
        return (((ModuleContext) parent).copyResource(thisSystem, db, object));
      }
    }
    return null;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param object Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */

  public Object copyResource(SystemStatus thisSystem, Connection db, Object object) throws SQLException,
      IOException, NamingException {
    return null;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param object Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public boolean bind(SystemStatus thisSystem, Connection db, Object object) throws SQLException, NamingException {
    //binding an object at root context not allowed
    System.out.println("Binding an object at root context not allowed");
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param dest   Description of the Parameter
   * @param object Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public boolean bind(SystemStatus thisSystem, Connection db, String dest, Object object) throws SQLException, NamingException {
    System.out.println(
        "BaseWebdavContext-> BINDING SUB-CONTEXT AT PATH: " + dest);
    dest = decodePath(dest);

    if ("".equals(dest.trim())) {
      //binding an object at root context not allowed
      System.out.println(
          "binding an object at root context not allowed....returning false");
      throw new NamingException(
          "Binding an object at root context not allowed..");
    }

    if (object instanceof Resource) {
      //dest contains the name of the file. eg: /Accounts/Dataline/temp.dat
      int slash = dest.lastIndexOf("/");
      dest = dest.substring(0, slash);
    }

    Object parent = lookup(dest);
    if (parent != null) {
      if (parent instanceof ModuleContext) {
        return (((ModuleContext) parent).bind(thisSystem, db, object));
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param dest       Description of the Parameter
   * @param object     Description of the Parameter
   * @throws SQLException          Description of the Exception
   * @throws NamingException       Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public boolean move(SystemStatus thisSystem, Connection db, String dest, Object object) throws SQLException,
      FileNotFoundException, NamingException {
    dest = decodePath(dest);

    if ("".equals(dest.trim())) {
      //binding an object at root context not allowed
      System.out.println("Moving a Folder to the root context not permitted");
      throw new NamingException(
          "Binding an object at root context not allowed..");
    }

    int slash = dest.lastIndexOf("/");
    String parentPath = dest.substring(0, slash);
    String contextName = dest.substring(slash + 1);

    if (object instanceof ModuleContext) {
      ModuleContext context = (ModuleContext) object;
      context.setContextName(contextName);
    } else if (object instanceof Resource) {
      Resource resource = (Resource) object;
      resource.setSubject(contextName);
    }

    Object parent = lookup(thisSystem, db, parentPath);
    if (parent != null) {
      if (parent instanceof ModuleContext) {
        return (((ModuleContext) parent).bind(thisSystem, db, object));
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db   Description of the Parameter
   * @param path Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public void unbind(SystemStatus thisSystem, Connection db, String path) throws SQLException, NamingException {
    System.out.println("BaseWebdavContext-> UN-BINDING...PATH: " + path);
    path = decodePath(path);

    if (path.endsWith("/")) {
      path = path.substring(0, path.lastIndexOf("/"));
    }

    int slash = path.lastIndexOf("/");
    String parentPath = path.substring(0, slash);
    String contextName = path.substring(slash + 1);

    if ("".equals(parentPath.trim())) {
      //unbinding folder at root context not allowed
      System.out.println(
          "Unbinding folder at root context not allowed....returning false");
      throw new NamingException("Unbinding at root context not allowed...");
    }

    Object parent = lookup(parentPath);
    if (parent != null) {
      if (parent instanceof ModuleContext) {
        ((ModuleContext) parent).unbind(thisSystem, db, contextName);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int getUserSiteId(Connection db, int userId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "SELECT site_id " +
      "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
      "WHERE user_id = ? ");
    pst.setInt(1, userId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      return DatabaseUtils.getInt(rs, "site_id");
    }
    return -1;
  }
  
  
  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("Context: " + this.getContextName() + "\n");
    Iterator i = this.getBindings().keySet().iterator();
    while (i.hasNext()) {
      String child = (String) i.next();
      sb.append("  - " + child + "\n");
    }
    return sb.toString();
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param linkItemId Description of the Parameter
   * @param userId     Description of the Parameter
   * @param permission Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean hasPermission(SystemStatus thisSystem, Connection db, int linkItemId, int userId, String permission)
      throws SQLException {
    if (permission.startsWith("project")) {
      return (hasProjectAccess(thisSystem, db, linkItemId, userId, permission));
    } else if (permission.startsWith("document")) {
      return (hasDocumentStoreAccess(
          thisSystem, db, linkItemId, userId, permission));
    } else {
      return (hasPermission(thisSystem, userId, permission));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param userId     Description of the Parameter
   * @param permission Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasPermission(SystemStatus thisSystem, int userId, String permission) {
    return thisSystem.hasPermission(userId, permission);
  }


  /**
   * Gets the userLevel attribute of the BaseWebdavContext object
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param roleLevel  Description of the Parameter
   * @return The userLevel value
   * @throws SQLException Description of the Exception
   */
  protected int getUserLevel(SystemStatus thisSystem, Connection db, int roleLevel) throws SQLException {
    LookupList roleList = thisSystem.getLookupList(db, "lookup_project_role");
    if (roleList != null) {
      return roleList.getIdFromLevel(roleLevel);
    }
    return -1;
  }


  /**
   * Gets the roleId attribute of the BaseWebdavContext object
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param userlevel  Description of the Parameter
   * @return The roleId value
   * @throws SQLException Description of the Exception
   */
  protected int getRoleId(SystemStatus thisSystem, Connection db, int userlevel) throws SQLException {
    LookupList roleList = thisSystem.getLookupList(db, "lookup_project_role");
    if (roleList != null) {
      return roleList.getLevelFromId(userlevel);
    }
    return -1;
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param projectId  Description of the Parameter
   * @param userId     Description of the Parameter
   * @param permission Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected boolean hasProjectAccess(SystemStatus thisSystem, Connection db, int projectId, int userId, String permission) throws SQLException {
    // See if the team member has access to perform a project action
    Project thisProject = new Project(db, projectId);
    TeamMember thisMember = null;
    try {
      // Load from project
      thisMember = new TeamMember(db, projectId, userId);
    } catch (Exception notValid) {
      // Create a guest
      // TODO: ONLY IF THE PROJECT ALLOWS GUESTS!!
      thisMember = new TeamMember();
      thisMember.setProjectId(projectId);
      thisMember.setUserLevel(getUserLevel(thisSystem, db, TeamMember.GUEST));
      thisMember.setRoleId(TeamMember.GUEST);
    }
    // Return the status of the permission
    if (thisMember.getRoleId() == TeamMember.PROJECT_LEAD) {
      return true;
    }
    // See what the minimum required is and see if user meets that
    thisProject.buildPermissionList(db);
    int code = thisProject.getAccessUserLevel(permission);
    int roleId = getRoleId(thisSystem, db, code);
    if (code == -1 || roleId == -1) {
      return false;
    }
    return (thisMember.getRoleId() <= roleId);
  }


  /**
   * Gets the documentStoreRoleId attribute of the BaseWebdavContext object
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param userlevel  Description of the Parameter
   * @return The documentStoreRoleId value
   * @throws SQLException Description of the Exception
   */
  protected int getDocumentStoreRoleId(SystemStatus thisSystem, Connection db, int userlevel) throws SQLException {
    LookupList roleList = thisSystem.getLookupList(
        db, "lookup_document_store_role");
    if (roleList != null) {
      return roleList.getLevelFromId(userlevel);
    }
    return -1;
  }


  /**
   * Gets the documentStoreUserLevel attribute of the BaseWebdavContext object
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param roleLevel  Description of the Parameter
   * @return The documentStoreUserLevel value
   * @throws SQLException Description of the Exception
   */
  protected int getDocumentStoreUserLevel(SystemStatus thisSystem, Connection db, int roleLevel) throws SQLException {
    LookupList roleList = thisSystem.getLookupList(db, "lookup_project_role");
    if (roleList != null) {
      return roleList.getIdFromLevel(roleLevel);
    }
    return -1;
  }


  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param permission      Description of the Parameter
   * @param thisSystem      Description of the Parameter
   * @param documentStoreId Description of the Parameter
   * @param userId          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected boolean hasDocumentStoreAccess(SystemStatus thisSystem, Connection db, int documentStoreId, int userId, String permission) throws SQLException {
    // See if the team member has access to perform a document store action
    DocumentStore thisDocumentStore = new DocumentStore(db, documentStoreId);
    DocumentStoreTeamMember thisMember = null;
    try {
      // Load from document store
      User tmpUser = new User(db, userId);
      int tmpUserRoleId = tmpUser.getRoleId();
      Contact tmpContact = new Contact(db, tmpUser.getContactId());
      int tmpDepartmentId = tmpContact.getDepartment();

      thisMember = new DocumentStoreTeamMember(
          db, thisDocumentStore.getId(), userId, tmpUserRoleId, tmpDepartmentId, tmpUser.getSiteId());
    } catch (Exception notValid) {
      // Create a guest
      // TODO: VERIFY THAT A GUEST CAN HAVE ACCESS!
      thisMember = new DocumentStoreTeamMember();
      thisMember.setDocumentStoreId(thisDocumentStore.getId());
      thisMember.setUserLevel(
          getDocumentStoreUserLevel(
              thisSystem, db, DocumentStoreTeamMember.GUEST));
      thisMember.setRoleId(DocumentStoreTeamMember.GUEST);
    }

    // Return the status of the permission
    if (thisMember.getRoleId() == DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER) {
      return true;
    }
    // See what the minimum required is and see if user meets that
    int code = thisDocumentStore.getAccessUserLevel(permission);
    int roleId = getDocumentStoreRoleId(thisSystem, db, code);
    if (code == -1 || roleId == -1) {
      return false;
    }
    // Return the status of the permission
    return (thisMember.getRoleId() <= roleId);
  }


  /**
   * Description of the Class
   *
   * @author ananth
   * @version $Id: BaseWebdavContext.java,v 1.2 2005/04/13 20:04:30
   *          mrajkowski Exp $
   * @created November 5, 2004
   */
  class ListOfNames implements NamingEnumeration {
    protected Enumeration names;


    /**
     * Constructor for the ListOfNames object
     *
     * @param names Description of the Parameter
     */
    ListOfNames(Enumeration names) {
      this.names = names;
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     */
    public boolean hasMoreElements() {
      try {
        return hasMore();
      } catch (NamingException e) {
        return false;
      }
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     * @throws NamingException Description of the Exception
     */
    public boolean hasMore() throws NamingException {
      return names.hasMoreElements();
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     * @throws NamingException Description of the Exception
     */
    public Object next() throws NamingException {
      String name = (String) names.nextElement();
      String className = bindings.get(name).getClass().getName();
      return new NameClassPair(name, className);
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     */
    public Object nextElement() {
      try {
        return next();
      } catch (NamingException e) {
        throw new NoSuchElementException(e.toString());
      }
    }


    /**
     * Description of the Method
     */
    public void close() {
    }
  }


  // Class for enumerating bindings

  /**
   * Description of the Class
   *
   * @author ananth
   * @version $Id: BaseWebdavContext.java,v 1.2 2005/04/13 20:04:30
   *          mrajkowski Exp $
   * @created November 5, 2004
   */
  class ListOfBindings extends ListOfNames {

    /**
     * Constructor for the ListOfBindings object
     *
     * @param names Description of the Parameter
     */
    ListOfBindings(Enumeration names) {
      super(names);
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     * @throws NamingException Description of the Exception
     */
    public Object next() throws NamingException {
      String name = (String) names.nextElement();
      return new Binding(name, bindings.get(name));
    }
  }
}

