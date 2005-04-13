package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Project;
import com.zeroio.iteam.base.ProjectList;
import com.zeroio.iteam.base.TeamMemberList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.Iterator;

/**
 *  The CFS Company Directory module.
 *
 * @author     esther
 * @created    July 28, 2004
 * @version    $Id: CompanyDirectoryProjects.java,v 1.9 2004/07/28 19:02:11 esther
 *      Exp $
 */
public final class CompanyDirectoryProjects extends CFSModule {

  /**
   *  This method retrieves a list of employees from the database and populates
   *  a Vector of the employees retrieved.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.0
   */
  public String executeCommandList(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!hasPermission(context, "contacts-internal_contacts-projects-view")) {
      return ("PermissionError");
    }
    String employeeId = context.getRequest().getParameter("empid");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact contactDetails = new Contact(db, Integer.parseInt(employeeId));
      context.getRequest().setAttribute("ContactDetails", contactDetails);
      //PagedList Info
      ProjectList projects = new ProjectList();
      PagedListInfo companyDirectoryProjectsInfo = this.getPagedListInfo(context, "CompanyDirectoryProjectsInfo");
      companyDirectoryProjectsInfo.setLink("CompanyDirectoryProjects.do?command=List&empid="+employeeId +
            HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
      companyDirectoryProjectsInfo.setItemsPerPage(0);
      projects.setPagedListInfo(companyDirectoryProjectsInfo);
      //Project Info
      projects.setGroupId(-1);
      projects.setProjectsForUser(contactDetails.getUserId());
      projects.setIncludeGuestProjects(true);
      projects.setPortalState(Constants.FALSE);
      projects.setBuildOverallProgress(true);
      projects.buildList(db);
      // See which projects this user has access to...
      Iterator i = projects.iterator();
      while (i.hasNext()) {
        Project thisProject = (Project) i.next();
        thisProject.setHasAccess(TeamMemberList.isOnTeam(db, thisProject.getId(), getUserId(context)));
      }
      context.getRequest().setAttribute("projectList", projects);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ListProjects");

  }


}

