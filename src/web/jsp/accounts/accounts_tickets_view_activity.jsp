<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="activityDetails" class="org.aspcfs.modules.troubletickets.base.TicketActivityLog" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<form name="details" action="AccountTicketActivityLog.do?command=Modify&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&return=<%=request.getParameter("return")%>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>">Ticket Details</a> >
<a href="AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>">Activity Log</a> >
View Activity Log
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
      [ <dhv:container name="accountstickets" selected="activitylog" param="<%= param2 %>"/> ]
      <br /> <br />
    <dhv:permission name="accounts-accounts-tickets-activity-log-edit">
      <input type="submit" value="Modify" />
    </dhv:permission>
    <dhv:permission name="accounts-accounts-tickets-activity-log-delete">
      <input type="button" value="Delete" onClick="javascript:popURLReturn('AccountTicketActivityLog.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&popup=true','AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
    </dhv:permission>
      <br /><br />
    <%@ include file="../troubletickets/troubletickets_view_activity_include.jsp" %>
      <br />
    <dhv:permission name="accounts-accounts-tickets-activity-log-edit">
      <input type="submit" value="Modify" />
    </dhv:permission>
    <dhv:permission name="accounts-accounts-tickets-activity-log-delete">
      <input type="button" value="Delete" onClick="javascript:popURLReturn('AccountTicketActivityLog.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&popup=true','AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
    </dhv:permission>
 </td>
 </tr>
</table>
</form>
