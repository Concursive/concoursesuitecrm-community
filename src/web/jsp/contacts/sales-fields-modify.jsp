<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: companydirectory_fields_modify.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript">
function reopenContact(id) {
  if (id == '<%= ContactDetails.getId() %>') {
    scrollReload('ExternalContacts.do?command=SearchContacts');
    return -1;
  } else {
    return '<%= ContactDetails.getId() %>';
  }
}
</script>
<form name="details" action="ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Conta1cts" mainMenuItem="true">Leads</dhv:label></a> >
<a href="Sales.do?command=Details&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.Contact1Details">Leads Details</dhv:label></a> >
<a href="SalesFolders.do?command=FolderList&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.Folders">Folders</dhv:label></a> > 
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords()) %>">
  <a href="SalesFolders.do?command=Fields&contactId=<%=ContactDetails.getId()%>&catId=<%= Category.getId() %>"><dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label></a> >
</dhv:evaluate>
<% if (request.getParameter("return") == null) {%>
	<a href="SalesFolders.do?command=Fields&contactId=<%=ContactDetails.getId()%>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>"><dhv:label name="accounts.accounts_fields.FolderRecordDetails">Folder Record Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.accounts_fields_modify.ModifyFolderRecord">Modify Folder Record</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="leads" selected="folders" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
      <td>
        <dhv:label name="accounts.accounts_documents_folders_add.Folder">Folder</dhv:label>:
        <strong><%= toHtml(Category.getName()) %></strong>
      </td>
    <% if (!Category.getAllowMultipleRecords()) { %>
      <td align="right" nowrap>
        <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0">
        <dhv:label name="accounts.accounts_fields.FolderOneRecord">This folder can have only one record</dhv:label>
      </td>
    <% } else { %>
      <td align="right" nowrap>
        <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0">
        <dhv:label name="accounts.accounts_fields.FolderHaveMultipleRecords">This folder can have multiple records</dhv:label>
      </td>
    <% } %>
    </tr>
  </table>
  <dhv:evaluate if="<%= !Category.isEmpty() %>">
    &nbsp;<br>
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=UpdateFields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=Fields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>'"><br>
    &nbsp;<br><dhv:formMessage showSpace="false" />
  </dhv:evaluate>
<%
  Iterator groups = Category.iterator();
  while (groups.hasNext()) {
    CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>    
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" valign="center">
        <strong><%= StringUtils.toHtml(thisGroup.getName()) %></strong>
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
      <td valign="center" nowrap class="formLabel">
        <%= thisField.getNameHtml() %>
      </td>
      <td valign="center">
        <%= thisField.getHtmlElement(systemStatus) %> <font color="red"><%= (thisField.getRequired()?"*":"") %></font>
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
        <font color="#9E9E9E"><dhv:label name="accounts.accounts_fields.NoFieldsAvailable">No fields available.</dhv:label></font>
      </td>
    </tr>
<%}%>
  </table>
  &nbsp;
<%}%>
  <dhv:evaluate if="<%= !Category.isEmpty() %>">
    <br>
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=UpdateFields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=Fields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>'">
  </dhv:evaluate>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</dhv:container>
</form>
