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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    August 17, 2005
 * @version    $Id: ActionStep.java,v 1.1.2.17.2.2 2005/09/16 21:12:13 partha
 *      Exp $
 */
public class ActionStep extends GenericBean {
  // Constants
  public final static int USER_DELEGATED = -1;
  public final static int MANAGER = 0;
  public final static int ASSIGNED_USER = 1;
  public final static int ROLE = 2;
  public final static int DEPARTMENT = 3;
  public final static int UP_USER_HIERARCHY = 4;
  public final static int WITHIN_USER_HIERARCHY = 5;
  public final static int ASSIGNED_USER_AND_MANAGER = 6;
  public final static int USER_GROUP = 7;
  public final static int SPECIFIC_USER_GROUP = 8;

  public final static int ATTACH_NOTHING = -1;
  public final static int ATTACH_OPPORTUNITY = 110061030;
  public final static int ATTACH_DOCUMENT = 110061031;
  public final static int ATTACH_ACTIVITY = 110061032;
  public final static int ATTACH_FOLDER = 110061033;
  public final static int UPDATE_RATING = 110061034;
  public final static int ATTACH_NOTE_SINGLE = 110061035;
  public final static int ATTACH_NOTE_MULTIPLE = 110061036;
  public final static int ATTACH_LOOKUPLIST_MULTIPLE = 110061037;
  public final static int VIEW_ACCOUNT = 110061038;
  public final static int ATTACH_ACCOUNT_CONTACT = 110061039;
  public final static int ATTACH_RELATIONSHIP = 110061040;
  public final static int ADD_RECIPIENT = 302061653;

  // fields
  protected int id = -1;
  protected int parentId = -1;
  protected int phaseId = -1;
  protected String description = null;
  protected int actionId = -1;
  protected int durationTypeId = -1;
  protected int estimatedDuration = -1;
  protected int customFieldCategoryId = -1;
  protected int customFieldId = -1;
  protected int permissionType = -1;
  protected int roleId = -1;
  protected int departmentId = -1;
  protected boolean enabled = false;
  protected Timestamp entered = null;
  protected boolean allowSkipToHere = false;
  protected boolean actionRequired = false;
  protected String label = null;
  protected int userGroupId = -1;
  protected String targetRelationship = null;
  protected boolean allowUpdate = true;
  protected int campaignId = -1;
  protected boolean allowDuplicateRecipient = false;
  protected ArrayList accountTypes = null;
  // related records
  protected String durationTypeIdString = null;
  protected ActionStepList stepList = new ActionStepList();
  protected boolean buildCompleteStepList = false;
  protected boolean buildRelatedRecords = false;
  protected ActionItemWorkList actionItems = null;
  protected ActionStepLookupList lookupList = null;
  protected String userGroupName = null;


  /**
   *  Constructor for the ActionStep object
   */
  public ActionStep() { }


