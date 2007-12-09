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
<script language="javascript">
  var thisProjectId = -1;
  var hasNews = 1;
  var hasDiscussion = 1;
  var hasDocuments = 1;
  var hasLists = 1;
  var hasPlan = 1;
  var hasTickets = 1;
  var hasTeam = 1;
  var hasDetails = 1;
  var hasPermissions = 1;

  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, projectId, news, discussion, documents, lists, plan, tickets, team, details, permissions) {
    thisProjectId = projectId;
    hasNews = news;
    hasDiscussion = discussion;
    hasDocuments = documents;
    hasLists = lists;
    hasPlan = plan;
    hasTickets = tickets;
    hasTeam = team;
    hasDetails = details;
    hasPermissions = permissions;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuItem", "down", 0, 0, 170, getHeight("menuItemTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Update menu for this Contact based on permissions
  function updateMenu(){
    hideSpan('menuNews');
    hideSpan('menuDiscussion');
    hideSpan('menuDocuments');
    hideSpan('menuLists');
    hideSpan('menuPlan');
    hideSpan('menuTickets');
    hideSpan('menuTeam');
    hideSpan('menuDetails');
    hideSpan('menuPermissions');
    <%--
    if (hasNews == 1) { showSpan('menuNews'); } else { hideSpan('menuNews'); }
    if (hasDiscussion == 1) { showSpan('menuDiscussion'); } else { hideSpan('menuDiscussion'); }
    if (hasDocuments == 1) { showSpan('menuDocuments'); } else { hideSpan('menuDocuments'); }
    if (hasLists == 1) { showSpan('menuLists'); } else { hideSpan('menuLists'); }
    if (hasPlan == 1) { showSpan('menuPlan'); } else { hideSpan('menuPlan'); }
    if (hasTickets == 1) { showSpan('menuTickets'); } else { hideSpan('menuTickets'); }
    if (hasTeam == 1) { showSpan('menuTeam'); } else { hideSpan('menuTeam'); }
    if (hasDetails == 1) { showSpan('menuDetails'); } else { hideSpan('menuDetails'); }
    if (hasPermissions == 1) { showSpan('menuPermissions'); } else { hideSpan('menuPermissions'); }
    --%>
  }

  //Menu link functions
  function goProject() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&pid=' + thisProjectId;
  }
  function goNews() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=News&pid=' + thisProjectId;
  }
  function goDiscussion() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=' + thisProjectId;
  }
  function goDocuments() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=' + thisProjectId;
  }
  function goLists() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=' + thisProjectId;
  }
  function goPlan() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=' + thisProjectId;
  }
  function goTickets() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=' + thisProjectId;
  }
  function goTeam() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=Team&pid=' + thisProjectId;
  }
  function goDetails() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=Details&pid=' + thisProjectId;
  }
  function goPermissions() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&section=Setup&pid=' + thisProjectId;
  }

</script>
<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0">
      <tr id="menuProject" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goProject()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Project Center
        </td>
      </tr>
      <tr id="menuNews" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goNews()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to News
        </td>
      </tr>
      <tr id="menuDiscussion" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goDiscussion()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Discussion
        </td>
      </tr>
      <tr id="menuDocuments" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goDocuments()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Documents
        </td>
      </tr>
      <tr id="menuLists" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goLists()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Lists
        </td>
      </tr>
      <tr id="menuPlan" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goPlan()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Plan
        </td>
      </tr>
      <tr id="menuTickets" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goTickets()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Tickets
        </td>
      </tr>
      <tr id="menuTeam" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goTeam()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Team
        </td>
      </tr>
      <tr id="menuDetails" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goDetails()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Details
        </td>
      </tr>
      <tr id="menuPermissions" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goPermissions()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to Permissions
        </td>
      </tr>
    </table>
  </div>
</div>
