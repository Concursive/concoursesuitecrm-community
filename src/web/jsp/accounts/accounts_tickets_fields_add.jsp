<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<form name="details" action="AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%= TicketDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%= TicketDetails.getOrgId() %>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords()) %>">
  <a href="AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>">List of Folder Records</a> >
</dhv:evaluate>
<dhv:evaluate if="<%= (!Category.getAllowMultipleRecords()) %>">
  <a href="AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>">Folder Record Details</a> >
</dhv:evaluate>
Add Folder Record
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="tickets" param="<%= "orgId=" + TicketDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
      <%@ include file="accounts_ticket_header_include.jsp" %>
      [ <dhv:container name="accountstickets" selected="folders" param="<%= "id=" + TicketDetails.getId() %>"/> ]<br>
      <br />
      Folder: <strong><%= Category.getName() %></strong><br />
      <dhv:evaluate exp="<%= !Category.isEmpty() %>">
        <br />
        <input type="submit" value="Save" onClick="javascript:this.form.action='AccountTicketFolders.do?command=InsertFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
        <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'"><br />
        <br />
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
    <input type="submit" value="Save" onClick="javascript:this.form.action='AccountTicketFolders.do?command=InsertFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>'">
    </td></tr>
  </dhv:evaluate>
<%}else{%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr class="containerBody">
    <td colspan="2">
      <font color="#9E9E9E">No groups available.</font>
    </td>
  </tr>
</table>
<%}%>
</table>
</form>
