<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="optionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function checkType(value) {
    if (value == "Save for later") {
      document.forms['addProduct'].action = 'Quotes.do?command=Save&quoteId=<%= quote.getId() %>&auto-populate=true';
      document.forms['addProduct'].submit();
    } else if (value == "Submit") {
      if (confirm('Are you sure you want to submit this quote for review by the selected contact?')) {
        document.forms['addProduct'].action = 'Quotes.do?command=Submit&quoteId=<%= quote.getId() %>&auto-populate=true';
        document.forms['addProduct'].submit();
      }
    } else if (value == "Delete") {
      if (confirm('Are you sure you want to permanently delete this quote?')) {
        document.forms['addProduct'].action = 'Quotes.do?command=Delete&quoteId=<%= quote.getId() %>';
        document.forms['addProduct'].submit();
      }
    } else if (value == "Create Order") {
      document.forms['addProduct'].action = 'Orders.do?command="CreateOrder&quoteId=<%= quote.getId() %>';
      document.forms['addProduct'].submit();
    }
  }
  
  function reopen(){
    window.location.href='Quotes.do?command=Details&quoteId=<%= quote.getId() %>';
  }
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do">Quotes</a> >
      <a href="Quotes.do?command=Search">Search Results</a> >
      Quote #<%= quote.getId() %>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<form method="post" name="addProduct" action="Quotes.do?">
<% String param1 = "quoteId=" + quote.getId(); %>
<dhv:container name="quotes" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack" colspan="2">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong>Quote #<%= quote.getId() %></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel"> Description </td>
    <td>
      Prepared for <%= toHtml(quote.getNameFirst() +" "+ quote.getNameLast()) %> and <%= toHtml(quote.getName()) %>
<%
if(quote.getIssuedDate() != null){
%>
      on <dhv:tz timestamp="<%= quote.getIssuedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
<%
}
%>
<br />
<%
if(quote.getShortDescription() != null && !"".equals(quote.getShortDescription())){
%>
This quote is for: <%= toHtml(quote.getShortDescription()) %>
<br />
<%
}
if(quote.getTicketId() != -1){
%>
This quote is based on the following request: <a href="TroubleTickets.do?command=Details&id=<%= quote.getTicketId() %>">ticket #<%= quote.getTicketId() %> </a>
<br />
<%
}
%>
</td>
</tr>
  <tr class="containerBody">
    <td class="formLabel"> Action </td>
    <td>
<%
if(quoteStatusList.getValueFromId(quote.getStatusId()).equals("Accepted by customer")){
%>
      <input type="button" value="Create Order" onClick="checkType(this.value)"/>
<%
}else{
%>
      <input type="button" value="Add Product" onClick="javascript:popURL('ProductsCatalog.do?command=Categories&quoteId=<%= quote.getId() %>','Products','500','400','yes','yes');"/>
<%
}
%>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="6">
      <strong>List of Products </strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <strong>Action</strong>
    </td>
    <td>
      <strong>Product Id</strong>
    </td>
    <td>
      <strong>Quantity</strong>
    </td>
    <td>
      <strong>Product Price </strong>
    </td>
    <td>
      <strong>Recurring Price</strong>
    </td>
    <td align="right">
      <strong>Total</strong>
    </td>
  </tr>
<%
if(quote.getProductList().size() != 0){
  Iterator quoteProducts = (Iterator) quote.getProductList().iterator();
  while(quoteProducts.hasNext()){
    QuoteProduct product = (QuoteProduct) quoteProducts.next();
    ProductCatalog productCatalog = productList.getProductFromId(product.getProductId());
    QuoteProductOptionList optionsList = product.getProductOptionList();
    Iterator options = (Iterator) optionsList.iterator();
%>
  <tr class="containerBody">
<%
    if(!quoteStatusList.getValueFromId(quote.getStatusId()).equals("Accepted by customer")){
%>
    <td nowrap class="formLabel"><a href="Quotes.do?command=RemoveProduct&quoteId=<%= quote.getId() %>&productId=<%= product.getId() %>">remove</a></td>
<%
    }else{
%>
    <td nowrap class="formLabel">remove</td>
<%
    }
%>
    <td nowrap><%= toHtml(productCatalog.getCategoryName()+" "+productCatalog.getName()) %></td>
    <td nowrap><%= product.getQuantity() %></td>
    <td nowrap>$<%= (int)product.getPriceAmount() %></td>
    <td nowrap><%= (int) product.getRecurringAmount() %></td>
    <td nowrap align="right">$<%= (int)product.getTotalPrice() %></td>
  </tr>
<%
    while(options.hasNext()){
      QuoteProductOption option = (QuoteProductOption) options.next();
      ProductOption productOption = optionList.getOptionFromId(option.getProductOptionId());
      ProductOptionValues optionValue = productOptionValuesList.getValueFromId(option.getIntegerValue());
%>
  <tr class="containerBody">
    <td>&nbsp;</td>
    <td>&nbsp; &nbsp; <%= toHtml(optionValue.getDescription()) %></td>
    <td>&nbsp;</td>
    <td>$<%= (int)option.getPriceAmount() %></td>
    <td><%= (int) product.getRecurringAmount() %></td>
    <td>&nbsp;</td>
  </tr>
<%    
    }
  }
}
%>
  <tr>
    <td nowrap colspan="6" align="right"><strong>Grand Total Price = $<%= (int)quote.getGrandTotal()%></strong></td>
  </tr>
  <tr>
    <td class="formLabel">
      Notes
    </td>
    <td colspan=5>
      <textarea name="notes" rows="5" cols="65"><%= toString(quote.getNotes()) %></textarea>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Quote expiration date
    </td>
    <td colspan="5">
      <input type="text" name="expirationDate" value="<dhv:tz timestamp="<%= quote.getExpirationDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>"/>
      <a href="javascript:popCalendar('addProduct', 'expirationDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
&nbsp;
<br />
<input type="button" value="Save for later" onClick="checkType(this.value)"/>
<input type="button" value="Submit" onClick="checkType(this.value)"/>
<input type="button" value="Delete" onClick="checkType(this.value)"/>
</td>
</tr>
</table>
</form>
