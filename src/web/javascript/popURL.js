/**
 * Displays url in a new window
 * @arg1 = filename / url
 * @arg2 = title of window
 * @arg3 = width of window
 * @arg4 = height of window
 * @arg5 = allow resize (yes/no)
 * @arg6 = show scroll bars (yes/no)
 */
function popURL(filename, title, width, height, resize, bars) {
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open(filename, title, params);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popURLReturn(filename, returnUrl, title, width, height, resize, bars) {
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  alert("one");
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open(filename + "&return=" + escape(returnUrl), title, params);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
  alert("two");
}

function popURLCampaign(filename, title, width, height, resize, bars) {
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  
  filename = filename + "&source=" + document.searchForm.contactSource.options[document.searchForm.contactSource.selectedIndex].value; 
  
  var newwin=window.open(filename, title, params);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

