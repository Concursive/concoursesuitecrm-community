package org.aspcfs.modules.accounts.base;

import com.darkhorseventures.database.Connection;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id: AccountsListScheduledActions.java,v 1.4 2002/10/16 15:15:55
 *      mrajkowski Exp $
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
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccountsListScheduledActions --> Building Account Alerts ");
    }
    try {
      String alertType = companyCalendar.getCalendarInfo().getCalendarDetailsView();
      if (alertType.equalsIgnoreCase("AccountsAlertDates")) {
        this.addAlertDates(companyCalendar, db);
      } else if (alertType.equalsIgnoreCase("AccountsContractEndDates")) {
        this.addContractEndDates(companyCalendar, db);
      } else {
        this.addAlertDates(companyCalendar, db);
        this.addContractEndDates(companyCalendar, db);
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }


  /**
   *  Adds a feature to the AlertDates attribute of the
   *  AccountsListScheduledActions object
   *
   *@param  companyCalendar   The feature to be added to the AlertDates
   *      attribute
   *@param  db                The feature to be added to the AlertDates
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  public void addAlertDates(CalendarView companyCalendar, Connection db) throws SQLException {
    OrganizationList alertOrgs = new OrganizationList();
    alertOrgs.setOwnerId(this.getUserId());
    alertOrgs.setHasExpireDate(false);
    alertOrgs.setHasAlertDate(true);
    alertOrgs.buildList(db);
    Iterator n = alertOrgs.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
      companyCalendar.addEvent(thisOrg.getAlertDateStringLongYear(), "", thisOrg.getName() + ": " + thisOrg.getAlertText(), CalendarEventList.EVENT_TYPES[3], thisOrg.getOrgId());
    }
  }


  /**
   *  Adds a feature to the ContractEndDates attribute of the
   *  AccountsListScheduledActions object
   *
   *@param  companyCalendar   The feature to be added to the ContractEndDates
   *      attribute
   *@param  db                The feature to be added to the ContractEndDates
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  public void addContractEndDates(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccountsListScheduledActions --> Building Account Contract End Dates ");
    }
    OrganizationList contractEndOrgs = new OrganizationList();
    contractEndOrgs.setOwnerId(this.getUserId());
    contractEndOrgs.setHasAlertDate(false);
    contractEndOrgs.setHasExpireDate(true);
    contractEndOrgs.buildShortList(db);

    Iterator n = contractEndOrgs.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
      companyCalendar.addEvent(thisOrg.getContractEndDateStringLongYear(), "", thisOrg.getName() + ": " + "Contract Expiration", CalendarEventList.EVENT_TYPES[3], thisOrg.getOrgId());
    }
  }


  /**
   *  Adds a feature to the AlertCount attribute of the
   *  AccountsListScheduledActions object
   *
   *@param  companyCalendar   The feature to be added to the AlertCount
   *      attribute
   *@param  db                The feature to be added to the AlertCount
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  public void addAlertDateCount(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccountsListScheduledActions --> Building Alert Date Count ");
    }
    OrganizationList alertOrgs = new OrganizationList();
    alertOrgs.setOwnerId(this.getUserId());
    alertOrgs.setHasExpireDate(false);
    alertOrgs.setHasAlertDate(true);

    HashMap dayEvents = alertOrgs.queryRecordCount(db);
    Set s = dayEvents.keySet();
    Iterator i = s.iterator();
    while (i.hasNext()) {
      String thisDay = (String) i.next();
      companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[3], thisDay, dayEvents.get(thisDay));
    }
  }


  /**
   *  Adds a feature to the ContractEndDates attribute of the
   *  AccountsListScheduledActions object
   *
   *@param  companyCalendar   The feature to be added to the ContractEndDates
   *      attribute
   *@param  db                The feature to be added to the ContractEndDates
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  public void addContractEndDateCount(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccountsListScheduledActions --> Building Account Contract End Dates ");
    }

    OrganizationList contractEndOrgs = new OrganizationList();
    contractEndOrgs.setOwnerId(this.getUserId());
    contractEndOrgs.setHasAlertDate(false);
    contractEndOrgs.setHasExpireDate(true);
    HashMap dayEvents = contractEndOrgs.queryRecordCount(db);
    Set s = dayEvents.keySet();
    Iterator i = s.iterator();
    while (i.hasNext()) {
      String thisDay = (String) i.next();
      companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[3], thisDay, dayEvents.get(thisDay));
    }

  }


  /**
   *  Description of the Method
   *
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {

    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccountsListScheduledActions --> Building Account Alert Count ");
    }

    try {
      String alertType = companyCalendar.getCalendarInfo().getCalendarDetailsView();
      if (alertType.equalsIgnoreCase("AccountsAlertDates")) {
        this.addAlertDateCount(companyCalendar, db);
      } else if (alertType.equalsIgnoreCase("AccountsContractEndDates")) {
        this.addContractEndDateCount(companyCalendar, db);
      } else {
        this.addAlertDateCount(companyCalendar, db);
        this.addContractEndDateCount(companyCalendar, db);
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

  }

}

