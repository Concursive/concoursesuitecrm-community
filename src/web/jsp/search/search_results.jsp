<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.modules.accounts.base.Organization" %>
<%@ page import="org.aspcfs.modules.base.EmailAddress" %>
<%@ page import="org.aspcfs.modules.base.PhoneNumber" %>
<%@ page import="org.aspcfs.modules.contacts.base.Contact" %> 
<%@ page import="org.aspcfs.modules.pipeline.beans.OpportunityBean" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.Ticket" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.aspcfs.modules.pipeline.base.OpportunityComponent"%>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="EmployeeList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="leadsList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="OrganizationList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="TicketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="SearchSiteContactInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SearchSiteEmployeeInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SearchSiteLeadInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SearchSiteAccountInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SearchSiteOppInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SearchSiteTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%
  String searchText = "searchText=" + toHtml((String)SearchSiteContactInfo.getSavedCriteria().get("searchSearchText"));
  String paramSearchText = "&searchSearchText=" + toHtml((String)SearchSiteContactInfo.getSavedCriteria().get("searchSearchText"));
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>
<dhv:label name="search.results.searchString" param="<%= searchText %>">Your search for <b><%= toHtml(request.getParameter("searchSearchText")) %></b> returned:</dhv:label>
<br />&nbsp;<br /> 
<dhv:permission name="contacts-external_contacts-view,accounts-accounts-contacts-view">
<%
Iterator i = ContactList.iterator();
if (i.hasNext()) {
  if ((request.getParameter("pagedListSectionId") == null && !(SearchSiteTicketInfo.getExpandedSelection()) &&!(SearchSiteOppInfo.getExpandedSelection()) && !(SearchSiteAccountInfo.getExpandedSelection()) && !(SearchSiteLeadInfo.getExpandedSelection()) && !(SearchSiteEmployeeInfo.getExpandedSelection())) || SearchSiteContactInfo.getExpandedSelection()) {
%>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Contacts" type="accounts.Contacts" object="SearchSiteContactInfo" params="<%=paramSearchText%>" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
    </th>
    <th width="100%">
      <strong><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></strong>
    </th>
    <th width="100" nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></strong>
    </th>
    <th width="100">
      <strong><dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label></strong>
    </th>
    <th width="100">
      <strong><dhv:label name="accounts.accounts_add.Email">Email</dhv:label></strong>
    </th>
  </tr>
<%    
	int rowid = 0;
		while (i.hasNext()) {
      rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)i.next();
%>
      <tr class="row<%= rowid %>">
        <td nowrap>
          <%if(thisContact.getOrgId() < 0){%>
          <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameFull()) %></a>
          <%}else{%>
          <a href="Contacts.do?command=Details&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <%}%>
          <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\""+systemStatus.getLabel("alt.sendEmail","Send Email")+"\" align=\"absmiddle\">", "") %>
          <dhv:evaluate if="<%= thisContact.getOrgId() > 0 %>">
            [<a href="Accounts.do?command=Details&orgId=<%=  thisContact.getOrgId() %>"><dhv:label name="accounts.account">Account</dhv:label></a>]
          </dhv:evaluate>
        </td>
        <td>
          <%= toHtml(thisContact.getCompany()) %>
        </td>
        <td nowrap>
          <%= toHtml(thisContact.getTitle()) %>
        </td>
        <td nowrap>
          <% if (thisContact.getPhoneNumberList().size() > 1) { %>
            <%= thisContact.getPhoneNumberList().getHtmlSelect("contactphone", -1) %>
        <% } else if (thisContact.getPhoneNumberList().size() == 1) { 
             PhoneNumber thisNumber = (PhoneNumber) thisContact.getPhoneNumberList().get(0);
         %>
             <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + thisNumber.getNumber() %>
        <%}else{%>
          &nbsp;
        <%}%>
        </td>
        <td nowrap>
          <% if (thisContact.getEmailAddressList().size() > 1) { %>
            <%= thisContact.getEmailAddressList().getHtmlSelect("contactemail", -1) %>
        <% } else if (thisContact.getEmailAddressList().size() == 1) { 
             EmailAddress thisAddress = (EmailAddress) thisContact.getEmailAddressList().get(0);
         %>
             <%= String.valueOf(thisAddress.getTypeName().charAt(0)) + ":" + toHtml(thisAddress.getEmail()) %>
        <%}else{%>
          &nbsp;
        <%}%>
        </td>
      </tr>
<%}%>
</table>
  <% if (SearchSiteContactInfo.getExpandedSelection()) {%>
<br />
<dhv:pagedListControl object="SearchSiteContactInfo" tdClass="row1"/>
<%   }
   }
 }%>
