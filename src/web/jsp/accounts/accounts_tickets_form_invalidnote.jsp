<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>">Ticket Details</a> >
Forms
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="tickets" param="<%= "orgId=" + ticketDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
  <%@ include file="accounts_ticket_header_include.jsp" %>
  <% String param2 = "id=" + ticketDetails.getId(); %>
  [ <dhv:container name="accountstickets" selected="forms" param="<%= param2 %>"/> ]
  <br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <br />
  <tr>
    <th align="left">An asset should be associated with the ticket to add maintenance notes</th>
  </tr>
  </table>
 </td>
 </tr>
</table>
