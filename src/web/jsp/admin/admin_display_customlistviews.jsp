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
  - Version: $Id: admin_display_customlistviews.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="listViewEditor" class="org.aspcfs.modules.admin.base.CustomListViewEditor" scope="request"/>
<jsp:useBean id="customListViews" class="org.aspcfs.modules.admin.base.CustomListViewList" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_display_customlistviews_menu.jsp" %>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
<dhv:label name="admin.customListViews">Custom List Views</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<a href="AdminCustomListViews.do?command=Add&moduleId=<%= permissionCategory.getId() %>&constantId=<%= listViewEditor.getConstantId() %>"><dhv:label name="admin.customListViews.addView">Add View</dhv:label></a>
&nbsp;<br /><br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th nowrap>
      <strong><a href="Accounts.do?command=Search&column=o.name"><dhv:label name="admin.customListViews.viewName">View Name</dhv:label></a></strong>
    </th>
    <th nowrap>
        <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
		</th>
    <th nowrap>
        <strong><dhv:label name="admin.customListViews.totalColumns">Total Columns</dhv:label></strong>
		</th>
    <th nowrap>
        <strong><dhv:label name="documents.details.approved">Approved</dhv:label></strong>
		</th>
  </tr>
<%
	Iterator j = customListViews.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    CustomListView thisView = (CustomListView)j.next();
%>
  <tr>
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuView', '<%= thisView.getId() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuView');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
		<td class="row<%= rowid %>">
      <a href=""><%= toHtml(thisView.getName()) %></a>
		</td>
    <td class="row<%= rowid %>"><%= toHtml(thisView.getDescription()) %></td>
    <td class="row<%= rowid %>"><%= thisView.getFieldCount() %></td>
    <%-- <td class="row<%= rowid %>"><%= toHtml(thisView.getApproved()) %></td> --%>
    <td class="row<%= rowid %>">&nbsp;</td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      <dhv:label name="admin.customListViews.noViewsFound.text">No views found</dhv:label>
    </td>
  </tr>
<%}%>
</table>
