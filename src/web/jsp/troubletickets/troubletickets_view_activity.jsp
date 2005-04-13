<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="activityDetails" class="org.aspcfs.modules.troubletickets.base.TicketActivityLog" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<form name="details" action="TroubleTicketActivityLog.do?command=Modify&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&return=<%=request.getParameter("return")%>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do?"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTickets.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= ticketDetails.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
<a href="TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>"><dhv:label name="tickets.activitylog.long_html">Activity Log</dhv:label></a> >
<dhv:label name="project.addActivityLog">Add Activity Log</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + ticketDetails.getId(); %>
<dhv:container name="tickets" selected="activitylog" object="ticketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  <dhv:permission name="tickets-activity-log-edit">
    <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" />
  </dhv:permission>
  <dhv:permission name="tickets-activity-log-delete">
    <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('TroubleTicketActivityLog.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&popup=true','TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
  </dhv:permission>
    <br /> <br />
  <%@ include file="troubletickets_view_activity_include.jsp" %>
    <br />
  <dhv:permission name="tickets-activity-log-edit">
    <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" />
  </dhv:permission>
  <dhv:permission name="tickets-activity-log-delete">
    <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('TroubleTicketActivityLog.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&popup=true','TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
  </dhv:permission>
</dhv:container>
</form>
