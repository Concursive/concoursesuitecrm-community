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
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="accountQuoteListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_quotes_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<dhv:evaluate if="<%= isPopup(request) %>">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
</dhv:evaluate>
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
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<% if (version != null && !"".equals(version)) { %> 
<a href="AccountQuotes.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_quotes_list.Quotes">Quotes</dhv:label></a> > 
<dhv:label name="quotes.versionList">Version List</dhv:label>
<% } else { %>
<dhv:label name="accounts.accounts_quotes_list.Quotes">Quotes</dhv:label>
<% } %>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="quotes" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
  <dhv:permission name="accounts-quotes-add"><a href="AccountQuotes.do?command=AddQuoteForm&orgId=<%= OrgDetails.getOrgId() %><%= isPopup(request)?"&popup=true&popupType=true":"" %>"/><dhv:label name="accounts.accounts_quotes_list.AddAQuote">Add a Quote</dhv:label></a></dhv:permission>
</dhv:evaluate>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="accountQuoteListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<tr>
  <th width="8">
    &nbsp;
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=qe.group_id&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.group_id") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=qe.version&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.version") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=qe.short_description&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.short_description") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=statusName&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("statusName") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=qe.entered&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="accounts.accounts_calls_list.Entered">Created</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.entered") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=qe.issued&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%}%>
    <strong><dhv:label name="quotes.issued">Issued</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %> 
      </a>
      <%= accountQuoteListInfo.getSortIcon("qe.issued") %>
    <%}%>
  </th>
  <th nowrap>
    <% if (version == null || "".equals(version)) { %> 
      <a href="AccountQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=qe.closed&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
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
		<tr class="row<%= rowid %>">
      <td valign="center" nowrap>
        <% if(!thisQuote.getLock()){%>
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <a href="javascript:displayMenu('select<%= i %>','menuQuote','<%= OrgDetails.getOrgId() %>','<%= thisQuote.getId() %>','<%= version %>','<%= (thisQuote.getClosed() == null) ? "true" : "false" %>','<%= thisQuote.isTrashed() || OrgDetails.isTrashed() %>','<%=thisQuote.getHeaderId()%>',<%=allowMultipleVersion%>,<%=allowMultipleQuote%> );"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuQuote');">
          <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
        <% }else{ %>
          <font color="red"><dhv:label name="pipeline.locked">Locked</dhv:label></font>
        <% } %>
      </td>
      <td valign="center" width="10%">
        <a href="AccountQuotes.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&version=<%= version %>&quoteId=<%= thisQuote.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisQuote.getPaddedGroupId() %></a>
      </td>
      <td valign="center">
        <%= toHtml(thisQuote.getVersion()) %>
      </td>
      <td valign="center" width="50%">
        <%= toHtml(thisQuote.getShortDescription()) %>
      </td>
      <td valign="center" nowrap>
        <%= toHtml(quoteStatusList.getValueFromId(thisQuote.getStatusId())) %>
      </td>
      <td valign="center" width="10%">
        <zeroio:tz timestamp="<%= thisQuote.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
      </td>
      <td valign="center" width="10%">
    <% if(thisQuote.getIssuedDate() != null){ %>
        <zeroio:tz timestamp="<%= thisQuote.getIssuedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    <% }else{ %>&nbsp;<% } %>
      </td>
      <td valign="center" width="10%">
    <% if(thisQuote.getClosed() != null){ %>
        <zeroio:tz timestamp="<%= thisQuote.getClosed() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
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