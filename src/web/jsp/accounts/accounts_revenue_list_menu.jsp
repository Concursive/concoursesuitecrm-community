<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisRevenueId = -1;
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, revId) {
    thisOrgId = orgId;
    thisRevenueId = revId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuRevenue", "down", 0, 0, 170, getHeight("menuRevenueTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='RevenueManager.do?command=Details&id=' + thisRevenueId;
  }
  
  function modify() {
    window.location.href='RevenueManager.do?command=Modify&orgId=' + thisOrgId + '&id=' + thisRevenueId + '&return=list';
  }
  
  function deleteRevenue() {
    confirmDelete('RevenueManager.do?command=Delete&orgId=' + thisOrgId + '&id=' + thisRevenueId);
  }
</script>
<div id="menuRevenueContainer" class="menu">
  <div id="menuRevenueContent">
    <table id="menuRevenueTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-revenue-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-revenue-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-revenue-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteRevenue()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
