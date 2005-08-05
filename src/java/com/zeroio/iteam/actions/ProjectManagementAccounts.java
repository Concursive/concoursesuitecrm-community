package com.zeroio.iteam.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Project;
import com.zeroio.iteam.utils.ProjectUtils;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Feb 28, 2005
 */

public class ProjectManagementAccounts extends CFSModule {

  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "projects-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-accounts-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("accounts").toLowerCase());
      // Setup a paged list
      PagedListInfo projectAccountsInfo = this.getPagedListInfo(
          context, "projectAccountsInfo");
      projectAccountsInfo.setLink(
          "ProjectManagementAccounts.do?command=List&pid=" + thisProject.getId());
      projectAccountsInfo.setItemsPerPage(0);
      // Load the list of Accounts
      OrganizationList organizationList = new OrganizationList();
      organizationList.setPagedListInfo(projectAccountsInfo);
      organizationList.setProjectId(thisProject.getId());
      organizationList.buildList(db);
      context.getRequest().setAttribute("organizationList", organizationList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String popUp = context.getRequest().getParameter("popup");
    if (popUp != null && !"null".equals(popUp)) {
      return ("ProjectCenterPopupOK");
    } else {
      return ("ProjectCenterOK");
    }
  }

  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "projects-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String orgId = (String) context.getRequest().getParameter("orgId");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-accounts-manage")) {
        return "PermissionError";
      }
      ProjectUtils.addAccount(
          db, Integer.parseInt(projectId), Integer.parseInt(orgId));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ProjectAccountAddOK");
  }

  public String executeCommandRemove(ActionContext context) {
    if (!hasPermission(context, "projects-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String orgId = (String) context.getRequest().getParameter("orgId");
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-accounts-manage")) {
        return "PermissionError";
      }
      ProjectUtils.removeAccount(
          db, Integer.parseInt(projectId), Integer.parseInt(orgId));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ProjectAccountRemoveOK");
  }
}
