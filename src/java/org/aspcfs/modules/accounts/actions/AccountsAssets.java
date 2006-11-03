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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.assets.base.AssetMaterialList;
import org.aspcfs.modules.base.CategoryList;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.servicecontracts.base.ServiceContract;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Action handler to list, view, add, update and delete assets
 *
 * @author kbhoopal
 * @version $Id: AccountsAssets.java,v 1.1.8.1 2004/01/30 16:20:28 mrajkowski
 *          Exp $
 * @created December 23, 2003
 */
public class AccountsAssets extends CFSModule {

  /**
   * Action handler to list, add, view, update and delete assets
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {

    return ("OK");
  }


  /**
   * Lists the assets of an account
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-view")) {
      return ("PermissionError");
    }
    String parentId = context.getRequest().getParameter("parentId");
    AssetList assetList = new AssetList();
    String orgId = context.getRequest().getParameter("orgId");

    //Prepare pagedListInfo
    PagedListInfo assetListInfo = this.getPagedListInfo(
        context, "AssetListInfo");
    assetListInfo.setLink("AccountsAssets.do?command=List&orgId=" + orgId+"&parentId="+(parentId != null?parentId:""));
    Connection db = null;
    try {
      db = this.getConnection(context);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }
      setOrganization(context, db);
      // Build the asset list
      assetList.setPagedListInfo(assetListInfo);
      assetList.setOrgId(Integer.parseInt(orgId));
      if (parentId != null && !"".equals(parentId.trim()) && !"-1".equals(parentId.trim())) {
        assetList.setParentId(parentId);
        Asset parent = new Asset();
        parent.setBuildCompleteParentList(true);
        parent.setIncludeMe(true);
        parent.queryRecord(db, Integer.parseInt(parentId));
        context.getRequest().setAttribute("parent", parent);
      }
      Organization tmpOrganization = (Organization) context.getRequest().getAttribute(
          "OrgDetails");
      if (tmpOrganization.isTrashed()) {
        assetList.setIncludeOnlyTrashed(true);
      }
      assetList.buildList(db);
      buildFormElements(context, db);
      buildCategories(context, db, null);
      context.getRequest().setAttribute("assetList", assetList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsAssetsList");
  }


  /**
   * Adds an asset to the service contract/account
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-add")) {
      return ("PermissionError");
    }
    String parentId = context.getRequest().getParameter("parentId");
    Connection db = null;
    String orgId = context.getRequest().getParameter("orgId");
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }
      setOrganization(context, db);

      Asset thisAsset = (Asset) context.getFormBean();
      buildFormElements(context, db);
      buildCategories(context, db, thisAsset);
      if (parentId != null && !"".equals(parentId.trim()) && !"-1".equals(parentId.trim())) {
        if (thisAsset.getParentId() == -1) {
          thisAsset.setParentId(parentId);
        }
        Asset parent = new Asset();
        parent.setBuildCompleteParentList(true);
        parent.setIncludeMe(true);
        parent.queryRecord(db, Integer.parseInt(parentId));
        context.getRequest().setAttribute("parent", parent);
      }
      Organization orgDetails = (Organization) context.getRequest().getAttribute("OrgDetails");
      ContactList contactList = new ContactList();
      contactList.setOrgId(orgId);
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("contactList", contactList);

      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
      context.getRequest().setAttribute("asset", thisAsset);
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsAssetsAdd");
  }


  /**
   * Saves the asset
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean inserted = false;
    boolean isValid = false;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      Asset thisAsset = (Asset) context.getFormBean();
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }
      thisAsset.setEnteredBy(getUserId(context));
      thisAsset.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, thisAsset);
      if (isValid) {
        inserted = thisAsset.insert(db);
      }
      if (inserted) {
        thisAsset = new Asset(db, "" + thisAsset.getId());
        if (thisAsset.getStatusName() == null || "".equals(
            thisAsset.getStatusName())) {
          thisAsset.setStatusName(
              systemStatus.getLabel(
                  "accounts.accounts_contacts_calls_details_followup_include.None"));
        }
        this.processInsertHook(context, thisAsset);
      }
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    if (inserted) {
      return (executeCommandList(context));
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-edit")) {
      return ("PermissionError");
    }

    Connection db = null;
    String orgId = (String) context.getRequest().getParameter("orgId");
    if (orgId == null || "".equals(orgId)) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      int assetId = Integer.parseInt(
          (context.getRequest().getParameter("id")));
      Asset thisAsset = (Asset) context.getFormBean();
      if (thisAsset.getId() == -1) {
        thisAsset.setBuildMaterials(true);
        thisAsset.queryRecord(db, assetId);
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }
      buildFormElements(context, db);
      buildCategories(context, db, thisAsset);
      thisAsset.buildCompleteParentList(db);
      context.getRequest().setAttribute(
          "return", context.getRequest().getParameter("return"));

      Organization orgDetails = (Organization) context.getRequest().getAttribute("OrgDetails");
      ContactList contactList = new ContactList();
      contactList.setOrgId(orgDetails.getOrgId());
      contactList.setDefaultContactId(thisAsset.getContactId());
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("contactList", contactList);
      context.getRequest().setAttribute("asset", thisAsset);
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsAssetsModify");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-edit")) {
      return ("PermissionError");
    }
    boolean isValid = false;
    Connection db = null;
    int resultCount = -1;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);
      Asset thisAsset = (Asset) context.getFormBean();
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }
      Asset previousAsset = new Asset(db, "" + thisAsset.getId());
      thisAsset.setModifiedBy(getUserId(context));
      thisAsset.buildCompleteParentList(db);
      isValid = this.validateObject(context, db, thisAsset);
      if (isValid) {
        resultCount = thisAsset.update(db);
      }
      if (resultCount > 0) {
        thisAsset = new Asset(db, "" + thisAsset.getId());
        if (thisAsset.getStatusName() == null || "".equals(
            thisAsset.getStatusName())) {
          thisAsset.setStatusName(
              systemStatus.getLabel(
                  "accounts.accounts_contacts_calls_details_followup_include.None"));
        }
        this.processUpdateHook(context, previousAsset, thisAsset);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandList(context);
      } else {
        return executeCommandView(context);
      }
    } else {
      if (resultCount == -1 || !isValid) {
        return (executeCommandModify(context));
      }
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-delete")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    String id = context.getRequest().getParameter("id");
    String orgId = context.getRequest().getParameter("orgId");

    try {
      db = this.getConnection(context);
      Asset thisAsset = new Asset(db, id);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      DependencyList dependencies = thisAsset.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (dependencies.canDelete()) {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AccountsAssets.do?command=Trash&action=delete&orgId=" + orgId + "&id=" + id 
            + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType") + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.delete"), "javascript:window.location.href='AccountsAssets.do?command=Trash&action=delete&orgId=" + orgId + "&id=" + id + "&forceDelete=true" 
            + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType") + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-delete")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean recordUpdated = false;
    Connection db = null;
    Asset thisAsset = null;
    try {

      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      thisAsset = new Asset();
      thisAsset.setId(context.getRequest().getParameter("id"));
      recordUpdated = thisAsset.updateStatus(db, true, this.getUserId(context));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean inline = (context.getRequest().getParameter("popupType") != null && "inline"
        .equals(context.getRequest().getParameter("popupType")));
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "AccountsAssets.do?command=List&orgId=" + context.getRequest().getParameter(
              "orgId")+(inline?"&popup=true":""));
      //return (executeCommandList(context));
      return "DeleteOK";
    }
    processErrors(context, thisAsset.getErrors());
    context.getRequest().setAttribute(
        "actionError", systemStatus.getLabel(
            "object.validation.actionError.assetDeletion"));
    context.getRequest().setAttribute(
        "refreshUrl", "AccountsAssets.do?command=View&orgId=" + context.getRequest().getParameter(
            "orgId")+(inline?"&popup=true":""));
    //return executeCommandView(context);
    return "DeleteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-delete")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean recordDeleted = false;
    Connection db = null;
    Asset thisAsset = null;
    try {

      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      thisAsset = new Asset(db, context.getRequest().getParameter("id"));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }
      recordDeleted = thisAsset.delete(db, getDbNamePath(context));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      context.getRequest().setAttribute(
          "refreshUrl", "AccountsAssets.do?command=List&orgId=" + context.getRequest().getParameter(
              "orgId"));
      //return (executeCommandList(context));
      return getReturn(context, "Delete");
    }
    boolean inline = (context.getRequest().getParameter("popupType") != null 
                      && "inline".equals(context.getRequest().getParameter("popupType")));
    processErrors(context, thisAsset.getErrors());
    context.getRequest().setAttribute(
        "actionError", systemStatus.getLabel(
            "object.validation.actionError.assetDeletion"));
    context.getRequest().setAttribute(
        "refreshUrl", "AccountsAssets.do?command=View&orgId=" + context.getRequest().getParameter(
            "orgId"));
    //return executeCommandView(context);
    return getReturn(context, "Delete");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);

      String id = context.getRequest().getParameter("id");
      if (id == null || "".equals(id)) {
        id = (String) context.getRequest().getAttribute("id");
      }
      Asset thisAsset = new Asset();
      thisAsset.setBuildMaterials(true);
      thisAsset.setBuildCompleteParentList(true);
      thisAsset.queryRecord(db, Integer.parseInt(id));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }

      //Set Organization -- do seperately as orgId is not available as parameter
      Organization thisOrganization = new Organization(
          db, thisAsset.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      ServiceContract thisContract = new ServiceContract();
      thisContract.queryRecord(db, thisAsset.getContractId());

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }

      if (thisAsset.getContactId() > -1) {
        Contact thisContact = new Contact(db, thisAsset.getContactId());
        context.getRequest().setAttribute("assetContact", thisContact);
      }
      buildFormElements(context, db);
      buildCategories(context, db, thisAsset);
      context.getRequest().setAttribute("asset", thisAsset);
      context.getRequest().setAttribute("serviceContract", thisContract);

      return getReturn(context, "AccountsAssetsView");
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
  }


  /**
   * Retrieves all tickets related to an asset
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandHistory(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-view")) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);

      String id = context.getRequest().getParameter("id");
      Asset thisAsset = new Asset(db, id);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisAsset.getOrgId())) {
        return ("PermissionError");
      }

      //Set Organization -- do seperately as orgId is not available as parameter
      Organization thisOrganization = new Organization(
          db, thisAsset.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Prepare pagedListInfo
      PagedListInfo assetHistoryInfo = this.getPagedListInfo(
          context, "AssetHistoryInfo");
      assetHistoryInfo.setLink(
          "AccountsAssets.do?command=History&id=" + thisAsset.getId() + 
          RequestUtils.addLinkParams(context.getRequest(), "popup|popupType"));
      TicketList ticketList = new TicketList();
      ticketList.setPagedListInfo(assetHistoryInfo);
      ticketList.setAssetId(thisAsset.getId());
      ticketList.buildList(db);
      context.getRequest().setAttribute("ticketList", ticketList);
      thisAsset.buildCompleteParentList(db);
      context.getRequest().setAttribute("asset", thisAsset);
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsAssetsHistory");
  }


  /**
   * Loads related data for use in an HTML Form
   *
   * @param context Description of the Parameter
   * @param db      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildFormElements(ActionContext context, Connection db) throws SQLException {
    SystemStatus thisSystem = this.getSystemStatus(context);

    LookupList assetStatusList = new LookupList(db, "lookup_asset_status");
    assetStatusList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("assetStatusList", assetStatusList);

    LookupList assetVendorList = new LookupList(db, "lookup_asset_vendor");
    assetVendorList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("assetVendorList", assetVendorList);

    LookupList assetManufacturerList = new LookupList(db, "lookup_asset_manufacturer");
    assetManufacturerList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("assetManufacturerList", assetManufacturerList);

    LookupList responseModelList = new LookupList(db, "lookup_response_model");
    responseModelList.addItem(
        -1, thisSystem.getLabel("accounts.assets.contractDefault"));
    context.getRequest().setAttribute("responseModelList", responseModelList);

    LookupList phoneModelList = new LookupList(db, "lookup_phone_model");
    phoneModelList.addItem(
        -1, thisSystem.getLabel("accounts.assets.contractDefault"));
    context.getRequest().setAttribute("phoneModelList", phoneModelList);

    LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
    onsiteModelList.addItem(
        -1, thisSystem.getLabel("accounts.assets.contractDefault"));
    context.getRequest().setAttribute("onsiteModelList", onsiteModelList);

    LookupList assetMaterialList = new LookupList(db, "lookup_asset_materials");
    context.getRequest().setAttribute("assetMaterialList", assetMaterialList);

    LookupList emailModelList = new LookupList(db, "lookup_email_model");
    emailModelList.addItem(
        -1, thisSystem.getLabel("accounts.assets.contractDefault"));
    context.getRequest().setAttribute("emailModelList", emailModelList);
  }


  /**
   * Loads related category data for use in an HTML form. Rule-based so that
   * only the data that is currently needed in the form is loaded. This method
   * is also currently used for loading data for use in details and list forms,
   * however too much data is loaded in this case.
   *
   * @param context   Description of the Parameter
   * @param db        Description of the Parameter
   * @param thisAsset Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildCategories(ActionContext context, Connection db, Asset thisAsset) throws SQLException {
    Organization orgDetails = (Organization) context.getRequest().getAttribute(
        "OrgDetails");
    CategoryList categoryList1 = new CategoryList("asset_category");
    categoryList1.setCatLevel(0);
    categoryList1.setParentCode(0);
    categoryList1.setSiteId(orgDetails.getSiteId());
    categoryList1.setExclusiveToSite(true);
    categoryList1.buildList(db);
    categoryList1.setHtmlJsEvent(
        "onChange=\"javascript:updateCategoryList('1');\"");
    categoryList1.getCatListSelect().addItem(-1, "Undetermined");
    context.getRequest().setAttribute("categoryList1", categoryList1);

    CategoryList categoryList2 = new CategoryList("asset_category");
    categoryList2.setCatLevel(1);
    categoryList2.setSiteId(orgDetails.getSiteId());
    if (thisAsset == null) {
      categoryList2.buildList(db);
    } else if (thisAsset.getLevel1() > -1) {
      categoryList2.setParentCode(thisAsset.getLevel1());
      categoryList2.buildList(db);
    }
    categoryList2.setSiteId(orgDetails.getSiteId());
    categoryList2.setExclusiveToSite(true);
    categoryList2.setHtmlJsEvent(
        "onChange=\"javascript:updateCategoryList('2');\"");
    categoryList2.getCatListSelect().addItem(-1, "Undetermined");
    context.getRequest().setAttribute("categoryList2", categoryList2);

    CategoryList categoryList3 = new CategoryList("asset_category");
    categoryList3.setCatLevel(2);
    categoryList3.setSiteId(orgDetails.getSiteId());
    if (thisAsset == null) {
      categoryList3.buildList(db);
    } else if (thisAsset.getLevel2() > -1) {
      categoryList3.setParentCode(thisAsset.getLevel2());
      categoryList3.buildList(db);
    }
    categoryList3.setSiteId(orgDetails.getSiteId());
    categoryList3.setExclusiveToSite(true);
    categoryList3.getCatListSelect().addItem(-1, "Undetermined");
    context.getRequest().setAttribute("categoryList3", categoryList3);
  }


  /**
   * Sets the organization attribute of the AccountsAssets object
   *
   * @param context The new organization value
   * @param db      The new organization value
   * @throws SQLException Description of the Exception
   */
  public void setOrganization(ActionContext context, Connection db) throws SQLException {
    Organization thisOrganization = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null || "".equals(orgId)) {
      String contactId = context.getRequest().getParameter("contactId");
      Contact contact = new Contact(db, Integer.parseInt(contactId));
      orgId = "" + contact.getOrgId();
      context.getRequest().setAttribute("orgId", orgId);
    }
    thisOrganization = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }


  /**
   * Handles the JavaScript request to build the next category
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    Connection db = null;
    try {
      int level = Integer.parseInt(context.getRequest().getParameter("level"));
      int code = Integer.parseInt(context.getRequest().getParameter("code"));
      int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      Organization orgDetails = new Organization(db, orgId);
      CategoryList categoryList = new CategoryList("asset_category");
      categoryList.setCatLevel(level);
      categoryList.setSiteId(orgDetails.getSiteId());
      categoryList.setExclusiveToSite(true);
      categoryList.setParentCode(code);
      if (code > 0) {
        db = this.getConnection(context);
        // Build the info for the new level to populate
        // NOTE: the html is 1 based, catLevel is 0 based
        categoryList.buildList(db);
      }
      context.getRequest().setAttribute("categoryList", categoryList);
    } catch (Exception e) {
      // This is in an iframe which the user doesn't see
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }
    return ("CategoryJSListOK");
  }

  public String executeCommandViewMaterials(ActionContext context) {
    Connection db = null;
    HashMap map = new HashMap();
    LookupList list = new LookupList();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      String assetId = context.getRequest().getParameter("assetId");
      if (assetId == null || "".equals(assetId)) {
        assetId = (String) context.getRequest().getAttribute("assetId");
      }
      String materials = context.getRequest().getParameter("materials");
      db = this.getConnection(context);
      if (materials != null && !"".equals(materials)) {
        list = systemStatus.getLookupList(db, "lookup_asset_materials");
        map = AssetMaterialList.getQuantityMap(materials);
      }
      context.getRequest().setAttribute("materialTypeList", list);
      context.getRequest().setAttribute("materialMap", map);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ViewMaterials");
  }
}
