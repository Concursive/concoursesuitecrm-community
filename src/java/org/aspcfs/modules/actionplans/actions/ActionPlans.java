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
package org.aspcfs.modules.actionplans.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.relationships.base.Relationship;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 29, 2005
 * @version
 */
public final class ActionPlans extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAttach(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String item = context.getRequest().getParameter("item");
      String itemId = context.getRequest().getParameter("itemId");
      ActionItemWork itemWork = new ActionItemWork(db, Integer.parseInt(itemId));
      if (item != null) {
        if ("contact".equals(item.trim())) {
          String contactId = context.getRequest().getParameter("contactId");
          itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS));
          itemWork.setLinkItemId(contactId);
        } else if ("opportunity".equals(item.trim())) {
          //attaching an opportunity component with the step.
          String oppId = context.getRequest().getParameter("oppId");
          itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.PIPELINE_COMPONENT));
          itemWork.setLinkItemId(oppId);
        } else if ("fileitem".equals(item.trim())) {
          String fileId = context.getRequest().getParameter("fileId");
          itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
          itemWork.setLinkItemId(fileId);
        } else if ("note".equals(item.trim())) {
          String noteId = context.getRequest().getParameter("noteId");
          itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACTION_ITEM_WORK_NOTE_OBJECT));
          itemWork.setLinkItemId(noteId);
        } else if ("selection".equals(item.trim())) {
          String selectionId = context.getRequest().getParameter("selectionId");
          itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACTION_ITEM_WORK_SELECTION_OBJECT));
          itemWork.setLinkItemId(selectionId);
        } else if ("relation".equals(item.trim())) {
          String relationId = context.getRequest().getParameter("relationId");
          itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACTION_ITEM_WORK_RELATIONSHIP_OBJECT));
          itemWork.setLinkItemId(relationId);
        } else if ("folder".equals(item.trim())) {
          String recordId = context.getRequest().getParameter("recordId");
          itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
          itemWork.setLinkItemId(recordId);
        }
      }
      itemWork.attach(db);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "-none-";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandResetFolderAttachment(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String item = context.getRequest().getParameter("item");
      String itemId = context.getRequest().getParameter("itemId");
      String categoryId = context.getRequest().getParameter("categoryId");
      ActionItemWork itemWork = new ActionItemWork(db, Integer.parseInt(itemId));
      ActionPhaseWork phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
      ActionPlanWork planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
      planWork.buildLinkedObject(db);
      itemWork.setPlanWork(planWork);
      itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
      CustomFieldCategory category = new CustomFieldCategory(db, Integer.parseInt(categoryId));
      itemWork.setCustomFieldCategory(category);
      itemWork.deleteFolderAttachment(db);
      if (itemWork.getLinkItemId() != -1) {
        categoryId = String.valueOf(CustomFieldCategory.getIdFromRecord(db, itemWork.getLinkItemId()));
        category = new CustomFieldCategory(db, Integer.parseInt(categoryId));
        category.setRecordId(itemWork.getLinkItemId());
        category.setLinkModuleId(Constants.ACCOUNTS);
        category.setLinkItemId(planWork.getOrganization().getOrgId());
        category.buildResources(db);
        CustomFieldRecord record = new CustomFieldRecord(db, itemWork.getLinkItemId());
        record.buildColumns(db, category);
        itemWork.setCustomFieldCategory(category);
        context.getRequest().setAttribute("Record", record);
        context.getRequest().setAttribute("actionItemWork", itemWork);
        return "ResetFolderAttachmentOK";
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "-none-";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandReassign(ActionContext context) {
    Exception errorMessage = null;
    String returnStr = context.getRequest().getParameter("return");

    Connection db = null;
    boolean status = false;
    String userId = null;
    int prev = -1;
    User previous = null;
    User assigned = null;
    User manager = null;
    Contact previousContact = null;
    Contact assignedContact = null;
    Contact managerContact = null;
    ActionPlanWork planWork = new ActionPlanWork();
    try {
      db = this.getConnection(context);
      String actionPlanId = context.getRequest().getParameter("actionPlanId");
      userId = context.getRequest().getParameter("userId");

      planWork.setBuildPhaseWork(true);
      planWork.setBuildStepWork(true);
      planWork.setBuildLinkedObject(true);
      planWork.queryRecord(db, Integer.parseInt(actionPlanId));

      prev = planWork.getAssignedTo();

      if (userId != null && Integer.parseInt(userId) > 0) {
        status = planWork.reassign(db, Integer.parseInt(userId));
      }
      if (status) {
        //build contact details
        context.getRequest().setAttribute("actionPlanId", String.valueOf(planWork.getId()));
        assigned = this.getUser(context, planWork.getAssignedTo());
        assignedContact = new Contact(db, assigned.getContactId());
        manager = this.getUser(context, planWork.getManagerId());
        managerContact = new Contact(db, manager.getContactId());
        previous = this.getUser(context, prev);
        previousContact = new Contact(db, previous.getContactId());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (status && planWork.getId() > -1) {
      System.out.println("Process Emails....");
      try {
        String templateFile = getDbNamePath(context) + "templates_" + getUserLanguage(context) + ".xml";
        if (!FileUtils.fileExists(templateFile)) {
          templateFile = getDbNamePath(context) + "templates_en_US.xml";
        }
        //Send an email to the Action Plan new "owner", previous "owner" and the "manager"
        //build details
        planWork.sendEmail(
            context,
            assignedContact,
            managerContact,
            previousContact,
            planWork.getLinkItemName(),
            templateFile);
      } catch (Exception e) {
        e.printStackTrace(System.out);
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      }
    }
    if (errorMessage == null) {
      if (returnStr != null && "list".equals(returnStr)) {
        return "ReassignListOK";
      }
      return "ReassignDetailsOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      String actionPlanId = context.getRequest().getParameter("actionPlanId");
      String enabled = context.getRequest().getParameter("enabled");

      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(actionPlanId));
      if (enabled != null && "false".equals(enabled)) {
        planWork.setEnabled(false);
      } else {
        planWork.setEnabled(true);
      }
      planWork.update(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "EnableListOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmRevert(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      String planWorkId = context.getRequest().getParameter("actionPlanId");
      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
      planWork.buildPhaseWork(db);
      planWork.buildLinkedObject(db);
      DependencyList dependencies = planWork.getOrganization().processResetToLeadDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (planWork.getTicket() != null) {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.ticketActionPlansCanNotRevertAccounts", "Ticket Action Plans can not be used to rever the account contacts back to leads"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      } else if (!dependencies.canDelete()) {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.unableToRevertAccountsToLeads", "Unable to revert the account contacts back to leads due to the following dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("account.revertToLeadDependencies"));
        htmlDialog.addButton(systemStatus.getLabel("sales.revertBackToLead"), "javascript:window.location.href='MyActionPlans.do?command=RevertToLead&actionPlanId=" + planWorkId + "';");
        htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close();");
      }
      context.getSession().setAttribute("Dialog", htmlDialog);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ConfirmRevertOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRevertToLead(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      String planWorkId = context.getRequest().getParameter("actionPlanId");
      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
      planWork.buildPhaseWork(db);
      planWork.buildLinkedObject(db);
      Organization orgDetails = planWork.getOrganization();
      ContactList orgContacts = new ContactList();
      orgContacts.setHasConversionDate(Constants.TRUE);
      orgContacts.setOrgId(orgDetails.getOrgId());
      orgContacts.buildList(db);
      int contactId = orgDetails.revertBackToLead(db, context, this.getUserId(context));
      if (contactId != -1) {
        Contact contact = new Contact(db, contactId);
        context.getRequest().setAttribute("contact", contact);
        Iterator iter = (Iterator) orgContacts.iterator();
        while (iter.hasNext()) {
          Contact thisContact = (Contact) iter.next();
          deleteRecentItem(context, thisContact);
        }
      }
      deleteRecentItem(context, orgDetails);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "RevertToLeadOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpdateGlobalStatus(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("stepId");
      String statusId = context.getRequest().getParameter("statusId");

      if (itemId != null && Integer.parseInt(itemId) > 0) {
        ActionItemWork itemWork = new ActionItemWork();
        itemWork.setBuildStep(true);
        itemWork.queryRecord(db, Integer.parseInt(itemId));

        if (itemWork.isComplete()) {
          itemWork.setStatusId(ActionPlanWork.INCOMPLETE);
        } else {
          itemWork.setStatusId(ActionPlanWork.COMPLETED);
        }
        if (itemWork.getStartDate() == null) {
          itemWork.setStartDate(
              new java.sql.Timestamp(
              new java.util.Date().getTime()));
        }
        itemWork.setEndDate(
            new java.sql.Timestamp(
            new java.util.Date().getTime()));
        itemWork.setModifiedBy(this.getUserId(context));
        itemWork.update(db);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "UpdateGlobalStatusDetailsOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRevertStatus(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("stepId");
      String nextStepId = context.getRequest().getParameter("nextStepId");
      String statusId = context.getRequest().getParameter("statusId");

      if (itemId != null && Integer.parseInt(itemId) > 0) {
        ActionItemWork itemWork = new ActionItemWork();
        itemWork.setBuildStep(true);
        itemWork.queryRecord(db, Integer.parseInt(itemId));
        if (statusId != null && Integer.parseInt(statusId) > 0) {
          itemWork.setStatusId(statusId);
        } else {
          itemWork.setStatusId(-1);
        }
        itemWork.setModifiedBy(this.getUserId(context));

        //Build the plan work
        ActionPhaseWork phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
        ActionPlanWork planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
        itemWork.setPlanWork(planWork);

        if (nextStepId != null && Integer.parseInt(nextStepId) > 0) {
          ActionItemWork nextItem = new ActionItemWork(db, Integer.parseInt(nextStepId));
          itemWork.revertStatus(db, nextItem);
        } else {
          itemWork.revertStatus(db, null);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "RevertStatusDetailsOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRestart(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String planWorkId = context.getRequest().getParameter("actionPlanId");
      ActionPlanWork planWork = new ActionPlanWork();
      planWork.setBuildPhaseWork(true);
      planWork.setBuildStepWork(true);
      planWork.queryRecord(db, Integer.parseInt(planWorkId));
      //restart the plan
      planWork.setModifiedBy(this.getUserId(context));
      planWork.restart(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "RestartDetailsOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddNote(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("actionStepWork");

      ActionItemWorkNote thisNote = new ActionItemWorkNote();
      thisNote.setSubmitted(DateUtils.roundUpToNextFive(System.currentTimeMillis()));
      context.getRequest().setAttribute("actionItemWorkNote", thisNote);

      ActionItemWork itemWork = new ActionItemWork();
      itemWork.setBuildLinkedObject(true);
      itemWork.queryRecord(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("actionItemWork", itemWork);
      
      ActionPhaseWork phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
      ActionPlanWork planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
      planWork.setBuildLinkedObject(true);
      planWork.buildLinkedObject(db);
      
      setObjects(context, db, planWork);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "AddNoteOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddSelection(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("actionStepWork");

      ActionItemWork itemWork = new ActionItemWork();
      itemWork.setBuildLinkedObject(true);
      itemWork.queryRecord(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("actionItemWork", itemWork);

      ActionStepLookupList itemList = new ActionStepLookupList();
      itemList.setStepId(itemWork.getActionStepId());
      itemList.buildList(db);
      context.getRequest().setAttribute("itemList", itemList);

      ActionItemWorkSelectionList selectionList = new ActionItemWorkSelectionList();
      selectionList.setItemWorkId(itemWork.getId());
      selectionList.buildList(db);
      
      ActionPhaseWork phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
      ActionPlanWork planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
      planWork.setBuildLinkedObject(true);
      planWork.buildLinkedObject(db);
      
      setObjects(context, db, planWork);
      
      context.getRequest().setAttribute("selectionList", selectionList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "AddSelectionOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddRelation(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);

      String planWorkId = context.getRequest().getParameter("planWorkId");
      String itemId = context.getRequest().getParameter("actionStepWork");
      String orgId = context.getRequest().getParameter("orgId");

      if (orgId != null && !"".equals(orgId) && !"-1".equals(orgId)) {
        thisOrg = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("orgDetails", thisOrg);
      }

      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
      ActionItemWork itemWork = new ActionItemWork();
      itemWork.setPlanWork(planWork);
      itemWork.setBuildLinkedObject(true);
      itemWork.setBuildStep(true);
      itemWork.queryRecord(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("actionItemWork", itemWork);

      //build list of account type filters to be used for this step
      ArrayList accountTypes = itemWork.getStep().getAccountTypes();

      //Prepare pagedListInfo
      PagedListInfo stepRelationOrgListInfo = this.getPagedListInfo(
          context, "stepRelationOrgListInfo");
      stepRelationOrgListInfo.setLink("ActionPlans.do?command=AddRelation&planWorkId=" + planWorkId +
          "&actionStepWork=" + itemId + "&orgId=" + orgId);

      //build a list of accounts based on the step's allowed account types
      OrganizationList accounts = new OrganizationList();
      accounts.setTypes(StringUtils.getCommaSeparated(accountTypes));
      accounts.setPagedListInfo(stepRelationOrgListInfo);
      if (thisOrg != null) {
        if (thisOrg.getSiteId() == -1) {
          accounts.setIncludeOrganizationWithoutSite(true);
        } else {
          accounts.setOrgSiteId(thisOrg.getSiteId());
        }
        //exclude this account
        accounts.setExcludeIds(String.valueOf(thisOrg.getOrgId()));
      }
      accounts.buildList(db);
      context.getRequest().setAttribute("accounts", accounts);

      ArrayList selectedList = (ArrayList) context.getRequest().getAttribute("selectedList");

      if (selectedList == null || "true".equals(
          context.getRequest().getParameter("reset"))) {
        selectedList = new ArrayList();
      }

      //build list of accounts that already have a relationship (type = target_relation)
      //with this action plan's account
      String init = context.getRequest().getParameter("init");
      if (init != null && "true".equals(init)) {
        RelationshipList existingList = itemWork.getRelationshipList();
        if (existingList != null) {
          existingList.getMappedObjectIds(selectedList, itemWork.getTargetRelationship());
        }
      }

      String prevSelection = context.getRequest().getParameter(
          "previousSelection");
      if (prevSelection != null) {
        StringTokenizer st = new StringTokenizer(prevSelection, "|");
        while (st.hasMoreTokens()) {
          selectedList.add(String.valueOf(st.nextToken()));
        }
      }

      int rowCount = 1;
      while (context.getRequest().getParameter("hiddenItemId" + rowCount) != null) {
        int item = Integer.parseInt(context.getRequest().getParameter("hiddenItemId" + rowCount));
        if (context.getRequest().getParameter("item" + rowCount) != null) {
          if (!selectedList.contains(String.valueOf(item))) {
            selectedList.add(String.valueOf(item));
          }
        } else {
          selectedList.remove(String.valueOf(item));
        }
        rowCount++;
      }
      context.getRequest().setAttribute("selectedList", selectedList);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "AddRelationOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAttachRelation(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    boolean isValid = false;
    try {
      db = this.getConnection(context);

      String planWorkId = context.getRequest().getParameter("planWorkId");
      String itemId = context.getRequest().getParameter("actionStepWork");
      String orgId = context.getRequest().getParameter("orgId");

      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
      ActionItemWork itemWork = new ActionItemWork();
      itemWork.setPlanWork(planWork);
      itemWork.setBuildLinkedObject(true);
      itemWork.setBuildStep(true);
      itemWork.queryRecord(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("actionItemWork", itemWork);

      ArrayList finalSelection = new ArrayList();

      String prevSelection = context.getRequest().getParameter(
          "previousSelection");
      if (prevSelection != null) {
        StringTokenizer st = new StringTokenizer(prevSelection, "|");
        while (st.hasMoreTokens()) {
          finalSelection.add(String.valueOf(st.nextToken()));
        }
      }

      int rowCount = 1;
      ArrayList displayList = new ArrayList();
      while (context.getRequest().getParameter("hiddenItemId" + rowCount) != null) {
        int item = Integer.parseInt(context.getRequest().getParameter("hiddenItemId" + rowCount));
        if (context.getRequest().getParameter("item" + rowCount) != null) {
          if (!finalSelection.contains(String.valueOf(item))) {
            finalSelection.add(String.valueOf(item));
          }
        } else {
          finalSelection.remove(String.valueOf(item));
        }
        rowCount++;
      }

      //Iterate through each of the existing relationships and verify if
      //it exists in the final list of relationships and delete if not exists
      RelationshipList existingList = itemWork.getRelationshipList();
      if (existingList != null) {
        Iterator current = (Iterator) existingList.keySet().iterator();
        while (current.hasNext()) {
          ArrayList tempList = (ArrayList) existingList.get((String) current.next());
          Iterator tempIter = (Iterator) tempList.iterator();
          while (tempIter.hasNext()) {
            Relationship existingRelationship = (Relationship) tempIter.next();
            if (!existingRelationship.isPresent(finalSelection, itemWork.getTargetRelationship())) {
              existingRelationship.delete(db);
            }
          }
        }
      }

      int relId = -1;
      if (finalSelection.size() > 0) {
        String relType = itemWork.getTargetRelationship();
        if (relType != null) {
          Iterator t = finalSelection.iterator();
          while (t.hasNext()) {
            String mapTo = (String) t.next();
            Relationship relationship = new Relationship();
            if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
              relationship.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
              relationship.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
            }
            relationship.setObjectIdMapsFrom(planWork.getLinkItemId());
            relationship.setObjectIdMapsTo(Integer.parseInt(mapTo));

            int typeId = -1;
            if (relType.endsWith("_reciprocal")) {
              relationship.setObjectIdMapsFrom(relationship.getObjectIdMapsTo());
              relationship.setObjectIdMapsTo(planWork.getLinkItemId());
              typeId = Integer.parseInt(relType.substring(0, relType.indexOf("_")));
            } else {
              typeId = Integer.parseInt(relType);
            }
            relationship.setTypeId(typeId);

            //Check for duplicates
            RelationshipList thisList = new RelationshipList();
            if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
              thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
            }
            thisList.setObjectIdMapsFrom(relationship.getObjectIdMapsFrom());
            thisList.setTypeId(typeId);
            thisList.buildList(db);

            isValid = this.validateObject(context, db, relationship);
            if (isValid) {
              relationship.setEnteredBy(getUserId(context));
              relationship.setModifiedBy(getUserId(context));
              if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
                //Ignore dulicate records
                if (thisList.checkDuplicateRelationship(
                    db, relationship.getObjectIdMapsTo(), typeId, Constants.ACCOUNT_OBJECT) == 0) {
                  relationship.insert(db);
                  relId = relationship.getId();
                  this.processInsertHook(context, relationship);
                } else {
                  //Found a duplication relationship
                  Relationship duplicate = thisList.getDuplicateRelation(db, 
                      relationship.getObjectIdMapsTo(), typeId, Constants.ACCOUNT_OBJECT);
                  if (duplicate != null) {
                    relId = duplicate.getId();
                  }
                }
              }
            }
          }
        }

        //Build the latest relationships associated with this step
        RelationshipList relationshipList = new RelationshipList();
        if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
          relationshipList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
        }
        relationshipList.setObjectIdMapsFrom(planWork.getLinkItemId());
        relationshipList.setObjectIdMapsTo(-1);
        //set the relationship type
        String targetRelationship = itemWork.getTargetRelationship();
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
        relationshipList.filterAccounts(itemWork.getStep().getAccountTypes());

        context.getRequest().setAttribute("display",
            relationshipList.getDisplayHtml());
      } else {
        //User chose to reset all the relations associated with this step
        context.getRequest().setAttribute("display",
            itemWork.getLabel() != null ? itemWork.getLabel() :
            this.getSystemStatus(context).getLabel("actionPlans.addRelationships.text",
            "Add Relationship"));
        itemWork.resetAttachment(db);
      }
      context.getRequest().setAttribute("actionItemWork", itemWork);
      context.getRequest().setAttribute("status", "true");
      context.getRequest().setAttribute("linkRelation", String.valueOf(relId));
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "AddRelationOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAttachNote(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("itemWorkId");
      String actionId = context.getRequest().getParameter("actionId");
      int count = Integer.parseInt(context.getRequest().getParameter("count"));

      ActionItemWork itemWork = new ActionItemWork();
      itemWork.setBuildLinkedObject(true);
      itemWork.queryRecord(db, Integer.parseInt(itemId));

      ActionItemWorkNote thisNote = (ActionItemWorkNote) context.getFormBean();

      boolean isValid = this.validateObject(context, db, thisNote);
      boolean recordStatus = false;
      if (isValid) {
        if (Integer.parseInt(actionId) == ActionStep.ATTACH_NOTE_SINGLE && count > 0) {
          itemWork.getNote().setDescription(thisNote.getDescription());
          itemWork.getNote().setSubmitted(thisNote.getSubmitted());
          itemWork.getNote().setSubmittedBy(getUserId(context));
          recordStatus = itemWork.getNote().update(db);
        } else {
          thisNote.setSubmittedBy(getUserId(context));
          recordStatus = thisNote.insert(db);
        }
      }
      if (recordStatus) {
        context.getRequest().setAttribute("status", "true");
        itemWork.add(thisNote);
      }

      context.getRequest().setAttribute("actionItemWork", itemWork);
      context.getRequest().setAttribute("actionItemWorkNote", thisNote);
      
      
      ActionPhaseWork phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
      ActionPlanWork planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
      planWork.setBuildLinkedObject(true);
      planWork.buildLinkedObject(db);
      
      setObjects(context, db, planWork);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "AddNoteOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAttachSelection(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("itemWorkId");

      ArrayList newList = new ArrayList();
      ArrayList displayList = new ArrayList();
      int rowCount = 1;
      String elementId = null;
      while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
        elementId = context.getRequest().getParameter("hiddenelementid" + rowCount);
        if (elementId != null && Integer.parseInt(elementId) > 0) {
          if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
            newList.add(elementId);
            if (context.getRequest().getParameter("elementvalue" + rowCount) != null) {
              displayList.add(context.getRequest().getParameter("elementvalue" + rowCount));
            }
          }
        }
        rowCount++;
      }

      ActionItemWorkSelectionList compareList = new ActionItemWorkSelectionList();
      compareList.setItemWorkId(itemId);
      compareList.buildList(db);

      Iterator i = compareList.iterator();
      while (i.hasNext()) {
        ActionItemWorkSelection thisItem = (ActionItemWorkSelection) i.next();
        if (!thisItem.hasValue(newList)) {
          //System.out.println("DELETING: " + thisItem.getSelection() + ", " + thisItem.getDescription());
          thisItem.delete(db);
        }
      }

      ActionItemWorkSelection thisItem = null;
      Iterator k = newList.iterator();
      while (k.hasNext()) {
        String param = (String) k.next();
        if (!compareList.hasSelection(Integer.parseInt(param))) {
          thisItem = new ActionItemWorkSelection();
          thisItem.setItemWorkId(itemId);
          thisItem.setSelection(param);
          //System.out.println("INSERTING: " + thisItem.getSelection());
          thisItem.insert(db);
        }
      }

      ActionItemWork itemWork = new ActionItemWork(db, Integer.parseInt(itemId));
      
      ActionPhaseWork phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
      ActionPlanWork planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
      planWork.setBuildLinkedObject(true);
      planWork.buildLinkedObject(db);
      
      setObjects(context, db, planWork);
      
      context.getRequest().setAttribute("actionItemWork", itemWork);
      context.getRequest().setAttribute("status", "true");
      context.getRequest().setAttribute("display", StringUtils.getLineSeparated(displayList));
      context.getRequest().setAttribute("linkSelection", elementId);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "AddSelectionOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewNotes(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String planWorkId = context.getRequest().getParameter("planWorkId");
      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
      planWork.setBuildLinkedObject(true);
      planWork.buildLinkedObject(db);
      context.getRequest().setAttribute("actionPlanWork", planWork);

      setObjects(context, db, planWork);

      ActionPlanWorkNoteList noteList = new ActionPlanWorkNoteList();
      noteList.setPlanWorkId(planWorkId);
      noteList.buildList(db);
      context.getRequest().setAttribute("actionPlanWorkNoteList", noteList);

      ActionPlanWorkNote thisNote = new ActionPlanWorkNote();
      thisNote.setSubmitted(DateUtils.roundUpToNextFive(System.currentTimeMillis()));
      context.getRequest().setAttribute("actionPlanWorkNote", thisNote);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ViewNotesOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddNotes(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);
  
      String planWorkId = context.getRequest().getParameter("planWorkId");
      if (planWorkId == null){
    	  planWorkId = (String)context.getRequest().getAttribute("planWorkId");
      }
      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
      planWork.setBuildLinkedObject(true);
      planWork.buildLinkedObject(db);
      context.getRequest().setAttribute("actionPlanWork", planWork);
      
      setObjects(context, db, planWork);

      ActionPlanWorkNote thisNote = (ActionPlanWorkNote) context.getFormBean();
      thisNote.setSubmittedBy(getUserId(context));

      boolean isValid = this.validateObject(context, db, thisNote);
      if (isValid) {
        thisNote.insert(db);
        thisNote = new ActionPlanWorkNote();
        thisNote.setSubmitted(DateUtils.roundUpToNextFive(System.currentTimeMillis()));
        context.getRequest().setAttribute("actionPlanWorkNote", thisNote);
      } else {
        context.getRequest().setAttribute("actionPlanWorkNote", thisNote);
      }

      ActionPlanWorkNoteList noteList = new ActionPlanWorkNoteList();
      noteList.setPlanWorkId(planWorkId);
      noteList.buildList(db);
      context.getRequest().setAttribute("actionPlanWorkNoteList", noteList);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ViewNotesOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandAddRecipient(ActionContext context) {
    Connection db = null;
    Contact contact = null;
    String actionPlanId = context.getRequest().getParameter("planWorkId");
    if (actionPlanId == null || "".equals(actionPlanId.trim())) {
      actionPlanId = (String) context.getRequest().getAttribute("planWorkId");
    }
    String actionItemId = context.getRequest().getParameter("actionItemId");
    if (actionItemId == null || "".equals(actionItemId.trim())) {
      actionItemId = (String) context.getRequest().getAttribute("actionItemId");
    }
    String contactId = context.getRequest().getParameter("contactId");
    String recipientAdded = (String) context.getRequest().getAttribute("recipientAdded");
    try {
      db = this.getConnection(context);
      ActionPlanWork planWork = new ActionPlanWork();
      planWork.queryRecord(db, Integer.parseInt(actionPlanId));
      context.getRequest().setAttribute("ActionPlanWork", planWork);
      ActionItemWork itemWork = new ActionItemWork();
      itemWork.setPlanWork(planWork);
      contact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("contact", contact);
      itemWork.queryRecord(db, Integer.parseInt(actionItemId));
      if (recipientAdded != null && "true".equals(recipientAdded.trim())) {
        itemWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS));
        itemWork.setLinkItemId(contactId);
        itemWork.attach(db);
      }
      context.getRequest().setAttribute("actionItemWork", itemWork);
      
      //get campaign URL
      String surveyKey =  getDbNamePath(context) + "keys" + fs + "survey2.key";
      PrivateString thisKey = new PrivateString(surveyKey);
      itemWork.buildStep(db);
      Campaign thisCampaign = new Campaign(db, itemWork.getStep().getCampaignId());
      Template template = new Template();
      template.setText(thisCampaign.getMessage());
      String value = template.getValue("surveyId");
      if (value != null) {
      String encryptedString = java.net.URLEncoder.encode(
                PrivateString.encrypt(
                    thisKey.getKey(), "id=" + value + ",cid=" + contact.getId()), "UTF-8");
       context.getRequest().setAttribute("surveyURL", "ProcessSurvey.do?id=" + encryptedString);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AttachRecipientOK";
  }
  
  private void setObjects(ActionContext context, Connection db, ActionPlanWork planWork) throws NumberFormatException, SQLException{
      String orgId = context.getRequest().getParameter("orgId");
      Organization thisOrg = null;
      if (orgId != null && !"".equals(orgId) && !"-1".equals(orgId)) {
        thisOrg = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("orgDetails", thisOrg);
      } else if (planWork.getOrganization() != null && planWork.getOrganization().getOrgId() != -1) {
        context.getRequest().setAttribute("orgDetails", planWork.getOrganization());
      }
      if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS)) {
        context.getRequest().setAttribute("ticket", planWork.getTicket());
      }
      if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.LEADS)) {
          context.getRequest().setAttribute("lead", planWork.getLead());
      }
  }
}

