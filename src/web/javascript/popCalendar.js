/**
 * Displays a calendar in a new window
 * @arg1 = form name of the object being modified
 * @arg2 = element name of the date entry field 
 */
function popCalendar(formname, element) {
  var theDate = eval("document." + formname + "." + element + ".value");
  var filename = ('/month.jsp?action=popup&form=' + formname + '&element=' + element + '&date=' + theDate);
  var newwin=window.open(filename, 'calendar', 'WIDTH=175,HEIGHT=200,RESIZABLE=yes,SCROLLBARS=no,STATUS=0');
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}
