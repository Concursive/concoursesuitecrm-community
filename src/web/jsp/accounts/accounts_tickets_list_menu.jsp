<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTicId = -1;
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, ticId) {
    thisOrgId = orgId;
    thisTicId = ticId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuTic", "down", 0, 0, 170, getHeight("menuTicTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    <table id="menuTicTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-tickets-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteTic()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
