package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actions.CFSModule;
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
  private ActionContext context = null;
  private CFSModule module = null;

  /**
   *  Constructor for the TicketListScheduledActions object
   */
  public TicketListScheduledActions() { }

  public void setModule(CFSModule tmp) {
    this.module = tmp;
  }
  
  public void setContext(ActionContext tmp) {
    this.context = tmp;
  }
  
  public ActionContext getContext() {
    return context;
  }
  
  public CFSModule getModule() {
    return module;
  }

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
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TicketListScheduledActions-> Building Ticket Alerts for user " + module.getUserId(context));
      }
      //get User
      User thisUser = module.getUser(context, module.getUserId(context));

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      Timestamp todayTimestamp = new Timestamp(System.currentTimeMillis());
      String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, todayTimestamp);
      // List 1//currently non-functional
      // NOTE: any filters set here must be unset in next list
      this.setOrgId(thisUser.getContact().getOrgId());
      this.setOnlyOpen(true);
      this.setOnlyWithProducts(true);
      this.buildList(db);
      Iterator tickets = this.iterator();
      while (tickets.hasNext()) {
        Ticket thisTicket = (Ticket) tickets.next();
        TicketEventList thisList = (TicketEventList) companyCalendar.getEventList(alertDate, CalendarEventList.EVENT_TYPES[11]);
        thisList.getOpenProductTickets().add(thisTicket);
      }
      
      // List 2
      this.clear();
      this.setOrgId(-1);
      this.setOnlyWithProducts(false);
      // now set the values for this list
      this.setAssignedTo(this.getUserId());
      this.setOnlyAssigned(true);
      this.setHasEstimatedResolutionDate(true);
      this.setOnlyOpen(true);
      this.buildList(db);
      Iterator m = this.iterator();
      while (m.hasNext()) {
        Ticket thisTicket = (Ticket) m.next();
        thisTicket.buildContactInformation(db);
        alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisTicket.getEstimatedResolutionDate());
        TicketEventList thisList = (TicketEventList) companyCalendar.getEventList(alertDate, CalendarEventList.EVENT_TYPES[12]);
        thisList.getOpenTickets().add(thisTicket);
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Ticket Calendar Alerts 2");
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
    // List 1//currently non-functional
    try {
      //get User
      User thisUser = module.getUser(context, module.getUserId(context));
      
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();
      
      this.setOrgId(thisUser.getContact().getOrgId());
      this.setOnlyOpen(true);
      this.setOnlyWithProducts(true);
      HashMap dayEvents = this.queryRecordCount(db, timeZone);
      Iterator i = dayEvents.keySet().iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(thisDay, CalendarEventList.EVENT_TYPES[11], dayEvents.get(thisDay));
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Ticket Calendar Alert Counts 1");
    }
    // List 2
    try {
      //clear the search criteria for the first list
      this.clear();
      this.setOrgId(-1);
      this.setOnlyWithProducts(false);

      //get the userId
      int userId = module.getUserId(context);
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      //set the search criteria for the first list
      this.setAssignedTo(this.getUserId());
      this.setOnlyAssigned(true);
      this.setHasEstimatedResolutionDate(true);
      this.setOnlyOpen(true);
      HashMap dayEvents = this.queryRecordCount(db, timeZone);
      Iterator i = dayEvents.keySet().iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(thisDay, CalendarEventList.EVENT_TYPES[12], dayEvents.get(thisDay));
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Ticket Calendar Alert Counts 2");
    }
  }
}
