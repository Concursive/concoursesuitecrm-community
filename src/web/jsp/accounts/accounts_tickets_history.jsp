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
<jsp:useBean id="OrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="TicketDetails"
             class="org.aspcfs.modules.troubletickets.base.Ticket"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
  <%-- Trails --%>
  <table class="trails" cellspacing="0">
    <tr>
      <td>
        <a href="Accounts.do"><dhv:label
            name="accounts.accounts">Accounts</dhv:label></a> >
        <a href="Accounts.do?command=Search"><dhv:label
            name="accounts.SearchResults">Search Results</dhv:label></a> >
        <a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label
            name="accounts.details">Account Details</dhv:label></a> >
        <a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label
            name="accounts.tickets.tickets">Tickets</dhv:label></a> >
        <a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label
            name="accounts.tickets.details">Ticket Details</dhv:label></a> >
        <dhv:label name="accountsassets.history.long_html">History</dhv:label>
      </td>
    </tr>
  </table>
  <%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="tickets" object="OrgDetails"
               param='<%= "orgId=" + OrgDetails.getOrgId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountstickets" selected="history"
                 object="TicketDetails"
                 param='<%= "id=" + TicketDetails.getId() %>'
                 appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%@ include file="accounts_ticket_header_include_add_note.jsp" %>
    <table cellpadding="4" cellspacing="0" border="0" width="100%"
           class="pagedList">
      <tr>
        <th colspan="4">
          <strong><dhv:label name="accounts.tickets.historyLog">Ticket Log
            History</dhv:label></strong>
        </th>
      </tr>
      <%
        Iterator hist = TicketDetails.getHistory().iterator();
        if (hist.hasNext()) {
          while (hist.hasNext()) {
            TicketLog thisEntry = (TicketLog) hist.next();
      %>
      <% if (thisEntry.getSystemMessage() == true) {%>
      <tr class="row1">
          <% } else { %>
      <tr class="containerBody">
        <%}%>
        <td nowrap valign="top" class="formLabel">
          <dhv:username id="<%= thisEntry.getEnteredBy() %>"/>
        </td>
        <td nowrap valign="top">
          <zeroio:tz timestamp="<%= thisEntry.getEntered() %>"
                     timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
        </td>
        <td valign="top" width="100%">
          <%= toHtml(thisEntry.getEntryText()) %>
        </td>
      </tr>
      <%
        }
      } else {
      %>
      <tr class="containerBody">
        <td>
          <font color="#9E9E9E" colspan="3"><dhv:label
              name="project.tickets.noLogEntries">No Log
            Entries.</dhv:label></font>
        </td>
      </tr>
      <%}%>
    </table>
  </dhv:container>
</dhv:container>
