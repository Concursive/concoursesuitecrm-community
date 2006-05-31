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
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="productListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="products_categories_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<form name="productCategories" action="ProductCatalogEditor.do?command=List&auto-populate=true&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= parentCategory.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
			<dhv:label name="product.editor">Editor</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="abovetab">
    <td>
      <% String link = "ProductCatalogEditor.do?command=List&moduleId=" + permissionCategory.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" />
    </td>
  </tr>
</table>
<br />
<dhv:permission name="admin-sysconfig-products-add">
  <table border="0" cellpadding="1" cellspacing="0" width="100%">
    <tr>
      <td>
        <img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle">
        <a href="ProductCategories.do?command=Add&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= parentCategory.getId() %>"><dhv:label name="product.newCategory">New Category</dhv:label></a>
        &nbsp;|&nbsp;
        <a href="ProductCatalogs.do?command=Add&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= parentCategory.getId() %>"><dhv:label name="product.newProduct">New Product</dhv:label></a>
       </td>
    </tr>
  </table>
  <br />
</dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="productListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" align="center" nowrap>&nbsp;</th>
    <th width="100%"><strong><dhv:label name="product.name">Name</dhv:label></strong></th>
    <th><strong><dhv:label name="products.SKU">SKU</dhv:label></strong></th>
    <th align="right"><strong><dhv:label name="product.price">Price</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="product.startDate">Start Date</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="product.expirationDate">Expiration Date</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="product.enabled">Enabled</dhv:label></strong></th>
  </tr>
  <dhv:evaluate if="<%= categoryList.size() == 0 && productList.size() == 0 %>">
    <tr class="row2">
      <td colspan="7"><dhv:label name="product.noItemsMsg">No items to display.</dhv:label></td>
    </tr>
  </dhv:evaluate>
<%
  int rowid = 0;
  int count = 0;
  Iterator i = categoryList.iterator();
  while (i.hasNext()) {
    ProductCategory thisCategory = (ProductCategory) i.next();
    rowid = (rowid != 1 ? 1 : 2);
    count++;
%>
  <tr class="row<%= rowid %>">
    <td align="center" nowrap>
      <a href="javascript:displayMenu('select<%= count %>', 'menuCatalog', -1, <%= thisCategory.getId() %>, '<%= thisCategory.getParentId() %>', 'CATEGORY','false');"
        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuCatalog');">
        <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <img src="images/stock_folder-23.gif" border="0" align="absmiddle"/>
      <a href="ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= thisCategory.getId() %>"><%= toHtml(thisCategory.getName()) %></a>
    </td>
    <td nowrap>
       -- 
    </td>
    <td align="right" nowrap>
       -- 
    </td>
    <td align="center" nowrap>
       -- 
    </td>
    <td align="center" nowrap>
       -- 
    </td>
    <td align="center" nowrap>
      -- 
    </td>
  </tr>
  <%
    }
  %>
  <%
    Iterator j = productList.iterator();
    while (j.hasNext()) {
      ProductCatalog thisProduct = (ProductCatalog) j.next();
      rowid = (rowid != 1 ? 1 : 2);
      count++;
  %>
  <tr class="row<%= rowid %>">
    <td align="center" nowrap>
      <a href="javascript:displayMenu('select<%= count %>', 'menuCatalog', <%= thisProduct.getId() %>,<%= parentCategory.getId() %>, -1, 'PRODUCT','<%= thisProduct.isTrashed() %>');"
        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuCatalog');">
        <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <a href="ProductCatalogs.do?command=Details&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= parentCategory.getId() %>&productId=<%= thisProduct.getId() %>"><%= toHtml(thisProduct.getName()) %></a>
    </td>
    <td nowrap>
      <%= toHtml(thisProduct.getSku()) %>
    </td>
    <td align="right" nowrap>
      <dhv:evaluate if="<%= thisProduct.getActivePrice() != null %>">
        <zeroio:currency value="<%= thisProduct.getActivePrice().getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisProduct.getActivePrice() == null %>">
        --
      </dhv:evaluate>
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisProduct.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisProduct.getExpirationDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
    <td align="center" nowrap>
      <% if (thisProduct.getActive()) { %>
          <dhv:label name="account.yes">Yes</dhv:label>
      <% } else { %>
          <dhv:label name="account.no">No</dhv:label>
      <% } %>
    </td>
  </tr>
  <%
    }
  %>
</table>
</form>
<dhv:pagedListControl object="productListInfo"/>
