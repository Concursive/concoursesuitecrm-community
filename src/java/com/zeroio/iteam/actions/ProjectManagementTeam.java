/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Project Management module for CFS
 *
 *@author     matt rajkowski
 *@created    November 12, 2001
 *@version    $Id: ProjectManagementTeam.java,v 1.11 2002/12/23 16:12:28
 *      mrajkowski Exp $
 */
public final class ProjectManagementTeam extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "projects-team-edit"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    String projectId = (String) context.getRequest().getParameter("pid");

    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("team_modify").toLowerCase());

      UserList userList = new UserList();
      userList.setBuildContact(true);
      userList.setBuildHierarchy(false);
      userList.buildList(db);
      //context.getRequest().setAttribute("UserList", userList);
      context.getRequest().setAttribute("UserSize", "" + userList.size());

      LookupList departmentList = new LookupList(
          db, "lookup_department");
      departmentList.addItem(0, "Users without a department");
      context.getRequest().setAttribute("DepartmentList", departmentList);

      TeamMemberList team = new TeamMemberList();
      team.setProjectId(thisProject.getId());
      team.buildList(db);

      StringBuffer vector1 = new StringBuffer();
      StringBuffer vector2 = new StringBuffer();
      StringBuffer vector3 = new StringBuffer();
      StringBuffer vector4 = new StringBuffer();
      StringBuffer vector5 = new StringBuffer();

      Iterator iName = userList.iterator();
      while (iName.hasNext()) {
        User thisUser = (User) iName.next();
        vector1.append(thisUser.getContact().getNameFull());
        vector2.append(thisUser.getContact().getDepartment());
        vector3.append(thisUser.getId());

        if (team.hasUserId(thisUser.getId())) {
          vector4.append("1");
          vector5.append("1");
        } else {
          vector4.append("0");
          vector5.append("0");
        }

        if (iName.hasNext()) {
          vector1.append("|");
          vector2.append("|");
          vector3.append("|");
          vector4.append("|");
          vector5.append("|");
        }
      }

      context.getRequest().setAttribute("vector1", vector1.toString());
      context.getRequest().setAttribute("vector2", vector2.toString());
      context.getRequest().setAttribute("vector3", vector3.toString());
      context.getRequest().setAttribute("vector4", vector4.toString());
      context.getRequest().setAttribute("vector5", vector5.toString());

      HtmlSelect selCurrentTeam = new HtmlSelect();
      Iterator iTeam = team.iterator();
      while (iTeam.hasNext()) {
        TeamMember thisMember = (TeamMember) iTeam.next();
        Contact thisContact = userList.getUser(thisMember.getUserId()).getContact();
        selCurrentTeam.addItem(thisMember.getUserId(), thisContact.getNameFull());
      }
      context.getRequest().setAttribute("CurrentTeam", selCurrentTeam);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "UpdateItem", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateUserList(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      String deptId = context.getRequest().getParameter("deptId");
      db = this.getConnection(context);
      UserList userList = new UserList();
      if (deptId != null) {
        userList.setDepartment(deptId);
      }
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("MakeUserListOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "projects-team-edit"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    Connection db = null;

    String projectId = (String) context.getRequest().getParameter("pid");

    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("team_modify").toLowerCase());

      TeamMemberList thisTeam = (TeamMemberList) context.getFormBean();
      thisTeam.setProjectId(thisProject.getId());
      thisTeam.setEnteredBy(getUserId(context));
      thisTeam.setModifiedBy(getUserId(context));
      recordInserted = thisTeam.update(db);
      if (!recordInserted) {
        //processErrors(context, thisTeam.getErrors());
      } else {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("ModifyOK");
      } else {
        return (executeCommandModify(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

