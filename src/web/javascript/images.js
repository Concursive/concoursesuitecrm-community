function changeImages() {
  var img = document[changeImages.arguments[0]];
  if(img.id == '1'){
    img.id = "0";
    img.src = "/MyTasks.do?command=ProcessImage&id=box.gif|gif|"+changeImages.arguments[1]+"|"+img.id;
   }
  else{
    img.id = "1";
    img.src = "/MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|"+changeImages.arguments[1]+"|"+img.id;
  }
}

function changeDivContent(divName, divContents) {
		if(document.layers){
			// Netscape 4 or equiv.
			divToChange = document.layers[divName];
			divToChange.document.open();
			divToChange.document.write(divContents);
			divToChange.document.close();
		} else if(document.all){
			// MS IE or equiv.
			divToChange = document.all[divName];
			divToChange.innerHTML = divContents;
		} else if(document.getElementById){
			// Netscape 6 or equiv.
			divToChange = document.getElementById(divName);
			divToChange.innerHTML = divContents;
		}
	}
