<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisSurveyId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, surveyId) {
    thisSurveyId = surveyId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuSurvey", "down", 0, 0, 170, getHeight("menuSurveyTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignManagerSurvey.do?command=Details&id=' + thisSurveyId;
  }
  
  function modify() {
    window.location.href='CampaignManagerSurvey.do?command=Modify&id=' + thisSurveyId + '&return=list';
  }
  
  function deleteSurvey() {
    popURLReturn('CampaignManagerSurvey.do?command=ConfirmDelete&id=' + thisSurveyId + '&popup=true','CampaignManagerSurvey.do?command=View', 'Delete_survey','330','200','yes','no');
  }
</script>
<div id="menuSurveyContainer" class="menu">
  <div id="menuSurveyContent">
    <table id="menuSurveyTable" class="pulldown" width="170">
      <dhv:permission name="campaign-campaigns-surveys-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-surveys-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-surveys-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteSurvey()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
