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
package org.aspcfs.modules.actionplans.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
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
 * @created August 31, 2005
 */
public class ActionStepLookupList extends ArrayList  implements SyncableList {
  private int stepId = -1;

  public final static String tableName = "action_step_lookup";
  public final static String uniqueField = "code";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;

  /**
   * Sets the lastAnchor attribute of the ActionStepLookupList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionStepLookupList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionStepLookupList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionStepLookupList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionStepLookupList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }
  
  /**
   * Sets the PagedListInfo attribute of the ActionStepLookupList object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the ActionStepLookupList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionStepLookupList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the stepId attribute of the ActionStepLookupList object
   *
   * @return The stepId value
   */
  public int getStepId() {
    return stepId;
  }


  /**
   * Sets the stepId attribute of the ActionStepLookupList object
   *
   * @param tmp The new stepId value
   */
  public void setStepId(int tmp) {
    this.stepId = tmp;
  }


  /**
   * Sets the stepId attribute of the ActionStepLookupList object
   *
   * @param tmp The new stepId value
   */
  public void setStepId(String tmp) {
    this.stepId = Integer.parseInt(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    int items = -1;
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM action_step_lookup asl " +
            "WHERE asl.code > 0 ");
    createFilter(db, sqlFilter);
    sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", asl.description ");
    items = prepareFilter(pst);
    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      ActionStepLookup thisItem = new ActionStepLookup(rs);
      this.add(thisItem);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Description of the Method
   * @param db        Description of the Parameter
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (stepId > -1) {
      sqlFilter.append("AND asl.step_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND asl.entered > ? ");
      }
      sqlFilter.append("AND asl.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND asl.modified > ? ");
      sqlFilter.append("AND asl.entered < ? ");
      sqlFilter.append("AND asl.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (stepId != -1) {
      pst.setInt(++i, stepId);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      int count = 0;
      Iterator i = this.iterator();
      while (i.hasNext()) {
        count += 10;
        ActionStepLookup stepLookup = (ActionStepLookup) i.next();
        stepLookup.setStepId(stepId);
        stepLookup.setLevel(count);
        stepLookup.insert(db);
      }
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }

  }
  
  /**
   *  Gets the object attribute of the ActionStepLookupList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ActionStepLookup getObject(ResultSet rs) throws SQLException {
  	ActionStepLookup obj = new ActionStepLookup(rs);
    return obj;
  }
  
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    //Need to build a base SQL statement for returning records
     sqlSelect.append(" SELECT ");
     sqlSelect.append(
         "asl.* " +
             "FROM action_step_lookup asl " +
             "WHERE asl.code > 0 ");
     if(sqlFilter == null || sqlFilter.length() == 0){
     	StringBuffer buff = new StringBuffer();
     	createFilter(db, buff);
     	sqlFilter = buff.toString();
     }
     pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
     prepareFilter(pst);

     return  DatabaseUtils.executeQuery(db, pst, pagedListInfo);
  }
  
  
  
  /**
   * @param  db                Description of the Parameter
   * @param  pst               Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
  	return queryList(db, pst, "", "");
  }

}

