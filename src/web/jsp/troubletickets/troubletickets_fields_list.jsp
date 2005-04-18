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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Records" class="org.aspcfs.modules.base.CustomFieldRecordList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_fields_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<form name="details" action="TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTickets.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
<a href="TroubleTicketsFolders.do?command=FolderList&ticketId=<%= TicketDetails.getId() %>"><dhv:label name="accounts.Folders">Folders</dhv:label></a> >
<dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="folders" object="TicketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
      <td>
        <dhv:label name="accounts.accounts_documents_folders_add.Folder.colon">Folder:</dhv:label>
        <strong><%= toHtml(Category.getName()) %></strong>
      </td>
      <% if (CategoryList.size() > 0) { %>
        <td align="right" nowrap>
        <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0">
        <dhv:label name="accounts.accounts_fields.FolderHaveMultipleRecords">This folder can have multiple records</dhv:label>
      </td>
    <% } %>
    </tr>
  </table>
<% 
  if (CategoryList.size() > 0) {
%>
      &nbsp;<br>
    <dhv:evaluate if="<%= (!Category.getReadOnly()) %>"><dhv:permission name="accounts-accounts-folders-add"><a href="TroubleTicketsFolders.do?command=AddFolderRecord&ticketId=<%= TicketDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>"><dhv:label name="accounts.accounts_fields_list.AddRecordToFolder">Add a record to this folder</dhv:label></a><br>&nbsp;<br></dhv:permission></dhv:evaluate>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
      <tr>
        <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
        <dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete">
          <th valign="center">
            &nbsp;
          </th>
        </dhv:permission>
        </dhv:evaluate>
        <th>
          <strong><dhv:label name="accounts.accounts_fields_list.Record">Record</dhv:label></strong>
        </th>
        <th>
          <strong><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></strong>
        </th>
        <th>
          <strong><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></strong>
        </th>
        <th>
          <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></strong>
        </th>
      </tr>
<%
    if (Records.size() > 0) {
      int rowid = 0;
      int i = 0;
      Iterator records = Records.iterator();
      while (records.hasNext()) {
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        CustomFieldRecord thisRecord = (CustomFieldRecord)records.next();
%>    
      <tr class="containerBody">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <%-- To display the menu, pass the actionId, accountId and the contactId--%>
           <a href="javascript:displayMenu('select<%= i %>','menuFolders', '<%= TicketDetails.getId() %>', '<%= Category.getId() %>', '<%= thisRecord.getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuFolders');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </dhv:evaluate>
        </td>
        <td align="left" width="100%" nowrap class="row<%= rowid %>">
          <a href="TroubleTicketsFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>"><%= thisRecord.getFieldData().getValueHtml(false) %></a>
        </td>
        <td nowrap class="row<%= rowid %>">
        <zeroio:tz timestamp="<%= thisRecord.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:username id="<%= thisRecord.getModifiedBy() %>" />
        </td>
        <td nowrap class="row<%= rowid %>">
        <zeroio:tz timestamp="<%= thisRecord.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
        </td>
      </tr>
<%    
      }
    } else {
%>
      <tr class="containerBody">
        <td colspan="5">
          <font color="#9E9E9E"><dhv:label name="accounts.accounts_fields_list.NoRecordsEntered">No records have been entered.</dhv:label></font>
        </td>
      </tr>
<%  }  %>
<%} else {%>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr class="row2">
      <td>
        <dhv:label name="accounts.accounts_fields_list.NoCustomFoldersAvailable">No custom folders available.</dhv:label>
      </td>
    </tr>
<%}%>
  </table>
</dhv:container>
</form>
