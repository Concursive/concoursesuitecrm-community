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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.* " %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="assetVendorList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="assetManufacturerList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="maintenanceList" class="org.aspcfs.modules.troubletickets.base.TicketMaintenanceNoteList" scope="request"/>
<jsp:useBean id="SunListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="maintenancenotes_menu.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
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
<dhv:label name="tickets.maintenancenotes.long_html">Maintenance Notes</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + ticketDetails.getId(); %>
<dhv:container name="tickets" selected="maintenancenotes" object="ticketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td width="20%" nowrap>
        <b><dhv:label name="ticket.serialNumber.colon">Serial No:</dhv:label></b>
      <%= toHtml(ticketDetails.getAssetSerialNumber()) %>
    </td>
    <td width="20%" nowrap>
        <b><dhv:label name="ticket.vendor.colon">Vendor:</dhv:label></b>
      <dhv:evaluate if="<%= ticketDetails.getAssetVendorCode() > 0 %>">
        <%=toHtml(assetManufacturerList.getSelectedValue(ticketDetails.getAssetVendorCode())) %>    
      </dhv:evaluate>&nbsp;
    </td>
    <td width="20%" nowrap>
        <b><dhv:label name="ticket.modelNumber.colon">Model:</dhv:label></b>
      <%= toHtml(ticketDetails.getAssetModelVersion()) %>
    </td>
    <td width="20%" nowrap>
        <b><dhv:label name="reports.helpdesk.ticket.location.colon">Location:</dhv:label></b>
        <% if(ticketDetails.getAssetLocation().trim().equals("")) {%>
          <dhv:label name="account.notSpecified.label">Not Specified</dhv:label>
        <%} else {%>
          <%= toHtml(ticketDetails.getAssetLocation()) %>
        <%}%>
    </td>
  </tr>
  <tr valign="top" class="underlineSection">
    <td width="20%" nowrap>
        <b><dhv:label name="ticket.serviceContractNumber.colon">Service Contract No.:</dhv:label></b>
      <%= toHtml(ticketDetails.getServiceContractNumber()) %>
    </td>
    <td width="20%" nowrap>
        <b><dhv:label name="ticket.onsiteServiceModel.colon">Onsite Service Model:</dhv:label></b>
      <%= toHtml((ticketDetails.getAssetOnsiteResponseModel() == -1) ? onsiteModelList.getSelectedValue(ticketDetails.getContractOnsiteResponseModel()) : onsiteModelList.getSelectedValue(ticketDetails.getAssetOnsiteResponseModel()))%>
    </td>
    <td width="20%" nowrap>
        <b><dhv:label name="project.startDate.colon">Start Date:</dhv:label></b>
      <zeroio:tz timestamp="<%= ticketDetails.getContractStartDate() %>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
    <td nowrap>
        <b><dhv:label name="product.endDate">End Date</dhv:label>:</b>
      <zeroio:tz timestamp="<%= ticketDetails.getContractEndDate() %>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
</table>
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <dhv:permission name="tickets-maintenance-report-add">
          <a href="TroubleTicketMaintenanceNotes.do?command=Add&id=<%=ticketDetails.getId()%>"><dhv:label name="ticket.addMaintenanceNote">Add Maintenance Note</dhv:label></a>
      </dhv:permission>
    </td>
  </tr>
</table>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SunListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
        &nbsp;
      </th>
      <th width="15%" nowrap>
        <strong><dhv:label name="ticket.enteredDate">Entered Date</dhv:label></strong>
      </th>
      <th width="85%" nowrap>
        <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
      </th>
    </tr>
    <%
      Iterator itr = maintenanceList.iterator();
      if (itr.hasNext()){
        int rowid = 0;
        int i = 0;
        while (itr.hasNext()){
          i++;
          rowid = (rowid != 1 ? 1 : 2);
          TicketMaintenanceNote thisSun = (TicketMaintenanceNote)itr.next();
      %>
    <tr valign="top" class="row<%=rowid%>">
      <td width="8" valign="center" nowrap >
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
           <a href="javascript:displayMenu('select<%= i %>','menuTicketForm', '<%=ticketDetails.getId() %>', '<%= thisSun.getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTicketForm');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
        </td>
      <td width="15%" nowrap>
        <a href="TroubleTicketMaintenanceNotes.do?command=View&id=<%=ticketDetails.getId()%>&formId=<%= thisSun.getId()%>">
        <zeroio:tz timestamp="<%=thisSun.getEntered()%>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></a>
      </td>
      <td width="15%" >
        <%= toHtml(thisSun.getDescriptionOfService()) %>
      </td>
     </tr>
      <%
        }
      }else{
      %>
      <tr class="containerBody">
        <td colspan="3">
          <dhv:label name="ticket.noMaintenanceNotesFound">No maintenance notes found.</dhv:label>
        </td>
      </tr>
      <%
      }
      %></table>
  <br>
  <dhv:pagedListControl object="SunListInfo"/>
</dhv:container>