function popProductListSingle(hiddenFieldId, displayFieldId, params, orgId) {
  title  = 'Service_Contracts';
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

  contractId = document.getElementById('contractId').value;
  if (!contractId || contractId == '-1') {
    alert("A contract needs to be selected first");
  } else {
    var newwin=window.open('ProductSelector.do?command=ListProducts&listType=single&reset=true&contractId=' + contractId + '&previousSelection='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
    newwin.focus();
    if (newwin != null) {
      if (newwin.opener == null)
        newwin.opener = self;
    }
  }
}

function setParentList(selectedValues, selectedIds, listType, displayFieldId, hiddenFieldId){
  if(selectedIds.length == 0 && listType == "list"){
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected", "", displayFieldId);
  }
  var i = 0;
  opener.document.getElementById(hiddenFieldId).value = selectedIds[i];
  opener.changeDivContent(displayFieldId, selectedValues[i]);
   if (opener.document.getElementById('productSku')){
     opener.document.getElementById('productSku').value = selectedValues[i];
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

