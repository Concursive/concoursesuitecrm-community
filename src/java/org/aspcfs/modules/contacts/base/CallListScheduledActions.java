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
public class CallListScheduledActions extends CallList implements ScheduledActions {

  private int userId = -1;


  /**
   *  Constructor for the CallListScheduledActions object
   */
  public CallListScheduledActions() { }


  /**
   *  Sets the userId attribute of the CallListScheduledActions object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   *  Gets the userId attribute of the CallListScheduledActions object
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
  public String calendarAlerts(CalendarView companyCalendar, Connection db) throws SQLException{
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("CallListScheduledActions --> Building Call Alerts ");
      }
      PagedListInfo alertPaged = new PagedListInfo();
      alertPaged.setMaxRecords(20);
      alertPaged.setColumnToSortBy("alertdate");
      this.setEnteredBy(this.getUserId());
      this.setHasAlertDate(true);
      this.buildList(db);
      Iterator m = this.iterator();
      while (m.hasNext()) {
        Call thisCall = (Call) m.next();
        if (thisCall.getOppId() == -1 && thisCall.getContactId() > -1) {
          companyCalendar.addEvent(thisCall.getAlertDateStringLongYear(), "", thisCall.getSubject(), "Contact Calls", thisCall.getContactId(), thisCall.getId());
        } else {
          companyCalendar.addEvent(thisCall.getAlertDateStringLongYear(), "", thisCall.getSubject(), "Opportunity Calls", thisCall.getOppId(), thisCall.getId());
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Call Calendar Alerts");
    }
    return "CalendarCallsOK";
  }
}

