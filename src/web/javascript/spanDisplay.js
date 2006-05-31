function showSpan(thisID) {
  if(document.getElementById(thisID) != null){
    document.getElementById(thisID).style.display = '';
  }
  return true;
}
function hideSpan(thisID) {
  if(document.getElementById(thisID) != null){
    document.getElementById(thisID).style.display = 'none';
  }
  return true;
}
function isSpanVisible(thisID) {
  if(document.getElementById(thisID) != null){
    return document.getElementById(thisID).style.display == '';
  }
}
