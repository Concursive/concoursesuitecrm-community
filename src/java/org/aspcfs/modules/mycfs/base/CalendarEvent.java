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

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: CalendarEvent.java,v 1.1.1.1 2002/01/14 19:49:24 mrajkowski
 *          Exp $
 * @created July 27, 2001
 */
public class CalendarEvent implements Comparable {

  //General Information
  protected String subject = "";
  protected String location = "";
  protected String category = "";
  protected String icon = "";

  protected int id = -1;
  protected int idsub = -1;

  //Body of the event
  protected String details = "";
  //Special HTML formatting
  protected String HTMLClass = "";
  //Link where event goes to
  protected String link = "";
  //status of event
  protected int status = -1;

  //Date
  protected String month = "";
  protected String day = "";
  protected String year = "";

  //Time
  protected String time = "00:00";

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
  private ArrayList relatedLinks = null;


  //Telephone Number

  //Email Address
  //URL

  /**
   * Constructor for the CalendarEvent object
   */
  public CalendarEvent() {
  }


  /**
   * Sets the date attribute of the CalendarEvent object
   *
   * @param eventDate The new date value
   */
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
   * Sets the Month attribute of the CalendarEvent object
   *
   * @param tmp The new Month value
   */
  public void setMonth(String tmp) {
    month = tmp;
  }


  /**
   * Sets the Icon attribute of the CalendarEvent object
   *
   * @param icon The new Icon value
   */
  public void setIcon(String icon) {
    this.icon = icon;
  }


  /**
   * Sets the idsub attribute of the CalendarEvent object
   *
   * @param idsub The new idsub value
   */
  public void setIdsub(int idsub) {
    this.idsub = idsub;
  }


  /**
   * Sets the Day attribute of the CalendarEvent object
   *
   * @param tmp The new Day value
   */
  public void setDay(String tmp) {
    day = tmp;
  }


  /**
   * Sets the Year attribute of the CalendarEvent object
   *
   * @param tmp The new Year value
   */
  public void setYear(String tmp) {
    year = tmp;
  }


  /**
   * Sets the Time attribute of the CalendarEvent object
   *
   * @param tmp The new Time value
   */
  public void setTime(String tmp) {
    if (time != null && !time.equals("")) {
      time = tmp;
    } else {
      time = "00:00";
    }
  }


  /**
   * Sets the id attribute of the CalendarEvent object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the link attribute of the CalendarEvent object
   *
   * @param tmp The new link value
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   * Sets the Subject attribute of the CalendarEvent object
   *
   * @param tmp The new Subject value
   */
  public void setSubject(String tmp) {
    subject = tmp;
  }


  /**
   * Sets the Category attribute of the CalendarEvent object
   *
   * @param tmp The new Category value
   */
  public void setCategory(String tmp) {
    category = tmp;
  }


  /**
   * Sets the HTMLClass attribute of the CalendarEvent object
   *
   * @param tmp The new HTMLClass value
   */
  public void setHTMLClass(String tmp) {
    HTMLClass = tmp;
  }


  /**
   * Sets the status attribute of the CalendarEvent object
   *
   * @param status The new status value
   */
  public void setStatus(int status) {
    this.status = status;
  }


  /**
   * Sets the relatedLinks attribute of the CalendarEvent object
   *
   * @param relatedLinks The new relatedLinks value
   */
  public void setRelatedLinks(ArrayList relatedLinks) {
    this.relatedLinks = relatedLinks;
  }


  /**
   * Adds a feature to the RelatedLink attribute of the CalendarEvent object
   *
   * @param link The feature to be added to the RelatedLink attribute
   */
  public void addRelatedLink(String link) {
    if (relatedLinks == null) {
      relatedLinks = new ArrayList();
    }
    relatedLinks.add(link);
  }


  /**
   * Gets the relatedLinks attribute of the CalendarEvent object
   *
   * @return The relatedLinks value
   */
  public ArrayList getRelatedLinks() {
    return relatedLinks;
  }


  /**
   * Gets the status attribute of the CalendarEvent object
   *
   * @return The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   * Gets the idsub attribute of the CalendarEvent object
   *
   * @return The idsub value
   */
  public int getIdsub() {
    return idsub;
  }


  /**
   * Gets the id attribute of the CalendarEvent object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the Subject attribute of the CalendarEvent object
   *
   * @return The Subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the Category attribute of the CalendarEvent object
   *
   * @return The Category value
   */
  public String getCategory() {
    return category;
  }


