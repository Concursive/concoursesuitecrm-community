<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisImportId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, importId, hasCancelOption, process, canDelete) {
    thisImportId = importId;
    updateMenu(hasCancelOption, process, canDelete);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuImport", "down", 0, 0, 170, getHeight("menuImportTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  function updateMenu(hasCancelOption, process, canDelete){
    if(document.getElementById('menuCancel') != null){
      if(hasCancelOption == 0){
          hideSpan('menuCancel');
      }else{
        showSpan('menuCancel');
      }
    }
    
    if(document.getElementById('menuProcess') != null){
      if(process == 0){
          hideSpan('menuProcess');
      }else{
        showSpan('menuProcess');
      }
    }
    
    if(document.getElementById('menuDelete') != null){
      if(canDelete == 0){
          hideSpan('menuDelete');
      }else{
        showSpan('menuDelete');
      }
    }
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'AccountContactsImports.do?command=Details&importId=' + thisImportId;
  }
  
  function deleteAction() {
   popURLReturn('AccountContactsImports.do?command=ConfirmDelete&importId=' + thisImportId,'AccountContactsImports.do?command=View', 'Delete_message','320','200','yes','no');
  }
  
  function cancel() {
   confirmDelete('AccountContactsImports.do?command=Cancel&importId=' + thisImportId);
  }
  
  function process() {
   window.location.href = 'AccountContactsImports.do?command=InitValidate&return=list&importId=' + thisImportId;
  }
</script>
<div id="menuImportContainer" class="menu">
  <div id="menuImportContent">
    <table id="menuImportTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-contacts-imports-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-imports-add">
      <tr id="menuProcess">
        <td>
          <img src="images/icons/stock_compile-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:process()">Process</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-imports-add">
      <tr id="menuCancel">
        <td>
          <img src="images/icons/stock_calc-cancel-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:cancel()">Cancel</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-imports-delete">
      <tr id="menuDelete">
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteAction()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
