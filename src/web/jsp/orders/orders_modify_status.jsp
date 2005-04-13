<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.orders.base.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OrderDetails" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="productOptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="paymentList" class="org.aspcfs.modules.orders.base.OrderPaymentList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
      Order Details
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<form action="Orders.do?command=SaveStatus&id=<%= OrderDetails.getId() %>&auto-populate=true" method="post">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Modify the Status and Notes of this order</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">New Status</td>
    <td width="100%"><%= statusSelect.getHtmlSelect("statusId", statusBean.getStatusId()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Notes</td>
    <td width="100%"><textarea name="authorizationCode" rows="5" cols="35"><%= OrderDetails.getNotes() %></textarea></td>
  </tr>
</table>
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" />
</form>

