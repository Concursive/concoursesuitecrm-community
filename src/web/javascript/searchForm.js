<!--
function field(id, name, type){
	this.id = id
	this.name = name
	this.type = type
}

function operator(id, operand, text){
	this.id = id
	this.displayText = text
	this.operand = operand
}

function reset(form){
	var fieldList = document.searchForm.fieldSelect;
	var operatorList = document.searchForm.operatorSelect;
	var searchValueTxt = document.searchForm.searchValue
	
	fieldList.options.selectedIndex = 0;
	operatorList.options.selectedIndex = 0;
	searchValueTxt.value = searchValueTxt.defaultValue;
}
function updateOperators(){
	operatorList = document.searchForm.operatorSelect;
	fieldSelectIndex = searchField[document.searchForm.fieldSelect.options.selectedIndex].type
	// empty the operator list
	for (i = operatorList.options.length; i >= 0; i--)
		operatorList.options[i]= null;
	// fill operator list with new values
	for (i = 0; i < listOfOperators[fieldSelectIndex].length; i++) {
		operatorList.options[i] = new Option(listOfOperators[fieldSelectIndex][i].displayText, listOfOperators[fieldSelectIndex][i].id)
	}
} // end updateOperators

// need to set datatype for editing (searchField[index].type)
function addValues(){
	var fieldList = document.searchForm.fieldSelect;
	var operatorList = document.searchForm.operatorSelect;
	var searchList = document.searchForm.searchCriteria;
	var count = 0;
	var x=0;
	
	fieldName = fieldList.options[fieldList.selectedIndex].text;
	fieldID = fieldList.options[fieldList.selectedIndex].value;
	operatorName = operatorList.options[operatorList.selectedIndex].text;
	operatorID = operatorList.options[operatorList.selectedIndex].value
	searchText = document.searchForm.searchValue.value;
	var newOption = fieldName + " (" + operatorName + ") " + searchText;
	var newCriteria = fieldID  + "|" + operatorID + "|" + searchText;
	
	if (searchList.length == 0 || searchList.options[0].value == "-1"){
		searchList.options[0] = new Option(newOption)
	}	else {
			if (searchCriteria.length == 0) {
				for (count=0; count<(searchList.length); count++) {
					searchCriteria[count] = searchList.options[count].value;
				}
				
			}
			searchList.options[searchList.length] = new Option(newOption);
		}
		
	searchCriteria[searchCriteria.length] = newCriteria;
	//reset(this.form)
  document.searchForm.searchValue.value = document.searchForm.searchValue.defaultValue;
  document.searchForm.searchValue.focus();
}

function removeValues(){
	var searchList = document.searchForm.searchCriteria
	var tempArray = new Array()
	var offset = 0;
	var count = 0;
	
	
	if (searchCriteria.length != searchList.length) {
		for (count=0; count<(searchList.length); count++) {
			searchCriteria[count] = searchList.options[count].value;
		}
	}
	
	if (searchList.length == 0) {
		alert("Nothing to remove")
	}	else if (searchList.options.selectedIndex == -1) {
    alert("An item needs to be selected before it can be removed");
  } else {
		searchCriteria[searchList.selectedIndex] = "skip";
		searchList.options[searchList.selectedIndex] = null;
		for (i=0; i < searchCriteria.length; i++){
			if (searchCriteria[i] == "skip") {
				offset = 1;
				delete searchCriteria[i];
				tempArray[i] = searchCriteria[i+offset];
			}
			else 
				if (i+offset == searchCriteria.length) {
					break;
				}
				else {
					tempArray[i] = searchCriteria[i+offset];
				}
		}
		delete searchCriteria
		searchCriteria = new Array();
		for (i=0; i < tempArray.length; i++){
			if (tempArray[i] != null) {
				searchCriteria[i] = tempArray[i];
			}
		}
		
	}
}
	
function editValues(){
	var i;
	searchList = document.searchForm.searchCriteria;
	fieldList = document.searchForm.fieldSelect;
	operatorList = document.searchForm.operatorSelect;
	searchValueTxt = document.searchForm.searchValue
	tmpString = searchList.options[searchList.selectedIndex].text;
	tmpArray = tmpString.split("|")
	for (i=0; i < fieldList.options.length; i++){
		if (tmpArray[0] == fieldList.options[i].text){
			fieldList.selectedIndex = i
		}
	}
	searchValueTxt.value = tmpArray[2];
}

function saveValues(){
	var criteria = "";
	var count=0;
	var searchList = document.searchForm.searchCriteria;
	
	if (searchList.options.length == 0 || searchList.options[0].value == "-1"){
		criteria = "";
	} else {
		if (searchCriteria.length != searchList.length) {
			delete searchCriteria
			searchCriteria = new Array();
		
			for (count=0; count<(searchList.length); count++) {
				searchCriteria[count] = searchList.options[count].value;
			}
		}
	
		for (i = 0; i < searchCriteria.length; i++){
			criteria += searchCriteria[i];
			criteria += "^";
		}
		
		document.searchForm.searchCriteriaText.value = criteria;
	}
}
-->
