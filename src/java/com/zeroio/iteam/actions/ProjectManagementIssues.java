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

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Issue;
import com.zeroio.iteam.base.IssueCategory;
import com.zeroio.iteam.base.IssueReply;
import com.zeroio.iteam.base.Project;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

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
    String projectId = (String) context.getRequest().getParameter("pid");
    String categoryId = context.getRequest().getParameter("cid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project (for access)
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    boolean isValid = false;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String pageReturn = (String) context.getRequest().getParameter("return");
    try {
      //Process the issue
      Issue thisIssue = (Issue) context.getFormBean();
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      //Process the issue
      thisIssue.setModifiedBy(getUserId(context));
      if (thisIssue.getId() > 0) {
        Issue previousIssue = new Issue(db, thisIssue.getId(), thisProject.getId());
        if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-edit") &&
            previousIssue.getEnteredBy() != getUserId(context)) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, thisIssue);
        if (isValid) {
          resultCount = thisIssue.update(db);
          indexAddItem(context, thisIssue);
        }
      } else {
        if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-add")) {
          return "PermissionError";
        }
        thisIssue.setProjectId(thisProject.getId());
        thisIssue.setEnteredBy(getUserId(context));
        isValid = this.validateObject(context, db, thisIssue);
        if (isValid) {
          recordInserted = thisIssue.insert(db);
          indexAddItem(context, thisIssue);
        }
      }
      if (recordInserted || resultCount > 0 || isValid) {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted || resultCount == 1) {
      if (recordInserted || "list".equals(pageReturn)) {
        return ("SaveOK");
      } else {
        return ("SaveDetailsOK");
      }
    } else {
      return (executeCommandAdd(context));
    }
  }


  /**
   *  Action to view the messages within a topic
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
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
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_details").toLowerCase());
      //Load the issue data
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      thisIssue.buildFiles(db);
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      //Add paged list
      if ("true".equals(context.getRequest().getParameter("resetList"))) {
        this.deletePagedListInfo(context, "projectIssueRepliesInfo");
      }
      PagedListInfo replyInfo = this.getPagedListInfo(context, "projectIssueRepliesInfo");
      replyInfo.setLink("ProjectManagementIssues.do?command=Details&pid=" + thisProject.getId() + "&iid=" + thisIssue.getId() + "&cid=" + thisIssue.getCategoryId());
      thisIssue.getReplyList().setPagedListInfo(replyInfo);
      thisIssue.getReplyList().setBuildFiles(true);
      thisIssue.buildReplyList(db);
      context.getRequest().setAttribute("Issue", thisIssue);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String popUp = context.getRequest().getParameter("popup");
    if (popUp != null && !"null".equals(popUp)) {
      return ("PopupOK");
    } else {
      return ("ProjectCenterOK");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandEdit(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      // Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      // Load the issue category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      // Check permissions
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-topics-edit") &&
          thisIssue.getEnteredBy() != getUserId(context)) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
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
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
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
      String filePath = this.getPath(context, "projects");
      thisIssue.delete(db, filePath);
      indexDeleteItem(context, thisIssue);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DeleteOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReply(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    String replyId = (String) context.getRequest().getParameter("rid");
    String quote = (String) context.getRequest().getParameter("quote");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
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
  public String executeCommandEditReply(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    String replyId = (String) context.getRequest().getParameter("rid");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      // Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      // Load the category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      // Load the reply
      IssueReply issueReply = new IssueReply(db, Integer.parseInt(replyId));
      context.getRequest().setAttribute("IssueReply", issueReply);
      //Additional permission to allow owner to edit
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-messages-edit") && 
          issueReply.getEnteredBy() != getUserId(context)) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_reply").toLowerCase());
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
  public String executeCommandSaveReply(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    boolean isValid = false;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    try {
      IssueReply issueReply = (IssueReply) context.getFormBean();
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      //Load the issue
      Issue thisIssue = new Issue(db, Integer.parseInt(issueId), thisProject.getId());
      context.getRequest().setAttribute("Issue", thisIssue);
      //Load the issue category
      IssueCategory issueCategory = new IssueCategory(db, thisIssue.getCategoryId(), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", issueCategory);
      //Process the reply
      issueReply.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, issueReply);
      if (isValid) {
        if (issueReply.getId() > 0) {
          IssueReply previousReply = new IssueReply(db, issueReply.getId(), thisIssue.getId());
          //Additional permission to allow owner to edit
          if (!hasProjectAccess(context, db, thisProject, "project-discussion-messages-edit") &&
              previousReply.getEnteredBy() != getUserId(context)) {
            return "PermissionError";
          }
          resultCount = issueReply.update(db);
          indexAddItem(context, issueReply);
        } else {
          if (!hasProjectAccess(context, db, thisProject, "project-discussion-messages-reply")) {
            return "PermissionError";
          }
          issueReply.setIssueId(thisIssue.getId());
          issueReply.setEnteredBy(getUserId(context));
          recordInserted = issueReply.insert(db);
          indexAddItem(context, issueReply);
        }
      } 
      if (!isValid || (!recordInserted && resultCount <= 0)) {
        processErrors(context, issueReply.getErrors());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (isValid && (recordInserted || resultCount == 1)) {
      return ("SaveOK");
    }
    return executeCommandReply(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteReply(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String issueId = (String) context.getRequest().getParameter("iid");
    String replyId = (String) context.getRequest().getParameter("rid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
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
      String filePath = this.getPath(context, "projects");
      issueReply.delete(db, filePath);
      indexDeleteItem(context, issueReply);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DeleteOK");
  }
}

