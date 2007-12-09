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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteDeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> >
      <a href="Quotes.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <% if (version != null && !"".equals(version)) { %> 
      <a href="Quotes.do?command=Search&version=<%= quote.getId() %>"><dhv:label name="quotes.versionResults">Version Results</dhv:label></a> >
      <% } %>
      <a href="Quotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= (version!=null && !"".equals(version))?version:"" %>"><dhv:label name="quotes.quoteDetails">Quote Details</dhv:label></a> >
      <dhv:label name="quotes.history">Quote History</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% String param1 = "quoteId=" + quote.getId() + "|version="+version; %>
<dhv:container name="quotes" selected="history" object="quote" param="<%= param1 %>">
  <%@ include file="quotes_header_include.jsp" %>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="3">
        <strong><dhv:label name="quotes.history">Quote History</dhv:label></strong>
      </th>
    </tr>
<%
      Iterator hist = quote.getHistory().iterator();
      if (hist.hasNext()) {
        while (hist.hasNext()) {
          QuoteLog thisEntry = (QuoteLog)hist.next();
%>
        <% if (thisEntry.getSystemMessage() == true) {%>
      <tr class="row1">
        <% } else { %>
      <tr class="containerBody">
        <%}%>
        <td nowrap valign="top">
          <dhv:username id="<%= thisEntry.getEnteredBy() %>"/>
        </td>
        <td nowrap valign="top">
        <zeroio:tz timestamp="<%= thisEntry.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
        </td>
        <td valign="top" width="100%">
          <%= toHtml(thisEntry.getNotes()) %>
        </td>
      </tr>
  <%
        }
      } else {
    %>
      <tr class="containerBody">
        <td>
          <font color="#9E9E9E" colspan="3"><dhv:label name="quotes.history.noLogEntries">No Log Entries.</dhv:label></font>
        </td>
      </tr>
    <%}%>
  </table>
</dhv:container>