<%-- 
  - Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Records" class="org.aspcfs.modules.base.CustomFieldRecordList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="leads_opp_fields_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

<a href="Leads.do?command=Dashboard"><dhv:label name="pipeline.pipeline">PIPELINE</dhv:label></a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<% }else{ %>
	<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>">Opportunity Details</a> >
<a href="LeadsFolders.do?command=FolderList&headerId=<%= OpportunityHeader.getId() %>"><dhv:label name="accounts.Folders">Folders</dhv:label></a> > 
<dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param='<%= "username="+PipelineViewpointInfo.getVpUserName() %>'><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<dhv:container name="opportunities" selected="folders" object="OpportunityHeader" param='<%= "id=" + OpportunityHeader.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
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
  <br />
    <dhv:hasAuthority owner="<%= OpportunityHeader.getManagerOwnerIdRange() %>">
    <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
      <dhv:permission name="pipeline-folders-add">
        <a href="LeadsFolders.do?command=AddFolderRecord&headerId=<%= OpportunityHeader.getId() %>&catId=<%= Category.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_fields_list.AddRecordToFolder">Add a record to this folder</dhv:label></a>
        <br>&nbsp;<br />
      </dhv:permission>
    </dhv:evaluate>
    </dhv:hasAuthority>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
      <tr>
        <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
        <th valign="center">
          &nbsp;
        </th>
        </dhv:evaluate>
      <th>
        <dhv:label name="accounts.accounts_fields_list.Record">Record</dhv:label>
        </th>
      <th>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
        </th>
      <th>
        <dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label>
        </th>
      <th>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label>
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
        boolean hasPermission = false;
%>
  <dhv:hasAuthority owner="<%= OpportunityHeader.getManagerOwnerIdRange() %>">
    <% hasPermission = true; %>
  </dhv:hasAuthority>
      <tr class="containerBody">
        <dhv:evaluate if="<%= (!Category.getReadOnly()) %>">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
           <a href="javascript:displayMenu('select<%= count %>','menuField','<%= OpportunityHeader.getId() %>', '<%= Category.getId() %>', '<%= thisRecord.getId() %>', '<%= hasPermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuField');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
          </td>
        </dhv:evaluate>
        <td align="left" width="100%" nowrap class="row<%= rowid %>">
          <a href="LeadsFolders.do?command=Fields&headerId=<%= OpportunityHeader.getId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisRecord.getFieldData() != null ? thisRecord.getFieldData().getValueHtml(false) : "&nbsp;" %></a>
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
%>
   </table>
<%
    } else {
%>
      <tr class="containerBody">
        <td colspan="5">
          <font color="#9E9E9E"><dhv:label name="accounts.accounts_fields_list.NoRecordsEntered">No records have been entered.</dhv:label></font>
        </td>
      </tr>
   </table>
<%
    }
  } else {
%>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr class="row2">
      <td>
        <dhv:label name="accounts.accounts_fields_list.NoCustomFoldersAvailable">No custom folders available.</dhv:label>
      </td>
    </tr>
  </table>
<%}%>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</dhv:container>
