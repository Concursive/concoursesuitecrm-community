<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="TroubleTickets.do">Tickets</a> > 
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
Ticket Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
    <strong>Ticket # <%= TicketDetails.getPaddedId() %><br>
    <%= toHtml(TicketDetails.getCompanyName()) %></strong>
    <dhv:evaluate exp="<%= !(TicketDetails.getCompanyEnabled()) %>"><font color="red">(account disabled)</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + TicketDetails.getId(); %>
      <dhv:container name="tickets" selected="history" param="<%= param1 %>"/>
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
      <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
      </dhv:evaluate>
    </td>
  </tr>
  <tr>
		<td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan="3">
            <strong>Ticket Log History</strong>
          </td>     
        </tr>
      <%  
          Iterator hist = TicketDetails.getHistory().iterator();
          if (hist.hasNext()) {
            while (hist.hasNext()) {
              TicketLog thisEntry = (TicketLog)hist.next();
      %>
            <% if (thisEntry.getSystemMessage() == true) {%>
          <tr bgColor="#F1F0E0">
            <% } else { %>
          <tr class="containerBody">
            <%}%>
            <td nowrap valign="top" class="formLabel">
              <%= toHtml(thisEntry.getEnteredByName()) %>
            </td>
            <td nowrap valign="top">
              <%= thisEntry.getEnteredString() %>
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

