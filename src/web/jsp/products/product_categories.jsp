<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="trailCategories" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<script type="text/javascript">
  var counter = 0;
  function setVariable(j){
    counter = j;
  }
  function checkForm(){
    var i = 1;
    var qtyEntered = false;
    
    while ( i<counter ) {
      var a = document.getElementById("qty_"+i).value;
      i = i + 1;
      if( a != ""){
        var result = checkInt( a );
        if( result == false){
          alert('Please enter a valid Number');
          return false;
        } else {
          // at least one quantity has been provided
          qtyEntered = true;
        }
      }
    }
    if (!qtyEntered) {
      alert("Please provide atlease one product's quantity");
      return false;
    } else {
      return true;
    }
  }
  function addToAction(value){
    document.forms['category'].action = document.forms['category'].action + value;
    document.forms['category'].submit();
  }
</script>
<form name="category" action="ProductsCatalog.do?command=Categories&quoteId=<%= quote.getId() %>" method="post">
<%-- Trails --%>
<table cellspacing="0" class="trails">
  <tr>
    <td>
<%
if( category.getName() !=  null ){
%>
      <a href="ProductsCatalog.do?command=Categories&quoteId=<%= quote.getId() %>">Product Catalog</a> >
<%
} else {
%>
      Product Catalog
<%
}
int i=0;
if(trailCategories != null){
  Iterator iterator = (Iterator) trailCategories.iterator();
  for(i=0; iterator.hasNext(); i++) {
    ProductCategory parent = (ProductCategory) iterator.next();
%>
      <a href="javascript:addToAction('&categoryId=<%= parent.getId() %>');"><%= parent.getName() %></a> >
<%
  }
}
if( category.getName() != null ){
%>
      <%= category.getName() %>
<%
}
%>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%
if( categoryList != null && categoryList.size() > 0) {
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Category Name</strong>
    </th>
    <th>
      <strong>Description</strong>
    </th>
  </tr>
<%
  Iterator categoryIterator = categoryList.iterator();
  while(categoryIterator.hasNext()){
    ProductCategory category1 = (ProductCategory) categoryIterator.next();
%>
  <tr class="containerBody">
    <td nowrap>
      <a href="javascript:addToAction('&categoryId=<%= category1.getId() %>');"><%= category1.getName() %></a>
    </td>
    <td>
      <%= toHtml(category1.getShortDescription()) %><br />
    </td>
  </tr>
<%
  }
%>
</table>
<%
}
%>
</form>
<%
if (productList != null && productList.size() > 0) {
%>
<form name="product" method="post" action="ProductsCatalog.do?command=AddProducts&quoteId=<%= quote.getId() %>&categoryId=<%= category.getId() %>" onSubmit="return checkForm();">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList2">
  <tr>
    <th colspan="5">Please enter the quantity (in whole numbers) in the space provided to select the product. 
      Please click next to proceed to the next stage.
    </th>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Product Id </strong>
    </th>
    <th >
      <strong>Product Name</strong>
    </th>
    <th >
      <strong>Product Description</strong>
    </th>
    <th >
      <strong>Price</strong>
    </th>
    <th >
      <strong>Quantity</strong>
    </th>
  </tr>
<%
  Iterator productIterator = productList.iterator();
  for(i=1;productIterator.hasNext();i++){
    ProductCatalog product = (ProductCatalog) productIterator.next();
%>
  <tr class="containerBody">
    <td align="left"><%= product.getId() %></td>
    <td align="left"><%= toHtml(product.getName()) %><input type="hidden" name="product_<%= i %>" value="<%= product.getId() %>"/></td>
    <td align="left"><%=(product.getShortDescription().equals("") || product.getShortDescription() == null) ? "-" : toHtml(product.getShortDescription()) %>&nbsp;</td>
    <td align="left">$<%= (int)product.getPriceAmount() %></td>
    <td><input type="text" id="qty_<%= i %>" name="qty_<%= i %>"/></td>
  </tr>
<%
  }
%>
</table>
<br />
<input type="button" value="Cancel" onClick="javascript:self.close();"/>
<input type="submit" value="Next >" onClick="setVariable('<%= i %>');"/>
</form>
<%
}
%>
