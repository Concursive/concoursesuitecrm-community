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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 17, 2005
 * @version    $Id: ActionPhaseWork.java,v 1.1.2.12 2005/10/18 21:19:31 partha
 *      Exp $
 */
public class ActionPhaseWork extends GenericBean {
  private int id = -1;
  private int planWorkId = -1;
  private int actionPhaseId = -1;

  private int statusId = -1;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private int level = -1;

  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;

  private String phaseName = null;
  private String phaseDescription = null;
  private int parentId = -1;
  private boolean global = false;
  //resources
  private int assignedTo = -1;
  private int managerId = -1;
  private boolean buildStepWork = false;
  private boolean buildLinkedObject = false;
  private ActionItemWorkList itemWorkList = null;
  private ActionPhase phase = null;
  private boolean buildPhase = false;
  private ActionPlanWork planWork = null;
  private boolean buildCurrentStepsOnly = false;


  /**
   *  Gets the managerId attribute of the ActionPhaseWork object
   *
   * @return    The managerId value
   */
  public int getManagerId() {
    return managerId;
  }


  /**
   *  Sets the managerId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new managerId value
   */
  public void setManagerId(int tmp) {
    this.managerId = tmp;
  }


  /**
   *  Sets the managerId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new managerId value
   */
  public void setManagerId(String tmp) {
    this.managerId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the assignedTo attribute of the ActionPhaseWork object
   *
   * @return    The assignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Gets the global attribute of the ActionPhaseWork object
   *
   * @return    The global value
   */
  public boolean getGlobal() {
    return global;
  }


  /**
   *  Sets the global attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new global value
   */
  public void setGlobal(boolean tmp) {
    this.global = tmp;
  }


  /**
   *  Sets the global attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new global value
   */
  public void setGlobal(String tmp) {
    this.global = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the assignedTo attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(int tmp) {
    this.assignedTo = tmp;
  }


  /**
   *  Sets the assignedTo attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(String tmp) {
    this.assignedTo = Integer.parseInt(tmp);
  }


  /**
   *  Gets the level attribute of the ActionPhaseWork object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildLinkedObject attribute of the ActionPhaseWork object
   *
   * @return    The buildLinkedObject value
   */
  public boolean getBuildLinkedObject() {
    return buildLinkedObject;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(boolean tmp) {
    this.buildLinkedObject = tmp;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(String tmp) {
    this.buildLinkedObject = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the parentId attribute of the ActionPhaseWork object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the entered attribute of the ActionPhaseWork object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modified attribute of the ActionPhaseWork object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the ActionPhaseWork object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the ActionPhaseWork object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the phaseName attribute of the ActionPhaseWork object
   *
   * @return    The phaseName value
   */
  public String getPhaseName() {
    return phaseName;
  }


  /**
   *  Sets the phaseName attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new phaseName value
   */
  public void setPhaseName(String tmp) {
    this.phaseName = tmp;
  }


  /**
   *  Gets the phaseDescription attribute of the ActionPhaseWork object
   *
   * @return    The phaseDescription value
   */
  public String getPhaseDescription() {
    return phaseDescription;
  }


  /**
   *  Sets the phaseDescription attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new phaseDescription value
   */
  public void setPhaseDescription(String tmp) {
    this.phaseDescription = tmp;
  }


  /**
   *  Gets the buildStepWork attribute of the ActionPhaseWork object
   *
   * @return    The buildStepWork value
   */
  public boolean getBuildStepWork() {
    return buildStepWork;
  }


  /**
   *  Sets the buildStepWork attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildStepWork value
   */
  public void setBuildStepWork(boolean tmp) {
    this.buildStepWork = tmp;
  }


  /**
   *  Sets the buildStepWork attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildStepWork value
   */
  public void setBuildStepWork(String tmp) {
    this.buildStepWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the itemWorkList attribute of the ActionPhaseWork object
   *
   * @return    The itemWorkList value
   */
  public ActionItemWorkList getItemWorkList() {
    return itemWorkList;
  }


  /**
   *  Sets the itemWorkList attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new itemWorkList value
   */
  public void setItemWorkList(ActionItemWorkList tmp) {
    this.itemWorkList = tmp;
  }


  /**
   *  Gets the id attribute of the ActionPhaseWork object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planWorkId attribute of the ActionPhaseWork object
   *
   * @return    The planWorkId value
   */
  public int getPlanWorkId() {
    return planWorkId;
  }


  /**
   *  Sets the planWorkId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new planWorkId value
   */
  public void setPlanWorkId(int tmp) {
    this.planWorkId = tmp;
  }


  /**
   *  Sets the planWorkId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new planWorkId value
   */
  public void setPlanWorkId(String tmp) {
    this.planWorkId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the actionPhaseId attribute of the ActionPhaseWork object
   *
   * @return    The actionPhaseId value
   */
  public int getActionPhaseId() {
    return actionPhaseId;
  }


  /**
   *  Sets the actionPhaseId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new actionPhaseId value
   */
  public void setActionPhaseId(int tmp) {
    this.actionPhaseId = tmp;
  }


  /**
   *  Sets the actionPhaseId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new actionPhaseId value
   */
  public void setActionPhaseId(String tmp) {
    this.actionPhaseId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the statusId attribute of the ActionPhaseWork object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Sets the statusId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the startDate attribute of the ActionPhaseWork object
   *
   * @return    The startDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Sets the startDate attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new startDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the endDate attribute of the ActionPhaseWork object
   *
   * @return    The endDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Sets the endDate attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new endDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the phase attribute of the ActionPhaseWork object
   *
   * @return    The phase value
   */
  public ActionPhase getPhase() {
    return phase;
  }


  /**
   *  Sets the phase attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new phase value
   */
  public void setPhase(ActionPhase tmp) {
    this.phase = tmp;
  }


  /**
   *  Gets the buildPhase attribute of the ActionPhaseWork object
   *
   * @return    The buildPhase value
   */
  public boolean getBuildPhase() {
    return buildPhase;
  }


  /**
   *  Sets the buildPhase attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildPhase value
   */
  public void setBuildPhase(boolean tmp) {
    this.buildPhase = tmp;
  }


  /**
   *  Sets the buildPhase attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildPhase value
   */
  public void setBuildPhase(String tmp) {
    this.buildPhase = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the planWork attribute of the ActionPhaseWork object
   *
   * @return    The planWork value
   */
  public ActionPlanWork getPlanWork() {
    return planWork;
  }


  /**
   *  Sets the planWork attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new planWork value
   */
  public void setPlanWork(ActionPlanWork tmp) {
    this.planWork = tmp;
  }


  /**
   *  Gets the buildCurrentStepsOnly attribute of the ActionPhaseWork object
   *
   * @return    The buildCurrentStepsOnly value
   */
  public boolean getBuildCurrentStepsOnly() {
    return buildCurrentStepsOnly;
  }


  /**
   *  Sets the buildCurrentStepsOnly attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildCurrentStepsOnly value
   */
  public void setBuildCurrentStepsOnly(boolean tmp) {
    this.buildCurrentStepsOnly = tmp;
  }


  /**
   *  Sets the buildCurrentStepsOnly attribute of the ActionPhaseWork object
   *
   * @param  tmp  The new buildCurrentStepsOnly value
   */
  public void setBuildCurrentStepsOnly(String tmp) {
    this.buildCurrentStepsOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Constructor for the ActionPhaseWork object
   */
  public ActionPhaseWork() { }


  /**
   *  Constructor for the ActionPhaseWork object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionPhaseWork(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionPhaseWork object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionPhaseWork(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Action Phase Work ID");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT apw.*, " +
        "ap.phase_name, ap.description, ap.parent_id, ap.\"global\" " +
        "FROM action_phase_work apw " +
        "LEFT JOIN action_phase ap ON (apw.action_phase_id = ap.phase_id) " +
        "WHERE apw.phase_work_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (buildStepWork) {
      this.buildStepWork(db);
    }

    if (buildPhase) {
      buildPhaseObject(db);
    }

    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildStepWork(Connection db) throws SQLException {
    itemWorkList = new ActionItemWorkList();
    itemWorkList.setPhaseWorkId(id);
    itemWorkList.setBuildLinkedObject(buildLinkedObject);
    itemWorkList.setBuildStep(true);
    itemWorkList.setPlanWork(planWork);
    itemWorkList.buildList(db);
  }

  public void buildCurrentSteps(Connection db) throws SQLException {
    itemWorkList = new ActionItemWorkList();
    itemWorkList.setPhaseWorkId(id);
    itemWorkList.setBuildLinkedObject(true);
    itemWorkList.setBuildStep(true);
    itemWorkList.setPlanWork(planWork);
    itemWorkList.buildList(db);
  }

  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void startRandomPhase(Connection db) throws SQLException {
    if (itemWorkList != null && itemWorkList.size() > 0) {
      itemWorkList.startRandomSteps(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildPhaseObject(Connection db) throws SQLException {
    phase = new ActionPhase();
    phase.setBuildSteps(true);
    phase.queryRecord(db, this.getActionPhaseId());
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("phase_work_id");
    planWorkId = rs.getInt("plan_work_id");
    actionPhaseId = rs.getInt("action_phase_id");

    statusId = DatabaseUtils.getInt(rs, "status_id");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    level = rs.getInt("level");
    //record keeping
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    //Action Phase
    parentId = DatabaseUtils.getInt(rs, "parent_id", 0);
    phaseName = rs.getString("phase_name");
    phaseDescription = rs.getString("description");
    global = rs.getBoolean("global");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (level != 0) {
      level = this.retrieveNextLevel(db);
    }
    id = DatabaseUtils.getNextSeq(db, "action_phase_work_phase_work_id_seq");
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO action_phase_work (" +
        (id > -1 ? "phase_work_id, " : "") +
        "plan_work_id, action_phase_id, ");
    if (startDate != null) {
      sql.append("start_date, ");
    }
    if (endDate != null) {
      sql.append("end_date, ");
    }
    sql.append("\"level\", ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredby, modifiedby, status_id ) ");
    sql.append("VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ");
    if (startDate != null) {
      sql.append("?, ");
    }
    if (endDate != null) {
      sql.append("?, ");
    }
    sql.append("?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ? ) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.getPlanWorkId());
    pst.setInt(++i, this.getActionPhaseId());
    if (startDate != null) {
      pst.setTimestamp(++i, startDate);
    }
    if (endDate != null) {
      pst.setTimestamp(++i, endDate);
    }
    pst.setInt(++i, level);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());

    pst.execute();
    pst.close();
    id =
        DatabaseUtils.getCurrVal(db, "action_phase_work_phase_work_id_seq", id);

    return true;
  }


  /**
   *  Gets the global attribute of the ActionPhaseWork object
   *
   * @return    The global value
   */
  public boolean isGlobal() {
    return this.global;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  actionPhase    Description of the Parameter
   * @param  actionPlan     Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, ActionPlan actionPlan, ActionPhase actionPhase) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //If it is a global phase, then set the level to 0.
      //This will need to be changed if global phases can have levels too
      if (actionPhase.isGlobal()) {
        level = 0;
      } else {
        //Set the start date if the action phase is the current phase in the plan
        if (actionPhase.isCurrent(actionPlan)) {
          this.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        } else if (actionPhase.beforeCurrent(actionPlan)) {
          this.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
          this.setEndDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        }
      }

      //If the action phase is not the current phase in the action plan then
      //
      //insert the action phase work
      this.insert(db);
      this.buildPhaseObject(db);
      //insert the action step work
      Iterator i = actionPhase.getSteps().iterator();
      while (i.hasNext()) {
        ActionStep actionStep = (ActionStep) i.next();
        ActionItemWork itemWork = new ActionItemWork();
        itemWork.setPhaseWorkId(this.getId());
        itemWork.setActionStepId(actionStep.getId());
        if (actionStep.getPermissionType() == ActionStep.MANAGER) {
          itemWork.setOwnerId(this.getManagerId());
        } else if (actionStep.getPermissionType() == ActionStep.ASSIGNED_USER ||
            actionStep.getPermissionType() == ActionStep.UP_USER_HIERARCHY ||
            actionStep.getPermissionType() == ActionStep.WITHIN_USER_HIERARCHY) {
          itemWork.setOwnerId(this.getAssignedTo());
        } else {
          //TODO:: set the owner to the appropriate user(s).
          itemWork.setOwnerId(this.getAssignedTo());
        }
        itemWork.setEnteredBy(this.getEnteredBy());
        itemWork.setModifiedBy(this.getModifiedBy());

        if (!actionPhase.isGlobal()) {
          if (actionPhase.getRandom()) {
            if (actionPhase.isCurrent(actionPlan)) {
              itemWork.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            }
          } else {
            if (actionPhase.isCurrent(actionPlan)) {
              if (actionStep.isCurrent(actionPlan)) {
                itemWork.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
              } else if (actionStep.beforeCurrent(actionPlan)) {
                itemWork.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
                itemWork.setEndDate(new java.sql.Timestamp(new java.util.Date().getTime()));
                itemWork.setStatusId(ActionPlanWork.UNDEFINED);
              }
            } else if (actionPhase.beforeCurrent(actionPlan)) {
              itemWork.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
              itemWork.setEndDate(new java.sql.Timestamp(new java.util.Date().getTime()));
              itemWork.setStatusId(ActionPlanWork.UNDEFINED);
            }
          }
        }
        itemWork.insert(db);
      }
      if (commit) {
        db.commit();
      }
      result = true;
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException("ActionPhaseWork: " + e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void update(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE action_phase_work ");
    sql.append("SET status_id = ?, ");
    sql.append("start_date = ?, ");
    sql.append("end_date = ?, ");
    sql.append("modifiedby = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " ");
    sql.append("WHERE phase_work_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, statusId);
    DatabaseUtils.setTimestamp(pst, ++i, startDate);
    DatabaseUtils.setTimestamp(pst, ++i, endDate);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //delete action item work records
      ActionItemWorkList items = new ActionItemWorkList();
      items.setPhaseWorkId(id);
      items.buildList(db);
      items.setPlanWork(this.getPlanWork());
      items.delete(db);
      //delete action phase work record
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM action_phase_work " +
          "WHERE phase_work_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   *  Gets the skipped attribute of the ActionPhaseWork object
   *
   * @return    The skipped value
   */
  public boolean isSkipped() {
    return (statusId == ActionPlanWork.SKIPPED);
  }


  /**
   *  Gets the current attribute of the ActionPhaseWork object
   *
   * @return    The current value
   */
  public boolean isCurrent() {
    if (isSkipped()) {
      return false;
    }
    Iterator item = itemWorkList.iterator();
    while (item.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) item.next();
      if (itemWork.getStartDate() != null && itemWork.getEndDate() == null) {
        return true;
      }
    }
    return false;
  }


  /**
   *  Gets the currentStep attribute of the ActionPhaseWork object
   *
   * @return    The currentStep value
   */
  public ActionItemWork getCurrentStep() {
    Iterator steps = itemWorkList.iterator();
    while (steps.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) steps.next();
      if (itemWork.isCurrent()) {
        return itemWork;
      }
    }
    return null;
  }


  /**
   *  Gets the daysInPhase attribute of the ActionPhaseWork object
   *
   * @return    The daysInPhase value
   */
  public int getDaysInPhase() {
    //Calculations
    if (startDate != null) {
      if (endDate != null) {
        float ageCheck = ((endDate.getTime() - startDate.getTime()) / 3600000);
        int totalHours = java.lang.Math.round(ageCheck);
        return java.lang.Math.round(totalHours / 24);
        //ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
      } else {
        float ageCheck = (
            (Calendar.getInstance().getTimeInMillis() - startDate.getTime()) / 3600000);
        int totalHours = java.lang.Math.round(ageCheck);
        return java.lang.Math.round(totalHours / 24);
        //ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
      }
    }
    return 0;
  }


  /**
   *  Gets the nextPhase attribute of the ActionPhaseWork object
   *
   * @param  db             Description of the Parameter
   * @return                The nextPhase value
   * @throws  SQLException  Description of the Exception
   */
  public ActionPhaseWork getNextPhase(Connection db) throws SQLException {
    ActionPhaseWorkList phaseWorkList = new ActionPhaseWorkList();
    phaseWorkList.setPlanWorkId(planWorkId);
    phaseWorkList.buildList(db);
    Iterator j = phaseWorkList.iterator();
    while (j.hasNext()) {
      ActionPhaseWork thisPhase = (ActionPhaseWork) j.next();
      if (thisPhase.getId() == this.getId()) {
        if (j.hasNext()) {
          return (ActionPhaseWork) j.next();
        }
      }
    }
    return null;
  }


  /**
   *  Gets the previousPhase attribute of the ActionPhaseWork object
   *
   * @param  db             Description of the Parameter
   * @return                The previousPhase value
   * @throws  SQLException  Description of the Exception
   */
  public ActionPhaseWork getPreviousPhase(Connection db) throws SQLException {
    ActionPhaseWorkList phaseWorkList = new ActionPhaseWorkList();
    phaseWorkList.setPlanWorkId(planWorkId);
    phaseWorkList.buildList(db);
    Iterator j = phaseWorkList.iterator();
    ActionPhaseWork previousPhase = null;
    while (j.hasNext()) {
      ActionPhaseWork thisPhase = (ActionPhaseWork) j.next();
      if (thisPhase.getId() == this.getId()) {
        return previousPhase;
      } else {
        previousPhase = thisPhase;
      }
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  private int retrieveNextLevel(Connection db) throws SQLException {
    int returnLevel = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT MAX(\"level\") as levelcount " +
        "FROM action_phase_work " +
        "WHERE plan_work_id = ? ");
    pst.setInt(1, planWorkId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      returnLevel = rs.getInt("levelcount");
      if (rs.wasNull()) {
        returnLevel = 0;
      }
    }
    rs.close();
    pst.close();
    return ++returnLevel;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean allStepsComplete() {
    boolean result = true;
    if (itemWorkList != null && itemWorkList.size() > 0) {
      Iterator iter = (Iterator) itemWorkList.iterator();
      while (iter.hasNext()) {
        ActionItemWork item = (ActionItemWork) iter.next();
        if (!item.hasStatus()) {
          result = false;
          break;
        }
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  userId  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean requiresUserAttention(int userId, HttpServletRequest request) {
    boolean result = false;
    if (!this.getPhase().getRandom()) {
      return (this.getCurrentStep().userHasPermission(userId, request));
    } else {
      if (itemWorkList != null && itemWorkList.size() > 0) {
        Iterator iter = (Iterator) itemWorkList.iterator();
        while (iter.hasNext()) {
          ActionItemWork item = (ActionItemWork) iter.next();
          if (!item.hasStatus() && item.userHasPermission(userId, request)) {
            result = true;
            break;
          }
        }
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean noStepComplete() {
    boolean result = true;
    if (itemWorkList != null && itemWorkList.size() > 0) {
      Iterator iter = (Iterator) itemWorkList.iterator();
      while (iter.hasNext()) {
        ActionItemWork item = (ActionItemWork) iter.next();
        if (item.hasStatus()) {
          result = false;
          break;
        }
      }
    }
    return result;
  }
}

