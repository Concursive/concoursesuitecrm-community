<%
  Ticket thisTicket = (Ticket)request.getAttribute("TicketDetails");
  if (thisTicket == null)
    thisTicket = (Ticket)request.getAttribute("ticketDetails");
%>
<table width="100%" border="0">
  <tr>
    <td width="33%" valign="top" nowrap>
      <strong>Ticket #</strong> <%= thisTicket.getPaddedId() %><br />
      <%= toHtml(thisTicket.getCompanyName()) %>
      <dhv:evaluate if="<%= !(thisTicket.getCompanyEnabled()) %>"><font color="red">(account disabled)</font></dhv:evaluate>
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
            <dhv:tz timestamp="<%= thisTicket.getClosed() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
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
      <a href="TroubleTickets.do?command=PrintReport&id=<%= thisTicket.getId() %>">Printable Ticket Form</a>
    </td>
  </tr>
</table>