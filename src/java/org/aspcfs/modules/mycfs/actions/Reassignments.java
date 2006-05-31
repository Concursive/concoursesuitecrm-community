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

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.AssignmentList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.RevenueList;
import org.aspcfs.modules.actionlist.base.ActionLists;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.CallList;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.troubletickets.base.TicketList;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Handles reassigning user data
 *
 * @author chris
 * @version $Id$
 * @created December 4, 2002
 */
public final class Reassignments extends CFSModule {

  /**
   * Shows the reassignments page<br>
   * TODO: Reduce the object/database overhead for generating the counts
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandReassign(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-reassign-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    SystemStatus thisSystem = this.getSystemStatus(context);
    User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    int userId = -1;
    User sourceUser = null;

    OrganizationList sourceAccounts = null;
    ContactList sourceAllContacts = null;
    ContactList sourcePublicContacts = null;
    ContactList sourceHierarchyContacts = null;
    ContactList sourcePersonalContacts = null;
    ContactList sourceAccountContacts = null;
    ContactList sourceLeads = null;
    UserList sourceUsers = null;
    TicketList sourceOpenTickets = null;
    TaskList incompleteTicketTasks = null;
    RevenueList sourceRevenue = null;
    AssignmentList sourceAssignments = null;
    OpportunityList sourceOpportunities = null;
    OpportunityList sourceOpenOpportunities = null;
    OpportunityList managingOpportunities = null;
    DocumentStoreTeamMemberList sourceDocumentStoreTeamMemberList = null;
    CallList sourcePendingActivities = null;
    ActionLists sourceActionLists = null;
    
    AccessTypeList accessTypeList = null;
    //Get the multiple-level heirarchy
    UserList shortChildList = thisRec.getShortChildList();
    UserList userSelectList = thisRec.getFullChildList(
        shortChildList, new UserList());
    userSelectList.setMyId(getUserId(context));
    userSelectList.setMyValue(thisRec.getContact().getNameLastFirst());
    userSelectList.setIncludeMe(true);
    userSelectList.setEmptyHtmlSelectRecord(
        thisSystem.getLabel("accounts.accounts_add.NoneSelected"));
    UserList userToAssignList = new UserList();
    userToAssignList.setMyId(userSelectList.getMyId());
    userToAssignList.setMyValue(userSelectList.getMyValue());
    userToAssignList.setIncludeMe(true);
    userToAssignList.setEmptyHtmlSelectRecord(
      thisSystem.getLabel("accounts.accounts_add.NoneSelected"));
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

        sourceAllContacts = new ContactList();
        sourceAllContacts.setOwner(userId);
        sourceAllContacts.setLeadsOnly(Constants.FALSE);
        sourceAllContacts.setEmployeesOnly(Constants.FALSE);
        sourceAllContacts.setBuildDetails(false);
        sourceAllContacts.setBuildTypes(false);
        sourceAllContacts.setIncludeAllSites(true);
        sourceAllContacts.buildList(db);
        context.getRequest().setAttribute("SourceAllContacts", sourceAllContacts);

        sourcePublicContacts = new ContactList();
        sourcePublicContacts.setOwner(userId);
        sourcePublicContacts.setLeadsOnly(Constants.FALSE);
        sourcePublicContacts.setEmployeesOnly(Constants.FALSE);
        sourcePublicContacts.setBuildDetails(false);
        sourcePublicContacts.setBuildTypes(false);
        sourcePublicContacts.setRuleId(AccessType.PUBLIC);
        sourcePublicContacts.setExcludeAccountContacts(true);
        sourcePublicContacts.setIncludeAllSites(true);
        sourcePublicContacts.buildList(db);
        context.getRequest().setAttribute("SourcePublicContacts", sourcePublicContacts);

        sourceHierarchyContacts = new ContactList();
        sourceHierarchyContacts.setOwner(userId);
        sourceHierarchyContacts.setLeadsOnly(Constants.FALSE);
        sourceHierarchyContacts.setEmployeesOnly(Constants.FALSE);
        sourceHierarchyContacts.setBuildDetails(false);
        sourceHierarchyContacts.setBuildTypes(false);
        sourceHierarchyContacts.setRuleId(AccessType.CONTROLLED_HIERARCHY);
        sourceHierarchyContacts.setIncludeAllSites(true);
        sourceHierarchyContacts.buildList(db);
        context.getRequest().setAttribute("SourceHierarchyContacts", sourceHierarchyContacts);

        /*
        sourcePersonalContacts = new ContactList();
        sourcePersonalContacts.setOwner(userId);
        sourcePersonalContacts.setLeadsOnly(Constants.FALSE);
        sourcePersonalContacts.setEmployeesOnly(Constants.FALSE);
        sourcePersonalContacts.setBuildDetails(false);
        sourcePersonalContacts.setBuildTypes(false);
        sourcePersonalContacts.setRuleId(AccessType.PERSONAL);
        sourcePersonalContacts.setIncludeAllSites(true);
        sourcePersonalContacts.buildList(db);
        context.getRequest().setAttribute("SourcePersonalContacts", sourcePersonalContacts);
        */

