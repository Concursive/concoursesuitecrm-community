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
    var url = 'AccountContactsCalls.do?command=Details&contactId=' + thisContactId + '&id=' + thisCallId + 
   '&trailSource=accounts';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function complete() {
    var url = 'AccountContactsCalls.do?command=Complete&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list' + 
   '&trailSource=accounts';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function modify() {
    var url = 'AccountContactsCalls.do?command=Modify&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list' + 
   '&trailSource=accounts';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function forward() {
    var url ='AccountContactsCalls.do?command=ForwardCall&contactId=' + thisContactId + '&id=' + thisCallId + 
   '&trailSource=accounts&return=list';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function deleteCall() {
  var url = 'AccountContactsCalls.do?command=Cancel&contactId=' + thisContactId + '&id=' + thisCallId + '&return=list&action=cancel&trailSource=accounts';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
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
      <tr id="menuComplete" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="complete()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.CompleteActivity">Complete Activity</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-edit">
      <tr id="menuReschedule" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ModifyActivity">Modify Activity</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-edit">
      <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ModifyActivity">Modify Activity</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-delete">
      <tr id="menuCancel" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteCall()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.CancelActivity">Cancel Activity</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="forward()">
        <th>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
        <dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
