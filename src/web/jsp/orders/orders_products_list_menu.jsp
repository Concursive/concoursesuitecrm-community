<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id,orderProductId) {
    thisId = orderProductId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu(id, "down", 0, 0, 170, getHeight(id+"Table"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href='OrdersProducts.do?command=Details&productId=' + thisId;
  }
  
  function modify() {
    popURL('OrdersProducts.do?command=Modify&productId='+thisId+'&popup=true','OrdersProducts','300','200','yes','yes');
  }
  
  function download() {
    popURL('OrdersProducts.do?command=DisplayCustomerProduct&productId='+thisId+'&popup=true','CustomerProduct','600','300','yes','yes');
  }
</script>
<div id="menuOrderProductsContainer" class="menu">
  <div id="menuOrderProductsContent">
    <table id="menuOrderProductsTable" class="pulldown" width="170">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:download()"><dhv:label name="button.download">Download</dhv:label></a>
        </td>
      </tr>
    </table>
  </div>
</div>
