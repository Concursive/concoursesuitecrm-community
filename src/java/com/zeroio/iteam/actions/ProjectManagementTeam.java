/*
 *  Copyright 2001 Dark Horse Ventures
 *  Uses iteam objects from matt@zeroio.com http://www.mavininteractive.com
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;

/**
 *  Project Management module for CFS
 *
 *@author     matt
 *@created    November 12, 2001
 *@version    $Id$
 */
public final class ProjectManagementTeam extends CFSModule {

  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    
    String projectId = (String)context.getRequest().getParameter("pid");
    
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
      
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "-- Select Department --");
      context.getRequest().setAttribute("DepartmentList", departmentList);
      
      TeamMemberList team = new TeamMemberList();
      team.setProjectId(thisProject.getId());
      team.buildList(db);
      //context.getRequest().setAttribute("Team", team);
      
      StringBuffer vector1 = new StringBuffer();
      StringBuffer vector2 = new StringBuffer();
      StringBuffer vector3 = new StringBuffer();
      StringBuffer vector4 = new StringBuffer();
      StringBuffer vector5 = new StringBuffer();
      
      Iterator iName = userList.iterator();
      while (iName.hasNext()) {
        User thisUser = (User)iName.next();
        vector1.append(thisUser.getContact().getNameFirst() + " " + thisUser.getContact().getNameLast());
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
        TeamMember thisMember = (TeamMember)iTeam.next();
        Contact thisContact = userList.getUser(thisMember.getUserId()).getContact();
        selCurrentTeam.addItem(thisMember.getUserId(), thisContact.getNameFirst() + " " + thisContact.getNameLast());
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
  
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    
    String projectId = (String)context.getRequest().getParameter("pid");
    
    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("team_modify").toLowerCase());
      
      TeamMemberList thisTeam = (TeamMemberList)context.getFormBean();
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
