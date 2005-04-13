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
package com.zeroio.iteam.search;

import com.darkhorseventures.framework.beans.SearchBean;
import com.zeroio.iteam.base.PermissionList;
import com.zeroio.iteam.beans.IteamSearchBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;

public class IteamSearchQuery {

  /**
   *  Description of the Method
   *
   *@param  db                 Description of the Parameter
   *@param  userId             Description of the Parameter
   *@param  search             Description of the Parameter
   *@param  specificProjectId  Description of the Parameter
   *@return                    Description of the Return Value
   *@exception  SQLException   Description of the Exception
   */
  public static String buildIteamSearchQuery(SearchBean search, Connection db, int userId, int specificProjectId) throws SQLException {
    // get the projects for the user
    // get the project permissions for each project
    // if user has access to the data, then add to query
    HashMap projectList = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT project_id, userlevel " +
        "FROM project_team " +
        "WHERE user_id = ? " +
        "AND status IS NULL " +
        (specificProjectId > -1 ? "AND project_id = ? " : ""));
    pst.setInt(1, userId);
    if (specificProjectId > -1) {
      pst.setInt(2, specificProjectId);
    }
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      int projectId = rs.getInt("project_id");
      int roleId = rs.getInt("userlevel");
      // these projects override the lower access projects
      projectList.put(new Integer(projectId), new Integer(roleId));
    }
    rs.close();
    pst.close();
    // build query string
    StringBuffer projectBuffer = new StringBuffer();
    // scan for permissions
    Iterator projects = projectList.keySet().iterator();
    while (projects.hasNext()) {
      StringBuffer permissionBuffer = new StringBuffer();
      Integer projectId = (Integer) projects.next();
      Integer roleId = (Integer) projectList.get(projectId);
      // for each project check the available user permissions
      PermissionList permissionList = new PermissionList();
      permissionList.setProjectId(projectId.intValue());
      permissionList.buildList(db);

      if (search.getSection() == IteamSearchBean.DETAILS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for project permissions
        if (permissionList.getAccessLevel("project-details-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:project");
        }
      }
      if (search.getSection() == IteamSearchBean.NEWS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for news permissions
        if (permissionList.getAccessLevel("project-news-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          // current, archived, unreleased
          // check for status permissions
          if (permissionList.getAccessLevel("project-news-view-unreleased") >= roleId.intValue()) {
            permissionBuffer.append("type:news");
          } else {
            // take into account a date range  [20030101 TO 20040101]
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            permissionBuffer.append("(type:news AND newsStatus:2 AND newsDate:[20030101 TO " + formatter.format(new java.util.Date()) + "])");
          }
        }
      }
      if (search.getSection() == IteamSearchBean.DISCUSSION || search.getSection() == SearchBean.UNDEFINED) {
        // Check for issue category permissions
        if (permissionList.getAccessLevel("project-discussion-forums-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:issuecategory");
        }
        // Check for issue permissions and issue reply permissions
        if (permissionList.getAccessLevel("project-discussion-topics-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:issue OR type:issuereply");
        }
      }
      if (search.getSection() == IteamSearchBean.DOCUMENTS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for file item permissions
        if (permissionList.getAccessLevel("project-documents-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:file");
        }
      }
      if (search.getSection() == IteamSearchBean.LISTS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for task category permissions and task permissions
        if (permissionList.getAccessLevel("project-lists-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:listcategory OR type:list");
        }
      }
      if (search.getSection() == IteamSearchBean.TICKETS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for ticket permissions
        if (permissionList.getAccessLevel("project-tickets-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:ticket");
        }
      }
      if (search.getSection() == IteamSearchBean.PLAN || search.getSection() == SearchBean.UNDEFINED) {
        // Check for requirement permissions
        if (permissionList.getAccessLevel("project-plan-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:outline");
          // Check for assignment folder permissions
          permissionBuffer.append(" OR ");
          permissionBuffer.append("type:activityfolder");
          // Check for assignment permissions
          permissionBuffer.append(" OR ");
          permissionBuffer.append("type:activity");
          // Check for assignment note permissions
          permissionBuffer.append(" OR ");
          permissionBuffer.append("type:activitynote");
        }
      }
      // piece together
      if (permissionBuffer.length() > 0) {
        if (projectBuffer.length() > 0) {
          projectBuffer.append(" OR ");
        }
        projectBuffer.append("(projectId:" + projectId.intValue() + " AND (" + permissionBuffer.toString() + ")) ");
      }
      // debugging
      if (permissionBuffer.length() == 0) {
        System.out.println("NO PERMISSIONS FOR PROJECT: " + projectId.intValue());
      }
    }
    // user does not have any projects, so lock them into a non-existent project
    // for security
    if (projectBuffer.length() == 0) {
      return "projectId:-1";
    } else {
      return projectBuffer.toString();
    }
  }
}
