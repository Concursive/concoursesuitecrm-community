<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  var menu_init = false;
  var thisRowColumnId = -1;
  var thisPageRowId = -1
  var thisPageVersionId = -1;
  var thisPosition = -1;
  function displayMenuColumn(loc, id, colId, pageRowId, pageVersionId, iceletId, position,subrows) {
    thisRowColumnId = colId;
    thisPageRowId = pageRowId;
    thisPageVersionId = pageVersionId;
    thisIceletId = iceletId;
    thisPosition = position;
    updateColumnMenu(iceletId, subrows);
    if (!menu_init) {
      menu_init = true;
      //Initialize all the menus in this location
      new ypSlideOutMenu("menuColumn", "down", 0, 0, 170, getHeight("menuColumnTable"));
      new ypSlideOutMenu("menuRow", "down", 0, 0, 170, getHeight("menuRowTable"));
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
    popURL('RowColumns.do?command=AddIcelet&pageRowId=' + thisPageRowId + '&rowColumnId=' + thisRowColumnId + '&popup=true','AddIcelet',700,500,'yes','yes');
  }

  function modifyIcelet() {
    popURL('RowColumns.do?command=ModifyIceletProperties&pageRowId=' + thisPageRowId+'&rowColumnId=' + thisRowColumnId + '&iceletId=' + thisIceletId +'&popup=true','ModifyProperties',700,500,'yes','yes');
  }

  function replaceIcelet() {
    popURL('RowColumns.do?command=ReplaceIcelet&pageRowId=' + thisPageRowId+'&rowColumnId=' + thisRowColumnId + '&popup=true','ReplaceIcelet',700,500,'yes','yes');
  }

  function addColumnLeft() {
    popURL('RowColumns.do?command=AddIcelet&pageRowId=' + thisPageRowId+'&position=' + thisPosition +'&align=left'+'&nextRowColumnId=' + thisRowColumnId + '&popup=true&auto-populate=true','AddColumn',700,500,'yes','yes');
  }

  function addColumnRight() {
    popURL('RowColumns.do?command=AddIcelet&pageRowId=' + thisPageRowId+'&position=' + thisPosition +'&align=right'+'&previousRowColumnId=' + thisRowColumnId + '&popup=true&auto-populate=true','AddColumn',700,500,'yes','yes');
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
    popURL('AdminDashboards.do?command=DeleteColumn&rowColumnId='+thisRowColumnId+'&pageVersionId='+thisPageVersionId+'&dashboardId='+dashboardId+'&moduleId='+moduleId+'&popup=true','Delete_Tab','330','200','yes','no');
  }
</script>
<div id="menuColumnContainer" class="menu">
  <div id="menuColumnContent">
    <table id="menuColumnTable" class="pulldown" width="170" cellspacing="0">
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveColumnLeft()"><img alt="<dhv:label name='alt.unindent'>Unindent</dhv:label>" src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveColumnRight()"><img alt="<dhv:label name='alt.indent'>Indent</dhv:label>" src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      <tr id="menuColumnAddIcelet" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="replaceIcelet('');">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add Portlet</dhv:label>
        </td>
      </tr>
      <tr id="menuColumnEditIcelet" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyIcelet();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Edit Portlet</dhv:label>
        </td>
      </tr>
      <tr id="menuColumnReplaceIcelet" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="replaceIcelet();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Replace Portlet</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addColumnLeft();">
        <th>
          <img src="images/icons/stock_left-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a column to the left</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addColumnRight();">
        <th>
          <img src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a column to the right</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addSubRow();">
        <th>
          <img src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add a row in this column</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteColumn();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>

<script language="javascript">
	var thisPageRowId = -1;
	var thisPageVersionId = -1;
	var thisPageId = -1;
  //Set the action parameters for clicked item
  function displayMenuRow(loc, id, rowId, pageVersionId, columnId, pageId) {
    thisPageRowId = rowId;
    thisPageVersionId = pageVersionId;
    thisRowColumnId = columnId;
    thisPageId = pageId;
    if (!menu_init) {
      menu_init = true;
      //Initialize all the menus in this location
      new ypSlideOutMenu("menuRow", "down", 0, 0, 170, getHeight("menuRowTable"));
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
    popURL('AdminDashboards.do?command=DeleteRow&pageVersionId=' + thisPageVersionId +'&rowColumnId=' + thisRowColumnId +'&pageRowId=' + thisPageRowId+'&dashboardId='+dashboardId+'&moduleId='+moduleId+'&popup=true', 'Delete_Row','330','200','yes','no');
  }
</script>
<div id="menuRowContainer" class="menu">
  <div id="menuRowContent">
    <table id="menuRowTable" class="pulldown" width="170" cellspacing="0">
      <tr>
        <td valign="top" colspan="2" width="100%" nowrap>
          <a href="javascript:moveRowUp()"><img alt="<dhv:label name='alt.moveUp'>Move Up</dhv:label>" src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
          <a href="javascript:moveRowDown()"><img alt="<dhv:label name='alt.moveDown'>Move Down</dhv:label>" src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addRowBefore();">
        <th>
          <img src="images/icons/stock_up-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add row above this row</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addRowAfter();">
        <th>
          <img src="images/icons/stock_down-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add row below this row</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addColumn();">
        <th>
          <img src="images/icons/stock_right-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Add Column</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteThisRow();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
