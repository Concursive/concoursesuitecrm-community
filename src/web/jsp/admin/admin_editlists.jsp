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
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.utils.web.LookupList,org.aspcfs.utils.web.LookupListElement" %>
<jsp:useBean id="LookupLists" class="org.aspcfs.utils.web.LookupListList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<dhv:label name="admin.lookupLists">Lookup Lists</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="35" align="center">
      &nbsp;
    </th>
    <th width="200">
      <strong><dhv:label name="admin.listName">List Name</dhv:label></strong>
    </th>
    <th width="35" align="center">
      <strong><dhv:label name="project.items">Items</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="button.preview">Preview</dhv:label></strong>
    </th>
  </tr>
<%
  int rowid = 0;
  Iterator i = LookupLists.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1 ? 1 : 2);
    LookupListElement thisElement = (LookupListElement)i.next();
%>
    <tr class="row<%= rowid %>">
      <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="Admin.do?command=ModifyList&module=<%= thisElement.getModuleId() %>&sublist=<%= thisElement.getLookupId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a></td></dhv:permission>
      <td valign="center" width="200"><%= toHtml(thisElement.getDescription()) %></td>
      <td width="35" valign="center" align="center"><%= thisElement.getLookupList().getEnabledElementCount() %></td>
      <td valign="center"><%= thisElement.getLookupList().getHtmlSelect("list", 0) %></td>
    </tr>
<%
     }
%>
<dhv:evaluate if="<%= LookupLists.size() == 0 %>">
  <tr>
    <td valign="center" colspan="4"><dhv:label name="admin.noCustomLookupLists.text">No custom lookup lists to configure.</dhv:label></td>
  </tr>
</dhv:evaluate>
</table>
