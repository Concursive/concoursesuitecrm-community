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
  function displayMenuTab(loc, id, tabId, siteId) {
    thisTabId = tabId;
    thisSiteId = siteId;
    if (!menu_init) {
      menu_init = true;
      //Initialize all the menus in this location
      new ypSlideOutMenu("menuPageGroup", "down", 0, 0, 170, getHeight("menuPageGroupTable"));
      new ypSlideOutMenu("menuTab", "down", 0, 0, 170, getHeight("menuTabTable"));
      new ypSlideOutMenu("menuRow", "down", 0, 0, 170, getHeight("menuRowTable"));
      new ypSlideOutMenu("menuPage", "down", 0, 0, 170, getHeight("menuPageTable"));
      new ypSlideOutMenu("menuColumn", "down", 0, 0, 170, getHeight("menuColumnTable"));
      new ypSlideOutMenu("menuField", "down", 0, 0, 170, getHeight("menuFieldTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Menu link functions
  function modifyTab() {
    popURL('Tabs.do?command=Modify&siteId=' + thisSiteId+'&tabId=' + thisTabId + '&popup=true','ModifyPhase',600,400,'yes','yes');
  }

  function addTabBefore() {
    popURL('Tabs.do?command=Add&siteId=' + thisSiteId +'&nextTabId=' + thisTabId + '&popup=true','AddTabBefore',600,400,'yes','yes');
  }

  function addTabAfter() {
    popURL('Tabs.do?command=Add&siteId=' + thisSiteId +'&previousTabId='+thisTabId  + '&popup=true','AddTabAfter',600,400,'yes','yes');
   }

  function moveTabLeft() {
    window.location.href='Tabs.do?command=Move&siteId=' + thisSiteId+'&tabId='+thisTabId+'&moveTabLeft=YES&popup=true';
  }

  function moveTabRight() {
    window.location.href='Tabs.do?command=Move&siteId=' + thisSiteId+'&tabId='+thisTabId+'&moveTabLeft=NO&popup=true';
  }

  function deleteTab() {
    popURLReturn('Tabs.do?command=ConfirmDelete&siteId=' + thisSiteId+'&tabId=' + thisTabId+ '&popup=true','Sites.do?command=Details&siteId='+thisSiteId, 'Delete_Tab','330','200','yes','no');
  }

  function reopenId(tempId) {
    window.location.href='Sites.do?command=Details&siteId=' + thisSiteId+'&tabId='+tempId + '&popup=true';
  }
</script>
<div id="menuTabContainer" class="menu">
  <div id="menuTabContent">
    <table id="menuTabTable" class="pulldown" width="170" cellspacing="0">
    <dhv:permission name="site-editor-edit">
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveTabLeft()"><img alt="<dhv:label name='alt.unindent'>Unindent</dhv:label>" src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveTabRight()"><img alt="<dhv:label name='alt.indent'>Indent</dhv:label>" src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyTab();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Rename</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addTabBefore();">
        <th>
          <img src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a tab to the left</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addTabAfter();">
        <th>
          <img src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a tab to the right</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteTab();">
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
