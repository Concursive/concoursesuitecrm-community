<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="javascript">
  var thisRelId = -1;
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, relId) {
    thisRelId = relId;
    thisOrgId = orgId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuRelation", "down", 0, 0, 170, getHeight("menuRelationTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Relationship operations
  function deleteRelation() {
    confirmDelete('AccountRelationships.do?command=Delete&orgId=' + thisOrgId + '&id='+ thisRelId);
  }
</script>

<div id="menuRelationContainer" class="menu">
  <div id="menuRelationContent">
    <table id="menuRelationTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-relationships-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteRelation()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>

