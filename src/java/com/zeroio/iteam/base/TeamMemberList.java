/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Represents a list of members of a project
 *
 *@author     matt rajkowski
 *@created    July 23, 2001
 *@version    $Id: TeamMemberList.java,v 1.2.134.1 2004/03/19 21:00:50 rvasista
 *      Exp $
 */
public class TeamMemberList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private Project project = null;
  private int projectId = -1;
  private int userLevel = -1;
  private String insertMembers = null;
  private String deleteMembers = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int forProjectUser = -1;


  /**
   *  Constructor for the TeamMemberList object
   */
  public TeamMemberList() { }


  /**
   *  Sets the pagedListInfo attribute of the TeamMemberList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the TeamMemberList object
   *
   *@param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the projectId attribute of the TeamMemberList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the userLevel attribute of the TeamMemberList object
   *
   *@param  tmp  The new userLevel value
   */
  public void setUserLevel(int tmp) {
    this.userLevel = tmp;
  }


  /**
   *  Sets the userLevel attribute of the TeamMemberList object
   *
   *@param  tmp  The new userLevel value
   */
  public void setUserLevel(String tmp) {
    this.userLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the project attribute of the TeamMemberList object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the TeamMemberList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the TeamMemberList object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the insertMembers attribute of the TeamMemberList object
   *
   *@param  tmp  The new insertMembers value
   */
  public void setInsertMembers(String tmp) {
    insertMembers = tmp;
  }


  /**
   *  Sets the deleteMembers attribute of the TeamMemberList object
   *
   *@param  tmp  The new deleteMembers value
   */
  public void setDeleteMembers(String tmp) {
    deleteMembers = tmp;
  }


  /**
   *  Sets the forProjectUser attribute of the TeamMemberList object
   *
   *@param  tmp  The new forProjectUser value
   */
  public void setForProjectUser(int tmp) {
    this.forProjectUser = tmp;
  }


  /**
   *  Gets the project attribute of the TeamMemberList object
   *
   *@return    The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   *  Description of the Method
   *
   *@param  thisId  Description of the Parameter
   *@return         Description of the Return Value
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
        "FROM project_team t " +
        "WHERE t.project_id > -1 ");
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
      pst = db.prepareStatement(sqlCount.toString() +
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
    pagedListInfo.setDefaultSort("r.level, namelast", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);
    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "t.*, r.level " +
        "FROM project_team t, contact u, lookup_project_role r " +
        "WHERE t.project_id > -1 " +
        "AND t.user_id = u.user_id " +
        "AND t.userlevel = r.code ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      TeamMember thisTeamMember = new TeamMember(rs);
      thisTeamMember.setProject(project);
      this.add(thisTeamMember);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (projectId > -1) {
      sqlFilter.append("AND project_id = ? ");
    }
    if (forProjectUser > -1) {
      sqlFilter.append("AND project_id IN (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL) ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    if (forProjectUser > -1) {
      pst.setInt(++i, forProjectUser);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean update(Connection db) throws SQLException {
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
          if (itemIdValue.indexOf("@") > 0) {
            itemId = org.aspcfs.modules.admin.base.User.getIdByEmailAddress(db, itemIdValue);
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
            // See if ID is already on the team
            if (!isOnTeam(db, projectId, itemId)) {
              // Insert the member
              PreparedStatement pst = db.prepareStatement(
                  "INSERT INTO project_team " +
                  "(project_id, user_id, userlevel, enteredby, modifiedby, status) " +
                  "VALUES (?, ?, ?, ?, ?, ?) ");
              int i = 0;
              pst.setInt(++i, projectId);
              pst.setInt(++i, itemId);
              DatabaseUtils.setInt(pst, ++i, userLevel);
              pst.setInt(++i, enteredBy);
              pst.setInt(++i, modifiedBy);
              pst.setInt(++i, TeamMember.STATUS_PENDING);
              pst.execute();
              pst.close();
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
   *  Description of the Method
   *
   *@param  str  Description of the Parameter
   *@param  o    Description of the Parameter
   *@param  n    Description of the Parameter
   *@return      Description of the Return Value
   */
  private String replace(String str, String o, String n) {
    boolean all = true;
    if (str != null && o != null && o.length() > 0 && n != null) {
      StringBuffer result = null;
      int oldpos = 0;
      do {
        int pos = str.indexOf(o, oldpos);
        if (pos < 0) {
          break;
        }
        if (result == null) {
          result = new StringBuffer();
        }
        result.append(str.substring(oldpos, pos));
        result.append(n);
        pos += o.length();
        oldpos = pos;
      } while (all);
      if (oldpos == 0) {
        return str;
      } else {
        result.append(str.substring(oldpos));
        return new String(result);
      }
    } else {
      return str;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator team = this.iterator();
    while (team.hasNext()) {
      TeamMember thisMember = (TeamMember) team.next();
      thisMember.delete(db);
    }
  }


  /**
   *  Gets the onTeam attribute of the TeamMemberList object
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@param  userId            Description of the Parameter
   *@return                   The onTeam value
   *@exception  SQLException  Description of the Exception
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
   *  Gets the userRelated attribute of the TeamMemberList class
   *
   *@param  db                Description of the Parameter
   *@param  masterUserId      Description of the Parameter
   *@param  userIdToCheck     Description of the Parameter
   *@return                   The userRelated value
   *@exception  SQLException  Description of the Exception
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
}

