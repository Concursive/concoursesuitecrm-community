<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisProductId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, productId) {
    thisProductId = productId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuProduct", "down", 0, 0, 170, getHeight("menuProductTable"));
    }

    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href = 'ProductsCatalog.do?command=ViewProductDetails&productId=' + thisProductId + '&moduleId=<%= PermissionCategory.getId() %>';
  }
  
  function modify() {
    window.location.href = 'ProductsCatalog.do?command=ModifyProduct&productId=' + thisProductId + '&moduleId=<%= PermissionCategory.getId() %>&return=list';
  }
  
  function deleteProduct() {
    popURLReturn('ProductsCatalog.do?command=ConfirmDeleteProduct&productId=' + thisProductId + '&moduleId=<%= PermissionCategory.getId() %>&popup=true','ProductsCatalog.do?command=ListAllProducts&moduleId=<%= PermissionCategory.getId() %>','Delete_product','330','250','yes','no');
  }
  
</script>
<div id="menuProductContainer" class="menu">
  <div id="menuProductContent">
    <table id="menuProductTable" class="pulldown" width="170">
      <dhv:permission name="admin-sysconfig-products-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-products-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-products-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:deleteProduct()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
