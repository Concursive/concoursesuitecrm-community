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
<jsp:useBean id="ProductList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request" />
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="ProductCatalogListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<%@ include file="../initPage.jsp" %>
<form name="productListView" method="post" action="ProductCategoryProducts.do?command=ProductList&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
      <a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
      <a href="ProductCategoryProducts.do?command=List&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCategoryProducts.do?command=Add&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.productChoice">Product Choice</dhv:label></a> >
      <dhv:label name="product.productList">Product List</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_category_header_include.jsp" %>
<% String param1 = "categoryId=" + ProductCategory.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcategories" selected="products" object="ProductCategory" param='<%= param1 + "|" + param2 %>'>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ProductCatalogListInfo" showHiddenParams="true" enableJScript="true" form="productListView"/>
  <% int columnCount = 0; %>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8" <% ++columnCount; %>>
        <dhv:label name="accounts.accounts_add.select">Select</dhv:label>
      </th>
      <th nowrap width="100%" <% ++columnCount; %>>
        <dhv:label name="products.productName">Product Name</dhv:label>
      </th>
      <th nowrap <% ++columnCount; %>>
        <dhv:label name="quotes.sku">SKU</dhv:label>
      </th>
      <th nowrap <% ++columnCount; %>>
        <dhv:label name="documents.details.startDate">Start Date</dhv:label>
      </th>
      <th nowrap <% ++columnCount; %>>
        <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
      </th>
      <th nowrap <% ++columnCount; %>>
        <dhv:label name="product.enabled">Enabled</dhv:label>
      </th>
    </tr>
    <%
      Iterator j = ProductList.iterator();
      if (j.hasNext()) {
        int rowid = 0;
        int i = 0;
        int count = 0;
        while (j.hasNext()) {
          i++;
          count++;
          rowid = (rowid != 1 ? 1 : 2);
          ProductCatalog thisProduct = (ProductCatalog) j.next();
    %>
        <tr class="row<%= rowid + ((selectedElements.get(new Integer(thisProduct.getId())) != null) ? "hl":"") %>">
          <td width="8" align="center">
            <input type="checkbox" name="checkelement<%= count %>" value="<%= thisProduct.getId() %>" <%= ((selectedElements.get(new Integer(thisProduct.getId()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');" />
          </td>
          <input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisProduct.getId() %>">
          <td nowrap><%= toHtml(thisProduct.getName()) %></td>
          <td nowrap><%= toHtml(thisProduct.getSku()) %></td>
          <td nowrap>
            <zeroio:tz timestamp="<%= thisProduct.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
          </td>
          <td nowrap>
            <zeroio:tz timestamp="<%= thisProduct.getExpirationDate() %>" dateOnly="true" default="&nbsp;"/>
          </td>
          <td>
            <% if(thisProduct.getEnabled()) {%>
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
      <tr class="containerBody">
        <td colspan="<%= columnCount %>">
          <dhv:label name="product.noProductOptionsFound">No Product Options found.</dhv:label><br />
        </td>
      </tr>
    <%
    }
    %>
    </table>
    <br />
    <input type="hidden" name="finalsubmit" value="false">
    <input type="hidden" name="rowcount" value="0">
    <input type="hidden" name="categoryId" value="<%= ProductCategory.getId() %>">
    <input type="submit" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:document.productListView.finalsubmit.value='true';document.productListView.submit();">
    <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCategoryProducts.do?command=List&moduleId=<%= PermissionCategory.getId()%>&categoryId=<%= ProductCategory.getId() %>'">
    [<a href="javascript:SetChecked(1,'checkelement','productListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
    [<a href="javascript:SetChecked(0,'checkelement','productListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
</dhv:container>
</form>
