package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import java.text.DateFormat;
import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    July 16, 2004
 *@version    $id:exp$
 */
public class QuoteListScheduledActions extends QuoteList implements ScheduledActions {

  private int userId = -1;
  private ActionContext context = null;
  private CFSModule module = null;


  /**
   *Constructor for the QuoteListScheduledActions object
   */
  public QuoteListScheduledActions() { }


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
   *  Constructor for the calendarActions object
   *
   *@param  db                Description of the Parameter
   *@param  companyCalendar   Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {

   /*  try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("QuoteListScheduledActions-> Building Quote Alerts for user " + module.getUserId(context));
      }

      //get User
      User thisUser = module.getUser(context, module.getUserId(context));

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      Timestamp todayTimestamp = new Timestamp(System.currentTimeMillis());
      String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, todayTimestamp);

      // Retrieve the quote status lookup list
      SystemStatus systemStatus = module.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");

      // Build quotes for this user that are in their account
      // and pending their approval
      this.setOrgId(thisUser.getContact().getOrgId());
      this.setStatusId(list.getIdFromValue("Pending customer acceptance"));
      quoteList.buildList(db);
      Iterator quotes = quoteList.iterator();
      while (quotes.hasNext()) {
        Quote thisQuote = (Quote) quotes.next();
        companyCalendar.addEvent(alertDate, CalendarEventList.EVENT_TYPES[10], thisQuote);
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Quote Calendar Alerts");
    } */
  }



  /**
   *  Description of the Method
   *
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {
    /* try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TaskListScheduledActions-> Building Alert Count ");
      }
      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();
      // Add Task count to calendar
      this.setOwner(module.getUserId(context));
      HashMap dayEvents = this.queryRecordCount(db, timeZone);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(thisDay, CalendarEventList.EVENT_TYPES[0], dayEvents.get(thisDay));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TaskListScheduledActions-> Added Tasks for " + thisDay + "- " + String.valueOf(dayEvents.get(thisDay)));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Task Calendar Alerts");
    } */
  }

}

