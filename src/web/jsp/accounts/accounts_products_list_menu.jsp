<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisFileId = -1;
  var thisAdId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, adId, fileId) {
    //alert('inside displayMenu');
    thisOrgId = orgId;
    thisAdId = adId;
    thisFileId = fileId;
    if (!menu_init) {
      menu_init = true;
      //alert('sliding out the menu');
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='AccountsProducts.do?command=SVGDetails&orgId=' + thisOrgId + '&adId=' + thisAdId + '&fid=' + thisFileId;
  }
  
  function download() {
    window.location.href='AccountsProducts.do?command=Download&orgId=' + thisOrgId + '&adId=' + thisAdId + '&fid=' + thisFileId + '&ver=1.0';
  }
  
  function deleteFile() {
    popURLReturn('AccountsProducts.do?command=ConfirmDelete&orgId=' + thisOrgId + '&adId=' + thisAdId + '&fid=' + thisFileId + '&popup=true','AccountsProducts.do?command=List&orgId=' + thisOrgId, 'Delete_account_product','330','200','yes','no');
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()"><dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label></a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()"><dhv:label name="button.modify">Modify</dhv:label></a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:download()"><dhv:label name="button.download">Download</dhv:label></a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteFile()"><dhv:label name="button.delete">Delete</dhv:label></a>
        </td>
      </tr>
    </table>
  </div>
</div>
