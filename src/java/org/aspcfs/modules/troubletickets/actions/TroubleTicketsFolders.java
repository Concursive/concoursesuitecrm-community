package org.aspcfs.modules.troubletickets.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Constants;
import com.zeroio.webutils.*;
import org.aspcfs.modules.base.*;

/**
 *  Web actions for working with custom folder form data
 *
 *@author     matt rajkowski
 *@created    November 6, 2003
 *@version    $Id$
 */
public final class TroubleTicketsFolders extends CFSModule {
  
  /**
   *  Fields: Shows a list of custom field records that are located "within" the
   *  selected Custom Folder. Also shows the details of a particular Custom
   *  Field Record when it is selected (details page)
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandFields(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket thisTicket = null;
    String recordId = null;
    boolean showRecords = true;
    String selectedCatId = null;
    try {
      String ticketId = context.getRequest().getParameter("ticketId");
      db = this.getConnection(context);
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", thisTicket);

      //Show a list of the different folders available in this module
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      //See which one is currently selected or use the default
      selectedCatId = (String) context.getRequest().getParameter("catId");
      if (selectedCatId == null) {
        selectedCatId = (String) context.getRequest().getAttribute("catId");
      }
      if (selectedCatId == null) {
        selectedCatId = "" + thisList.getDefaultCategoryId();
      }
      context.getRequest().setAttribute("catId", selectedCatId);

      if (Integer.parseInt(selectedCatId) > 0) {
        //See if a specific record has been chosen from the list
        recordId = context.getRequest().getParameter("recId");
        String recordDeleted = (String) context.getRequest().getAttribute("recordDeleted");
        if (recordDeleted != null) {
          recordId = null;
        }

        //Now build the specified or default category
        CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
        if (recordId == null && thisCategory.getAllowMultipleRecords()) {
          //The user didn't request a specific record, so show a list
          //of records matching this category that the user can choose from
          PagedListInfo folderListInfo = this.getPagedListInfo(context, "AccountFolderInfo");
          folderListInfo.setLink("TroubleTicketsFolders.do?command=Fields&ticketId=" + ticketId + "&catId=" + selectedCatId);

          CustomFieldRecordList recordList = new CustomFieldRecordList();
          recordList.setLinkModuleId(Constants.FOLDERS_TICKETS);
          recordList.setLinkItemId(thisTicket.getId());
          recordList.setCategoryId(thisCategory.getId());
          recordList.buildList(db);
          recordList.buildRecordColumns(db, thisCategory);
          context.getRequest().setAttribute("Records", recordList);
        } else {
          //The user requested a specific record, or this category only
          //allows a single record.
          thisCategory.setLinkModuleId(Constants.FOLDERS_TICKETS);
          thisCategory.setLinkItemId(thisTicket.getId());
          if (recordId != null) {
            thisCategory.setRecordId(Integer.parseInt(recordId));
          } else {
            thisCategory.buildRecordId(db);
            recordId = String.valueOf(thisCategory.getRecordId());
          }
          thisCategory.setIncludeEnabled(Constants.TRUE);
          thisCategory.setIncludeScheduled(Constants.TRUE);
          thisCategory.setBuildResources(true);
          thisCategory.buildResources(db);
          showRecords = false;

          if (thisCategory.getRecordId() > -1) {
            CustomFieldRecord thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
            context.getRequest().setAttribute("Record", thisRecord);
          }
        }
        context.getRequest().setAttribute("Category", thisCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Custom Fields Details");
    if (Integer.parseInt(selectedCatId) <= 0) {
      return ("FieldsEmptyOK");
    } else if (recordId == null && showRecords) {
      return ("FieldRecordListOK");
    } else {
      return ("FieldsOK");
    }
  }


  /**
   *  AddFolderRecord: Displays the form for inserting a new custom field record
   *  for the selected Account.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAddFolderRecord(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket thisTicket = null;
    try {
      String ticketId = context.getRequest().getParameter("ticketId");
      db = this.getConnection(context);
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", thisTicket);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisCategory.setLinkItemId(thisTicket.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Add Folder Record");
    return ("AddFolderRecordOK");
  }


  /**
   *  ModifyFields: Displays the modify form for the selected Custom Field
   *  Record.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandModifyFields(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket thisTicket = null;

    String selectedCatId = (String) context.getRequest().getParameter("catId");
    String recordId = (String) context.getRequest().getParameter("recId");

    try {
      String ticketId = context.getRequest().getParameter("ticketId");
      db = this.getConnection(context);
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", thisTicket);

      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisCategory.setLinkItemId(thisTicket.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Modify Custom Fields");
    if (recordId.equals("-1")) {
      return ("AddFolderRecordOK");
    } else {
      return ("ModifyFieldsOK");
    }
  }


  /**
   *  UpdateFields: Performs the actual update of the selected Custom Field
   *  Record based on user-submitted information from the modify form.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandUpdateFields(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket thisTicket = null;
    int resultCount = 0;
    try {
      String ticketId = context.getRequest().getParameter("ticketId");
      db = this.getConnection(context);
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", thisTicket);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      String recordId = (String) context.getRequest().getParameter("recId");

      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisCategory.setLinkItemId(thisTicket.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        resultCount = thisCategory.update(db);
      }
      if (resultCount == -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TroubleTicketsFolders-> ModifyField validation error");
        }
      } else {
        thisCategory.buildResources(db);
        CustomFieldRecord thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
        context.getRequest().setAttribute("Record", thisRecord);
      }
      context.getRequest().setAttribute("Category", thisCategory);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      return ("ModifyFieldsOK");
    } else if (resultCount == 1) {
      return ("UpdateFieldsOK");
    } else {
      context.getRequest().setAttribute("Error", CFSModule.NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  InsertFields: Performs the actual insert of a new Custom Field Record.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandInsertFields(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCode = -1;
    Ticket thisTicket = null;
    try {
      String ticketId = context.getRequest().getParameter("ticketId");
      db = this.getConnection(context);
      thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", thisTicket);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisCategory.setLinkItemId(thisTicket.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setEnteredBy(this.getUserId(context));
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        resultCode = thisCategory.insert(db);
      }
      if (resultCode == -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TroubleTicketsFolders-> InsertField validation error");
        }
      } else {
        processInsertHook(context, thisCategory);
      }
      context.getRequest().setAttribute("Category", thisCategory);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCode == -1) {
      return ("AddFolderRecordOK");
    } else {
      return (this.executeCommandFields(context));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteFields(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordDeleted = false;
    try {
      String selectedCatId = context.getRequest().getParameter("catId");
      String recordId = context.getRequest().getParameter("recId");
      String ticketId = context.getRequest().getParameter("ticketId");
      db = this.getConnection(context);
      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      CustomFieldRecord thisRecord = new CustomFieldRecord(db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.FOLDERS_TICKETS);
      thisRecord.setLinkItemId(Integer.parseInt(ticketId));
      thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
      if (!thisCategory.getReadOnly()) {
        recordDeleted = thisRecord.delete(db);
      }
      context.getRequest().setAttribute("recordDeleted", "true");
    } catch (Exception e) {
      addModuleBean(context, "ViewTickets", "Delete Folder Record");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DeleteFieldsOK");
  }
}

