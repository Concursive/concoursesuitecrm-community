/**
 * Checks to see if a valid URL is entered
 * @arg1 = url to check
 */
function checkURL(url){
 if (url.length > 0){
   url = padURL(url);
   var j = new RegExp(); 
   topLevelCount = url.length - (url.lastIndexOf('.') + 1);
   j.compile("^[A-Za-z]+://[A-Za-z0-9-]+\.[A-Za-z]{2}");
   if (!j.test(url) || (url.indexOf('.', 0) == -1) ||  topLevelCount > 4 || topLevelCount < 2) {
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
