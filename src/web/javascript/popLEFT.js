/**
 * Displays url in a new window
 * @arg1 = filename / url
 * @arg2 = title of window
 * @arg3 = width of window
 * @arg4 = height of window
 * @arg5 = allow resize (yes/no)
 * @arg6 = show scroll bars (yes/no)
 */

function popLEFT(filename, title, width, height, resize, bars) {
  var posx = (30)
  var posy = (30);
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open(filename, title, params);
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}
