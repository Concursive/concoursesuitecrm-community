function showSpan(thisID) {
  document.getElementById(thisID).style.display = '';
  return true;
}
function hideSpan(thisID) {
  document.getElementById(thisID).style.display = 'none';
  return true;
}
function isSpanVisible(thisID) {
  return document.getElementById(thisID).style.display == '';
}
