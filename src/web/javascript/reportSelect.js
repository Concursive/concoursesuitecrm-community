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
		
		if (document.generate.fields.selectedIndex >= 0) {
			searchText = document.generate.fields[document.generate.fields.selectedIndex].value;
			var newOption = document.generate.fields[document.generate.fields.selectedIndex].text;
			searchList.options[searchList.options.length] = new Option(newOption, (searchText));		
			document.generate.fields[document.generate.fields.selectedIndex] = null;
		} else {
			alert ("You must first select an item to add");
		}
		
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
	
	
	// -------------------------------------------------------------------
// swapOptions(select_object,option1,option2)
//  Swap positions of two options in a select list
// -------------------------------------------------------------------
function swapOptions(obj,i,j) {
	var o = obj.options;
	var i_selected = o[i].selected;
	var j_selected = o[j].selected;
	var temp = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
	var temp2= new Option(o[j].text, o[j].value, o[j].defaultSelected, o[j].selected);
	o[i] = temp2;
	o[j] = temp;
	o[i].selected = j_selected;
	o[j].selected = i_selected;
	}
	
// -------------------------------------------------------------------
// moveOptionUp(select_object)
//  Move selected option in a select list up one
// -------------------------------------------------------------------
function moveOptionUp(obj) {
	// If > 1 option selected, do nothing
	var selectedCount=0;
	for (i=0; i<obj.options.length; i++) {
		if (obj.options[i].selected) {
			selectedCount++;
			}
		}
	if (selectedCount > 1) {
		return;
		}
	// If this is the first item in the list, do nothing
	var i = obj.selectedIndex;
	if (i == 0) {
		return;
		}
	swapOptions(obj,i,i-1);
	obj.options[i-1].selected = true;
	}

// -------------------------------------------------------------------
// moveOptionDown(select_object)
//  Move selected option in a select list down one
// -------------------------------------------------------------------
function moveOptionDown(obj) {
	// If > 1 option selected, do nothing
	var selectedCount=0;
	for (i=0; i<obj.options.length; i++) {
		if (obj.options[i].selected) {
			selectedCount++;
			}
		}
	if (selectedCount > 1) {
		return;
		}
	// If this is the last item in the list, do nothing
	var i = obj.selectedIndex;
	if (i == (obj.options.length-1)) {
		return;
		}
	swapOptions(obj,i,i+1);
	obj.options[i+1].selected = true;
	}
	
	
-->
