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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteDeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= "dashboard".equals(request.getParameter("viewSource")) %>' == 'true') {
      scrollReload('Leads.do?command=Dashboard');
    } else {
      scrollReload('Leads.do?command=Search');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> > 
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > 
<% }else{ %>
	<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> > 
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= quote.getHeaderId() %>&reset=true<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> > 
<a href="LeadsQuotes.do?command=QuotesList&headerId=<%= opportunityHeader.getId() %>&reset=true<%= addLinkParams(request, "viewSource") %>"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> > 
<a href="LeadsQuotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= (version!=null && !"".equals(version))?version:"" %>"><dhv:label name="quotes.quoteDetails">Quote Details</dhv:label></a> > 
<dhv:label name="quotes.history">Quote History</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<dhv:container name="opportunities" selected="quotes" object="opportunityHeader" param="<%= "id=" + opportunityHeader.getId() %>" appendToUrl="<%= addLinkParams(request, "viewSource") %>">
  <dhv:container name="opportunitiesQuotes" selected="history" object="quote" param="<%= "quoteId=" + quote.getId() + "|version=" + version %>" appendToUrl="<%= addLinkParams(request, "viewSource") %>">
    <%@ include file="../quotes/quotes_header_include.jsp" %>
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
            <%= toHtml(thisEntry.getLastName() +", "+ thisEntry.getFirstName())  %>
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
</dhv:container>

