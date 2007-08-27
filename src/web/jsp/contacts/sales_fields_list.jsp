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
  - Version: $Id: sales_fields_list.jsp
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Records" class="org.aspcfs.modules.base.CustomFieldRecordList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sales_fields_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function reopenContact(id) {
    if (id == '<%= ContactDetails.getId() %>') {
      scrollReload('SalesFolders.do?command=SearchContacts');
      return -1;
    } else {
      return '<%= ContactDetails.getId() %>';
    }
  }
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<a href="Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>"><dhv:label name="LeadsDetails" mainMenuItem="true">Leads Details</dhv:label></a> >
<a href="SalesFolders.do?command=FolderList&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.Folders">Folders</dhv:label></a> > 
<dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label>
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
  <dhv:evaluate if="<%= (ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !Category.getReadOnly()) %>">
    <dhv:permission name="sales-leads-folders-add"><a href="SalesFolders.do?command=AddFolderRecord&contactId=<%= ContactDetails.getId() %>&catId=<%=(String)request.getAttribute("catId") %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_fields_list.AddRecordToFolder">Add a record to this folder</dhv:label></a><br>&nbsp;<br></dhv:permission>
  </dhv:evaluate>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
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
      int count  =0 ;
      Iterator records = Records.iterator();
      while (records.hasNext()) {
        count++;
        rowid = (rowid == 1 ? 2 : 1);
        CustomFieldRecord thisRecord = (CustomFieldRecord)records.next();
%>    
    <tr class="row<%= rowid %>">
      <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <dhv:evaluate if="<%= (ContactDetails.getEnabled() && !ContactDetails.isTrashed()) %>">
            <a href="javascript:displayMenu('select<%= count %>','menuField','<%= ContactDetails.getId() %>', '<%= Category.getId() %>', '<%= thisRecord.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuField');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
          </dhv:evaluate>
          <dhv:evaluate if="<%= (!ContactDetails.getEnabled() || ContactDetails.isTrashed() ) %>">&nbsp;</dhv:evaluate>
        </td>
      </dhv:evaluate>
      <td align="left" width="100%" nowrap>
        <a href="SalesFolders.do?command=Fields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisRecord.getFieldData() != null ? thisRecord.getFieldData().getValueHtml(false) : "&nbsp;" %></a>
      </td>
      <td nowrap>
      <zeroio:tz timestamp="<%= thisRecord.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
      <td nowrap>
        <dhv:username id="<%= thisRecord.getModifiedBy() %>" />
      </td>
      <td nowrap>
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
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr class="row2">
      <td>
        <dhv:label name="accounts.accounts_fields_list.NoCustomFoldersAvailable">No custom folders available.</dhv:label>
      </td>
    </tr>
<%}%>
  </table>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</dhv:container>
