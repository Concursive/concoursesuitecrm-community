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
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProductCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="SearchProductCatalogListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
<script language="Javascript">
  function clearForm() {
    document.forms['searchProductCatalog'].searchName.value="";
    document.forms['searchProductCatalog'].searchAbbreviation.value="";
    document.forms['searchProductCatalog'].searchSku.value="";
    document.forms['searchProductCatalog'].listView.options.selectedIndex = 0;
    document.forms['searchProductCatalog'].listFilter1.options.selectedIndex = 0;
    document.forms['searchProductCatalog'].searchName.focus();
    document.forms['searchProductCatalog'].searchcodeCategoryId.value = "-1";
    changeDivContent('changecategory', label('label.all','All'));
  }
  
  function clearProductCategory() {
    document.forms['searchProductCatalog'].searchcodeCategoryId.value = "-1";
    changeDivContent('changecategory', label('label.all','All'));
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<dhv:label name="product.searchProducts">Search Products</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<body onLoad="javascript:document.searchProductCatalog.searchName.focus();">
<form name="searchProductCatalog" action="ProductCatalogs.do?command=Search" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.searchProductCatalog">Search Product Catalog</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="products.productName">Product Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchName" value="<%= SearchProductCatalogListInfo.getSearchOptionValue("searchName") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchAbbreviation" value="<%= SearchProductCatalogListInfo.getSearchOptionValue("searchAbbreviation") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="quotes.sku">SKU</dhv:label>
    </td>
    <td>
      <input type="text" size="15" name="searchSku" value="<%= SearchProductCatalogListInfo.getSearchOptionValue("searchSku") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="product.productType">Product Type</dhv:label>
    </td>
    <td>
      <%= TypeSelect.getHtmlSelect("listFilter1", SearchProductCatalogListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="products.productCategory">Product Category</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changecategory">
              <% if("".equals(SearchProductCatalogListInfo.getSearchOptionValue("searchcodeCategoryId")) || "-1".equals(SearchProductCatalogListInfo.getSearchOptionValue("searchcodeCategoryId"))) { %>
                  <dhv:label name="quotes.all">All</dhv:label>
              <% } else { %>
                <%= toHtmlValue(ProductCategory.getName()) %>
              <% } %>
            </div>
          </td>
          <td valign="top" width="100%" nowrap>
            <input type="hidden" name="searchcodeCategoryId" id="searchcodeCategoryId" value="<%= SearchProductCatalogListInfo.getSearchOptionValue("searchcodeCategoryId") %>">
            &nbsp;[<a href="javascript:popProductCategoriesListSingle('searchcodeCategoryId', 'changecategory', 'listType=single&filters=top&setParentList=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
						&nbsp;[<a href="javascript:clearProductCategory();"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
    </td>
    <td align="left" valign="bottom">
      <select size="1" name="listView">
        <option <%= SearchProductCatalogListInfo.getOptionValue("all") %>><dhv:label name="product.allProducts">All Products</dhv:label></option>
        <option <%= SearchProductCatalogListInfo.getOptionValue("enabled") %>><dhv:label name="product.activeProducts">Active Products</dhv:label></option>
        <option <%= SearchProductCatalogListInfo.getOptionValue("disabled") %>><dhv:label name="product.inactiveProducts">Inactive Products</dhv:label></option>
      </select>
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="moduleId" value="<%= PermissionCategory.getId() %>" />
</form>
</body>
