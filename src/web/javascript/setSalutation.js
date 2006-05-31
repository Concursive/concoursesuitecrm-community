/**
 * Sets the salutation field with the text from the salutation drop list 
 * @arg1 = email address to check
 */
function fillSalutation(formName){
  var index = document.forms[formName].listSalutation.selectedIndex;
  var text = document.forms[formName].listSalutation.options[index].text;
  if (index > 0) {
    document.forms[formName].nameSalutation.value = text;
  } else {
    document.forms[formName].nameSalutation.value = "";
  }
}

