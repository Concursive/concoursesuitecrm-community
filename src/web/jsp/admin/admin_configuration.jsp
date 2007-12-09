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
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="permissionCategoryList" class="org.aspcfs.modules.admin.base.PermissionCategoryList" scope="request"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<dhv:label name="trails.configureModules">Configure Modules</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="admin.setupTheSystem.text">Setup the system to meet the specific needs of your organization, including configuration of lookup lists and custom fields.  Choose a module to proceed.</dhv:label></td></tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="admin.modules">Modules</dhv:label></strong>
    </th>
  </tr>
<%
  int column = 0;
  Iterator i = permissionCategoryList.iterator();
  while (i.hasNext()) {
    column = (column != 1 ? 1 : 2);
    PermissionCategory thisPermissionCat = (PermissionCategory) i.next();
%>
  <tr class="row<%= column %>">
    <td>
      <a href="Admin.do?command=ConfigDetails&moduleId=<%= thisPermissionCat.getId() %>"><dhv:label name="<%= String.valueOf(thisPermissionCat.getConstant()) %>"><%= thisPermissionCat.getCategory() %></dhv:label></a>
    </td>
  </tr>
<%}%>
</table>

