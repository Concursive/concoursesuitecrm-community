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
  - Version: $Id: admin_listroles.jsp 11310 2005-04-13 20:05:00 +0000 (Wed, 13 Apr 2005) mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="roleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="portalRoleListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_portallist_menu.jsp" %>
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
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<dhv:label name="admin.viewPortalRoles">View Portal Roles</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="admin-roles-add"><a href="PortalRoleEditor.do?command=Add"><dhv:label name="admin.addNewPortalRole">Add New Portal Role</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="portalRoleListInfo"/></center></dhv:include>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="portalRoleListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <th width="35%" nowrap>
      <b><a href="PortalRoleEditor.do?command=List&column=role"><dhv:label name="project.portalrole">PortalRole</dhv:label></a></b>
      <%= portalRoleListInfo.getSortIcon("role") %>
    </th>
    <th width="65%" nowrap>
      <b><a href="PortalRoleEditor.do?command=List&column=description"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></a></b>
      <%= portalRoleListInfo.getSortIcon("description") %>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.active">Active</dhv:label></b>
    </th>
  </tr>
<%
  Iterator i = roleList.iterator();
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
          <a href="javascript:displayMenu('select<%= count %>','menuRole', '<%= thisRole.getId() %>', '<%= thisRole.getEnabled() ? 1 : 0 %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuRole');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td class="row<%= rowid %>">
          <a href="PortalRoleEditor.do?command=Modify&id=<%= thisRole.getId() %>"><%= toHtml(thisRole.getRole()) %></a>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisRole.getDescription()) %>
        </td>
        <td align="center" class="row<%= rowid %>">
          <%= thisRole.getEnabled() ? "true" : "false" %>
        </td>
      </tr>
<%      
    }
  } else {
%>  
  <tr>
    <td class="containerBody" colspan="5">
      <dhv:label name="admin.noRolesFound">No roles found.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="portalRoleListInfo" tdClass="row1"/>

