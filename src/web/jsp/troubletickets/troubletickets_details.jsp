<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="TicketDetails" class="com.darkhorseventures.cfsbase.Ticket" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="details" action="/TroubleTickets.do?command=Modify&auto-populate=true" method="post">
<a href="TroubleTickets.do?command=Home">Back to Ticket List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
    <strong>Ticket # <%=TicketDetails.getPaddedId()%><br>
    <%=toHtml(TicketDetails.getCompanyName())%></strong>
    <dhv:evaluate exp="<%=!(TicketDetails.getCompanyEnabled())%>"><font color="red">(account disabled)</font></dhv:evaluate>
    </td>
  </tr>
  
<% if (TicketDetails.getClosed() != null) { %>  
  <tr class="containerMenu">
    <td bgColor="#F1F0E0">
      <font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosedString())%></font>
    </td>
  </tr>
<%}%>
  
  <tr>
		<td class="containerBack">
      <% if (TicketDetails.getClosed() != null) { %>
        <dhv:permission name="tickets-tickets-edit"><input type="button" value="Reopen"></dhv:permission>
      <%} else {%>
        <dhv:permission name="tickets-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='/TroubleTickets.do?command=Modify&auto-populate=true';submit();"></dhv:permission>
        <dhv:permission name="tickets-tickets-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='/TroubleTickets.do?command=Delete&id=<%= TicketDetails.getId()%>';confirmSubmit(this.form);"></dhv:permission>
      <%}%>
<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete"><br>&nbsp;<br></dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Ticket Information</strong>
    </td>     
  </tr>
  <tr class="containerBody">
		<td nowrap class="formLabel">
      Ticket Source
		</td>
		<td>
      <%=toHtml(TicketDetails.getSourceName())%>
		</td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="tickets-problem">Issue</dhv:label>
    </td>
    <td valign=top>
      <%= toHtml(TicketDetails.getProblem()) %>
      <input type="hidden" name="problem" value="<%=toHtml(TicketDetails.getProblem())%>">
      <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
      <input type="hidden" name="id" value="<%=TicketDetails.getId()%>">
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
		<td valign=top bgColor="white">
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
		
		<td valign="top">
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
    <td colspan="2" valign="center" align="left">
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
		<td colspan="3" valign="center" align="left">
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
			<td nowrap valign="top" width="100" class="formLabel">
        <%=toHtml(thisEntry.getEnteredByName())%>
			</td>
			<td nowrap valign="top" width="150">
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
    <tr>
      <td bgcolor="white">
        <font color="#9E9E9E" colspan="3">No Log Entries.</font>
			</td>
    </tr>
  <%}%>
</table>
&nbsp;<br>
		<% if (TicketDetails.getClosed() != null) { %>
      <dhv:permission name="tickets-tickets-edit"><input type=button value="Reopen"></dhv:permission>
		<%} else {%>
      <dhv:permission name="tickets-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='/TroubleTickets.do?command=Modify&auto-populate=true';submit();"></dhv:permission>
        <dhv:permission name="tickets-tickets-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='/TroubleTickets.do?command=Delete&id=<%= TicketDetails.getId()%>';confirmSubmit(this.form);"></dhv:permission>
		<%}%>
	</td>
  </tr>
</table>
</form>
