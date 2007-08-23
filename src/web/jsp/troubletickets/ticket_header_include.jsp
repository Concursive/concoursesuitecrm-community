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
<%@ page import="org.aspcfs.modules.base.Constants, org.aspcfs.modules.troubletickets.base.Ticket" %>
<%
  Ticket thisTicket = (Ticket)request.getAttribute("TicketDetails");
  if (thisTicket == null) {
    thisTicket = (Ticket)request.getAttribute("ticketDetails");
  }
  if (thisTicket == null) {
    thisTicket = (Ticket) request.getAttribute("ticket");
  }
%>
<table width="100%" border="0">
  <tr>
    <td nowrap width="100%"><table width="100%" class="empty">
        <dhv:evaluate if='<%= thisTicket.getCompanyNameHierarchy() != null && !"".equals(thisTicket.getCompanyNameHierarchy().trim()) %>'>
        <tr><td nowrap width="100%" align="left"><strong><dhv:label name="tickets.parentAccounts.colon">Parent Accounts:</dhv:label></strong> <%= toHtml(thisTicket.getCompanyNameHierarchy()) %></td></tr>
        </dhv:evaluate>
        <tr><td nowrap colspan="2" align="left"><strong><dhv:label name="account.account.colon">Account:</dhv:label></strong> <%= toHtml(thisTicket.getCompanyName()) %></td></tr>
      </table>
    </td>
    <td nowrap align="right">
      <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="TroubleTickets.do?command=PrintReport&id=<%= thisTicket.getId() %>"><dhv:label name="accounts.tickets.print">Printable Ticket Form</dhv:label></a>
    </td>
  </tr>
  <tr>
    <td nowrap width="100%">
      <strong><dhv:label name="quotes.quotes.header.status">Status:</dhv:label></strong>
  <% if (thisTicket.isTrashed()) { %>
        <dhv:label name="global.trashed">Trashed</dhv:label>&nbsp;
        <% if (thisTicket.getClosed() == null){ %>
          (<dhv:label name="quotes.open">Open</dhv:label>)
        <%} else{%>
          (<font color="red">Closed on
            <zeroio:tz timestamp="<%= thisTicket.getClosed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
          </font>)
        <%}%>
  <% } else if (thisTicket.getClosed() == null){ %>
        <dhv:label name="quotes.open">Open</dhv:label>
  <%} else{%>
        <font color="red">Closed on
        <zeroio:tz timestamp="<%= thisTicket.getClosed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
        </font>
  <%}%>
      </td>
    <dhv:evaluate if="<%= thisTicket.getContractId() > -1 %>">
      <td align="right" nowrap>
        <strong><dhv:label name="account.ticket.hoursRemaining.colon">Hours Remaining:</dhv:label></strong>
        <%= thisTicket.getTotalHoursRemaining() %>
        <input type="hidden" name="totalHoursRemaining" value="<%= thisTicket.getTotalHoursRemaining() %>" />
      </td>
    </dhv:evaluate>
  </tr>
</table>
<br />