        sourceAccountContacts = new ContactList();
        sourceAccountContacts.setOwner(userId);
        sourceAccountContacts.setLeadsOnly(Constants.FALSE);
        sourceAccountContacts.setEmployeesOnly(Constants.FALSE);
        sourceAccountContacts.setBuildDetails(false);
        sourceAccountContacts.setBuildTypes(false);
        sourceAccountContacts.setWithAccountsOnly(true);
        sourceAccountContacts.setIncludeAllSites(true);
        sourceAccountContacts.buildList(db);
        context.getRequest().setAttribute("SourceAccountContacts", sourceAccountContacts);

        sourceLeads = new ContactList();
        sourceLeads.setOwner(userId);
        sourceLeads.setLeadsOnly(Constants.TRUE);
        sourceLeads.setEmployeesOnly(Constants.FALSE);
        sourceLeads.setBuildDetails(false);
        sourceLeads.setBuildTypes(false);
        sourceLeads.setIncludeAllSites(true);
        sourceLeads.buildList(db);
        context.getRequest().setAttribute("SourceLeads", sourceLeads);

        sourceUsers = new UserList();
        sourceUsers.setManagerId(userId);
        sourceUsers.buildList(db);
        context.getRequest().setAttribute("SourceUsers", sourceUsers);

        sourceOpenTickets = new TicketList();
        sourceOpenTickets.setAssignedTo(userId);
        sourceOpenTickets.setIncludeAllSites(true);
        sourceOpenTickets.setOnlyOpen(true);
        sourceOpenTickets.buildList(db);
        context.getRequest().setAttribute(
            "SourceOpenTickets", sourceOpenTickets);

        incompleteTicketTasks = new TaskList();
        incompleteTicketTasks.setOwner(userId);
        incompleteTicketTasks.setHasLinkedTicket(Constants.TRUE);
        incompleteTicketTasks.setComplete(Constants.FALSE);
        incompleteTicketTasks.buildList(db);
        context.getRequest().setAttribute(
            "IncompleteTicketTasks", incompleteTicketTasks);
        
        sourceRevenue = new RevenueList();
        sourceRevenue.setOwner(userId);
        sourceRevenue.buildList(db);
        context.getRequest().setAttribute("SourceRevenue", sourceRevenue);

        sourceAssignments = new AssignmentList();
        sourceAssignments.setAssignmentsForUser(userId);
        sourceAssignments.setIncompleteOnly(true);
        sourceAssignments.buildList(db);
        context.getRequest().setAttribute(
            "SourceAssignments", sourceAssignments);

        managingOpportunities = new OpportunityList();
        managingOpportunities.setManager(userId);
        managingOpportunities.setQueryOpenOnly(true);
        // TODO: Is the following needed? if so, it needs a userIdRange set too
        //managingOpportunities.setControlledHierarchyOnly(Constants.FALSE);
        accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
        managingOpportunities.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
        managingOpportunities.buildList(db);
        context.getRequest().setAttribute(
            "ManagingOpenOpportunities", managingOpportunities);
            
        sourceOpportunities = new OpportunityList();
        sourceOpportunities.setOwner(userId);
        sourceOpportunities.buildList(db);
        context.getRequest().setAttribute(
            "SourceOpportunities", sourceOpportunities);

        sourceOpenOpportunities = new OpportunityList();
        sourceOpenOpportunities.setOwner(userId);
        sourceOpenOpportunities.setQueryOpenOnly(true);
        sourceOpenOpportunities.buildList(db);
        context.getRequest().setAttribute(
            "SourceOpenOpportunities", sourceOpenOpportunities);