  /**
   * Gets the icon attribute of the CalendarEvent class
   *
   * @param thisCategory Description of the Parameter
   * @param systemStatus Description of the Parameter
   * @return The icon value
   */
  public static String getIcon(String thisCategory, SystemStatus systemStatus) {
    if (thisCategory.equals("event")) {
      return "<img border=\"0\" src=\"images/event-timed.gif\" align=\"texttop\" width=\"12\" height=\"12\" title=\"" + getLabel(
          "calendar.Event", systemStatus, "Event") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("holiday")) {
      return "<img border=\"0\" src=\"images/event-star12.gif\" align=\"texttop\" width=\"12\" height=\"12\" title=\"" + getLabel(
          "calendar.Holiday", systemStatus, "Holiday") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("System Alerts")) {
      return "<img border=\"0\" src=\"images/box-hold.gif\" align=\"texttop\" width=\"16\" height=\"15\" title=\"" + getLabel(
          "calendar.UserAccountExpires", systemStatus, "User Account Expires") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Opportunity") || thisCategory.equalsIgnoreCase(
        "Opportunities")) {
      return "<img border=\"0\" src=\"images/alertopp.gif\" align=\"texttop\" title=\"" + getLabel(
          "calendar.Opportunities", systemStatus, "Opportunities") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Activities") || thisCategory.equalsIgnoreCase(
        "Contact Activities") || thisCategory.equalsIgnoreCase(
            "Opportunity Activities")) {
      return "<img border=\"0\" src=\"images/alertcall.gif\" align=\"texttop\" title=\"" + getLabel(
          "calendar.Activities", systemStatus, "Activities") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Pending Activities")) {
      return "<img border=\"0\" src=\"images/box-hold.gif\" align=\"texttop\" title=\"" + getLabel(
          "calendar.PendingActivities", systemStatus, "Pending Activities") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Assignments")) {
      return "<img border=\"0\" src=\"images/alertassignment.gif\" align=\"texttop\" title=\"" + getLabel(
          "calendar.Assignments", systemStatus, "Assignments") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Account Alerts") || thisCategory.equalsIgnoreCase(
        "Account Contract Alerts")) {
      return "<img border=\"0\" src=\"images/accounts.gif\" width=\"14\" height=\"14\" align=\"texttop\" title=\"" + getLabel(
          "calendar.Accounts", systemStatus, "Accounts") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Tasks")) {
      return "<img src=\"images/box.gif\" border=\"0\" align=\"texttop\" width=\"14\" height=\"14\" title=\"" + getLabel(
          "calendar.Tasks", systemStatus, "Tasks") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Ticket Requests")) {
      return "<img src=\"images/tree0.gif\" border=\"0\" align=\"texttop\" title=\"" + getLabel(
          "calendar.Tickets", systemStatus, "Tickets") + "\" />";
    } else if (thisCategory.equalsIgnoreCase("Project Tickets")) {
      return "<img src=\"images/tree1.gif\" border=\"0\" align=\"texttop\" title=\"" + getLabel(
          "calendar.projectTickets", systemStatus, "Project Tickets") + "\" />";
    }
    return "";
  }


  /**
   * Gets the DateTimeString attribute of the CalendarEvent object
   *
   * @return The DateTimeString value
   */
  public String getDateTimeString() {
    return (year + "-" + month + "-" + day + " " + time);
  }


  /**
   * Gets the dateString attribute of the CalendarEvent object
   *
   * @return The dateString value
   */
  public String getDateString() {
    return (month + "/" + day + "/" + year);
  }


  /**
   * Gets the holiday attribute of the CalendarEvent object
   *
   * @return The holiday value
   */
  public boolean isHoliday() {
    return ("holiday").equalsIgnoreCase(this.category);
  }


  /**
   * Gets the label attribute of the CalendarEvent object
   *
   * @param label        Description of the Parameter
   * @param systemStatus Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The label value
   */
  public static String getLabel(String label, SystemStatus systemStatus, String defaultValue) {
    if (systemStatus != null) {
      String value = systemStatus.getLabel(label);
      if (value != null) {
        return StringUtils.toHtml(value);
      }
    }
    return StringUtils.toHtml(defaultValue);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public String toString() {
    return month + "/" + day + "/" + year + ": " + category;
  }


  /**
   * Description of the Method
   *
   * @param object Description of Parameter
   * @return Description of the Returned Value
   */
  public int compareTo(Object object) {
    return (eventComparator.compare(this, object));
  }


  /**
   * Description of the Method
   *
   * @param object Description of Parameter
   * @return Description of the Returned Value
   */
  public int compareDateTo(Object object) {
    return (eventComparator.compare(this, object));
  }


  /**
   * Description of the Class
   *
   * @author mrajkowski
   * @version $Id: CalendarEvent.java,v 1.1.1.1 2002/01/14 19:49:24 mrajkowski
   *          Exp $
   * @created July 27, 2001
   */
  class comparatorEvent implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of Parameter
     * @param right Description of Parameter
     * @return Description of the Returned Value
     */
    public int compare(Object left, Object right) {
      return (((CalendarEvent) left).getDateTimeString().compareTo(
          ((CalendarEvent) right).getDateTimeString()));
    }

  }
}

