<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTicketId = -1;
  var thisFromId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, ticketId, formId) {
    thisTicketId = ticketId;
    thisFromId = formId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuTicketForm", "down", 0, 0, 170, getHeight("menuTicketFormTable"));
    }

    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href = 'TroubleTicketMaintenanceNotes.do?command=View&id=' + thisTicketId + '&formId=' + thisFromId;
  }
  
  
  function modify() {
    window.location.href = 'TroubleTicketMaintenanceNotes.do?command=Modify&id=' + thisTicketId + '&formId=' + thisFromId + '&return=list';
  }

  function deleteNote() {
    popURLReturn('TroubleTicketMaintenanceNotes.do?command=ConfirmDelete&id=' + thisTicketId + '&formId=' + thisFromId + '&popup=true','TroubleTicketMaintenanceNotes.do?command=List&id=' + thisTicketId,'Delete_maintenancenote','330','200','yes','no');
  }
  
</script>
<div id="menuTicketFormContainer" class="menu">
  <div id="menuTicketFormContent">
    <table id="menuTicketFormTable" class="pulldown" width="170">
     <dhv:permission name="tickets-maintenance-report-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
     <dhv:permission name="tickets-maintenance-report-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
     <dhv:permission name="tickets-maintenance-report-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:deleteNote()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
