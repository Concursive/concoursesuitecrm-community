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
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccountsListScheduledActions --> Building Account Alerts ");
    }
    
    String alertType = companyCalendar.getCalendarInfo().getCalendarDetailsView();
    if(alertType.equalsIgnoreCase("AccountsAlertDates")){
      this.addAlertDates(companyCalendar,db);
    }else if(alertType.equalsIgnoreCase("AccountsContractEndDates")){
      this.addContractEndDates(companyCalendar,db);
    }else{
      this.addAlertDates(companyCalendar,db);
      this.addContractEndDates(companyCalendar,db);
    }
    return ("CalAccountsOK");
  }
  
  
  public void addAlertDates(CalendarView companyCalendar, Connection db){
    OrganizationList alertOrgs = new OrganizationList();
    alertOrgs.setOwnerId(this.getUserId());
    alertOrgs.setHasExpireDate(false);
    alertOrgs.setHasAlertDate(true);
    
    try {
      alertOrgs.buildList(db);
    } catch (SQLException e) {
      System.out.println("AccountsListScheduledActions --> Alerts SQLException ");
    }

    Iterator n = alertOrgs.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
        companyCalendar.addEvent(thisOrg.getAlertDateStringLongYear(), "", thisOrg.getName() + ": " + thisOrg.getAlertText(), "Accounts", thisOrg.getOrgId());
    }
  }
  
  
  public void addContractEndDates(CalendarView companyCalendar, Connection db){
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccountsListScheduledActions --> Building Account Contract End Dates ");
    }
    OrganizationList contractEndOrgs = new OrganizationList(); 
    contractEndOrgs.setOwnerId(this.getUserId());
    contractEndOrgs.setHasAlertDate(false);
    contractEndOrgs.setHasExpireDate(true);
    try {
      contractEndOrgs.buildList(db);
    } catch (SQLException e) {
      System.out.println("AccountsListScheduledActions --> calendarAlerts SQLException ");
    }

    Iterator n = contractEndOrgs.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
        companyCalendar.addEvent(thisOrg.getContractEndDateStringLongYear(), "", thisOrg.getName() + ": " + "Contract Expiration", "Accounts", thisOrg.getOrgId());
    }
  }
}

