function popAccountsListSingle(hiddenFieldId, displayFieldId) {
  popAccountsListSingle(hiddenFieldId, displayFieldId, '');
}

function popSiteAccountsListSingle(hiddenFieldId, displayFieldId, site, params) {
  title  = 'Accounts';
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
  var newwin=window.open('AccountSelector.do?command=ListAccounts&listType=single&reset=true&siteId='+document.getElementById(site).value+'&previousSelection='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popAccountsListSingle(hiddenFieldId, displayFieldId, params) {
  title  = 'Accounts';
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
  var newwin=window.open('AccountSelector.do?command=ListAccounts&listType=single&reset=true&previousSelection='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popAccountsListSingleAlert(functionName, orgId, itemId, params) {
	title  = 'Accounts';
	width  =  '575';
	height =  '400';
	resize =  'yes';
	bars   =  'no';
	var posx = (screen.width - width)/2;
	var posy = (screen.height - height)/2;
	var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	if(params != ''){
		params = '&' + params;
	}
	url = 'AccountSelector.do?command=ListAccounts&listType=singleAlert&reset=true&previousSelection=' + orgId ;
	url += '&functionName=' + functionName ;
	url += '&orgId=' + orgId ;
	url += '&itemId=' + itemId ;
	url += params ;
	var newwin=window.open(url, title, windowParams);
	newwin.focus();
	if (newwin != null) {
		if (newwin.opener == null)
			newwin.opener = self;
	}
}

function singleAlert(functionName, orgId, itemId, newOrgId, orgName) {
	if (orgId == newOrgId) {
		alert("Your current selection matches the previous selection. Select a different account.");
		return ;
	}
	var msg = opener.executeFunction(functionName, orgName) ;
	if(confirm(msg)) {
		document.acctListView.finalsubmit.value = true;
		opener.executeFunction(functionName, itemId, orgId, newOrgId);
		window.close();
	} else {
		document.acctListView.finalsubmit.value = false;
	}
}

function setParentList(acctIds, acctNames, listType, displayFieldId, hiddenFieldId, browserId, accountSiteIds){
  if(acctNames.length == 0 && listType == "list"){
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected", "", displayFieldId);
  }
  var i = 0;
  if (listType == "list"){
    opener.deleteOptions(displayFieldId);
    for (i=0; i < acctNames.length; i++) {
      opener.insertOption(acctNames[i], acctIds[i], displayFieldId);
    }
  } else if(listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = acctIds[i];
    if (opener.document.getElementById('orgSiteId')){
      opener.document.getElementById('orgSiteId').value = accountSiteIds[i];
    }
    opener.changeDivContent(displayFieldId, acctNames[i]);
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
