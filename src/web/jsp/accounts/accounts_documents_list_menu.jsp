<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, fileId) {
    thisOrgId = orgId;
    thisFileId = fileId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function details() {
    window.location.href='AccountsDocuments.do?command=Details&orgId=' + thisOrgId + '&fid=' + thisFileId;
  }
  
  function modify() {
    window.location.href='AccountsDocuments.do?command=Modify&orgId=' + thisOrgId + '&fid=' + thisFileId;
  }
  
  function download() {
    window.location.href='AccountsDocuments.do?command=Download&orgId=' + thisOrgId + '&fid=' + thisFileId;
  }
  
  function deleteFile() {
    confirmDelete('AccountsDocuments.do?command=Delete&fid=' + thisFileId + '&orgId=' + thisOrgId);
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Download
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteFile()">
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
