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
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftPlanMapList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryPlanMapList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    August 17, 2005
 * @version    $Id: ActionPlan.java,v 1.1.2.9 2005/10/17 15:13:24 mrajkowski Exp
 *      $
 */
public class ActionPlan extends GenericBean {

  public final static int ACCOUNTS = 42420034;
  public final static int TICKETS = 912051329;
  public final static int CONTACTS = 2;
  public final static int QUOTES = 912051328;
  public final static int PIPELINE = 3;
  public final static int PROJECTS = 912051330;
  public final static int COMMUNICATIONS = 912051331;
  public final static int ADMIN = 912051332;
  public final static int CONTACTS_CALLS = 912051333;
  public final static int CFSNOTE = 912051334;
  public final static int PIPELINE_CALLS = 912051335;
  public final static int PIPELINE_COMPONENT = 1011200517;
  public final static int MYHOMEPAGE = 912051336;
  public final static int ACTION_ITEM_WORK_NOTE_OBJECT = 831200519;
  public final static int ACTION_ITEM_WORK_SELECTION_OBJECT = 831200520;
  public final static int ACTION_ITEM_WORK_RELATIONSHIP_OBJECT = 110061020;
  // fields
  protected int id = -1;
  protected String name = null;
  protected String description = null;
  protected boolean enabled = false;
  protected Timestamp approved = null;
  protected Timestamp archiveDate = null;
  private int catCode = 0;
  private int subCat1 = 0;
  private int subCat2 = 0;
  private int subCat3 = 0;
  private int linkObjectId = -1;
  protected int siteId = -1;
  // record status
  protected Timestamp entered = null;
  protected int enteredBy = -1;
  protected Timestamp modified = null;
  protected int modifiedBy = -1;
  //other related records
  protected boolean buildPhases = false;
  protected boolean buildSteps = false;
  protected boolean justApproved = false;
  protected boolean justDisapproved = false;
  protected ActionPhaseList phases = new ActionPhaseList();
  protected ActionPlanWorkList planWorks = null;
  protected boolean buildRelatedRecords = false;
  protected int owner = -1;
  protected String siteName = null;


  /**
   *  Constructor for the ActionPlan object
   */
  public ActionPlan() { }


