/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
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
 *@version    $Id$
 */
public class TeamMemberList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private Project project = null;
  private int projectId = -1;
  private String insertMembers = null;
  private String deleteMembers = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;


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
    pst.close();
    rs.close();

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
    pagedListInfo.setDefaultSort("entered", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "t.* " +
        "FROM project_team t " +
        "WHERE t.project_id > -1 ");

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
      this.addElement(thisTeamMember);
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

      if (insertMembers != null && (!insertMembers.equals("")) && projectId > -1) {
        insertMembers = insertMembers.substring(0, insertMembers.length() - 1);
        StringTokenizer items = new StringTokenizer(insertMembers, "|");
        while (items.hasMoreTokens()) {
          String itemId = items.nextToken();
          String sqlInsert =
              "INSERT INTO project_team " +
              "(project_id, user_id, enteredby, modifiedby) " +
              "VALUES (?, ?, ?, ?) ";
          PreparedStatement pst = db.prepareStatement(sqlInsert);
          int i = 0;
          pst.setInt(++i, projectId);
          pst.setInt(++i, Integer.parseInt(itemId));
          pst.setInt(++i, enteredBy);
          pst.setInt(++i, modifiedBy);
          pst.execute();
          pst.close();
        }
      }

      if ((deleteMembers != null) && (!deleteMembers.equals("")) && projectId > -1) {
        deleteMembers = deleteMembers.substring(0, deleteMembers.length() - 1);
        String sqlDelete =
            "DELETE FROM project_team " +
            "WHERE project_id = " + projectId + " " +
            "AND user_id in (" + replace(deleteMembers, "|", ",") + ")";
        Statement st = db.createStatement();
        st.execute(sqlDelete);
        st.close();
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
}

