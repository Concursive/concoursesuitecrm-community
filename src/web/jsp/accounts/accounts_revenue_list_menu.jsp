<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisRevenueId = -1;
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, revId) {
    thisOrgId = orgId;
    thisRevenueId = revId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuRevenue", "down", 0, 0, 170, getHeight("menuRevenueTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    <table id="menuRevenueTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-revenue-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-revenue-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-revenue-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteRevenue()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
