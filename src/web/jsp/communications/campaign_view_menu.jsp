<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCampaignId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, campaignId) {
    thisCampaignId = campaignId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCampaign", "down", 0, 0, 170, getHeight("menuCampaignTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignManager.do?command=ViewDetails&id=' + thisCampaignId + '&reset=true';
  }
  
  function modify() {
    window.location.href='CampaignManager.do?command=Modify&id=' + thisCampaignId + '&return=list'
  }
  
  function deleteCampaign() {
    confirmDelete('CampaignManager.do?command=Delete&id=' + thisCampaignId);
  }
</script>
<div id="menuCampaignContainer" class="menu">
  <div id="menuCampaignContent">
    <table id="menuCampaignTable" class="pulldown" width="170">
      <dhv:permission name="campaign-campaigns-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteCampaign()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