</dhv:permission>
<dhv:permission name="contacts-internal_contacts-view">
<%
  Iterator j = EmployeeList.iterator();
  if (j.hasNext()) {
  if ((request.getParameter("pagedListSectionId") == null && !(SearchSiteTicketInfo.getExpandedSelection()) &&!(SearchSiteOppInfo.getExpandedSelection()) && !(SearchSiteAccountInfo.getExpandedSelection()) && !(SearchSiteLeadInfo.getExpandedSelection()) && !(SearchSiteContactInfo.getExpandedSelection())) || SearchSiteEmployeeInfo.getExpandedSelection()) {
    int rowid = 0;
%>
<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Employees" type="employees.employees" object="SearchSiteEmployeeInfo" params="<%=paramSearchText%>" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
    </th>
    <th width="175">
      <strong><dhv:label name="project.department">Department</dhv:label></strong>
    </th>
    <th width="100">
      <strong><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></strong>
    </th>
    <th width="100" nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_detailsimport.PhoneBusiness">Phone: Business</dhv:label></strong>
    </th>
  </tr>
<%    
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      Contact thisEmployee = (Contact)j.next();
%>
      <tr class="row<%= rowid %>">
        <td>
          <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>"><%= toHtml(thisEmployee.getNameLastFirst()) %></a>
          <%= thisEmployee.getEmailAddressTag("Business", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\""+systemStatus.getLabel("alt.sendEmail","Send Email")+"\" align=\"absmiddle\">", "") %>
        </td>
        <td>
          <%= toHtml(thisEmployee.getDepartmentName()) %>
        </td>
        <td>
          <%= toHtml(thisEmployee.getTitle()) %>
        </td>
        <td>
          <%= toHtml(thisEmployee.getPhoneNumber("Business")) %>
        </td>
      </tr>
<%      
    }
%>
</table>
  <% if (SearchSiteEmployeeInfo.getExpandedSelection()) {%>
<br>
<dhv:pagedListControl object="SearchSiteEmployeeInfo" tdClass="row1"/>
<%    }
    }
 }%>
