<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisImportId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, importId, hasCancelOption, process, canDelete) {
    thisImportId = importId;
    updateMenu(hasCancelOption, process, canDelete);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuImport", "down", 0, 0, 170, getHeight("menuImportTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    window.location.href = 'ExternalContactsImports.do?command=Details&importId=' + thisImportId;
  }
  
  function deleteAction() {
   popURLReturn('ExternalContactsImports.do?command=ConfirmDelete&importId=' + thisImportId,'ExternalContactsImports.do?command=View', 'Delete_message','320','200','yes','no');
  }
  
  function cancel() {
   confirmDelete('ExternalContactsImports.do?command=Cancel&importId=' + thisImportId);
  }
  
  function process() {
   window.location.href = 'ExternalContactsImports.do?command=InitValidate&return=list&importId=' + thisImportId;
  }
</script>
<div id="menuImportContainer" class="menu">
  <div id="menuImportContent">
    <table id="menuImportTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-external_contacts-imports-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-imports-add">
      <tr id="menuProcess" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="process()">
        <th>
          <img src="images/icons/stock_compile-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Process
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-imports-add">
      <tr id="menuCancel" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="cancel()">
        <th>
          <img src="images/icons/stock_calc-cancel-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Cancel
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-imports-delete">
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteAction()">
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
