package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.io.*;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  The CFS Company Directory module.
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    $Id: CompanyDirectory.java,v 1.9 2001/07/20 19:02:11 mrajkowski
 *      Exp $
 */
public final class CompanyDirectory extends CFSModule {

  /**
   *  Includes an HTML page for output
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return ("IncludeOK");
  }


  /**
   *  This method retrieves a list of employees from the database and populates
   *  a Vector of the employees retrieved.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String executeCommandListEmployees(ActionContext context) {
	  
    if (!(hasPermission(context, "contacts-internal_contacts-view"))) {
	    return ("PermissionError");
    }
    
    Exception errorMessage = null;

    PagedListInfo companyDirectoryInfo = this.getPagedListInfo(context, "CompanyDirectoryInfo");
    companyDirectoryInfo.setLink("/CompanyDirectory.do?command=ListEmployees");

    Connection db = null;
    ContactList employeeList = new ContactList();
    try {
      db = this.getConnection(context);
      employeeList.setPagedListInfo(companyDirectoryInfo);
      employeeList.setTypeId(Contact.EMPLOYEE_TYPE);
      employeeList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Internal Contacts", "Internal List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("EmployeeList", employeeList);
      return ("ListEmployeesOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  This method retrieves the details of a single employee. The resulting
   *  Employee object is added to the request if successful.<p>
   *
   *  This method handles output for both viewing and modifying a contact.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String executeCommandEmployeeDetails(ActionContext context) {
	  
    if (!(hasPermission(context, "contacts-internal_contacts-view"))) {
	    return ("PermissionError");
    }
	  
    Exception errorMessage = null;

    String employeeId = context.getRequest().getParameter("empid");
    String action = context.getRequest().getParameter("action");

    Connection db = null;
    Contact thisEmployee = null;

    try {
      db = this.getConnection(context);
      thisEmployee = new Contact(db, employeeId);
      context.getRequest().setAttribute("EmployeeBean", thisEmployee);
      addRecentItem(context, thisEmployee);

      if (action != null && action.equals("modify")) {
        buildFormElements(context, db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (action != null && action.equals("modify")) {
        //If user is going to the modify form
        addModuleBean(context, "Internal Contacts", "Modify Employee Details");
        return ("EmployeeDetailsModifyOK");
      } else {
        //If user is going to the detail screen
        addModuleBean(context, "Internal Contacts", "View Employee Details");
        return ("EmployeeDetailsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  This method retrieves an EmployeeBean that was submitted, then updates the
   *  database with the results. <p>
   *
   *  If someone else has already updated the database record, then a message is
   *  displayed for the user and the record is not updated.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String executeCommandUpdateEmployee(ActionContext context) {
	  
    if (!(hasPermission(context, "contacts-internal_contacts-edit"))) {
	    return ("PermissionError");
    }
	  
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    try {
      Contact thisEmployee = (Contact)context.getFormBean();
      thisEmployee.setTypeId(Contact.EMPLOYEE_TYPE);
      thisEmployee.setRequestItems(context.getRequest());
      thisEmployee.setModifiedBy(getUserId(context));
      db = this.getConnection(context);
      resultCount = thisEmployee.update(db);
      if (resultCount == -1) {
        processErrors(context, thisEmployee.getErrors());
        buildFormElements(context, db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    //Decide what happened with the update...
    addModuleBean(context, "Internal Contacts", "Update Employee");
    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("EmployeeDetailsModifyOK");
      } else if (resultCount == 1) {
        return ("EmployeeDetailsUpdateOK");
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
   *  Preparation for displaying the insert employee contact form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.10
   */
  public String executeCommandInsertEmployeeForm(ActionContext context) {
	  
    if (!(hasPermission(context, "contacts-internal_contacts-add"))) {
	    return ("PermissionError");
    }
	  
    Exception errorMessage = null;

    addModuleBean(context, "Add Employee", "Add a new Employee");

    Connection db = null;
    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("EmployeeInsertFormOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Process the insert form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.10
   */
  public String executeCommandInsertEmployee(ActionContext context) {
	  
    if (!(hasPermission(context, "contacts-internal_contacts-add"))) {
	    return ("PermissionError");
    }
	  
    Exception errorMessage = null;
    boolean recordInserted = false;

    Contact thisEmployee = (Contact)context.getFormBean();
    thisEmployee.setTypeId(Contact.EMPLOYEE_TYPE);
    thisEmployee.setOrgId(0);
    thisEmployee.setRequestItems(context.getRequest());
    thisEmployee.setEnteredBy(getUserId(context));
    thisEmployee.setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = thisEmployee.insert(db);
      if (recordInserted) {
        thisEmployee = new Contact(db, "" + thisEmployee.getId());
        context.getRequest().setAttribute("EmployeeBean", thisEmployee);
        addRecentItem(context, thisEmployee);
      } else {
        processErrors(context, thisEmployee.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Internal Contacts", "Internal Insert");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("EmployeeDetailsOK");
      } else {
        return (executeCommandInsertEmployeeForm(context));
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
   *@since           1.10
   */
  public String executeCommandDeleteEmployee(ActionContext context) {
	  
    if (!(hasPermission(context, "contacts-internal_contacts-delete"))) {
	    return ("PermissionError");
    }
	  
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Contact thisContact = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("empid"));
      recordDeleted = thisContact.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Internal Contacts", "Internal Delete");
    if (errorMessage == null) {
      if (recordDeleted) {
        return ("EmployeeDeleteOK");
      } else {
        processErrors(context, thisContact.getErrors());
        return (executeCommandListEmployees(context));
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
   *@since           1.10
   */
  public String executeCommandViewCalendar(ActionContext context) {
    addModuleBean(context, "Company Profile", "Company Calendar");
    CalendarView companyCalendar = new CalendarView(context.getRequest());
    companyCalendar.addHolidays();
    companyCalendar.setMonthArrows(true);
    context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
    return "CalendarOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.10
   */
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    LookupList departmentList = new LookupList(db, "lookup_department");
    departmentList.addItem(0, "--None--");
    context.getRequest().setAttribute("DepartmentList", departmentList);
    
    LookupList phoneTypeList = new LookupList(db, "lookup_contactphone_types");
    context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

    LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    LookupList addressTypeList = new LookupList(db, "lookup_contactaddress_types");
    context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);
  }

}

