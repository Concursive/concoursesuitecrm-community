<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Records" class="org.aspcfs.modules.base.CustomFieldRecordList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_fields_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<form name="details" action="Contacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscontacts" selected="folders" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
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
    <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
      <dhv:permission name="accounts-accounts-contacts-folders-add">
        <a href="Contacts.do?command=AddFolderRecord&contactId=<%= ContactDetails.getId() %>&catId=<%=(String)request.getAttribute("catId") %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_fields_list.AddRecordToFolder">Add a record to this folder</dhv:label></a><br>&nbsp;<br>
      </dhv:permission>
    </dhv:evaluate>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
      <tr>
        <dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
        <th width="8">&nbsp;</th>
      </dhv:evaluate>
      <th align="left">
        <strong><dhv:label name="accounts.accounts_fields_list.Record">Record</dhv:label></strong>
      </th>
      <th align="left">
        <strong><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></strong>
      </th>
      <th align="left">
        <strong><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></strong>
      </th>
      <th align="left">
        <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></strong>
        </th>
      </tr>
<%
    if (Records.size() > 0) {
      int rowid = 0;
      int count = 0;
      Iterator records = Records.iterator();
      while (records.hasNext()) {
        count++;
        rowid = (rowid == 1 ? 2 : 1);
        CustomFieldRecord thisRecord = (CustomFieldRecord)records.next();
%>    
      <tr class="containerBody">
        <dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <%-- <a href="javascript:displayMenu('menuField','<%= ContactDetails.getId() %>', '<%= Category.getId() %>', '<%= thisRecord.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>)"><img src="images/select.gif" name="select<%= count %>" align="absmiddle" border="0"></a>--%>
          <a href="javascript:displayMenu('select<%= count %>','menuField','<%= ContactDetails.getId() %>', '<%= Category.getId() %>', '<%= thisRecord.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuField');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
          </td>
        </dhv:evaluate>
        <td align="left" width="100%" nowrap class="row<%= rowid %>">
          <a href="Contacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisRecord.getFieldData() != null ? thisRecord.getFieldData().getValueHtml(false) : "&nbsp;" %></a>
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:tz timestamp="<%= thisRecord.getEntered()  %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:username id="<%= thisRecord.getModifiedBy() %>" />
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:tz timestamp="<%= thisRecord.getModified()  %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
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
<%}
 } else {
%>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr class="containerBody">
      <td>
        <dhv:label name="accounts.accounts_fields_list.NoCustomFoldersAvailable">No custom folders available.</dhv:label>
      </td>
    </tr>
<%}%>
   </table>
  </dhv:container>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>
