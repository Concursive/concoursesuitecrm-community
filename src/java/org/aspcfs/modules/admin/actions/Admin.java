package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Administrative commands executor.
 *
 *@author     chris
 *@created    January 7, 2002
 */
public final class Admin extends CFSModule {

	/**
	 *  Constructor for the Admin object
	 *
	 *@since
	 */
	public Admin() { }


	/**
	 *  Default -- calls Home.
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandDefault(ActionContext context) {
		
		if (!(hasPermission(context, "admin-view"))) {
		    return ("PermissionError");
		}
	
		return executeCommandHome(context);
	}


	/**
	 *  Home.
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandHome(ActionContext context) {
		
		if (!(hasPermission(context, "admin-view"))) {
		    return ("PermissionError");
		}
		
		addModuleBean(context, "Admin", "Admin");
		return ("HomeOK");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandConfig(ActionContext context) {
		
		if (!(hasPermission(context, "admin-sysconfig-view"))) {
		    return ("PermissionError");
		}
	
		addModuleBean(context, "Configuration", "Configuration");
		return ("ConfigurationOK");
	}


	/**
	 *  Take an initial look at all of the configurable lists within CFS
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandEditLists(ActionContext context) {
		
		if (!(hasPermission(context, "admin-sysconfig-lists-view"))) {
		    return ("PermissionError");
		}
		
		Exception errorMessage = null;
		Connection db = null;

		try {
			db = this.getConnection(context);
			buildFormElements(context, db);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "Configuration", "Configuration");
		return ("EditListsOK");
	}


	/**
	 *  Update a particular list with the new values
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandUpdateList(ActionContext context) {
		
		if (!(hasPermission(context, "admin-sysconfig-lists-edit"))) {
		    return ("PermissionError");
		}
		
		Exception errorMessage = null;
		Connection db = null;

		String[] params = context.getRequest().getParameterValues("selectedList");
		String[] names = new String[params.length];
		String tblName = "";
		int j = 0;

		StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("selectNames"), "^");
		
		while (st.hasMoreTokens()) {
			names[j] = (String) st.nextToken();
			j++;
		}

		try {
			db = this.getConnection(context);
			tblName = buildFormElements(context, db, Integer.parseInt(context.getRequest().getParameter("listid")));

			//begin for all lookup lists
			LookupList compareList = new LookupList(db, tblName);
			LookupList newList = new LookupList(params, names);
			if (System.getProperty("DEBUG") != null) newList.printVals();

			Iterator i = compareList.iterator();
			while (i.hasNext()) {
				LookupElement thisElement = (LookupElement) i.next();

				//still there, stay enabled, don't re-insert it
				if (System.getProperty("DEBUG") != null) System.out.println("Here: " + thisElement.getCode() + " " + newList.getSelectedValue(thisElement.getCode()));

				//not there, disable it, leave it
				if (newList.getSelectedValue(thisElement.getCode()).equals("") || 
            				newList.getSelectedValue(thisElement.getCode()) == null) {
					thisElement.disableElement(db, tblName);
				}
			}

			Iterator k = newList.iterator();
			while (k.hasNext()) {
				LookupElement thisElement = (LookupElement) k.next();

				if (thisElement.getCode() == 0) {
					thisElement.insertElement(db, tblName);
				}
				else {
					thisElement.setNewOrder(db, tblName);
				}
			}

			newList.setDefaultValue(0);
			setFormElement(context, context.getRequest().getParameter("listid"), newList);
			//end

		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "Configuration", "Configuration");
		return ("UpdateListOK");
	}


	/**
	 *  Modify a selected list
	 *
	 *@param  context  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since
	 */
	public String executeCommandModifyList(ActionContext context) {
		
		if (!(hasPermission(context, "admin-sysconfig-lists-edit"))) {
		    return ("PermissionError");
		}
		
		Exception errorMessage = null;
		Connection db = null;

		int id = Integer.parseInt(context.getRequest().getParameter("listId"));

		try {
			db = this.getConnection(context);

			if (id == 1) {
				ContactTypeList typeList = new ContactTypeList(db);
				LookupList ctl = typeList.getLookupList("typeId",0);
				//LookupList ctl = new LookupList(db, "lookup_contact_types");
				ctl.setSelectSize(8);
				ctl.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", ctl);
				context.getRequest().setAttribute("ListLabel", "Contacts and Resources: Contact Type");
			}
			else if (id == 2) {
				LookupList atl = new LookupList(db, "lookup_account_types");
				atl.setSelectSize(8);
				atl.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", atl);
				context.getRequest().setAttribute("ListLabel", "Account Management: Account Type");
			}
			else if (id == 3) {
				LookupList departmentList = new LookupList(db, "lookup_department");
				departmentList.setSelectSize(8);
				departmentList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", departmentList);
				context.getRequest().setAttribute("ListLabel", "Contacts and Resources: Department");
			}
			else if (id == 4) {
				LookupList sourceList = new LookupList(db, "lookup_ticketsource");
				sourceList.setSelectSize(8);
				sourceList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", sourceList);
				context.getRequest().setAttribute("ListLabel", "Tickets: Ticket Source");
			}
			else if (id == 5) {
				LookupList severityList = new LookupList(db, "ticket_severity");
				severityList.setSelectSize(8);
				severityList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", severityList);
				context.getRequest().setAttribute("ListLabel", "Tickets: Ticket Severity ");
			}
			else if (id == 6) {
				LookupList priorityList = new LookupList(db, "ticket_priority");
				priorityList.setSelectSize(8);
				priorityList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", priorityList);
				context.getRequest().setAttribute("ListLabel", "Tickets: Ticket Priority");
			}
			else if (id == 7) {
				LookupList contactEmailTypeList = new LookupList(db, "lookup_contactemail_types");
				contactEmailTypeList.setSelectSize(8);
				contactEmailTypeList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", contactEmailTypeList);
				context.getRequest().setAttribute("ListLabel", "Contacts and Resources: Contact Email Type");
			}
			else if (id == 8) {
				LookupList contactPhoneTypeList = new LookupList(db, "lookup_contactphone_types");
				contactPhoneTypeList.setSelectSize(8);
				contactPhoneTypeList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", contactPhoneTypeList);
				context.getRequest().setAttribute("ListLabel", "Contacts and Resources: Contact Phone Type");
			}
			else if (id == 9) {
				LookupList contactAddressTypeList = new LookupList(db, "lookup_contactaddress_types");
				contactAddressTypeList.setSelectSize(8);
				contactAddressTypeList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", contactAddressTypeList);
				context.getRequest().setAttribute("ListLabel", "Contacts and Resources: Contact Address Type");
			}
			else if (id == 10) {
				LookupList stageList = new LookupList(db, "lookup_stage");
				stageList.setSelectSize(8);
				stageList.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", stageList);
				context.getRequest().setAttribute("ListLabel", "Opportunities/Leads: Stage");
			}
			else if (id == 11) {
				LookupList rtl = new LookupList(db, "lookup_revenue_types");
				rtl.setSelectSize(8);
				rtl.setMultiple(true);
				context.getRequest().setAttribute("SelectedList", rtl);
				context.getRequest().setAttribute("ListLabel", "Account Management: Revenue Type");
			}

		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "Configuration", "Configuration");
		return ("ModifyListOK");
	}


