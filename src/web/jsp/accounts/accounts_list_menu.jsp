<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, status) {
    thisOrgId = orgId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuAccount", "down", 0, 0, 170, getHeight("menuAccountTable"));
    }
<dhv:permission name="accounts-accounts-edit">
    if(status == 0){
      hideSpan('menuArchiveAccount');
      showSpan('menuReEnableAccount');
    }else if(status == 1){
      hideSpan('menuReEnableAccount');
      showSpan('menuArchiveAccount');
    }else{
      hideSpan('menuReEnableAccount');
      hideSpan('menuArchiveAccount');
    }
</dhv:permission>
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'Accounts.do?command=Details&orgId=' + thisOrgId;
  }
  
  
  function modify() {
    window.location.href = 'Accounts.do?command=Modify&orgId=' + thisOrgId + '&return=list';
  }
  
  function enable() {
    window.location.href = 'Accounts.do?command=Enable&orgId=' + thisOrgId + '&return=list';
  }
  
  function archive() {
    window.location.href = 'Accounts.do?command=Delete&orgId=' + thisOrgId + '&action=disable&return=list';
  }
  
  function deleteAccount() {
    popURLReturn('Accounts.do?command=ConfirmDelete&id=' + thisOrgId+ '&popup=true','Accounts.do?command=Search', 'Delete_account','330','200','yes','no');
  }
</script>
<div id="menuAccountContainer" class="menu">
  <div id="menuAccountContent">
    <table id="menuAccountTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-edit">
      <tr id="menuArchiveAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Archive
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-edit">
      <tr id="menuReEnableAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Un-Archive
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteAccount()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
