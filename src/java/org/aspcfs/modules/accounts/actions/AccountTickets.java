package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.admin.base.*;

/**
 *  Maintains Tickets related to an Account
 *
 *@author     chris
 *@created    August 15, 2001
 *@version    $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *      $
 */
public final class AccountTickets extends CFSModule {

  /**
   *  Sample action for prototyping, by including a specified page
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
   *  Re-opens a ticket
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReopenTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-edit")) {
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
      if (resultCount == -1) {
        return (executeCommandTicketDetails(context));
      } else if (resultCount == 1) {
        return (executeCommandTicketDetails(context));
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Prepares a ticket form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAddTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    Organization newOrg = null;
    //Parameters
    String temporgId = context.getRequest().getParameter("orgId");
    int tempid = Integer.parseInt(temporgId);
    try {
      db = this.getConnection(context);
      //Organization for header
      newOrg = new Organization(db, tempid);
      //Ticket
      newTic = (Ticket) context.getFormBean();
      if (context.getRequest().getParameter("refresh") != null || 
          (context.getRequest().getParameter("contact") != null && 
           context.getRequest().getParameter("contact").equals("on"))) {
        newTic.getHistory().setTicketId(newTic.getId());
        newTic.getHistory().buildList(db);
      } else {
        newTic.setOrgId(tempid);
      }
      buildFormElements(context, db, newTic);
      addModuleBean(context, "View Accounts", "Add a Ticket");
      context.getRequest().setAttribute("OrgDetails", newOrg);
      return ("AddTicketOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Inserts a submitted ticket
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandInsertTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    boolean contactRecordInserted = false;
    Contact nc = null;
    Ticket newTicket = null;
    //Parameters
    String newContact = context.getRequest().getParameter("contact");
    //Process the submitted ticket
    Ticket newTic = (Ticket) context.getFormBean();
    newTic.setEnteredBy(getUserId(context));
    newTic.setModifiedBy(getUserId(context));
    //Insert a new contact if specified
    if (newContact != null && newContact.equals("on")) {
      //NOTE: If there are any changes here, also change TroubleTickets where a new contact is created
      nc = new Contact();
      nc.setNameFirst(context.getRequest().getParameter("thisContact_nameFirst"));
      nc.setNameLast(context.getRequest().getParameter("thisContact_nameLast"));
      nc.setTitle(context.getRequest().getParameter("thisContact_title"));
      nc.setRequestItems(context.getRequest());
      nc.setOrgId(newTic.getOrgId());
      nc.setEnteredBy(getUserId(context));
      nc.setModifiedBy(getUserId(context));
      nc.setOwner(getUserId(context));
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
        //Reload the ticket for the details page... redundant to do here
        newTicket = new Ticket(db, newTic.getId());
        context.getRequest().setAttribute("TicketDetails", newTicket);
        addRecentItem(context, newTicket);
      } else {
        processErrors(context, newTic.getErrors());
      }
      addModuleBean(context, "View Accounts", "Ticket Insert ok");
      if (recordInserted) {
        return ("InsertTicketOK");
      } else {
        return (executeCommandAddTicket(context));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Load the ticket details tab
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandTicketDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Ticket newTic = new Ticket();
      if (newTic.getAssignedTo() > 0) {
        newTic.checkEnabledOwnerAccount(db);
      }
      newTic.queryRecord(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);
      //Load the organization for the header
      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      addModuleBean(context, "View Accounts", "View Tickets");
      return ("TicketDetailsOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Confirm the delete operation showing dependencies
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    try {
      db = this.getConnection(context);
      Ticket ticket = new Ticket(db, Integer.parseInt(id));
      DependencyList dependencies = ticket.processDependencies(db);
      //Prepare the dialog based on the dependencies
      HtmlDialog htmlDialog = new HtmlDialog();
      htmlDialog.setTitle("Dark Horse CRM: Confirm Delete");
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='AccountTickets.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      } else {
        htmlDialog.addMessage(dependencies.getHtmlString());
        htmlDialog.setHeader("This object has the following dependencies within Dark Horse CRM:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='AccountTickets.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
      }
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Delete the specified ticket
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDeleteTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Connection db = null;
    //Parameters
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Organization newOrg = new Organization(db, orgId);
      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
      recordDeleted = thisTic.delete(db);
      if (recordDeleted) {
        deleteRecentItem(context, thisTic);
        context.getRequest().setAttribute("OrgDetails", newOrg);
        context.getRequest().setAttribute("refreshUrl", "Accounts.do?command=ViewTickets&orgId=" + orgId);
        return ("DeleteTicketOK");
      } else {
        return (executeCommandTicketDetails(context));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Loads the ticket for modification
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandModifyTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      //Load the ticket
      if (context.getRequest().getParameter("companyName") == null) {
        newTic = new Ticket(db, Integer.parseInt(ticketId));
      } else {
        newTic = (Ticket) context.getFormBean();
        newTic.getHistory().setTicketId(newTic.getId());
      }
      //Load the organization
      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Load the departmentList for assigning
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "-- None --");
      departmentList.setJsEvent("onChange=\"javascript:updateUserList();\"");
      context.getRequest().setAttribute("DepartmentList", departmentList);
      //Load the severity list
      LookupList severityList = new LookupList(db, "ticket_severity");
      context.getRequest().setAttribute("SeverityList", severityList);
      //Load the priority list
      LookupList priorityList = new LookupList(db, "ticket_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      //Load the ticket source list
      LookupList sourceList = new LookupList(db, "lookup_ticketsource");
      sourceList.addItem(0, "-- None --");
      context.getRequest().setAttribute("SourceList", sourceList);
      //Load the top level category list
      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      context.getRequest().setAttribute("CategoryList", categoryList);
      //Load the user list the ticket can be assigned to
      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord("-- None --");
      userList.setBuildContact(true);
      userList.setBuildContactDetails(false);
      userList.setDepartment(newTic.getDepartmentCode());
      userList.setExcludeDisabledIfUnselected(true);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
      //Load the ticket sub-category1 list
      TicketCategoryList subList1 = new TicketCategoryList();
      subList1.setCatLevel(1);
      subList1.setParentCode(newTic.getCatCode());
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList1", subList1);
      //Load the contact list
      ContactList contactList = new ContactList();
      if (newTic != null && newTic.getOrgId() != -1) {
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(newTic.getOrgId());
        contactList.setEmptyHtmlSelectRecord("-- None --");
        contactList.buildList(db);
      }
      context.getRequest().setAttribute("ContactList", contactList);
      //Load the ticket sub-category2 list
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
      subList2.getCatListSelect().addItem(0, "Undetermined");
      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.buildList(db);
      context.getRequest().setAttribute("SubList2", subList2);
      //Load the ticket sub-category3 list
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
      subList3.getCatListSelect().addItem(0, "Undetermined");
      subList3.buildList(db);
      context.getRequest().setAttribute("SubList3", subList3);
      if (context.getRequest().getParameter("refresh") != null && (Integer.parseInt(context.getRequest().getParameter("refresh")) == 1 || Integer.parseInt(context.getRequest().getParameter("refresh")) == 3)) {
        newTic.setSubCat3(0);
      }
      //Put the ticket in the request
      addRecentItem(context, newTic);
      context.getRequest().setAttribute("TicketDetails", newTic);
      addModuleBean(context, "View Accounts", "View Tickets");
      return ("ModifyTicketOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Update the specified ticket
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdateTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-edit")) {
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

    if (context.getRequest().getParameter("close").equals("1")) {
      newTic.setCloseIt(true);
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
          thisCat.setLevel(catCount);
          thisCat.setCategoryLevel(catCount);
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
      resultCount = newTic.update(db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == -1) {
      processErrors(context, newTic.getErrors());
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return (executeCommandModifyTicket(context));
      } else if (resultCount == 1) {

        return ("UpdateTicketOK");
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
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@param  newTic            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
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

    TicketCategoryList categoryList = new TicketCategoryList();
    categoryList.setCatLevel(0);
    categoryList.setParentCode(0);
    categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
    categoryList.getCatListSelect().addItem(0, "Undetermined");
    categoryList.buildList(db);
    context.getRequest().setAttribute("CategoryList", categoryList);

    UserList userList = new UserList();
    userList.setEmptyHtmlSelectRecord("-- None --");
    userList.setBuildContact(true);
    userList.setBuildContactDetails(false);
    userList.setExcludeDisabledIfUnselected(true);
    if (newTic.getDepartmentCode() > 0) {
      userList.setDepartment(newTic.getDepartmentCode());
      userList.buildList(db);
    }
    context.getRequest().setAttribute("UserList", userList);

    ContactList contactList = new ContactList();
    contactList.setBuildDetails(false);
    contactList.setBuildTypes(false);
    contactList.setOrgId(Integer.parseInt(context.getRequest().getParameter("orgId")));
    contactList.buildList(db);
    context.getRequest().setAttribute("ContactList", contactList);

    TicketCategoryList subList1 = new TicketCategoryList();
    subList1.setCatLevel(1);
    subList1.setParentCode(newTic.getCatCode());
    subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
    subList1.getCatListSelect().addItem(0, "Undetermined");
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
    subList2.getCatListSelect().addItem(0, "Undetermined");
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
    subList3.getCatListSelect().addItem(0, "Undetermined");
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
      userList.setEmptyHtmlSelectRecord("-- None --");
      if ((departmentCode != null) && (!"0".equals(departmentCode))) {
        userList.setBuildContact(true);
        userList.setBuildContactDetails(false);
        userList.setDepartment(Integer.parseInt(departmentCode));
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
   *  Loads the history for the specified ticket
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String ticketId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      //Load the ticket
      Ticket thisTic = new Ticket();
      thisTic.setBuildHistory(true);
      thisTic.queryRecord(db, Integer.parseInt(ticketId));
      //Load the organization
      Organization thisOrganization = new Organization(db, thisTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //check whether or not the owner is an active User
      if (thisTic.getAssignedTo() > -1) {
        thisTic.checkEnabledOwnerAccount(db);
      }
      context.getRequest().setAttribute("TicketDetails", thisTic);
      addRecentItem(context, thisTic);
      addModuleBean(context, "View Tickets", "Ticket Details");
      return ("ViewHistoryOK");
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   *@deprecated      Replaced combobox with a pop-up
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

}

