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
package org.aspcfs.apps.users.task;

import com.zeroio.iteam.base.AssignmentList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.RevenueList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.CallList;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.troubletickets.base.TicketList;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created December 8, 2004
 */
public class ProcessUserCleanup {

  public final static String fs = System.getProperty("file.separator");

  public ProcessUserCleanup(Connection db, String url) throws Exception {
    ProcessUserCleanup temp = new ProcessUserCleanup(db, (SystemStatus) null);
    temp = null;
  }

  /**
   * Constructor for the ProcessUserCleanup object
   *
   * @param db Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public ProcessUserCleanup(Connection db, SystemStatus thisSystem) throws Exception {

    UserList users = null;
    UserList expiredUsers = null;
    UserList disabledUsers = null;
    
    // Build the disabled users
    disabledUsers = new UserList();
    disabledUsers.setEnabled(Constants.FALSE);
    disabledUsers.buildList(db);

    // Build the expired users
    expiredUsers = new UserList();
    expiredUsers.setExpired(Constants.TRUE);
    expiredUsers.buildList(db);

    users = new UserList();
    if (disabledUsers.size() > 0) {
      users.addAll(disabledUsers);
    }
    if (expiredUsers.size() > 0) {
      users.addAll(expiredUsers);
    }

    boolean flag = false;
    Iterator iterator = (Iterator) users.iterator();
    while (iterator.hasNext()) {
      User thisUser = (User) iterator.next();
      if (checkUser(db, thisUser.getId())) {
        if (!thisUser.getHidden()) {
          thisUser.setHidden(true);
          thisUser.update(db);
          flag = true;
        }
      } else if (thisUser.getHidden()) {
        thisUser.setHidden(false);
        thisUser.update(db);
        flag = true;
      }
    }
    if (flag) {
      try {
        if (thisSystem != null) {
          thisSystem.updateHierarchy(db);
        }
        System.out.println("ExitValue: 0");
      } catch (Exception e) {
        System.out.println("ExitValue: 1");
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param userId Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public boolean checkUser(Connection db, int userId) throws Exception {
    int valid = 0;
    OrganizationList sourceAccounts = null;
    ContactList sourceContacts = null;
    UserList sourceUsers = null;
    TicketList sourceOpenTickets = null;
    RevenueList sourceRevenue = null;
    AssignmentList sourceAssignments = null;
    OpportunityList sourceOpportunities = null;
    OpportunityList sourceOpenOpportunities = null;
    CallList sourceActivities = null;

    sourceAccounts = new OrganizationList();
    sourceAccounts.setOwnerId(userId);
    sourceAccounts.buildList(db);
    valid += sourceAccounts.size();

    sourceContacts = new ContactList();
    sourceContacts.setOwner(userId);
    sourceContacts.setIncludeAllSites(true);
    sourceContacts.setBuildDetails(false);
    sourceContacts.setBuildTypes(false);
    sourceContacts.buildList(db);
    valid += sourceContacts.size();

    sourceUsers = new UserList();
    sourceUsers.setManagerId(userId);
    sourceUsers.buildList(db);
    valid += sourceUsers.size();

    sourceOpenTickets = new TicketList();
    sourceOpenTickets.setAssignedTo(userId);
    sourceOpenTickets.setIncludeAllSites(true);
    sourceOpenTickets.setOnlyOpen(true);
    sourceOpenTickets.buildList(db);
    valid += sourceOpenTickets.size();

    sourceRevenue = new RevenueList();
    sourceRevenue.setOwner(userId);
    sourceRevenue.buildList(db);
    valid += sourceRevenue.size();

    sourceAssignments = new AssignmentList();
    sourceAssignments.setAssignmentsForUser(userId);
    sourceAssignments.setIncompleteOnly(true);
    sourceAssignments.buildList(db);
    valid += sourceAssignments.size();

    sourceOpportunities = new OpportunityList();
    sourceOpportunities.setOwner(userId);
    sourceOpportunities.buildList(db);
    valid += sourceOpportunities.size();

    sourceOpenOpportunities = new OpportunityList();
    sourceOpenOpportunities.setOwner(userId);
    sourceOpenOpportunities.setQueryOpenOnly(true);
    sourceOpenOpportunities.buildList(db);
    valid += sourceOpenOpportunities.size();

    sourceActivities = new CallList();
    sourceActivities.setOwner(userId);
    sourceActivities.buildList(db);
    valid += sourceActivities.size();

    return (valid == 0);
  }
}

