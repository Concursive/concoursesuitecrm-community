<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="TicketDetails" class="com.darkhorseventures.cfsbase.Ticket" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="details" action="/TroubleTickets.do?command=Modify&auto-populate=true" method="post">
<a href="TroubleTickets.do?command=Home">Back to Ticket List</a><br>&nbsp;

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td bgColor="#FFFF95"><strong>Ticket #<%=TicketDetails.getId()%> - <%=toHtml(TicketDetails.getCompanyName())%></strong></td>
  </tr>
  
<% if (TicketDetails.getClosed() != null) { %>  
  <tr>
    <td bgColor="#F1F0E0">
      <font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosedString())%></font>
    </td>
  </tr>
<%}%>
  
  <tr>
  	<td>
  	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      				<tr>
		<td colspan=2 bgColor="white">
		
		<% if (TicketDetails.getClosed() != null) { %>
		        <dhv:permission name="tickets-tickets-edit"><input type="button" value="Reopen"></dhv:permission>
		<%} else {%>
		<dhv:permission name="tickets-tickets-edit"><input type="submit" value="Modify"></dhv:permission>
		<dhv:permission name="tickets-tickets-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='/TroubleTickets.do?command=Delete&id=<%= TicketDetails.getId()%>'"></dhv:permission>
		<%}%>
		
		</td>
		</tr>
		
		<tr bgcolor="#DEE0FA">
		<td colspan=2 valign=center align=left>
		<strong>Ticket Information</strong>
		</td>     
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Ticket Source
		</td>
		<td bgColor="white">
		<%=toHtml(TicketDetails.getSourceName())%>
		</td>
		</tr>
		
		<tr>
      <td width=100 class="formLabel">
        <dhv:label name="tickets-problem">Problem</dhv:label>
      </td>
      <td valign=top bgColor="white">
        <%= toHtml(TicketDetails.getProblem()) %>
        <input type=hidden name=problem value="<%=toHtml(TicketDetails.getProblem())%>">
        <input type=hidden name=orgId value="<%=TicketDetails.getOrgId()%>">
        <input type=hidden name=id value="<%=TicketDetails.getId()%>">
      </td>
		</tr>
		
    <dhv:include name="tickets-code" none="true">
		<tr>
		<td width=100 class="formLabel">
		Category
		</td>
		<td bgColor="white">
		<%=toHtml(TicketDetails.getCategoryName())%>
		</td>
		</tr>
    </dhv:include>
		
    <dhv:include name="tickets-severity" none="true">
		<tr>
		<td width=100 class="formLabel">
      Severity
    </td>
		<td valign=top bgColor="white">
      <%=toHtml(TicketDetails.getSeverityName())%>
		</td>
		</tr>
		</dhv:include>
	
    <dhv:include name="tickets-priority" none="true">
		<tr>
		<td width=100 class="formLabel">
      Priority
    </td>
		<td valign=top bgColor="white">
      <%=toHtml(TicketDetails.getPriorityName())%>
		</td>
		</tr>
		</dhv:include>
    
		<tr>
		<td width=100 class="formLabel">
		Department
		</td>
		<td bgcolor="white">
		<%= toHtml(TicketDetails.getDepartmentName()) %>
		</td>
		</tr>
				
		<tr>
		<td width=100 class="formLabel">
		Assigned To
		</td>
		<td bgColor="white">
		<%= toHtml(TicketDetails.getOwnerName()) %>
		</td>
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Solution
		</td>
		<td bgColor="white">
		<%= toHtml(TicketDetails.getSolution()) %>
		</td>
		</tr>
				
		<tr>
		<td width=100 class="formLabel">
      		Entered
    		</td>
		
		<td valign=top bgColor="white">
		<%=toHtml(TicketDetails.getEnteredByName())%> - <%=TicketDetails.getEnteredString()%>
		</td>
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
      		Modified
    		</td>
		
		<td valign=top bgColor="white">
		<%=toHtml(TicketDetails.getModifiedByName())%> - <%=TicketDetails.getModifiedString()%>
		</td>
		</tr>
	
		</table>
	</td>
  </tr>
<%
  if (TicketDetails.getThisContact() != null ) {
%>
  <tr>
  	<td>
  	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr bgcolor="#DEE0FA">
      <td colspan=4 valign=center align=left>
      <strong>Primary Contact</strong>
      </td>     
		</tr>
		<tr>
      <td width=100 class="formLabel">
          Name
      </td>
      <td bgColor="white">
      <%=toHtml(TicketDetails.getThisContact().getNameLast())%>, <%=toHtml(TicketDetails.getThisContact().getNameFirst())%>
      </td>
		</tr>
		<tr>
      <td width=100 class="formLabel">
          Title
      </td>
      <td bgColor="white">
      <%=toHtml(TicketDetails.getThisContact().getTitle())%>
      </td>
		</tr>
		<tr>
      <td width=100 class="formLabel">
          Email
      </td>
      <td bgColor="white">
      <a href="mailto:<%=toHtml(TicketDetails.getThisContact().getEmailAddress("Business"))%>"><%=toHtml(TicketDetails.getThisContact().getEmailAddress("Business"))%></a>
      </td>
		</tr>
		<tr>
      <td width=100 class="formLabel">
          Phone
      </td>
      <td bgColor="white">
      <%=TicketDetails.getThisContact().getPhoneNumber("Business")%>
      </td>
		</tr>
	</table>
	</td>
  </tr>
<%}%>
  <tr>
  	<td>
  	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	
			<tr bgcolor="#DEE0FA">
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
			<%=thisEntry.getEnteredString()%>
			</td>
			<td valign=center>
			<%=toHtml(thisEntry.getEntryText())%>
			</td>
			</tr>
	<%    
			}
		} else {
	%>
			<tr><td>
			<font color="#9E9E9E" colspan=3>No Log Entries.</font>
			</td></tr>
		<%}%>
				
		<tr>
		<td colspan=4 bgColor="white">
		<% if (TicketDetails.getClosed() != null) { %>
		        <dhv:permission name="tickets-tickets-edit"><input type=button value="Reopen"></dhv:permission>
		<%} else {%>
		<dhv:permission name="tickets-tickets-edit"><input type=submit value="Modify"></dhv:permission>
		<dhv:permission name="tickets-tickets-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='/TroubleTickets.do?command=Delete&id=<%= TicketDetails.getId()%>'"></dhv:permission>
		<%}%>
		</td>
		</tr>
   	</table>
	</td></tr>
</table>
</form>
