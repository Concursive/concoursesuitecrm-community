<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTicId = -1;
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, ticId) {
    thisOrgId = orgId;
    thisTicId = ticId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuTic", "down", 0, 0, 170, getHeight("menuTicTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='AccountTickets.do?command=TicketDetails&id=' + thisTicId;
  }
  
  function modify() {
    window.location.href='AccountTickets.do?command=ModifyTicket&id=' + thisTicId + '&return=list';
  }
  
  function deleteTic() {
    popURL('AccountTickets.do?command=ConfirmDelete&orgId=' + thisOrgId + '&id=' + thisTicId + '&popup=true', 'Delete_ticket','320','200','yes','no');
  }
  
</script>
<div id="menuTicContainer" class="menu">
  <div id="menuTicContent">
    <table id="menuTicTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-tickets-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteTic()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
