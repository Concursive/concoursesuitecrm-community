<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.Ticket" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Tickets<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="tickets" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-accounts-tickets-add"><a href="AccountTickets.do?command=AddTicket&orgId=<%= OrgDetails.getOrgId() %>">Add New Ticket</a>
<input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
<br>
</dhv:permission>
<center><%= AccountTicketInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountTicketInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete">
    <td>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td>
      	<strong>Status</strong>
    </td>
    <td>
      <strong><dhv:label name="tickets-problem"><a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>&column=problem">Issue</a></dhv:label></strong>&nbsp;<%= AccountTicketInfo.getSortIcon("problem") %>
    </td>
    <td>
      <strong><a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>&column=pri_code">Priority</a></strong>&nbsp;<%= AccountTicketInfo.getSortIcon("pri_code") %>
    </td>
    <td>
      <strong><a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>&column=entered">Age</a></strong>&nbsp;<%= AccountTicketInfo.getSortIcon("entered") %>
    </td>
    <td valign=center align=left>
      <strong><a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>&column=modified">Last Modified</a></strong>&nbsp;<%= AccountTicketInfo.getSortIcon("modified") %>
    </td>
  </tr>
<%
	Iterator j = TicList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket)j.next();
%>   
	<tr class="containerBody">
    <dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="accounts-accounts-tickets-edit"><a href="AccountTickets.do?command=ModifyTicket&id=<%=thisTic.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-tickets-delete"><a href="javascript:confirmDelete('AccountTickets.do?command=DeleteTicket&orgId=<%=OrgDetails.getOrgId()%>&id=<%=thisTic.getId()%>');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width="8" nowrap valign="center" class="row<%= rowid %>">
<% if (thisTic.getClosed() == null) { %>
      <font color="green">open</font>
<%} else {%>
      <font color="red">closed</font>
<%}%>
    </td>
    <td valign="center" class="row<%= rowid %>">
      <a href="AccountTickets.do?command=TicketDetails&id=<%=thisTic.getId()%>"><%= toHtml(thisTic.getProblemHeader()) %></a>&nbsp;
	<% if (thisTic.getCategoryName() != null) { %>
      [<%=toHtml(thisTic.getCategoryName())%>]
	<%}%>
    </td>
    <td width="65" valign="center" nowrap class="row<%= rowid %>">
      <%=toHtml(thisTic.getPriorityName())%>
    </td>
    <td width="40" valign="center" nowrap class="row<%= rowid %>">
      <%=thisTic.getAgeOf()%>
    </td>
    <td width="160" nowrap valign="center" class="row<%= rowid %>">
<% if (thisTic.getClosed() == null) { %>
      <%=thisTic.getModifiedDateTimeString()%>
<%} else {%>
      <%=thisTic.getClosedDateTimeString()%>
<%}%>
    </td>
	</tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6">
      No tickets found.
    </td>
  </tr>
<%}%>
</table>
	<br>
  <dhv:pagedListControl object="AccountTicketInfo"/>
<br>
</td>
</tr>
</table>

