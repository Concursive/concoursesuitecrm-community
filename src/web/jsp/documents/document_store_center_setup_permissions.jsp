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
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.* " %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="categories" class="org.aspcfs.modules.documents.base.DocumentStorePermissionCategoryLookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/swapClass.js"></script>
<script language="JavaScript" type="text/javascript">
  function updateRole() {}
</script>
<form method="POST" name="inputForm" action="DocumentManagement.do?command=UpdatePermissions">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      <a href="DocumentManagement.do?command=DocumentStoreCenter&section=Setup&documentStoreId=<%= documentStore.getId() %>"><dhv:label name="documents.permissions.setup">Setup</dhv:label></a> >
      <dhv:label name="documents.permissions.configurePermissions">Configure Permissions</dhv:label>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="documents.permissions.update">Update </dhv:label>" />
<input type="button" value="<dhv:label name="documents.permissions.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='DocumentManagement.do?command=DocumentStoreCenter&section=Setup&documentStoreId=<%= documentStore.getId() %>'" />
<br />
&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <input type="hidden" name="documentStoreId" value="<%= documentStore.getId() %>">
  <input type="hidden" name="id" value="<%= documentStore.getId() %>">
  <input type="hidden" name="modified" value="<%= documentStore.getModified() %>">
  <tr>
    <th colspan="4" valign="center">
      <strong>
        <dhv:label name="documents.permissions.permissions">Permissions</dhv:label>
      </strong>
    </th>
  </tr>
<%
   int permissionCount = 0;
   Iterator i = categories.iterator();
   while (i.hasNext()) {
     DocumentStorePermissionCategoryLookup thisCategory = (DocumentStorePermissionCategoryLookup) i.next();
%>
<%-- For each category --%>
  <tr class="row1">
    <td width="85%" nowrap><%= toHtml(thisCategory.getDescription()) %></td>
    <td>
      <dhv:label name="documents.permissions.lowestAccessLevel">Lowest Access level</dhv:label>
    </td>
  </tr>
<%
    Iterator j = thisCategory.getPermissions().iterator();
    while (j.hasNext()) {
      ++permissionCount;
      DocumentStorePermissionLookup thisPermission = (DocumentStorePermissionLookup) j.next();
%>
<%-- For each permission --%>
  <tr class="row2" onmouseover="swapClass(this,'rowHighlight')" onmouseout="swapClass(this,'row2')">
    <td nowrap>&nbsp; &nbsp;<%= toHtml(thisPermission.getDescription()) %></td>
    <td>
      <input type="hidden" name="perm<%= permissionCount %>" value="<%= thisPermission.getId() %>">
      <dhv:documentRoleSelect
          name='<%= "perm" + permissionCount + "level" %>'
          value="<%= documentStore.getAccessUserLevel(thisPermission.getPermission()) %>"/>
    </td>
  </tr>
<%-- End Content --%>
<%
     }
   }
%>
</table>
<br />
<input type="submit" value="<dhv:label name="documents.permissions.update">Update</dhv:label>" />
<input type="button" value="<dhv:label name="documents.permissions.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='DocumentManagement.do?command=DocumentStoreCenter&section=Setup&documentStoreId=<%= documentStore.getId() %>'" />
</form>
