/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.mycfs.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.modules.accounts.base.RevenueList;
import org.aspcfs.modules.contacts.base.ContactList;
import java.sql.*;
import java.util.*;
import com.zeroio.iteam.base.*;

/**
 *  Handles reassigning user data
 *
 *@author     chris
 *@created    December 4, 2002
 *@version    $Id$
 */
public final class Reassignments extends CFSModule {

  /**
   *  Shows the reassignments page<br>
   *  TODO: Reduce the object/database overhead for generating the counts
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandReassign(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-reassign-view"))) {
      return ("PermissionError");
    }
    Connection db = null;

    User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    int userId = -1;
    User sourceUser = null;

    OrganizationList sourceAccounts = null;
    ContactList sourceContacts = null;
    UserList sourceUsers = null;
    TicketList sourceOpenTickets = null;
    RevenueList sourceRevenue = null;
    AssignmentList sourceAssignments = null;
    OpportunityList sourceOpportunities = null;
    OpportunityList sourceOpenOpportunities = null;
    //Get the multiple-level heirarchy
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisRec.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setEmptyHtmlSelectRecord("None Selected");
    //Check to see if a user is selected AND if they are in the hierarchy
    if (context.getRequest().getParameter("userId") != null) {
      userId = Integer.parseInt(context.getRequest().getParameter("userId"));
    }
    if (userId > -1) {
      if (userId == thisRec.getId()) {
        sourceUser = thisRec;
      } else {
        sourceUser = (User) thisRec.getChild(userId);
      }
    }
    //Generate the counts of all of the items
    if (sourceUser != null) {
      try {
        db = getConnection(context);

        sourceAccounts = new OrganizationList();
        sourceAccounts.setOwnerId(userId);
        sourceAccounts.buildList(db);
        context.getRequest().setAttribute("SourceAccounts", sourceAccounts);

        sourceContacts = new ContactList();
        sourceContacts.setOwner(userId);
        sourceContacts.setBuildDetails(false);
        sourceContacts.setBuildTypes(false);
        sourceContacts.buildList(db);
        context.getRequest().setAttribute("SourceContacts", sourceContacts);

        sourceUsers = new UserList();
        sourceUsers.setManagerId(userId);
        sourceUsers.buildList(db);
        context.getRequest().setAttribute("SourceUsers", sourceUsers);

        sourceOpenTickets = new TicketList();
        sourceOpenTickets.setAssignedTo(userId);
        sourceOpenTickets.setOnlyOpen(true);
        sourceOpenTickets.buildList(db);
        context.getRequest().setAttribute("SourceOpenTickets", sourceOpenTickets);

        sourceRevenue = new RevenueList();
        sourceRevenue.setOwner(userId);
        sourceRevenue.buildList(db);
        context.getRequest().setAttribute("SourceRevenue", sourceRevenue);

        sourceAssignments = new AssignmentList();
        sourceAssignments.setAssignmentsForUser(userId);
        sourceAssignments.setIncompleteOnly(true);
        sourceAssignments.buildList(db);
        context.getRequest().setAttribute("SourceAssignments", sourceAssignments);

        sourceOpportunities = new OpportunityList();
        sourceOpportunities.setOwner(userId);
        sourceOpportunities.buildList(db);
        context.getRequest().setAttribute("SourceOpportunities", sourceOpportunities);

        sourceOpenOpportunities = new OpportunityList();
        sourceOpenOpportunities.setOwner(userId);
        sourceOpenOpportunities.setQueryOpenOnly(true);
        sourceOpenOpportunities.buildList(db);
        context.getRequest().setAttribute("SourceOpenOpportunities", sourceOpenOpportunities);
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      } finally {
        this.freeConnection(context, db);
      }
    }
    context.getRequest().setAttribute("SourceUser", sourceUser);
    context.getRequest().setAttribute("UserList", userList);
    context.getRequest().setAttribute("UserSelectList", userList.clone());
    addModuleBean(context, "Reassign", "Bulk Reassign");
    return ("ReassignOK");
  }


  /**
   *  Performs the actual reassignments
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDoReassign(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-reassign-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    User sourceUser = null;
    //Process request parameters
    int userId = -1;
    if (context.getRequest().getParameter("userId") != null) {
      userId = Integer.parseInt(context.getRequest().getParameter("userId"));
    }
    if (userId == -1) {
      //A user id was not selected so abort
      return ("PermissionError");
    } else {
      //Make sure the user is in this user's hierarchy
      if (userId == thisRec.getId()) {
        sourceUser = thisRec;
      } else {
        sourceUser = (User) thisRec.getChild(userId);
      }
      if (sourceUser == null) {
        return ("PermissionError");
      }
    }
    //Check to see what is being reassigned
    //Accounts
    OrganizationList sourceAccounts = null;
    int targetIdAccounts = -1;
    if (context.getRequest().getParameter("ownerToAccounts") != null) {
      targetIdAccounts = Integer.parseInt(context.getRequest().getParameter("ownerToAccounts"));
    }
    //Contacts
    ContactList sourceContacts = null;
    int targetIdContacts = -1;
    if (context.getRequest().getParameter("ownerToContacts") != null) {
      targetIdContacts = Integer.parseInt(context.getRequest().getParameter("ownerToContacts"));
    }
    //Opportunities: Open
    OpportunityComponentList sourceOpenOpps = null;
    int targetIdOpenOpps = -1;
    if (context.getRequest().getParameter("ownerToOpenOpps") != null) {
      targetIdOpenOpps = Integer.parseInt(context.getRequest().getParameter("ownerToOpenOpps"));
    }
    //Opportunities: Closed
    OpportunityComponentList sourceOpps = null;
    int targetIdOpps = -1;
    if (context.getRequest().getParameter("ownerToOpenClosedOpps") != null) {
      targetIdOpps = Integer.parseInt(context.getRequest().getParameter("ownerToOpenClosedOpps"));
    }
    //Revenue
    RevenueList sourceRevenue = null;
    int targetIdRevenue = -1;
    if (context.getRequest().getParameter("ownerToRevenue") != null) {
      targetIdRevenue = Integer.parseInt(context.getRequest().getParameter("ownerToRevenue"));
    }
    //Tickets: Open
    TicketList sourceOpenTickets = null;
    int targetIdOpenTickets = -1;
    if (context.getRequest().getParameter("ownerToOpenTickets") != null) {
      targetIdOpenTickets = Integer.parseInt(context.getRequest().getParameter("ownerToOpenTickets"));
    }
    //Users
    UserList sourceUsers = null;
    int targetIdUsers = -1;
    if (context.getRequest().getParameter("ownerToUsers") != null) {
      targetIdUsers = Integer.parseInt(context.getRequest().getParameter("ownerToUsers"));
    }
    //Project Assignments
    AssignmentList sourceAssignments = null;
    int targetIdAssignments = -1;
    if (context.getRequest().getParameter("ownerToActivities") != null) {
      targetIdAssignments = Integer.parseInt(context.getRequest().getParameter("ownerToActivities"));
    }
    try {
      db = getConnection(context);
      //Reassign accounts
      if (targetIdAccounts > -1) {
        sourceAccounts = new OrganizationList();
        sourceAccounts.setOwnerId(userId);
        sourceAccounts.buildList(db);
        sourceAccounts.reassignElements(db, targetIdAccounts);
      }
      //Reassign contacts
      if (targetIdContacts > -1) {
        sourceContacts = new ContactList();
        sourceContacts.setOwner(userId);
        sourceContacts.setBuildDetails(false);
        sourceContacts.setBuildTypes(false);
        sourceContacts.buildList(db);
        sourceContacts.reassignElements(db, targetIdContacts);
      }
      //Reassign opportunities
      if (targetIdOpenOpps > -1) {
        sourceOpenOpps = new OpportunityComponentList();
        sourceOpenOpps.setOwner(userId);
        sourceOpenOpps.setQueryOpenOnly(true);
        sourceOpenOpps.buildList(db);
        sourceOpenOpps.reassignElements(db, targetIdOpenOpps);
        invalidateUserInMemory(targetIdOpenOpps, context);
      }
      //Reassign opportunities
      if (targetIdOpps > -1) {
        sourceOpps = new OpportunityComponentList();
        sourceOpps.setOwner(userId);
        sourceOpps.buildList(db);
        sourceOpps.reassignElements(db, targetIdOpps);
        invalidateUserInMemory(targetIdOpps, context);
      }
      //Reassign revenue
      if (targetIdRevenue > -1) {
        sourceRevenue = new RevenueList();
        sourceRevenue.setOwner(userId);
        sourceRevenue.buildList(db);
        sourceRevenue.reassignElements(db, targetIdRevenue);
        invalidateUserInMemory(targetIdRevenue, context);
      }
      //Reassign open tickets
      if (targetIdOpenTickets > -1) {
        sourceOpenTickets = new TicketList();
        sourceOpenTickets.setAssignedTo(userId);
        sourceOpenTickets.setOnlyOpen(true);
        sourceOpenTickets.buildList(db);
        sourceOpenTickets.reassignElements(db, targetIdOpenTickets);
      }
      //Reassign users
      if (targetIdUsers > -1) {
        sourceUsers = new UserList();
        sourceUsers.setManagerId(userId);
        sourceUsers.buildList(db);
        sourceUsers.reassignElements(db, targetIdUsers);
        thisRec.setBuildHierarchy(true);
        thisRec.buildResources(db);
      }
      //Reassign Assignments
      if (targetIdAssignments > -1) {
        sourceAssignments = new AssignmentList();
        sourceAssignments.setAssignmentsForUser(userId);
        sourceAssignments.setIncompleteOnly(true);
        sourceAssignments.buildList(db);
        sourceAssignments.reassignElements(db, targetIdAssignments);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //Update the cache
    if (targetIdOpps > -1 || targetIdOpenOpps > -1 || targetIdRevenue > -1) {
      invalidateUserData(context, getUserId(context));
      invalidateUserInMemory(sourceUser.getId(), context);
    }
    return ("DoReassignOK");
  }
}

