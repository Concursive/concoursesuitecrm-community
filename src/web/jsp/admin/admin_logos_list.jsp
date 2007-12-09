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
  - Version: $Id: 
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*, com.zeroio.iteam.base.*, com.zeroio.webutils.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="admin_logos_list_menu.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> > 
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<dhv:label name="admin.logos">Logos</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<dhv:permission name="admin-sysconfig-logos-add"><a href="AdminLogos.do?command=AddLogo&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.addNewLogo">Add new Logo</dhv:label></a></dhv:permission>
<br><br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" align="center" nowrap>&nbsp;</th>
    <th align="center"><strong><dhv:label name="admin.default">Default</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.details.archived">Archived</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="admin.thumbnail">Thumbnail</dhv:label></strong></th>
  </tr>
<dhv:evaluate if="<%= FileItemList.size() == 0 %>">
  <tr class="row2">
    <td colspan="5"><dhv:label name="admin.noLogosToDisplay.text">No logos to display.</dhv:label></td>
  </tr>
</dhv:evaluate>
<%
	int rowid = 0;
  if ( FileItemList.size() > 0) {
    Iterator iterator = FileItemList.iterator();
    while (iterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem) iterator.next();
%>    
  <tr class="row<%= rowid %>">
    <td align="center" valign="top" nowrap>
			<% int status = -1;%>
			<% status = thisFile.getEnabled() ? 1 : 0; %>
      <a href="javascript:displayMenu('selectfi<%= thisFile.getId() %>', 'menuFile', '<%= thisFile.getId() %>','<%= status %>')"
         onMouseOver="over(0, 'fi<%= thisFile.getId() %>')"
         onmouseout="out(0, 'fi<%= thisFile.getId() %>'); hideMenu('menuFile');">
         <img src="images/select.gif" name="selectfi<%= thisFile.getId() %>" id="selectfi<%= thisFile.getId() %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="top" align="center"><dhv:evaluate if="<%= thisFile.getDefaultFile() %>">*</dhv:evaluate>&nbsp;</td>
      <td valign="top" align="center"><dhv:evaluate if="<%= !thisFile.getEnabled() %>">*</dhv:evaluate>&nbsp;</td>
      <td width="50%" valign="top"><%= toHtml(thisFile.getSubject()) %></td>
      <td width="50%" valign="top" align="center" nowrap>
		    <dhv:fileItemImage name="imagePreview" path="quotes" id="<%= thisFile.getId() %>" version="1" thumbnail="true"/>
		  </td>
  </tr>
<% }} %>
</table>
