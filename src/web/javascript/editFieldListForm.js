<!--

function moveToList(){

  	var f = document.fieldList;
	var SelectedIndex = f.customerFieldDisplayList.options.selectedIndex;
	if (SelectedIndex == -1) {
		alert("Please select atleast one Field to Move.");
		return;
	}
	var numItems = f.customerFieldList.length;
	for (i=0; i<f.customerFieldDisplayList.options.length; i++) {
		if(f.customerFieldDisplayList.options[i].selected) {
			             for(var j=0;j<f.customerFieldList.options.length;j++){
                               if(f.customerFieldDisplayList.options[i].text == f.customerFieldList.options[j].text)
                           {
                               f.customerFieldDisplayList.options[i]=null;
                                --i;
                               return false;
                           }  
                        }
				var text = f.customerFieldDisplayList.options[i].text;
				f.customerFieldList.options[numItems]=new Option (text,text);
				f.customerFieldList.options[numItems].value = f.customerFieldDisplayList.options[i].value;
				f.customerFieldDisplayList.options[i].text ="Empty" ;
			}
		numItems=f.customerFieldList.length;
	}

	for (i=0; i<f.customerFieldDisplayList.options.length; i++) {
		if(f.customerFieldDisplayList.options[i].text=="Empty") {
			f.customerFieldDisplayList.options[i]=null;
			--i;
		}
	}
	return;
  }

function moveToDisplayList() {

         var f = document.fieldList;
             SelectedIndex = 0;
                 var mLenDispList = f.customerFieldDisplayList.length;
                 SelectedIndex	 = f.customerFieldList.options.selectedIndex;
                 if (SelectedIndex == -1) {
                     alert("Please select atleast one Field to Move.");
                     return;
                 }
                 var numItems = f.customerFieldList.length;
                     for (i=0; i<numItems; i++) {
                     if(f.customerFieldList.options[i].selected) {
                            for(var j=0;j<f.customerFieldDisplayList.options.length;j++){
                               if(f.customerFieldDisplayList.options[j].text == f.customerFieldList.options[i].text)
                           {
                               alert("Field is already exists");
                               return false;
                           }

                         }

                            var text = f.customerFieldList.options[i].text;
                            f.customerFieldDisplayList.options[mLenDispList]=new Option (text,text);
                            f.customerFieldDisplayList.options[mLenDispList].value = f.customerFieldList.options[i].value;
                            f.customerFieldList.options[i].text ="Empty";
                            mLenDispList = f.customerFieldDisplayList.length;
             }
         }
         for (i=0; i<f.customerFieldList.options.length; i++) {
             if(f.customerFieldList.options[i].text=="Empty") {
                 f.customerFieldList.options[i]=null;
                 --i;
             }

    }//sortListValues(document.configureCountriesForm.customerFieldDisplayList);

   return true;
  }
function moveUp() {
		var formVar = document.fieldList.customerFieldDisplayList;
		var selectIndex = formVar.selectedIndex;
		if (selectIndex != -1 && selectIndex != 0) {
			var selectedOptionValue = formVar.options[selectIndex].value;
			var selectedOptionText = formVar.options[selectIndex].text;
			var previousOptionValue = formVar.options[selectIndex -1].value;
			var previousOptionText = formVar.options[selectIndex -1].text;
			formVar.options[selectIndex].value = previousOptionValue;
			formVar.options[selectIndex].text = previousOptionText;
			formVar.options[selectIndex - 1].value = selectedOptionValue;
			formVar.options[selectIndex - 1].text = selectedOptionText;

			formVar.options[selectIndex - 1].selected = true;
			formVar.options[selectIndex].selected = false;
		}
		return;
	}

	function moveDown() {

		var formVar = document.fieldList.customerFieldDisplayList;
		var selectIndex = formVar.selectedIndex;
		var noOfOptions = formVar.length;
		if (selectIndex != -1 && selectIndex != (noOfOptions - 1)) {

			var selectedOptionValue = formVar.options[selectIndex].value;
			var selectedOptionText = formVar.options[selectIndex].text;
			var nextOptionValue = formVar.options[selectIndex + 1].value;
			var nextOptionText = formVar.options[selectIndex + 1].text;
			formVar.options[selectIndex].value = nextOptionValue;
			formVar.options[selectIndex].text = nextOptionText;
			formVar.options[selectIndex + 1].value = selectedOptionValue;
			formVar.options[selectIndex + 1].text = selectedOptionText;
        	formVar.options[selectIndex + 1].selected = true;
			formVar.options[selectIndex].selected = false;
		}
		return;
	}
-->
