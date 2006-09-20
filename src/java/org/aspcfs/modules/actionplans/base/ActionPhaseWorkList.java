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

import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 17, 2005
 * @version    $Id: ActionPhaseWorkList.java,v 1.1.2.8 2005/10/17 20:34:35
 *      partha Exp $
 */
public class ActionPhaseWorkList extends ArrayList {
  private PagedListInfo pagedListInfo = null;
  private int planWorkId = -1;
  private int actionPhaseId = -1;
  private boolean hasWork = false;
  private int global = Constants.UNDEFINED;
  private boolean isCurrent = false;
  //resources
  private boolean buildStepWork = false;
  private boolean buildLinkedObject = false;
  private boolean buildPhase = false;
  private ActionPlanWork planWork = null;
  private boolean buildCurrentStepsOnly = false;


  /**
   *  Gets the global attribute of the ActionPhaseWorkList object
   *
   * @return    The global value
   */
  public int getGlobal() {
    return global;
  }


  /**
   *  Sets the global attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new global value
   */
  public void setGlobal(int tmp) {
    this.global = tmp;
  }


  /**
   *  Sets the global attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new global value
   */
  public void setGlobal(String tmp) {
    this.global = Integer.parseInt(tmp);
  }


  /**
   *  Gets the actionPhaseId attribute of the ActionPhaseWorkList object
   *
   * @return    The actionPhaseId value
   */
  public int getActionPhaseId() {
    return actionPhaseId;
  }


  /**
   *  Sets the actionPhaseId attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new actionPhaseId value
   */
  public void setActionPhaseId(int tmp) {
    this.actionPhaseId = tmp;
  }


