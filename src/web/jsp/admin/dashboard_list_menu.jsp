<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: products_categories_list_menu.jsp 17025 2006-11-03 22:40:01Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisDashboardId = -1;
  var thisPreviousId = -1;
  var thisNextId = -1;
  var thisView = "";
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, dashboardId, previousId, nextId) {
    thisDashboardId = dashboardId;
    thisPreviousId = previousId;
    thisNextId = nextId;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuDashboard", "down", 0, 0, 170, getHeight("menuDashboardTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu() {
      showSpan('menuModifyDashboardDetails');
      showSpan('menuGrantDashboardPermissions');
      showSpan('menuEditDashboard');
      showSpan('menuDeleteDashboard');
  }

  
  //Product Funtions
  function modifyDashboardDetails() {
    window.location.href = 'AdminDashboards.do?command=ModifyDetails&dashboardId=' + thisDashboardId + '&moduleId=<%= permissionCategory.getId() %>&previousId=' + thisPreviousId + '&nextId=' + thisNextId +'';
  }
  function configureDashboard() {
    popURL('AdminDashboards.do?command=ConfigureDashboard&dashboardId=' + thisDashboardId + '&moduleId=<%= permissionCategory.getId() %>&popup=true','Dashboard','800','600','yes','yes');
  }
  function grantDashboardPermissions() {  
		popURL('PageRoles.do?command=List&pageId='+ thisDashboardId + '&moduleId=<%=permissionCategory.getId()%>','Dashboard','800','600','yes','yes');
  }
  function deleteDashboard() {
	    popURLReturn('AdminDashboards.do?command=ConfirmDelete&dashboardId=' + thisDashboardId + '&moduleId=<%= permissionCategory.getId() %>&popup=true','AdminDashboards.do?command=List&moduleId=<%= permissionCategory.getId() %>','Delete_dashboard','330','200','yes','no');
  }
  function moveDashboardUp() {
    window.location.href = 'AdminDashboards.do?command=Move&dashboardId=' + thisDashboardId + '&moduleId=<%= permissionCategory.getId() %>&moveUp=true&previousId=' + thisPreviousId + '&nextId=' + thisNextId +'';
  }

  function moveDashboardDown() {
    window.location.href = 'AdminDashboards.do?command=Move&dashboardId=' + thisDashboardId + '&moduleId=<%= permissionCategory.getId() %>&moveUp=false&previousId=' + thisPreviousId + '&nextId=' + thisNextId +'';
  }
  
  
</script>
<div id="menuDashboardContainer" class="menu">
  <div id="menuDashboardContent">
    <table id="menuDashboardTable" class="pulldown" width="170" cellspacing="0">
       <dhv:permission name="admin-sysconfig-dashboard-edit">
       <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveDashboardUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveDashboardDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-dashboard-edit">
      <tr id="menuModifyDashboardDetails" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyDashboardDetails()">
        <th><img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.dashboard.admin.menu.ModifyDetails">Modify Details</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-dashboard-edit">
      <tr id="menuGrantDashboardPermissions" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="grantDashboardPermissions()">
        <th><img src="images/icons/stock_copy-16.gif" border="0" align="Grant Permissions" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.dashboard.admin.menu.GrantPermissions">Grant Permissions</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-dashboard-edit">
      <tr id="menuEditDashboard" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="configureDashboard()">
        <th><img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.dashboard.admin.menu.Configure">Configure</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-dashboard-delete">
      <tr id="menuDeleteDashboard" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteDashboard()">
        <th><img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.dashboard.admin.menu.Delete">Delete</dhv:label></td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
