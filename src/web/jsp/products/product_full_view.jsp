<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="currentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="configuratorList" class="org.aspcfs.modules.products.base.ProductOptionConfiguratorList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<form name="products" action="Products.do?command=View" method="post">
<%-- Trails --%>
<table cellspacing="0" class="trails">
<table class="trails">
  <tr>
    <td>
      <a href="Products.do?command=SearchForm"><dhv:label name="product.catalog">Catalog</dhv:label></a> >
<%
if (currentCategory.getName() != null) {
%>
      <a href="Products.do?command=View"><dhv:label name="product.topCategories">Top Categories</dhv:label></a> >
      <%= toHtml(currentCategory.getName()) %>
<%
} else {
%>
      <dhv:label name="product.topCategories">Top Categories</dhv:label>
<%
}
%>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%
if (configuratorList.size() <= 0) {
%>
<%
  if( categoryList != null && categoryList.size() > 0) {
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="3">
      <strong><dhv:label name="product.searchProductCatalog">Search Product Catalog</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td>
      <strong><dhv:label name="product.categoryId">Category Id</dhv:label></strong>
    </td>
    <td>
      <strong><dhv:label name="product.CategoryName">Category Name</dhv:label></strong>
    </td>
    <td>
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    </td>
  </tr>
<%
  Iterator categoryIterator = categoryList.iterator();
  while(categoryIterator.hasNext()){
    ProductCategory category = (ProductCategory) categoryIterator.next();
%>
  <tr class="containerBody">
    <td nowrap valign="top"><a href="Products.do?command=View&categoryId=<%= category.getId() %>"><%= category.getId() %></a></td>
    <td nowrap valign="top"><a href="Products.do?command=View&categoryId=<%= category.getId() %>"><%= toHtml(category.getName()) %></a></td>
    <td><%= toHtml(category.getShortDescription()) %></td>
  </tr>
<%
  }
%>
</table>
<%
  }
} else {
%>
<%
if (currentCategory.getId() !=-1) {
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.selectedCategory.text">The selected category, its products & its sub-categories</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel"><strong><dhv:label name="product.categoryId">Category Id</dhv:label></strong></td>
    <td nowrap valign="top"><%= currentCategory.getId() %></td>
  </tr>
  <tr>
    <td class="formLabel"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></td>
    <td nowrap valign="top"><%= toHtml(currentCategory.getName()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong></td>
    <td><%= toHtml(currentCategory.getShortDescription()) %></td>
  </tr>
<%
if (currentCategory.getProductList().size() > 0) {
%>
  <tr>
    <td class="formLabel"><dhv:label name="product.products">Products</dhv:label></td>
    <td>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="product.productId">Product Id</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="products.productName">Product Name</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="product.productDescription">Product Description</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="product.msrp">MSRP</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="quotes.Price">Price</dhv:label></strong>
    </th>
  </tr>
<%
    Iterator productIterator = (Iterator) currentCategory.getProductList().iterator();
    while(productIterator.hasNext()) {
      ProductCatalog product = (ProductCatalog) productIterator.next();
%>
  <tr class="containerBody">
    <td valign="top"><%= product.getId() %></td>
    <td valign="top"><%= toHtml(product.getName()) %></td>
    <td valign="top"><%=(product.getShortDescription().equals("") || product.getShortDescription() == null) ? "-" : toHtml(product.getShortDescription()) %>&nbsp;</td>
    <td valign="top"><zeroio:currency value="<%= product.getActivePrice().getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
    <td valign="top"><zeroio:currency value="<%= product.getActivePrice().getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
  </tr>
<%
}
%>
</table>
    </td>
  </tr>
<%
}
%>
</table>
<%
}
if( categoryList != null && categoryList.size() > 0) {
%>
<table cellpadding="4" cellspacing="0" border="2" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="product.categoryId">Category Id</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="product.CategoryName">Category Name</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="product.parentCategory">Parent Category</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    </th>
  </tr>
<%
  Iterator categoryIterator = categoryList.iterator();
  while(categoryIterator.hasNext()){
    ProductCategory category = (ProductCategory) categoryIterator.next();
%>
  <tr class="containerBody">
    <td nowrap valign="top"><%= category.getId() %></td>
    <td nowrap valign="top"><%= toHtml(category.getName()) %></td>
    <td nowrap valign="top"><%= toHtml(category.getParentName()) %></td>
    <td><%= toHtml(category.getShortDescription()) %></td>
  </tr>
<%
    if (category.getProductList().size() > 0){
%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="3">
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="product.productId">Product Id</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="products.productName">Product Name</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="product.productDescription">Product Description</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.msrp">MSRP</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="quotes.Price">Price</dhv:label></strong>
    </th>
  </tr>
<%
    Iterator productIterator = (Iterator) category.getProductList().iterator();
    while(productIterator.hasNext()) {
      ProductCatalog product = (ProductCatalog) productIterator.next();
%>
  <tr class="containerBody">
    <td valign="top"><%= product.getId() %></td>
    <td valign="top"><%= toHtml(product.getName()) %></td>
    <td valign="top"><%=(product.getShortDescription().equals("") || product.getShortDescription() == null) ? "-" : toHtml(product.getShortDescription()) %>&nbsp;</td>
    <td valign="top"><zeroio:currency value="<%= product.getActivePrice().getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
    <td valign="top"><zeroio:currency value="<%= product.getActivePrice().getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
  </tr>
<%
    if (product.getOptionList().size() > 0) {
%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="5">
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="product.optionId">Option Id</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.optionName">Option Name</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.configuratorName" param="break=<br />">Configurator<br />Name</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.configuratorText" param="break=<br />">Configurator<br />Text</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.configuratorDescription" param="break=<br />">Configurator<br />Description</dhv:label></strong>
    </th>
  </tr>

<%
      Iterator optionIterator = (Iterator) product.getOptionList().iterator();
      while (optionIterator.hasNext()) {
        ProductOption option = (ProductOption) optionIterator.next();
        ProductOptionConfigurator configurator = configuratorList.getConfiguratorFromId(option.getConfiguratorId());
%>
  <tr class="containerBody">
    <td valign="top"><%= option.getId() %></td>
    <td valign="top"><%= toHtml(option.getShortDescription()) %></td>
    <td valign="top"><%=(option.getLongDescription().equals("") || option.getLongDescription() == null) ? "-" : toHtml(option.getLongDescription()) %>&nbsp;</td>
    <td valign="top"><%= toHtml(configurator.getClassName()) %></td>
    <td valign="top"><%= toHtml(configurator.getShortDescription()) %></td>
    <td valign="top"><%= toHtml(configurator.getLongDescription()) %></td>
  </tr>
<%
    if (option.getOptionValuesList().size() > 0){
%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="7">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="product.valueId">Value Id</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.resultId">Result Id</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.msrp">MSRP</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="quotes.Price">Price</dhv:label></strong>
    </th>
  </tr>

<%
        Iterator valueIterator = (Iterator) option.getOptionValuesList().iterator();
        while (valueIterator.hasNext()) {
          ProductOptionValues value = (ProductOptionValues) valueIterator.next();
%>
  <tr class="containerBody">
    <td valign="top"><%= value.getId() %></td>
    <td valign="top"><%= value.getResultId() %></td>
    <td valign="top"><%= toHtml(value.getDescription()) %></td>
    <td valign="top"><zeroio:currency value="<%= value.getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
    <td valign="top"><zeroio:currency value="<%= value.getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
  </tr>
<%
        }
%>
</table>
    </td>
  </tr>
<%
      }
      }
%>
</table>
    </td>
  </tr>
<%
    }
    }
%>
</table>
    </td>
  </tr>
<%
  }
  }
%>
</table>
<%
}
}
%>
</form>

