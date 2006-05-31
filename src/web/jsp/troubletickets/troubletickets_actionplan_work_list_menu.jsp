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
  var thisWorkId = -1;
  var thisSiteId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, workId, managerId, status, siteId) {
    thisWorkId = workId;
    thisSiteId = siteId;
    var userId = '<%= User.getUserRecord().getId() %>';
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuActionPlan", "down", 0, 0, 170, getHeight("menuActionPlanTable"));
    }
    if (userId == managerId) {
      //User logged in is the plan's manager too
      showSpan('menuReassignActionPlan');
      showSpan('menuDeleteActionPlan');
      showSpan('menuRevertActionPlan');
      if (status == 0) {
        hideSpan('menuArchiveActionPlan');
        showSpan('menuReEnableActionPlan');
      } else if (status == 1) {
        hideSpan('menuReEnableActionPlan');
        showSpan('menuArchiveActionPlan');
      } else {
        hideSpan('menuReEnableActionPlan');
        hideSpan('menuArchiveActionPlan');
      }
    } else {
      hideSpan('menuReassignActionPlan');
      hideSpan('menuDeleteActionPlan');
      hideSpan('menuRevertActionPlan');
      hideSpan('menuReEnableActionPlan');
      hideSpan('menuArchiveActionPlan');
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'TroubleTicketActionPlans.do?command=Details&ticketId=<%= ticket.getId() %>&actionPlanId=' + thisWorkId;
  }
  
  function reassign() {
    var URL = 'ContactsList.do?command=ContactList&listView=employees&usersOnly=true&siteId='+thisSiteId+'&searchcodePermission=sales-leads-edit,tickets-action-plans-view,accounts-action-plans-view,myhomepage-action-plans-view&listType=single&flushtemplist=true&hiddensource=reassignplan&actionplan=true&actionPlanWork=' + thisWorkId;
    popURL(URL, 'Action_Plan', 700, 425, 'yes', 'yes');
  }
  
  function continueReassignPlan(userId, actionPlanWork) {
    window.location.href = "TroubleTicketActionPlans.do?command=Reassign&ticketId=<%= ticket.getId() %>&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=list";
  }

  function reviewNotes() {
    var URL = 'TroubleTicketActionPlans.do?command=ViewNotes&ticketId=<%= ticket.getId() %>&planWorkId=' + thisWorkId;
    popURL(URL, 'Action_Plan', 700, 425, 'yes', 'yes');
  }
  
  function enable() {
    window.location.href = 'TroubleTicketActionPlans.do?command=Enable&ticketId=<%= ticket.getId() %>&actionPlanId=' + thisWorkId + '&enabled=true';
  }
  
  function archive() {
    window.location.href = 'TroubleTicketActionPlans.do?command=Enable&ticketId=<%= ticket.getId() %>&actionPlanId=' + thisWorkId + '&enabled=false';
  }
  
  function deletePlan() {
    confirmDelete('TroubleTicketActionPlans.do?command=Delete&ticketId=<%= ticket.getId() %>&planId=' + thisWorkId);
  }
</script>
<div id="menuActionPlanContainer" class="menu">
  <div id="menuActionPlanContent">
    <table id="menuActionPlanTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="tickets-action-plans-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
           <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="actionPlan.viewActionPlan">View Action Plan</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-action-plans-edit">
      <tr id="menuReassignActionPlan" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="reassign()">
        <th>
           <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="actionPlan.reassign">Reassign</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-action-plans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="reviewNotes()">
        <th>
          <img src="images/icons/stock_new_bullet-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="actionPlan.reviewNotes">Review Notes</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-action-plans-edit">
      <tr id="menuArchiveActionPlan" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
           <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-action-plans-edit">
      <tr id="menuReEnableActionPlan" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_list_menu.UnArchive">Un-Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-action-plans-delete">
      <tr id="menuDeleteActionPlan" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deletePlan()">
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
