<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.orders.base.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="session"/>
<jsp:useBean id="publication" class="org.aspcfs.modules.products.base.ProductCategory" scope="session"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
  
  }
</script>
<%-- Trails --%>
<table class="trails">
  <tr>
    <td>
      <a href="ProductsAndServices.do">Products And Services</a> >
      <a href="ProductsAndServices.do?command=PublicationList">Publications</a> > 
      <a href="ProductsAndServices.do?command=Overview&pubId=<%= publication.getId() %>" ><%= toHtml(publication.getName()) %></a> >
      Order
    </td>
  </tr>
</table>
<%-- End Trails --%>
<h2><%= toHtmlValue(publication.getName()) %></h2>
<form name="order_form" action="Publish.do?command=Insert" onSubmit="return checkForm(this);" method="post">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Details</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Ad Size</td>
    <td><%= toHtmlValue(product.getName()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Ad Dimension</td>
    <td><%= toHtmlValue(product.getShortDescription()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Price</td>
    <td><%= toHtmlValue("$" + (int) (product.getActivePrice().getPriceAmount())) %></td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Select Ad</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Ad</td>
    <td>
      <select name="inventory">
        <option value="-1">-- none --</option>
      </select>
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th colspan="2">
      <strong>Select Dates</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Dates</td>
    <td>
      <input type="checkbox" name="" value="" /> 04/20/2004 <br />
      <input type="checkbox" name="" value="" /> 04/22/2004 <br />
      <input type="checkbox" name="" value="" /> 04/24/2004
    </td>
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
    <td class="formLabel">First Name</td>
    <td><input type="text" name="nameFirst" size="30"></td>
  </tr>
  <tr>
    <td class="formLabel">Last Name</td>
    <td><input type="text" name="nameLast" size="30"></td>
  </tr>
</table>
<br />
<input type="button" value="< Previous" onClick="javascript:window.location.href='Publish.do?command=PlaceAd&pubId=<%= publication.getId() %>'">
<input type="submit" value="Approve">
<input type="button" value="cancel" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductsAndServices.do?command=Overview&pubId=<%= toHtmlValue(publication.getId()) %>'">
