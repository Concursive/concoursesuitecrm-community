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
  function displayMenuPage(loc, id, pageId, pageGroupId, siteId, tabId, tabBannerId) {
    thisPageId = pageId;
    thisSiteId = siteId;
    thisTabId = tabId;
    thisPageGroupId = pageGroupId;
    thisTabBannerId = tabBannerId;
    updatePageMenu(tabBannerId);
    if (!menu_init) {
      menu_init = true;
      //Initialize all the menus in this location
      new ypSlideOutMenu("menuPage", "down", 0, 0, 170, getHeight("menuPageTable"));
      new ypSlideOutMenu("menuTab", "down", 0, 0, 170, getHeight("menuTabTable"));
      new ypSlideOutMenu("menuPageGroup", "down", 0, 0, 170, getHeight("menuPageGroupTable"));
      new ypSlideOutMenu("menuRow", "down", 0, 0, 170, getHeight("menuRowTable"));
      new ypSlideOutMenu("menuColumn", "down", 0, 0, 170, getHeight("menuColumnTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updatePageMenu(tabBannerId) {
    if (tabBannerId == '-1') {
      showSpan('menuAddTabBanner');
      hideSpan('menuModifyTabBanner');
    } else {
      showSpan('menuModifyTabBanner');
      hideSpan('menuAddTabBanner');
    }
  }
  //Menu link functions
  function modifyPage() {
    popURL('Pages.do?command=Modify&pageGroupId=' + thisPageGroupId+'&pageId=' + thisPageId + '&popup=true','ModifyPhase',600,400,'yes','yes');
  }

  function addPageBefore() {
    popURL('Pages.do?command=Add&pageGroupId=' + thisPageGroupId +'&nextPageId=' + thisPageId + '&popup=true','AddPageBefore',600,400,'yes','yes');
  }

  function addPageAfter() {
    popURL('Pages.do?command=Add&pageGroupId=' + thisPageGroupId +'&previousPageId='+thisPageId  + '&popup=true','AddPageAfter',600,400,'yes','yes');
   }

  function movePageUp() {
    var url ='Pages.do?command=Move&pageId='+thisPageId+'&movePageUp=YES&popup=true';
    window.frames['server_commands'].location.href=url;
  }

  function movePageDown() {
    var url ='Pages.do?command=Move&pageId='+thisPageId+'&movePageUp=NO&popup=true';
    window.frames['server_commands'].location.href=url;
  }
  
  function changeTabBanner() {
    alert('change tab banner method.. should popup list of tab banners ');
  }
  
  function addTabBanner() {
    alert('add a tab banner');
  }
  
  function modifyTabBanner() {
    alert('replace the tab banner with Id '+ thisTabBannerId);
  }

  function deletePage() {
    popURLReturn('Pages.do?command=ConfirmDelete&pageId=' + thisPageId+'&pageGroupId=' + thisPageGroupId+'&siteId=' + thisSiteId+ '&popup=true','Sites.do?command=Details&siteId='+thisSiteId+'&tabId='+thisTabId+'&pageId='+thisPageId+'&popup=true', 'Delete_Page','330','200','yes','no');
  }

</script>
<div id="menuPageContainer" class="menu">
  <div id="menuPageContent">
    <table id="menuPageTable" class="pulldown" width="170" cellspacing="0">
    <dhv:permission name="site-editor-edit">
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:movePageUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:movePageDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyPage();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Modify</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addPageBefore();">
        <th>
          <img src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add page above this page</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addPageAfter();">
        <th>
          <img src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add page below this page</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr id="menuAddTabBanner" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addTabBanner();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a tab banner</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr id="menuModifyTabBanner" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyTabBanner();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Replace the tab banner</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="changeTabBanner();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Change the tab banner</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deletePage();">
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
