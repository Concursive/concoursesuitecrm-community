/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 29, 2005
 * @version    $Id: Exp$
 */
public final class AccountActionPlans extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      String orgId = context.getRequest().getParameter("orgId");
      if (orgId == null) {
        orgId = context.getRequest().getParameter("accountId");
      }
      boolean popup = (context.getRequest().getParameter("popup") != null && "true".equals(context.getRequest().getParameter("popup")));
      Organization orgDetails = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("orgDetails", orgDetails);

      PagedListInfo planWorkListInfo = this.getPagedListInfo(context, "accountActionPlanWorkListInfo");
      planWorkListInfo.setLink("AccountActionPlans.do?command=View&orgId=" + orgId+(popup?"&popup=true":""));
      if (!planWorkListInfo.hasListFilters()) {
        planWorkListInfo.addFilter(1, "myhierarchy");
        planWorkListInfo.addFilter(2, "true");
      }

      ActionPlanWorkList planWorkList = new ActionPlanWorkList();
      planWorkList.setPagedListInfo(planWorkListInfo);
      planWorkList.setSiteId(orgDetails.getSiteId());
      if (user.getSiteId() == -1) {
        planWorkList.setIncludeAllSites(true);
      }

      planWorkList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
      planWorkList.setLinkItemId(orgId);

      if ("my".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        planWorkList.setOwner(this.getUserId(context));
      } else if ("mymanaged".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        planWorkList.setManager(this.getUserId(context));
      } else if ("mywaiting".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        planWorkList.setOwner(this.getUserId(context));
        planWorkList.setManager(this.getUserId(context));
        planWorkList.setCurrentStepOwner(this.getUserId(context));
        planWorkList.setAllMyPlans(true);
      } else if ("myhierarchy".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        planWorkList.setOwnerRange(this.getUserRange(context));
      }

      if ("all".equals(planWorkListInfo.getFilterValue("listFilter2"))) {
        planWorkList.setEnabled(Constants.UNDEFINED);
      } else if ("true".equals(planWorkListInfo.getFilterValue("listFilter2"))) {
        planWorkList.setEnabled(Constants.TRUE);
      } else if ("false".equals(planWorkListInfo.getFilterValue("listFilter2"))) {
        planWorkList.setEnabled(Constants.FALSE);
      }

      planWorkList.setBuildPhaseWork(true);
      planWorkList.setBuildStepWork(true);
      planWorkList.setBuildLinkedObject(true);
      planWorkList.buildList(db);
      context.getRequest().setAttribute("actionPlanWorkList", planWorkList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return getReturn(context, "View");
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
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      String planWorkId = context.getRequest().getParameter("actionPlanId");
      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
      planWork.setBuildLinkedObject(true);
      planWork.buildPhaseWork(db);
      planWork.buildLinkedObject(db);
      planWork.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandView(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-add"))) {
      return ("PermissionError");
    }
    ActionPlanWork planWork = (ActionPlanWork) context.getFormBean();
    Connection db = null;
    try {
      db = this.getConnection(context);

      String orgId = context.getRequest().getParameter("orgId");
      if (orgId == null) {
        orgId = context.getRequest().getParameter("accountId");
      }
      Organization orgDetails = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("orgDetails", orgDetails);

      //prepare a list of action plans
      ActionPlanList actionPlanList = new ActionPlanList();
      actionPlanList.setIncludeOnlyApproved(Constants.TRUE);
      actionPlanList.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
      actionPlanList.setEnabled(Constants.TRUE);
      actionPlanList.setSiteId(orgDetails.getSiteId());
      actionPlanList.buildList(db);
      context.getRequest().setAttribute("actionPlanList", actionPlanList);
      context.getRequest().setAttribute("actionPlanWork", planWork);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Add");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization orgDetails = null;
    User assigned = null;
    User manager = null;
    Contact assignedContact = null;
    Contact managerContact = null;
    ActionPlanWork planWork = (ActionPlanWork) context.getFormBean();
    String actionPlanIdString = context.getRequest().getParameter("actionPlan");
    if (actionPlanIdString != null && !"".equals(actionPlanIdString.trim()) && !"null".equals(actionPlanIdString.trim())) {
      planWork.setActionPlanId(actionPlanIdString);
    }
    try {
      db = this.getConnection(context);

      String orgId = context.getRequest().getParameter("orgId");
      if (orgId == null) {
        orgId = context.getRequest().getParameter("accountId");
      }
      orgDetails = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("orgDetails", orgDetails);

      boolean isValid = this.validateObject(context, db, planWork);
      boolean recordStatus = false;
      if (isValid) {
        ActionPlan actionPlan = new ActionPlan();
        actionPlan.setBuildPhases(true);
        actionPlan.setBuildSteps(true);
        actionPlan.queryRecord(db, planWork.getActionPlanId());

        planWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
        planWork.setLinkItemId(orgDetails.getOrgId());
        planWork.setEnteredBy(this.getUserId(context));
        planWork.setModifiedBy(this.getUserId(context));
        planWork.insert(db, actionPlan);
        this.processInsertHook(context, planWork);
        context.getRequest().setAttribute("actionPlanId", String.valueOf(planWork.getId()));
        assigned = this.getUser(context, planWork.getAssignedTo());
        assignedContact = new Contact(db, assigned.getContactId());
        manager = this.getUser(context, planWork.getManagerId());
        managerContact = new Contact(db, manager.getContactId());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    //Process Emails
    if (planWork.getId() > -1) {
      try {
        String templateFile = getDbNamePath(context) + "templates_" + getUserLanguage(context) + ".xml";
        if (!FileUtils.fileExists(templateFile)) {
          templateFile = getDbNamePath(context) + "templates_en_US.xml";
        }
        //Send an email to the Action Plan "owner" & Action Plan "manager"
        planWork.sendEmail(
            context,
            assignedContact,
            managerContact,
            orgDetails.getName(),
            templateFile);
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      }
    } else {
      return executeCommandAdd(context);
    }
    return (executeCommandDetails(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String orgId = context.getRequest().getParameter("orgId");
      if (orgId == null) {
        orgId = context.getRequest().getParameter("accountId");
      }
      Organization orgDetails = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("orgDetails", orgDetails);

      String actionPlanId = context.getRequest().getParameter("actionPlanId");
      if (actionPlanId == null || "".equals(actionPlanId)) {
        actionPlanId = (String) context.getRequest().getAttribute("actionPlanId");
      }
      ActionPlanWork planWork = new ActionPlanWork();
      planWork.setBuildPhaseWork(true);
      planWork.setBuildGlobalPhases(Constants.FALSE);
      planWork.setBuildStepWork(true);
      planWork.setBuildLinkedObject(true);
      planWork.setOrganization(orgDetails);
      planWork.queryRecord(db, Integer.parseInt(actionPlanId));
      planWork.buildStepLinks();
      context.getRequest().setAttribute("actionPlanWork", planWork);

      //Build plan with just the global phases
      ActionPlanWork globalPlanWork = new ActionPlanWork();
      globalPlanWork.setBuildPhaseWork(true);
      globalPlanWork.setBuildGlobalPhases(Constants.TRUE);
      globalPlanWork.setBuildStepWork(true);
      globalPlanWork.setOrganization(orgDetails);
      globalPlanWork.setBuildLinkedObject(true);
      globalPlanWork.queryRecord(db, Integer.parseInt(actionPlanId));
      context.getRequest().setAttribute("globalActionPlanWork", globalPlanWork);

      SystemStatus thisSystem = this.getSystemStatus(context);
      LookupList ratingLookup = thisSystem.getLookupList(db, "lookup_contact_rating");
      ratingLookup.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
      ratingLookup.setJsEvent("onChange=\"javascript:updateRating(this);\"");
      context.getRequest().setAttribute("ratingLookup", ratingLookup);
      context.getRequest().setAttribute("systemStatus", thisSystem);
      context.getRequest().setAttribute("objectName", ActionPlan.getDescriptionGivenConstantId(db, ActionPlan.ACCOUNTS));
      context.getRequest().setAttribute("constants", ActionPlan.buildConstants(db));
      String notAttached = context.getRequest().getParameter("notAttached");
      if (notAttached != null && "true".equals(notAttached.trim())) {
        context.getRequest().setAttribute("actionWarning",thisSystem.getLabel("","The recipient was not added to the active campaign"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Details");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModifyStatus(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus thisSystem = this.getSystemStatus(context);

      String itemId = context.getRequest().getParameter("itemId");
      ActionItemWork itemWork = new ActionItemWork(db, Integer.parseInt(itemId));
      itemWork.buildStep(db);
      context.getRequest().setAttribute("actionItemWork", itemWork);

      ActionItemWork nextItemWork = itemWork.getNextItem(db);
      if (nextItemWork == null) {
        nextItemWork = new ActionItemWork();
      } else {
        nextItemWork.buildStep(db);
        nextItemWork.buildLinkedObject(db);
      }
      context.getRequest().setAttribute("nextItemWork", nextItemWork);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ModifyStatusOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpdateStatus(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean status = false;
    ActionItemWork itemWork = null;
    ActionPhaseWork phaseWork = null;
    ActionPhaseWork nextPhaseWork = null;
    ActionItemWork nextItem = null;
    ActionPlanWork planWork = null;
    ActionItemWork oldItem = null;
    Organization orgDetails = null;
    boolean sendEmailForAllNewSteps = false;
    boolean sendEmailForNextStep = false;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("stepId");
      String nextStepId = context.getRequest().getParameter("nextStepId");
      String statusId = context.getRequest().getParameter("statusId");

      String orgId = context.getRequest().getParameter("orgId");
      orgDetails = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("orgDetails", orgDetails);

      if (itemId != null && Integer.parseInt(itemId) > 0) {
        itemWork = new ActionItemWork();
        itemWork.setBuildStep(true);
        itemWork.queryRecord(db, Integer.parseInt(itemId));
        phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
        planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
        planWork.setBuildLinkedObject(true);
        //planWork.buildLinkedObject(db);
        planWork.buildPhaseWork(db);
        itemWork.setPlanWork(planWork);
        oldItem = new ActionItemWork();
        oldItem.setPlanWork(planWork);
        oldItem.queryRecord(db, itemWork.getId());
				if (Integer.parseInt(statusId) == ActionPlanWork.COMPLETED) {
					itemWork.checkPreviousSteps(db, planWork);
				}
        if (statusId != null && Integer.parseInt(statusId) > 0) {
          itemWork.setStatusId(statusId);
          itemWork.setModifiedBy(this.getUserId(context));
        }

        if (nextStepId != null && Integer.parseInt(nextStepId) > 0) {
          nextItem = new ActionItemWork();
          nextItem.setBuildStep(true);
          nextItem.queryRecord(db, Integer.parseInt(nextStepId));
          nextItem.setPlanWork(planWork);
          if (nextItem.getPhaseWorkId() != itemWork.getPhaseWorkId()) {
            nextPhaseWork = new ActionPhaseWork(db, nextItem.getPhaseWorkId());
            nextPhaseWork.setPlanWork(planWork);
            nextPhaseWork.setBuildLinkedObject(true);
            nextPhaseWork.buildPhaseObject(db);
            nextPhaseWork.buildStepWork(db);
            if (nextPhaseWork != null && nextPhaseWork.getPhase().getRandom() && nextPhaseWork.noStepComplete()) {
              sendEmailForAllNewSteps = true;
            }
          }
          status = itemWork.updateStatus(db, nextItem);
          this.processUpdateHook(context, oldItem, itemWork);
          if (!sendEmailForAllNewSteps) {
            if (nextItem != null) {
              this.processUpdateHook(context, itemWork, nextItem);
            }
          }
        } else {
          status = itemWork.updateStatus(db, null);
          phaseWork.setBuildStepWork(true);
          phaseWork.setBuildPhase(true);
          phaseWork.queryRecord(db, itemWork.getPhaseWorkId());
          this.processUpdateHook(context, oldItem, itemWork);
          nextPhaseWork = phaseWork.getNextPhase(db);
          if (phaseWork.getPhase().getRandom() && phaseWork.allStepsComplete() && nextPhaseWork != null) {
            nextPhaseWork.setPlanWork(planWork);
            nextPhaseWork.setBuildLinkedObject(true);
            nextPhaseWork.buildStepWork(db);
            nextPhaseWork.buildPhaseObject(db);
            if (nextPhaseWork.getPhase().getRandom() && nextPhaseWork.noStepComplete()) {
              // random phase to random phase translation
              sendEmailForAllNewSteps = true;
            } else
                if (!nextPhaseWork.getPhase().getRandom() && nextPhaseWork.noStepComplete()) {
              // random phase to serial phase translation
              if (nextPhaseWork.getItemWorkList().size() > 0) {
                nextItem = (ActionItemWork) nextPhaseWork.getItemWorkList().get(0);
                nextItem.setPlanWork(planWork);
                nextItem.buildStep(db);
                nextItem.buildLinkedObject(db);
                this.processUpdateHook(context, itemWork, nextItem);
              }
            }
          }
        }
      }
      if (sendEmailForAllNewSteps) {
        if (nextPhaseWork != null && nextPhaseWork.getItemWorkList() != null) {
          Iterator iter = (Iterator) nextPhaseWork.getItemWorkList().iterator();
          while (iter.hasNext()) {
            nextItem = (ActionItemWork) iter.next();
            nextItem.setPlanWork(planWork);
            nextItem = new ActionItemWork(db, nextItem.getId());
            nextItem.buildStep(db);
            nextItem.buildLinkedObject(db);
            this.processUpdateHook(context, itemWork, nextItem);
          }
        }
      }
      String returnUrl = context.getRequest().getParameter("returnUrl");
      context.getRequest().setAttribute("refreshUrl", returnUrl);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean isAccountPopup = (context.getRequest().getParameter("popupType") != null && "inline".equals(context.getRequest().getParameter("popupType")));
    if ("true".equals(context.getRequest().getParameter("popup")) && !isAccountPopup) {
      return "UpdateStatusOK";
    }
    return (executeCommandDetails(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpdateRating(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String planId = context.getRequest().getParameter("planId");
      String rating = context.getRequest().getParameter("rating");

      ActionPlanWork planWork = new ActionPlanWork();
      planWork.setBuildLinkedObject(true);
      planWork.queryRecord(db, Integer.parseInt(planId));
      planWork.updateRating(db, Integer.parseInt(rating));

    } catch (Exception e) {
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
  public String executeCommandAttach(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "AttachOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandResetFolderAttachment(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-delete"))) {
      return ("PermissionError");
    }
    return "ResetFolderAttachmentOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewNotes(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "ViewNotesOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandReassign(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "ReassignOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRestart(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "RestartOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "EnableOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRevertStatus(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "RevertStatusOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpdateGlobalStatus(ActionContext context) {
    if (!(hasPermission(context, "accounts-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "UpdateGlobalStatusOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmRevert(ActionContext context) {
    return "GoConfirmRevertOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRevertToLead(ActionContext context) {
    return "GoRevertToLeadOK";
  }
}