	/**
	 *  Sets the FormElement attribute of the Admin object
	 *
	 *@param  context      The new FormElement value
	 *@param  whichString  The new FormElement value
	 *@param  newList      The new FormElement value
	 *@since
	 */
	protected void setFormElement(ActionContext context, String whichString, LookupList newList) {
		int which = Integer.parseInt(whichString);

		if (which == 1) {
			context.getRequest().setAttribute("ContactTypeList", newList);
		}
		else if (which == 2) {
			context.getRequest().setAttribute("AccountTypeList", newList);
		}
		else if (which == 3) {
			context.getRequest().setAttribute("DepartmentList", newList);
		}
		else if (which == 4) {
			context.getRequest().setAttribute("SourceList", newList);
		}
		else if (which == 5) {
			context.getRequest().setAttribute("SeverityList", newList);
		}
		else if (which == 6) {
			context.getRequest().setAttribute("PriorityList", newList);
		}
		else if (which == 7) {
			context.getRequest().setAttribute("ContactEmailTypeList", newList);
		}
		else if (which == 8) {
			context.getRequest().setAttribute("ContactPhoneTypeList", newList);
		}
		else if (which == 9) {
			context.getRequest().setAttribute("ContactAddressTypeList", newList);
		}
		else if (which == 10) {
			context.getRequest().setAttribute("StageList", newList);
		}
		else if (which == 11) {
			context.getRequest().setAttribute("RevenueTypeList", newList);
		}
	}


