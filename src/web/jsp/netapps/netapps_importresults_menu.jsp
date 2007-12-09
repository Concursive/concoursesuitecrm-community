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
  var thisExpirationId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, expirationId, deletePermission) {
    thisExpirationId = expirationId;
    updateMenu(deletePermission);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContractExpiration", "down", 0, 0, 170, getHeight("menuContractExpirationTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contract Expiration based on permissions
  function updateMenu(hasDeletePermission){
    if(document.getElementById('menuDelete') != null){
      if(hasDeletePermission == 0){
        hideSpan('menuDelete');
      }else{
        showSpan('menuDelete');
      }
    }
  }
  //Menu link functions
  function details() {
    window.location.href = 'NetworkApplicationsImports.do?command=ContractExpirationDetails&expirationId=' + thisExpirationId;
  }
  
  function deleteContract() {
    url = 'NetworkApplicationsImports.do?command=DeleteContractExpiration&expirationId=' + thisExpirationId + '&importId=<%= ImportDetails.getId() %>&return=list';
    confirmDelete(url);
  }
  
</script>
<div id="menuContractExpirationContainer" class="menu">
  <div id="menuContractExpirationContent">
    <table id="menuContractExpirationTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="netapps_expiration_contracts_imports-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="netapps_expiration_contracts_imports-delete">
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteContract()">
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
