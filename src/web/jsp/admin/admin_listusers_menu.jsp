<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisUserId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, userId, status) {
    thisUserId = userId;
    if (!menu_init) {
      menu_init = true;
      if(status == 0){
        hideSpan('menuDisable');
        showSpan('menuEnable');
      }else if(status == 1){
        hideSpan('menuEnable');
        showSpan('menuDisable');
      }else{
        hideSpan('menuEnable');
        hideSpan('menuDisable');
      }
      new ypSlideOutMenu("menuUser", "down", 0, 0, 170, getHeight("menuUserTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href = 'Users.do?command=UserDetails&id=' + thisUserId;
  }
  
  function modify() {
    window.location.href = 'Users.do?command=ModifyUser&id=' + thisUserId + '&return=list';
  }
  
  function disable() {
    window.location.href = 'Users.do?command=DisableUserConfirm&id=' + thisUserId + '&return=list';
  }
  
  function enable() {
    window.location.href = 'Users.do?command=EnableUser&id=' + thisUserId + '&return=list';
  }
</script>
<div id="menuUserContainer" class="menu">
  <div id="menuUserContent">
    <table id="menuUserTable" class="pulldown" width="170">
      <dhv:permission name="admin-users-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-users-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <tr>
        <td>
          &nbsp;
        </td>
        <td id="menuDisable">
          <a href="javascript:disable()">Disable</a>
        </td>
        <td id="menuEnable">
          <a href="javascript:enable()">Enable</a>
        </td>
      </tr>
    </table>
  </div>
</div>
