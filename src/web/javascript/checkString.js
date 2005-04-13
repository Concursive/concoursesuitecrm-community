/* Checks to see if string is empty */
function strtrim(value) {
  return value.replace(/^\s+/,'').replace(/\s+$/,'');
}

function checkNullString(value){
  if (strtrim(value) == ""){
    return true;
  }
  return false;
}
