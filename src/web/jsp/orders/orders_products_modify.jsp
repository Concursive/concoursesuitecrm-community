<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.orders.beans.*,java.text.*,org.aspcfs.modules.orders.base.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="orderProduct" class="org.aspcfs.modules.orders.base.OrderProduct" scope="request"/>
<jsp:useBean id="productStatusList" class="org.aspcfs.modules.orders.base.OrderProductStatusList" scope="request"/>
<jsp:useBean id="productOptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="paymentList" class="org.aspcfs.modules.orders.base.OrderPaymentList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statusBean" class="org.aspcfs.modules.orders.beans.StatusBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Orders.do">Orders</a> > 
      <a href="Orders.do?command=Search">Search Orders</a> >
      Order Details > Order Item Modify
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<form action="OrdersProducts.do?command=Save&productId=<%= orderProduct.getId() %>&auto-populate=true" method="post">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Modify the status of this order item</strong>
    </th>
  </tr>
  <tr>
    <td nowrap>New Status</td>
    <td width="100%"><%= statusSelect.getHtmlSelect("statusId", statusBean.getStatusId()) %></td>
  </tr>
</table>
<input type="submit" value="Save" />
</form>

