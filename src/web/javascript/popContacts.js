function popContactsListSingle(hiddenFieldId,displayFieldId) {
  title  = 'Contacts';
  width  =  '700';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('/ContactsList.do?command=ContactList&listType=single&flushtemplist=true&selectedIds='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId, title, params);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popContactsListMultiple(displayFieldId,highLightedId) {
  title  = 'Contacts';
  width  =  '700';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('/ContactsList.do?command=ContactList&listType=list&flushtemplist=true&selectedIds='+highLightedId+'&displayFieldId='+displayFieldId, title, params);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popContactsListMultipleCampaign(displayFieldId,highLightedId) {
  title  = 'Contacts';
  width  =  '700';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('/ContactsList.do?command=ContactList&listType=list&campaign=true&flushtemplist=true&selectedIds='+highLightedId+'&displayFieldId='+displayFieldId, title, params);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function setParentList(recipientEmails,recipientIds,listType,displayFieldId,hiddenFieldId){
	  if(recipientEmails.length == 0 && listType == "list"){
      opener.deleteOptions(displayFieldId);
		  opener.insertOption("None Selected","",displayFieldId);
	  }
    var i = 0;
    if(listType == "list"){
      opener.deleteOptions(displayFieldId);
      for(i=0; i < recipientEmails.length; i++) {
          opener.insertOption(recipientEmails[i],"",displayFieldId);
      }
    }
    else if(listType == "single"){
        opener.document.getElementById(hiddenFieldId).value = recipientIds[i];
        opener.changeDivContent(displayFieldId,recipientEmails[i]);
    }
  }
  
  function setParentListCampaign(recipientEmails,recipientIds,listType,displayFieldId,hiddenFieldId){
	  //if(recipientEmails.length == 0 && listType == "list"){
    //  opener.deleteOptions(displayFieldId);
		//  opener.insertOption("None Selected","",displayFieldId);
	  //}
    
    var i = 0;
    var searchList = opener.document.searchForm.searchCriteria;

    if(listType == "list"){
      if (searchList.length == 0 || searchList.options[0].value == "-1") {
              opener.deleteOptions(displayFieldId);
      } else {
        if (opener.searchCriteria.length == 0) {
                for (count=0; count<(searchList.length); count++) {
                        opener.searchCriteria[count] = searchList.options[count].value;
                }
        }
      }
      
      for(i=0; i < recipientEmails.length; i++) {
          var newCriteria = "9|1|" + recipientIds[i];
          opener.insertOption("Contact Name (is) " + recipientEmails[i],"",displayFieldId);
          opener.searchCriteria[opener.searchCriteria.length] = newCriteria;
			}
      
		}
		
    else if(listType == "single"){
        opener.document.getElementById(hiddenFieldId).value = recipientIds[i];
        opener.changeDivContent(displayFieldId,recipientEmails[i]);
    }
    
  }
  
  
  function SetChecked(val,chkName,thisForm,browser) {
      if((val==0 && ValidateForm(thisForm,chkName)) || val==1){
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
