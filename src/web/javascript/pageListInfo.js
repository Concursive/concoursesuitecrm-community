  function offsetsubmit(formName, newOffset) {
    document.forms[formName].offset.value = newOffset;
    document.forms[formName].submit();
  }

  function setFieldSubmit(formField,thisValue,thisForm) {
        var frm = document.forms[thisForm];
        var len = document.forms[thisForm].elements.length;
        var i=0;
        for( i=0 ; i<len ; i++) {
                if (frm.elements[i].name.indexOf(formField)!=-1) {
                  frm.elements[i].value=thisValue;
              }
        }
        frm.submit();
  }

