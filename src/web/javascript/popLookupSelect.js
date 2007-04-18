function popLookupSelectMultiple(displayFieldId,hiddenFieldId,table) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = '';
  var selectedDisplays ='';
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + '|';
                          selectedDisplays = selectedDisplays + '|';
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
                  selectedDisplays = selectedDisplays + escape(document.getElementById(displayFieldId).options[count].text);
          }
          
  }
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSelector&hiddenFieldId='+hiddenFieldId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&table=' + table + '&listType=list', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popContactTypeSelectMultiple(displayFieldId, category, contactId) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = '';
  var selectedDisplays ='';
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + '|';
                          selectedDisplays = selectedDisplays + '|';
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
                  selectedDisplays = selectedDisplays + document.getElementById(displayFieldId).options[count].text;
          }
          
  }
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ExternalContacts.do?command=PopupSelector&reset=true&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&category=' +  category + '&contactId=' + contactId , title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popProductCatalogSelectMultiple(displayFieldId, contractId) {
  title  = 'product_catalog_list';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'yes';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = '';
  var selectedDisplays ='';
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + '|';
                          selectedDisplays = selectedDisplays + '|';
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
                  selectedDisplays = selectedDisplays + document.getElementById(displayFieldId).options[count].text;
          }
  }
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ProductsCatalog.do?command=PopupSelector&reset=true&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&contractId=' + contractId +'&listType=list' , title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popQuoteConditionSelectMultiple(displayFieldId,highLightedId,table,quoteId,currentIds,currentValues, type) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = currentIds;
  var selectedDisplays = currentValues;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('QuotesConditions.do?command=PopupSelector&quoteId='+quoteId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&table=' + table + '&type='+ type+ '', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popAssetMaterialsSelectMultiple(displayFieldId,highLightedId,table,assetId,currentIds,currentQuantities) {
  title  = 'asset_material_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = currentIds;
  var selectedQtys = currentQuantities;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('AssetMaterialsSelector.do?command=PopupSelector&assetId='+assetId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionQuantity='+ selectedQtys +'&table=' + table + '', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popUserGroupsSelectMultiple(displayFieldId,highLightedId,table,userId,currentIds,currentValues, type) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = currentIds;
  var selectedDisplays = currentValues;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var userString = '&userId='+userId;
  if (displayFieldId == 'campaign') {
    userString = '&campaignId='+userId;
  }
  var newwin=window.open('UserGroups.do?command=PopupSelector'+userString+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&table=' + table + '&type='+ type+ '', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popUserGroupsListSingle(hiddenFieldId, displayFieldId, params) {
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
  var newwin=window.open('UserGroups.do?command=PopupSingleSelector&listType=single&flushtemplist=true&selectedIds='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popPortfolioCategoryListSingle(hiddenFieldId, displayFieldId, params) {
  title  = 'PortfolioCategory';
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
  var newwin=window.open('PortfolioEditor.do?command=PopupSingleSelector&listType=single&flushtemplist=true&selectedIds='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

 function popFieldsList(folderId,fieldListPropertyName) {
    title  = 'FieldsInFolders';
    width  =  '500';
    height =  '300';
    resize =  'yes';
    bars   =  'yes';

   if(folderId=="-1"){
        alert("A folder needs to be selected before this property can be configured");
        return;
    }else{
    var posx = (screen.width - width)/2;
    var posy = (screen.height - height)/2;
    var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
    var newwin=window.open('RowColumns.do?command=FieldsLists&folderId='+folderId+'&fieldListPropertyName='+fieldListPropertyName+'', title, windowParams);
    newwin.focus();
    if (newwin != null) {
        if (newwin.opener == null)
        newwin.opener = self;
    }
  }
}


function popFolderGraphMajorAxisSelect(folderId, hiddenFieldId, displayFieldId, params) {
  title  = 'MajorAxisField';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  if(folderId=="-1"){
      alert("A folder needs to be selected before this property can be configured");
      return;
  } else{
      var posx = (screen.width - width)/2;
      var posy = (screen.height - height)/2;
      var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
      if(params != null && params != ""){
        params = '&' + params;
      }
      var newwin=window.open('FolderAndFieldSelector.do?command=FieldSelect&listType=single&flushtemplist=true&folderId='+folderId+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
      newwin.focus();
      if (newwin != null) {
        if (newwin.opener == null)
          newwin.opener = self;
      }
   }
}
function popFolderGraphMinorAxisSelect(folderId, hiddenFieldId, displayFieldId, params) {
  title  = 'MinoraxisParameter';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  if(folderId=="-1"){
      alert("A folder needs to be selected before this property can be configured");
      return;
  } else{
      var posx = (screen.width - width)/2;
      var posy = (screen.height - height)/2;
      var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
      if(params != null && params != ""){
        params = '&' + params;
      }
      var newwin=window.open('FolderAndFieldSelector.do?command=FieldListAndType&listType=single&flushtemplist=true&folderId='+folderId+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
      newwin.focus();
      if (newwin != null) {
        if (newwin.opener == null)
          newwin.opener = self;
      }
   }
}

function popFolderGraphRecordRangeSelect(folderId, majorAxisField, hiddenFieldId, displayFieldId, params) {
  title  = 'RecordRangeStartSelect';
  width  =  '450';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if(params != null && params != ""){
    params = '&' + params;
  }
  if(majorAxisField >-1) {
      var newwin=window.open('FolderAndFieldSelector.do?command=RangeSelect&listType=single&flushtemplist=true&folderId='+folderId+'&majorAxisField='+majorAxisField+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);

      newwin.focus();
      if (newwin != null) {
        if (newwin.opener == null)
          newwin.opener = self;
      }
   }
   else{
      alert("Please Select MajorAxis Field");
  }
}

function popActionPlansSelectMultiple(displayFieldId,highLightedId,categoryId,constantId,siteId,currentIds, type) {
  var selectedIds = currentIds;
  window.location.href= 'AdminCategories.do?command=PopupSelector&categoryId='+categoryId+'&siteId='+siteId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&categoryId=' + categoryId +'&constantId='+ constantId + '&type='+ type+ '';
}

function popLookupSelectSingle(displayFieldId, moduleId, lookupId) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSingleSelector&displayFieldId='+displayFieldId+'&lookupId=' + lookupId + '&moduleId=' + moduleId + '', title, params);
  newwin.focus();

  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popLookupSelector(hiddenFieldId, displayFieldId, table, params) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSelector&displayFieldId='+displayFieldId+'&hiddenFieldId=' + hiddenFieldId + '&table=' + table + params, title, windowParams);
  newwin.focus();

  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}



  function setParentValue(displayFieldId, fieldValue) {
    opener.document.getElementById(displayFieldId).value = fieldValue;
    window.close();
  }

  function setParentList(selectedIds,selectedValues,listType,displayFieldId, hiddenFieldId, browserId){
    if(selectedValues.length == 0 && listType == "list"){
      opener.deleteOptions(displayFieldId);
		  opener.insertOption("None Selected","-1",displayFieldId);
      return true;
	  }
    var i = 0;
    if(listType == "list"){
      opener.deleteOptions(displayFieldId);
      for(i=0; i < selectedValues.length; i++) {
          opener.insertOption(selectedValues[i],selectedIds[i],displayFieldId);
      }
    }
    else if(listType == "single"){
        opener.document.getElementById(hiddenFieldId).value = selectedIds[i];
        opener.changeDivContent(displayFieldId,selectedValues[i]);
    }
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
      if(E.checked){
        hL(E,browser);
      }
      else{
        dL(E,browser);
      }
    }
    
    function hL(E,browser){
      if (browser=="ie"){
          while (E.tagName!="TR"){
            E=E.parentElement;
          }
      }
      else{
        while (E.tagName!="TR"){
          E=E.parentNode;
          }
      }
      if(E.className.indexOf("hl")==-1){
         E.className = E.className+"hl";
      }
    }
    
    function dL(E,browser){
      if (browser=="ie"){
        while (E.tagName!="TR"){
          E=E.parentElement;
        }
      }
      else{
        while (E.tagName!="TR"){
          E=E.parentNode;
        }
      }
      E.className = E.className.substr(0,4);
    }
    
    function deleteOptions(optionListId){
     var frm = document.getElementById(optionListId);
     while (frm.options.length>0){
      deleteIndex=frm.options.length-1;
      frm.options[deleteIndex]=null;
     }
   }
    
    
   function insertOption(text,value,optionListId){
     var frm = document.getElementById(optionListId);
      
     if (frm.selectedIndex>0){
       insertIndex=frm.selectedIndex;
     }
     else{
       insertIndex= frm.options.length;
     }
     frm.options[insertIndex] = new Option(text,value);
    }

    
    function changeDivContent(divName, divContents) {
		if(document.layers){
			// Netscape 4 or equiv.
			divToChange = document.layers[divName];
			divToChange.document.open();
			divToChange.document.write(divContents);
			divToChange.document.close();
		} else if(document.all){
			// MS IE or equiv.
			divToChange = document.all[divName];
			divToChange.innerHTML = divContents;
		} else if(document.getElementById){
			// Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
		}
	}
  
  function selectAllOptions(obj) {
    var size = obj.options.length;
    var i = 0;
    
    for (i=0;i<size;i++) {
      if (obj.options[i].value != -1) {
          obj.options[i].selected = true;
      } else {
          obj.options[i].selected = false;
      }
    }
    
    return true;
  }
  
  function setParentHiddenField(hiddenFieldId, strValue) {
    document.getElementById(hiddenFieldId).value = strValue;
  }

