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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="ProductList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="CompleteList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="ProductCatalogListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_category_product_list_menu.jsp" %> 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCatalogs.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script>existingIds = new Array();</script>
<%
  Iterator k = CompleteList.iterator();
  int count = -1;
  while (k.hasNext()) {
    count++;
    ProductCatalog thisProduct = (ProductCatalog) k.next();
%>
   <script>
    existingIds[<%= count %>] = "<%= thisProduct.getId() %>";
   </script>
<%
  }
%>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
      <a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
			<dhv:label name="product.products">Products</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_category_header_include.jsp" %>
<% String param1 = "categoryId=" + ProductCategory.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcategories" selected="products" object="ProductCategory" param="<%= param1 + "|" + param2 %>">
  <a href="javascript:popProductCatalogsListMultipleCategory(existingIds, 'categoryId=<%= ProductCategory.getId() %>&moduleId=<%= PermissionCategory.getId() %>');"><dhv:label name="product.addProduct">Add Product</dhv:label></a>
  <center><%= ProductCatalogListInfo.getAlphabeticalPageLinks() %></center>
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ProductCatalogListInfo"/>
  <% int columnCount = 0; %>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8" <% ++columnCount; %>>
        &nbsp;
      </th>
      <th <% ++columnCount; %>>
        <strong><a href="ProductCategoryProducts.do?command=List&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>&column=pctlg.product_name"><dhv:label name="products.productName">Product Name</dhv:label></a></strong>
        <%= ProductCatalogListInfo.getSortIcon("pctlg.product_name") %>
      </th>
      <th <% ++columnCount; %>>
        <strong><dhv:label name="quotes.sku">SKU</dhv:label></strong>
      </th>
      <th nowrap <% ++columnCount; %>>
        <strong><dhv:label name="product.parentName">Parent Name</dhv:label></strong>
      </th>
      <th nowrap <% ++columnCount; %>>
        <strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong>
      </th>
      <th nowrap <% ++columnCount; %>>
        <strong><dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label></strong>
      </th>
    </tr>
  <%
    Iterator j = ProductList.iterator();
    if (j.hasNext()) {
      int rowid = 0;
      int i = 0;
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        ProductCatalog thisCatalog = (ProductCatalog) j.next();
  %>
      <tr class="row<%= rowid %>">
        <td width="8" valign="center" nowrap>
          <% int status = -1; %>
          <% status = thisCatalog.getEnabled() ? 1 : 0; %>
          <a href="javascript:displayMenu('select<%= i %>', 'menuCatalog', '<%= thisCatalog.getId() %>', '<%= status %>');"
          onMouseOver="over(0, <%= i %>)" onMouseOut="out(0, <%= i %>); hideMenu('menuCatalog');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
        </td>
        <td width="100%">
          <a href="ProductCategoryProducts.do?command=Details&catalogId=<%=thisCatalog.getId()%>&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><%= toHtml(thisCatalog.getName()) %></a>
        </td>
        <td nowrap>
          <%= toHtml(thisCatalog.getSku()) %>
        </td>
        <td>
          <%= toHtml(thisCatalog.getParentName()) %>
        </td>
        <td nowrap>
          <zeroio:tz timestamp="<%= thisCatalog.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
        </td>
        <td nowrap>
          <zeroio:tz timestamp="<%= thisCatalog.getExpirationDate() %>" dateOnly="true" default="&nbsp;"/>
        </td>
      </tr>
  <%
      }
    } else {
  %>
    <tr class="containerBody">
      <td colspan="<%= columnCount %>">
        <dhv:label name="product.noProductsMappedToCategory">No Products have been mapped with this category.</dhv:label>
      </td>
    </tr>
  <%
  }
  %>
  </table>
  <br />
  <dhv:pagedListControl object="ProductCatalogListInfo"/>
</dhv:container>