<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%
  Ticket thisTicket = (Ticket)request.getAttribute("TicketDetails");
  if (thisTicket == null)
    thisTicket = (Ticket)request.getAttribute("ticketDetails");
%>
<table width="100%" border="0">
  <tr>
    <td width="33%" valign="top" nowrap>
      <strong><dhv:label name="accounts.tickets.symbol.number">Ticket #</dhv:label></strong> <%= thisTicket.getPaddedId() %>
    </td>
    <td width="33%" align="center" valign="top" nowrap>
      <table border="0">
        <tr>
          <td align="right" nowrap>
            <strong>Status:</strong>
          </td>
          <td nowrap>
      <% if (thisTicket.getClosed() == null){ %>
            Open
      <%}else{%>
            <font color="red">Closed on
            <zeroio:tz timestamp="<%= thisTicket.getClosed() %>" />
            </font>
      <%}%>
          </td>
        </tr>
        <dhv:evaluate if="<%= thisTicket.getContractId() > -1 %>">
        <tr>
          <td align="right" nowrap>
            <strong>Hours Remaining:</strong>
          </td>
          <td nowrap>
            <%= thisTicket.getTotalHoursRemaining() %>
          </td>
        </tr>
        </dhv:evaluate>
      </table>
    </td>
    <td width="33%" align="right" valign="top" nowrap>
      <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="TroubleTickets.do?command=PrintReport&id=<%= thisTicket.getId() %>"><dhv:label name="accounts.tickets.print">Printable Ticket Form</dhv:label></a>
    </td>
  </tr>
</table>

