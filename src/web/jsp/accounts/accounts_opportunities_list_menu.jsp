<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisHeaderId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, headerId) {
    thisOrgId = orgId;
    thisHeaderId = headerId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href='Opportunities.do?command=Details&headerId=' + thisHeaderId + '&orgId=' + thisOrgId + '&reset=true';
  }
  
  function modify() {
    window.location.href='Opportunities.do?command=Modify&headerId=' + thisHeaderId + '&orgId=' + thisOrgId + '&return=list';
  }
  
  function deleteOpp() {
    popURLReturn('Opportunities.do?command=ConfirmDelete&orgId=' + thisOrgId + '&headerId=' + thisHeaderId,'Opportunities.do?command=View&orgId=' + thisOrgId + '&popup=true', 'Delete_opp','320','200','yes','no');
  }
</script>
<div id="menuOppContainer" class="menu">
  <div id="menuOppContent">
    <table id="menuOppTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-opportunities-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-opportunities-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Rename</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-opportunities-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteOpp()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
