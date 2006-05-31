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
package org.aspcfs.modules.troubletickets.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;                                                            
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.UserGroup;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.UserUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: TroubleTickets.java,v 1.37 2002/12/20 14:07:55 mrajkowski Exp
 *          $
 * @created March 15, 2002
 */
public final class TroubleTickets extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("DefaultError");
    }
    return (this.executeCommandHome(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    try {
      db = this.getConnection(context);
      if (context.getRequest().getParameter("refresh") != null || (context.getRequest().getParameter(
          "contact") != null && context.getRequest().getParameter("contact").equals(
              "on"))) {
        newTic = (Ticket) context.getFormBean();
        newTic.getHistory().setTicketId(newTic.getId());
        newTic.getHistory().buildList(db);
      } else {
        newTic = new Ticket();
      }
      buildFormElements(context, db, newTic);

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
      context.getRequest().setAttribute(
          "systemStatus", this.getSystemStatus(context));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      context.getRequest().setAttribute(
          "ThisContact", newTic.getThisContact());
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "AddTicket", "Ticket Add");
    if (context.getRequest().getParameter("actionSource") != null) {
      context.getRequest().setAttribute(
          "actionSource", context.getRequest().getParameter("actionSource"));
      return "AddTicketOK";
    }
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    return ("AddOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandGenerateForm(ActionContext context) {
    if (!(hasPermission(context, "tickets-reports-add"))) {
      return ("PermissionError");
    }
    User user = this.getUser(context, this.getUserId(context));
    int siteId = user.getSiteId();
    PagedListInfo exportListInfo = this.getPagedListInfo(context, "TicExportListInfo");
    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList siteIdList = new LookupList(db, "lookup_site_id");
      siteIdList.setJsEvent("id=\"searchcodeSiteId\" onChange=\"updateCategoryList();\"");
      siteIdList.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteIdList);
      String siteIdString = exportListInfo.getSearchOptionValue("searchcodeSiteId");
      if (user.getSiteId() == -1 && siteIdString != null && !"".equals(siteIdString.trim())) {
        siteId = Integer.parseInt(siteIdString);
      }
      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setSiteId(siteId);
      categoryList.setExclusiveToSite(true);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      categoryList.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("CategoryList", categoryList);
      
      String catCode = exportListInfo.getSearchOptionValue("searchcodeCatCode");
      TicketCategoryList subList1 = new TicketCategoryList();
      subList1.setCatLevel(1);
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.setSiteId(siteId);
      subList1.setExclusiveToSite(true);
      if (catCode != null && !"".equals(catCode.trim())) {
        subList1.setParentCode(catCode);
        subList1.buildList(db);
      }
      subList1.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList1", subList1);
  
      String subList1String = exportListInfo.getSearchOptionValue("searchcodeSubList1");
      TicketCategoryList subList2 = new TicketCategoryList();
      subList2.setCatLevel(2);
      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.setSiteId(siteId);
      subList2.setExclusiveToSite(true);
      if (subList1String != null && !"".equals(subList1String.trim())) {
        subList2.setParentCode(subList1String);
        subList2.getCatListSelect().setDefaultKey(subList1String);
        subList2.buildList(db);
      }
      subList2.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList2", subList2);

      String subList2String = exportListInfo.getSearchOptionValue("searchcodeSubList2");
      TicketCategoryList subList3 = new TicketCategoryList();
      subList3.setCatLevel(3);
      subList3.setHtmlJsEvent("onChange=\"javascript:updateSubList4();\"");
      subList3.setSiteId(siteId);
      subList3.setExclusiveToSite(true);
      if (subList2String != null && !"".equals(subList2String.trim())) {
        subList3.setParentCode(subList2String);
        subList3.getCatListSelect().setDefaultKey(subList2String);
        subList3.buildList(db);
      }
      subList3.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList3", subList3);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Reports", "Generate new");
    return ("GenerateFormOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandExportReport(ActionContext context) {
    if (!hasPermission(context, "tickets-reports-add")) {
      return ("PermissionError");
    }
    boolean isValid = false;
    Connection db = null;
    String subject = context.getRequest().getParameter("subject");
    context.getRequest().setAttribute("subject", subject);
    String ownerCriteria = context.getRequest().getParameter("criteria1");
    PagedListInfo exportListInfo = this.getPagedListInfo(context, "TicExportListInfo");
    exportListInfo.setIsValid(true);
    User user = this.getUser(context, this.getUserId(context));
    int siteId = user.getSiteId();
    if (!isSiteAccessPermitted(context, String.valueOf(siteId))) {
      return ("PermissionError");
    }

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //setup file stuff
    String filePath = this.getPath(context, "ticket-reports");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(new java.util.Date());
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(new java.util.Date());
    filePath += datePathToUse1 + fs + datePathToUse2 + fs;

    TicketReport ticketReport = new TicketReport();
    ticketReport.setCriteria(
        context.getRequest().getParameterValues("selectedList"));
    ticketReport.setFilePath(filePath);
    ticketReport.setSubject(subject);

    exportListInfo.setLink("TroubleTickets.do?command=ExportReport");
    exportListInfo.setColumnToSortBy(context.getRequest().getParameter("sort"));
    exportListInfo.setItemsPerPage(0);
    ticketReport.setPagedListInfo(exportListInfo);
    exportListInfo.setSearchCriteria(ticketReport, context);
    ticketReport.setExclusiveToSite(true);
    ticketReport.setIncludeAllSites(false);
    
    if (ownerCriteria.equals("assignedToMe")) {
      ticketReport.setAssignedTo(this.getUserId(context));
      ticketReport.setIncludeAllSites(true);
    } else if (ownerCriteria.equals("unassigned")) {
      ticketReport.setOnlyUnassigned(true);
      ticketReport.setSiteId(siteId);
    } else if (ownerCriteria.equals("createdByMe")) {
      ticketReport.setTicketEnteredBy(getUserId(context));
      ticketReport.setSiteId(siteId);
    } else if (ownerCriteria.equals("allTickets")) {
      ticketReport.setSiteId(siteId);
    }

    try {
      db = this.getConnection(context);
      isValid = exportListInfo.getIsValid();
      if (isValid) {
        if (ticketReport.getSubCat3() == 0) {
          ticketReport.setSubCat3(-1);
        } 
        if (ticketReport.getSubCat2() == 0) {
//          ticketReport.setSubCat3(-1);
          ticketReport.setSubCat2(-1);
        } 
        if (ticketReport.getSubCat1() == 0) {
//          ticketReport.setSubCat3(-1);
//          ticketReport.setSubCat2(-1);
          ticketReport.setSubCat1(-1);
        } 
        if (ticketReport.getCatCode() == 0) {
//          ticketReport.setSubCat3(-1);
//          ticketReport.setSubCat2(-1);
//          ticketReport.setSubCat1(-1);
          ticketReport.setCatCode(-1);
        }
        ticketReport.buildReportFull(db, context);
        ticketReport.setEnteredBy(getUserId(context));
        ticketReport.setModifiedBy(getUserId(context));
        ticketReport.saveAndInsert(db);
      } else {
        processErrors(context, ticketReport.getErrors());
        return executeCommandGenerateForm(context);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandReports(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandShowReportHtml(ActionContext context) {
    if (!hasPermission(context, "tickets-reports-view")) {
      return ("PermissionError");
    }
    //Parameters
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId));
      String filePath = this.getPath(context, "ticket-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception e) {
    } finally {
      this.freeConnection(context, db);
    }
    return ("ReportHtmlOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReports(ActionContext context) {
    if (!hasPermission(context, "tickets-reports-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Build the list of report files
    FileItemList files = new FileItemList();
    files.setLinkModuleId(Constants.DOCUMENTS_TICKETS_REPORTS);
    files.setLinkItemId(-1);
    //Have a paged list
    PagedListInfo rptListInfo = this.getPagedListInfo(
        context, "TicketRptListInfo");
    rptListInfo.setLink("TroubleTickets.do?command=Reports");
    try {
      db = this.getConnection(context);
      files.setPagedListInfo(rptListInfo);
      if ("all".equals(rptListInfo.getListView())) {
        files.setOwnerIdRange(this.getUserRange(context));
      } else {
        files.setOwner(this.getUserId(context));
      }
      files.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "TicketReports");
    context.getRequest().setAttribute("FileList", files);
    return ("ReportsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!hasPermission(context, "tickets-reports-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    //Parameters
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId));
      if (thisItem.getEnteredBy() == this.getUserId(context)) {
        recordDeleted = thisItem.delete(
            db, this.getPath(context, "ticket-reports"));
        String filePath1 = this.getPath(context, "ticket-reports") + getDatePath(
            thisItem.getEntered()) + thisItem.getFilename() + ".csv";
        java.io.File fileToDelete1 = new java.io.File(filePath1);
        if (!fileToDelete1.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath1);
        }
        String filePath2 = this.getPath(context, "ticket-reports") + getDatePath(
            thisItem.getEntered()) + thisItem.getFilename() + ".html";
        java.io.File fileToDelete2 = new java.io.File(filePath2);
        if (!fileToDelete2.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath2);
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Reports delete");
    if (recordDeleted) {
      return ("DeleteReportOK");
    } else {
      return ("DeleteReportERROR");
    }
  }


  /**
   * DownloadCSVReport: Sends a copy of the CSV report to the user's local
   * machine
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDownloadCSVReport(ActionContext context) {
    if (!(hasPermission(context, "tickets-reports-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_TICKETS_REPORTS);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      FileItem itemToDownload = null;
      itemToDownload = thisItem;
      //itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "ticket-reports") + getDatePath(
          itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println(
            "Accounts-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-edit")) {
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
        newTic = new Ticket();
        newTic.setBuildOrgHierarchy(true);
        newTic.queryRecord(db, Integer.parseInt(ticketId));
      } else {
        newTic = (Ticket) context.getFormBean();
        if (newTic.getOrgId() != -1) {
          newTic.setCompanyName(OrganizationList.buildParentNameHierarchy(db, newTic.getOrgId(), true, new HashMap()));
        }
        newTic.buildRelatedInformation(db);
      }

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }

      String fromDefectDetails = context.getRequest().getParameter("defectCheck");
      if (fromDefectDetails == null || "".equals(fromDefectDetails.trim())) {
        fromDefectDetails = (String) context.getRequest().getAttribute("defectCheck");
      }
      if (fromDefectDetails != null && !"".equals(fromDefectDetails.trim())) {
        context.getRequest().setAttribute("defectCheck", fromDefectDetails);
      }

      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(
          0, systemStatus.getLabel("calendar.none.4dashes"));
      departmentList.setJsEvent(
          "onChange=\"javascript:updateUserList();javascript:resetAssignedDate();\"");
      context.getRequest().setAttribute("DepartmentList", departmentList);

      LookupList resolvedByDeptList = new LookupList(db, "lookup_department");
      resolvedByDeptList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      resolvedByDeptList.setJsEvent(
          "onChange=\"javascript:updateResolvedByUserList();\"");
      context.getRequest().setAttribute("resolvedByDeptList", resolvedByDeptList);

      //Load the severity list
      LookupList severityList = new LookupList(db, "ticket_severity");
      context.getRequest().setAttribute("SeverityList", severityList);
      //Load the ticket state
      LookupList ticketStateList = new LookupList(db, "lookup_ticket_state");
      ticketStateList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("ticketStateList", ticketStateList);
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
      if (getDbName(context).equals("cdb_voice") || getDbName(context).equals("cdb_ds21")) {
        CampaignList campaignList = new CampaignList();
        campaignList.setEnabled(Constants.TRUE);
        campaignList.setCompleteOnly(true);
        campaignList.buildList(db);
        context.getRequest().setAttribute("CampaignList", campaignList);
      }
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);

      //Load the top level category list
      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setSiteId(orgDetails.getSiteId());
      categoryList.setExclusiveToSite(true);
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
      userList.setHidden(Constants.FALSE);
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
      subList1.setParentCode(newTic.getCatCode());
      subList1.setSiteId(orgDetails.getSiteId());
      subList1.setExclusiveToSite(true);
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList1", subList1);
      //Load the contact list
      ContactList contactList = new ContactList();
      contactList.setEmptyHtmlSelectRecord(
          systemStatus.getLabel("calendar.none.4dashes"));
      contactList.setBuildDetails(false);
      contactList.setBuildTypes(false);
      contactList.setOrgId(newTic.getOrgId());
      contactList.setDefaultContactId(newTic.getContactId());
      contactList.buildList(db);
      context.getRequest().setAttribute("ContactList", contactList);
      //Load the ticket sub-category2 list
      TicketCategoryList subList2 = new TicketCategoryList();
      subList2.setCatLevel(2);
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
      subList2.setSiteId(orgDetails.getSiteId());
      subList2.setExclusiveToSite(true);
      subList2.buildList(db);
      subList2.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList2", subList2);
      //Load the ticket sub-category3 list
      TicketCategoryList subList3 = new TicketCategoryList();
      subList3.setCatLevel(3);
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
      subList3.setSiteId(orgDetails.getSiteId());
      subList3.setExclusiveToSite(true);
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
      actionPlans.setJsEvent("id=\"actionPlanId\"");
      actionPlans.setEnabled(Constants.TRUE);
      actionPlans.setIncludeOnlyApproved(Constants.TRUE);
      if (actionPlans.getLinkCatCode() <= 0 && actionPlans.getLinkSubCat1() <= 0 && actionPlans.getLinkSubCat2() <= 0 && actionPlans.getLinkSubCat3() <= 0) {
        actionPlans.setDisplayNone(true);
      }
      actionPlans.setSiteId(orgDetails.getSiteId());
      if (user.getSiteId() == -1) {
        actionPlans.setIncludeAllSites(true);
      }
      actionPlans.buildList(db);

      ActionPlanWorkList workList = new ActionPlanWorkList();
      workList.setSiteId(newTic.getSiteId());
      if (user.getSiteId() == -1) {
        workList.setIncludeAllSites(true);
      }
      workList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      workList.setLinkItemId(newTic.getId());
      workList.buildList(db);
      if (workList.size() > 0) {
        context.getRequest().setAttribute("insertActionPlan", String.valueOf(true));
      }
      actionPlans.addAtleastOne(db, workList);

      context.getRequest().setAttribute("actionPlans", actionPlans);
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
    addModuleBean(context, "ViewTickets", "View Tickets");

    return getReturn(context, "Modify");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    String ticketId = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      String fromDefectDetails = context.getRequest().getParameter("defectCheck");
      if (fromDefectDetails == null || "".equals(fromDefectDetails.trim())) {
        fromDefectDetails = (String) context.getRequest().getAttribute("defectCheck");
      }

      // Parameters
      ticketId = context.getRequest().getParameter("id");
      // Reset the pagedLists since this could be a new visit to this ticket
      deletePagedListInfo(context, "TicketDocumentListInfo");
      deletePagedListInfo(context, "SunListInfo");
      deletePagedListInfo(context, "TMListInfo");
      deletePagedListInfo(context, "CSSListInfo");
      deletePagedListInfo(context, "TicketsFolderInfo");
      deletePagedListInfo(context, "TicketTaskListInfo");
      deletePagedListInfo(context, "ticketPlanWorkListInfo");
      db = this.getConnection(context);
      // Load the ticket
      newTic = new Ticket();
      newTic.setBuildOrgHierarchy(true);
      newTic.queryRecord(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }

      Organization orgDetails = new Organization(db, newTic.getOrgId());
      // check wether or not the product id exists
      if (newTic.getProductId() != -1) {
        ProductCatalog product = new ProductCatalog(db, newTic.getProductId());
        context.getRequest().setAttribute("product", product);

        QuoteList quoteList = new QuoteList();
        quoteList.setTicketId(newTic.getId());
        quoteList.buildList(db);
        context.getRequest().setAttribute("quoteList", quoteList);
      }

      // check wether of not the customer product id exists
      if (newTic.getCustomerProductId() != -1) {
        CustomerProduct customerProduct = new CustomerProduct(
            db, newTic.getCustomerProductId());
        customerProduct.buildFileList(db);
        context.getRequest().setAttribute("customerProduct", customerProduct);
      }
      if (newTic.getDefectId() !=-1) {
        TicketDefect defect = new TicketDefect(db, newTic.getDefectId());
        context.getRequest().setAttribute("defect", defect);
        if (fromDefectDetails != null && !"".equals(fromDefectDetails) && !fromDefectDetails.equals(String.valueOf(defect.getId()))) {
          fromDefectDetails = String.valueOf(defect.getId());
        }
      }

      // check whether or not the owner is an active User
      if (newTic.getAssignedTo() > -1) {
        newTic.checkEnabledOwnerAccount(db);
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
      if (fromDefectDetails != null && !"".equals(fromDefectDetails.trim())) {
        context.getRequest().setAttribute("defectCheck", fromDefectDetails);
      }
      TicketCategoryList ticketCategoryList = new TicketCategoryList();
      ticketCategoryList.setSiteId(orgDetails.getSiteId());
      ticketCategoryList.setIncludeDisabled(true);
      ticketCategoryList.setExclusiveToSite(true);
      ticketCategoryList.buildList(db);
      context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
    addRecentItem(context, newTic);
    addModuleBean(context, "ViewTickets", "View Tickets");
    return ("DetailsOK");
  }


  /**
   * View Tickets History
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    Ticket thisTic = null;
    String ticketId = null;

    try {
      ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      thisTic = new Ticket();
      thisTic.setBuildHistory(true);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisTic.setSystemStatus(systemStatus);
      thisTic.queryRecord(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }

      //check whether or not the owner is an active User
      if (thisTic.getAssignedTo() > -1) {
        thisTic.checkEnabledOwnerAccount(db);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Tickets", "Ticket Details");
    context.getRequest().setAttribute("TicketDetails", thisTic);
    addModuleBean(context, "ViewTickets", "View Tickets");
    addRecentItem(context, thisTic);
    return ("ViewHistoryOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandHome(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }
    context.getSession().removeAttribute("searchTickets");
    Connection db = null;
    TicketList assignedToMeList = new TicketList();
    TicketList openList = new TicketList();
    TicketList createdByMeList = new TicketList();
    TicketList allTicketsList = new TicketList();
    TicketList userGroupTicketList = new TicketList();
    User user = this.getUser(context, this.getUserId(context));
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    //reset the paged lists
    if (context.getRequest().getParameter("resetList") != null && context.getRequest().getParameter(
        "resetList").equals("true")) {
      context.getSession().removeAttribute("AssignedToMeInfo");
      context.getSession().removeAttribute("OpenInfo");
      context.getSession().removeAttribute("CreatedByMeInfo");
      context.getSession().removeAttribute("AllTicketsInfo");
      context.getSession().removeAttribute("UserGroupTicketInfo");
    }
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    //Assigned To Me
    PagedListInfo assignedToMeInfo = this.getPagedListInfo(
        context, "AssignedToMeInfo", "t.entered", "desc");
    assignedToMeInfo.setLink("TroubleTickets.do?command=Home");
    if (sectionId == null) {
      if (!assignedToMeInfo.getExpandedSelection()) {
        if (assignedToMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          assignedToMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (assignedToMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          assignedToMeInfo.setItemsPerPage(
              PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(assignedToMeInfo.getId())) {
      assignedToMeInfo.setExpandedSelection(true);
    }
    if (sectionId == null || assignedToMeInfo.getExpandedSelection() == true) {
      assignedToMeList.setPagedListInfo(assignedToMeInfo);
      assignedToMeList.setAssignedTo(user.getId());
      assignedToMeList.setIncludeAllSites(true);
      assignedToMeList.setOnlyOpen(true);
      if ("assignedToMe".equals(assignedToMeInfo.getListView())) {
        assignedToMeList.setAssignedTo(user.getId());
      }
    }
    //Other Tickets In My Department
    PagedListInfo openInfo = this.getPagedListInfo(
        context, "OpenInfo", "t.entered", "desc");
    openInfo.setLink("TroubleTickets.do?command=Home");
    if (sectionId == null) {
      if (!openInfo.getExpandedSelection()) {
        if (openInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          openInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (openInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          openInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(openInfo.getId())) {
      openInfo.setExpandedSelection(true);
    }
    if (sectionId == null || openInfo.getExpandedSelection() == true) {
      openList.setPagedListInfo(openInfo);
      openList.setUnassignedToo(true);
      openList.setBuildDepartmentTickets(true);
      openList.setDepartment(
          thisUser.getUserRecord().getContact().getDepartment());
      openList.setExcludeAssignedTo(user.getId());
      if (UserUtils.getUserSiteId(context.getRequest()) != -1) {
        openList.setIncludeAllSites(false);
      }
      openList.setExclusiveToSite(true);
      openList.setSiteId(UserUtils.getUserSiteId(context.getRequest()));
      openList.setOnlyOpen(true);
    }
    //Tickets Created By Me
    PagedListInfo createdByMeInfo = this.getPagedListInfo(
        context, "CreatedByMeInfo", "t.entered", "desc");
    createdByMeInfo.setLink("TroubleTickets.do?command=Home");
    if (sectionId == null) {
      if (!createdByMeInfo.getExpandedSelection()) {
        if (createdByMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          createdByMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (createdByMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          createdByMeInfo.setItemsPerPage(
              PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(createdByMeInfo.getId())) {
      createdByMeInfo.setExpandedSelection(true);
    }
    if (sectionId == null || createdByMeInfo.getExpandedSelection() == true) {
      createdByMeList.setPagedListInfo(createdByMeInfo);
      createdByMeList.setEnteredBy(user.getId());
      createdByMeList.setSiteId(user.getSiteId());
      createdByMeList.setExclusiveToSite(true);
      if (user.getSiteId() != -1) {
        createdByMeList.setIncludeAllSites(false);
      }
      createdByMeList.setOnlyOpen(true);
    }
    //Tickets in my User Group
    PagedListInfo userGroupTicketInfo = this.getPagedListInfo(
        context, "UserGroupTicketInfo", "t.entered", "desc");
    userGroupTicketInfo.setLink("TroubleTickets.do?command=Home");
    if (sectionId == null) {
      if (!userGroupTicketInfo.getExpandedSelection()) {
        if (userGroupTicketInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          userGroupTicketInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (userGroupTicketInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          userGroupTicketInfo.setItemsPerPage(
              PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(userGroupTicketInfo.getId())) {
      userGroupTicketInfo.setExpandedSelection(true);
    }
    if (sectionId == null || userGroupTicketInfo.getExpandedSelection() == true) {
      userGroupTicketList.setPagedListInfo(userGroupTicketInfo);
      userGroupTicketList.setInMyUserGroups(user.getId());
    }
    //All Tickets
    PagedListInfo allTicketsInfo = this.getPagedListInfo(
        context, "AllTicketsInfo", "t.entered", "desc");
    allTicketsInfo.setLink("TroubleTickets.do?command=Home");
    if (sectionId == null) {
      if (!allTicketsInfo.getExpandedSelection()) {
        if (allTicketsInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          allTicketsInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (allTicketsInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          allTicketsInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(allTicketsInfo.getId())) {
      allTicketsInfo.setExpandedSelection(true);
    }
    if (sectionId == null || allTicketsInfo.getExpandedSelection()) {
      allTicketsList.setPagedListInfo(allTicketsInfo);
      allTicketsList.setSiteId(user.getSiteId());
      allTicketsList.setExclusiveToSite(true);
      if (user.getSiteId() > -1) {
        allTicketsList.setIncludeAllSites(false);
      }
      allTicketsList.setUnassignedToo(true);
      allTicketsList.setOnlyOpen(true);
    }
    try {
      db = this.getConnection(context);
      if (sectionId == null || assignedToMeInfo.getExpandedSelection()) {
        assignedToMeList.buildList(db);
      }
      if (sectionId == null || openInfo.getExpandedSelection()) {
        openList.buildList(db);
      }
      if (sectionId == null || createdByMeInfo.getExpandedSelection()) {
        createdByMeList.buildList(db);
      }
      if (sectionId == null || allTicketsInfo.getExpandedSelection()) {
        allTicketsList.buildList(db);
      }
      if (sectionId == null || userGroupTicketInfo.getExpandedSelection()) {
        userGroupTicketList.buildList(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "View Tickets");
    context.getRequest().setAttribute("CreatedByMeList", createdByMeList);
    context.getRequest().setAttribute("AssignedToMeList", assignedToMeList);
    context.getRequest().setAttribute("OpenList", openList);
    context.getRequest().setAttribute("AllTicketsList", allTicketsList);
    context.getRequest().setAttribute("UserGroupTicketList", userGroupTicketList);
    addModuleBean(context, "ViewTickets", "View Tickets");
    return ("HomeOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearchTickets(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");

    Connection db = null;
    User user = this.getUser(context, this.getUserId(context));
    TicketList ticList = new TicketList();
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    ticListInfo.setLink("TroubleTickets.do?command=SearchTickets");
    ticList.setPagedListInfo(ticListInfo);
    ticListInfo.setSearchCriteria(ticList, context);

    try {
      db = this.getConnection(context);

      if (ticList.getSiteId() == Constants.INVALID_SITE) {
        ticList.setSiteId(user.getSiteId());
        ticList.setIncludeAllSites(true);
      } else if (ticList.getSiteId() == -1) {
        ticList.setExclusiveToSite(true);
        ticList.setIncludeAllSites(false);
      } else {
        ticList.setExclusiveToSite(true);
        ticList.setIncludeAllSites(false);
      }
      if ("unassigned".equals(ticListInfo.getListView())) {
        ticList.setUnassignedToo(true);
        ticList.setBuildDepartmentTickets(true);
        ticList.setDepartment(
            thisUser.getUserRecord().getContact().getDepartment());
      } else if ("assignedToMe".equals(ticListInfo.getListView())) {
        ticList.setAssignedTo(getUserId(context));
      } else {
        ticList.setUnassignedToo(true);
        if ("createdByMe".equals(ticListInfo.getListView())) {
          ticList.setEnteredBy(getUserId(context));
        }
      }
      //set the status
      if (ticListInfo.getFilterKey("listFilter1") == 1) {
        ticList.setOnlyOpen(true);
      } else if (ticListInfo.getFilterKey("listFilter1") == 2) {
        ticList.setOnlyClosed(true);
      }
      ticList.buildList(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "SearchTickets", "Search Tickets");
    context.getRequest().setAttribute("TicList", ticList);
    context.getSession().setAttribute("searchTickets", "yes");
    addModuleBean(context, "SearchTickets", "Search Tickets");
    return ("ResultsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReopen(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-edit"))) {
      return ("PermissionError");
    }
    int resultCount = -1;
    Connection db = null;
    Ticket thisTicket = null;
    try {
      db = this.getConnection(context);
      thisTicket = new Ticket(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      Ticket oldTicket = new Ticket(db, thisTicket.getId());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
      if (resultCount == -1) {
        return (executeCommandDetails(context));
      } else if (resultCount == 1) {
        thisTicket.queryRecord(db, thisTicket.getId());
        this.processUpdateHook(context, oldTicket, thisTicket);
        return (executeCommandDetails(context));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    return ("UserError");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean contactRecordInserted = false;
    boolean isValid = true;

    Contact nc = null;
    Ticket newTicket = null;

    String newContact = context.getRequest().getParameter("contact");

    Ticket newTic = (Ticket) context.getFormBean();
    newTic.setEnteredBy(getUserId(context));
    newTic.setModifiedBy(getUserId(context));

    if (newContact != null && newContact.equals("on")) {
      //If there are any changes here, also check AccountTickets where a new contact is created
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
      if (newTic.getOrgId() == 0) {
        nc.setEmployee(true);
      }
    }

    try {
      db = this.getConnection(context);

      if (nc != null) {
        isValid = this.validateObject(context, db, nc);
        if (isValid) {
          //Check if portal user can insert this record
          if (!isRecordAccessPermitted(context, db, nc.getOrgId())) {
            return ("PermissionError");
          }
          contactRecordInserted = nc.insert(db);
          if (contactRecordInserted) {
            newTic.setContactId(nc.getId());
          }
          if (contactRecordInserted) {
            if (newTic.getOrgId() > 0){
              newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
            }
            isValid = this.validateObject(context, db, newTic) && isValid;
            //Check if portal user can insert this record
            if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
              return ("PermissionError");
            }

            if (isValid) {
              recordInserted = newTic.insert(db);
            }
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
        //Prepare the ticket for the response
        newTicket = new Ticket(db, newTic.getId());
        context.getRequest().setAttribute("TicketDetails", newTicket);
        TicketCategoryList ticketCategoryList = new TicketCategoryList();
        ticketCategoryList.setSiteId(newTicket.getSiteId());
        ticketCategoryList.setExclusiveToSite(true);
        ticketCategoryList.setEnabledState(Constants.TRUE);
        ticketCategoryList.buildList(db);
        context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);

        if (newTicket.getDefectId() !=-1) {
          TicketDefect defect = new TicketDefect(db, newTicket.getDefectId());
          context.getRequest().setAttribute("defect", defect);
        }
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

        addRecentItem(context, newTicket);

        processInsertHook(context, newTicket);
      } else {
        if (newTic.getOrgId() != -1) {
          Organization thisOrg = new Organization(db, newTic.getOrgId());
          newTic.setCompanyName(thisOrg.getName());
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Ticket Insert ok");
    if (recordInserted && isValid) {
      if (context.getRequest().getParameter("actionSource") != null) {
        context.getRequest().setAttribute(
            "actionSource", context.getRequest().getParameter("actionSource"));
        return "InsertTicketOK";
      }
      return ("InsertOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Prepares supplemental form data that a user can search by
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandSearchTicketsForm(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Prepare ticket state form data
    HtmlSelect ticketTypeSelect = new HtmlSelect();
    ticketTypeSelect.addItem("0", systemStatus.getLabel("calendar.any.4dashes", "-- Any --"));
    ticketTypeSelect.addItem("1", systemStatus.getLabel("filter.openOnly", "Open Only"));
    ticketTypeSelect.addItem("2", systemStatus.getLabel("filter.closedOnly", "Closed Only"));
    ticketTypeSelect.build();
    context.getRequest().setAttribute("TicketTypeSelect", ticketTypeSelect);
    PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");
    try {
      db = this.getConnection(context);
      //Prepare severity list form data
      LookupList severityList = new LookupList(db, "ticket_severity");
      severityList.addItem(0, systemStatus.getLabel("calendar.any.4dashes", "-- Any --"));
      context.getRequest().setAttribute("SeverityList", severityList);

      //Prepare priority list form data
      LookupList priorityList = new LookupList(db, "ticket_priority");
      priorityList.addItem(0, systemStatus.getLabel("calendar.any.4dashes", "-- Any --"));
      context.getRequest().setAttribute("PriorityList", priorityList);

      //Prepare escalation list form data
      LookupList escalationList = new LookupList(db, "lookup_ticket_escalation");
      escalationList.addItem(0, systemStatus.getLabel("calendar.any.4dashes", "-- Any --"));
      context.getRequest().setAttribute("EscalationList", escalationList);
      addModuleBean(context, "SearchTickets", "Tickets Search");

      //check if account/owner is already selected, if so build it
      if (!"".equals(ticListInfo.getSearchOptionValue("searchcodeOrgId")) && !"-1".equals(
          ticListInfo.getSearchOptionValue("searchcodeOrgId"))) {
        String orgId = ticListInfo.getSearchOptionValue("searchcodeOrgId");
        Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrg);
      }

      if (!"".equals(ticListInfo.getSearchOptionValue("searchcodeUserGroupId")) && !"-1".equals(ticListInfo.getSearchOptionValue("searchcodeUserGroupId"))) {
        String groupId = ticListInfo.getSearchOptionValue("searchcodeUserGroupId");
        UserGroup group = new UserGroup(db, Integer.parseInt(groupId));
        context.getRequest().setAttribute("userGroup", group);
      }

      //sites lookup
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
      context.getRequest().setAttribute("SiteList", siteList);

      return ("SearchTicketsFormOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-edit")) {
      return ("PermissionError");
    }
    int resultCount = 0;
    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean isValid = true;
    Ticket newTic = (Ticket) context.getFormBean();
    Connection db = null;
    try {
      db = this.getConnection(context);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      for (catCount = 0; catCount < 4; catCount++) {
        if ((context.getRequest().getParameter("newCat" + catCount + "chk") != null && context.getRequest().getParameter(
            "newCat" + catCount + "chk").equals("on") && context.getRequest().getParameter(
                "newCat" + catCount) != null && !(context.getRequest().getParameter(
                    "newCat" + catCount).equals("")))) {
          thisCat = new TicketCategory();
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
          thisCat.setSiteId(orgDetails.getSiteId());
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
      newTic.setModifiedBy(getUserId(context));
      //Get the previousTicket, update the ticket, then send both to a hook
      Ticket previousTicket = new Ticket(db, newTic.getId());
      if (previousTicket.getProductId() > -1) {
        // TODO: determine why this was being done. If this code is allowed to execute, then a new product cannot
        // be linked to this ticket
        //newTic.setProductId(previousTicket.getProductId());
      }
      newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
      isValid = this.validateObject(context, db, newTic) && isValid;
      if (isValid) {
        resultCount = newTic.update(db);
        if (resultCount == 1) {
          newTic.queryRecord(db, newTic.getId());
          processUpdateHook(context, previousTicket, newTic);
        }
      }
      TicketCategoryList ticketCategoryList = new TicketCategoryList();
      ticketCategoryList.setSiteId(newTic.getSiteId());
      ticketCategoryList.setExclusiveToSite(true);
      ticketCategoryList.setEnabledState(Constants.TRUE);
      ticketCategoryList.buildList(db);
      context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandHome(context));
      }
      return getReturn(context, "Update");
    }
    return (executeCommandModify(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-delete"))) {
      return ("PermissionError");
    }
    HtmlDialog htmlDialog = new HtmlDialog();
    Ticket ticket = null;
    String id = context.getRequest().getParameter("id");
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      ticket = new Ticket(db, Integer.parseInt(id));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, ticket.getOrgId())) {
        return ("PermissionError");
      }
      String returnType = (String) context.getRequest().getParameter("return");
      DependencyList dependencies = ticket.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));

      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      if ("searchResults".equals(returnType)) {
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='TroubleTickets.do?command=Trash&id=" + id + "&return=searchResults" + RequestUtils.addLinkParams(
                context.getRequest(), "popup|popupType|actionId") + "'");

      } else {
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='TroubleTickets.do?command=Trash&id=" + id + RequestUtils.addLinkParams(
                context.getRequest(), "popup|popupType|actionId") + "'");
      }
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
   * Deletes the specified ticket and triggers any hooks
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Ticket thisTic = null;
    Connection db = null;
    //Parameters
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      thisTic = new Ticket(db, Integer.parseInt(passedId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordDeleted = thisTic.delete(db, getDbNamePath(context));
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
        deleteRecentItem(context, thisTic);

        String returnType = (String) context.getRequest().getParameter(
            "return");
        if ("searchResults".equals(returnType)) {
          context.getRequest().setAttribute(
              "refreshUrl", "TroubleTickets.do?command=SearchTickets" + RequestUtils.addLinkParams(
                  context.getRequest(), "popup|popupType|actionId"));
        } else {
          context.getRequest().setAttribute(
              "refreshUrl", "TroubleTickets.do?command=Home" + RequestUtils.addLinkParams(
                  context.getRequest(), "popup|popupType|actionId"));
        }
        return ("DeleteOK");
      }
      return (executeCommandHome(context));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Ticket thisTic = null;
    Connection db = null;
    //Parameters
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      thisTic = new Ticket(db, Integer.parseInt(passedId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordUpdated = thisTic.updateStatus(db, true, this.getUserId(context));
      if (recordUpdated) {
        processDeleteHook(context, thisTic);
        deleteRecentItem(context, thisTic);

        String returnType = (String) context.getRequest().getParameter(
            "return");
        if ("searchResults".equals(returnType)) {
          context.getRequest().setAttribute(
              "refreshUrl", "TroubleTickets.do?command=SearchTickets" + RequestUtils.addLinkParams(
                  context.getRequest(), "popup|popupType|actionId"));
        } else {
          context.getRequest().setAttribute(
              "refreshUrl", "TroubleTickets.do?command=Home" + RequestUtils.addLinkParams(
                  context.getRequest(), "popup|popupType|actionId"));
        }
        return ("DeleteOK");
      }
      return (executeCommandHome(context));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestore(ActionContext context) {

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
      return (executeCommandDetails(context));
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @param db      Description of Parameter
   * @param newTic  Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildFormElements(ActionContext context, Connection db, Ticket newTic) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);
    LookupList departmentList = new LookupList(db, "lookup_department");
    departmentList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
    departmentList.setJsEvent(
        "onChange=\"javascript:updateUserList();javascript:resetAssignedDate();\"");
    context.getRequest().setAttribute("DepartmentList", departmentList);
    User user = this.getUser(context, this.getUserId(context));
    int siteId = user.getSiteId();
    if (newTic.getOrgId() > -1) {
      Organization org = new Organization(db, newTic.getOrgId());
      siteId = org.getSiteId();
    }
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

    //Load the ticket state
    LookupList ticketStateList = new LookupList(db, "lookup_ticket_state");
    ticketStateList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("ticketStateList", ticketStateList);

    TicketCategoryList categoryList = new TicketCategoryList();
    categoryList.setCatLevel(0);
    categoryList.setParentCode(0);
    categoryList.setSiteId(siteId);
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
    userList.setSiteId(siteId);
    userList.setIncludeUsersWithAccessToAllSites(true);
    userList.buildList(db);
    context.getRequest().setAttribute("UserList", userList);

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
    resolvedUserList.setSiteId(UserUtils.getUserSiteId(context.getRequest()));
    resolvedUserList.setIncludeUsersWithAccessToAllSites(true);
    resolvedUserList.buildList(db);
    context.getRequest().setAttribute("resolvedUserList", resolvedUserList);

    ContactList contactList = new ContactList();
    if (newTic != null && newTic.getOrgId() != -1) {
      contactList.setBuildDetails(false);
      contactList.setBuildTypes(false);
      contactList.setOrgId(newTic.getOrgId());
      contactList.setDefaultContactId(newTic.getContactId());
      contactList.buildList(db);
    }
    context.getRequest().setAttribute("ContactList", contactList);

    TicketDefectList list = new TicketDefectList();
    list.setSiteId(this.getUserSiteId(context));
    list.buildList(db);
    HtmlSelect defectSelect = list.getHtmlSelectObj(newTic.getDefectId());
    defectSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes","None"), 0);
    context.getRequest().setAttribute("defectSelect", defectSelect);

    TicketCategoryList subList1 = new TicketCategoryList();
    subList1.setCatLevel(1);
    subList1.setParentCode(newTic.getCatCode());
    subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
    subList1.setSiteId(siteId);
    subList1.setExclusiveToSite(true);
    subList1.buildList(db);
    subList1.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("SubList1", subList1);

    TicketCategoryList subList2 = new TicketCategoryList();
    subList2.setCatLevel(2);
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
    subList2.setSiteId(siteId);
    subList2.setExclusiveToSite(true);
    subList2.buildList(db);
    subList2.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("SubList2", subList2);

    TicketCategoryList subList3 = new TicketCategoryList();
    subList3.setCatLevel(3);
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
    subList3.setSiteId(siteId);
    subList3.setExclusiveToSite(true);
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
    actionPlans.setIncludeOnlyApproved(Constants.TRUE);
    actionPlans.setSiteId(siteId);
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
      Organization orgDetails = null;
      String reset = context.getRequest().getParameter("reset");
      String orgId = context.getRequest().getParameter("orgId");
      String catCode = context.getRequest().getParameter("catCode");
      String subCat1 = context.getRequest().getParameter("subCat1");
      String subCat2 = context.getRequest().getParameter("subCat2");
      String subCat3 = context.getRequest().getParameter("subCat3");
      db = this.getConnection(context);
      orgDetails = new Organization(db, Integer.parseInt(orgId));
      plans = new ActionPlanList();
      plans.setSiteId(orgDetails.getSiteId());
      plans.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      if (reset != null && "true".equals(reset.trim())) {
        TicketCategoryList catList = new TicketCategoryList();
        catList.setSiteId(orgDetails.getSiteId());
        catList.setExclusiveToSite(true);
        catList.setCatLevel(0);
        catList.setParentCode(0);
        catList.buildList(db);
        context.getRequest().setAttribute("CategoryList", catList);
      } else if (catCode != null) {
        plans.setLinkCatCode(catCode);
        TicketCategoryList subList1 = new TicketCategoryList();
        subList1.setSiteId(orgDetails.getSiteId());
        subList1.setExclusiveToSite(true);
        subList1.setCatLevel(1);
        subList1.setParentCode(Integer.parseInt(catCode));
        subList1.buildList(db);
        assignment = new TicketCategoryAssignment(db, Integer.parseInt(catCode), (String) null);
        context.getRequest().setAttribute("SubList1", subList1);
      } else if (subCat1 != null) {
        plans.setLinkSubCat1(subCat1);
        TicketCategoryList subList2 = new TicketCategoryList();
        subList2.setSiteId(orgDetails.getSiteId());
        subList2.setExclusiveToSite(true);
        subList2.setCatLevel(2);
        subList2.setParentCode(Integer.parseInt(subCat1));
        subList2.buildList(db);
        assignment = new TicketCategoryAssignment(db, Integer.parseInt(subCat1), (String) null);
        context.getRequest().setAttribute("SubList2", subList2);
      } else if (subCat2 != null) {
        plans.setLinkSubCat2(subCat2);
        TicketCategoryList subList3 = new TicketCategoryList();
        subList3.setSiteId(orgDetails.getSiteId());
        subList3.setExclusiveToSite(true);
        subList3.setCatLevel(3);
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
      if (reset == null || !"true".equals(reset.trim())) {
        plans.buildList(db);
      }
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
      db = this.getConnection(context);

      String siteIdString = context.getRequest().getParameter("orgSiteId");
      if (siteIdString == null || "".equals(siteIdString)) {
        siteIdString = (String) context.getRequest().getAttribute("orgSiteId");
      }
      int siteId = UserUtils.getUserSiteId(context.getRequest());
      if (siteIdString != null && !"".equals(siteIdString)){
        siteId = Integer.parseInt(siteIdString);
      }
      String populateResourceAssigned = context.getRequest().getParameter("populateResourceAssigned");
      String populateResolvedBy = context.getRequest().getParameter("populateResolvedBy");

      if ("true".equals(populateResourceAssigned)){
        String resourceAssignedDepartmentCode = context.getRequest().getParameter(
            "resourceAssignedDepartmentCode");
        UserList resourceAssignedList = new UserList();
        resourceAssignedList.setHidden(Constants.FALSE);
        resourceAssignedList.setEmptyHtmlSelectRecord(
            systemStatus.getLabel("calendar.none.4dashes"));
        resourceAssignedList.setSiteId(siteId);
        resourceAssignedList.setIncludeUsersWithAccessToAllSites(true);
        resourceAssignedList.setBuildContact(true);
        resourceAssignedList.setBuildContactDetails(false);
        resourceAssignedList.setExcludeDisabledIfUnselected(true);
        resourceAssignedList.setExcludeExpiredIfUnselected(true);
        if (resourceAssignedDepartmentCode != null &&
            !"".equals(resourceAssignedDepartmentCode) &&
            !"-1".equals(resourceAssignedDepartmentCode)) {
          resourceAssignedList.setDepartment(Integer.parseInt(resourceAssignedDepartmentCode));
        }
        resourceAssignedList.setRoleType(Constants.ROLETYPE_REGULAR);
        resourceAssignedList.buildList(db);
        HtmlSelect resourceAssignedSelect = resourceAssignedList.getHtmlSelectObj(systemStatus.getLabel("calendar.none.4dashes"), -1);

        context.getRequest().setAttribute("resourceAssignedList", resourceAssignedList);
        context.getRequest().setAttribute("resourceAssignedSelect", resourceAssignedSelect);
      }

      if ("true".equals(populateResolvedBy)){
        String resolvedByDepartmentCode = context.getRequest().getParameter(
            "resolvedByDepartmentCode");
        UserList resolvedByList = new UserList();
        resolvedByList.setHidden(Constants.FALSE);
        resolvedByList.setEmptyHtmlSelectRecord(
            systemStatus.getLabel("calendar.none.4dashes"));
        resolvedByList.setSiteId(siteId);
        resolvedByList.setIncludeUsersWithAccessToAllSites(true);
        resolvedByList.setBuildContact(true);
        resolvedByList.setBuildContactDetails(false);
        resolvedByList.setExcludeDisabledIfUnselected(true);
        resolvedByList.setExcludeExpiredIfUnselected(true);
        if (resolvedByDepartmentCode != null &&
          !"".equals(resolvedByDepartmentCode) &&
          !"-1".equals(resolvedByDepartmentCode)) {
          resolvedByList.setDepartment(Integer.parseInt(resolvedByDepartmentCode));
        }
        resolvedByList.setRoleType(Constants.ROLETYPE_REGULAR);
        resolvedByList.buildList(db);
        HtmlSelect resolvedBySelect = resolvedByList.getHtmlSelectObj(systemStatus.getLabel("calendar.none.4dashes"), -1);

        context.getRequest().setAttribute("resolvedByList", resolvedByList);
        context.getRequest().setAttribute("resolvedBySelect", resolvedBySelect);
      }
    } catch (Exception errorMessage) {

    } finally {
      this.freeConnection(context, db);
    }
    return ("DepartmentJSListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      Organization orgDetails = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", orgDetails);
      String populateResourceAssigned = context.getRequest().getParameter("populateResourceAssigned");
      String populateResolvedBy = context.getRequest().getParameter("populateResolvedBy");
      if ("true".equals(populateResourceAssigned) || "true".equals(populateResolvedBy)) {
        executeCommandDepartmentJSList(context);
      }
      context.getRequest().setAttribute("orgSiteId", String.valueOf(orgDetails.getSiteId()));
      String populateDefects = context.getRequest().getParameter("populateDefects");
      if ("true".equals(populateDefects)){
        TicketDefectList defectList = new TicketDefectList();
        defectList.setSiteId(orgDetails.getSiteId());
        defectList.buildList(db);
        context.getRequest().setAttribute("defectList", defectList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }
    return ("OrganizationJSListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrintReport(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view") &&
        !hasPermission(context, "accounts-accounts-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("id");
      HashMap map = new HashMap();
      map.put("ticketid", new Integer(id));
      map.put("path", getWebInfPath(context, "reports"));
      //provide the dictionary as a parameter to the quote report
      map.put(
          "CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
      String filename = "ticket.xml";
      
      //Replace the font based on the system language to support i18n chars
      String fontPath = getWebInfPath(context, "fonts");
      String reportDir = getWebInfPath(context, "reports");
      JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename); 
      String language = getPref(context, "SYSTEM.LANGUAGE");
      
      JasperReportUtils.modifyFontProperties(
          jasperReport, reportDir, fontPath, language);
      
      byte[] bytes = JasperRunManager.runReportToPdf(
        jasperReport, map, db);
        
      if (bytes != null) {
        FileDownload fileDownload = new FileDownload();
        fileDownload.setDisplayName("Ticket_Details_" + id + ".pdf");
        fileDownload.sendFile(context, bytes, "application/pdf");
      } else {
        return ("SystemError");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("-none-");
  }
}