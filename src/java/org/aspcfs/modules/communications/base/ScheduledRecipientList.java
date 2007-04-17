/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id$
 * @created Mar 28, 2007
 *
 */
public class ScheduledRecipientList extends ArrayList implements SyncableList {

  private final static String tableName = "scheduled_recipient";
  private final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  
  /**
   *  Constructor for the ScheduledRecipientList object
   */
  public ScheduledRecipientList() { }


  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static ScheduledRecipient getObject(ResultSet rs) throws SQLException {
    ScheduledRecipient scheduledRecipient = new ScheduledRecipient(rs);
    return scheduledRecipient;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }

  /**
   * Description of the Method
   *
   * @param db
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();

    // Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT sr.*" +
        " FROM " + DatabaseUtils.getTableName(db, tableName) + " sr" +
        " WHERE sr.id > -1 ");

    createFilter(db, sqlFilter);
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    return pst;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = this.prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    while (rs.next()) {
      this.add(getObject(rs));
    }
    rs.close();
    if(pst != null){
      pst.close();
    }
  }

   /**
   *  Description of the Method
   *
   *@param  db         Description of the Parameter
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND sr.entered > ? ");
      }
      sqlFilter.append("AND sr.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND sr.modified > ? ");
      sqlFilter.append("AND sr.entered < ? ");
      sqlFilter.append("AND sr.modified < ? ");
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
}
