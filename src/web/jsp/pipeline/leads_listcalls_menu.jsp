<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisHeaderId = -1;
  var thisCallId = -1;
  var thisView = "";
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, headerId, callId, view) {
    thisHeaderId = headerId;
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
    var url = 'LeadsCalls.do?command=Details&headerId=' + thisHeaderId + '&id=' + thisCallId + '<%= addLinkParams(request, "viewSource") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function complete() {
    var url = 'LeadsCalls.do?command=Complete&headerId=' + thisHeaderId + '&id=' + thisCallId + '&return=list<%= addLinkParams(request, "viewSource") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function modify() {
    var url = 'LeadsCalls.do?command=Modify&headerId=' + thisHeaderId + '&id=' + thisCallId + '&return=list<%= addLinkParams(request, "viewSource") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  function forward() {
    var url ='LeadsCallsForward.do?command=ForwardCall&headerId=' + thisHeaderId + '&id=' + thisCallId + '&return=list<%= addLinkParams(request, "viewSource") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
  
  
  function deleteCall() {
  var url = 'LeadsCalls.do?command=Cancel&headerId=' + thisHeaderId + '&id=' + thisCallId + '&return=list&action=cancel<%= addLinkParams(request, "viewSource") %>';
    if(thisView == 'pending'){
      url += '&view=pending';
    }
    window.location.href=url;
  }
</script>
<div id="menuCallContainer" class="menu">
  <div id="menuCallContent">
    <table id="menuCallTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="pipeline-opportunities-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit">
      <tr id="menuComplete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="complete()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Complete Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit">
      <tr id="menuReschedule" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit">
      <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-delete">
      <tr id="menuCancel" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCall()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Cancel Activity
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="forward()">
        <th>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Forward
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
