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
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    November 29, 2001
 *@version    $Id$
 */
public final class ProjectManagementIssues extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");

    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      
      LookupList categoryList = new LookupList(db, "lookup_project_issues");
      categoryList.addItem(0, "-- Select Category --");
      context.getRequest().setAttribute("CategoryList", categoryList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "AddItem", "");
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    String projectId = (String)context.getRequest().getParameter("pid");

    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());

      Issue thisIssue = (Issue)context.getFormBean();
      thisIssue.setProjectId(thisProject.getId());
      thisIssue.setEnteredBy(getUserId(context));
      thisIssue.setModifiedBy(getUserId(context));
      recordInserted = thisIssue.insert(db);
      if (!recordInserted) {
        processErrors(context, thisIssue.getErrors());
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
        return ("AddOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");
    if (projectId == null) { projectId = (String)context.getRequest().getAttribute("pid"); }
    String issueId = (String)context.getRequest().getParameter("iid");
    if (issueId == null) { issueId = (String)context.getRequest().getAttribute("iid"); }

    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_details").toLowerCase());
      
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      Contact user = this.getUser(context, thisIssue.getEnteredBy()).getContact();
      thisIssue.setUser(user.getNameFirstLast()); 
      thisIssue.buildReplyList(db);
      Iterator i = thisIssue.getReplyList().iterator();
      while (i.hasNext()) {
        IssueReply issueReply = (IssueReply)i.next();
        Contact userReply = this.getUser(context, issueReply.getEnteredBy()).getContact();
        issueReply.setUser(userReply.getNameFirstLast()); 
      }
      context.getRequest().setAttribute("Issue", thisIssue);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Details", "");
    if (errorMessage == null) {
      if (context.getRequest().getParameter("popup") != null) {
        return ("PopupOK");
      } else {
        return ("ProjectCenterOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");
    String issueId = (String)context.getRequest().getParameter("iid");

    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_modify").toLowerCase());
      
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      
      LookupList categoryList = new LookupList(db, "lookup_project_issues");
      categoryList.addItem(0, "-- Select Category --");
      context.getRequest().setAttribute("CategoryList", categoryList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Modify", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandReply(ActionContext context) {
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");
    String issueId = (String)context.getRequest().getParameter("iid");

    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_reply").toLowerCase());
      
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      
      IssueReply issueReply = new IssueReply();
      issueReply.setSubject("RE: " + thisIssue.getSubject());
      context.getRequest().setAttribute("IssueReply", issueReply);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Add Reply", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandInsertReply(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    String projectId = (String)context.getRequest().getParameter("pid");
    String issueId = (String)context.getRequest().getParameter("iid");

    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);

      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      
      IssueReply issueReply = (IssueReply)context.getFormBean();
      issueReply.setIssueId(thisIssue.getId());
      issueReply.setEnteredBy(getUserId(context));
      issueReply.setModifiedBy(getUserId(context));
      recordInserted = issueReply.insert(db);
      if (!recordInserted) {
        processErrors(context, issueReply.getErrors());
        context.getRequest().setAttribute("IncludeSection", ("issues_reply").toLowerCase());
      } else {
        context.getRequest().setAttribute("IncludeSection", ("issues_details").toLowerCase());
        context.getRequest().setAttribute("pid", projectId);
        context.getRequest().setAttribute("iid", issueId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("AddOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

}

