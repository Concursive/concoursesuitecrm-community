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
  var thisQueueId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, queueId) {
    thisQueueId = queueId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menu1aPDF", "down", 0, 0, 170, getHeight("menu1aPDFTable"));
      new ypSlideOutMenu("menu1aHtml", "down", 0, 0, 170, getHeight("menu1aHtmlTable"));
      new ypSlideOutMenu("menu1aCsv", "down", 0, 0, 170, getHeight("menu1aCsvTable"));
      new ypSlideOutMenu("menu1aExcel", "down", 0, 0, 170, getHeight("menu1aExcelTable"));
      new ypSlideOutMenu("menu1b", "down", 0, 0, 170, getHeight("menu1bTable"));
      new ypSlideOutMenu("menu2", "down", 0, 0, 170, getHeight("menu2Table"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function deleteReport() {
    confirmDelete('Reports.do?command=DeleteReport&id=' + thisQueueId);
  }
  function stream() {
    popURL('Reports.do?command=StreamReport&id=' + thisQueueId + '&popup=true', 'PDF_REPORT','640','480','yes','yes');
  }
  function download() {
    window.location.href = 'Reports.do?command=DownloadReport&id=' + thisQueueId;
  }
  function cancel() {
    confirmDelete('Reports.do?command=CancelReport&id=' + thisQueueId);
  }
</script>
<div id="menu1aPDFContainer" class="menu">
  <div id="menu1aPDFContent">
    <table id="menu1aPDFTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="stream()">
        <th>
          <img src="images/icons/stock_save-pdf-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.viewAsPDF">View as PDF</dhv:label>
        </td>
      </tr>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.downloadAsPDF">Download PDF</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteReport()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
<div id="menu1aHtmlContainer" class="menu">
  <div id="menu1aHtmlContent">
    <table id="menu1aHtmlTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="stream()">
        <th>
          <img src="images/mime/gnome-text-html-23.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.viewAsHTML">View as HTML</dhv:label>
        </td>
      </tr>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.downloadAsHTML">Download HTML</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteReport()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
<div id="menu1aCsvContainer" class="menu">
  <div id="menu1aCsvContent">
    <table id="menu1aCsvTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="stream()">
        <th>
          <img src="images/mime/gnome-text-plain-23.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.viewAsCSV">View as CSV</dhv:label>
        </td>
      </tr>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.downloadAsCSV">Download CSV</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteReport()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
<div id="menu1aExcelContainer" class="menu">
  <div id="menu1aExcelContent">
    <table id="menu1aExcelTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="stream()">
        <th>
          <img src="images/mime/gnome-text-plain-23.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.viewAsExcel">View as Excel</dhv:label>
        </td>
      </tr>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.downloadAsExcel">Download Excel</dhv:label>
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteReport()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
<div id="menu1bContainer" class="menu">
  <div id="menu1bContent">
    <table id="menu1bTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteReport()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
<div id="menu2Container" class="menu">
  <div id="menu2Content">
    <table id="menu2Table" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="cancel()">
        <th>
          <img src="images/icons/stock_stop-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="reports.cancelReport">Cancel Report</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
