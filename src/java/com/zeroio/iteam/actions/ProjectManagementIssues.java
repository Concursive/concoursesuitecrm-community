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
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.admin.base.User;
//import com.zeroio.controller.*;
/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    November 29, 2001
 *@version    $Id: ProjectManagementIssues.java,v 1.9.160.1 2004/07/07 15:12:07
 *      mrajkowski Exp $
 */
public final class ProjectManagementIssues extends CFSModule {

  public final static String lf = System.getProperty("line.separator");


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    String categoryId = context.getRequest().getParameter("cid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project (for access)
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      //Load the category
      IssueCategory issueCategory = new IssueCategory(db, Integer.parseInt(categoryId), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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
  public String executeCommandSave(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String pageReturn = (String) context.getRequest().getParameter("return");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      //Process the issue
      Issue thisIssue = (Issue) context.getFormBean();
      thisIssue.setModifiedBy(getUserId(context));
      if (thisIssue.getId() > 0) {
        if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-edit")) {
          return "PermissionError";
        }
        resultCount = thisIssue.update(db);
        indexAddItem(context, thisIssue);
      } else {
        if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-add")) {
          return "PermissionError";
        }
        thisIssue.setProjectId(thisProject.getId());
        thisIssue.setEnteredBy(getUserId(context));
        recordInserted = thisIssue.insert(db);
        indexAddItem(context, thisIssue);
      }
      if (!recordInserted && resultCount <= 0) {
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
      if (recordInserted || resultCount == 1) {
        if (recordInserted || "list".equals(pageReturn)) {
          return ("SaveOK");
        } else {
          return ("SaveDetailsOK");
        }
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to view the messages within a topic
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    if (projectId == null) {
      projectId = (String) context.getRequest().getAttribute("pid");
    }
    String issueId = (String) context.getRequest().getParameter("iid");
    if (issueId == null) {
      issueId = (String) context.getRequest().getAttribute("iid");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_details").toLowerCase());
      //Load the issue data
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      //Add paged list
      if ("true".equals(context.getRequest().getParameter("resetList"))) {
        this.deletePagedListInfo(context, "projectIssueRepliesInfo");
      }
      PagedListInfo replyInfo = this.getPagedListInfo(context, "projectIssueRepliesInfo");
      replyInfo.setLink("ProjectManagementIssues.do?command=Details&pid=" + thisProject.getId() + "&iid=" + thisIssue.getId() + "&cid=" + thisIssue.getCategoryId());
      thisIssue.getReplyList().setPagedListInfo(replyInfo);
      thisIssue.buildReplyList(db);
      context.getRequest().setAttribute("Issue", thisIssue);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandEdit(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      //Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      //Load the issue category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-delete")) {
        return "PermissionError";
      }
      //Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      //Load the issue category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      thisIssue.setProjectId(thisProject.getId());
      thisIssue.setCategoryId(issueCategory.getId());
      thisIssue.delete(db);
      indexDeleteItem(context, thisIssue);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("DeleteOK");
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
  public String executeCommandReply(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    String replyId = (String) context.getRequest().getParameter("rid");
    String quote = (String) context.getRequest().getParameter("quote");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-messages-reply")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_reply").toLowerCase());
      //Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      //Load the category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      //Prepare the reply
      IssueReply issueReply = new IssueReply();
      issueReply.setSubject("RE: " + thisIssue.getSubject());
      if ("true".equals(quote)) {
        if (replyId != null) {
          IssueReply quoteMessage = new IssueReply(db, Integer.parseInt(replyId));
          User quoteUser = new User(db, quoteMessage.getEnteredBy());
          issueReply.setBody(quoteUser.getContact().getNameFirstLast() + " wrote:" + lf + quoteMessage.getBody() + lf + "-----" + lf + lf);
        } else {
          User quoteUser = new User(db, thisIssue.getEnteredBy());
          issueReply.setBody(quoteUser.getContact().getNameFirstLast() + " wrote:" + lf + thisIssue.getBody() + lf + "-----" + lf + lf);
        }
      }
      context.getRequest().setAttribute("IssueReply", issueReply);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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
  public String executeCommandEditReply(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    String replyId = (String) context.getRequest().getParameter("rid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      //TODO: Additional permission to allow owner to edit
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-messages-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_reply").toLowerCase());
      //Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      //Load the category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      //Load the reply
      IssueReply issueReply = new IssueReply(db, Integer.parseInt(replyId));
      context.getRequest().setAttribute("IssueReply", issueReply);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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
  public String executeCommandSaveReply(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      //TODO: Additional permission to allow owner to edit
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-messages-reply")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      //Load the issue category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      //Process the reply
      IssueReply issueReply = (IssueReply) context.getFormBean();
      issueReply.setModifiedBy(getUserId(context));
      if (issueReply.getId() > 0) {
        resultCount = issueReply.update(db);
        indexAddItem(context, issueReply);
      } else {
        issueReply.setIssueId(thisIssue.getId());
        issueReply.setEnteredBy(getUserId(context));
        recordInserted = issueReply.insert(db);
        indexAddItem(context, issueReply);
      }
      if (!recordInserted && resultCount <= 0) {
        processErrors(context, issueReply.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted || resultCount == 1) {
        return ("SaveOK");
      } else {
        return (executeCommandReply(context));
      }
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
  public String executeCommandDeleteReply(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    String replyId = (String) context.getRequest().getParameter("rid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      //TODO: Additional permission to allow owner to delete own
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-messages-delete")) {
        return "PermissionError";
      }
      //Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      //Load the category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      //Load the reply
      IssueReply issueReply = new IssueReply(db, Integer.parseInt(replyId));
      issueReply.setProjectId(thisProject.getId());
      issueReply.setCategoryId(issueCategory.getId());
      issueReply.delete(db);
      indexDeleteItem(context, issueReply);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("DeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

