function popContactsListSingle(hiddenFieldId, displayFieldId, params) {
  title  = 'Contacts';
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
  var newwin=window.open('ContactsList.do?command=ContactList&listType=single&flushtemplist=true&selectedIds='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popContactsListMultiple(displayFieldId, highLightedId, params) {
  title  = 'Contacts';
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
  var newwin=window.open('ContactsList.do?command=ContactList&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&listType=list&flushtemplist=true&selectedIds='+highLightedId+'&displayFieldId='+displayFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function checkKey(displayFieldId, key) {
  var pattern1 = /\*/;
  var pattern2 = /9\*1\*/;
  
  for (count=0; count<(opener.document.getElementById(displayFieldId).length); count++) {
    var item = opener.document.getElementById(displayFieldId).options[count].value;
    if (key.length == 0 || key == null) {
      //leave the non-contact-select options in there
      //null checks are for "other" select options
      return true;
    }
    item = campaignModifyPatternToValue(item);
    if (item == key) {
      return true;
    }
  }
  return false;
}        

function checkArrayKey(array, key) {
  key = campaignModifyPatternToValue(key);
  if (array.length == 0 && (key.length == 0 || key == null)) {
    //leave the non-contact-select options in there
    //null checks are for "other" select options
    return true;
  }
  if (key == -1) {
    return true;
  }
  for (count=0; count<(array.length); count++) {
    if (array[count] == key || key.length == 0 || key == null) {
      return true;
    }
  }
  return false;
}

function campaignModifyPatternToValue(value) {
  var pattern1 = /\*/;
  var pattern2 = /9\*1\*/;
  var result = "";
  if (pattern1.test(value)) {
    if (pattern2.test(value)) {
      result = value.split("*");
      return result[2];
    } else {
      return -1;
    }
  }
  return value;
}

function removeOptions(displayFieldId, ids) {
  var limit = opener.document.getElementById(displayFieldId).options.length;
  var item = "";
  var tempArray = new Array()
  var offset = 0;
  for (x=0; x<limit; x++) {
    item = opener.document.getElementById(displayFieldId).options[x].value;
    if (!(checkArrayKey(ids, item)) && (item!=null && item!="")) {
      opener.removeValue(x);
      limit = opener.document.getElementById(displayFieldId).options.length;
      x=0;
    }
  }
}

function popContactsListMultipleCampaign(displayFieldId,highLightedId, params) {
  title  = 'Contacts';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var selectedIds = "";
  var selectedDisplays ="";
  var pattern = /^9\*1\*/;
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
    if (pattern.test(document.getElementById(displayFieldId).options[count].value)) {
      if (selectedIds.length > 0) {
        selectedIds = selectedIds + "|";
        selectedDisplays = selectedDisplays + "|";
      }
      selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value.substring(4);
      selectedDisplays = selectedDisplays + document.getElementById(displayFieldId).options[count].text;
    } else if (document.getElementById(displayFieldId).options[count].value.length > 0 && document.getElementById(displayFieldId).options[count].value > -1) {
      if (selectedIds.length > 0) {
        selectedIds = selectedIds + "|";
        selectedDisplays = selectedDisplays + "|";
      }
      selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
      selectedDisplays = selectedDisplays + document.getElementById(displayFieldId).options[count].text;
    }
  }
  if(params != null && params != ""){
    params = '&' + params;
  }else{
    params = '';
  }
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ContactsList.do?command=ContactList&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&listType=list&campaign=true&flushtemplist=true&selectedIds='+highLightedId+'&displayFieldId='+displayFieldId+params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function setParentList(recipientEmails,recipientIds,listType,displayFieldId,hiddenFieldId){
  if(recipientEmails.length == 0 && listType == "list"){
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected","-1",displayFieldId);
    return ;
  }
  var i = 0;
  if (listType == "list"){
    opener.deleteOptions(displayFieldId);
    for (i=0; i < recipientEmails.length; i++) {
      opener.insertOption(recipientEmails[i],recipientIds[i],displayFieldId);
    }
  } else if(listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = recipientIds[i];
    opener.changeDivContent(displayFieldId,recipientEmails[i]);
  }
}
  
function setParentListCampaign(recipientEmails,recipientIds,listType,displayFieldId,hiddenFieldId){
  if (recipientEmails.length == 0 && listType == "list") {
    removeOptions(displayFieldId, recipientIds);
    return;
    //opener.insertOption("None Selected","",displayFieldId);
  }
  var i = 0;
  var searchList = opener.document.forms[0].searchCriteria;
  if (listType == "list") {
    if (searchList.length == 0 || searchList.options[0].value == "-1") {
      opener.deleteOptions(displayFieldId);
    } else {
      if (opener.searchCriteria.length == 0) {
        for (count=0; count<(searchList.length); count++) {
          opener.searchCriteria[count] = searchList.options[count].value;
        }
      }
    }
    //remove contacts that have been un-checked
    removeOptions(displayFieldId, recipientIds);
    for (i=0; i < recipientEmails.length; i++) {
      var newCriteria = "9|1|" + recipientIds[i];
      //don't insert duplicate options
      if (!(checkKey(displayFieldId, recipientIds[i]))) {
        opener.insertOption("Contact Name (is) " + recipientEmails[i],recipientIds[i],displayFieldId);
        opener.searchCriteria[opener.searchCriteria.length] = newCriteria;
      }
    }
  } else if (listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = recipientIds[i];
    opener.changeDivContent(displayFieldId,recipientEmails[i]);
  }
  return true;
}

function SetChecked(val,chkName,thisForm,browser) {
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
