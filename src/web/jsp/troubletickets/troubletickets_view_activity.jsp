<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="activityDetails" class="org.aspcfs.modules.troubletickets.base.TicketActivityLog" scope="request"/>
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
<a href="TroubleTickets.do?">Help Desk</a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home">View Tickets</a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= ticketDetails.getId() %>">Ticket Details</a> >
<a href="TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>">Activity Log</a> >
Add Activity Log
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + ticketDetails.getId(); %>
<dhv:container name="tickets" selected="activitylog" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
  <form name="details" action="TroubleTicketActivityLog.do?command=Modify&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&return=<%=request.getParameter("return")%>" method="post">
    <dhv:permission name="tickets-activity-log-edit">
      <input type="submit" value="Modify" />
    </dhv:permission>
    <dhv:permission name="tickets-activity-log-delete">
      <input type="button" value="Delete" onClick="javascript:popURLReturn('TroubleTicketActivityLog.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&popup=true','TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
    </dhv:permission>
      <br /> <br />
    <%@ include file="troubletickets_view_activity_include.jsp" %>
      <br />
    <dhv:permission name="tickets-activity-log-edit">
      <input type="submit" value="Modify" />
    </dhv:permission>
    <dhv:permission name="tickets-activity-log-delete">
      <input type="button" value="Delete" onClick="javascript:popURLReturn('TroubleTicketActivityLog.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&popup=true','TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
    </dhv:permission>
  </form>
 </td>
 </tr>
</table>
