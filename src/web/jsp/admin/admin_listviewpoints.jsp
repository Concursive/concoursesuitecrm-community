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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="ViewpointList" class="org.aspcfs.modules.admin.base.ViewpointList" scope="request"/>
<jsp:useBean id="ViewpointListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_listviewpoints_menu.jsp" %>
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
<a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
<a href="Users.do?command=UserDetails&id=<%= request.getParameter("userId") %>"><dhv:label name="admin.userDetails">User Details</dhv:label></a> >
<dhv:label name="users.viewpoints.long_html">Viewpoints</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="users" selected="viewpoints" object="UserRecord" param='<%= "id=" + UserRecord.getId() %>'>
  <dhv:permission name="admin-roles-add"><a href="Viewpoints.do?command=InsertViewpointForm&userId=<%= UserRecord.getId() %>"><dhv:label name="admin.addNewViewpoint">Add New Viewpoint</dhv:label></a></dhv:permission>
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
  <center><dhv:pagedListAlphabeticalLinks object="ViewpointListInfo"/></center></dhv:include>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ViewpointListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8" nowrap>
        &nbsp;
      </th>
      <th nowrap>
        <b><a href="Viewpoints.do?command=ListViewpoints&userId=<%= UserRecord.getId()%>&column=c.namelast"><dhv:label name="admin.viewpoint">Viewpoint</dhv:label></a></b>
        <%= ViewpointListInfo.getSortIcon("c.namelast") %>
      </th>
      <th width="120">
        <b><a href="Viewpoints.do?command=ListViewpoints&userId=<%= UserRecord.getId()%>&column=vp.entered"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></b>
        <%= ViewpointListInfo.getSortIcon("vp.entered") %>
      </th>
      <th width="30" nowrap>
        <b><a href="Viewpoints.do?command=ListViewpoints&userId=<%= UserRecord.getId()%>&column=vp.enabled"><dhv:label name="product.enabled">Enabled</dhv:label></a></b>
        <%= ViewpointListInfo.getSortIcon("vp.enabled") %>
      </th>
    </tr>
  <%
    Iterator i = ViewpointList.iterator();
    if (i.hasNext()) {
      int rowid = 0;
      int count = 0;
      while (i.hasNext()) {
        count++;
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
        Viewpoint thisViewpoint = (Viewpoint)i.next();
  %>
        <tr class="row<%= rowid %>">
          <td width="8" nowrap>
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
            <a href="javascript:displayMenu('select<%= count %>','menuViewpoint', '<%= UserRecord.getId() %>','<%= thisViewpoint.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuViewpoint');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
          </td>
          <td width="100%">
            <a href="Viewpoints.do?command=ViewpointDetails&id=<%= thisViewpoint.getId() %>&userId=<%= UserRecord.getId()%>"><%= thisViewpoint.getVpUser().getContact().getNameLastFirst() %></a>
          </td>
          <td nowrap>
            <zeroio:tz timestamp="<%= thisViewpoint.getEntered() %>" />
          </td>
          <td nowrap>
            <% if(thisViewpoint.getEnabled()) {%>
              <font color="green"><dhv:label name="account.yes">Yes</dhv:label></font>
            <%} else {%>
              <font color="red"><dhv:label name="account.no">No</dhv:label></font>
            <%}%>
          </td>
        </tr>
  <%
      }
    } else {
  %>
    <tr>
      <td class="containerBody" colspan="4">
        <dhv:label name="admin.noViewpointsFound">No Viewpoints found.</dhv:label>
      </td>
    </tr>
  <%
    }
  %>
  </table>
  <br>
  <dhv:pagedListControl object="ViewpointListInfo" tdClass="empty"/>
</dhv:container>
