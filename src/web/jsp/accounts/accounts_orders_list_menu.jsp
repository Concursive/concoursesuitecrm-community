<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisOrderId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, contactId) {
    thisOrgId = orgId;
    thisOrderId = contactId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOrder", "down", 0, 0, 170, getHeight("menuOrderTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href='AccountOrders.do?command=Details&id=' + thisOrderId;
  }
  
  function modify() {
    popURL('AccountOrders.do?command=ModifyStatus&id='+thisOrderId+'&popup=true','OrderStatus','300','200','yes','yes');
  }
  
</script>
<div id="menuOrderContainer" class="menu">
  <div id="menuOrderContent">
    <table id="menuOrderTable" class="pulldown" width="170">
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
    </table>
  </div>
</div>
