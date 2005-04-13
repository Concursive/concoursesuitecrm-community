<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="ticketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="projectTicketsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="details" action="ProjectManagementTickets.do?command=Modify&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>" method="post">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-organizer-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>"><dhv:label name="dependency.tickets">Tickets</dhv:label></a> >
      <dhv:label name="tickets.details">Ticket Details</dhv:label>
    </td>
  </tr>
</table>
<br>
<% if (ticket.getClosed() != null) { %>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="<dhv:label name="button.reopen">Re-open</dhv:label>" onClick="javascript:confirmForward('ProjectManagementTickets.do?command=Reopen&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>&return=<%= request.getParameter("return") %>');">
</zeroio:permission>
<%} else {%>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="<dhv:label name="button.edit">Edit</dhv:label>" onClick="javascript:window.location.href='ProjectManagementTickets.do?command=Modify&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>&return=<%= request.getParameter("return") %>'">
</zeroio:permission>
<zeroio:permission name="project-tickets-delete">
  <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:confirmDelete('ProjectManagementTickets.do?command=Delete&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>');">
</zeroio:permission>
<%}%>
<zeroio:permission name="project-tickets-edit,project-tickets-delete" if="any">
<br />
<br />
</zeroio:permission>
<dhv:pagedListStatus object="projectTicketsInfo">
  <strong>Ticket # <%= ticket.getProjectTicketCount() %></strong>
  <dhv:evaluate if="<%= ticket.isClosed() %>">
    (<font color="red"><dhv:label name="project.ticketClosedOn" param="<%= "time="+toHtml(ticket.getClosedString()) %>">This ticket was closed on <%= toHtml(ticket.getClosedString()) %></dhv:label></font>)
  </dhv:evaluate>
  <dhv:evaluate if="<%= !ticket.isClosed() %>">
    (<font color="green"><dhv:label name="quotes.open">Open</dhv:label></font>)
  </dhv:evaluate>
</dhv:pagedListStatus>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_asset_history.Issue">Issue</dhv:label>
    </td>
    <td valign="top">
<%
  Iterator files = ticket.getFiles().iterator();
  while (files.hasNext()) {
    FileItem thisFile = (FileItem)files.next();
    if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= ticket.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"><dhv:label name="tickets.playAudioMessage">Play Audio Message</dhv:label></a><br>
<%
    }
  }
%>
      <%= toHtml(ticket.getProblem()) %>
    </td>
  </tr>
<%--
  <tr class="containerBody">
		<td class="formLabel">
      Category
		</td>
		<td>
      <%= toHtml(ticket.getCategoryName()) %>
		</td>
  </tr>
--%>
  <tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="project.severity">Severity</dhv:label>
    </td>
		<td>
      <%= toHtml(ticket.getSeverityName()) %>
		</td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong><dhv:label name="project.assignment">Assignment</dhv:label></strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
    </td>
		<td>
      <%= toHtml(ticket.getPriorityName()) %>
		</td>
  </tr>
<%--
  <tr class="containerBody">
		<td class="formLabel">
      Department
		</td>
		<td>
      <%= toHtml(ticket.getDepartmentName()) %>
		</td>
  </tr>
--%>
  <tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label>
		</td>
		<td>
      <dhv:username id="<%= ticket.getAssignedTo() %>"/>
      <dhv:evaluate if="<%= !(ticket.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
		</td>
  </tr>
  <dhv:evaluate if="<%= ticket.getEstimatedResolutionDate() != null %>">
    <tr class="containerBody">
      <td class="formLabel">
        Estimated Resolution Date
      </td>
      <td>
        <zeroio:tz timestamp="<%= ticket.getEstimatedResolutionDate() %>" default="&nbsp;"/>
      </td>
    </tr>
  </dhv:evaluate>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong><dhv:label name="accounts.accounts_asset_history.Resolution">Resolution</dhv:label></strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel" valign="top">
      <dhv:label name="project.ticketSolution">Solution</dhv:label>
		</td>
		<td>
      <%= toHtml(ticket.getSolution()) %>
		</td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
		<strong>Ticket Information</strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
    </td>
		<td>
      <dhv:username id="<%= ticket.getEnteredBy() %>"/>
      -
      <zeroio:tz timestamp="<%= ticket.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
		</td>
  </tr>
  <tr class="containerBody">
		<td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label>
    </td>
		<td>
      <dhv:username id="<%= ticket.getModifiedBy() %>"/>
      -
      <zeroio:tz timestamp="<%= ticket.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
		</td>
  </tr>
  <dhv:evaluate if="<%= ticket.isClosed() %>">
    <tr class="containerBody">
      <td class="formLabel">
        Closed Date
      </td>
      <td>
        <zeroio:tz timestamp="<%= ticket.getClosed() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
    </tr>
  </dhv:evaluate>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Ticket Log History</strong>
		</th>
  </tr>
<%  
		Iterator hist = ticket.getHistory().iterator();
		if (hist.hasNext()) {
			while (hist.hasNext()) {
				TicketLog thisEntry = (TicketLog) hist.next();
%>
			<% if (thisEntry.getSystemMessage() == true) {%>
    <tr class="containerBody">
			<% } else { %>
    <tr class="containerBody">
			<%}%>
			<td nowrap valign="top" align="right">
        <dhv:username id="<%= thisEntry.getEnteredBy() %>"/>
        <zeroio:tz timestamp="<%= thisEntry.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
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
        <font color="#9E9E9E" colspan="2"><dhv:label name="project.tickets.noLogEntries">No Log Entries.</dhv:label></font>
			</td>
    </tr>
  <%}%>
</table>
&nbsp;<br>
<% if (ticket.getClosed() != null) { %>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="<dhv:label name="button.reopen">Re-open</dhv:label>" onClick="javascript:confirmForward('ProjectManagementTickets.do?command=Reopen&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>&return=<%= request.getParameter("return") %>');">
</zeroio:permission>
<%} else {%>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="<dhv:label name="button.edit">Edit</dhv:label>" onClick="javascript:window.location.href='ProjectManagementTickets.do?command=Modify&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>&return=<%= request.getParameter("return") %>'">
</zeroio:permission>
<zeroio:permission name="project-tickets-delete">
  <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:confirmDelete('ProjectManagementTickets.do?command=Delete&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>');">
</zeroio:permission>
<%}%>
</form>
