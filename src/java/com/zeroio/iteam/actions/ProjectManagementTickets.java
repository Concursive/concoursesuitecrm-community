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
import com.zeroio.iteam.base.Project;
import com.zeroio.iteam.base.TeamMember;
import com.zeroio.iteam.base.TeamMemberList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ProjectManagementTickets.java,v 1.1.4.1 2004/07/07 15:12:07
 *          mrajkowski Exp $
 * @created July 6, 2004
 */
public final class ProjectManagementTickets extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Connection db = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-tickets-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "tickets_add");
      //Load the team
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
      //Prepare the form
      Ticket thisTicket = (Ticket) context.getFormBean();
      thisTicket.setOrgId(0);
      thisTicket.setContactId(
          this.getUser(context, this.getUserId(context)).getContactId());
      if (thisTicket.getId() > 0) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        thisTicket.buildHistory(db, systemStatus);
      }
      buildFormElements(context, db, thisTicket);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ("true".equals(context.getRequest().getParameter("popup"))) {
      return ("ProjectTicketsPopupOK");
    }
    return ("ProjectCenterOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    boolean isValid = false;
    String returnTo = context.getRequest().getParameter("return");
    try {
      //Process the ticket
      int projectId = Integer.parseInt(
          (String) context.getRequest().getParameter("pid"));
      Ticket thisTicket = (Ticket) context.getFormBean();
      thisTicket.setProjectId(projectId);
      boolean newTicket = (thisTicket.getId() == -1);
      if (newTicket) {
        thisTicket.setEnteredBy(getUserId(context));
      }
      thisTicket.setModifiedBy(getUserId(context));
      db = this.getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, thisTicket.getProjectId(), context);
      thisProject.buildPermissionList(db);
      // Only assign to users of the project
      if (thisTicket.getAssignedTo() > -1 && !TeamMemberList.isOnTeam(
          db, thisProject.getId(), thisTicket.getAssignedTo())) {
        return "PermissionError";
      }
      if (newTicket) {
        if (!hasProjectAccess(context, db, thisProject, "project-tickets-add")) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, thisTicket);
        if (isValid) {
          recordInserted = thisTicket.insert(db);
        }
        if (recordInserted) {
          thisTicket.insertProjectLink(db, projectId);
          indexAddItem(context, thisTicket);
          processInsertHook(context, thisTicket);
        }
      } else {
        // allow access to edit if assigned to this user and ticket is not closed
        if (!hasProjectAccess(
            context, db, thisProject, "project-tickets-edit") &&
            !(hasProjectAccess(
                context, db, thisProject, "project-tickets-view") &&
            thisTicket.getClosed() == null && thisTicket.getAssignedTo() == getUserId(
                context))) {
          return "PermissionError";
        }
        if (thisProject.getId() != thisTicket.getProjectId()) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, thisTicket);
        if (isValid) {
          Ticket previousTicket = new Ticket(db, thisTicket.getId());
          resultCount = thisTicket.update(db);
          if (resultCount == 1) {
            // Since ticket is now closed, return user to the list
            if (thisTicket.getCloseIt()) {
              returnTo = "list";
            }
            // Reload, index, and process the hook
            thisTicket.queryRecord(db, thisTicket.getId());
            indexAddItem(context, thisTicket);
            processUpdateHook(context, previousTicket, thisTicket);
          }
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      return ("InsertOK");
    } else if (resultCount == 1) {
      if ("list".equals(returnTo)) {
        return getReturn(context, "UpdateList");
      } else {
        return getReturn(context, "Update");
      }
    } else {
      return (executeCommandAdd(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    Ticket thisTicket = null;
    try {
      String ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      //Load the ticket
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("ticket", thisTicket);
      //Load the project
      Project thisProject = loadProject(
          db, thisTicket.getProjectId(), context);
      thisProject.buildPermissionList(db);
      // allow access to edit if assigned to this user and ticket is not closed
      if (!hasProjectAccess(context, db, thisProject, "project-tickets-edit") &&
          !(hasProjectAccess(context, db, thisProject, "project-tickets-view") &&
          thisTicket.getClosed() == null && thisTicket.getAssignedTo() == getUserId(
              context))) {
        return "PermissionError";
      }
      //addRecentItem(context, newTic);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (thisTicket != null && thisTicket.getClosed() != null) {
      return (executeCommandDetails(context));
    } else {
      return (executeCommandAdd(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    Connection db = null;
    String projectId = context.getRequest().getParameter("pid");
    String ticketId = context.getRequest().getParameter("id");
    String returnAction = context.getRequest().getParameter("return");
    try {
      db = this.getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-tickets-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      Ticket thisTicket = null;
      if (returnAction == null) {
        //Determine pagedlist
        PagedListInfo projectTicketsInfo = this.getPagedListInfo(
            context, "projectTicketsInfo", "t.entered", null);
        projectTicketsInfo.setLink(
            "ProjectManagementTickets.do?command=Details&pid=" + thisProject.getId());
        projectTicketsInfo.setMode(PagedListInfo.DETAILS_VIEW);
        //Load the ticket based on the offset
        TicketList tickets = new TicketList();
        tickets.setProjectId(thisProject.getId());
        tickets.setPagedListInfo(projectTicketsInfo);
        if (ticketId != null) {
          tickets.setId(Integer.parseInt(ticketId));
        }
        if ("all".equals(projectTicketsInfo.getListView())) {

        } else if ("closed".equals(projectTicketsInfo.getListView())) {
          tickets.setOnlyClosed(true);
        } else {
          tickets.setOnlyOpen(true);
        }
        tickets.buildList(db);
        // Retrieve the ticket
        if (tickets.size() > 0) {
          thisTicket = (Ticket) tickets.get(0);
        }
      }
      if (thisTicket == null) {
        thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      }
      context.getRequest().setAttribute(
          "IncludeSection", ("tickets_details").toLowerCase());
      if (thisTicket.getAssignedTo() > 0) {
        thisTicket.checkEnabledOwnerAccount(db);
      }
      // Load the ticket history
      thisTicket.buildHistory(db, getSystemStatus(context));
      context.getRequest().setAttribute("ticket", thisTicket);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ProjectCenterOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReopen(ActionContext context) {
    Connection db = null;
    Ticket thisTicket = null;
    int resultCount = -1;
    try {
      db = this.getConnection(context);
      //Load the ticket and change the status
      thisTicket = new Ticket(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      //Load the project
      Project thisProject = loadProject(
          db, thisTicket.getProjectId(), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-tickets-edit")) {
        return "PermissionError";
      }
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ReopenOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    boolean recordDeleted = false;
    Ticket thisTicket = null;
    String ticketId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      //Load and delete the ticket
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      //Load the project and permissions
      Project thisProject = loadProject(
          db, thisTicket.getProjectId(), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-tickets-delete")) {
        return "PermissionError";
      }
      recordDeleted = thisTicket.delete(db, this.getDbNamePath(context));
      indexDeleteItem(context, thisTicket);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //deleteRecentItem(context, thisTicket);
    return ("DeleteOK");
  }


  /**
   * Description of the Method
   *
   * @param context    Description of the Parameter
   * @param db         Description of the Parameter
   * @param thisTicket Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildFormElements(ActionContext context, Connection db, Ticket thisTicket) throws SQLException {
    context.getRequest().setAttribute("TicketDetails", thisTicket);
    //Severity List
    LookupList severityList = new LookupList(db, "ticket_severity");
    context.getRequest().setAttribute("SeverityList", severityList);
    //Priority List
    LookupList priorityList = new LookupList(db, "ticket_priority");
    context.getRequest().setAttribute("PriorityList", priorityList);
    /*
     *  TicketCategoryList subList1 = new TicketCategoryList();
     *  subList1.setCatLevel(1);
     *  subList1.setParentCode(thisTicket.getCatCode());
     *  subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
     *  subList1.buildList(db);
     *  context.getRequest().setAttribute("SubList1", subList1);
     *  TicketCategoryList subList2 = new TicketCategoryList();
     *  subList2.setCatLevel(2);
     *  if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == 1) {
     *  subList2.setParentCode(0);
     *  thisTicket.setSubCat1(0);
     *  thisTicket.setSubCat2(0);
     *  thisTicket.setSubCat3(0);
     *  } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == -1) {
     *  subList2.setParentCode(thisTicket.getSubCat1());
     *  subList2.getCatListSelect().setDefaultKey(thisTicket.getSubCat2());
     *  } else {
     *  subList2.setParentCode(thisTicket.getSubCat1());
     *  }
     *  subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
     *  subList2.buildList(db);
     *  context.getRequest().setAttribute("SubList2", subList2);
     *  TicketCategoryList subList3 = new TicketCategoryList();
     *  subList3.setCatLevel(3);
     *  if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 2)) {
     *  subList3.setParentCode(0);
     *  thisTicket.setSubCat2(0);
     *  thisTicket.setSubCat3(0);
     *  } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == -1) {
     *  subList3.setParentCode(thisTicket.getSubCat2());
     *  subList3.getCatListSelect().setDefaultKey(thisTicket.getSubCat3());
     *  } else {
     *  subList3.setParentCode(thisTicket.getSubCat2());
     *  }
     *  subList3.buildList(db);
     *  context.getRequest().setAttribute("SubList3", subList3);
     *  if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 3)) {
     *  thisTicket.setSubCat3(0);
     *  }
     */
  }
}

