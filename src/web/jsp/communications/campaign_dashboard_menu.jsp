<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCampaignId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, campaignId, cancel, download) {
    thisCampaignId = campaignId;
    updateMenu(cancel, download);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCampaign", "down", 0, 0, 170, getHeight("menuCampaignTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contact based on permissions
  function updateMenu(hasCancelPermission, hasDownload){
    if(hasCancelPermission == 0){
      hideSpan('menuCancel');
    }else{
      showSpan('menuCancel');
    }
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignManager.do?command=Details&id=' + thisCampaignId + '&reset=true';
  }
  
  function generateReport() {
    window.location.href='CampaignManager.do?command=ExportReport&id=' + thisCampaignId;
  }
  
  function cancel() {
    confirmForward('CampaignManager.do?command=Cancel&id=' + thisCampaignId + '&notify=true');
  }
</script>
<div id="menuCampaignContainer" class="menu">
  <div id="menuCampaignContent">
    <table id="menuCampaignTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="campaign-campaigns-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-view">
      <tr id="menuReport" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="generateReport()">
        <th>
          <img src="images/icons/stock_export-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Export to Excel
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuCancel" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="cancel()">
        <th>
          <img src="images/icons/stock_stop-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Cancel
        </td>
      </tr>
    </table>
  </div>
</div>
