<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var thisCallId = -1;
  var thisView = "";
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, callId, view) {
    thisContactId = contactId;
    thisCallId = callId;
    thisView = view;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCall", "down", 0, 0, 170, getHeight("menuCallTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contact based on permissions
  function updateMenu(){
    if(thisView == 'pending'){
      showSpan('menuComplete');
      showSpan('menuCancel');
      showSpan('menuReschedule');
      hideSpan('menuModify');
    }else{
      hideSpan('menuComplete');
      hideSpan('menuCancel');
      hideSpan('menuReschedule');
      if(thisView != 'cancel'){
        showSpan('menuModify');
      }else{
        hideSpan('menuModify');
       }
      }
    }
  
  //Menu link functions
  function details() {
    var url = 'ExternalContactsCalls.do?command=Details&contactId=' + thisContactId + '&id=' + thisCallId + '<%= addLinkParams(request, "popup|popupType|actionId") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function complete() {
    var url = 'ExternalContactsCalls.do?command=Complete&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function modify() {
    var url = 'ExternalContactsCalls.do?command=Modify&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function forward() {
    var url ='ExternalContactsCallsForward.do?command=ForwardCall&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  
  function deleteCall() {
  var url = 'ExternalContactsCalls.do?command=Cancel&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list&action=cancel<%= addLinkParams(request, "popup|popupType|actionId") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
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
      <dhv:permission name="contacts-external_contacts-calls-edit">
      <tr id="menuComplete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="complete()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Complete Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-calls-edit">
      <tr id="menuReschedule" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-completed-calls-edit">
      <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-calls-delete">
      <tr id="menuCancel" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCall()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Cancel Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="forward()">
        <td>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          Forward
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
