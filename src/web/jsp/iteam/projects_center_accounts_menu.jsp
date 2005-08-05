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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, trashed) {
    thisOrgId = orgId;
    updateMenu(trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuItem", "down", 0, 0, 170, getHeight("menuItemTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  function updateMenu(trashed){
    if (trashed == 'true'){
      hideSpan('menuDelete');
    } else {
      showSpan('menuDelete');
    }
  }
  //Menu link functions
  function goToAccount() {
    document.location.href='Accounts.do?command=Details&orgId=' + thisOrgId;
  }
  function showAccount() {
    document.location.href='Accounts.do?command=Details&orgId=' + thisOrgId + '&popup=true';
  }
  function removeAccount() {
    confirmDelete('ProjectManagementAccounts.do?command=Remove&pid=<%= Project.getId() %>&orgId=' + thisOrgId);
  }
</script>
<%-- List Item Pop-up Menu --%>
<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0" cellspacing="0">
    <dhv:permission name="accounts-view">
      <tr id="menuView" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="goToAccount()">
        <th valign="top">
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="project.goToThisAccount">Go to this Account</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <zeroio:permission name="project-accounts-manage">
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="removeAccount()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="project.removeAccount">Remove Account</dhv:label>
        </td>
      </tr>
    </zeroio:permission>
    </table>
  </div>
</div>

