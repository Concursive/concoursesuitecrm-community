package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.servicecontracts.base.*;
import org.aspcfs.modules.assets.base.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.login.beans.*;
import org.aspcfs.modules.mycfs.beans.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.CategoryList;

/**
 *  Action handler to list, view, add, update and delete assets
 *
 *@author     kbhoopal
 *@created    December 23, 2003
 *@version    $Id: AccountsAssets.java,v 1.1.8.1 2004/01/30 16:20:28 mrajkowski
 *      Exp $
 */
public class AccountsAssets extends CFSModule {

  /**
   *  Action handler to list, add, view, update and delete assets
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {

    return ("OK");
  }


  /**
   *  Lists the assets of an account
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-view")) {
      return ("PermissionError");
    }
    AssetList assetList = new AssetList();
    String orgId = context.getRequest().getParameter("orgId");

    //find record permissions for portal users
    if (!isRecordAccessPermitted(context,Integer.parseInt(orgId))){
      return ("PermissionError");
    }
    //Prepare pagedListInfo
    PagedListInfo assetListInfo = this.getPagedListInfo(context, "AssetListInfo");
    assetListInfo.setLink("AccountsAssets.do?command=List&orgId=" + orgId);
    Connection db = null;
    try {
      db = this.getConnection(context);
      setOrganization(context, db);
      // Build the asset list
      assetList.setPagedListInfo(assetListInfo);
      assetList.setOrgId(Integer.parseInt(orgId));
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
    return ("AccountsAssetsListOK");
  }


  /**
   *  Adds an asset to the service contract/account
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      Asset thisAsset = (Asset) context.getFormBean();
      buildFormElements(context, db);
      buildCategories(context, db, thisAsset);

      ContactList contactList = new ContactList();
      contactList.setOrgId(Integer.parseInt(context.getRequest().getParameter("orgId")));
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord("-- None --");
      context.getRequest().setAttribute("contactList", contactList);

      Calendar calendar = Calendar.getInstance();
      String currentDate = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
      context.getRequest().setAttribute("currentDate", currentDate);

      return ("AccountsAssetsAddOK");
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
   *  Saves the asset
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean inserted = false;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      Asset thisAsset = (Asset) context.getFormBean();
      thisAsset.setEnteredBy(getUserId(context));
      thisAsset.setModifiedBy(getUserId(context));
      inserted = thisAsset.insert(db);

      if(!inserted){
        processErrors(context, thisAsset.getErrors());
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
    } else {
      return (executeCommandAdd(context));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-edit")) {
      return ("PermissionError");
    }

    Connection db = null;

    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      int assetId = Integer.parseInt((context.getRequest().getParameter("id")));
      Asset thisAsset = (Asset) context.getFormBean();
      thisAsset.queryRecord(db, assetId);
      buildFormElements(context, db);
      buildCategories(context, db, thisAsset);
      context.getRequest().setAttribute("return", context.getRequest().getParameter("return"));
      
      ContactList contactList = new ContactList();
      contactList.setOrgId(Integer.parseInt(context.getRequest().getParameter("orgId")));
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord("-- None --");
      context.getRequest().setAttribute("contactList", contactList);
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    return ("AccountsAssetsModifyOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      Asset thisAsset = (Asset) context.getFormBean();
      thisAsset.setModifiedBy(getUserId(context));
      resultCount = thisAsset.update(db);
      if (resultCount == -1){
        processErrors(context, thisAsset.getErrors());
      }
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    if (resultCount == -1){
      return (executeCommandModify(context));
    }else if (resultCount == 1){
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandList(context);
      } else {
        return executeCommandView(context);
      }
    }else{
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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

      htmlDialog.setTitle("Dark Horse CRM: Account Management - Asset");
      DependencyList dependencies = thisAsset.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      htmlDialog.setHeader("The asset you are requesting to delete has the following dependencies within Dark Horse CRM:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='AccountsAssets.do?command=Delete&action=delete&orgId=" + orgId + "&id=" + id + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-assets-delete")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Connection db = null;
    Asset thisAsset = null;
    try {

      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      thisAsset = new Asset();
      thisAsset.setId(context.getRequest().getParameter("id"));
      recordDeleted = thisAsset.deleteAll(db, this.getPath(context, "accounts"));
    } catch (Exception e) {
      errorMessage = e;
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "AccountsAssets.do?command=List&orgId=" + context.getRequest().getParameter("orgId"));
        //return (executeCommandList(context));
        return this.getReturn(context, "Delete");
      }

      processErrors(context, thisAsset.getErrors());
      context.getRequest().setAttribute("refreshUrl", "AccountsAssets.do?command=View&orgId=" + context.getRequest().getParameter("orgId") + "&id=" + context.getRequest().getParameter("id"));
      //return executeCommandView(context);
      return this.getReturn(context, "Delete");
    }

    System.out.println(errorMessage);
    context.getRequest().setAttribute("actionError", "Asset could not be deleted because of referential integrity .");
    context.getRequest().setAttribute("refreshUrl", "AccountsAssets.do?command=View&orgId=" + context.getRequest().getParameter("orgId"));
    return ("DeleteError");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      Asset thisAsset = new Asset(db, id);

      //Set Organization -- do seperately as orgId is not available as parameter
      Organization thisOrganization = new Organization(db, thisAsset.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      ServiceContract thisContract = new ServiceContract();
      thisContract.queryRecord(db, thisAsset.getContractId());

      
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context,thisContract.getOrgId())){
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

      return ("AccountsAssetsViewOK");
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
   *  Retrieves all tickets related to an asset
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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

      //Set Organization -- do seperately as orgId is not available as parameter
      Organization thisOrganization = new Organization(db, thisAsset.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      //Prepare pagedListInfo
      PagedListInfo assetHistoryInfo = this.getPagedListInfo(context, "AssetHistoryInfo");
      assetHistoryInfo.setLink("AccountsAssets.do?command=History&id=" + thisAsset.getId());
      TicketList ticketList = new TicketList();
      ticketList.setPagedListInfo(assetHistoryInfo);
      ticketList.setAssetId(thisAsset.getId());
      ticketList.buildList(db);
      context.getRequest().setAttribute("ticketList", ticketList);
      
      context.getRequest().setAttribute("asset", thisAsset);
      return ("AccountsAssetsHistoryOK");
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
   *  Loads related data for use in an HTML Form
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildFormElements(ActionContext context, Connection db) throws SQLException {
    LookupList assetStatusList = new LookupList(db, "lookup_asset_status");
    assetStatusList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("assetStatusList", assetStatusList);

    LookupList responseModelList = new LookupList(db, "lookup_response_model");
    responseModelList.addItem(-1, "-- Contract Default --");
    context.getRequest().setAttribute("responseModelList", responseModelList);

    LookupList phoneModelList = new LookupList(db, "lookup_phone_model");
    phoneModelList.addItem(-1, "-- Contract Default --");
    context.getRequest().setAttribute("phoneModelList", phoneModelList);

    LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
    onsiteModelList.addItem(-1, "-- Contract Default --");
    context.getRequest().setAttribute("onsiteModelList", onsiteModelList);

    LookupList emailModelList = new LookupList(db, "lookup_email_model");
    emailModelList.addItem(-1, "-- Contract Default --");
    context.getRequest().setAttribute("emailModelList", emailModelList);
  }


  /**
   *  Loads related category data for use in an HTML form. Rule-based so that
   *  only the data that is currently needed in the form is loaded. This method
   *  is also currently used for loading data for use in details and list forms,
   *  however too much data is loaded in this case.
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  thisAsset         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildCategories(ActionContext context, Connection db, Asset thisAsset) throws SQLException {
    CategoryList categoryList1 = new CategoryList("asset_category");
    categoryList1.setCatLevel(0);
    categoryList1.setParentCode(0);
    categoryList1.buildList(db);
    categoryList1.setHtmlJsEvent("onChange=\"javascript:updateCategoryList('1');\"");
    categoryList1.getCatListSelect().addItem(-1, "Undetermined");
    context.getRequest().setAttribute("categoryList1", categoryList1);

    CategoryList categoryList2 = new CategoryList("asset_category");
    categoryList2.setCatLevel(1);
    if (thisAsset == null) {
      categoryList2.buildList(db);
    } else if (thisAsset.getLevel1() > -1) {
      categoryList2.setParentCode(thisAsset.getLevel1());
      categoryList2.buildList(db);
    }
    categoryList2.setHtmlJsEvent("onChange=\"javascript:updateCategoryList('2');\"");
    categoryList2.getCatListSelect().addItem(-1, "Undetermined");
    context.getRequest().setAttribute("categoryList2", categoryList2);

    CategoryList categoryList3 = new CategoryList("asset_category");
    categoryList3.setCatLevel(2);
    if (thisAsset == null) {
      categoryList3.buildList(db);
    } else if (thisAsset.getLevel2() > -1) {
      categoryList3.setParentCode(thisAsset.getLevel2());
      categoryList3.buildList(db);
    }
    categoryList3.getCatListSelect().addItem(-1, "Undetermined");
    context.getRequest().setAttribute("categoryList3", categoryList3);
  }


  /**
   *  Sets the organization attribute of the AccountsAssets object
   *
   *@param  context           The new organization value
   *@param  db                The new organization value
   *@exception  SQLException  Description of the Exception
   */
  public void setOrganization(ActionContext context, Connection db) throws SQLException {
    Organization thisOrganization = null;
    String orgId = context.getRequest().getParameter("orgId");
    thisOrganization = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }


  /**
   *  Handles the JavaScript request to build the next category
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    Connection db = null;
    try {
      int level = Integer.parseInt(context.getRequest().getParameter("level"));
      int code = Integer.parseInt(context.getRequest().getParameter("code"));
      CategoryList categoryList = new CategoryList("asset_category");
      categoryList.setCatLevel(level);
      categoryList.setParentCode(code);
      if (code > 0) {
        db = this.getConnection(context);
        // Build the info for the new level to populate
        // NOTE: the html is 1 based, catLevel is 0 based
        categoryList.buildList(db);
      }
      context.getRequest().setAttribute("categoryList", categoryList);
    } catch (Exception errorMessage) {

    } finally {
      this.freeConnection(context, db);
    }
    return ("CategoryJSListOK");
  }
}
