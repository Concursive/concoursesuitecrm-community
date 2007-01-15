<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.utils.*, org.aspcfs.modules.orders.base.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="orderProduct" class="org.aspcfs.modules.orders.base.OrderProduct" scope="request"/>
<jsp:useBean id="orderPayment" class="org.aspcfs.modules.orders.base.OrderPayment" scope="request"/>
<jsp:useBean id="paymentMethod" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="paymentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="creditCardType" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="creditCard" class="org.aspcfs.modules.orders.base.PaymentCreditCard" scope="request"/>
<jsp:useBean id="creditCardPassword" class="java.lang.String" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function reopen(){
    window.location.href='AccountOrdersPayments.do?command=Details&paymentId=<%= orderPayment.getId() %>';
  }
</script>
<form name="accounts_order_payment_form" action="AccountOrdersPayments.do?command=Details&paymentId=<%= orderPayment.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Accounts.do">Accounts</a> > 
      <a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
      <a href="AccountOrders.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Orders</a> >
      <a href="AccountOrders.do?command=Details&id=<%= order.getId() %>">Order Details</a> >
      <a href="AccountOrdersProducts.do?command=Details&productId=<%= orderProduct.getId() %>">Order Item Details </a> >
      Order Item Payment Details
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%= showError(request, "actionError", false) %>
<dhv:container name="accounts" selected="orders" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
    <td width="100%"><%@ include file="accounts_details_header_include.jsp" %></td>
    <td class="tabSpace2">&nbsp;</td>
  </tr>
  <tr>
    <td class="containerBackSide">
<input type="button" value="Modify Status" onClick="javascript:popURL('OrdersPayments.do?command=Modify&paymentId=<%= orderPayment.getId() %>&popup=true','OrderPayments','500','200','yes','yes');"/><br />
<dhv:evaluate if='<%= creditCardPassword == null || "".equals(creditCardPassword) %>'>
  To view credit card information, enter the site password:
  <input type="password" name="creditCardPassword"/>
  <input type="submit" value="Submit"/>
</dhv:evaluate>
<dhv:evaluate if='<%= creditCardPassword != null && !"".equals(creditCardPassword) %>'>
  <a href="AccountOrdersPayments.do?command=Details&paymentId=<%= orderPayment.getId() %>&resetPassword=true">Hide credit card information</a>
</dhv:evaluate>
<br /><br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Order Item Payment Details</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">Order Item Payment Number</td>
    <td width="100%"><%= orderPayment.getId() %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Order Item Number</td>
    <td width="100%"><%= orderProduct.getId() %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Payment Method</td>
    <td width="100%"><%= toHtml(paymentMethod.getValueFromId(orderPayment.getPaymentMethodId())) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Payment Amount</td>
    <td width="100%">$<%= (int) orderPayment.getAmount() %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Authorization Reference #</td>
<%
      if(orderPayment.getAuthorizationRefNumber() != null ){
%>
    <td width="100%"><%= toHtml(orderPayment.getAuthorizationRefNumber()) %></td>
<%
      } else {
%>
    <td width="100%">&nbsp;</td>
<%    
      }
%>
  </tr>
  <tr>
    <td nowrap class="formLabel">Authorization Code </td>
<%
      if(orderPayment.getAuthorizationCode() != null ){
%>
    <td width="100%"><%= toHtml(orderPayment.getAuthorizationCode()) %></td>
<%
      } else {
%>
    <td width="100%">&nbsp;</td>
<%    
      }
%>
  </tr>
  <tr>
    <td nowrap class="formLabel">Authorization Date </td>
    <td width="100%"><dhv:tz default="Not Processed" timestamp="<%= orderPayment.getAuthorizationDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Date To Process </td>
    <td width="100%"><dhv:tz default="--" timestamp="<%= orderPayment.getDateToProcess() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Current Status</td>
    <td width="100%"><%= toHtml(paymentSelect.getValueFromId(orderPayment.getStatusId())) %></td>
  </tr>
</table>
<br />
<% 
if ( order.getAddressList().size() > 0) { 
  OrderAddress billingAddress = order.getAddress("Billing");
%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Billing Address</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Address Line1</td>
    <td><%= toHtml(billingAddress.getStreetAddressLine1()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label></td>
    <td><%= toHtml(billingAddress.getStreetAddressLine2()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Address Line 3</td>
    <td><%= toHtml(billingAddress.getStreetAddressLine3()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accounts_add.City">City</dhv:label></td>
    <td><%= toHtml(billingAddress.getCity()) %></td>
  </tr>
  <tr>
    <td class="formLabel">State</td>
    <td><%= toHtml((billingAddress.getState() != null && !"".equals(billingAddress.getState())) ? billingAddress.getState() : billingAddress.getOtherState()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label></td>
    <td><%= toHtml(billingAddress.getZip()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Country</td>
    <td><%= toHtml(billingAddress.getCountry()) %></td>
  </tr>
</table>
<br />
<%
}
%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Billing Information</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Payment Type</td>
    <td><%= toHtml(creditCardType.getValueFromId(creditCard.getCardType())) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Card Number </td>
    <td>
      <dhv:evaluate if='<%= creditCardPassword == null || "".equals(creditCardPassword) %>'>
        &lt;protected from view&gt;
      </dhv:evaluate>
      <dhv:evaluate if='<%= creditCardPassword != null && !"".equals(creditCardPassword) %>'>
        <%= creditCard.getCardNumber() %>
      </dhv:evaluate>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Expiration</td>
    <td><%= toHtml(creditCard.getExpirationMonth()+"/"+creditCard.getExpirationYear()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Card Security Code </td>
    <td><%= toHtml(creditCard.getCardSecurityCode()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Name on Card</td>
    <td><%= toHtml(creditCard.getNameOnCard()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Company Name on Card</td>
    <td><%= toHtml(creditCard.getCompanyNameOnCard()) %></td>
  </tr>
</table>
<br />
<table  cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr><th colspan="4"><strong>Order Payment Status</strong></th></tr>
  <tr>
    <td><strong>Status Id</strong></td>
    <td><strong>Status Date</strong></td>
    <td><strong>Entered By</strong></td>
    <td><strong>Status</strong></td>
  </tr>
<%
int rowid=0;
Iterator iterator = orderPayment.getOrderPaymentStatusList().iterator();
while (iterator.hasNext()) {
  OrderPaymentStatus paymentStatus = (OrderPaymentStatus) iterator.next();
  rowid = ( rowid != 1 ? 1:2 );
%>  
  <tr class="row<%= rowid %>">
    <td><%= paymentStatus.getId() %></td>
    <td><dhv:tz timestamp="<%= paymentStatus.getModified() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/></td>
    <td><dhv:username id="<%= paymentStatus.getModifiedBy() %>" /></td>
    <td><%= toHtml(paymentSelect.getValueFromId(paymentStatus.getStatusId())) %></td>
  </tr>
<%
}
%>
</table>
</dhv:container>
</form>
