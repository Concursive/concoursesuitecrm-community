package com.darkhorseventures.cfsbase;

import java.util.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    July 27, 2001
 *@version    $Id$
 */
public class CalendarEvent implements Comparable {

  //General Information
  protected String subject = "";
  protected String location = "";
  protected String category = "";
  protected String icon = "";

  protected String details = "";
  //Body of the event
  protected String HTMLClass = "";
  //Special HTML formatting
  protected String link = "";
  //Link where event goes to

  //Date
  protected String month = "";
  protected String day = "";
  protected String year = "";

  //Time
  protected String time = "00:00";
  protected String hour = "";
  protected String minutes = "";
  protected String ampm = "";
  protected String duration = "";

  //Repeat
  protected int repeatType = 0;
  //Every [x] weeks [s,m,t,w,r,f]
  //On the [first] [Sunday] of every [1] months
  //Every month
  //Every year
  //--
  //Repeat until date
  //Repeat forever

  //Invites
  protected Vector invites = null;
  //email address/names
  //Show guest list to guests?

  //Reminder
  protected Vector reminders = null;
  //Alert me via Instant Message
  //Alert me via Pager/Cell Phone/Fax
  //Send email to ...
  //When? 10,15,20,30,45 minutes before; 1,2 hours before; on the day, 1 day before, 2 days before

  //Other Info
  protected String comments = "";

  private Comparator eventComparator = new comparatorEvent();


  //Telephone Number

  //Email Address
  //URL

  /**
   *  Constructor for the CalendarEvent object
   *
   *@since
   */
  public CalendarEvent() {
  }


  /**
   *  Sets the Month attribute of the CalendarEvent object
   *
   *@param  tmp  The new Month value
   *@since
   */
  public void setMonth(String tmp) {
    month = tmp;
  }


  /**
   *  Sets the Day attribute of the CalendarEvent object
   *
   *@param  tmp  The new Day value
   *@since
   */
  public void setDay(String tmp) {
    day = tmp;
  }


  /**
   *  Sets the Year attribute of the CalendarEvent object
   *
   *@param  tmp  The new Year value
   *@since
   */
  public void setYear(String tmp) {
    year = tmp;
  }


  /**
   *  Sets the Time attribute of the CalendarEvent object
   *
   *@param  tmp  The new Time value
   *@since
   */
  public void setTime(String tmp) {
    if (time != null && !time.equals("")) {
      time = tmp;
    } else {
      time = "00:00";
    }
  }


  /**
   *  Sets the Subject attribute of the CalendarEvent object
   *
   *@param  tmp  The new Subject value
   *@since
   */
  public void setSubject(String tmp) {
    subject = tmp;
  }


  /**
   *  Sets the Category attribute of the CalendarEvent object
   *
   *@param  tmp  The new Category value
   *@since
   */
  public void setCategory(String tmp) {
    category = tmp;
    if (category.equals("event")) {
      icon = "<img border=0 src=\"/images/event-timed.gif\" align=texttop width=12 height=12>";
    } else if (category.equals("holiday")) {
      icon = "<img border=0 src=\"/images/event-holiday.gif\" align=texttop width=12 height=12>";
    }
  }


  /**
   *  Sets the HTMLClass attribute of the CalendarEvent object
   *
   *@param  tmp  The new HTMLClass value
   *@since
   */
  public void setHTMLClass(String tmp) {
    HTMLClass = tmp;
  }


  /**
   *  Sets the Link attribute of the CalendarEvent object
   *
   *@param  tmp  The new Link value
   *@since
   */
  public void setLink(String tmp) {
    link = tmp;
  }


  /**
   *  Gets the Subject attribute of the CalendarEvent object
   *
   *@return    The Subject value
   *@since
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the Category attribute of the CalendarEvent object
   *
   *@return    The Category value
   *@since
   */
  public String getCategory() {
    return category;
  }


  /**
   *  Gets the Icon attribute of the CalendarEvent object
   *
   *@return    The Icon value
   *@since
   */
  public String getIcon() {
    return icon;
  }


  /**
   *  Gets the DateTimeString attribute of the CalendarEvent object
   *
   *@return    The DateTimeString value
   *@since
   */
  public String getDateTimeString() {
    return (year + "-" + month + "-" + day + " " + time);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public String toString() {
    return "" + month + "/" + day + "/" + year + ": " + category;
  }


  /**
   *  Description of the Method
   *
   *@param  object  Description of Parameter
   *@return         Description of the Returned Value
   *@since
   */
  public int compareTo(Object object) {
    return (eventComparator.compare(this, object));
  }


  /**
   *  Description of the Method
   *
   *@param  object  Description of Parameter
   *@return         Description of the Returned Value
   *@since
   */
  public int compareDateTo(Object object) {
    return (eventComparator.compare(this, object));
  }


  /**
   *  Description of the Class
   *
   *@author     mrajkowski
   *@created    July 27, 2001
   *@version    $Id$
   */
  class comparatorEvent implements Comparator {
    /**
     *  Description of the Method
     *
     *@param  left   Description of Parameter
     *@param  right  Description of Parameter
     *@return        Description of the Returned Value
     *@since
     */
    public int compare(Object left, Object right) {
      return (((CalendarEvent)left).getDateTimeString().compareTo(((CalendarEvent)right).getDateTimeString()));
    }
  }
}

