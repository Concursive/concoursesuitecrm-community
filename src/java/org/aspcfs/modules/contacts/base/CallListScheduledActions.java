package org.aspcfs.modules.contacts.base;

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
 *@version    $Id: CallListScheduledActions.java,v 1.2 2002/10/04 19:25:45
 *      mrajkowski Exp $
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
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CallListScheduledActions --> Building Call Alerts ");
    }
    try {
      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();
      
      this.setEnteredBy(this.getUserId());
      this.setHasAlertDate(true);
      this.buildList(db);
      Iterator m = this.iterator();
      while (m.hasNext()) {
        Call thisCall = (Call) m.next();
        CalendarEvent thisEvent = null;
        String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisCall.getAlertDate());
        if (thisCall.getOppHeaderId() == -1 && thisCall.getContactId() > -1) {
          thisEvent = companyCalendar.addEvent(alertDate, "", 
              (thisCall.getContactName() != null && !"".equals(thisCall.getContactName()) ? thisCall.getContactName() + ": " : "") + thisCall.getAlertText(), CalendarEventList.EVENT_TYPES[5], thisCall.getContactId(), thisCall.getId());
        } else {
          thisEvent = companyCalendar.addEvent(alertDate, "", 
              (thisCall.getContactName() != null && !"".equals(thisCall.getContactName()) ? thisCall.getContactName() + ": " : "") + thisCall.getAlertText(), CalendarEventList.EVENT_TYPES[6], thisCall.getOppHeaderId(), thisCall.getId());
        }
        if (thisCall.getContactId() > 0) {
          String contactLink = "[<a href=\"javascript:popURL('ExternalContacts.do?command=ContactDetails&id=" + 
              thisCall.getContactId() + "&popup=true&popupType=inline','Details','650','500','yes','yes');\">Contact Link</a>]";
          thisEvent.addRelatedLink(contactLink);
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Call Calendar Alerts");
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
      System.out.println("CallListScheduledActions --> Building Alert Counts ");
    }
    try {
      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();
      
      this.setEnteredBy(this.getUserId());
      this.setHasAlertDate(true);
      HashMap dayEvents = this.queryRecordCount(db, timeZone);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[5], thisDay, dayEvents.get(thisDay));
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Call Calendar Alert Counts");
    }
  }
}

