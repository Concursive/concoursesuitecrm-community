<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisReportId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, reportId) {
    thisReportId = reportId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuReport", "down", 0, 0, 170, getHeight("menuReportTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    popURL('LeadsReports.do?command=ShowExportHtml&pid=-1&fid=' + thisReportId + '&popup=true','Report','600','400','yes','yes');
  }
  
  function download() {
    window.location.href = 'LeadsReports.do?command=DownloadCSVExport&fid=' + thisReportId;
  }
  
  function deleteReport() {
    confirmDelete('LeadsReports.do?command=DeleteCSVExport&pid=-1&fid=' + thisReportId);
  }
  
</script>
<div id="menuReportContainer" class="menu">
  <div id="menuReportContent">
    <table id="menuReportTable" class="pulldown" width="170">
      <dhv:permission name="pipeline-reports-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-reports-view">
      <tr>
        <td>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:download()">Download</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-reports-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteReport()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
