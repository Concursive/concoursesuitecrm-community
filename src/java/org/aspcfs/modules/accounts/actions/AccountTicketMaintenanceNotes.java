package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import java.sql.Timestamp;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.servicecontracts.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import java.text.*;
import org.aspcfs.modules.base.*;

/**
 *  Action class to view, add, edit, delete and list maintenance notes
 *
 *@author     kbhoopal
 *@created    March 17, 2004
 *@version    $Id: AccountTicketMaintenanceNotes.java,v 1.3.8.1 2004/05/13
 *      16:12:09 kbhoopal Exp $
 */
public final class AccountTicketMaintenanceNotes extends CFSModule {

  /**
   *  Lists maintenance notes
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      String ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      // Load the ticket
      Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, thisTicket.getOrgId())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ticketDetails", thisTicket);
      if (thisTicket.getAssetId() == -1) {
        return ("FormERROR");
      }

      // Load the Organization
      loadOrganizaton(context, db, thisTicket);
      // Build the onsiteModelList
      LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
      onsiteModelList.addItem(-1, "-- None --");
      context.getRequest().setAttribute("onsiteModelList", onsiteModelList);
      // Build the list of maintenance notes
      TicketMaintenanceNoteList thisList = new TicketMaintenanceNoteList();
      PagedListInfo sunListInfo = this.getPagedListInfo(context, "SunListInfo");
      sunListInfo.setLink("AccountTicketMaintenanceNotes.do?command=List&id=" + thisTicket.getId());
      thisList.setPagedListInfo(sunListInfo);
      thisList.setTicketId(thisTicket.getId());
      thisList.buildList(db);
      context.getRequest().setAttribute("maintenanceList", thisList);
      return ("ListOK");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Prepares the add page to add a maintenance note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket thisTicket = null;
    try {
      String ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      // Load the specified ticket
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("ticketDetails", thisTicket);
      // Load the Organization
      loadOrganizaton(context, db, thisTicket);
      // Load form elements
      LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
      onsiteModelList.addItem(-1, "-- None --");
      context.getRequest().setAttribute("onsiteModelList", onsiteModelList);
      return ("AddOK");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Prepares the modify page to modify a maintenance note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-edit")) {
      return ("PermissionError");
    }
    Ticket thisTicket = null;
    Connection db = null;
    // Begin with data needed for all forms
    try {
      String ticketId = context.getRequest().getParameter("id");
      String formId = context.getRequest().getParameter("formId");
      db = this.getConnection(context);
      // Load the ticket
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("ticketDetails", thisTicket);
      // Load the Organization
      loadOrganizaton(context, db, thisTicket);
      // Load onsite model list
      LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
      onsiteModelList.addItem(-1, "-- None --");
      context.getRequest().setAttribute("onsiteModelList", onsiteModelList);
      context.getRequest().setAttribute("return", context.getRequest().getParameter("return"));
      // Load the maintenance note elements
      TicketMaintenanceNote thisSun = new TicketMaintenanceNote();
      thisSun.queryRecord(db, Integer.parseInt(formId));
      context.getRequest().setAttribute("maintenanceDetails", thisSun);
      return ("ModifyOK");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Saves a maintenance note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      String ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      // Load the ticket
      Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      // Load the Organization
      loadOrganizaton(context, db, thisTicket);
      // Save the base data
      TicketMaintenanceNote thisMaintenance = new TicketMaintenanceNote();
      thisMaintenance.setModifiedBy(getUserId(context));
      thisMaintenance.setEnteredBy(getUserId(context));
      thisMaintenance.setLinkTicketId(thisTicket.getId());
      String descriptionOfService = context.getRequest().getParameter("descriptionOfService");
      thisMaintenance.setDescriptionOfService(descriptionOfService);
      //Saves details of each replaced part
      thisMaintenance.setRequestItems(context.getRequest());
      thisMaintenance.insert(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandList(context);
  }


  /**
   *  Updates a maintenance note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    try {
      String ticketId = context.getRequest().getParameter("id");
      String formId = context.getRequest().getParameter("formId");
      db = this.getConnection(context);
      // Load the ticket
      Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      // Load the Organization
      loadOrganizaton(context, db, thisTicket);
      // Save the base data
      TicketMaintenanceNote thisMaintenance = new TicketMaintenanceNote();
      thisMaintenance.setId(Integer.parseInt(formId));
      thisMaintenance.setModifiedBy(getUserId(context));
      thisMaintenance.setLinkTicketId(thisTicket.getId());
      String descriptionOfService = context.getRequest().getParameter("descriptionOfService");
      thisMaintenance.setDescriptionOfService(descriptionOfService);
      //Saves details of each replaced part
      thisMaintenance.setRequestItems(context.getRequest());
      String modified = context.getRequest().getParameter("modified");
      thisMaintenance.setModified(modified);

      resultCount = thisMaintenance.update(db);
      if (resultCount == -1) {
        processErrors(context, thisMaintenance.getErrors());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
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
   *  Prepares the view page for the maintenance note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      String ticketId = context.getRequest().getParameter("id");
      String formId = context.getRequest().getParameter("formId");
      db = this.getConnection(context);
      // Load the ticket
      Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, thisTicket.getOrgId())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ticketDetails", thisTicket);
      // Load the Organization
      loadOrganizaton(context, db, thisTicket);
      // Load the onsiteModelList
      LookupList onsiteModelList = new LookupList(db, "lookup_onsite_model");
      onsiteModelList.addItem(-1, "-- None --");
      context.getRequest().setAttribute("onsiteModelList", onsiteModelList);
      // Load the related form items
      // Maintenance Note items
      TicketMaintenanceNote thisSun = new TicketMaintenanceNote();
      thisSun.queryRecord(db, Integer.parseInt(formId));
      context.getRequest().setAttribute("maintenanceDetails", thisSun);
      return ("ViewOK");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }



  /**
   *  Confirms a request for deleting a maintenance note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    try {
      // Process form data
      String ticketId = context.getRequest().getParameter("id");
      String formId = context.getRequest().getParameter("formId");
      // Load the ticket
      db = this.getConnection(context);
      Ticket thisTicket = new Ticket();
      thisTicket.queryRecord(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("ticketDetails", thisTicket);
      // Prepare the HTML Dialog
      htmlDialog.setTitle("Dark Horse CRM: Confirm Delete");
      htmlDialog.addMessage("\nAre you sure you want to delete this form?");
      htmlDialog.setHeader("The form you are requesting to delete may have dependencies within Dark Horse CRM:");
      htmlDialog.addButton("Delete", "javascript:window.location.href='AccountTicketMaintenanceNotes.do?command=Delete&id=" + ticketId + "&formId=" + formId + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
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
   *  Deletes a maintenance note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-maintenance-report-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Ticket thisTicket = null;
    Connection db = null;
    // Process the parameters
    String ticketId = context.getRequest().getParameter("id");
    int formId = Integer.parseInt((String) context.getRequest().getParameter("formId"));
    try {
      db = this.getConnection(context);
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("ticketDetails", thisTicket);
      // Load the Organization
      loadOrganizaton(context, db, thisTicket);
      TicketMaintenanceNote thisMaintenance = new TicketMaintenanceNote();
      thisMaintenance.setId(formId);
      recordDeleted = thisMaintenance.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError", "The note could not be deleted because of referential integrity .");
      context.getRequest().setAttribute("refreshUrl", "AccountTicketMaintenanceNotes.do?command=View&id=" + ticketId);
      return ("DeleteError");
    } finally {
      this.freeConnection(context, db);
    }
    // The record was deleted
    if (recordDeleted) {
      context.getRequest().setAttribute("refreshUrl", "AccountTicketMaintenanceNotes.do?command=List&id=" + ticketId);
      return this.getReturn(context, "Delete");
    }
    // An error occurred, so notify the user
    processErrors(context, thisTicket.getErrors());
    context.getRequest().setAttribute("refreshUrl", "AccountTicketMaintenanceNotes.do?command=View&id=" + ticketId + "&formId=" + formId);
    return this.getReturn(context, "Delete");
  }


  /**
   *  loads organization details to display in at the top of the page
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  ticket            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void loadOrganizaton(ActionContext context, Connection db, Ticket ticket) throws SQLException {
    //Load the organization
    Organization thisOrganization = new Organization(db, ticket.getOrgId());
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }
}

