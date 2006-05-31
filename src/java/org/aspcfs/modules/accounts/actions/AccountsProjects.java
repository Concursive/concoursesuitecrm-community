package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Project;
import com.zeroio.iteam.base.ProjectList;
import com.zeroio.iteam.base.TeamMemberList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Feb 28, 2005
 */

public class AccountsProjects extends CFSModule {

  public String executeCommandList(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!hasPermission(context, "accounts-projects-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      Organization thisOrganization = setOrganization(context, db);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisOrganization.getOrgId())) {
        return ("PermissionError");
      }
      //PagedList Info
      ProjectList projects = new ProjectList();
      PagedListInfo projectListInfo = this.getPagedListInfo(
          context, "AccountProjectInfo");
      projectListInfo.setLink(
          "AccountsProjects.do?command=List&orgId=" + thisOrganization.getId());
      projectListInfo.setItemsPerPage(0);
      projects.setPagedListInfo(projectListInfo);
      //Project Info
      projects.setGroupId(-1);
      projects.setProjectsForOrgId(thisOrganization.getId());
      projects.setIncludeGuestProjects(true);
      projects.setPortalState(Constants.FALSE);
      projects.setBuildOverallProgress(true);
      projects.buildList(db);
      // See which projects this user has access to...
      Iterator i = projects.iterator();
      while (i.hasNext()) {
        Project thisProject = (Project) i.next();
        thisProject.setHasAccess(
            TeamMemberList.isOnTeam(
                db, thisProject.getId(), getUserId(context)));
      }
      context.getRequest().setAttribute("projectList", projects);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AccountsProjectsListOK");
  }

  private static Organization setOrganization(ActionContext context, Connection db) throws SQLException {
    Organization thisOrganization = null;
    String orgId = context.getRequest().getParameter("orgId");
    thisOrganization = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return thisOrganization;
  }
}
