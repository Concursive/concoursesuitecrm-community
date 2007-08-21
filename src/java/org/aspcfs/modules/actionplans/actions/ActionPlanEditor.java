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
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.UserGroupList;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.relationships.base.RelationshipTypeList;
import org.aspcfs.modules.base.CustomFieldCategoryList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    August 22, 2005
 * @version    $Id: ActionPlanEditor.java,v 1.1.2.6 2005/09/09 19:16:16 partha
 *      Exp $
 */
public final class ActionPlanEditor extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    return "ListPlansOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandListEditors(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      PlanEditorList planEditors = new PlanEditorList();
      planEditors.setModuleId(moduleId);
      planEditors.buildList(db);
      context.getRequest().setAttribute("planEditors", planEditors);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListEditorsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandListPlans(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    ActionPlanList plans = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    PagedListInfo listInfo = getPagedListInfo(context, "planListInfo");
    User user = this.getUser(context, this.getUserId(context));
    listInfo.setLink("ActionPlanEditor.do?command=ListPlans&moduleId=" + moduleId + "&constantId=" + constantId);
    Connection db = null;
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("constantId", constantId);

      plans = new ActionPlanList();
      plans.setPagedListInfo(listInfo);
      plans.setLinkObjectId(constantId);
      if (user.getSiteId() == -1) {
        plans.setIncludeAllSites(true);
      } else {
        plans.setExclusiveToSite(true);
        plans.setSiteId(user.getSiteId());
      }
      plans.setBuildPhases(true);
      plans.setBuildSteps(true);
      plans.setBuildRelatedRecords(true);
      plans.buildList(db);
      context.getRequest().setAttribute("actionPlanList", plans);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListPlansOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddPlan(ActionContext context) {
  Connection db = null;
  String status = null;
  try {
    db = this.getConnection(context);
    status = this.executeCommandAddPlan(context, db);
  } catch (Exception e) {
    context.getRequest().setAttribute("Error", e);
    return ("SystemError");
  } finally {
    this.freeConnection(context, db);
  }
  return status;
}
  
  
  private String executeCommandAddPlan(ActionContext context,Connection db) throws NumberFormatException, SQLException {
    if (!hasPermission(context, "admin-actionplans-add")) {
      return ("PermissionError");
    }    
    addModuleBean(context, "ActionPlan", "AddPlan");
    String siteId = context.getRequest().getParameter("siteId");
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    User user = this.getUser(context, this.getUserId(context));
    SystemStatus systemStatus = this.getSystemStatus(context);
    ActionPlan plan = (ActionPlan) context.getFormBean();
      if (context.getRequest().getParameter("enabled") == null) {
        plan.setEnabled(true);
      }
      if (siteId != null && !"".equals(siteId)) {
        plan.setSiteId(siteId);
        if (!isSiteAccessPermitted(context, String.valueOf(plan.getSiteId()))) {
          return ("PermissionError");
        }
/*
        if (user.getSiteId() != -1 && user.getSiteId() != plan.getSiteId()) {
          return ("PermissionError");
        }
*/
      } else if (user.getSiteId() > -1) {
        plan.setSiteId(user.getSiteId());
      }
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("constantId", constantId);

      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      String refresh = context.getParameter("refresh");

      ActionPlanCategoryList categoryList = new ActionPlanCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setSiteId(plan.getSiteId());
      categoryList.setExclusiveToSite(true);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      categoryList.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("CategoryList", categoryList);

      ActionPlanCategoryList subList1 = new ActionPlanCategoryList();
      subList1.setCatLevel(1);
      subList1.setSiteId(plan.getSiteId());
      subList1.setExclusiveToSite(true);
      subList1.setParentCode(plan.getCatCode());
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList1", subList1);

      ActionPlanCategoryList subList2 = new ActionPlanCategoryList();
      subList2.setCatLevel(2);
      subList2.setSiteId(plan.getSiteId());
      subList2.setExclusiveToSite(true);
      if (refresh != null && !"".equals(refresh) && Integer.parseInt(refresh) == 1) {
        subList2.setParentCode(0);
        plan.setSubCat1(0);
        plan.setSubCat2(0);
        plan.setSubCat3(0);
      } else if (refresh != null && !"".equals(refresh) && Integer.parseInt(refresh) == -1) {
        subList2.setParentCode(plan.getSubCat1());
        subList2.getCatListSelect().setDefaultKey(plan.getSubCat2());
      } else {
        subList2.setParentCode(plan.getSubCat1());
      }
      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.buildList(db);
      subList2.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList2", subList2);

      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);

      ActionPlanCategoryList subList3 = new ActionPlanCategoryList();
      subList3.setCatLevel(3);
      subList3.setSiteId(plan.getSiteId());
      subList3.setExclusiveToSite(true);
      if (refresh != null && !"".equals(refresh) && (Integer.parseInt(refresh) == 1 || Integer.parseInt(refresh) == 2)) {
        subList3.setParentCode(0);
        plan.setSubCat2(0);
        plan.setSubCat3(0);
      } else if (refresh != null && !"".equals(refresh) && Integer.parseInt(refresh) == -1) {
        subList3.setParentCode(plan.getSubCat2());
        subList3.getCatListSelect().setDefaultKey(plan.getSubCat3());
      } else {
        subList3.setParentCode(plan.getSubCat2());
      }
      subList3.buildList(db);
      subList3.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList3", subList3);

      if (refresh != null && !"".equals(refresh) && (Integer.parseInt(refresh) == 1 || Integer.parseInt(refresh) == 3)) {
        plan.setSubCat3(0);
      }
      context.getRequest().setAttribute("actionPlan", plan);
    return "AddPlanOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSavePlan(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-add")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    String from = context.getRequest().getParameter("return");
    if (from != null && "list".equals(from)) {
      return executeCommandDefault(context);
    }
    addModuleBean(context, "ActionPlan", "AddPlan");
    SystemStatus systemStatus = this.getSystemStatus(context);
    ActionPlan plan = (ActionPlan) context.getFormBean();
    Connection db = null;
    boolean isValid = false;
    boolean recordInserted = false;
    int recordCount = -1;
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("constantId", constantId);
      plan.setModifiedBy(this.getUserId(context));
      if (plan.getId() > -1) {
        isValid = this.validateObject(context, db, plan);
        if (isValid) {
          recordCount = plan.update(db);
        }
      } else {
        plan.setEnteredBy(this.getUserId(context));
        isValid = this.validateObject(context, db, plan);
        if (isValid) {
          recordInserted = plan.insert(db);
        }
      }
      if (!isValid) {
        if (plan.getId() > -1) {
          return executeCommandRenamePlan(context,db);
        } else {
          return executeCommandAddPlan(context,db);
        }
      }
      context.getRequest().setAttribute("planId", String.valueOf(plan.getId()));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandPlanDetails(context);
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPlanDetails(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    Connection db = null;
    addModuleBean(context, "ActionPlan", "PlanDetails");
    SystemStatus systemStatus = this.getSystemStatus(context);
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    ActionPlan plan = null;
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("constantId", constantId);
      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      //List of roles
      RoleList roles = new RoleList();
      roles.buildList(db);
      context.getRequest().setAttribute("roles", roles);
      //List of departments
      LookupList departmentType = systemStatus.getLookupList(db, "lookup_department");
      context.getRequest().setAttribute("departmentType", departmentType);

      plan = new ActionPlan();
      plan.setBuildPhases(true);
      plan.setBuildSteps(true);
      plan.queryRecord(db, Integer.parseInt(planId));
      context.getRequest().setAttribute("actionPlan", plan);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "PlanDetailsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRenamePlan(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandRenamePlan(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }
  
  
  private String executeCommandRenamePlan(ActionContext context, Connection db) throws NumberFormatException, SQLException {
    if (!hasPermission(context, "admin-actionplans-edit")) {
      return ("PermissionError");
    }
    User user = this.getUser(context, this.getUserId(context));
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    ActionPlan plan = (ActionPlan) context.getFormBean();
    addModuleBean(context, "ActionPlan", "RenamePlan");
    SystemStatus systemStatus = this.getSystemStatus(context);

      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("constantId", constantId);
      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      if (plan.getId() == -1) {
        plan = new ActionPlan(db, Integer.parseInt(planId));
      }

      String refresh = context.getParameter("refresh");
      ActionPlanCategoryList categoryList = new ActionPlanCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setSiteId(plan.getSiteId());
      categoryList.setExclusiveToSite(true);
      categoryList.setParentCode(0);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      categoryList.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("CategoryList", categoryList);

      ActionPlanCategoryList subList1 = new ActionPlanCategoryList();
      subList1.setCatLevel(1);
      subList1.setSiteId(plan.getSiteId());
      subList1.setExclusiveToSite(true);
      subList1.setParentCode(plan.getCatCode());
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList1", subList1);

      ActionPlanCategoryList subList2 = new ActionPlanCategoryList();
      subList2.setCatLevel(2);
      subList2.setSiteId(plan.getSiteId());
      subList2.setExclusiveToSite(true);
      if (refresh != null && !"".equals(refresh) && Integer.parseInt(refresh) == 1) {
        subList2.setParentCode(0);
        plan.setSubCat1(0);
        plan.setSubCat2(0);
        plan.setSubCat3(0);
      } else if (refresh != null && !"".equals(refresh) && Integer.parseInt(refresh) == -1) {
        subList2.setParentCode(plan.getSubCat1());
        subList2.getCatListSelect().setDefaultKey(plan.getSubCat2());
      } else {
        subList2.setParentCode(plan.getSubCat1());
      }

      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.buildList(db);
      subList2.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList2", subList2);

      ActionPlanCategoryList subList3 = new ActionPlanCategoryList();
      subList3.setCatLevel(3);
      subList3.setSiteId(plan.getSiteId());
      subList3.setExclusiveToSite(true);
      if (refresh != null && !"".equals(refresh) && (Integer.parseInt(refresh) == 1 || Integer.parseInt(refresh) == 2)) {
        subList3.setParentCode(0);
        plan.setSubCat2(0);
        plan.setSubCat3(0);
      } else if (refresh != null && !"".equals(refresh) && Integer.parseInt(refresh) == -1) {
        subList3.setParentCode(plan.getSubCat2());
        subList3.getCatListSelect().setDefaultKey(plan.getSubCat3());
      } else {
        subList3.setParentCode(plan.getSubCat2());
      }

      subList3.buildList(db);
      subList3.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList3", subList3);

      if (refresh != null && !"".equals(refresh) && (Integer.parseInt(refresh) == 1 || Integer.parseInt(refresh) == 3)) {
        plan.setSubCat3(0);
      }

      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);

      context.getRequest().setAttribute("actionPlan", plan);
    return "AddPlanOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandApprovePlan(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-edit")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    String justApproved = context.getRequest().getParameter("justApproved");
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    ActionPlan plan = null;
    Connection db = null;
    boolean isValid = false;
    addModuleBean(context, "ActionPlan", "ApprovePlan");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      plan = new ActionPlan(db, Integer.parseInt(planId));
      if (justApproved != null && !"".equals(justApproved)) {
        if (DatabaseUtils.parseBoolean(justApproved)) {
          plan.setJustApproved(true);
        } else {
          plan.setJustDisapproved(true);
        }
        isValid = this.validateObject(context, db, plan);
        if (isValid) {
          plan.update(db);
        }
      }
      context.getRequest().setAttribute("actionPlan", plan);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (from != null && "list".equals(from)) {
      return executeCommandListPlans(context);
    }
    return "PlanDetailsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandArchivePlan(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-edit")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    String justArchived = context.getRequest().getParameter("justArchived");
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    ActionPlan plan = null;
    Connection db = null;
    boolean isValid = false;
    addModuleBean(context, "ActionPlan", "ArchivePlan");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      plan = new ActionPlan(db, Integer.parseInt(planId));
      if (justArchived != null && !"".equals(justArchived)) {
        if (DatabaseUtils.parseBoolean(justArchived)) {
          plan.setEnabled(false);
          plan.setArchiveDate((java.sql.Timestamp) null);
        } else {
          plan.setEnabled(true);
        }
        isValid = this.validateObject(context, db, plan);
        if (isValid) {
          plan.update(db);
        }
      }
      context.getRequest().setAttribute("actionPlan", plan);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (from != null && "list".equals(from)) {
      return executeCommandListPlans(context);
    }
    return "PlanDetailsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDeletePlan(ActionContext context) {
    if (!(hasPermission(context, "admin-actionplans-delete"))) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    Connection db = null;
    ActionPlan plan = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      plan = new ActionPlan();
      plan.setBuildSteps(true);
      plan.queryRecord(db, Integer.parseInt(planId));

      DependencyList dependencies = plan.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("actionplan.dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='ActionPlanEditor.do?command=DeletePlan&planId=" + plan.getId() + "&moduleId=" + moduleId + "&constantId=" + constantId + "';");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDeletePlan(ActionContext context) {
    if (!(hasPermission(context, "admin-actionplans-delete"))) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    String planId = (String) context.getRequest().getParameter("planId");
    if (planId != null && !"".equals(planId)) {
      context.getRequest().setAttribute("planId", planId);
    }
    ActionPlan plan = null;
    Connection db = null;
    try {
      db = getConnection(context);
      plan = new ActionPlan();
      plan.setBuildPhases(true);
      plan.setBuildSteps(true);
      plan.queryRecord(db, Integer.parseInt(planId));
      //delete the plan
      plan.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "ActionPlanEditor.do?command=ListPlans&moduleId=" +
        moduleId + "&constantId=" + constantId);
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddPhase(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandAddPhase(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }

  private String executeCommandAddPhase(ActionContext context,Connection db) throws SQLException {  
    if (!hasPermission(context, "admin-actionplans-add")) {
      return ("PermissionError");
    }
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    String previousPhaseId = context.getRequest().getParameter("previousPhaseId");
    String nextPhaseId = context.getRequest().getParameter("nextPhaseId");
    if (nextPhaseId != null && !"".equals(nextPhaseId.trim())) {
      context.getRequest().setAttribute("nextPhaseId", nextPhaseId);
    }
    if (previousPhaseId != null && !"".equals(previousPhaseId)) {
      context.getRequest().setAttribute("previousPhaseId", previousPhaseId);
    }
    ActionPlan plan = null;
    ActionPhase phase = (ActionPhase) context.getFormBean();
    addModuleBean(context, "ActionPlan", "AddPhase");
    SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      plan = new ActionPlan();
      plan.setBuildPhases(true);
      plan.queryRecord(db, Integer.parseInt(planId));
      if (phase == null) {
        phase = new ActionPhase();
      }
      phase.setPlanId(plan.getId());
      context.getRequest().setAttribute("actionPlan", plan);
      context.getRequest().setAttribute("actionPhase", phase);
      ActionPhaseList list = new ActionPhaseList();
      list.setPlanId(plan.getId());
      list.buildList(db);
      HtmlSelect thisSelect = list.getHtmlSelectObject();
      thisSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes", "-- None --"), 0);
      context.getRequest().setAttribute("actionPhaseSelect", thisSelect);
    return "AddPhaseOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSavePhase(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-add")) {
      return ("PermissionError");
    }
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    String previousPhaseId = context.getRequest().getParameter("previousPhaseId");
    String nextPhaseId = context.getRequest().getParameter("nextPhaseId");
    boolean isValid = false;
    boolean recordInserted = false;
    int recordCount = -1;
    ActionPlan plan = null;
    ActionPhase phase = (ActionPhase) context.getFormBean();
    Connection db = null;
    addModuleBean(context, "ActionPlan", "SavePhase");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      //update or save the current phase
      if (phase.getPlanId() == -1) {
        phase.setPlanId(planId);
      }
      if (phase.getId() > -1) {
        isValid = this.validateObject(context, db, phase);
        if (isValid) {
          recordCount = phase.update(db);
        }
      } else {
        isValid = this.validateObject(context, db, phase);
        if (isValid) {
          recordInserted = phase.insert(db);
        }
      }
      if (!isValid || (!recordInserted && recordCount == -1)) {
        if (nextPhaseId != null && !"".equals(nextPhaseId.trim())) {
          context.getRequest().setAttribute("nextPhaseId", nextPhaseId);
        }
        if (phase.getId() > -1) {
          return executeCommandModifyPhase(context,db);
        } else {
          return executeCommandAddPhase(context,db);
        }
      } else {
          if (previousPhaseId != null && !"".equals(previousPhaseId)) {
            phase.setAfterPhaseId(db, previousPhaseId, true);
          } else if (nextPhaseId != null && !"".equals(nextPhaseId)) {
            phase.setBeforePhaseId(db, nextPhaseId, true);
          }
      }
      context.getRequest().setAttribute("planId", String.valueOf(phase.getPlanId()));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SavePhaseOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandMovePhase(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-edit")) {
      return ("PermissionError");
    }
    String previousPhaseId = context.getRequest().getParameter("previousPhaseId");
    String nextPhaseId = context.getRequest().getParameter("nextPhaseId");
    String phaseId = context.getRequest().getParameter("phaseId");
    String movePhaseUp = context.getRequest().getParameter("movePhaseUp");
    ActionPhase phase = null;
    Connection db = null;
    addModuleBean(context, "ActionPlan", "MovePhase");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      phase = new ActionPhase(db, Integer.parseInt(phaseId));
      //Move phase up or down
      if (movePhaseUp != null && !"".equals(movePhaseUp)) {
        if (DatabaseUtils.parseBoolean(movePhaseUp)) {
          if (phase.getParentId() != -1 && phase.getParentId() != 0) {
            nextPhaseId = String.valueOf(phase.getParentId());
          }
        } else {
          int result = ActionPhase.getPhaseIdGivenParentId(db, phase.getId());
          if (result != -1) {
            previousPhaseId = String.valueOf(result);
          }
        }
      } else {
        return "PermissionError";
      }
      if (previousPhaseId != null && !"".equals(previousPhaseId)) {
        phase.setAfterPhaseId(db, previousPhaseId, false);
      } else if (nextPhaseId != null && !"".equals(nextPhaseId)) {
        phase.setBeforePhaseId(db, nextPhaseId, false);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "MovePhaseOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModifyPhase(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandModifyPhase(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }
  
  private String executeCommandModifyPhase(ActionContext context,Connection db) throws NumberFormatException, SQLException {  
    if (!hasPermission(context, "admin-actionplans-add")) {
      return ("PermissionError");
    }
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    String phaseId = context.getRequest().getParameter("phaseId");
    if (phaseId == null || "".equals(phaseId)) {
      phaseId = (String) context.getRequest().getAttribute("phaseId");
    }
    ActionPlan plan = null;
    ActionPhase phase = (ActionPhase) context.getFormBean();
    addModuleBean(context, "ActionPhase", "Modify Phase");
    SystemStatus systemStatus = this.getSystemStatus(context);
      plan = new ActionPlan();
      plan.setBuildPhases(true);
      plan.queryRecord(db, Integer.parseInt(planId));
      if (phase.getId() == -1) {
        phase = new ActionPhase(db, Integer.parseInt(phaseId));
      }
      context.getRequest().setAttribute("actionPhase", phase);
      context.getRequest().setAttribute("actionPlan", plan);
      //Build all the actionPhases
      ActionPhaseList list = new ActionPhaseList();
      list.setPlanId(plan.getId());
      list.buildList(db);
      HtmlSelect thisSelect = list.getHtmlSelectObject();
      thisSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes", "-- None --"), 0);
      context.getRequest().setAttribute("actionPhaseSelect", thisSelect);
    return "AddPhaseOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDeletePhase(ActionContext context) {
    if (!(hasPermission(context, "admin-actionplans-delete"))) {
      return ("PermissionError");
    }
    String phaseId = context.getRequest().getParameter("phaseId");
    if (phaseId == null || "".equals(phaseId)) {
      phaseId = (String) context.getRequest().getAttribute("phaseId");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    Connection db = null;
    ActionPhase phase = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      phase = new ActionPhase();
      phase.setBuildSteps(true);
      phase.queryRecord(db, Integer.parseInt(phaseId));

      DependencyList dependencies = phase.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("actionphase.dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='ActionPhaseEditor.do?command=DeletePhase&planId=" + phase.getPlanId() + "&phaseId=" + phase.getId() + "&moduleId="+moduleId+"&constantId="+constantId+ "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDeletePhase(ActionContext context) {
    if (!(hasPermission(context, "admin-actionplans-delete"))) {
      return ("PermissionError");
    }
    String planId = (String) context.getRequest().getParameter("planId");
    if (planId != null && !"".equals(planId)) {
      context.getRequest().setAttribute("planId", planId);
    }
    String phaseId = context.getRequest().getParameter("phaseId");
    if (phaseId == null || "".equals(phaseId)) {
      phaseId = (String) context.getRequest().getAttribute("phaseId");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    ActionPhase phase = null;
    Connection db = null;
    try {
      db = getConnection(context);
      phase = new ActionPhase();
      phase.setBuildSteps(true);
      phase.queryRecord(db, Integer.parseInt(phaseId));
      //delete the phase
      phase.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "ActionPlanEditor.do?command=PlanDetails&planId=" + planId+"&moduleId="+moduleId+"&constantId="+constantId);
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddStep(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandAddStep(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }
  
  private String executeCommandAddStep(ActionContext context,Connection db) throws NumberFormatException, SQLException {  
    if (!hasPermission(context, "admin-actionplans-add")) {
      return ("PermissionError");
    }
    User user = this.getUser(context, this.getUserId(context));
    String previousStepId = context.getRequest().getParameter("previousStepId");
    if (previousStepId != null && !"".equals(previousStepId)) {
      context.getRequest().setAttribute("previousStepId", previousStepId);
    }
    String nextStepId = context.getRequest().getParameter("nextStepId");
    if (nextStepId != null && !"".equals(nextStepId)) {
      context.getRequest().setAttribute("nextStepId", nextStepId);
    }
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    String phaseId = context.getRequest().getParameter("phaseId");
    if (phaseId == null || "".equals(phaseId)) {
      phaseId = (String) context.getRequest().getAttribute("phaseId");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    context.getRequest().setAttribute("moduleId", moduleId);
    context.getRequest().setAttribute("constantId", constantId);
    ActionPlan plan = null;
    ActionPhase phase = null;
    ActionStep step = null;
    CampaignList activeCampaigns = null;
    PermissionCategory permissionCategory = null;
    step = (ActionStep) context.getFormBean();
    addModuleBean(context, "ActionStep", "AddStep");
    SystemStatus systemStatus = this.getSystemStatus(context);
      plan = new ActionPlan(db, Integer.parseInt(planId));
      phase = new ActionPhase();
      phase.setBuildSteps(true);
      phase.queryRecord(db, Integer.parseInt(phaseId));
      if (step == null) {
        step = new ActionStep();
      }
      if (step.getPhaseId() == -1) {
        step.setPhaseId(phase.getId());
      }
      context.getRequest().setAttribute("actionStep", step);
      context.getRequest().setAttribute("actionPhase", phase);
      context.getRequest().setAttribute("actionPlan", plan);

      ActionPhaseList phaseList = new ActionPhaseList();
      phaseList.setPlanId(plan.getId());
      phaseList.buildList(db);
      HtmlSelect thisSelect = phaseList.getHtmlSelectObject();
      context.getRequest().setAttribute("actionPhaseSelect", thisSelect);
      //List of roles
      RoleList roles = new RoleList();
      roles.buildList(db);
      roles.setEmptyHtmlSelectRecord(systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("roles", roles);
      //List of departments
      LookupList departmentType = systemStatus.getLookupList(db, "lookup_department");
      departmentType.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("departmentType", departmentType);
      //List of user groups
      UserGroupList groups = new UserGroupList();
      groups.setSiteId(plan.getSiteId());
      groups.buildList(db);
      groups.setLabelNoneSelected(systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("userGroupList", groups.getHtmlSelectObj(step.getUserGroupId()));
      //List of durations
      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      
      permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      //List of Relationship Types
      RelationshipTypeList relationshipTypes = new RelationshipTypeList();
      if (permissionCategory.getConstant() == PermissionCategory.PERMISSION_CAT_ACCOUNTS) {
        relationshipTypes.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
        CustomFieldCategoryList thisList = new CustomFieldCategoryList();
        thisList.setLinkModuleId(Constants.ACCOUNTS);
        thisList.setIncludeEnabled(Constants.TRUE);
        thisList.setIncludeScheduled(Constants.TRUE);
        thisList.setBuildResources(false);
        thisList.buildList(db);
        context.getRequest().setAttribute("categoryList", thisList);
      }
      relationshipTypes.buildList(db);
      context.getRequest().setAttribute("relationshipTypes", relationshipTypes);
      activeCampaigns = new CampaignList();
      activeCampaigns.setType(Campaign.GENERAL);
      activeCampaigns.setCompleteOnly(true);
      activeCampaigns.buildList(db);
      context.getRequest().setAttribute("campaignList", activeCampaigns);
      //List of Account Types
      LookupList accountTypeList = new LookupList(db, "lookup_account_types");
      accountTypeList.addItem(
          0, systemStatus.getLabel(
          "accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("accountTypeList", accountTypeList);
      //List of required actions
      LookupList stepActionSelect = ActionStep.getStepActionsLookup(db);
      LookupList mappedStepActions = null;
      if (permissionCategory.getConstant() == PermissionCategory.PERMISSION_CAT_TICKETS) {
        mappedStepActions = ActionStep.parseRequiredActionLookupList(db, stepActionSelect, ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      } else if (permissionCategory.getConstant() == PermissionCategory.PERMISSION_CAT_ACCOUNTS) {
        mappedStepActions = ActionStep.parseRequiredActionLookupList(db, stepActionSelect, ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
      }
      mappedStepActions.addItem(-1,systemStatus.getLabel("admin.actionPlan.noAdditionalActionRequired.text","-- No additional action required --"));
      context.getRequest().setAttribute("stepActionSelect", mappedStepActions);
    return "AddStepOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModifyStep(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandModifyStep(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }    

  private String executeCommandModifyStep(ActionContext context,Connection db) throws NumberFormatException, SQLException {    
    if (!hasPermission(context, "admin-actionplans-add")) {
      return ("PermissionError");
    }
    String planId = context.getRequest().getParameter("planId");
    if (planId == null || "".equals(planId)) {
      planId = (String) context.getRequest().getAttribute("planId");
    }
    String phaseId = context.getRequest().getParameter("phaseId");
    if (phaseId == null || "".equals(phaseId)) {
      phaseId = (String) context.getRequest().getAttribute("phaseId");
    }
    String stepId = context.getRequest().getParameter("stepId");
    if (stepId == null || "".equals(stepId)) {
      stepId = (String) context.getRequest().getAttribute("stepId");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    context.getRequest().setAttribute("moduleId", moduleId);
    context.getRequest().setAttribute("constantId", constantId);
    ActionPlan plan = null;
    ActionPhase phase = null;
    CampaignList activeCampaigns = null;
    PermissionCategory permissionCategory = null;
    ActionStep step = (ActionStep) context.getFormBean();
    addModuleBean(context, "ActionStep", "AddStep");
    SystemStatus systemStatus = this.getSystemStatus(context);
      plan = new ActionPlan(db, Integer.parseInt(planId));
      phase = new ActionPhase(db, Integer.parseInt(phaseId));
      if (step.getId() == -1) {
        step = new ActionStep(db, Integer.parseInt(stepId));
      }
      if (step.getPhaseId() == -1) {
        step.setPhaseId(phase.getId());
      }
      context.getRequest().setAttribute("actionStep", step);
      context.getRequest().setAttribute("actionPhase", phase);
      context.getRequest().setAttribute("actionPlan", plan);

      ActionPhaseList phaseList = new ActionPhaseList();
      phaseList.setPlanId(plan.getId());
      phaseList.buildList(db);
      HtmlSelect thisSelect = phaseList.getHtmlSelectObject();
      context.getRequest().setAttribute("actionPhaseSelect", thisSelect);
      //List of roles
      RoleList roles = new RoleList();
      roles.buildList(db);
      roles.setEmptyHtmlSelectRecord(systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("roles", roles);
      //List of departments
      LookupList departmentType = systemStatus.getLookupList(db, "lookup_department");
      departmentType.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("departmentType", departmentType);
      //List of user groups
      UserGroupList groups = new UserGroupList();
      groups.setSiteId(plan.getSiteId());
      groups.buildList(db);
      groups.setLabelNoneSelected(systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("userGroupList", groups.getHtmlSelectObj(step.getUserGroupId()));
      //List of durations
      LookupList durationType = systemStatus.getLookupList(db, "lookup_duration_type");
      context.getRequest().setAttribute("durationType", durationType);
      
      permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      //List of Relationship Types
      RelationshipTypeList relationshipTypes = new RelationshipTypeList();
      if (permissionCategory.getConstant() == PermissionCategory.PERMISSION_CAT_ACCOUNTS) {
        relationshipTypes.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
        CustomFieldCategoryList thisList = new CustomFieldCategoryList();
        thisList.setLinkModuleId(Constants.ACCOUNTS);
        thisList.setIncludeEnabled(Constants.TRUE);
        thisList.setIncludeScheduled(Constants.TRUE);
        thisList.setBuildResources(false);
        thisList.buildList(db);
        context.getRequest().setAttribute("categoryList", thisList);
      }
      relationshipTypes.buildList(db);
      context.getRequest().setAttribute("relationshipTypes", relationshipTypes);
      activeCampaigns = new CampaignList();
      activeCampaigns.setCompleteOnly(true);
      activeCampaigns.setActive(Constants.TRUE);
      activeCampaigns.buildList(db);
      context.getRequest().setAttribute("campaignList", activeCampaigns);
      //List of Account Types
      LookupList accountTypeList = new LookupList(db, "lookup_account_types");
      accountTypeList.addItem(
          0, systemStatus.getLabel(
          "accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("accountTypeList", accountTypeList);
      //List of required actions
      LookupList stepActionSelect = ActionStep.getStepActionsLookup(db);
      LookupList mappedStepActions = null;
      
      if (permissionCategory.getConstant() == PermissionCategory.PERMISSION_CAT_TICKETS) {
        mappedStepActions = ActionStep.parseRequiredActionLookupList(db, stepActionSelect, ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      } else if (permissionCategory.getConstant() == PermissionCategory.PERMISSION_CAT_ACCOUNTS) {
        mappedStepActions = ActionStep.parseRequiredActionLookupList(db, stepActionSelect, ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
      }
      mappedStepActions.addItem(-1,systemStatus.getLabel("admin.actionPlan.noAdditionalActionRequired.text","-- No additional action required --"));
      context.getRequest().setAttribute("stepActionSelect", mappedStepActions);
      //Make the account types available in the request
      context.getRequest().setAttribute(
            "typeList", step.getAccountTypes());
    return "ModifyStepOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSaveStep(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-edit")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    String stepId = context.getRequest().getParameter("stepId");
    if (stepId == null || "".equals(stepId)) {
      stepId = (String) context.getRequest().getAttribute("stepId");
    }
    String previousStepId = context.getRequest().getParameter("previousStepId");
    String nextStepId = context.getRequest().getParameter("nextStepId");
    boolean isValid = false;
    boolean recordInserted = false;
    int recordCount = -1;
    ActionStep step = (ActionStep) context.getFormBean();
    ActionStep oldStep = null;
    Connection db = null;
    addModuleBean(context, "ActionStep", "AddStep");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      step.setAccountTypes(context.getRequest().getParameterValues("selectedList"));
      if (context.getRequest().getParameter("allowUpdate") == null) {
        step.setAllowUpdate(false);
      }
      if (step.getId() > 0) {
        oldStep = new ActionStep(db, step.getId());
        isValid = this.validateObject(context, db, step);
        if (isValid) {
          recordCount = step.update(db);
        }
      } else {
        isValid = this.validateObject(context, db, step);
        if (isValid) {
          recordInserted = step.insert(db);
        }
      }
      if (recordCount > 0) {
        step.checkRemoveStepAttachments(db, oldStep);
      }
      if (!isValid || (!recordInserted && recordCount == -1)) {
        context.getRequest().setAttribute("phaseId", String.valueOf(step.getPhaseId()));
        ActionPhase phase = new ActionPhase(db, step.getPhaseId());
        context.getRequest().setAttribute("planId", String.valueOf(phase.getPlanId()));
        context.getRequest().setAttribute(
            "typeList", step.getAccountTypes());
        if (step.getId() > -1) {
          return executeCommandModifyStep(context,db);
        } else {
          return executeCommandAddStep(context,db);
        }
      } else {
        if (previousStepId != null && !"".equals(previousStepId)) {
          step.setAfterStepId(db, previousStepId, true);
        } else if (nextStepId != null && !"".equals(nextStepId)) {
          step.setBeforeStepId(db, nextStepId, true);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SaveStepOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandMoveStep(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-edit")) {
      return ("PermissionError");
    }
    String previousStepId = context.getRequest().getParameter("previousStepId");
    String nextStepId = context.getRequest().getParameter("nextStepId");
    String stepId = context.getRequest().getParameter("stepId");
    String moveStepUp = context.getRequest().getParameter("moveStepUp");
    ActionStep step = null;
    Connection db = null;
    addModuleBean(context, "ActionPlan", "MoveStep");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      step = new ActionStep(db, Integer.parseInt(stepId));
      //Move step up or down
      if (moveStepUp != null && !"".equals(moveStepUp)) {
        if (DatabaseUtils.parseBoolean(moveStepUp)) {
          if (step.getParentId() != -1 && step.getParentId() != 0) {
            nextStepId = String.valueOf(step.getParentId());
          }
        } else {
          int result = ActionStep.getStepIdGivenParentId(db, step.getId());
          if (result != -1) {
            previousStepId = String.valueOf(result);
          }
        }
      } else {
        return "PermissionError";
      }
      if (previousStepId != null && !"".equals(previousStepId)) {
        step.setAfterStepId(db, previousStepId, false);
      } else if (nextStepId != null && !"".equals(nextStepId)) {
        step.setBeforeStepId(db, nextStepId, false);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "MoveStepOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDeleteStep(ActionContext context) {
    if (!(hasPermission(context, "admin-actionplans-delete"))) {
      return ("PermissionError");
    }
    String stepId = context.getRequest().getParameter("stepId");
    if (stepId == null || "".equals(stepId)) {
      stepId = (String) context.getRequest().getAttribute("stepId");
    }
    Connection db = null;
    String planId = context.getRequest().getParameter("planId");
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    ActionStep step = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      step = new ActionStep();
      step.queryRecord(db, Integer.parseInt(stepId));

      DependencyList dependencies = step.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("actionstep.dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='ActionStepEditor.do?command=DeleteStep&stepId=" + step.getId() + "&planId=" + planId +"&moduleId="+moduleId+"&constantId="+constantId+ "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDeleteStep(ActionContext context) {
    if (!(hasPermission(context, "admin-actionplans-delete"))) {
      return ("PermissionError");
    }
    String planId = (String) context.getRequest().getParameter("planId");
    if (planId != null && !"".equals(planId)) {
      context.getRequest().setAttribute("planId", planId);
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    String stepId = (String) context.getRequest().getParameter("stepId");
    if (stepId != null && !"".equals(stepId)) {
      context.getRequest().setAttribute("stepId", stepId);
    }
    ActionStep step = null;
    Connection db = null;
    try {
      db = getConnection(context);
      step = new ActionStep();
      step.queryRecord(db, Integer.parseInt(stepId));
      //delete the step
      step.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "ActionStepEditor.do?command=PlanDetails&planId=" + planId+ "&moduleId="+moduleId+"&constantId="+constantId);
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    Connection db = null;
    try {
      User user = this.getUser(context, this.getUserId(context));
      String reset = context.getRequest().getParameter("reset");
      String catCode = context.getRequest().getParameter("catCode");
      String subCat1 = context.getRequest().getParameter("subCat1");
      String subCat2 = context.getRequest().getParameter("subCat2");
      String siteId = context.getRequest().getParameter("siteId");
      if (siteId == null || "".equals(siteId.trim())) {
        siteId = String.valueOf(user.getSiteId());
      }
      if (!isSiteAccessPermitted(context, siteId)) {
        return ("PermissionError");
      }
/*      if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
        return ("PermissionError");
      }*/
      db = this.getConnection(context);
      if (reset != null && "true".equals(reset)) {
        ActionPlanCategoryList categoryList = new ActionPlanCategoryList();
        categoryList.setCatLevel(0);
        categoryList.setSiteId(siteId);
        categoryList.setExclusiveToSite(true);
        categoryList.buildList(db);
        context.getRequest().setAttribute("CategoryList", categoryList);
      } else if (catCode != null) {
        ActionPlanCategoryList subList1 = new ActionPlanCategoryList();
        subList1.setCatLevel(1);
        subList1.setParentCode(Integer.parseInt(catCode));
        subList1.setSiteId(siteId);
        subList1.setExclusiveToSite(true);
        subList1.buildList(db);
        context.getRequest().setAttribute("SubList1", subList1);
      } else if (subCat1 != null) {
        ActionPlanCategoryList subList2 = new ActionPlanCategoryList();
        subList2.setCatLevel(2);
        subList2.setParentCode(Integer.parseInt(subCat1));
        subList2.setSiteId(siteId);
        subList2.setExclusiveToSite(true);
        subList2.buildList(db);
        context.getRequest().setAttribute("SubList2", subList2);
      } else if (subCat2 != null) {
        ActionPlanCategoryList subList3 = new ActionPlanCategoryList();
        subList3.setCatLevel(3);
        subList3.setParentCode(Integer.parseInt(subCat2));
        subList3.setSiteId(siteId);
        subList3.setExclusiveToSite(true);
        subList3.buildList(db);
        context.getRequest().setAttribute("SubList3", subList3);
      }
    } catch (Exception errorMessage) {

    } finally {
      this.freeConnection(context, db);
    }
    return ("CategoryJSListOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddMappings(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("constantId", constantId);
      HashMap map = ActionStep.getRequiredActionMappings(db, Integer.parseInt(constantId));
      context.getRequest().setAttribute("elements", map);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList stepActions = systemStatus.getLookupList(db, "lookup_step_actions");
      context.getRequest().setAttribute("stepActions", stepActions);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddMappingsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSaveMappings(ActionContext context) { 
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String constantId = context.getRequest().getParameter("constantId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("constantId", constantId);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList stepActions = ActionStep.getStepActionsLookup(db);
      boolean result = ActionStep.parseRequiredActionMappings(context, db, stepActions, Integer.parseInt(constantId));
      if (!result) {
        // TODO: Executing a new action within an open db can create a deadlock
        return executeCommandAddMappings(context);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SaveMappingsOK";
  }
}

