function spinRight(thisID) {
  isNS4 = (document.layers) ? true : false;
  isIE4 = (document.all && !document.getElementById) ? true : false;
  isIE5 = (document.all && document.getElementById) ? true : false;
  isNS6 = (!document.all && document.getElementById) ? true : false;
  if (isNS4){
    elm = document.layers[thisID];
  } else if (isIE4) {
    elm = document.all[thisID];
  } else if (isIE5 || isNS6) {
    elm = document.getElementById(thisID);
    if (elm.value < spinMax) {
      elm.value = parseInt(elm.value) + 1;
    } else {
      elm.value = spinMax;
    }
  }
}
function spinLeft(thisID) {
  isNS4 = (document.layers) ? true : false;
  isIE4 = (document.all && !document.getElementById) ? true : false;
  isIE5 = (document.all && document.getElementById) ? true : false;
  isNS6 = (!document.all && document.getElementById) ? true : false;
  if (isNS4){
    elm = document.layers[thisID];
  } else if (isIE4) {
    elm = document.all[thisID];
  } else if (isIE5 || isNS6) {
    elm = document.getElementById(thisID);
    if (elm.value > spinMin) {
      elm.value = parseInt(elm.value) - 1;
    } else {
      elm.value = spinMin;
    }
  }
}
