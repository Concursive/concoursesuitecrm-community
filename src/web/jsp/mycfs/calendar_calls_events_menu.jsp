<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisAccountId = -1;
  var thisContactId = -1;
  var thisCallId = -1;
  var thisView = "";
  var menu_init = false;
  var thisOwner = false;
  //Set the action parameters for clicked item
  function displayCallMenu(loc, id, accountId, contactId, callId, view, isOwner) {
    thisAccountId = accountId;
    thisContactId = contactId;
    thisCallId = callId;
    thisOwner = isOwner;
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
      showSpan('menuViewAccountCall');
    }else{
      hideSpan('menuAccountCall');
      hideSpan('menuViewAccountCall');
    }
     if(thisContactId > -1){
      showSpan('menuGoToContactCall');
      showSpan('menuViewContactHistory');
      showSpan('menuViewContactDetail');
    }else{
      hideSpan('menuGoToContactCall');
      hideSpan('menuViewContactHistory');
      hideSpan('menuViewContactDetail');
    }
    if (!thisOwner){
      hideSpan('menuModifyCall');
      hideSpan('menuCompleteCall');
      hideSpan('menuCancelCall');
      hideSpan('menuRescheduleCall');
    }
  }

  function details() {
    var url = 'CalendarCalls.do?command=Details&contactId=' + thisContactId + '&id=' + thisCallId +'&orgId=' + thisAccountId+ '&popup=true';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    popURL(url, 'CONFIRM_DELETE','500','600','yes','yes');
  }
  
  function completeCall() {
    var url = 'CalendarCalls.do?command=Complete&contactId=' + thisContactId + '&id=' + thisCallId +'&orgId=' + thisAccountId + '&popup=true';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    popURL(url, 'CONFIRM_DELETE','630','425','yes','yes');
  }
  
  function modifyCall() {
    var url = 'CalendarCalls.do?command=Modify&contactId=' + thisContactId + '&id=' + thisCallId +'&orgId=' + thisAccountId+ '&popup=true';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    popURL(url, 'CONFIRM_DELETE','630','425','yes','yes');
  }
  
  function deleteCall() {
  var url = 'CalendarCalls.do?command=Cancel&contactId=' + thisContactId + '&id=' + thisCallId+'&orgId=' + thisAccountId + '&action=cancel&popup=true';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    popURL(url, 'CONFIRM_DELETE','630','425','yes','no');
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

  function viewAccountCall() {
    popURL('Accounts.do?command=Details&orgId=' + thisAccountId + '&popup=true&viewOnly=true','Details','650','500','yes','yes');
  }

</script>
<div id="menuCallContainer" class="menu">
  <div id="menuCallContent">
    <table id="menuCallTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-contacts-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
        <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-edit">
      <tr id="menuCompleteCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="completeCall()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.CompleteActivity">Complete Activity</dhv:label>
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
<dhv:label name="contact.call.modifyActivity">Modify  Activity</dhv:label>
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
<dhv:label name="contact.call.modifyActivity">Modify  Activity</dhv:label>
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
          <dhv:label name="accounts.accounts_calls_list_menu.CancelActivity">Cancel Activity</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-view">
      <tr id="menuViewContactDetail" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="showContactCall()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="calendar.viewContactDetails">View Contact Details</dhv:label>
        </td>
      </tr>
      <tr id="menuViewContactHistory" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="showContactHistory()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="calendar.viewContactHistory">View Contact History</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-view">
      <tr id="menuGoToContactCall"  onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goToContactCall()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="calendar.goToContact">Go To Contact</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-view">
      <tr id="menuViewAccountCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewAccountCall()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="calendar.viewAccount">View Account</dhv:label>
        </td>
      </tr>
      <tr id="menuAccountCall" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goToAccountCall()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="calendar.goToAccount">Go To Account</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
