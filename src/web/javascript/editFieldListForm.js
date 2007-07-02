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
                               if(formFieldList.customerFieldDisplayList.options[j].value == formFieldList.customerFieldList.options[i].id)
                           {
                               alert("Field is already exists");
                               return false;
                           }
                         }
                         var text = formFieldList.customerFieldList.options[i].text;
                         var Id = formFieldList.customerFieldList.options[i].id;
                         formFieldList.customerFieldDisplayList.options[mLenDispList]=new Option (text,Id);
                         formFieldList.customerFieldDisplayList.options[mLenDispList].id = formFieldList.customerFieldList.options[i].id;
                         formFieldList.customerFieldList.options[i].text ="Empty";
                         mLenDispList = formFieldList.customerFieldDisplayList.length;
             }
         }
         for (i=0; i<formFieldList.customerFieldList.options.length; i++) {
             if(formFieldList.customerFieldList.options[i].text=="Empty") {
                 formFieldList.customerFieldList.options[i]=null;
                 --i;
             }
    }
   return true;
  }
function moveUp() {
		var formFieldDisplayList = document.fieldList.customerFieldDisplayList;
		var selectIndex = formFieldDisplayList.selectedIndex;
		if (selectIndex != -1 && selectIndex != 0) {
			var selectedOptionValue = formFieldDisplayList.options[selectIndex].value;
			var selectedOptionText = formFieldDisplayList.options[selectIndex].text;
			var previousOptionValue = formFieldDisplayList.options[selectIndex -1].value;
			var previousOptionText = formFieldDisplayList.options[selectIndex -1].text;
			formFieldDisplayList.options[selectIndex].value = previousOptionValue;
			formFieldDisplayList.options[selectIndex].text = previousOptionText;
			formFieldDisplayList.options[selectIndex - 1].value = selectedOptionValue;
			formFieldDisplayList.options[selectIndex - 1].text = selectedOptionText;

			formFieldDisplayList.options[selectIndex - 1].selected = true;
			formFieldDisplayList.options[selectIndex].selected = false;
		}
		return;
	}

	function moveDown() {

		var formFieldDisplayList = document.fieldList.customerFieldDisplayList;
		var selectIndex = formFieldDisplayList.selectedIndex;
		var noOfOptions = formFieldDisplayList.length;
		if (selectIndex != -1 && selectIndex != (noOfOptions - 1)) {

			var selectedOptionValue = formFieldDisplayList.options[selectIndex].value;
			var selectedOptionText = formFieldDisplayList.options[selectIndex].text;
			var nextOptionValue = formFieldDisplayList.options[selectIndex + 1].value;
			var nextOptionText = formFieldDisplayList.options[selectIndex + 1].text;
			formFieldDisplayList.options[selectIndex].value = nextOptionValue;
			formFieldDisplayList.options[selectIndex].text = nextOptionText;
			formFieldDisplayList.options[selectIndex + 1].value = selectedOptionValue;
			formFieldDisplayList.options[selectIndex + 1].text = selectedOptionText;
        	formFieldDisplayList.options[selectIndex + 1].selected = true;
			formFieldDisplayList.options[selectIndex].selected = false;
		}
		return;
	}

-->