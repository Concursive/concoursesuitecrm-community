<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<form name="details" action="TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>" method="post">
<a href="TroubleTickets.do">Tickets</a> > 
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords()) %>">
  <a href="TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>">List of Folder Records</a> >
</dhv:evaluate>
<% if (request.getParameter("return") == null) {%>
	<a href="TroubleTicketsFolders.do?command=Fields&ticketId=<%=TicketDetails.getId()%>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>">Folder Record Details</a> >
<%}%>
Modify Folder Record
<hr color="#BFBFBB" noshade>
<strong>Ticket # <%= TicketDetails.getPaddedId() %><br>
<%= toHtml(TicketDetails.getCompanyName()) %></strong>
<dhv:evaluate exp="<%= !(TicketDetails.getCompanyEnabled()) %>"><font color="red">(account disabled)</font></dhv:evaluate>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="folders" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      Folder: <strong><%= Category.getName() %></strong><br>
      &nbsp;<br>
      <input type="submit" value="Update" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=UpdateFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
      <% if ("list".equals(request.getParameter("return"))) { %>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
      <% }else{ %>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
      <% } %><br>
      &nbsp;<br>
<%
  Iterator groups = Category.iterator();
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
        <font color='#006699'><%= toHtml(thisField.getError()) %></font>
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
<br>
<input type="submit" value="Update" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=UpdateFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
<% if("list".equals(request.getParameter("return"))) { %>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
<% }else{ %>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
<% } %>
</td></tr>
</table>
</form>
