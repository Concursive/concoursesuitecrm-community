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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.modules.troubletickets.base.*;
/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    February 2, 2004
 *@version    $Id: TicketPerDayDescription.java,v 1.1.2.5 2004/02/26 18:54:42
 *      kbhoopal Exp $
 */
public class TicketPerDayDescription extends GenericBean {

  private int id = -1;
  private int linkFormId = -1;
  private java.sql.Timestamp activityDate = null;
  private String descriptionOfService = null;
  private int travelHours = -1;
  private int travelMinutes = -1;
  private int laborHours = -1;
  private int laborMinutes = -1;
  private String activityDateTimeZone = null;


  /**
   *  Constructor for the TicketPerDayDescription object
   */
  public TicketPerDayDescription() { }


  /**
   *  Constructor for the TicketReplacementPart object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TicketPerDayDescription(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkFormId attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new linkFormId value
   */
  public void setLinkFormId(int tmp) {
    this.linkFormId = tmp;
  }


  /**
   *  Sets the linkFormId attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new linkFormId value
   */
  public void setLinkFormId(String tmp) {
    this.linkFormId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the activityDate attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new activityDate value
   */
  public void setActivityDate(java.sql.Timestamp tmp) {
    this.activityDate = tmp;
  }


  /**
   *  Sets the activityDate attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new activityDate value
   */
  public void setActivityDate(String tmp) {
    activityDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the descriptionOfService attribute of the TicketPerDayDescription
   *  object
   *
   *@param  tmp  The new descriptionOfService value
   */
  public void setDescriptionOfService(String tmp) {
    this.descriptionOfService = tmp;
  }


  /**
   *  Sets the travelHours attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new travelHours value
   */
  public void setTravelHours(int tmp) {
    this.travelHours = tmp;
  }


  /**
   *  Sets the travelHours attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new travelHours value
   */
  public void setTravelHours(String tmp) {
    this.travelHours = Integer.parseInt(tmp);
  }


  /**
   *  Sets the travelMinutes attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new travelMinutes value
   */
  public void setTravelMinutes(int tmp) {
    this.travelMinutes = tmp;
  }


  /**
   *  Sets the travelMinutes attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new travelMinutes value
   */
  public void setTravelMinutes(String tmp) {
    this.travelMinutes = Integer.parseInt(tmp);
  }


  /**
   *  Sets the laborHours attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new laborHours value
   */
  public void setLaborHours(int tmp) {
    this.laborHours = tmp;
  }


  /**
   *  Sets the laborHours attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new laborHours value
   */
  public void setLaborHours(String tmp) {
    this.laborHours = Integer.parseInt(tmp);
  }


  /**
   *  Sets the laborMinutes attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new laborMinutes value
   */
  public void setLaborMinutes(int tmp) {
    this.laborMinutes = tmp;
  }


  /**
   *  Sets the laborMinutes attribute of the TicketPerDayDescription object
   *
   *@param  tmp  The new laborMinutes value
   */
  public void setLaborMinutes(String tmp) {
    this.laborMinutes = Integer.parseInt(tmp);
  }


  /**
   *  Sets the activityDateTimeZone attribute of the TicketPerDayDescription
   *  object
   *
   *@param  tmp  The new activityDateTimeZone value
   */
  public void setActivityDateTimeZone(String tmp) {
    this.activityDateTimeZone = tmp;
  }


  /**
   *  Gets the activityDateTimeZone attribute of the TicketPerDayDescription
   *  object
   *
   *@return    The activityDateTimeZone value
   */
  public String getActivityDateTimeZone() {
    return activityDateTimeZone;
  }


  /**
   *  Gets the id attribute of the TicketPerDayDescription object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the activityDate attribute of the TicketPerDayDescription object
   *
   *@return    The activityDate value
   */
  public java.sql.Timestamp getActivityDate() {
    return activityDate;
  }


  /**
   *  Gets the descriptionOfService attribute of the TicketPerDayDescription
   *  object
   *
   *@return    The descriptionOfService value
   */
  public String getDescriptionOfService() {
    return descriptionOfService;
  }


  /**
   *  Gets the linkFormId attribute of the TicketPerDayDescription object
   *
   *@return    The linkFormId value
   */
  public int getLinkFormId() {
    return linkFormId;
  }


  /**
   *  Gets the travelHours attribute of the TicketPerDayDescription object
   *
   *@return    The travelHours value
   */
  public int getTravelHours() {
    return travelHours;
  }


  /**
   *  Gets the travelMinutes attribute of the TicketPerDayDescription object
   *
   *@return    The travelMinutes value
   */
  public int getTravelMinutes() {
    return travelMinutes;
  }


  /**
   *  Gets the laborHours attribute of the TicketPerDayDescription object
   *
   *@return    The laborHours value
   */
  public int getLaborHours() {
    return laborHours;
  }


  /**
   *  Gets the laborMinutes attribute of the TicketPerDayDescription object
   *
   *@return    The laborMinutes value
   */
  public int getLaborMinutes() {
    return laborMinutes;
  }


  /**
   *  Gets the properties that are TimeZone sensitive
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("activityDate");
    return thisList;
  }


  /**
   *  inserts the work item for the data into the database
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    PreparedStatement pst = null;

    pst = db.prepareStatement(
        "INSERT INTO  ticket_activity_item " +
        "(link_form_id, " +
        "activity_date, " +
        "activity_date_timezone, " +
        "description, " +
        "travel_hours, " +
        "travel_minutes, " +
        "labor_hours, " +
        "labor_minutes) " +
        "VALUES (?,?,?,?,?,?,?,?)");

    int i = 0;
    pst.setInt(++i, linkFormId);
    pst.setTimestamp(++i, activityDate);
    pst.setString(++i, this.activityDateTimeZone);
    pst.setString(++i, descriptionOfService);
    pst.setInt(++i, travelHours);
    pst.setInt(++i, travelMinutes);
    pst.setInt(++i, laborHours);
    pst.setInt(++i, laborMinutes);

    pst.execute();
    pst.close();

  }


  /**
   *  Deletes the work item for the day from the database
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "DELETE FROM  ticket_activity_item " +
        "WHERE item_id = ? ");
    int i = 0;
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  request                       Description of the Parameter
   *@param  parseItem                     Description of the Parameter
   *@exception  java.text.ParseException  Description of the Exception
   */
  public void buildRecord(HttpServletRequest request, int parseItem) throws java.text.ParseException {
    //this.setActivityDate(request.getParameter("activityDate" + parseItem));
    this.setDescriptionOfService(request.getParameter("descriptionOfService" + parseItem));
    this.setTravelHours(request.getParameter("travelHours" + parseItem));
    this.setTravelMinutes(request.getParameter("travelMinutes" + parseItem));
    this.setLaborHours(request.getParameter("laborHours" + parseItem));
    this.setLaborMinutes(request.getParameter("laborMinutes" + parseItem));
    this.setActivityDateTimeZone(request.getParameter("activityDate" + parseItem + "TimeZone"));
    sanitizeDate(request, request.getParameter("activityDate" + parseItem), "ActivityDate");
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("item_id");
    linkFormId = rs.getInt("link_form_id");
    activityDate = rs.getTimestamp("activity_date");
    descriptionOfService = rs.getString("description");
    travelHours = rs.getInt("travel_hours");
    travelMinutes = rs.getInt("travel_minutes");
    laborHours = rs.getInt("labor_hours");
    laborMinutes = rs.getInt("labor_minutes");
    activityDateTimeZone = rs.getString("activity_date_timezone");
  }
}

