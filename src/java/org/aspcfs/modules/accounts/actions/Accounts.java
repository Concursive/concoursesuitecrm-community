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
 *  Description of the Class
 *
 *@author     chris
 *@created    August 15, 2001
 *@version    $Id$
 */
public final class Accounts extends CFSModule {

  /**
   *  Description of the Method
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandReports(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    FileItemList files = new FileItemList();
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDownloadCSVReport(ActionContext context) {
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
        System.err.println("PMF-> Trying to send a file that does not exist");
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
  public String executeCommandShowReportHtml(ActionContext context) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandGenerateForm(ActionContext context) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandExportReport(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;

    String subject = context.getRequest().getParameter("subject");
    String type = context.getRequest().getParameter("type");
    String ownerCriteria = context.getRequest().getParameter("criteria1");
    int folderId = Integer.parseInt(context.getRequest().getParameter("catId"));

    //String tdNumStart = "valign='top' align='right' bgcolor='#FFFFFF' nowrap";

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
        }  else if (ownerCriteria.equals("levels")) {
	  	oppReport.setAccountOwnerIdRange(this.getUserRange(context));
	}

        oppReport.getOrgReportJoin().setCriteria(context.getRequest().getParameterValues("selectedList"));

	/**
        if (ownerCriteria.equals("my")) {
          oppReport.setOwner(this.getUserId(context));
        } else if (ownerCriteria.equals("all")) {
          oppReport.setOwnerIdRange(this.getUserRange(context));
        }
	*/

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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandSearch(ActionContext context) {
    PagedListInfo orgListInfo = this.getPagedListInfo(context, "OrgListInfo");
    orgListInfo.setCurrentLetter("");
    orgListInfo.setCurrentOffset(0);

    addModuleBean(context, "Search Accounts", "Accounts Search");
    return ("SearchOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAdd(ActionContext context) {
    int errorCode = 0;
    Exception errorMessage = null;

    Connection db = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      db = this.getConnection(context);

      LookupList industrySelect = new LookupList(db, "lookup_industry");
      context.getRequest().setAttribute("IndustryList", industrySelect);

      LookupList phoneTypeList = new LookupList(db, "lookup_orgphone_types");
      context.getRequest().setAttribute("OrgPhoneTypeList", phoneTypeList);

      LookupList addrTypeList = new LookupList(db, "lookup_orgaddress_types");
      context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

      LookupList emailTypeList = new LookupList(db, "lookup_orgemail_types");
      context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);

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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetails(ActionContext context) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDashboard(ActionContext context) {
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
   *  Description of the Method
   *
   *@param  context  Description of Paramet47hu *@return Description of the
   *      Returned Value
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandView(ActionContext context) {
    Exception errorMessage = null;

    PagedListInfo orgListInfo = this.getPagedListInfo(context, "OrgListInfo");
    orgListInfo.setLink("/Accounts.do?command=View");

    //Need to reset the contact list PagedListInfo since this is a new account
    this.deletePagedListInfo(context, "ContactListInfo");

    Connection db = null;
    OrganizationList organizationList = new OrganizationList();
    String passedName = context.getRequest().getParameter("name");

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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandViewTickets(ActionContext context) {
    Exception errorMessage = null;

    Connection db = null;
    TicketList ticList = new TicketList();
    Organization newOrg = null;

    int passedId = Integer.parseInt(context.getRequest().getParameter("orgId"));

    try {
      db = this.getConnection(context);
      //ticList.setPagedListInfo(orgListInfo);
      ticList.setOrgId(passedId);
      ticList.buildList(db);
      newOrg = new Organization(db, passedId);
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;

    Organization newOrg = (Organization) context.getFormBean();
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Organization newOrg = (Organization) context.getFormBean();
    newOrg.setRequestItems(context.getRequest());

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
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
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
   *@since
   */
  public String executeCommandDelete(ActionContext context) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandModify(ActionContext context) {

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
      context.getRequest().setAttribute("IndustryList", industrySelect);

      LookupList phoneTypeList = new LookupList(db, "lookup_orgphone_types");
      context.getRequest().setAttribute("OrgPhoneTypeList", phoneTypeList);

      LookupList addrTypeList = new LookupList(db, "lookup_orgaddress_types");
      context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

      LookupList emailTypeList = new LookupList(db, "lookup_orgemail_types");
      context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);

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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandFields(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;

    String recordId = null;

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

      if (recordId == null) {
        CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
        //thisCategory.setLinkModuleId(Constants.ACCOUNTS);
        //thisCategory.setLinkItemId(thisOrganization.getOrgId());
        //thisCategory.setRecordId(Integer.parseInt(recordId));
        //thisCategory.setIncludeEnabled(Constants.TRUE);
        //thisCategory.setIncludeScheduled(Constants.TRUE);
        //thisCategory.setBuildResources(true);
        //thisCategory.buildResources(db);
        context.getRequest().setAttribute("Category", thisCategory);

        CustomFieldRecordList recordList = new CustomFieldRecordList();
        recordList.setLinkModuleId(Constants.ACCOUNTS);
        recordList.setLinkItemId(thisOrganization.getOrgId());
        recordList.setCategoryId(thisCategory.getId());
        recordList.buildList(db);
        context.getRequest().setAttribute("Records", recordList);
      } else {
        CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
        thisCategory.setLinkModuleId(Constants.ACCOUNTS);
        thisCategory.setLinkItemId(thisOrganization.getOrgId());
        thisCategory.setRecordId(Integer.parseInt(recordId));
        thisCategory.setIncludeEnabled(Constants.TRUE);
        thisCategory.setIncludeScheduled(Constants.TRUE);
        thisCategory.setBuildResources(true);
        thisCategory.buildResources(db);
        context.getRequest().setAttribute("Category", thisCategory);
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Custom Fields Details");
      if (recordId == null) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAddFolderRecord(ActionContext context) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandModifyFields(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      String recordId = (String) context.getRequest().getParameter("recId");

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
      return ("ModifyFieldsOK");
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
  public String executeCommandUpdateFields(ActionContext context) {
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
      resultCount = thisCategory.update(db);
      if (resultCount == -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Accounts-> ModifyField validation error");
        }
      } else {
        thisCategory.buildResources(db);
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
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
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
  public String executeCommandInsertFields(ActionContext context) {
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
      resultCode = thisCategory.insert(db);
      if (resultCode == -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Accounts-> InsertField validation error");
        }
      } else {
        if (this.getDbName(context).equals("cdb_matt") &&
            thisCategory.hasField(4)) {
          Notification thisNotification = new Notification(Notification.SSL);
          thisNotification.setHost("127.0.0.1");
          thisNotification.setPort(44444);
          thisNotification.setMessageToSend(thisCategory.getFieldValue(4));
          thisNotification.send();
        } else if (this.getDbName(context).equals("cdb_vport") &&
            thisCategory.hasField(11)) {
          MessageTemplate template = new MessageTemplate();
          template.setText("Some text $field");
          template.addParseElement("$field", thisCategory.getFieldValue(11));
          
          Notification thisNotification = new Notification(Notification.SSL);
          thisNotification.setHost("151.204.139.251");
          thisNotification.setPort(44444);
          thisNotification.setMessageToSend(template.getParsedText());
          thisNotification.send();
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

}

