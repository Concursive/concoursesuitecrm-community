package org.aspcfs.modules.troubletickets.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import java.text.*;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.quotes.base.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    March 15, 2002
 *@version    $Id: TroubleTickets.java,v 1.37 2002/12/20 14:07:55 mrajkowski Exp
 *      $
 */
public final class TroubleTickets extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("DefaultError");
    }
    return (this.executeCommandHome(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    try {
      db = this.getConnection(context);
      if (context.getRequest().getParameter("refresh") != null || (context.getRequest().getParameter("contact") != null && context.getRequest().getParameter("contact").equals("on"))) {
        newTic = (Ticket) context.getFormBean();
        newTic.getHistory().setTicketId(newTic.getId());
        newTic.getHistory().buildList(db);
      } else {
        newTic = new Ticket();
      }
      buildFormElements(context, db, newTic);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      context.getRequest().setAttribute("ThisContact", newTic.getThisContact());
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "AddTicket", "Ticket Add");
    if (context.getRequest().getParameter("actionSource") != null) {
      return "AddTicketOK";
    }
    return ("AddOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandGenerateForm(ActionContext context) {

    if (!(hasPermission(context, "tickets-reports-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "Reports", "Generate new");
    return ("GenerateFormOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandExportReport(ActionContext context) {
    if (!hasPermission(context, "tickets-reports-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;
    String subject = context.getRequest().getParameter("subject");
    String ownerCriteria = context.getRequest().getParameter("criteria1");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //setup file stuff
    String filePath = this.getPath(context, "ticket-reports");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(new java.util.Date());
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(new java.util.Date());
    filePath += datePathToUse1 + fs + datePathToUse2 + fs;

    TicketReport ticketReport = new TicketReport();
    ticketReport.setCriteria(context.getRequest().getParameterValues("selectedList"));
    ticketReport.setFilePath(filePath);
    ticketReport.setSubject(subject);

    PagedListInfo thisInfo = new PagedListInfo();
    thisInfo.setColumnToSortBy(context.getRequest().getParameter("sort"));
    thisInfo.setItemsPerPage(0);
    ticketReport.setPagedListInfo(thisInfo);

    if (ownerCriteria.equals("assignedToMe")) {
      ticketReport.setAssignedTo(this.getUserId(context));
      ticketReport.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
    } else if (ownerCriteria.equals("unassigned")) {
      ticketReport.setUnassignedToo(true);
      ticketReport.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
    } else if (ownerCriteria.equals("createdByMe")) {
      ticketReport.setUnassignedToo(true);
      ticketReport.setEnteredBy(getUserId(context));
    }

    try {
      db = this.getConnection(context);
      ticketReport.buildReportFull(db, context);
      ticketReport.setEnteredBy(getUserId(context));
      ticketReport.setModifiedBy(getUserId(context));
      ticketReport.saveAndInsert(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return executeCommandReports(context);
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
      String filePath = this.getPath(context, "ticket-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = this.includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception e) {
    } finally {
      this.freeConnection(context, db);
    }
    return ("ReportHtmlOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    PagedListInfo rptListInfo = this.getPagedListInfo(context, "TicketRptListInfo");
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
      Iterator i = files.iterator();
      while (i.hasNext()) {
        FileItem thisItem = (FileItem) i.next();
        //TODO: Remove these and replace in JSP with user cache
        Contact enteredBy = this.getUser(context, thisItem.getEnteredBy()).getContact();
        Contact modifiedBy = this.getUser(context, thisItem.getModifiedBy()).getContact();
        thisItem.setEnteredByString(enteredBy.getNameFirstLast());
        thisItem.setModifiedByString(modifiedBy.getNameFirstLast());
      }
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
        recordDeleted = thisItem.delete(db, this.getPath(context, "ticket-reports"));
        String filePath1 = this.getPath(context, "ticket-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".csv";
        java.io.File fileToDelete1 = new java.io.File(filePath1);
        if (!fileToDelete1.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath1);
        }
        String filePath2 = this.getPath(context, "ticket-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".html";
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
   *  DownloadCSVReport: Sends a copy of the CSV report to the user's local
   *  machine
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
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
      thisItem = new FileItem(db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_TICKETS_REPORTS);
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
      String filePath = this.getPath(context, "ticket-reports") + getDatePath(itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println("Accounts-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    try {
      String ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      if (context.getRequest().getParameter("companyName") == null) {
        newTic = new Ticket(db, Integer.parseInt(ticketId));
      } else {
        newTic = (Ticket) context.getFormBean();
        newTic.getHistory().setTicketId(newTic.getId());
        newTic.getHistory().buildList(db);
      }

      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "-- None --");
      departmentList.setJsEvent("onChange=\"javascript:updateUserList();\"");
      context.getRequest().setAttribute("DepartmentList", departmentList);

      LookupList severityList = new LookupList(db, "ticket_severity");
      context.getRequest().setAttribute("SeverityList", severityList);

      LookupList priorityList = new LookupList(db, "ticket_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      LookupList sourceList = new LookupList(db, "lookup_ticketsource");
      sourceList.addItem(0, "-- None --");
      context.getRequest().setAttribute("SourceList", sourceList);

      if (this.getDbName(context).equals("cdb_voice") || this.getDbName(context).equals("cdb_ds21")) {
        CampaignList campaignList = new CampaignList();
        campaignList.setEnabled(Constants.TRUE);
        campaignList.setCompleteOnly(true);
        campaignList.buildList(db);
        context.getRequest().setAttribute("CampaignList", campaignList);
      }

      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      categoryList.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("CategoryList", categoryList);

      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord("-- None --");
      userList.setBuildContact(true);
      userList.setBuildContactDetails(false);
      userList.setDepartment(newTic.getDepartmentCode());
      userList.setExcludeDisabledIfUnselected(true);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);

      TicketCategoryList subList1 = new TicketCategoryList();

      subList1.setCatLevel(1);
      subList1.setParentCode(newTic.getCatCode());
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList1", subList1);

      ContactList contactList = new ContactList();
      contactList.setEmptyHtmlSelectRecord("-- None --");
      contactList.setBuildDetails(false);
      contactList.setBuildTypes(false);
      contactList.setOrgId(newTic.getOrgId());
      contactList.buildList(db);
      context.getRequest().setAttribute("ContactList", contactList);

      TicketCategoryList subList2 = new TicketCategoryList();
      subList2.setCatLevel(2);

      if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == 1) {
        subList2.setParentCode(0);
        newTic.setSubCat1(0);
        newTic.setSubCat2(0);
        newTic.setSubCat3(0);
      } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == -1) {
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

      if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 2)) {
        subList3.setParentCode(0);
        newTic.setSubCat2(0);
        newTic.setSubCat3(0);
      } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == -1) {
        subList3.setParentCode(newTic.getSubCat2());
        subList3.getCatListSelect().setDefaultKey(newTic.getSubCat3());
      } else {
        subList3.setParentCode(newTic.getSubCat2());
      }

      subList3.buildList(db);
      subList3.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList3", subList3);

      if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 3)) {
        newTic.setSubCat3(0);
      }

      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
    addModuleBean(context, "ViewTickets", "View Tickets");
    return ("ModifyOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    String ticketId = null;
    try {
      // Parameters
      ticketId = context.getRequest().getParameter("id");
      // Reset the pagedLists since this could be a new visit to this ticket
      deletePagedListInfo(context, "TicketDocumentListInfo");
      deletePagedListInfo(context, "SunListInfo");
      deletePagedListInfo(context, "TMListInfo");
      deletePagedListInfo(context, "CSSListInfo");
      deletePagedListInfo(context, "TicketsFolderInfo");
      deletePagedListInfo(context, "TicketTaskListInfo");
      db = this.getConnection(context);
      // Load the ticket
      newTic = new Ticket();
      newTic.queryRecord(db, Integer.parseInt(ticketId));

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
        CustomerProduct customerProduct = new CustomerProduct(db, newTic.getCustomerProductId());
        customerProduct.buildFileList(db);
        context.getRequest().setAttribute("customerProduct", customerProduct);
      }

      // check whether or not the owner is an active User
      if (newTic.getAssignedTo() > -1) {
        newTic.checkEnabledOwnerAccount(db);
      }
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
   *  View Tickets History
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      thisTic.queryRecord(db, Integer.parseInt(ticketId));

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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
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
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    //reset the paged lists
    if (context.getRequest().getParameter("resetList") != null && context.getRequest().getParameter("resetList").equals("true")) {
      context.getSession().removeAttribute("AssignedToMeInfo");
      context.getSession().removeAttribute("OpenInfo");
      context.getSession().removeAttribute("CreatedByMeInfo");
      context.getSession().removeAttribute("AllTicketsInfo");
    }
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    //Assigned To Me
    PagedListInfo assignedToMeInfo = this.getPagedListInfo(context, "AssignedToMeInfo", "t.entered", "desc");
    assignedToMeInfo.setLink("TroubleTickets.do?command=Home");
    if (sectionId == null) {
      if (!assignedToMeInfo.getExpandedSelection()) {
        if (assignedToMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          assignedToMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (assignedToMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          assignedToMeInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(assignedToMeInfo.getId())) {
      assignedToMeInfo.setExpandedSelection(true);
    }
    if (sectionId == null || assignedToMeInfo.getExpandedSelection() == true) {
      assignedToMeList.setPagedListInfo(assignedToMeInfo);
      assignedToMeList.setAssignedTo(getUserId(context));
      assignedToMeList.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
      assignedToMeList.setOnlyOpen(true);
      if ("assignedToMe".equals(assignedToMeInfo.getListView())) {
        assignedToMeList.setAssignedTo(getUserId(context));
        assignedToMeList.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
      }
    }
    //Other Tickets In My Department
    PagedListInfo openInfo = this.getPagedListInfo(context, "OpenInfo", "t.entered", "desc");
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
      openList.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
      openList.setExcludeAssignedTo(this.getUserId(context));
      openList.setOnlyOpen(true);
    }
    //Tickets Created By Me
    PagedListInfo createdByMeInfo = this.getPagedListInfo(context, "CreatedByMeInfo", "t.entered", "desc");
    createdByMeInfo.setLink("TroubleTickets.do?command=Home");
    if (sectionId == null) {
      if (!createdByMeInfo.getExpandedSelection()) {
        if (createdByMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          createdByMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (createdByMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          createdByMeInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(createdByMeInfo.getId())) {
      createdByMeInfo.setExpandedSelection(true);
    }
    if (sectionId == null || createdByMeInfo.getExpandedSelection() == true) {
      createdByMeList.setPagedListInfo(createdByMeInfo);
      createdByMeList.setUnassignedToo(true);
      createdByMeList.setEnteredBy(getUserId(context));
      createdByMeList.setOnlyOpen(true);
    }
    //All Tickets
    PagedListInfo allTicketsInfo = this.getPagedListInfo(context, "AllTicketsInfo", "t.entered", "desc");
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
    if (sectionId == null || allTicketsInfo.getExpandedSelection() == true) {
      allTicketsList.setPagedListInfo(allTicketsInfo);
      allTicketsList.setUnassignedToo(true);
      allTicketsList.setOnlyOpen(true);
    }
    try {
      db = this.getConnection(context);
      if (sectionId == null || assignedToMeInfo.getExpandedSelection() == true) {
        assignedToMeList.buildList(db);
      }
      if (sectionId == null || openInfo.getExpandedSelection() == true) {
        openList.buildList(db);
      }
      if (sectionId == null || createdByMeInfo.getExpandedSelection() == true) {
        createdByMeList.buildList(db);
      }
      if (sectionId == null || allTicketsInfo.getExpandedSelection() == true) {
        allTicketsList.buildList(db);
        //System.out.println("Built all tickets --> "+ allTicketsList.size());
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
      addModuleBean(context, "ViewTickets", "View Tickets");
      return ("HomeOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearchTickets(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");

    Connection db = null;

    TicketList ticList = new TicketList();
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    ticListInfo.setLink("TroubleTickets.do?command=SearchTickets");
    ticList.setPagedListInfo(ticListInfo);
    ticListInfo.setSearchCriteria(ticList);

    try {
      db = this.getConnection(context);

      if ("unassigned".equals(ticListInfo.getListView())) {
        ticList.setUnassignedToo(true);
        ticList.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
      } else if ("assignedToMe".equals(ticListInfo.getListView())) {
        ticList.setAssignedTo(getUserId(context));
        ticList.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
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
    context.getSession().setAttribute("searchTickets","yes");
    addModuleBean(context, "SearchTickets", "Search Tickets");
    return ("ResultsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      return (executeCommandDetails(context));
    } else if (resultCount == 1) {
      return (executeCommandDetails(context));
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    boolean contactRecordInserted = false;

    Contact nc = null;
    Ticket newTicket = null;

    String newContact = context.getRequest().getParameter("contact");

    Ticket newTic = (Ticket) context.getFormBean();
    newTic.setEnteredBy(getUserId(context));
    newTic.setModifiedBy(getUserId(context));
    if (newTic.getAssignedTo() > -1 && newTic.getAssignedDate() == null){
      newTic.setAssignedDate(new java.sql.Timestamp(System.currentTimeMillis()));
    }

    if (newContact != null && newContact.equals("on")) {
      //If there are any changes here, also check AccountTickets where a new contact is created
      nc = new Contact();
      nc.setNameFirst(context.getRequest().getParameter("thisContact_nameFirst"));
      nc.setNameLast(context.getRequest().getParameter("thisContact_nameLast"));
      nc.setTitle(context.getRequest().getParameter("thisContact_title"));
      nc.setRequestItems(context.getRequest());
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
        contactRecordInserted = nc.insert(db);
        if (contactRecordInserted) {
          newTic.setContactId(nc.getId());
        } else {
          processErrors(context, nc.getErrors());
        }

        if (contactRecordInserted) {
          recordInserted = newTic.insert(db);
        }
      } else {
        recordInserted = newTic.insert(db);
      }

      if (recordInserted) {
        //Prepare the ticket for the response
        newTicket = new Ticket(db, newTic.getId());
        context.getRequest().setAttribute("TicketDetails", newTicket);

        if (newTicket.getProductId() != -1){
          ProductCatalog product = new ProductCatalog(db, newTicket.getProductId());
          context.getRequest().setAttribute("product", product);
        }
        
        // check wether of not the customer product id exists
        if (newTicket.getCustomerProductId() != -1){
          CustomerProduct customerProduct = new CustomerProduct(db, newTicket.getCustomerProductId());
          customerProduct.buildFileList(db);
          context.getRequest().setAttribute("customerProduct", customerProduct);
        }

        addRecentItem(context, newTicket);

        processInsertHook(context, newTic);
      } else {
        processErrors(context, newTic.getErrors());
        if (newTic.getOrgId() != -1) {
          Organization thisOrg = new Organization(db, newTic.getOrgId());
          newTic.setCompanyName(thisOrg.getName());
        }
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "ViewTickets", "Ticket Insert ok");
      if (recordInserted) {
        if (context.getRequest().getParameter("actionSource") != null) {
          return "InsertTicketOK";
        }
        return ("InsertOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Prepares supplemental form data that a user can search by
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandSearchTicketsForm(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Prepare ticket state form data
    HtmlSelect ticketTypeSelect = new HtmlSelect();
    ticketTypeSelect.addItem("0", "-- Any --");
    ticketTypeSelect.addItem("1", "Open Only");
    ticketTypeSelect.addItem("2", "Closed Only");
    ticketTypeSelect.build();
    context.getRequest().setAttribute("TicketTypeSelect", ticketTypeSelect);
    PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");
    try {
      db = this.getConnection(context);
      //Prepare severity list form data
      LookupList severityList = new LookupList(db, "ticket_severity");
      severityList.addItem(0, "-- Any --");
      context.getRequest().setAttribute("SeverityList", severityList);

      //Prepare priority list form data
      LookupList priorityList = new LookupList(db, "ticket_priority");
      priorityList.addItem(0, "-- Any --");
      context.getRequest().setAttribute("PriorityList", priorityList);
      addModuleBean(context, "SearchTickets", "Tickets Search");

      //check if account/owner is already selected, if so build it
      if (!"".equals(ticListInfo.getSearchOptionValue("searchcodeOrgId")) && !"-1".equals(ticListInfo.getSearchOptionValue("searchcodeOrgId"))) {
        String orgId = ticListInfo.getSearchOptionValue("searchcodeOrgId");
        Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrg);
      }

      return ("SearchTicketsFormOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean smartCommentsResult = true;
    Ticket newTic = (Ticket) context.getFormBean();
    if (newTic.getAssignedTo() > -1 && newTic.getAssignedDate() == null){
      newTic.setAssignedDate(new java.sql.Timestamp(System.currentTimeMillis()));
    }
    try {
      db = this.getConnection(context);
      for (catCount = 0; catCount < 4; catCount++) {
        if ((context.getRequest().getParameter("newCat" + catCount + "chk") != null && context.getRequest().getParameter("newCat" + catCount + "chk").equals("on") && context.getRequest().getParameter("newCat" + catCount) != null && !(context.getRequest().getParameter("newCat" + catCount).equals("")))) {
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
          thisCat.setDescription(context.getRequest().getParameter("newCat" + catCount));
          thisCat.setCategoryLevel(catCount);
          thisCat.setLevel(catCount);
          catInserted = thisCat.insert(db);
          if (catInserted == true) {
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
      newTic.setModifiedBy(getUserId(context));
      //Get the previousTicket, update the ticket, then send both to a hook
      Ticket previousTicket = new Ticket(db, newTic.getId());
 
      resultCount = newTic.update(db);
      if (resultCount == 1) {
        processUpdateHook(context, previousTicket, newTic);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      processErrors(context, newTic.getErrors());
    }
    if (resultCount == -1) {
      return (executeCommandModify(context));
    } else if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
        return (executeCommandHome(context));
      } else {
        return ("UpdateOK");
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
    if (!(hasPermission(context, "tickets-tickets-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    Ticket ticket = null;
    String id = context.getRequest().getParameter("id");
    Connection db = null;
    try {
      db = this.getConnection(context);
      ticket = new Ticket(db, Integer.parseInt(id));
      DependencyList dependencies = ticket.processDependencies(db);
      htmlDialog.setTitle("Dark Horse CRM: Confirm Delete");
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='TroubleTickets.do?command=Delete&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      } else if (dependencies.canDelete()) {
        htmlDialog.addMessage(dependencies.getHtmlString());
        htmlDialog.setHeader("This object has the following dependencies within Dark Horse CRM:");

        String returnType = (String) context.getRequest().getParameter("return");
        if ("searchResults".equals(returnType)) {
          htmlDialog.addButton("Delete All", "javascript:window.location.href='TroubleTickets.do?command=Delete&id=" + id + "&return=searchResults" + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
        } else {
          htmlDialog.addButton("Delete All", "javascript:window.location.href='TroubleTickets.do?command=Delete&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
        }
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
      } else {
        htmlDialog.setHeader("This ticket cannot be deleted because it is associated with an activities form.");
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
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


  /**
   *  Deletes the specified ticket and triggers any hooks
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
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
      recordDeleted = thisTic.delete(db, this.getPath(context, "tickets"));
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
        deleteRecentItem(context, thisTic);

        String returnType = (String) context.getRequest().getParameter("return");
        if ("searchResults".equals(returnType)) {
          context.getRequest().setAttribute("refreshUrl", "TroubleTickets.do?command=SearchTickets" + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
        } else {
          context.getRequest().setAttribute("refreshUrl", "TroubleTickets.do?command=Home" + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
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
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@param  newTic            Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  protected void buildFormElements(ActionContext context, Connection db, Ticket newTic) throws SQLException {
    LookupList departmentList = new LookupList(db, "lookup_department");
    departmentList.addItem(0, "-- None --");
    departmentList.setJsEvent("onChange=\"javascript:updateUserList();\"");
    context.getRequest().setAttribute("DepartmentList", departmentList);

    LookupList severityList = new LookupList(db, "ticket_severity");
    context.getRequest().setAttribute("SeverityList", severityList);

    LookupList priorityList = new LookupList(db, "ticket_priority");
    context.getRequest().setAttribute("PriorityList", priorityList);

    LookupList sourceList = new LookupList(db, "lookup_ticketsource");
    sourceList.addItem(0, "-- None --");
    context.getRequest().setAttribute("SourceList", sourceList);

    ContactList contactList = new ContactList();
    if (newTic != null && newTic.getOrgId() != -1) {
      contactList.setBuildDetails(false);
      contactList.setBuildTypes(false);
      contactList.setOrgId(newTic.getOrgId());
      contactList.buildList(db);
    }
    context.getRequest().setAttribute("ContactList", contactList);

    UserList userList = new UserList();
    userList.setEmptyHtmlSelectRecord("-- None --");
    userList.setBuildContact(true);
    userList.setBuildContactDetails(false);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setRoleType(Constants.ROLETYPE_REGULAR);
    if (newTic.getDepartmentCode() > 0) {
      userList.setDepartment(newTic.getDepartmentCode());
      userList.buildList(db);
    }
    context.getRequest().setAttribute("UserList", userList);

    TicketCategoryList categoryList = new TicketCategoryList();
    categoryList.setCatLevel(0);
    categoryList.setParentCode(0);
    categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
    categoryList.buildList(db);
    categoryList.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("CategoryList", categoryList);

    TicketCategoryList subList1 = new TicketCategoryList();
    subList1.setCatLevel(1);
    subList1.setParentCode(newTic.getCatCode());
    subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
    subList1.buildList(db);
    subList1.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("SubList1", subList1);

    TicketCategoryList subList2 = new TicketCategoryList();
    subList2.setCatLevel(2);
    if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == 1) {
      subList2.setParentCode(0);
      newTic.setSubCat1(0);
      newTic.setSubCat2(0);
      newTic.setSubCat3(0);
    } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == -1) {
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
    if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 2)) {
      subList3.setParentCode(0);
      newTic.setSubCat2(0);
      newTic.setSubCat3(0);
    } else if (context.getRequest().getParameter("refresh") != null && Integer.parseInt(context.getRequest().getParameter("refresh")) == -1) {
      subList3.setParentCode(newTic.getSubCat2());
      subList3.getCatListSelect().setDefaultKey(newTic.getSubCat3());
    } else {
      subList3.setParentCode(newTic.getSubCat2());
    }
    subList3.buildList(db);
    subList3.getCatListSelect().addItem(0, "Undetermined");
    context.getRequest().setAttribute("SubList3", subList3);

    if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 3)) {
      newTic.setSubCat3(0);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    Connection db = null;
    try {
      String catCode = context.getRequest().getParameter("catCode");
      String subCat1 = context.getRequest().getParameter("subCat1");
      String subCat2 = context.getRequest().getParameter("subCat2");

      db = this.getConnection(context);
      if (catCode != null) {
        TicketCategoryList subList1 = new TicketCategoryList();
        subList1.setCatLevel(1);
        subList1.setParentCode(Integer.parseInt(catCode));
        subList1.buildList(db);
        context.getRequest().setAttribute("SubList1", subList1);
      } else if (subCat1 != null) {
        TicketCategoryList subList2 = new TicketCategoryList();
        subList2.setCatLevel(2);
        subList2.setParentCode(Integer.parseInt(subCat1));
        subList2.buildList(db);
        context.getRequest().setAttribute("SubList2", subList2);
      } else if (subCat2 != null) {
        TicketCategoryList subList3 = new TicketCategoryList();
        subList3.setCatLevel(3);
        subList3.setParentCode(Integer.parseInt(subCat2));
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDepartmentJSList(ActionContext context) {
    Connection db = null;
    try {
      String departmentCode = context.getRequest().getParameter("departmentCode");
      db = this.getConnection(context);
      UserList userList = new UserList();
      userList.setEnabled(UserList.TRUE);
      userList.setEmptyHtmlSelectRecord("-- None --");
      if ((departmentCode != null) && (!"0".equals(departmentCode))) {
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      map.put("ticketid",new Integer(id));
      String reportPath = getWebInfPath(context, "reports");
      map.put("path",reportPath);
      String filename = "ticket.xml";
      byte[] bytes = JasperReportUtils.getReportAsBytes(reportPath + filename, map, db);
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

