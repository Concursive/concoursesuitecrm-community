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
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    August 17, 2005
 * @version    $Id$
 */
public class ActionPhase extends GenericBean {
  // fields
  protected int id = -1;
  protected int parentId = -1;
  protected int planId = -1;
  protected String name = null;
  protected String description = null;
  protected boolean enabled = false;
  protected boolean random = false;
  protected boolean global = false;
  // record status
  protected Timestamp entered = null;

  //other related records
  protected boolean buildSteps = false;
  protected ActionStepList steps = new ActionStepList();
  protected ActionPhaseList phaseList = new ActionPhaseList();
  protected boolean buildCompletePhaseList = false;


  /**
   *  Constructor for the ActionPhase object
   */
  public ActionPhase() { }


  /**
   *  Constructor for the ActionPhase object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionPhase(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionPhase object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionPhase(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Action Plan ID");
    }
    StringBuffer sb = new StringBuffer(
        " SELECT aph.* " +
        " FROM action_phase aph " +
        " WHERE aph.phase_id = ? ");
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    if (buildSteps) {
      buildSteps(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //action_plan table
    this.setId(rs.getInt("phase_id"));
    //parentId should be 0 if database value is null
    parentId = DatabaseUtils.getInt(rs, "parent_id", 0);
    planId = DatabaseUtils.getInt(rs, "plan_id");
    name = rs.getString("phase_name");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    random = rs.getBoolean("random");
    global = rs.getBoolean("global");
  }


  /**
   *  Gets the global attribute of the ActionPhase object
   *
   * @return    The global value
   */
  public boolean isGlobal() {
    return global;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "action_phase_phase_id_seq");
      sql.append(
          "INSERT INTO action_phase ( plan_id, phase_name, " +
          "description, ");
      if (parentId > -1) {
        sql.append("parent_id, ");
      }
      if (id > -1) {
        sql.append("phase_id, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      sql.append(" enabled, random, \"global\")");
      sql.append(" VALUES (?, ?, ?, ");
      if (parentId > -1) {
        sql.append("?, ");
      }
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      sql.append("?, ?, ? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getPlanId());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      if (parentId > -1) {
        DatabaseUtils.setInt(pst, ++i, this.getParentId());
      }
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setBoolean(++i, this.getEnabled());
      pst.setBoolean(++i, this.getRandom());
      pst.setBoolean(++i, this.getGlobal());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "action_phase_phase_id_seq", id);
      if (commit) {
        db.commit();
      }
      result = true;
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
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
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE action_phase " +
        " SET parent_id = ?, " +
        " plan_id = ?, " +
        " phase_name = ?, " +
        " description = ?, " +
        " enabled = ?, " +
        " random = ?, " +
        " \"global\" = ? " +
        " WHERE phase_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId() == 0 ? -1 : this.getParentId());
    DatabaseUtils.setInt(pst, ++i, this.getPlanId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
    pst.setBoolean(++i, this.getRandom());
    pst.setBoolean(++i, this.getGlobal());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Deletes the phase and resets the chain of phases.
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    this.queryRecord(db, this.getId());

    //Build and delete steps
    this.buildSteps(db);
    steps.delete(db);

    //Reset the parentId of the childPhase
    int childPhaseId = ActionPhase.getPhaseIdGivenParentId(db, this.getId());
    ActionPhase childPhase = null;
    if (childPhaseId != -1) {
      childPhase = new ActionPhase(db, childPhaseId);
    }
    if (childPhase != null) {
      if (this.getParentId() != -1 && this.getParentId() != 0) {
        childPhase.setParentId(this.getParentId());
      } else {
        childPhase.setParentId(-1);
      }
      childPhase.update(db);
    }

    // Delete the phase
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM action_phase WHERE phase_id = ? ");
    pst.setInt(1, this.getId());
    boolean result = pst.execute();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int updateSteps(Connection db) throws SQLException {
    int result = -1;
    Iterator iterator = (Iterator) steps.iterator();
    while (iterator.hasNext()) {
      ActionStep step = (ActionStep) iterator.next();
      result += step.update(db);
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
    //This needs to be uncommented on creating the ActionPhaseList class
    steps = new ActionStepList();
    steps.setBuildCompleteStepList(true);
    steps.setParentId(0);
    steps.setPhaseId(this.getId());
    steps.buildList(db);
  }


  /**
   *  Gets the timeZoneParams attribute of the ActionPhase class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("entered");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPhaseList(Connection db) throws SQLException {
    phaseList = new ActionPhaseList();
    phaseList.setParentId(this.getId());
    phaseList.setBuildSteps(this.getBuildSteps());
    phaseList.setBuildCompletePhaseList(this.getBuildCompletePhaseList());
    phaseList.buildList(db);
  }


  /**
   *  Sets the afterPhaseId attribute of the ActionPhase object
   *
   * @param  db                     The new afterPhaseId value
   * @param  previousPhaseIdString  The new afterPhaseId value
   * @param  addingPhase            The new afterPhaseId value
   * @return                        Description of the Return Value
   * @exception  SQLException       Description of the Exception
   */
  public boolean setAfterPhaseId(Connection db, String previousPhaseIdString, boolean addingPhase) throws SQLException {
    int nextPhaseId = -1;
    if (previousPhaseIdString == null || "".equals(previousPhaseIdString) || "-1".equals(previousPhaseIdString)) {
      return true;
    }
    ActionPhase previousPhase = new ActionPhase(db, Integer.parseInt(previousPhaseIdString));
    ActionPhase nextPhase = null;
    nextPhaseId = ActionPhase.getPhaseIdGivenParentId(db, previousPhase.getId());
    if (nextPhaseId != -1) {
      nextPhase = new ActionPhase(db, nextPhaseId);
    }

    if (!addingPhase) {
      //Set the previousPhase parent id
      if (this.getParentId() == 0 || this.getParentId() == -1) {
        previousPhase.setParentId(-1);
      } else {
        previousPhase.setParentId(this.getParentId());
      }
      previousPhase.update(db);
    }

    //Set next phase's parent
    if (nextPhaseId != -1) {
      nextPhase.setParentId(this.getId());
      nextPhase.update(db);
    }
    //Set the current phase parentId
    this.setParentId(previousPhase.getId());
    this.update(db);

    return true;
  }


  /**
   *  Sets the beforePhaseId attribute of the ActionPhase object
   *
   * @param  db                 The new beforePhaseId value
   * @param  nextPhaseIdString  The new beforePhaseId value
   * @param  addingPhase        The new beforePhaseId value
   * @return                    Description of the Return Value
   * @exception  SQLException   Description of the Exception
   */
  public boolean setBeforePhaseId(Connection db, String nextPhaseIdString, boolean addingPhase) throws SQLException {
    boolean result = false;
    int earlierNextPhaseId = -1;
    if (nextPhaseIdString == null || "".equals(nextPhaseIdString) || "-1".equals(nextPhaseIdString)) {
      return true;
    }
    int nextPhaseId = Integer.parseInt(nextPhaseIdString);
    ActionPhase earlierNextPhase = null;
    ActionPhase nextPhase = new ActionPhase(db, nextPhaseId);

    if (!addingPhase) {
      earlierNextPhaseId = ActionPhase.getPhaseIdGivenParentId(db, this.getId());
      if (earlierNextPhaseId != -1) {
        earlierNextPhase = new ActionPhase(db, earlierNextPhaseId);
      }
    }

    //Set the current phase parentId
    if (nextPhase.getParentId() == 0 || nextPhase.getParentId() == -1) {
      this.setParentId(-1);
    } else {
      this.setParentId(nextPhase.getParentId());
    }
    this.update(db);

    //Set the nextPhase parent id
    nextPhase.setParentId(this.getId());
    nextPhase.update(db);

    //Set the previousNextPhase parentId
    if (earlierNextPhase != null) {
      earlierNextPhase.setParentId(nextPhase.getId());
      earlierNextPhase.update(db);
    }
    return true;
  }


//TODO generalize this to work for a tree structure of phases
  /**
   *  Gets the phaseIdGivenParentId attribute of the ActionPhase class
   *
   * @param  db                Description of the Parameter
   * @param  parentId          Description of the Parameter
   * @return                   The phaseIdGivenParentId value
   * @exception  SQLException  Description of the Exception
   */
  public static int getPhaseIdGivenParentId(Connection db, int parentId) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT phase_id FROM action_phase WHERE parent_id = ?");
    pst.setInt(1, parentId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = DatabaseUtils.getInt(rs, "phase_id");
    }
    rs.close();
    pst.close();

    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Phase ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;

    // Check for records inserted for the plan
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) as recordcount " +
          " FROM action_phase_work " +
          " WHERE action_phase_id = ?");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("actionPhaseRecords");
          thisDependency.setCount(recordCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    // Check for steps in the phase
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) as recordcount " +
          " FROM action_step " +
          " WHERE phase_id = ?");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("actionSteps");
          thisDependency.setCount(recordCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    return dependencyList;
  }


  /**
   *  Gets the lastStep attribute of the ActionPhase object
   *
   * @return    The lastStep value
   */
  public int getLastStep() {
    if (steps != null && steps.size() > 0) {
      return steps.getLastStepId();
    }
    return -1;
  }


  /**
   *  Determines if this is the current phase in the action plan
   *
   * @param  actionPlan  Description of the Parameter
   * @return             The current value
   */
  public boolean isCurrent(ActionPlan actionPlan) {
    ActionPhase currentPhase = actionPlan.getCurrentPhase();
    if (currentPhase != null) {
      return (currentPhase.getId() == this.getId());
    }
    return false;
  }


  /**
   *  Determines if this phase comes before the current phase of the plan
   *
   * @param  actionPlan  Description of the Parameter
   * @return             Description of the Return Value
   */
  public boolean beforeCurrent(ActionPlan actionPlan) {
    boolean before = false;
    Iterator i = actionPlan.getPhases().iterator();
    while (i.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) i.next();
      if (thisPhase.isCurrent(actionPlan)) break;
      if (thisPhase.getId() == this.getId()) { 
        before = true;
        break;
      }
    }
    return before;
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the ActionPhase object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionPhase object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionPhase object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the parentId attribute of the ActionPhase object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the ActionPhase object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ActionPhase object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planId attribute of the ActionPhase object
   *
   * @return    The planId value
   */
  public int getPlanId() {
    return planId;
  }


  /**
   *  Sets the planId attribute of the ActionPhase object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(int tmp) {
    this.planId = tmp;
  }


  /**
   *  Sets the planId attribute of the ActionPhase object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(String tmp) {
    this.planId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the name attribute of the ActionPhase object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the ActionPhase object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the description attribute of the ActionPhase object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the ActionPhase object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the enabled attribute of the ActionPhase object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the ActionPhase object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ActionPhase object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the entered attribute of the ActionPhase object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionPhase object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionPhase object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the buildSteps attribute of the ActionPhase object
   *
   * @return    The buildSteps value
   */
  public boolean getBuildSteps() {
    return buildSteps;
  }


  /**
   *  Sets the buildSteps attribute of the ActionPhase object
   *
   * @param  tmp  The new buildSteps value
   */
  public void setBuildSteps(boolean tmp) {
    this.buildSteps = tmp;
  }


  /**
   *  Sets the buildSteps attribute of the ActionPhase object
   *
   * @param  tmp  The new buildSteps value
   */
  public void setBuildSteps(String tmp) {
    this.buildSteps = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the steps attribute of the ActionPhase object
   *
   * @return    The steps value
   */
  public ActionStepList getSteps() {
    return steps;
  }


  /**
   *  Sets the steps attribute of the ActionPhase object
   *
   * @param  tmp  The new steps value
   */
  public void setSteps(ActionStepList tmp) {
    this.steps = tmp;
  }


  /**
   *  Gets the phaseList attribute of the ActionPhase object
   *
   * @return    The phaseList value
   */
  public ActionPhaseList getPhaseList() {
    return phaseList;
  }


  /**
   *  Sets the phaseList attribute of the ActionPhase object
   *
   * @param  tmp  The new phaseList value
   */
  public void setPhaseList(ActionPhaseList tmp) {
    this.phaseList = tmp;
  }


  /**
   *  Gets the buildCompletePhaseList attribute of the ActionPhase object
   *
   * @return    The buildCompletePhaseList value
   */
  public boolean getBuildCompletePhaseList() {
    return buildCompletePhaseList;
  }


  /**
   *  Sets the buildCompletePhaseList attribute of the ActionPhase object
   *
   * @param  tmp  The new buildCompletePhaseList value
   */
  public void setBuildCompletePhaseList(boolean tmp) {
    this.buildCompletePhaseList = tmp;
  }


  /**
   *  Sets the buildCompletePhaseList attribute of the ActionPhase object
   *
   * @param  tmp  The new buildCompletePhaseList value
   */
  public void setBuildCompletePhaseList(String tmp) {
    this.buildCompletePhaseList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the random attribute of the ActionPhase object
   *
   * @return    The random value
   */
  public boolean getRandom() {
    return random;
  }


  /**
   *  Gets the global attribute of the ActionPhase object
   *
   * @return    The global value
   */
  public boolean getGlobal() {
    return global;
  }


  /**
   *  Sets the global attribute of the ActionPhase object
   *
   * @param  tmp  The new global value
   */
  public void setGlobal(boolean tmp) {
    this.global = tmp;
  }


  /**
   *  Sets the global attribute of the ActionPhase object
   *
   * @param  tmp  The new global value
   */
  public void setGlobal(String tmp) {
    this.global = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the random attribute of the ActionPhase object
   *
   * @param  tmp  The new random value
   */
  public void setRandom(boolean tmp) {
    this.random = tmp;
  }


  /**
   *  Sets the random attribute of the ActionPhase object
   *
   * @param  tmp  The new random value
   */
  public void setRandom(String tmp) {
    this.random = DatabaseUtils.parseBoolean(tmp);
  }
}

