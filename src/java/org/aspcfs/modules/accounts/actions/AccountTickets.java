package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import com.darkhorseventures.database.Connection;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.admin.base.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    August 15, 2001
 *@version    $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *      $
 */
public final class AccountTickets extends CFSModule {

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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReopenTicket(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-edit"))) {
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
        return (executeCommandTicketDetails(context));
      } else if (resultCount == 1) {
        return (executeCommandTicketDetails(context));
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
   *@since
   */
  public String executeCommandAddTicket(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-add"))) {
      return ("PermissionError");
    }

    int errorCode = 0;
    Exception errorMessage = null;
    Connection db = null;
    Ticket newTic = null;
    Organization newOrg = null;

    String temporgId = context.getRequest().getParameter("orgId");
    int tempid = Integer.parseInt(temporgId);

    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, tempid);

      if (context.getRequest().getParameter("refresh") != null || (context.getRequest().getParameter("contact") != null && context.getRequest().getParameter("contact").equals("on"))) {
        newTic = (Ticket) context.getFormBean();
        newTic.getHistory().setTicketId(newTic.getId());
        newTic.getHistory().buildList(db);
      } else {
        newTic = new Ticket();
        newTic.setOrgId(tempid);
      }

      buildFormElements(context, db, newTic);

    } catch (Exception e) {
      errorCode = 1;
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorCode == 0) {
      addModuleBean(context, "View Accounts", "Add a Ticket");
      context.getRequest().setAttribute("OrgDetails", newOrg);
      return ("AddTicketOK");
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
  public String executeCommandInsertTicket(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-add"))) {
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
      nc.addType(0);
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
        newTicket = new Ticket(db, newTic.getId());
        context.getRequest().setAttribute("TicketDetails", newTicket);
        addRecentItem(context, newTicket);
      } else {
        processErrors(context, newTic.getErrors());
      }

    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Ticket Insert ok");
      if (recordInserted) {
        return ("InsertTicketOK");
      } else {
        return (executeCommandAddTicket(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      System.out.println(errorMessage.toString());
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
  public String executeCommandTicketDetails(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-view"))) {
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
      newTic.checkEnabledOwnerAccount(db);

      newTic.getHistory().setPagedListInfo(ticListInfo);

      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);
      addModuleBean(context, "View Accounts", "View Tickets");
      return ("TicketDetailsOK");
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
  public String executeCommandDeleteTicket(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    Organization newOrg = null;
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));

    String passedId = null;

    Ticket thisTic = null;
    passedId = context.getRequest().getParameter("id");

    Connection db = null;

    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, orgId);

      thisTic = new Ticket(db, Integer.parseInt(passedId));
      recordDeleted = thisTic.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        deleteRecentItem(context, thisTic);
        context.getRequest().setAttribute("OrgDetails", newOrg);
        return ("DeleteTicketOK");
      } else {
        return (executeCommandTicketDetails(context));
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
  public String executeCommandModifyTicket(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-edit"))) {
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

      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "-- None --");
      //departmentList.setJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=ModifyTicket&passedid=" + newTic.getId() + "&auto-populate=true#department';document.forms[0].submit()");
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
      //categoryList.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=ModifyTicket&passedid=" + newTic.getId() + "&auto-populate=true&refresh=1#categories';document.forms[0].submit()");
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

      OrganizationList orgList = new OrganizationList();
      orgList.setMinerOnly(false);
      orgList.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=ModifyTicket&passedid=" + newTic.getId() + "&auto-populate=true';document.forms[0].submit()");
      orgList.buildList(db);
      context.getRequest().setAttribute("OrgList", orgList);

      TicketCategoryList subList1 = new TicketCategoryList();

      subList1.setCatLevel(1);
      subList1.setParentCode(newTic.getCatCode());
      //subList1.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=ModifyTicket&passedid=" + newTic.getId() + "&auto-populate=true&refresh=2#categories';document.forms[0].submit()");
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      context.getRequest().setAttribute("SubList1", subList1);

      ContactList contactList = new ContactList();

      if (newTic != null && newTic.getOrgId() != -1) {
        contactList.setBuildDetails(false);
        contactList.setOrgId(newTic.getOrgId());
        contactList.setEmptyHtmlSelectRecord("-- None --");
        contactList.buildList(db);
      }

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

      //subList2.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=ModifyTicket&passedid=" + newTic.getId() + "&auto-populate=true&refresh=3#categories';document.forms[0].submit()");
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
      addModuleBean(context, "View Accounts", "View Tickets");
      return ("ModifyTicketOK");
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
  public String executeCommandUpdateTicket(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-tickets-edit"))) {
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
    //departmentList.setJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=AddTicket&auto-populate=true#department';document.forms[0].submit()");
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
    //categoryList.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=AddTicket&auto-populate=true&refresh=1#categories';document.forms[0].submit()");
    categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
    categoryList.buildList(db);
    context.getRequest().setAttribute("CategoryList", categoryList);

    UserList userList = new UserList();
    userList.setEmptyHtmlSelectRecord("-- None --");
    userList.setBuildContact(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setDepartment(newTic.getDepartmentCode());
    userList.buildList(db);
    context.getRequest().setAttribute("UserList", userList);

    ContactList contactList = new ContactList();
    //contactList.setTypeId(Integer.parseInt(typeId));
    contactList.setBuildDetails(false);
    contactList.setOrgId(Integer.parseInt(context.getRequest().getParameter("orgId")));
    contactList.buildList(db);
    context.getRequest().setAttribute("ContactList", contactList);

    TicketCategoryList subList1 = new TicketCategoryList();

    subList1.setCatLevel(1);
    subList1.setParentCode(newTic.getCatCode());
    //subList1.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=AddTicket&auto-populate=true&refresh=2#categories';document.forms[0].submit()");
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

    //subList2.setHtmlJsEvent("onChange = javascript:document.forms[0].action='/AccountTickets.do?command=AddTicket&auto-populate=true&refresh=3#categories';document.forms[0].submit()");
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

