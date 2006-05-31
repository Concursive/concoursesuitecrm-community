function popFolderForm(hiddenFieldId, displayFieldId, categoryId, recordId, orgId, params) {
  title  = 'Folders';
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
  var newwin=window.open('Accounts.do?command=CheckFields&catId='+categoryId+'&recId='+recordId+'&popup=true&displayFieldId='+displayFieldId+'&orgId='+orgId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function setParentHiddenField(hiddenFieldId, strValue) {
  document.getElementById(hiddenFieldId).value = strValue;
}

