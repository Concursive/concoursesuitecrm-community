/**
 * Checks to see if a date is entered in the 3-20-2001 or 3/20/2001 format
 * @arg1 = date to check
 */

function checkDate(datein) {
  var m = 0, d = 0, y = 0;
  var sep;

  if (datein.indexOf("/") != -1) {
    sep = datein.split("/");
  } else if (datein.indexOf("-") != -1) {
    sep = datein.split("-");
  } else {
    return false;
  }
  
  if (checkDigits(sep[0])) {
    m = parseInt(sep[0],10);
  } else {
    return false;
  }
  if (checkDigits(sep[1])) {
    d = parseInt(sep[1],10);
  } else {
    return false;
  }
  if (sep[2] != null) { 
    if (checkDigits(sep[2]) && sep[2].length > 1) {
      y = parseInt(sep[2],10);
    } else {
      return false;
    }
  } else {
    return false;
  }
  
  if (sep[3] != null) return false;
  
  if ((m <= 0 || m > 12) ||
      (d <= 0 || d > 31) ||
      (y < 0 || (y > 99 && y < 999) || y > 2200)) {
    return false;
  } else {
    return true;
  }
}

function checkDigits(str) {
  var valid = "0123456789";
  for (var i=0; i < str.length; i++) {
    temp = str.substring(i, i+1);
    if (valid.indexOf(temp) == -1) {
      return false;
    }
  }
  return true;
}

function checkAlertDate(datein) {
  var m = 0, d = 0, y = 0;
  var sep;
  var now = new Date();
  
  var theYear = now.getYear() 
  theYear += (theYear < 1900) ? 1900 : 0 
  
  var today = new Date(theYear,(now.getMonth()+1),now.getDate());
  
  if (datein.indexOf("/") != -1) {
    sep = datein.split("/");
  } else if (datein.indexOf("-") != -1) {
    sep = datein.split("-");
  } 
  
  m = parseInt(sep[0],10);
  d = parseInt(sep[1],10);
  y = parseInt(sep[2],10);
  
  var fullYear = today.getFullYear() + '';
  var century = fullYear.substring(0,2);
  if(y > 9 && y <= 99){
    y =  century + y ;
  }else if(y >= 0 && y < 10){
    y =  century + '0' + y ;
  }
  var thisDate = new Date(y, m, d);
  
  var difference = thisDate.getTime() - today.getTime();
  
  var daysDifference = Math.floor(difference/1000/60/60/24);
  
  if ( daysDifference < 0 ) {
	  return false; 
  } else {
	  return true; 
  }
}

/*
if (date1 == date2) return 0
if (date1 < date2) return -1
if (date1 > date2) return 1
*/
function compareDates(date1,date2){
  
  if (date1.indexOf("/") != -1) {
    sep = date1.split("/");
  } else if (date1.indexOf("-") != -1) {
    sep = date1.split("-");
  } 
  m = parseInt(sep[0],10);
  d = parseInt(sep[1],10);
  y = parseInt(sep[2],10);
  var thisDate1 = new Date(y, m, d);


  if (date2.indexOf("/") != -1) {
    sep = date2.split("/");
  } else if (date1.indexOf("-") != -1) {
    sep = date2.split("-");
  } 
  m = parseInt(sep[0],10);
  d = parseInt(sep[1],10);
  y = parseInt(sep[2],10);
  var thisDate2 = new Date(y, m, d);

  var difference = thisDate1.getTime() - thisDate2.getTime();
  var daysDifference = Math.floor(difference/1000/60/60/24);

  if (daysDifference < 0 )
    return -1;
  
  if (daysDifference > 0)
    return 1;
 
  return 0; 
}

