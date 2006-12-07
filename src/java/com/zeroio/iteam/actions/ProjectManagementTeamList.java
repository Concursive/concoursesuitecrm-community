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
package com.zeroio.iteam.actions;

import java.sql.Connection;
import java.util.StringTokenizer;

import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Project;
import com.zeroio.iteam.base.ProjectList;
import com.zeroio.iteam.base.TeamMemberList;

//import com.zeroio.controller.*;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ProjectManagementTeamList.java,v 1.1.4.1 2004/07/07 15:12:07
 *          mrajkowski Exp $
 * @created July 6, 2004
 */
public final class ProjectManagementTeamList extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandProjects(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    String search = context.getRequest().getParameter("search");
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
        projects.setIncludeGuestProjects(false);
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
      } else if ("acct".equals(source) && "all".equals(status)) {
        OrganizationList organizationList = new OrganizationList();
      	organizationList.setName('%' + search + '%');
      	organizationList.buildShortList(db);
      	context.getRequest().setAttribute("orgList", organizationList);
        return "MakeOrgListOK";
      }
    } catch (Exception e) {

    } finally {
      freeConnection(context, db);
    }
    return null;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandItems(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    String id = st.nextToken();
    String orgId = null;
    if (st.hasMoreTokens()) {
    	orgId = st.nextToken();
    }
    Connection db = null;
    try {
      db = getConnection(context);
      if ("my".equals(source) || "all".equals(source)) {
        //Load the project and check permissions
        Project thisProject = new Project(
            db, Integer.parseInt(id), this.getUserRange(context));
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
        users.setRoleType(Constants.ROLETYPE_REGULAR); //fetch only regular users
        users.setSiteId(this.getUserSiteId(context));
        users.buildList(db);
        users = UserList.sortEnabledUsers(users, new UserList());
        context.getRequest().setAttribute("UserList", users);
        return ("MakeUserListOK");
      }  
      if ("acct".equals(source) && "all".equals(status)) {
        // if account is associated with the project build reguluar and portal users,
      	// otherwise build only regular users.
      	boolean nonPortalUsersOnly = false;
      	OrganizationList organizationList = new OrganizationList();
      	organizationList.setProjectId(id);
      	organizationList.setOrgId(orgId);
      	organizationList.buildList(db);
      	if (organizationList.isEmpty()) {
      		nonPortalUsersOnly = true;
      	}
      	ContactList contactList = new ContactList();
      	contactList.setOrgId(orgId);
      	contactList.setIncludeUsersOnly(true);
      	contactList.setWithAccountsOnly(true);
      	contactList.setPortalUsersOnly(
      			nonPortalUsersOnly ? Constants.FALSE : Constants.UNDEFINED);
      	contactList.buildList(db);
        context.getRequest().setAttribute("contactList", contactList);
        return ("MakeContactListOK");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      freeConnection(context, db);
    }
    return null;
  }

}

