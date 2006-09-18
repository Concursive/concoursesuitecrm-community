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
<jsp:useBean id="CatalogList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="SearchProductCatalogListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_catalog_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>

<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCatalogs.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchProducts">Search Products</dhv:label></a> >
			<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<a href="ProductCatalogs.do?command=Add&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.addProduct">Add Product</dhv:label></a>
<center><%= SearchProductCatalogListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchProductCatalogListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8" <% ++columnCount; %>>
      &nbsp;
    </th>
    <th nowrap width="100%" <% ++columnCount; %>>
      <strong><a href="ProductCatalogs.do?command=Search&moduleId=<%= PermissionCategory.getId() %>&column=pctlg.product_name"><dhv:label name="products.productName">Product Name</dhv:label></a></strong>
      <%= SearchProductCatalogListInfo.getSortIcon("pctlg.product_name") %>
    </th>
		<%--
    <th nowrap <% ++columnCount; %>>
      <strong>Category Name</strong>
    </th>
    --%>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="quotes.sku">SKU</dhv:label></strong>
    </th>
    <th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label></strong>
    </th>
    	<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="product.enabled">Enabled</dhv:label></strong>
    </th>
	</tr>
<% 
	Iterator j = CatalogList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int i = 0;
		while (j.hasNext()) {
			i++;
			rowid = (rowid != 1 ? 1 : 2);
			ProductCatalog thisCatalog = (ProductCatalog) j.next();
%>
		<tr>
			<td width="8" valign="center" nowrap class="row<%= rowid %>">
				<% int status = -1; %>
				<% status = thisCatalog.getEnabled() ? 1 : 0; %>
				<a href="javascript:displayMenu('select<%= i %>', 'menuCatalog', '<%= thisCatalog.getId() %>', '<%= status %>');"
				onMouseOver="over(0, <%= i %>)" onMouseOut="out(0, <%= i %>); hideMenu('menuCatalog');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
			</td>
			<td class="row<%= rowid %>">
    	  <a href="ProductCatalogs.do?command=Details&catalogId=<%=thisCatalog.getId()%>&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(thisCatalog.getName()) %></a>
			</td>
      <%--
			<td class="row<%= rowid %>" nowrap>
				<%= toHtml(thisCatalog.getCategoryName()) %>
			</td>
      --%>
			<td class="row<%= rowid %>" nowrap>
				<%= toHtml(thisCatalog.getSku()) %>
			</td><td class="row<%= rowid %>" align="center">
				<zeroio:tz timestamp="<%= thisCatalog.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
			</td>
			<td class="row<%= rowid %>" align="center">
				<zeroio:tz timestamp="<%= thisCatalog.getExpirationDate() %>" dateOnly="true" default="&nbsp;"/>
			</td>
      <td class="row<%= rowid %>" align="center">
<% if(thisCatalog.getEnabled()) {%>
  <dhv:label name="account.yes">Yes</dhv:label>
<%} else {%>
  <dhv:label name="account.no">No</dhv:label>
<%}%>
			</td>
    </tr>
<%
		}
	} else {
%>
	<tr>
		<td colspan="<%= columnCount %>">
			<dhv:label name="product.noProductsFound">No Products found with the specified search parameters.</dhv:label><br />
			[ <a href="ProductCatalogs.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a> ]
		</td>
	</tr>
<%
}
%>
</table>
<br />
<dhv:pagedListControl object="SearchProductCatalogListInfo" tdClass="row1"/>
