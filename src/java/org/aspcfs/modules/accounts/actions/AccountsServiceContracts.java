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
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.servicecontracts.base.*;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * A service contract action handler provides action methods to List, view, add and modify service
 * service contracts. Service Contracts are contractual agreements with accounts (a.k.a. organizations)
 * that specify the service model, start and end dates, hours and other information
 *
 * @author kbhoopal
 * @version $Id: AccountsServiceContracts.java,v 1.1.2.1 2003/12/24 16:07:31
 *          kbhoopal Exp $
 * @created December 23, 2003
 */
public class AccountsServiceContracts extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return ("OK");
  }


  /**
   * Lists the service contracts of an account
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-view")) {
      return ("PermissionError");
    }
    ServiceContractList serviceContractList = new ServiceContractList();
    String orgId = context.getRequest().getParameter("orgId");
    //Prepare pagedListInfo
    PagedListInfo serviceContractListInfo = this.getPagedListInfo(
        context, "ServiceContractListInfo");
    serviceContractListInfo.setLink(
        "AccountsServiceContracts.do?command=List&orgId=" + orgId
            + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType"));
    Connection db = null;
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }
      setOrganization(context, db);
      //Build the service contract list
      serviceContractList.setPagedListInfo(serviceContractListInfo);
      serviceContractList.setOrgId(Integer.parseInt(orgId));
      Organization tmpOrganization = (Organization) context.getRequest().getAttribute(
          "OrgDetails");
      if (tmpOrganization.isTrashed()) {
        serviceContractList.setIncludeOnlyTrashed(true);
      }
      serviceContractList.buildList(db);
      context.getRequest().setAttribute(
          "serviceContractList", serviceContractList);
      buildFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsServiceContractsList");
  }


  /**
   * Adds a service contract to an account
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      setOrganization(context, db);

      ServiceContract thisContract = (ServiceContract) context.getFormBean();
      buildFormElements(context, db);

      ContactList contactList = new ContactList();
      contactList.setOrgId(
          Integer.parseInt(context.getRequest().getParameter("orgId")));
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("contactList", contactList);

      //prepare contract product list
      thisContract.setServiceContractProductList(
          new ServiceContractProductList());

      //prepare contract hours modification history
      ServiceContractHoursList schHistory = new ServiceContractHoursList();
      context.getRequest().setAttribute(
          "serviceContractHoursHistory", schHistory);

      if (thisContract.getEnteredBy() != -1) {
        //prepare contract product list
        thisContract.setProductList(
            context.getRequest().getParameterValues("selectedList"));
        ServiceContractProductList scpl = new ServiceContractProductList();
        thisContract.setServiceContractProductList(scpl);
        Iterator itr = thisContract.getProductList().iterator();
        while (itr.hasNext()) {
          int productId = Integer.parseInt((String) itr.next());
          ProductCatalog pc = new ProductCatalog(db, productId);

          ServiceContractProduct spc = new ServiceContractProduct();
          spc.setProductId(pc.getId());
          spc.setContractId(thisContract.getId());
          spc.setProductName(pc.getName());
          spc.setProductSku(pc.getSku());

          thisContract.getServiceContractProductList().add(spc);
        }
      }

      context.getRequest().setAttribute("serviceContract", thisContract);

    } catch (Exception e) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsServiceContractsAdd");
  }


  /**
   * Saves the service contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean inserted = false;
    boolean isValid = false;
    try {
      db = this.getConnection(context);
      setOrganization(context, db);

      ServiceContract thisContract = (ServiceContract) context.getFormBean();
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }

      thisContract.setProductList(
          context.getRequest().getParameterValues("selectedList"));
      thisContract.setEnteredBy(getUserId(context));
      thisContract.setModifiedBy(getUserId(context));

      String subId = context.getRequest().getParameter("subId");
      if (subId != null && !"".equals(subId)) {
        thisContract.setSubmitterId(Integer.parseInt(subId));
      }

      isValid = this.validateObject(context, db, thisContract);
      if (isValid) {
        inserted = thisContract.insert(db);
      }
      if (inserted) {
        this.processInsertHook(context, thisContract);
        // inserting into hours history if service_contract update is successful
        String tmpHours = (String) context.getRequest().getParameter(
            "totalHoursRemaining");
        if (tmpHours != null) {
          tmpHours = tmpHours.trim();
          if (!"".equals(tmpHours)) {
            ServiceContractHours scHours = new ServiceContractHours();
            scHours.setServiceContractId(thisContract.getId());
            scHours.setAdjustmentHours(tmpHours);
            scHours.setAdjustmentReason(
                (String) context.getRequest().getParameter("adjustmentReason"));
            scHours.setAdjustmentNotes(
                (String) context.getRequest().getParameter("adjustmentNotes"));
            scHours.setEnteredBy(getUserId(context));
            scHours.setModifiedBy(getUserId(context));
            scHours.insert(db);
          }
        }
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
   * Updates a service contract
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);
      ServiceContract thisContract = (ServiceContract) context.getFormBean();
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }
      ServiceContract previousContract = new ServiceContract(
          db, thisContract.getId());
      thisContract.setProductList(
          context.getRequest().getParameterValues("selectedList"));

      //Calculate hours remaining if it has been adjusted
      double newHoursRemaining = thisContract.getTotalHoursRemaining();
      String tmpHours = (String) context.getRequest().getParameter(
          "adjustmentHours");
      if (tmpHours != null) {
        tmpHours = tmpHours.trim();
        if (!"".equals(tmpHours)) {
          newHoursRemaining = newHoursRemaining + Double.parseDouble(tmpHours);
        }
      }

      thisContract.setTotalHoursRemaining(newHoursRemaining);
      thisContract.setModifiedBy(getUserId(context));

      String subId = context.getRequest().getParameter("subId");
      if (subId != null && !"".equals(subId)) {
        thisContract.setSubmitterId(Integer.parseInt(subId));
      }

      isValid = this.validateObject(context, db, thisContract);
      if (isValid) {
        resultCount = thisContract.update(db);
      }
      if (resultCount == 1) {
        this.processUpdateHook(context, previousContract, thisContract);
        // inserting into hours history if service_contract update is successful
        if (!"".equals(tmpHours) && (Double.parseDouble(tmpHours) != 0.0)) {
          ServiceContractHours scHours = new ServiceContractHours();
          scHours.setServiceContractId(thisContract.getId());
          scHours.setAdjustmentHours(tmpHours);
          scHours.setAdjustmentReason(
              (String) context.getRequest().getParameter("adjustmentReason"));
          scHours.setAdjustmentNotes(
              (String) context.getRequest().getParameter("adjustmentNotes"));
          scHours.setEnteredBy(getUserId(context));
          scHours.setModifiedBy(getUserId(context));
          scHours.insert(db);
        }
      }
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
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
   * Confirms a request to delete a service contract
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-delete")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    String id = context.getRequest().getParameter("id");
    String orgId = context.getRequest().getParameter("orgId");

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      ServiceContract thisContract = new ServiceContract(db, id);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }
      //Find depedencies for the service contract
      DependencyList dependencies = thisContract.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(
          systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AccountsServiceContracts.do?command=Trash&action=delete&orgId=" + orgId + "&id=" + id
          + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      errorMessage = e;
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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
   * Deletes a service contract and its dependent records
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean recordDeleted = false;
    Connection db = null;
    ServiceContract thisContract = null;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      int id = Integer.parseInt((context.getRequest().getParameter("id")));
      thisContract = new ServiceContract(db, id);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }

      recordDeleted = thisContract.updateStatus(db, true, this.getUserId(context));
    } catch (Exception e) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }

    boolean inline = (context.getRequest().getParameter("popupType") != null && "inline"
        .equals(context.getRequest().getParameter("popupType")));
    if (recordDeleted) {
      context.getRequest().setAttribute(
          "refreshUrl",
          "AccountsServiceContracts.do?command=List&orgId="
              + context.getRequest().getParameter("orgId")
              + (inline ? "&popup=true" : ""));
      return "DeleteOK";
    }
    processErrors(context, thisContract.getErrors());
    context.getRequest().setAttribute(
        "refreshUrl",
        "AccountsServiceContracts.do?command=View&orgId="
            + context.getRequest().getParameter("orgId") + "&id=" + context.getRequest().getParameter("id") + (inline ? "&popup=true" : ""));
    return "DeleteOK";
  }


  /**
   * Deletes a service contract and its dependent records
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean recordDeleted = false;
    Connection db = null;
    ServiceContract thisContract = null;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      int id = Integer.parseInt((context.getRequest().getParameter("id")));
      thisContract = new ServiceContract(db, id);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }

      recordDeleted = thisContract.delete(db, getDbNamePath(context));
    } catch (Exception e) {
      errorMessage = e;
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      context.getRequest().setAttribute(
          "refreshUrl", "AccountsServiceContracts.do?command=List&orgId=" + context.getRequest().getParameter(
          "orgId"));
      return getReturn(context, "Delete");
    }

    processErrors(context, thisContract.getErrors());
    context.getRequest().setAttribute(
        "refreshUrl", "AccountsServiceContracts.do?command=View&orgId=" + context.getRequest().getParameter(
        "orgId") + "&id=" + context.getRequest().getParameter("id"));
    return getReturn(context, "Delete");

/*    context.getRequest().setAttribute(
        "actionError", systemStatus.getLabel(
            "object.validation.actionError.contractDeletion"));
    context.getRequest().setAttribute(
        "refreshUrl", "AccountsServiceContracts.do?command=View&orgId=" + context.getRequest().getParameter(
            "orgId"));
    return ("DeleteError");
*/
  }


  /**
   * Prepares the modify page to display a service contract
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization thisOrg = null;
    boolean fromBean = true;
    try {
      db = this.getConnection(context);
      setOrganization(context, db);
      thisOrg = (Organization) context.getRequest().getAttribute("OrgDetails");

      int contractId = Integer.parseInt(
          (context.getRequest().getParameter("id")));
      ServiceContract thisContract = (ServiceContract) context.getFormBean();
      if (thisContract.getId() == -1) {
        fromBean = false;
        thisContract.queryRecord(db, contractId);
      }

      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }

      buildFormElements(context, db);

      ContactList contactList = new ContactList();
      contactList.setOrgId(thisOrg.getOrgId());
      contactList.setDefaultContactId(thisContract.getContactId());
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("contactList", contactList);

      //Fetch the history of changes to the hours used in the service contract
      ServiceContractHoursList schHistory = new ServiceContractHoursList();
      schHistory.setContractId(thisContract.getId());
      schHistory.buildList(db);
      context.getRequest().setAttribute(
          "serviceContractHoursHistory", schHistory);

      if (fromBean) {
        //prepare changed contract product list
        thisContract.setProductList(
            context.getRequest().getParameterValues("selectedList"));
        ServiceContractProductList scpl = new ServiceContractProductList();
        thisContract.setServiceContractProductList(scpl);
        Iterator itr = thisContract.getProductList().iterator();
        while (itr.hasNext()) {
          int productId = Integer.parseInt((String) itr.next());
          ProductCatalog pc = new ProductCatalog(db, productId);

          ServiceContractProduct spc = new ServiceContractProduct();
          spc.setProductId(pc.getId());
          spc.setContractId(thisContract.getId());
          spc.setProductName(pc.getName());
          spc.setProductSku(pc.getSku());

          thisContract.getServiceContractProductList().add(spc);
        }

        //Reset contract hours remaining fields  
        double newHoursRemaining = thisContract.getTotalHoursRemaining();
        String tmpHours = (String) context.getRequest().getParameter(
            "adjustmentHours");
        if (tmpHours != null) {
          tmpHours = tmpHours.trim();
          if (!"".equals(tmpHours)) {
            newHoursRemaining = newHoursRemaining - Double.parseDouble(
                tmpHours);
          }
        }
        thisContract.setTotalHoursRemaining(newHoursRemaining);
      }

      int subId = thisContract.getSubmitterId();
      if (!"".equals(subId) && subId > -1) {
        Organization SubmiterOrg = new Organization(db, thisContract.getSubmitterId());
        context.getRequest().setAttribute("SubmiterOrgDetails", SubmiterOrg);
      }

      context.getRequest().setAttribute("serviceContract", thisContract);
      context.getRequest().setAttribute(
          "return", context.getRequest().getParameter("return"));

    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsServiceContractsModify");
  }


  /**
   * Prepares the view page to display a service contract
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-view")) {
      return ("PermissionError");
    }
    Organization thisOrg = null;
    Connection db = null;
    String id = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      setOrganization(context, db);
      thisOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
      int orgId = thisOrg.getOrgId();

      ServiceContract thisContract = new ServiceContract(db, id);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }

      //find record permissions for portal users
      if ((!isRecordAccessPermitted(context, db, thisContract.getOrgId())) ||
          (!isRecordAccessPermitted(context, db, orgId))) {
        return ("PermissionError");
      }

      if (thisContract.getContactId() > -1) {
        Contact thisContact = new Contact();
        thisContact.queryRecord(db, thisContract.getContactId());
        context.getRequest().setAttribute(
            "serviceContractContact", thisContact);
      }

      //reseller
      int subId = thisContract.getSubmitterId();
      if (!"".equals(subId) && subId > -1) {
        Organization thisSubmitter = new Organization(db, subId);
        context.getRequest().setAttribute("serviceContractSubmitter", thisSubmitter);
      }

      //Fetch the history of changes to the hours used in the service contract
      ServiceContractHoursList schHistory = new ServiceContractHoursList();
      schHistory.setContractId(id);
      schHistory.buildList(db);
      context.getRequest().setAttribute(
          "serviceContractHoursHistory", schHistory);

      //prepare contract product list
      ServiceContractProductList scpl = new ServiceContractProductList();
      scpl.setContractId(thisContract.getId());
      scpl.buildList(db);
      context.getRequest().setAttribute("serviceContractProductList", scpl);

      buildFormElements(context, db);
      context.getRequest().setAttribute("serviceContract", thisContract);
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccountsServiceContractsView");
  }


  /**
   * The hours remaining (a.k.a. the hours purchased during the service contract
   * can be manually changed or can change due to ticket activities
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandHoursHistory(ActionContext context) {

    Connection db = null;
    String id = context.getRequest().getParameter("id");
    ServiceContractHoursList schHistory = new ServiceContractHoursList();
    //Prepare pagedListInfo
    PagedListInfo serviceContractHoursHistoryInfo = this.getPagedListInfo(
        context, "serviceContractHoursHistoryInfo");
    serviceContractHoursHistoryInfo.setLink(
        "AccountsServiceContracts.do?command=HoursHistory&id=" + id);
    try {
      db = this.getConnection(context);
      ServiceContract thisContract = new ServiceContract(db, id);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContract.getOrgId())) {
        return ("PermissionError");
      }
      LookupList serviceContractHoursReasonList = new LookupList(
          db, "lookup_hours_reason");
      serviceContractHoursReasonList.addItem(
          -1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute(
          "serviceContractHoursReasonList", serviceContractHoursReasonList);
      //Build the service contract list
      schHistory.setPagedListInfo(serviceContractHoursHistoryInfo);
      schHistory.setContractId(id);
      schHistory.buildList(db);
      context.getRequest().setAttribute(
          "serviceContractHoursHistory", schHistory);
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AccountsServiceContractsHoursOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param db      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildFormElements(ActionContext context, Connection db) throws SQLException {

    SystemStatus thisSystem = this.getSystemStatus(context);

    LookupList serviceContractCategoryList = new LookupList(
        db, "lookup_sc_category");
    serviceContractCategoryList.addItem(
        -1, thisSystem.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute(
        "serviceContractCategoryList", serviceContractCategoryList);

    LookupList serviceContractTypeList = new LookupList(db, "lookup_sc_type");
    serviceContractTypeList.addItem(
        -1, thisSystem.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute(
        "serviceContractTypeList", serviceContractTypeList);

    LookupList responseModelList = new LookupList(db, "lookup_response_model");
    responseModelList.addItem(
        -1, thisSystem.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("responseModelList", responseModelList);

    LookupList phoneModelList = new LookupList(db, "lookup_phone_model");
    phoneModelList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("phoneModelList", phoneModelList);

    LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
    onsiteModelList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("onsiteModelList", onsiteModelList);

    LookupList emailModelList = new LookupList(db, "lookup_email_model");
    emailModelList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("emailModelList", emailModelList);

    LookupList hoursReasonList = new LookupList(db, "lookup_hours_reason");
    hoursReasonList.addItem(
        -1, thisSystem.getLabel("accounts.servicecontracts.noAdjustment"));
    context.getRequest().setAttribute("hoursReasonList", hoursReasonList);

  }


  /**
   * Sets the organization attribute of the AccountsServiceContracts object
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
      if (contactId != null) {
        Contact contact = new Contact(db, Integer.parseInt(contactId));
        orgId = "" + contact.getOrgId();
      }
    }
    thisOrganization = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }
}
