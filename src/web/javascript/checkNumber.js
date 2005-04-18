function checkNumber(number) {
	var validNumbers = "0123456789.,";
	if(number.length > 0){
    for(i = 0; i < number.length; i++){
      if(validNumbers.indexOf(number.charAt(i)) == -1){
        return false
      }
    }
  }
  return true;
}


function checkRealNumber(number) {
	var validNumbers = "-0123456789.,";
	if(number.length > 0){
    for(i = 0; i < number.length; i++){
      if(validNumbers.indexOf(number.charAt(i)) == -1){
        return false
      }
    }
  }
  return true;
}

function checkNaturalNumber(number) {
	var validNumbers = "0123456789,";
  var invalidNumbers = "0,";
  var counter = 0;
	if(number.length > 0){
    for(i = 0; i < number.length; i++){
      if(validNumbers.indexOf(number.charAt(i)) == -1){
        return false
      }
      if(invalidNumbers.indexOf(number.charAt(i)) != -1) {
        counter = counter + 1;
      }
    }
    if (counter == number.length) {
      return false;
    }
  }
  return true;
}
