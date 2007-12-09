<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTaskId = -1;
  var thisTypeId = -1;
  var thisTicketId = -1;
  var thisContactId = -1;
  var thisOwnerId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, typeId, taskId, ticketId, contactId, ownerId, hasAuthority) {
    thisTaskId = taskId;
    thisTypeId = typeId;
    thisTicketId = ticketId;
    thisContactId = contactId;
    thisOwnerId = ownerId;
    updateTaskMenu(hasAuthority);
    document.getElementById('ownerid').value = thisOwnerId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuTask", "down", 0, 0, 170, getHeight("menuTaskTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateTaskMenu(hasAuthority){
    if (hasAuthority == 'true') {
      showSpan('menuTaskModify');
      showSpan('menuTaskReassign');
      showSpan('menuTaskDelete');
    }else{
      hideSpan('menuTaskModify');
      hideSpan('menuTaskReassign');
      showSpan('menuTaskDelete');
    }
  }
  
  //Menu link functions
  function details() {
    window.location.href='MyTasks.do?command=Details&id=' + thisTaskId;
  }
  
  function modify() {
    window.location.href='MyTasks.do?command=Modify&id=' + thisTaskId;
  }
  
  function assignTask(ownerId) {
    var url = 'MyTasks.do?command=ReassignTask&id='+ thisTaskId + '&ownerId=' + ownerId + '&return=myhomepage';
    window.frames['server_commands'].location.href = url;
  }

  function reassign() {
    if (thisTicketId != '-1') {
      popContactsListSingle('ownerid','changeowner', 'listView=employees&tasks=true&hiddensource=tasks&usersOnly=true&ticketId='+ thisTicketId +'&reset=true');
    } else if (thisContactId != '-1') {
        popContactsListSingle('ownerid','changeowner', 'listView=employees&tasks=true&hiddensource=tasks&usersOnly=true&mySiteOnly=true&siteIdContact='+thisContactId+'&reset=true');
    } else {
      popContactsListSingle('ownerid','changeowner', 'listView=employees&tasks=true&hiddensource=tasks&usersOnly=true<%= User.getUserRecord().getSiteId() == -1? "&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&reset=true');
    }
  }

  function forward() {
  window.location.href='MyTasksForward.do?command=ForwardMessage&forwardType=' + thisTypeId + '&id=' + thisTaskId;
  }
  
  function deleteTask() {
    <% if (TaskList.getTasksAssignedByUser() != -1) { %>
      alert(label("cannot.delete.task.reason","Cannot delete the selected Task as it has been already assigned to another User."));
    <% } else { %>
      popURLReturn('MyTasks.do?command=ConfirmDelete&id=' + thisTaskId + '&popup=true','MyTasks.do?command=ListTasks', 'Delete_task','320','200','yes','no');
    <% } %>
  }
</script>
<div id="menuTaskContainer" class="menu">
  <div id="menuTaskContent">
    <table id="menuTaskTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="myhomepage-tasks-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-tasks-edit">
      <tr id="menuTaskModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-tasks-edit">
      <tr id="menuTaskReassign" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="reassign()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="actionPlan.reassign">Reassign</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-tasks-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="forward()">
        <th>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-tasks-delete">
      <tr id="menuTaskDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteTask()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
