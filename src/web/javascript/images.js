function changeImages() {
  var img = document[changeImages.arguments[0]];
  if(img.id == '1'){
    img.id = "0";
    img.src = changeImages.arguments[1];
   }
  else{
    img.id = "1";
    img.src = changeImages.arguments[2];
  }
}

