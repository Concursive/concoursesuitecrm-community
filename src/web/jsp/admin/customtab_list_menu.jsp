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
  var thisCustomTabId = -1;
  var thisPreviousId = -1;
  var thisNextId = -1;
  var thisView = "";
  var thisContainerId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, customtabId, previousId, nextId, containerId) {
    thisCustomTabId = customtabId;
    thisPreviousId = previousId;
    thisNextId = nextId;
    thisContainerId = containerId;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCustomTab", "down", 0, 0, 170, getHeight("menuCustomTabTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu() {
      showSpan('menuModifyCustomTabDetails');
      showSpan('menuGrantCustomTabPermissions');
      showSpan('menuEditCustomTab');
      showSpan('menuDeleteCustomTab');
  }

  
  //Product Funtions
  function modifyCustomTabDetails() {
    window.location.href = 'AdminCustomTabs.do?command=ModifyDetails&customtabId=' + thisCustomTabId + '&moduleId=<%= permissionCategory.getId() %>&previousId=' + thisPreviousId + '&nextId=' + thisNextId +'';
  }
  function configureCustomTab() {
    popURL('AdminCustomTabs.do?command=ConfigureCustomTab&customtabId=' + thisCustomTabId + '&moduleId=<%= permissionCategory.getId() %>&containerId='+thisContainerId+'&popup=true','CustomTab','800','600','yes','yes');
  }
  function grantCustomTabPermissions() {
    popURL('PageRoles.do?command=List&pageId='+ thisCustomTabId + '&moduleId=<%=permissionCategory.getId()%>','CustomTab','800','600','yes','yes');
  }
  function deleteCustomTab() {
    popURLReturn('AdminCustomTabs.do?command=ConfirmDelete&customtabId=' + thisCustomTabId + '&moduleId=<%= permissionCategory.getId() %>&popup=true','AdminCustomTabs.do?command=List&moduleId=<%= permissionCategory.getId() %>','Delete_customtab','330','200','yes','no');
  }
  function moveCustomTabUp() {
    window.location.href = 'AdminCustomTabs.do?command=Move&customtabId=' + thisCustomTabId + '&moduleId=<%= permissionCategory.getId() %>&moveUp=true&previousId=' + thisPreviousId + '&nextId=' + thisNextId +'';
  }

  function moveCustomTabDown() {
    window.location.href = 'AdminCustomTabs.do?command=Move&customtabId=' + thisCustomTabId + '&moduleId=<%= permissionCategory.getId() %>&moveUp=false&previousId=' + thisPreviousId + '&nextId=' + thisNextId +'';
  }
  
  
</script>
<div id="menuCustomTabContainer" class="menu">
  <div id="menuCustomTabContent">
    <table id="menuCustomTabTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-sysconfig-customtab-edit">
       <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveCustomTabUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveCustomTabDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-customtab-edit">
      <tr id="menuModifyCustomTabDetails" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyCustomTabDetails()">
        <th><img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.customtab.admin.menu.ModifyDetails">Modify Details</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-customtab-edit">
      <tr id="menuGrantCustomTabPermissions" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="grantCustomTabPermissions()">
        <th><img src="images/icons/stock_copy-16.gif" border="0" align="Grant Permissions" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.customtab.admin.menu.GrantPermissions">Grant Permissions</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-customtab-edit">
      <tr id="menuEditCustomTab" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="configureCustomTab()">
        <th><img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.customtab.admin.menu.Configure">Configure</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-customtab-delete">
      <tr id="menuDeleteCustomTab" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCustomTab()">
        <th><img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="portlet.customtab.admin.menu.Delete">Delete</dhv:label></td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
