<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisGroupId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, groupId) {
    thisGroupId = groupId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuGroup", "down", 0, 0, 170, getHeight("menuGroupTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignManagerGroup.do?command=Details&id=' + thisGroupId;
  }
  
  function modify() {
    window.location.href='CampaignManagerGroup.do?command=Modify&id=' + thisGroupId + '&return=list';
  }
  
  function deleteGroup() {
  popURLReturn('CampaignManagerGroup.do?command=ConfirmDelete&id=' + thisGroupId + '&popup=true','CampaignManagerGroup.do?command=View', 'Delete_group','330','200','yes','no');
  }
</script>
<div id="menuGroupContainer" class="menu">
  <div id="menuGroupContent">
    <table id="menuGroupTable" class="pulldown" width="170">
      <dhv:permission name="campaign-campaigns-groups-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-groups-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-groups-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteGroup()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
