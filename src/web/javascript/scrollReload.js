/**
 * Appends page x/y to url for reloading to same position
 */
function scrollReload(filename) {
  var posx = 0;
  var posy = 0;
  var d = null;
  if (document.documentElement && document.documentElement.scrollTop) {
    d = document.documentElement;
  } else {
    d = document.body;
  }
  if (window.scrollX) {
    posx = window.scrollX;
  } else {
    posx = d.scrollLeft;
  }
  if (window.scrollY) {
    posy = window.scrollY;
  } else {
    posy = d.scrollTop;
  }
  if (posx > 0 || posy > 0) {
    filename = filename + "&scrollLeft=" + posx + "&scrollTop=" + posy;
  }
  window.location.href = filename;
}
