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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.products.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="quoteListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="quotes_list_menu.jsp" %>
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
<script type="text/javascript">
  function reopen(){
    window.location.href='Quotes.do?command=Search';
  }
</script>
<%
  boolean allowMultipleQuote = allowMultipleQuote(pageContext);
  boolean allowMultipleVersion = allowMultipleVersion(pageContext);
%>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> >
      <% if (version != null && !"".equals(version)) { %>
      <a href="Quotes.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> > <dhv:label name="quotes.versionResults">Version Results</dhv:label>
      <% } else { %> <dhv:label name="accounts.SearchResults">Search Results</dhv:label> <% } %>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%-- Begin the container contents --%>
<dhv:permission name="quotes-quotes-add"><a href="Quotes.do?command=AddQuoteForm"><dhv:label name="accounts.accounts_quotes_list.AddAQuote">Add a Quote</dhv:label></a></dhv:permission>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="quoteListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=qe.group_id&version=<%= version %>">
    <%}%>
        <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("qe.group_id") %>
    <%}%>
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=qe.version&version=<%= version %>">
    <%}%>
      <strong><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("qe.version") %>
    <%}%>
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=org.name&version=<%= version %>">
    <%}%>
      <strong><dhv:label name="accounts.account">Account</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("org.name") %>
    <%}%>
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=qe.short_description&version=<%= version %>">
    <%}%>
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("qe.short_description") %>
    <%}%>
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=statusName&version=<%= version %>">
    <%}%>
      <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("statusName") %>
    <%}%>
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=qe.entered&version=<%= version %>">
    <%}%>
      <strong><dhv:label name="accounts.accounts_calls_list.Entered">Created</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("qe.entered") %>
    <%}%>
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=qe.issued&version=<%= version %>">
    <%}%>
      <strong><dhv:label name="quotes.issued">Issued</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("qe.issued") %>
    <%}%>
    </th>
    <th nowrap>
    <% if (version == null || "".equals(version)) { %>
      <a href="Quotes.do?command=Search&column=qe.closed&version=<%= version %>">
    <%}%>
      <strong><dhv:label name="quotes.closed">Closed</dhv:label></strong>
    <% if (version == null || "".equals(version)) { %>
      </a>
      <%= quoteListInfo.getSortIcon("qe.closed") %>
    <%}%>
    </th>
  </tr>
<%
	Iterator j = quoteList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    Quote thisQuote = (Quote)j.next();
    i++;
    rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap>
      <% if(!thisQuote.getLock()){%>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuQuote','<%= thisQuote.getId() %>','<%= version %>','<%= (thisQuote.getClosed() == null) ? "true" : "false" %>','<%= (thisQuote.isTrashed()) %>','<%= thisQuote.getHeaderId() %>',<%= allowMultipleVersion %>,<%= allowMultipleQuote %> );"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuQuote');">
         <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      <% }else{ %>
          <font color="red"><dhv:label name="pipeline.locked">Locked</dhv:label></font>
      <% } %>
    </td>
    <td valign="center" width="10%">
      <a href="Quotes.do?command=Details&quoteId=<%=thisQuote.getId()%>&version=<%= version %>"><%= thisQuote.getPaddedGroupId() %></a>
    </td>
    <td valign="center">
      <%= toHtml(thisQuote.getVersion()) %>
    </td>
    <td valign="center" width="20%">
      <%= toHtml(thisQuote.getName() +(quoteList.getIncludeAllSites() && thisQuote.getSiteName() != null && !"".equals(thisQuote.getSiteName().trim())?" ("+thisQuote.getSiteName()+") ":"")) %>
    </td>
    <td valign="center" width="50%">
      <%= toHtml(thisQuote.getShortDescription()) %>
    </td>
    <td valign="center"nowrap>
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
    <% if(thisQuote.getClosed() != null ){ %>
        <zeroio:tz timestamp="<%= thisQuote.getClosed() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    <% }else{ %>&nbsp;<% } %>
    </td>
  </tr>
<%  }
  } else {%>
  <tr class="containerBody">
    <td colspan="9">
      <dhv:label name="accounts.accounts_quotes_list.NoQuotesFound">No quotes found.</dhv:label>
    </td>
  </tr>
<% } %>
</table>
<br>
<dhv:pagedListControl object="quoteListInfo"/>