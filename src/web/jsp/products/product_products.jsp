<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<form method="post" action="ProductsCatalog.do?command=AddProducts&quoteId=<%= quote.getId() %>&categoryId=<%= category.getId() %>">
<%-- Trails --%>
  <table class="trails" cellspacing="0">
    <tr>
      <td>
        <a href="ProductsCatalog.do?command=ParentCategories&quoteId=<%= quote.getId() %>"><dhv:label name="product.productCatalog">Product Catalog</dhv:label></a> > 
        <a href="ProductsCatalog.do?command=Categories&quoteId=<%= quote.getId() %>&categoryId=<%= category.getParentId() %>"><%= toHtml(category.getName()) %></a> >
        <dhv:label name="product.listOfProducts">List of Products</dhv:label>
      </td>
    </tr>
  </table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <th width="100%">
      <strong><%= toHtml(category.getName()) %></strong>
    </th>
    <tr><td>&nbsp;</td></tr>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="product.productId">Product Id</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="products.productName">Product Name</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="product.productDescription">Product Description</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="quotes.Price">Price</dhv:label></strong>
    </th>
    <th >
      <strong><dhv:label name="quotes.quantity">Quantity</dhv:label></strong>
    </th>
  </tr>
<%
Iterator productIterator = productList.iterator();
for(int i=1;productIterator.hasNext();i++){
  ProductCatalog product = (ProductCatalog) productIterator.next();
%>
  <tr class="containerBody">
    <td align="left"><%= product.getId() %></td>
    <td align="left"><%= toHtml(product.getName()) %><input type="hidden" name="product_<%= i %>" value="<%= product.getId() %>"/></td>
    <td align="left"><%=(product.getShortDescription().equals("") || product.getShortDescription() == null) ? "-" : toHtml(product.getShortDescription()) %>&nbsp;</td>
    <td align="left"><zeroio:currency value="<%= product.getActivePrice().getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
    <td><input type="text" name="qty_<%= i %>"/></td>
  </tr>
<%
}
%>
</table>
<br />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();"/>
<input type="submit" value="<dhv:label name="button.next.symbol">Next ></dhv:label>"/>
</form>
