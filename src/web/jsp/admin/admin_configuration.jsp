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
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategoryList" class="org.aspcfs.modules.admin.base.PermissionCategoryList" scope="request"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
Configure Modules
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>Setup the system to meet the specific needs of your organization, 
    including configuration of lookup lists and custom fields.  Choose
    a module to proceed.</td></tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Modules</strong>
    </th>
  </tr>
<%
  int column = 0;
  Iterator i = PermissionCategoryList.iterator();
  while (i.hasNext()) {
    column = (column != 1 ? 1 : 2);
    PermissionCategory thisPermissionCat = (PermissionCategory) i.next();
%>
  <tr class="row<%= column %>">
    <td>
      <a href="Admin.do?command=ConfigDetails&moduleId=<%= thisPermissionCat.getId() %>"><%= toHtml(thisPermissionCat.getCategory()) %></a>
    </td>
  </tr>
<%}%>
</table>

