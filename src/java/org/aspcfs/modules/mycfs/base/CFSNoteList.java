//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package org.aspcfs.modules.mycfs.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    February 21, 2002
 *@version    $Id$
 */
public class CFSNoteList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private int sentTo = -1;
  private int sentFrom = -1;
  private boolean oldMessagesOnly = false;
  private boolean sentMessagesOnly = false;
  private boolean newMessagesOnly = false;


  /**
   *  Constructor for the CFSNoteList object
   *
   *@since
   */
  public CFSNoteList() { }


  /**
   *  Sets the newMessagesOnly attribute of the CFSNoteList object
   *
   *@param  newMessagesOnly  The new newMessagesOnly value
   */
  public void setNewMessagesOnly(boolean newMessagesOnly) {
    this.newMessagesOnly = newMessagesOnly;
  }


  /**
   *  Sets the PagedListInfo attribute of the CFSNoteList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the SentTo attribute of the CFSNoteList object
   *
   *@param  sentTo  The new SentTo value
   *@since
   */
  public void setSentTo(int sentTo) {
    this.sentTo = sentTo;
  }



  /**
   *  Sets the SentFrom attribute of the CFSNoteList object
   *
   *@param  sentFrom  The new SentFrmm value
   *@since
   */
  public void setSentFrom(int sentFrom) {
    this.sentFrom = sentFrom;
  }


  /**
   *  Sets the OldMessagesOnly attribute of the CFSNoteList object
   *
   *@param  oldMessagesOnly  The new OldMessagesOnly value
   *@since
   */
  public void setOldMessagesOnly(boolean oldMessagesOnly) {
    this.oldMessagesOnly = oldMessagesOnly;
  }


  /**
   *  Sets the sentMessagesOnly attribute of the CFSNoteList object
   *
   *@param  sentMessagesOnly  The sent Messages Only value
   *@since
   */
  public void setSentMessagesOnly(boolean sentMessagesOnly) {
    this.sentMessagesOnly = sentMessagesOnly;
  }



  /**
   *  Gets the PagedListInfo attribute of the CFSNoteList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the SentTo attribute of the CFSNoteList object
   *
   *@return    The SentTo value
   *@since
   */
  public int getSentTo() {
    return sentTo;
  }


  /**
   *  Gets the SentFrom attribute of the CFSNoteList object
   *
   *@return    The SentFrom value
   *@since
   */
  public int getSentFrom() {
    return sentFrom;
  }

  /**
   *  Gets the OldMessagesOnly attribute of the CFSNoteList object
   *
   *@return    The OldMessagesOnly value
   *@since
   */
  public boolean getOldMessagesOnly() {
    return oldMessagesOnly;
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
          "WHERE m.id > -1 AND m.delete_flag=" + DatabaseUtils.getFalse(db) + " ");
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
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      pst.close();
      rs.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
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
      sqlSelect.append("SELECT DISTINCT ");
    }

    if (sentMessagesOnly) {
      sqlSelect.append(
          "m.id,m.subject,m.body,m.sent,m.delete_flag " +
          "FROM cfsinbox_message m " +
          "WHERE m.id > -1 AND m.delete_flag=" + DatabaseUtils.getFalse(db) + " ");
    } else {
      sqlSelect.append(
          "m.*, ml.*, ct_sent.namefirst as sent_namefirst, ct_sent.namelast as sent_namelast " +
          "FROM cfsinbox_messagelink ml,cfsinbox_message m " +
          "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
          "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
          "LEFT JOIN contact ct_sent ON (m.enteredby = ct_sent.user_id) " +
          "WHERE m.id > -1 AND (m.id = ml.id) ");
    }

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;

      CFSNote thisNote = new CFSNote();
      if (pagedListInfo != null) {
        thisNote.setCurrentView(pagedListInfo.getListView());
      }
      thisNote.buildRecord(rs);
      thisNote.buildRecipientList(db);
      this.addElement(thisNote);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   *@since
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
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
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (sentTo > -1 && !sentMessagesOnly) {
      pst.setInt(++i, sentTo);
    } else {
      pst.setInt(++i, sentFrom);
    }

    return i;
  }
}

