<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="TicList" class="com.darkhorseventures.cfsbase.TicketList" scope="request"/>
<jsp:useBean id="TicListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<a href="/Accounts.do?command=View">Back to Account List</a><br>&nbsp;
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
<dhv:permission name="accounts-accounts-tickets-add"><a href="/AccountTickets.do?command=AddTicket&orgId=<%= OrgDetails.getOrgId() %>">Add New Ticket</a>
<input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
<br>
&nbsp;
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
      <strong>Number</strong>
    </td>
    <td valign=center align=left>
      <strong>Status</strong>
    </td>
    <td valign=center align=left>
      <strong>Last Modified</strong>
    </td>
    <td>
      <b>Priority</b>
    </td>
    <td>
      <b>Age</b>
    </td>
    <td>
      <b><dhv:label name="tickets-problem">Issue</dhv:label></b>
    </td>
  </tr>
<%
	Iterator j = TicList.iterator();
	
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
	<tr class="containerBody">
    <td width=15 valign=center nowrap class="row<%= rowid %>">
      <a href="/AccountTickets.do?command=TicketDetails&id=<%=thisTic.getId()%>"><%=thisTic.getPaddedId()%></a>
    </td>
    <td width=8% nowrap valign=center class="row<%= rowid %>">
<% if (thisTic.getClosed() == null) { %>
      <font color="green">open</font>
<%} else {%>
      <font color="red">closed</font>
<%}%>
    </td>
    <td width=24% nowrap valign=center class="row<%= rowid %>">
<% if (thisTic.getClosed() == null) { %>
      <%=thisTic.getModified()%>
<%} else {%>
      <%=thisTic.getClosed()%>
<%}%>
    </td>
    <td width=10 valign=center nowrap class="row<%= rowid %>">
      <%=thisTic.getPriorityName()%>
    </td>
    <td width=8% valign=center nowrap class="row<%= rowid %>">
      <%=thisTic.getAgeOf()%>
    </td>
    <td width=50% valign=center class="row<%= rowid %>">
      <%= toHtml(thisTic.getProblem()) %> <% if (thisTic.getCategoryName() != null) { %>[<%=toHtml(thisTic.getCategoryName())%>]<%}%>
    </td>
	</tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan=6 valign=center>
      No tickets found.
    </td>
  </tr>
<%}%>
</table>
	<br>
  <dhv:pagedListControl object="TicListInfo"/>
<br>
</td></tr>
</table>

