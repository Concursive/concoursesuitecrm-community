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
  var thisViewId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, viewId) {
    viewId = viewId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuView", "down", 0, 0, 170, getHeight("menuViewTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    //window.location.href = 'Accounts.do?command=Details&orgId=' + thisOrgId;
  }
  
  function modify() {
    //window.location.href = 'Accounts.do?command=Modify&orgId=' + thisOrgId + '&return=list';
  }
  
  function enable() {
    //window.location.href = 'Accounts.do?command=Enable&orgId=' + thisOrgId + '&return=list';
  }
  
  function archive() {
    //window.location.href = 'Accounts.do?command=Delete&orgId=' + thisOrgId + '&action=disable&return=list';
  }
  
  function deleteAccount() {
    //popURLReturn('Accounts.do?command=ConfirmDelete&id=' + thisOrgId+ '&popup=true','Accounts.do?command=Search', 'Delete_account','330','200','yes','no');
  }
</script>
<div id="menuViewContainer" class="menu">
  <div id="menuViewContent">
    <table id="menuViewTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="">
      <tr id="menuModifyAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="">
      <tr id="menuArchiveAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="">
      <tr id="menuReEnableAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_list_menu.UnArchive">Un-Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="">
      <tr id="menuDeleteAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteAccount()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
