<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.orders.beans.*,java.text.*,org.aspcfs.utils.*, org.aspcfs.modules.orders.base.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="orderProduct" class="org.aspcfs.modules.orders.base.OrderProduct" scope="request"/>
<jsp:useBean id="orderPayment" class="org.aspcfs.modules.orders.base.OrderPayment" scope="request"/>
<jsp:useBean id="paymentMethod" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="paymentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="creditCardType" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="creditCard" class="org.aspcfs.modules.orders.base.PaymentCreditCard" scope="request"/>
<jsp:useBean id="statusBean" class="org.aspcfs.modules.orders.beans.StatusBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<%-- Trails --%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Orders.do">Orders</a> > 
      <a href="Orders.do?command=Search">Search Orders</a> >
      Order Details > Order Item Payment Modify
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<form name="orderPayment" action="OrdersPayments.do?command=Save&paymentId=<%= orderPayment.getId() %>&auto-populate=true" method="post">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Modify Payment Details</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">New Status</td>
    <td width="100%"><%= paymentSelect.getHtmlSelect("statusId", statusBean.getStatusId()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Authorization Reference Number</td>
    <td width="100%"><input type="text" size="15" name="authorizationRefNumber" value="<%= toHtmlValue( orderPayment.getAuthorizationRefNumber() ) %>"/></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Authorization Code</td>
    <td width="100"><input type="text" size="15" name="authorizationCode" value="<%= toHtmlValue( orderPayment.getAuthorizationCode() ) %>"/></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Authorization Date</td>
    <td width="100%">
      <input type="text" name="authorizationDate" value="<dhv:tz timestamp="<%= orderPayment.getAuthorizationDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>"/>
      <a href="javascript:popCalendar('orderPayment', 'authorizationDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" />
</form>

