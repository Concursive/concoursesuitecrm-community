/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.search.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * The Search module.
 *
 * @author mrajkowski
 * @version $Id$
 * @created July 12, 2001
 */
public final class Search extends CFSModule {

  /**
   * Currently does nothing important.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandSiteSearch(ActionContext context) {
    int ITEMS_PER_PAGE = 15;
    Exception errorMessage = null;
    Connection db = null;
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    PagedListInfo contactSearchInfo = this.getPagedListInfo(
        context, "SearchSiteContactInfo");

    PagedListInfo employeeSearchInfo = this.getPagedListInfo(
        context, "SearchSiteEmployeeInfo");

    PagedListInfo leadsSearchInfo = this.getPagedListInfo(
        context, "SearchSiteLeadInfo");

    PagedListInfo accountSearchInfo = this.getPagedListInfo(
        context, "SearchSiteAccountInfo");

    PagedListInfo opportuntySearchInfo = this.getPagedListInfo(
        context, "SearchSiteOppInfo");

    PagedListInfo ticSearchInfo = this.getPagedListInfo(
        context, "SearchSiteTicketInfo");
    if (context.getRequest().getParameter("resetList") != null && context.getRequest().getParameter(
        "resetList").equals("true")) {
      opportuntySearchInfo.setExpandedSelection(false);
      ticSearchInfo.setExpandedSelection(false);
      accountSearchInfo.setExpandedSelection(false);
      leadsSearchInfo.setExpandedSelection(false);
      employeeSearchInfo.setExpandedSelection(false);
      contactSearchInfo.setExpandedSelection(false);
    }
    try {
      db = this.getConnection(context);
      if (hasPermission(context, "contacts-external_contacts-view") || hasPermission(
          context, "accounts-accounts-contacts-view")) {
        contactSearchInfo.setLink("Search.do?command=SiteSearch");
        if (sectionId == null) {
          if (!contactSearchInfo.getExpandedSelection()) {
            if (contactSearchInfo.getItemsPerPage() != ITEMS_PER_PAGE) {
              contactSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);
            }
          }
        } else if (sectionId.equals(contactSearchInfo.getId())) {
          if (!contactSearchInfo.getExpandedSelection()){
            contactSearchInfo.setExpandedSelection(true);
          }
        }

        ContactList contactList = new ContactList();
        contactList.setPagedListInfo(contactSearchInfo);
        contactSearchInfo.setSearchCriteria(contactList, context);

        contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
        contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
        contactList.setAllContacts(
            true, this.getUserId(context), this.getUserRange(context));
        contactList.setBuildDetails(true);
        contactList.setBuildTypes(false);
        contactList.setSiteId(UserUtils.getUserSiteId(context.getRequest()));
        contactList.setExclusiveToSite(true);
        if (UserUtils.getUserSiteId(context.getRequest()) == -1) {
          contactList.setIncludeAllSites(true);
        }
        
        if (sectionId == null || contactSearchInfo.getExpandedSelection()) {
          contactList.buildList(db);
        }
        context.getRequest().setAttribute("ContactList", contactList);
      }

      if (hasPermission(context, "contacts-internal_contacts-view")) {
        employeeSearchInfo.setLink("Search.do?command=SiteSearch");
        if (sectionId == null) {
          if (!employeeSearchInfo.getExpandedSelection()) {
            if (employeeSearchInfo.getItemsPerPage() != ITEMS_PER_PAGE) {
              employeeSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);
            }
          }
        } else if (sectionId.equals(employeeSearchInfo.getId())) {
          if (!employeeSearchInfo.getExpandedSelection()){
            employeeSearchInfo.setExpandedSelection(true);
          }
        }

        ContactList employeeList = new ContactList();
        employeeList.setPagedListInfo(employeeSearchInfo);
        employeeSearchInfo.setSearchCriteria(employeeList, context);

        employeeList.setEmployeesOnly(Constants.TRUE);
        employeeList.setPersonalId(ContactList.IGNORE_PERSONAL);
        employeeList.setBuildDetails(true);
        employeeList.setBuildTypes(false);
        employeeList.setSiteId(UserUtils.getUserSiteId(context.getRequest()));
        employeeList.setExclusiveToSite(true);
        if (UserUtils.getUserSiteId(context.getRequest()) == -1) {
          employeeList.setIncludeAllSites(true);
        }
        if (sectionId == null || employeeSearchInfo.getExpandedSelection()) {
          employeeList.buildList(db);
        }
        context.getRequest().setAttribute("EmployeeList", employeeList);
      }
      
      if (hasPermission(context, "sales-view")) {
        leadsSearchInfo.setLink("Search.do?command=SiteSearch");
        if (sectionId == null) {
          if (!leadsSearchInfo.getExpandedSelection()) {
            if (leadsSearchInfo.getItemsPerPage() != ITEMS_PER_PAGE) {
              leadsSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);
            }
          }
        } else if (sectionId.equals(leadsSearchInfo.getId())) {
          if (!leadsSearchInfo.getExpandedSelection()){
            leadsSearchInfo.setExpandedSelection(true);
          }
        }

        ContactList leadsList = new ContactList();
        leadsList.setPagedListInfo(leadsSearchInfo);
        leadsSearchInfo.setSearchCriteria(leadsList, context);

        leadsList.setLeadsOnly(Constants.TRUE);
        leadsList.setBuildDetails(true);
        leadsList.setBuildTypes(false);
        leadsList.setControlledHierarchyOnly(true, this.getUserRange(context));
        leadsList.setSiteId(UserUtils.getUserSiteId(context.getRequest()));
        leadsList.setExclusiveToSite(true);
        if (UserUtils.getUserSiteId(context.getRequest()) == -1) {
          leadsList.setIncludeAllSites(true);
        }
        if (sectionId == null || leadsSearchInfo.getExpandedSelection()) {
          leadsList.buildList(db);
        }
        context.getRequest().setAttribute("leadsList", leadsList);
      }
      
      if (hasPermission(context, "accounts-accounts-view")) {
        accountSearchInfo.setLink("Search.do?command=SiteSearch");
        if (sectionId == null) {
          if (!accountSearchInfo.getExpandedSelection()) {
            if (accountSearchInfo.getItemsPerPage() != ITEMS_PER_PAGE) {
              accountSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);
            }
          }
        } else if (sectionId.equals(accountSearchInfo.getId())) {
          if (!accountSearchInfo.getExpandedSelection()){
            accountSearchInfo.setExpandedSelection(true);
          }
        }

        OrganizationList organizationList = new OrganizationList();
        organizationList.setMinerOnly(false);
        organizationList.setPagedListInfo(accountSearchInfo);
        accountSearchInfo.setSearchCriteria(organizationList, context);

        organizationList.setOrgSiteId(UserUtils.getUserSiteId(context.getRequest()));
        if (sectionId == null || accountSearchInfo.getExpandedSelection()) {
          organizationList.buildList(db);
        }
        context.getRequest().setAttribute(
            "OrganizationList", organizationList);
      }

      if (hasPermission(context, "pipeline-opportunities-view")) {
        opportuntySearchInfo.setLink("Search.do?command=SiteSearch");
        if (sectionId == null) {
          if (!opportuntySearchInfo.getExpandedSelection()) {
            if (opportuntySearchInfo.getItemsPerPage() != ITEMS_PER_PAGE) {
              opportuntySearchInfo.setItemsPerPage(ITEMS_PER_PAGE);
            }
          }
        } else if (sectionId.equals(opportuntySearchInfo.getId())) {
          if (!opportuntySearchInfo.getExpandedSelection()){
            opportuntySearchInfo.setExpandedSelection(true);
          }
        }

        OpportunityList oppList = new OpportunityList();
        if (UserUtils.getUserSiteId(context.getRequest()) > -1) {
          oppList.setIncludeAllSites(false);
        } else {
          oppList.setIncludeAllSites(true);
          oppList.setSiteId(-1);
        }
        oppList.setPagedListInfo(opportuntySearchInfo);
        opportuntySearchInfo.setSearchCriteria(oppList, context);

        oppList.setOwnerIdRange(this.getUserRange(context));
        if (sectionId == null || opportuntySearchInfo.getExpandedSelection()) {
          oppList.buildList(db);
        }
        context.getRequest().setAttribute("OpportunityList", oppList);
      }

      if (hasPermission(context, "tickets-tickets-view")) {
        ticSearchInfo.setLink("Search.do?command=SiteSearch");
        if (sectionId == null) {
          if (!ticSearchInfo.getExpandedSelection()) {
            if (ticSearchInfo.getItemsPerPage() != ITEMS_PER_PAGE) {
              ticSearchInfo.setItemsPerPage(ITEMS_PER_PAGE);
            }
          }
        } else if (sectionId.equals(ticSearchInfo.getId())) {
          if (!ticSearchInfo.getExpandedSelection()){
            ticSearchInfo.setExpandedSelection(true);
          }
        }

        TicketList ticketList = new TicketList();
        ticketList.setPagedListInfo(ticSearchInfo);
        ticSearchInfo.setSearchCriteria(ticketList, context);

        if (UserUtils.getUserSiteId(context.getRequest()) != -1) {
          ticketList.setIncludeAllSites(false);
        }
        ticketList.setExclusiveToSite(true);
        ticketList.setSiteId(UserUtils.getUserSiteId(context.getRequest()));
        if (sectionId == null || ticSearchInfo.getExpandedSelection()) {
          ticketList.buildList(db);
        }
        context.getRequest().setAttribute("TicketList", ticketList);
      }
      context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
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

