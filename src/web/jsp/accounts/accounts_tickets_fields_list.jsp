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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Records" class="org.aspcfs.modules.base.CustomFieldRecordList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_tickets_fields_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<form name="details" action="AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%= TicketDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%= TicketDetails.getOrgId() %>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
List of Folder Records
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
<%
  CategoryList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
  if (CategoryList.size() > 0) {
%>
    <br />
    Folder: <%= CategoryList.getHtmlSelect("catId", (String)request.getAttribute("catId")) %><%= (Category.getReadOnly()?"&nbsp;<img border='0' valign='absBottom' src='images/lock.gif' alt='Folder is read-only'>":"") %><br />
    <br />
    This folder can have multiple records...<br>
    <br />
    <dhv:evaluate exp="<%= (!Category.getReadOnly()) %>"><dhv:permission name="accounts-accounts-folders-add"><a href="AccountTicketFolders.do?command=AddFolderRecord&ticketId=<%= TicketDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>">Add a record to this folder</a><br>&nbsp;<br></dhv:permission></dhv:evaluate>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
      <tr>
        <dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
        <dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete">
          <th valign="center">
            <strong>Action</strong>
          </th>
        </dhv:permission>
        </dhv:evaluate>
        <th>
          <strong>Record</strong>
        </th>
        <th>
          <strong>Entered</strong>
        </th>
        <th>
          <strong>Modified By</strong>
        </th>
        <th>
          <strong>Last Modified</strong>
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
          <dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <%-- To display the menu, pass the actionId, accountId and the contactId--%>
           <a href="javascript:displayMenu('select<%= i %>','menuFolders', '<%= TicketDetails.getId() %>', '<%= Category.getId() %>', '<%= thisRecord.getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuFolders');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </dhv:evaluate>
        </td>
        <td align="left" width="100%" nowrap class="row<%= rowid %>">
          <a href="AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>"><%= thisRecord.getFieldData().getValueHtml(false) %></a>
        </td>
        <td nowrap class="row<%= rowid %>">
        <zeroio:tz timestamp="<%= thisRecord.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" />
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:username id="<%= thisRecord.getModifiedBy() %>" />
        </td>
        <td nowrap class="row<%= rowid %>">
        <zeroio:tz timestamp="<%= thisRecord.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" />
        </td>
      </tr>
<%    
      }
    } else {
%>
      <tr class="containerBody">
        <td colspan="5">
          <font color="#9E9E9E">No records have been entered.</font>
        </td>
      </tr>
<%  }  %>
<%} else {%>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr class="containerBody">
      <td>
        No custom folders available.
      </td>
    </tr>
<%}%>
  </table>
</td></tr>
</table>
</form>
