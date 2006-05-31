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
  function displayMenuRow(loc, id, rowId, pageVersionId, columnId, siteId, tabId, pageId) {
    thisPageRowId = rowId;
    thisPageVersionId = pageVersionId;
    thisRowColumnId = columnId;
    thisSiteId = siteId;
    thisTabId = tabId;
    thisPageId = pageId;
    if (!menu_init) {
      menu_init = true;
      //Initialize all the menus in this location
      new ypSlideOutMenu("menuRow", "down", 0, 0, 170, getHeight("menuRowTable"));
      new ypSlideOutMenu("menuTab", "down", 0, 0, 170, getHeight("menuTabTable"));
      new ypSlideOutMenu("menuPageGroup", "down", 0, 0, 170, getHeight("menuPageGroupTable"));
      new ypSlideOutMenu("menuPage", "down", 0, 0, 170, getHeight("menuPageTable"));
      new ypSlideOutMenu("menuColumn", "down", 0, 0, 170, getHeight("menuColumnTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Menu link functions
  function addRowBefore() {
    var url = 'PageRows.do?command=Add&pageVersionId=' + thisPageVersionId +'&rowColumnId=' + thisRowColumnId +'&nextPageRowId=' + thisPageRowId + '&popup=true&auto-populate=true';
    window.frames['server_commands'].location.href=url;
  }

  function addRowAfter() {
    var url = 'PageRows.do?command=Add&pageVersionId=' + thisPageVersionId +'&rowColumnId=' + thisRowColumnId  +'&previousPageRowId='+thisPageRowId  + '&popup=true&auto-populate=true';
    window.frames['server_commands'].location.href=url;
   }

  function moveRowUp() {
    var url ='PageRows.do?command=Move&pageVersionId=' + thisPageVersionId +'&rowColumnId=' + thisRowColumnId +'&pageRowId='+thisPageRowId+'&movePageRowUp=YES&popup=true';
    window.frames['server_commands'].location.href=url;
  }

  function moveRowDown() {
    var url ='PageRows.do?command=Move&pageVersionId=' + thisPageVersionId +'&rowColumnId=' + thisRowColumnId +'&pageRowId='+thisPageRowId+'&movePageRowUp=NO&popup=true';
    window.frames['server_commands'].location.href=url;
  }

  function addColumn() {
    popURL('RowColumns.do?command=AddIcelet&pageRowId=' + thisPageRowId + '&buildLastColumn=true&popup=true&auto-populate=true','AddColumn',600,400,'yes','yes');
  }

  function deleteThisRow() {
    popURLReturn('PageRows.do?command=ConfirmDelete&pageVersionId=' + thisPageVersionId +'&rowColumnId=' + thisRowColumnId +'&siteId='+ thisSiteId+'&pageRowId=' + thisPageRowId+ '&popup=true','Sites.do?command=Details&siteId='+thisSiteId+'&tabId='+thisTabId+'&pageId='+thisPageId+'&popup=true', 'Delete_Row','330','200','yes','no');
  }
</script>
<div id="menuRowContainer" class="menu">
  <div id="menuRowContent">
    <table id="menuRowTable" class="pulldown" width="170" cellspacing="0">
    <dhv:permission name="site-editor-edit">
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveRowUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveRowDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addRowBefore();">
        <th>
          <img src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add row above this row</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addRowAfter();">
        <th>
          <img src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add row below this row</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addColumn();">
        <th>
          <img src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add Column</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteThisRow();">
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