  /**
   *  Sets the actionPhaseId attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new actionPhaseId value
   */
  public void setActionPhaseId(String tmp) {
    this.actionPhaseId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildPhase attribute of the ActionPhaseWorkList object
   *
   * @return    The buildPhase value
   */
  public boolean getBuildPhase() {
    return buildPhase;
  }


  /**
   *  Sets the buildPhase attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildPhase value
   */
  public void setBuildPhase(boolean tmp) {
    this.buildPhase = tmp;
  }


  /**
   *  Sets the buildPhase attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildPhase value
   */
  public void setBuildPhase(String tmp) {
    this.buildPhase = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the hasWork attribute of the ActionPhaseWorkList object
   *
   * @return    The hasWork value
   */
  public boolean getHasWork() {
    return hasWork;
  }


  /**
   *  Sets the hasWork attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new hasWork value
   */
  public void setHasWork(boolean tmp) {
    this.hasWork = tmp;
  }


  /**
   *  Sets the hasWork attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new hasWork value
   */
  public void setHasWork(String tmp) {
    this.hasWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildLinkedObject attribute of the ActionPhaseWorkList object
   *
   * @return    The buildLinkedObject value
   */
  public boolean getBuildLinkedObject() {
    return buildLinkedObject;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(boolean tmp) {
    this.buildLinkedObject = tmp;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(String tmp) {
    this.buildLinkedObject = DatabaseUtils.parseBoolean(tmp);
  }



  /**
   *  Gets the planWorkId attribute of the ActionPhaseWorkList object
   *
   * @return    The planWorkId value
   */
  public int getPlanWorkId() {
    return planWorkId;
  }


  /**
   *  Sets the planWorkId attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new planWorkId value
   */
  public void setPlanWorkId(int tmp) {
    this.planWorkId = tmp;
  }


  /**
   *  Sets the planWorkId attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new planWorkId value
   */
  public void setPlanWorkId(String tmp) {
    this.planWorkId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildStepWork attribute of the ActionPhaseWorkList object
   *
   * @return    The buildStepWork value
   */
  public boolean getBuildStepWork() {
    return buildStepWork;
  }


  /**
   *  Sets the buildStepWork attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildStepWork value
   */
  public void setBuildStepWork(boolean tmp) {
    this.buildStepWork = tmp;
  }


  /**
   *  Sets the buildStepWork attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildStepWork value
   */
  public void setBuildStepWork(String tmp) {
    this.buildStepWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the planWork attribute of the ActionPhaseWorkList object
   *
   * @return    The planWork value
   */
  public ActionPlanWork getPlanWork() {
    return planWork;
  }


  /**
   *  Sets the planWork attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new planWork value
   */
  public void setPlanWork(ActionPlanWork tmp) {
    this.planWork = tmp;
  }


  /**
   *  Gets the buildCurrentStepsOnly attribute of the ActionPhaseWorkList object
   *
   * @return    The buildCurrentStepsOnly value
   */
  public boolean getBuildCurrentStepsOnly() {
    return buildCurrentStepsOnly;
  }


  /**
   *  Sets the buildCurrentStepsOnly attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildCurrentStepsOnly value
   */
  public void setBuildCurrentStepsOnly(boolean tmp) {
    this.buildCurrentStepsOnly = tmp;
  }


  /**
   *  Sets the buildCurrentStepsOnly attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new buildCurrentStepsOnly value
   */
  public void setBuildCurrentStepsOnly(String tmp) {
    this.buildCurrentStepsOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the isCurrent attribute of the ActionPhaseWorkList object
   *
   * @return    The isCurrent value
   */
  public boolean getIsCurrent() {
    return isCurrent;
  }


  /**
   *  Sets the isCurrent attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new isCurrent value
   */
  public void setIsCurrent(boolean tmp) {
    this.isCurrent = tmp;
  }


  /**
   *  Sets the isCurrent attribute of the ActionPhaseWorkList object
   *
   * @param  tmp  The new isCurrent value
   */
  public void setIsCurrent(String tmp) {
    this.isCurrent = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
        "FROM action_phase_work apw " +
        "LEFT JOIN action_phase ap ON (apw.action_phase_id = ap.phase_id) " +
        "WHERE apw.phase_work_id > 0 ");

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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND " + DatabaseUtils.toLowerCase(db) + "(ap.phase_name) < ? ");
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
      pagedListInfo.setDefaultSort("ap.phase_name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", ap.phase_name ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        "apw.*, " +
        "ap.phase_name, ap.description, ap.parent_id, ap." + DatabaseUtils.addQuotes(db, "global") + " " +
        "FROM action_phase_work apw " +
        "LEFT JOIN action_phase ap ON (apw.action_phase_id = ap.phase_id) " +
        "WHERE apw.phase_work_id > 0 ");
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
      ActionPhaseWork actionPhaseWork = new ActionPhaseWork(rs);
      this.add(actionPhaseWork);
    }
    rs.close();
    pst.close();

    if (buildStepWork || buildLinkedObject || buildPhase) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ActionPhaseWork actionPhaseWork = (ActionPhaseWork) i.next();
        actionPhaseWork.setPlanWork(this.getPlanWork());
        if (buildLinkedObject) {
          actionPhaseWork.setBuildLinkedObject(buildLinkedObject);
        }
        if (buildCurrentStepsOnly) {
          actionPhaseWork.buildCurrentSteps(db);
        } else if (buildStepWork) {
          actionPhaseWork.buildStepWork(db);
        }
        if (this.getBuildPhase()) {
          actionPhaseWork.setBuildPhase(this.getBuildPhase());
          actionPhaseWork.buildPhaseObject(db);
        }
      }
    }
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
    if (planWorkId > -1) {
      sqlFilter.append("AND apw.plan_work_id = ? ");
    }
    if (actionPhaseId > -1) {
      sqlFilter.append("AND apw.action_phase_id = ? ");
    }
    if (hasWork) {
      sqlFilter.append("AND apw.start_date IS NOT NULL ");
    }
    if (global != Constants.UNDEFINED) {
      sqlFilter.append("AND ap." + DatabaseUtils.addQuotes(db, "global") + " = ? ");
    }
    if (isCurrent) {
      sqlFilter.append("AND apw.start_date IS NOT NULL AND apw.end_date IS NULL AND apw.status_id IS NULL ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (planWorkId > -1) {
      pst.setInt(++i, planWorkId);
    }
    if (actionPhaseId > -1) {
      pst.setInt(++i, actionPhaseId);
    }
    if (global != Constants.UNDEFINED) {
      pst.setBoolean(++i, (global == Constants.TRUE));
    }
    return i;
  }


  /**
   *  Gets the rootPhase attribute of the ActionPhaseWorkList object
   *
   * @return    The rootPhase value
   */
  public ActionPhaseWork getRootPhase() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhaseWork phaseWork = (ActionPhaseWork) i.next();
      if (phaseWork.getParentId() == 0) {
        return phaseWork;
      }
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhaseWork phaseWork = (ActionPhaseWork) i.next();
      phaseWork.setPlanWork(planWork);
      phaseWork.delete(db);
    }
  }


  /**
   *  Sets the phaseWorks attribute of the ActionPhaseWorkList object
   *
   * @param  map               The new phaseWorks value
   * @exception  SQLException  Description of the Exception
   */
  public void setPhaseWorks(HashMap map) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhaseWork phaseWork = (ActionPhaseWork) i.next();
      if (map.get(new Integer(phaseWork.getId())) != null) {
        //Remove duplicate entries from the map if they already exist in the list
        map.remove(new Integer(phaseWork.getId()));
      }
    }
    i = (Iterator) map.keySet().iterator();
    while (i.hasNext()) {
      ActionPhaseWork phaseWork = (ActionPhaseWork) map.get(i.next());
      this.add(phaseWork);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void reset(Connection db) throws SQLException {
    String nullStr = null;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_phase_work " +
        "SET start_date = ?, end_date = ?, status_id = ? " +
        "WHERE phase_work_id = ? ");
    pst.setNull(1, java.sql.Types.DATE);
    pst.setNull(2, java.sql.Types.DATE);
    pst.setInt(3, ActionPlanWork.INCOMPLETE);
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhaseWork phaseWork = (ActionPhaseWork) i.next();
      pst.setInt(4, phaseWork.getId());
      pst.executeUpdate();
    }
  }


  /**
   *  Gets the phaseWorkById attribute of the ActionPhaseWorkList object
   *
   * @param  phaseWorkId  Description of the Parameter
   * @return              The phaseWorkById value
   */
  public ActionPhaseWork getPhaseWorkById(int phaseWorkId) {
    ActionPhaseWork result = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhaseWork phaseWork = (ActionPhaseWork) i.next();
      if (phaseWork.getId() == phaseWorkId) {
        result = phaseWork;
        break;
      }
    }
    return result;
  }
}

