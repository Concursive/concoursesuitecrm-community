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
package org.aspcfs.modules.mycfs.base;

import java.util.*;
import org.aspcfs.utils.ObjectUtils;

/**
 *  Description of the Class
 *
 *@author
 *@created    December 18, 2002
 *@version    $Id: CalendarEventList.java,v 1.12 2004/08/31 12:48:26 mrajkowski
 *      Exp $
 */
public class CalendarEventList extends HashMap {

  private java.util.Date date = null;
  private HashMap eventTypes = null;
  //anything added to this array should be added at the end
  public final static String[] EVENT_TYPES = {
      "Tasks",
      "Calls",
      "Opportunities",
      "Account Alerts",
      "Account Contract Alerts",
      "Contact Calls",
      "Opportunity Calls",
      "Holiday",
      "Assignments",
      "System Alerts",
      "Quotes",
      "Tickets",
      "Ticket Requests",
      "Pending Calls"};


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
   *  Adds a feature to the Event attribute of the CallEventList object
   *
   *@param  eventType  The feature to be added to the Event attribute
   *@param  event      The feature to be added to the Event attribute
   */
  public void addEvent(String eventType, Object event) {
    Object categoryEvents = null;
    if (this.containsKey(eventType)) {
      categoryEvents = this.get(eventType);
    } else {
      categoryEvents = addCategoryEventList(eventType);
    }
    if (eventType.equalsIgnoreCase(EVENT_TYPES[7])) {
      ((ArrayList) categoryEvents).add(event);
    } else {
      ObjectUtils.invokeMethod(categoryEvents, "addEvent", event);
    }
  }


  /**
   *  Gets the events attribute of the CalendarEventList object
   *
   *@param  eventType  Description of the Parameter
   *@return            The events value
   */
  public Object getEvents(String eventType) {
    Object categoryEvents = null;
    if (this.containsKey(eventType)) {
      categoryEvents = this.get(eventType);
    } else {
      categoryEvents = addCategoryEventList(eventType);
    }
    return categoryEvents;
  }


  /**
   *  Associates a count with a event Type specifying the number of events of
   *  that type for that day.
   *
   *@param  eventType   The feature to be added to the EventCount attribute
   *@param  eventCount  The feature to be added to the EventCount attribute
   */
  public void addEventCount(String eventType, Object eventCount) {
    Object categoryEvents = null;
    if (this.containsKey(eventType)) {
      categoryEvents = this.get(eventType);
    } else {
      categoryEvents = addCategoryEventList(eventType);
    }
    ObjectUtils.invokeMethod(categoryEvents, "setSize", eventCount);
  }


  /**
   *  Adds a feature to the CategoryEventList attribute of the CallEventList
   *  object
   *
   *@param  eventType  The feature to be added to the CategoryEventList
   *      attribute
   *@return            Description of the Return Value
   */
  public Object addCategoryEventList(String eventType) {
    Object thisList = null;
    if (eventType.equalsIgnoreCase(EVENT_TYPES[0])) {
      thisList = new TaskEventList();
    } else if (eventType.equals(EVENT_TYPES[1])) {
      thisList = new CallEventList();
    } else if (eventType.equals(EVENT_TYPES[13])) {
      thisList = new CallEventList();
    } else if (eventType.equals(EVENT_TYPES[2])) {
      thisList = new OpportunityEventList();
    } else if (eventType.equals(EVENT_TYPES[3])) {
      thisList = new OrganizationEventList();
    } else if (eventType.equals(EVENT_TYPES[10])) {
      thisList = new QuoteEventList();
    } else if (eventType.equals(EVENT_TYPES[11])) {
      thisList = new TicketEventList();
    } else if (eventType.equals(EVENT_TYPES[12])) {
      thisList = new TicketEventList();
    } else if (eventType.equals(EVENT_TYPES[7])) {
      thisList = new ArrayList();
    }
    this.put(eventType, thisList);
    return thisList;
  }
}

