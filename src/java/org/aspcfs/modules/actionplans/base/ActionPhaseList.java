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
import org.aspcfs.utils.web.HtmlSelect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    August 17, 2005
 * @version    $Id: ActionPhaseList.java,v 1.1.2.1 2005/08/17 15:29:07 partha
 *      Exp $
 */
public class ActionPhaseList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int parentId = -1;
  private int planId = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean buildSteps = false;
  protected boolean buildCompletePhaseList = false;


  /**
   *  Constructor for the ActionPhaseList object
   */
  public ActionPhaseList() { }


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
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM action_phase aph " +
        " WHERE aph.phase_id > -1 ");
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
      pagedListInfo.setDefaultSort("aph.entered", null);
      boolean flag = true;
      sqlOrder.append(
          "ORDER BY aph.entered" + (pagedListInfo.getSortOrder() != null ? " DESC " : " "));
    } else {
      sqlOrder.append("ORDER BY aph.entered ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " aph.* " +
        " FROM action_phase aph " +
        " WHERE aph.phase_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ActionPhase thisPlan = new ActionPhase(rs);
      this.add(thisPlan);
    }
    rs.close();
    pst.close();
    if (buildSteps && !buildCompletePhaseList) {
      buildSteps(db);
    }
    if (this.getBuildCompletePhaseList()) {
      int size = this.size();
      for (int i = 0; i < size; i++) {
        ActionPhase phase = (ActionPhase) this.get(i);
        phase.setBuildCompletePhaseList(true);
        if (buildSteps) {
          phase.setBuildSteps(true);
          phase.buildSteps(db);
        }
        phase.buildPhaseList(db);
        if (phase.getPhaseList().size() > 0) {
          this.addAll(this.size(), phase.getPhaseList());
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
  protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id > -1) {
      sqlFilter.append("AND aph.phase_id = ? ");
    }
    if (parentId == 0) {
      sqlFilter.append("AND aph.parent_id IS NULL ");
    } else if (parentId != -1) {
      sqlFilter.append("AND aph.parent_id = ? ");
    }
    if (planId > -1) {
      sqlFilter.append("AND aph.plan_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (parentId > 0) {
      DatabaseUtils.setInt(pst, ++i, parentId);
    }
    if (planId > -1) {
      pst.setInt(++i, planId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean result = false;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionPhase phase = (ActionPhase) iterator.next();
      result = phase.delete(db) && result;
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildSteps(Connection db) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionPhase phase = (ActionPhase) iterator.next();
      phase.buildSteps(db);
    }
  }


  /**
   *  Gets the stepSize attribute of the ActionPhaseList object
   *
   * @return    The stepSize value
   */
  public int getStepsSize() {
    int result = 0;
    Iterator itr = (Iterator) this.iterator();
    while (itr.hasNext()) {
      ActionPhase phase = (ActionPhase) itr.next();
      result += phase.getSteps().size();
    }
    return result;
  }


  /**
   *  Gets the htmlSelectObject attribute of the ActionPhaseList object
   *
   * @return    The htmlSelectObject value
   */
  public HtmlSelect getHtmlSelectObject() {
    HtmlSelect actionPhaseListSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) i.next();
      actionPhaseListSelect.addItem(thisPhase.getId(), thisPhase.getName());
    }
    return actionPhaseListSelect;
  }


  /**
   *  Gets the htmlSelect attribute of the ActionPhaseList object
   *
   * @param  selectName  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the htmlSelect attribute of the ActionPlanList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect actionPhaseListSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) i.next();
      actionPhaseListSelect.addItem(thisPhase.getId(), thisPhase.getName());
    }
    return actionPhaseListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the lastPhaseId attribute of the ActionPhaseList object
   *
   * @return    The lastPhaseId value
   */
  public int getLastPhaseId() {
    int result = -1;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) i.next();
      if (!i.hasNext()) {
        result = thisPhase.getId();
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasUserGroupStep() {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionPhase phase = (ActionPhase) iter.next();
      if (phase.getSteps() != null && phase.getSteps().hasUserGroupStep()) {
        return true;
      }
    }
    return false;
  }


///////Get & Set methods
  /**
   *  Gets the pagedListInfo attribute of the ActionPhaseList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the ActionPhaseList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the id attribute of the ActionPhaseList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionPhaseList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionPhaseList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the parentId attribute of the ActionPhaseList object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the ActionPhaseList object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ActionPhaseList object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planId attribute of the ActionPhaseList object
   *
   * @return    The planId value
   */
  public int getPlanId() {
    return planId;
  }


  /**
   *  Sets the planId attribute of the ActionPhaseList object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(int tmp) {
    this.planId = tmp;
  }


  /**
   *  Sets the planId attribute of the ActionPhaseList object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(String tmp) {
    this.planId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the ActionPhaseList object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPhaseList object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPhaseList object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the ActionPhaseList object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPhaseList object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPhaseList object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildSteps attribute of the ActionPhaseList object
   *
   * @return    The buildSteps value
   */
  public boolean getBuildSteps() {
    return buildSteps;
  }


  /**
   *  Sets the buildSteps attribute of the ActionPhaseList object
   *
   * @param  tmp  The new buildSteps value
   */
  public void setBuildSteps(boolean tmp) {
    this.buildSteps = tmp;
  }


  /**
   *  Sets the buildSteps attribute of the ActionPhaseList object
   *
   * @param  tmp  The new buildSteps value
   */
  public void setBuildSteps(String tmp) {
    this.buildSteps = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildCompletePhaseList attribute of the ActionPhaseList object
   *
   * @return    The buildCompletePhaseList value
   */
  public boolean getBuildCompletePhaseList() {
    return buildCompletePhaseList;
  }


  /**
   *  Sets the buildCompletePhaseList attribute of the ActionPhaseList object
   *
   * @param  tmp  The new buildCompletePhaseList value
   */
  public void setBuildCompletePhaseList(boolean tmp) {
    this.buildCompletePhaseList = tmp;
  }


  /**
   *  Sets the buildCompletePhaseList attribute of the ActionPhaseList object
   *
   * @param  tmp  The new buildCompletePhaseList value
   */
  public void setBuildCompletePhaseList(String tmp) {
    this.buildCompletePhaseList = DatabaseUtils.parseBoolean(tmp);
  }
}


