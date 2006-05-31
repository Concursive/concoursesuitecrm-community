  function submitBatch(thisForm, selectName, chkName) {
    var frm = document.forms[thisForm];
    var len = document.forms[thisForm].elements.length;
    var i = 0;
    var selected = false;
    for( i=0 ; i<len ; i++) {
      if (frm.elements[i].name.indexOf(chkName) != -1)
        if (frm.elements[i].checked)
          selected = true;
    }
    
    if (selected == false) {
      alert(label("check.oneitem.selected","Please select at least one item to proceed"));
      return;
    }
    
    var select = document.getElementById(selectName);
    frm.action = select.options[select.selectedIndex].value;
    frm.submit();
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

