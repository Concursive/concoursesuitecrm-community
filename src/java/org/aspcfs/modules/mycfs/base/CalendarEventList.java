package org.aspcfs.modules.mycfs.base;

import java.util.*;

/**
 *  Description of the Class
 *
 *@author
 *@created    December 18, 2002
 *@version    $Id$
 */
public class CalendarEventList extends ArrayList {

  private java.util.Date date = null;
  private HashMap eventTypes = null;
  //anything added to this array should be added at the end 
  public final static String[] EVENT_TYPES = {"Tasks", "Calls", "Opportunity", "Account Alerts", "Account Contract Alerts", "Contact Calls", "Opportunity Calls", "Holiday", "Assignments", "System Alerts", "Quotes pending your approval", "Requests you have made that are in progress","Open Tickets"};


  /**
   *  Constructor for the CalendarEventList object
   */
  public CalendarEventList() { }


  /**
   *  Sets the date attribute of the CalendarEventList object
   *
   *@param  tmp  The new date value
   */
  public void setDate(java.util.Date tmp) {
    this.date = tmp;
  }


  /**
   *  Sets the eventTypes attribute of the CalendarEventList object
   *
   *@param  eventTypes  The new eventTypes value
   */
  public void setEventTypes(HashMap eventTypes) {
    this.eventTypes = eventTypes;
  }


  /**
   *  Gets the eventTypes attribute of the CalendarEventList object
   *
   *@return    The eventTypes value
   */
  public HashMap getEventTypes() {
    return eventTypes;
  }


  /**
   *  Gets the date attribute of the CalendarEventList object
   *
   *@return    The date value
   */
  public java.util.Date getDate() {
    return date;
  }


  /**
   *  Associates a count with a event Type specifying the number of events of that type for that day.
   *
   *@param  type   The feature to be added to the EventType attribute
   *@param  count  The feature to be added to the EventType attribute
   */
  public void addEventType(String type, Object count) {
    if (eventTypes == null) {
      eventTypes = new HashMap();
    }
    if (eventTypes.get(type) != null) {
      eventTypes.remove(type);
    }
    eventTypes.put(type, count);
  }
}

