function deleteOptions(optionListId){
     var frm = document.getElementById(optionListId);
     frm.innerHTML="";
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
    
    
    function insertOptionGroup(text, optionListId) {
      var frm = document.getElementById(optionListId);
      var optGroup = document.createElement('optgroup');
      optGroup.label = text;
      frm.appendChild(optGroup);
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
  
  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }
