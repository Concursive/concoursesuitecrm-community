<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisModuleId = -1;
  var thisConstantId = -1;
  var thisPhaseId = -1;
  var thisPlanId = -1;
  var thisStepId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenuPhase(loc, id, moduleId, constantId, phaseId, planId, stepId) {
    thisModuleId = moduleId;
    thisConstantId = constantId;
    thisPhaseId = phaseId;
    thisPlanId = planId;
    thisStepId = stepId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuPhase", "down", 0, 0, 170, getHeight("menuPhaseTable"));
      new ypSlideOutMenu("menuStep", "down", 0, 0, 170, getHeight("menuStepTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function movePhaseUp() {
    var url = 'ActionPhaseEditor.do?command=MovePhase&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&movePhaseUp=YES';
    window.frames['server_commands'].location.href=url;
  }
  function movePhaseDown() {
    var url = 'ActionPhaseEditor.do?command=MovePhase&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&movePhaseUp=NO';
    window.frames['server_commands'].location.href=url;
  }
  function addStep() {
    popURL('ActionStepEditor.do?command=AddStep&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&previousStepId='+thisStepId+'&moduleId='+thisModuleId+'&constantId='+'&popup=true','AddStep',600,450,'yes','yes');
  }
  function addPhaseBefore() {
    popURL('ActionPhaseEditor.do?command=AddPhase&planId=' + thisPlanId+'&nextPhaseId='+thisPhaseId+'&popup=true','AddPhaseBefore',600,400,'yes','yes');
  }
  function addPhaseAfter() {
    popURL('ActionPhaseEditor.do?command=AddPhase&planId=' + thisPlanId+'&previousPhaseId='+thisPhaseId+'&popup=true','AddPhaseAfter',600,400,'yes','yes');
  }
  function modifyPhase() {
    popURL('ActionPhaseEditor.do?command=ModifyPhase&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&popup=true','ModifyPhase',600,400,'yes','yes');
  }
  function deletePhase() {
    popURLReturn('ActionPhaseEditor.do?command=ConfirmDeletePhase&planId=' + thisPlanId+'&phaseId='+thisPhaseId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId+'&popup=true','ActionPlanEditor.do?command=PlanDetails&planId='+thisPlanId+'&moduleId='+thisModuleId+'&constantId='+thisConstantId,'DeletePhase',330,200,'yes','no');
  }
</script>
<div id="menuPhaseContainer" class="menu">
  <div id="menuPhaseContent">
    <table id="menuPhaseTable" class="pulldown" width="200" cellspacing="0">
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="movePhaseUp();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="alt.moveUp">Move Up</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="movePhaseDown();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="alt.moveDown">Move Down</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addStep();">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.addStep">Add a Step</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addPhaseBefore();">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.addPhaseBeforePhase.text">Add a Phase before this Phase</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addPhaseAfter();">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.addPhaseAfterPhase.text">Add a Phase after this Phase</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyPhase();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="actionPlan.modifyPhase">Modify Phase</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-actionplans-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deletePhase();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="admin.actionPlan.deletePhase">Delete Phase</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
