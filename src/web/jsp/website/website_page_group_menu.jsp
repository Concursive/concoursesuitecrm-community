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
  //Set the action parameters for clicked item
  function displayMenuPageGroup(loc, id, pageGroupId, siteId, tabId) {
    thisPageGroupId = pageGroupId;
    thisSiteId = siteId;
    thisTabId = tabId;
    if (!menu_init) {
      menu_init = true;
      //Initialize all the menus in this location
      new ypSlideOutMenu("menuPageGroup", "down", 0, 0, 170, getHeight("menuPageGroupTable"));
      new ypSlideOutMenu("menuPage", "down", 0, 0, 170, getHeight("menuPageTable"));
      new ypSlideOutMenu("menuTab", "down", 0, 0, 170, getHeight("menuTabTable"));
      new ypSlideOutMenu("menuRow", "down", 0, 0, 170, getHeight("menuRowTable"));
      new ypSlideOutMenu("menuColumn", "down", 0, 0, 170, getHeight("menuColumnTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Menu link functions
  function modifyPageGroup() {
    popURL('PageGroups.do?command=Modify&pageGroupId=' + thisPageGroupId+'&tabId=' + thisTabId + '&popup=true','ModifyPageGroup',600,400,'yes','yes');
  }

  function addPageGroupBefore() {
    popURL('PageGroups.do?command=Add&tabId=' + thisTabId +'&nextPageGroupId=' + thisPageGroupId + '&popup=true','AddPageGroupBefore',600,400,'yes','yes');
  }

  function addPageGroupAfter() {
    popURL('PageGroups.do?command=Add&tabId=' + thisTabId +'&previousPageGroupId='+thisPageGroupId  + '&popup=true','AddPageGroupAfter',600,400,'yes','yes');
   }

  function movePageGroupUp() {
    var url ='PageGroups.do?command=Move&pageGroupId='+thisPageGroupId+'&movePageGroupUp=YES&popup=true';
    window.frames['server_commands'].location.href=url;
  }

  function movePageGroupDown() {
    var url ='PageGroups.do?command=Move&pageGroupId='+thisPageGroupId+'&movePageGroupUp=NO&popup=true';
    window.frames['server_commands'].location.href=url;
  }
  
  function deletePageGroup() {
    popURLReturn('PageGroups.do?command=ConfirmDelete&pageGroupId=' + thisPageGroupId+'&siteId=' + thisSiteId+'&tabId='+thisTabId+'&popup=true','Sites.do?command=Details&siteId='+thisSiteId+'&tabId='+thisTabId+'&pageId='+thisPageGroupId+'&popup=true', 'Delete_PageGroup','330','200','yes','no');
  }

</script>
<div id="menuPageGroupContainer" class="menu">
  <div id="menuPageGroupContent">
    <table id="menuPageGroupTable" class="pulldown" width="170" cellspacing="0">
    <dhv:permission name="site-editor-edit">
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:movePageGroupUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:movePageGroupDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyPageGroup();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Modify</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addPageGroupBefore();">
        <th>
          <img src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a page group before this page group</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addPageGroupAfter();">
        <th>
          <img src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a page group after this page group</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deletePageGroup();">
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
