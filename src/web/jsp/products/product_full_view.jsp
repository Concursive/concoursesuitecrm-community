<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="currentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="configuratorList" class="org.aspcfs.modules.products.base.ProductOptionConfiguratorList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<form name="products" action="Products.do?command=View" method="post">
<%-- Trails --%>
<table cellspacing="0" class="trails">
<table class="trails">
  <tr>
    <td>
      <a href="Products.do?command=SearchForm">Catalog</a> >
<%
if (currentCategory.getName() != null) {
%>
      <a href="Products.do?command=View">Top Categories</a> >
      <%= toHtml(currentCategory.getName()) %>
<%
} else {
%>
      Top Categories
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
      <strong>Product Catalog</strong>
    </th>
  </tr>
  <tr>
    <td>
      <strong>Category Id</strong>
    </td>
    <td>
      <strong>Category Name</strong>
    </td>
    <td>
      <strong>Description</strong>
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
      <strong>The selected category, its products & its sub-categories</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel"><strong>Category Id</strong></td>
    <td nowrap valign="top"><%= currentCategory.getId() %></td>
  </tr>
  <tr>
    <td class="formLabel"><strong>Name</strong></td>
    <td nowrap valign="top"><%= toHtml(currentCategory.getName()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><strong>Description</strong></td>
    <td><%= toHtml(currentCategory.getShortDescription()) %></td>
  </tr>
<%
if (currentCategory.getProductList().size() > 0) {
%>
  <tr>
    <td class="formLabel">Products</td>
    <td>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Product Id </strong>
    </th>
    <th>
      <strong>Product Name</strong>
    </th>
    <th>
      <strong>Product Description</strong>
    </th>
    <th>
      <strong>MSRP</strong>
    </th>
    <th>
      <strong>Price</strong>
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
    <td valign="top">$<%= (int)product.getMsrpAmount() %></td>
    <td valign="top">$<%= (int)product.getPriceAmount() %></td>
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
      <strong>Category Id</strong>
    </th>
    <th>
      <strong>Category Name</strong>
    </th>
    <th>
      <strong>Parent Category</strong>
    </th>
    <th>
      <strong>Description</strong>
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
      <strong>Product Id </strong>
    </th>
    <th >
      <strong>Product Name</strong>
    </th>
    <th>
      <strong>Product Description</strong>
    </th>
    <th >
      <strong>MSRP</strong>
    </th>
    <th >
      <strong>Price</strong>
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
    <td valign="top">$<%= (int)product.getMsrpAmount() %></td>
    <td valign="top">$<%= (int)product.getPriceAmount() %></td>
  </tr>
<%
    if (product.getProductOptionList().size() > 0) {
%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="5">
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Option Id </strong>
    </th>
    <th >
      <strong>Option Name</strong>
    </th>
    <th>
      <strong>Description</strong>
    </th>
    <th >
      <strong>Configurator<br />Name</strong>
    </th>
    <th >
      <strong>Configurator<br />Text</strong>
    </th>
    <th >
      <strong>Configurator<br />Description</strong>
    </th>
  </tr>

<%
      Iterator optionIterator = (Iterator) product.getProductOptionList().iterator();
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
      <strong>Value Id </strong>
    </th>
    <th >
      <strong>Result Id</strong>
    </th>
    <th >
      <strong>Description</strong>
    </th>
    <th >
      <strong>MSRP</strong>
    </th>
    <th >
      <strong>Price</strong>
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
    <td valign="top">$<%= (int)value.getMsrpAmount() %></td>
    <td valign="top">$<%= (int)value.getPriceAmount() %></td>
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

