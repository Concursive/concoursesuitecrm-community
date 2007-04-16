<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.website.base.PageRoleMap"%>
<jsp:useBean id="pageRoleMapList" class="org.aspcfs.modules.website.base.PageRoleMapList" scope="request"/>
<jsp:useBean id="pageId" class="java.lang.String" scope="request"/>
<jsp:useBean id="roleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="roleListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  function reopen() {
    window.location.href='PageRoles.do?command=List&moduleId=<%=permissionCategory.getId()%>&pageId=<%=pageId%>';
  }
</script>
<!-- Sub Trails -->
<table class="trails" cellspacing="0"><tr><td>
  <dhv:label name="">Granting Permissions</dhv:label>
</td></tr></table>
<!-- End Sub Trails -->
<%-- Begin the container contents --%>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="siteListInfo"/>
<form name="Dashboard"
	action="PageRoles.do?command=Save&moduleId=<%=permissionCategory.getId()%>&pageId=<%=pageId%>"
	method="post">

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th width="50%"><strong><dhv:label name="">Role</dhv:label></strong></th>
    <th width="50%"><strong>Description</strong></th>
  </tr>
<%
  int rowid = 0;
  Iterator iter = roleList.iterator();
  while (iter.hasNext()) {
    Role role = (Role) iter.next();
    rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
  
    <td valign="center" nowrap>
		<input type="checkbox" name="role" value="<%=role.getId()%>" <%=pageRoleMapList.getPermissionForRole(role.getId())?"checked":""%>/>
    </td>
    <td valign="top" width="50%">
    	<%=toHtml(role.getRole())%>
    </td>
    <td valign="top" width="50%"><%=toHtml(role.getDescription())%></td>
  </tr>
<%}%>
</table>
<br>
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save" />
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"/>
</form>
<dhv:pagedListControl object="siteListInfo"/>
