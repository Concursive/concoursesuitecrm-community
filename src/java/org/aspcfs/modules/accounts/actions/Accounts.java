package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.util.Iterator;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import java.text.*;

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

