<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<script language="javascript">
  var menu_init = false;
  var thisProjectId = <%= Project.getId() %>
  var thisActivityId = -1;
  var thisFolderId = -1;
  var thisRequirementId = -1;
  var thisMapId = -1;
  var thisIndent = -1;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, pId, rId, fId, aId, map, indent, trashed) {
    thisProject = pId;
    thisRequirementId = rId;
    thisFolderId = fId;
    thisActivityId = aId;
    thisMapId = map;
    thisIndent = indent;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuActivity", "down", 0, 0, 170, getHeight("menuActivityTable"));
      new ypSlideOutMenu("menuFolder", "down", 0, 0, 170, getHeight("menuFolderTable"));
      new ypSlideOutMenu("menuRequirement", "down", 0, 0, 170, getHeight("menuRequirementTable"));
    }
    updateMenu(trashed);
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(trashed){
    if (trashed == 'true'){
      hideSpan('menuActivityMoveActivity');
      hideSpan('menuActivityAddActivityFolder');
      hideSpan('menuActivityAddActivity');
      hideSpan('menuActivityDeleteActivity');
      hideSpan('menuFolderMoveFolder');
      hideSpan('menuFolderAddActivityFolder');
      hideSpan('menuFolderAddActivity');
      hideSpan('menuFolderUpdateFolder');
      hideSpan('menuFolderDeleteFolderAndShift');
      hideSpan('menuRequirementViewOutline');
      hideSpan('menuRequirementAddActivityFolder');
      hideSpan('menuRequirementAddActivity');
    } else {
      showSpan('menuActivityMoveActivity');
      showSpan('menuActivityAddActivityFolder');
      showSpan('menuActivityAddActivity');
      showSpan('menuActivityDeleteActivity');
      showSpan('menuFolderMoveFolder');
      showSpan('menuFolderAddActivityFolder');
      showSpan('menuFolderAddActivity');
      showSpan('menuFolderUpdateFolder');
      showSpan('menuFolderDeleteFolderAndShift');
      showSpan('menuRequirementViewOutline');
      showSpan('menuRequirementAddActivityFolder');
      showSpan('menuRequirementAddActivity');
    }
  }

  function viewOutline() {
    popURL('ProjectManagementRequirements.do?command=Details&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&popup=true','Outline_Details','650','375','yes','yes');
  }
  function deleteActivity() {
    if (confirm(label('are.you.sure','Are you sure?'))) {
      scrollReload('ProjectManagementAssignments.do?command=Delete&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId);
    }
  }
  function deleteFolder() {
    if (confirm(label('are.you.sure','Are you sure?'))) {
      scrollReload('ProjectManagementAssignmentsFolder.do?command=DeleteFolder&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&folderId=' + thisFolderId);
    }
  }
  function addFolder() {
    popURL('ProjectManagementAssignmentsFolder.do?command=AddFolder&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&parentId=' + thisFolderId + '&prevMapId=' + thisMapId + '&prevIndent=' + thisIndent + '&popup=true&return=ProjectAssignments&param=' + thisProjectId + '','Assignment_Folder','650','375','yes','yes');
  }
  function updateFolder() {
    popURL('ProjectManagementAssignmentsFolder.do?command=ModifyFolder&pid=' + thisProjectId + '&folderId=' + thisFolderId + '&popup=true&return=ProjectAssignments','Folder_Modify','650','375','yes','yes');
  }
  function addActivity() {
    popURL('ProjectManagementAssignments.do?command=Add&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&folderId=' + thisFolderId + '&prevMapId=' + thisMapId + '&prevIndent=' + thisIndent + '&popup=true&return=ProjectAssignments&param=' + thisProjectId,'Assignment','650','505','yes','yes');
  }
  function updateActivity() {
    popURL('ProjectManagementAssignments.do?command=Modify&pid=' + thisProjectId + '&aid=' + thisActivityId + '&popup=true&return=ProjectAssignments','Assignment_Modify','650','475','yes','yes');
  }
  function moveActivity() {
    popURL('ProjectManagementAssignments.do?command=Move&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId + '&popup=true&return=ProjectAssignments&param=' + thisProjectId,'Assignment_Move','400','475','yes','yes');
  }
  function moveItemRight() {
    scrollReload('ProjectManagementAssignments.do?command=MoveItem&dir=r&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId + '&folderId=' + thisFolderId);
  }
  function moveItemLeft() {
    scrollReload('ProjectManagementAssignments.do?command=MoveItem&dir=l&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId + '&folderId=' + thisFolderId);
  }
  function moveItemUp() {
    scrollReload('ProjectManagementAssignments.do?command=MoveItem&dir=u&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId + '&folderId=' + thisFolderId);
  }
  function moveItemDown() {
    scrollReload('ProjectManagementAssignments.do?command=MoveItem&dir=d&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId + '&folderId=' + thisFolderId);
  }
</script>
<%-- Activity Pop-up Menu --%>
<div id="menuActivityContainer" class="menu">
  <div id="menuActivityContent">
    <table id="menuActivityTable" class="pulldown" width="170" cellspacing="0">
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuActivityMoveActivity">
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveItemLeft()"><img alt="<dhv:label name='alt.unindent'>Unindent</dhv:label>" src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemRight()"><img alt="<dhv:label name='alt.indent'>Indent</dhv:label>" src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuActivityAddActivityFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addFolder()">
        <th>
          <img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="project.addActivityFolder">Add Activity Folder</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuActivityAddActivity" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addActivity()">
        <th>
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="projects.center_assignments.AddAnActivity">Add an Activity</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-view,project-plan-outline-modify" if="any">
      <tr id="menuActivityViewUpdateActivity" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="updateActivity()">
        <th valign="top">
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="project.viewUpdateActivity">View/Update this Activity</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
<%--
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveActivity()">
        <th valign="top">
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Move Activity to Another Folder
        </td>
      </tr>
      </zeroio:permission>
--%>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuActivityDeleteActivity" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteActivity()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="project.deleteActivity">Delete Activity</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
    </table>
  </div>
</div>
<%-- Activity Folder Pop-up Menu --%>
<div id="menuFolderContainer" class="menu">
  <div id="menuFolderContent">
    <table id="menuFolderTable" class="pulldown" width="170" cellspacing="0">
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuFolderMoveFolder">
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveItemLeft()"><img alt="<dhv:label name='alt.unindent'>Unindent</dhv:label>" src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemRight()"><img alt="<dhv:label name='alt.indent'>Indent</dhv:label>" src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuFolderAddActivityFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addFolder()">
        <th>
          <img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="project.addActivityFolder">Add Activity Folder</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuFolderAddActivity" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addActivity()">
        <th>
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_contacts_calls_add.LogAnActivity">Log an Activity</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuFolderUpdateFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="updateFolder()">
        <th valign="top">
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="project.updateThisFolder">Update this Folder</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
<%--
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFolder()">
        <th valign="top">
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Move Folder to Another Folder
        </td>
      </tr>
      </zeroio:permission>
--%>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuFolderDeleteFolderAndShift" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFolder()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="project.deleteFolderShiftLeft">Delete Folder and Shift Contents Left</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
    </table>
  </div>
</div>
<%-- Requirement Pop-up Menu --%>
<div id="menuRequirementContainer" class="menu">
  <div id="menuRequirementContent">
    <table id="menuRequirementTable" class="pulldown" width="170" cellspacing="0">
      <zeroio:permission name="project-plan-outline-view">
      <tr id="menuRequirementViewOutline" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewOutline()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="project.viewOutlineDetails">View Outline Details</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuRequirementAddActivityFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addFolder()">
        <th>
          <img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="project.addActivityFolder">Add Activity Folder</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr id="menuRequirementAddActivity" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addActivity()">
        <th>
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="projects.center_assignments.AddAnActivity">Add an Activity</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
    </table>
  </div>
</div>
