<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="categories" class="org.aspcfs.modules.admin.base.PermissionCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Reports.do">Reports</a> >
<a href="Reports.do">Queue</a> >
Modules
</td>
</tr>
</table>
<%-- End Trails --%>
To add a report to the queue, choose a module to see a list of corresponding reports:<br>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="100%">
      <strong>Module</strong>
    </th>
  </tr>
<dhv:evaluate if="<%= categories.size() == 0 %>">
  <tr>
    <td>No modules found with reports enabled</td>
  </tr>
</dhv:evaluate>
<%
  Iterator i = categories.iterator();
  int row = 0;
  while (i.hasNext()) {
    PermissionCategory category = (PermissionCategory) i.next();
%>
  <tr class="row<%= ++row%2 == 0 ? "2" : "1" %>">
    <td><a href="Reports.do?command=ListReports&categoryId=<%= category.getId() %>"><%= toHtml(category.getCategory()) %></a></td>
  </tr>
<%
  }
%>
</table>
