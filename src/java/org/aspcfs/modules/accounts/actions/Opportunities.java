package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    October 17, 2001
 *@version    $Id$
 */
public final class Opportunities extends CFSModule {

	/**
	 *  Description of the Method
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandHome(ActionContext context) {
		addModuleBean(context, "Opportunities", "Opportunities Home");
		return ("HomeOK");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */

	public String executeCommandView(ActionContext context) {

		Exception errorMessage = null;

		String orgId = context.getRequest().getParameter("orgId");

		addModuleBean(context, "View Accounts", "View Opportunity Details");

		PagedListInfo oppPagedInfo = this.getPagedListInfo(context, "OpportunityPagedInfo");
		oppPagedInfo.setLink("/Opportunities.do?command=View&orgId=" + orgId);

		Connection db = null;
		OpportunityList oppList = new OpportunityList();
		Organization thisOrganization = null;

		try {
			db = this.getConnection(context);
			oppList.setOwner(getUserId(context));
			oppList.setPagedListInfo(oppPagedInfo);
			oppList.setOrgId(orgId);
			oppList.buildList(db);
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			context.getRequest().setAttribute("OpportunityList", oppList);
			return ("ListOK");
		}
		else {
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

	public String executeCommandAdd(ActionContext context) {
		Exception errorMessage = null;

		HtmlSelect busTypeSelect = new HtmlSelect();
		busTypeSelect.setSelectName("type");
		busTypeSelect.addItem("N", "New");
		busTypeSelect.addItem("E", "Existing");
		busTypeSelect.build();

		HtmlSelect unitSelect = new HtmlSelect();
		unitSelect.setSelectName("units");
		unitSelect.addItem("M", "Months");
		//unitSelect.addItem("D", "Days");
		//unitSelect.addItem("W", "Weeks");
		//unitSelect.addItem("Y", "Years");
		unitSelect.build();

		String orgId = context.getRequest().getParameter("orgId");
		Organization thisOrganization = null;

		Connection db = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			db = this.getConnection(context);
			
			LookupList stageSelect = new LookupList(db, "lookup_stage");
			context.getRequest().setAttribute("StageList", stageSelect);
			
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
		}
		catch (SQLException e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			context.getRequest().setAttribute("BusTypeList", busTypeSelect);
			context.getRequest().setAttribute("UnitTypeList", unitSelect);
			addModuleBean(context, "View Accounts", "Add Opportunity");
			return ("AddOK");
		}
		else {
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
		boolean recordInserted = false;

		Opportunity newOpp = (Opportunity) context.getRequest().getAttribute("OppDetails");
		newOpp.setEnteredBy(getUserId(context));
		newOpp.setOwner(getUserId(context));
		newOpp.setModifiedBy(getUserId(context));
		
		String closeNow = context.getRequest().getParameter("closeNow");
		
		if ((closeNow != null && closeNow.equals("on")) || newOpp.getCloseIt() == true) {
			newOpp.setCloseIt(true);
		}

		String orgId = context.getRequest().getParameter("orgId");
		Organization thisOrganization = null;

		Connection db = null;
		try {
			db = this.getConnection(context);
			recordInserted = newOpp.insert(db, context);
			if (recordInserted) {
				newOpp = new Opportunity(db, "" + newOpp.getId());
				context.getRequest().setAttribute("OppDetails", newOpp);
				addRecentItem(context, newOpp);
				thisOrganization = new Organization(db, Integer.parseInt(orgId));
				context.getRequest().setAttribute("OrgDetails", thisOrganization);
			}
			else {
				processErrors(context, newOpp.getErrors());
			}
		}
		catch (SQLException e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "View Accounts", "Insert an Opportunity");
		if (errorMessage == null) {
			if (recordInserted) {
				return ("DetailsOK");
			}
			else {
				return (executeCommandAdd(context));
			}
		}
		else {
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
		addModuleBean(context, "View Accounts", "View Opportunity Details");
		Exception errorMessage = null;

		String orgId = context.getRequest().getParameter("orgId");
		String oppId = context.getRequest().getParameter("id");

		Connection db = null;
		Opportunity newOpp = null;
		Organization thisOrganization = null;

		try {
			db = this.getConnection(context);
			newOpp = new Opportunity(db, oppId);
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			context.getRequest().setAttribute("OppDetails", newOpp);
			addRecentItem(context, newOpp);
			return ("DetailsOK");
		}
		else {
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
		Opportunity newOpp = null;
		Organization thisOrganization = null;

		String orgId = context.getRequest().getParameter("orgId");
		String tempcontact = context.getRequest().getParameter("contactLink");
		String tempaccount = context.getRequest().getParameter("accountLink");

		Connection db = null;
		try {
			db = this.getConnection(context);
			newOpp = new Opportunity(db, context.getRequest().getParameter("id"));
			recordDeleted = newOpp.delete(db, context);
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "View Accounts", "Delete an opportunity");
		if (errorMessage == null) {
			if (recordDeleted) {
				deleteRecentItem(context, newOpp);
				return ("DeleteOK");
			}
			else {
				processErrors(context, newOpp.getErrors());
				return (executeCommandView(context));
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}


	/**
	 *  Modify Contact
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandModify(ActionContext context) {
		addModuleBean(context, "View Accounts", "Modify an Opportunity");
		Exception errorMessage = null;

		HtmlSelect busTypeSelect = new HtmlSelect();
		busTypeSelect.setSelectName("type");
		busTypeSelect.addItem("N", "New");
		busTypeSelect.addItem("E", "Existing");

		HtmlSelect unitSelect = new HtmlSelect();
		unitSelect.setSelectName("units");
		unitSelect.addItem("M", "Months");
		//unitSelect.addItem("D", "Days");
		//unitSelect.addItem("W", "Weeks");
		//unitSelect.addItem("Y", "Years");

		int tempId = -1;
		String passedId = context.getRequest().getParameter("id");
		tempId = Integer.parseInt(passedId);

		String orgId = context.getRequest().getParameter("orgId");

		Connection db = null;
		Statement st = null;
		ResultSet rs = null;
		Opportunity newOpp = null;
		Organization thisOrganization = null;

		try {
			db = this.getConnection(context);
			
			newOpp = new Opportunity(db, "" + tempId);
			
			busTypeSelect.setDefaultKey(newOpp.getType());
			unitSelect.setDefaultKey(newOpp.getUnits());

			LookupList stageSelect = new LookupList(db, "lookup_stage");
			context.getRequest().setAttribute("StageList", stageSelect);
			
			UserList userList = new UserList();
			//userList.setEmptyHtmlSelectRecord("-- None --");
			userList.setBuildContact(true);
			userList.setIncludeMe(true);
			userList.setMyId(getUserId(context));
			userList.setMyValue(getNameLast(context) + ", " + getNameFirst(context));
			userList.setManagerId(getUserId(context));
			userList.buildList(db);
			context.getRequest().setAttribute("UserList", userList);

			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			context.getRequest().setAttribute("OppDetails", newOpp);
			addRecentItem(context, newOpp);
			context.getRequest().setAttribute("BusTypeList", busTypeSelect);
			context.getRequest().setAttribute("UnitTypeList", unitSelect);
			return ("ModifyOK");
		}
		else {
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

		Opportunity oldOpp = null;
		Opportunity newOpp = (Opportunity) context.getFormBean();
		newOpp.setId(Integer.parseInt(context.getRequest().getParameter("id")));
		
		String closeNow = context.getRequest().getParameter("closeNow");
		
		if (closeNow != null && closeNow.equals("on")) {
			newOpp.setCloseIt(true);
		} else {
			newOpp.setOpenIt(true);
		}
		

		String orgId = context.getRequest().getParameter("orgId");
		Organization thisOrganization = null;

		Connection db = null;
		int resultCount = 0;

		try {
			db = this.getConnection(context);
			
			oldOpp = new Opportunity(db, context.getRequest().getParameter("id"));
			
			if ( (oldOpp.getStage() != newOpp.getStage()) || newOpp.getCloseIt() == true ) {
				newOpp.setStageChange(true);
			} else {
				newOpp.setStageChange(false);
			}
			
			newOpp.setModifiedBy(getUserId(context));
			resultCount = newOpp.update(db, context);
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
		}
		catch (SQLException e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			if (resultCount == -1) {
				processErrors(context, newOpp.getErrors());
				return executeCommandModify(context);
			}
			else if (resultCount == 1) {
				return ("UpdateOK");
			}
			else {
				context.getRequest().setAttribute("Error",
						"<b>This record could not be updated because someone else updated it first.</b><p>" +
						"You can hit the back button to review the changes that could not be committed, " +
						"but you must reload the record and make the changes again.");
				return ("UserError");
			}
		}
		else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}

}

