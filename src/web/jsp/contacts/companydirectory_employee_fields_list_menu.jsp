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
  var thisContactId = -1;
  var thisRecId = -1;
  var thisCatId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, catId, recId) {
    thisContactId = contactId;
    thisRecId = recId;
    thisCatId = catId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuField", "down", 0, 0, 170, getHeight("menuFieldTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function folderDetails() {
    window.location.href = 'CompanyDirectory.do?command=Fields&empid=' + thisContactId + '&catId=' + thisCatId + '&recId=' + thisRecId + '<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function modify() {
    window.location.href = 'CompanyDirectory.do?command=ModifyFields&empid=' + thisContactId + '&catId=' + thisCatId + '&recId=' + thisRecId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function deleteField() {
    window.location.href = 'CompanyDirectory.do?command=DeleteFields&empid=' + thisContactId + '&catId=' + thisCatId + '&recId=' + thisRecId + '&return=list';
  }
  
</script>
<div id="menuFieldContainer" class="menu">
  <div id="menuFieldContent">
    <table id="menuFieldTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-internal_contacts-folders-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="folderDetails()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-internal_contacts-folders-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-internal_contacts-folders-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteField()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
