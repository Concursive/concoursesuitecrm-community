/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.*;
import java.security.*;
import org.aspcfs.utils.PrivateString;
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
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
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
        selCurrentTeam.addItem(thisMember.getUserId(), getUser(context, thisMember.getUserId()).getContact().getNameFirstLast() + (thisMember.getStatus() == TeamMember.STATUS_ADDED ? "" : " *"));
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
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-team-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("team_modify").toLowerCase());
      context.getRequest().setAttribute("pid", projectId);
      // TODO: Add the emailing feature when new users are added to a project
      //Process the members
      TeamMemberList thisTeam = (TeamMemberList) context.getFormBean();
      thisTeam.setProjectId(thisProject.getId());
      thisTeam.setUserLevel(getUserLevel(context, db, TeamMember.GUEST));
      thisTeam.setEnteredBy(getUserId(context));
      thisTeam.setModifiedBy(getUserId(context));
      recordInserted = thisTeam.update(db);
      if (recordInserted) {
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
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
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
  /*
   *  public String executeCommandSendInvitations(ActionContext context) {
   *  Connection db = null;
   *  /Parameters
   *  String projectId = (String) context.getRequest().getParameter("pid");
   *  try {
   *  Key key = (Key) context.getServletContext().getAttribute("TEAM.KEY");
   *  db = getConnection(context);
   *  /Project permissions
   *  Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
   *  thisProject.buildPermissionList(db);
   *  if (!hasProjectAccess(context, db, thisProject, "project-team-edit")) {
   *  return "PermissionError";
   *  }
   *  if (!"true".equals((String) context.getServletContext().getAttribute("INVITE")) &&
   *  !getUser(context).getAccessInvite()) {
   *  return "PermissionError";
   *  }
   *  context.getRequest().setAttribute("Project", thisProject);
   *  context.getRequest().setAttribute("IncludeSection", "team_invite_status");
   *  context.getRequest().setAttribute("pid", projectId);
   *  String filePath = this.getPath(context, "templates");
   *  ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
   *  /Process the invitations
   *  InvitationList invitations = new InvitationList(context.getRequest());
   *  invitations.setProjectId(thisProject.getId());
   *  Iterator i = invitations.iterator();
   *  while (i.hasNext()) {
   *  Invitation thisInvitation = (Invitation) i.next();
   *  if (thisInvitation.isValid()) {
   *  /Create unregistered user
   *  User thisUser = new User();
   *  thisUser.setFirstName(thisInvitation.getFirstName());
   *  thisUser.setLastName(thisInvitation.getLastName());
   *  thisUser.setUsername(thisInvitation.getEmail());
   *  thisUser.setPassword("unregistered");
   *  thisUser.setEmail(thisInvitation.getEmail());
   *  thisUser.setGroupId(1);
   *  thisUser.setDepartmentId(1);
   *  thisUser.setEnteredBy(getUser(context).getId());
   *  thisUser.setRegistered(false);
   *  thisUser.insert(db, context);
   *  /INSERT INTO THE CACHE
   *  updateUserCache(context, thisUser.getId(), thisUser.getNameFirstLast());
   *  /Insert user into project as pending
   *  TeamMember thisMember = new TeamMember();
   *  thisMember.setProjectId(thisProject.getId());
   *  thisMember.setUserId(thisUser.getId());
   *  thisMember.setUserLevel(getUserLevel(context, db, TeamMember.GUEST));
   *  thisMember.setStatus(TeamMember.STATUS_INVITING);
   *  thisMember.setEnteredBy(getUserId(context));
   *  thisMember.setModifiedBy(getUserId(context));
   *  thisMember.insert(db);
   *  /Load the templates
   *  Template inviteSubject = new Template(StringUtils.loadText(filePath + (String) context.getServletContext().getAttribute("INVITE.SUBJECT")));
   *  Template inviteBody = new Template(StringUtils.loadText(filePath + (String) context.getServletContext().getAttribute("INVITE.MESSAGE")));
   *  /Add text tags for invitation
   *  String data = java.net.URLEncoder.encode(PrivateString.encrypt(key, "id=" + thisUser.getId() + ",pid=" + thisProject.getId()), "UTF-8");
   *  inviteBody.addParseElement("${invite.firstName}", thisUser.getContact().getNameFirst());
   *  inviteBody.addParseElement("${invite.lastName}", thisUser.getContact().getNameLast());
   *  inviteBody.addParseElement("${invite.name}", thisUser.getContact().getNameFirstLast());
   *  inviteBody.addParseElement("${user.name}", getUser(context, getUserId(context)).getContact().getNameFirstLast());
   *  inviteBody.addParseElement("${project.name}", thisProject.getTitle());
   *  inviteBody.addParseElement("${project.description}", thisProject.getShortDescription());
   *  inviteBody.addParseElement("${link.accept}", HTTPUtils.getServerUrl(context.getRequest()) + "/LoginAccept.do?data=" + data);
   *  inviteBody.addParseElement("${link.reject}", HTTPUtils.getServerUrl(context.getRequest()) + "/LoginReject.do?data=" + data);
   *  inviteBody.addParseElement("${link.info}", HTTPUtils.getServerUrl(context.getRequest()));
   *  inviteBody.addParseElement("\r\n", "");
   *  inviteBody.addParseElement("\r", "");
   *  inviteBody.addParseElement("\n", "");
   *  /Prepare the invitation
   *  ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
   *  SMTPMessage message = new SMTPMessage();
   *  message.setHost(prefs.get("MAILSERVER"));
   *  message.setFrom(getUser(context).getNameFirstLast() + " <" + getUser(context).getEmail() + ">");
   *  message.addReplyTo(getUser(context).getNameFirstLast() + " <" + getUser(context).getEmail() + ">");
   *  message.addTo(thisInvitation.getEmail());
   *  message.setSubject(inviteSubject.getParsedText());
   *  message.setBody(inviteBody.getParsedText());
   *  message.setType("text/html");
   *  /Send the invitations
   *  int result = message.send();
   *  if (result == 0) {
   *  /Record that message was delivered
   *  thisMember.setStatus(TeamMember.STATUS_PENDING);
   *  thisInvitation.setSentMail(true);
   *  } else {
   *  /Record that message was not delivered
   *  thisMember.setStatus(TeamMember.STATUS_MAILERROR);
   *  thisInvitation.setSentMail(false);
   *  System.out.println("ProjectManagementTeam-> MAIL ERROR: " + message.getErrorMsg());
   *  }
   *  thisMember.updateStatus(db);
   *  }
   *  }
   *  context.getRequest().setAttribute("invitationList", invitations);
   *  } catch (Exception errorMessage) {
   *  context.getRequest().setAttribute("Error", errorMessage);
   *  return ("SystemError");
   *  } finally {
   *  freeConnection(context, db);
   *  }
   *  return "InvitationsOK";
   *  }
   */
}

