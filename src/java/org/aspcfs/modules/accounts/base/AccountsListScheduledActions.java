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
package org.aspcfs.modules.accounts.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Dependency;
import java.text.DateFormat;
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

  private ActionContext context = null;
  private CFSModule module = null;


  /**
   *  Constructor for the AccountsListScheduledActions object
   */
  public AccountsListScheduledActions() { }


  /**
   *  Sets the module attribute of the QuoteListScheduledActions object
   *
   *@param  tmp  The new module value
   */
  public void setModule(CFSModule tmp) {
    this.module = tmp;
  }


  /**
   *  Sets the context attribute of the QuoteListScheduledActions object
   *
   *@param  tmp  The new context value
   */
  public void setContext(ActionContext tmp) {
    this.context = tmp;
  }


  /**
   *  Gets the context attribute of the QuoteListScheduledActions object
   *
   *@return    The context value
   */
  public ActionContext getContext() {
    return context;
  }


  /**
   *  Gets the module attribute of the QuoteListScheduledActions object
   *
   *@return    The module value
   */
  public CFSModule getModule() {
    return module;
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
      //get the userId
      int userId = module.getUserId(context);

      String alertType = companyCalendar.getCalendarInfo().getCalendarDetailsView();
      this.setOwnerId(userId);
      if (alertType.equalsIgnoreCase("AccountsAlertDates")) {
        this.addAlertDates(companyCalendar, db);
      } else if (alertType.equalsIgnoreCase("AccountsContractEndDates")) {
        this.addContractEndDates(companyCalendar, db);
      } else {
        this.addAlertDates(companyCalendar, db);
        this.clear();
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

    //get TimeZone
    TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

    this.setHasExpireDate(false);
    this.setHasAlertDate(true);
    this.buildShortList(db);
    Iterator n = this.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
      String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisOrg.getAlertDate());
      companyCalendar.addEvent(alertDate, CalendarEventList.EVENT_TYPES[3], thisOrg);
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

    //get TimeZone
    TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

    this.setHasAlertDate(false);
    this.setHasExpireDate(true);
    this.buildShortList(db);

    Iterator n = this.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
      String endDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisOrg.getContractEndDate());
      companyCalendar.addEvent(endDate, CalendarEventList.EVENT_TYPES[3], thisOrg);
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
      System.out.println("AccountsListScheduledActions --> Building Alert Date Count ");
    }

    //get the userId
    int userId = module.getUserId(context);

    String alertType = companyCalendar.getCalendarInfo().getCalendarDetailsView();
    boolean anyAlert = false;
    //get TimeZone
    TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

    this.setOwnerId(userId);

    if ("AccountsAlertDates".equals(alertType)) {
      this.setHasExpireDate(false);
      this.setHasAlertDate(true);
    } else if ("AccountsContractEndDates".equals(alertType)) {
      this.setHasExpireDate(true);
      this.setHasAlertDate(false);
    } else {
      this.setHasExpireDate(true);
      this.setHasAlertDate(false);
      anyAlert = true;
    }

    HashMap dayEvents = this.queryRecordCount(db, timeZone, new HashMap());

    if (anyAlert) {
      this.setHasExpireDate(false);
      this.setHasAlertDate(true);
      dayEvents = this.queryRecordCount(db, timeZone, dayEvents);
    }

    Set s = dayEvents.keySet();
    Iterator i = s.iterator();
    while (i.hasNext()) {
      String thisDay = (String) i.next();
      companyCalendar.addEventCount(thisDay, CalendarEventList.EVENT_TYPES[3], dayEvents.get(thisDay));
    }
  }
}

