<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCampaignId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, campaignId, cancel, download, restart, candelete) {
    thisCampaignId = campaignId;
    updateMenu(cancel, download, restart, candelete);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCampaign", "down", 0, 0, 170, getHeight("menuCampaignTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contact based on permissions
  function updateMenu(hasCancelPermission, hasDownload, restart, candelete){
    if(hasCancelPermission == 'false'){
      hideSpan('menuCancel');
    } else {
      showSpan('menuCancel');
    }
    if (restart == 'false') {
      hideSpan('menuRestart');
    } else {
      showSpan('menuRestart');
    }
    if (candelete == 'false') {
      hideSpan('menuDelete');
    } else {
      showSpan('menuDelete');
    }
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignManager.do?command=Details&id=' + thisCampaignId + '&reset=true';
  }
  
  function generateReport() {
    window.location.href='CampaignManager.do?command=ExportReport&id=' + thisCampaignId;
  }
  
  function restartCampaign() {
    if (confirm(label("confirm.restart.campaign", "Are you sure you want to restart this campaign?"))) {
      window.location.href='CampaignManager.do?command=Restart&id='+ thisCampaignId;
    }
  }
  
  function deleteCampaign() {
    popURLReturn('CampaignManager.do?command=ConfirmDelete&id=' + thisCampaignId,'CampaignManager.do?command=Dashboard','Delete_campaign','330','200','yes','no');
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
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-edit">
      <tr id="menuRestart" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="restartCampaign();">
        <th>
          <img src="images/icons/stock_toggle-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="campaigns.restartCampaign">Restart Campaign</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-delete">
      <tr id="menuDelete" nmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCampaign()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-view">
      <tr id="menuReport" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="generateReport()">
        <th>
          <img src="images/icons/stock_export-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="campaign.exportToExcel">Export to Excel</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuCancel" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="cancel()">
        <th>
          <img src="images/icons/stock_stop-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.cancel">Cancel</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
