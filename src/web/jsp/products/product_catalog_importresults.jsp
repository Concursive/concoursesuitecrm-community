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
  - Version: $Id: product_catalog_importresults.jsp 2006-05-25 $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.ProductCatalog,org.aspcfs.modules.products.base.ProductCategory" %>
<jsp:useBean id="ImportResults" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.base.Import" scope="request"/>
<jsp:useBean id="ProductCatalogImportResultsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_catalog_importresults_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
  <a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
        <a href="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.viewImports">View Imports</dhv:label></a> >
    <a href="ProductCatalogImports.do?command=Details&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>"><dhv:label name="product.ImportDetails">Import Details</dhv:label></a> >
    <dhv:label name="global.button.ViewResults">View Results</dhv:label>
  </td>
</tr>
</table>
<%-- End Trails --%>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="ProductCatalogImportResultsInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ProductCatalogImportResultsInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      &nbsp;
    </th>
    <th nowrap>
      <strong><a href="ProductCatalogImports.do?command=ViewResults&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>&column=pctlg.product_name"><dhv:label name="products.name">Name</dhv:label></a></strong>
      <%= ProductCatalogImportResultsInfo.getSortIcon("pctlg.product_name") %>
    </th>
    <th nowrap>
      <strong><a href="ProductCatalogImports.do?command=ViewResults&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>&column=pctlg.sku"><dhv:label name="product.product_catalog_detailsimport.productCode">Product Code</dhv:label></a></strong>
      <%= ProductCatalogImportResultsInfo.getSortIcon("pctlg.sku") %>
    </th>
    <th>
      <strong><dhv:label name="product.shortDescription.colon">Short description</dhv:label></strong>
    </th>
     <th>
      <strong><dhv:label name="product.Category.colon">Category</dhv:label></strong>
    </th>
    
  </tr>
<%    
	Iterator i = ImportResults.iterator();
	if (i.hasNext()) {
	int rowid = 0;
  int count  =0;
		while (i.hasNext()) {
      count++;
      rowid = (rowid != 1 ? 1 : 2);
      ProductCatalog thisProduct = (ProductCatalog)i.next();
%>    
      <tr>
        <td width="8" class="row<%= rowid %>" nowrap>
         <%-- check if user has edit or delete based on the type of product --%>
        <%
          int hasDeletePermission = 0;
          if(!thisProduct.isApproved()){
          %>
            <dhv:permission name="product-catalog-product-delete">
              <% hasDeletePermission = 1; %>
            </dhv:permission>
        <% } %>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= count %>','menuProduct','<%= thisProduct.getId() %>','<%= hasDeletePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>)"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td class="row<%= rowid %>" <%= "".equals(toString(thisProduct.getName())) ? "width=\"10\"" : ""  %> nowrap>
          <% if(!"".equals(toString(thisProduct.getName()))){ %>
          <a href="ProductCatalogImports.do?command=ProductDetails&moduleId=<%=permissionCategory.getId()%>&productId=<%= thisProduct.getId() %>"><%= toHtml(thisProduct.getName()) %></a>
          <% }else{ %>
            &nbsp;
          <%}%>
        </td>
        <td class="row<%= rowid %>">
          <% if(!"".equals(toString(thisProduct.getName()))){ %>
            <%= toHtml(thisProduct.getSku()) %>
          <%}else{%>
            <a href="ProductCatalogImports.do?command=ProductDetails&moduleId=<%=permissionCategory.getId()%>&productId=<%= thisProduct.getId() %>"><%= toHtml(thisProduct.getSku()) %></a>
          <%}%>
        </td>
         <td class="row<%= rowid %>">
          <% if(!"".equals(toString(thisProduct.getShortDescription()))){ %>
            <%= toHtml(thisProduct.getShortDescription()) %>
          <%}else{%>
           &nbsp;
          <%}%>
        </td>
        <td class="row<%= rowid %>">
          <% if(!"".equals(toString(thisProduct.getCategoryName()))){ %>
            <%= toHtml(thisProduct.getCategoryName()) %>
          <%}else{%>
           &nbsp;
          <%}%>
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="containerBody" colspan="5">
      <dhv:label name="products.product_catalog_detailsimport.NoContactsFound">No products found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ProductCatalogImportResultsInfo" tdClass="row1"/>
