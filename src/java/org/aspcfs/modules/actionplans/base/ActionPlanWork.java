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

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.web.HtmlSelect;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 17, 2005
 * @version    $Id: ActionPlanWork.java 14105 2006-01-31 16:59:06 -0500 (Tue, 31
 *      Jan 2006) ananth $
 */
public class ActionPlanWork extends GenericBean {
  //Status ids
  public final static int INCOMPLETE = -1;
  public final static int UNDEFINED = 0;
  public final static int SKIPPED = 1;
  public final static int COMPLETED = 2;

  private int id = -1;
  private int actionPlanId = -1;
  private int managerId = -1;
  private int assignedTo = -1;

  private int linkModuleId = -1;
  private int linkItemId = -1;
  private String linkItemName = null;
  private boolean enabled = true;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int currentPhaseId = -1;

  //Action Plan
  private String planName = null;
  private String planDescription = null;
  private int planSiteId = -1;
  //resources
  private boolean buildPhaseWork = false;
  private boolean buildStepWork = false;
  private boolean buildLinkedObject = false;
  private int buildGlobalPhases = Constants.UNDEFINED;
  private ActionPhaseWorkList phaseWorkList = null;
  private Contact contact = null;
  private Organization organization = null;
  private Ticket ticket = null;
  private boolean buildCurrentPhaseWork = false;
  private boolean buildCurrentStepWork = false;


  /**
   *  Gets the buildGlobalPhases attribute of the ActionPlanWork object
   *
   * @return    The buildGlobalPhases value
   */
  public int getBuildGlobalPhases() {
    return buildGlobalPhases;
  }


  /**
   *  Sets the buildGlobalPhases attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildGlobalPhases value
   */
  public void setBuildGlobalPhases(int tmp) {
    this.buildGlobalPhases = tmp;
  }


