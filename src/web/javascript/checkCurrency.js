/**
 * Checks to see if valid currency text is entered
 * @arg1 = text to check
 */
function checkCurrency(valueIn) {
  var val = valueIn.replace(/\$/g,"");
  val = val.replace(/,/g,"");
  val = val.toLowerCase();
  if (val.charAt(0) == '=') val = val.substr(1);

  if (val.substr(1).search(/[\+\-\*\/]/g) != -1) {
    var c = val.charAt(0);
    if(val.charAt(0) >='a' && val.charAt(0) <='z') {
      value = "error";
    } else {
      try {
        val = eval(val);
      } catch (e) { 
        val = "error"; 
      }
      autoplace = false;
    }
  }
  
  numval = parseFloat(val);
  if (isNaN(numval) || Math.abs(numval)>1.0e+10) {
      return false;
  } else {
    if(autoplace && val.indexOf(".") == -1) numval/=100;
    if (type == "currency") 
        field.value = format_currency(numval);
    else
        field.value = format_currency(numval, true);
    return true;
  }
}
