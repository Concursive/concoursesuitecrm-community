<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisModuleId = -1;
  var thisConstantId = -1;
  var thisStepId = -1;
  var thisPhaseId = -1;
  var thisPlanId = -1;
  var menu_init_step = false;
  //Set the action parameters for clicked item
  function displayMenuStep(loc, id, moduleId, constantId, stepId, phaseId, planId) {
    thisModuleId = moduleId;
    thisConstantId = constantId;
    thisStepId = stepId;
    thisPhaseId = phaseId;
    thisPlanId = planId;
    if (!menu_init_step) {
      menu_init_step = true;
      new ypSlideOutMenu("menuPhase", "down", 0, 0, 170, getHeight("menuPhaseTable"));
      new ypSlideOutMenu("menuStep", "down", 0, 0, 170, getHeight("menuStepTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function moveStepUp() {
    var url = 'ActionStepEditor.do?command=MoveStep&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&stepId='+thisStepId+'&moveStepUp=YES';
    window.frames['server_commands'].location.href=url;
  }
  function moveStepDown() {
    var url = 'ActionStepEditor.do?command=MoveStep&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&stepId='+thisStepId+'&moveStepUp=NO';
    window.frames['server_commands'].location.href=url;
  }
  function addStepBefore() {
    popURL('ActionStepEditor.do?command=AddStep&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&nextStepId='+thisStepId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&popup=true','AddStepbefore',600,400,'yes','yes');
  }
  function addStepAfter() {
    popURL('ActionStepEditor.do?command=AddStep&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&previousStepId='+thisStepId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&popup=true','AddStepAfter',600,400,'yes','yes');
  }
  function modifyStep() {
    popURL('ActionStepEditor.do?command=ModifyStep&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&stepId='+thisStepId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&popup=true','ModifyStep',600,400,'yes','yes');
  }
  function deleteStep() {
    popURLReturn('ActionStepEditor.do?command=ConfirmDeleteStep&planId='+thisPlanId+'&phaseId='+thisPhaseId+'&stepId='+thisStepId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&popup=true','ActionPlanEditor.do?command=PlanDetails&planId='+thisPlanId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId,'DeleteStep',330,200,'yes','no');
  }
</script>
<div id="menuStepContainer" class="menu">
  <div id="menuStepContent">
    <table id="menuStepTable" class="pulldown" width="200" cellspacing="0">
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="moveStepUp();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="alt.moveUp">Move Up</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="moveStepDown();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="alt.moveDown">Move Down</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addStepBefore();">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.addStepBeforeStep.text">Add a Step before this Step</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addStepAfter();">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.addStepAfterStep.text">Add a Step after this Step</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyStep();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.modifyStep">Modify Step</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteStep();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.deleteStep">Delete Step</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
