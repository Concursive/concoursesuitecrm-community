<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisContractId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, contractId) {
    thisOrgId = orgId;
    thisContractId = contractId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuServiceContract", "down", 0, 0, 170, getHeight("menuServiceContractTable"));
    }

    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    <table id="menuServiceContractTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-service-contracts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-service-contracts-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-service-contracts-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteContract()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
