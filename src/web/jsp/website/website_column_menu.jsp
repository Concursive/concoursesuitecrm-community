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
  function displayMenuColumn(loc, id, colId, rowId, pageVersionId, siteId, tabId, pageId, iceletId, subrows) {
    thisRowColumnId = colId;
    thisPageRowId = rowId;
    thisPageVersionId = pageVersionId;
    thisSiteId = siteId;
    thisTabId = tabId;
    thisPageId = pageId;
    thisIceletId = iceletId;
    updateColumnMenu(iceletId, subrows);
    if (!menu_init) {
      menu_init = true;
      //Initialize all the menus in this location
      new ypSlideOutMenu("menuColumn", "down", 0, 0, 170, getHeight("menuColumnTable"));
      new ypSlideOutMenu("menuTab", "down", 0, 0, 170, getHeight("menuTabTable"));
      new ypSlideOutMenu("menuPageGroup", "down", 0, 0, 170, getHeight("menuPageGroupTable"));
      new ypSlideOutMenu("menuPage", "down", 0, 0, 170, getHeight("menuPageTable"));
      new ypSlideOutMenu("menuRow", "down", 0, 0, 170, getHeight("menuRowTable"));
      new ypSlideOutMenu("menuField", "down", 0, 0, 170, getHeight("menuFieldTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Menu link functions
  function updateColumnMenu(iceletId, subrows) {
    if (subrows == 'true') {
      hideSpan('menuColumnAddIcelet');
      hideSpan('menuColumnEditIcelet');
      hideSpan('menuColumnReplaceIcelet');
      } else {
      if (iceletId == '-1') {
        showSpan('menuColumnAddIcelet');
        hideSpan('menuColumnEditIcelet');
        hideSpan('menuColumnReplaceIcelet');
      } else {
        showSpan('menuColumnEditIcelet');
        showSpan('menuColumnReplaceIcelet');
        hideSpan('menuColumnAddIcelet');
      }
    }
  }

  function addIcelet(addendum) {
    popURL('RowColumns.do?command=AddIcelet&pageRowId=' + thisPageRowId +'&rowColumnId=' + thisRowColumnId + '&popup=true','AddIcelet',700,500,'yes','yes');
  }

  function modifyIcelet() {
    popURL('RowColumns.do?command=ModifyIceletProperties&pageRowId=' + thisPageRowId +'&rowColumnId=' + thisRowColumnId + '&iceletId=' + thisIceletId +'&fromWebsite=true&popup=true','ModifyProperties',700,500,'yes','yes');
  }

  function replaceIcelet() {
    popURL('RowColumns.do?command=ReplaceIcelet&pageRowId=' + thisPageRowId +'&rowColumnId=' + thisRowColumnId + '&popup=true','ReplaceIcelet',700,500,'yes','yes');
  }

  function addColumnLeft() {
    popURL('RowColumns.do?command=AddIcelet&pageRowId=' + thisPageRowId +'&nextRowColumnId=' + thisRowColumnId + '&popup=true&auto-populate=true','AddColumn',700,500,'yes','yes');
  }

  function addColumnRight() {
    popURL('RowColumns.do?command=AddIcelet&pageRowId=' + thisPageRowId +'&previousRowColumnId=' + thisRowColumnId + '&popup=true&auto-populate=true','AddColumn',700,500,'yes','yes');
  }

  function moveColumnLeft() {
    var url = 'RowColumns.do?command=Move&pageRowId=' + thisPageRowId+'&rowColumnId='+thisRowColumnId+'&moveRowColumnLeft=YES&popup=true';
    window.frames['server_commands'].location.href=url;
  }

  function moveColumnRight() {
    var url = 'RowColumns.do?command=Move&pageRowId=' + thisPageRowId+'&rowColumnId='+thisRowColumnId+'&moveRowColumnLeft=NO&popup=true';
    window.frames['server_commands'].location.href=url;
  }

  function addSubRow() {
    var url = 'RowColumns.do?command=AddSubRow&rowColumnId='+thisRowColumnId+'&popup=true';
    window.frames['server_commands'].location.href=url;
  }

  function deleteColumn() {
    popURLReturn('RowColumns.do?command=ConfirmDelete&pageVersionId=' + thisPageVersionId+'&rowColumnId='+thisRowColumnId+'&siteId='+thisSiteId+'&pageVersionId='+thisPageVersionId+'&popup=true','Sites.do?command=Details&siteId='+thisSiteId+'&tabId='+thisTabId+'&pageId='+thisPageId+'&popup=true', 'Delete_Tab','330','200','yes','no');
  }
</script>
<div id="menuColumnContainer" class="menu">
  <div id="menuColumnContent">
    <table id="menuColumnTable" class="pulldown" width="170" cellspacing="0">
    <dhv:permission name="site-editor-edit">
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveColumnLeft()"><img alt="<dhv:label name='alt.unindent'>Unindent</dhv:label>" src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveColumnRight()"><img alt="<dhv:label name='alt.indent'>Indent</dhv:label>" src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr id="menuColumnAddIcelet" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="replaceIcelet('');">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add Portlet</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr id="menuColumnEditIcelet" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyIcelet();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Edit Portlet</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr id="menuColumnReplaceIcelet" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="replaceIcelet();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Replace Portlet</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addColumnLeft();">
        <th>
          <img src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a column to the left</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addColumnRight();">
        <th>
          <img src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a column to the right</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addSubRow();">
        <th>
          <img src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a row in this column</dhv:label>
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="site-editor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteColumn();">
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
