<%
  Ticket thisTicket = (Ticket)request.getAttribute("TicketDetails");
  if (thisTicket == null)
    thisTicket = (Ticket)request.getAttribute("ticketDetails");
%>
<table width="100%" border="0">
  <tr>
    <td width="33%" nowrap>
      <strong>Ticket #</strong> <%= thisTicket.getPaddedId() %><br />
      <%= toHtml(thisTicket.getCompanyName()) %>
      <dhv:evaluate exp="<%= !(thisTicket.getCompanyEnabled()) %>"><font color="red">(account disabled)</font></dhv:evaluate>
    </td>
    <td width="33%" align="center" valign="top" nowrap>
      <strong>Status:</strong>
      <% if (thisTicket.getClosed() == null){ %>
      "Open" 
      <%}else{%>
      <font color="red">Closed on <%= toHtml(thisTicket.getClosedString()) %></font>
      <%}%>
    </td>
    <td width="33%" align="right" valign="top" nowrap>
      <strong>Hours Remaining:</strong>
    <% if (thisTicket.getContractId() == -1) { %>
    "Contract Not Specified"
    <%}else{%>
    <%=thisTicket.getTotalHoursRemaining()%>
    <%}%>
    </td>
  </tr>
</table>