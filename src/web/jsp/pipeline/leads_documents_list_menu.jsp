<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisHeaderId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, headerId, fId) {
    thisHeaderId = headerId;
    thisFileId = fId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'LeadsDocuments.do?command=Details&headerId=' + thisHeaderId + '&fid=' + thisFileId + '<%= addLinkParams(request, "viewSource") %>';
  }
  
  function download() {
    window.location.href = 'LeadsDocuments.do?command=Details&headerId=' + thisHeaderId + '&fid=' + thisFileId + '<%= addLinkParams(request, "viewSource") %>';
  }
  
  function modify() {
    window.location.href = 'LeadsDocuments.do?command=Modify&fid=' + thisFileId + '&headerId=' + thisHeaderId + '<%= addLinkParams(request, "viewSource") %>';
  }
  
  function deleteFile() {
    confirmDelete('LeadsDocuments.do?command=Delete&fid=' + thisFileId + '&headerId=' + thisHeaderId + '<%= addLinkParams(request, "viewSource") %>');
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170">
      <dhv:permission name="pipeline-opportunities-documents-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-view">
      <tr>
        <td>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:download()">Download</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteFile()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