        sourceDocumentStoreTeamMemberList = new DocumentStoreTeamMemberList();
        sourceDocumentStoreTeamMemberList.setForDocumentStoreUser(userId);
        sourceDocumentStoreTeamMemberList.setMemberType(
            DocumentStoreTeamMemberList.USER);
        sourceDocumentStoreTeamMemberList.setUserLevel(
            DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER);
        sourceDocumentStoreTeamMemberList.buildList(db);
        context.getRequest().setAttribute(
            "SourceDocumentStores", sourceDocumentStoreTeamMemberList);

        sourcePendingActivities = new CallList();
        sourcePendingActivities.setOwner(userId);
        sourcePendingActivities.setOnlyPending(true);
        sourcePendingActivities.buildList(db);
        context.getRequest().setAttribute(
            "SourcePendingActivities", sourcePendingActivities);

        sourceActionLists = new ActionLists();
        sourceActionLists.setOwner(userId);
        sourceActionLists.setInProgressOnly(true);
        sourceActionLists.buildList(db);
        context.getRequest().setAttribute(
            "SourceInProgressActionLists", sourceActionLists);

        int fromUserSiteId = sourceUser.getSiteId();

        // filter users from the user list based on site Id.of from user
        Iterator userItr = userSelectList.iterator();
        while (userItr.hasNext()){
          User tmpUser = (User)userItr.next();
          if (fromUserSiteId == tmpUser.getSiteId()){
            userToAssignList.add(tmpUser);
          }
          if (fromUserSiteId != this.getUserSiteId(context)){
            userToAssignList.setIncludeMe(false);
          }
        }
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      } finally {
        this.freeConnection(context, db);
      }
    }
    context.getRequest().setAttribute("SourceUser", sourceUser);
    context.getRequest().setAttribute("UserList", userToAssignList);
    context.getRequest().setAttribute("UserSelectList", userSelectList);
    addModuleBean(context, "Reassign", "Bulk Reassign");
    return ("ReassignOK");
  }


  /**
   * Performs the actual reassignments
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDoReassign(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-reassign-edit"))) {
      return ("PermissionError");
    }
    HashMap errors = new HashMap();
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
      targetIdAccounts = Integer.parseInt(
          context.getRequest().getParameter("ownerToAccounts"));
    }
    //All Contacts (Public, Controlled Hierarchy and Personal contacts)
    ContactList sourceAllContacts = null;
    int targetIdAllContacts = -1;
    if (context.getRequest().getParameter("ownerToAllContacts") != null) {
      targetIdAllContacts = Integer.parseInt(
          context.getRequest().getParameter("ownerToAllContacts"));
    }
    //Public Contacts
    ContactList sourcePublicContacts = null;
    int targetIdPublicContacts = -1;
    if (context.getRequest().getParameter("ownerToPublicContacts") != null) {
      targetIdPublicContacts = Integer.parseInt(
          context.getRequest().getParameter("ownerToPublicContacts"));
    }
    //Controlled Hierarchy Contacts
    ContactList sourceHierarchyContacts = null;
    int targetIdHierarchyContacts = -1;
    if (context.getRequest().getParameter("ownerToHierarchyContacts") != null) {
      targetIdHierarchyContacts = Integer.parseInt(
          context.getRequest().getParameter("ownerToHierarchyContacts"));
    }
    //Personal Contacts
    /*
    ContactList sourcePersonalContacts = null;
    int targetIdPersonalContacts = -1;
    if (context.getRequest().getParameter("ownerToPersonalContacts") != null) {
      targetIdPersonalContacts = Integer.parseInt(
          context.getRequest().getParameter("ownerToPersonalContacts"));
    }
    */
    //Account Contacts
    ContactList sourceAccountContacts = null;
    int targetIdAccountContacts = -1;
    if (context.getRequest().getParameter("ownerToAccountContacts") != null) {
      targetIdAccountContacts = Integer.parseInt(
          context.getRequest().getParameter("ownerToAccountContacts"));
    }
    //Leads
    ContactList sourceLeads = null;
    int targetIdLeads = -1;
    if (context.getRequest().getParameter("ownerToLeads") != null) {
      targetIdLeads = Integer.parseInt(
          context.getRequest().getParameter("ownerToLeads"));
    }
    //Opportunities being managed: Open
    OpportunityHeaderList sourceOpenOppsBeingManaged = null;
    int targetIdOpenOppsBeingManaged = -1;
    if (context.getRequest().getParameter("managerToOpenOpps") != null) {
      targetIdOpenOppsBeingManaged = Integer.parseInt(
          context.getRequest().getParameter("managerToOpenOpps"));
    }

    //Opportunities: Open
    OpportunityComponentList sourceOpenOpps = null;
    int targetIdOpenOpps = -1;
    if (context.getRequest().getParameter("ownerToOpenOpps") != null) {
      targetIdOpenOpps = Integer.parseInt(
          context.getRequest().getParameter("ownerToOpenOpps"));
    }
    //Opportunities: Closed
    OpportunityComponentList sourceOpps = null;
    int targetIdOpps = -1;
    if (context.getRequest().getParameter("ownerToOpenClosedOpps") != null) {
      targetIdOpps = Integer.parseInt(
          context.getRequest().getParameter("ownerToOpenClosedOpps"));
    }
    //Revenue
    RevenueList sourceRevenue = null;
    int targetIdRevenue = -1;
    if (context.getRequest().getParameter("ownerToRevenue") != null) {
      targetIdRevenue = Integer.parseInt(
          context.getRequest().getParameter("ownerToRevenue"));
    }
    //Tickets: Open
    TicketList sourceOpenTickets = null;
    int targetIdOpenTickets = -1;
    if (context.getRequest().getParameter("ownerToOpenTickets") != null) {
      targetIdOpenTickets = Integer.parseInt(
          context.getRequest().getParameter("ownerToOpenTickets"));
    }
    //Incomplete Ticket Tasks: Open Tickets
    TaskList sourceIncompleteTicketTasks = null;
    int targetIdIncompleteTicketTasks = -1;
    if (context.getRequest().getParameter("ownerToIncompleteTicketTasks") != null) {
      targetIdIncompleteTicketTasks = Integer.parseInt(
          context.getRequest().getParameter("ownerToIncompleteTicketTasks"));
    }
    //Users
    UserList sourceUsers = null;
    int targetIdUsers = -1;
    if (context.getRequest().getParameter("ownerToUsers") != null) {
      targetIdUsers = Integer.parseInt(
          context.getRequest().getParameter("ownerToUsers"));
    }
    //Project Assignments
    AssignmentList sourceAssignments = null;
    int targetIdAssignments = -1;
    if (context.getRequest().getParameter("ownerToActivities") != null) {
      targetIdAssignments = Integer.parseInt(
          context.getRequest().getParameter("ownerToActivities"));
    }
    //Document Store Assignments
    DocumentStoreTeamMemberList sourceDocumentStoreTeamMemberList = null;
    int targetIdDocumentStores = -1;
    if (context.getRequest().getParameter("ownerToOpenDocumentStores") != null) {
      targetIdDocumentStores = Integer.parseInt(
          context.getRequest().getParameter("ownerToOpenDocumentStores"));
    }
    // Pending Activities
    CallList sourcePendingActivities = null;
    int targetIdActivities = -1;
    if (context.getRequest().getParameter("ownerToPendingActivities") != null) {
      targetIdActivities = Integer.parseInt(
          context.getRequest().getParameter("ownerToPendingActivities"));
    }
    // In Progress Action Lists
    ActionLists sourceActionLists = null;
    int targetIdActionLists = -1;
    if (context.getRequest().getParameter("ownerToActionLists") != null) {
      targetIdActionLists = Integer.parseInt(
          context.getRequest().getParameter("ownerToActionLists"));
    }
    try {
      db = getConnection(context);
      //Reassign users
      if (targetIdUsers > -1) {
        sourceUsers = new UserList();
        sourceUsers.setManagerId(userId);
        sourceUsers.buildList(db);
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors = sourceUsers.reassignElements(
            context, db, systemStatus, targetIdUsers, this.getUserId(context));
        if (errors.size() == 0) {
          thisRec.setBuildHierarchy(true);
          thisRec.buildResources(db);
        } else {
          processErrors(context, errors);
          return executeCommandReassign(context);
        }
      }
      //Reassign accounts
      if (targetIdAccounts > -1) {
        sourceAccounts = new OrganizationList();
        sourceAccounts.setOwnerId(userId);
        sourceAccounts.buildList(db);
        sourceAccounts.reassignElements(
            db, targetIdAccounts, this.getUserId(context));
      }
      //Reassign all contacts (Public, Controlled Hierarchy and Personal contacts)
      if (targetIdAllContacts > -1) {
        sourceAllContacts = new ContactList();
        sourceAllContacts.setOwner(userId);
        sourceAllContacts.setLeadsOnly(Constants.FALSE);
        sourceAllContacts.setEmployeesOnly(Constants.FALSE);
        sourceAllContacts.setBuildDetails(false);
        sourceAllContacts.setBuildTypes(false);
        sourceAllContacts.setIncludeAllSites(true);
        sourceAllContacts.buildList(db);
        sourceAllContacts.reassignElements(
            db, targetIdAllContacts, this.getUserId(context));
      }
      //Reassign Public General Contacts
      if (targetIdPublicContacts > -1) {
        sourcePublicContacts = new ContactList();
        sourcePublicContacts.setOwner(userId);
        sourcePublicContacts.setLeadsOnly(Constants.FALSE);
        sourcePublicContacts.setEmployeesOnly(Constants.FALSE);
        sourcePublicContacts.setBuildDetails(false);
        sourcePublicContacts.setBuildTypes(false);
        sourcePublicContacts.setRuleId(AccessType.PUBLIC);
        sourcePublicContacts.setExcludeAccountContacts(true);
        sourcePublicContacts.setIncludeAllSites(true);
        sourcePublicContacts.buildList(db);
        sourcePublicContacts.reassignElements(
            db, targetIdPublicContacts, this.getUserId(context));
      }
      //Reassign Controlled Hierarchy General Contacts
      if (targetIdHierarchyContacts > -1) {
        sourceHierarchyContacts = new ContactList();
        sourceHierarchyContacts.setOwner(userId);
        sourceHierarchyContacts.setLeadsOnly(Constants.FALSE);
        sourceHierarchyContacts.setEmployeesOnly(Constants.FALSE);
        sourceHierarchyContacts.setBuildDetails(false);
        sourceHierarchyContacts.setBuildTypes(false);
        AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.GENERAL_CONTACTS);
        sourceHierarchyContacts.setRuleId(AccessType.CONTROLLED_HIERARCHY);
        sourceHierarchyContacts.setIncludeAllSites(true);
        sourceHierarchyContacts.buildList(db);
        sourceHierarchyContacts.reassignElements(
            db, targetIdHierarchyContacts, this.getUserId(context));
      }
      //Reassign Personal General Contacts
      /*
      if (targetIdPersonalContacts > -1) {
        sourcePersonalContacts = new ContactList();
        sourcePersonalContacts.setOwner(userId);
        sourcePersonalContacts.setLeadsOnly(Constants.FALSE);
        sourcePersonalContacts.setEmployeesOnly(Constants.FALSE);
        sourcePersonalContacts.setBuildDetails(false);
        sourcePersonalContacts.setBuildTypes(false);
        AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.GENERAL_CONTACTS);
        sourcePersonalContacts.setRuleId(AccessType.PERSONAL);
        sourcePersonalContacts.setIncludeAllSites(true);
        sourcePersonalContacts.buildList(db);
        sourcePersonalContacts.reassignElements(
            db, targetIdPersonalContacts, this.getUserId(context));
      }
      */
      //Reassign Account Contacts
      if (targetIdAccountContacts > -1) {
        sourceAccountContacts = new ContactList();
        sourceAccountContacts.setOwner(userId);
        sourceAccountContacts.setLeadsOnly(Constants.FALSE);
        sourceAccountContacts.setEmployeesOnly(Constants.FALSE);
        sourceAccountContacts.setBuildDetails(false);
        sourceAccountContacts.setBuildTypes(false);
        sourceAccountContacts.setWithAccountsOnly(true);
        sourceAccountContacts.setIncludeAllSites(true);
        sourceAccountContacts.buildList(db);
        sourceAccountContacts.reassignElements(
            db, targetIdAccountContacts, this.getUserId(context));
      }
      //Reassign leads
      if (targetIdLeads > -1) {
        sourceLeads = new ContactList();
        sourceLeads.setOwner(userId);
        sourceLeads.setLeadsOnly(Constants.TRUE);
        sourceLeads.setEmployeesOnly(Constants.FALSE);
        sourceLeads.setBuildDetails(false);
        sourceLeads.setBuildTypes(false);
        sourceLeads.setIncludeAllSites(true);
        sourceLeads.buildList(db);
        sourceLeads.reassignElements(
            db, targetIdLeads, this.getUserId(context));
      }
      //Reassign opportunities being managed
      if (targetIdOpenOppsBeingManaged > -1) {
        sourceOpenOppsBeingManaged = new OpportunityHeaderList();
        sourceOpenOppsBeingManaged.setManager(userId);
        sourceOpenOppsBeingManaged.setQueryOpenOnly(true);
        // TODO: Is the following needed? if so, it needs a userIdRange set too
        //sourceOpenOppsBeingManaged.setControlledHierarchyOnly(Constants.FALSE);
        AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
        sourceOpenOppsBeingManaged.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
        sourceOpenOppsBeingManaged.buildList(db);
        sourceOpenOppsBeingManaged.reassignElements(
            db, targetIdOpenOppsBeingManaged, this.getUserId(context));
        invalidateUserInMemory(userId, context);
        invalidateUserInMemory(targetIdOpenOppsBeingManaged, context);
      }
      //Reassign opportunities (open only)
      if (targetIdOpenOpps > -1) {
        sourceOpenOpps = new OpportunityComponentList();
        sourceOpenOpps.setOwner(userId);
        sourceOpenOpps.setQueryOpenOnly(true);
        sourceOpenOpps.buildList(db);
        sourceOpenOpps.reassignElements(
            db, targetIdOpenOpps, this.getUserId(context));
        invalidateUserInMemory(userId, context);
        invalidateUserInMemory(targetIdOpenOpps, context);
      }
      //Reassign opportunities (open and closed)
      if (targetIdOpps > -1) {
        sourceOpps = new OpportunityComponentList();
        sourceOpps.setOwner(userId);
        sourceOpps.buildList(db);
        sourceOpps.reassignElements(db, targetIdOpps, this.getUserId(context));
        invalidateUserInMemory(userId, context);
        invalidateUserInMemory(targetIdOpps, context);
      }
      //Reassign revenue
      if (targetIdRevenue > -1) {
        sourceRevenue = new RevenueList();
        sourceRevenue.setOwner(userId);
        sourceRevenue.buildList(db);
        sourceRevenue.reassignElements(
            db, targetIdRevenue, this.getUserId(context));
        invalidateUserInMemory(userId, context);
        invalidateUserInMemory(targetIdRevenue, context);
      }
      //Reassign open tickets
      if (targetIdOpenTickets > -1) {
        sourceOpenTickets = new TicketList();
        sourceOpenTickets.setAssignedTo(userId);
        sourceOpenTickets.setIncludeAllSites(true);
        sourceOpenTickets.setOnlyOpen(true);
        sourceOpenTickets.buildList(db);
        sourceOpenTickets.reassignElements(
            db, targetIdOpenTickets, this.getUserId(context));
      }
      //Reassign incomplete tasks of open tickets
      if (targetIdIncompleteTicketTasks > -1) {
        sourceIncompleteTicketTasks = new TaskList();
        sourceIncompleteTicketTasks.setOwner(userId);
        //sourceIncompleteTicketTasks.setOnlyOpen(true);
        sourceIncompleteTicketTasks.setHasLinkedTicket(Constants.TRUE);
        sourceIncompleteTicketTasks.buildList(db);
        sourceIncompleteTicketTasks.reassignElements(
            db, targetIdIncompleteTicketTasks, this.getUserId(context));
      }
      //Reassign Assignments
      if (targetIdAssignments > -1) {
        sourceAssignments = new AssignmentList();
        sourceAssignments.setAssignmentsForUser(userId);
        sourceAssignments.setIncompleteOnly(true);
        sourceAssignments.buildList(db);
        sourceAssignments.reassignElements(
            db, targetIdAssignments, this.getUserId(context));
      }
      //Reassign document stores
      if (targetIdDocumentStores > -1) {
        DocumentStoreTeamMemberList.reassignElements(
            db, userId, targetIdDocumentStores, this.getUserId(context));
      }
      // Pending Activities
      if (targetIdActivities > -1) {
        sourcePendingActivities = new CallList();
        sourcePendingActivities.setOwner(userId);
        sourcePendingActivities.setOnlyPending(true);
        sourcePendingActivities.buildList(db);
        int numberOfReassignedActivities = sourcePendingActivities.reassignElements(
            db, context, targetIdActivities, this.getUserId(context));
      }
      // In Progress Action Lists
      if (targetIdActionLists > -1) {
        sourceActionLists = new ActionLists();
        sourceActionLists.setOwner(userId);
        sourceActionLists.setInProgressOnly(true);
        sourceActionLists.buildList(db);
        int numberOfReassignedActionLists = sourceActionLists.reassignElements(
            db, targetIdActionLists, this.getUserId(context));
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

