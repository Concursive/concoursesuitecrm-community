<%-- 
  - Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: companydirectory_fields.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Record" class="org.aspcfs.modules.base.CustomFieldRecord" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
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
<form name="details" action="SalesFolders.do?command=Fields&contactId=<%= ContactDetails.getId() %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<a href="Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>"><dhv:label name="LeadsDetails" mainMenuItem="true">Leads Details</dhv:label></a> >
<a href="SalesFolders.do?command=FolderList&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.Folders">Folders</dhv:label></a> > 
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record == null) %>">
  <dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record != null) %>">
<a href="SalesFolders.do?command=Fields&contactId=<%=ContactDetails.getId()%>&catId=<%= Category.getId() %>"><dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label></a> >
  <dhv:label name="accounts.accounts_fields.FolderRecordDetails">Folder Record Details</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= (!Category.getAllowMultipleRecords()) %>">
  <dhv:label name="accounts.accounts_fields.FolderRecordDetails">Folder Record Details</dhv:label>
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="leads" selected="folders" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
      <td>
        <dhv:label name="accounts.accounts_documents_folders_add.Folder.colon">Folder:</dhv:label>
        <strong><%= toHtml(Category.getName()) %></strong>
      </td>
      <% if (!Category.getAllowMultipleRecords()) { %>
        <td align="right" nowrap>
          <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0" />
          <dhv:label name="accounts.accounts_fields.FolderOneRecord">This folder can have only one record</dhv:label>
        </td>
      <% } else { %>
        <td align="right" nowrap>
          <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0" />
          <dhv:label name="accounts.accounts_fields.FolderHaveMultipleRecords">This folder can have multiple records</dhv:label>
        </td>
      <% } %>
    </tr>
  </table>
  <br>
  <dhv:evaluate if="<%= (ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !Category.getReadOnly()) %>">
    <dhv:permission name="sales-leads-folders-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=ModifyFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';submit();"></dhv:permission>
    <dhv:permission name="sales-leads-folders-delete"><input type="button" value="<dhv:label name="global.button.DeleteFolderRecord">Delete Folder Record</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=DeleteFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';confirmSubmit(this.form);"></dhv:permission>
    <dhv:permission name="sales-leads-folders-edit,sales-leads-folders-delete">
      <br><br><dhv:formMessage showSpace="false" />
    </dhv:permission>
  </dhv:evaluate>
  <%
    Iterator groups = Category.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
  %>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
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
        <td valign="top" class="formLabel" nowrap>
          <%= thisField.getNameHtml() %>
        </td>
        <td valign="top">
          <%= thisField.getValueHtml() %>
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
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= Record.getEnteredBy() %>" />
        <zeroio:tz timestamp="<%= Record.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= Record.getModifiedBy() %>" />
        <zeroio:tz timestamp="<%= Record.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
  </table>
  <br>
  <dhv:evaluate if="<%= (ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !Category.getReadOnly()) %>">
    <dhv:permission name="sales-leads-folders-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=ModifyFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';submit();"></dhv:permission>
    <dhv:permission name="sales-leads-folders-delete"><input type="button" value="<dhv:label name="global.button.DeleteFolderRecord">Delete Folder Record</dhv:label>" onClick="javascript:this.form.action='SalesFolders.do?command=DeleteFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';confirmSubmit(this.form);"></dhv:permission>
  </dhv:evaluate>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</dhv:container>
</form>
