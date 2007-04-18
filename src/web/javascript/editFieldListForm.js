<!--

function moveToList(){

  	var formFieldList = document.fieldList;
	var SelectedIndex = formFieldList.customerFieldDisplayList.options.selectedIndex;
	if (SelectedIndex == -1) {
		alert("Please select atleast one Field to Move.");
		return;
	}
	var numItems = formFieldList.customerFieldList.length;
	for (i=0; i<formFieldList.customerFieldDisplayList.options.length; i++) {
		if(formFieldList.customerFieldDisplayList.options[i].selected) {
			             for(var j=0;j<formFieldList.customerFieldList.options.length;j++){
                               if(formFieldList.customerFieldDisplayList.options[i].text == formFieldList.customerFieldList.options[j].text)
                           {
                               formFieldList.customerFieldDisplayList.options[i]=null;
                                --i;
                               return false;
                           }  
                        }
				var text = formFieldList.customerFieldDisplayList.options[i].text;
				formFieldList.customerFieldList.options[numItems]=new Option (text,text);
				formFieldList.customerFieldList.options[numItems].value = formFieldList.customerFieldDisplayList.options[i].value;
				formFieldList.customerFieldDisplayList.options[i].text ="Empty" ;
			}
		numItems=formFieldList.customerFieldList.length;
	}

	for (i=0; i<formFieldList.customerFieldDisplayList.options.length; i++) {
		if(formFieldList.customerFieldDisplayList.options[i].text=="Empty") {
			formFieldList.customerFieldDisplayList.options[i]=null;
			--i;
		}
	}
	return;
  }

function moveToDisplayList() {

         var formFieldList = document.fieldList;
             SelectedIndex = 0;
                 var mLenDispList = formFieldList.customerFieldDisplayList.length;
                 SelectedIndex	 = formFieldList.customerFieldList.options.selectedIndex;
                 if (SelectedIndex == -1) {
                     alert("Please select atleast one Field to Move.");
                     return;
                 }
                 var numItems = formFieldList.customerFieldList.length;
                     for (i=0; i<numItems; i++) {
                     if(formFieldList.customerFieldList.options[i].selected) {
                            for(var j=0;j<formFieldList.customerFieldDisplayList.options.length;j++){
                               if(formFieldList.customerFieldDisplayList.options[j].text == formFieldList.customerFieldList.options[i].text)
                           {
                               alert("Field is already exists");
                               return false;
                           }

                         }

                            var text = formFieldList.customerFieldList.options[i].text;
                            formFieldList.customerFieldDisplayList.options[mLenDispList]=new Option (text,text);
                            formFieldList.customerFieldDisplayList.options[mLenDispList].value = formFieldList.customerFieldList.options[i].value;
                            formFieldList.customerFieldList.options[i].text ="Empty";
                            mLenDispList = formFieldList.customerFieldDisplayList.length;
             }
         }
         for (i=0; i<formFieldList.customerFieldList.options.length; i++) {
             if(formFieldList.customerFieldList.options[i].text=="Empty") {
                 formFieldList.customerFieldList.options[i]=null;
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
