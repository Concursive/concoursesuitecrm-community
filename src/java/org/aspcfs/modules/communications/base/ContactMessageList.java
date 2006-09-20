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
package org.aspcfs.modules.communications.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created January 1, 2006
 */
public class ContactMessageList extends ArrayList {
  private PagedListInfo pagedListInfo = null;
  private int receivedBy = -1;
  private int receivedFrom = -1;
  private String receivedByRange = null;
  //resources
  private boolean buildMessage = false;


  /**
   * Gets the buildMessage attribute of the ContactMessageList object
   *
   * @return The buildMessage value
   */
  public boolean getBuildMessage() {
    return buildMessage;
  }


  /**
   * Sets the buildMessage attribute of the ContactMessageList object
   *
   * @param tmp The new buildMessage value
   */
  public void setBuildMessage(boolean tmp) {
    this.buildMessage = tmp;
  }


  /**
   * Sets the buildMessage attribute of the ContactMessageList object
   *
   * @param tmp The new buildMessage value
   */
  public void setBuildMessage(String tmp) {
    this.buildMessage = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the ContactMessageList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the ContactMessageList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the receivedBy attribute of the ContactMessageList object
   *
   * @return The receivedBy value
   */
  public int getReceivedBy() {
    return receivedBy;
  }


  /**
   * Sets the receivedBy attribute of the ContactMessageList object
   *
   * @param tmp The new receivedBy value
   */
  public void setReceivedBy(int tmp) {
    this.receivedBy = tmp;
  }


  /**
   * Sets the receivedBy attribute of the ContactMessageList object
   *
   * @param tmp The new receivedBy value
   */
  public void setReceivedBy(String tmp) {
    this.receivedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the receivedFrom attribute of the ContactMessageList object
   *
   * @return The receivedFrom value
   */
  public int getReceivedFrom() {
    return receivedFrom;
  }


  /**
   * Sets the receivedFrom attribute of the ContactMessageList object
   *
   * @param tmp The new receivedFrom value
   */
  public void setReceivedFrom(int tmp) {
    this.receivedFrom = tmp;
  }


  /**
   * Sets the receivedFrom attribute of the ContactMessageList object
   *
   * @param tmp The new receivedFrom value
   */
  public void setReceivedFrom(String tmp) {
    this.receivedFrom = Integer.parseInt(tmp);
  }


  /**
   * Gets the receivedByRange attribute of the ContactMessageList object
   *
   * @return The receivedByRange value
   */
  public String getReceivedByRange() {
    return receivedByRange;
  }


  /**
   * Sets the receivedByRange attribute of the ContactMessageList object
   *
   * @param tmp The new receivedByRange value
   */
  public void setReceivedByRange(String tmp) {
    this.receivedByRange = tmp;
  }


  /**
   * Constructor for the ContactMessageList object
   */
  public ContactMessageList() { }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM contact_message cm " +
            "LEFT JOIN " + DatabaseUtils.addQuotes(db, "message")+ " m ON (cm.message_id = m.id) " +
            "LEFT JOIN contact ct_rf ON (cm.received_from = ct_rf.contact_id) " +
            "LEFT JOIN contact ct_rb ON (cm.received_by = ct_rb.user_id) " +
            "WHERE cm.id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
              sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(name) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY name ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "cm.* " +
            "FROM contact_message cm " +
            "LEFT JOIN " + DatabaseUtils.addQuotes(db, "message")+ " m ON (cm.message_id = m.id) " +
            "LEFT JOIN contact ct_rf ON (cm.received_from = ct_rf.contact_id) " +
            "LEFT JOIN contact ct_rb ON (cm.received_by = ct_rb.user_id) " +
            "WHERE cm.id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ContactMessage thisMessage = new ContactMessage(rs);
      this.add(thisMessage);
    }
    rs.close();
    pst.close();

    if (buildMessage) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ContactMessage thisMessage = (ContactMessage) i.next();
        thisMessage.buildMessage(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (receivedBy != -1) {
      sqlFilter.append("AND cm.received_by = ? ");
    }

    if (receivedFrom != -1) {
      sqlFilter.append("AND cm.received_from = ? ");
    }

    if (receivedByRange != null) {
      sqlFilter.append("AND cm.received_by IN (" + receivedByRange + ") ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (receivedBy != -1) {
      pst.setInt(++i, receivedBy);
    }

    if (receivedFrom != -1) {
      pst.setInt(++i, receivedFrom);
    }

    return i;
  }

}

