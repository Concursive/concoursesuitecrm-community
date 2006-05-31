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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.tasks.base.TaskCategoryList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Project Management module
 *
 * @author matt rajkowski
 * @version $Id: ProjectManagement.java,v 1.1.1.1 2002/01/14 19:49:26
 *          mrajkowski Exp $
 * @created November 6, 2001
 */
public final class ProjectManagement extends CFSModule {

  /**
   * Show the Project List by default
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandOverview(context);
  }


  /**
   * Lists the users in the system that have a corresponding contact record
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.6
   */
  public String executeCommandPersonalView(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "projects-personal-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    ProjectList projects = new ProjectList();
    try {
      db = getConnection(context);
      //Project Info
      projects.setGroupId(-1);
      projects.setOpenProjectsOnly(true);
      projects.setProjectsForUser(this.getUserId(context));
      projects.setIncludeGuestProjects(true);
      //Assignment Info
      projects.setBuildAssignments(true);
      projects.setAssignmentsForUser(this.getUserId(context));
      projects.setOpenAssignmentsOnly(true);
      projects.setWithAssignmentDaysComplete(6);
      //Issue Info
      projects.setBuildIssues(true);
      projects.setLastIssues(3);
      projects.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ProjectList", projects);
    return ("PersonalViewOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandEnterpriseView(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "projects-enterprise-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      ProjectList projects = new ProjectList();
      PagedListInfo projectListInfo = this.getPagedListInfo(
          context, "projectEnterpriseInfo");
      projectListInfo.setLink("ProjectManagement.do?command=EnterpriseView");
      if (projectListInfo.getListView() == null) {
        projectListInfo.setItemsPerPage(0);
        // My open projects
        projectListInfo.setListView("open");
        //Assignments
        projectListInfo.addFilter(1, "hide");
        //Topics
        projectListInfo.addFilter(2, "hide");
        //News
        projectListInfo.addFilter(3, "hide");
      }
      projects.setPagedListInfo(projectListInfo);
      //Project Info
      projects.setGroupId(-1);
      projects.setProjectsForUser(getUserId(context));
      projects.setIncludeGuestProjects(true);
      projects.setPortalState(Constants.FALSE);
      if (projectListInfo.getListView().equals("open")) {
        projects.setOpenProjectsOnly(true);
      } else if (projectListInfo.getListView().equals("closed")) {
        projects.setClosedProjectsOnly(true);
      } else if (projectListInfo.getListView().equals("recent")) {
        projects.setDaysLastAccessed(7);
      }

      //Assignment Info
      if (!"hide".equals(projectListInfo.getFilterValue("listFilter1"))) {
        projects.setBuildAssignments(true);
      }
      //List View: My or All assignments
      if ("all".equals(projectListInfo.getFilterValue("listFilter1"))) {

      } else {
        projects.setAssignmentsForUser(getUserId(context));
      }
      projects.setOpenAssignmentsOnly(true);
      projects.setWithAssignmentDaysComplete(6);
      //List View: Discussion Topics
      if (!"hide".equals(projectListInfo.getFilterValue("listFilter2"))) {
        projects.setBuildIssues(true);
        if ("last3".equals(projectListInfo.getFilterValue("listFilter2"))) {
          projects.setLastIssues(3);
        } else {
          projects.setLastIssues(6);
        }
      }
      //List View: News Articles
      if (!"hide".equals(projectListInfo.getFilterValue("listFilter3"))) {
        projects.setBuildNews(true);
        projects.setCurrentNews(Constants.TRUE);
        if ("last3".equals(projectListInfo.getFilterValue("listFilter3"))) {
          projects.setLastNews(3);
        } else {
          projects.setLastNews(6);
        }
      }
      projects.setInvitationAcceptedOnly(true);
      projects.buildList(db);
      context.getRequest().setAttribute("projectList", projects);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("EnterpriseViewOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandProjectList(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "projects-enterprise-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      //PagedList Info
      ProjectList projects = new ProjectList();
      PagedListInfo projectListInfo = this.getPagedListInfo(
          context, "projectListInfo");
      projectListInfo.setLink("ProjectManagement.do?command=ProjectList");
      if (projectListInfo.getListView() == null) {
        projectListInfo.setItemsPerPage(0);
        // My open projects
        projectListInfo.setListView("open");
        // Categories
        projectListInfo.addFilter(1, "-1");
      }
      projects.setPagedListInfo(projectListInfo);
      //Project Info
      projects.setGroupId(-1);
      projects.setProjectsForUser(getUserId(context));
      projects.setIncludeGuestProjects(true);
      projects.setPortalState(Constants.FALSE);
      //projects.setUserRange(this.getUserRange(context));
      if (projectListInfo.getListView().equals("open")) {
        projects.setOpenProjectsOnly(true);
      } else if (projectListInfo.getListView().equals("closed")) {
        projects.setClosedProjectsOnly(true);
      } else if (projectListInfo.getListView().equals("recent")) {
        projects.setDaysLastAccessed(7);
      } else if (projectListInfo.getListView().equals("trashed")) {
        projects.setIncludeOnlyTrashed(true);
      }
      projects.setInvitationAcceptedOnly(true);
      projects.setBuildOverallProgress(true);
      //projects.setBuildPermissions(true);
      projects.setCategoryId(projectListInfo.getFilterValue("listFilter1"));
      projects.buildList(db);
      context.getRequest().setAttribute("projectList", projects);
      // Prepare the list of categories to display, based on categories used
      ProjectCategoryList categoryList = new ProjectCategoryList();
      categoryList.setCategoriesForProjectUser(getUserId(context));
      categoryList.setIncludeId(projectListInfo.getFilterValue("listFilter1"));
      categoryList.buildList(db);
      HtmlSelect thisSelect = categoryList.getHtmlSelect();
      thisSelect.addItem(-1, "All Categories", 0);
      context.getRequest().setAttribute("projectCategoryList", thisSelect);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ProjectListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandRSVP(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    Connection db = null;
    try {
      db = getConnection(context);
      //Get a list of projects that user has been invited to
      ProjectList invitedProjects = new ProjectList();
      invitedProjects.setProjectsForUser(getUserId(context));
      invitedProjects.setIncludeGuestProjects(false);
      invitedProjects.setInvitationPendingOnly(true);
      invitedProjects.buildList(db);
      context.getRequest().setAttribute("invitedProjectList", invitedProjects);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("RSVPOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandOverview(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    String link = "ProjectManagement.do?command=Overview";
    Connection db = null;
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    // Prepare the drop-down
    PagedListInfo overviewListInfo = this.getPagedListInfo(
        context, "overviewListInfo");
    overviewListInfo.setLink("ProjectManagement.do?command=Overview");
    if (overviewListInfo.getListView() == null) {
      overviewListInfo.setListView("48hours");
    }
    Calendar cal = Calendar.getInstance();
    if (overviewListInfo.getListView().equals("today")) {
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
    } else if (overviewListInfo.getListView().equals("24hours")) {
      cal.add(Calendar.DAY_OF_MONTH, -1);
    } else if (overviewListInfo.getListView().equals("48hours")) {
      cal.add(Calendar.DAY_OF_MONTH, -2);
    } else if (overviewListInfo.getListView().equals("14days")) {
      // 14 Days
      cal.add(Calendar.DAY_OF_MONTH, -14);
    } else if (overviewListInfo.getListView().equals("30days")) {
      // 30 Days
      cal.add(Calendar.DAY_OF_MONTH, -30);
    } else {
      // 7 Days -- default
      cal.add(Calendar.DAY_OF_MONTH, -7);
    }
    Timestamp alertRangeStart = new Timestamp(cal.getTimeInMillis());
    //Prepare lists
    //Upcoming assignments
    //Latest news for my projects (last 5)
    //Latest discussion for my projects (last 5)
    //Latest documents for my projects (last 5)
    //Latest tickets for my projects (last 5)
    AssignmentList assignmentList = new AssignmentList();
    NewsArticleList newsList = new NewsArticleList();
    IssueList issueList = new IssueList();
    FileItemList fileItemList = new FileItemList();
    TicketList ticketList = new TicketList();
    //reset the paged lists
    if (context.getRequest().getParameter("resetList") != null && context.getRequest().getParameter(
        "resetList").equals("true")) {
      context.getSession().removeAttribute("overviewAssignmentListInfo");
      context.getSession().removeAttribute("overviewNewsListInfo");
      context.getSession().removeAttribute("overviewIssueListInfo");
      context.getSession().removeAttribute("overviewFileItemListListInfo");
      context.getSession().removeAttribute("overviewTicketListInfo");
    }
    //PagedLists needed
    assignmentList.setPagedListInfo(
        processPagedListInfo(
            context, sectionId, "overviewAssignmentListInfo", "a.due_date", null, link, MINIMIZED_ITEMS_PER_PAGE));
    newsList.setPagedListInfo(
        processPagedListInfo(
            context, sectionId, "overviewNewsListInfo", "n.start_date", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    issueList.setPagedListInfo(
        processPagedListInfo(
            context, sectionId, "overviewIssueListInfo", "i.last_reply_date", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    fileItemList.setPagedListInfo(
        processPagedListInfo(
            context, sectionId, "overviewFileItemListListInfo", "f.modified", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    ticketList.setPagedListInfo(
        processPagedListInfo(
            context, sectionId, "overviewTicketListInfo", "t.entered", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    //Query the records
    assignmentList.setForProjectUser(getUserId(context));
    assignmentList.setAssignmentsForUser(getUserId(context));
    assignmentList.setIncompleteOnly(true);
    assignmentList.setOnlyIfRequirementOpen(true);
    newsList.setForUser(getUserId(context));
    newsList.setAlertRangeStart(alertRangeStart);
    newsList.setCurrentNews(Constants.TRUE);
    issueList.setForUser(getUserId(context));
    issueList.setAlertRangeStart(alertRangeStart);
    fileItemList.setLinkModuleId(Constants.PROJECTS_FILES);
    fileItemList.setForProjectUser(getUserId(context));
    fileItemList.setAlertRangeStart(alertRangeStart);
    ticketList.setForProjectUser(getUserId(context));
    ticketList.setIncludeAllSites(true);
    ticketList.setAssignedTo(getUserId(context));
    ticketList.setOnlyOpen(true);
    try {
      db = getConnection(context);
      if (!issueList.getPagedListInfo().getExpandedSelection() &&
          !newsList.getPagedListInfo().getExpandedSelection() &&
          !ticketList.getPagedListInfo().getExpandedSelection() &&
          !fileItemList.getPagedListInfo().getExpandedSelection()) {
        assignmentList.buildList(db);
      }
      if (!issueList.getPagedListInfo().getExpandedSelection() &&
          !ticketList.getPagedListInfo().getExpandedSelection() &&
          !assignmentList.getPagedListInfo().getExpandedSelection() &&
          !fileItemList.getPagedListInfo().getExpandedSelection()) {
        newsList.buildList(db);
      }
      if (!newsList.getPagedListInfo().getExpandedSelection() &&
          !ticketList.getPagedListInfo().getExpandedSelection() &&
          !assignmentList.getPagedListInfo().getExpandedSelection() &&
          !fileItemList.getPagedListInfo().getExpandedSelection()) {
        issueList.buildList(db);
      }
      if (!issueList.getPagedListInfo().getExpandedSelection() &&
          !ticketList.getPagedListInfo().getExpandedSelection() &&
          !assignmentList.getPagedListInfo().getExpandedSelection() &&
          !newsList.getPagedListInfo().getExpandedSelection()) {
        fileItemList.buildList(db);
      }
      if (!newsList.getPagedListInfo().getExpandedSelection() &&
          !issueList.getPagedListInfo().getExpandedSelection() &&
          !assignmentList.getPagedListInfo().getExpandedSelection() &&
          !fileItemList.getPagedListInfo().getExpandedSelection()) {
        ticketList.buildList(db);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("assignmentList", assignmentList);
    context.getRequest().setAttribute("newsList", newsList);
    context.getRequest().setAttribute("issueList", issueList);
    context.getRequest().setAttribute("fileItemList", fileItemList);
    context.getRequest().setAttribute("ticketList", ticketList);
    return ("OverviewOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCalendar(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    Connection db = null;
    //Prepare lists
    NewsArticleList newsList = new NewsArticleList();
    IssueList issueList = new IssueList();
    FileItemList fileItemList = new FileItemList();
    TicketList ticketList = new TicketList();
    //PagedLists needed
    //newsList.setPagedListInfo(processPagedListInfo(context, sectionId, "overviewNewsListInfo", "n.start_date", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    //issueList.setPagedListInfo(processPagedListInfo(context, sectionId, "overviewIssueListInfo", "i.last_reply_date", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    //fileItemList.setPagedListInfo(processPagedListInfo(context, sectionId, "overviewFileItemListListInfo", "f.entered", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    //ticketList.setPagedListInfo(processPagedListInfo(context, sectionId, "overviewTicketListInfo", "t.entered", "desc", link, MINIMIZED_ITEMS_PER_PAGE));
    //Query the records
    //TODO: Filters need to be set!
    //TODO: Incomplete assignments by due date!

    //Determine date range for calendar
    Calendar startCal = Calendar.getInstance();
    startCal.add(Calendar.DATE, -7);
    Timestamp startDate = new Timestamp(startCal.getTimeInMillis());
    //Configure lists
    newsList.setAlertRangeStart(startDate);
    issueList.setAlertRangeStart(startDate);
    fileItemList.setAlertRangeStart(startDate);
    ticketList.setAlertRangeStart(startDate);
    try {
      db = getConnection(context);
      newsList.buildList(db);
      issueList.buildList(db);
      fileItemList.buildList(db);
      ticketList.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("newsList", newsList);
    context.getRequest().setAttribute("issueList", issueList);
    context.getRequest().setAttribute("fileItemList", fileItemList);
    context.getRequest().setAttribute("ticketList", ticketList);
    return ("OverviewOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAddProject(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "projects-projects-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      Project thisProject = (Project) context.getFormBean();
      if (thisProject.getRequestDate() == null) {
        thisProject.setRequestDate(
            DateUtils.roundUpToNextFive(System.currentTimeMillis()));
      }
      db = getConnection(context);
      //Category List
      LookupList categoryList = new LookupList(db, "lookup_project_category");
      categoryList.addItem(
          -1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("categoryList", categoryList);
      //Previous projects
      ProjectList projectList = new ProjectList();
      projectList.setGroupId(-1);
      projectList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      //projectList.setEnteredByUserRange(getUserRange(context));
      projectList.setProjectsForUser(getUserId(context));
      projectList.setIncludeGuestProjects(true);
      projectList.buildList(db);
      context.getRequest().setAttribute("ProjectList", projectList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AddProjectOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandInsertProject(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "projects-projects-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    try {
      db = getConnection(context);
      Project thisProject = (Project) context.getFormBean();
      thisProject.setGroupId(-1);
      thisProject.setEnteredBy(this.getUserId(context));
      thisProject.setModifiedBy(this.getUserId(context));
      // NOTE: This needs to be implemented
      //if (!getUser(context).getAccessGuestProjects()) {
      thisProject.setAllowGuests(false);
      //}
      isValid = this.validateObject(context, db, thisProject);
      if (isValid) {
        if (thisProject.insert(db, context)) {
          updateProjectCache(
              context, thisProject.getId(), thisProject.getTitle());
          indexAddItem(context, thisProject);
          //Add the current user to the team TODO: Put in a transaction
          TeamMember thisMember = new TeamMember();
          thisMember.setProjectId(thisProject.getId());
          thisMember.setUserId(this.getUserId(context));
          thisMember.setUserLevel(
              getUserLevel(context, db, TeamMember.PROJECT_LEAD));
          thisMember.setEnteredBy(this.getUserId(context));
          thisMember.setModifiedBy(this.getUserId(context));
          thisMember.insert(db);
          //Go to the project
          context.getRequest().setAttribute(
              "pid", String.valueOf(thisProject.getId()));
          return (executeCommandProjectCenter(context));
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return (executeCommandAddProject(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyProject(ActionContext context) {
    if (!(hasPermission(context, "projects-projects-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-details-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("modifyproject").toLowerCase());
      //Category List
      LookupList categoryList = new LookupList(db, "lookup_project_category");
      categoryList.addItem(
          -1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("categoryList", categoryList);
      return ("ProjectCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCustomizeProject(ActionContext context) {
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-setup-customize")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("setup_customize").toLowerCase());
      return ("ProjectCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigurePermissions(ActionContext context) {
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-setup-permissions")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("setup_permissions").toLowerCase());
      //Load the possible permission categories and permissions
      PermissionCategoryLookupList categories = new PermissionCategoryLookupList();
      categories.setIncludeEnabled(Constants.TRUE);
      categories.buildList(db);
      categories.buildResources(db);
      context.getRequest().setAttribute("categories", categories);
      return ("ProjectCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdatePermissions(ActionContext context) {
    Connection db = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      //Make sure user can modify permissions
      if (!hasProjectAccess(
          context, db, thisProject, "project-setup-permissions")) {
        return "PermissionError";
      }
      PermissionList.updateProjectPermissions(
          db, context.getRequest(), Integer.parseInt(projectId));
      return "UpdatePermissionsOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateProject(ActionContext context) {
    if (!(hasPermission(context, "projects-projects-edit"))) {
      return ("PermissionError");
    }
    Project thisProject = (Project) context.getFormBean();
    //thisProject.setRequestItems(context.getRequest());
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    try {
      db = this.getConnection(context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-details-edit")) {
        return "PermissionError";
      }
      thisProject.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, thisProject);
      if (isValid) {
        resultCount = thisProject.update(db, context);
      }
      if (resultCount == -1) {
        //Category List
        LookupList categoryList = new LookupList(
            db, "lookup_project_category");
        categoryList.addItem(
            -1, this.getSystemStatus(context).getLabel(
                "calendar.none.4dashes"));
        context.getRequest().setAttribute("categoryList", categoryList);
        context.getRequest().setAttribute("Project", thisProject);
        context.getRequest().setAttribute(
            "IncludeSection", ("modifyproject").toLowerCase());
      } else if (resultCount == 1) {
        updateProjectCache(
            context, thisProject.getId(), thisProject.getTitle());
        indexAddItem(context, thisProject);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //Results
    if (resultCount == -1) {
      return ("ProjectCenterOK");
    } else if (resultCount == 1) {
      context.getRequest().setAttribute(
          "pid", String.valueOf(thisProject.getId()));
      return ("UpdateProjectOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateFeatures(ActionContext context) {
    Project thisProject = (Project) context.getFormBean();
    Connection db = null;
    int resultCount = 0;
    try {
      db = this.getConnection(context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-setup-customize")) {
        return "PermissionError";
      }
      thisProject.setModifiedBy(this.getUserId(context));
      // NOTE: This needs to be implemented
      //thisProject.setUpdateAllowGuests(getUser(context).getAccessGuestProjects());
      resultCount = thisProject.updateFeatures(db);
      if (resultCount == -1) {
        this.processErrors(context, thisProject.getErrors());
        context.getRequest().setAttribute("Project", thisProject);
        return ("CustomizeProjectOK");
      }
      if (resultCount == 1) {
        context.getRequest().setAttribute(
            "pid", String.valueOf(thisProject.getId()));
        return ("UpdateFeaturesOK");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    return ("UserError");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandProjectCenter(ActionContext context) {
    if (!(hasPermission(context, "projects-projects-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Project thisProject = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    if (projectId == null) {
      projectId = (String) context.getRequest().getAttribute("pid");
    }
    String section = (String) context.getRequest().getParameter("section");
    //Determine the section to display
    if (section == null || section.equals("")) {
      section = "News";
      //Reset any pagedListInfo objects for this new project
      deletePagedListInfo(context, "projectNewsInfo");
      deletePagedListInfo(context, "projectRequirementsInfo");
      deletePagedListInfo(context, "projectAssignmentsInfo");
      deletePagedListInfo(context, "projectIssueCategoryInfo");
      deletePagedListInfo(context, "projectIssuesInfo");
      deletePagedListInfo(context, "projectTicketsInfo");
      deletePagedListInfo(context, "projectTeamInfo");
      deletePagedListInfo(context, "projectEmployeeTeamInfo");
      deletePagedListInfo(context, "projectAccountContactTeamInfo");
      deletePagedListInfo(context, "projectDocumentsGalleryInfo");
      deletePagedListInfo(context, "projectAccountsInfo");
    }
    try {
      db = getConnection(context);
      thisProject = loadProject(db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if ("News".equals(section)) {
        if (!hasProjectAccess(context, db, thisProject, "project-news-view")) {
          return "PermissionError";
        }
        addRecentItem(context, thisProject);
        PagedListInfo newsInfo = this.getPagedListInfo(
            context, "projectNewsInfo");
        newsInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=News&pid=" + thisProject.getId());
        //Load the news
        NewsArticleList newsList = new NewsArticleList();
        newsList.setProjectId(thisProject.getId());
        newsList.setPagedListInfo(newsInfo);
        if ("archived".equals(newsInfo.getListView()) && hasProjectAccess(
            context, db, thisProject, "project-news-view-archived")) {
          newsList.setArchivedNews(Constants.TRUE);
        } else if ("unreleased".equals(newsInfo.getListView()) && hasProjectAccess(
            context, db, thisProject, "project-news-view-unreleased")) {
          newsList.setUnreleasedNews(Constants.TRUE);
        } else if ("drafts".equals(newsInfo.getListView()) && hasProjectAccess(
            context, db, thisProject, "project-news-view-unreleased")) {
          newsList.setIncompleteNews(Constants.TRUE);
        } else {
          if (hasProjectAccess(
              context, db, thisProject, "project-news-view-unreleased")) {
            //all news (project access)
            newsList.setOverviewAll(true);
          } else {
            //current news (default)
            newsList.setCurrentNews(Constants.TRUE);
          }
        }
        // Determine the list view
        // Show news by order for projects
        context.getRequest().setAttribute(
            "IncludeSubSection", "news_by_article");
        newsInfo.setColumnToSortBy("n.priority_id asc, n.start_date desc");
        newsList.buildList(db);
        context.getRequest().setAttribute("newsList", newsList);
        // Prepare the list of categories to display
        NewsArticleCategoryList categoryList = new NewsArticleCategoryList();
        categoryList.setProjectId(thisProject.getId());
        categoryList.setEnabled(Constants.TRUE);
        categoryList.buildList(db);
        context.getRequest().setAttribute(
            "newsArticleCategoryList", categoryList);
        // Prepare the list of Lists to display
        TaskCategoryList taskCategoryList = new TaskCategoryList();
        taskCategoryList.setProjectId(thisProject.getId());
        //taskCategoryList.setEnabled(Constants.TRUE);
        taskCategoryList.buildList(db);
        context.getRequest().setAttribute(
            "taskCategoryList", taskCategoryList);
      } else if ("Requirements".equals(section)) {
        context.getSession().removeAttribute("projectAssignmentsInfo");
        if (!hasProjectAccess(context, db, thisProject, "project-plan-view")) {
          return "PermissionError";
        }
        PagedListInfo requirementsInfo = this.getPagedListInfo(
            context, "projectRequirementsInfo", 50);
        requirementsInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + thisProject.getId());
        thisProject.getRequirements().setPagedListInfo(requirementsInfo);
        thisProject.setBuildRequirementAssignments(false);
        if ("all".equals(requirementsInfo.getListView())) {

        } else if ("closed".equals(requirementsInfo.getListView())) {
          thisProject.getRequirements().setClosedOnly(true);
        } else if ("open".equals(requirementsInfo.getListView())) {
          thisProject.getRequirements().setOpenOnly(true);
        }
        thisProject.buildRequirementList(db);
        thisProject.getRequirements().buildPlanActivityCounts(db);
      } else if ("Team".equals(section)) {
        if (!hasProjectAccess(context, db, thisProject, "project-team-view")) {
          return "PermissionError";
        }
        //Check the pagedList filter
        PagedListInfo projectTeamInfo = this.getPagedListInfo(
            context, "projectTeamInfo");
        projectTeamInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Team&pid=" + thisProject.getId());
        projectTeamInfo.setItemsPerPage(0);

        PagedListInfo projectEmployeeTeamInfo = this.getPagedListInfo(
            context, "projectEmployeeTeamInfo");
        projectEmployeeTeamInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Team&pid=" + thisProject.getId());
        projectEmployeeTeamInfo.setItemsPerPage(0);

        PagedListInfo projectAccountContactTeamInfo = this.getPagedListInfo(
            context, "projectAccountContactTeamInfo");
        projectAccountContactTeamInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Team&pid=" + thisProject.getId());
        projectAccountContactTeamInfo.setItemsPerPage(0);

        //Generate the list
        thisProject.getTeam().setPagedListInfo(projectTeamInfo);
        thisProject.getEmployeeTeam().setPagedListInfo(
            projectEmployeeTeamInfo);
        thisProject.getAccountContactTeam().setPagedListInfo(
            projectAccountContactTeamInfo);

        thisProject.buildTeamMemberList(db);
        Iterator i = thisProject.getTeam().iterator();
        while (i.hasNext()) {
          TeamMember thisMember = (TeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildRecord(db, thisMember.getUserId());
          thisMember.setUser(thisUser);
        }
        i = thisProject.getEmployeeTeam().iterator();
        while (i.hasNext()) {
          TeamMember thisMember = (TeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildRecord(db, thisMember.getUserId());
          thisMember.setUser(thisUser);
        }
        i = thisProject.getAccountContactTeam().iterator();
        while (i.hasNext()) {
          TeamMember thisMember = (TeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildRecord(db, thisMember.getUserId());
          thisMember.setUser(thisUser);
        }
      } else if ("Assignments".equals(section)) {
        if (!hasProjectAccess(context, db, thisProject, "project-plan-view")) {
          return "PermissionError";
        }
        //Configure paged list for handling the list view
        PagedListInfo projectAssignmentsInfo = this.getPagedListInfo(
            context, "projectAssignmentsInfo");
        projectAssignmentsInfo.setItemsPerPage(0);
        projectAssignmentsInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + thisProject.getId());
        thisProject.getAssignments().setPagedListInfo(projectAssignmentsInfo);
        //Variables that can be used
        //String folderId = (String) context.getRequest().getParameter("fid");
        String expand = (String) context.getRequest().getParameter("expand");
        String contract = (String) context.getRequest().getParameter(
            "contract");
        String requirementId = (String) context.getRequest().getParameter(
            "rid");
        //Build the requirement and the assignments
        Requirement thisRequirement = new Requirement(
            db, Integer.parseInt(requirementId));
        context.getRequest().setAttribute("requirement", thisRequirement);
        if ("open".equals(projectAssignmentsInfo.getListView())) {
          thisRequirement.getAssignments().setIncompleteOnly(true);
        } else if ("closed".equals(projectAssignmentsInfo.getListView())) {
          thisRequirement.getAssignments().setClosedOnly(true);
        } else {
          //All
        }
        //HashMap that contains folder state info
        HashMap folderState = (HashMap) context.getSession().getAttribute(
            "AssignmentsFolderState");
        if (folderState == null) {
          folderState = new HashMap();
          context.getSession().setAttribute(
              "AssignmentsFolderState", folderState);
        }
        ArrayList thisFolderState = (ArrayList) folderState.get(
            new Integer(thisRequirement.getId()));
        if (thisFolderState == null) {
          thisFolderState = new ArrayList();
          folderState.put(
              new Integer(thisRequirement.getId()), thisFolderState);
        }
        if (expand != null) {
          thisFolderState.add(new Integer(Integer.parseInt(expand)));
        }
        if (contract != null) {
          thisFolderState.remove(new Integer(Integer.parseInt(contract)));
        }
        //thisRequirement.buildPlan(db, thisFolderState);
        //Load the map for displaying in order
        RequirementMapList map = new RequirementMapList();
        map.setProjectId(thisProject.getId());
        map.setRequirementId(thisRequirement.getId());
        map.buildList(db);
        context.getRequest().setAttribute("mapList", map);
        //Load the assignments
        AssignmentList assignments = new AssignmentList();
        assignments.setRequirementId(thisRequirement.getId());
        assignments.buildList(db);
        context.getRequest().setAttribute("assignments", assignments);
        //Filter the maplist
        map.filter(
            assignments, RequirementMapList.FILTER_PRIORITY, projectAssignmentsInfo.getFilterValue(
                "listFilter1"));
        if ("open".equals(projectAssignmentsInfo.getListView())) {
          map.filterAssignments(assignments, "incompleteOnly");
        } else if ("closed".equals(projectAssignmentsInfo.getListView())) {
          map.filterAssignments(assignments, "closedOnly");
        } else {
          //All
        }
        //Load the assignment folders
        AssignmentFolderList folders = new AssignmentFolderList();
        folders.setRequirementId(thisRequirement.getId());
        folders.buildList(db);
        context.getRequest().setAttribute("folders", folders);
        //Load the Priority Lookup for displaying values
        LookupList priorityList = new LookupList(
            db, "lookup_project_priority");
        context.getRequest().setAttribute("PriorityList", priorityList);
      } else if ("Issues_Categories".equals(section)) {
        if (!hasProjectAccess(
            context, db, thisProject, "project-discussion-forums-view")) {
          return "PermissionError";
        }
        PagedListInfo categoryInfo = this.getPagedListInfo(
            context, "projectIssueCategoryInfo");
        categoryInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=" + thisProject.getId());
        thisProject.getIssueCategories().setPagedListInfo(categoryInfo);
        thisProject.buildIssueCategoryList(db);
      } else if ("Issues".equals(section)) {
        if (!hasProjectAccess(
            context, db, thisProject, "project-discussion-topics-view")) {
          return "PermissionError";
        }
        String categoryId = context.getRequest().getParameter("cid");
        if (categoryId == null) {
          categoryId = context.getRequest().getParameter("categoryId");
        }
        //Set the paging
        if ("true".equals(context.getRequest().getParameter("resetList"))) {
          this.deletePagedListInfo(context, "projectIssuesInfo");
        }
        PagedListInfo issuesInfo = this.getPagedListInfo(
            context, "projectIssuesInfo");
        issuesInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Issues&pid=" + thisProject.getId() + "&cid=" + categoryId);
        thisProject.getIssues().setPagedListInfo(issuesInfo);
        //Build the category info
        IssueCategory issueCategory = new IssueCategory(
            db, Integer.parseInt(categoryId), thisProject.getId());
        context.getRequest().setAttribute("IssueCategory", issueCategory);
        //Build the issues
        thisProject.buildIssueList(db, issueCategory.getId());
      } else if ("File_Library".equals(section) || "File_Gallery".equals(
          section)) {
        if (!hasProjectAccess(
            context, db, thisProject, "project-documents-view")) {
          return "PermissionError";
        }
        String folderId = context.getRequest().getParameter("folderId");
        if (folderId == null) {
          folderId = (String) context.getRequest().getAttribute("folderId");
        }
        //Build the file item list
        FileItemList files = new FileItemList();
        if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
          files.setTopLevelOnly(true);
          //Reset the pagedListInfo
          deletePagedListInfo(context, "projectDocumentsGalleryInfo");
        } else {
          //Load the folder to determine the view
          FileFolder thisFolder = new FileFolder(
              db, Integer.parseInt(folderId));
          context.getRequest().setAttribute("currentFolder", thisFolder);
          if (thisFolder.getDisplay() == FileFolder.VIEW_GALLERY ||
              thisFolder.getDisplay() == FileFolder.VIEW_SLIDESHOW) {
            section = "File_Library";
          }
          files.setFolderId(Integer.parseInt(folderId));
        }
        files.setLinkModuleId(Constants.PROJECTS_FILES);
        files.setLinkItemId(thisProject.getId());
        if ("File_Gallery".equals(section)) {
          PagedListInfo galleryInfo = this.getPagedListInfo(
              context, "projectDocumentsGalleryInfo");
          files.setPagedListInfo(galleryInfo);
          if (context.getRequest().getParameter("details") != null) {
            galleryInfo.setLink(
                "ProjectManagement.do?command=ProjectCenter&section=File_Gallery&pid=" + thisProject.getId() + "&folderId=" + folderId + "&details=true");
            galleryInfo.setItemsPerPage(1);
          } else {
            galleryInfo.setLink(
                "ProjectManagement.do?command=ProjectCenter&section=File_Gallery&pid=" + thisProject.getId() + "&folderId=" + folderId);
            galleryInfo.setItemsPerPage(6);
          }
        }
        files.buildList(db);
        thisProject.setFiles(files);
        //Build the folder list for both the header, and in the list
        FileFolderList folders = new FileFolderList();
        if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
          folders.setTopLevelOnly(true);
        } else {
          folders.setParentId(Integer.parseInt(folderId));
          //Build array of folder trails
          ProjectManagementFileFolders.buildHierarchy(db, context);
        }
        folders.setLinkModuleId(Constants.PROJECTS_FILES);
        folders.setLinkItemId(thisProject.getId());
        folders.setBuildItemCount(true);
        folders.buildList(db);
        context.getRequest().setAttribute("fileFolderList", folders);
      } else if ("Lists_Categories".equals(section)) {
        if (!hasProjectAccess(context, db, thisProject, "project-lists-view")) {
          return "PermissionError";
        }
        TaskCategoryList categoryList = new TaskCategoryList();
        categoryList.setProjectId(thisProject.getId());
        //Check the pagedList filter
        PagedListInfo projectListsCategoriesInfo = this.getPagedListInfo(
            context, "projectListsCategoriesInfo");
        projectListsCategoriesInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=" + thisProject.getId());
        projectListsCategoriesInfo.setItemsPerPage(0);
        //Generate the list
        categoryList.setPagedListInfo(projectListsCategoriesInfo);
        categoryList.buildList(db);
        context.getRequest().setAttribute("categoryList", categoryList);
      } else if ("Lists".equals(section)) {
        if (!hasProjectAccess(context, db, thisProject, "project-lists-view")) {
          return "PermissionError";
        }
        String categoryId = context.getRequest().getParameter("cid");
        if (categoryId == null) {
          categoryId = context.getRequest().getParameter("categoryId");
        }
        //Add the category to the request
        LookupElement thisCategory = new LookupElement(
            db, Integer.parseInt(categoryId), "lookup_task_category");
        context.getRequest().setAttribute("category", thisCategory);
        //Build the list items
        TaskList outlineList = new TaskList();
        outlineList.setProjectId(thisProject.getId());
        outlineList.setCategoryId(Integer.parseInt(categoryId));
        //Check the pagedList filter
        PagedListInfo projectListsInfo = this.getPagedListInfo(
            context, "projectListsInfo");
        projectListsInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Lists&pid=" + thisProject.getId() + "&cid=" + Integer.parseInt(
                categoryId));
        projectListsInfo.setItemsPerPage(0);
        if ("all".equals(projectListsInfo.getListView())) {
          outlineList.setComplete(Constants.UNDEFINED);
        } else if ("closed".equals(projectListsInfo.getListView())) {
          outlineList.setComplete(Constants.TRUE);
        } else {
          outlineList.setComplete(Constants.FALSE);
        }
        //Generate the list
        outlineList.setPagedListInfo(projectListsInfo);
        outlineList.buildList(db);
        context.getRequest().setAttribute("outlineList", outlineList);
      } else if ("Tickets".equals(section)) {
        if (!hasProjectAccess(
            context, db, thisProject, "project-tickets-view")) {
          return "PermissionError";
        }
        PagedListInfo projectTicketsInfo = this.getPagedListInfo(
            context, "projectTicketsInfo");
        projectTicketsInfo.setLink(
            "ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=" + thisProject.getId());
        projectTicketsInfo.setMode(PagedListInfo.LIST_VIEW);
        //projectTicketsInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        ////projectTicketsInfo.setExpandedSelection(true);
        TicketList tickets = new TicketList();
        tickets.setProjectId(thisProject.getId());
        tickets.setPagedListInfo(projectTicketsInfo);
        if ("all".equals(projectTicketsInfo.getListView())) {

        } else if ("closed".equals(projectTicketsInfo.getListView())) {
          tickets.setOnlyClosed(true);
        } else {
          tickets.setOnlyOpen(true);
        }
        if (thisProject.isTrashed()) {
          tickets.setIncludeOnlyTrashed(true);
        }
        tickets.buildList(db);
        context.getRequest().setAttribute("ticketList", tickets);
      } else {
        //Just looking at the details
        if (!hasProjectAccess(
            context, db, thisProject, "project-details-view")) {
          return "PermissionError";
        }
        // Prepare the list of categories to display
        ProjectCategoryList categoryList = new ProjectCategoryList();
        if (thisProject.getCategoryId() > -1) {
          categoryList.setCategoryId(thisProject.getCategoryId());
          categoryList.buildList(db);
        }
        context.getRequest().setAttribute("projectCategoryList", categoryList);
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", section.toLowerCase());
      //The user has access, so show that they accessed the project
      TeamMember.updateLastAccessed(
          db, thisProject.getId(), getUserId(context));
      String popUp = context.getRequest().getParameter("popup");
      if (popUp != null && !"null".equals(popUp)) {
        return ("ProjectCenterPopupOK");
      } else {
        return ("ProjectCenterOK");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteProject(ActionContext context) {
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-details-delete")) {
        return "PermissionError";
      }
      if (!thisProject.delete(db, getDbNamePath(context))) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        thisProject.getErrors().put(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.projectDeletion"));
        processErrors(context, thisProject.getErrors());
      } else {
        updateProjectCache(context, thisProject.getId(), null);
        indexDeleteItem(context, thisProject);
      }
      return "DeleteProjectOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrashProject(ActionContext context) {
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-details-delete")) {
        return "PermissionError";
      }
      if (!thisProject.updateStatus(db, true, this.getUserId(context))) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        thisProject.getErrors().put(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.projectDeletion"));
        processErrors(context, thisProject.getErrors());
      } else {
        updateProjectCache(context, thisProject.getId(), null);
        indexDeleteItem(context, thisProject);
      }
      return "DeleteProjectOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestoreProject(ActionContext context) {
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-details-delete")) {
        return "PermissionError";
      }
      if (!thisProject.updateStatus(db, false, this.getUserId(context))) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        thisProject.getErrors().put(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.projectDeletion"));
        processErrors(context, thisProject.getErrors());
      } else {
        updateProjectCache(context, thisProject.getId(), null);
        indexAddItem(context, thisProject);
      }
      return "DeleteProjectOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAcceptProject(ActionContext context) {
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.accept(db, this.getUserId(context));
      return "AcceptProjectOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRejectProject(ActionContext context) {
    Connection db = null;
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.reject(db, this.getUserId(context));
      return "AcceptProjectOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context                  Description of the Parameter
   * @param sectionId                Description of the Parameter
   * @param infoName                 Description of the Parameter
   * @param sortColumn               Description of the Parameter
   * @param sortOrder                Description of the Parameter
   * @param link                     Description of the Parameter
   * @param MINIMIZED_ITEMS_PER_PAGE Description of the Parameter
   * @return Description of the Return Value
   */
  private PagedListInfo processPagedListInfo(ActionContext context, String sectionId, String infoName, String sortColumn, String sortOrder, String link, int MINIMIZED_ITEMS_PER_PAGE) {
    PagedListInfo thisInfo = this.getPagedListInfo(
        context, infoName, sortColumn, sortOrder);
    thisInfo.setLink(link);
    if (sectionId == null) {
      if (!thisInfo.getExpandedSelection()) {
        if (thisInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          thisInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (thisInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          thisInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(thisInfo.getId())) {
      thisInfo.setExpandedSelection(true);
    }
    return thisInfo;
  }
}

