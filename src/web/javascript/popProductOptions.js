function popProductOptionsListSingle(hiddenFieldId, displayFieldId) {
  popProductOptionsListSingle(hiddenFieldId, displayFieldId, '');
}

function popProductOptionsListSingle(hiddenFieldId, displayFieldId, params) {
  title  = 'ProductOptions';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  if(params != ''){
    params = '&' + params;
  }
  var newwin=window.open('ProductOptionSelector.do?command=ListProductOptions&listType=single&reset=true&previousSelection='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popProductOptionsListMultiple(displayFieldId, highLightedId, params) {
  title  = 'ProductOptions';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var selectedIds = "";
  var selectedDisplays ="";
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
    if (document.getElementById(displayFieldId).options[count].value > -1) {
      if (selectedIds.length > 0) {
        selectedIds = selectedIds + "|";
        selectedDisplays = selectedDisplays + "|";
      }
      selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
      selectedDisplays = selectedDisplays + document.getElementById(displayFieldId).options[count].text;
    }
  }
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if(params != null && params != ""){
    params = '&' + params;
  }
  var newwin=window.open('ProductOptionSelector.do?command=ListProductOptions&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&listType=list&flushtemplist=true&selectedIds='+highLightedId+'&displayFieldId='+displayFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popProductOptionsListMultiple(existingIds, params) {
  title  = 'ProductOptions';
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
  var selectedIds = "";
  for (count=0; count < existingIds.length; ++count) {
    if (selectedIds.length > 0) {
      selectedIds = selectedIds + "|";
    }
    selectedIds = selectedIds + existingIds[count];
  }
  var newwin=window.open('ProductOptionSelector.do?command=ListProductOptions&previousSelection=' + selectedIds + '&listType=list' + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function setParentList(optionIds, optionNames, listType, displayFieldId, hiddenFieldId){
  if(optionNames.length == 0 && listType == "list"){
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected", "", displayFieldId);
  }
  var i = 0;
  if (listType == "list"){
    opener.deleteOptions(displayFieldId);
    for (i=0; i < optionNames.length; i++) {
      opener.insertOption(optionNames[i], optionIds[i], displayFieldId);
    }
  } else if(listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = optionIds[i];
    opener.changeDivContent(displayFieldId, optionNames[i]);
  }
}


function SetChecked(val, chkName, thisForm, browser) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(chkName)!=-1) {
      frm.elements[i].checked=val;
      highlight(frm.elements[i],browser);
    }
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
