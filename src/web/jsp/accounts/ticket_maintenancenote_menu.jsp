<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTicketId = -1;
  var thisFromId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, ticketId, formId) {
    thisTicketId = ticketId;
    thisFromId = formId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuTicketForm", "down", 0, 0, 170, getHeight("menuTicketFormTable"));
    }

    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'AccountTicketMaintenanceNotes.do?command=View&id=' + thisTicketId + '&formId=' + thisFromId;
  }
  
  
  function modify() {
    window.location.href = 'AccountTicketMaintenanceNotes.do?command=Modify&id=' + thisTicketId + '&formId=' + thisFromId + '&return=list';
  }

  function deleteNote() {
    popURLReturn('AccountTicketMaintenanceNotes.do?command=ConfirmDelete&id=' + thisTicketId + '&formId=' + thisFromId + '&popup=true','AccountTicketMaintenanceNotes.do?command=List&id=' + thisTicketId,'Delete_maintenancenote','330','200','yes','no');
  }
  
</script>
<div id="menuTicketFormContainer" class="menu">
  <div id="menuTicketFormContent">
    <table id="menuTicketFormTable" class="pulldown" width="170" cellspacing="0">
     <dhv:permission name="accounts-accounts-tickets-maintenance-report-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
     <dhv:permission name="accounts-accounts-tickets-maintenance-report-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
     <dhv:permission name="accounts-accounts-tickets-maintenance-report-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteNote()">
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
