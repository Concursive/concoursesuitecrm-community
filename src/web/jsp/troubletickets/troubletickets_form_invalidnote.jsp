<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="TroubleTickets.do?">Tickets</a> > 
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
Activity Log
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + ticketDetails.getId(); %>
<dhv:container name="tickets" selected="maintenancenotes" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th >
	    <strong>All information required for maintenance notes were not provided in the ticket</strong>
	  </th>
  </tr>
  <tr class="row2">
    <td>
	    When all required information is entered, this page shows the asset maintenance notes.
	  </td>
  </tr>
  </table>
 </td>
 </tr>
</table>
