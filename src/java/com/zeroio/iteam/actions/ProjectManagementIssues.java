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
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.modules.contacts.base.Contact;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
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
	  
/* 	if (!(hasPermission(context, "projects-issues-add"))) {
	    return ("PermissionError");
    	}
 */
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");

    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      
      LookupList categoryList = new LookupList(db, "lookup_project_issues");
      categoryList.addItem(-1, "-- Select Category --");
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
	  
/* 	if (!(hasPermission(context, "projects-issues-add"))) {
	    return ("PermissionError");
    	}
 */
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
	  
/* 	if (!(hasPermission(context, "projects-issues-view"))) {
	    return ("PermissionError");
    	}
 */
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
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
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
	  
/* 	if (!(hasPermission(context, "projects-issues-edit"))) {
	    return ("PermissionError");
    	}
 */
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
      
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      
      LookupList categoryList = new LookupList(db, "lookup_project_issues");
      categoryList.addItem(-1, "-- Select Category --");
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
	  
/* 	if (!(hasPermission(context, "projects-issues-add"))) {
	    return ("PermissionError");
    	}
 */
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
      
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      
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
	  
/* 	if (!(hasPermission(context, "projects-issues-add"))) {
	    return ("PermissionError");
    	}
 */
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
      
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      
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

