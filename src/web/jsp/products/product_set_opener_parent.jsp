<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="creditCard" class="org.aspcfs.utils.CreditCardUtils" scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- Hide the trails and top buttons if a popup --%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table width="100%" class="trails">
  <tr>
    <td width="100%">
      Products And Services >
      Publications > 
      <%= toHtml(product.getCategoryName()) %></a> >
      <%= toHtml(product.getName()) %> >
      Order
    </td>
  </tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<script type="text/javascript">
  //reload parent onLoad
  opener.parent.window.location.href='MyCFS.do?command=Home';
</script>
<table class="pagedListHeader2" cellspacing="0">
  <tr>
    <td>Thank you for your order.</td>
  </tr>
  <tr>
    <th>Your order has been submitted with the following details:</th>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Order Details</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Number</td>
    <td>#<%= order.getId() %></td>
  </tr>
  <tr>
    <td class="formLabel">Date</td>
    <td><dhv:tz timestamp="<%= order.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/></td>
  </tr>
  <tr>
    <td class="formLabel">Description</td>
    <td><%= toHtml(order.getDescription()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Notes</td>
    <td><%= toHtml(order.getNotes()) %></td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Billing Information</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Name on Card</td>
    <td><%= toHtml(creditCard.getNameOnCard()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Payment Type</td>
    <td><%= toHtml(creditCard.getType()) %> <%= toHtml(creditCard.displayOnlyLastFour()) %></td>
  </tr>
</table>
<br />
<input type="button" value="Close Window" onClick="window.close()" />

