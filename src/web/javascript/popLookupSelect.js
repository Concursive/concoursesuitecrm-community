function popLookupSelectMultiple(displayFieldId,highLightedId,table) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = "";
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + "|";
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
          }
          
  }
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSelector&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&table=' + table + '', title, params);
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
  
  var selectedIds = "";
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + "|";
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
          }
          
  }
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ExternalContacts.do?command=PopupSelector&reset=true&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&category=' +  category + '&contactId=' + contactId , title, params);
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
  
  var selectedIds = "";
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + "|";
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
          }
          
  }
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ProductsCatalog.do?command=PopupSelector&reset=true&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&contractId=' + contractId , title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


  function setParentList(selectedValues,selectedIds,listType,displayFieldId){
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

