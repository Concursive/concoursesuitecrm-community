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
 *@created    November 6, 2001
 *@version    $Id: ProjectManagement.java,v 1.1.1.1 2002/01/14 19:49:26
 *      mrajkowski Exp $
 */
public final class ProjectManagement extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
    //based on user settings...go to their default page
    if (this.hasPermission(context, "projects-personal-view")) {
      return executeCommandPersonalView(context);
    } else if (this.hasPermission(context, "projects-enterprise-view")) {
      return executeCommandEnterpriseView(context);
    } else {
      return "PermissionError";
    }
  }


  /**
   *  Lists the users in the system that have a corresponding contact record
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.6
   */
  public String executeCommandPersonalView(ActionContext context) {
	  
	if (!(hasPermission(context, "projects-personal-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    Connection db = null;
    ProjectList projects = new ProjectList();

    try {
      db = getConnection(context);
      //Project Info
      projects.setGroupId(-1);
      projects.setOpenProjectsOnly(true);
      projects.setProjectsForUser(this.getUserId(context));
      //Assignment Info
      projects.setBuildAssignments(true);
      projects.setAssignmentsForUser(this.getUserId(context));
      projects.setOpenAssignmentsOnly(true);
      projects.setWithAssignmentDaysComplete(6);
      //Issue Info
      projects.setBuildIssues(true);
      projects.setLastIssues(3);
      projects.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "PersonalView", "");
    if (errorMessage == null) {
      context.getRequest().setAttribute("ProjectList", projects);
      return ("PersonalViewOK");
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
  public String executeCommandEnterpriseView(ActionContext context) {
	  
	if (!(hasPermission(context, "projects-enterprise-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    Connection db = null;
    ProjectList projects = new ProjectList();

    try {
      db = getConnection(context);
      //Project Info
      projects.setGroupId(-1);
      //projects.setOpenProjectsOnly(true);
      //projects.setProjectsWithAssignmentsOnly(true);
      projects.setUserRange(this.getUserRange(context));
      //Assignment Info
      projects.setBuildAssignments(true);
      //projects.setAssignmentsForUser(getUserId(context));
      projects.setOpenAssignmentsOnly(true);
      projects.setWithAssignmentDaysComplete(6);
      //Issue Info
      projects.setBuildIssues(true);
      projects.setLastIssues(3);
      projects.buildList(db);

      Iterator i = projects.iterator();
      while (i.hasNext()) {
        Project thisProject = (Project)i.next();
        Iterator assignments = thisProject.getAssignments().iterator();
        while (assignments.hasNext()) {
          Assignment thisAssignment = (Assignment)assignments.next();
          Contact userAssigned = this.getUser(context, thisAssignment.getUserAssignedId()).getContact();
          thisAssignment.setUserAssigned(userAssigned.getNameFirstLast());
        }
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "EnterpriseView", "");
    if (errorMessage == null) {
      context.getRequest().setAttribute("ProjectList", projects);
      return ("EnterpriseViewOK");
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
  public String executeCommandAddProject(ActionContext context) {
	  
	if (!(hasPermission(context, "projects-projects-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "--None--");
      context.getRequest().setAttribute("DepartmentList", departmentList);

      ProjectList projectList = new ProjectList();
      projectList.setGroupId(-1);
      projectList.setEmptyHtmlSelectRecord("--None--");
      projectList.setEnteredByUserRange(getUserRange(context));
      projectList.buildList(db);
      context.getRequest().setAttribute("ProjectList", projectList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "AddProject", "");
    if (errorMessage == null) {
      return ("AddProjectOK");
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
  public String executeCommandInsertProject(ActionContext context) {
	  
	if (!(hasPermission(context, "projects-projects-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = (Project)context.getFormBean();
      thisProject.setGroupId(-1);
      thisProject.setEnteredBy(getUserId(context));
      thisProject.setModifiedBy(getUserId(context));
      recordInserted = thisProject.insert(db, context);
      if (!recordInserted) {
        processErrors(context, thisProject.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return (executeCommandEnterpriseView(context));
      } else {
        return (executeCommandAddProject(context));
      }
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
  public String executeCommandModifyProject(ActionContext context) {
	  
	if (!(hasPermission(context, "projects-projects-edit"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;

    Project thisProject = null;
    String projectId = (String)context.getRequest().getParameter("pid");

    try {
      db = this.getConnection(context);
      thisProject = new Project(db, Integer.parseInt(projectId), this.getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("modifyproject").toLowerCase());

      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "--None--");
      context.getRequest().setAttribute("DepartmentList", departmentList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      //return ("ModifyProjectOK");
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
  public String executeCommandUpdateProject(ActionContext context) {
	  
	if (!(hasPermission(context, "projects-projects-edit"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    Project thisProject = (Project)context.getFormBean();
    //thisProject.setRequestItems(context.getRequest());

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      thisProject.setModifiedBy(this.getUserId(context));
      resultCount = thisProject.update(db, context);
      if (resultCount == -1) {
        processErrors(context, thisProject.getErrors());
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(0, "--None--");
        context.getRequest().setAttribute("DepartmentList", departmentList);
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        context.getRequest().setAttribute("Project", thisProject);
        return ("ModifyProjectOK");
      } else if (resultCount == 1) {
        context.getRequest().setAttribute("pid", "" + thisProject.getId());
        return ("UpdateProjectOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
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
  public String executeCommandProjectCenter(ActionContext context) {
	  
	if (!(hasPermission(context, "projects-projects-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    Connection db = null;
    Project thisProject = null;
    String projectId = (String)context.getRequest().getParameter("pid");
    if (projectId == null) {
      projectId = (String)context.getRequest().getAttribute("pid");
    }

    String section = (String)context.getRequest().getParameter("section");
    if (section == null || section.equals("")) {
      section = "Home";
    }

    try {
      db = getConnection(context);
      thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if ("Requirements".equals(section)) {
        String expand = (String)context.getRequest().getParameter("expand");
        String contract = (String)context.getRequest().getParameter("contract");
        ArrayList reqsOpen = (ArrayList)context.getSession().getAttribute("Tree-OpenRequirements");
        if (reqsOpen == null) {
          reqsOpen = new ArrayList();
          context.getSession().setAttribute("Tree-OpenRequirements", reqsOpen);
        }
        if (expand != null) {
          if (!reqsOpen.contains(expand)) {
            reqsOpen.add(expand);
          }
        }
        if (contract != null) {
          if (reqsOpen.contains(contract)) {
            reqsOpen.remove(contract);
          }
        }
        
        thisProject.setBuildRequirementAssignments(true);
        thisProject.buildRequirementList(db);
        Iterator i = thisProject.getRequirements().iterator();
        while (i.hasNext()) {
          Requirement thisRequirement = (Requirement)i.next();
          thisRequirement.setTreeOpen(reqsOpen.contains("" + thisRequirement.getId()));
          Contact enteredBy = this.getUser(context, thisRequirement.getEnteredBy()).getContact();
          Contact modifiedBy = this.getUser(context, thisRequirement.getModifiedBy()).getContact();
          thisRequirement.setEnteredByString(enteredBy.getNameFirstLast());
          thisRequirement.setModifiedByString(modifiedBy.getNameFirstLast());
        }
      } else if ("Team".equals(section)) {
        thisProject.buildTeamMemberList(db);
        Iterator i = thisProject.getTeam().iterator();
        while (i.hasNext()) {
          TeamMember thisMember = (TeamMember)i.next();
          User thisUser = this.getUser(context, thisMember.getUserId());
          thisMember.setUser(thisUser);
          thisMember.setContact(thisUser.getContact());
        }
      } else if ("Assignments".equals(section)) {
        thisProject.buildAssignmentList(db);
        Iterator i = thisProject.getAssignments().iterator();
        while (i.hasNext()) {
          Assignment thisAssignment = (Assignment)i.next();
          Contact userAssigned = this.getUser(context, thisAssignment.getUserAssignedId()).getContact();
          thisAssignment.setUserAssigned(userAssigned.getNameFirstLast());
        }
      } else if ("Issues_Categories".equals(section)) {
        thisProject.buildIssueCategoryList(db);
        Iterator i = thisProject.getIssueCategories().iterator();
        while (i.hasNext()) {
          IssueCategory thisIssueCategory = (IssueCategory)i.next();
          //User user = new User(db, this.getUser(context).getGroupId(), thisIssueCategory.getModifiedBy());
          //thisIssueCategory.setUserModified(user.getNameFirstLast());
        }
      } else if ("Issues".equals(section)) {
        String categoryId = context.getRequest().getParameter("cid");
        if (categoryId == null) categoryId = context.getRequest().getParameter("categoryId");
        thisProject.buildIssueList(db, Integer.parseInt(categoryId));
        IssueCategory issueCategory = new IssueCategory(db, Integer.parseInt(categoryId), thisProject.getId());
        context.getRequest().setAttribute("IssueCategory", issueCategory);
        Iterator i = thisProject.getIssues().iterator();
        while (i.hasNext()) {
          Issue thisIssue = (Issue)i.next();
          Contact user = this.getUser(context, thisIssue.getEnteredBy()).getContact();
          thisIssue.setUser(user.getNameFirstLast());
        }
      } else if ("File_Library".equals(section)) {
        thisProject.buildFileItemList(db);
        Iterator i = thisProject.getFiles().iterator();
        while (i.hasNext()) {
          FileItem thisItem = (FileItem)i.next();
          Contact user = this.getUser(context, thisItem.getEnteredBy()).getContact();
          thisItem.setEnteredByString(user.getNameFirstLast());
        }
      } else if ("Lists_Categories".equals(section)) {
        TaskCategoryList categoryList = new TaskCategoryList();
        categoryList.setProjectId(thisProject.getId());
        categoryList.buildList(db);
        context.getRequest().setAttribute("categoryList", categoryList);
      } else if ("Lists".equals(section)) {
        String categoryId = context.getRequest().getParameter("cid");
        if (categoryId == null) {
          categoryId = context.getRequest().getParameter("categoryId");
        }
        LookupElement thisCategory = new LookupElement(db, Integer.parseInt(categoryId), "lookup_task_category");
        context.getRequest().setAttribute("category", thisCategory);
        
        TaskList outlineList = new TaskList();
        outlineList.setProjectId(thisProject.getId());
        outlineList.setCategoryId(Integer.parseInt(categoryId));
        outlineList.buildList(db);
        context.getRequest().setAttribute("outlineList", outlineList);
/*         
        Iterator i = outlineList.iterator();
        while (i.hasNext()) {
          Issue thisIssue = (Issue)i.next();
          Contact user = this.getUser(context, thisIssue.getEnteredBy()).getContact();
          thisIssue.setUser(user.getNameFirstLast());
        }
 */
      } else {
        addRecentItem(context, thisProject);
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", section.toLowerCase());
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
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

}

