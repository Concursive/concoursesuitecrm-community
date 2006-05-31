<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCampaignId = -1;
  var thisGroupId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, campaignId, groupId, canDelete) {
    thisCampaignId = campaignId;
    thisGroupId = groupId;
    updateMenu(canDelete);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuUserGroup", "down", 0, 0, 170, getHeight("menuUserGroupTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function updateMenu(canDelete) {
    if (canDelete == 'true') {
      showSpan('deleteGroupMap');
    } else {
      hideSpan('deleteGroupMap');
    }
  }
  
  function deleteGroupMap() {
    if (confirm(label('','Do you really want to remove access to the active campaign for this user group?'))) {
      var url = 'CampaignUserGroups.do?command=Delete&id=' + thisCampaignId+'&userGroupId='+thisGroupId;
      window.location.href= url;
    }
  }
</script>
<div id="menuUserGroupContainer" class="menu">
  <div id="menuUserGroupContent">
    <table id="menuUserGroupTable" class="pulldown" width="150" cellspacing="0">
      <dhv:permission name="campaign-dashboard-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteGroupMap();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
