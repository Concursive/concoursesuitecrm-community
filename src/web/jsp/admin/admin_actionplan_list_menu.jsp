<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisPlanId = -1;
  var thisModuleId = -1;
  var thisConstantId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, moduleId, constantId, planId, approved, archived) {
    thisPlanId = planId;
    thisModuleId = moduleId;
    thisConstantId = constantId;
    updateMenu(approved, archived);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuPlan", "down", 0, 0, 170, getHeight("menuPlanTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function updateMenu(approved, archived) {
    if (approved) {
      showSpan('disapproved');
      hideSpan('approved');
    } else {
      showSpan('approved');
      hideSpan('disapproved');
    }
    if (archived) {
      showSpan('unarchived');
      hideSpan('archived');
    } else {
      showSpan('archived');
      hideSpan('unarchived');
    }
  }
  //Menu link functions
  function details() {
    window.location.href='ActionPlanEditor.do?command=PlanDetails&planId=' + thisPlanId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId;
  }
  function approve(approved) {
    if (approved) {
      window.location.href='ActionPlanEditor.do?command=ApprovePlan&planId=' + thisPlanId +'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&justApproved=TRUE&return=list';
    } else {
      window.location.href='ActionPlanEditor.do?command=ApprovePlan&planId=' + thisPlanId +'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&justApproved=FALSE&return=list';
    }
  }
  function rename() {
    window.location.href='ActionPlanEditor.do?command=RenamePlan&planId=' + thisPlanId +'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&return=list';
  }
  function archive(archived) {
    if (archived) {
      window.location.href='ActionPlanEditor.do?command=ArchivePlan&planId=' + thisPlanId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&justArchived=TRUE&return=list';
    } else {
      window.location.href='ActionPlanEditor.do?command=ArchivePlan&planId=' + thisPlanId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&justArchived=FALSE&return=list';
    }
  }
  function deletePlan() {
    popURLReturn('ActionPlanEditor.do?command=ConfirmDeletePlan&planId=' + thisPlanId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+ '&popup=true','ActionPlanEditor.do&command=ListPlans&moduleId='+thisModuleId+'&constantId='+thisConstantId,'ActionPlanDelete',330,200,'yes','no');
  }
</script>
<div id="menuPlanContainer" class="menu">
  <div id="menuPlanContent">
    <table id="menuPlanTable" class="pulldown" width="150" cellspacing="0">
      <dhv:permission name="admin-actionplans-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr id="approved" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="approve(true);">
        <th>
          <img src="images/icons/stock_toggle-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_detailsimport.Approve">Approve</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr id="disapproved" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="approve(false);">
        <th>
          <img src="images/icons/stock_toggle-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="actionPlan.disapprove">Disapprove</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="rename();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.rename">Rename</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr id="archived" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive(true);">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr id="unarchived" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive(false);">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_list_menu.UnArchive">Un-Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deletePlan();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
