<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTicketId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, ticId) {
    thisTicketId = ticId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuTicket", "down", 0, 0, 170, getHeight("menuTicketTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
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
    <table id="menuTicketTable" class="pulldown" width="170">
      <dhv:permission name="tickets-tickets-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:deleteTicket()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
