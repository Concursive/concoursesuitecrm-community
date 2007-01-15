<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="searchOrderListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="categorySelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function clearForm() {
    document.forms['orderNumberForm'].id.value = "";
    document.forms['orderItemNumberForm'].productId.value = "";
    document.forms['orderPaymentNumberForm'].paymentId.value = "";
    document.forms['searchOrder'].listFilter1.options.selectedIndex = 0;
    document.forms['searchOrder'].listFilter2.options.selectedIndex = 0;
    document.forms['orderNumberForm'].id.focus();
  }
</script>
<dhv:evaluate if='<%= request.getParameter("productId") == null && request.getParameter("paymentId") == null %>'><body onLoad="javascript:document.forms['orderNumberForm'].id.focus();"></dhv:evaluate>
<dhv:evaluate if='<%= request.getParameter("productId") != null %>'><body onLoad="javascript:document.forms['orderItemNumberForm'].productId.focus();"></dhv:evaluate>
<dhv:evaluate if='<%= request.getParameter("paymentId") != null %>'><body onLoad="javascript:document.forms['orderPaymentNumberForm'].paymentId.focus();"></dhv:evaluate>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Orders.do">Orders</a> > 
      Search Orders
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%= showError(request, "actionError", false) %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Orders/Items/Payments by Number</strong>
    </th>
  </tr>
  <form name="orderNumberForm" method="post" action="Orders.do?command=Details">
    <tr>
      <td class="formLabel">
        Order Number
      </td>
      <td>
        <input type="text" size="10" name="id" />
        <input type="submit" value="<dhv:label name="button.search">Search</dhv:label>" />
      </td>
    </tr>
  </form>
  <form name="orderItemNumberForm" method="post" action="OrdersProducts.do?command=Details">
    <tr>
      <td class="formLabel">
        Order Item Number
      </td>
      <td>
        <input type="text" size="10" name="productId" />
        <input type="submit" value="<dhv:label name="button.search">Search</dhv:label>" />
      </td>
    </tr>
  </form>
  <form name="orderPaymentNumberForm" method="post" action="OrdersPayments.do?command=Details">
    <tr>
      <td class="formLabel">
        Payment Number
      </td>
      <td>
        <input type="text" size="10" name="paymentId" />
        <input type="submit" value="<dhv:label name="button.search">Search</dhv:label>" />
      </td>
    </tr>
  </form>
</table>
<form name="searchOrder" action="Orders.do?command=Search" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Orders</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Order Status
    </td>
    <td>
      <%= statusSelect.getHtmlSelect("listFilter1", searchOrderListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Product Category
    </td>
    <td>
      <%= categorySelect.getHtml("listFilter2", searchOrderListInfo.getFilterKey("listFilter2")) %>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
</form>
</body>
