<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
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
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="details" action="ProjectManagementTickets.do?command=Modify&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>" method="post">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-organizer-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>">Tickets</a> >
      Details
    </td>
  </tr>
</table>
<br>
<% if (ticket.getClosed() != null) { %>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="Re-open" onClick="javascript:confirmForward('ProjectManagementTickets.do?command=Reopen&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>&return=<%= request.getParameter("return") %>');">
</zeroio:permission>
<%} else {%>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="Edit" onClick="javascript:window.location.href='ProjectManagementTickets.do?command=Modify&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>'">
</zeroio:permission>
<zeroio:permission name="project-tickets-delete">
  <input type="button" value="Delete" onClick="javascript:confirmDelete('ProjectManagementTickets.do?command=Delete&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>');">
</zeroio:permission>
<%}%>
<zeroio:permission name="project-tickets-edit,project-tickets-delete" if="any">
<br />
<br />
</zeroio:permission>
<dhv:evaluate if="<%= ticket.getClosed() != null %>">
  (<font color="red">This ticket was closed on <%= toHtml(ticket.getClosedString()) %></font>)
</dhv:evaluate>
<dhv:evaluate if="<%= ticket.getClosed() == null %>">
  (<font color="green">Open</font>)
<br />
</dhv:evaluate>
<dhv:pagedListStatus title="<%= "<strong>Ticket # " + ticket.getProjectTicketCount() + "</strong>" %>" object="projectTicketsInfo"/>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>Classification</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Issue
    </td>
    <td valign="top">
<%
  Iterator files = ticket.getFiles().iterator();
  while (files.hasNext()) {
    FileItem thisFile = (FileItem)files.next();
    if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= ticket.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom">Play Audio Message</a><br>
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
      Severity
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
      <strong>Assignment</strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Priority
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
      Assigned To
		</td>
		<td>
      <dhv:username id="<%= ticket.getAssignedTo() %>"/>
      <dhv:evaluate if="<%= !(ticket.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
		</td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Resolution</strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel" valign="top">
      Solution
		</td>
		<td>
      <%= toHtml(ticket.getSolution()) %>
		</td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
		<strong>Ticket Information</strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Entered
    </td>
		<td>
      <dhv:username id="<%= ticket.getEnteredBy() %>"/>
      -
      <zeroio:tz timestamp="<%= ticket.getEntered() %>"/>
		</td>
  </tr>
  <tr class="containerBody">
		<td nowrap class="formLabel">
      Last Modified
    </td>
		<td>
      <dhv:username id="<%= ticket.getModifiedBy() %>"/>
      -
      <zeroio:tz timestamp="<%= ticket.getModified() %>" />
		</td>
  </tr>
<%--
  <tr class="containerBody">
		<td nowrap class="formLabel">
      Source
		</td>
		<td>
      <%= toHtml(ticket.getSourceName()) %>
		</td>
  </tr>
--%>
</table>
<br>
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
        <zeroio:tz timestamp="<%= thisEntry.getEntered() %>"/>
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
        <font color="#9E9E9E" colspan="2">No Log Entries.</font>
			</td>
    </tr>
  <%}%>
</table>
&nbsp;<br>
<% if (ticket.getClosed() != null) { %>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="Re-open" onClick="javascript:confirmForward('ProjectManagementTickets.do?command=Reopen&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>&return=<%= request.getParameter("return") %>');">
</zeroio:permission>
<%} else {%>
<zeroio:permission name="project-tickets-edit">
  <input type="button" value="Edit" onClick="javascript:window.location.href='ProjectManagementTickets.do?command=Modify&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>'">
</zeroio:permission>
<zeroio:permission name="project-tickets-delete">
  <input type="button" value="Delete" onClick="javascript:confirmDelete('ProjectManagementTickets.do?command=Delete&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>');">
</zeroio:permission>
<%}%>
</form>
