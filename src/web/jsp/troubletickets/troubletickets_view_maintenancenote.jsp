<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="maintenanceDetails" class="org.aspcfs.modules.troubletickets.base.TicketMaintenanceNote" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do?"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= ticketDetails.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
<a href="TroubleTicketMaintenanceNotes.do?command=List&id=<%=ticketDetails.getId()%>">Maintenance Notes</a> >
View Maintenance Note
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
  <form name="details" action="TroubleTicketMaintenanceNotes.do?command=Modify&id=<%=ticketDetails.getId()%>&formId=<%=maintenanceDetails.getId()%>&return=view" method="post">
    <dhv:permission name="tickets-maintenance-report-edit">
      <input type="submit" value="Modify" />
    </dhv:permission>
    <dhv:permission name="tickets-maintenance-report-delete">
      <input type="button" value="Delete" onClick="javascript:popURLReturn('TroubleTicketMaintenanceNotes.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=maintenanceDetails.getId()%>&popup=true','TroubleTicketMaintenanceNotes.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
    </dhv:permission>
      <br /><br />
    <%@ include file="troubletickets_view_maintenancenote_include.jsp" %>
      <br />
    <dhv:permission name="tickets-maintenance-report-edit">
      <input type="submit" value="Modify" />
    </dhv:permission>
    <dhv:permission name="tickets-maintenance-report-delete">
      <input type="button" value="Delete" onClick="javascript:popURLReturn('TroubleTicketMaintenanceNotes.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=maintenanceDetails.getId()%>&popup=true','TroubleTicketMaintenanceNotes.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
    </dhv:permission>
  </form>
 </td>
 </tr>
</table>
