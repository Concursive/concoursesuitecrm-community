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
  - Version: $Id: product_catalog_confirm_importupload.jsp 11310 2005-04-13 20:05:00 +0000 (Ср, 13 апр 2005) mrajkowski $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.products.base.ProductCatalogImport" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
    <a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
    <a href="Admin.do?command=ConfigDetails&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.productCatalog">Product Catalog</dhv:label></a> >
    <a href="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.import">Import</dhv:label></a> >
    <a href="ProductCatalogImports.do?command=New&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.newImport">New Import</dhv:label></a> >
    <dhv:label name="products.uploadComplete">Upload Complete</dhv:label>
  </td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="products.importNotProcessed.text">Your file to import has been received, but has not been processed.</dhv:label><br />
      <dhv:label name="products.toBeginProcessingUseProccessNow.text">To begin processing, use the &quot;Process Now&quot; button.  However, since processing requires a few minutes of configuration, you can choose to process the file later by using the &quot;Process Later&quot; button.</dhv:label>
    </td>
  </tr>
</table>
<%-- Import Details --%>
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
    <%= ImportDetails.getZipFileItem().getClientFilename() %>&nbsp;&nbsp;[ <a href="javascript:window.location.href='ProductCatalogImports.do?command=Download&importId=<%= ImportDetails.getZipFileItem() %>&fid=<%= ImportDetails.getZipFileItem().getId() %>'"><dhv:label name="products.downloadFile">Download Images</dhv:label></a> ]
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.fileSize">Images File Size</dhv:label>
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
    <%= ImportDetails.getFile().getClientFilename() %>&nbsp;&nbsp;[ <a href="javascript:window.location.href='ProductCatalogImports.do?command=Download&importId=<%= ImportDetails.getId() %>&fid=<%= ImportDetails.getFile().getId() %>'"><dhv:label name="products.downloadFile">Download File</dhv:label></a> ]
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
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.entered">Entered</dhv:label>
  </td>
  <td>
    <dhv:username id="<%= ImportDetails.getEnteredBy() %>"/>
    <zeroio:tz timestamp="<%= ImportDetails.getEntered()  %>" />
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
<input type="button" value="<dhv:label name="global.button.ProcessNow">Process Now</dhv:label>" onClick="javascript:window.location.href='ProductCatalogImports.do?command=InitValidate&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>';">&nbsp;
<input type="button" value="<dhv:label name="global.button.ProcessLater">Process Later</dhv:label>" onClick="javascript:window.location.href='ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>';">

