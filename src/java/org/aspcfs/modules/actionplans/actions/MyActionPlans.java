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
package org.aspcfs.modules.actionplans.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @version $Id: MyActionPlans.java 13876 2006-01-19 17:03:37 -0500 (Thu, 19
 *          Jan 2006) partha $
 * @created August 16, 2005
 */
public final class MyActionPlans extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-view"))) {
      return ("PermissionError");
    }
    return executeCommandList(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-view"))) {
      return ("PermissionError");
    }
    //Add the default view
    if (context.getSession().getAttribute("actionPlanWorkListView") == null) {
      context.getSession().setAttribute("actionPlanWorkListView", "planDashboardView");
      return (executeCommandDashboard(context));
    }

    //check to see if the view needs to be changed
    String view = context.getRequest().getParameter("planView");
    if (view != null && !"".equals(view)) {
      if ("planListView".equals(view)) {
        context.getSession().setAttribute("actionPlanWorkListView", "planListView");
        return (executeCommandList(context));
      } else if ("planDashboardView".equals(view)) {
        context.getSession().setAttribute("actionPlanWorkListView", "planDashboardView");
        return (executeCommandDashboard(context));
      }
    }

    //return the view requested
    if (context.getSession().getAttribute("actionPlanWorkListView") != null) {
      view = (String) context.getSession().getAttribute("actionPlanWorkListView");
      if ("planDashboardView".equals(view)) {
        return (executeCommandDashboard(context));
      }
    }
    return (executeCommandList(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-view"))) {
      return ("PermissionError");
    }
    //Prepare the user id to base all data on
    int idToUse = 0;
    User thisRec = null;

    int userId = getUserId(context);
    //default it to the current user

    //Check if a specific user was selected
    int overrideId = StringUtils.parseInt(
        context.getRequest().getParameter("oid"), -1);

    //Check if the list is being reset
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      overrideId = -1;
      context.getSession().removeAttribute("plansoverride");
      context.getSession().removeAttribute("plansothername");
      context.getSession().removeAttribute("planspreviousId");
    }

    //Determine the user whose data is being shown, by default it's the current user
    if (overrideId < 0) {
      if (context.getSession().getAttribute("plansoverride") != null) {
        overrideId = StringUtils.parseInt(
            (String) context.getSession().getAttribute("plansoverride"), -1);
      } else {
        overrideId = userId;
      }
    }

    Connection db = null;

    UserList shortChildList = new UserList();

    try {
      db = this.getConnection(context);

      if (overrideId > 0) {
        idToUse = overrideId;
      } else {
        idToUse = this.getUserId(context);
      }

      thisRec = this.getUser(context, idToUse);
      shortChildList = thisRec.getShortChildList();
      shortChildList = UserList.sortEnabledUsers(
          shortChildList, new UserList());
      context.getRequest().setAttribute("currentUser", thisRec);
      //Track the id in the request and the session
      if (context.getRequest().getParameter("oid") != null && !"true".equals(
          (String) context.getRequest().getParameter("reset"))) {
        context.getRequest().setAttribute("override", String.valueOf(idToUse));
        context.getRequest().setAttribute(
            "othername", thisRec.getContact().getNameFull());
        context.getRequest().setAttribute(
            "previousId", String.valueOf(thisRec.getManagerId()));
        context.getSession().setAttribute(
            "plansoverride", String.valueOf(overrideId));
        context.getSession().setAttribute(
            "plansothername", thisRec.getContact().getNameFull());
        context.getSession().setAttribute(
            "planspreviousId", String.valueOf(thisRec.getManagerId()));
      }

      //Generate the action plans pagedList for the idToUse right of graph
      PagedListInfo dashboardListInfo = this.getPagedListInfo(
          context, "actionPlanWorkDashboardInfo");
      dashboardListInfo.setLink("MyActionPlans.do?command=Dashboard");
      if (!dashboardListInfo.hasListFilters()) {
        dashboardListInfo.addFilter(1, "my");
        dashboardListInfo.addFilter(2, "true");
      }
      //dashboardListInfo.setColumnToSortBy("x.description");

      //Build a list of active action plans
      ActionPlanList actionPlanList = new ActionPlanList();
      actionPlanList.setIncludeOnlyApproved(Constants.TRUE);
      actionPlanList.setSiteId(thisRec.getSiteId());
      if (thisRec.getSiteId() == -1) {
        actionPlanList.setIncludeAllSites(true);
      }
      actionPlanList.setBuildPhases(true);
      actionPlanList.setJsEvent("onChange=\"javascript:updateGraph(this);\"");
      actionPlanList.buildList(db);
      context.getRequest().setAttribute("actionPlanList", actionPlanList);
      context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));

      //Build the action plan selected or the default plan
      ActionPlan thisPlan = new ActionPlan();
      String planId = context.getRequest().getParameter("planId");
      if (planId != null && !"".equals(planId.trim())) {
        //first check in the request
        thisPlan.setBuildPhases(true);
        thisPlan.queryRecord(db, Integer.parseInt(planId));
        context.getSession().setAttribute("actionPlanId", planId);
      } else
      if ((String) context.getSession().getAttribute("actionPlanId") != null) {
        //plan id not available in the request. check the session
        planId = (String) context.getSession().getAttribute("actionPlanId");
        if (!"".equals(planId.trim())) {
          thisPlan.setBuildPhases(true);
          thisPlan.queryRecord(db, Integer.parseInt(planId));
        }
      } else {
        thisPlan = (ActionPlan) actionPlanList.getPlan();
      }
      context.getRequest().setAttribute("actionPlan", thisPlan);

      //populate the user phase map and the action plan work list based on the selected plan
      if (thisPlan.getId() > -1) {
        HashMap userPhase = ActionPlanWork.buildUserPhaseMap(db, thisPlan.getId());
        Iterator p = shortChildList.iterator();
        while (p.hasNext()) {
          User child = (User) p.next();
          ActionPlanWork.adjustPhaseCount(userPhase, new ArrayList(child.getShortChildList()), child.getId());
        }
        context.getRequest().setAttribute("userPhaseMap", userPhase);

        ActionPlanWorkList planWorkList = new ActionPlanWorkList();
        planWorkList.setPagedListInfo(dashboardListInfo);
        dashboardListInfo.setSearchCriteria(planWorkList, context);
        planWorkList.setOwner(idToUse);
        planWorkList.setManager(idToUse);
        planWorkList.setCurrentStepOwner(idToUse);
        planWorkList.setAllMyPlans(true);

        if ("all".equals(dashboardListInfo.getFilterValue("listFilter1"))) {
          planWorkList.setEnabled(Constants.UNDEFINED);
        } else
        if ("true".equals(dashboardListInfo.getFilterValue("listFilter1"))) {
          planWorkList.setEnabled(Constants.TRUE);
        } else
        if ("false".equals(dashboardListInfo.getFilterValue("listFilter1"))) {
          planWorkList.setEnabled(Constants.FALSE);
        }

        //planWorkList.setActionPlanId(thisPlan.getId());
        planWorkList.setViewpoint(idToUse);
        planWorkList.setSiteId(thisRec.getSiteId());
        if (thisRec.getSiteId() == -1) {
          planWorkList.setIncludeAllSites(true);
        }
        planWorkList.setBuildPhaseWork(true);
        planWorkList.setBuildStepWork(true);
        planWorkList.setBuildLinkedObject(true);
        planWorkList.buildList(db);
        context.getRequest().setAttribute("actionPlanWorkList", planWorkList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("shortChildList", shortChildList);
    return "DashboardOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    PagedListInfo planWorkListInfo = this.getPagedListInfo(context, "actionPlanWorkListInfo");
    planWorkListInfo.setLink("MyActionPlans.do?command=List");
    if (!planWorkListInfo.hasListFilters()) {
      planWorkListInfo.addFilter(1, "myviewpoint");
      planWorkListInfo.addFilter(2, "true");
    }
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;
    try {
      db = this.getConnection(context);

      ActionPlanWorkList accountPlanWorkList = new ActionPlanWorkList();
      accountPlanWorkList.setPagedListInfo(planWorkListInfo);
      accountPlanWorkList.setSiteId(user.getSiteId());
      if (user.getSiteId() == -1) {
        accountPlanWorkList.setIncludeAllSites(true);
      }

      if ("myviewpoint".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        accountPlanWorkList.setViewpoint(this.getUserId(context));
      } else if ("my".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        accountPlanWorkList.setOwner(this.getUserId(context));
      } else
      if ("mymanaged".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        accountPlanWorkList.setManager(this.getUserId(context));
      } else
      if ("mywaiting".equals(planWorkListInfo.getFilterValue("listFilter1"))) {
        accountPlanWorkList.setCurrentStepOwner(this.getUserId(context));
      }

      if ("all".equals(planWorkListInfo.getFilterValue("listFilter2"))) {
        accountPlanWorkList.setEnabled(Constants.UNDEFINED);
      } else
      if ("true".equals(planWorkListInfo.getFilterValue("listFilter2"))) {
        accountPlanWorkList.setEnabled(Constants.TRUE);
      } else
      if ("false".equals(planWorkListInfo.getFilterValue("listFilter2"))) {
        accountPlanWorkList.setEnabled(Constants.FALSE);
      }

      accountPlanWorkList.setBuildPhaseWork(true);
      accountPlanWorkList.setBuildStepWork(true);
      accountPlanWorkList.setBuildLinkedObject(true);
      accountPlanWorkList.buildList(db);

      context.getRequest().setAttribute("accountActionPlanWorkList", accountPlanWorkList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ListOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      String actionPlanId = context.getRequest().getParameter("actionPlanId");

      //Build plan with regular phases
      ActionPlanWork planWork = new ActionPlanWork();
      planWork.setBuildPhaseWork(true);
      planWork.setBuildGlobalPhases(Constants.FALSE);
      planWork.setBuildStepWork(true);
      planWork.setBuildLinkedObject(true);
      planWork.queryRecord(db, Integer.parseInt(actionPlanId));

      planWork.buildStepLinks();

      context.getRequest().setAttribute("actionPlanWork", planWork);
      if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS))
        context.getRequest().setAttribute("ticket_id", planWork.getLinkItemId() + "");
      //Build plan with just the global phases
      ActionPlanWork globalPlanWork = new ActionPlanWork();
      globalPlanWork.setBuildPhaseWork(true);
      globalPlanWork.setBuildGlobalPhases(Constants.TRUE);
      globalPlanWork.setBuildStepWork(true);
      globalPlanWork.setBuildLinkedObject(true);
      globalPlanWork.queryRecord(db, Integer.parseInt(actionPlanId));
      context.getRequest().setAttribute("globalActionPlanWork", globalPlanWork);

      SystemStatus thisSystem = this.getSystemStatus(context);
      LookupList ratingLookup = thisSystem.getLookupList(db, "lookup_contact_rating");
      ratingLookup.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
      ratingLookup.setJsEvent("onChange=\"javascript:updateRating(this);\"");
      context.getRequest().setAttribute("ratingLookup", ratingLookup);
      context.getRequest().setAttribute("systemStatus", thisSystem);
      context.getRequest().setAttribute("objectName", ActionPlan.getDescriptionGivenConstantId(db, ActionPlan.MYHOMEPAGE));
      context.getRequest().setAttribute("constants", ActionPlan.buildConstants(db));
      String notAttached = context.getRequest().getParameter("notAttached");
      if (notAttached != null && "true".equals(notAttached.trim())) {
        context.getRequest().setAttribute("actionWarning", thisSystem.getLabel("", "The recipient was not added to the active campaign"));
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "DetailsOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "EnableOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReassign(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "ReassignOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAttach(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "AttachOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandResetFolderAttachment(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-delete"))) {
      return ("PermissionError");
    }
    return "ResetFolderAttachmentOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewNotes(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "ViewNotesOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyStatus(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
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

      String planId = context.getRequest().getParameter("planId");
      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planId));

      UserList userList = new UserList();
      userList.add(thisSystem.getUser(planWork.getManagerId()));
      if (planWork.getManagerId() != planWork.getAssignedTo()) {
        userList.add(thisSystem.getUser(planWork.getAssignedTo()));
      }
      context.getRequest().setAttribute("ownerList", userList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ModifyStatusOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateStatus(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean status = false;
    ActionItemWork itemWork = null;
    ActionPhaseWork phaseWork = null;
    ActionPhaseWork nextPhaseWork = null;
    ActionItemWork nextItem = null;
    ActionPlanWork planWork = null;
    ActionItemWork oldItem = null;
    boolean sendEmailForAllNewSteps = false;
    boolean sendEmailForNextStep = false;
    try {
      db = this.getConnection(context);

      String itemId = context.getRequest().getParameter("stepId");
      String nextStepId = context.getRequest().getParameter("nextStepId");
      String statusId = context.getRequest().getParameter("statusId");

      if (itemId != null && Integer.parseInt(itemId) > 0) {
        itemWork = new ActionItemWork();
        itemWork.setBuildStep(true);
        itemWork.queryRecord(db, Integer.parseInt(itemId));
        phaseWork = new ActionPhaseWork(db, itemWork.getPhaseWorkId());
        planWork = new ActionPlanWork(db, phaseWork.getPlanWorkId());
        planWork.setBuildLinkedObject(true);
        planWork.buildLinkedObject(db);
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
    if ("true".equals(context.getRequest().getParameter("popup"))) {
      return "UpdateStatusOK";
    }
    return (executeCommandDetails(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRevertStatus(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "RevertStatusOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateGlobalStatus(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "UpdateGlobalStatusOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestart(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
      return ("PermissionError");
    }
    return "RestartOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateRating(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-edit"))) {
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-plans-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      String planWorkId = context.getRequest().getParameter("actionPlanId");
      ActionPlanWork planWork = new ActionPlanWork(db, Integer.parseInt(planWorkId));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmRevert(ActionContext context) {
    return "GoConfirmRevertOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRevertToLead(ActionContext context) {
    return "GoRevertToLeadOK";
  }
}

