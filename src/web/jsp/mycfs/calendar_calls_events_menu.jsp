<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisAccountId = -1;
  var thisContactId = -1;
  var thisCallId = -1;
  var thisView = "";
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayCallMenu(loc, id, accountId, contactId, callId, view) {
    thisAccountId = accountId;
    thisContactId = contactId;
    thisCallId = callId;
    thisView = view;
    updateCallMenu();
    if (!menu_init) {
      menu_init = true;
      initialize_menus();
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contact based on permissions
  function updateCallMenu(){
    if(thisView == 'pending'){
      showSpan('menuCompleteCall');
      showSpan('menuCancelCall');
      showSpan('menuRescheduleCall');
      if(document.getElementById('menuModifyCall') != null){
        hideSpan('menuModifyCall');
      }
    }else{
      hideSpan('menuCompleteCall');
      hideSpan('menuCancelCall');
      hideSpan('menuRescheduleCall');
      if(document.getElementById('menuModifyCall') != null){
        if(thisView != 'cancel'){
          showSpan('menuModifyCall');
        }else{
          hideSpan('menuModifyCall');
        }
      }
    }
    
    if(thisAccountId > -1){
      showSpan('menuAccountCall');
    }else{
      hideSpan('menuAccountCall');
    }
  }
  
  function completeCall() {
    var url = 'CalendarCalls.do?command=Complete&contactId=' + thisContactId + '&id=' + thisCallId + '&popup=true';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    popURL(url, 'CONFIRM_DELETE','500','600','yes','yes');
  }
  
  function modifyCall() {
    var url = 'CalendarCalls.do?command=Modify&contactId=' + thisContactId + '&id=' + thisCallId + '&popup=true';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    popURL(url, 'CONFIRM_DELETE','500','600','yes','yes');
  }
  
  function deleteCall() {
  var url = 'CalendarCalls.do?command=Cancel&contactId=' + thisContactId + '&id=' + thisCallId + '&action=cancel&popup=true';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    popURL(url, 'CONFIRM_DELETE','500','600','yes','no');
  }
  
  function showContactCall() {
    popURL('ExternalContacts.do?command=ContactDetails&id=' + thisContactId + '&popup=true&viewOnly=true','Details','650','500','yes','yes');
  }
  
  function showContactHistory(){
    popURL('CalendarCalls.do?command=View&contactId=' + thisContactId + '&source=calendar&popup=true','CONTACT_HISTORY','650','500','yes','yes');
  }
  function goToContactCall() {
    if(thisAccountId != -1){
      window.parent.location.href = 'Contacts.do?command=Details&id=' + thisContactId;
    }else{
      window.parent.location.href = 'ExternalContacts.do?command=ContactDetails&id=' + thisContactId;
    }
  }
  
  function goToAccountCall() {
    window.parent.location.href = 'Accounts.do?command=Details&orgId=' + thisAccountId;
  }
</script>
<div id="menuCallContainer" class="menu">
  <div id="menuCallContent">
    <table id="menuCallTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-contacts-calls-edit">
      <tr id="menuCompleteCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="completeCall()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Complete Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-edit">
      <tr id="menuRescheduleCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="modifyCall()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-edit">
      <tr id="menuModifyCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="modifyCall()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-delete">
      <tr id="menuCancelCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteCall()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Cancel Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="showContactCall()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Contact Details
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="showContactHistory()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Contact History
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goToContactCall()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go To Contact
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-view">
      <tr id="menuAccountCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goToAccountCall()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go To Account
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
