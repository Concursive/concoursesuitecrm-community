<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="javascript">
function setField(formField,thisValue,thisForm) {
    var frm = document.forms[thisForm];
    var len = document.forms[thisForm].elements.length;
    var i=0;
    for( i=0 ; i<len ; i++) {
      if (frm.elements[i].name.indexOf(formField)!=-1) {
        if(thisValue){
          frm.elements[i].value = "true";
        } else {
          frm.elements[i].value = "false";
        }
      }
    }
 }
 </script>
<%-- Trails --%>
<table cellspacing="0" class="trails">
  <tr>
    <td>
      <% String link = "ProductsCatalog.do?command=Categories&quoteId=" + quote.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" showLastLink="true"/> >
      <dhv:label name="product.options">Options</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<form name="optionForm" method="post" action="ProductsCatalog.do?command=AddOptions&quoteId=<%= quote.getId() %>&categoryId=<%= parentCategory.getId() %>">
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th >
      <strong><dhv:label name="products.productName">Product Name</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="product.quantity">Quantity</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="product.productPrice">Product Price</dhv:label></strong>
    </th>
  </tr>
  <%
  int i = 0;
  int rowid = 0;
  Iterator iterator = (Iterator) quoteProductList.iterator();
  for(i=1;iterator.hasNext();i++){
    QuoteProduct quoteProduct = (QuoteProduct) iterator.next();
    ProductCatalog product = quoteProduct.getProductCatalog();
    ProductCatalogPricing price = product.getActivePrice();
    ProductOptionList optionList = (ProductOptionList) product.getOptionList();
    rowid = (rowid != 1 ? 1 : 2);
  %>
  <tr>
    <input type="hidden" name="product_<%= i %>" value="<%= product.getId() %>"/>
    <input type="hidden" name="qty_<%= i %>" value="<%= quoteProduct.getQuantity() %>"/>
    <input type="hidden" name="price_<%= i %>" value="<%= quoteProduct.getPriceAmount() %>"/>
    <input type="hidden" id="comment_<%= i %>" name="comment_<%= i %>"  value="<%=(quoteProduct.getComment() == null || "".equals(quoteProduct.getComment())) ? "" : toHtml(quoteProduct.getComment()) %>" />
    <td><b><%= product.getName() %></b></td>
    <td align="center"><b><%= quoteProduct.getQuantity() %></b></td>
    <td align="right"><b><zeroio:currency value="<%= quoteProduct.getQuantity() * quoteProduct.getPriceAmount() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></b></td>
  </tr>
  <tr>
    <td colspan="3" class="empty">
      <dhv:evaluate if="<%= optionList.size() != 0 %>">
      <table cellspacing="0" cellpadding="1" width="100%">
      <%
            int j=0;
            Iterator optionIterator = (Iterator) optionList.iterator();
            for (j=1; optionIterator.hasNext(); j++) {
              ProductOption option = (ProductOption) optionIterator.next();
      %>
        <tr>
          <td>
            <table cellspacing="0" cellpadding="0" width="100">
              <tr>
                <td>
                  <input type="hidden" name="option_<%= product.getId() %>_<%= j %>" value="<%= option.getId() %>"/>
                </td>
                <td><%= option.getHtml() %></td>
              </tr>
             </table>
          </td>
        </tr>
      <% } %>  
      </table>
      </dhv:evaluate>
      <dhv:evaluate if="<%= optionList.size() == 0 %>">
        <dhv:label name="product.productNoConfigurableOptions">This product has no configurable options.</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
  <%
  }
  %>
</table>
<br />
<dhv:label name="product.submitProductsToQuote" param="break=<br />">Please click on the Submit button to <br />add the selected products and their selected options to the Quote.</dhv:label><br /><br />
<input type="submit" value="<dhv:label name="button.submit">Submit</dhv:label>"/><br /><br />
</form>
