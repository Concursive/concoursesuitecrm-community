<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="com.darkhorseventures.cfsbase.Ticket" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="details" action="/AccountTickets.do?command=ModifyTicket&auto-populate=true" method="post">
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Back to Ticket List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%=toHtml(TicketDetails.getCompanyName())%> - Ticket #<%=TicketDetails.getId()%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Details</font></a> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Folders</font></a> |
      <font color="#787878">Activities</font> | 
      <a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Contacts</font></a> | 
      <a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Opportunities</font></a> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#0000FF">Tickets</font></a> |
      <font color="#787878">Documents</font> 
    </td>
  </tr>
  <tr>
  	<td class="containerBack">
<% if (TicketDetails.getClosed() != null) { %>
      <input type=button value="Reopen"> <font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosed())%></font>
<%} else {%>
      <input type=submit value="Modfiy">
      <input type="submit" value="Delete" onClick="javascript:this.form.action='/AccountTickets.do?command=DeleteTicket&id=<%= TicketDetails.getId() %>'">
<%}%>
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
		<td colspan=2 valign=center align=left>
      <strong>Ticket Information</strong>
		</td>     
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Ticket Source
		</td>
		<td>
      <%=toHtml(TicketDetails.getSourceName())%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Problem
    </td>
		<td valign=top>
      <%=toHtml(TicketDetails.getProblem())%>
      <input type=hidden name=problem value="<%=toHtml(TicketDetails.getProblem())%>">
      <input type=hidden name=orgId value="<%=TicketDetails.getOrgId()%>">
      <input type=hidden name=id value="<%=TicketDetails.getId()%>">
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Category
		</td>
		<td>
      <%=toHtml(TicketDetails.getCategoryName())%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Severity
    </td>
		<td valign=top>
      <%=toHtml(TicketDetails.getSeverityName())%>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Priority
    </td>
		<td valign=top bgColor="white">
      <%=toHtml(TicketDetails.getPriorityName())%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Department
		</td>
		<td>
      <%= toHtml(TicketDetails.getDepartmentName()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Assigned To
		</td>
		<td>
      <%= toHtml(TicketDetails.getOwnerName()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Solution
		</td>
		<td>
      <%= toHtml(TicketDetails.getSolution()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Entered
    </td>
		<td valign=top>
      <%=toHtml(TicketDetails.getEnteredByName())%> - <%=TicketDetails.getEntered()%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Modified
    </td>
		<td valign=top>
      <%=toHtml(TicketDetails.getModifiedByName())%> - <%=TicketDetails.getModified()%>
		</td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=4 valign=center align=left>
      <strong>Primary Contact</strong>
		</td>     
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Name
		</td>
		<td>
      <%=toHtml(TicketDetails.getThisContact().getNameLast())%>, <%=toHtml(TicketDetails.getThisContact().getNameFirst())%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Title
		</td>
		<td>
      <%=toHtml(TicketDetails.getThisContact().getTitle())%>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Email
		</td>
		<td>
      <a href="mailto:<%=toHtml(TicketDetails.getThisContact().getEmailAddress("Business"))%>"><%=toHtml(TicketDetails.getThisContact().getEmailAddress("Business"))%></a>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Phone
		</td>
		<td>
      <%=TicketDetails.getThisContact().getPhoneNumber("Business")%>
		</td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
		<td colspan=4 valign=center align=left>
      <strong>Ticket Log History</strong>
		</td>     
  </tr>
<%  
		Iterator hist = TicketDetails.getHistory().iterator();
		if (hist.hasNext()) {
			while (hist.hasNext()) {
				TicketLog thisEntry = (TicketLog)hist.next();
%>    
<% if (thisEntry.getSystemMessage() == true) {%>
  <tr bgColor="#F1F0E0">
<% } else { %>
  <tr class="containerBody">
<%}%>
    <td nowrap valign=center width=100 class="formLabel">
      <%=toHtml(thisEntry.getEnteredByName())%>
    </td>
    <td nowrap valign=center width=150>
			<%=thisEntry.getEntered()%>
    </td>
    <td valign=center>
			<%=toHtml(thisEntry.getEntryText())%>
    </td>
  </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td>
      <font color="#9E9E9E" colspan=3>No Log Entries.</font>
    </td>
  </tr>
<%}%>
</table>
<br>
<% if (TicketDetails.getClosed() != null) { %>
  <input type=button value="Reopen">
<%} else {%>
  <input type=submit value="Modify">
  <input type="submit" value="Delete" onClick="javascript:this.form.action='/AccountTickets.do?command=DeleteTicket&id=<%= TicketDetails.getId() %>'">
<%}%>
</td></tr>
</table>
</form>
