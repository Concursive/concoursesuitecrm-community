<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, status) {
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
    return ypSlideOutMenu.displayMenu(id);
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
    <table id="menuAccountTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-edit">
      <tr>
        <td>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td id="menuArchiveAccount">
          <a href="javascript:archive()">Archive</a>
        </td>
        <td id="menuReEnableAccount">
          <a href="javascript:enable()">Un-Archive</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:deleteAccount()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
