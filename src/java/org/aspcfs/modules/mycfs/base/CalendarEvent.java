package com.darkhorseventures.cfsbase;

import java.util.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    July 27, 2001
 *@version    $Id: CalendarEvent.java,v 1.1.1.1 2002/01/14 19:49:24 mrajkowski
 *      Exp $
 */
public class CalendarEvent implements Comparable {

  //General Information
  protected String subject = "";
  protected String location = "";
  protected String category = "";
  protected String icon = "";

  protected int id = -1;
  protected int idsub = -1;

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
  protected ArrayList invites = null;
  //email address/names
  //Show guest list to guests?

  //Reminder
  protected ArrayList reminders = null;
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
   */
  public CalendarEvent() { }

  public void setDate(java.sql.Timestamp eventDate) {
    if (eventDate != null) {
      SimpleDateFormat shortDateFormat = new SimpleDateFormat("M/d/yyyy");
      String eventDateString = shortDateFormat.format(eventDate);
      StringTokenizer st = new StringTokenizer(eventDateString, "/");
      if (st.hasMoreTokens()) {
        this.setMonth(st.nextToken());
        this.setDay(st.nextToken());
        this.setYear(st.nextToken());
      }
    }
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
   *  Sets the Icon attribute of the CalendarEvent object
   *
   *@param  icon  The new Icon value
   *@since
   */
  public void setIcon(String icon) {
    this.icon = icon;
  }


  /**
   *  Sets the idsub attribute of the CalendarEvent object
   *
   *@param  idsub  The new idsub value
   */
  public void setIdsub(int idsub) {
    this.idsub = idsub;
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
   *  Sets the id attribute of the CalendarEvent object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the link attribute of the CalendarEvent object
   *
   *@param  tmp  The new link value
   */
  public void setLink(String tmp) {
    this.link = tmp;
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
   *  Gets the idsub attribute of the CalendarEvent object
   *
   *@return    The idsub value
   */
  public int getIdsub() {
    return idsub;
  }


  /**
   *  Gets the id attribute of the CalendarEvent object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the link attribute of the CalendarEvent object
   *
   *@return    The link value
   */
  public String getLink() {

    if (category.equalsIgnoreCase("Opportunity")) {
      return "<a href=\"/Leads.do?command=DetailsOpp&id=" + id + "\">";
    } else if (category.equalsIgnoreCase("Account")) {
      return "<a href=\"/Accounts.do?command=Details&orgId=" + id + "\">";
    } else if (category.equalsIgnoreCase("Assignment")) {
      return "<a href=\"/ProjectManagementAssignments.do?command=Modify&aid=" + idsub + "&pid=" + id + "\">";
    } else if (category.equalsIgnoreCase("Contact Call")) {
      return "<a href=\"/ExternalContactsCalls.do?command=Details&id=" + idsub + "&contactId=" + id + "\">";
    } else if (category.equalsIgnoreCase("Opportunity Call")) {
      return "<a href=\"/LeadsCalls.do?command=Details&id=" + idsub + "&oppId=" + id + "\">";
    }

    return link;
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
    if (!icon.equals("")) {
      return icon;
    } else if (category.equals("event")) {
      return "<img border=0 src=\"/images/event-timed.gif\" alt=\"Event:" + this.getSubject() + "\" align=texttop width=12 height=12>";
    } else if (category.equalsIgnoreCase("holiday")) {
      return "<img border=0 src=\"/images/event-holiday.gif\" alt=\"Holiday:" + this.getSubject() + "\" align=texttop width=12 height=12>";
    } else if (category.equalsIgnoreCase("Opportunity")) {
      return "<img border=0 src=\"images/alertopp.gif\" alt=\"Opp:" + this.getSubject() + "\" align=texttop>";
    } else if (category.equalsIgnoreCase("Contact Call") || category.equalsIgnoreCase("Opportunity Call")) {
      return "<img border=0 src=\"images/alertcall.gif\" alt=\"Call:" + this.getSubject() + "\" align=texttop>";
    } else if (category.equalsIgnoreCase("Assignment")) {
      return "<img border=0 src=\"images/alertassignment.gif\" alt=\"Assignment:" + this.getSubject() + "\" align=texttop>";
    } else if (category.equalsIgnoreCase("Account")) {
      return "<img border=0 src=\"images/alertassignment.gif\" alt=\"Account:" + this.getSubject() + "\" align=texttop>";
    }

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
  
  public String getDateString() {
    return (month + "/" + day + "/" + year);
  }


  /**
   *  Gets the holiday attribute of the CalendarEvent object
   *
   *@return    The holiday value
   */
  public boolean isHoliday() {
    return ("holiday").equalsIgnoreCase(this.category);
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
   *@version    $Id: CalendarEvent.java,v 1.1.1.1 2002/01/14 19:49:24 mrajkowski
   *      Exp $
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
      return (((CalendarEvent) left).getDateTimeString().compareTo(((CalendarEvent) right).getDateTimeString()));
    }

  }
}

