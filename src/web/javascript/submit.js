  function sendMessage() {
    formTest = true;
    message = "";
      
    if(document.forms[0].listView.options[0].value == "none"){
		    message += "- Select at least one recipient\r\n";
        formTest = false;
    }
    if(document.forms[0].subject.value == ""){
		    message += "- Enter a subject\r\n";
        formTest = false;
    }
    if(document.forms[0].body.value == ""){
        message += "- Enter a message in the body\r\n";
        formTest = false;
    }
    if (formTest) {
      return true;
    } else {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
  
  function validateTask() {
    formTest = true;
    message = "";
    if(document.forms[0].description.value == ""){
		    message += "- Enter a title\r\n";
        formTest = false;
    }
    
    if (formTest) {
      return true;
    } else {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
  
  
  function setParentList(recipientEmails,recipientIds,formName,field){
	  if(recipientEmails.length == 0 && field == "list"){
      opener.deleteOptions(formName);
		  opener.insertOption("None Selected","",formName);
	  }
    var i = 0;
    if(field == "list"){
      opener.deleteOptions(formName);
      for(i=0; i < recipientEmails.length; i++) {
          opener.insertOption(recipientEmails[i],"",formName);
      }
    }
    else if(field == "contactsingle"){
        opener.document.forms[formName].contact.value = recipientIds[i];
        opener.changeDivContent('changecontact',recipientEmails[i]);
    }
    else{
        opener.document.forms[formName].owner.value = recipientIds[i];
        opener.changeDivContent('changeowner',recipientEmails[i]);
    }
  }
  
  
  
   function deleteOptions(thisForm){
   var frm = document.forms[thisForm];
   while (frm.listView.options.length>0){
      deleteIndex=frm.listView.options.length-1;
      frm.listView.options[deleteIndex]=null;
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


    function ValidateForm(thisForm,chkName){
      var frm = document.forms[thisForm];
      var len = document.forms[thisForm].elements.length;
      var i=0;
      for( i=0 ; i<len ; i++) {
        if ((frm.elements[i].name.indexOf(chkName)!=-1)){
          if(frm.elements[i].checked==1){
            return true
          }
        }
      }
      alert("No record selected")
      return false;
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

    
    
    
    function insertOption(text,value,thisForm){
      var frm = document.forms[thisForm];
     if (frm.listView.selectedIndex>0){
       insertIndex=frm.listView.selectedIndex;
     }
     else{
       insertIndex= frm.listView.options.length;
     }
     frm.listView.options[insertIndex] = new Option(text,value);
    }
    
    
function keepCount(chkName,thisForm) {
var NewCount = 0;
var frm = document.forms[thisForm];
var len = document.forms[thisForm].elements.length;
      var i=0;
      for( i=0 ; i<len ; i++) {
        if ((frm.elements[i].name.indexOf(chkName)!=-1)){
          if(frm.elements[i].checked==1){
            NewCount++;
          }
        }
      }
  if (NewCount == 2){
    alert('Pick just one please')
    frm; return false;
  }
}


function setField(formField,thisValue,thisForm) {
        var frm = document.forms[thisForm];
        var len = document.forms[thisForm].elements.length;
        var i=0;
        for( i=0 ; i<len ; i++) {
                if (frm.elements[i].name.indexOf(formField)!=-1) {
                  if(thisValue){
                    frm.elements[i].value = "1";
                  }
                  else {
                    frm.elements[i].value = "0";
                  }
              }
        }
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
  
  
  
