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
  
  function setParentList(a){
    opener.deleteOptions();
	  if(a.length == 0){
		    createOption("None Selected","");
	  }
    var i = 0;
    for(i=0; i < a.length; i++) {
        opener.insertOption(a[i],"");
	   }
  }
  
  
   function deleteOptions(){
     
   while (document.newMessageForm.listView.options.length>0){
      deleteIndex=document.newMessageForm.listView.options.length-1;
      document.newMessageForm.listView.options[deleteIndex]=null;
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

    function insertOption(text,value){
     if (document.forms[0].listView.selectedIndex>0){
       insertIndex=opener.document.newMessageForm.listView.selectedIndex;
     }
     else{
       insertIndex= document.forms[0].listView.options.length;
     }
     document.forms[0].listView.options[insertIndex] = new Option(text,value);
    }
