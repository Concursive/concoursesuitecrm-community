<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCampaignId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, campaignId, fileId) {
    thisCampaignId = campaignId;
    thisFileId = fileId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignDocuments.do?command=Details&id=' + thisCampaignId + '&fid=' + thisFileId;
  }
  
  function modify() {
    window.location.href='CampaignDocuments.do?command=Modify&fid=' + thisFileId +  
   '&id=' + thisCampaignId;
  }
  
  function download() {
    window.location.href='CampaignDocuments.do?command=Download&id=' + thisCampaignId + '&fid=' + thisFileId;
  }
  
  function deleteFile() {
    confirmDelete('CampaignDocuments.do?command=Delete&fid=' + thisFileId + '&id=' + thisCampaignId);
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170">
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
      <dhv:permission name="campaign-campaigns-view">
      <tr>
        <td>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:download()">Download</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-edit">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteFile()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
