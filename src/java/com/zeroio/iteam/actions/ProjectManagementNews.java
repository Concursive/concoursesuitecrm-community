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

//import org.theseus.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DateUtils;

/**
 *  Actions for working with News Articles
 *
 *@author     matt rajkowski
 *@created    June 24, 2003
 *@version    $Id: ProjectManagementNews.java,v 1.1 2003/06/25 04:58:38 matt Exp
 *      $
 */
public final class ProjectManagementNews extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      // Load the project (for access)
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("news_add").toLowerCase());
      // Default the date for the news article to now
      NewsArticle thisArticle = (NewsArticle) context.getFormBean();
      if (thisArticle.getStartDate() == null) {
        thisArticle.setStartDate(DateUtils.roundUpToNextFive(System.currentTimeMillis()));
      }
      return ("ProjectCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String pageReturn = (String) context.getRequest().getParameter("return");
    String newPage = context.getRequest().getParameter("newPage");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      //Process the issue
      NewsArticle thisArticle = (NewsArticle) context.getFormBean();
      thisArticle.setModifiedBy(getUserId(context));
      if (thisArticle.getId() > 0) {
        if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
          return "PermissionError";
        }
        resultCount = thisArticle.update(db);
      } else {
        if (!hasProjectAccess(context, db, thisProject, "project-news-add")) {
          return "PermissionError";
        }
        thisArticle.setProjectId(thisProject.getId());
        thisArticle.setEnteredBy(getUserId(context));
        recordInserted = thisArticle.insert(db);
      }
      if (!recordInserted && resultCount <= 0) {
        processErrors(context, thisArticle.getErrors());
        processWarnings(context, thisArticle.getWarnings());
      }
      if (recordInserted || resultCount == 1) {
        indexAddItem(context, thisArticle);
        if (newPage != null && newPage.equals("true")) {
          return (executeCommandAddPage(context));
        }
        if (recordInserted || "list".equals(pageReturn)) {
          return ("SaveOK");
        } else {
          return ("SaveDetailsOK");
        }
      } else {
        return (executeCommandAdd(context));
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
  public String executeCommandAddPage(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project (for access)
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Check the bean
      NewsArticle thisArticle = (NewsArticle) context.getFormBean();
      thisArticle.queryRecord(db, thisArticle.getId());
      context.getRequest().setAttribute("IncludeSection", ("news_add_page").toLowerCase());
      return ("ProjectCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSavePage(ActionContext context) {
    Connection db = null;
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
      context.getRequest().setAttribute("IncludeSection", ("news_add").toLowerCase());
      //Process the issue
      NewsArticle thisArticle = (NewsArticle) context.getFormBean();
      thisArticle.setModifiedBy(getUserId(context));
      if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
        return "PermissionError";
      }
      resultCount = thisArticle.updatePage(db);
      if (resultCount <= 0) {
        processErrors(context, thisArticle.getErrors());
      }
      if (resultCount == 1) {
        thisArticle = new NewsArticle(db, thisArticle.getId(), thisProject.getId());
        indexAddItem(context, thisArticle);
        return ("SavePageOK");
      } else {
        return (executeCommandAddPage(context));
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
  public String executeCommandDeletePage(ActionContext context) {
    Connection db = null;
    int resultCount = -1;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String id = (String) context.getRequest().getParameter("id");
    String pageReturn = (String) context.getRequest().getParameter("return");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("news_add").toLowerCase());
      //Process the issue
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(id), thisProject.getId());
      thisArticle.setModifiedBy(getUserId(context));
      if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
        return "PermissionError";
      }
      resultCount = thisArticle.deletePage(db);
      if (resultCount <= 0) {
        processErrors(context, thisArticle.getErrors());
      }
      indexAddItem(context, thisArticle);
      return ("DeletePageOK");
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
  public String executeCommandEdit(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newsId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("news_add").toLowerCase());
      //Load the news article
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      context.getRequest().setAttribute("newsArticle", thisArticle);
      return ("ProjectCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
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
    String newsId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-delete")) {
        return "PermissionError";
      }
      //Load the issue
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      thisArticle.delete(db);
      indexDeleteItem(context, thisArticle);
      return ("DeleteOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newsId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("news_details").toLowerCase());
      //Load the news article
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      //Check article to see if user can view this type
      if (thisArticle.getStatus() == NewsArticle.DRAFT && !hasProjectAccess(context, db, thisProject, "project-news-view-unreleased")) {
        return "PermissionError";
      } else if (thisArticle.getStatus() == NewsArticle.UNAPPROVED && !hasProjectAccess(context, db, thisProject, "project-news-view-unreleased")) {
        return "PermissionError";
      }
      //TODO: Add archived permission check too
      context.getRequest().setAttribute("newsArticle", thisArticle);
      if (isPopup(context)) {
        return ("NewsDetailsPopupOK");
      } else {
        return ("ProjectCenterOK");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}

