package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.webutils.PagedListInfo;
import java.sql.*;

/**
 *  The Search module.
 *
 *@author     mrajkowski
 *@created    July 12, 2001
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
    Exception errorMessage = null;
    
    PagedListInfo searchSiteInfo = this.getPagedListInfo(context, "SearchSiteInfo");
    searchSiteInfo.setLink("/ExternalContacts.do?command=ListContacts");

    String searchCriteria = context.getRequest().getParameter("search");

    Connection db = null;
    
    ContactList contactList = new ContactList();
    ContactList employeeList = new ContactList();
    OrganizationList organizationList = new OrganizationList();
    OpportunityList oppList = new OpportunityList();
    TicketList ticList = new TicketList();

    //this is search stuff
      
    if (searchCriteria != null && !(searchCriteria.equals(""))) {
      searchCriteria = "%" + searchCriteria + "%";
      //contactList.setFirstName(searchCriteria);
      //employeeList.setFirstName(searchCriteria);
      
      contactList.setPersonalId(getUserId(context));
      contactList.setSearchText(searchCriteria);
      employeeList.setPersonalId(getUserId(context));
      employeeList.setSearchText(searchCriteria);
      
      organizationList.setName(searchCriteria);
      oppList.setDescription(searchCriteria);
      
      ticList.setSearchText(searchCriteria);
    }
      
    //end search stuff
    
    try {
      db = this.getConnection(context);

      contactList.setPagedListInfo(searchSiteInfo);
      //contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
      contactList.setOwnerIdRange(this.getUserRange(context));
      
      //if ("all".equals(externalContactsInfo.getListView())) {
      //  contactList.setOwnerIdRange(this.getUserRange(context));
      //} else {
      //  contactList.setOwner(this.getUserId(context));
      //}
      
      contactList.buildList(db);
      
      employeeList.setTypeId(1);
      employeeList.buildList(db);
      
      organizationList.setMinerOnly(false);
      organizationList.buildList(db);
      
      oppList.setOwnerIdRange(this.getUserRange(context));
      oppList.buildList(db);
      
      ticList.buildList(db);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "Search", "Search Results");
      context.getRequest().setAttribute("ContactList", contactList);
      context.getRequest().setAttribute("EmployeeList", employeeList);
      context.getRequest().setAttribute("OrganizationList", organizationList);
      context.getRequest().setAttribute("OpportunityList", oppList);
      context.getRequest().setAttribute("TicketList", ticList);
      return ("SearchOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
    

    //Populate the SearchBean
    //SearchResultsBean thisBean = new SearchResultsBean();
    //thisBean.setFirstName(thisUser.getFirstName());
    //thisBean.setLastName(thisUser.getLastName());

    //Put it in the request
    //context.getRequest().setAttribute("SearchResultsBean", thisBean);

    //return ("SearchOK");
  }

}

