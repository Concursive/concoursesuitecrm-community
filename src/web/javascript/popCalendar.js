/**
 * Displays a calendar in a new window
 * @arg1 = form name of the object being modified
 * @arg2 = element name of the date entry field 
 */
function popCalendar(formname, element) {
  return popCalendar(formname, element, 'en', 'US');
}
function popCalendar(formname, element, language, country) {
  var theDate = eval("document." + formname + "." + element + ".value");
  var filename = ('month.jsp?action=popup&form=' + formname + '&element=' + element + '&date=' + theDate + '&language=' + language + '&country=' + country);
  var posx = 0;
  var posy = 0;
  posx = (screen.width - 200)/2;
  posy = (screen.height - 200)/2;
  var newwin=window.open(filename, 'popcalendar', 'WIDTH=210,HEIGHT=200,RESIZABLE=yes,SCROLLBARS=no,STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenx=' + posx + ',screeny=' + posy);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}
