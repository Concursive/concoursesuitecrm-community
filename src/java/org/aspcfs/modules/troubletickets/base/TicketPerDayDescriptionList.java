//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.utils.web.*;
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

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int formId = -1;
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
  public TicketPerDayDescriptionList(HttpServletRequest request, HashMap errors) {
    int i = 1;
    totalTravelHours = 0;
    totalTravelMinutes = 0;
    totalLaborHours = 0;
    totalLaborMinutes = 0;
    while (request.getParameter("activityDate" + i) != null) {
        if (!(request.getParameter("activityDate" + i).trim().equals(""))) {
          TicketPerDayDescription thisPerDayDescription = new TicketPerDayDescription();
          try{
            thisPerDayDescription.buildRecord(request, i);
            this.totalTravelHours = totalTravelHours + thisPerDayDescription.getTravelHours();
            this.totalTravelMinutes = totalTravelMinutes + thisPerDayDescription.getTravelMinutes();
            this.totalLaborHours = totalLaborHours + thisPerDayDescription.getLaborHours();
            this.totalLaborMinutes = totalLaborMinutes + thisPerDayDescription.getLaborMinutes();
          }catch(Exception e){
            errors.put("activityDate" + i + "Error","invalid date");
          }
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
   *  Sets the pagedListInfo attribute of the TicketPerDayDescriptionList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the TicketPerDayDescriptionList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketPerDayDescriptionList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the formId attribute of the TicketPerDayDescriptionList object
   *
   *@param  tmp  The new formId value
   */
  public void setFormId(int tmp) {
    this.formId = tmp;
  }


  /**
   *  Sets the formId attribute of the TicketPerDayDescriptionList object
   *
   *@param  tmp  The new formId value
   */
  public void setFormId(String tmp) {
    this.formId = Integer.parseInt(tmp);
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
   *  Gets the pagedListInfo attribute of the TicketPerDayDescriptionList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the TicketPerDayDescriptionList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the formId attribute of the TicketPerDayDescriptionList object
   *
   *@return    The formId value
   */
  public int getFormId() {
    return formId;
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
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      TicketPerDayDescription thisDescription = this.getObject(rs);
      this.add(thisDescription);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  fetches a list of activity items
   *
   *@param  db                Description of the Parameter
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM ticket_activity_item " +
        "WHERE item_id > -1 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine column to sort by
      pagedListInfo.setDefaultSort("activity_date", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY activity_date ");
    }
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " * " +
        "FROM ticket_activity_item " +
        "WHERE item_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter         Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id > -1) {
      sqlFilter.append("AND item_id = ? ");
    }

    if (formId > -1) {
      sqlFilter.append("AND link_form_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (formId > -1) {
      pst.setInt(++i, formId);
    }
    return i;
  }


  /**
   *  Deletes the activities from the database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteList(Connection db) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      TicketPerDayDescription thisDescription = (TicketPerDayDescription) itr.next();
      thisDescription.delete(db);
    }
    return true;
  }


  /**
   *  Gets the object attribute of the TicketPerDayDescriptionList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public TicketPerDayDescription getObject(ResultSet rs) throws SQLException {
    TicketPerDayDescription thisDescription = new TicketPerDayDescription(rs);
    return thisDescription;
  }

}

