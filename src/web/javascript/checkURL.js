/**
 * Checks to see if a valid URL is entered
 * @arg1 = url to check
 */
function checkURL(url){
  if (url.length > 0){
    url = padURL(url);
    var i = url.search(/^[A-Za-z]+:\/\/\w+([\.-]?\w+)*/);
    if (i == -1  || (url.indexOf('.', 0) == -1)) {
      return false;
    }
  }
  return true;
}

function padURL(url){
  if(url.indexOf('http://', 0) == -1){
    url = "http://" + url;
  }
  return url;
}
