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
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="RoleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="RoleListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_listroles_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
View Roles
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="admin-roles-add"><a href="Roles.do?command=InsertRoleForm">Add New Role</a></dhv:permission>
<center><%= RoleListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="RoleListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="35%" nowrap>
      <b><a href="Roles.do?command=ListRoles&column=role">Role</a></b>
      <%= RoleListInfo.getSortIcon("role") %>
    </th>
    <th width="65%" nowrap>
      <b><a href="Roles.do?command=ListRoles&column=description">Description</a></b>
      <%= RoleListInfo.getSortIcon("description") %>
    </th>
    <th nowrap>
      <b># of users</b>
    </th>
  </tr>
<%
  Iterator i = RoleList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    int count = 0;
    while (i.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      Role thisRole = (Role)i.next();
%>      
      <tr>
        <td nowrap class="row<%= rowid %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <a href="javascript:displayMenu('select<%= count %>','menuRole', '<%= thisRole.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuRole');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td class="row<%= rowid %>">
          <a href="Roles.do?command=RoleDetails&id=<%= thisRole.getId() %>"><%= toHtml(thisRole.getRole()) %></a>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisRole.getDescription()) %>
        </td>
        <td align="center" class="row<%= rowid %>">
          <%= thisRole.getUserCount() %>
        </td>
      </tr>
<%      
    }
  } else {
%>  
  <tr>
    <td class="containerBody" colspan="5">
      No roles found.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="RoleListInfo" tdClass="row1"/>

