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
<%@ page import="java.util.*,org.aspcfs.modules.documents.base.*" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="currentMember" class="org.aspcfs.modules.documents.base.DocumentStoreTeamMember" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      <dhv:label name="documents.permissions.setup">Setup</dhv:label>
    </td>
  </tr>
</table>
<dhv:evaluate if="<%= currentMember.getRoleId() <= DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER %>">
<br>
<a href="DocumentManagement.do?command=ConfigurePermissions&documentStoreId=<%= documentStore.getId() %>&return=DocumentStoreCenter"><dhv:label name="documents.permissions.configure_permissions">Configure Permissions</dhv:label></a>
<br>
</dhv:evaluate>
<br>
<dhv:label name="documents.permissions.introduction">To adjust document store permissions choose Configure Permissions.  Each document store has a set
of permissions that indicate which user roles are allowed to perform a document store
function.  By default, the Document Store Owner role is capable of all document store functions,
while a Guest has very limited functionality.<br>
<br>
There are four major access levels:<br>
</dhv:label>
<br>
<dhv:label name="documents.permissions.manager">
<b>Document Store Manager:</b> Usually the creators(s) of the document stores and having the most control of the data<br>
</dhv:label>
<br>
<dhv:label name="documents.permissions.contributor">
<b>Contributor level (3-2-1):</b> Those that will be responsible for adding or updating related document store information<br>
</dhv:label>
<br>
<dhv:label name="documents.permissions.guest">
<b>Guest:</b> Typically a read-only access level... those that are not directly part of the document store but have access to review some of the document store information<br>
</dhv:label>
