/**
 * Checks to see if a valid email address is entered
 * @arg1 = email address to check
 */
function checkEmail(emailin) {
  if (emailin.length > 0){
    if ((emailin.length < 6) ||
        (emailin.indexOf('@',0) < 1) ||
        (emailin.lastIndexOf('@') != emailin.indexOf('@',0)) ||
        (emailin.lastIndexOf('@') > (emailin.length - 5)) ||
        (emailin.lastIndexOf('.') > (emailin.length - 3)) ||
        (emailin.lastIndexOf('.') < (emailin.length - 4)) ||
        (emailin.indexOf('..',0) > -1) ||
        (emailin.indexOf('@.',0) > -1) ||
        (emailin.indexOf('.@',0) > -1) ||
        (emailin.indexOf(',',0) > -1)) {
        return false;
    }
  }
  return true;
}

/**
 *  Code for doing a regular expression validation (TODO: Integrate it later)
    var j = new RegExp();
    j.compile("[A-Za-z0-9._-]+@[^.]+\..+");
    if (!j.test(form["Email-Address"].value)) {
            alert("You must supply a valid email address.");
            return false;
    } 
**/
        
        