  /**
   *  Constructor for the ActionPlan object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionPlan(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionPlan object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionPlan(Connection db, int id) throws SQLException {
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
        " SELECT ap.*, ls.description as site_name " +
        " FROM action_plan ap " +
        " LEFT JOIN action_plan_constants apc ON (ap.link_object_id = apc.map_id) " +
        " LEFT JOIN lookup_site_id ls ON (ap.site_id = ls.code) " +
        " WHERE ap.plan_id = ? ");
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
    if (buildPhases || buildSteps) {
      buildPhases(db);
    }
    if (buildRelatedRecords) {
      buildRelatedRecords(db, owner);
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
    this.setId(rs.getInt("plan_id"));
    name = rs.getString("plan_name");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
    approved = rs.getTimestamp("approved");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    archiveDate = rs.getTimestamp("archive_date");
    catCode = DatabaseUtils.getInt(rs, "cat_code", 0);
    subCat1 = DatabaseUtils.getInt(rs, "subcat_code1", 0);
    subCat2 = DatabaseUtils.getInt(rs, "subcat_code2", 0);
    subCat3 = DatabaseUtils.getInt(rs, "subcat_code3", 0);
    linkObjectId = DatabaseUtils.getInt(rs, "link_object_id");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    //lookup_site_id table
    siteName = rs.getString("site_name");
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
      id = DatabaseUtils.getNextSeq(db, "action_plan_plan_id_seq");
      sql.append(
          "INSERT INTO action_plan (plan_name, description, enabled, cat_code, " +
          "subcat_code1, subcat_code2, subcat_code3, link_object_id, site_id, ");
      if (id > -1) {
        sql.append("plan_id, ");
      }
      if (this.getJustApproved() || this.getApproved() != null) {
        sql.append("approved, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      sql.append("enteredby, ");
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("modifiedby )");
      sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (this.getApproved() != null) {
        sql.append("?, ");
      } else if (this.getJustApproved()) {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      sql.append("?, ");
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      pst.setBoolean(++i, this.getEnabled());
      if (this.getCatCode() > 0) {
        pst.setInt(++i, this.getCatCode());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getSubCat1() > 0) {
        pst.setInt(++i, this.getSubCat1());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getSubCat2() > 0) {
        pst.setInt(++i, this.getSubCat2());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getSubCat3() > 0) {
        pst.setInt(++i, this.getSubCat3());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      DatabaseUtils.setInt(pst, ++i, this.getLinkObjectId());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (this.getApproved() != null) {
        DatabaseUtils.setTimestamp(pst, ++i, this.getApproved());
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setInt(++i, this.getEnteredBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "action_plan_plan_id_seq", id);
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
        "UPDATE action_plan " +
        " SET plan_name = ?, " +
        " description = ?, " +
        " cat_code = ?, " +
        "subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
        " link_object_id = ?, site_id = ?, ");
    if (this.getEnabled()) {
      sql.append(" enabled = ?, archive_date = null, ");
    } else {
      if (archiveDate != null) {
        sql.append(" enabled = ?, archive_date = ?, ");
      } else {
        sql.append(" enabled = ?, archive_date = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
    }
    if (this.getJustDisapproved()) {
      sql.append(" approved = null, ");
    } else if (this.getJustApproved()) {
      sql.append(" approved = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    } else if (this.getApproved() != null) {
      sql.append(" approved = ?, ");
    }
    sql.append(
        " modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        " modifiedby = ? " +
        " WHERE plan_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    if (this.getCatCode() > 0) {
      pst.setInt(++i, this.getCatCode());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSubCat1() > 0) {
      pst.setInt(++i, this.getSubCat1());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSubCat2() > 0) {
      pst.setInt(++i, this.getSubCat2());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSubCat3() > 0) {
      pst.setInt(++i, this.getSubCat3());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    DatabaseUtils.setInt(pst, ++i, this.getLinkObjectId());
    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
    if (this.getEnabled()) {
      pst.setBoolean(++i, true);
    } else {
      if (archiveDate != null) {
        pst.setBoolean(++i, false);
        pst.setTimestamp(++i, this.getArchiveDate());
      } else {
        pst.setBoolean(++i, false);
      }
    }
    if (!this.getJustDisapproved() && !this.getJustApproved() && this.getApproved() != null) {
      DatabaseUtils.setTimestamp(pst, ++i, this.getApproved());
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    //Build and delete phases
    this.buildPhases(db);
    phases.delete(db);

    //Delete the mapped plan
    if (linkObjectId == this.getMapIdGivenConstantId(db, ActionPlan.TICKETS)) {
      TicketCategoryDraftPlanMapList draftTicketPlanMaps = new TicketCategoryDraftPlanMapList();
      draftTicketPlanMaps.setPlanId(this.getId());
      draftTicketPlanMaps.buildList(db);
      draftTicketPlanMaps.parsePlans(db, new HashMap());
      
      TicketCategoryPlanMapList ticketPlanMaps = new TicketCategoryPlanMapList();
      ticketPlanMaps.setPlanId(this.getId());
      ticketPlanMaps.buildList(db);
      ticketPlanMaps.delete(db);
    }
    // Delete the phase
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM action_plan WHERE plan_id = ? ");
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
  public int updatePhases(Connection db) throws SQLException {
    int result = -1;
    Iterator iterator = (Iterator) phases.iterator();
    while (iterator.hasNext()) {
      ActionPhase phase = (ActionPhase) iterator.next();
      result += phase.update(db);
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPhases(Connection db) throws SQLException {
    //This needs to be uncommented on creating the ActionPhaseList class
    phases = new ActionPhaseList();
    phases.setPlanId(this.getId());
    phases.setBuildSteps(this.getBuildSteps());
    phases.buildList(db);
    phases = phases.reorder();
  }


  /**
   *  Gets the timeZoneParams attribute of the ActionPlan class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("archiveDate");
    thisList.add("entered");
    thisList.add("modified");
    return thisList;
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
      throw new SQLException("Plan ID not specified");
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
          " FROM action_plan_work " +
          " WHERE action_plan_id = ?");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("actionPlanRecords");
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

    // Check for phases in the plan
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) as recordcount " +
          " FROM action_phase " +
          " WHERE plan_id = ?");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("actionPhases");
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
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  owner             Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRelatedRecords(Connection db, int owner) throws SQLException {
    this.planWorks = new ActionPlanWorkList();
    planWorks.setActionPlanId(this.getId());
    planWorks.setSiteId(this.getSiteId());
    planWorks.setIncludeAllSites(true);
    planWorks.setOwner(owner);
    planWorks.buildList(db);
  }


  /**
   *  Gets the mapIdGivenConstantId attribute of the ActionPlan class
   *
   * @param  db                Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   The mapIdGivenConstantId value
   * @exception  SQLException  Description of the Exception
   */
  public static int getMapIdGivenConstantId(Connection db, int constantId) throws SQLException {
    int mapId = -1;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement("SELECT map_id FROM action_plan_constants WHERE constant_id = ? ");
    pst.setInt(1, constantId);
    rs = pst.executeQuery();
    if (rs.next()) {
      mapId = rs.getInt("map_id");
    }
    rs.close();
    pst.close();
    
    return mapId;
  }


