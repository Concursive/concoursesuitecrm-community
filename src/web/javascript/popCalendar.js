/**
 * Displays a calendar in a new window
 * @arg1 = form name of the object being modified
 * @arg2 = element name of the date entry field 
 */
function popCalendar(formname, element) {
  var theDate = eval("document." + formname + "." + element + ".value");
  var filename = ('/month.jsp?action=popup&form=' + formname + '&element=' + element + '&date=' + theDate);
  var posx = 0;
  var posy = 0;
  
  /*
  if (navigator.appName == "Netscape") {
    posx = (screen.width - window.innerWidth)/2;
    posy = (screen.height - window.innerHeight)/2;
  } else {
    posx = (screen.width - document.body.clientWidth - 175)/2;
    posy = (screen.height - document.body.clientHeight - 200)/2;
  }
  */
  
  var posx = (screen.width - 190)/2;
  var posy = (screen.height - 200)/2;
  
  var newwin=window.open(filename, 'popcalendar', 'WIDTH=190,HEIGHT=200,RESIZABLE=yes,SCROLLBARS=no,STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenx=' + posx + ',screeny=' + posy);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}
