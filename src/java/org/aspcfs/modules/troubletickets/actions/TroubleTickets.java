package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import java.text.*;

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

    if (!(hasPermission(context, "tickets-tickets-add"))) {
      return ("PermissionError");
    }

    int errorCode = 0;
    Exception errorMessage = null;
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
      errorCode = 1;
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorCode == 0) {
      addModuleBean(context, "AddTicket", "Ticket Add");
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      context.getRequest().setAttribute("ThisContact", newTic.getThisContact());
      return ("SystemError");
    }

  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandGenerateForm(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-reports-add"))) {
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

    if (!(hasPermission(context, "tickets-tickets-reports-add"))) {
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
    thisInfo.setItemsPerPage(50);
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

      //builds list also
      ticketReport.buildReportFull(db);
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

    if (!(hasPermission(context, "tickets-tickets-reports-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;

    try {
      db = getConnection(context);

      //-1 is the project ID for non-projects
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), -1);

      String filePath = this.getPath(context, "ticket-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = this.includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception e) {
      errorMessage = e;
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

    if (!(hasPermission(context, "tickets-tickets-reports-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    FileItemList files = new FileItemList();
    files.setLinkModuleId(Constants.DOCUMENTS_TICKETS_REPORTS);
    files.setLinkItemId(-1);

    PagedListInfo rptListInfo = this.getPagedListInfo(context, "TicketRptListInfo");
    rptListInfo.setLink("/TroubleTickets.do?command=Reports");

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
        Contact enteredBy = this.getUser(context, thisItem.getEnteredBy()).getContact();
        Contact modifiedBy = this.getUser(context, thisItem.getModifiedBy()).getContact();
        thisItem.setEnteredByString(enteredBy.getNameFirstLast());
        thisItem.setModifiedByString(modifiedBy.getNameFirstLast());
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "Reports", "TicketReports");
      context.getRequest().setAttribute("FileList", files);
      return ("ReportsOK");
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
  public String executeCommandDeleteReport(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-reports-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);

      //-1 is the project ID for non-projects
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), -1);

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
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Reports", "Reports delete");

    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteReportOK");
      } else {
        return ("DeleteReportERROR");
      }
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

    if (!(hasPermission(context, "tickets-tickets-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
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
      //departmentList.setJsEvent("onChange = javascript:document.forms[0].action='/TroubleTickets.do?command=Modify&passedid=" + newTic.getId() + "&auto-populate=true#department';document.forms[0].submit()");
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
      //categoryList.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/TroubleTickets.do?command=Modify&passedid=" + newTic.getId() + "&auto-populate=true&refresh=1#categories';document.forms[0].submit()");
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      context.getRequest().setAttribute("CategoryList", categoryList);

      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord("-- None --");
      userList.setBuildContact(true);
      userList.setDepartment(newTic.getDepartmentCode());
      userList.setExcludeDisabledIfUnselected(true);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);

      TicketCategoryList subList1 = new TicketCategoryList();

      subList1.setCatLevel(1);
      subList1.setParentCode(newTic.getCatCode());
      //subList1.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/TroubleTickets.do?command=Modify&passedid=" + newTic.getId() + "&auto-populate=true&refresh=2#categories';document.forms[0].submit()");
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      context.getRequest().setAttribute("SubList1", subList1);

      ContactList contactList = new ContactList();
      contactList.setEmptyHtmlSelectRecord("-- None --");
      contactList.setPersonalId(getUserId(context));
      //contactList.setTypeId(Integer.parseInt(typeId));
      contactList.setBuildDetails(false);
      contactList.setOrgId(newTic.getOrgId());

      if (newTic.getOrgId() == -1) {
        contactList.setShowEmployeeContacts(true);
      }

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

      //subList2.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/TroubleTickets.do?command=Modify&passedid=" + newTic.getId() + "&auto-populate=true&refresh=3#categories';document.forms[0].submit()");
      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.buildList(db);
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
      context.getRequest().setAttribute("SubList3", subList3);

      if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 3)) {
        newTic.setSubCat3(0);
      }

      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("TicketDetails", newTic);
      addModuleBean(context, "ViewTickets", "View Tickets");
      return ("ModifyOK");
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
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Ticket newTic = null;
    String ticketId = null;

    PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicketDetails");
    ticListInfo.setColumnToSortBy("entered");

    try {
      ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      newTic = new Ticket(db, Integer.parseInt(ticketId));

      //check whether or not the owner is an active User
      if (newTic.getAssignedTo() > -1) {
        newTic.checkEnabledOwnerAccount(db);
      }

      newTic.getHistory().setPagedListInfo(ticListInfo);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Tickets", "Ticket Details");
    if (errorMessage == null) {
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);
      addModuleBean(context, "ViewTickets", "View Tickets");
      return ("DetailsOK");
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
  public String executeCommandHome(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }

    int errorCode = 0;
    Exception errorMessage = null;
    Connection db = null;

    TicketList assignedToMeList = new TicketList();
    TicketList openList = new TicketList();
    TicketList createdByMeList = new TicketList();
    String sectionId = null;

    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }

    //reset the paged lists
    if (context.getRequest().getParameter("resetList") != null && context.getRequest().getParameter("resetList").equals("true")) {
      context.getSession().removeAttribute("AssignedToMeInfo");
      context.getSession().removeAttribute("OpenInfo");
      context.getSession().removeAttribute("CreatedByMeInfo");
    }

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    PagedListInfo assignedToMeInfo = this.getPagedListInfo(context, "AssignedToMeInfo");
    assignedToMeInfo.setLink("/TroubleTickets.do?command=Home");

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

    PagedListInfo openInfo = this.getPagedListInfo(context, "OpenInfo");
    openInfo.setLink("/TroubleTickets.do?command=Home");

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
      openList.setOnlyOpen(true);

      if ("unassigned".equals(openInfo.getListView())) {
        openList.setUnassignedToo(true);
        openList.setDepartment(thisUser.getUserRecord().getContact().getDepartment());
      }
    }

    PagedListInfo createdByMeInfo = this.getPagedListInfo(context, "CreatedByMeInfo");
    createdByMeInfo.setLink("/TroubleTickets.do?command=Home");

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
    } catch (Exception e) {
      errorCode = 1;
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "ViewTickets", "View Tickets");
    context.getRequest().setAttribute("CreatedByMeList", createdByMeList);
    context.getRequest().setAttribute("AssignedToMeList", assignedToMeList);
    context.getRequest().setAttribute("OpenList", openList);

    if (errorCode == 0) {
      addModuleBean(context, "ViewTickets", "View Tickets");
      return ("HomeOK");
    } else {
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearchTickets(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }

    PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");

    if (ticListInfo.getSavedCriteria().isEmpty()) {
      return (executeCommandSearchTicketsForm(context));
    }

    if (context.getRequest().getParameter("reset") != null) {
      if (context.getRequest().getParameter("reset").equals("true")) {
        ticListInfo.getSavedCriteria().clear();
        return (executeCommandSearchTicketsForm(context));
      }
    }

    int errorCode = 0;
    Exception errorMessage = null;
    Connection db = null;

    TicketList ticList = new TicketList();
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    ticListInfo.setLink("/TroubleTickets.do?command=SearchTickets");
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

      ticList.buildList(db);

    } catch (Exception e) {
      errorCode = 1;
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "SearchTickets", "Search Tickets");
    context.getRequest().setAttribute("TicList", ticList);

    if (errorCode == 0) {
      addModuleBean(context, "SearchTickets", "Search Tickets");
      return ("ResultsOK");
    } else {
      return ("SystemError");
    }
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
    Exception errorMessage = null;
    Connection db = null;
    Ticket thisTicket = null;

    try {
      db = this.getConnection(context);
      thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return (executeCommandDetails(context));
      } else if (resultCount == 1) {
        return (executeCommandDetails(context));
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
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

    if (newContact != null && newContact.equals("on")) {
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
        nc.addType(Contact.EMPLOYEE_TYPE);
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
        addRecentItem(context, newTicket);

        processInsertHook(context, newTic);
      } else {
        processErrors(context, newTic.getErrors());
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "ViewTickets", "Ticket Insert ok");
      if (recordInserted) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandSearchTicketsForm(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-view"))) {
      return ("PermissionError");
    }

    int errorCode = 0;
    Exception errorMessage = null;
    Connection db = null;

    HtmlSelect ticketTypeSelect = new HtmlSelect();
    ticketTypeSelect.setSelectName("type");
    ticketTypeSelect.addItem("0", "-- Any --");
    ticketTypeSelect.addItem("1", "Open Only");
    ticketTypeSelect.addItem("2", "Closed Only");
    ticketTypeSelect.build();
    context.getRequest().setAttribute("TicketTypeSelect", ticketTypeSelect);

    try {
      db = this.getConnection(context);

      OrganizationList orgList = new OrganizationList();
      orgList.setMinerOnly(false);
      orgList.setShowMyCompany(true);
      orgList.buildList(db);
      context.getRequest().setAttribute("OrgList", orgList);

      LookupList severityList = new LookupList(db, "ticket_severity");
      severityList.addItem(0, "-- Any --");
      context.getRequest().setAttribute("SeverityList", severityList);

      LookupList priorityList = new LookupList(db, "ticket_priority");
      priorityList.addItem(0, "-- Any --");
      context.getRequest().setAttribute("PriorityList", priorityList);
    } catch (Exception e) {
      errorCode = 1;
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorCode == 0) {
      addModuleBean(context, "SearchTickets", "Tickets Search");
      return ("SearchTicketsFormOK");
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
  public String executeCommandUpdate(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean smartCommentsResult = true;

    Ticket newTic = (Ticket) context.getFormBean();

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
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == -1) {
      processErrors(context, newTic.getErrors());
    }

    if (errorMessage == null) {
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
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    String passedId = null;
    Ticket thisTic = null;
    Connection db = null;

    passedId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      thisTic = new Ticket(db, Integer.parseInt(passedId));
      recordDeleted = thisTic.delete(db);
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        deleteRecentItem(context, thisTic);
        return ("DeleteOK");
      } else {
        return (executeCommandHome(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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
      contactList.setPersonalId(getUserId(context));
      contactList.setOrgId(newTic.getOrgId());
      contactList.buildList(db);
    }
    context.getRequest().setAttribute("ContactList", contactList);

    UserList userList = new UserList();
    userList.setEmptyHtmlSelectRecord("-- None --");
    userList.setBuildContact(true);
    userList.setExcludeDisabledIfUnselected(true);

    if (newTic.getDepartmentCode() > 0) {
      userList.setDepartment(newTic.getDepartmentCode());
      userList.buildList(db);
    }
    context.getRequest().setAttribute("UserList", userList);

    OrganizationList orgList = new OrganizationList();
    orgList.setMinerOnly(false);
    //orgList.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/TroubleTickets.do?command=Add&auto-populate=true';document.forms[0].submit()");
    orgList.setHtmlJsEvent("onChange=\"javascript:updateContactList();\"");
    orgList.setShowMyCompany(true);
    orgList.buildList(db);
    context.getRequest().setAttribute("OrgList", orgList);

    TicketCategoryList categoryList = new TicketCategoryList();
    categoryList.setCatLevel(0);
    categoryList.setParentCode(0);
    categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
    categoryList.buildList(db);
    context.getRequest().setAttribute("CategoryList", categoryList);

    TicketCategoryList subList1 = new TicketCategoryList();
    subList1.setCatLevel(1);
    subList1.setParentCode(newTic.getCatCode());
    subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
    subList1.buildList(db);
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
    Exception errorMessage = null;
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
    } catch (SQLException e) {
      errorMessage = e;
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
    Exception errorMessage = null;
    Connection db = null;
    try {
      String departmentCode = context.getRequest().getParameter("departmentCode");
      db = this.getConnection(context);
      UserList userList = new UserList();
      userList.setEnabled(UserList.TRUE);
      userList.setEmptyHtmlSelectRecord("-- None --");
      if ((departmentCode != null) && (!"0".equals(departmentCode))) {
        userList.setBuildContact(true);
        userList.setDepartment(Integer.parseInt(departmentCode));
        userList.buildList(db);
      }
      context.getRequest().setAttribute("UserList", userList);
    } catch (SQLException e) {
      errorMessage = e;
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
    Exception errorMessage = null;
    Connection db = null;
    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);

      ContactList contactList = new ContactList();
      if (orgId != null && !"-1".equals(orgId)) {
        contactList.setBuildDetails(false);
        contactList.setPersonalId(getUserId(context));
        contactList.setOrgId(Integer.parseInt(orgId));
        contactList.buildList(db);
      }
      context.getRequest().setAttribute("ContactList", contactList);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("OrganizationJSListOK");
  }
}

