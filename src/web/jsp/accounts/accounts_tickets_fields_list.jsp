<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Records" class="org.aspcfs.modules.base.CustomFieldRecordList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<form name="details" action="AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%= TicketDetails.getOrgId() %>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%= TicketDetails.getOrgId() %>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
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
    Folder: <%= CategoryList.getHtmlSelect("catId", (String)request.getAttribute("catId")) %><%= (Category.getReadOnly()?"&nbsp;<img border='0' valign='absBottom' src='images/lock.gif' alt='Folder is read-only'>":"") %><br>
    &nbsp;<br>
    This folder can have multiple records...<br>
    &nbsp;<br>
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
      Iterator records = Records.iterator();
      while (records.hasNext()) {
        rowid = (rowid != 1 ? 1 : 2);
        CustomFieldRecord thisRecord = (CustomFieldRecord)records.next();
%>    
      <tr class="containerBody">
        <dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
        <dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:permission name="accounts-accounts-folders-edit"><a href="AccountTicketFolders.do?command=ModifyFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-folders-delete"><a href="javascript:confirmDelete('AccountTicketFolders.do?command=DeleteFields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
        </dhv:evaluate>
      
        <td align="left" width="100%" nowrap class="row<%= rowid %>">
          <a href="AccountTicketFolders.do?command=Fields&ticketId=<%= TicketDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>"><%= thisRecord.getFieldData().getValueHtml(false) %></a>
        </td>
        <td nowrap class="row<%= rowid %>">
          <%= toHtml(thisRecord.getEnteredString()) %>
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:username id="<%= thisRecord.getModifiedBy() %>" />
        </td>
        <td nowrap class="row<%= rowid %>">
          <%= toHtml(thisRecord.getModifiedDateTimeString()) %>
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
