<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
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
          alert(label("check.number.invalid","Please enter a valid Number"));
          return false;
        } else {
          // at least one quantity has been provided
          qtyEntered = true;
        }
      }
    }
    if (!qtyEntered) {
      alert(label("check.product.quantity","Please provide atleast one product's quantity"));
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
      <% String link = "ProductsCatalog.do?command=Categories&quoteId=" + quote.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" />
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% if (!(productList != null && productList.size() > 0) && !( categoryList != null && categoryList.size() > 0)) { %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="empty">
  <tr class="containerBody">
    <td>
      <% if(parentCategory.getName() != null ){ %>
        <dhv:label name="product.noItemsFoundIn" param='<%= "catalog="+parentCategory.getName() %>'>No items found in <%= parentCategory.getName() %></dhv:label>
      <% } else { %>
        <dhv:label name="product.noItemsFoundInCatalog">No items found in catalog</dhv:label>
      <%}%>.
    </td>
  </tr>
</table>
<%}%>
<% if( categoryList != null && categoryList.size() > 0) { 
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="product.CategoryName">Category Name</dhv:label></strong>
    </th>
    <%--
    <th>
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    </th>
    --%>
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
    <%--
    <td>
      <%= toHtml(category1.getShortDescription()) %><br />
    </td>
    --%>
  </tr>
<% } %>
</table>
<% } %>
</form>
<% if (productList != null && productList.size() > 0) { %>
<form name="product" method="post" action="ProductsCatalog.do?command=AddProducts&quoteId=<%= quote.getId() %>&categoryId=<%= parentCategory.getId() %>" onSubmit="return checkForm();">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList2">
  <tr>
    <th colspan="5">
      <dhv:label name="product.quantity.text">Please enter the quantity (in whole numbers) in the space provided to select the product. Please click next to proceed to the next stage.</dhv:label>
    </th>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="quotes.squ">SKU</dhv:label></strong>
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
  int i = 0;
  for(i=1;productIterator.hasNext();i++){
    ProductCatalog product = (ProductCatalog) productIterator.next();
%>
  <tr class="containerBody">
    <td align="left"><%= toHtml(product.getSku()) %></td>
    <td align="left"><%= toHtml(product.getName()) %><input type="hidden" name="product_<%= i %>" value="<%= product.getId() %>"/></td>
    <td align="left">
      <input type="text" id="comment_<%= i %>" name="comment_<%= i %>"  value="<%=(product.getShortDescription() == null || "".equals(product.getShortDescription())) ? "" : toHtml(product.getShortDescription()) %>" />
    </td>
    <td align="right">
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" id="price_<%= i %>" name="price_<%= i %>" value="<zeroio:number value="<%= product.getActivePrice().getPriceAmount() %>" locale="<%= User.getLocale() %>" />" size="10"/>
    </td>
    <td><input type="text" id="qty_<%= i %>" name="qty_<%= i %>" size="6"/></td>
  </tr>
<% } %>
</table>
<br />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();"/>
<input type="submit" value="<dhv:label name="button.next.symbol">Next ></dhv:label>" onClick="setVariable('<%= i %>');"/>
</form>
<% } %>
