package org.aspcfs.modules.pipeline.base;

import com.darkhorseventures.framework.actions.ActionContext;
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
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id: OpportunityListScheduledActions.java,v 1.6 2002/12/18
 *      21:05:57 chris Exp $
 */
public class OpportunityListScheduledActions extends OpportunityComponentList implements ScheduledActions {

  private ActionContext context = null;
  private CFSModule module = null;


  /**
   *  Constructor for the OpportunityListScheduledActions object
   */
  public OpportunityListScheduledActions() { }


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
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("OppListScheduledActions -> Building opportunity alerts");
      }
      //get the userId
      int userId = module.getUserId(context);

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      this.setOwner(userId);
      this.setHasAlertDate(true);
      this.buildShortList(db);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("OppListScheduledActions -> size of opps: " + this.size());
      }
      Iterator n = this.iterator();
      while (n.hasNext()) {
        OpportunityComponent thisOpp = (OpportunityComponent) n.next();
        String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisOpp.getAlertDate());
        companyCalendar.addEvent(alertDate, CalendarEventList.EVENT_TYPES[2], thisOpp);
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
      System.out.println("OppListScheduledActions -> Building Alert Counts ");
    }

    try {
      //get the userId
      int userId = module.getUserId(context);

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      this.setOwner(userId);
      this.setHasAlertDate(true);

      HashMap dayEvents = this.queryRecordCount(db, timeZone);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(thisDay, CalendarEventList.EVENT_TYPES[2], dayEvents.get(thisDay));
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Opportunity Calendar Alert Counts");
    }
  }
}

