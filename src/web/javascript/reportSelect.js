<!--	
	function allValues() {
		var i=0;
		var initialLength = document.generate.fields.options.length;
		
		for(i=0; i<initialLength; i++) {
			document.generate.fields.options[0].selected = true;
			addValue();
		}
		
		return true;
	}
	
	function addValue(){
		var searchList = document.generate.selectedList;
		searchText = document.generate.fields[document.generate.fields.selectedIndex].value;
	
		var newOption = document.generate.fields[document.generate.fields.selectedIndex].text;
		searchList.options[searchList.options.length] = new Option(newOption, (searchText));
		
		document.generate.fields[document.generate.fields.selectedIndex] = null;
		
		return true;
	}
	
	function resetValue() {
		var searchList = document.generate.selectedList;
		var returnList = document.generate.fields;
		
		searchText = searchList[searchList.selectedIndex].value;
		
		var newOption = searchList[searchList.selectedIndex].text;
		returnList.options[returnList.options.length] = new Option(newOption, (searchText));
		
		return true;
	}
	
	function removeValue(){
		var searchList = document.generate.selectedList;
	
		if (searchList.length == 0) {
			alert("Nothing to remove");
		} else if (searchList.selectedIndex == -1) {
			alert("An item needs to be selected before it can be removed");
		} else {
			resetValue();
			searchList.options[searchList.selectedIndex] = null;
		}
		
		return true;
	}
    
	// -------------------------------------------------------------------
	//  Select All options
	// -------------------------------------------------------------------
	function selectAllOptions(obj) {
		var size = obj.options.length;
		var i = 0;
	
		if (size == 0) {
			alert ("You should have at least one item in this list.");
			return false;
		}
	
		for (i=0;i<size;i++) {
			obj.options[i].selected = true;
		}
	
		return true;
	}
-->
