<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*, org.aspcfs.modules.base.EmailAddress " %>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_defect_ticket_list_menu.jsp" %>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<%-- Trails --%>
<%
  boolean canEdit = true;
  boolean canDelete = true;
  if (User.getSiteId() != -1){
    if (defect.getSiteId() != User.getSiteId()){
      canEdit = false;
      canDelete = false;
    }
  }
%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> >
  <a href="TroubleTicketDefects.do?command=View"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="tickets-defects-edit">
  <dhv:evaluate if="<%=canEdit%>" >
    <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='TroubleTicketDefects.do?command=Modify&defectId=<%= defect.getId() %>';">
  </dhv:evaluate>
</dhv:permission>
<dhv:permission name="tickets-defects-delete">
  <dhv:evaluate if="<%=canDelete%>" >
    <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('TroubleTicketDefects.do?command=ConfirmDelete&defectId=<%= defect.getId() %>&popup=true', 'DeleteTicketDefect','320','200','yes','no');">
  </dhv:evaluate>
</dhv:permission>
<dhv:permission name="tickets-defects-edit,tickets-defects-delete"><br />&nbsp;<br /></dhv:permission>
<%-- Defect Information --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contacts.details">Details</dhv:label></strong>
    </th>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
    </td>
    <td>
      <%= toHtml(defect.getTitle()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td><%= toHtml(defect.getDescription()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= defect.getEnabled() %>"><dhv:label name="account.yes">Yes</dhv:label></dhv:evaluate>
      <dhv:evaluate if="<%= !defect.getEnabled() %>"><dhv:label name="account.no">No</dhv:label></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= defect.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="reports.parameter.endDate">End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= defect.getEndDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
  </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.site">Site</dhv:label>
      </td>
      <td>
        <%= SiteList.getSelectedValue(defect.getSiteId()) %>
      </td>
    </tr>
</table>
<br />
<dhv:evaluate if="<%= (defect.getSiteId() == -1) && (User.getSiteId() != -1) %>" >
      <strong><dhv:label name="tickets.ticketsInSiteColon">Tickets in site:</dhv:label><%= SiteList.getSelectedValue(User.getSiteId()) %></strong><br/><br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th width="8">
      &nbsp;
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    </th>
    <th><b><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></b></th>
    <th><b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b></th>
    <th><b><dhv:label name="ticket.age">Age</dhv:label></b></th>
    <th><b><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></b></th>
	<th><b><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></b></th>
  </tr>
<%
	Iterator n = defect.getTickets().iterator();
	if ( n.hasNext() ) {
		int rowid = 0;
    int count = 0;
		while (n.hasNext()) {
      ++count;
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket) n.next();
%>
	<tr class="row<%= rowid %>">
    <td rowspan="2" width="8" valign="top" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuTicket','<%= thisTic.getId() %>');" 
          onMouseOver="over(0, <%= count %>)" 
          onmouseout="out(0, <%= count %>); hideMenu('menuTicket');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="10%" valign="top" nowrap>
			<a href="TroubleTickets.do?command=Details&id=<%= thisTic.getId() %>&defectCheck=<%= defect.getId() %>"><%= thisTic.getPaddedId() %></a>
		</td>
		<td width="12%" valign="top" nowrap>
			<%= toHtml(thisTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top">
      <% if(!User.getTimeZone().equals(thisTic.getEstimatedResolutionDateTimeZone())){%>
      <zeroio:tz timestamp="<%= thisTic.getEstimatedResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTic.getEstimatedResolutionDate() %>" dateOnly="true" timeZone="<%= thisTic.getEstimatedResolutionDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
		</td>
		<td width="6%" align="right" valign="top" nowrap>
			<%= thisTic.getAgeOf() %>
		</td>
		<td width="45%" valign="top">
			<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate if="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="20%" nowrap valign="top">
      <dhv:evaluate if="<%= thisTic.isAssigned() %>">
        <dhv:username id="<%= thisTic.getAssignedTo() %>" default="ticket.unassigned.text"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !(thisTic.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      <dhv:evaluate if="<%= (!thisTic.isAssigned()) %>">
        <font color="red"><dhv:username id="<%= thisTic.getAssignedTo() %>" default="ticket.unassigned.text"/></font>
      </dhv:evaluate>
		</td>
	</tr>
  <tr class="row<%= rowid %>">
    <td colspan="7" valign="top">
      <%= toHtml(thisTic.getProblemHeader()) %>
      <% if (thisTic.getClosed() == null) { %>
        [<font color="green"><dhv:label name="project.open.lowercase">open</dhv:label></font>]
      <%} else {%>
        [<font color="red"><dhv:label name="project.closed.lowercase">closed</dhv:label></font>]
      <%}%>
    </td>
  </tr>
	<%}%>
	<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      <dhv:label name="tickets.search.notFound">No tickets found</dhv:label>
    </td>
  </tr>
	<%}%>
</table>
<br />
<dhv:permission name="tickets-defects-edit">
  <dhv:evaluate if="<%=canEdit%>" >
    <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='TroubleTicketDefects.do?command=Modify&defectId=<%= defect.getId() %>';">
  </dhv:evaluate>
</dhv:permission>
<dhv:permission name="tickets-defects-delete">
  <dhv:evaluate if="<%=canDelete%>" >
    <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('TroubleTicketDefects.do?command=ConfirmDelete&defectId=<%= defect.getId() %>&popup=true', 'DeleteTicketDefect','320','200','yes','no');">
  </dhv:evaluate>
</dhv:permission>