</dhv:permission>
<dhv:permission name="sales-view">
<%
  Iterator w = leadsList.iterator();
  if (w.hasNext()) {
  if ((request.getParameter("pagedListSectionId") == null && !(SearchSiteTicketInfo.getExpandedSelection()) &&!(SearchSiteOppInfo.getExpandedSelection()) && !(SearchSiteAccountInfo.getExpandedSelection()) && !(SearchSiteEmployeeInfo.getExpandedSelection()) && !(SearchSiteContactInfo.getExpandedSelection())) || SearchSiteLeadInfo.getExpandedSelection()) {
    int rowid = 0;
%>
<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Leads" type="sales.leads" object="SearchSiteLeadInfo" params="<%=paramSearchText%>" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="50%">
      <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
    </th>
    <th width="50%">
      <strong><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></strong>
    </th>
    <th width="100" nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></strong>
    </th>
    <th width="100">
      <strong><dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label></strong>
    </th>
    <th width="100">
      <strong><dhv:label name="accounts.accounts_add.Email">Email</dhv:label></strong>
    </th>
  </tr>
<%    
    while (w.hasNext()) {
      rowid = (rowid != 1?1:2);
      Contact thisLead = (Contact) w.next();
%>
      <tr class="row<%= rowid %>">
        <td>
          <dhv:evaluate if="<%= hasText(thisLead.getNameLastFirst()) %>">
            <a href="Sales.do?command=Details&contactId=<%= thisLead.getId() %>"><%= toHtml(thisLead.getNameLastFirst()) %></a>
            <%= thisLead.getEmailAddressTag("Business", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\""+systemStatus.getLabel("alt.sendEmail","Send Email")+"\" align=\"absmiddle\">", "") %>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !hasText(thisLead.getNameLastFirst()) %>">
            --
          </dhv:evaluate>
        </td>
        <td>
          <dhv:evaluate if="<%= hasText(thisLead.getNameLastFirst()) %>">
            <%= toHtml(thisLead.getCompany()) %>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !hasText(thisLead.getNameLastFirst()) %>">
            <a href="Sales.do?command=Details&contactId=<%= thisLead.getId() %>"><%= toHtml(thisLead.getCompany()) %></a>
          </dhv:evaluate>
        </td>
        <td nowrap>
          <%= toHtml(thisLead.getTitle()) %>
        </td>
        <td nowrap>
          <% if (thisLead.getPhoneNumberList().size() > 1) { %>
            <%= thisLead.getPhoneNumberList().getHtmlSelect("leadphone", -1) %>
        <% } else if (thisLead.getPhoneNumberList().size() == 1) { 
             PhoneNumber thisNumber = (PhoneNumber) thisLead.getPhoneNumberList().get(0);
         %>
             <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + thisNumber.getNumber() %>
        <%}else{%>
          &nbsp;
        <%}%>
        </td>
        <td nowrap>
          <% if (thisLead.getEmailAddressList().size() > 1) { %>
            <%= thisLead.getEmailAddressList().getHtmlSelect("leademail", -1) %>
        <% } else if (thisLead.getEmailAddressList().size() == 1) { 
             EmailAddress thisAddress = (EmailAddress) thisLead.getEmailAddressList().get(0);
         %>
             <%= String.valueOf(thisAddress.getTypeName().charAt(0)) + ":" + toHtml(thisAddress.getEmail()) %>
        <%}else{%>
          &nbsp;
        <%}%>
        </td>
      </tr>
<%      
    }
%>
</table>
  <% if (SearchSiteLeadInfo.getExpandedSelection()) {%>
<br>
  <dhv:pagedListControl object="SearchSiteLeadInfo" tdClass="row1"/>
<%
      }
    }
 }%>
</dhv:permission>
<dhv:permission name="accounts-accounts-view">
<%
  Iterator k = OrganizationList.iterator();
  if (k.hasNext()) {
  if ((request.getParameter("pagedListSectionId") == null && !(SearchSiteTicketInfo.getExpandedSelection()) &&!(SearchSiteOppInfo.getExpandedSelection()) && !(SearchSiteLeadInfo.getExpandedSelection()) && !(SearchSiteEmployeeInfo.getExpandedSelection()) && !(SearchSiteContactInfo.getExpandedSelection())) || SearchSiteAccountInfo.getExpandedSelection()) {
    int rowid = 0;
%>
<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Accounts" type="accounts.accounts" object="SearchSiteAccountInfo" params="<%=paramSearchText%>" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="100%">
      <strong><dhv:label name="organization.name">Account Name</dhv:label></strong>
    </th>
    <dhv:include name="sitesearch-account-email" none="true">
    <th width="175">
      <strong><dhv:label name="accounts.accounts_add.Email">Email</dhv:label></strong>
    </th>
    </dhv:include>
    <th width="100">
      <strong><dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label></strong>
    </th>
  </tr>
<%    
    while (k.hasNext()) {
      rowid = (rowid != 1?1:2);
      Organization thisOrg = (Organization)k.next();
%>
  <tr class="row<%= rowid %>">
		<td>
      <a href="Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
		</td>
    <dhv:include name="sitesearch-account-email" none="true">
		<td valign="center" nowrap>
      <dhv:evaluate if="<%= !"".equals(thisOrg.getEmailAddressList().getPrimaryEmailAddress()) %>">
        <a href="mailto:<%= toHtml(thisOrg.getEmailAddressList().getPrimaryEmailAddress()) %>"><%= toHtml(thisOrg.getEmailAddressList().getPrimaryEmailAddress()) %></a>
      </dhv:evaluate>&nbsp;
    </td>
    </dhv:include>
		<td valign="center" nowrap>
      <% if (thisOrg.getPhoneNumberList().size() > 1) { %>
            <%= thisOrg.getPhoneNumberList().getHtmlSelect("contactphone", -1) %>
      <% } else if (thisOrg.getPhoneNumberList().size() == 1) { 
           PhoneNumber thisNumber = (PhoneNumber) thisOrg.getPhoneNumberList().get(0);
       %>
           <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + thisNumber.getNumber() %>
      <%}else{%>
          &nbsp;
        <%}%>
    </td>
  </tr>
<%
    }
%>
</table>
  <% if (SearchSiteAccountInfo.getExpandedSelection()) {%>
<br>
  <dhv:pagedListControl object="SearchSiteAccountInfo" tdClass="row1"/>
<%
      }
    }
 }%>
