<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="details" action="AccountTickets.do?command=ModifyTicket&auto-populate=true" method="post">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
Ticket Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%=toHtml(TicketDetails.getCompanyName())%> - Ticket # <%=TicketDetails.getPaddedId()%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="tickets" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
  	<td class="containerBack">
<% if (TicketDetails.getClosed() != null) { %>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>';submit();"> <font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosedString())%></font></dhv:permission>
<%} else {%>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountTickets.do?command=ModifyTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='AccountTickets.do?command=DeleteTicket&id=<%=TicketDetails.getId() %>';confirmSubmit(this.form);"></dhv:permission>
<%}%>
<dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete"><br>&nbsp;</dhv:permission>

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
      <dhv:label name="tickets-problem">Issue</dhv:label>
    </td>
		<td valign=top>
      <%= toHtml(TicketDetails.getProblem()) %>
      <input type=hidden name=problem value="<%=toHtml(TicketDetails.getProblem())%>">
      <input type=hidden name=orgId value="<%=TicketDetails.getOrgId()%>">
      <input type=hidden name=id value="<%=TicketDetails.getId()%>">
		</td>
  </tr>
  
  <dhv:include name="tickets-code" none="true">
	<tr class="containerBody">
		<td class="formLabel">
      Category
		</td>
		<td>
      <%=toHtml(TicketDetails.getCategoryName())%>
		</td>
  </tr>
  </dhv:include>
  
  <dhv:include name="tickets-severity" none="true">
	<tr class="containerBody">
		<td class="formLabel">
      Severity
    </td>
		<td valign=top>
      <%=toHtml(TicketDetails.getSeverityName())%>
		</td>
  </tr>
  </dhv:include>
	
  <dhv:include name="tickets-priority" none="true">
  <tr class="containerBody">
		<td class="formLabel">
      Priority
    </td>
		<td valign=top bgColor="white">
      <%=toHtml(TicketDetails.getPriorityName())%>
		</td>
  </tr>
  </dhv:include>
  
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
      <dhv:evaluate exp="<%=!(TicketDetails.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
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
      <%=toHtml(TicketDetails.getEnteredByName())%> - <%=TicketDetails.getEnteredString()%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Modified
    </td>
		<td valign=top>
      <%=toHtml(TicketDetails.getModifiedByName())%> - <%=TicketDetails.getModifiedString()%>
		</td>
  </tr>
</table>
&nbsp;
<%
  if (TicketDetails.getThisContact() != null ) {
%>
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
      <%=toHtml(TicketDetails.getThisContact().getNameLastFirst())%>
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
      <%= TicketDetails.getThisContact().getEmailAddressTag("Business", toHtml(TicketDetails.getThisContact().getEmailAddress("Business")), "&nbsp;") %>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Phone
		</td>
		<td>
      <%= toHtml(TicketDetails.getThisContact().getPhoneNumber("Business")) %>
		</td>
  </tr>
</table>
&nbsp;
<%}%>
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
    <td nowrap valign="top" width=100 class="formLabel">
      <%=toHtml(thisEntry.getEnteredByName())%>
    </td>
    <td nowrap valign="top" width=150>
			<%=thisEntry.getEnteredString()%>
    </td>
    <td valign="top">
			<%=toHtml(thisEntry.getEntryText())%>
    </td>
  </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td bgcolor="white">
      <font color="#9E9E9E" colspan="3">No Log Entries.</font>
    </td>
  </tr>
<%}%>
</table>
<dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete"><br></dhv:permission>
<% if (TicketDetails.getClosed() != null) { %>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
<%} else {%>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountTickets.do?command=ModifyTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='AccountTickets.do?command=DeleteTicket&id=<%=TicketDetails.getId() %>';confirmSubmit(this.form);"></dhv:permission>
<%}%>

</td></tr>
</table>
</form>
