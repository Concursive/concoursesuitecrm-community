<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
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
  function displayMenu(loc, id, pId, rId, fId, aId, map, indent) {
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
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  function viewOutline() {
    popURL('ProjectManagementRequirements.do?command=Details&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&popup=true','Outline_Details','650','375','yes','yes');
  }
  function deleteActivity() {
    if (confirm('Are you sure?')) {
      scrollReload('ProjectManagementAssignments.do?command=Delete&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId);
    }
  }
  function deleteFolder() {
    if (confirm('Are you sure?')) {
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
    popURL('ProjectManagementAssignments.do?command=Add&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&folderId=' + thisFolderId + '&prevMapId=' + thisMapId + '&prevIndent=' + thisIndent + '&popup=true&return=ProjectAssignments&param=' + thisProjectId,'Assignment','650','400','yes','yes');
  }
  function updateActivity() {
    popURL('ProjectManagementAssignments.do?command=Modify&pid=' + thisProjectId + '&aid=' + thisActivityId + '&popup=true&return=ProjectAssignments','Assignment_Modify','650','375','yes','yes');
  }
  function moveActivity() {
    popURL('ProjectManagementAssignments.do?command=Move&pid=' + thisProjectId + '&rid=' + thisRequirementId + '&aid=' + thisActivityId + '&popup=true&return=ProjectAssignments&param=' + thisProjectId,'Assignment_Move','400','375','yes','yes');
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
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveItemLeft()"><img alt="Unindent" src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemRight()"><img alt="Indent" src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemUp()"><img alt="Move Up" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemDown()"><img alt="Move Down" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addFolder()">
        <th>
          <img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Add Activity Folder
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addActivity()">
        <th>
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Add Activity
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-view,project-plan-outline-modify" if="any">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="updateActivity()">
        <th valign="top">
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View/Update this Activity
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
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteActivity()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete Activity
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
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveItemLeft()"><img alt="Unindent" src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemRight()"><img alt="Indent" src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemUp()"><img alt="Move Up" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveItemDown()"><img alt="Move Down" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addFolder()">
        <th>
          <img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Add Activity Folder
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addActivity()">
        <th>
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Add Activity
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="updateFolder()">
        <th valign="top">
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Update this Folder
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
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFolder()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete Folder and Shift Contents Left
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
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewOutline()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          View Outline Details
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addFolder()">
        <th>
          <img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Add Activity Folder
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-plan-outline-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addActivity()">
        <th>
          <img src="images/icons/stock_live-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Add Activity
        </td>
      </tr>
      </zeroio:permission>
    </table>
  </div>
</div>
