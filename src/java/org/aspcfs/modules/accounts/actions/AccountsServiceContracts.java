package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.servicecontracts.base.*;
import org.aspcfs.modules.assets.base.*;
import java.sql.*;
import java.io.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.*;

/**
 *  A service contract action handler provides action methods to List, view, add and modify service
 *  service contracts. Service Contracts are contractual agreements with accounts (a.k.a. organizations)
 *  that specify the service model, start and end dates, hours and other information 
 *
 *@author     kbhoopal
 *@created    December 23, 2003
 *@version    $Id: AccountsServiceContracts.java,v 1.1.2.1 2003/12/24 16:07:31
 *      kbhoopal Exp $
 */
public class AccountsServiceContracts extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return ("OK");
  }


  /**
   *  Lists the service contracts of an account
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-view")) {
      return ("PermissionError");
    }
    ServiceContractList serviceContractList = new ServiceContractList();

    String orgId = context.getRequest().getParameter("orgId");

    //Prepare pagedListInfo
    PagedListInfo serviceContractListInfo = this.getPagedListInfo(context, "ServiceContractListInfo");
    serviceContractListInfo.setLink("AccountsServiceContracts.do?command=List&orgId=" + orgId);
    Connection db = null;
    try {
      db = this.getConnection(context);
      setOrganization(context, db);
      //Build the service contract list
      serviceContractList.setPagedListInfo(serviceContractListInfo);
      serviceContractList.setOrgId(Integer.parseInt(orgId));
      serviceContractList.buildList(db);
      context.getRequest().setAttribute("serviceContractList", serviceContractList);
      buildFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AccountsServiceContractsListOK");
  }


  /**
   *  Adds a service contract to an account
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      contactList.setOrgId(Integer.parseInt(context.getRequest().getParameter("orgId")));
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord("-- None --");
      context.getRequest().setAttribute("contactList", contactList);

      ServiceContractHoursList schHistory = new ServiceContractHoursList();
      context.getRequest().setAttribute("serviceContractHoursHistory", schHistory);

    } catch (Exception e) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AccountsServiceContractsAddOK");
  }


  /**
   *  Saves the service contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean inserted = false;
    try {
      db = this.getConnection(context);
      setOrganization(context, db);

      ServiceContract thisContract = (ServiceContract) context.getFormBean();
      thisContract.setEnteredBy(getUserId(context));
      thisContract.setModifiedBy(getUserId(context));
      inserted = thisContract.insert(db);
      if (inserted) {
        // inserting into hours history if service_contract update is successful
        String tmpHours = (String) context.getRequest().getParameter("totalHoursRemaining");
        if (!"".equals(tmpHours)) {
          ServiceContractHours scHours = new ServiceContractHours();
          scHours.setServiceContractId(thisContract.getId());
          scHours.setAdjustmentHours(tmpHours);
          scHours.setAdjustmentReason((String) context.getRequest().getParameter("adjustmentReason"));
          scHours.setAdjustmentNotes((String) context.getRequest().getParameter("adjustmentNotes"));
          scHours.setEnteredBy(getUserId(context));
          scHours.setModifiedBy(getUserId(context));
          scHours.insert(db);
        }
      } else {
        processErrors(context, thisContract.getErrors());
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
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);
      ServiceContract thisContract = (ServiceContract) context.getFormBean();

      //Calculate hours remaining if it has been adjusted
      double newHoursRemaining = thisContract.getTotalHoursRemaining();
      String tmpHours = (String) context.getRequest().getParameter("adjustmentHours");
      if (!"".equals(tmpHours)) {
        newHoursRemaining = newHoursRemaining + Double.parseDouble(tmpHours);
      }

      thisContract.setTotalHoursRemaining(newHoursRemaining);
      thisContract.setModifiedBy(getUserId(context));
      resultCount = thisContract.update(db);
      if (resultCount == -1) {
        processErrors(context, thisContract.getErrors());
      }
      if (resultCount == 1) {
        // inserting into hours history if service_contract update is successful
        if (!"".equals(tmpHours)) {
          ServiceContractHours scHours = new ServiceContractHours();
          scHours.setServiceContractId(thisContract.getId());
          scHours.setAdjustmentHours(tmpHours);
          scHours.setAdjustmentReason((String) context.getRequest().getParameter("adjustmentReason"));
          scHours.setAdjustmentNotes((String) context.getRequest().getParameter("adjustmentNotes"));
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
    if (resultCount == -1) {
      return (executeCommandModify(context));
    } else if (resultCount == 1) {
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandList(context);
      } else {
        return executeCommandView(context);
      }
    } else {
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
      ServiceContract thisContract = new ServiceContract(db, id);
      //Find depedencies for the service contract
      htmlDialog.setTitle("Dark Horse CRM: Account Management - Service Contract");
      DependencyList dependencies = thisContract.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      htmlDialog.setHeader("The service contract you are requesting to delete has the following dependencies within Dark Horse CRM:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='AccountsServiceContracts.do?command=Delete&action=delete&orgId=" + orgId + "&id=" + id + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Connection db = null;
    ServiceContract thisContract = null;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      setOrganization(context, db);

      thisContract = new ServiceContract();
      thisContract.setId(context.getRequest().getParameter("id"));
      recordDeleted = thisContract.deleteAll(db, this.getPath(context, "accounts"));
    } catch (Exception e) {
      errorMessage = e;
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "AccountsServiceContracts.do?command=List&orgId=" + context.getRequest().getParameter("orgId"));
        return this.getReturn(context, "Delete");
      }

      processErrors(context, thisContract.getErrors());
      context.getRequest().setAttribute("refreshUrl", "AccountsServiceContracts.do?command=View&orgId=" + context.getRequest().getParameter("orgId") + "&id=" + context.getRequest().getParameter("id"));
      return this.getReturn(context, "Delete");
    }

    context.getRequest().setAttribute("actionError", "Service Contract could not be deleted because of referential integrity .");
    context.getRequest().setAttribute("refreshUrl", "AccountsServiceContracts.do?command=View&orgId=" + context.getRequest().getParameter("orgId"));
    return ("DeleteError");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      setOrganization(context, db);

      int contractId = Integer.parseInt((context.getRequest().getParameter("id")));
      ServiceContract thisContract = (ServiceContract) context.getFormBean();
      if (thisContract.getId() == -1) {
        thisContract.queryRecord(db, contractId);
      }

      //Fetch the history of changes to the hours used in the service contract
      ServiceContractHoursList schHistory = new ServiceContractHoursList();
      schHistory.setContractId(contractId);
      schHistory.buildList(db);
      context.getRequest().setAttribute("serviceContractHoursHistory", schHistory);

      buildFormElements(context, db);
      context.getRequest().setAttribute("serviceContract", thisContract);
      context.getRequest().setAttribute("return", context.getRequest().getParameter("return"));

      ContactList contactList = new ContactList();
      contactList.setOrgId(Integer.parseInt(context.getRequest().getParameter("orgId")));
      contactList.buildList(db);
      contactList.setEmptyHtmlSelectRecord("-- None --");
      context.getRequest().setAttribute("contactList", contactList);
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AccountsServiceContractsModifyOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-service-contracts-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String id = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      setOrganization(context, db);

      ServiceContract thisContract = new ServiceContract(db, id);
      if (thisContract.getContactId() > -1) {
        Contact thisContact = new Contact();
        thisContact.queryRecord(db, thisContract.getContactId());
        context.getRequest().setAttribute("serviceContractContact", thisContact);
      }

      //Fetch the history of changes to the hours used in the service contract
      ServiceContractHoursList schHistory = new ServiceContractHoursList();
      schHistory.setContractId(id);
      schHistory.buildList(db);
      context.getRequest().setAttribute("serviceContractHoursHistory", schHistory);

      buildFormElements(context, db);
      context.getRequest().setAttribute("serviceContract", thisContract);
      //System.out.println(" Hours Remaining --" + thisContract.getTotalHoursRemaining());
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AccountsServiceContractsViewOK");
  }


  /**
   *  The hours remaining (a.k.a. the hours purchased during the service contract
   *  can be manually changed or can change due to ticket activities
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandHoursHistory(ActionContext context) {

    Connection db = null;
    String id = context.getRequest().getParameter("id");
    ServiceContractHoursList schHistory = new ServiceContractHoursList();
    //Prepare pagedListInfo
    PagedListInfo serviceContractHoursHistoryInfo = this.getPagedListInfo(context, "serviceContractHoursHistoryInfo");
    serviceContractHoursHistoryInfo.setLink("AccountsServiceContracts.do?command=HoursHistory&id=" + id);
    try {
      db = this.getConnection(context);
      LookupList serviceContractHoursReasonList = new LookupList(db, "lookup_hours_reason");
      serviceContractHoursReasonList.addItem(-1, "-- None --");
      context.getRequest().setAttribute("serviceContractHoursReasonList", serviceContractHoursReasonList);
      //Build the service contract list
      schHistory.setPagedListInfo(serviceContractHoursHistoryInfo);
      schHistory.setContractId(id);
      schHistory.buildList(db);
      context.getRequest().setAttribute("serviceContractHoursHistory", schHistory);
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
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildFormElements(ActionContext context, Connection db) throws SQLException {

    LookupList serviceContractCategoryList = new LookupList(db, "lookup_sc_category");
    serviceContractCategoryList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("serviceContractCategoryList", serviceContractCategoryList);

    LookupList serviceContractTypeList = new LookupList(db, "lookup_sc_type");
    serviceContractTypeList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("serviceContractTypeList", serviceContractTypeList);

    LookupList responseModelList = new LookupList(db, "lookup_response_model");
    responseModelList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("responseModelList", responseModelList);

    LookupList phoneModelList = new LookupList(db, "lookup_phone_model");
    phoneModelList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("phoneModelList", phoneModelList);

    LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
    onsiteModelList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("onsiteModelList", onsiteModelList);

    LookupList emailModelList = new LookupList(db, "lookup_email_model");
    emailModelList.addItem(-1, "-- None --");
    context.getRequest().setAttribute("emailModelList", emailModelList);

  }


  /**
   *  Sets the organization attribute of the AccountsServiceContracts object
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
}
