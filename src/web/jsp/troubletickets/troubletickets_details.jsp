<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="details" action="TroubleTickets.do?command=Modify&auto-populate=true" method="post">
<a href="TroubleTickets.do">Tickets</a> > 
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
Ticket Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
    <strong>Ticket # <%= TicketDetails.getPaddedId() %><br>
    <%= toHtml(TicketDetails.getCompanyName()) %></strong>
    <dhv:evaluate exp="<%= !(TicketDetails.getCompanyEnabled()) %>"><font color="red">(account disabled)</font></dhv:evaluate>
    </td>
  </tr>
<dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
  <tr class="containerMenu">
    <td bgColor="#F1F0E0">
      <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
    </td>
  </tr>
</dhv:evaluate>
  <tr>
		<td class="containerBack">
      <% if (TicketDetails.getClosed() != null) { %>
        <dhv:permission name="tickets-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();"></dhv:permission>
      <%} else {%>
        <dhv:permission name="tickets-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='TroubleTickets.do?command=Modify&auto-populate=true';submit();"></dhv:permission>
        <dhv:permission name="tickets-tickets-delete"><input type="button" value="Delete" onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');"></dhv:permission>
      <%}%>
<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete"><br>&nbsp;<br></dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Ticket Information</strong>
    </td>     
  </tr>
  <tr class="containerBody">
		<td nowrap class="formLabel">
      Ticket Source
		</td>
		<td>
      <%= toHtml(TicketDetails.getSourceName()) %>
		</td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="tickets-problem">Issue</dhv:label>
    </td>
    <td valign="top">
<%
  Iterator files = TicketDetails.getFiles().iterator();
  while (files.hasNext()) {
    FileItem thisFile = (FileItem)files.next();
    if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom">Play Audio Message</a><br>
<%
    }
  }
%>
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
      <%= toHtml(TicketDetails.getCategoryName()) %>
		</td>
  </tr>
</dhv:include>
<dhv:include name="tickets-severity" none="true">
  <tr class="containerBody">
		<td class="formLabel">
      Severity
    </td>
		<td>
      <%= toHtml(TicketDetails.getSeverityName()) %>
		</td>
  </tr>
</dhv:include>
<dhv:include name="tickets-priority" none="true">
  <tr class="containerBody">
		<td class="formLabel">
      Priority
    </td>
		<td>
      <%= toHtml(TicketDetails.getPriorityName()) %>
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
      <dhv:evaluate if="<%= !(TicketDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel" valign="top">
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
		<td>
      <%= toHtml(TicketDetails.getEnteredByName()) %> - <%= TicketDetails.getEnteredString() %>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Modified
    </td>
		<td>
      <%= toHtml(TicketDetails.getModifiedByName()) %> - <%= TicketDetails.getModifiedString() %>
		</td>
  </tr>
</table>
&nbsp;
<dhv:evaluate if="<%= TicketDetails.getThisContact() != null %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Primary Contact</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Name
    </td>
    <td>
      <%= toHtml(TicketDetails.getThisContact().getNameLastFirst()) %>
    </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      Title
    </td>
    <td>
      <%= toHtml(TicketDetails.getThisContact().getTitle()) %>
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
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
		<td colspan="3">
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
			<td nowrap valign="top" class="formLabel">
        <%= toHtml(thisEntry.getEnteredByName()) %>
			</td>
			<td nowrap valign="top">
        <%= thisEntry.getEnteredString() %>
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
        <font color="#9E9E9E" colspan="3">No Log Entries.</font>
			</td>
    </tr>
  <%}%>
</table>
&nbsp;

<%-- include the tasks created --%>
<%@ include file="troubletickets_tasklist.jsp" %>

&nbsp;
<br>
<% if (TicketDetails.getClosed() != null) { %>
  <dhv:permission name="tickets-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();"></dhv:permission>
<%} else {%>
  <dhv:permission name="tickets-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='TroubleTickets.do?command=Modify&auto-populate=true';submit();"></dhv:permission>
  <dhv:permission name="tickets-tickets-delete"><input type="button" value="Delete" onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');"></dhv:permission>
<%}%>
	</td>
  </tr>
</table>
</form>
