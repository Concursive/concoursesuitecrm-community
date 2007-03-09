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
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.UserGroup;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.LookupList;

import java.sql.*;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 17, 2005
 * @version    $Id: ActionItemWork.java,v 1.1.2.23.2.6 2005/10/10 16:01:09
 *      partha Exp $
 */
public class ActionItemWork extends GenericBean {
  private int id = -1;
  private int phaseWorkId = -1;
  private int actionStepId = -1;

  private int statusId = -1;
  private int ownerId = -1;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private int level = -1;

  private int linkModuleId = -1;
  private int linkItemId = -1;

  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;

  //Action Step properties
  private String stepDescription = null;
  protected int permissionType = -1;
  private int estimatedDuration = -1;
  private int parentId = -1;
  private int actionId = -1;
  private boolean allowSkipToHere = false;
  private boolean actionRequired = false;
  private String label = null;
  private String targetRelationship = null;
  private boolean allowUpdate = true;
  //resources
  private boolean hasNext = false;
  private ActionItemWork nextStep = null;
  private boolean buildLinkedObject = false;
  private Contact contact = null;
  private OpportunityComponent component = null;
  private FileItem fileItem = null;
  private Call activity = null;
  private CustomFieldCategory customFieldCategory = null;
  private ActionItemWorkNote note = null;
  private ActionItemWorkNoteList noteList = null;
  private ActionItemWorkSelectionList selectionList = null;
  private RelationshipList relationshipList = null;
  private ActionStep step = null;
  private UserList groupUsers = null;
  private UserList roleUsers = null;
  private UserList departmentUsers = null;
  private String userGroupName = null;
  private String departmentName = null;
  private String roleName = null;
  private boolean buildStep = false;
  private int groupId = -1;
  // related resources
  private ActionPlanWork planWork = null;


  /**
   *  Gets the allowUpdate attribute of the ActionItemWork object
   *
   * @return    The allowUpdate value
   */
  public boolean getAllowUpdate() {
    return allowUpdate;
  }


  /**
   *  Sets the allowUpdate attribute of the ActionItemWork object
   *
   * @param  tmp  The new allowUpdate value
   */
  public void setAllowUpdate(boolean tmp) {
    this.allowUpdate = tmp;
  }


