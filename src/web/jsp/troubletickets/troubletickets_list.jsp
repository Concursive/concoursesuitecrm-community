<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CreatedByMeList" class="com.darkhorseventures.cfsbase.TicketList" scope="request"/>
<jsp:useBean id="CreatedByMeInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="AssignedToMeList" class="com.darkhorseventures.cfsbase.TicketList" scope="request"/>
<jsp:useBean id="AssignedToMeInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="OpenList" class="com.darkhorseventures.cfsbase.TicketList" scope="request"/>
<jsp:useBean id="OpenInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<dhv:permission name="tickets-tickets-add"><a href="/TroubleTickets.do?command=Add">Add a Ticket</a></dhv:permission>
<br>
<%= showAttribute(request, "actionError") %>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td align="left" colspan="7">
      <strong>Tickets Assigned to Me</strong>
    </td>
  </tr>
  <tr class="title">
    <dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
		<td valign="center" align="left">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign="center" align="left">
      <strong>Number</strong>
    </td>
    <td><b>Priority</b></td>
    <td><b>Age</b></td>
    <td><b>Company</b></td>
    <td><b><dhv:label name="tickets-problem">Issue</dhv:label></b></td>
		<td><b>Assigned&nbsp;To</b></td>
  </tr>
  
  <%
	Iterator k = AssignedToMeList.iterator();
	
	if ( k.hasNext() ) {
		int rowid = 0;
		while (k.hasNext()) {
		if (rowid != 1) {
			rowid = 1;
		} else {
			rowid = 2;
		}
	
		Ticket assignedTic = (Ticket)k.next();
%>   
	<tr>
	<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
    <td width="8" valign="top" nowrap class="row<%= rowid %>">
      <dhv:permission name="tickets-tickets-edit"><a href="/TroubleTickets.do?command=Modify&id=<%= assignedTic.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="tickets-tickets-edit,tickets-tickets-delete" all="true">|</dhv:permission><dhv:permission name="tickets-tickets-delete"><a href="javascript:confirmDelete('/TroubleTickets.do?command=Delete&id=<%= assignedTic.getId() %>');">Del</a></dhv:permission>
    </td>
    	</dhv:permission>
		<td width="15" valign="top" nowrap class="row<%= rowid %>">
			<a href="/TroubleTickets.do?command=Details&id=<%=assignedTic.getId()%>"><%=assignedTic.getPaddedId()%></a>
		</td>
		<td width="10" valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(assignedTic.getPriorityName())%>
		</td>
		<td width="8%" valign="top" nowrap class="row<%= rowid %>">
			<%=assignedTic.getAgeOf()%>
		</td>
		<td width="40%" valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(assignedTic.getCompanyName())%><dhv:evaluate exp="<%=!(assignedTic.getCompanyEnabled())%>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="52%" valign="top" class="row<%= rowid %>">
			<%= toHtml(assignedTic.getProblemHeader()) %> <% if (assignedTic.getCategoryName() != null) { %>[<%=toHtml(assignedTic.getCategoryName())%>]<%}%>
		</td>
		<td valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(assignedTic.getOwnerName())%>
		</td>
	</tr>
	<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="AssignedToMeInfo" tdClass="row1"/>
  
	<%} else {%>
		<tr bgcolor="white"><td colspan="7" valign="center">No tickets found.</td></tr>
		</table>
	<%}%>
  
<br>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td align="left" colspan="7">
      <strong>Open/Unassigned Tickets</strong>
    </td>
  </tr>
  <tr class="title">
  <dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
		<td valign="center" align="left">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign="center" align="left">
      <strong>Number</strong>
    </td>
    <td><b>Priority</b></td>
    <td><b>Age</b></td>
    <td><b>Company</b></td>
    <td><b><dhv:label name="tickets-problem">Issue</dhv:label></b></td>
		<td><b>Assigned&nbsp;To</b></td>
  </tr>
  
  <%
	Iterator n = OpenList.iterator();
	
	if ( n.hasNext() ) {
		int rowid = 0;
		while (n.hasNext()) {
		if (rowid != 1) {
			rowid = 1;
		} else {
			rowid = 2;
		}
	
		Ticket openTic = (Ticket)n.next();
%>   
	<tr>
	<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
    <td width="8" valign="top" nowrap class="row<%= rowid %>">
      <dhv:permission name="tickets-tickets-edit"><a href="/TroubleTickets.do?command=Modify&id=<%= openTic.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="tickets-tickets-edit,tickets-tickets-delete" all="true">|</dhv:permission><dhv:permission name="tickets-tickets-delete"><a href="javascript:confirmDelete('/TroubleTickets.do?command=Delete&id=<%= openTic.getId() %>');">Del</a></dhv:permission>
    </td>
    	</dhv:permission>
		<td width="15" valign="top" nowrap class="row<%= rowid %>">
			<a href="/TroubleTickets.do?command=Details&id=<%=openTic.getId()%>"><%=openTic.getPaddedId()%></a>
		</td>
		<td width="10" valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(openTic.getPriorityName())%>
		</td>
		<td width="8%" valign="top" nowrap class="row<%= rowid %>">
			<%=openTic.getAgeOf()%>
		</td>
		<td width="40%" valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(openTic.getCompanyName())%><dhv:evaluate exp="<%=!(openTic.getCompanyEnabled())%>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="52%" valign="top" class="row<%= rowid %>">
			<%= toHtml(openTic.getProblemHeader()) %> <% if (openTic.getCategoryName() != null) { %>[<%=toHtml(openTic.getCategoryName())%>]<%}%>
		</td>
		<td valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(openTic.getOwnerName())%>
		</td>
	</tr>
	<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="OpenInfo" tdClass="row1"/>
  
	<%} else {%>
		<tr bgcolor="white"><td colspan="7" valign="center">No tickets found.</td></tr>
		</table>
	<%}%>
  
<br>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td align="left" colspan="7">
      <strong>Tickets Created by Me</strong>
    </td>
  </tr>
  <tr class="title">
  <dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
		<td valign="center" align="left">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign="center" align="left">
      <strong>Number</strong>
    </td>
    <td><b>Priority</b></td>
    <td><b>Age</b></td>
    <td><b>Company</b></td>
    <td><b><dhv:label name="tickets-problem">Issue</dhv:label></b></td>
		<td><b>Assigned&nbsp;To</b></td>
  </tr>
  
  <%
	Iterator j = CreatedByMeList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		while (j.hasNext()) {
		if (rowid != 1) {
			rowid = 1;
		} else {
			rowid = 2;
		}
	
		Ticket thisTic = (Ticket)j.next();
%>   
	<tr>
	<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
    <td width="8" valign="top" nowrap class="row<%= rowid %>">
      <dhv:permission name="tickets-tickets-edit"><a href="/TroubleTickets.do?command=Modify&id=<%= thisTic.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="tickets-tickets-edit,tickets-tickets-delete" all="true">|</dhv:permission><dhv:permission name="tickets-tickets-delete"><a href="javascript:confirmDelete('/TroubleTickets.do?command=Delete&id=<%= thisTic.getId() %>');">Del</a></dhv:permission>
    </td>
    	</dhv:permission>
		<td width="15" valign="top" nowrap class="row<%= rowid %>">
			<a href="/TroubleTickets.do?command=Details&id=<%=thisTic.getId()%>"><%=thisTic.getPaddedId()%></a>
		</td>
		<td width="10" valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(thisTic.getPriorityName())%>
		</td>
		<td width="8%" valign="top" nowrap class="row<%= rowid %>">
			<%=thisTic.getAgeOf()%>
		</td>
		<td width="40%" valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(thisTic.getCompanyName())%><dhv:evaluate exp="<%=!(thisTic.getCompanyEnabled())%>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="52%" valign="top" class="row<%= rowid %>">
			<%= toHtml(thisTic.getProblemHeader()) %> <% if (thisTic.getCategoryName() != null) { %>[<%=toHtml(thisTic.getCategoryName())%>]<%}%>
		</td>
		<td valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(thisTic.getOwnerName())%>
		</td>
	</tr>
	<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="CreatedByMeInfo" tdClass="row1"/>
  
	<%} else {%>
		<tr bgcolor="white"><td colspan="7" valign="center">No tickets found.</td></tr>
		</table>
	<%}%>
  