  /**
   *  Gets the descriptionGivenConstantId attribute of the ActionPlan class
   *
   * @param  db                Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   The descriptionGivenConstantId value
   * @exception  SQLException  Description of the Exception
   */
  public static String getDescriptionGivenConstantId(Connection db, int constantId) throws SQLException {
    String constantDescription = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement("SELECT description FROM action_plan_constants WHERE constant_id = ? ");
    pst.setInt(1, constantId);
    rs = pst.executeQuery();
    if (rs.next()) {
      constantDescription = rs.getString("description");
    }
    rs.close();
    pst.close();
    return constantDescription;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static HashMap buildConstants(Connection db) throws SQLException {
    HashMap constants = new HashMap();
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement("SELECT constant_id, description FROM action_plan_constants ");
    rs = pst.executeQuery();
    while (rs.next()) {
      constants.put(new Integer(rs.getInt("constant_id")), rs.getString("description"));
    }
    rs.close();
    pst.close();
    return constants;
  }


  /**
   *  Insert a constant into the action plan constants table. This should
   *  accompany the declaration of the static final constant in the ActionPlan
   *  class.
   *
   * @param  db                Description of the Parameter
   * @param  constant          Description of the Parameter
   * @param  description       Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static boolean insertConstant(Connection db, int constant, String description) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement("INSERT INTO action_plan_constants (constant_id,description) VALUES( ?, ?) ");
    pst.setInt(1, constant);
    pst.setString(2, description);
    pst.execute();
    pst.close();
    
    return true;
  }


  /**
   *  The action plan could start with steps that do not have any status and
   *  hence can be skipped. This method determines the current phase for the
   *  plan to start by iterating through its phases
   *
   * @return    The currentPhase value
   */
  public ActionPhase getCurrentPhase() {
    Iterator i = phases.iterator();
    while (i.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) i.next();
      if (!thisPhase.getGlobal()) {
        Iterator j = thisPhase.getSteps().iterator();
        while (j.hasNext()) {
          ActionStep thisStep = (ActionStep) j.next();
          if (thisStep.allowsUpdate()) {
            return thisPhase;
          }
        }
      }
    }
    return null;
  }


  /**
   *  The action plan could start with steps that do not have any status and
   *  hence can be skipped. This method determines the current step for the
   *  plan to start by iterating through its phases and steps
   *
   * @return    The currentStep value
   */
  public ActionStep getCurrentStep() {
    Iterator i = phases.iterator();
    while (i.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) i.next();
      if (!thisPhase.getGlobal()) {
        Iterator j = thisPhase.getSteps().iterator();
        while (j.hasNext()) {
          ActionStep thisStep = (ActionStep) j.next();
          if (thisStep.allowsUpdate()) {
            return thisStep;
          }
        }
      }
    }
    return null;
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the ActionPlan object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionPlan object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionPlan object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the name attribute of the ActionPlan object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the ActionPlan object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the description attribute of the ActionPlan object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the ActionPlan object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the enabled attribute of the ActionPlan object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the ActionPlan object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ActionPlan object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the approved attribute of the ActionPlan object
   *
   * @return    The approved value
   */
  public Timestamp getApproved() {
    return approved;
  }


  /**
   *  Sets the approved attribute of the ActionPlan object
   *
   * @param  tmp  The new approved value
   */
  public void setApproved(Timestamp tmp) {
    this.approved = tmp;
  }