  /**
   *  Constructor for the ActionStep object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionStep(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionStep object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionStep(Connection db, int id) throws SQLException {
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
        " SELECT astp.*, " +
        " ldt.description AS duration, ug.group_name as groupname " +
        " FROM action_step astp " +
        " LEFT JOIN lookup_duration_type ldt ON (astp.duration_type_id = ldt.code) " +
        " LEFT JOIN custom_field_category cfc ON (astp.category_id = cfc.category_id) " +
        " LEFT JOIN custom_field_info cfi ON (astp.field_id = cfi.field_id) " +
        " LEFT JOIN \"role\" r ON (astp.role_id = r.role_id) " +
        " LEFT JOIN lookup_department dpt ON (astp.department_id = dpt.code) " +
        " LEFT JOIN user_group ug ON (astp.group_id = ug.group_id) " +
        " WHERE astp.step_id = ? ");
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
    if (this.getBuildRelatedRecords()) {
      buildRelatedRecords(db);
    }

    this.buildAccountTypes(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRelatedRecords(Connection db) throws SQLException {
    actionItems = new ActionItemWorkList();
    actionItems.setActionStepId(this.getId());
    actionItems.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private void buildAccountTypes(Connection db) throws SQLException {
    if (this.id == -1) {
      throw new SQLException("Action Step ID not specified");
    }
    accountTypes = new ArrayList();
    PreparedStatement pst = db.prepareStatement(
        "SELECT type_id FROM action_step_account_types " +
        "WHERE step_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      String type = String.valueOf(rs.getInt("type_id"));
      accountTypes.add(type);
    }
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //action_step table
    this.setId(rs.getInt("step_id"));
    //parentId should be 0 if database value returned null
    parentId = DatabaseUtils.getInt(rs, "parent_id", 0);
    phaseId = DatabaseUtils.getInt(rs, "phase_id");
    description = rs.getString("description");
    actionId = DatabaseUtils.getInt(rs, "action_id");
    durationTypeId = DatabaseUtils.getInt(rs, "duration_type_id");
    estimatedDuration = DatabaseUtils.getInt(rs, "estimated_duration");
    customFieldCategoryId = DatabaseUtils.getInt(rs, "category_id");
    customFieldId = DatabaseUtils.getInt(rs, "field_id");
    permissionType = DatabaseUtils.getInt(rs, "permission_type");
    roleId = DatabaseUtils.getInt(rs, "role_id");
    departmentId = DatabaseUtils.getInt(rs, "department_id");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    allowSkipToHere = rs.getBoolean("allow_skip_to_here");
    actionRequired = rs.getBoolean("action_required");
    label = rs.getString("label");
    userGroupId = DatabaseUtils.getInt(rs, "group_id");
    targetRelationship = rs.getString("target_relationship");
    allowUpdate = rs.getBoolean("allow_update");
    campaignId = DatabaseUtils.getInt(rs, "campaign_id");
    allowDuplicateRecipient = rs.getBoolean("allow_duplicate_recipient");
    // lookup_duration_types table
    durationTypeIdString = rs.getString("duration");
    // user_group table
    userGroupName = rs.getString("groupname");
  }


  /**
   *  Sets the accountTypes attribute of the ActionStep object
   *
   * @param  params  The new accountTypes value
   */
  public void setAccountTypes(String[] params) {
    if (params != null) {
      accountTypes = new ArrayList(Arrays.asList(params));
    } else {
      accountTypes = new ArrayList();
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "action_step_step_id_seq");
      sql.append(
          "INSERT INTO action_step (phase_id, description, " +
          " duration_type_id, estimated_duration, ");
      if (parentId > -1) {
        sql.append("parent_id, ");
      }
      if (actionId > -1) {
        sql.append("action_id, ");
      }
      if (customFieldCategoryId > -1) {
        sql.append("category_id, ");
      }
      if (customFieldId > -1) {
        sql.append("field_id, ");
      }
      if (id > -1) {
        sql.append("step_id, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      sql.append("enabled, permission_type, role_id, department_id, " +
          " allow_skip_to_here, action_required, label, group_id, target_relationship, allow_update, campaign_id, " +
          " allow_duplicate_recipient)");
      sql.append(" VALUES (?, ?, ?, ?, ");
      if (parentId > -1) {
        sql.append("?, ");
      }
      if (actionId > -1) {
        sql.append("?, ");
      }
      if (customFieldCategoryId > -1) {
        sql.append("?, ");
      }
      if (customFieldId > -1) {
        sql.append("?, ");
      }
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getPhaseId());
      pst.setString(++i, this.getDescription());
      DatabaseUtils.setInt(pst, ++i, this.getDurationTypeId());
      DatabaseUtils.setInt(pst, ++i, this.getEstimatedDuration());
      if (parentId > -1) {
        DatabaseUtils.setInt(pst, ++i, this.getParentId());
      }
      if (actionId > -1) {
        DatabaseUtils.setInt(pst, ++i, this.getActionId());
      }
      if (customFieldCategoryId > -1) {
        DatabaseUtils.setInt(pst, ++i, this.getCustomFieldCategoryId());
      }
      if (customFieldId > -1) {
        DatabaseUtils.setInt(pst, ++i, this.getCustomFieldId());
      }
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setBoolean(++i, this.getEnabled());
      DatabaseUtils.setInt(pst, ++i, this.getPermissionType());
      DatabaseUtils.setInt(pst, ++i, this.getRoleId());
      DatabaseUtils.setInt(pst, ++i, this.getDepartmentId());
      pst.setBoolean(++i, allowSkipToHere);
      pst.setBoolean(++i, actionRequired);
      pst.setString(++i, label);
      DatabaseUtils.setInt(pst, ++i, this.getUserGroupId());
      pst.setString(++i, this.getTargetRelationship());
      pst.setBoolean(++i, this.getAllowUpdate());
      DatabaseUtils.setInt(pst, ++i, this.getCampaignId());
      pst.setBoolean(++i, this.getAllowDuplicateRecipient());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "action_step_step_id_seq", id);

