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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 31, 2005
 */
public class ActionStepLookupList extends ArrayList {
  private int stepId = -1;


  /**
   *  Gets the stepId attribute of the ActionStepLookupList object
   *
   * @return    The stepId value
   */
  public int getStepId() {
    return stepId;
  }


  /**
   *  Sets the stepId attribute of the ActionStepLookupList object
   *
   * @param  tmp  The new stepId value
   */
  public void setStepId(int tmp) {
    this.stepId = tmp;
  }


  /**
   *  Sets the stepId attribute of the ActionStepLookupList object
   *
   * @param  tmp  The new stepId value
   */
  public void setStepId(String tmp) {
    this.stepId = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM action_step_lookup asl " +
        "WHERE asl.code > 0 ");

    createFilter(sqlFilter, db);

    sqlOrder.append("ORDER BY \"level\", asl.description ");
    //Need to build a base SQL statement for returning records
    sqlSelect.append(" SELECT ");
    sqlSelect.append(
        "asl.* " +
        "FROM action_step_lookup asl " +
        "WHERE asl.code > 0 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      ActionStepLookup thisItem = new ActionStepLookup(rs);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   * @param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (stepId > -1) {
      sqlFilter.append("AND asl.step_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (stepId != -1) {
      pst.setInt(++i, stepId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
}
