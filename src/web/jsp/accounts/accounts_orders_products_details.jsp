<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.orders.base.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="orderProduct" class="org.aspcfs.modules.orders.base.OrderProduct" scope="request"/>
<jsp:useBean id="productStatusList" class="org.aspcfs.modules.orders.base.OrderProductStatusList" scope="request"/>
<jsp:useBean id="productOptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="paymentList" class="org.aspcfs.modules.orders.base.OrderPaymentList" scope="request"/>
<jsp:useBean id="historyList" class="org.aspcfs.modules.products.base.CustomerProductHistoryList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="paymentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %> 
<%@ include file="../orders/orders_products_payments_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script language="JavaScript">
  function reopen(){
    window.location.href='AccountOrdersProducts.do?command=Details&productId=<%= orderProduct.getId() %>';
  }
</script>
<form name="accounts_order_product_form" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="AccountOrders.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Orders</a> >
<a href="AccountOrders.do?command=Details&id=<%= order.getId() %>">Order Details</a> >
Order Item Details
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="orders" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
<input type="button" value="Modify Status" onClick="javascript:popURL('OrdersProducts.do?command=Modify&productId=<%= orderProduct.getId() %>&popup=true','OrdersProducts','300','200','yes','yes');"/>&nbsp;
<input type="button" value="Download Product" onClick="javascript:popURL('OrdersProducts.do?command=DisplayCustomerProduct&productId=<%= orderProduct.getId() %>&popup=true','CustomerProduct','600','200','yes','yes');"/>
<br /><br />
<table  cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Order Item Details</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">Order Item Number</td>
    <td width="100%"><%= orderProduct.getId() %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Ad Details</td>
    <td width="100%"><%= toHtml(orderProduct.getProduct().getCategoryName()+": "+orderProduct.getProduct().getName()) %>
<%
String dimensions = ""+orderProduct.getProduct().getShortDescription();
if(dimensions != null && !"".equals(dimensions)){
%>
      [ <%= toHtml(dimensions) %> ]
<%}%>
    </td>
  </tr>
<%
if(orderProduct.getProductOptionList().size() > 0){
  Iterator options = (Iterator) orderProduct.getProductOptionList().iterator();
  while(options.hasNext()){
    OrderProductOption option = (OrderProductOption) options.next();
    ProductOptionValues value = productOptionValuesList.getValueFromId(option.getIntegerValue());
%>
  <tr>
    <td nowrap class="formLabel">Selected Option</td>
    <td width="100%"><%= value.getDescription() %></td>
  </tr>
<%
  }
}
%>
  <tr>
    <td nowrap class="formLabel">Status</td>
    <td width="100%"><%= toHtml(statusSelect.getValueFromId(orderProduct.getStatusId())) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Status Date</td>
    <td width="100%"><dhv:tz timestamp="<%= orderProduct.getStatusDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
  </tr>
<%
if (historyList.size() > 0) {
%>
  <tr>
    <td nowrap class="formLabel">Publish Dates</td>
    <td width="100%">
<%
  Iterator historyIterator = (Iterator) historyList.iterator();
  while (historyIterator.hasNext()) {
    CustomerProductHistory history = (CustomerProductHistory) historyIterator.next();
%>
    <dhv:tz timestamp="<%= history.getProductStartDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/><br />
<%
  }
%>
    </td>
  </tr>
<%
}
%>
</table>
<br />
<table  cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr><th colspan="4"><strong>Order Item Status</strong></th></tr>
  <tr>
    <td><strong>Item Status Id</strong></td>
    <td><strong><dhv:label name="ticket.enteredDate">Entered Date</dhv:label></strong></td>
    <td><strong>Entered By</strong></td>
    <td><strong>Status</strong></td>
  </tr>
<%
int rowid=0;
int i=0;
Iterator iterator = productStatusList.iterator();
while (iterator.hasNext()) {
  OrderProductStatus productStatus = (OrderProductStatus) iterator.next();
  i++;
  rowid = ( rowid != 1 ? 1:2 );
%>  
  <tr>
    <td class="row<%= rowid %>"><%= productStatus.getId() %></td>
    <td class="row<%= rowid %>"><dhv:tz timestamp="<%= productStatus.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
    <td class="row<%= rowid %>"><dhv:username id="<%= productStatus.getEnteredBy() %>" /></td>
    <td class="row<%= rowid %>"><%= toHtml(statusSelect.getValueFromId(productStatus.getStatusId())) %></td>
  </tr>
<%
}
%>
</table><br /><br />
<br />
<table  cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr><th colspan="6"><strong>Order Item Payments</strong></th></tr>
  <tr>
    <td>&nbsp;</td>
    <td><strong>Payment Id</strong></td>
    <td><strong>Order Item</strong></td>
    <td><strong>Date to Process</strong></td>
    <td><strong>Date Processed</strong></td>
    <td><strong>Status</strong></td>
  </tr>
  
<%
    rowid=0;
    i=0;
    Iterator payments = (Iterator) paymentList.iterator();
    while(payments.hasNext()){
      OrderPayment orderPayment = (OrderPayment) payments.next();
      i++;
      rowid = ( rowid != 1 ? 1:2 );
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
</dhv:container>
</form>
