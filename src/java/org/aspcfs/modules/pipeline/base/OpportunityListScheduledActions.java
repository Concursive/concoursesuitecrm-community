package org.aspcfs.modules.pipeline.base;

import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id: OpportunityListScheduledActions.java,v 1.6 2002/12/18
 *      21:05:57 chris Exp $
 */
public class OpportunityListScheduledActions extends OpportunityComponentList implements ScheduledActions {

  private int userId = -1;


  /**
   *  Constructor for the OpportunityListScheduledActions object
   */
  public OpportunityListScheduledActions() { }


  /**
   *  Sets the userId attribute of the OpportunityListScheduledActions object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   *  Gets the userId attribute of the OpportunityListScheduledActions object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Constructor for the calendarActions object
   *
   *@param  db                Description of the Parameter
   *@param  companyCalendar   Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("OpportunityListScheduledActions -> Building opportunity alerts");
      }
      this.setOwner(this.getUserId());
      this.setHasAlertDate(true);
      this.buildShortList(db);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("OppListScheduledActions -> size of opps: " + this.size());
      }
      Iterator n = this.iterator();
      while (n.hasNext()) {
        OpportunityComponent thisOpp = (OpportunityComponent) n.next();
        companyCalendar.addEvent(thisOpp.getAlertDateStringLongYear(), "",
            ((thisOpp.getAccountName() != null && !thisOpp.getAccountName().equals("")) ? thisOpp.getAccountName() + ": " : "") +
            thisOpp.getDescription() +
            " (" + thisOpp.getAlertText() + ")",
            "Opportunity", thisOpp.getId());
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Opportunity Calendar Alerts");
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
      System.out.println("OppListScheduledActions --> Building Alert Counts ");
    }
    try {
      this.setOwner(this.getUserId());
      this.setHasAlertDate(true);
      HashMap dayEvents = this.queryRecordCount(db);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[2], thisDay, dayEvents.get(thisDay));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("OppListScheduledActions --> Added Opps for day " + thisDay + "- " + String.valueOf(dayEvents.get(thisDay)));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Opportunity Calendar Alert Counts");
    }
  }
}

