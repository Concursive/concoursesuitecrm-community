/**
 * Displays a confirmation to the user
 * @arg1 = URL to forward to if confirmation returns true
 */
function confirmCheckboxes(which) {
	var pass=false
	for(i=0;i<which.length;i++) {
		var tempobj = which.elements[i]
		if (tempobj.type=="checkbox"&&tempobj.checked==true) {
			pass=true
		}
	}
	
	if(!pass) {
		alert("Please select one or more groups first")
		return false
	} else {
		return true
	}
}

