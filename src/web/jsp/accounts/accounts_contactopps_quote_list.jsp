<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="opportunity" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="accountQuoteListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contactopps_quotes_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%
  boolean allowMultipleQuote = allowMultipleQuote(pageContext);
  boolean allowMultipleVersion = allowMultipleVersion(pageContext);
%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= opportunity.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="">Opportunity Details</dhv:label></a> >
<% if (version != null && !"".equals(version)) { %> 
<a href="AccountContactsOppQuotes.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&contactId=<%= ContactDetails.getId() %>&headerId=<%=opportunity.getId() %>&version="><dhv:label name="accounts.accounts_quotes_list.Quotes">Quotes</dhv:label></a> > 
<dhv:label name="quotes.versionList">Version List</dhv:label>
<% } else { %>
<dhv:label name="accounts.accounts_quotes_list.Quotes">Quotes</dhv:label>
<% } %>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscontacts" selected="opportunities" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <dhv:container name="accountcontactopportunities" selected="quotes" object="opportunity" param='<%= "headerId=" + quoteList.getHeaderId() + "|" + "orgId=" + OrgDetails.getOrgId() + "|" + "contactId=" + ContactDetails.getId()%>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
    <dhv:evaluate if='<%= (allowMultipleQuote) || (quoteList.size() == 0)%>' >    
      <a href="AccountContactsOppQuotes.do?command=AddQuoteForm&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"/><dhv:label name="accounts.accounts_quotes_list.AddAQuote">Add a Quote</dhv:label></a>
    </dhv:evaluate>
  </dhv:permission>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="accountQuoteListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<tr>
  <th width="8">
    &nbsp;
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&column=qe.group_id&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.group_id") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&column=qe.version&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.version") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&column=qe.short_description&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.short_description") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&column=statusName&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("statusName") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&column=qe.entered&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accounts_calls_list.Entered">Created</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.entered") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&column=qe.issued&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="quotes.issued">Issued</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.issued") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&column=qe.closed&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="quotes.closed">Closed</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.closed") %>
    <%}%>
  </th>
</tr>
<%
	Iterator j = quoteList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Quote thisQuote = (Quote)j.next();
    String status = quoteStatusList.getValueFromId(thisQuote.getStatusId());
%>
		<tr class="containerBody">
      <td valign="center" nowrap class="row<%= rowid %>">
        <% if(!thisQuote.getLock()){%>
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
           <a href="javascript:displayMenu('select<%= i %>','menuQuote','<%= OrgDetails.getOrgId() %>','<%= thisQuote.getId() %>','<%= ContactDetails.getId() %>','<%=quoteList.getHeaderId() %>','<%= version %>','<%= (thisQuote.getClosed() == null) ? "true" : "false" %>','<%= (thisQuote.isTrashed()) %>','<%=allowMultipleQuote%>','<%=allowMultipleVersion%>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuQuote');">
           <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
       <% }else{ %>
          <font color="red"><dhv:label name="pipeline.locked">Locked</dhv:label></font>
       <% } %>
      </td>
      <td valign="center" class="row<%= rowid %>" width="10%">
        <a href="AccountContactsOppQuotes.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteList.getHeaderId() %>&version=<%= version %>&quoteId=<%= thisQuote.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisQuote.getPaddedGroupId() %></a>
      </td>
      <td valign="center" class="row<%= rowid %>">
        <%= toHtml(thisQuote.getVersion()) %>
      </td>
      <td valign="center" width="50%" class="row<%= rowid %>">
        <%= toHtml(thisQuote.getShortDescription()) %>
      </td>
      <td valign="center" class="row<%= rowid %>" nowrap>
        <%= toHtml(quoteStatusList.getValueFromId(thisQuote.getStatusId())) %>
      </td>
      <td valign="center" width="10%" class="row<%= rowid %>">
        <dhv:tz timestamp="<%= thisQuote.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
      </td>
      <td valign="center" width="10%" class="row<%= rowid %>">
    <% if(thisQuote.getIssuedDate() != null){ %>
        <dhv:tz timestamp="<%= thisQuote.getIssuedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    <% }else{ %>&nbsp;<% } %>
      </td>
      <td valign="center" width="10%" class="row<%= rowid %>">
    <% if(thisQuote.getClosed() != null){ %>
        <dhv:tz timestamp="<%= thisQuote.getClosed() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    <% }else{ %>&nbsp;<% } %>
      </td>
   </tr>
<% }
  } else {%>
		<tr class="containerBody">
      <td colspan="8">
        <dhv:label name="accounts.accounts_quotes_list.NoQuotesFound">No quotes found.</dhv:label>
      </td>
    </tr>
<% } %>
	</table>
	<br>
  <dhv:pagedListControl object="accountQuoteListInfo"/>
    </dhv:container>
  </dhv:container>
</dhv:container>

