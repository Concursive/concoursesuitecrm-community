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

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author chris
 * @version $Id$
 * @created February 21, 2002
 */
public class CFSNoteList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private int sentTo = -1;
  private int sentFrom = -1;
  private boolean oldMessagesOnly = false;
  private boolean sentMessagesOnly = false;
  private boolean newMessagesOnly = false;
  private boolean buildAll = false;


  /**
   * Constructor for the CFSNoteList object
   */
  public CFSNoteList() {
  }


  /**
   * Sets the newMessagesOnly attribute of the CFSNoteList object
   *
   * @param newMessagesOnly The new newMessagesOnly value
   */
  public void setNewMessagesOnly(boolean newMessagesOnly) {
    this.newMessagesOnly = newMessagesOnly;
  }


  /**
   * Sets the PagedListInfo attribute of the CFSNoteList object
   *
   * @param tmp The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the SentTo attribute of the CFSNoteList object
   *
   * @param sentTo The new SentTo value
   */
  public void setSentTo(int sentTo) {
    this.sentTo = sentTo;
  }


  /**
   * Sets the SentFrom attribute of the CFSNoteList object
   *
   * @param sentFrom The new SentFrmm value
   */
  public void setSentFrom(int sentFrom) {
    this.sentFrom = sentFrom;
  }


  /**
   * Sets the OldMessagesOnly attribute of the CFSNoteList object
   *
   * @param oldMessagesOnly The new OldMessagesOnly value
   */
  public void setOldMessagesOnly(boolean oldMessagesOnly) {
    this.oldMessagesOnly = oldMessagesOnly;
  }


  /**
   * Sets the sentMessagesOnly attribute of the CFSNoteList object
   *
   * @param sentMessagesOnly The sent Messages Only value
   */
  public void setSentMessagesOnly(boolean sentMessagesOnly) {
    this.sentMessagesOnly = sentMessagesOnly;
  }


  /**
   * Gets the PagedListInfo attribute of the CFSNoteList object
   *
   * @return The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the SentTo attribute of the CFSNoteList object
   *
   * @return The SentTo value
   */
  public int getSentTo() {
    return sentTo;
  }


  /**
   * Gets the SentFrom attribute of the CFSNoteList object
   *
   * @return The SentFrom value
   */
  public int getSentFrom() {
    return sentFrom;
  }


  /**
   * Gets the OldMessagesOnly attribute of the CFSNoteList object
   *
   * @return The OldMessagesOnly value
   */
  public boolean getOldMessagesOnly() {
    return oldMessagesOnly;
  }

  public boolean getBuildAll() {
    return buildAll;
  }

  public void setBuildAll(boolean tmp) {
    this.buildAll = tmp;
  }

  public void setBuildAll(String tmp) {
    this.buildAll = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    if (sentMessagesOnly) {
      sqlCount.append(
          "SELECT COUNT(*) AS recordcount " +
          "FROM cfsinbox_message m " +
          "WHERE m.id > -1 ");
    } else {
      sqlCount.append(
          "SELECT COUNT(*) AS recordcount " +
          "FROM cfsinbox_messagelink ml,cfsinbox_message m " +
          "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
          "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
          "LEFT JOIN contact ct_sent ON (m.enteredby = ct_sent.user_id) " +
          "WHERE m.id > -1 AND (m.id = ml.id) ");
    }

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
            "AND subject < ? ");
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
      pagedListInfo.setDefaultSort("m.entered", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY m.entered desc ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }

    if (sentMessagesOnly) {
      sqlSelect.append(
          "m.id, m.subject, m.body, m.sent, m.delete_flag " +
          "FROM cfsinbox_message m " +
          "WHERE m.id > -1 ");
    } else {
      sqlSelect.append(
          "m.*, ml.*, ct_sent.namefirst AS sent_namefirst, ct_sent.namelast AS sent_namelast " +
          "FROM cfsinbox_messagelink ml, cfsinbox_message m " +
          "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
          "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
          "LEFT JOIN contact ct_sent ON (m.enteredby = ct_sent.user_id) " +
          "WHERE m.id > -1 AND (m.id = ml.id) ");
    }

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      CFSNote thisNote = new CFSNote();
      if (pagedListInfo != null) {
        thisNote.setCurrentView(pagedListInfo.getListView());
      }
      thisNote.buildRecord(rs);
      this.addElement(thisNote);
    }
    rs.close();
    pst.close();
    // Build additional data
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CFSNote thisNote = (CFSNote) i.next();
      thisNote.buildRecipientList(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (sentMessagesOnly) {
      sqlFilter.append("AND m.delete_flag = ? ");
    }
    if (buildAll) {
      return;
    }

    if (sentTo > -1 && !sentMessagesOnly) {
      sqlFilter.append("AND ml.sent_to = ? ");
    } else {
      sqlFilter.append("AND m.enteredby = ? ");
    }

    if (oldMessagesOnly == true) {
      sqlFilter.append("AND ml.status = 2 ");
    } else if (newMessagesOnly) {
      sqlFilter.append("AND ml.status IN (0) ");
    } else if (!sentMessagesOnly) {
      sqlFilter.append("AND ml.status IN (0,1) ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (sentMessagesOnly) {
      pst.setBoolean(++i, false);
    }
    if (buildAll) {
      return i;
    }

    if (sentTo > -1 && !sentMessagesOnly) {
      pst.setInt(++i, sentTo);
    } else {
      pst.setInt(++i, sentFrom);
    }

    return i;
  }
}