</dhv:permission>
<dhv:permission name="pipeline-opportunities-view">
<%
  Iterator m = OpportunityList.iterator();
  if (m.hasNext()) {
    if ((request.getParameter("pagedListSectionId") == null && !(SearchSiteTicketInfo.getExpandedSelection()) &&!(SearchSiteAccountInfo.getExpandedSelection()) && !(SearchSiteLeadInfo.getExpandedSelection()) && !(SearchSiteEmployeeInfo.getExpandedSelection()) && !(SearchSiteContactInfo.getExpandedSelection())) || SearchSiteOppInfo.getExpandedSelection()) {
    int rowid = 0;
%>
<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Opportunities" type="accounts.accounts_contacts_oppcomponent_add.Opportunities" object="SearchSiteOppInfo" params="<%=paramSearchText%>" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      <strong><dhv:label name="quotes.opportunity">Opportunity</dhv:label></strong>
    </th>
    <th width="175" valign="center">
      <strong><dhv:label name="documents.details.organization">Organization</dhv:label></strong>
    </th>
    <th width="175" valign="center">
      <strong><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></strong>
    </th>
    <th width="100" valign="center">
      <strong><dhv:label name="accounts.accounts_revenue_add.Amount">Amount</dhv:label></strong>
    </th>
    <th width="100" valign="center" nowrap>
      <strong><dhv:label name="pipeline.revenueStart">Revenue Start</dhv:label></strong>
    </th>
  </tr>
  <%
    while (m.hasNext()) {
			rowid = (rowid != 1?1:2);
      OpportunityBean thisOpp = (OpportunityBean) m.next();
%>
	<tr class="row<%= rowid %>">
      <td valign="center">
        <dhv:evaluate if="<%= thisOpp.getHeader().getContactLink() > 0 %>">
          <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= thisOpp.getHeader().getId() %>&contactId=<%= thisOpp.getHeader().getContactLink() %>">
          <dhv:evaluate if="<%= allowMultiple %>">
            <%= toHtml(thisOpp.getHeader().getDescription()) %>: <%= toHtml(thisOpp.getComponent().getDescription()) %>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !allowMultiple %>">
            <%= toHtml(thisOpp.getHeader().getDescription()) %>
          </dhv:evaluate>
          </a>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisOpp.getHeader().getContactLink() <= 0 %>">
          <a href="Leads.do?command=DetailsOpp&headerId=<%= thisOpp.getHeader().getId() %>">
          <dhv:evaluate if="<%= allowMultiple %>">
            <%= toHtml(thisOpp.getHeader().getDescription()) %>: <%= toHtml(thisOpp.getComponent().getDescription()) %>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !allowMultiple %>">
            <%= toHtml(thisOpp.getHeader().getDescription()) %>
          </dhv:evaluate>
          </a>
        </dhv:evaluate>
      </td>
      <td valign="center">
<%
      if (thisOpp.getHeader().getAccountLink() > -1) {
%>        
        <a href="Opportunities.do?command=View&orgId=<%= thisOpp.getHeader().getAccountLink() %>">
<%
      }
%>        
        <%= toHtml(thisOpp.getHeader().getAccountName()) %>
<%
      if (thisOpp.getHeader().getAccountLink() > -1) {
%>     
        </a>
<%
      }
%>        
      </td>
      <td valign="center">
<%
      if (thisOpp.getHeader().getContactLink() > -1) {
%>        
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= thisOpp.getHeader().getId() %>&contactId=<%= thisOpp.getHeader().getContactLink() %>">
<%
      }
%>        
        <%= toHtml(thisOpp.getHeader().getDisplayName()) %>
<%
      if (thisOpp.getHeader().getContactLink() > -1) {
%>     
        </a>
<%
      }
%>        
      </td>
      <td valign="center" nowrap>
        <zeroio:currency value="<%= thisOpp.getComponent().getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
      <td valign="center" nowrap>
        <%= toHtml(thisOpp.getComponent().getCloseDateString()) %>
      </td>
    </tr>
<%}%>
</table>
  <% if (SearchSiteOppInfo.getExpandedSelection()) {%>
<br>
  <dhv:pagedListControl object="SearchSiteOppInfo" tdClass="row1"/>
<%
    }
  }
 }%>
