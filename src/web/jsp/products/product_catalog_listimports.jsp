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
  - Version: $Id: product_catalog_listimports.jsp 11310 2005-04-13 20:05:00 +0000 (Ср, 13 апр 2005) mrajkowski $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.base.Import,com.zeroio.iteam.base.FileItem" %>
<jsp:useBean id="ImportList" class="org.aspcfs.modules.base.ImportList" scope="request"/>
<jsp:useBean id="ProductCatalogImportListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"></jsp:useBean>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_catalog_listimports_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0"><tr>
	<td>
		<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
		<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
		<a href="Admin.do?command=ConfigDetails&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.productCatalog">Product Catalog</dhv:label></a> >
		<dhv:label name="products.viewImports">View Imports</dhv:label>
	</td>
</tr></table>
<%-- End Trails --%>
<dhv:permission name="product-catalog-product-imports-add">
  <a href="ProductCatalogImports.do?command=New&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.newImport">New Import</dhv:label></a><br>
</dhv:permission><br>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= ProductCatalogImportListInfo.getOptionValue("all") %>><dhv:label name="products.allImports">All Imports</dhv:label></option>
        <option <%= ProductCatalogImportListInfo.getOptionValue("my") %>><dhv:label name="products.myImports">My Imports</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ProductCatalogImportListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th rowspan="2" valign="middle">
      &nbsp;
    </th>
    <th rowspan="2" valign="middle" width="100%">
      <strong><a href="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>&column=m.name"><dhv:label name="product.name">Name</dhv:label></a></strong>
      <%= ProductCatalogImportListInfo.getSortIcon("m.name") %>
    </th>
    <th rowspan="2" valign="middle">
      <strong><dhv:label name="product.status">Status</dhv:label></strong>
    </th>
    <th colspan="3" style="text-align:center !important">
      <strong><dhv:label name="products.results">Results</dhv:label></strong>
    </th>
    <th rowspan="2" valign="middle" nowrap>
      <strong><a href="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>&column=m.entered"><dhv:label name="products.entered">Entered</dhv:label></a></strong>
      <%= ProductCatalogImportListInfo.getSortIcon("m.entered") %>
    </th>
    <th rowspan="2" valign="middle">
      <strong><dhv:label name="products.modified">Modified</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <th>
      <strong><dhv:label name="products.product_catalog_listimports.total">Total</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="products.product_catalog_listimports.success">Success</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="products.product_catalog_listimports.failed">Failed</dhv:label></strong>
    </th>
  </tr>
<%
  Iterator j = ImportList.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  int i =0;
  while (j.hasNext()) {
      i++;
      rowid = (rowid != 1 ? 1 : 2);
      Import thisImport = (Import) j.next();
%>
  <tr class="row<%= rowid %>">
    <td nowrap>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= i %>','menuImport','<%= thisImport.getId() %>', '<%= thisImport.getStatusId() == Import.RUNNING ? "1" : "0" %>','<%= thisImport.getStatusId() == Import.UNPROCESSED ? "1" : "0"%>','<%= thisImport.canDelete()? "1" : "0"%>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" nowrap>
      <a href="ProductCatalogImports.do?command=Details&moduleId=<%=permissionCategory.getId()%>&importId=<%= thisImport.getId() %>"><%= toHtmlValue(thisImport.getName()) %></a>
      <dhv:evaluate if="<%= !thisImport.canProcess() && thisImport.getFile().hasVersion(Import.ERROR_FILE_VERSION) %>">
      &nbsp;<%= thisImport.getFile().getImageTag("-23") %><br />[<a href="javascript:window.location.href='ProductCatalogImports.do?command=Download&importId=<%= thisImport.getId() %>&fid=<%= thisImport.getFile().getId() %>&ver=<%= Import.ERROR_FILE_VERSION %>';"><dhv:label name="products.downloadErrorFile">Download Error File</dhv:label></a>]
      </dhv:evaluate>
    </td>
    <td>
      <%= toString(thisImport.getStatusString()) %>
    </td>
    <td nowrap align="center">
      <%= thisImport.getTotalImportedRecords() + thisImport.getTotalFailedRecords() %>
    </td>
    <td nowrap align="center">
      <%= thisImport.getTotalImportedRecords() %>
    </td>
    <td nowrap align="center">
      <%= thisImport.getTotalFailedRecords() %>
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisImport.getEntered() %>" />
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisImport.getModified() %>" />
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="8" valign="center">
          <dhv:label name="products.product_catalog_listimports.noImportsFound.text">No imports found.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="ProductCatalogImportListInfo" tdClass="row1"/>

