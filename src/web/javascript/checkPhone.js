function checkPhone(phonein) {
	var stripped = phonein.replace(/[^0123456789\+]/g, '');
	
	//strip out acceptable non-numeric characters and make sure there is a number entered
	if (stripped.length > 0) {
    if (stripped.indexOf("+") == 0) {
			if ( (stripped.substr(1, ((phonein.length)-1))).indexOf("+") == -1 ) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}else if(phonein.length > 0){
    return false;
  }
	return true;
}


/**
 *  Code for doing a regular expression validation (TODO: Integrate it later)
    j.compile("[0-9]{3}-[0-9]{3}-[0-9]{4}");
    if (!j.test(form["Phone-Number"].value)) {
      alert("You must supply a valid US phone number.");
      return false;
    }
 *
**/