	/**
	 *  Build all the necessarry form elements (lists)
	 *
	 *@param  context           Description of Parameter
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
		ContactTypeList typeList = new ContactTypeList(db);
		LookupList ctl = typeList.getLookupList("typeId",0);
		
		LookupList departmentList = new LookupList(db, "lookup_department");
		LookupList sourceList = new LookupList(db, "lookup_ticketsource");
		LookupList severityList = new LookupList(db, "ticket_severity");
		LookupList priorityList = new LookupList(db, "ticket_priority");
		LookupList contactEmailTypeList = new LookupList(db, "lookup_contactemail_types");
		LookupList contactPhoneTypeList = new LookupList(db, "lookup_contactphone_types");
		LookupList contactAddressTypeList = new LookupList(db, "lookup_contactaddress_types");
		LookupList stageList = new LookupList(db, "lookup_stage");
		LookupList atl = new LookupList(db, "lookup_account_types");
		
		LookupList rtl = new LookupList(db, "lookup_revenue_types");
		
		context.getRequest().setAttribute("SeverityList", severityList);
		context.getRequest().setAttribute("PriorityList", priorityList);
		context.getRequest().setAttribute("SourceList", sourceList);
		context.getRequest().setAttribute("DepartmentList", departmentList);
		context.getRequest().setAttribute("ContactTypeList", ctl);
		context.getRequest().setAttribute("ContactEmailTypeList", contactEmailTypeList);
		context.getRequest().setAttribute("ContactPhoneTypeList", contactPhoneTypeList);
		context.getRequest().setAttribute("ContactAddressTypeList", contactAddressTypeList);
		context.getRequest().setAttribute("StageList", stageList);
		context.getRequest().setAttribute("AccountTypeList", atl);
		context.getRequest().setAttribute("RevenueTypeList", rtl);
	}


	/**
	 *  Build all the necessarry form elements (lists). Ignore option allows one to
	 *  be "selected" (called during Update. All other lists besisdes the
	 *  "selected" one are built from the database for the returned page The db
	 *  table name of the "selected" list is returned so that the necessary
	 *  operations can be carried out in order to update that particular list. The
	 *  returned page then has the updated values for that list
	 *
	 *@param  context           Description of Parameter
	 *@param  db                Description of Parameter
	 *@param  ignore            Description of Parameter
	 
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected String buildFormElements(ActionContext context, Connection db, int ignore) throws SQLException {
		String tableName = "";

		if (ignore != 1) {
			//LookupList ctl = new LookupList(db, "lookup_contact_types");
			ContactTypeList typeList = new ContactTypeList(db);
			LookupList ctl = typeList.getLookupList("typeId",0);
			context.getRequest().setAttribute("ContactTypeList", ctl);
		}
		else {
			tableName = "lookup_contact_types";
		}
		
		if (ignore != 2) {
			LookupList atl = new LookupList(db, "lookup_account_types");
			context.getRequest().setAttribute("AccountTypeList", atl);
		}
		else {
			tableName = "lookup_account_types";
		}

		if (ignore != 3) {
			LookupList departmentList = new LookupList(db, "lookup_department");
			context.getRequest().setAttribute("DepartmentList", departmentList);
		}
		else {
			tableName = "lookup_department";
		}

		if (ignore != 4) {
			LookupList sourceList = new LookupList(db, "lookup_ticketsource");
			context.getRequest().setAttribute("SourceList", sourceList);
		}
		else {
			tableName = "lookup_ticketsource";
		}

		if (ignore != 5) {
			LookupList severityList = new LookupList(db, "ticket_severity");
			context.getRequest().setAttribute("SeverityList", severityList);
		}
		else {
			tableName = "ticket_severity";
		}

		if (ignore != 6) {
			LookupList priorityList = new LookupList(db, "ticket_priority");
			context.getRequest().setAttribute("PriorityList", priorityList);
		}
		else {
			tableName = "ticket_priority";
		}

		if (ignore != 7) {
			LookupList contactEmailTypeList = new LookupList(db, "lookup_contactemail_types");
			context.getRequest().setAttribute("ContactEmailTypeList", contactEmailTypeList);
		}
		else {
			tableName = "lookup_contactemail_types";
		}

		if (ignore != 8) {
			LookupList contactPhoneTypeList = new LookupList(db, "lookup_contactphone_types");
			context.getRequest().setAttribute("ContactPhoneTypeList", contactPhoneTypeList);
		}
		else {
			tableName = "lookup_contactphone_types";
		}

		if (ignore != 9) {
			LookupList contactAddressTypeList = new LookupList(db, "lookup_contactaddress_types");
			context.getRequest().setAttribute("ContactAddressTypeList", contactAddressTypeList);
		}
		else {
			tableName = "lookup_contactaddress_types";
		}
		
		if (ignore != 10) {
			LookupList stageList = new LookupList(db, "lookup_stage");
			context.getRequest().setAttribute("StageList", stageList);
		}
		else {
			tableName = "lookup_stage";
		}
		
		if (ignore != 11) {
			LookupList rtl = new LookupList(db, "lookup_revenue_types");
			context.getRequest().setAttribute("RevenueTypeList", rtl);
		}
		else {
			tableName = "lookup_revenue_types";
		}

		return tableName;
	}

}

