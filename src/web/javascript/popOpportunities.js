function popOpportunityList(hiddenFieldId, displayFieldId, params) {
  title  = 'Opportunities';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if(params != null && params != ""){
    params = '&' + params;
  }
  var newwin=window.open('OpportunitySelector.do?command=List&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popOppForm(hiddenFieldId, displayFieldId, oppId, componentId, params) {
  title  = 'Opportunities';
  width  =  '700'; 
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if(params != null && params != ""){
    params = '&' + params;
  }
  if (componentId == -1) {
    componentId = document.getElementById(hiddenFieldId).value;
  }
  if (componentId != -1 || oppId != -1) {
    var newwin=window.open('OpportunitiesComponents.do?command=ModifyComponent&popup=true&id='+componentId+'&headerId='+oppId+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  } else {
    var newwin=window.open('Opportunities.do?command=Add&popup=true&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  }
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function setParentHiddenField(hiddenFieldId, strValue) {
  document.getElementById(hiddenFieldId).value = strValue;
}

