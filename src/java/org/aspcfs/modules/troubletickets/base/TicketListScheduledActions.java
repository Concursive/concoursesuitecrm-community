package org.aspcfs.modules.troubletickets.base;

import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import java.text.DateFormat;
import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    June 30, 2004
 *@version    $Id$
 */
public class TicketListScheduledActions extends TicketList implements ScheduledActions {

  private int userId = -1;


  /**
   *  Constructor for the TicketListScheduledActions object
   */
  public TicketListScheduledActions() { }


  /**
   *  Sets the userId attribute of the TicketListScheduledActions object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   *  Gets the userId attribute of the TicketListScheduledActions object
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
      System.out.println("TicketListScheduledActions --> Building Call Alerts ");
    }
    try {
      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      this.setAssignedTo(this.getUserId());
      this.setOnlyAssigned(true);
      this.setHasEstimatedResolutionDate(true);
      this.setOnlyOpen(true);
      this.buildList(db);
      Iterator m = this.iterator();
      while (m.hasNext()) {
        Ticket thisTicket = (Ticket) m.next();
        thisTicket.buildContactInformation(db);
        CalendarEvent thisEvent = null;
        String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisTicket.getEstimatedResolutionDate());
        thisEvent = companyCalendar.addEvent(alertDate, "",
            "Ticket# " + thisTicket.getPaddedId() + " (" + thisTicket.getCompanyName() + (thisTicket.getThisContact().getValidName() != null && !"".equals(thisTicket.getThisContact().getValidName()) ? ": " + thisTicket.getThisContact().getValidName() + ")" : ")"), CalendarEventList.EVENT_TYPES[12], thisTicket.getId());
    
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Ticket Calendar Alerts");
    }
  }


  /**
   *  Build event categories and count of occurance of each category.
   *
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {

    if (System.getProperty("DEBUG") != null) {
      System.out.println("TicketListScheduledActions --> Building Alert Counts ");
    }
    try {
      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      this.setAssignedTo(this.getUserId());
      this.setOnlyAssigned(true);
      this.setHasEstimatedResolutionDate(true);
      this.setOnlyOpen(true);
      HashMap dayEvents = this.queryRecordCount(db, timeZone);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[12], thisDay, dayEvents.get(thisDay));
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Ticket Calendar Alert Counts");
    }
  }
  
  public void setAlertRangeEnd(java.sql.Timestamp endDate){}
  public void setAlertRangeStart(java.sql.Timestamp startDate){}
}

