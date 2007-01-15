<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="optionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
<form method="post" name="addProduct" action="Quotes.do">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> >
      <a href="Quotes.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <dhv:label name="quotes.symbol.number" param='<%= "number="+quote.getGroupId() %>'>Quote #<%= quote.getGroupId() %></dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% String param1 = "quoteId=" + quote.getId(); %>
<dhv:container name="quotes" selected="details" object="quote" param="<%= param1 %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="4">
        <strong><dhv:label name="quotes.symbol.number" param='<%= "number="+quote.getGroupId() %>'>Quote #<%= quote.getGroupId() %></dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
    <td class="formLabel"> Description </td>
      <td>
  <% if(quote.getIssuedDate() != null){ %>
        <dhv:label name="quotes.preparedFor.and.on" param='<%= "name="+toHtml(quote.getNameFirst() +" "+ quote.getNameLast())+"|company="+toHtml(quote.getName())+"|time="+getTime(pageContext,quote.getIssuedDate(),"&nbsp;",DateFormat.SHORT,false,false,true,"&nbsp;") %>'>Prepared for <%= toHtml(quote.getNameFirst() +" "+ quote.getNameLast()) %> and <%= toHtml(quote.getName()) %> on <zeroio:tz timestamp="<%= quote.getIssuedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></dhv:label>
  <% } else { %>
        <dhv:label name="quotes.preparedFor" param='<%= "name="+toHtml(quote.getNameFirst() +" "+ quote.getNameLast())+"|company="+toHtml(quote.getName()) %>'>Prepared for <%= toHtml(quote.getNameFirst() +" "+ quote.getNameLast()) %> and <%= toHtml(quote.getName()) %></dhv:label>
  <% } %>
  <br />
  <%
  if(quote.getShortDescription() != null && !"".equals(quote.getShortDescription())){
  %>
  <dhv:label name="quotes.thisQuoteIsFor.colon" param='<%= "description="+toHtml(quote.getShortDescription()) %>'>This quote is for: <%= toHtml(quote.getShortDescription()) %></dhv:label>
  <br />
  <%
  }
  %>
  <dhv:evaluate if="<%= quote.getTicketId() != -1 %>">
  <%
    String ticketParam = "ticket.number=" + quote.getTicketId() + "|startLink=<a href=\"TroubleTickets.do?command=Details&id=" + quote.getTicketId() + "\">|endLink=</a>";
  %>
    <dhv:label name="quotes.quoteBasedOnFollowingRequest.text" param="<%= ticketParam %>">This quote is based on the following request: <a href="TroubleTickets.do?command=Details&id=<%= quote.getTicketId() %>">Ticket #<%= quote.getTicketId() %></a></dhv:label>
    <br />
  </dhv:evaluate>
  </td>
  </tr>
    <tr class="containerBody">
      <td class="formLabel"><dhv:label name="accounts.Action">Action</dhv:label></td>
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
        &nbsp;
      </td>
      <td>
        <strong><dhv:label name="product.productId">Product Id</dhv:label></strong>
      </td>
      <td>
        <strong><dhv:label name="quotes.quantity">Quantity</dhv:label></strong>
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
      <td colspan="5"><zeroio:dateSelect form="addProduct" field="expirationDate" timestamp="<%= quote.getExpirationDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" /></td>
    </tr>
  </table>
  <br />
  <input type="button" value="Save for later" onClick="checkType(this.value)"/>
  <input type="button" value="Submit" onClick="checkType(this.value)"/>
  <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="checkType(this.value)"/>
</dhv:container>
</form>