  /**
   *  Sets the approved attribute of the ActionPlan object
   *
   * @param  tmp  The new approved value
   */
  public void setApproved(String tmp) {
    this.approved = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the entered attribute of the ActionPlan object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionPlan object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionPlan object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the ActionPlan object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPlan object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPlan object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modified attribute of the ActionPlan object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the ActionPlan object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ActionPlan object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the ActionPlan object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPlan object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPlan object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildPhases attribute of the ActionPlan object
   *
   * @return    The buildPhases value
   */
  public boolean getBuildPhases() {
    return buildPhases;
  }


  /**
   *  Sets the buildPhases attribute of the ActionPlan object
   *
   * @param  tmp  The new buildPhases value
   */
  public void setBuildPhases(boolean tmp) {
    this.buildPhases = tmp;
  }


  /**
   *  Sets the buildPhases attribute of the ActionPlan object
   *
   * @param  tmp  The new buildPhases value
   */
  public void setBuildPhases(String tmp) {
    this.buildPhases = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildSteps attribute of the ActionPlan object
   *
   * @return    The buildSteps value
   */
  public boolean getBuildSteps() {
    return buildSteps;
  }


  /**
   *  Sets the buildSteps attribute of the ActionPlan object
   *
   * @param  tmp  The new buildSteps value
   */
  public void setBuildSteps(boolean tmp) {
    this.buildSteps = tmp;
  }


  /**
   *  Sets the buildSteps attribute of the ActionPlan object
   *
   * @param  tmp  The new buildSteps value
   */
  public void setBuildSteps(String tmp) {
    this.buildSteps = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the justApproved attribute of the ActionPlan object
   *
   * @return    The justApproved value
   */
  public boolean getJustApproved() {
    return justApproved;
  }


  /**
   *  Sets the justApproved attribute of the ActionPlan object
   *
   * @param  tmp  The new justApproved value
   */
  public void setJustApproved(boolean tmp) {
    this.justApproved = tmp;
  }


  /**
   *  Sets the justApproved attribute of the ActionPlan object
   *
   * @param  tmp  The new justApproved value
   */
  public void setJustApproved(String tmp) {
    this.justApproved = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the justDisapproved attribute of the ActionPlan object
   *
   * @return    The justDisapproved value
   */
  public boolean getJustDisapproved() {
    return justDisapproved;
  }


  /**
   *  Sets the justDisapproved attribute of the ActionPlan object
   *
   * @param  tmp  The new justDisapproved value
   */
  public void setJustDisapproved(boolean tmp) {
    this.justDisapproved = tmp;
  }


  /**
   *  Sets the justDisapproved attribute of the ActionPlan object
   *
   * @param  tmp  The new justDisapproved value
   */
  public void setJustDisapproved(String tmp) {
    this.justDisapproved = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the phases attribute of the ActionPlan object
   *
   * @return    The phases value
   */
  public ActionPhaseList getPhases() {
    return phases;
  }


  /**
   *  Sets the phases attribute of the ActionPlan object
   *
   * @param  tmp  The new phases value
   */
  public void setPhases(ActionPhaseList tmp) {
    this.phases = tmp;
  }


  /**
   *  Gets the archiveDate attribute of the ActionPlan object
   *
   * @return    The archiveDate value
   */
  public Timestamp getArchiveDate() {
    return archiveDate;
  }


  /**
   *  Sets the archiveDate attribute of the ActionPlan object
   *
   * @param  tmp  The new archiveDate value
   */
  public void setArchiveDate(Timestamp tmp) {
    this.archiveDate = tmp;
  }


  /**
   *  Sets the archiveDate attribute of the ActionPlan object
   *
   * @param  tmp  The new archiveDate value
   */
  public void setArchiveDate(String tmp) {
    this.archiveDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the planWorks attribute of the ActionPlan object
   *
   * @return    The planWorks value
   */
  public ActionPlanWorkList getPlanWorks() {
    return planWorks;
  }


  /**
   *  Sets the planWorks attribute of the ActionPlan object
   *
   * @param  tmp  The new planWorks value
   */
  public void setPlanWorks(ActionPlanWorkList tmp) {
    this.planWorks = tmp;
  }


  /**
   *  Gets the buildRelatedRecords attribute of the ActionPlan object
   *
   * @return    The buildRelatedRecords value
   */
  public boolean getBuildRelatedRecords() {
    return buildRelatedRecords;
  }


  /**
   *  Sets the buildRelatedRecords attribute of the ActionPlan object
   *
   * @param  tmp  The new buildRelatedRecords value
   */
  public void setBuildRelatedRecords(boolean tmp) {
    this.buildRelatedRecords = tmp;
  }


  /**
   *  Sets the buildRelatedRecords attribute of the ActionPlan object
   *
   * @param  tmp  The new buildRelatedRecords value
   */
  public void setBuildRelatedRecords(String tmp) {
    this.buildRelatedRecords = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the owner attribute of the ActionPlan object
   *
   * @return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Sets the owner attribute of the ActionPlan object
   *
   * @param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the owner attribute of the ActionPlan object
   *
   * @param  tmp  The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Gets the catCode attribute of the ActionPlan object
   *
   * @return    The catCode value
   */
  public int getCatCode() {
    return catCode;
  }


  /**
   *  Sets the catCode attribute of the ActionPlan object
   *
   * @param  tmp  The new catCode value
   */
  public void setCatCode(int tmp) {
    this.catCode = tmp;
  }


  /**
   *  Sets the catCode attribute of the ActionPlan object
   *
   * @param  tmp  The new catCode value
   */
  public void setCatCode(String tmp) {
    this.catCode = Integer.parseInt(tmp);
  }


  /**
   *  Gets the subCat1 attribute of the ActionPlan object
   *
   * @return    The subCat1 value
   */
  public int getSubCat1() {
    return subCat1;
  }


  /**
   *  Sets the subCat1 attribute of the ActionPlan object
   *
   * @param  tmp  The new subCat1 value
   */
  public void setSubCat1(int tmp) {
    this.subCat1 = tmp;
  }


  /**
   *  Sets the subCat1 attribute of the ActionPlan object
   *
   * @param  tmp  The new subCat1 value
   */
  public void setSubCat1(String tmp) {
    this.subCat1 = Integer.parseInt(tmp);
  }


  /**
   *  Gets the subCat2 attribute of the ActionPlan object
   *
   * @return    The subCat2 value
   */
  public int getSubCat2() {
    return subCat2;
  }


  /**
   *  Sets the subCat2 attribute of the ActionPlan object
   *
   * @param  tmp  The new subCat2 value
   */
  public void setSubCat2(int tmp) {
    this.subCat2 = tmp;
  }


  /**
   *  Sets the subCat2 attribute of the ActionPlan object
   *
   * @param  tmp  The new subCat2 value
   */
  public void setSubCat2(String tmp) {
    this.subCat2 = Integer.parseInt(tmp);
  }


  /**
   *  Gets the subCat3 attribute of the ActionPlan object
   *
   * @return    The subCat3 value
   */
  public int getSubCat3() {
    return subCat3;
  }


  /**
   *  Sets the subCat3 attribute of the ActionPlan object
   *
   * @param  tmp  The new subCat3 value
   */
  public void setSubCat3(int tmp) {
    this.subCat3 = tmp;
  }


  /**
   *  Sets the subCat3 attribute of the ActionPlan object
   *
   * @param  tmp  The new subCat3 value
   */
  public void setSubCat3(String tmp) {
    this.subCat3 = Integer.parseInt(tmp);
  }


  /**
   *  Gets the linkObjectId attribute of the ActionPlan object
   *
   * @return    The linkObjectId value
   */
  public int getLinkObjectId() {
    return linkObjectId;
  }


  /**
   *  Sets the linkObjectId attribute of the ActionPlan object
   *
   * @param  tmp  The new linkObjectId value
   */
  public void setLinkObjectId(int tmp) {
    this.linkObjectId = tmp;
  }


  /**
   *  Sets the linkObjectId attribute of the ActionPlan object
   *
   * @param  tmp  The new linkObjectId value
   */
  public void setLinkObjectId(String tmp) {
    this.linkObjectId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the siteId attribute of the ActionPlan object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the ActionPlan object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the ActionPlan object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the siteName attribute of the ActionPlan object
   *
   * @return    The siteName value
   */
  public String getSiteName() {
    return siteName;
  }


  /**
   *  Sets the siteName attribute of the ActionPlan object
   *
   * @param  tmp  The new siteName value
   */
  public void setSiteName(String tmp) {
    this.siteName = tmp;
  }
  /**
   *  Gets the DisplayInPlanSteps  attribute of the ActionPlanWork object
   *
   * @return    The DisplayInPlanSteps value
   */
  public String getDisplayInPlanSteps() {
    String result = "";
    Iterator actionPlanPhases = phases.iterator();
    while (actionPlanPhases.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) actionPlanPhases.next();
      Iterator steps = thisPhase.getSteps().iterator();
      while (steps.hasNext()) {
    	ActionStep step = (ActionStep) steps.next();
        if (step!=null && step.getDisplayInPlanList()){
          if (steps.hasNext()) {        	
            result+=step.getPlanListLabel()+ "\n";
          }else{
            result+=step.getPlanListLabel();  
          }	
        }
      }
			if (actionPlanPhases.hasNext()){
				result+="\n";  
			}
    }
    return result;
  }
}

