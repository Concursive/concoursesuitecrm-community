<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.orders.base.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OrderDetails" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="productOptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="paymentList" class="org.aspcfs.modules.orders.base.OrderPaymentList" scope="request"/>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="paymentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %> 
<%@ include file="../orders/orders_products_list_menu.jsp" %>
<%@ include file="../orders/orders_products_payments_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script language="JavaScript">
  function reopen(){
    window.location.href='AccountOrders.do?command=Details&id=<%= OrderDetails.getId() %>';
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="AccountOrders.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Orders</a> >
Order Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="orders" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<form name="order_form" method="post">
<input type="hidden" name="id" value="<%=OrderDetails.getId()%>">
<input type="hidden" name="orgId" value="<%=OrderDetails.getOrgId()%>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr><td class="containerBack">
<input type="button" value="Modify Status" onClick="javascript:popURL('Orders.do?command=ModifyStatus&id=<%= OrderDetails.getId() %>&popup=true','OrderStatus','300','200','yes','yes');"/>
<br /><br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Order Details</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">Order Number</td>
    <td width="100%"><%= OrderDetails.getId() %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Entered Date</td>
    <td width="100%"><dhv:tz timestamp="<%= OrderDetails.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Organization Name</td>
    <td width="100%"><%= toHtml(OrderDetails.getName()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Type</td>
    <td width="100%"><%= toHtml(typeSelect.getValueFromId(OrderDetails.getOrderTypeId())) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Description</td>
    <td width="100%"><%= toHtml(OrderDetails.getDescription()) %></td>
  </tr>
<%
if (OrderDetails.getQuoteId() != -1) {
%>
  <tr>
    <td nowrap class="formLabel">Originating Quote</td>
    <td width="100%"><a href="AccountQuotes.do?command=Details&quoteId=<%= OrderDetails.getQuoteId() %>&orgId=<%= OrderDetails.getOrgId() %>">Quote #<%= OrderDetails.getQuoteId() %></a></td>
  </tr>
<%
}
%>
  <tr>
    <td nowrap class="formLabel">Status</td>
    <td width="100%"><%= toHtml(statusSelect.getValueFromId(OrderDetails.getStatusId())) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Status Date</td>
    <td width="100%"><dhv:tz timestamp="<%= OrderDetails.getModified() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
  </tr>
</table>
&nbsp;<br /><br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr><th colspan="8"><strong>Order Items</strong></th></tr>
  <tr>
    <td><strong>Action</strong></td>
    <td><strong>Item Id</strong></td>
    <td><strong>Ad Size</strong></td>
    <td><strong>Publication</strong></td>
    <td><strong>Quantity</strong></td>
    <td><strong>Adsjet.com Price</strong></td>
    <td><strong>Total Price</strong></td>
    <td><strong>Status</strong></td>
  </tr>
  <%
     Iterator products = OrderDetails.getProductList().iterator();
     int rowid=0;
     int i=0;
     while (products.hasNext()) {
       OrderProduct orderProduct = (OrderProduct) products.next();
       ProductCatalog product = orderProduct.getProduct();
       i++;
       rowid = ( rowid != 1 ? 1:2 );
  %>  
  <tr>
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <a href="javascript:displayMenu('menuOrderProducts', '<%= orderProduct.getId() %>');"
      onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>"><a href="AccountOrdersProducts.do?command=Details&productId=<%= orderProduct.getId() %>"><%= orderProduct.getId() %></a></td>
    <td class="row<%= rowid %>"><%= toHtml(product.getName()) %>
<%  
      if(product.getShortDescription() != null && !"".equals(product.getShortDescription())){
%>
      [ <%= product.getShortDescription() %> ] 
<%
      }
%>
    </td>
    <td class="row<%= rowid %>"><%= toHtml(product.getCategoryName()) %></td>
    <td class="row<%= rowid %>"><%= orderProduct.getQuantity() %></td>
    <td class="row<%= rowid %>">$<%= (int) orderProduct.getPriceAmount() %></td>
    <td class="row<%= rowid %>">$<%= (int) orderProduct.getTotalPrice() %></td>
    <td class="row<%= rowid %>"><%= toHtml(statusSelect.getValueFromId(orderProduct.getStatusId())) %></td>
  </tr>
  <% 
     if(orderProduct.getProductOptionList().size() > 0) {
        Iterator options = (Iterator) orderProduct.getProductOptionList().iterator();
        while(options.hasNext()){
          OrderProductOption orderProductOption = (OrderProductOption) options.next();
          ProductOption option = productOptionList.getOptionFromId(orderProductOption.getProductOptionId());
          ProductOptionValues value = productOptionValuesList.getValueFromId(orderProductOption.getIntegerValue());
%>
  <tr>
    <td class="row<%= rowid %>">&nbsp;</td>
    <td class="row<%= rowid %>">&nbsp;</td>
    <td class="row<%= rowid %>">&nbsp; &nbsp; &nbsp;<%= toHtml(value.getDescription()) %></td>
    <td class="row<%= rowid %>">&nbsp;</td>
    <td class="row<%= rowid %>">&nbsp;</td>
    <td class="row<%= rowid %>"><%= orderProductOption.getTotalPrice() %></td>
    <td class="row<%= rowid %>">&nbsp;</td>
    <td class="row<%= rowid %>">&nbsp;</td>
  </tr>
<%
        }
      }
    }
    rowid = ( rowid != 1 ? 1:2 );
%>
  <tr>
    <td colspan="6" align="right" class="row<%= rowid %>"><strong>Grand Total Price</strong></td>
    <td colspan="2" class="row<%= rowid %>">$<%= (int) OrderDetails.getGrandTotal() %></td>
  </tr>
</table><br /><br />
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr><th colspan="6"><strong>Order Payments</strong></th></tr>
  <tr>
    <td><strong>Action</strong></td>
    <td><strong>Payment Id</strong></td>
    <td><strong>Order Item</strong></td>
    <td><strong>Date to Process</strong></td>
    <td><strong>Date Processed</strong></td>
    <td><strong>Status</strong></td>
  </tr>
  
<%
    Iterator payments = (Iterator) paymentList.iterator();
    while(payments.hasNext()){
      OrderPayment orderPayment = (OrderPayment) payments.next();
      i++;
      rowid = (rowid != 1 ? 1:2 );
%>
  <tr>
    <td width=8 valign="center" nowrap class="row<%= rowid %>">
      <a href="javascript:displayPaymentsMenu('menuOrderPayment', '<%= orderPayment.getId() %>');"
      onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>"><a href="AccountOrdersPayments.do?command=Details&paymentId=<%= orderPayment.getId() %>"><%= orderPayment.getId() %></a></td>
    <td class="row<%= rowid %>"><%= orderPayment.getOrderItemId() %></td>
    <td class="row<%= rowid %>"><dhv:tz default="Not Specified" timestamp="<%= orderPayment.getDateToProcess() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
    <td class="row<%= rowid %>"><dhv:tz  default="Not Processed" timestamp="<%= orderPayment.getAuthorizationDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
    <td class="row<%= rowid %>"><%= toHtml(paymentSelect.getValueFromId( orderPayment.getStatusId())) %></td>
  </tr>
<%
    }
  %>
</table>
</td>
</tr>
</table>
</form>
