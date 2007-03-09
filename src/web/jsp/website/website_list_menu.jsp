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
  - Version: $Id:  $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisWebsiteId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, siteId, enabled) {
    thisWebsiteId = siteId;
    updateMenu(enabled);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuSite", "down", 0, 0, 170, getHeight("menuSiteTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(enabled) {
    if (enabled == 'true'){
      hideSpan('menuActivate');
      showSpan('menuDeactivate');
    } else {
      hideSpan('menuDeactivate');
      showSpan('menuActivate');
    }
  }
  //Menu link functions
  function details() {
    window.location.href='Sites.do?command=Details&siteId=' + thisWebsiteId + '&popup=true';
  }

  function preview() {
    window.location.href='Sites.do?command=Details&siteId=' + thisWebsiteId + '&popup=true' + '&viewType=preview';
  }

  function modify() {
    window.location.href='Sites.do?command=Modify&siteId='+ thisWebsiteId + '&popup=true';
  }

  function activate(flag) {
    if (flag) {
      if (confirm(label('','Are you sure you want to Activate this website?'))) {
        window.location.href='Sites.do?command=Activate&siteId='+ thisWebsiteId + '&enable=true&popup=true';
      }
    } else {
      if (confirm(label('','Are you sure you want to De-Activate this website?'))) {
        window.location.href='Sites.do?command=Activate&siteId='+ thisWebsiteId + '&enable=false&popup=true';
      }
    }
  }

  function deleteSite() {
    popURLReturn('Sites.do?command=ConfirmDelete&siteId=' + thisWebsiteId+ '&popup=true','Sites.do?command=List', 'Delete_Site','330','200','yes','no');
  }

  function reopenId(tempId) {
    window.location.href='Sites.do?command=Details&siteId='+tempId + '&popup=true';;
  }
</script>
<div id="menuSiteContainer" class="menu">
  <div id="menuSiteContent">
    <table id="menuSiteTable" class="pulldown" width="170" cellspacing="0">
    <dhv:permission name="site-editor-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Edit Details</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="preview();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="website.preview">Preview</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.modify">Modify</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr id="menuActivate" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="activate(true);">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Activate</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr id="menuDeactivate" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="activate(false);">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">De-Activate</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Export</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteSite();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    </table>
  </div>
</div>
