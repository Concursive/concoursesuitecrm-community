/**
 * Displays a option list in a new window
 * @arg1 = form name of the object being modified
 * @arg2 = element name of the date entry field 
 */
 
 
function popList(formname, element) {
  return popList(formname, element, 'en', 'US');
}

function popList(formname, element, language, country) {
  var optionVal = eval("document." + formname + "." + element + ".value");
  var filename = ('list_values.jsp?action=popup&form=' + formname + '&element=' + element + '&optionVal=' + optionVal+ '&language=' + language + '&country=' + country);
  var posx = 0;
  var posy = 0;
  posx = (screen.width - 500)/2;
  posy = (screen.height - 450)/2;
  var newwin=window.open(filename, 'popOptionList', 'WIDTH=500,HEIGHT=300,RESIZABLE=yes,SCROLLBARS=no,STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenx=' + posx + ',screeny=' + posy);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}
