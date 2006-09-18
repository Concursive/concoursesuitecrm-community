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
  - Version: $Id: product_catalog_detailsimport.jsp 13376 2005-11-22 21:02:12 +0000 (Вт, 22 ноя 2005) kbhoopal $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,org.aspcfs.modules.base.Import" %>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.products.base.ProductCatalogImport" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript">
  function confirmApprove() {
    if(confirmAction(label("are.you.sure", "Are you sure?"))){
      window.location.href='ProductCatalogImports.do?command=Approve&importId=<%= ImportDetails.getId() %>&moduleId=<%=permissionCategory.getId()%>';
    }
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
    <a href="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.viewImports">View Imports</dhv:label></a> >
    <dhv:label name="products.importDetails">Import Details</dhv:label>
  </td>
</tr>
</table>
<%-- Import Details --%>
<% if(ImportDetails.canProcess()){ %>
<dhv:permission name="product-catalog-product-imports-add">
<input type="button" value="<dhv:label name="global.button.ProcessNow">Process Now</dhv:label>" onClick="javascript:window.location.href='ProductCatalogImports.do?command=InitValidate&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>'">
</dhv:permission>
<% } %>
<dhv:evaluate if="<%= ImportDetails.canApprove() %>">
<dhv:permission name="product-catalog-product-imports-edit">
<input type="button" value="<dhv:label name="global.button.Approve">Approve</dhv:label>" onClick="javascript:confirmApprove();">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%= ImportDetails.canDelete() %>">
<dhv:permission name="product-catalog-product-imports-delete">
<input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('ProductCatalogImports.do?command=ConfirmDelete&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>','Delete_message','320','200','yes','no');">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%= ImportDetails.hasBeenProcessed() %>">
<dhv:permission name="product-catalog-product-imports-view">
<input type="button" value="<dhv:label name="global.button.ViewResults">View Results</dhv:label>" onClick="javascript:window.location.href='ProductCatalogImports.do?command=ViewResults&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>';">
</dhv:permission>
</dhv:evaluate>
<% boolean breakAdded = false; %>
<dhv:evaluate if="<%= ImportDetails.canDelete() && !breakAdded %>">
<dhv:permission name="product-catalog-product-imports-delete">
<br><br>
<% breakAdded = true; %>
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%= (ImportDetails.canProcess() || ImportDetails.canApprove()) && !breakAdded %>">
<dhv:permission name="product-catalog-product-imports-edit">
<br><br>
<% breakAdded = true; %>
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%= ImportDetails.hasBeenProcessed() && !breakAdded %>">
<dhv:permission name="product-catalog-product-imports-view">
<br><br>
<% breakAdded = true; %>
</dhv:permission>
</dhv:evaluate>
<dhv:formMessage showSpace="false" />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.details">Details</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="product.name">Name</dhv:label>
  </td>
  <td>
    <%= toString(ImportDetails.getName()) %>
  </td>
  </tr>
  <tr class="containerBody">
  <td valign="top" class="formLabel" nowrap>
    <dhv:label name="product.description">Description</dhv:label>
  </td>
  <td>
    <%= toHtml(ImportDetails.getDescription()) %>
  </td>
  </tr>
  <% if(ImportDetails.getZipFileItem()!=null){%>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.images">Images</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getZipFileItem().getClientFilename() %>&nbsp;&nbsp;[ <a href="javascript:window.location.href='ProductCatalogImports.do?command=Download&importId=<%= ImportDetails.getId() %>&fid=<%= ImportDetails.getZipFileItem().getId() %>'"><dhv:label name="products.downloadFile">Download Images</dhv:label></a> ]
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.imageFileSize">Images File Size</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getZipFileItem().getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
  </td>
  </tr>    
  <%}%>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.file">File</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getFile().getVersion(Import.IMPORT_FILE_VERSION).getClientFilename() %>&nbsp;&nbsp;[ <a href="javascript:window.location.href='ProductCatalogImports.do?command=Download&importId=<%= ImportDetails.getId() %>&fid=<%= ImportDetails.getFile().getId() %>&ver=<%= Import.IMPORT_FILE_VERSION %>'"><dhv:label name="products.downloadFile">Download File</dhv:label></a> ]
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.fileSize">File Size</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getFile().getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
  </td>
  </tr>

  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="product.status">Status</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getStatusString() %> &nbsp;
  </td>
  </tr>
  <dhv:evaluate if="<%= !ImportDetails.canProcess() %>">
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.importedRecords">Imported Records</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getTotalImportedRecords() %> &nbsp;
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.failedRecords">Failed Records</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getTotalFailedRecords() %> &nbsp;
    <dhv:evaluate if="<%= ImportDetails.getFile().hasVersion(Import.ERROR_FILE_VERSION) %>">
      &nbsp;<%= ImportDetails.getFile().getImageTag("-23") %>[ <a href="javascript:window.location.href='ProductCatalogImports.do?command=Download&importId=<%= ImportDetails.getId() %>&fid=<%= ImportDetails.getFile().getId() %>&ver=<%= Import.ERROR_FILE_VERSION %>';">  <dhv:label name="products.downloadErrorFile">Download Error File</dhv:label></a> ]
    </dhv:evaluate>
  </td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.entered">Entered</dhv:label>
  </td>
  <td>
    <dhv:username id="<%= ImportDetails.getEnteredBy() %>"/>
    <zeroio:tz timestamp="<%= ImportDetails.getEntered()  %>"/>
  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="products.modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= ImportDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ImportDetails.getModified()  %>" />
    </td>
  </tr>
</table><br>
<% if(ImportDetails.canProcess()){ %>
<dhv:permission name="product-catalog-product-imports-add">
<input type="button" value="<dhv:label name="global.button.ProcessNow">Process Now</dhv:label>" onClick="javascript:window.location.href='ProductCatalogImports.do?command=InitValidate&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>'">
</dhv:permission>
<% } %>
<dhv:evaluate if="<%= ImportDetails.canApprove() %>">
<dhv:permission name="product-catalog-product-imports-edit">
<input type="button" value="<dhv:label name="global.button.Approve">Approve</dhv:label>" onClick="javascript:confirmApprove();">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%= ImportDetails.canDelete() %>">
<dhv:permission name="product-catalog-product-imports-delete">
<input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('ProductCatalogImports.do?command=ConfirmDelete&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>','Delete_message','320','200','yes','no');">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%= ImportDetails.hasBeenProcessed() %>">
<dhv:permission name="product-catalog-product-imports-view">
<input type="button" value="<dhv:label name="global.button.ViewResults">View Results</dhv:label>" onClick="javascript:window.location.href='ProductCatalogImports.do?command=ViewResults&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>';">
</dhv:permission>
</dhv:evaluate>
