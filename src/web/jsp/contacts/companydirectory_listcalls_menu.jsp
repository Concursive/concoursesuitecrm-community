<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var thisCallId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, callId, editPermission, deletePermission) {
    thisContactId = contactId;
    thisCallId = callId;
    updateMenu(editPermission, deletePermission);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCall", "down", 0, 0, 170, getHeight("menuCallTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contact based on permissions
  function updateMenu(hasEditPermission, hasDeletePermission){
    if(hasEditPermission == 0){
      hideSpan('menuEdit');
    }else{
      showSpan('menuEdit');
    }
    
    if(hasDeletePermission == 0){
      hideSpan('menuDelete');
    }else{
      showSpan('menuDelete');
    }
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'ExternalContactsCalls.do?command=Details&id=' + thisCallId + '&contactId=' + thisContactId + '<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function modify() {
      window.location.href = 'ExternalContactsCalls.do?command=Modify&id=' + thisCallId + '&contactId=' + thisContactId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function forward() {
    window.location.href = 'ExternalContactsCallsForward.do?command=ForwardCall&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function deleteCall() {
    popURL('ExternalContactsCalls.do?command=ConfirmDelete&id=' + thisCallId + '&contactId=' + thisContactId + '&popup=true<%= addLinkParams(request, "popupType|actionId") %>', 'CONFIRM_DELETE','320','200','yes','no');
  }
  
</script>
<div id="menuCallContainer" class="menu">
  <div id="menuCallContent">
    <table id="menuCallTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-external_contacts-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      <dhv:permission name="contacts-external_contacts-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="forward()">
        <th>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Forward
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCall()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
    </table>
  </div>
</div>