  /**
   *  Sets the allowUpdate attribute of the ActionItemWork object
   *
   * @param  tmp  The new allowUpdate value
   */
  public void setAllowUpdate(String tmp) {
    this.allowUpdate = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the targetRelationship attribute of the ActionItemWork object
   *
   * @return    The targetRelationship value
   */
  public String getTargetRelationship() {
    return targetRelationship;
  }


  /**
   *  Sets the targetRelationship attribute of the ActionItemWork object
   *
   * @param  tmp  The new targetRelationship value
   */
  public void setTargetRelationship(String tmp) {
    this.targetRelationship = tmp;
  }


  /**
   *  Gets the buildStep attribute of the ActionItemWork object
   *
   * @return    The buildStep value
   */
  public boolean getBuildStep() {
    return buildStep;
  }


  /**
   *  Sets the buildStep attribute of the ActionItemWork object
   *
   * @param  tmp  The new buildStep value
   */
  public void setBuildStep(boolean tmp) {
    this.buildStep = tmp;
  }


  /**
   *  Sets the buildStep attribute of the ActionItemWork object
   *
   * @param  tmp  The new buildStep value
   */
  public void setBuildStep(String tmp) {
    this.buildStep = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the actionRequired attribute of the ActionItemWork object
   *
   * @return    The actionRequired value
   */
  public boolean getActionRequired() {
    return actionRequired;
  }


  /**
   *  Sets the actionRequired attribute of the ActionItemWork object
   *
   * @param  tmp  The new actionRequired value
   */
  public void setActionRequired(boolean tmp) {
    this.actionRequired = tmp;
  }


  /**
   *  Sets the actionRequired attribute of the ActionItemWork object
   *
   * @param  tmp  The new actionRequired value
   */
  public void setActionRequired(String tmp) {
    this.actionRequired = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the label attribute of the ActionItemWork object
   *
   * @return    The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   *  Sets the label attribute of the ActionItemWork object
   *
   * @param  tmp  The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   *  Gets the note attribute of the ActionItemWork object
   *
   * @return    The note value
   */
  public ActionItemWorkNote getNote() {
    return note;
  }


  /**
   *  Sets the note attribute of the ActionItemWork object
   *
   * @param  tmp  The new note value
   */
  public void setNote(ActionItemWorkNote tmp) {
    this.note = tmp;
  }


  /**
   *  Gets the noteList attribute of the ActionItemWork object
   *
   * @return    The noteList value
   */
  public ActionItemWorkNoteList getNoteList() {
    return noteList;
  }


  /**
   *  Sets the noteList attribute of the ActionItemWork object
   *
   * @param  tmp  The new noteList value
   */
  public void setNoteList(ActionItemWorkNoteList tmp) {
    this.noteList = tmp;
  }


  /**
   *  Gets the relationshipList attribute of the ActionItemWork object
   *
   * @return    The relationshipList value
   */
  public RelationshipList getRelationshipList() {
    return relationshipList;
  }


  /**
   *  Sets the relationshipList attribute of the ActionItemWork object
   *
   * @param  tmp  The new relationshipList value
   */
  public void setRelationshipList(RelationshipList tmp) {
    this.relationshipList = tmp;
  }


  /**
   *  Gets the selectionList attribute of the ActionItemWork object
   *
   * @return    The selectionList value
   */
  public ActionItemWorkSelectionList getSelectionList() {
    return selectionList;
  }


  /**
   *  Sets the selectionList attribute of the ActionItemWork object
   *
   * @param  tmp  The new selectionList value
   */
  public void setSelectionList(ActionItemWorkSelectionList tmp) {
    this.selectionList = tmp;
  }



  /**
   *  Gets the hasNext attribute of the ActionItemWork object
   *
   * @return    The hasNext value
   */
  public boolean getHasNext() {
    return hasNext;
  }


  /**
   *  Sets the hasNext attribute of the ActionItemWork object
   *
   * @param  tmp  The new hasNext value
   */
  public void setHasNext(boolean tmp) {
    this.hasNext = tmp;
  }


  /**
   *  Sets the hasNext attribute of the ActionItemWork object
   *
   * @param  tmp  The new hasNext value
   */
  public void setHasNext(String tmp) {
    this.hasNext = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the nextStep attribute of the ActionItemWork object
   *
   * @return    The nextStep value
   */
  public ActionItemWork getNextStep() {
    return nextStep;
  }


  /**
   *  Sets the nextStep attribute of the ActionItemWork object
   *
   * @param  tmp  The new nextStep value
   */
  public void setNextStep(ActionItemWork tmp) {
    this.nextStep = tmp;
  }


  /**
   *  Gets the allowSkipToHere attribute of the ActionItemWork object
   *
   * @return    The allowSkipToHere value
   */
  public boolean getAllowSkipToHere() {
    return allowSkipToHere;
  }


  /**
   *  Sets the allowSkipToHere attribute of the ActionItemWork object
   *
   * @param  tmp  The new allowSkipToHere value
   */
  public void setAllowSkipToHere(boolean tmp) {
    this.allowSkipToHere = tmp;
  }


  /**
   *  Sets the allowSkipToHere attribute of the ActionItemWork object
   *
   * @param  tmp  The new allowSkipToHere value
   */
  public void setAllowSkipToHere(String tmp) {
    this.allowSkipToHere = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the permissionType attribute of the ActionItemWork object
   *
   * @return    The permissionType value
   */
  public int getPermissionType() {
    return permissionType;
  }


  /**
   *  Sets the permissionType attribute of the ActionItemWork object
   *
   * @param  tmp  The new permissionType value
   */
  public void setPermissionType(int tmp) {
    this.permissionType = tmp;
  }


  /**
   *  Sets the permissionType attribute of the ActionItemWork object
   *
   * @param  tmp  The new permissionType value
   */
  public void setPermissionType(String tmp) {
    this.permissionType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the level attribute of the ActionItemWork object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the ActionItemWork object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the ActionItemWork object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Gets the contact attribute of the ActionItemWork object
   *
   * @return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Sets the contact attribute of the ActionItemWork object
   *
   * @param  tmp  The new contact value
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   *  Gets the component attribute of the ActionItemWork object
   *
   * @return    The component value
   */
  public OpportunityComponent getComponent() {
    return component;
  }


  /**
   *  Sets the component attribute of the ActionItemWork object
   *
   * @param  tmp  The new component value
   */
  public void setComponent(OpportunityComponent tmp) {
    this.component = tmp;
  }


  /**
   *  Gets the fileItem attribute of the ActionItemWork object
   *
   * @return    The fileItem value
   */
  public FileItem getFileItem() {
    return fileItem;
  }


  /**
   *  Sets the fileItem attribute of the ActionItemWork object
   *
   * @param  tmp  The new fileItem value
   */
  public void setFileItem(FileItem tmp) {
    this.fileItem = tmp;
  }


  /**
   *  Gets the activity attribute of the ActionItemWork object
   *
   * @return    The activity value
   */
  public Call getActivity() {
    return activity;
  }


  /**
   *  Sets the activity attribute of the ActionItemWork object
   *
   * @param  tmp  The new activity value
   */
  public void setActivity(Call tmp) {
    this.activity = tmp;
  }


  /**
   *  Gets the customFieldCategory attribute of the ActionItemWork object
   *
   * @return    The customFieldCategory value
   */
  public CustomFieldCategory getCustomFieldCategory() {
    return customFieldCategory;
  }


  /**
   *  Sets the customFieldCategory attribute of the ActionItemWork object
   *
   * @param  tmp  The new customFieldCategory value
   */
  public void setCustomFieldCategory(CustomFieldCategory tmp) {
    this.customFieldCategory = tmp;
  }



  /**
   *  Gets the buildLinkedObject attribute of the ActionItemWork object
   *
   * @return    The buildLinkedObject value
   */
  public boolean getBuildLinkedObject() {
    return buildLinkedObject;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionItemWork object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(boolean tmp) {
    this.buildLinkedObject = tmp;
  }


  /**
   *  Sets the buildLinkedObject attribute of the ActionItemWork object
   *
   * @param  tmp  The new buildLinkedObject value
   */
  public void setBuildLinkedObject(String tmp) {
    this.buildLinkedObject = DatabaseUtils.parseBoolean(tmp);
  }



  /**
   *  Gets the actionId attribute of the ActionItemWork object
   *
   * @return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Sets the actionId attribute of the ActionItemWork object
   *
   * @param  tmp  The new actionId value
   */
  public void setActionId(int tmp) {
    this.actionId = tmp;
  }


  /**
   *  Sets the actionId attribute of the ActionItemWork object
   *
   * @param  tmp  The new actionId value
   */
  public void setActionId(String tmp) {
    this.actionId = Integer.parseInt(tmp);
  }



  /**
   *  Gets the parentId attribute of the ActionItemWork object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the ActionItemWork object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ActionItemWork object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }



  /**
   *  Gets the estimatedDuration attribute of the ActionItemWork object
   *
   * @return    The estimatedDuration value
   */
  public int getEstimatedDuration() {
    return estimatedDuration;
  }


  /**
   *  Sets the estimatedDuration attribute of the ActionItemWork object
   *
   * @param  tmp  The new estimatedDuration value
   */
  public void setEstimatedDuration(int tmp) {
    this.estimatedDuration = tmp;
  }


  /**
   *  Sets the estimatedDuration attribute of the ActionItemWork object
   *
   * @param  tmp  The new estimatedDuration value
   */
  public void setEstimatedDuration(String tmp) {
    this.estimatedDuration = Integer.parseInt(tmp);
  }



  /**
   *  Gets the stepDescription attribute of the ActionItemWork object
   *
   * @return    The stepDescription value
   */
  public String getStepDescription() {
    return stepDescription;
  }


  /**
   *  Sets the stepDescription attribute of the ActionItemWork object
   *
   * @param  tmp  The new stepDescription value
   */
  public void setStepDescription(String tmp) {
    this.stepDescription = tmp;
  }


  /**
   *  Gets the id attribute of the ActionItemWork object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionItemWork object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionItemWork object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the phaseWorkId attribute of the ActionItemWork object
   *
   * @return    The phaseWorkId value
   */
  public int getPhaseWorkId() {
    return phaseWorkId;
  }


  /**
   *  Sets the phaseWorkId attribute of the ActionItemWork object
   *
   * @param  tmp  The new phaseWorkId value
   */
  public void setPhaseWorkId(int tmp) {
    this.phaseWorkId = tmp;
  }


  /**
   *  Sets the phaseWorkId attribute of the ActionItemWork object
   *
   * @param  tmp  The new phaseWorkId value
   */
  public void setPhaseWorkId(String tmp) {
    this.phaseWorkId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the actionStepId attribute of the ActionItemWork object
   *
   * @return    The actionStepId value
   */
  public int getActionStepId() {
    return actionStepId;
  }


  /**
   *  Sets the actionStepId attribute of the ActionItemWork object
   *
   * @param  tmp  The new actionStepId value
   */
  public void setActionStepId(int tmp) {
    this.actionStepId = tmp;
  }


  /**
   *  Sets the actionStepId attribute of the ActionItemWork object
   *
   * @param  tmp  The new actionStepId value
   */
  public void setActionStepId(String tmp) {
    this.actionStepId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the statusId attribute of the ActionItemWork object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Sets the statusId attribute of the ActionItemWork object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the ActionItemWork object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the ownerId attribute of the ActionItemWork object
   *
   * @return    The ownerId value
   */
  public int getOwnerId() {
    return ownerId;
  }


  /**
   *  Sets the ownerId attribute of the ActionItemWork object
   *
   * @param  tmp  The new ownerId value
   */
  public void setOwnerId(int tmp) {
    this.ownerId = tmp;
  }


  /**
   *  Sets the ownerId attribute of the ActionItemWork object
   *
   * @param  tmp  The new ownerId value
   */
  public void setOwnerId(String tmp) {
    this.ownerId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the startDate attribute of the ActionItemWork object
   *
   * @return    The startDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Sets the startDate attribute of the ActionItemWork object
   *
   * @param  tmp  The new startDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the ActionItemWork object
   *
   * @param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the endDate attribute of the ActionItemWork object
   *
   * @return    The endDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Sets the endDate attribute of the ActionItemWork object
   *
   * @param  tmp  The new endDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the ActionItemWork object
   *
   * @param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the linkModuleId attribute of the ActionItemWork object
   *
   * @return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Sets the linkModuleId attribute of the ActionItemWork object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the ActionItemWork object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the linkItemId attribute of the ActionItemWork object
   *
   * @return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   *  Sets the linkItemId attribute of the ActionItemWork object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the ActionItemWork object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the entered attribute of the ActionItemWork object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionItemWork object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionItemWork object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modified attribute of the ActionItemWork object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the ActionItemWork object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ActionItemWork object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the ActionItemWork object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the ActionItemWork object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ActionItemWork object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the ActionItemWork object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionItemWork object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ActionItemWork object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the groupId attribute of the ActionItemWork object
   *
   * @return    The groupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   *  Sets the groupId attribute of the ActionItemWork object
   *
   * @param  tmp  The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the groupId attribute of the ActionItemWork object
   *
   * @param  tmp  The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planWork attribute of the ActionItemWork object
   *
   * @return    The planWork value
   */
  public ActionPlanWork getPlanWork() {
    return planWork;
  }


  /**
   *  Sets the planWork attribute of the ActionItemWork object
   *
   * @param  tmp  The new planWork value
   */
  public void setPlanWork(ActionPlanWork tmp) {
    this.planWork = tmp;
  }


  /**
   *  Constructor for the ActionItemWork object
   */
  public ActionItemWork() { }


  /**
   *  Constructor for the ActionItemWork object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionItemWork(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionItemWork object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionItemWork(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the ActionItemWork object
   *
   * @param  db                Description of the Parameter
   * @param  linkModuleId      Description of the Parameter
   * @param  linkItemId        Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionItemWork(Connection db, int linkModuleId, int linkItemId) throws SQLException {
    if (linkModuleId == -1) {
      throw new SQLException("Invalid ModuleId Specified");
    }
    if (linkItemId == -1) {
      throw new SQLException("Invalid Item Id Specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT item_work_id " +
        "FROM action_item_work " +
        "WHERE link_module_id = ? AND link_item_id = ? ");
    pst.setInt(1, linkModuleId);
    pst.setInt(2, linkItemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.id = rs.getInt("item_work_id");
    }
    rs.close();
    pst.close();
    queryRecord(db, this.id);
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
      throw new SQLException("Invalid Action Item Work ID");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT aiw.*, " +
        "acs.description, acs.estimated_duration, acs.parent_id, acs.action_id, " +
        "acs.permission_type, acs.allow_skip_to_here, acs.action_required, acs.label, acs.target_relationship, acs.allow_update " +
        "FROM action_item_work aiw " +
        "LEFT JOIN action_step acs ON (aiw.action_step_id = acs.step_id) " +
        "LEFT JOIN action_phase_work apw ON (aiw.phase_work_id = apw.phase_work_id) " +
        "WHERE aiw.item_work_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    //Step needs to be built before calling buildLinkedObject()
    if (buildStep) {
      buildStep(db);
    }

    //build resources
    if (buildLinkedObject) {
      this.buildLinkedObject(db);
    }

    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildStep(Connection db) throws SQLException {
    step = new ActionStep(db, this.getActionStepId());
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildUserLists(Connection db) throws SQLException {
    switch (step.getPermissionType()) {
      case ActionStep.ROLE:
        roleUsers = new UserList();
        roleUsers.setRoleId(step.getRoleId());
        roleUsers.buildList(db);
        Role role = new Role(db, step.getRoleId());
        this.setRoleName(role.getRole());
        break;
      case ActionStep.DEPARTMENT:
        departmentUsers = new UserList();
        departmentUsers.setDepartment(step.getDepartmentId());
        departmentUsers.buildList(db);
        LookupList list = new LookupList(db, "lookup_department");
        this.setDepartmentName(list.getSelectedValue(step.getDepartmentId()));
        break;
      case ActionStep.USER_GROUP:
        groupUsers = new UserList();
        ActionPhaseWork phaseWork = new ActionPhaseWork(db, this.getPhaseWorkId());
        if (this.getPlanWork() == null) {
          planWork = new ActionPlanWork();
          planWork.setBuildLinkedObject(true);
          planWork.queryRecord(db, phaseWork.getPlanWorkId());
        }
        if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS)) {
          //do not build any users
        } else if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
          //do not build any users
        } else if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS)) {
          if (planWork.getTicket() == null || planWork.getTicket().getId() <= 0 ) {
            planWork.buildLinkedObject(db);
          }
          if (planWork.getTicket().getUserGroupId() != -1) {
            groupUsers.setUserGroupId(planWork.getTicket().getUserGroupId());
            groupUsers.buildList(db);
            UserGroup group = new UserGroup(db, planWork.getTicket().getUserGroupId());
            this.setUserGroupName(group.getName());
          }
        }
        break;
      case ActionStep.SPECIFIC_USER_GROUP:
        groupUsers = new UserList();
        if (step.getUserGroupId() != -1) {
          groupUsers.setUserGroupId(step.getUserGroupId());
          groupUsers.buildList(db);
          this.setUserGroupName(step.getUserGroupName());
        }
        break;
      case ActionStep.WITHIN_USER_HIERARCHY:
        departmentUsers = new UserList();
        departmentUsers.setManagerId(this.getOwnerId());
        departmentUsers.setEnabled(Constants.TRUE);
        departmentUsers.buildList(db);
        this.setDepartmentName("");
        break;
      case ActionStep.UP_USER_HIERARCHY:
        this.setDepartmentName("");
        break;
      default:
        break;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  userId  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean userHasPermission(int userId, HttpServletRequest request) {
    boolean result = false;
    switch (step.getPermissionType()) {
      case ActionStep.ROLE:
        result = (roleUsers != null && roleUsers.getUser(userId) != null);
        break;
      case ActionStep.DEPARTMENT:
        result = (departmentUsers != null && departmentUsers.getUser(userId) != null);
        break;
      case ActionStep.USER_GROUP:
        result = (groupUsers != null && groupUsers.getUser(userId) != null);
        break;
      case ActionStep.SPECIFIC_USER_GROUP:
        result = (groupUsers != null && groupUsers.getUser(userId) != null);
        break;
      case ActionStep.MANAGER:
        result = (userId == planWork.getManagerId());
        break;
      case ActionStep.UP_USER_HIERARCHY:
        result = this.getOwnerId() == userId || (UserUtils.getUserIdRange(request).indexOf(String.valueOf(this.getOwnerId())) != -1);
        break;
      case ActionStep.WITHIN_USER_HIERARCHY:
        result = (departmentUsers != null && departmentUsers.getUser(userId) != null) || this.getOwnerId() == userId;
        break;
      case ActionStep.ASSIGNED_USER_AND_MANAGER:
        result = (userId == this.getOwnerId() || userId == planWork.getManagerId());
        break;
      case ActionStep.USER_DELEGATED:
        result = (this.getOwnerId() == userId);
        break;
      default:
        result = (this.getOwnerId() == userId);
        break;
    }
    if (step.getAllowSkipToHere() && userId == planWork.getManagerId()) {
      result = true;
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildLinkedObject(Connection db) throws SQLException {
    step = new ActionStep(db, this.getActionStepId());
    if (this.getRoleName() == null && this.getDepartmentName() == null && this.getUserGroupName() == null) {
      buildUserLists(db);
    }
    if (linkItemId > -1) {
      switch (actionId) {
        case ActionStep.ATTACH_ACCOUNT_CONTACT:
          contact = new Contact(db, linkItemId);
          break;
        case ActionStep.ADD_RECIPIENT:
          contact = new Contact(db, linkItemId);
          break;
        case ActionStep.ATTACH_OPPORTUNITY:
          component = new OpportunityComponent(db, linkItemId);
          break;
        case ActionStep.ATTACH_DOCUMENT:
          fileItem = new FileItem(db, linkItemId);
          break;
        case ActionStep.ATTACH_ACTIVITY:
          activity = new Call(db, linkItemId);
          break;
        case ActionStep.ATTACH_FOLDER:
          int categoryId = CustomFieldCategory.getIdFromRecord(db, linkItemId);
          if (categoryId != -1) {
            customFieldCategory = new CustomFieldCategory(db, categoryId);
            customFieldCategory.setLinkModuleId(Constants.ACCOUNTS);
            if (this.getPlanWork() != null && this.getPlanWork().getLinkItemId() != -1) {
              customFieldCategory.setLinkItemId(this.getPlanWork().getLinkItemId());
              customFieldCategory.setRecordId(linkItemId);
              customFieldCategory.setBuildResources(true);
              customFieldCategory.buildResources(db);
            }
          }
          break;
        case ActionStep.ATTACH_NOTE_SINGLE:
          note = new ActionItemWorkNote(db, linkItemId);
          break;
        case ActionStep.ATTACH_NOTE_MULTIPLE:
          note = new ActionItemWorkNote(db, linkItemId);
          noteList = new ActionItemWorkNoteList();
          noteList.setItemWorkId(id);
          noteList.buildList(db);
          break;
        case ActionStep.ATTACH_LOOKUPLIST_MULTIPLE:
          selectionList = new ActionItemWorkSelectionList();
          selectionList.setItemWorkId(id);
          selectionList.buildList(db);
          break;
        case ActionStep.ATTACH_RELATIONSHIP:
          relationshipList = new RelationshipList();
          if (planWork != null) {
            if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
              relationshipList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
            }
            relationshipList.setObjectIdMapsFrom(planWork.getLinkItemId());
            relationshipList.setObjectIdMapsTo(-1);

            //set the relationship type
            if (targetRelationship.endsWith("_reciprocal")) {
              relationshipList.setObjectIdMapsTo(planWork.getLinkItemId());
              relationshipList.setObjectIdMapsFrom(-1);
              relationshipList.setTypeId(
                  Integer.parseInt(targetRelationship.substring(0,
                  targetRelationship.indexOf("_"))));
            } else {
              relationshipList.setTypeId(
                  Integer.parseInt(targetRelationship));
            }
            relationshipList.buildList(db);

            //Filter account relationships that don't fall under action step's account types
            if (step == null) {
              this.buildStep(db);
            }
            relationshipList.filterAccounts(step.getAccountTypes());
          }
          break;
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void resetAttachment(Connection db) throws SQLException {
    linkModuleId = -1;
    linkItemId = -1;

    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_item_work " +
        "SET link_module_id = ?, link_item_id = ? " +
        "WHERE item_work_id = ? ");
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, linkModuleId);
    DatabaseUtils.setInt(pst, ++i, linkItemId);
    pst.setInt(++i, this.getId());
    pst.executeUpdate();

    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  thisNote  Description of the Parameter
   */
  public void add(ActionItemWorkNote thisNote) {
    if (actionId == ActionStep.ATTACH_NOTE_SINGLE) {
      this.setNote(thisNote);
    } else if (actionId == ActionStep.ATTACH_NOTE_MULTIPLE) {
      if (noteList == null) {
        noteList = new ActionItemWorkNoteList();
      }
      noteList.add(thisNote);
    }
  }


  /**
   *  Gets the current attribute of the ActionItemWork object
   *
   * @return    The current value
   */
  public boolean isCurrent() {
    boolean result = (startDate != null && endDate == null && statusId != ActionPlanWork.SKIPPED);
    if (this.getPlanWork() != null && this.getPlanWork().getPhaseWorkList() != null) {
      ActionPhaseWork phaseWork = this.getPlanWork().getPhaseWorkList().getPhaseWorkById(this.getPhaseWorkId());
      if (phaseWork != null) {
        if (phaseWork.getPhase() != null && phaseWork.getPhase().getRandom() && phaseWork.getItemWorkList() != null 
            && phaseWork.getItemWorkList().size() > 0 && !phaseWork.allStepsComplete()) {
          result = false;
        }
      }
    }
    return result;
  }


  /**
   *  Gets the currentSkip attribute of the ActionItemWork object
   *
   * @return    The currentSkip value
   */
  public boolean isCurrentSkip() {
    ActionItemWork nextItem = nextStep;
    while (nextItem != null && !nextItem.allowsUpdate()) {
      nextItem = nextItem.getNextStep();
    }
    if (nextItem == null) {
      return true;
      //hit the end of the plan
    } else if (nextItem != null && nextItem.isCurrent()) {
      return true;
      //hit the current step in this skip
    }
    return false;
  }


  /**
   *  Determines if this step is before the current step in the action plan
   *
   * @return    The previous value
   */
  public boolean isPrevious() {
    if (isCurrent()) {
      return false;
    }
    if (hasNext) {
      //There exists a next step and the next step does not allow updates
      if (nextStep != null && !nextStep.allowsUpdate()) {
        if (isCurrentSkip()) {
          return true;
        }
      }
      //There exists a next step and the next step is the current one
      if (nextStep != null && nextStep.isCurrent()) {
        return true;
      }
      if(nextStep!=null && nextStep.getPlanWork()!=null && this.getPlanWork().getPhaseWorkList() != null)
      {
        ActionPhaseWork nextPhaseWork = nextStep.getPlanWork().getPhaseWorkList().getPhaseWorkById(nextStep.getPhaseWorkId());
        if (nextPhaseWork != null) {
          if (nextPhaseWork.getPhase() != null && nextPhaseWork.getPhase().getRandom() && nextPhaseWork.getItemWorkList() != null 
              && nextPhaseWork.getItemWorkList().size() > 0 && nextPhaseWork.noStepComplete()) {
            return true;
          }
        }
      }
    } else {
      //Last step in the plan. Check if it already has a status
      if (hasStatus()) {
        return true;
      }
    }
    return false;
  }
  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void attach(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Action Item Work ID not specified");
    }
    if (linkModuleId == -1) {
      throw new SQLException("Link Module ID not specified");
    }
    if (linkItemId == -1) {
      throw new SQLException("Link Item ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_item_work " +
        "SET link_module_id = ?, " +
        "link_item_id = ? " +
        "WHERE item_work_id = ? ");
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, linkModuleId);
    DatabaseUtils.setInt(pst, ++i, linkItemId);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void deleteFolderAttachment(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Action Item Work ID not specified");
    }
    if (linkModuleId == -1) {
      throw new SQLException("Link Module ID not specified");
    }
    int newRecordId = -1;
    String sql =
        "SELECT MAX(record_id) AS record_id " +
        "FROM custom_field_record cfr " +
        "WHERE cfr.link_module_id = ? " +
        "AND cfr.link_item_id = ? " +
        "AND cfr.category_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, Constants.ACCOUNTS);
    pst.setInt(2, planWork.getOrganization().getOrgId());
    pst.setInt(3, customFieldCategory.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      newRecordId = DatabaseUtils.getInt(rs, "record_id");
    }
    rs.close();
    pst.close();

    pst = db.prepareStatement(
        "UPDATE action_item_work " +
        "SET link_item_id = ? " +
        (newRecordId == -1 ? ", link_module_id = ? " : "") +
        "WHERE item_work_id = ? ");
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, newRecordId);
    if (newRecordId == -1) {
      DatabaseUtils.setInt(pst, ++i, -1);
    }
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
    this.setLinkItemId(newRecordId);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void reassign(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Action Item Work ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_item_work " +
        "SET owner = ? " +
        "WHERE item_work_id = ? ");
    int i = 0;
    pst.setInt(++i, ownerId);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Gets the complete attribute of the ActionItemWork object
   *
   * @return    The complete value
   */
  public boolean isComplete() {
    if (statusId == ActionPlanWork.COMPLETED) {
      return true;
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasStatus() {
    if (statusId == ActionPlanWork.COMPLETED ||
        statusId == ActionPlanWork.SKIPPED ||
        statusId == ActionPlanWork.UNDEFINED) {
      return true;
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean update(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_item_work " +
        "SET status_id = ?, " +
        "start_date = ?, " +
        "end_date = ?, " +
        "owner = ? " +
        "WHERE item_work_id = ? ");
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, statusId);
    DatabaseUtils.setTimestamp(pst, ++i, startDate);
    DatabaseUtils.setTimestamp(pst, ++i, endDate);
    DatabaseUtils.setInt(pst, ++i, ownerId);
    pst.setInt(++i, id);
    pst.executeUpdate();
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
  public boolean updateAttachment(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_item_work " +
        "SET link_item_id = ?, link_module_id = ? " +
        "WHERE item_work_id = ? ");
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, linkItemId);
    DatabaseUtils.setInt(pst, ++i, linkModuleId);
    pst.setInt(++i, id);
    pst.executeUpdate();
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
  public boolean updateStatus(Connection db) throws SQLException {
    boolean commit = false;
    int count = 0;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      this.update(db);

      //Action Plan Work gets a new modified date when a step changes status
      ActionPhaseWork phaseWork = new ActionPhaseWork();
      phaseWork.setBuildStepWork(true);
      phaseWork.queryRecord(db, this.getPhaseWorkId());
      ActionPlanWork planWork = null;
      if (this.getPlanWork() != null) {
        planWork = this.getPlanWork();
      } else {
        planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
      }
      planWork.setModifiedBy(this.getModifiedBy());

      //Take necessary action if this step allows skipping of previous steps
      if (allowSkipToHere) {
        //TODO: Add additional filters to restrict the size of the list
        ActionItemWorkList actionPlanSteps = new ActionItemWorkList();
        actionPlanSteps.setPlanWorkId(planWork.getId());
        actionPlanSteps.buildList(db);

        ActionItemWorkList skippedSteps = this.getSkippedSteps(actionPlanSteps);
        if (this.getPlanWork() == null || this.getPlanWork().getPhaseWorkList() == null || this.getPlanWork().getPhaseWorkList().size() == 0) {
          planWork.setBuildPhaseWork(true);
          planWork.buildPhaseWork(db);
          this.setPlanWork(planWork);
        }
        this.removeGlobalSteps(skippedSteps);
        skippedSteps.updateStatus(db, ActionPlanWork.SKIPPED);
        //action plan work current phase needs to be set
        planWork.setCurrentPhaseId(phaseWork.getActionPhaseId());
      }

      //reload the phase work for any changes
      phaseWork = new ActionPhaseWork();
      phaseWork.setBuildPhase(true);
      phaseWork.setBuildLinkedObject(true);
      phaseWork.setBuildStepWork(true);
      phaseWork.setPlanWork(planWork);
      phaseWork.queryRecord(db, this.getPhaseWorkId());
      //Take necessary action if this was the last step in the phase
      if (phaseWork.allStepsComplete()) {
        //Action Phase Work gets an EndDate when the last step of the phase has a valid status
        phaseWork.setStatusId(statusId);
        phaseWork.setModifiedBy(modifiedBy);
        phaseWork.setEndDate(
            new java.sql.Timestamp(
            new java.util.Date().getTime()));
        phaseWork.update(db);

        //The next Action Phase Work gets a StartDate when the last step of the previous phase has a valid status
        ActionPhaseWork nextPhase = phaseWork.getNextPhase(db);
        if (nextPhase == null) {
          //Last step of the last phase in the plan has been updated. Plan is complete
          planWork.setCurrentPhaseId(-1);
        } else {
          nextPhase.setModifiedBy(modifiedBy);
          nextPhase.setStartDate(
              new java.sql.Timestamp(
              new java.util.Date().getTime()));
          nextPhase.update(db);
          nextPhase.setPlanWork(planWork);
          nextPhase.buildStepWork(db);
          nextPhase.buildPhaseObject(db);
          if (nextPhase.getPhase().getRandom()) {
            nextPhase.buildStepWork(db);
            nextPhase.startRandomPhase(db);
          }
          if (phaseWork.getPhase().getRandom() && !nextPhase.getPhase().getRandom()
               && nextPhase.getItemWorkList() != null && nextPhase.getItemWorkList().size() > 0) {
            ActionItemWork nextStep = (ActionItemWork) nextPhase.getItemWorkList().get(0);
            nextStep.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            nextStep.setPlanWork(planWork);
            nextStep.updateStatus(db);
          }
          //action plan work current phase needs to be set
          planWork.setCurrentPhaseId(nextPhase.getActionPhaseId());
        }
      }

      //Update the action plan work
      planWork.update(db);

      if (commit) {
        db.commit();
      }
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
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  nextStep          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean updateStatus(Connection db, ActionItemWork nextStep) throws SQLException {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //update the status of the current step
      if (allowSkipToHere) {
        this.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
      }
      if (this.getStartDate() == null) {
        this.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
      }
      this.setEndDate(new java.sql.Timestamp(new java.util.Date().getTime()));
      this.updateStatus(db);
      //update the status of the next step if it exists
      if (nextStep != null) {
        nextStep.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        nextStep.setPlanWork(this.getPlanWork());
        if (!nextStep.allowsUpdate()) {
          //if step does not allow updates, then steps need to be jumped
          ActionItemWorkList actionPlanSteps = new ActionItemWorkList();
          actionPlanSteps.setPlanWorkId(planWork.getId());
          actionPlanSteps.buildList(db);

          ActionItemWorkList jumpSteps = this.getJumpedSteps(actionPlanSteps);
          jumpSteps.updateStatus(db, ActionPlanWork.UNDEFINED);
          //steps have been skipped. determine the current step and update it.
          ActionItemWork currentStep = this.getNextCurrentItem(actionPlanSteps);
          if (currentStep != null) {
            currentStep.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            currentStep.setPlanWork(this.getPlanWork());
            currentStep.updateStatus(db);
            //action plan work current phase needs to be set
            ActionPhaseWork currentPhase = new ActionPhaseWork(db, currentStep.getPhaseWorkId());
            this.getPlanWork().setCurrentPhaseId(currentPhase.getActionPhaseId());
            this.getPlanWork().update(db);
          }
        } else {
          nextStep.updateStatus(db);
        }
      }
      if (commit) {
        db.commit();
      }
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
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void revertStatus(Connection db) throws SQLException {
    boolean commit = false;
    boolean allStepsComplete = false;
    boolean completedOneSkippedStep = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      ActionPhaseWork phaseWork = new ActionPhaseWork();
      phaseWork.setBuildStepWork(true);
      phaseWork.setBuildPhase(true);
      phaseWork.queryRecord(db, this.getPhaseWorkId());
      allStepsComplete = phaseWork.allStepsComplete();

      PreparedStatement pst = db.prepareStatement(
          "UPDATE action_item_work " +
          "SET status_id = ?, start_date = ?, end_date = ? " +
          "WHERE item_work_id = ? ");
      int i = 0;
      DatabaseUtils.setInt(pst, ++i, statusId);
      DatabaseUtils.setTimestamp(pst, ++i, startDate);
      DatabaseUtils.setTimestamp(pst, ++i, endDate);
      pst.setInt(++i, id);
      pst.executeUpdate();
      pst.close();

      phaseWork = new ActionPhaseWork();
      phaseWork.setBuildStepWork(true);
      phaseWork.setBuildPhase(true);
      phaseWork.queryRecord(db, this.getPhaseWorkId());

      //Action Plan Work gets a new modified date when a step changes status
      ActionPlanWork planWork = null;
      if (this.getPlanWork() != null) {
        this.getPlanWork().setBuildPhaseWork(true);
        this.getPlanWork().buildPhaseWork(db);
        planWork = this.getPlanWork();
      } else {
        planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
        planWork.setBuildPhaseWork(true);
        planWork.buildPhaseWork(db);
        this.setPlanWork(planWork);
      }
      planWork.setModifiedBy(this.getModifiedBy());
      if (allowSkipToHere) {
        //TODO: Add additional filters to restrict the size of the list
        ActionItemWorkList actionPlanSteps = new ActionItemWorkList();
        actionPlanSteps.setPlanWorkId(planWork.getId());
        actionPlanSteps.buildList(db);

        ActionItemWorkList skippedSteps = this.getAlreadySkippedSteps(actionPlanSteps, true);
        skippedSteps.revertStatus(db, ActionPlanWork.INCOMPLETE);
        ActionPhaseWorkList skippedPhases = new ActionPhaseWorkList();
        skippedPhases.setPhaseWorks(skippedSteps.getPhaseWorks(db));
        skippedPhases.reset(db);
        //action plan work current phase needs to be set
        if (skippedSteps != null && skippedSteps.size() > 0) {
          ActionItemWork previousStep = (ActionItemWork) skippedSteps.get(skippedSteps.size() - 1);
          if (previousStep.getStartDate() == null) {
            previousStep.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
          }
          previousStep.revertStatus(db, (ActionItemWork) null);
          //Update the current step so that it is not the current step any more.
          this.setStartDate((java.sql.Timestamp) null);
          this.update(db);
          ActionPhaseWork previousPhaseWork = new ActionPhaseWork();
          previousPhaseWork.setBuildPhase(true);
          previousPhaseWork.setBuildStepWork(true);
          previousPhaseWork.setBuildLinkedObject(true);
          previousPhaseWork.queryRecord(db, previousStep.getPhaseWorkId());
          previousPhaseWork.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
          previousPhaseWork.setEndDate((java.sql.Timestamp) null);
          previousPhaseWork.update(db);
          planWork.setCurrentPhaseId(previousPhaseWork.getActionPhaseId());
          if (previousPhaseWork.getPhase().getRandom()) {
            previousPhaseWork.getItemWorkList().startRandomSteps(db);
          }
          planWork.update(db);
        } else {
          completedOneSkippedStep = true;
        }
      }
      // another conditional block to handle skipped steps. reset the
      // current phase and the current step depending on the phase and step.
      if (allStepsComplete) {
        if (!allowSkipToHere || completedOneSkippedStep) {
          planWork.setCurrentPhaseId(phaseWork.getActionPhaseId());
        }
        //Update the action plan work
        planWork.update(db);

        ActionPhaseWork nextPhase = phaseWork.getNextPhase(db);
        if (nextPhase != null && nextPhase.getId() != -1) {
          nextPhase.setBuildStepWork(true);
          nextPhase.setBuildPhase(true);
          nextPhase.queryRecord(db, nextPhase.getId());
          boolean changedNextPhase = false;
          if (nextPhase.getStartDate() != null) {
            changedNextPhase = true;
            nextPhase.setStartDate((java.sql.Timestamp) null);
            nextPhase.update(db);
            //TODO:: if the next phase is random, then reset all the phase steps.
            if (nextPhase.getPhase().getRandom()) {
              nextPhase.buildStepWork(db);
              nextPhase.getItemWorkList().reset(db);
            } else if (!nextPhase.getPhase().getRandom()) {
              ActionItemWork item = (ActionItemWork) nextPhase.getItemWorkList().get(0);
              item.setStartDate((java.sql.Timestamp) null);
              item.setStatusId(ActionPlanWork.INCOMPLETE);
              item.update(db);
            }
          }
        }
      }

      if (commit) {
        db.commit();
      }
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
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  nextStep          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void revertStatus(Connection db, ActionItemWork nextStep) throws SQLException {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //revert the status of this step
      String nullStr = null;
      this.setEndDate(nullStr);
      this.setStatusId(ActionPlanWork.INCOMPLETE);
      this.revertStatus(db);
      //revert the status of the next step if it exists
      if (nextStep != null) {
        nextStep.setStartDate(nullStr);
        if (!nextStep.allowsUpdate()) {
          //step does not allow updates. step(s) need to be skipped
          ActionItemWorkList actionPlanSteps = new ActionItemWorkList();
          actionPlanSteps.setPlanWorkId(planWork.getId());
          actionPlanSteps.buildList(db);

          ActionItemWorkList jumpSteps = this.getJumpedSteps(actionPlanSteps);
          jumpSteps.reset(db);

          //steps were skipped. determine the current step and update it.
          ActionItemWork currentStep = this.getNextCurrentItem(actionPlanSteps);
          if (currentStep != null) {
            currentStep.setStartDate(nullStr);
            currentStep.revertStatus(db);
          }
        } else {
          nextStep.revertStatus(db);
        }
      }

      if (commit) {
        db.commit();
      }
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
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //delete action item work notes
      ActionItemWorkNoteList notes = new ActionItemWorkNoteList();
      notes.setItemWorkId(id);
      notes.buildList(db);
      notes.delete(db);
      //delete action item work selections
      ActionItemWorkSelectionList selections = new ActionItemWorkSelectionList();
      selections.setItemWorkId(id);
      selections.buildList(db);
      selections.delete(db);
      //delete action item work record
      Statement st = db.createStatement();
      st.executeUpdate("DELETE FROM action_item_work WHERE item_work_id = " + this.getId());
      st.close();

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
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("item_work_id");
    phaseWorkId = rs.getInt("phase_work_id");
    actionStepId = rs.getInt("action_step_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    ownerId = rs.getInt("owner");
    linkModuleId = DatabaseUtils.getInt(rs, "link_module_id");
    linkItemId = DatabaseUtils.getInt(rs, "link_item_id");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    level = rs.getInt("level");
    //record keeping
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    //Action Step
    stepDescription = rs.getString("description");
    permissionType = DatabaseUtils.getInt(rs, "permission_type");
    estimatedDuration = DatabaseUtils.getInt(rs, "estimated_duration", 0);
    parentId = DatabaseUtils.getInt(rs, "parent_id", 0);
    actionId = DatabaseUtils.getInt(rs, "action_id");
    allowSkipToHere = rs.getBoolean("allow_skip_to_here");
    actionRequired = rs.getBoolean("action_required");
    label = rs.getString("label");
    targetRelationship = rs.getString("target_relationship");
    allowUpdate = rs.getBoolean("allow_update");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    level = this.retrieveNextLevel(db);
    id = DatabaseUtils.getNextSeq(db, "action_item_work_item_work_id_seq");
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO action_item_work (" +
        (id > -1 ? "item_work_id, " : "") +
        "phase_work_id, action_step_id, status_id, owner, ");
    if (startDate != null) {
      sql.append("start_date, ");
    }
    if (endDate != null) {
      sql.append("end_date, ");
    }
    sql.append("" + DatabaseUtils.addQuotes(db, "level") + ", link_module_id, link_item_id, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredby, modifiedby ) ");
    sql.append("VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ");
    if (startDate != null) {
      sql.append("?, ");
    }
    if (endDate != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ");
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
    pst.setInt(++i, this.getPhaseWorkId());
    pst.setInt(++i, this.getActionStepId());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setInt(pst, ++i, this.getOwnerId());
    if (startDate != null) {
      pst.setTimestamp(++i, startDate);
    }
    if (endDate != null) {
      pst.setTimestamp(++i, endDate);
    }
    pst.setInt(++i, this.getLevel());
    DatabaseUtils.setInt(pst, ++i, this.getLinkModuleId());
    DatabaseUtils.setInt(pst, ++i, this.getLinkItemId());
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
    id =
        DatabaseUtils.getCurrVal(db, "action_item_work_item_work_id_seq", id);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasAttachment() {
    if (linkModuleId != -1) {
      return true;
    }
    return false;
  }


  /**
   *  Gets the statusGraphicTag attribute of the ActionItemWork object
   *
   * @param  systemStatus  Description of the Parameter
   * @return               The statusGraphicTag value
   */
  public String getStatusGraphicTag(SystemStatus systemStatus) {
    if (statusId == ActionPlanWork.COMPLETED) {
      return "<img border=\"0\" src=\"images/box-checked.gif\" align=\"absmiddle\" alt=\"" + StringUtils.toHtml(systemStatus.getLabel("actionPlan.completed")) + "\">";
    } else if (statusId == ActionPlanWork.SKIPPED) {
      return "<img border=\"0\" src=\"images/box-closed.gif\" align=\"absmiddle\" alt=\"" + StringUtils.toHtml(systemStatus.getLabel("actionPlan.skipped")) + "\">";
    } else {
      return "<img border=\"0\" src=\"images/box.gif\" align=\"absmiddle\">";
    }
  }


  /**
   *  Gets the nextItem attribute of the ActionItemWork object
   *
   * @param  db                Description of the Parameter
   * @return                   The nextItem value
   * @exception  SQLException  Description of the Exception
   */
  public ActionItemWork getNextItem(Connection db) throws SQLException {
    if (this.getId() == -1) {
      new SQLException("Action Item Work ID not specified");
    }
    //populate the current phase
    ActionPhaseWork currentPhase = new ActionPhaseWork(db, this.getPhaseWorkId());
    //build a list of steps for the current phase
    ActionItemWorkList itemWorkList = new ActionItemWorkList();
    itemWorkList.setPhaseWorkId(phaseWorkId);
    itemWorkList.buildList(db);
    //return the next step if found
    Iterator i = itemWorkList.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      if (thisItem.getId() == this.getId()) {
        if (i.hasNext()) {
          return (ActionItemWork) i.next();
        }
      }
    }
    //This was the last step in the current phase. Check if next phase exists.
    ActionPhaseWorkList phaseWorkList = new ActionPhaseWorkList();
    phaseWorkList.setPlanWorkId(currentPhase.getPlanWorkId());
    phaseWorkList.buildList(db);
    Iterator j = phaseWorkList.iterator();
    while (j.hasNext()) {
      ActionPhaseWork thisPhase = (ActionPhaseWork) j.next();
      if (thisPhase.getId() == currentPhase.getId()) {
        if (j.hasNext()) {
          ActionPhaseWork nextPhase = (ActionPhaseWork) j.next();
          nextPhase.buildStepWork(db);
          if (nextPhase.getItemWorkList().size() > 0) {
            return (ActionItemWork) nextPhase.getItemWorkList().get(0);
          }
        }
      }
    }
    //action plan is complete
    return null;
  }


  /**
   *  Gets the skippedSteps attribute of the ActionItemWork object
   *
   * @param  actionPlanSteps  Description of the Parameter
   * @return                  The skippedSteps value
   */
  private ActionItemWorkList getSkippedSteps(ActionItemWorkList actionPlanSteps) {
    ActionItemWorkList skippedSteps = new ActionItemWorkList();
    Iterator i = actionPlanSteps.iterator();
    while (i.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) i.next();
      if (itemWork.getId() != this.getId()) {
        if (itemWork.getEndDate() == null && itemWork.getStatusId() == -1) {
          skippedSteps.add(itemWork);
        }
      } else {
        break;
      }
    }
    return skippedSteps;
  }


  /**
   *  Description of the Method
   *
   * @param  skippedSteps  Description of the Parameter
   */
  private void removeGlobalSteps(ActionItemWorkList skippedSteps) {
    Iterator i = skippedSteps.iterator();
    while (i.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) i.next();
      if (planWork.getPhaseWorkList().getPhaseWorkById(itemWork.getPhaseWorkId()).isGlobal()) {
        i.remove();
      }
    }
  }


  /**
   *  Gets the jumpedSteps attribute of the ActionItemWork object
   *
   * @param  actionPlanSteps  Description of the Parameter
   * @return                  The jumpedSteps value
   */
  private ActionItemWorkList getJumpedSteps(ActionItemWorkList actionPlanSteps) {
    ActionItemWorkList jumpedSteps = new ActionItemWorkList();
    Iterator i = actionPlanSteps.iterator();
    //Get to this step
    while (i.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) i.next();
      if (itemWork.getId() == this.getId()) {
        break;
      }
    }
    //Add all the steps that need to be jumped
    while (i.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) i.next();
      if (!itemWork.allowsUpdate()) {
        jumpedSteps.add(itemWork);
      } else {
        break;
      }
    }
    return jumpedSteps;
  }


  /**
   *  Gets the nextCurrentItem attribute of the ActionItemWork object
   *
   * @param  actionPlanSteps  Description of the Parameter
   * @return                  The nextCurrentItem value
   */
  private ActionItemWork getNextCurrentItem(ActionItemWorkList actionPlanSteps) {
    ActionItemWork itemWork = null;
    Iterator i = actionPlanSteps.iterator();
    //Get to this step
    while (i.hasNext()) {
      itemWork = (ActionItemWork) i.next();
      if (itemWork.getId() == this.getId()) {
        break;
      }
    }
    //Skip all the steps that have been jumped
    while (i.hasNext()) {
      itemWork = (ActionItemWork) i.next();
      if (itemWork.allowsUpdate()) {
        break;
      }
    }
    return itemWork;
  }


  /**
   *  Gets the alreadySkippedSteps attribute of the ActionItemWork object
   *
   * @param  actionPlanSteps  Description of the Parameter
   * @return                  The alreadySkippedSteps value
   */
  private ActionItemWorkList getAlreadySkippedSteps(ActionItemWorkList actionPlanSteps) {
    ActionItemWorkList skippedSteps = new ActionItemWorkList();
    int startingId = -1;
    Iterator i = actionPlanSteps.iterator();
    while (i.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) i.next();
      //TODO::Check if all the steps are already skipped.
      if (startingId == -1 && (itemWork.getEndDate() != null) && itemWork.getStatusId() == ActionPlanWork.SKIPPED) {
        startingId = itemWork.getId();
        if (this.getPlanWork().getPhaseWorkList().getPhaseWorkById(itemWork.getPhaseWorkId()).getPhase().getRandom()) {
          //TODO::Restart all the incomplete steps in the random phase
        }
      } else if ((startingId != -1) && (itemWork.getEndDate() != null) && (itemWork.getId() != this.getId()) && (itemWork.getStatusId() != ActionPlanWork.SKIPPED)) {
        startingId = -1;
      }
      if (itemWork.getId() != this.getId() && startingId != -1) {
        if ((itemWork.getEndDate() != null) && itemWork.getStatusId() == ActionPlanWork.SKIPPED) {
          skippedSteps.add(itemWork);
        }
      } else if (itemWork.getId() != this.getId() && startingId == -1 && skippedSteps.size() > 0) {
        skippedSteps = new ActionItemWorkList();
      } else if (itemWork.getId() == this.getId()) {
        break;
      }
    }
    return skippedSteps;
  }


  /**
   *  Gets the alreadySkippedSteps attribute of the ActionItemWork object
   *
   * @param  actionPlanSteps  Description of the Parameter
   * @param  includesRandom   Description of the Parameter
   * @return                  The alreadySkippedSteps value
   */
  private ActionItemWorkList getAlreadySkippedSteps(ActionItemWorkList actionPlanSteps, boolean includesRandom) {
    ActionItemWorkList skippedSteps = new ActionItemWorkList();
    boolean canContinue = true;
    int i = actionPlanSteps.getIndexById(this.getId());
    for (; i > -1 && canContinue; --i) {
      ActionItemWork itemWork = (ActionItemWork) actionPlanSteps.get(i);
      if (itemWork.getStatusId() == ActionPlanWork.COMPLETED && !this.getPlanWork().getPhaseWorkList().getPhaseWorkById(itemWork.getPhaseWorkId()).getPhase().getGlobal()) {
        canContinue = false;
        if (this.getPlanWork().getPhaseWorkList().getPhaseWorkById(itemWork.getPhaseWorkId()).getPhase().getRandom()) {
          //Add all incomplete steps in the current random phase
          int counter = 0;
          while (true) {
            if (i >= 1) {
              int j = 0;
              boolean flag = true;
              for (j = 0; flag; j++) {
                ActionItemWork randomWork = (ActionItemWork) actionPlanSteps.get(i - j);
                flag = (itemWork.getPhaseWorkId() == randomWork.getPhaseWorkId());
                if (randomWork.getStatusId() == ActionPlanWork.SKIPPED && flag) {
                  skippedSteps.add(randomWork);
                }
                //check for the begining of the 
                if (randomWork.getParentId() == 0) {
                  flag = false;
                }
              }
              return skippedSteps;
            } else {
              return skippedSteps;
            }
          }
        }
        return skippedSteps;
      } else if (itemWork.getStatusId() == ActionPlanWork.SKIPPED) {
        skippedSteps.add(itemWork);
      }
    }
    return skippedSteps;
  }


  /**
   *  Determines if this is the last step in the action phase it belongs to
   *
   * @param  db                Description of the Parameter
   * @return                   The lastStep value
   * @exception  SQLException  Description of the Exception
   */
  public boolean isLastStep(Connection db) throws SQLException {
    if (this.getId() == -1) {
      new SQLException("Action Item Work ID not specified");
    }
    //build a list of steps for the current phase
    ActionItemWorkList itemWorkList = new ActionItemWorkList();
    itemWorkList.setPhaseWorkId(phaseWorkId);
    itemWorkList.buildList(db);
    //check if this is the last step
    Iterator i = itemWorkList.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      if (thisItem.getId() == this.getId()) {
        if (i.hasNext()) {
          return false;
        }
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
  private int retrieveNextLevel(Connection db) throws SQLException {
    int returnLevel = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT MAX(" + DatabaseUtils.addQuotes(db, "level") + ") AS levelcount " +
        "FROM action_item_work " +
        "WHERE phase_work_id = ? ");
    pst.setInt(1, phaseWorkId);
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
   * @param  context        Description of the Parameter
   * @param  next           Description of the Parameter
   * @param  stepName       Description of the Parameter
   * @param  template       Description of the Parameter
   * @param  actionPlan     Description of the Parameter
   * @return                Description of the Return Value
   * @exception  Exception  Description of the Exception
   */
  public boolean sendEmail(ActionContext context, ActionPlanWork actionPlan, Contact next, String stepName, String template) throws Exception {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    ActionPlanEmail planEmail = new ActionPlanEmail(template, actionPlan, next, stepName, context);
    // Prepare the email
    SMTPMessage mail = new SMTPMessage();
    mail.setHost(prefs.get("MAILSERVER"));
    mail.setFrom(prefs.get("EMAILADDRESS"));
    mail.addReplyTo(prefs.get("EMAILADDRESS"));
    mail.setType("text/html");
    mail.setSubject(planEmail.getSubject());
    mail.setBody(planEmail.getBody());
    if (next.getPrimaryEmailAddress() != null && !"".equals(next.getPrimaryEmailAddress())) {
      mail.addTo(next.getPrimaryEmailAddress());
      System.out.println("ADDING: " + next.getPrimaryEmailAddress());
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


  /**
   *  Description of the Method
   *
   * @param  context         Description of the Parameter
   * @param  actionPlan      Description of the Parameter
   * @param  next            Description of the Parameter
   * @param  stepName        Description of the Parameter
   * @param  template        Description of the Parameter
   * @param  stepCompletion  Description of the Parameter
   * @return                 Description of the Return Value
   * @exception  Exception   Description of the Exception
   */
  public boolean sendEmail(ActionContext context, ActionPlanWork actionPlan, Contact next, String stepName, String template, String stepCompletion) throws Exception {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    ActionPlanEmail planEmail = new ActionPlanEmail(template, actionPlan, next, stepName, context, stepCompletion);
    // Prepare the email
    SMTPMessage mail = new SMTPMessage();
    mail.setHost(prefs.get("MAILSERVER"));
    mail.setFrom(prefs.get("EMAILADDRESS"));
    mail.addReplyTo(prefs.get("EMAILADDRESS"));
    mail.setType("text/html");
    mail.setSubject(planEmail.getSubject());
    mail.setBody(planEmail.getBody());
    if (next.getPrimaryEmailAddress() != null && !"".equals(next.getPrimaryEmailAddress())) {
      mail.addTo(next.getPrimaryEmailAddress());
      System.out.println("ADDING: " + next.getPrimaryEmailAddress());
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


  /**
   *  Gets the step attribute of the ActionItemWork object
   *
   * @return    The step value
   */
  public ActionStep getStep() {
    return step;
  }


  /**
   *  Sets the step attribute of the ActionItemWork object
   *
   * @param  tmp  The new step value
   */
  public void setStep(ActionStep tmp) {
    this.step = tmp;
  }


  /**
   *  Gets the groupUsers attribute of the ActionItemWork object
   *
   * @return    The groupUsers value
   */
  public UserList getGroupUsers() {
    return groupUsers;
  }


  /**
   *  Sets the groupUsers attribute of the ActionItemWork object
   *
   * @param  tmp  The new groupUsers value
   */
  public void setGroupUsers(UserList tmp) {
    this.groupUsers = tmp;
  }


  /**
   *  Gets the roleUsers attribute of the ActionItemWork object
   *
   * @return    The roleUsers value
   */
  public UserList getRoleUsers() {
    return roleUsers;
  }


  /**
   *  Sets the roleUsers attribute of the ActionItemWork object
   *
   * @param  tmp  The new roleUsers value
   */
  public void setRoleUsers(UserList tmp) {
    this.roleUsers = tmp;
  }


  /**
   *  Gets the departmentUsers attribute of the ActionItemWork object
   *
   * @return    The departmentUsers value
   */
  public UserList getDepartmentUsers() {
    return departmentUsers;
  }


  /**
   *  Sets the departmentUsers attribute of the ActionItemWork object
   *
   * @param  tmp  The new departmentUsers value
   */
  public void setDepartmentUsers(UserList tmp) {
    this.departmentUsers = tmp;
  }


  /**
   *  Gets the userGroupName attribute of the ActionItemWork object
   *
   * @return    The userGroupName value
   */
  public String getUserGroupName() {
    return userGroupName;
  }


  /**
   *  Sets the userGroupName attribute of the ActionItemWork object
   *
   * @param  tmp  The new userGroupName value
   */
  public void setUserGroupName(String tmp) {
    this.userGroupName = tmp;
  }


  /**
   *  Gets the departmentName attribute of the ActionItemWork object
   *
   * @return    The departmentName value
   */
  public String getDepartmentName() {
    return departmentName;
  }


  /**
   *  Sets the departmentName attribute of the ActionItemWork object
   *
   * @param  tmp  The new departmentName value
   */
  public void setDepartmentName(String tmp) {
    this.departmentName = tmp;
  }


  /**
   *  Gets the roleName attribute of the ActionItemWork object
   *
   * @return    The roleName value
   */
  public String getRoleName() {
    return roleName;
  }


  /**
   *  Sets the roleName attribute of the ActionItemWork object
   *
   * @param  tmp  The new roleName value
   */
  public void setRoleName(String tmp) {
    this.roleName = tmp;
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
   *  Description of the Method
   *
   */
  public void checkPreviousSteps(Connection db, ActionPlanWork tmpPlanWork) throws SQLException {
		boolean quickComplete = this.getStep().getQuickComplete();
		boolean checkComplete = true;
		if (quickComplete) {
			Iterator nextPhaseIter = tmpPlanWork.getPhaseWorkList().iterator();
			while (nextPhaseIter.hasNext()) {
				ActionPhaseWork thisPhaseWork = (ActionPhaseWork) nextPhaseIter.next();
				thisPhaseWork.buildStepWork(db);
				ActionItemWorkList actionItemWorkList = thisPhaseWork.getItemWorkList();
				int noOfItems = actionItemWorkList.size();
				int itemCounter = 0;
				while ((itemCounter < noOfItems) && checkComplete){
					ActionItemWork thisItemWork = (ActionItemWork) actionItemWorkList.get(itemCounter);
					thisItemWork.setStatusId(ActionPlanWork.COMPLETED);
					if ((itemCounter + 1) == noOfItems) {
						if (thisItemWork.getId() != id) {
							thisItemWork.updateStatus(db, null);
						}
					} else {
						if (thisItemWork.getId() != id) {
							ActionItemWork nextItemWork = (ActionItemWork) actionItemWorkList.get(itemCounter+1);
							thisItemWork.updateStatus(db, nextItemWork);
						}
					}
					if (thisItemWork.getId() == id) {
						checkComplete = false;
					}
					itemCounter++;
				}
			} 
		}
  }
}

