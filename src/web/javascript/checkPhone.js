function checkPhone(phonein) {
	var stripped = phonein.replace(/[^0123456789\+]/g, '');
	
	//strip out acceptable non-numeric characters and make sure there is a number entered
	
	if (stripped.length > 0) {
		if (stripped.indexOf("+") == -1) {
			//must be a valid length
			if (!(stripped.length == 10 || (stripped.length == 11 && stripped.charAt(0) == "1")) ) {
				return false;
			} else {
				return true;
			}
		} else if (stripped.indexOf("+") == 0) {
			if ( (stripped.substr(1, ((phonein.length)-1))).indexOf("+") == -1 ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}else if(phonein.length > 0){
    return false;
  }
	
	return true;
	
}

