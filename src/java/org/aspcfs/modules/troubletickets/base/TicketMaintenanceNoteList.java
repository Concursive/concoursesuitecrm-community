/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.troubletickets.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A collection of Ticket objects, can also be used for querying and filtering
 * the tickets that are included in the list.
 *
 * @author chris
 * @version $Id: TicketSunMaintenanceList.java,v 1.1.2.2 2004/01/30 23:54:47
 *          kbhoopal Exp $
 * @created December 5, 2001
 */
public class TicketMaintenanceNoteList extends ArrayList {
  public final static String tableName = "ticket_sun_form";
  public final static String uniqueField = "form_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int ticketId = -1;


  /**
   * Constructor for the TicketList object
   */
  public TicketMaintenanceNoteList() {
  }

  /**
   * Sets the lastAnchor attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the TicketSunMaintenanceList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the TicketSunMaintenanceList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * Sets the pagedListInfo attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the id attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the ticketId attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new ticketId value
   */
  public void setTicketId(int tmp) {
    this.ticketId = tmp;
  }


  /**
   * Sets the ticketId attribute of the TicketSunMaintenanceList object
   *
   * @param tmp The new ticketId value
   */
  public void setTicketId(String tmp) {
    this.ticketId = Integer.parseInt(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the TicketSunMaintenanceList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the id attribute of the TicketSunMaintenanceList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the ticketId attribute of the TicketSunMaintenanceList object
   *
   * @return The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      TicketMaintenanceNote thisMaintenance = this.getObject(rs);
      this.add(thisMaintenance);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Description of the Method
   *
   * @param db  Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM ticket_sun_form tsf " +
            "WHERE form_id > -1 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();

      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY entered ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " tsf.*  " +
            " FROM ticket_sun_form tsf " +
            " WHERE form_id > -1 ");

    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   * Builds a base SQL where statement for filtering records to be used by
   * sqlSelect and sqlCount
   *
   * @param sqlFilter Description of Parameter
   * @param db        Description of the Parameter
   * @since 1.2
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    int i = 0;
    if (id > -1) {
      sqlFilter.append("AND tsf.id = ? ");
    }
    if (ticketId > -1) {
      sqlFilter.append("AND tsf.link_ticket_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }
  }


  /**
   * Sets the parameters for the preparedStatement - these items must
   * correspond with the createFilter statement
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.2
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (ticketId > -1) {
      pst.setInt(++i, ticketId);
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }


  /**
   * Gets the object attribute of the TicketSunMaintenanceList object
   *
   * @param rs Description of the Parameter
   * @return The object value
   * @throws SQLException Description of the Exception
   */
  public TicketMaintenanceNote getObject(ResultSet rs) throws SQLException {
    TicketMaintenanceNote thisMaintenance = new TicketMaintenanceNote(rs);
    return thisMaintenance;
  }

}

