//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    February 11, 2004
 *@version    $Id: TicketPerDayDescriptionList.java,v 1.1.2.3 2004/02/11
 *      19:26:19 kbhoopal Exp $
 */
public class TicketPerDayDescriptionList extends ArrayList {

  private int totalTravelHours = -1;
  private int totalTravelMinutes = -1;
  private int totalLaborHours = -1;
  private int totalLaborMinutes = -1;


  /**
   *  Constructor for the TicketPerDayDescriptionList object
   */
  public TicketPerDayDescriptionList() { }


  /**
   *  Constructor for the TicketPerDayDescriptionList object
   *
   *@param  request  Description of the Parameter
   */
  public TicketPerDayDescriptionList(HttpServletRequest request) {
    int i = 1;
    totalTravelHours = 0;
    totalTravelMinutes = 0;
    totalLaborHours = 0;
    totalLaborMinutes = 0;
    while (request.getParameter("activityDate" + i) != null) {
      if (!(request.getParameter("activityDate" + i).trim().equals(""))) {
        TicketPerDayDescription thisPerDayDescription = new TicketPerDayDescription();
        thisPerDayDescription.buildRecord(request, i);
        this.totalTravelHours = totalTravelHours + thisPerDayDescription.getTravelHours();
        this.totalTravelMinutes = totalTravelMinutes + thisPerDayDescription.getTravelMinutes();
        this.totalLaborHours = totalLaborHours + thisPerDayDescription.getLaborHours();
        this.totalLaborMinutes = totalLaborMinutes + thisPerDayDescription.getLaborMinutes();
        this.add(thisPerDayDescription);
      }
      i++;
    }
    totalTravelHours = totalTravelHours + totalTravelMinutes / 60;
    totalTravelMinutes = totalTravelMinutes % 60;
    totalLaborHours = totalLaborHours + totalLaborMinutes / 60;
    totalLaborMinutes = totalLaborMinutes % 60;
  }


  /**
   *  Sets the totalTravelHours attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalTravelHours value
   */
  public void setTotalTravelHours(int tmp) {
    this.totalTravelHours = tmp;
  }


  /**
   *  Sets the totalTravelHours attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalTravelHours value
   */
  public void setTotalTravelHours(String tmp) {
    this.totalTravelHours = Integer.parseInt(tmp);
  }


  /**
   *  Sets the totalTravelMinutes attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalTravelMinutes value
   */
  public void setTotalTravelMinutes(int tmp) {
    this.totalTravelMinutes = tmp;
  }


  /**
   *  Sets the totalTravelMinutes attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalTravelMinutes value
   */
  public void setTotalTravelMinutes(String tmp) {
    this.totalTravelMinutes = Integer.parseInt(tmp);
  }


  /**
   *  Sets the totalLaborHours attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalLaborHours value
   */
  public void setTotalLaborHours(int tmp) {
    this.totalLaborHours = tmp;
  }


  /**
   *  Sets the totalLaborHours attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalLaborHours value
   */
  public void setTotalLaborHours(String tmp) {
    this.totalLaborHours = Integer.parseInt(tmp);
  }


  /**
   *  Sets the totalLaborMinutes attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalLaborMinutes value
   */
  public void setTotalLaborMinutes(int tmp) {
    this.totalLaborMinutes = tmp;
  }


  /**
   *  Sets the totalLaborMinutes attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@param  tmp  The new totalLaborMinutes value
   */
  public void setTotalLaborMinutes(String tmp) {
    this.totalLaborMinutes = Integer.parseInt(tmp);
  }


  /**
   *  Gets the totalTravelHours attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@return    The totalTravelHours value
   */
  public int getTotalTravelHours() {
    return totalTravelHours;
  }


  /**
   *  Gets the totalTravelMinutes attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@return    The totalTravelMinutes value
   */
  public int getTotalTravelMinutes() {
    return totalTravelMinutes;
  }


  /**
   *  Gets the totalLaborHours attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@return    The totalLaborHours value
   */
  public int getTotalLaborHours() {
    return totalLaborHours;
  }


  /**
   *  Gets the totalLaborMinutes attribute of the TicketPerDayDescriptionList
   *  object
   *
   *@return    The totalLaborMinutes value
   */
  public int getTotalLaborMinutes() {
    return totalLaborMinutes;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpFormId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryList(Connection db, int tmpFormId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    pst = db.prepareStatement(" SELECT *  " +
        " FROM  ticket_activity_item " +
        " WHERE link_form_id = ? ");

    pst.setInt(1, tmpFormId);
    rs = pst.executeQuery();

    while (rs.next()) {
      TicketPerDayDescription thisPerDayDescription = new TicketPerDayDescription(rs);
      this.add(thisPerDayDescription);
    }

    rs.close();
    pst.close();

  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpFormId         Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteList(Connection db, int tmpFormId) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement(" DELETE " +
        " FROM  ticket_activity_item " +
        " WHERE link_form_id = ? ");

    pst.setInt(1, tmpFormId);
    pst.execute();
    pst.close();

    return true;
  }
}

