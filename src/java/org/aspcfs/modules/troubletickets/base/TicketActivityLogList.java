//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import java.util.Calendar;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    March 18, 2004
 *@version    $Id$
 */
public class TicketActivityLogList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int ticketId = -1;

  /**
   *  Constructor for the TicketList object
   *
   *@since
   */
  public TicketActivityLogList() { }


  /**
   *  Sets the pagedListInfo attribute of the TicketCSSTMMaintenanceList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCSSTMMaintenanceList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCSSTMMaintenanceList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ticketId attribute of the TicketCSSTMMaintenanceList object
   *
   *@param  tmp  The new ticketId value
   */
  public void setTicketId(int tmp) {
    this.ticketId = tmp;
  }


  /**
   *  Sets the ticketId attribute of the TicketCSSTMMaintenanceList object
   *
   *@param  tmp  The new ticketId value
   */
  public void setTicketId(String tmp) {
    this.ticketId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the TicketCSSTMMaintenanceList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the TicketCSSTMMaintenanceList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the ticketId attribute of the TicketCSSTMMaintenanceList object
   *
   *@return    The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      TicketActivityLog thisActivity = this.getObject(rs);
      this.add(thisActivity);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
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
    StringBuffer sqlGroup = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM ticket_csstm_form tcf " +
        "WHERE form_id > -1 ");

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
      pagedListInfo.setDefaultSort("firstdate", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY firstdate ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }

    sqlSelect.append(
        "form_id, " +
        "link_ticket_id, " +
        "phone_response_time, " +
        "engineer_response_time, " +
        "follow_up_required, " +
        "follow_up_description, " +
        "alert_date,  " +
        "entered, " +
        "enteredby, " +
        "modified, " +
        "modifiedby, " +
        "enabled, " +
        "travel_towards_sc, " +
        "labor_towards_sc, " +
        "min(activity_date) as firstdate, " +
        "max(activity_date) as lastdate " +
        "FROM ticket_csstm_form " +
        "LEFT JOIN  ticket_activity_item on (link_form_id = form_id) " +
        "WHERE form_id > -1 "
        );

    sqlGroup.append("GROUP BY form_id,link_ticket_id,phone_response_time,engineer_response_time, follow_up_required, follow_up_description, alert_date,  entered, enteredby, modified,modifiedby, enabled, travel_towards_sc, labor_towards_sc ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlGroup.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@param  db         Description of the Parameter
   *@since             1.2
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    int i = 0;
    if (id > -1) {
      sqlFilter.append("AND form_id = ? ");
    }
    if (ticketId > -1) {
      sqlFilter.append("AND link_ticket_id = ? ");
    }
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.2
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }

    if (ticketId > -1) {
      pst.setInt(++i, ticketId);
    }

    return i;
  }


  /**
   *  Gets the object attribute of the TicketCSSTMMaintenanceList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public TicketActivityLog getObject(ResultSet rs) throws SQLException {
    TicketActivityLog thisActivity = new TicketActivityLog(rs);
    return thisActivity;
  }

}