  /**
   *  Sets the buildGlobalPhases attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildGlobalPhases value
   */
  public void setBuildGlobalPhases(String tmp) {
    this.buildGlobalPhases = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planSiteId attribute of the ActionPlanWork object
   *
   * @return    The planSiteId value
   */
  public int getPlanSiteId() {
    return planSiteId;
  }


  /**
   *  Sets the planSiteId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new planSiteId value
   */
  public void setPlanSiteId(int tmp) {
    this.planSiteId = tmp;
  }


  /**
   *  Sets the planSiteId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new planSiteId value
   */
  public void setPlanSiteId(String tmp) {
    this.planSiteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the currentPhaseId attribute of the ActionPlanWork object
   *
   * @return    The currentPhaseId value
   */
  public int getCurrentPhaseId() {
    return currentPhaseId;
  }


  /**
   *  Sets the currentPhaseId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new currentPhaseId value
   */
  public void setCurrentPhaseId(int tmp) {
    this.currentPhaseId = tmp;
  }


  /**
   *  Sets the currentPhaseId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new currentPhaseId value
   */
  public void setCurrentPhaseId(String tmp) {
    this.currentPhaseId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the organization attribute of the ActionPlanWork object
   *
   * @return    The organization value
   */
  public Organization getOrganization() {
    return organization;
  }


  /**
   *  Sets the organization attribute of the ActionPlanWork object
   *
   * @param  tmp  The new organization value
   */
  public void setOrganization(Organization tmp) {
    this.organization = tmp;
  }


  /**
   *  Gets the buildLinkedObject attribute of the ActionPlanWork object
   *
   * @return    The buildLinkedObject value
   */
  public boolean getBuildLinkedObject() {
    return buildLinkedObject;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(boolean tmp) {
    this.buildLinkedObject = tmp;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(String tmp) {
    this.buildLinkedObject = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the enabled attribute of the ActionPlanWork object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the ActionPlanWork object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ActionPlanWork object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the contact attribute of the ActionPlanWork object
   *
   * @return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Sets the contact attribute of the ActionPlanWork object
   *
   * @param  tmp  The new contact value
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   *  Gets the phaseWorkList attribute of the ActionPlanWork object
   *
   * @return    The phaseWorkList value
   */
  public ActionPhaseWorkList getPhaseWorkList() {
    return phaseWorkList;
  }


  /**
   *  Sets the phaseWorkList attribute of the ActionPlanWork object
   *
   * @param  tmp  The new phaseWorkList value
   */
  public void setPhaseWorkList(ActionPhaseWorkList tmp) {
    this.phaseWorkList = tmp;
  }


  /**
   *  Gets the buildPhaseWork attribute of the ActionPlanWork object
   *
   * @return    The buildPhaseWork value
   */
  public boolean getBuildPhaseWork() {
    return buildPhaseWork;
  }


  /**
   *  Sets the buildPhaseWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildPhaseWork value
   */
  public void setBuildPhaseWork(boolean tmp) {
    this.buildPhaseWork = tmp;
  }


  /**
   *  Sets the buildPhaseWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildPhaseWork value
   */
  public void setBuildPhaseWork(String tmp) {
    this.buildPhaseWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildStepWork attribute of the ActionPlanWork object
   *
   * @return    The buildStepWork value
   */
  public boolean getBuildStepWork() {
    return buildStepWork;
  }


  /**
   *  Sets the buildStepWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildStepWork value
   */
  public void setBuildStepWork(boolean tmp) {
    this.buildStepWork = tmp;
  }


  /**
   *  Sets the buildStepWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildStepWork value
   */
  public void setBuildStepWork(String tmp) {
    this.buildStepWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the planName attribute of the ActionPlanWork object
   *
   * @return    The planName value
   */
  public String getPlanName() {
    return planName;
  }


  /**
   *  Sets the planName attribute of the ActionPlanWork object
   *
   * @param  tmp  The new planName value
   */
  public void setPlanName(String tmp) {
    this.planName = tmp;
  }


  /**
   *  Gets the planDescription attribute of the ActionPlanWork object
   *
   * @return    The planDescription value
   */
  public String getPlanDescription() {
    return planDescription;
  }


  /**
   *  Sets the planDescription attribute of the ActionPlanWork object
   *
   * @param  tmp  The new planDescription value
   */
  public void setPlanDescription(String tmp) {
    this.planDescription = tmp;
  }


  /**
   *  Gets the id attribute of the ActionPlanWork object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionPlanWork object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionPlanWork object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the actionPlanId attribute of the ActionPlanWork object
   *
   * @return    The actionPlanId value
   */
  public int getActionPlanId() {
    return actionPlanId;
  }


  /**
   *  Sets the actionPlanId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new actionPlanId value
   */
  public void setActionPlanId(int tmp) {
    this.actionPlanId = tmp;
  }


  /**
   *  Sets the actionPlanId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new actionPlanId value
   */
  public void setActionPlanId(String tmp) {
    this.actionPlanId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the managerId attribute of the ActionPlanWork object
   *
   * @return    The managerId value
   */
  public int getManagerId() {
    return managerId;
  }


  /**
   *  Sets the managerId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new managerId value
   */
  public void setManagerId(int tmp) {
    this.managerId = tmp;
  }


  /**
   *  Sets the managerId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new managerId value
   */
  public void setManagerId(String tmp) {
    this.managerId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the assignedTo attribute of the ActionPlanWork object
   *
   * @return    The assignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Sets the assignedTo attribute of the ActionPlanWork object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(int tmp) {
    this.assignedTo = tmp;
  }


  /**
   *  Sets the assignedTo attribute of the ActionPlanWork object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(String tmp) {
    this.assignedTo = Integer.parseInt(tmp);
  }


  /**
   *  Gets the linkModuleId attribute of the ActionPlanWork object
   *
   * @return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Sets the linkModuleId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the linkItemId attribute of the ActionPlanWork object
   *
   * @return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   *  Sets the linkItemId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }
 
  /**
   *  Gets the linkItemName attribute of the ActionPlanWork object
   *
   * @return    The linkItemName value
   */
  public String getLinkItemName() {
    return linkItemName;
  }


  /**
   *  Sets the linkItemName attribute of the ActionPlanWork object
   *
   * @param  tmp  The new linkItemName value
   */
  public void setLinkItemName(String tmp) {
    this.linkItemName = tmp;
  }
  /**
   *  Sets the linkItemId attribute of the ActionPlanWork object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the entered attribute of the ActionPlanWork object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionPlanWork object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionPlanWork object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modified attribute of the ActionPlanWork object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the ActionPlanWork object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ActionPlanWork object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the ActionPlanWork object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPlanWork object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ActionPlanWork object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the ActionPlanWork object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPlanWork object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionPlanWork object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the ticket attribute of the ActionPlanWork object
   *
   * @return    The ticket value
   */
  public Ticket getTicket() {
    return ticket;
  }


  /**
   *  Sets the ticket attribute of the ActionPlanWork object
   *
   * @param  tmp  The new ticket value
   */
  public void setTicket(Ticket tmp) {
    this.ticket = tmp;
  }


  /**
   *  Gets the buildCurrentPhaseWork attribute of the ActionPlanWork object
   *
   * @return    The buildCurrentPhaseWork value
   */
  public boolean getBuildCurrentPhaseWork() {
    return buildCurrentPhaseWork;
  }


  /**
   *  Sets the buildCurrentPhaseWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildCurrentPhaseWork value
   */
  public void setBuildCurrentPhaseWork(boolean tmp) {
    this.buildCurrentPhaseWork = tmp;
  }


  /**
   *  Sets the buildCurrentPhaseWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildCurrentPhaseWork value
   */
  public void setBuildCurrentPhaseWork(String tmp) {
    this.buildCurrentPhaseWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildCurrentStepWork attribute of the ActionPlanWork object
   *
   * @return    The buildCurrentStepWork value
   */
  public boolean getBuildCurrentStepWork() {
    return buildCurrentStepWork;
  }


  /**
   *  Sets the buildCurrentStepWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildCurrentStepWork value
   */
  public void setBuildCurrentStepWork(boolean tmp) {
    this.buildCurrentStepWork = tmp;
  }


  /**
   *  Sets the buildCurrentStepWork attribute of the ActionPlanWork object
   *
   * @param  tmp  The new buildCurrentStepWork value
   */
  public void setBuildCurrentStepWork(String tmp) {
    this.buildCurrentStepWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the daysActive attribute of the ActionPlanWork object
   *
   * @return    The daysActive value
   */
  public int getDaysActive() {
    //Calculations
    if (entered != null) {
      float ageCheck = (
          (System.currentTimeMillis() - entered.getTime()) / 3600000);
      int totalHours = java.lang.Math.round(ageCheck);
      return java.lang.Math.round(totalHours / 24);
      //ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
    }
    return 0;
  }


  /**
   *  Constructor for the ActionPlanWork object
   */
  public ActionPlanWork() { }


  /**
   *  Constructor for the ActionPlanWork object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionPlanWork(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionPlanWork object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionPlanWork(Connection db, int id) throws SQLException {
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
      throw new SQLException("Invalid Action Plan Work ID");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT apw.*, " +
        "ap.plan_name, ap.description, ap.site_id, " +
        "c.namefirst, c.namelast " +
        "FROM action_plan_work apw " +
        "LEFT JOIN action_plan ap ON (apw.action_plan_id = ap.plan_id) " +
        "LEFT JOIN contact c ON (apw.assignedTo = c.user_id) " +
        "WHERE apw.plan_work_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    //build resources
    if (buildLinkedObject) {
      this.buildLinkedObject(db);
    }

    if (buildPhaseWork) {
      this.buildPhaseWork(db);
    }
    if (buildCurrentPhaseWork) {
      this.buildCurrentPhaseWork(db);
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
  public void buildPhaseWork(Connection db) throws SQLException {
    if (phaseWorkList == null) {
      phaseWorkList = new ActionPhaseWorkList();
    }
    phaseWorkList.setPlanWorkId(id);
    phaseWorkList.setBuildStepWork(buildStepWork);
    phaseWorkList.setGlobal(buildGlobalPhases);
    phaseWorkList.setBuildPhase(true);
    phaseWorkList.setPlanWork(this);
    phaseWorkList.setBuildLinkedObject(buildLinkedObject);
    phaseWorkList.buildList(db);
  }


  public void buildCurrentPhaseWork(Connection db) throws SQLException {
    if (phaseWorkList == null) {
      phaseWorkList = new ActionPhaseWorkList();
    }
    phaseWorkList.setPlanWorkId(id);
    phaseWorkList.setBuildCurrentStepsOnly(buildCurrentStepWork);
    phaseWorkList.setHasWork(true);
    phaseWorkList.setGlobal(buildGlobalPhases);
    phaseWorkList.setBuildPhase(true);
    phaseWorkList.setPlanWork(this);
    phaseWorkList.setBuildLinkedObject(buildLinkedObject);
    phaseWorkList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildLinkedObject(Connection db) throws SQLException {
    if (linkModuleId == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS)) {
      if (contact == null || contact.getId() == -1) {
        contact = new Contact(db, linkItemId);
        linkItemName= contact.getNameFirstLast();
      }
    } else
        if (linkModuleId == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
      if (organization == null || organization.getOrgId() == -1) {
        organization = new Organization(db, linkItemId);
        linkItemName= organization.getName();
      }
    } else
        if (linkModuleId == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS)) {
      if (ticket == null || ticket.getId() == -1) {
        ticket = new Ticket(db, linkItemId);
       
        if (organization == null || organization.getOrgId() == -1) {
          organization = new Organization(db, ticket.getOrgId());
          linkItemName= organization.getName();
        }
        if (contact == null || contact.getId() == -1) {
          contact = new Contact(db, ticket.getContactId());
          linkItemName= contact.getNameFirstLast();
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("plan_work_id");
    actionPlanId = DatabaseUtils.getInt(rs, "action_plan_id");
    managerId = DatabaseUtils.getInt(rs, "manager");
    assignedTo = DatabaseUtils.getInt(rs, "assignedTo");
    linkModuleId = DatabaseUtils.getInt(rs, "link_module_id");
    linkItemId = DatabaseUtils.getInt(rs, "link_item_id");
    enabled = rs.getBoolean("enabled");
    currentPhaseId = DatabaseUtils.getInt(rs, "current_phase");
    //record keeping
    entered = rs.getTimestamp("entered");
    enteredBy = DatabaseUtils.getInt(rs, "enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = DatabaseUtils.getInt(rs, "modifiedby");
    //Action Plan
    planName = rs.getString("plan_name");
    planDescription = rs.getString("description");
    planSiteId = DatabaseUtils.getInt(rs, "site_id");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "action_plan_work_plan_work_id_seq");
    sql.append(
        "INSERT INTO action_plan_work (" +
        (id > -1 ? "plan_work_id, " : "") +
        "action_plan_id, manager, assignedTo, enabled, link_module_id, link_item_id, current_phase, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredby, modifiedby ) ");
    sql.append("VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ? ) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.getActionPlanId());
    DatabaseUtils.setInt(pst, ++i, this.getManagerId());
    pst.setInt(++i, this.getAssignedTo());
    pst.setBoolean(++i, this.getEnabled());
    DatabaseUtils.setInt(pst, ++i, this.getLinkModuleId());
    DatabaseUtils.setInt(pst, ++i, this.getLinkItemId());
    DatabaseUtils.setInt(pst, ++i, currentPhaseId);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "action_plan_work_plan_work_id_seq", id);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  actionPlan     Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, ActionPlan actionPlan) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //insert the action plan work
      if (actionPlan.getPhases().size() > 0) {
        //Action Phase can have steps that do not have status. Determine the current phase
        //that needs to be started if plan starts with steps that can be skipped.
        ActionPhase currentPhase = actionPlan.getCurrentPhase();
        if (currentPhase != null) {
          this.setCurrentPhaseId(currentPhase.getId());
        }
      }
      this.setPlanName(actionPlan.getName());
      this.insert(db);
      //insert the action phase work
      Iterator i = actionPlan.getPhases().iterator();
      while (i.hasNext()) {
        ActionPhase actionPhase = (ActionPhase) i.next();
        ActionPhaseWork phaseWork = new ActionPhaseWork();
        phaseWork.setPlanWorkId(this.getId());
        phaseWork.setActionPhaseId(actionPhase.getId());
        phaseWork.setAssignedTo(this.getAssignedTo());
        phaseWork.setManagerId(this.getManagerId());
        phaseWork.setEnteredBy(this.getEnteredBy());
        phaseWork.setModifiedBy(this.getModifiedBy());
        if (actionPhase.beforeCurrent(actionPlan)) {
          phaseWork.setStatusId(ActionPlanWork.COMPLETED);
        }
        phaseWork.insert(db, actionPlan, actionPhase);
      }

      if (commit) {
        db.commit();
      }
      result = true;
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException("ActionPlanWork: " + e.getMessage());
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
    sql.append("UPDATE action_plan_work ");
    sql.append("SET manager = ?, assignedTo = ?, current_phase = ?, ");
    sql.append("enabled = ?, modifiedby = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " ");
    sql.append("WHERE plan_work_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, managerId);
    pst.setInt(++i, assignedTo);
    DatabaseUtils.setInt(pst, ++i, currentPhaseId);
    pst.setBoolean(++i, enabled);
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
      //delete action plan work notes
      ActionPlanWorkNoteList notes = new ActionPlanWorkNoteList();
      notes.setPlanWorkId(id);
      notes.buildList(db);
      notes.delete(db);

      //delete action phase work records
      ActionPhaseWorkList phases = new ActionPhaseWorkList();
      phases.setPlanWorkId(id);
      phases.buildList(db);
      this.setBuildLinkedObject(true);
      this.buildLinkedObject(db);
      phases.setPlanWork(this);
      phases.delete(db);

      //delete the action plan work record
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM action_plan_work " +
          "WHERE plan_work_id = ? ");
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
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  rating         Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void updateRating(Connection db, int rating) throws SQLException {
    if (linkModuleId == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
      if (organization != null) {
        organization.setRating(rating);
        organization.update(db);
      }
    } else
        if (linkModuleId == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS)) {
    } else
        if (linkModuleId == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS)) {
      if (organization != null) {
        organization.setRating(rating);
        organization.update(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  userId         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean reassign(Connection db, int userId) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      //build a list of steps in this action plan work
      ActionItemWorkList steps = this.getSteps();
      //update the owner of the steps if the step's current owner is the assignedTo user
      //and permissionType is that of assigned user
      Iterator i = steps.iterator();
      while (i.hasNext()) {
        ActionItemWork itemWork = (ActionItemWork) i.next();
        if (itemWork.getOwnerId() == this.getAssignedTo() &&
            itemWork.getPermissionType() != ActionStep.MANAGER) {
          itemWork.setOwnerId(userId);
          itemWork.reassign(db);
        }
      }
      //update the assignedTo user of this plan to the new user
      this.setAssignedTo(userId);
      this.update(db);

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
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean restart(Connection db) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //reset phases which have been worked on
      ActionPhaseWorkList phases = new ActionPhaseWorkList();
      phases.setPlanWorkId(this.getId());
      phases.setBuildStepWork(true);
      phases.setBuildPhase(true);
      phases.setHasWork(true);
      phases.buildList(db);
      phases.reset(db);
      //reset steps which have been worked on
      Iterator i = phases.iterator();
      while (i.hasNext()) {
        ActionPhaseWork phaseWork = (ActionPhaseWork) i.next();
        ActionItemWorkList steps = phaseWork.getItemWorkList();
        if (steps != null) {
          steps.reset(db);
        }
      }
      //update the topmost phase and topmost step
      if (phases.size() > 0) {
        ActionPhaseWork currentPhase = (ActionPhaseWork) phases.get(0);
        currentPhase.setStartDate(
            new java.sql.Timestamp(
            new java.util.Date().getTime()));
        currentPhase.setEndDate((Timestamp) null);
        currentPhase.setStatusId(INCOMPLETE);
        currentPhase.update(db);
        currentPhase.buildPhaseObject(db);

        ActionItemWorkList steps = currentPhase.getItemWorkList();
        if (steps != null && steps.size() > 0) {
          String nullStr = null;
          if (currentPhase.getPhase().getRandom()) {
            Iterator iter = steps.iterator();
            while (iter.hasNext()) {
              ActionItemWork currentStep = (ActionItemWork) iter.next();
              currentStep.setStartDate(
                  new java.sql.Timestamp(
                  new java.util.Date().getTime()));
              currentStep.setStatusId(INCOMPLETE);
              currentStep.setEndDate(nullStr);
              currentStep.update(db);
            }
          } else {
            ActionItemWork currentStep = (ActionItemWork) steps.get(0);
            currentStep.setStartDate(
                new java.sql.Timestamp(
                new java.util.Date().getTime()));
            currentStep.setStatusId(INCOMPLETE);
            currentStep.setEndDate(nullStr);
            currentStep.update(db);
          }
        }
        this.setCurrentPhaseId(currentPhase.getActionPhaseId());
      }
      //update this plan
      this.update(db);
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
   *  Iterates through all the phases and determines the current phase in the
   *  Action Plan
   *
   * @return    The currentPhase value
   */
  public ActionPhaseWork getCurrentPhase() {
    //Check if the root phase is the current one
    if (phaseWorkList != null) {
      ActionPhaseWork topPhase = phaseWorkList.getRootPhase();
      if (topPhase != null && topPhase.isCurrent()) {
        return topPhase;
      }

      Iterator phase = phaseWorkList.iterator();
      while (phase.hasNext()) {
        ActionPhaseWork phaseWork = (ActionPhaseWork) phase.next();
        if (phaseWork.isCurrent()) {
          return phaseWork;
        }
      }
    }
    return null;
  }


  /**
   *  Gets the currentStep attribute of the ActionPlanWork object
   *
   * @return    The currentStep value
   */
  public ActionItemWork getCurrentStep() {
    ActionPhaseWork currentPhase = getCurrentPhase();
    if (currentPhase != null) {
      return currentPhase.getCurrentStep();
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  userId  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean requiresAction(int userId) {
    ActionItemWorkList pendingSteps = this.getPendingSteps();
    Iterator i = pendingSteps.iterator();
    while (i.hasNext()) {
      ActionItemWork pendingStep = (ActionItemWork) i.next();
      if (pendingStep.getOwnerId() == userId) {
        return true;
      }
    }
    return false;
  }


  /**
   *  Gets the statusSelect attribute of the ActionPlanWork object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The statusSelect value
   */
  public static HtmlSelect getStatusSelect(String selectName, int defaultKey) {
    HtmlSelect statusSelect = new HtmlSelect();
    statusSelect.setSelectName(selectName);
    statusSelect.addItem(COMPLETED, "Completed");
    statusSelect.addItem(SKIPPED, "Skipped");
    statusSelect.setDefaultKey(defaultKey);
    statusSelect.build();
    return statusSelect;
  }


  /**
   *  Gets the actionPlanWork attribute of the ActionPlanWork class
   *
   * @param  db             Description of the Parameter
   * @param  linkModuleId   Description of the Parameter
   * @param  linkItemId     Description of the Parameter
   * @return                The actionPlanWork value
   * @throws  SQLException  Description of the Exception
   */
  public static ActionPlanWork getActionPlanWork(Connection db, int linkModuleId, int linkItemId) throws SQLException {
    ActionPlanWork actionPlanWork = new ActionPlanWork();
    PreparedStatement pst = db.prepareStatement(
        "SELECT plan_work_id " +
        "FROM action_plan_work " +
        "WHERE link_module_id = ? " +
        "AND link_item_id = ? ");
    DatabaseUtils.setInt(pst, 1, linkModuleId);
    DatabaseUtils.setInt(pst, 2, linkItemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      actionPlanWork = new ActionPlanWork(db, rs.getInt("plan_work_id"));
    }
    pst.close();
    return actionPlanWork;
  }


  /**
   *  Gets the steps attribute of the ActionPlanWork object
   *
   * @return    The steps value
   */
  public ActionItemWorkList getSteps() {
    ActionItemWorkList steps = new ActionItemWorkList();
    Iterator phases = phaseWorkList.iterator();
    while (phases.hasNext()) {
      ActionPhaseWork thisPhase = (ActionPhaseWork) phases.next();
      Iterator items = thisPhase.getItemWorkList().iterator();
      while (items.hasNext()) {
        ActionItemWork thisItem = (ActionItemWork) items.next();
        steps.add(thisItem);
      }
    }
    return steps;
  }


  /**
   *  Gets the pendingSteps attribute of the ActionPlanWork object
   *
   * @return    The pendingSteps value
   */
  public ActionItemWorkList getPendingSteps() {
    ActionItemWorkList steps = new ActionItemWorkList();
    Iterator phases = phaseWorkList.iterator();
    while (phases.hasNext()) {
      ActionPhaseWork thisPhase = (ActionPhaseWork) phases.next();
      Iterator items = thisPhase.getItemWorkList().iterator();
      while (items.hasNext()) {
        ActionItemWork thisItem = (ActionItemWork) items.next();
        if (!thisItem.hasStatus()) {
          steps.add(thisItem);
        }
      }
    }
    return steps;
  }


  /**
   *  Description of the Method
   */
  public void buildStepLinks() {
    Iterator steps1 = (this.getSteps()).iterator();
    Iterator steps2 = (this.getSteps()).iterator();

    //advance steps2 by one step
    if (steps2.hasNext()) {
      steps2.next();
    }

    while (steps1.hasNext()) {
      ActionItemWork thisStep = (ActionItemWork) steps1.next();
      if (steps2.hasNext()) {
        ActionItemWork nextStep = (ActionItemWork) steps2.next();
        thisStep.setNextStep(nextStep);
        thisStep.setHasNext(true);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  actionPlanId   Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static HashMap buildUserPhaseMap(Connection db, int actionPlanId) throws SQLException {
    if (actionPlanId == -1) {
      throw new SQLException("Action Plan ID not specified");
    }
    int userId = -1;

    HashMap userPhase = new HashMap();
    HashMap phaseMap = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT assignedTo, current_phase, count(assignedTo) AS assigned_count " +
        "FROM action_plan_work " +
        "WHERE action_plan_id = ? " +
        "GROUP BY assignedTo, current_phase " +
        "ORDER BY assignedTo, current_phase ");
    pst.setInt(1, actionPlanId);
    ResultSet rs = pst.executeQuery();

    while (rs.next()) {
      if (userId == -1) {
        //retrieve the phase and count information
        int phaseId = DatabaseUtils.getInt(rs, "current_phase");
        if (phaseId != -1) {
          phaseMap.put(new Integer(phaseId), new Integer(rs.getInt("assigned_count")));
        }
      } else {
        //determine if the user has changed
        if (userId != rs.getInt("assignedTo")) {
          //store the phaseMap against the user
          userPhase.put(new Integer(userId), new HashMap(phaseMap));
          phaseMap = new HashMap();
        }
        //retrieve the phase and count information
        int phaseId = DatabaseUtils.getInt(rs, "current_phase");
        if (phaseId != -1) {
          phaseMap.put(new Integer(phaseId), new Integer(rs.getInt("assigned_count")));
        }
      }
      userId = rs.getInt("assignedTo");
    }
    
    pst.close();
    //Handle the last user record
    if (userId != -1) {
      userPhase.put(new Integer(userId), new HashMap(phaseMap));
    }
    return userPhase;
  }


  /**
   *  Description of the Method
   *
   * @param  userPhase  Description of the Parameter
   * @param  user       Description of the Parameter
   * @param  childList  Description of the Parameter
   */
  public static void adjustPhaseCount(HashMap userPhase, ArrayList childList, int user) {
    if (userPhase != null) {
      HashMap userPhases = (HashMap) userPhase.get(new Integer(user));
      Iterator i = childList.iterator();
      while (i.hasNext()) {
        User child = (User) i.next();
        ActionPlanWork.adjustPhaseCount(userPhase, new ArrayList(child.getShortChildList()), child.getId());
        HashMap childPhases = (HashMap) userPhase.get(new Integer(child.getId()));
        if (childPhases != null) {
          Iterator p = childPhases.keySet().iterator();
          if (userPhases == null) {
            userPhases = new HashMap();
            userPhase.put(new Integer(user), userPhases);
          }
          while (p.hasNext()) {
            Integer phase = (Integer) p.next();
            Integer userCount = (Integer) userPhases.get(phase);
            Integer childCount = (Integer) childPhases.get(phase);
            if (childCount != null) {
              if (userCount != null) {
                userPhases.put(phase, new Integer(userCount.intValue() + childCount.intValue()));
              } else {
                userPhases.put(phase, childCount);
              }
            }
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context     Description of the Parameter
   * @param  assigned    Description of the Parameter
   * @param  manager     Description of the Parameter
   * @param  orgName     Description of the Parameter
   * @param  template    Description of the Parameter
   * @return             Description of the Return Value
   * @throws  Exception  Description of the Exception
   */
  public boolean sendEmail(ActionContext context, Contact assigned, Contact manager, String orgName, String template) throws Exception {
    return (sendEmail(context, assigned, manager, null, orgName, template));
  }


  /**
   *  Description of the Method
   *
   * @param  context     Description of the Parameter
   * @param  assigned    Description of the Parameter
   * @param  manager     Description of the Parameter
   * @param  previous    Description of the Parameter
   * @param  orgName     Description of the Parameter
   * @param  template    Description of the Parameter
   * @return             Description of the Return Value
   * @throws  Exception  Description of the Exception
   */
  public boolean sendEmail(ActionContext context, Contact assigned, Contact manager, Contact previous, String orgName, String template) throws Exception {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    ActionPlanEmail planEmail = null;
    if (previous != null && previous.getId() > -1) {
      planEmail = new ActionPlanEmail(template, this, previous, assigned, manager, orgName, context);
    } else {
      planEmail = new ActionPlanEmail(template, this, assigned, manager, orgName, context);
    }
    // Prepare the email
    SMTPMessage mail = new SMTPMessage();
    mail.setHost(prefs.get("MAILSERVER"));
    mail.setFrom(prefs.get("EMAILADDRESS"));
    mail.addReplyTo(prefs.get("EMAILADDRESS"));
    mail.setType("text/html");
    mail.setSubject(planEmail.getSubject());
    mail.setBody(planEmail.getBody());
    if (assigned.getPrimaryEmailAddress() != null && !"".equals(assigned.getPrimaryEmailAddress())) {
      mail.addTo(assigned.getPrimaryEmailAddress());
    }
    if (manager.getPrimaryEmailAddress() != null && !"".equals(manager.getPrimaryEmailAddress())) {
      mail.addTo(manager.getPrimaryEmailAddress());
    }
    if (previous != null && previous.getId() > -1 &&
        previous.getPrimaryEmailAddress() != null &&
        !"".equals(previous.getPrimaryEmailAddress())) {
      mail.addTo(previous.getPrimaryEmailAddress());
    }
    if (mail.send() == 2) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ActionPlanWork -> Send error: " + mail.getErrorMsg() + "\n");
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ActionPlanWork -> Sending message...");
      }
    }
    return true;
  }
}

