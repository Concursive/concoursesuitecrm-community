function popAccountFileItemList(hiddenFieldId, displayFieldId, params) {
  title  = 'Documents';
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
  var newwin=window.open('AccountsDocuments.do?command=View&popup=true&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function setParentHiddenField(hiddenFieldId, strValue) {
  document.getElementById(hiddenFieldId).value = strValue;
}

function setParentList(hiddenFieldId, strValue) {
  document.getElementById(hiddenFieldId).value = strValue;
}

function popDocumentsListSingle(hiddenFieldId, displayFieldId, moduleId, params) {
  title  = 'Documents';
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
  var newwin=window.open('DocumentSelector.do?command=ListDocuments&popup=true&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + '&moduleId='+moduleId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}
function popDocumentsListMultiple(hiddenFieldId, displayFieldId, moduleId, params) {
  title  = 'Documents';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var messageText = "";

  if(document.getElementById('contactLink') != null) {
    var contId = document.getElementById('contactLink').value;
    if(contId == "-1" || contId == "") {
      messageText += label("check.ticket.contact.entered", "- Check that a Contact is selected\r\n");
      alert(label("check.send.email", "The message could not be sent, please check the following:\r\n\r\n") + messageText);
      return;
    }
  }

  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if(params != null && params != ""){
    params = '&' + params;
  }
  var selectedIds = "";
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
    if (document.getElementById(displayFieldId).options[count].value > -1) {
      if (selectedIds.length > 0) {
        selectedIds = selectedIds + "|";
      }
      selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
    }
  }
  if(document.getElementById('contactLink') != null) {
    var newwin=window.open('DocumentSelector.do?command=ListDocuments&popup=true&listType=list&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + '&moduleId='+moduleId + '&previousSelection=' + selectedIds + '&contId=' + contId + params, title, windowParams);
  } else {
    var newwin=window.open('DocumentSelector.do?command=ListDocuments&popup=true&listType=list&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + '&moduleId='+moduleId + '&previousSelection=' + selectedIds + params, title, windowParams);
  }
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function setParentFileList(fileIds, fileNames, listType, displayFieldId, hiddenFieldId, browserId){
  if(fileNames.length == 0 && listType == "list"){
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected", "", displayFieldId);
    return;
  }
  var i = 0;
  if (listType == "list"){
    opener.deleteOptions(displayFieldId);
    for (i=0; i < fileNames.length; i++) {
      opener.insertOption(fileNames[i], fileIds[i], displayFieldId);
    }
  } else if(listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = fileIds[i];
    opener.changeDivContent(displayFieldId, fileNames[i]);
  }
}

function highlight(E,browser){
  if (E.checked){
    hL(E,browser);
  } else {
    dL(E,browser);
  }
}
    
function hL(E,browser){
  if (browser=="ie"){
    while (E.tagName!="TR"){
      E=E.parentElement;
    }
  } else{
    while (E.tagName!="TR") {
      E=E.parentNode;
    }
  }
  if (E.className.indexOf("hl")==-1) {
     E.className = E.className+"hl";
  }
}

function dL(E,browser){
  if (browser=="ie") {
    while (E.tagName!="TR"){
      E=E.parentElement;
    }
  } else{
    while (E.tagName!="TR"){
      E=E.parentNode;
    }
  }
  E.className = E.className.substr(0,4);
}


