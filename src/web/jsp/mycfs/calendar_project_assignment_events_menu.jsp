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
  --%>
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisAssignmentId = -1;
  var thisProjectId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayProjectMenu(loc, id, assignmentId, projectId) {
    thisAssignmentId = assignmentId;
    thisProjectId = projectId;
    if (!menu_init) {
      menu_init = true;
      initialize_menus();
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function modifyProjectAssignment() {
    popURL('ProjectManagementAssignments.do?command=Modify&pid=' + thisProjectId + '&aid=' + thisAssignmentId + '&popup=true&return=Calendar','Assignment_Modify','650','475','yes','yes');
  }
</script>
<div id="menuProjectContainer" class="menu">
  <div id="menuProjectContent">
    <table id="menuProjectTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="projects-personal-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="modifyProjectAssignment()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
