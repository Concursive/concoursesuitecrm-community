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
    
    Exception errorMessage = null;
		addModuleBean(context, "Configuration", "Configuration");
    Connection db = null;
    PermissionCategoryList thisPermCatList = new PermissionCategoryList();
    
    try {
      db = this.getConnection(context);
      thisPermCatList.setEnabledState(1);
      thisPermCatList.buildList(db);
      context.getRequest().setAttribute("PermissionCategoryList", thisPermCatList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    if (errorMessage == null) {
      return ("ConfigurationOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
    
	}
  
	public String executeCommandConfigDetails(ActionContext context) {
		
		if (!(hasPermission(context, "admin-sysconfig-view"))) {
		    return ("PermissionError");
		}
    
    Exception errorMessage = null;
		addModuleBean(context, "Configuration", "Configuration");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    
    try {
      db = this.getConnection(context);
      PermissionCategory permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    if (errorMessage == null) {
      return ("ConfigDetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
    
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
    PermissionCategory permCat = null;
    String moduleId = context.getRequest().getParameter("moduleId");

		try {
			db = this.getConnection(context);
      permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
			buildFormElements(context, db);
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

    context.getRequest().setAttribute("PermissionCategory", permCat);
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
    String tblName = null;
    
		String[] params = context.getRequest().getParameterValues("selectedList");
		String[] names = new String[params.length];
		int j = 0;

		StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("selectNames"), "^");
		
		while (st.hasMoreTokens()) {
			names[j] = (String) st.nextToken();
			j++;
		}

		try {
			db = this.getConnection(context);
      
			tblName = context.getRequest().getParameter("tableName");

			//begin for all lookup lists
			LookupList compareList = new LookupList(db, tblName);
			LookupList newList = new LookupList(params, names);

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
				} else {
					thisElement.setNewOrder(db, tblName);
				}
        
			}
			//end
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}

    context.getRequest().setAttribute("moduleId", context.getRequest().getParameter("module"));
		addModuleBean(context, "Configuration", "Configuration");
		return (executeCommandEditLists(context));
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
    int module = -1;
    int sublist = -1;
    
    if (context.getRequest().getParameter("module") != null) {
      module = Integer.parseInt(context.getRequest().getParameter("module"));
    }
    
    if (context.getRequest().getParameter("sublist") != null) {
      sublist = Integer.parseInt(context.getRequest().getParameter("sublist"));
    }
    
    LookupList selectedList = null;
    String subTitle = null;
    
		try {
			db = this.getConnection(context);
      
      if (module == PermissionCategory.PERMISSION_CAT_LEADS) {
        if (sublist == PermissionCategory.LOOKUP_LEADS_STAGE) {
          selectedList = new LookupList(db, "lookup_stage");
          subTitle = "Pipeline Management: Stage";
        } 
      }
      
      if (module == PermissionCategory.PERMISSION_CAT_CONTACTS) {
        if (sublist == PermissionCategory.LOOKUP_CONTACTS_TYPE) {
          ContactTypeList typeList = new ContactTypeList(db);
          selectedList = typeList.getLookupList("typeId",0);
          subTitle = "Contacts &amp; Resources: Type";
        } else if (sublist == PermissionCategory.LOOKUP_CONTACTS_EMAIL) {
          selectedList = new LookupList(db, "lookup_contactemail_types");
          subTitle = "Contacts &amp; Resources: Email Type";
        } else if (sublist == PermissionCategory.LOOKUP_CONTACTS_ADDRESS) {
          selectedList = new LookupList(db, "lookup_contactaddress_types");
          subTitle = "Contacts &amp; Resources: Address Type";
        } else if (sublist == PermissionCategory.LOOKUP_CONTACTS_PHONE) {
          selectedList = new LookupList(db, "lookup_contactphone_types");
          subTitle = "Contacts &amp; Resources: Phone Type";
        } else if (sublist == PermissionCategory.LOOKUP_CONTACTS_DEPT) {
          selectedList = new LookupList(db, "lookup_department");
          subTitle = "Contacts &amp; Resources: Department";
        }
      }
      
      if (module == PermissionCategory.PERMISSION_CAT_ACCOUNTS) {
        if (sublist == PermissionCategory.LOOKUP_ACCOUNTS_TYPE) {
          selectedList = new LookupList(db, "lookup_account_types");
          subTitle = "Account Management: Type";
        } else if (sublist == PermissionCategory.LOOKUP_ACCOUNTS_REVENUE_TYPE) {
          selectedList = new LookupList(db, "lookup_revenue_types");
          subTitle = "Account Management: Revenue Type";
        }
      }      
      
      if (module == PermissionCategory.PERMISSION_CAT_TICKETS) {
        if (sublist == PermissionCategory.LOOKUP_TICKETS_SOURCE) {
          selectedList = new LookupList(db, "lookup_ticketsource");
          subTitle = "Tickets: Source";
        } else if (sublist == PermissionCategory.LOOKUP_TICKETS_SEVERITY) {
          selectedList = new LookupList(db, "ticket_severity");
          subTitle = "Tickets: Severity";
        } else if (sublist == PermissionCategory.LOOKUP_TICKETS_PRIORITY) {
          selectedList = new LookupList(db, "ticket_priority");
          subTitle = "Tickets: Priority";
        } 
      }      
		}
		catch (Exception e) {
			errorMessage = e;
		}
		finally {
			this.freeConnection(context, db);
		}
    
    if (selectedList != null) {
      selectedList.setSelectSize(8);
      selectedList.setMultiple(true);
    }

    context.getRequest().setAttribute("SelectedList", selectedList);
    context.getRequest().setAttribute("SubTitle", subTitle);
    context.getRequest().setAttribute("moduleId", context.getRequest().getParameter("module"));
		addModuleBean(context, "Configuration", "Configuration");
		return ("ModifyListOK");
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
    
    int moduleId = Integer.parseInt(context.getRequest().getParameter("moduleId"));
    
    if (moduleId == PermissionCategory.PERMISSION_CAT_LEADS) {
      LookupList stageList = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageList);
    } else if (moduleId == PermissionCategory.PERMISSION_CAT_ACCOUNTS) {
      LookupList atl = new LookupList(db, "lookup_account_types");
      LookupList rtl = new LookupList(db, "lookup_revenue_types");
      context.getRequest().setAttribute("AccountTypeList", atl);
      context.getRequest().setAttribute("RevenueTypeList", rtl);
    } else if (moduleId == PermissionCategory.PERMISSION_CAT_CONTACTS) {
      LookupList departmentList = new LookupList(db, "lookup_department");
      LookupList contactEmailTypeList = new LookupList(db, "lookup_contactemail_types");
      LookupList contactPhoneTypeList = new LookupList(db, "lookup_contactphone_types");
      LookupList contactAddressTypeList = new LookupList(db, "lookup_contactaddress_types");
      ContactTypeList typeList = new ContactTypeList(db);
      LookupList ctl = typeList.getLookupList("typeId",0);     
      context.getRequest().setAttribute("DepartmentList", departmentList);
      context.getRequest().setAttribute("ContactTypeList", ctl);
      context.getRequest().setAttribute("ContactEmailTypeList", contactEmailTypeList);
      context.getRequest().setAttribute("ContactPhoneTypeList", contactPhoneTypeList);
      context.getRequest().setAttribute("ContactAddressTypeList", contactAddressTypeList);
    } else if (moduleId == PermissionCategory.PERMISSION_CAT_TICKETS) {
      LookupList sourceList = new LookupList(db, "lookup_ticketsource");
      LookupList severityList = new LookupList(db, "ticket_severity");
      LookupList priorityList = new LookupList(db, "ticket_priority");    
      context.getRequest().setAttribute("SeverityList", severityList);
      context.getRequest().setAttribute("PriorityList", priorityList);
      context.getRequest().setAttribute("SourceList", sourceList);
    }
    
	}
}

