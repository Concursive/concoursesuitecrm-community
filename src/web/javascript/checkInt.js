function checkInt(number) {
	var validNumbers = "0123456789";
	
	if(number.length > 0){
    for(i = 0; i < number.length; i++){
      if(validNumbers.indexOf(number.charAt(i)) == -1){
        return false
      }
    }
  }
  return true;
}
  
