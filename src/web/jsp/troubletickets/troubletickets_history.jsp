<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do">Help Desk</a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home">View Tickets</a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
History
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="history" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
		<td class="containerBack">
      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
        <tr>
          <th colspan="3">
            <strong>Ticket Log History</strong>
          </th>
        </tr>
<%
          Iterator hist = TicketDetails.getHistory().iterator();
          if (hist.hasNext()) {
            while (hist.hasNext()) {
              TicketLog thisEntry = (TicketLog)hist.next();
%>
            <% if (thisEntry.getSystemMessage() == true) {%>
          <tr class="row1">
            <% } else { %>
          <tr class="containerBody">
            <%}%>
            <td nowrap valign="top">
              <%= toHtml(thisEntry.getEnteredByName()) %>
            </td>
            <td nowrap valign="top">
            <zeroio:tz timestamp="<%= thisEntry.getEntered() %>" />
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
              <font color="#9E9E9E" colspan="3">No Log Entries.</font>
            </td>
          </tr>
        <%}%>
      </table>
	</td>
  </tr>
</table>

