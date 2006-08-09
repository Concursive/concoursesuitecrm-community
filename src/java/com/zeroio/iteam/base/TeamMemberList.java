/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Represents a list of members of a project
 *
 * @author matt rajkowski
 * @version $Id: TeamMemberList.java,v 1.2.134.1 2004/03/19 21:00:50 rvasista
 *          Exp $
 * @created July 23, 2001
 */
public class TeamMemberList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private Project project = null;
  private int projectId = -1;
  private int userLevel = -1;
  private int roleLevel = -1;
  private String insertMembers = null;
  private String deleteMembers = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int forProjectUser = -1;
  private int userId = -1;
  private boolean employeesOnly = false;
  private boolean accountContactsOnly = false;
  private boolean portalUsersOnly = false;

  /**
   * Constructor for the TeamMemberList object
   */
  public TeamMemberList() {
  }


  /**
   * Sets the pagedListInfo attribute of the TeamMemberList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the TeamMemberList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the projectId attribute of the TeamMemberList object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the userLevel attribute of the TeamMemberList object
   *
   * @param tmp The new userLevel value
   */
  public void setUserLevel(int tmp) {
    this.userLevel = tmp;
  }


  /**
   * Sets the userLevel attribute of the TeamMemberList object
   *
   * @param tmp The new userLevel value
   */
  public void setUserLevel(String tmp) {
    this.userLevel = Integer.parseInt(tmp);
  }


  /**
   * Sets the roleLevel attribute of the TeamMemberList object
   *
   * @param tmp The new roleLevel value
   */
  public void setRoleLevel(int tmp) {
    this.roleLevel = tmp;
  }


  /**
   * Sets the project attribute of the TeamMemberList object
   *
   * @param tmp The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   * Sets the enteredBy attribute of the TeamMemberList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the TeamMemberList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the insertMembers attribute of the TeamMemberList object
   *
   * @param tmp The new insertMembers value
   */
  public void setInsertMembers(String tmp) {
    insertMembers = tmp;
  }


  /**
   * Sets the deleteMembers attribute of the TeamMemberList object
   *
   * @param tmp The new deleteMembers value
   */
  public void setDeleteMembers(String tmp) {
    deleteMembers = tmp;
  }


  /**
   * Sets the forProjectUser attribute of the TeamMemberList object
   *
   * @param tmp The new forProjectUser value
   */
  public void setForProjectUser(int tmp) {
    this.forProjectUser = tmp;
  }


  /**
   * Sets the employeesOnly attribute of the TeamMemberList object
   *
   * @param tmp The new employeesOnly value
   */
  public void setEmployeesOnly(boolean tmp) {
    this.employeesOnly = tmp;
  }


  /**
   * Sets the employeesOnly attribute of the TeamMemberList object
   *
   * @param tmp The new employeesOnly value
   */
  public void setEmployeesOnly(String tmp) {
    this.employeesOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the accountContactsOnly attribute of the TeamMemberList object
   *
   * @param tmp The new accountContactsOnly value
   */
  public void setAccountContactsOnly(boolean tmp) {
    this.accountContactsOnly = tmp;
  }


  /**
   * Sets the accountContactsOnly attribute of the TeamMemberList object
   *
   * @param tmp The new accountContactsOnly value
   */
  public void setAccountContactsOnly(String tmp) {
    this.accountContactsOnly = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the project attribute of the TeamMemberList object
   *
   * @return The project value
   */
  public Project getProject() {
    return project;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Description of the Method
   *
   * @param thisId Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasUserId(int thisId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      TeamMember thisMember = (TeamMember) i.next();
      if (thisMember.getUserId() == thisId) {
        return true;
      }
    }
    return false;
  }


  /**
	 * @return Returns the portalUsersOnly.
	 */
	public boolean getPortalUsersOnly() {
		return portalUsersOnly;
	}


	/**
	 * @param portalUsersOnly The portalUsersOnly to set.
	 */
	public void setPortalUsersOnly(boolean portalUsersOnly) {
		this.portalUsersOnly = portalUsersOnly;
	}


	/**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM project_team t, contact u, lookup_project_role r " +
        "WHERE t.project_id > -1 " +
        "AND t.user_id = u.user_id " +
        "AND t.userlevel = r.code ");
    createFilter(sqlFilter);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(-1);
    }
    //Get the total number of records matching filter
    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (rs.next()) {
      int maxRecords = rs.getInt("recordcount");
      pagedListInfo.setMaxRecords(maxRecords);
    }
    rs.close();
    pst.close();
    //Determine the offset, based on the filter, for the first record to show
    if (!pagedListInfo.getCurrentLetter().equals("")) {
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString() +
          "AND project_id < ? ");
      items = prepareFilter(pst);
      pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
      rs = pst.executeQuery();
      if (rs.next()) {
        int offsetCount = rs.getInt("recordcount");
        pagedListInfo.setCurrentOffset(offsetCount);
      }
      rs.close();
      pst.close();
    }
    //Determine column to sort by
    pagedListInfo.setDefaultSort("r.\"level\", namelast", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);
    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "t.*, r.\"level\" " +
        "FROM project_team t, contact u, lookup_project_role r " +
        "WHERE t.project_id > -1 " +
        "AND t.user_id = u.user_id " +
        "AND t.userlevel = r.code ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      TeamMember thisTeamMember = new TeamMember(rs);
      thisTeamMember.setProject(project);
      this.add(thisTeamMember);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (projectId > -1) {
      sqlFilter.append("AND project_id = ? ");
    }
    if (forProjectUser > -1) {
      sqlFilter.append(
          "AND project_id IN (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL) ");
    }
    if (roleLevel > -1) {
      sqlFilter.append(
          "AND t.userlevel IN (SELECT code FROM lookup_project_role WHERE \"level\" = ?) ");
    }
    if (userId > -1) {
      sqlFilter.append("AND t.user_id = ? ");
    }
    if (employeesOnly) {
      sqlFilter.append("AND u.org_id = 0 ");
    }
    if (accountContactsOnly) {
      sqlFilter.append("AND u.org_id > 0 ");
      sqlFilter.append("AND t.role_type != ").append(Constants.ROLETYPE_CUSTOMER).append(" ");
    }
    if (portalUsersOnly) {
      sqlFilter.append("AND u.org_id > 0 ");
      sqlFilter.append("AND t.role_type = ").append(Constants.ROLETYPE_CUSTOMER).append(" ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    if (forProjectUser > -1) {
      pst.setInt(++i, forProjectUser);
    }
    if (roleLevel > -1) {
      pst.setInt(++i, roleLevel);
    }
    if (userId > -1) {
      pst.setInt(++i, userId);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean update(Connection db, int masterUserId, int groupId, ArrayList addedUsers) throws SQLException {
    try {
      db.setAutoCommit(false);
      //Add new members
      if (insertMembers != null && (!insertMembers.equals("")) && projectId > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TeamMemberList-> New: " + insertMembers);
        }
        StringTokenizer items = new StringTokenizer(insertMembers, "|");
        while (items.hasMoreTokens()) {
          int itemId = -1;
          String itemIdValue = items.nextToken();
          boolean byEmail = false;
          if (itemIdValue.indexOf("@") > 0) {
            itemId = User.getIdByEmailAddress(db, itemIdValue);
            if (itemId > -1) {
              byEmail = true;
            }
          } else {
            itemId = Integer.parseInt(itemIdValue);
          }
          if (itemId == -1) {
            //If not, add them to the TeamMemberList...
            //The lead will be asked whether to send an email and invite and to
            //enter their name.
            // NOTE: This feature not supported yet
            //TeamMember member = new TeamMember();
            //User user = new User();
            //user.setEmail(itemIdValue);
            //member.setUser(user);
            //this.add(member);
          } else {
          	User user = new User(db, itemId);
            // See if ID is already on the team
            if (!isOnTeam(db, projectId, itemId)) {
              // Insert the member
              PreparedStatement pst = db.prepareStatement(
                  "INSERT INTO project_team " +
                  "(project_id, user_id, userlevel, enteredby, modifiedby, status, role_type) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?) ");
              int i = 0;
              pst.setInt(++i, projectId);
              pst.setInt(++i, itemId);
              DatabaseUtils.setInt(pst, ++i, userLevel);
              pst.setInt(++i, enteredBy);
              pst.setInt(++i, modifiedBy);
              pst.setInt(++i, TeamMember.STATUS_PENDING);
              DatabaseUtils.setInt(pst, ++i, user.getRoleType());              
              pst.execute();
              pst.close();
              // Existing user will be sent an email
              addedUsers.add(user);
            }
          }
        }
      }
      //Removed deleted members
      if ((deleteMembers != null) && (!deleteMembers.equals("")) && projectId > -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TeamMemberList-> Del: " + deleteMembers);
        }
        //Delete everyone but self
        StringTokenizer items = new StringTokenizer(deleteMembers, "|");
        while (items.hasMoreTokens()) {
          String itemId = items.nextToken();
          if (Integer.parseInt(itemId) != modifiedBy) {
            PreparedStatement pst = db.prepareStatement(
                "DELETE FROM project_team " +
                "WHERE project_id = ? " +
                "AND user_id = ?");
            pst.setInt(1, projectId);
            pst.setInt(2, Integer.parseInt(itemId));
            pst.execute();
            pst.close();
          }
        }
      }
      db.commit();
      db.setAutoCommit(true);
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    Iterator team = this.iterator();
    while (team.hasNext()) {
      TeamMember thisMember = (TeamMember) team.next();
      thisMember.setProject(project);
      thisMember.setProjectId(projectId);
      thisMember.setEnteredBy(enteredBy);
      thisMember.setModifiedBy(modifiedBy);
      thisMember.insert(db);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator team = this.iterator();
    while (team.hasNext()) {
      TeamMember thisMember = (TeamMember) team.next();
      thisMember.delete(db);
    }
  }


  /**
   * Gets the onTeam attribute of the TeamMemberList object
   *
   * @param db        Description of the Parameter
   * @param projectId Description of the Parameter
   * @param userId    Description of the Parameter
   * @return The onTeam value
   * @throws SQLException Description of the Exception
   */
  public static boolean isOnTeam(Connection db, int projectId, int userId) throws SQLException {
    boolean exists = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT userlevel " +
        "FROM project_team " +
        "WHERE project_id = ? " +
        "AND user_id = ? ");
    pst.setInt(1, projectId);
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
   * Gets the userRelated attribute of the TeamMemberList class
   *
   * @param db            Description of the Parameter
   * @param masterUserId  Description of the Parameter
   * @param userIdToCheck Description of the Parameter
   * @return The userRelated value
   * @throws SQLException Description of the Exception
   */
  public static boolean isUserRelated(Connection db, int masterUserId, int userIdToCheck) throws SQLException {
    boolean exists = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT p1.project_id " +
        "FROM project_team p1, project_team p2 " +
        "WHERE p1.project_id = p2.project_id " +
        "AND p1.user_id = ? " +
        "AND p2.user_id = ? ");
    pst.setInt(1, masterUserId);
    pst.setInt(2, userIdToCheck);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      exists = true;
    }
    rs.close();
    pst.close();
    return exists;
  }

  public TeamMember getTeamMember(int id) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      TeamMember thisMember = (TeamMember) i.next();
      if (thisMember.getUserId() == id) {
        return thisMember;
      }
    }
    return null;
  }
}

