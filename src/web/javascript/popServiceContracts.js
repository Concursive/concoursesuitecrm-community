function popServiceContractListSingle(hiddenFieldId, displayFieldId, params, orgId) {
  title = 'Service_Contracts';
  width = '575';
  height = '400';
  resize = 'yes';
  bars = 'yes';
  var posx = (screen.width - width) / 2;
  var posy = (screen.height - height) / 2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  if (params != '') {
    params = '&' + params;
  }
  if (!orgId || orgId == '-1') {
    orgId = document.getElementById('orgId').value;
  }
  if (!orgId || orgId == '-1') {
    alert("An organization needs to be selected first");
  } else {
    var newwin = window.open('ServiceContractSelector.do?command=ListServiceContracts&listType=single&reset=true&orgId=' + orgId + '&previousSelection=' + document.getElementById(hiddenFieldId).value + '&displayFieldId=' + displayFieldId + '&hiddenFieldId=' + hiddenFieldId + params, title, windowParams);
    newwin.focus();
    if (newwin != null) {
      if (newwin.opener == null)
        newwin.opener = self;
    }
  }
}

function popServiceContractListSingle2(hiddenFieldId, displayFieldId, params, orgId, subId) {
  title = 'Service_Contracts';
  width = '575';
  height = '400';
  resize = 'yes';
  bars = 'yes';
  var posx = (screen.width - width) / 2;
  var posy = (screen.height - height) / 2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  if (params != '') {
    params = '&' + params;
  }
  if (!orgId || orgId == '-1') {
    orgId = document.getElementById('orgId').value;
  }
  if (!orgId || orgId == '-1') {
    alert("An organization needs to be selected first");
  } else {
    var newwin = window.open('ServiceContractSelector.do?command=ListServiceContracts2&listType=single&reset=true&orgId=' + orgId + '&subId=' + subId + '&previousSelection=' + document.getElementById(hiddenFieldId).value + '&displayFieldId=' + displayFieldId + '&hiddenFieldId=' + hiddenFieldId + params, title, windowParams);
    newwin.focus();
    if (newwin != null) {
      if (newwin.opener == null)
        newwin.opener = self;
    }
  }
}

function setContractList(scIds, scNumbers, listType, displayFieldId, hiddenFieldId) {
  if (scNumbers.length == 0 && listType == "list") {
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected", "", displayFieldId);
  }
  var i = 0;
  if (listType == "list") {
    opener.deleteOptions(displayFieldId);
    for (i = 0; i < scNumbers.length; i++) {
      opener.insertOption(scNumbers[i], scIds[i], displayFieldId);
    }
  } else if (listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = scIds[i];
    opener.changeDivContent(displayFieldId, scNumbers[i]);

    if (opener.document.getElementById('serviceContractNumber')) {
      opener.document.getElementById('serviceContractNumber').value = scNumbers[i];
    }
    if (opener.document.getElementById('assetId')) {
      opener.resetNumericFieldValue('assetId');
      opener.changeDivContent('addAsset', "None Selected");
      opener.resetNumericFieldValue('productId');
      opener.changeDivContent('addLaborCategory', "None Selected");
    }

  }
}

function setContractList2(scIds, scNumbers, listType, displayFieldId, hiddenFieldId) {
  if (scNumbers.length == 0 && listType == "list") {
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected", "", displayFieldId);
  }
  var i = 0;
  if (listType == "list") {
    opener.deleteOptions(displayFieldId);
    for (i = 0; i < scNumbers.length; i++) {
      opener.insertOption(scNumbers[i], scIds[i], displayFieldId);
    }
  } else if (listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = scIds[i];
    opener.changeDivContent(displayFieldId, scNumbers[i]);

    if (opener.document.getElementById('serviceContractNumber')) {
      opener.document.getElementById('serviceContractNumber').value = scNumbers[i];
    }
    if (opener.document.getElementById('assetId')) {
      opener.resetNumericFieldValue('assetId');
      opener.changeDivContent('addAsset', "None Selected");
    }

  }
}

function SetChecked(val, chkName, thisForm, browser) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i = 0;
  for (i = 0; i < len; i++) {
    if (frm.elements[i].name.indexOf(chkName) != -1) {
      frm.elements[i].checked = val;
      highlight(frm.elements[i], browser);
    }
  }
}

function highlight(E, browser) {
  if (E.checked) {
    hL(E, browser);
  } else {
    dL(E, browser);
  }
}

function hL(E, browser) {
  if (browser == "ie") {
    while (E.tagName != "TR") {
      E = E.parentElement;
    }
  } else {
    while (E.tagName != "TR") {
      E = E.parentNode;
    }
  }
  if (E.className.indexOf("hl") == -1) {
    E.className = E.className + "hl";
  }
}

function dL(E, browser) {
  if (browser == "ie") {
    while (E.tagName != "TR") {
      E = E.parentElement;
    }
  } else {
    while (E.tagName != "TR") {
      E = E.parentNode;
    }
  }
  E.className = E.className.substr(0, 4);
}

