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
			FileItem thisItem = (FileItem)i.next();
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
		return("ReportsOK");
	} else {
		context.getRequest().setAttribute("Error", errorMessage);
		return ("SystemError");
	}

  }
  
  public String executeCommandDownloadCSVReport(ActionContext context) {
    Exception errorMessage = null;

    String itemId = (String)context.getRequest().getParameter("fid");
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
  
  public String executeCommandShowReportHtml(ActionContext context) {
	Exception errorMessage = null;
	
	String projectId = (String)context.getRequest().getParameter("pid");
	String itemId = (String)context.getRequest().getParameter("fid");
	
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
  
  public String executeCommandGenerateForm(ActionContext context) {
	addModuleBean(context, "Reports", "Generate new");
	return("GenerateFormOK");
  }
  
  public void setBaseAccountReportInfo( ArrayList newList, ReportRow thisRow, Organization thisOrg, String tdNumStart) {
	if ((newList.contains("1"))) {	thisRow.addCell(thisOrg.getName(), tdNumStart);	}
	if ((newList.contains("2"))) {	thisRow.addCell(thisOrg.getAccountNumber()); }
	if ((newList.contains("3"))) {	thisRow.addCell(thisOrg.getUrl()); }
	if ((newList.contains("4"))) {	thisRow.addCell(thisOrg.getTicker()); }
	if ((newList.contains("5"))) {	thisRow.addCell(thisOrg.getEmployees());}
	if ((newList.contains("6"))) {	thisRow.addCell(thisOrg.getEnteredString());}
	if ((newList.contains("7"))) {	thisRow.addCell(thisOrg.getEnteredByName());}
	if ((newList.contains("8"))) {	thisRow.addCell(thisOrg.getModifiedString());}
	if ((newList.contains("9"))) {	thisRow.addCell(thisOrg.getModifiedByName());}
	if ((newList.contains("10"))) { thisRow.addCell(thisOrg.getOwnerName());}
	if ((newList.contains("11"))) { thisRow.addCell(thisOrg.getContractEndDateString());}
	if ((newList.contains("12"))) { thisRow.addCell(thisOrg.getNotes());}
  }
  
  public String executeCommandExportReport(ActionContext context) {
	Exception errorMessage = null;
	boolean recordInserted = false;
	Connection db = null;
	String subject = context.getRequest().getParameter("subject");
	String type = context.getRequest().getParameter("type");
	
	String[] params = null;
	String[] names = null;
	ArrayList newList = null;
	int lastId = -1;
	
	if (context.getRequest().getParameterValues("fields") != null) {
		params = context.getRequest().getParameterValues("fields");
		newList = new ArrayList(Arrays.asList(params));
	} else {
		newList = new ArrayList();
	}
	
	Report rep = new Report();
	rep.setDelimitedCharacter(",");
	
	rep.setHeader("CFS Accounts: " + subject);
	
	if ((newList.contains("1"))) {	rep.addColumn("Account", "Account Name"); }
	if ((newList.contains("2"))) {	rep.addColumn("Account No."); }
	if ((newList.contains("3"))) {	rep.addColumn("URL"); }
	if ((newList.contains("4"))) {	rep.addColumn("Ticker"); }
	if ((newList.contains("5"))) {	rep.addColumn("Employees"); }
	if ((newList.contains("6"))) { rep.addColumn("Entered"); }
	if ((newList.contains("7"))) {	rep.addColumn("Entered By"); }
	if ((newList.contains("8"))) { rep.addColumn("Modified");}
	if ((newList.contains("9"))) {	rep.addColumn("Modified By");}
	if ((newList.contains("10"))) { rep.addColumn("Owner"); }
	if ((newList.contains("11"))) { rep.addColumn("Contract End Date"); }
	if ((newList.contains("12"))) { rep.addColumn("Notes"); }
	
	String tdNumStart = "valign='top' align='right' bgcolor='#FFFFFF' nowrap";
	String filePath = this.getPath(context, "account-reports");
	
	SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy");
	String datePathToUse1 = formatter1.format(new java.util.Date());
	SimpleDateFormat formatter2 = new SimpleDateFormat ("MMdd");
	String datePathToUse2 = formatter2.format(new java.util.Date());
	filePath += datePathToUse1 + fs + datePathToUse2 + fs;
	
	SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddhhmmss");
	String filenameToUse = formatter.format(new java.util.Date());
	
	File f = new File(filePath);
	f.mkdirs();

    	OrganizationList organizationList = new OrganizationList();
	ContactList contactList = new ContactList();
	TicketList ticList = new TicketList();
	Organization ctOrg = null;
	
	CustomFieldCategoryList thisList = new CustomFieldCategoryList();
        thisList.setLinkModuleId(Constants.ACCOUNTS);
        thisList.setIncludeEnabled(Constants.TRUE);
        thisList.setIncludeScheduled(Constants.TRUE);
        thisList.setBuildResources(true);
	
	CustomFieldRecordList recordList = new CustomFieldRecordList();
	CustomFieldGroup thisGroup = new CustomFieldGroup();
	
	try {
		db = this.getConnection(context);
		
		//Accounts with Folders
		if (type.equals("4")) {
			
			rep.addColumn("Folder Name");
			rep.addColumn("Record Name");
			rep.addColumn("Group Name");
			rep.addColumn("Field Name");
			rep.addColumn("Field Value");
			rep.addColumn("Entered");
			rep.addColumn("Modified");
			
			thisList.buildList(db);	
		
			organizationList.setMinerOnly(false);
			organizationList.buildList(db);
			
			Iterator m = organizationList.iterator();
			
			while (m.hasNext()) {
				Organization thisOrg = (Organization) m.next();
				
				Iterator cat = thisList.iterator();
				while (cat.hasNext()) {
					CustomFieldCategory thisCat = (CustomFieldCategory) cat.next();
					
					recordList = new CustomFieldRecordList();
					recordList.setLinkModuleId(Constants.ACCOUNTS);
					recordList.setLinkItemId(thisOrg.getOrgId());
					recordList.setCategoryId(thisCat.getId());
					recordList.buildList(db);
					
					Iterator rec = recordList.iterator();
					
					while (rec.hasNext()) {
						CustomFieldRecord thisRec = (CustomFieldRecord) rec.next();
						Iterator grp = thisCat.iterator();
						while (grp.hasNext()) {
							thisGroup = new CustomFieldGroup();
							thisGroup = (CustomFieldGroup)grp.next();
							thisGroup.buildResources(db);
							
							Iterator fields = thisGroup.iterator();
							if (fields.hasNext()) {
								while (fields.hasNext()) {
									ReportRow thisRow = new ReportRow();
									CustomField thisField = (CustomField)fields.next();
									
									thisField.setRecordId(thisRec.getId());
									thisField.buildResources(db);
							
									setBaseAccountReportInfo( newList, thisRow, thisOrg, tdNumStart);
								
									thisRow.addCell(thisCat.getName());
									thisRow.addCell(thisCat.getName() + " #" + thisRec.getId());
									thisRow.addCell(thisGroup.getName());
									thisRow.addCell(thisField.getNameHtml());
									thisRow.addCell(thisField.getValueHtml());
									thisRow.addCell(thisRec.getEnteredString());
									thisRow.addCell(thisRec.getModifiedDateTimeString());
									
									rep.addRow(thisRow);
								}
							}
						}
					}
				}
			}
		}
		
		//Accounts with tickets report
		else if (type.equals("3")) {
			rep.addColumn("Ticket ID");
			rep.addColumn("Problem");
			rep.addColumn("Ticket Source");
			rep.addColumn("Severity");
			rep.addColumn("Priority");
			rep.addColumn("Category");
			rep.addColumn("Assigned Dept.");
			rep.addColumn("Assigned To");
			rep.addColumn("Solution");
			rep.addColumn("Date Closed");
			rep.addColumn("Entered");
			rep.addColumn("Entered By");
			rep.addColumn("Modified");
			rep.addColumn("Modified By");
			
			ticList.buildList(db);
			
			Iterator t = ticList.iterator();
			while (t.hasNext()) {
				Ticket thisTic = (Ticket)t.next();
				
				if (thisTic.getOrgId() > -1) {
					System.out.println("this: " + thisTic.getOrgId() + " Last: " + lastId);
					if (thisTic.getOrgId() != lastId) {
						System.out.println("I am getting a new Org...");
						ctOrg = new Organization(db, thisTic.getOrgId());
					}
					
					ReportRow thisRow = new ReportRow();
					
					setBaseAccountReportInfo( newList, thisRow, ctOrg, tdNumStart);
					
					thisRow.addCell(thisTic.getId());
					thisRow.addCell(thisTic.getProblem());
					thisRow.addCell(thisTic.getSourceName());
					thisRow.addCell(thisTic.getSeverityName());
					thisRow.addCell(thisTic.getPriorityName());
					thisRow.addCell(thisTic.getCategoryName());
					thisRow.addCell(thisTic.getDepartmentName());
					thisRow.addCell(thisTic.getOwnerName());
					thisRow.addCell(thisTic.getSolution());
					thisRow.addCell(thisTic.getClosedString());
					thisRow.addCell(thisTic.getEnteredString());
					thisRow.addCell(thisTic.getEnteredByName());
					thisRow.addCell(thisTic.getModifiedString());
					thisRow.addCell(thisTic.getModifiedByName());
					
					rep.addRow(thisRow);
					lastId = thisTic.getOrgId();
				}
				
				
			}
			
	 	}
		
		//Accounts with contacts report
		else if (type.equals("2")) {
			
			rep.addColumn("Contact Type");
			rep.addColumn("Last Name");
			rep.addColumn("First Name");
			rep.addColumn("Middle Name");
			rep.addColumn("Department");
			rep.addColumn("Title");
			rep.addColumn("Owner");
			rep.addColumn("Business Email");
			rep.addColumn("Business Phone");
			rep.addColumn("Business Address");
			rep.addColumn("City");
			rep.addColumn("State");
			rep.addColumn("Zip");
			rep.addColumn("Country");
			rep.addColumn("Notes");
			
			contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
			contactList.setPersonalId(this.getUserId(context));
			contactList.setOwnerIdRange(this.getUserRange(context));
			//contactList.setOwner(this.getUserId(context));
			contactList.buildList(db);
			
			Iterator c = contactList.iterator();
			while (c.hasNext()) {
				Contact thisContact = (Contact)c.next();
				
				if (thisContact.getOrgId() > 0) {
					System.out.println("this: " + thisContact.getOrgId() + " Last: " + lastId);
					if (thisContact.getOrgId() != lastId) {
						System.out.println("I am getting a new Org...");
						ctOrg = new Organization(db, thisContact.getOrgId());
					}
					
					ReportRow thisRow = new ReportRow();
					
					setBaseAccountReportInfo( newList, thisRow, ctOrg, tdNumStart);
					
					thisRow.addCell(thisContact.getTypeName());
					thisRow.addCell(thisContact.getNameLast());
					thisRow.addCell(thisContact.getNameFirst());
					thisRow.addCell(thisContact.getNameMiddle());
					thisRow.addCell(thisContact.getDepartmentName());
					thisRow.addCell(thisContact.getTitle());
					thisRow.addCell(thisContact.getOwnerName());
					thisRow.addCell(thisContact.getEmailAddress("Business"));
					thisRow.addCell(thisContact.getPhoneNumber("Business"));
					thisRow.addCell(thisContact.getAddress("Business").getStreetAddressLine1() + " " + thisContact.getAddress("Business").getStreetAddressLine2());
					thisRow.addCell(thisContact.getAddress("Business").getCity());
					thisRow.addCell(thisContact.getAddress("Business").getState());
					thisRow.addCell(thisContact.getAddress("Business").getZip());
					thisRow.addCell(thisContact.getAddress("Business").getCountry());
					thisRow.addCell(thisContact.getNotes());
					
					rep.addRow(thisRow);
					lastId = thisContact.getOrgId();
				}
				
				
			}
		//All accounts or accounts w/folders
	 	} else {
		
			organizationList.setMinerOnly(false);
			organizationList.buildList(db);
			
			Iterator m = organizationList.iterator();
			while (m.hasNext()) {
				Organization thisOrg = (Organization) m.next();
				
				ReportRow thisRow = new ReportRow();
				
				setBaseAccountReportInfo( newList, thisRow, thisOrg, tdNumStart);

				rep.addRow(thisRow);
			}
		}
		
		rep.saveHtml(filePath + filenameToUse + ".html");
		rep.saveDelimited(filePath + filenameToUse + ".csv");

		File fileLink = new File(filePath + filenameToUse + ".csv");
		
		FileItem thisItem = new FileItem();
		thisItem.setLinkModuleId(Constants.ACCOUNTS_REPORTS);
		thisItem.setLinkItemId(0);
		thisItem.setProjectId(-1);
		thisItem.setEnteredBy(getUserId(context));
		thisItem.setModifiedBy(getUserId(context));
		thisItem.setSubject(subject);
		thisItem.setClientFilename(filenameToUse + ".csv");
		thisItem.setFilename(filenameToUse);
		thisItem.setSize((int)fileLink.length());
		thisItem.insert(db);
		
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
  
  public String executeCommandDeleteReport(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;

    String projectId = (String)context.getRequest().getParameter("pid");
    String itemId = (String)context.getRequest().getParameter("fid");

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
  
   public String executeCommandDashboard(ActionContext context) {
	addModuleBean(context, "Dashboard", "Dashboard");
	
	UserBean thisUser = (UserBean)context.getSession().getAttribute("User");
	
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
	}
	finally {
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
	}
	else {
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

    Organization newOrg = (Organization)context.getFormBean();
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
    
    Organization newOrg = (Organization)context.getFormBean();
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
      recordDeleted = thisOrganization.delete(db);
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
    
    UserBean thisUser = (UserBean)context.getSession().getAttribute("User");
	
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
      
      String selectedCatId = (String)context.getRequest().getParameter("catId");
      if (selectedCatId == null) {
        selectedCatId = (String)context.getRequest().getAttribute("catId");
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
  
  public String executeCommandAddFolderRecord(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      String selectedCatId = (String)context.getRequest().getParameter("catId");
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
  
  public String executeCommandModifyFields(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;

    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      String selectedCatId = (String)context.getRequest().getParameter("catId");
      String recordId = (String)context.getRequest().getParameter("recId");
      
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

      String selectedCatId = (String)context.getRequest().getParameter("catId");
      String recordId = (String)context.getRequest().getParameter("recId");
      
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
        if (System.getProperty("DEBUG") != null) System.out.println("Accounts-> ModifyField validation error");
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

      String selectedCatId = (String)context.getRequest().getParameter("catId");
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
        if (System.getProperty("DEBUG") != null) System.out.println("Accounts-> InsertField validation error");
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

