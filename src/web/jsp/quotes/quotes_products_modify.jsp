<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteProduct" class="org.aspcfs.modules.quotes.base.QuoteProduct" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkCurrency.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function checkForm(form) {
    var flag = true;
    var message = "";
    if (checkNullString(document.forms['modifyProduct'].priceAmount.value)) {
      message += label("check.quote.extendedprice.blank","The extended price can not be blank.\n");
      flag = false;
    } else {
      if(!checkRealNumber(document.forms['modifyProduct'].priceAmount.value)) {
        message += label("check.number.invalid","- Please enter a valid Number\r\n");
        flag = false;
      }
    }
    if (checkNullString(document.forms['modifyProduct'].quantity.value)) {
      message += label("check.quote.quantity.blank","The quantity can not be blank.\n");
      flag = false;
    } else {
      if(!checkInt(document.forms['modifyProduct'].quantity.value)) {
        message += label("check.number.invalid","- Please enter a valid Number\r\n");
        flag = false;
      }
    }
    if (!checkNullString(document.forms['modifyProduct'].estimatedDeliveryDate.value)) {
      if (!checkDate(document.forms['modifyProduct'].estimatedDeliveryDate.value)){
        message += label("check.quote.estdeldate.invalid","The estimated delivery date is not a valid date.\n");
        flag = false;
      }
    }
    if (flag == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
  
</script>
<body onLoad="javascript:document.modifyProduct.priceAmount.focus();">
<form method="post" name="modifyProduct" action="QuotesProducts.do?command=Save&quoteId=<%= quote.getId() %>&quoteProductId=<%= quoteProduct.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong><dhv:label name="quotes.quoteItemNumber.symbol">Quote Item #</dhv:label><%= quoteProduct.getId() %></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="quotes.sku">SKU</dhv:label></td>
    <td><%= toHtml(quoteProduct.getProductCatalog().getSku()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="quotes.itemName">Item Name</dhv:label>
    </td>
    <td><%= toHtml(quoteProduct.getProductCatalog().getName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="quotes.itemDescription">Item Description</dhv:label></td>
    <td><input type="text" name="comment" value="<%= toHtmlValue(quoteProduct.getComment()) %>" /></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="quotes.Price">Price</dhv:label></td>
    <td><input type="text" name="priceAmount" value="<%= quoteProduct.getPriceAmount() %>"/></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="quotes.quantity">Quantity</dhv:label></td>
    <td><input type="text" name="quantity" value="<%= quoteProduct.getQuantity() %>" /></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="quotes.estimatedDelivery" param="break=&nbsp;">Estimated Delivery</dhv:label></td>
    <td><input type="text" name="estimatedDelivery" id="estimatedDelivery" value="<%= toHtmlValue(quoteProduct.getEstimatedDelivery()) %>" />
      [<a href="javascript:popLookupSelectSingle('estimatedDelivery','<%= PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG %>','<%= PermissionCategory.LOOKUP_PRODUCT_SHIP_TIME %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="quotes.estimatedDeliveryDate" param="break=<br />">Estimated<br />Delivery Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="modifyProduct" field="estimatedDeliveryDate" timestamp="<%= quoteProduct.getEstimatedDeliveryDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
</table>
<br />
<%-- Quote product options --%>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="product.options">Options</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= quoteProduct.getProductOptionList().size() > 0 %>">
  <tr>
    <td class="empty">
  <%
    Iterator i = quoteProduct.getProductOptionList().iterator();
    while (i.hasNext()) {
      QuoteProductOption option = (QuoteProductOption) i.next();
  %>
    <input type="hidden" name="option_<%= option.getId() %>" value="<%= option.getId() %>"><%= option.getQuoteHtml() %>
  <%
    }
  %>
    </td>
   </tr>
   </dhv:evaluate>
   <dhv:evaluate if="<%= quoteProduct.getProductOptionList().size() == 0 %>">
   <tr>
    <td><dhv:label name="product.noProductOptionsFound">Product options not found</dhv:label></td>
   </tr>
   </dhv:evaluate>
</table>
&nbsp;<br />
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:opener.reopen();self.close();" />
</form>

