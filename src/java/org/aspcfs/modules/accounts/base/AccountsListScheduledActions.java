package com.darkhorseventures.cfsbase;

import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.webutils.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id$
 */
public class AccountsListScheduledActions extends OrganizationList implements ScheduledActions {

  private int userId = -1;


  /**
   *  Constructor for the AccountsListScheduledActions object
   */
  public AccountsListScheduledActions() { }


  /**
   *  Sets the userId attribute of the AccountsListScheduledActions object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   *  Gets the userId attribute of the AccountsListScheduledActions object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Description of the Method
   *
   *@param  companyCalendar  Description of the Parameter
   *@param  db               Description of the Parameter
   *@return                  Description of the Return Value
   */
  public String calendarAlerts(CalendarView companyCalendar, Connection db) {
    System.out.println("AccountsListScheduledActions --> Building Account Alerts ");
    PagedListInfo orgAlertPaged = new PagedListInfo();
    orgAlertPaged.setMaxRecords(20);
    orgAlertPaged.setColumnToSortBy("alertdate");

    this.setPagedListInfo(orgAlertPaged);
    this.setEnteredBy(this.getUserId());
    this.setHasAlertDate(true);

    /*
     *  OrganizationList expireOrgs = new OrganizationList();
     *  expireOrgs.setPagedListInfo(orgAlertPaged);
     *  expireOrgs.setEnteredBy(this.getUserId());
     *  expireOrgs.setHasExpireDate(true);
     */
    try {
      this.buildList(db);
      //expireOrgs.buildList(db);
    } catch (SQLException e) {
      System.out.println("AccountsListScheduledActions --> calendarAlerts SQLException ");
    }

    Iterator n = this.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
      companyCalendar.addEvent(thisOrg.getAlertDateStringLongYear(), "", thisOrg.getName() + ": " + thisOrg.getAlertText(), "Accounts", thisOrg.getOrgId());
    }

    /*
     *  Iterator m = expireOrgs.iterator();
     *  while (m.hasNext()) {
     *  Organization thatOrg = (Organization) m.next();
     *  companyCalendar.addEvent(thatOrg.getContractEndDateStringLongYear(), "", thatOrg.getName() + ": " + "Contract Expiration", "Account", thatOrg.getOrgId());
     *  }
     */
    return ("CalAccountsOK");
  }
}

