<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisContractId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, contractId) {
    thisOrgId = orgId;
    thisContractId = contractId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuServiceContract", "down", 0, 0, 170, getHeight("menuServiceContractTable"));
    }

    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href = 'AccountsServiceContracts.do?command=View&orgId=' + thisOrgId + '&id=' + thisContractId;
  }
  
  function modify() {
    window.location.href = 'AccountsServiceContracts.do?command=Modify&orgId=' + thisOrgId + '&id=' + thisContractId + '&return=list';
  }
  
  function deleteContract() {
    popURLReturn('AccountsServiceContracts.do?command=ConfirmDelete&orgId=' + thisOrgId + '&id=' + thisContractId + '&popup=true','AccountsServiceContracts.do?command=List&orgId=' + thisOrgId,'Delete_servicecontract','330','200','yes','no');
  }
  
</script>
<div id="menuServiceContractContainer" class="menu">
  <div id="menuServiceContractContent">
    <table id="menuServiceContractTable" class="pulldown" width="170">
      <dhv:permission name="accounts-service-contracts-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-service-contracts-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-service-contracts-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:deleteContract()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
