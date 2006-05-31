function popActionPlanAttachment(type, hiddenFieldId, displayFieldId, params) {
  title  = 'Action_Plans';
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
  var newwin='';
  if (type == 'note') {
    newwin = window.open('ActionPlans.do?command=AddNote&popup=true&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  } else if (type == 'selection') {
    newwin = window.open('ActionPlans.do?command=AddSelection&popup=true&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  } else if (type == 'relation') {
    newwin = window.open('ActionPlans.do?command=AddRelation&popup=true&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
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

