<%-- 
  - Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id: admin_showclients_menu.jsp 13721 2005-12-29 20:43:02 -0500 (Thu, 29 Dec 2005) ananth $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisClientId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, clientId, status) {
    thisClientId = clientId;
    menu_init = true;
    if (document.getElementById('menuDisable') != null && document.getElementById('menuEnable') != null) {
      showSpan('menuModify');
      showSpan('menuDelete');
      if (status == 0) {
        hideSpan('menuDisable');
        showSpan('menuEnable');
      } else if (status == 1) {
        hideSpan('menuEnable');
        showSpan('menuDisable');
      } else {
        hideSpan('menuEnable');
        hideSpan('menuDisable');
      }
    }
    new ypSlideOutMenu("menuClient", "down", 0, 0, 170, getHeight("menuClientTable"));
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function modify() {
    window.location.href = 'AdminClientManager.do?command=ModifyClientForm&id=' + thisClientId;
  }

  function disable() {
    window.location.href = 'AdminClientManager.do?command=DisableClient&id=' + thisClientId;
  }

  function enable() {
    window.location.href = 'AdminClientManager.do?command=EnableClient&id=' + thisClientId;
  }

  function deleteClient() {
    window.location.href = 'AdminClientManager.do?command=DeleteClient&id=' + thisClientId;
  }
</script>

<div id="menuClientContainer" class="menu">
  <div id="menuClientContent">
    <table id="menuClientTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-sysconfig-edit">
        <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
            onclick="modify()">
          <th>
            <img src="images/icons/stock_edit-16.gif" border="0"
                 align="absmiddle" height="16" width="16"/>
          </th>
          <td width="100%">
            <dhv:label name="global.button.modify">Modify</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-edit">
        <tr id="menuDisable" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
            onclick="disable()">
          <th>
            <img src="images/icons/stock_toggle-16.gif" border="0"
                 align="absmiddle" height="16" width="16"/>
          </th>
          <td>
            <dhv:label name="admin.disableClient">Disable Client</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-edit">
        <tr id="menuEnable" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
            onclick="enable()">
          <th>
            <img src="images/icons/stock_toggle-16.gif" border="0"
                 align="absmiddle" height="16" width="16"/>
          </th>
          <td>
            <dhv:label name="admin.enableClient">Enable Client</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-edit">
        <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
            onclick="deleteClient()">
          <th>
            <img src="images/icons/stock_delete-16.gif" border="0"
                 align="absmiddle" height="16" width="16"/>
          </th>
          <td width="100%">
            <dhv:label name="global.button.delete">Delete</dhv:label>
          </td>
        </tr>
      </dhv:permission>
    </table>
  </div>
</div>
