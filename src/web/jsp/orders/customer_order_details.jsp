<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.orders.base.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="productOptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="paymentList" class="org.aspcfs.modules.orders.base.OrderPaymentList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="paymentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="customerProductHistoryList" class="org.aspcfs.modules.products.base.CustomerProductHistoryList" scope="request"/>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %> 
<%@ include file="../initPage.jsp" %>
<%@ include file="customer_order_item_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="ProductsAndServices.do">Products And Services</a> > 
      <a href="OrderHistory.do?command=View">Order History</a> > 
      Order Details
    </td>
  </tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Order Details</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">Order Number</td>
    <td width="100%"><%= order.getId() %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Entered Date</td>
    <td width="100%"><dhv:tz timestamp="<%= order.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Organization Name</td>
    <td width="100%"><%= toHtml(order.getName()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Type</td>
    <td width="100%"><%= toHtml(typeSelect.getValueFromId(order.getOrderTypeId())) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Description</td>
    <td width="100%"><%= toHtml(order.getDescription()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Status</td>
    <td width="100%"><%= toHtml(statusSelect.getValueFromId(order.getStatusId())) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Status Date</td>
    <td width="100%"><dhv:tz timestamp="<%= order.getModified() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
  </tr>
</table>
<br />
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
     Iterator products = order.getProductList().iterator();
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
      <a href="javascript:displayMenu('menuOrderHistory', '<%= customerProductHistoryList.getCustomerProductIdFromOrderProductId(orderProduct.getId()) %>');"
      onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>"><%= orderProduct.getId() %></td>
    <td class="row<%= rowid %>"><%= toHtml(product.getName()) %>
<%  
      if(product.getShortDescription() != null && !"".equals(product.getShortDescription())){
%>
      [ <%= toHtml(product.getShortDescription()) %> ] 
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
    <td colspan="2" class="row<%= rowid %>"><%= toHtml("" + order.getGrandTotal()) %></td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr><th colspan="5"><strong>Order Payments</strong></th></tr>
  <tr>
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
    <td class="row<%= rowid %>"><%= orderPayment.getId() %></td>
    <td class="row<%= rowid %>"><%= orderPayment.getOrderItemId() %></td>
    <td class="row<%= rowid %>"><dhv:tz default="--" timestamp="<%= orderPayment.getDateToProcess() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
    <td class="row<%= rowid %>"><dhv:tz  default="Not Processed" timestamp="<%= orderPayment.getAuthorizationDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
    <td class="row<%= rowid %>"><%= toHtml(paymentSelect.getValueFromId( orderPayment.getStatusId())) %></td>
  </tr>
<%
    }
  %>
</table>

