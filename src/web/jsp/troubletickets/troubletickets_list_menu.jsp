<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTicketId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, ticId) {
    thisTicketId = ticId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuTicket", "down", 0, 0, 170, getHeight("menuTicketTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'TroubleTickets.do?command=Details&id=' + thisTicketId;
  }
  
  
  function modify() {
    window.location.href = 'TroubleTickets.do?command=Modify&id=' + thisTicketId + '&return=list';
  }
  
  function deleteTicket() {
    popURL('TroubleTickets.do?command=ConfirmDelete&id=' + thisTicketId + '&popup=true', 'Delete_ticket','320','200','yes','no');
  }
</script>
<div id="menuTicketContainer" class="menu">
  <div id="menuTicketContent">
    <table id="menuTicketTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="tickets-tickets-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteTicket()">
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
