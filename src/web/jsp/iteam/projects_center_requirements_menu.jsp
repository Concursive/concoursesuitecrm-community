<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<script language="javascript">
  var thisReqId = -1;
  var thisPrId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, reqId, prId) {
    thisReqId = reqId;
    thisPrId = prId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuItem", "down", 0, 0, 170, getHeight("menuItemTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function viewItem() {
    document.location.href='ProjectManagement.do?command=ProjectCenter&section=Assignments&rid=' + thisReqId + '&pid=' + thisPrId;
  }
  function editItem() {
    document.location.href='ProjectManagementRequirements.do?command=Modify&rid=' + thisReqId + '&pid=' + thisPrId;
  }
  function importAssignments() {
    document.location.href='ProjectManagementRequirements.do?command=PrepareImport&rid=' + thisReqId + '&pid=' + thisPrId;
  }
  function deleteItem() {
    confirmDelete('ProjectManagementRequirements.do?command=Delete&rid=' + thisReqId + '&pid=' + thisPrId);
  }
</script>
<%-- List Item Pop-up Menu --%>
<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewItem()">
        <th valign="top">
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Outline
        </td>
      </tr>
    <zeroio:permission name="project-plan-outline-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editItem()">
        <th valign="top">
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Edit Outline Details
        </td>
      </tr>
    </zeroio:permission>
<%--
    <zeroio:permission name="project-plan-outline-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="importAssignments()">
        <th valign="top">
          <img src="images/icons/stock_help-pane-off-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Import plan
        </td>
      </tr>
    </zeroio:permission>
--%>
    <zeroio:permission name="project-plan-outline-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteItem()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete Outline
        </td>
      </tr>
    </zeroio:permission>
    </table>
  </div>
</div>

