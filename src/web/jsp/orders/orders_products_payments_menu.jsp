<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisPaymentId = -1;
  var payment_menu_init = false;
  //Set the action parameters for clicked item
  function displayPaymentsMenu(id,paymentId) {
    thisPaymentId = paymentId;
    if (!payment_menu_init) {
      payment_menu_init = true;
      new ypSlideOutMenu(id, "down", 0, 0, 170, getHeight(id+"Table"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function paymentDetails() {
    window.location.href='OrdersPayments.do?command=Details&paymentId=' + thisPaymentId;
  }
  
  function paymentModify() {
    popURL('OrdersPayments.do?command=Modify&paymentId='+thisPaymentId+'&popup=true','OrdersPayments','500','200','yes','yes');
  }
  
</script>
<div id="menuOrderPaymentContainer" class="menu">
  <div id="menuOrderPaymentContent">
    <table id="menuOrderPaymentTable" class="pulldown" width="170">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:paymentDetails()">View Details</a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:paymentModify()">Modify</a>
        </td>
      </tr>
    </table>
  </div>
</div>
