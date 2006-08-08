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
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AccountTickets extends CFSModule {

  /**
   * Sample action for prototyping, by including a specified page
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return getReturn(context, "Include");
  }


  /**
   * Re-opens a ticket
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReopenTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-edit")) {
      return ("PermissionError");
    }
    int resultCount = -1;
    Connection db = null;
    Ticket thisTicket = null;
    Ticket oldTicket = null;
    try {
      db = this.getConnection(context);
      thisTicket = new Ticket(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      oldTicket = new Ticket(db, thisTicket.getId());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
      thisTicket.queryRecord(db, thisTicket.getId());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      return (executeCommandTicketDetails(context));
    } else if (resultCount == 1) {
      this.processUpdateHook(context, oldTicket, thisTicket);
      return (executeCommandTicketDetails(context));
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Prepares a ticket form
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAddTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    Organization newOrg = null;
    //Parameters
    String temporgId = context.getRequest().getParameter("orgId");
    int tempid = Integer.parseInt(temporgId);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, tempid)) {
        return ("PermissionError");
      }
      //Organization for header
      newOrg = new Organization(db, tempid);
      //Ticket
      newTic = (Ticket) context.getFormBean();
      if (context.getRequest().getParameter("refresh") != null ||
          (context.getRequest().getParameter("contact") != null &&
          context.getRequest().getParameter("contact").equals("on"))) {
      } else {
        newTic.setOrgId(tempid);
      }
      buildFormElements(context, db, newTic, newOrg);
      addModuleBean(context, "View Accounts", "Add a Ticket");
      context.getRequest().setAttribute("OrgDetails", newOrg);

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
      context.getRequest().setAttribute(
          "systemStatus", this.getSystemStatus(context));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AddTicket");
  }


  /**
   * Inserts a submitted ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandInsertTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean contactRecordInserted = false;
    boolean isValid = true;
    Contact nc = null;
    Ticket newTicket = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Parameters
    String newContact = context.getRequest().getParameter("contact");
    //Process the submitted ticket
    Ticket newTic = (Ticket) context.getFormBean();
    if (newTic.getAssignedTo() > -1 && newTic.getAssignedDate() == null) {
      newTic.setAssignedDate(
          new java.sql.Timestamp(System.currentTimeMillis()));
    }
    newTic.setEnteredBy(getUserId(context));
    newTic.setModifiedBy(getUserId(context));
    //Insert a new contact if specified
    if (newContact != null && newContact.equals("on")) {
      //NOTE: If there are any changes here, also change TroubleTickets where a new contact is created
      nc = new Contact();
      nc.setNameFirst(
          context.getRequest().getParameter("thisContact_nameFirst"));
      nc.setNameLast(
          context.getRequest().getParameter("thisContact_nameLast"));
      nc.setTitle(context.getRequest().getParameter("thisContact_title"));
      nc.setRequestItems(context);
      nc.setOrgId(newTic.getOrgId());
      nc.setEnteredBy(getUserId(context));
      nc.setModifiedBy(getUserId(context));
      nc.setOwner(getUserId(context));
    }
    try {
      db = this.getConnection(context);

      //Display account name in the header
      String temporgId = context.getRequest().getParameter("orgId");
      int tempid = Integer.parseInt(temporgId);

      // set ticket source to Web for tickets inserted by portal user
      if (isPortalUser(context)) {
        LookupList sourceList = new LookupList(db, "lookup_ticketsource");
        newTic.setSourceCode(sourceList.getIdFromValue("Web"));
      }

      //Check if portal user can insert this record
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }

      Organization newOrg = new Organization(db, tempid);
      context.getRequest().setAttribute("OrgDetails", newOrg);

      if (nc != null) {
        isValid = this.validateObject(context, db, nc) && isValid;
        if (isValid) {
          contactRecordInserted = nc.insert(db);
        }
        if (contactRecordInserted) {
          newTic.setContactId(nc.getId());
        }
        if (contactRecordInserted) {
          if (newTic.getOrgId() > 0){
            newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
          }
          isValid = this.validateObject(context, db, newTic);
          if (isValid) {
            recordInserted = newTic.insert(db);
          }
        }
      } else {
        if (newTic.getOrgId() > 0){
          newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
        }
        isValid = this.validateObject(context, db, newTic) && isValid;
        if (isValid) {
          recordInserted = newTic.insert(db);
        }
      }
      if (recordInserted) {
        //Reload the ticket for the details page... redundant to do here
        newTicket = new Ticket(db, newTic.getId());
        context.getRequest().setAttribute("TicketDetails", newTicket);

        if (newTicket.getProductId() != -1) {
          ProductCatalog product = new ProductCatalog(
              db, newTicket.getProductId());
          context.getRequest().setAttribute("product", product);
        }

        // check wether of not the customer product id exists
        if (newTicket.getCustomerProductId() != -1) {
          CustomerProduct customerProduct = new CustomerProduct(
              db, newTicket.getCustomerProductId());
          customerProduct.buildFileList(db);
          context.getRequest().setAttribute(
              "customerProduct", customerProduct);
        }

        if (newTicket.getDefectId() != -1) {
          TicketDefect defect = new TicketDefect(db, newTicket.getDefectId());
          context.getRequest().setAttribute("defect", defect);
        }
        //Load the ticket state
        LookupList ticketStateList = new LookupList(db, "lookup_ticket_state");
        ticketStateList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("ticketStateList", ticketStateList);
        TicketCategoryList ticketCategoryList = new TicketCategoryList();
        ticketCategoryList.setSiteId(newOrg.getSiteId());
        ticketCategoryList.setExclusiveToSite(true);
        ticketCategoryList.setEnabledState(Constants.TRUE);
        ticketCategoryList.buildList(db);
        context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);


        addRecentItem(context, newTicket);
        processInsertHook(context, newTic);
      }
      addModuleBean(context, "View Accounts", "Ticket Insert ok");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      return getReturn(context, "InsertTicket");
    }
    return (executeCommandAddTicket(context));
  }


  /**
   * Load the ticket details tab
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandTicketDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      // Load the ticket
      Ticket newTic = new Ticket();
      newTic.setBuildHistory(true);
      SystemStatus systemStatus = this.getSystemStatus(context);
      newTic.setSystemStatus(systemStatus);
      newTic.setBuildOrgHierarchy(true);
      newTic.queryRecord(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
      if (newTic.getProductId() != -1) {
        ProductCatalog product = new ProductCatalog(db, newTic.getProductId());
        context.getRequest().setAttribute("product", product);

        QuoteList quoteList = new QuoteList();
        quoteList.setTicketId(newTic.getId());
        quoteList.buildList(db);
        context.getRequest().setAttribute("quoteList", quoteList);
      }

      LookupList causeList = new LookupList(db, "lookup_ticket_cause");
      causeList.addItem(-1,"");
      context.getRequest().setAttribute("causeList", causeList);

      //Load the ticket state
      LookupList ticketStateList = new LookupList(db, "lookup_ticket_state");
      ticketStateList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("ticketStateList", ticketStateList);

      LookupList resolutionList = new LookupList(db, "lookup_ticket_resolution");
      resolutionList.addItem(-1,"");
      context.getRequest().setAttribute("resolutionList", resolutionList);

      // check wether or not the customer product id exists
      if (newTic.getCustomerProductId() != -1) {
        CustomerProduct customerProduct = new CustomerProduct(
            db, newTic.getCustomerProductId());
        customerProduct.buildFileList(db);
        context.getRequest().setAttribute("customerProduct", customerProduct);
      }

      if (newTic.getDefectId() !=-1) {
        TicketDefect defect = new TicketDefect(db, newTic.getDefectId());
        context.getRequest().setAttribute("defect", defect);
      }

      if (newTic.getAssignedTo() > 0) {
        newTic.checkEnabledOwnerAccount(db);
      }
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);
      // Load the organization for the header
      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      addModuleBean(context, "View Accounts", "View Tickets");
      // Reset any pagedLists since this could be a new visit to this ticket
      deletePagedListInfo(context, "AccountTicketsFolderInfo");
      deletePagedListInfo(context, "AccountTicketDocumentListInfo");
      deletePagedListInfo(context, "AccountTicketTaskListInfo");
      deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
      TicketCategoryList ticketCategoryList = new TicketCategoryList();
      ticketCategoryList.setEnabledState(Constants.TRUE);
      ticketCategoryList.setSiteId(thisOrganization.getSiteId());
      ticketCategoryList.setExclusiveToSite(true);
      ticketCategoryList.buildList(db);
      context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "TicketDetails");
  }


  /**
   * Confirm the delete operation showing dependencies
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Ticket ticket = new Ticket(db, Integer.parseInt(id));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, ticket.getOrgId())) {
        return ("PermissionError");
      }
      DependencyList dependencies = ticket.processDependencies(db);
      //Prepare the dialog based on the dependencies
      HtmlDialog htmlDialog = new HtmlDialog();
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='AccountTickets.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
              context.getRequest(), "popup|popupType|actionId") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Delete the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDeleteTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Connection db = null;
    //Parameters
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Organization newOrg = new Organization(db, orgId);
      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordDeleted = thisTic.delete(db, getDbNamePath(context));
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
        deleteRecentItem(context, thisTic);
        String inline = context.getRequest().getParameter("popupType");
        context.getRequest().setAttribute("OrgDetails", newOrg);
        context.getRequest().setAttribute(
            "refreshUrl", "Accounts.do?command=ViewTickets&orgId=" + orgId + 
            (inline != null && "inline".equals(inline.trim()) ? "&popup=true":""));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      return ("DeleteTicketOK");
    } else {
      return (executeCommandTicketDetails(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrashTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Connection db = null;
    //Parameters
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Organization newOrg = new Organization(db, orgId);
      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordUpdated = thisTic.updateStatus(db, true, this.getUserId(context));
      if (recordUpdated) {
        processDeleteHook(context, thisTic);
        deleteRecentItem(context, thisTic);
        context.getRequest().setAttribute("OrgDetails", newOrg);
        String inline = context.getRequest().getParameter("popupType");
        context.getRequest().setAttribute(
            "refreshUrl", "Accounts.do?command=ViewTickets&orgId=" + orgId +
            (inline != null && "inline".equals(inline) ? "&popup=true":""));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated) {
      return ("DeleteTicketOK");
    } else {
      return (executeCommandTicketDetails(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestoreTicket(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-edit"))) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Connection db = null;
    Ticket thisTicket = null;
    try {
      db = this.getConnection(context);
      thisTicket = new Ticket(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      thisTicket.setModifiedBy(getUserId(context));
      recordUpdated = thisTicket.updateStatus(
          db, false, this.getUserId(context));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated) {
      return (executeCommandTicketDetails(context));
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Loads the ticket for modification
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      //Load the ticket
      if (context.getRequest().getParameter("companyName") == null) {
        newTic = new Ticket(db, Integer.parseInt(ticketId));
      } else {
        newTic = (Ticket) context.getFormBean();
        newTic.buildRelatedInformation(db);
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
      //Load the organization
      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Load the departmentList for assigning
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(
          0, systemStatus.getLabel("calendar.none.4dashes"));
      departmentList.setJsEvent(
          "onChange=\"javascript:updateUserList();javascript:resetAssignedDate();\"");
      context.getRequest().setAttribute("DepartmentList", departmentList);

      //Load the ticket state
      LookupList ticketStateList = new LookupList(db, "lookup_ticket_state");
      ticketStateList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("ticketStateList", ticketStateList);

      LookupList resolvedByDeptList = new LookupList(db, "lookup_department");
      resolvedByDeptList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      resolvedByDeptList.setJsEvent(
          "onChange=\"javascript:updateResolvedByUserList();\"");
      context.getRequest().setAttribute("resolvedByDeptList", resolvedByDeptList);    
      
      //Load the severity list
      LookupList severityList = new LookupList(db, "ticket_severity");
      context.getRequest().setAttribute("SeverityList", severityList);
      //Load the priority list
      LookupList priorityList = new LookupList(db, "ticket_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      //Load the ticket source list
      LookupList sourceList = new LookupList(db, "lookup_ticketsource");
      sourceList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SourceList", sourceList);
      LookupList causeList = new LookupList(db, "lookup_ticket_cause");
      causeList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("causeList", causeList);
      LookupList resolutionList = new LookupList(db, "lookup_ticket_resolution");
      resolutionList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("resolutionList", resolutionList);
      TicketDefectList list = new TicketDefectList();
      list.setSiteId(newTic.getOrgSiteId());
      list.buildList(db);
      HtmlSelect defectSelect = list.getHtmlSelectObj(newTic.getDefectId());
      defectSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes","None"), 0);
      context.getRequest().setAttribute("defectSelect", defectSelect);
      //Load the ticket escalation list
      LookupList escalationList = new LookupList(db, "lookup_ticket_escalation");
      escalationList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("EscalationList", escalationList);
      //Load the top level category list
      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setSiteId(thisOrganization.getSiteId());
      categoryList.setExclusiveToSite(true);
      categoryList.setParentCode(0);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      categoryList.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("CategoryList", categoryList);
      //Load the user list the ticket can be assigned to
      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord(
          systemStatus.getLabel("calendar.none.4dashes"));
      userList.setHidden(Constants.FALSE);
      userList.setBuildContact(true);
      userList.setBuildContactDetails(false);
      userList.setDepartment(
          newTic.getDepartmentCode() != -1 ? newTic.getDepartmentCode() : 0);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      userList.setSiteId(newTic.getOrgSiteId());
      userList.setIncludeUsersWithAccessToAllSites(true);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
      //Load the user list the ticket can be resolved by
      UserList resolvedUserList = new UserList();
      resolvedUserList.setHidden(Constants.FALSE);
      resolvedUserList.setEmptyHtmlSelectRecord(
          systemStatus.getLabel("calendar.none.4dashes"));
      resolvedUserList.setBuildContact(true);
      resolvedUserList.setBuildContactDetails(false);
      resolvedUserList.setExcludeDisabledIfUnselected(true);
      resolvedUserList.setExcludeExpiredIfUnselected(true);
      resolvedUserList.setRoleType(Constants.ROLETYPE_REGULAR);
      resolvedUserList.setDepartment(
          newTic.getResolvedByDeptCode() != -1 ? newTic.getResolvedByDeptCode() : 0);
      resolvedUserList.setSiteId(newTic.getOrgSiteId());
      resolvedUserList.setIncludeUsersWithAccessToAllSites(true);
      resolvedUserList.buildList(db);
      context.getRequest().setAttribute("resolvedUserList", resolvedUserList);
      //Load the ticket sub-category1 list
      TicketCategoryList subList1 = new TicketCategoryList();
      subList1.setCatLevel(1);
      subList1.setSiteId(thisOrganization.getSiteId());
      subList1.setExclusiveToSite(true);
      subList1.setParentCode(newTic.getCatCode());
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList1", subList1);
      //Load the contact list
      ContactList contactList = new ContactList();
      if (newTic != null && newTic.getOrgId() != -1) {
        contactList.setEmptyHtmlSelectRecord(
            systemStatus.getLabel("calendar.none.4dashes"));
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(newTic.getOrgId());
        contactList.setDefaultContactId(newTic.getContactId());
        contactList.buildList(db);
      }
      context.getRequest().setAttribute("ContactList", contactList);
      //Load the ticket sub-category2 list
      TicketCategoryList subList2 = new TicketCategoryList();
      subList2.setCatLevel(2);
      subList2.setSiteId(thisOrganization.getSiteId());
      subList2.setExclusiveToSite(true);
      if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(
          context.getRequest().getParameter("refresh")) == 1) {
        subList2.setParentCode(0);
        newTic.setSubCat1(0);
        newTic.setSubCat2(0);
        newTic.setSubCat3(0);
      } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(
          context.getRequest().getParameter("refresh")) == -1) {
        subList2.setParentCode(newTic.getSubCat1());
        subList2.getCatListSelect().setDefaultKey(newTic.getSubCat2());
      } else {
        subList2.setParentCode(newTic.getSubCat1());
      }
      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.buildList(db);
      subList2.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList2", subList2);
      //Load the ticket sub-category3 list
      TicketCategoryList subList3 = new TicketCategoryList();
      subList3.setCatLevel(3);
      subList3.setSiteId(thisOrganization.getSiteId());
      subList3.setExclusiveToSite(true);
      if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(
          context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(
              context.getRequest().getParameter("refresh")) == 2)) {
        subList3.setParentCode(0);
        newTic.setSubCat2(0);
        newTic.setSubCat3(0);
      } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(
          context.getRequest().getParameter("refresh")) == -1) {
        subList3.setParentCode(newTic.getSubCat2());
        subList3.getCatListSelect().setDefaultKey(newTic.getSubCat3());
      } else {
        subList3.setParentCode(newTic.getSubCat2());
      }
      subList3.setHtmlJsEvent("onChange=\"javascript:updateSubList4();\"");
      subList3.buildList(db);
      subList3.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList3", subList3);
      if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(
          context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(
              context.getRequest().getParameter("refresh")) == 3)) {
        newTic.setSubCat3(0);
      }

      ActionPlanList actionPlans = new ActionPlanList();
      actionPlans.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      if (newTic.getCatCode() > 0) {
        actionPlans.setLinkCatCode(newTic.getCatCode());
      }
      if (newTic.getSubCat1() > 0) {
        actionPlans.setLinkSubCat1(newTic.getSubCat1());
      }
      if (newTic.getSubCat2() > 0) {
        actionPlans.setLinkSubCat2(newTic.getSubCat2());
      }
      if (newTic.getSubCat3() > 0) {
        actionPlans.setLinkSubCat3(newTic.getSubCat3());
      }
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, PermissionCategory.MULTIPLE_CATEGORY_TICKET);
      actionPlans.setTableName(thisEditor.getTableName());
      actionPlans.setSiteId(thisOrganization.getSiteId());
      actionPlans.setJsEvent("id=\"actionPlanId\"");
      actionPlans.setEnabled(Constants.TRUE);
      actionPlans.setIncludeOnlyApproved(Constants.TRUE);
      if (actionPlans.getLinkCatCode() <= 0 && actionPlans.getLinkSubCat1() <= 0 && actionPlans.getLinkSubCat2() <= 0 && actionPlans.getLinkSubCat3() <= 0) {
        actionPlans.setDisplayNone(true);
      }
      actionPlans.buildList(db);

      ActionPlanWorkList workList = new ActionPlanWorkList();
      workList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      workList.setLinkItemId(newTic.getId());
      workList.setSiteId(newTic.getSiteId());
      if (user.getSiteId() == -1) {
        workList.setIncludeAllSites(true);
      }
      workList.buildList(db);
      if (workList.size() > 0) {
        context.getRequest().setAttribute("insertActionPlan", String.valueOf(true));
      }
      actionPlans.addAtleastOne(db, workList);
      context.getRequest().setAttribute("actionPlans", actionPlans);

      //Put the ticket in the request
      addRecentItem(context, newTic);
      context.getRequest().setAttribute("TicketDetails", newTic);
      addModuleBean(context, "View Accounts", "View Tickets");

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ModifyTicket");
  }


  /**
   * Update the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;

    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean isValid = true;

    Ticket newTic = (Ticket) context.getFormBean();
    if (newTic.getAssignedTo() > -1 && newTic.getAssignedDate() == null) {
      newTic.setAssignedDate(
          new java.sql.Timestamp(System.currentTimeMillis()));
    }
    if (context.getRequest().getParameter("close").equals("1")) {
      newTic.setCloseIt(true);
    }

    try {
      db = this.getConnection(context);
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
      for (catCount = 0; catCount < 4; catCount++) {
        if ((context.getRequest().getParameter("newCat" + catCount + "chk") != null && context.getRequest().getParameter(
            "newCat" + catCount + "chk").equals("on") && context.getRequest().getParameter(
                "newCat" + catCount) != null && !(context.getRequest().getParameter(
                    "newCat" + catCount).equals("")))) {
          thisCat = new TicketCategory();
          thisCat.setSiteId(orgDetails.getSiteId());
          if (catCount == 0) {
            thisCat.setParentCode(0);
          } else if (catCount == 1) {
            thisCat.setParentCode(newTic.getCatCode());
          } else if (catCount == 2) {
            thisCat.setParentCode(newTic.getSubCat1());
          } else {
            thisCat.setParentCode(newTic.getSubCat2());
          }
          thisCat.setDescription(
              context.getRequest().getParameter("newCat" + catCount));
          thisCat.setCategoryLevel(catCount);
          thisCat.setLevel(catCount);
          isValid = this.validateObject(context, db, thisCat) && isValid;
          if (isValid) {
            catInserted = thisCat.insert(db);
            if (catInserted) {
              if (catCount == 0) {
                newTic.setCatCode(thisCat.getId());
              } else if (catCount == 1) {
                newTic.setSubCat1(thisCat.getId());
              } else if (catCount == 2) {
                newTic.setSubCat2(thisCat.getId());
              } else {
                newTic.setSubCat3(thisCat.getId());
              }
            }
          }
        }
      }
      //Get the previousTicket, update the ticket, then send both to a hook
      Ticket previousTicket = new Ticket(db, newTic.getId());
      newTic.setModifiedBy(getUserId(context));
      newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
      isValid = this.validateObject(context, db, newTic) && isValid;
      if (isValid) {
        resultCount = newTic.update(db);
      }
      if (resultCount == 1) {
        newTic.queryRecord(db, newTic.getId());
        processUpdateHook(context, previousTicket, newTic);
        TicketCategoryList ticketCategoryList = new TicketCategoryList();
        ticketCategoryList.setEnabledState(Constants.TRUE);
        ticketCategoryList.setSiteId(newTic.getSiteId());
        ticketCategoryList.setExclusiveToSite(true);
        ticketCategoryList.buildList(db);
        context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == 1 && isValid) {
      return "UpdateTicketOK";
    }
    return (executeCommandModifyTicket(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @param db      Description of Parameter
   * @param newTic  Description of Parameter
   * @param newOrg  Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildFormElements(ActionContext context, Connection db, Ticket newTic, Organization newOrg) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);
    LookupList departmentList = new LookupList(db, "lookup_department");
    departmentList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
    departmentList.setJsEvent(
        "onChange=\"javascript:updateUserList();javascript:resetAssignedDate();\"");
    context.getRequest().setAttribute("DepartmentList", departmentList);

    //Load the ticket state
    LookupList ticketStateList = new LookupList(db, "lookup_ticket_state");
    ticketStateList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("ticketStateList", ticketStateList);

    LookupList resolvedByDeptList = new LookupList(db, "lookup_department");
    resolvedByDeptList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
    resolvedByDeptList.setJsEvent(
        "onChange=\"javascript:updateResolvedByUserList();\"");
    context.getRequest().setAttribute("resolvedByDeptList", resolvedByDeptList);    
    
    LookupList severityList = new LookupList(db, "ticket_severity");
    context.getRequest().setAttribute("SeverityList", severityList);

    LookupList priorityList = new LookupList(db, "ticket_priority");
    context.getRequest().setAttribute("PriorityList", priorityList);

    LookupList sourceList = new LookupList(db, "lookup_ticketsource");
    sourceList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SourceList", sourceList);

    LookupList causeList = new LookupList(db, "lookup_ticket_cause");
    causeList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("causeList", causeList);

    LookupList resolutionList = new LookupList(db, "lookup_ticket_resolution");
    resolutionList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("resolutionList", resolutionList);

    LookupList escalationList = new LookupList(db, "lookup_ticket_escalation");
    escalationList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("EscalationList", escalationList);

    TicketCategoryList categoryList = new TicketCategoryList();
    categoryList.setCatLevel(0);
    categoryList.setParentCode(0);
    categoryList.setSiteId(newOrg.getSiteId());
    categoryList.setExclusiveToSite(true);
    categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
    categoryList.buildList(db);
    categoryList.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("CategoryList", categoryList);

    UserList userList = new UserList();
    userList.setHidden(Constants.FALSE);
    userList.setEmptyHtmlSelectRecord(
        systemStatus.getLabel("calendar.none.4dashes"));
    userList.setBuildContact(true);
    userList.setBuildContactDetails(false);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    userList.setRoleType(Constants.ROLETYPE_REGULAR);
    userList.setDepartment(
        newTic.getDepartmentCode() != -1 ? newTic.getDepartmentCode() : 0);
    userList.setSiteId(newOrg.getSiteId());
    userList.setIncludeUsersWithAccessToAllSites(true);
    userList.buildList(db);
    context.getRequest().setAttribute("UserList", userList);

    //Load the user list the ticket can be resolved by
    UserList resolvedUserList = new UserList();
    resolvedUserList.setHidden(Constants.FALSE);
    resolvedUserList.setEmptyHtmlSelectRecord(
        systemStatus.getLabel("calendar.none.4dashes"));
    resolvedUserList.setBuildContact(true);
    resolvedUserList.setBuildContactDetails(false);
    resolvedUserList.setExcludeDisabledIfUnselected(true);
    resolvedUserList.setExcludeExpiredIfUnselected(true);
    resolvedUserList.setRoleType(Constants.ROLETYPE_REGULAR);
    resolvedUserList.setDepartment(
        newTic.getResolvedByDeptCode() != -1 ? newTic.getResolvedByDeptCode() : 0);
    resolvedUserList.setSiteId(newOrg.getSiteId());
    resolvedUserList.setIncludeUsersWithAccessToAllSites(true);
    resolvedUserList.buildList(db);
    context.getRequest().setAttribute("resolvedUserList", resolvedUserList);
      
    ContactList contactList = new ContactList();
    contactList.setBuildDetails(false);
    contactList.setBuildTypes(false);
    contactList.setOrgId(
        Integer.parseInt(context.getRequest().getParameter("orgId")));
    contactList.setDefaultContactId(newTic.getContactId());
    contactList.buildList(db);
    context.getRequest().setAttribute("ContactList", contactList);

    TicketDefectList list = new TicketDefectList();
    list.setSiteId(newOrg.getSiteId());
    list.buildList(db);
    HtmlSelect defectSelect = list.getHtmlSelectObj(newTic.getDefectId());
    defectSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes","None"), 0);
    context.getRequest().setAttribute("defectSelect", defectSelect);

    TicketCategoryList subList1 = new TicketCategoryList();
    subList1.setCatLevel(1);
    subList1.setSiteId(newOrg.getSiteId());
    subList1.setExclusiveToSite(true);
    subList1.setParentCode(newTic.getCatCode());
    subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
    subList1.buildList(db);
    subList1.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("SubList1", subList1);

    TicketCategoryList subList2 = new TicketCategoryList();
    subList2.setCatLevel(2);
    subList2.setSiteId(newOrg.getSiteId());
    subList2.setExclusiveToSite(true);
    if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(
        context.getRequest().getParameter("refresh")) == 1) {
      subList2.setParentCode(0);
      newTic.setSubCat1(0);
      newTic.setSubCat2(0);
      newTic.setSubCat3(0);
    } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(
        context.getRequest().getParameter("refresh")) == -1) {
      subList2.setParentCode(newTic.getSubCat1());
      subList2.getCatListSelect().setDefaultKey(newTic.getSubCat2());
    } else {
      subList2.setParentCode(newTic.getSubCat1());
    }
    subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
    subList2.buildList(db);
    subList2.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("SubList2", subList2);

    TicketCategoryList subList3 = new TicketCategoryList();
    subList3.setCatLevel(3);
    subList3.setSiteId(newOrg.getSiteId());
    subList3.setExclusiveToSite(true);
    if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(
        context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(
            context.getRequest().getParameter("refresh")) == 2)) {
      subList3.setParentCode(0);
      newTic.setSubCat2(0);
      newTic.setSubCat3(0);
    } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(
        context.getRequest().getParameter("refresh")) == -1) {
      subList3.setParentCode(newTic.getSubCat2());
      subList3.getCatListSelect().setDefaultKey(newTic.getSubCat3());
    } else {
      subList3.setParentCode(newTic.getSubCat2());
    }
    subList3.setHtmlJsEvent("onChange=\"javascript:updateSubList4();\"");
    subList3.buildList(db);
    subList3.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("SubList3", subList3);

    ActionPlanList actionPlans = new ActionPlanList();
    actionPlans.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
    if (newTic.getCatCode() > 0) {
      actionPlans.setLinkCatCode(newTic.getCatCode());
    }
    if (newTic.getSubCat1() > 0) {
      actionPlans.setLinkSubCat1(newTic.getSubCat1());
    }
    if (newTic.getSubCat2() > 0) {
      actionPlans.setLinkSubCat2(newTic.getSubCat2());
    }
    if (newTic.getSubCat3() > 0) {
      actionPlans.setLinkSubCat3(newTic.getSubCat3());
    }
    if (actionPlans.getLinkCatCode() <= 0 && actionPlans.getLinkSubCat1() <= 0 && actionPlans.getLinkSubCat2() <= 0 && actionPlans.getLinkSubCat3() <= 0) {
      actionPlans.setDisplayNone(true);
    }
    CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, PermissionCategory.MULTIPLE_CATEGORY_TICKET);
    actionPlans.setTableName(thisEditor.getTableName());
    actionPlans.setJsEvent("id=\"actionPlanId\"");
    actionPlans.setEnabled(Constants.TRUE);
    actionPlans.setSiteId(newOrg.getSiteId());
    actionPlans.setIncludeOnlyApproved(Constants.TRUE);
    actionPlans.buildList(db);
    context.getRequest().setAttribute("actionPlans", actionPlans);

    if (context.getRequest().getParameter("insertActionPlan") != null && !"".equals(context.getRequest().getParameter("insertActionPlan"))) {
      context.getRequest().setAttribute("insertActionPlan", String.valueOf(true));
    }

    if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(
        context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(
            context.getRequest().getParameter("refresh")) == 3)) {
      newTic.setSubCat3(0);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    Connection db = null;
    try {
      ActionPlanList plans = null;
      TicketCategoryAssignment assignment = null;
      String orgId = context.getRequest().getParameter("orgId");
      String catCode = context.getRequest().getParameter("catCode");
      String subCat1 = context.getRequest().getParameter("subCat1");
      String subCat2 = context.getRequest().getParameter("subCat2");
      String subCat3 = context.getRequest().getParameter("subCat3");
      db = this.getConnection(context);
      Organization orgDetails = new Organization(db, Integer.parseInt(orgId));
      plans = new ActionPlanList();
      plans.setSiteId(orgDetails.getSiteId());
      plans.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      if (catCode != null) {
        plans.setLinkCatCode(catCode);
        TicketCategoryList subList1 = new TicketCategoryList();
        subList1.setCatLevel(1);
        subList1.setSiteId(orgDetails.getSiteId());
        subList1.setExclusiveToSite(true);
        subList1.setParentCode(Integer.parseInt(catCode));
        subList1.buildList(db);
        assignment = new TicketCategoryAssignment(db, Integer.parseInt(catCode), (String) null);
        context.getRequest().setAttribute("SubList1", subList1);
      } else if (subCat1 != null) {
        plans.setLinkSubCat1(subCat1);
        TicketCategoryList subList2 = new TicketCategoryList();
        subList2.setCatLevel(2);
        subList2.setSiteId(orgDetails.getSiteId());
        subList2.setExclusiveToSite(true);
        subList2.setParentCode(Integer.parseInt(subCat1));
        subList2.buildList(db);
        assignment = new TicketCategoryAssignment(db, Integer.parseInt(subCat1), (String) null);
        context.getRequest().setAttribute("SubList2", subList2);
      } else if (subCat2 != null) {
        plans.setLinkSubCat2(subCat2);
        TicketCategoryList subList3 = new TicketCategoryList();
        subList3.setCatLevel(3);
        subList3.setSiteId(orgDetails.getSiteId());
        subList3.setExclusiveToSite(true);
        subList3.setParentCode(Integer.parseInt(subCat2));
        subList3.buildList(db);
        assignment = new TicketCategoryAssignment(db, Integer.parseInt(subCat2), (String) null);
        context.getRequest().setAttribute("SubList3", subList3);
      } else if (subCat3 != null) {
        plans.setLinkSubCat3(subCat3);
        assignment = new TicketCategoryAssignment(db, Integer.parseInt(subCat3), (String) null);
      }
      if (plans.getLinkCatCode() <= 0 && plans.getLinkSubCat1() <= 0 && plans.getLinkSubCat2() <= 0 && plans.getLinkSubCat3() <= 0) {
        plans.setDisplayNone(true);
      }
      if (assignment != null && assignment.getId() > -1) {
        assignment.setSiteId(orgDetails.getSiteId());
        assignment.buildDepartmentUsers(db);
        context.getRequest().setAttribute("assignment", assignment);
      } else if (assignment != null) {
        assignment.setSiteId(orgDetails.getSiteId());
        assignment.setDepartmentId(0);
        assignment.buildDepartmentUsers(db);
        context.getRequest().setAttribute("assignment", assignment);
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, PermissionCategory.MULTIPLE_CATEGORY_TICKET);
      plans.setTableName(thisEditor.getTableName());
      plans.setJsEvent("id=\"actionPlanId\"");
      plans.setEnabled(Constants.TRUE);
      plans.setSiteId(orgDetails.getSiteId());
      plans.setIncludeOnlyApproved(Constants.TRUE);
      plans.buildList(db);
      context.getRequest().setAttribute("actionPlans", plans);
    } catch (Exception errorMessage) {
    } finally {
      this.freeConnection(context, db);
    }
    return ("CategoryJSListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDepartmentJSList(ActionContext context) {
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      String departmentCode = context.getRequest().getParameter(
          "departmentCode");
      db = this.getConnection(context);
      UserList userList = new UserList();
      userList.setEnabled(Constants.TRUE);
      userList.setHidden(Constants.FALSE);
      userList.setExpired(Constants.FALSE);
      userList.setEmptyHtmlSelectRecord(
          systemStatus.getLabel("calendar.none.4dashes"));
      if (departmentCode != null && !"".equals(departmentCode) && !"-1".equals(
          departmentCode)) {
        userList.setBuildContact(true);
        userList.setBuildContactDetails(false);
        userList.setDepartment(Integer.parseInt(departmentCode));
        userList.setRoleType(Constants.ROLETYPE_REGULAR);
        userList.buildList(db);
      }
      context.getRequest().setAttribute("UserList", userList);
    } catch (Exception errorMessage) {

    } finally {
      this.freeConnection(context, db);
    }
    return ("DepartmentJSListOK");
  }


  /**
   * Loads the history for the specified ticket
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String ticketId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      //Load the ticket
      Ticket thisTic = new Ticket();
      thisTic.setBuildHistory(true);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisTic.setSystemStatus(systemStatus);
      thisTic.queryRecord(db, Integer.parseInt(ticketId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      //Load the organization
      Organization thisOrganization = new Organization(db, thisTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //check whether or not the owner is an active User
      if (thisTic.getAssignedTo() > -1) {
        thisTic.checkEnabledOwnerAccount(db);
      }
      context.getRequest().setAttribute("TicketDetails", thisTic);
      addRecentItem(context, thisTic);
      addModuleBean(context, "View Tickets", "Ticket Details");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ViewHistory");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @deprecated Replaced combobox with a pop-up
   */
  public String executeCommandOrganizationJSList(ActionContext context) {
    Connection db = null;
    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);

      ContactList contactList = new ContactList();
      if (orgId != null && !"-1".equals(orgId)) {
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(Integer.parseInt(orgId));
        contactList.buildList(db);
      }
      context.getRequest().setAttribute("ContactList", contactList);
    } catch (Exception errorMessage) {

    } finally {
      this.freeConnection(context, db);
    }
    return ("OrganizationJSListOK");
  }

}
