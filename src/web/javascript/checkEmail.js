/**
 * Checks to see if a valid email address is entered
 * @arg1 = email address to check
 */
function checkEmail(emailin) {
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
  return true;
}
