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


function switchClass(thisId){
    if(document.all){
			// MS IE or equiv.
      var tdList = document.all.tags("TR");
      for (i=0; i<tdList.length; i++){
        if(tdList(i).id == thisId){
          if(tdList(i).className == "strike"){
            tdList(i).className = "";
          }
          else{
          tdList(i).className = "strike";
          }
        }
      }
    }
      else if(document.getElementById){
        //Netscape or Mozilla equivalent (DOM not well supported by ie5.5)
        if(document.getElementById(thisId).getAttribute('class') == "strike"){
          document.getElementById(thisId).setAttribute('class','');
        }
        else{
          document.getElementById(thisId).setAttribute('class','strike');
        }
      }
    }
    
    
    function switchStyle(thisId,attribute,searchTag){
      if(document.all){
			// MS IE or equiv.
      var list = document.all.tags(searchTag);
      for (i=0; i<list.length; i++){
        if(list(i).id == thisId){
          if(list(i).style.visibility == "hidden"){
            list(i).style.visibility = "visible";
          }
          else{
          list(i).style.visibility = "hidden";
          }
          switchTrStyle(list(i),'ie','TR');
          }
        }
      }
      else if(document.getElementById){
        //Netscape or Mozilla equivalent (DOM not well supported by ie5.5)
        if(document.getElementById(thisId).getAttribute('style') == "visibility: hidden;"){
          document.getElementById(thisId).setAttribute('style','visibility: visible;');
        }
        else{
          document.getElementById(thisId).setAttribute('style','visibility: hidden;');
        }
        switchTrStyle(document.getElementById(thisId),'ns','TR');
      }
    }
    
    
    function switchTrStyle(E,browser,switchTag){
      if (browser=="ie"){
        while (E.tagName != switchTag){
          E=E.parentElement;
        }
      }
      else{
        while (E.tagName != switchTag){
          E=E.parentNode;
        }
      }
      if(E.style.display == "none"){
        E.style.display = '';
      }
      else{
        E.style.display = 'none';
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
