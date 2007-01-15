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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="maintenanceDetails" class="org.aspcfs.modules.troubletickets.base.TicketMaintenanceNote" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<form name="details" action="AccountTicketMaintenanceNotes.do?command=Modify&id=<%=ticketDetails.getId()%>&formId=<%=maintenanceDetails.getId()%>&return=view<%= addLinkParams(request, "popup|popupType|actionId") %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
<a href="AccountTicketMaintenanceNotes.do?command=List&id=<%=ticketDetails.getId()%>"><dhv:label name="tickets.maintenancenotes.long_html">Maintenance Notes</dhv:label></a> >
<dhv:label name="ticket.viewMaintenanceNote">View Maintenance Note</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="tickets" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountstickets" selected="maintenancenotes" object="ticketDetails" param='<%= "id=" + ticketDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%@ include file="accounts_ticket_header_include.jsp" %>
    <dhv:evaluate if="<%= !ticketDetails.isTrashed() %>" >
      <dhv:permission name="accounts-accounts-tickets-maintenance-report-edit">
        <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" />
      </dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-maintenance-report-delete">
        <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('AccountTicketMaintenanceNotes.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=maintenanceDetails.getId()%>&popup=true<%= isPopup(request)?"&popupType=inline":"" %>','AccountTicketMaintenanceNotes.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
      </dhv:permission>
    </dhv:evaluate>
      <br /><br />
    <%@ include file="../troubletickets/troubletickets_view_maintenancenote_include.jsp" %>
      <br />
    <dhv:evaluate if="<%= !ticketDetails.isTrashed() %>" >
      <dhv:permission name="accounts-accounts-tickets-maintenance-report-edit">
        <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" />
      </dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-maintenance-report-delete">
        <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('AccountTicketMaintenanceNotes.do?command=ConfirmDelete&id=<%=ticketDetails.getId()%>&formId=<%=maintenanceDetails.getId()%>&popup=true<%= isPopup(request)?"&popupType=inline":"" %>','AccountTicketMaintenanceNotes.do?command=List&id=<%=ticketDetails.getId()%>', 'Delete_maintenancenote','320','200','yes','no');">
      </dhv:permission>
    </dhv:evaluate>
  </dhv:container>
</dhv:container>
</form>

