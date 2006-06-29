function popProductCategoriesListSingle(hiddenFieldId, displayFieldId) {
  popCategoriesListSingle(hiddenFieldId, displayFieldId, '');
}

function popProductCategoriesListSingle(hiddenFieldId, displayFieldId, params) {
  title = 'ProductCategories';
	width = '700';
	height = '425';
	resize = 'yes';
	bars = 'yes';
	var posx = (screen.width - width)/2;
	var posy = (screen.width - width)/2;
	var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	if(params != null && params != ""){
    params = '&' + params;
  }
  var newwin=window.open('ProductCategorySelector.do?command=ListProductCategories&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId+'&categoryId=-1'+ params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popIceProductMultiCategoriesList(popupUrl,categoryIds) {
  title = 'ProductCategories';
	width = '500';
	height = '425';
	resize = 'yes';
	bars = 'yes';
	var posx = (screen.width - width)/2;
	var posy = (screen.width - width)/2;
	var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;

  var newwin=window.open(popupUrl+"&selected="+categoryIds, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popProductCategoriesListSingleExclude(excludeIds, hiddenFieldId, displayFieldId, params) {
	title = 'ProductCategories';
	width = '700';
	height = '425';
	resize = 'yes';
	bars = 'yes';
	var posx = (screen.width - width)/2;
	var posy = (screen.width - width)/2;
	var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	if(params != null && params != ""){
    params = '&' + params;
  }
  var ignoreIds = "";
  for (count=0; count < excludeIds.length; ++count) {
    if (ignoreIds.length > 0) {
      ignoreIds = ignoreIds + "|";
    }
    ignoreIds = ignoreIds + excludeIds[count];
  }
  var newwin=window.open('ProductCategorySelector.do?command=ListProductCategories&ignoreIds='+ignoreIds+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId+'&categoryId=-1'+ params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popProductCategoriesListMultiple(existingIds, params) {
  title  = 'Product_Category';
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
  var newwin=window.open('ProductCategorySelector.do?command=ListProductCategories&previousSelection=' + selectedIds + '&listType=list' + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function setParentList(categoryIds, categoryNames, listType, displayFieldId, hiddenFieldId){
  if(categoryNames.length == 0 && listType == "list"){
    opener.deleteOptions(displayFieldId);
    opener.insertOption("None Selected", "", displayFieldId);
  }
  var i = 0;
  if (listType == "list"){
    opener.deleteOptions(displayFieldId);
    for (i=0; i < categoryNames.length; i++) {
      opener.insertOption(categoryNames[i], categoryIds[i], displayFieldId);
    }
  } else if(listType == "single") {
    opener.document.getElementById(hiddenFieldId).value = categoryIds[i];
    opener.changeDivContent(displayFieldId, categoryNames[i]);
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
