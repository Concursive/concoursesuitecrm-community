/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.search.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.*;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import java.sql.*;

/**
 *  The Search module.
 *
 *@author     mrajkowski
 *@created    July 12, 2001
 *@version    $Id$
 */
public final class Search extends CFSModule {

  /**
   *  Currently does nothing important.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandSiteSearch(ActionContext context) {
    int ITEMS_PER_PAGE = 15;
    Exception errorMessage = null;
    String searchCriteria = context.getRequest().getParameter("search");
    Connection db = null;
    if (searchCriteria != null && !(searchCriteria.equals(""))) {
      searchCriteria = "%" + searchCriteria + "%";
    }
    try {
      db = this.getConnection(context);
      if (hasPermission(context, "contacts-external_contacts-view") || hasPermission(context, "accounts-accounts-contacts-view")) {
        PagedListInfo contactSearchInfo = this.getPagedListInfo(context, "SearchSiteContactInfo");
        contactSearchInfo.setLink("Search.do?command=SiteSearch");
        contactSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);

        ContactList contactList = new ContactList();
        contactList.setSearchText(searchCriteria);
        contactList.setPagedListInfo(contactSearchInfo);
        contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
        contactList.setAllContacts(true, this.getUserId(context), this.getUserRange(context));
        contactList.setBuildDetails(true);
        contactList.setBuildTypes(false);
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }

      if (hasPermission(context, "contacts-internal_contacts-view")) {
        PagedListInfo employeeSearchInfo = this.getPagedListInfo(context, "SearchSiteEmployeeInfo");
        employeeSearchInfo.setLink("Search.do?command=SiteSearch");
        employeeSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);

        ContactList employeeList = new ContactList();
        employeeList.setSearchText(searchCriteria);
        employeeList.setPagedListInfo(employeeSearchInfo);
        employeeList.setEmployeesOnly(true);
        employeeList.setPersonalId(ContactList.IGNORE_PERSONAL);
        employeeList.setBuildDetails(true);
        employeeList.setBuildTypes(false);
        employeeList.buildList(db);
        context.getRequest().setAttribute("EmployeeList", employeeList);
      }

      if (hasPermission(context, "accounts-accounts-view")) {
        PagedListInfo accountSearchInfo = this.getPagedListInfo(context, "SearchSiteAccountInfo");
        accountSearchInfo.setLink("Search.do?command=SiteSearch");
        accountSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);

        OrganizationList organizationList = new OrganizationList();
        organizationList.setName(searchCriteria);
        organizationList.setMinerOnly(false);
        organizationList.setPagedListInfo(accountSearchInfo);
        organizationList.buildList(db);
        context.getRequest().setAttribute("OrganizationList", organizationList);
      }

      if (hasPermission(context, "pipeline-opportunities-view")) {
        PagedListInfo opportuntySearchInfo = this.getPagedListInfo(context, "SearchSiteOppInfo");
        opportuntySearchInfo.setLink("Search.do?command=SiteSearch");
        opportuntySearchInfo.setItemsPerPage(ITEMS_PER_PAGE);

        OpportunityList oppList = new OpportunityList();
        oppList.setDescription(searchCriteria);
        oppList.setPagedListInfo(opportuntySearchInfo);
        oppList.setOwnerIdRange(this.getUserRange(context));
        oppList.buildList(db);
        context.getRequest().setAttribute("OpportunityList", oppList);
      }

      if (hasPermission(context, "tickets-tickets-view")) {
        PagedListInfo ticSearchInfo = this.getPagedListInfo(context, "SearchSiteTicketInfo");
        ticSearchInfo.setLink("Search.do?command=SiteSearch");
        ticSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);

        TicketList ticketList = new TicketList();
        ticketList.setSearchText(searchCriteria);
        ticketList.setPagedListInfo(ticSearchInfo);
        ticketList.buildList(db);
        context.getRequest().setAttribute("TicketList", ticketList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "Search", "Search Results");
      return ("SearchOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }

}

