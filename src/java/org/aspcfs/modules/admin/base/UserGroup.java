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
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.actionplans.base.ActionStep;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    September 23, 2005
 * @version    $Id: UserGroup.java 13778 2006-01-11 08:51:10 -0500 (Wed, 11 Jan
 *      2006) mrajkowski $
 */
public class UserGroup extends GenericBean {
  //UserGroup Properties
  protected int id = -1;
  protected String name = null;
  protected String description = null;
  protected boolean enabled = false;
  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private Timestamp trashedDate = null;
  private int siteId = -1;
  private String siteName = null;
  // others
  private boolean buildResources = false;
  public UserList groupUsers = null;
  private String insertMembers = null;
  private String deleteMembers = null;
  private int userCount = 0;
  private boolean buildUserCount = false;


  /**
   *  Constructor for the UserGroup object
   */
  public UserGroup() { }


  /**
   *  Constructor for the UserGroup object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public UserGroup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the UserGroup object
   *
   * @param  db                Description of the Parameter
   * @param  groupId           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public UserGroup(Connection db, String groupId) throws SQLException {
    queryRecord(db, Integer.parseInt(groupId));
  }


  /**
   *  Constructor for the UserGroup object
   *
   * @param  db                Description of the Parameter
   * @param  groupId           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public UserGroup(Connection db, int groupId) throws SQLException {
    queryRecord(db, groupId);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  groupId        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int groupId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT ug.*, ls.description AS site_name " +
        "FROM user_group ug " +
        "LEFT JOIN lookup_site_id ls ON (ug.site_id = ls.code) " +
        "WHERE ug.group_id = ? ");
    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, groupId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("User Group record not found.");
    }
    if (this.getBuildUserCount()) {
      buildUserCount(db);
    }
    if (this.getBuildResources()) {
      buildResources(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("group_id"));
    this.setName(rs.getString("group_name"));
    this.setDescription(rs.getString("description"));
    this.setEnabled(rs.getBoolean("enabled"));
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    //lookup_site_id table
    siteName = rs.getString("site_name");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    //TODO:: build the list of users for the group
    groupUsers = new UserList();
    groupUsers.setBuildContact(true);
    groupUsers.setBuildContactDetails(true);
    groupUsers.setSiteId(this.getSiteId());
    groupUsers.setIncludeUsersWithAccessToAllSites(true);
    groupUsers.setUserGroupId(this.getId());
    groupUsers.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildUserCount(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS usercount FROM user_group_map WHERE group_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = null;
    rs = pst.executeQuery();
    if (rs.next()) {
      this.setUserCount(DatabaseUtils.getInt(rs, "usercount", 0));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("UserGroup-> Beginning insert");
    }
    //Insert the user group
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "user_group_group_id_seq");
    sql.append(
        "INSERT INTO user_group " +
        "(" + (id > -1 ? "group_id," : "") + " group_name, description, enabled, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredBy, modifiedBy, site_id ) ");
    sql.append("VALUES (" + (id > -1 ? "?," : "") + " ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, getModifiedBy());
    pst.setInt(++i, getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "user_group_group_id_seq", id);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("UserGroup-> User Group inserted");
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE user_group " +
        " SET group_name = ?, " +
        " description = ?, " +
        " enabled = ?, " +
        " modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        " modifiedby = ?, " +
        " site_id = ? " +
        " WHERE group_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Group ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    // Check for this group's usage in tickets
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) AS ticketcount " +
          "FROM ticket " +
          "WHERE user_group_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int ticketCount = rs.getInt("ticketcount");
        if (ticketCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("ticketMap");
          thisDependency.setCount(ticketCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    }
    // Check for this user group's campaign mappings
    try {
      i = 0;
      pst = db.prepareStatement("DELETE FROM campaign_group_map WHERE user_group_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      pst = db.prepareStatement(
          "SELECT count(*) AS campaigncount " +
          "FROM campaign_group_map " +
          "WHERE user_group_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int campaignCount = rs.getInt("campaigncount");
        if (campaignCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("campaignMap");
          thisDependency.setCount(campaignCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    }
    // Check for this group's usage in action steps
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) AS stepcount " +
          "FROM action_step " +
          "WHERE group_id = ? " +
          "AND permission_type = ? ");
      pst.setInt(++i, this.getId());
      pst.setInt(++i, ActionStep.SPECIFIC_USER_GROUP);
      rs = pst.executeQuery();
      if (rs.next()) {
        int stepCount = rs.getInt("stepcount");
        if (stepCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("actionSteps");
          thisDependency.setCount(stepCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote ID not specified");
    }
    PreparedStatement pst = null;
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      // delete the campaign group mappings
      pst = db.prepareStatement("DELETE FROM campaign_group_map WHERE user_group_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      // delete the user mappings
      pst = db.prepareStatement("DELETE FROM user_group_map WHERE group_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      // delete the group
      pst = db.prepareStatement("DELETE FROM user_group WHERE group_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Adds a feature to the User attribute of the UserGroup object
   *
   * @param  db             The feature to be added to the User attribute
   * @param  userId         The feature to be added to the User attribute
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean addUser(Connection db, int userId) throws SQLException {
    return addUser(db, userId, 10);
  }


  /**
   *  Adds a feature to the User attribute of the UserGroup object
   *
   * @param  db             The feature to be added to the User attribute
   * @param  userId         The feature to be added to the User attribute
   * @param  level          The feature to be added to the User attribute
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean addUser(Connection db, int userId, int level) throws SQLException {
    if (isUserInGroup(db, userId)) {
      return true;
    }
    //Insert the user group
    StringBuffer sql = new StringBuffer();
    int mapId = DatabaseUtils.getNextSeq(db, "user_group_map_group_map_id_seq");
    sql.append(
        "INSERT INTO user_group_map " +
        "(" + (mapId > -1 ? "group_map_id," : "") + " user_id, group_id, \"level\", ");
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enabled ) ");
    sql.append("VALUES (" + (mapId > -1 ? "?," : "") + " ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("? ) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (mapId > -1) {
      pst.setInt(++i, mapId);
    }
    pst.setInt(++i, userId);
    pst.setInt(++i, this.getId());
    pst.setInt(++i, level);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    pst.setBoolean(++i, true);
    pst.execute();
    pst.close();
    mapId = DatabaseUtils.getCurrVal(db, "user_group_map_group_map_id_seq", mapId);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  userId         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean removeUser(Connection db, int userId) throws SQLException {
    if (userId == -1) {
      return false;
    }
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "DELETE FROM user_group_map WHERE user_id = ? AND group_id = ? AND group_map_id > -1 ");
    pst.setInt(1, userId);
    pst.setInt(2, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Gets the userInGroup attribute of the UserGroup object
   *
   * @param  db             Description of the Parameter
   * @param  userId         Description of the Parameter
   * @return                The userInGroup value
   * @throws  SQLException  Description of the Exception
   */
  public boolean isUserInGroup(Connection db, int userId) throws SQLException {
    boolean exists = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM user_group_map " +
        "WHERE group_id = ? " +
        "AND user_id = ? ");
    pst.setInt(1, this.getId());
    pst.setInt(2, userId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      exists = true;
    }
    rs.close();
    pst.close();
    return exists;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean updateUserMembership(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      //Add new members
      if (insertMembers != null && !insertMembers.equals("") && this.getId() > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("UserGroup::UserList-> New: " + insertMembers);
        }
        StringTokenizer users = new StringTokenizer(insertMembers, "|");
        while (users.hasMoreTokens()) {
          int thisUserId = -1;
          String thisUserIdValue = users.nextToken();
          thisUserId = Integer.parseInt(thisUserIdValue);
          if (thisUserId != -1) {
            addUser(db, thisUserId);
          }
        }
      }
      //Removed deleted members
      if ((deleteMembers != null) && !deleteMembers.equals("") && this.getId() > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("UserGroup::UserList-> Del: " + deleteMembers);
        }
        //Delete everyone but self
        StringTokenizer users = new StringTokenizer(deleteMembers, "|");
        while (users.hasMoreTokens()) {
          String thisUserId = users.nextToken();
          this.removeUser(db, Integer.parseInt(thisUserId));
        }
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Gets the permission attribute of the UserGroup object
   *
   * @param  siteId  Description of the Parameter
   * @return         The permission value
   */
  public int getPermission(int siteId) {
    int result = PermissionCategory.NONE;
    if (this.getSiteId() == -1) {
      if (this.getSiteId() == siteId) {
        result = PermissionCategory.DELETE;
      } else {
        result = PermissionCategory.VIEW;
      }
    } else {
      if (siteId == -1) {
        result = PermissionCategory.DELETE;
      } else if (siteId == this.getSiteId()) {
        result = PermissionCategory.DELETE;
      } else {
        result = PermissionCategory.NONE;
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    return (this.getName()+" ("+this.getId()+")");
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the UserGroup object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the UserGroup object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the UserGroup object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the name attribute of the UserGroup object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the UserGroup object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the description attribute of the UserGroup object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the UserGroup object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the enabled attribute of the UserGroup object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the UserGroup object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the UserGroup object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the entered attribute of the UserGroup object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the UserGroup object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the UserGroup object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the UserGroup object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the UserGroup object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the UserGroup object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modified attribute of the UserGroup object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the UserGroup object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the UserGroup object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the UserGroup object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the UserGroup object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the UserGroup object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the trashedDate attribute of the UserGroup object
   *
   * @return    The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Sets the trashedDate attribute of the UserGroup object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the UserGroup object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the buildResources attribute of the UserGroup object
   *
   * @return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Sets the buildResources attribute of the UserGroup object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the UserGroup object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the groupUsers attribute of the UserGroup object
   *
   * @return    The groupUsers value
   */
  public UserList getGroupUsers() {
    return groupUsers;
  }


  /**
   *  Sets the groupUsers attribute of the UserGroup object
   *
   * @param  tmp  The new groupUsers value
   */
  public void setGroupUsers(UserList tmp) {
    this.groupUsers = tmp;
  }


  /**
   *  Gets the insertMembers attribute of the UserGroup object
   *
   * @return    The insertMembers value
   */
  public String getInsertMembers() {
    return insertMembers;
  }


  /**
   *  Sets the insertMembers attribute of the UserGroup object
   *
   * @param  tmp  The new insertMembers value
   */
  public void setInsertMembers(String tmp) {
    this.insertMembers = tmp;
  }


  /**
   *  Gets the deleteMembers attribute of the UserGroup object
   *
   * @return    The deleteMembers value
   */
  public String getDeleteMembers() {
    return deleteMembers;
  }


  /**
   *  Sets the deleteMembers attribute of the UserGroup object
   *
   * @param  tmp  The new deleteMembers value
   */
  public void setDeleteMembers(String tmp) {
    this.deleteMembers = tmp;
  }


  /**
   *  Gets the siteId attribute of the UserGroup object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the UserGroup object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the UserGroup object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the siteName attribute of the UserGroup object
   *
   * @return    The siteName value
   */
  public String getSiteName() {
    return siteName;
  }


  /**
   *  Sets the siteName attribute of the UserGroup object
   *
   * @param  tmp  The new siteName value
   */
  public void setSiteName(String tmp) {
    this.siteName = tmp;
  }


  /**
   *  Gets the userCount attribute of the UserGroup object
   *
   * @return    The userCount value
   */
  public int getUserCount() {
    return userCount;
  }


  /**
   *  Sets the userCount attribute of the UserGroup object
   *
   * @param  tmp  The new userCount value
   */
  public void setUserCount(int tmp) {
    this.userCount = tmp;
  }


  /**
   *  Sets the userCount attribute of the UserGroup object
   *
   * @param  tmp  The new userCount value
   */
  public void setUserCount(String tmp) {
    this.userCount = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildUserCount attribute of the UserGroup object
   *
   * @return    The buildUserCount value
   */
  public boolean getBuildUserCount() {
    return buildUserCount;
  }


  /**
   *  Sets the buildUserCount attribute of the UserGroup object
   *
   * @param  tmp  The new buildUserCount value
   */
  public void setBuildUserCount(boolean tmp) {
    this.buildUserCount = tmp;
  }


  /**
   *  Sets the buildUserCount attribute of the UserGroup object
   *
   * @param  tmp  The new buildUserCount value
   */
  public void setBuildUserCount(String tmp) {
    this.buildUserCount = DatabaseUtils.parseBoolean(tmp);
  }
}

