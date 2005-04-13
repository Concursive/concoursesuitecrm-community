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
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.tasks.base.TaskCategoryList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.util.Iterator;
import java.util.StringTokenizer;

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
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
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
      // Prepare the list of categories to display
      NewsArticleCategoryList categoryList = new NewsArticleCategoryList();
      categoryList.setProjectId(thisProject.getId());
      categoryList.setEnabled(Constants.TRUE);
      categoryList.buildList(db);
      context.getRequest().setAttribute("newsArticleCategoryList", categoryList);
      // Prepare the list of templates to display
      LookupList templateList = new LookupList(db, "lookup_news_template");
      context.getRequest().setAttribute("portalTemplateList", templateList);
      // Prepare the list of Lists to display
      TaskCategoryList taskCategoryList = new TaskCategoryList();
      taskCategoryList.setProjectId(thisProject.getId());
      //taskCategoryList.setEnabled(Constants.TRUE);
      taskCategoryList.buildList(db);
      context.getRequest().setAttribute("taskCategoryList", taskCategoryList);
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
    boolean isValid = false;
    int resultCount = -1;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String pageReturn = (String) context.getRequest().getParameter("return");
    String newPage = context.getRequest().getParameter("newPage");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("issues_add").toLowerCase());
      //Process the issue
      NewsArticle thisArticle = (NewsArticle) context.getFormBean();
      thisArticle.setModifiedBy(getUserId(context));
      thisArticle.setProjectId(thisProject.getId());
        if (thisArticle.getId() > 0) {
        if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, thisArticle);
        if (isValid) {
          resultCount = thisArticle.update(db);
        }
      } else {
        if (!hasProjectAccess(context, db, thisProject, "project-news-add")) {
          return "PermissionError";
        }
        thisArticle.setEnteredBy(getUserId(context));
        isValid = this.validateObject(context, db, thisArticle);
        if (isValid) {
          recordInserted = thisArticle.insert(db);
        }
      }
      if (recordInserted || resultCount == 1) {
        indexAddItem(context, thisArticle);
        if (newPage != null && newPage.equals("true")) {
          return (executeCommandAddPage(context));
        }
        if (isPopup(context)) {
          return "PopupCloseOK";
        }
        if (recordInserted || "list".equals(pageReturn)) {
          return ("SaveOK");
        } else {
          return ("SaveDetailsOK");
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return (executeCommandAdd(context));
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
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
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
    boolean isValid = false;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("news_add").toLowerCase());
      //Process the issue
      NewsArticle thisArticle = (NewsArticle) context.getFormBean();
      thisArticle.setModifiedBy(getUserId(context));
      if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
        return "PermissionError";
      }
      isValid = this.validateObject(context, db, thisArticle);
      if (isValid) {
        resultCount = thisArticle.updatePage(db);
      }
      if (resultCount == 1) {
        thisArticle = new NewsArticle(db, thisArticle.getId(), thisProject.getId());
        indexAddItem(context, thisArticle);
        return ("SavePageOK");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return (executeCommandAddPage(context));
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
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
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
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("news_add").toLowerCase());
      //Load the news article
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      context.getRequest().setAttribute("newsArticle", thisArticle);
      // Prepare the list of categories to display
      NewsArticleCategoryList categoryList = new NewsArticleCategoryList();
      categoryList.setProjectId(thisProject.getId());
      categoryList.setEnabled(Constants.TRUE);
      categoryList.setIncludeId(thisArticle.getCategoryId());
      categoryList.buildList(db);
      context.getRequest().setAttribute("newsArticleCategoryList", categoryList);
      // Prepare the list of templates to display
      LookupList templateList = new LookupList(db, "lookup_news_template");
      context.getRequest().setAttribute("portalTemplateList", templateList);
      // Prepare the list of Lists to display
      TaskCategoryList taskCategoryList = new TaskCategoryList();
      taskCategoryList.setProjectId(thisProject.getId());
      //taskCategoryList.setEnabled(Constants.TRUE);
      taskCategoryList.buildList(db);
      context.getRequest().setAttribute("taskCategoryList", taskCategoryList);
      if (isPopup(context)) {
        return "NewsEditPopupOK";
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
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newsId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-delete")) {
        return "PermissionError";
      }
      //Load the issue
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      thisArticle.delete(db, this.getPath(context, "projects-news"));
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
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
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
  
  
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandEditCategoryList(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String previousId = (String) context.getRequest().getParameter("previousId");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-add")) {
        return "PermissionError";
      }
      // Load the category list
      NewsArticleCategoryList categoryList = new NewsArticleCategoryList();
      categoryList.setProjectId(thisProject.getId());
      categoryList.setEnabled(Constants.TRUE);
      categoryList.buildList(db);
      context.getRequest().setAttribute("editList", categoryList.getHtmlSelect());
      // Edit List properties
      context.getRequest().setAttribute("subTitle", "Modify this project's article categories");
      context.getRequest().setAttribute("returnUrl", "ProjectManagementNews.do?command=SaveCategoryList&pid=" + thisProject.getId() + "&previousId=" + previousId);
      return ("EditListPopupOK");
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
  public String executeCommandSaveCategoryList(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String previousId = (String) context.getRequest().getParameter("previousId");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-add")) {
        return "PermissionError";
      }
      // Parse the request for items
      String[] params = context.getRequest().getParameterValues("selectedList");
      String[] names = new String[params.length];
      int j = 0;
      StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("selectNames"), "^");
      while (st.hasMoreTokens()) {
        names[j] = (String) st.nextToken();
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ProjectManagementNews-> Item: " + names[j]);
        }
        j++;
      }
      // Load the previous category list
      NewsArticleCategoryList categoryList = new NewsArticleCategoryList();
      categoryList.setProjectId(thisProject.getId());
      categoryList.buildList(db);
      categoryList.updateValues(db, params, names);
      // Reload the updated list for display
      categoryList.clear();
      categoryList.setEnabled(Constants.TRUE);
      categoryList.setIncludeId(previousId);
      categoryList.buildList(db);
      HtmlSelect thisSelect = categoryList.getHtmlSelect();
      thisSelect.addItem(-1, "-- None --", 0);
      context.getRequest().setAttribute("editList", thisSelect);
      return ("EditListPopupCloseOK");
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
  public String executeCommandClone(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newsId = (String) context.getRequest().getParameter("id");
    boolean recordInserted = false;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //context.getRequest().setAttribute("IncludeSection", ("news_add").toLowerCase());
      //Load the news article
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      context.getRequest().setAttribute("newsArticle", thisArticle);
      // Insert a copy of the article
      thisArticle.setEnteredBy(getUserId(context));
      thisArticle.setEntered("");
      thisArticle.setModifiedBy(getUserId(context));
      thisArticle.setModified("");
      thisArticle.setStatus(NewsArticle.DRAFT);
      recordInserted = thisArticle.insert(db);
      if (recordInserted) {
        indexAddItem(context, thisArticle);
      } else {
        processErrors(context, thisArticle.getErrors());
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      return ("CloneOK");
    } else {
      return ("CloneERROR");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandEmailMe(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newsId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-view")) {
        return "PermissionError";
      }
      // Load the article and send the email
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      if (1 == 1) {
        NewsArticleEmail newsArticleEmail = new NewsArticleEmail(getDbNamePath(context) + "templates.xml", thisArticle, context);
        Contact projectContact = new Contact(db, getUser(context, getUserId(context)).getContact().getId());
        // Prepare the email
        SMTPMessage mail = new SMTPMessage();
        mail.setHost(getPref(context, "MAILSERVER"));
        mail.setFrom(getPref(context, "EMAILADDRESS"));
        mail.addReplyTo(projectContact.getPrimaryEmailAddress(), projectContact.getNameFirstLast());
        mail.setType("text/html");
        mail.setSubject(newsArticleEmail.getSubject());
        mail.setBody(newsArticleEmail.getBody());
        // Send it...
        mail.addTo(projectContact.getPrimaryEmailAddress());
        mail.send();
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "news_email_ok");
      context.getRequest().setAttribute("pid", projectId);
      return "EmailMeOK";
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
  public String executeCommandEmailTeam(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newsId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-news-add")) {
        return "PermissionError";
      }
      // Load the article and send the email
      NewsArticle thisArticle = new NewsArticle(db, Integer.parseInt(newsId), thisProject.getId());
      Contact projectContact = new Contact(db, getUser(context, getUserId(context)).getContact().getId());
      if (1 == 1) {
        // Load the templates
        NewsArticleEmail newsArticleEmail = new NewsArticleEmail(getDbNamePath(context) + "templates.xml", thisArticle, context);
        // Prepare the email
        SMTPMessage mail = new SMTPMessage();
        mail.setHost(getPref(context, "MAILSERVER"));
        mail.setFrom(getPref(context, "EMAILADDRESS"));
        mail.addReplyTo(projectContact.getPrimaryEmailAddress(), projectContact.getNameFirstLast());
        mail.setType("text/html");
        mail.setSubject(newsArticleEmail.getSubject());
        mail.setBody(newsArticleEmail.getBody());
        // Sending to...
        TeamMemberList members = new TeamMemberList();
        members.setProjectId(thisProject.getId());
        members.buildList(db);
        // Send message to each individual user...
        Iterator userList = members.iterator();
        while (userList.hasNext()) {
          TeamMember thisMember = (TeamMember) userList.next();
          User thisUser = new User(db, thisMember.getUserId());
          Contact thisContact = new Contact(db, thisUser.getContactId());
          String email = thisContact.getPrimaryEmailAddress();
          if (email != null) {
            mail.setTo(email);
            mail.send();
          }
        }
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "news_email_ok");
      context.getRequest().setAttribute("pid", projectId);
      return "EmailTeamOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}

