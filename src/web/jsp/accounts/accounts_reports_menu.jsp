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
    popURL('Accounts.do?command=ShowReportHtml&pid=-1&fid=' + thisReportId + '&popup=true','Report','600','400','yes','yes');
  }
  
  function download() {
    window.location.href = 'Accounts.do?command=DownloadCSVReport&fid=' + thisReportId;
  }
  
  function deleteReport() {
    confirmDelete('Accounts.do?command=DeleteReport&pid=-1&fid=' + thisReportId);
  }
  
</script>
<div id="menuReportContainer" class="menu">
  <div id="menuReportContent">
    <table id="menuReportTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-reports-view">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-reports-delete">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:deleteReport()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-reports-view">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:download()">Download</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
