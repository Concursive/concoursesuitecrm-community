package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import java.text.*;
import java.io.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;

/**
 *  Actions for the Accounts module
 *
 *@author     chris
 *@created    August 15, 2001
 *@version    $Id$
 */
public final class Accounts extends CFSModule {

  /**
   *  Default: not used
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return ("IncludeOK");
  }


  /**
   *  Reports: Displays a list of previously generated reports with
   *  view/delete/download options.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandReports(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-reports-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    FileItemList files = new FileItemList();
    //want to show accounts reports only
    files.setLinkModuleId(Constants.ACCOUNTS_REPORTS);
    files.setLinkItemId(-1);

    PagedListInfo rptListInfo = this.getPagedListInfo(context, "RptListInfo");
    rptListInfo.setLink("/Accounts.do?command=Reports");

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
      addModuleBean(context, "Reports", "ViewReports");
      context.getRequest().setAttribute("FileList", files);
      return ("ReportsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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

    if (!(hasPermission(context, "accounts-accounts-reports-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    FileItem thisItem = null;

    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId), -1);
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
      String filePath = this.getPath(context, "account-reports") + getDatePath(itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";

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
   *  ShowReportHtml: Displays a preview of the selected report in HTML format
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandShowReportHtml(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-reports-view"))) {
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

      String filePath = this.getPath(context, "account-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".html";
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
   *  GenerateForm: Displays the form that allows the user to select criteria
   *  and specify information for a new Accounts report
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandGenerateForm(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-reports-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    CustomFieldCategoryList thisList = new CustomFieldCategoryList();
    thisList.setLinkModuleId(Constants.ACCOUNTS);
    thisList.setIncludeEnabled(Constants.TRUE);
    thisList.setIncludeScheduled(Constants.TRUE);
    thisList.setAllSelectOption(true);
    thisList.setBuildResources(false);

    try {
      db = getConnection(context);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Reports", "Generate new");
    return ("GenerateFormOK");
  }


  /**
   *  ExportReport: Creates both an HTML version (for preview) and a CSV version
   *  of the report
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandExportReport(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-reports-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;

    String subject = context.getRequest().getParameter("subject");
    String type = context.getRequest().getParameter("type");
    String ownerCriteria = context.getRequest().getParameter("criteria1");

    String filePath = this.getPath(context, "account-reports");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(new java.util.Date());
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(new java.util.Date());
    filePath += datePathToUse1 + fs + datePathToUse2 + fs;

    //new
    OrganizationReport orgReport = new OrganizationReport();
    orgReport.setCriteria(context.getRequest().getParameterValues("selectedList"));
    orgReport.setFilePath(filePath);
    orgReport.setEnteredBy(getUserId(context));
    orgReport.setModifiedBy(getUserId(context));
    orgReport.setSubject(subject);
    orgReport.setMinerOnly(false);

    if (ownerCriteria.equals("my")) {
      orgReport.setOwnerId(this.getUserId(context));
    } else if (ownerCriteria.equals("levels")) {
      orgReport.setOwnerIdRange(this.getUserRange(context));
    }

    try {
      db = this.getConnection(context);

      //Accounts with opportunities report
      if (type.equals("5")) {
        OpportunityReport oppReport = new OpportunityReport();
        oppReport.setHeader("CFS Accounts");
        oppReport.setFilePath(filePath);
        oppReport.setEnteredBy(getUserId(context));
        oppReport.setModifiedBy(getUserId(context));
        oppReport.setSubject(subject);

        if (ownerCriteria.equals("my")) {
          oppReport.setLimitId(this.getUserId(context));
        } else if (ownerCriteria.equals("levels")) {
          oppReport.setAccountOwnerIdRange(this.getUserRange(context));
        }

        oppReport.getOrgReportJoin().setCriteria(context.getRequest().getParameterValues("selectedList"));

        oppReport.setJoinOrgs(true);
        oppReport.buildReportFull(db);
        oppReport.saveAndInsert(db);
      } else if (type.equals("2")) {
        ContactReport contactReport = new ContactReport();
        contactReport.setFilePath(filePath);
        contactReport.setEnteredBy(getUserId(context));
        contactReport.setModifiedBy(getUserId(context));
        contactReport.setSubject(subject);
        contactReport.setHeader("CFS Accounts");
        contactReport.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
        contactReport.setPersonalId(this.getUserId(context));

	contactReport.setCriteria(null);
        contactReport.getOrgReportJoin().setCriteria(context.getRequest().getParameterValues("selectedList"));

        if (ownerCriteria.equals("my")) {
          contactReport.setLimitId(this.getUserId(context));
        } else if (ownerCriteria.equals("levels")) {
          contactReport.setAccountOwnerIdRange(this.getUserRange(context));
        }

        contactReport.setJoinOrgs(true);
        contactReport.buildReportFull(db);
        contactReport.saveAndInsert(db);
      } else if (type.equals("3")) {
        TicketReport ticReport = new TicketReport();
        ticReport.setFilePath(filePath);
        ticReport.setEnteredBy(getUserId(context));
        ticReport.setModifiedBy(getUserId(context));
        ticReport.setSubject(subject);
        ticReport.setHeader("CFS Accounts");
        ticReport.setJoinOrgs(true);

        if (ownerCriteria.equals("my")) {
          ticReport.setLimitId(this.getUserId(context));
        } else if (ownerCriteria.equals("levels")) {
          ticReport.setAccountOwnerIdRange(this.getUserRange(context));
        }

        ticReport.getOrgReportJoin().setCriteria(context.getRequest().getParameterValues("selectedList"));

        ticReport.buildReportFull(db);
        ticReport.saveAndInsert(db);
      } else {
        int folderId = Integer.parseInt(context.getRequest().getParameter("catId"));
        if (type.equals("4") && folderId == 0) {
          orgReport.setIncludeFolders(true);
        } else if (type.equals("4") && folderId > 0) {
          orgReport.setFolderId(folderId);
        }

        orgReport.buildReportFull(db);
        orgReport.saveAndInsert(db);
      }

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
   *  DeleteReport: Deletes previously generated report files (HTML and CSV)
   *  from server and all related file information from the project_files table.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-reports-delete"))) {
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
        recordDeleted = thisItem.delete(db, this.getPath(context, "account-reports"));

        String filePath1 = this.getPath(context, "account-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".csv";
        java.io.File fileToDelete1 = new java.io.File(filePath1);
        if (!fileToDelete1.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath1);
        }

        String filePath2 = this.getPath(context, "account-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".html";
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

    addModuleBean(context, "Reports", "Reports del");

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
   *  Search: Displays the Account search form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandSearch(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-view"))) {
      return ("PermissionError");
    }

    //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
    PagedListInfo orgListInfo = this.getPagedListInfo(context, "OrgListInfo");
    orgListInfo.setCurrentLetter("");
    orgListInfo.setCurrentOffset(0);

    addModuleBean(context, "Search Accounts", "Accounts Search");
    return ("SearchOK");
  }


  /**
   *  Add: Displays the form used for adding a new Account to CFS
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-add"))) {
      return ("PermissionError");
    }

    int errorCode = 0;
    Exception errorMessage = null;

    Connection db = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      db = this.getConnection(context);

      LookupList industrySelect = new LookupList(db, "lookup_industry");
      industrySelect.addItem(0, "--None--");
      context.getRequest().setAttribute("IndustryList", industrySelect);

      LookupList phoneTypeList = new LookupList(db, "lookup_orgphone_types");
      context.getRequest().setAttribute("OrgPhoneTypeList", phoneTypeList);

      LookupList addrTypeList = new LookupList(db, "lookup_orgaddress_types");
      context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

      LookupList emailTypeList = new LookupList(db, "lookup_orgemail_types");
      context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);
      
      LookupList accountTypeList = new LookupList(db, "lookup_account_types");
      accountTypeList.setSelectSize(3);
      accountTypeList.setMultiple(true);
      context.getRequest().setAttribute("AccountTypeList", accountTypeList);

    } catch (Exception e) {
      errorCode = 1;
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorCode == 0) {
      addModuleBean(context, "Add Account", "Accounts Add");
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Details: Displays all details relating to the selected Account. The user
   *  can also goto a modify page from this form or delete the Account entirely
   *  from the database
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Organization newOrg = null;

    try {
      String temporgId = context.getRequest().getParameter("orgId");
      int tempid = Integer.parseInt(temporgId);
      db = this.getConnection(context);
      newOrg = new Organization(db, tempid);
      addRecentItem(context, newOrg);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      String action = context.getRequest().getParameter("action");
      if (action != null && action.equals("modify")) {
        //If user is going to the modify form
        addModuleBean(context, "Accounts", "Modify Account Details");
        return ("DetailsOK");
      } else {
        //If user is going to the detail screen
        addModuleBean(context, "View Accounts", "View Account Details");
        context.getRequest().setAttribute("OrgDetails", newOrg);
        return ("DetailsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Dashboard: The introductory page for the Accounts module. Displays a
   *  calendar that maps out upcoming events related to Accounts (contract end
   *  dates, alerts, etc.)
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDashboard(ActionContext context) {

    if (!(hasPermission(context, "accounts-dashboard-view"))) {
      if (!(hasPermission(context, "accounts-accounts-view"))) {
        return ("PermissionError");
      }

      return (executeCommandView(context));
    }

    addModuleBean(context, "Dashboard", "Dashboard");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    int alertsDD = getUserId(context);

    if (context.getRequest().getParameter("userId") != null) {
      alertsDD = Integer.parseInt(context.getRequest().getParameter("userId"));
    }

    //this is how we get the multiple-level heirarchy...recursive function.

    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList newUserList = thisRec.getFullChildList(shortChildList, new UserList());

    newUserList.setMyId(getUserId(context));
    newUserList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    newUserList.setIncludeMe(true);

    newUserList.setJsEvent("onChange = javascript:document.forms[0].action='/Accounts.do?command=Dashboard';document.forms[0].submit()");

    CalendarView companyCalendar = new CalendarView(context.getRequest());

    PagedListInfo orgAlertPaged = new PagedListInfo();
    orgAlertPaged.setMaxRecords(20);
    orgAlertPaged.setColumnToSortBy("alertdate");

    OrganizationList alertOrgs = new OrganizationList();
    alertOrgs.setPagedListInfo(orgAlertPaged);
    alertOrgs.setEnteredBy(alertsDD);
    alertOrgs.setHasAlertDate(true);

    OrganizationList expireOrgs = new OrganizationList();
    expireOrgs.setPagedListInfo(orgAlertPaged);
    expireOrgs.setEnteredBy(alertsDD);
    expireOrgs.setHasExpireDate(true);

    Connection db = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      alertOrgs.buildList(db);
      expireOrgs.buildList(db);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    Iterator n = alertOrgs.iterator();
    while (n.hasNext()) {
      Organization thisOrg = (Organization) n.next();
      companyCalendar.addEvent(thisOrg.getAlertDateStringLongYear(), "", thisOrg.getName() + ": " + thisOrg.getAlertText(), "Account", thisOrg.getOrgId());
    }

    Iterator m = expireOrgs.iterator();
    while (m.hasNext()) {
      Organization thatOrg = (Organization) m.next();
      companyCalendar.addEvent(thatOrg.getContractEndDateStringLongYear(), "", thatOrg.getName() + ": " + "Contract Expiration", "Account", thatOrg.getOrgId());
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
      context.getRequest().setAttribute("NewUserList", newUserList);
      return ("DashboardOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  View: Lists Accounts that are in the system. Can be limited based on
   *  criteria selection
   *
   *@param  context  Description of Paramet47hu *@return Description of the
   *      Returned Value
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandView(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo orgListInfo = this.getPagedListInfo(context, "OrgListInfo");
    orgListInfo.setLink("/Accounts.do?command=View");

    //NOTE: For any new container menu item...
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);

    Connection db = null;
    OrganizationList organizationList = new OrganizationList();
    String passedName = context.getRequest().getParameter("searchName");

    if (passedName != null && !(passedName.trim()).equals("")) {
      passedName = "%" + passedName + "%";
      organizationList.setName(passedName);
    }

    try {
      db = this.getConnection(context);
      organizationList.setPagedListInfo(orgListInfo);
      organizationList.setMinerOnly(false);

      if ("my".equals(orgListInfo.getListView())) {
        organizationList.setOwnerId(this.getUserId(context));
      }

      organizationList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OrgList", organizationList);
      addModuleBean(context, "View Accounts", "Accounts View");
      return ("ListOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  ViewTickets: Displays Ticket history (open and closed) for a particular
   *  Account.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandViewTickets(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    Connection db = null;
    TicketList ticList = new TicketList();
    Organization newOrg = null;

    int passedId = Integer.parseInt(context.getRequest().getParameter("orgId"));

    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, passedId);
      PagedListInfo ticketListInfo = this.getPagedListInfo(context, "AccountTicketInfo");
      ticketListInfo.setLink("/Accounts.do?command=ViewTickets&orgId=" + passedId);
      ticList.setPagedListInfo(ticketListInfo);
      ticList.setOrgId(passedId);
      ticList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("TicList", ticList);
      context.getRequest().setAttribute("OrgDetails", newOrg);
      addModuleBean(context, "View Accounts", "Accounts View");
      return ("ViewTicketsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Insert: Inserts a new Account into the CFS database.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;

    Organization newOrg = (Organization) context.getFormBean();
    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
    Organization insertedOrg = null;

    newOrg.setEnteredBy(getUserId(context));
    newOrg.setRequestItems(context.getRequest());
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setOwner(getUserId(context));

    try {
      db = this.getConnection(context);
      recordInserted = newOrg.insert(db);

      if (recordInserted) {
        insertedOrg = new Organization(db, newOrg.getOrgId());
        context.getRequest().setAttribute("OrgDetails", insertedOrg);
        addRecentItem(context, newOrg);
      } else {
        processErrors(context, newOrg.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Accounts Insert ok");
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
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdate(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Organization newOrg = (Organization) context.getFormBean();
    newOrg.setRequestItems(context.getRequest());
    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));

    try {
      String orgId = context.getRequest().getParameter("orgId");
      int tempid = Integer.parseInt(orgId);
      db = this.getConnection(context);

      newOrg.setModifiedBy(getUserId(context));
      resultCount = newOrg.update(db);

      if (resultCount == -1) {
        processErrors(context, newOrg.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Modify Account");
    if (errorMessage == null) {
      if (resultCount == -1) {
        return (executeCommandModify(context));
      } else if (resultCount == 1) {
	      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
		      return (executeCommandView(context));
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
   *  Delete: Deletes an Account from the Organization table
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Organization thisOrganization = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
      recordDeleted = thisOrganization.delete(db, this.getPath(context, "accounts", thisOrganization.getOrgId()));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Accounts", "Delete Account");
    if (errorMessage == null) {
      if (recordDeleted) {
	deleteRecentItem(context, thisOrganization);
        return ("DeleteOK");
      } else {
        processErrors(context, thisOrganization.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Modify: Displays the form used for modifying the information of the
   *  currently selected Account
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandModify(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    //Command errors
    int errorCode = 0;

    String orgid = context.getRequest().getParameter("orgId");
    context.getRequest().setAttribute("orgId", orgid);
    int tempid = Integer.parseInt(orgid);

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.

    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    userList.setIncludeMe(true);
    context.getRequest().setAttribute("UserList", userList);

    Connection db = null;
    Organization newOrg = null;

    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, tempid);

      LookupList industrySelect = new LookupList(db, "lookup_industry");
      industrySelect.addItem(0, "--None--");
      context.getRequest().setAttribute("IndustryList", industrySelect);

      LookupList phoneTypeList = new LookupList(db, "lookup_orgphone_types");
      context.getRequest().setAttribute("OrgPhoneTypeList", phoneTypeList);

      LookupList addrTypeList = new LookupList(db, "lookup_orgaddress_types");
      context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

      LookupList emailTypeList = new LookupList(db, "lookup_orgemail_types");
      context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);
      
      LookupList accountTypeList = new LookupList(db, "lookup_account_types");
      accountTypeList.setSelectSize(3);
      accountTypeList.setMultiple(true);
      context.getRequest().setAttribute("AccountTypeList", accountTypeList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Account Modify");
      context.getRequest().setAttribute("OrgDetails", newOrg);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Fields: Shows a list of custom field records that are located "within" the
   *  selected Custom Folder. Also shows the details of a particular Custom
   *  Field Record when it is selected (details page)
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandFields(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-folders-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;
    String recordId = null;
    boolean showRecords = true;

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.ACCOUNTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      if (selectedCatId == null) {
        selectedCatId = (String) context.getRequest().getAttribute("catId");
      }
      if (selectedCatId == null) {
        selectedCatId = "" + thisList.getDefaultCategoryId();
      }
      context.getRequest().setAttribute("catId", selectedCatId);

      recordId = context.getRequest().getParameter("recId");
      String recordDeleted = (String)context.getRequest().getAttribute("recordDeleted");
      if (recordDeleted != null) {
        recordId = null;
      }

      CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
      if (recordId == null && thisCategory.getAllowMultipleRecords()) {
        //The user didn't request a specific record, so show a list
        //of records matching this category that the user can choose from
        PagedListInfo folderListInfo = this.getPagedListInfo(context, "AccountFolderInfo");
        folderListInfo.setLink("/Accounts.do?command=Fields&orgId=" + orgId + "&catId=" + selectedCatId);
     
        CustomFieldRecordList recordList = new CustomFieldRecordList();
        recordList.setLinkModuleId(Constants.ACCOUNTS);
        recordList.setLinkItemId(thisOrganization.getOrgId());
        recordList.setCategoryId(thisCategory.getId());
        recordList.buildList(db);
        recordList.buildRecordColumns(db, thisCategory);
        context.getRequest().setAttribute("Records", recordList);
      } else {
        //The user requested a specific record, or this category only
        //allows a single record.
        thisCategory.setLinkModuleId(Constants.ACCOUNTS);
        thisCategory.setLinkItemId(thisOrganization.getOrgId());
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

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Custom Fields Details");
      if (recordId == null && showRecords) {
        return ("FieldRecordListOK");
      } else {
        return ("FieldsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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

    if (!(hasPermission(context, "accounts-accounts-folders-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.ACCOUNTS);
      thisCategory.setLinkItemId(thisOrganization.getOrgId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Add Folder Record");
      return ("AddFolderRecordOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  ModifyFields: Displays the modify form for the selected Custom Field
   *  Record.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandModifyFields(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-folders-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;
    
    String selectedCatId = (String) context.getRequest().getParameter("catId");
    String recordId = (String) context.getRequest().getParameter("recId");

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.ACCOUNTS);
      thisCategory.setLinkItemId(thisOrganization.getOrgId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Modify Custom Fields");
      if (recordId.equals("-1")) {
        return ("AddFolderRecordOK"); 
      } else {
        return ("ModifyFieldsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
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

    if (!(hasPermission(context, "accounts-accounts-folders-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;
    int resultCount = 0;

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.ACCOUNTS);
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
      thisCategory.setLinkModuleId(Constants.ACCOUNTS);
      thisCategory.setLinkItemId(thisOrganization.getOrgId());
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
          System.out.println("Accounts-> ModifyField validation error");
        }
      } else {
        thisCategory.buildResources(db);
        CustomFieldRecord thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
        context.getRequest().setAttribute("Record", thisRecord);
      }
      context.getRequest().setAttribute("Category", thisCategory);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("ModifyFieldsOK");
      } else if (resultCount == 1) {
        return ("UpdateFieldsOK");
      } else {
        context.getRequest().setAttribute("Error", CFSModule.NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  InsertFields: Performs the actual insert of a new Custom Field Record.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandInsertFields(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-folders-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCode = -1;
    Organization thisOrganization = null;

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.ACCOUNTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.ACCOUNTS);
      thisCategory.setLinkItemId(thisOrganization.getOrgId());
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
          System.out.println("Accounts-> InsertField validation error");
        }
      } else {
        if (this.getDbName(context).equals("cdb_matt") &&
            thisCategory.hasField(4)) {
          Template template = new Template();
          template.setText(includeFile(this.getPath(context, "triggers") + "new_folder_item.template"));
          template.addParseElement("$instructions", thisCategory.getFieldValue(4));
          template.setValueEncoding(Template.XMLEncoding);
          Notification thisNotification = new Notification(Notification.SSL);
          thisNotification.setHost("127.0.0.1");
          thisNotification.setPort(44444);
          thisNotification.setMessageToSend(template.getParsedText());
          thisNotification.send(context);
        } else if (this.getDbName(context).equals("cdb_vport") &&
            thisCategory.hasField(11)) {
          Template template = new Template();
          template.setText(includeFile(this.getPath(context, "triggers") + "new_folder_item.template"));
          template.addParseElement("$instructions", thisCategory.getFieldValue(11));
          template.setValueEncoding(Template.XMLEncoding);
          Notification thisNotification = new Notification(Notification.SSL);
          thisNotification.setHost("151.204.140.140");
          thisNotification.setPort(44444);
          thisNotification.setMessageToSend(template.getParsedText());
          thisNotification.send(context);
        }
      }
      context.getRequest().setAttribute("Category", thisCategory);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCode == -1) {
        return ("AddFolderRecordOK");
      } else {
        return (this.executeCommandFields(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  public String executeCommandDeleteFields(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-folders-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    boolean recordDeleted = false;

    try {
      db = this.getConnection(context);
      String selectedCatId = context.getRequest().getParameter("catId");
      String recordId = context.getRequest().getParameter("recId");
      String orgId = context.getRequest().getParameter("orgId");
      
      CustomFieldCategory thisCategory = new CustomFieldCategory(db,
          Integer.parseInt(selectedCatId));
      
      CustomFieldRecord thisRecord = new CustomFieldRecord(db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.ACCOUNTS);
      thisRecord.setLinkItemId(Integer.parseInt(orgId));
      thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
      if (!thisCategory.getReadOnly()) {
        recordDeleted = thisRecord.delete(db);
      }
      context.getRequest().setAttribute("recordDeleted", "true");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
      
    if (errorMessage == null) {
      return ("DeleteFieldsOK");
    } else {
      addModuleBean(context, "Accounts", "Delete Folder Record");
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  private void resetPagedListInfo(ActionContext context) {
    this.deletePagedListInfo(context, "ContactListInfo");
    this.deletePagedListInfo(context, "AccountFolderInfo");
    this.deletePagedListInfo(context, "RptListInfo");
    this.deletePagedListInfo(context, "OpportunityPagedInfo");
    this.deletePagedListInfo(context, "AccountTicketInfo");
    this.deletePagedListInfo(context, "AutoGuideAccountInfo");
    this.deletePagedListInfo(context, "RevenueListInfo");
    this.deletePagedListInfo(context, "AccountDocumentInfo");
  }
}

