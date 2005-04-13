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
  var thisProjectId = -1;
  var thisAccess = 'false';
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, projectId, access) {
    thisProjectId = projectId;
    thisAccess = access;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuItem", "down", 0, 0, 170, getHeight("menuItemTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(){
    if(thisAccess == 'true'){
      showSpan('accessYes');
      hideSpan('accessNo');
    } else {
      showSpan('accessNo');
      hideSpan('accessYes');
    }
  }

  //Menu link functions
  function goToProject() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&pid=' + thisProjectId;
  }

  function showProject() {
    window.location.href='ProjectManagement.do?command=ProjectCenter&pid=' + thisProjectId + '&popup=true';
  }

</script>
<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0">
      <tr id="accessYes" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="goToProject()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go to this Project
        </td>
      </tr>
      <tr id="accessNo" onmouseover="cmOver(this)" onmouseout="cmOut(this)">
        <th>
          <img src="images/icons/stock_calc-cancel-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          You do not have access to this project
        </td>
      </tr>
    </table>
  </div>
</div>
