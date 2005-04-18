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

import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import java.sql.*;
import java.util.*;
import java.io.File;

import org.aspcfs.utils.web.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.*;
import org.aspcfs.controller.ApplicationPrefs;
import org.w3c.dom.Element;

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
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-team-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("team_modify").toLowerCase());
      //Load the team members
      TeamMemberList team = new TeamMemberList();
      team.setProjectId(thisProject.getId());
      team.buildList(db);

      //Prepare the first array of those already on list, for javascript
      StringBuffer vectorUserId = new StringBuffer();
      StringBuffer vectorState = new StringBuffer();
      HtmlSelect selCurrentTeam = new HtmlSelect();
      Iterator iTeam = team.iterator();
      while (iTeam.hasNext()) {
        TeamMember thisMember = (TeamMember) iTeam.next();
        User tmpUser =  getUser(context, thisMember.getUserId());
        if (tmpUser.getContact().getOrgId() == 0){
          selCurrentTeam.addItem(thisMember.getUserId(), tmpUser.getContact().getNameFirstLast());
        } else {
          //Append organization name if this user is not a primary contact of his organization
          Organization organization = new Organization(db,tmpUser.getContact().getOrgId());
          String userNameForDisplay = tmpUser.getContact().getNameFirstLast() + " (" + organization.getName() + ")";
          if (organization.getPrimaryContact() !=  null){
            if (organization.getPrimaryContact().getId() == tmpUser.getContact().getId()){
              userNameForDisplay =  tmpUser.getContact().getNameFirstLast();
            }
          }
          selCurrentTeam.addItem(thisMember.getUserId(), userNameForDisplay);
        }
        vectorUserId.append(thisMember.getUserId());
        vectorState.append("1");
        if (iTeam.hasNext()) {
          vectorUserId.append("|");
          vectorState.append("|");
        }
      }
      context.getRequest().setAttribute("currentTeam", selCurrentTeam);
      context.getRequest().setAttribute("vectorUserId", vectorUserId.toString());
      context.getRequest().setAttribute("vectorState", vectorState.toString());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ProjectCenterOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateUserList(ActionContext context) {
    if (!"true".equals((String) context.getServletContext().getAttribute("DEPARTMENT"))) {
      return "PermissionError";
    }
    Connection db = null;
    try {
      String deptId = context.getRequest().getParameter("deptId");
      db = this.getConnection(context);
      UserList userList = new UserList();
      if (deptId != null) {
        userList.setDepartment(Integer.parseInt(deptId));
      }
      userList.buildList(db);
      userList = UserList.sortEnabledUsers(userList, new UserList());
      context.getRequest().setAttribute("UserList", userList);
    } catch (SQLException e) {

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
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    boolean recordInserted = false;
    try {
      db = getConnection(context);
      //Project permissions
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-team-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("team_modify").toLowerCase());
      context.getRequest().setAttribute("pid", projectId);
      //Array of added users, not invited users
      ArrayList addedUsers = new ArrayList();
      //Process the members
      TeamMemberList thisTeam = (TeamMemberList) context.getFormBean();
      thisTeam.setProjectId(thisProject.getId());
      thisTeam.setUserLevel(getUserLevel(context, db, TeamMember.GUEST));
      thisTeam.setEnteredBy(getUserId(context));
      thisTeam.setModifiedBy(getUserId(context));
      recordInserted = thisTeam.update(db, getUserId(context), -1, addedUsers);
      if (recordInserted) {
        // Added users will get an email
        if (addedUsers.size() > 0) {
          ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
          // Send each user a message
          Iterator users = addedUsers.iterator();
          while (users.hasNext()) {
            DatabaseUtils.renewConnection(context, db);
            Contact projectContact = new Contact(db, getUser(context, getUserId(context)).getContact().getId());
            User thisUser = (User) users.next();
            Contact thisContact = new Contact(db, thisUser.getContactId());
            // Load the templates
            String templateFile = getDbNamePath(context) + "templates.xml";
            File configFile = new File(templateFile);
            XMLUtils xml = new XMLUtils(configFile);
            Element mappings = xml.getFirstChild("mappings");

            Template inviteSubject = new Template();
            inviteSubject.setText(XMLUtils.getNodeText(XMLUtils.getElement(mappings, "map", "id", "projects.userAdded.subject")));
            inviteSubject.addParseElement("\r\n", "");
            inviteSubject.addParseElement("\r", "");
            inviteSubject.addParseElement("\n", "");

            Template inviteBody = new Template();
            inviteBody.setText(XMLUtils.getNodeText(XMLUtils.getElement(mappings, "map", "id", "projects.userAdded.body")));
            inviteBody.addParseElement("${invite.firstName}", thisContact.getNameFirst());
            inviteBody.addParseElement("${invite.lastName}", thisContact.getNameLast());
            inviteBody.addParseElement("${invite.name}", thisContact.getNameFirstLast());
            inviteBody.addParseElement("${user.name}", projectContact.getNameFirstLast());
            inviteBody.addParseElement("${project.name}", thisProject.getTitle());
            inviteBody.addParseElement("${project.description}", thisProject.getShortDescription());
            inviteBody.addParseElement("${link}", RequestUtils.getLink(context, "ProjectManagement.do?command=RSVP"));
            //Send the message
            SMTPMessage message = new SMTPMessage();
            message.setHost(prefs.get("MAILSERVER"));
            message.setFrom(prefs.get("EMAILADDRESS"));
            message.addReplyTo(projectContact.getPrimaryEmailAddress(), projectContact.getNameFirstLast());
            message.addTo(thisContact.getPrimaryEmailAddress());
            message.setSubject(inviteSubject.getParsedText());
            message.setBody(inviteBody.getParsedText());
            message.setType("text/html");
            //Send the invitations
            int result = message.send();
          }
        }
        // Determine if there are invited users
        if (thisTeam.size() == 0) {
          return ("ModifyOK");
        } else {
          //Some users not in the system
          if (!"true".equals((String) context.getServletContext().getAttribute("INVITE"))) {
            return "PermissionError";
          }
          //Allow user to invite some users
          context.getRequest().setAttribute("IncludeSection", "team_invite");
          return ("ProjectCenterOK");
        }
      } else {
        return ("ModifyERROR");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandChangeRole(ActionContext context) {
    Connection db = null;
    //Process the params
    String projectId = context.getRequest().getParameter("pid");
    String userId = context.getRequest().getParameter("id");
    String newRole = context.getRequest().getParameter("role");
    try {
      db = this.getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-team-edit-role")) {
        return "PermissionError";
      }
      //Make sure user can change roles
      TeamMember currentMember = new TeamMember(db, thisProject.getId(), getUserId(context));
      if (currentMember.getRoleId() <= TeamMember.PROJECT_LEAD) {
        boolean changed = TeamMember.changeRole(db, thisProject.getId(), Integer.parseInt(userId), Integer.parseInt(newRole));
        if (!changed) {
          return ("ChangeRoleERROR");
        }
        return "ChangeRoleOK";
      }
      return ("ChangeRoleERROR");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}

