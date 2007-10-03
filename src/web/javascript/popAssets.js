function popAssetListSingle(hiddenFieldId1, displayFieldId1, params, hiddenFieldId2, displayFieldId2, orgId, contractId) {
  title = 'Assets';
  width = '575';
  height = '400';
  resize = 'yes';
  bars = 'yes';
  var posx = (screen.width - width) / 2;
  var posy = (screen.height - height) / 2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if (params != '') {
    params = '&' + params;
  }
  if (!orgId || orgId == '-1') {
    orgId = document.getElementById('orgId').value;
  }

  if (!contractId || contractId == '-1') {
    contractId = document.getElementById('contractId').value;
  }
  if (!orgId || orgId == '-1') {
    alert("An organization needs to be selected first");
  } else {
    var firstParam = 'AssetSelector.do?command=ListAssets&listType=single&reset=true&orgId=' + orgId + '&contractId=' + contractId + '&previousSelection=' + document.getElementById(hiddenFieldId1).value + '&displayFieldId1=' + displayFieldId1 + '&hiddenFieldId1=' + hiddenFieldId1;
    if ((hiddenFieldId2) && (hiddenFieldId2 != -1)) {
      firstParam = firstParam + '&displayFieldId2=' + displayFieldId2 + '&hiddenFieldId2=' + hiddenFieldId2;
    }

    var newwin = window.open(firstParam + params, title, windowParams);
    newwin.focus();
    if (newwin != null) {
      if (newwin.opener == null)
        newwin.opener = self;
    }
  }
}

function popAssetListSingle2(hiddenFieldId1, displayFieldId1, params, hiddenFieldId2, displayFieldId2, orgId, contractId, subId) {
  title = 'Assets';
  width = '575';
  height = '400';
  resize = 'yes';
  bars = 'yes';
  var posx = (screen.width - width) / 2;
  var posy = (screen.height - height) / 2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if (params != '') {
    params = '&' + params;
  }
  if (!orgId || orgId == '-1') {
    orgId = document.getElementById('orgId').value;
  }

  if (!contractId || contractId == '-1') {
    contractId = document.getElementById('contractId').value;
  }
  if (!orgId || orgId == '-1') {
    alert("An organization needs to be selected first");
  } else {
    var firstParam = 'AssetSelector.do?command=ListAssets2&listType=single&reset=true&orgId=' + orgId + '&contractId=' + contractId + '&subId=' + subId + '&previousSelection=' + document.getElementById(hiddenFieldId1).value + '&displayFieldId1=' + displayFieldId1 + '&hiddenFieldId1=' + hiddenFieldId1;
    if ((hiddenFieldId2) && (hiddenFieldId2 != -1)) {
      firstParam = firstParam + '&displayFieldId2=' + displayFieldId2 + '&hiddenFieldId2=' + hiddenFieldId2;
    }

    var newwin = window.open(firstParam + params, title, windowParams);
    newwin.focus();
    if (newwin != null) {
      if (newwin.opener == null)
        newwin.opener = self;
    }
  }
}

function setAssetList(assetIds, assetNumbers, scIds, scNumbers, listType, displayFieldId1, hiddenFieldId1, displayFieldId2, hiddenFieldId2) {
  if (assetNumbers.length == 0 && listType == "list") {
    opener.deleteOptions(displayFieldId1);
    opener.insertOption("None Selected", "", displayFieldId1);
  }
  var i = 0;
  if (listType == "list") {
    opener.deleteOptions(displayFieldId1);
    for (i = 0; i < assetNumbers.length; i++) {
      opener.insertOption(assetNumbers[i], assetIds[i], displayFieldId1);
    }
  } else if (listType == "single") {

    opener.document.getElementById(hiddenFieldId1).value = assetIds[i];
    opener.changeDivContent(displayFieldId1, assetNumbers[i]);
    if (opener.document.getElementById('assetSerialNumber')) {
      opener.document.getElementById('assetSerialNumber').value = assetNumbers[i];
    }

    if (hiddenFieldId2) {
      if (scIds[i] != "-1") {
        if (scIds[i] != opener.document.getElementById(hiddenFieldId2).value) {
          if (opener.document.getElementById('productId')) {
            opener.document.getElementById('productId').value = -1;
          }
          if (opener.document.getElementById('productSku')) {
            opener.document.getElementById('productSku').value = 'None Selected';
          }
          if (opener.document.getElementById('addLaborCategory')) {
            opener.changeDivContent('addLaborCategory', 'None Selected');
          }
        }
        opener.document.getElementById(hiddenFieldId2).value = scIds[i];
        opener.changeDivContent(displayFieldId2, scNumbers[i]);
        if (opener.document.getElementById('serviceContractNumber')) {
          opener.document.getElementById('serviceContractNumber').value = scNumbers[i];
        }
      }
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

