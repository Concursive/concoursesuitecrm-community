/**
 * Checks to see if a valid email address is entered
 * @arg1 = email address to check
 */
function checkEmail(emailin) {
  if (emailin.length > 0){
    if ((emailin.length < 5) ||
        (emailin.indexOf('@',0) < 1) ||
        (emailin.lastIndexOf('@') != emailin.indexOf('@',0)) ||
        (emailin.indexOf('..',0) > -1) ||
        (emailin.indexOf('@.',0) > -1) ||
        (emailin.indexOf('.@',0) > -1) ||
        (emailin.indexOf(',',0) > -1)) {
        return false;
    }
  var i = emailin.search(/^([a-zA-Z0-9_\.\-])+@\w+([\.\-_]?\w+)*/);
  if (i == -1  || emailin.indexOf('.', 0) == -1 || emailin.lastIndexOf('.') >= (emailin.length - 2) || 
        emailin.lastIndexOf('.') < (emailin.length - 5)) {
      return false;
    }
  }
  return true;
}

