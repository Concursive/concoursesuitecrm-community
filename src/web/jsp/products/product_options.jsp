<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="category" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="trailCategories" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="category" action="ProductsCatalog.do?command=Categories&quoteId=<%= quote.getId() %>" method="post">
<script type="text/javascript">
  function addToAction(value){
    document.forms['category'].action = document.forms['category'].action + value;
  }
</script>
<%-- Trails --%>
<table cellspacing="0" class="trails">
  <tr>
    <td>
      <a href="ProductsCatalog.do?command=Categories&quoteId=<%= quote.getId() %>">Product Catalog</a> >
<%
int i=0;
if(trailCategories != null){
  Iterator iterator = (Iterator) trailCategories.iterator();
  for(i=0; iterator.hasNext(); i++) {
    ProductCategory parent = (ProductCategory) iterator.next();
%>
      <a href="#" onClick="addToAction('&categoryId=<%= parent.getId() %>');submit();"><%= parent.getName() %></a> >
<%
  }
}
%>
      <a href="#" onClick="addToAction('&categoryId=<%= category.getId() %>');submit();"><%= category.getName() %></a> >
        Products Confirmation And Options
      </td>
    </tr>
  </table>
</form>
<%-- End Trails --%>
<form name="option" method="post" action="ProductsCatalog.do?command=AddOptions&quoteId=<%= quote.getId() %>&categoryId=<%= category.getId() %>">
<%
Iterator iterator = (Iterator) quoteProductList.iterator();
for(i=1;iterator.hasNext();i++){
  QuoteProduct quoteProduct = (QuoteProduct) iterator.next();
  ProductCatalog product = quoteProduct.getProductCatalog();
  ProductOptionList optionList = (ProductOptionList) product.getProductOptionList();
  if(optionList.size() <= 0){
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Product #<%= product.getId() %></strong>
    </th>
    <th>
      <strong>Quantity: <%= quoteProduct.getQuantity() %></strong>
    </th>
    <th>
      <strong>Product Price: $<%= (int) (quoteProduct.getQuantity() * product.getPriceAmount()) %></strong>
    </th>
  </tr>
  <tr><td colspan="3">&nbsp;</td></tr>
  <tr>
    <td colspan="3">
      This product has no configurable options.
      <input type="hidden" name="product_<%= i %>" value="<%= product.getId() %>"/>
      <input type="hidden" name="qty_<%= i %>" value="<%= quoteProduct.getQuantity() %>"/>
    </td>
  </tr>
</table>

<%
  }else{
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Product #<%= product.getId() %></strong>
    </th>
    <th>
      <strong>Quantity: <%= quoteProduct.getQuantity() %></strong>
    </th>
    <th>
      <strong>Product Price: $<%= (int) (quoteProduct.getQuantity() * product.getPriceAmount()) %></strong>
    </th>
  </tr>
  <tr>
    <td>
      <strong>Selection</strong>
    </td>
    <td>
      <strong>Option Name</strong>
    </td>
    <td>
      <strong>Option Value</strong>
    </td>
    <td>
      <strong>Option Price</strong>
    </td>
  </tr>
<%
    Iterator optionIterator = (Iterator) product.getProductOptionList().iterator();
    while(optionIterator.hasNext()){
      ProductOption option = (ProductOption) optionIterator.next();
      Iterator values = (Iterator) option.getOptionValuesList().iterator();
      while(values.hasNext()){
        ProductOptionValues value = (ProductOptionValues) values.next();

%>
  <tr class="containerBody">
    <td align="left">
<%
      if(option.getOptionValuesList().size() == 1 ){
%>
      <input type="checkbox" name="selection_<%= product.getId() %>_<%= option.getId() %>" value="<%= value.getId() %>"/>
<%
      }else{
%>
      <input type="radio" name="selection_<%= product.getId() %>_<%= option.getId() %>" value="<%= value.getId() %>"/>
<%
      }
%>
      </td>
    <td align="left">
      <input type="hidden" name="product_<%= i %>" value="<%= product.getId() %>"/>
      <input type="hidden" name="qty_<%= i %>" value="<%= quoteProduct.getQuantity() %>"/>
      <%= option.getShortDescription() %>
    </td>
    <td align="left">
      <input type="hidden" name="option_<%= product.getId() %>" value="<%= option.getId() %>"/>
      <%= toHtml(value.getDescription()) %>
    </td>
    <td align="left">
      <input type="hidden" name="value_<%= product.getId() %>_<%= option.getId() %>" value="<%= value.getId() %>"/>
      $<%= (int)value.getPriceAmount() %>
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
Please click on the Submit button to <br />add the selected products and their selected options to the Quote.<br /><br />
<input type="submit" value="Submit"/><br /><br />
</form>
