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
public class OpportunityListScheduledActions extends OpportunityList implements ScheduledActions {

  private int userId = -1;
  
  public OpportunityListScheduledActions() { }

 
  public void setUserId(int userId) {
    this.userId = userId;
  }

  
  public int getUserId() {
    return userId;
  }


  /**
   *  Constructor for the calendarActions object
   *
   *@param  db               Description of the Parameter
   *@param  companyCalendar  Description of the Parameter
   *@return                  Description of the Return Value
   */
  public String calendarAlerts(CalendarView companyCalendar, Connection db) throws SQLException{
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("OpportunityListScheduledActions -> Building opportunity alerts");
      }
      PagedListInfo alertPaged2 = new PagedListInfo();
      alertPaged2.setItemsPerPage(0);
      alertPaged2.setColumnToSortBy("oc.alertdate");
      this.setPagedListInfo(alertPaged2);
      this.setOwner(this.getUserId());
      this.setHasAlertDate(true);
      this.buildList(db);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("MyCFS-> size of opps: " + this.size());
      }
      Iterator n = this.iterator();
      while (n.hasNext()) {
        Opportunity thisOpp = (Opportunity) n.next();
        companyCalendar.addEvent(thisOpp.getAlertDateStringLongYear(), "",
          thisOpp.getAccountName() + ": " +
          thisOpp.getDescription()  + 
          " (" + thisOpp.getAlertText() + ")", 
          "Opportunity", thisOpp.getId());
      }
    } catch (SQLException e) {
     throw new SQLException("Error Building Opportunity Calendar Alerts");
    }
      return "CalendarOppsOK";
  }
}

