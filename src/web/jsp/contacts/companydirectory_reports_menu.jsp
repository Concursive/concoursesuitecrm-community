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
  var thisReportId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, reportId) {
    thisReportId = reportId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuReport", "down", 0, 0, 170, getHeight("menuReportTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function details() {
    popURL('ExternalContacts.do?command=ShowReportHtml&pid=-1&fid=' + thisReportId + '&popup=true&popup=true','Report','600','400','yes','yes');
  }
  
  function download() {
    window.location.href = 'ExternalContacts.do?command=DownloadCSVReport&fid=' + thisReportId;
  }
  
  function deleteReport() {
    confirmDelete('ExternalContacts.do?command=DeleteReport&pid=-1&fid=' + thisReportId);
  }
  
</script>
<div id="menuReportContainer" class="menu">
  <div id="menuReportContent">
    <table id="menuReportTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-external_contacts-reports-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Data
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-reports-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Download as .CSV File
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-reports-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteReport()">
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