</dhv:permission>
<dhv:permission name="tickets-tickets-view">
<%
  Iterator n = TicketList.iterator();
  if (n.hasNext()) {
    if ((request.getParameter("pagedListSectionId") == null && !(SearchSiteOppInfo.getExpandedSelection()) &&!(SearchSiteAccountInfo.getExpandedSelection()) && !(SearchSiteLeadInfo.getExpandedSelection()) && !(SearchSiteEmployeeInfo.getExpandedSelection()) && !(SearchSiteContactInfo.getExpandedSelection())) || SearchSiteTicketInfo.getExpandedSelection()) {
    int rowid = 0;
%>
<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Tickets" type="tickets.tickets" object="SearchSiteTicketInfo" params="<%=paramSearchText%>" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    </th>
    <th><b><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></b></th>
    <th><b><dhv:label name="ticket.age">Age</dhv:label></b></th>
    <th><b><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></b></th>
    <th><b><dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label></b></th>
  </tr>
  <%
  	while (n.hasNext()) {
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket) n.next();
%>
  <tr class="row<%= rowid %>">
		<td rowspan="2" width="15" valign="top" nowrap>
      <a href="TroubleTickets.do?command=Details&id=<%=thisTic.getId()%>"><%=thisTic.getPaddedId()%></a>
		</td>
		<td width="10" valign="top" nowrap>
      <%= toHtml(thisTic.getPriorityName()) %>
		</td>
		<td width="8%" align="right" valign="top" nowrap>
      <%= thisTic.getAgeOf() %>
		</td>
		<td width="90%" valign="top">
      <%= toHtml(thisTic.getCompanyName()) %>
		</td>
    <td width="150" nowrap valign="top">
      <dhv:username id="<%= thisTic.getAssignedTo() %>" default="ticket.unassigned.text"/>
    </td>
  </tr>
  <tr class="row<%= rowid %>">
		<td colspan="4" valign="top">
      <%= toHtml(thisTic.getProblemHeader()) %>
		</td>
  </tr>
<%}%>
</table>
  <% if (SearchSiteTicketInfo.getExpandedSelection()) {%>
<br>
  <dhv:pagedListControl object="SearchSiteTicketInfo" tdClass="row1"/>
<%
  }
 }
}%>
</dhv:permission>
