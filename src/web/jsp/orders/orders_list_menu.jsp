<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id,orderId) {
    thisId = orderId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOrders", "down", 0, 0, 170, getHeight("menuOrdersTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href='Orders.do?command=Details&id=' + thisId;
  }
  
  function modify() {
    popURL('Orders.do?command=ModifyStatus&id='+thisId+'&popup=true','OrderStatus','300','200','yes','yes');
  }
  
</script>
<div id="menuOrdersContainer" class="menu">
  <div id="menuOrdersContent">
    <table id="menuOrdersTable" class="pulldown" width="170">
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
          <a href="javascript:modify()">Modify Status</a>
        </td>
      </tr>
    </table>
  </div>
</div>