      processAccountTypes(db);

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
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

    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      //reset fields based on action type
      if (this.getActionId() != this.ATTACH_RELATIONSHIP) {
        targetRelationship = null;
        accountTypes.clear();
      }

      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE action_step " +
          " SET parent_id = ?, " +
          " phase_id = ?, " +
          " description = ?, " +
          " action_id = ?, " +
          " duration_type_id = ?, " +
          " estimated_duration = ?, " +
          " category_id = ?, " +
          " field_id = ?, " +
          " permission_type = ?, " +
          " role_id = ?, " +
          " department_id = ?, " +
          " group_id = ?, " +
          " target_relationship = ?, " +
          " enabled = ?, " +
          " allow_skip_to_here = ?, " +
          " action_required = ?, " +
          " label = ?, " +
          " allow_update = ?, " +
          " campaign_id = ?, " +
          " allow_duplicate_recipient = ? " +
          " WHERE step_id = ? ");

      int i = 0;
      pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, (this.getParentId() == 0 ? -1 : this.getParentId()));
      DatabaseUtils.setInt(pst, ++i, this.getPhaseId());
      pst.setString(++i, this.getDescription());
      DatabaseUtils.setInt(pst, ++i, this.getActionId());
      DatabaseUtils.setInt(pst, ++i, this.getDurationTypeId());
      DatabaseUtils.setInt(pst, ++i, this.getEstimatedDuration());
      DatabaseUtils.setInt(pst, ++i, this.getCustomFieldCategoryId());
      DatabaseUtils.setInt(pst, ++i, this.getCustomFieldId());
      DatabaseUtils.setInt(pst, ++i, this.getPermissionType());
      DatabaseUtils.setInt(pst, ++i, this.getRoleId());
      DatabaseUtils.setInt(pst, ++i, this.getDepartmentId());
      DatabaseUtils.setInt(pst, ++i, this.getUserGroupId());
      pst.setString(++i, this.getTargetRelationship());
      pst.setBoolean(++i, this.getEnabled());
      pst.setBoolean(++i, allowSkipToHere);
      pst.setBoolean(++i, actionRequired);
      pst.setString(++i, label);
      pst.setBoolean(++i, allowUpdate);
      DatabaseUtils.setInt(pst, ++i, this.getCampaignId());
      pst.setBoolean(++i, this.getAllowDuplicateRecipient());
      DatabaseUtils.setInt(pst, ++i, this.getId());
      resultCount = pst.executeUpdate();
      pst.close();

      processAccountTypes(db);

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void processAccountTypes(Connection db) throws SQLException {
    if (accountTypes != null) {
      resetAccountTypes(db);
      for (int k = 0; k < accountTypes.size(); k++) {
        String val = (String) accountTypes.get(k);
        if (val != null && !(val.equals(""))) {
          int typeId = Integer.parseInt((String) accountTypes.get(k));
          insertAccountType(db, typeId);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean resetAccountTypes(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Action Step ID not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM action_step_account_types WHERE step_id = ? ");
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  typeId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insertAccountType(Connection db, int typeId) throws SQLException {
    if (id == -1) {
      throw new SQLException("Action Step ID not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO action_step_account_types " +
        "(step_id, type_id) " +
        "VALUES (?, ?) ");
    pst.setInt(++i, this.getId());
    pst.setInt(++i, typeId);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    this.queryRecord(db, this.getId());
    //Reset the parentId of the childStep
    int childStepId = ActionStep.getStepIdGivenParentId(db, this.getId());
    ActionStep childStep = null;
    if (childStepId != -1) {
      childStep = new ActionStep();
      childStep.setBuildRelatedRecords(true);
      childStep.queryRecord(db, childStepId);
    }
    if (childStep != null) {
      if (this.getParentId() != -1 && this.getParentId() != 0) {
        childStep.setParentId(this.getParentId());
      } else {
        childStep.setParentId(-1);
      }
      childStep.update(db);
    }

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM action_step_lookup WHERE step_id = ? ");
    pst.setInt(1, this.getId());
    boolean result = pst.execute();
    pst.close();

    pst = db.prepareStatement(
        "DELETE FROM action_step_account_types WHERE step_id = ? ");
    pst.setInt(1, this.getId());
    result = pst.execute();
    pst.close();

    pst = db.prepareStatement(
        "DELETE FROM action_step WHERE step_id = ? ");
    pst.setInt(1, this.getId());
    result = pst.execute();
    pst.close();

    return result;
  }


  /**
   *  Gets the actionString attribute of the ActionStep object
   *
   * @param  actionId  Description of the Parameter
   * @return           The actionString value
   */
  public static String getActionString(int actionId) {
    switch (actionId) {
      case ActionStep.ATTACH_ACCOUNT_CONTACT:
        return "actionPlan.attachAccountContact.text";
      case ActionStep.ATTACH_ACTIVITY:
        return "actionPlan.attachActivity.text";
      case ActionStep.ATTACH_DOCUMENT:
        return "actionPlan.attachDocument.text";
      case ActionStep.ATTACH_OPPORTUNITY:
        return "actionPlan.attachOpportunity.text";
      case ActionStep.ATTACH_FOLDER:
        return "actionPlan.attachFolder.text";
      case ActionStep.ATTACH_NOTHING:
        return "actionPlan.noAdditionalActionRequired.text";
      case UPDATE_RATING:
        return "actionPlan.updateRating.text";
      case ATTACH_NOTE_SINGLE:
        return "actionPlan.attachNoteSingle.text";
      case ATTACH_NOTE_MULTIPLE:
        return "actionPlan.attachNoteMultiple.text";
      case ATTACH_LOOKUPLIST_MULTIPLE:
        return "actionPlan.attachLookupListMultiple.text";
      case VIEW_ACCOUNT:
        return "actionPlan.viewAccount.text";
      case ATTACH_RELATIONSHIP:
        return "actionPlan.attachRelationship.text";
      case ADD_RECIPIENT:
        return "admin.actionPlan.addRecipient.text";
      default:
        return "actionPlan.noAdditionalActionRequired.text";
    }
  }


  /**
   *  Gets the timeZoneParams attribute of the ActionStep class
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
  public void buildStepList(Connection db) throws SQLException {
    stepList = new ActionStepList();
    stepList.setParentId(this.getId());
    stepList.setBuildCompleteStepList(this.getBuildCompleteStepList());
    stepList.buildList(db);
  }


  /**
   *  Sets the afterStepId attribute of the ActionStep object
   *
   * @param  db                    The new afterStepId value
   * @param  previousStepIdString  The new afterStepId value
   * @param  addingStep            The new afterStepId value
   * @return                       Description of the Return Value
   * @exception  SQLException      Description of the Exception
   */
  public boolean setAfterStepId(Connection db, String previousStepIdString, boolean addingStep) throws SQLException {
    //The previousStep is the step that will finally be the parentId of the current Step.
    //The nextStep is the step that will finally be the child of the current step.
    int nextStepId = -1;
    if (previousStepIdString == null || "".equals(previousStepIdString) || "-1".equals(previousStepIdString)) {
      return true;
    }
    ActionStep previousStep = new ActionStep(db, Integer.parseInt(previousStepIdString));
    ActionStep nextStep = null;
    nextStepId = ActionStep.getStepIdGivenParentId(db, previousStep.getId());
    if (nextStepId != -1) {
      nextStep = new ActionStep(db, nextStepId);
    }
    if (!addingStep) {
      //Set the previousStep parent id
      if (this.getParentId() == 0 || this.getParentId() == -1) {
        previousStep.setParentId(-1);
      } else {
        previousStep.setParentId(this.getParentId());
      }
      previousStep.update(db);
    }

    //Set next Step's parent
    if (nextStepId != -1) {
      nextStep.setParentId(this.getId());
      nextStep.update(db);
    }
    //Set the current Step parentId
    this.setParentId(previousStep.getId());
    this.update(db);

    return true;
  }


  /**
   *  Sets the beforeStepId attribute of the ActionStep object
   *
   * @param  db                The new beforeStepId value
   * @param  nextStepIdString  The new beforeStepId value
   * @param  addingStep        The new beforeStepId value
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean setBeforeStepId(Connection db, String nextStepIdString, boolean addingStep) throws SQLException {
    boolean result = false;
    int earlierNextStepId = -1;
    if (nextStepIdString == null || "".equals(nextStepIdString) || "-1".equals(nextStepIdString)) {
      return true;
    }
    int nextStepId = Integer.parseInt(nextStepIdString);
    ActionStep earlierNextStep = null;
    ActionStep nextStep = new ActionStep(db, nextStepId);

    if (!addingStep) {
      earlierNextStepId = ActionStep.getStepIdGivenParentId(db, this.getId());
      if (earlierNextStepId != -1) {
        earlierNextStep = new ActionStep(db, earlierNextStepId);
      }
    }

    //Set the current Step parentId
    if (nextStep.getParentId() == 0 || nextStep.getParentId() == -1) {
      this.setParentId(-1);
    } else {
      this.setParentId(nextStep.getParentId());
    }
    int resultCount = this.update(db);

    //Set the nextStep parent id
    nextStep.setParentId(this.getId());
    nextStep.update(db);

    //Set the previousNextStep parentId
    if (earlierNextStep != null) {
      earlierNextStep.setParentId(nextStep.getId());
      earlierNextStep.update(db);
    }
    return true;
  }


  /**
   *  Gets the stepIdGivenParentId attribute of the ActionStep class
   *
   * @param  db                Description of the Parameter
   * @param  parentId          Description of the Parameter
   * @return                   The stepIdGivenParentId value
   * @exception  SQLException  Description of the Exception
   */
  public static int getStepIdGivenParentId(Connection db, int parentId) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT step_id FROM action_step WHERE parent_id = ?");
    pst.setInt(1, parentId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = DatabaseUtils.getInt(rs, "step_id");
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
      throw new SQLException("Step ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    // Check for records inserted for the step
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          " SELECT count(*) as recordcount " +
          " FROM action_item_work " +
          " WHERE action_step_id = ?");
      pst.setInt(++i, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("actionStepRecords");
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
    return dependencyList;
  }


  /**
   *  Gets the mappedActionLookupList attribute of the ActionStep object
   *
   * @param  db                Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   The mappedActionLookupList value
   * @exception  SQLException  Description of the Exception
   */
  public LookupList getMappedActionLookupList(Connection db, int constantId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    LookupList thisLookupList = new LookupList();
    pst = db.prepareStatement(
        "SELECT lsa.* " +
        "FROM lookup_step_action lsa " +
        "LEFT JOIN step_action_map map ON (lsa.code = map.action_id ) " +
        "WHERE map.constant_id = ? ");
    pst.setInt(1, constantId);
    rs = pst.executeQuery();
    while (rs.next()) {
      LookupElement thisElement = new LookupElement(rs);
      thisLookupList.add(thisElement);
    }
    rs.close();
    pst.close();
    return thisLookupList;
  }


  /**
   *  Gets the requiredActionMappings attribute of the ActionStep class
   *
   * @param  db                Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   The requiredActionMappings value
   * @exception  SQLException  Description of the Exception
   */
  public static HashMap getRequiredActionMappings(Connection db, int constantId) throws SQLException {
    HashMap map = new HashMap();
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement("SELECT action_constant_id, map_id FROM step_action_map WHERE constant_id = ? ");
    pst.setInt(1, constantId);
    rs = pst.executeQuery();
    while (rs.next()) {
      map.put(new Integer(rs.getInt("action_constant_id")), new Integer(rs.getInt("map_id")));
    }
    rs.close();
    pst.close();
    return map;
  }


  /**
   *  Description of the Method
   *
   * @param  context           Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  stepActions       Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static boolean parseRequiredActionMappings(ActionContext context, Connection db, LookupList stepActions, int constantId) throws SQLException {
    HashMap map = ActionStep.getRequiredActionMappings(db, constantId);
    PreparedStatement pst = null;
    Iterator iter = (Iterator) stepActions.iterator();
    while (iter.hasNext()) {
      LookupElement element = (LookupElement) iter.next();
      String currentId = context.getRequest().getParameter("element" + element.getId());
      if (currentId != null && !"".equals(currentId)) {
        if (map.get(new Integer(currentId)) != null) {
          // The mapping exists and hence remove the item from the map to avoid deletion
          map.remove(new Integer(currentId));
        } else {
          // the mapping does not exist. Hence insert it to the step_action_map
          ActionStepMap actionStepMap = new ActionStepMap();
          actionStepMap.setConstantId(constantId);
          actionStepMap.setActionConstantId(element.getId());
          actionStepMap.insert(db);
        }
      }
    }
    // All remaining mappings are not valid anymore. delete the mappings.
    iter = (Iterator) map.keySet().iterator();
    while (iter.hasNext()) {
      Integer actionId = (Integer) iter.next();
      pst = db.prepareStatement("DELETE FROM step_action_map WHERE map_id = ? ");
      pst.setInt(1, ((Integer) map.get(actionId)).intValue());
      pst.execute();
      pst.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  mappedList        Description of the Parameter
   * @param  stepActions       Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static boolean parseRequiredActionMappings(Connection db, HashMap mappedList, LookupList stepActions, int constantId) throws SQLException {
    HashMap map = ActionStep.getRequiredActionMappings(db, constantId);
    PreparedStatement pst = null;
    Iterator iter = (Iterator) stepActions.iterator();
    while (iter.hasNext()) {
      LookupElement element = (LookupElement) iter.next();
      Integer currentId = (Integer) mappedList.get(new Integer(element.getId()));
      if (currentId != null) {
        if (map.get(currentId) != null) {
          // The mapping exists and hence remove the item from the map to avoid deletion
          map.remove(currentId);
        } else {
          // the mapping does not exist. Hence insert it to the step_action_map
          ActionStepMap actionStepMap = new ActionStepMap();
          actionStepMap.setConstantId(constantId);
          actionStepMap.setActionConstantId(element.getId());
          actionStepMap.insert(db);
        }
      }
    }
    // All remaining mappings are not valid anymore. delete the mappings.
    iter = (Iterator) map.keySet().iterator();
    while (iter.hasNext()) {
      Integer actionId = (Integer) iter.next();
      pst = db.prepareStatement("DELETE FROM step_action_map WHERE map_id = ? ");
      pst.setInt(1, ((Integer) map.get(actionId)).intValue());
      pst.execute();
      pst.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  list              Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static LookupList parseRequiredActionLookupList(Connection db, LookupList list, int constantId) throws SQLException {
    LookupList newList = new LookupList();
    newList.setJsEvent("onChange=\"updateActionId();\"");
    HashMap map = ActionStep.getRequiredActionMappings(db, constantId);
    Iterator iter = (Iterator) list.iterator();
    while (iter.hasNext()) {
      LookupElement element = (LookupElement) iter.next();
      if (map.get(new Integer(element.getCode())) != null) {
        newList.addItem(element.getId(), element.getDescription());
      }
    }
    return newList;
  }


  /**
   *  Gets the stepActionsLookup attribute of the ActionStep class
   *
   * @param  db                Description of the Parameter
   * @return                   The stepActionsLookup value
   * @exception  SQLException  Description of the Exception
   */
  public static LookupList getStepActionsLookup(Connection db) throws SQLException {
    LookupList thisList = new LookupList();
    PreparedStatement pst = db.prepareStatement(
        "SELECT constant_id, description " +
        "FROM lookup_step_actions ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      thisList.addItem(rs.getInt("constant_id"), rs.getString("description"));
    }
    rs.close();
    pst.close();
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean allowsUpdate() {
    return allowUpdate;
  }


  /**
   *  Gets the current attribute of the ActionStep object
   *
   * @param  actionPlan  Description of the Parameter
   * @return             The current value
   */
  public boolean isCurrent(ActionPlan actionPlan) {
    ActionStep currentStep = actionPlan.getCurrentStep();
    if (currentStep != null) {
      return (currentStep.getId() == this.getId());
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @param  actionPlan  Description of the Parameter
   * @return             Description of the Return Value
   */
  public boolean beforeCurrent(ActionPlan actionPlan) {
    boolean before = false;
    Iterator i = actionPlan.getPhases().iterator();
    while (i.hasNext()) {
      ActionPhase thisPhase = (ActionPhase) i.next();
      Iterator j = thisPhase.getSteps().iterator();
      while (j.hasNext()) {
        ActionStep thisStep = (ActionStep) j.next();
        if (thisStep.isCurrent(actionPlan)) {
          break;
        }
        if (thisStep.getId() == this.getId()) {
          before = true;
          break;
        }
      }
    }
    return before;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  oldStep           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void checkRemoveStepAttachments(Connection db, ActionStep oldStep) throws SQLException {
    if (this.getActionId() != oldStep.getActionId()) {
      //Reset the attachment information in any of the action step instances.
      ActionItemWorkList workList = new ActionItemWorkList();
      workList.setActionStepId(this.getId());
      workList.buildList(db);
      workList.resetAttachment(db);
    }
  }


  /*
   *  public HtmlSelect getHtmlSelectObject() {
   *  HtmlSelect actionPhaseListSelect = new HtmlSelect();
   *  Iterator i = this.iterator();
   *  while (i.hasNext()) {
   *  ActionPhase thisPhase = (ActionPhase) i.next();
   *  actionPhaseListSelect.addItem(thisPhase.getId(), thisPhase.getName());
   *  }
   *  return actionPhaseListSelect;
   *  }
   */
  /*
   *  Get and Set methods
   */
  /**
   *  Gets the actionRequired attribute of the ActionStep object
   *
   * @return    The actionRequired value
   */
  public boolean getActionRequired() {
    return actionRequired;
  }


  /**
   *  Sets the actionRequired attribute of the ActionStep object
   *
   * @param  tmp  The new actionRequired value
   */
  public void setActionRequired(boolean tmp) {
    this.actionRequired = tmp;
  }


  /**
   *  Sets the actionRequired attribute of the ActionStep object
   *
   * @param  tmp  The new actionRequired value
   */
  public void setActionRequired(String tmp) {
    this.actionRequired = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the ActionStep object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionStep object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Gets the accountTypes attribute of the ActionStep object
   *
   * @return    The accountTypes value
   */
  public ArrayList getAccountTypes() {
    return accountTypes;
  }


  /**
   *  Sets the accountTypes attribute of the ActionStep object
   *
   * @param  tmp  The new accountTypes value
   */
  public void setAccountTypes(ArrayList tmp) {
    this.accountTypes = tmp;
  }


  /**
   *  Sets the id attribute of the ActionStep object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the parentId attribute of the ActionStep object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the ActionStep object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ActionStep object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the phaseId attribute of the ActionStep object
   *
   * @return    The phaseId value
   */
  public int getPhaseId() {
    return phaseId;
  }


  /**
   *  Sets the phaseId attribute of the ActionStep object
   *
   * @param  tmp  The new phaseId value
   */
  public void setPhaseId(int tmp) {
    this.phaseId = tmp;
  }


  /**
   *  Gets the label attribute of the ActionStep object
   *
   * @return    The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   *  Sets the label attribute of the ActionStep object
   *
   * @param  tmp  The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   *  Sets the phaseId attribute of the ActionStep object
   *
   * @param  tmp  The new phaseId value
   */
  public void setPhaseId(String tmp) {
    this.phaseId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the description attribute of the ActionStep object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the ActionStep object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the actionId attribute of the ActionStep object
   *
   * @return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Sets the actionId attribute of the ActionStep object
   *
   * @param  tmp  The new actionId value
   */
  public void setActionId(int tmp) {
    this.actionId = tmp;
  }


  /**
   *  Sets the actionId attribute of the ActionStep object
   *
   * @param  tmp  The new actionId value
   */
  public void setActionId(String tmp) {
    this.actionId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the durationTypeId attribute of the ActionStep object
   *
   * @return    The durationTypeId value
   */
  public int getDurationTypeId() {
    return durationTypeId;
  }


  /**
   *  Sets the durationTypeId attribute of the ActionStep object
   *
   * @param  tmp  The new durationTypeId value
   */
  public void setDurationTypeId(int tmp) {
    this.durationTypeId = tmp;
  }


  /**
   *  Sets the durationTypeId attribute of the ActionStep object
   *
   * @param  tmp  The new durationTypeId value
   */
  public void setDurationTypeId(String tmp) {
    this.durationTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the estimatedDuration attribute of the ActionStep object
   *
   * @return    The estimatedDuration value
   */
  public int getEstimatedDuration() {
    return estimatedDuration;
  }


  /**
   *  Sets the estimatedDuration attribute of the ActionStep object
   *
   * @param  tmp  The new estimatedDuration value
   */
  public void setEstimatedDuration(int tmp) {
    this.estimatedDuration = tmp;
  }


  /**
   *  Sets the estimatedDuration attribute of the ActionStep object
   *
   * @param  tmp  The new estimatedDuration value
   */
  public void setEstimatedDuration(String tmp) {
    this.estimatedDuration = Integer.parseInt(tmp);
  }


  /**
   *  Gets the customFieldCategoryId attribute of the ActionStep object
   *
   * @return    The customFieldCategoryId value
   */
  public int getCustomFieldCategoryId() {
    return customFieldCategoryId;
  }


  /**
   *  Sets the customFieldCategoryId attribute of the ActionStep object
   *
   * @param  tmp  The new customFieldCategoryId value
   */
  public void setCustomFieldCategoryId(int tmp) {
    this.customFieldCategoryId = tmp;
  }


  /**
   *  Sets the customFieldCategoryId attribute of the ActionStep object
   *
   * @param  tmp  The new customFieldCategoryId value
   */
  public void setCustomFieldCategoryId(String tmp) {
    this.customFieldCategoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the customFieldId attribute of the ActionStep object
   *
   * @return    The customFieldId value
   */
  public int getCustomFieldId() {
    return customFieldId;
  }


  /**
   *  Sets the customFieldId attribute of the ActionStep object
   *
   * @param  tmp  The new customFieldId value
   */
  public void setCustomFieldId(int tmp) {
    this.customFieldId = tmp;
  }


  /**
   *  Sets the customFieldId attribute of the ActionStep object
   *
   * @param  tmp  The new customFieldId value
   */
  public void setCustomFieldId(String tmp) {
    this.customFieldId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabled attribute of the ActionStep object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the ActionStep object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ActionStep object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the entered attribute of the ActionStep object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionStep object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionStep object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the durationTypeIdString attribute of the ActionStep object
   *
   * @return    The durationTypeIdString value
   */
  public String getDurationTypeIdString() {
    return durationTypeIdString;
  }


  /**
   *  Sets the durationTypeIdString attribute of the ActionStep object
   *
   * @param  tmp  The new durationTypeIdString value
   */
  public void setDurationTypeIdString(String tmp) {
    this.durationTypeIdString = tmp;
  }


  /**
   *  Gets the permissionType attribute of the ActionStep object
   *
   * @return    The permissionType value
   */
  public int getPermissionType() {
    return permissionType;
  }


  /**
   *  Sets the permissionType attribute of the ActionStep object
   *
   * @param  tmp  The new permissionType value
   */
  public void setPermissionType(int tmp) {
    this.permissionType = tmp;
  }


  /**
   *  Sets the permissionType attribute of the ActionStep object
   *
   * @param  tmp  The new permissionType value
   */
  public void setPermissionType(String tmp) {
    this.permissionType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the roleId attribute of the ActionStep object
   *
   * @return    The roleId value
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   *  Sets the roleId attribute of the ActionStep object
   *
   * @param  tmp  The new roleId value
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   *  Sets the roleId attribute of the ActionStep object
   *
   * @param  tmp  The new roleId value
   */
  public void setRoleId(String tmp) {
    this.roleId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the departmentId attribute of the ActionStep object
   *
   * @return    The departmentId value
   */
  public int getDepartmentId() {
    return departmentId;
  }


  /**
   *  Sets the departmentId attribute of the ActionStep object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(int tmp) {
    this.departmentId = tmp;
  }


  /**
   *  Sets the departmentId attribute of the ActionStep object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(String tmp) {
    this.departmentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the stepList attribute of the ActionStep object
   *
   * @return    The stepList value
   */
  public ActionStepList getStepList() {
    return stepList;
  }


  /**
   *  Sets the stepList attribute of the ActionStep object
   *
   * @param  tmp  The new stepList value
   */
  public void setStepList(ActionStepList tmp) {
    this.stepList = tmp;
  }


  /**
   *  Gets the buildCompleteStepList attribute of the ActionStep object
   *
   * @return    The buildCompleteStepList value
   */
  public boolean getBuildCompleteStepList() {
    return buildCompleteStepList;
  }


  /**
   *  Sets the buildCompleteStepList attribute of the ActionStep object
   *
   * @param  tmp  The new buildCompleteStepList value
   */
  public void setBuildCompleteStepList(boolean tmp) {
    this.buildCompleteStepList = tmp;
  }


  /**
   *  Sets the buildCompleteStepList attribute of the ActionStep object
   *
   * @param  tmp  The new buildCompleteStepList value
   */
  public void setBuildCompleteStepList(String tmp) {
    this.buildCompleteStepList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the allowSkipToHere attribute of the ActionStep object
   *
   * @return    The allowSkipToHere value
   */
  public boolean getAllowSkipToHere() {
    return allowSkipToHere;
  }


  /**
   *  Sets the allowSkipToHere attribute of the ActionStep object
   *
   * @param  tmp  The new allowSkipToHere value
   */
  public void setAllowSkipToHere(boolean tmp) {
    this.allowSkipToHere = tmp;
  }


  /**
   *  Sets the allowSkipToHere attribute of the ActionStep object
   *
   * @param  tmp  The new allowSkipToHere value
   */
  public void setAllowSkipToHere(String tmp) {
    this.allowSkipToHere = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildRelatedRecords attribute of the ActionStep object
   *
   * @return    The buildRelatedRecords value
   */
  public boolean getBuildRelatedRecords() {
    return buildRelatedRecords;
  }


  /**
   *  Sets the buildRelatedRecords attribute of the ActionStep object
   *
   * @param  tmp  The new buildRelatedRecords value
   */
  public void setBuildRelatedRecords(boolean tmp) {
    this.buildRelatedRecords = tmp;
  }


  /**
   *  Sets the buildRelatedRecords attribute of the ActionStep object
   *
   * @param  tmp  The new buildRelatedRecords value
   */
  public void setBuildRelatedRecords(String tmp) {
    this.buildRelatedRecords = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the actionItems attribute of the ActionStep object
   *
   * @return    The actionItems value
   */
  public ActionItemWorkList getActionItems() {
    return actionItems;
  }


  /**
   *  Sets the actionItems attribute of the ActionStep object
   *
   * @param  tmp  The new actionItems value
   */
  public void setActionItems(ActionItemWorkList tmp) {
    this.actionItems = tmp;
  }


  /**
   *  Gets the lookupList attribute of the ActionStep object
   *
   * @return    The lookupList value
   */
  public ActionStepLookupList getLookupList() {
    return lookupList;
  }


  /**
   *  Sets the lookupList attribute of the ActionStep object
   *
   * @param  tmp  The new lookupList value
   */
  public void setLookupList(ActionStepLookupList tmp) {
    this.lookupList = tmp;
  }


  /**
   *  Gets the userGroupId attribute of the ActionStep object
   *
   * @return    The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   *  Sets the userGroupId attribute of the ActionStep object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   *  Sets the userGroupId attribute of the ActionStep object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the userGroupName attribute of the ActionStep object
   *
   * @return    The userGroupName value
   */
  public String getUserGroupName() {
    return userGroupName;
  }


  /**
   *  Sets the userGroupName attribute of the ActionStep object
   *
   * @param  tmp  The new userGroupName value
   */
  public void setUserGroupName(String tmp) {
    this.userGroupName = tmp;
  }


  /**
   *  Gets the targetRelationship attribute of the ActionStep object
   *
   * @return    The targetRelationship value
   */
  public String getTargetRelationship() {
    return targetRelationship;
  }


  /**
   *  Sets the targetRelationship attribute of the ActionStep object
   *
   * @param  tmp  The new targetRelationship value
   */
  public void setTargetRelationship(String tmp) {
    this.targetRelationship = tmp;
  }


  /**
   *  Gets the allowUpdate attribute of the ActionStep object
   *
   * @return    The allowUpdate value
   */
  public boolean getAllowUpdate() {
    return allowUpdate;
  }


  /**
   *  Sets the allowUpdate attribute of the ActionStep object
   *
   * @param  tmp  The new allowUpdate value
   */
  public void setAllowUpdate(boolean tmp) {
    this.allowUpdate = tmp;
  }


  /**
   *  Sets the allowUpdate attribute of the ActionStep object
   *
   * @param  tmp  The new allowUpdate value
   */
  public void setAllowUpdate(String tmp) {
    this.allowUpdate = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the campaignId attribute of the ActionStep object
   *
   * @return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Sets the campaignId attribute of the ActionStep object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the ActionStep object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the allowDuplicateRecipient attribute of the ActionStep object
   *
   * @return    The allowDuplicateRecipient value
   */
  public boolean getAllowDuplicateRecipient() {
    return allowDuplicateRecipient;
  }


  /**
   *  Sets the allowDuplicateRecipient attribute of the ActionStep object
   *
   * @param  tmp  The new allowDuplicateRecipient value
   */
  public void setAllowDuplicateRecipient(boolean tmp) {
    this.allowDuplicateRecipient = tmp;
  }


  /**
   *  Sets the allowDuplicateRecipient attribute of the ActionStep object
   *
   * @param  tmp  The new allowDuplicateRecipient value
   */
  public void setAllowDuplicateRecipient(String tmp) {
    this.allowDuplicateRecipient = DatabaseUtils.parseBoolean(tmp);
  }
}

