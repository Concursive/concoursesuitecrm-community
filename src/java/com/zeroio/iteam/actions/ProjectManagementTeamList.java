/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import java.sql.*;
import java.util.StringTokenizer;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.admin.base.UserList;
//import com.zeroio.controller.*;
/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 6, 2004
 *@version    $Id: ProjectManagementTeamList.java,v 1.1.4.1 2004/07/07 15:12:07
 *      mrajkowski Exp $
 */
public final class ProjectManagementTeamList extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProjects(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    //Build the list
    Connection db = null;
    ProjectList projects = new ProjectList();
    try {
      db = getConnection(context);
      if ("my".equals(source) || "all".equals(source)) {
        projects.setProjectsForUser(getUserId(context));
        if ("open".equals(status)) {
          //Check if open or closed
          projects.setOpenProjectsOnly(true);
        } else {
          projects.setClosedProjectsOnly(true);
        }
        projects.buildList(db);
        context.getRequest().setAttribute("projectList", projects);
        return "ProjectsOK";
      } else if ("dept".equals(source) && "all".equals(status)) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(0, "Without a department");
        context.getRequest().setAttribute("departments", departmentList);
        return "MakeDepartmentListOK";
      }
    } catch (Exception e) {

    } finally {
      freeConnection(context, db);
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandItems(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    String id = st.nextToken();
    Connection db = null;
    try {
      db = getConnection(context);
      if ("my".equals(source) || "all".equals(source)) {
        //Load the project and check permissions
        Project thisProject = new Project(db, Integer.parseInt(id), this.getUserRange(context));
        thisProject.buildPermissionList(db);
        //Prepare list of team members
        TeamMemberList team = new TeamMemberList();
        team.setProjectId(Integer.parseInt(id));
        //Check permission first
        if (hasProjectAccess(context, db, thisProject, "project-team-view")) {
          team.buildList(db);
        }
        context.getRequest().setAttribute("team", team);
        return ("MakeTeamMemberListOK");
      }
      if ("dept".equals(source) && "all".equals(status)) {
        //Load departments and get the contacts
        UserList users = new UserList();
        users.setDepartment(Integer.parseInt(id));
        users.buildList(db);
        context.getRequest().setAttribute("UserList", users);
        return ("MakeUserListOK");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      freeConnection(context, db);
    }
    return null;
  }

}

