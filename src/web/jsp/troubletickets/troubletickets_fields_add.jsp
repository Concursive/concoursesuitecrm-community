<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<form name="details" action="TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do">Help Desk</a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home">View Tickets</a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords()) %>">
  <a href="TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>">List of Folder Records</a> >
</dhv:evaluate>
<dhv:evaluate if="<%= (!Category.getAllowMultipleRecords()) %>">
  <a href="TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>">Folder Record Details</a> >
</dhv:evaluate>
Add Folder Record
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="folders" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      Folder: <strong><%= Category.getName() %></strong><br>
      <dhv:evaluate exp="<%= !Category.isEmpty() %>">
        &nbsp;<br>
        <input type="submit" value="Save" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=InsertFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
        <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'"><br>
        &nbsp;<br>
      </dhv:evaluate>
<%
  Iterator groups = Category.iterator();
  if (groups.hasNext()) {
    while (groups.hasNext()) {
    CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><%= thisGroup.getName() %></strong>
	  </th>
  </tr>
<%  
  Iterator fields = thisGroup.iterator();
  if (fields.hasNext()) {
    while (fields.hasNext()) {
      CustomField thisField = (CustomField)fields.next();
%>    
    <tr class="containerBody">
      <%-- Do not use toHtml() here, it's done by CustomField --%>
      <td valign="top" nowrap class="formLabel">
        <%= thisField.getNameHtml() %>
      </td>
      <td valign="top">
        <%= thisField.getHtmlElement() %> <font color="red"><%= (thisField.getRequired()?"*":"") %></font>
        <font color="#006699"><%= toHtml(thisField.getError()) %></font>
        <%= toHtml(thisField.getAdditionalText()) %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E">No fields available.</font>
      </td>
    </tr>
<%}%>
</table>
&nbsp;
<%}%>
  <dhv:evaluate exp="<%= !Category.isEmpty() %>">
    <br>
    <input type="submit" value="Save" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=InsertFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
    </td></tr>
  </dhv:evaluate>
<%}else{%>
  <tr class="containerBody">
    <td colspan="2">
      <font color="#9E9E9E">No groups available.</font>
    </td>
  </tr>
<%}%>
</table>
</form>
