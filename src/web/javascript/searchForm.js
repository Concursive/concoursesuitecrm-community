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

function addValueFromChild(thisID, thisName){
	var newOption = "Contact Name (is) " + thisName;
	var searchList = document.searchForm.searchCriteria;
	var newCriteria = "9|1|" + thisID;
	
	if (searchList.length == 0 || searchList.options[0].value == "-1") {
		document.searchForm.searchCriteria.options[0] = new Option(newOption);
	} else {
			if (searchCriteria.length == 0) {
				for (count=0; count<(searchList.length); count++) {
					searchCriteria[count] = searchList.options[count].value;
				}
				
			}
			searchList.options[searchList.length] = new Option(newOption);
	}
		
	searchCriteria[searchCriteria.length] = newCriteria;
	searchForm.searchValue.value = document.searchForm.searchValue.defaultValue;
	searchForm.searchValue.focus();
	return true;
}


function ShowSpan(thisID)
{
	isNS4 = (document.layers) ? true : false;
	isIE4 = (document.all && !document.getElementById) ? true : false;
	isIE5 = (document.all && document.getElementById) ? true : false;
	isNS6 = (!document.all && document.getElementById) ? true : false;
	
	if (isNS4){
	elm = document.layers[thisID];
	}
	else if (isIE4) {
	elm = document.all[thisID];
	}
	else if (isIE5 || isNS6) {
	elm = document.getElementById(thisID);
	elm.style.visibility="visible";
	}
	
	return true;
   
}

function setText(obj) {
	document.searchForm.searchValue.value = obj.options[obj.selectedIndex].text;
	return true;
}

function HideSpan(thisID)
{
	isNS4 = (document.layers) ? true : false;
	isIE4 = (document.all && !document.getElementById) ? true : false;
	isIE5 = (document.all && document.getElementById) ? true : false;
	isNS6 = (!document.all && document.getElementById) ? true : false;

	if (isNS4){
	elm = document.layers[thisID];
	}
	else if (isIE4) {
	elm = document.all[thisID];
	}
	else if (isIE5 || isNS6) {
	elm = document.getElementById(thisID);
	elm.style.visibility="hidden";
	}
	
	return true;
   
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
	fieldSelectIndex = searchField[document.searchForm.fieldSelect.selectedIndex].type
	
	if (document.searchForm.fieldSelect.selectedIndex == 7) {
		javascript:ShowSpan('new0');
		document.searchForm.searchValue.value = document.searchForm.typeId.options[document.searchForm.typeId.selectedIndex].text;
	} else if (document.searchForm.fieldSelect.selectedIndex == 3) {
		javascript:HideSpan('new0');
		javascript:ShowSpan('new1');
		document.searchForm.searchValue.value = "";
	} else {
		javascript:HideSpan('new0');
		javascript:HideSpan('new1');
		document.searchForm.searchValue.value = "";
	}
	
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
  
  if (document.searchForm.searchValue.value.length == 0) {
    alert("You must specify search text in order to add criteria.");
    return false;
  }
	
	fieldName = fieldList.options[fieldList.selectedIndex].text;
	fieldID = fieldList.options[fieldList.selectedIndex].value;
	operatorName = operatorList.options[operatorList.selectedIndex].text;
	operatorID = operatorList.options[operatorList.selectedIndex].value
	searchText = document.searchForm.searchValue.value;
	
	var typeValue = document.searchForm.typeId.value;
	var newOption = fieldName + " (" + operatorName + ") " + searchText + " [" + document.searchForm.contactSource.options[document.searchForm.contactSource.selectedIndex].text + "]";
  
  //var newOption = fieldName + " (" + operatorName + ") " + searchText;

	
	if (document.searchForm.fieldSelect.selectedIndex != 7) {
		var newCriteria = fieldID  + "|" + operatorID + "|" + searchText + "|" + document.searchForm.contactSource.options[document.searchForm.contactSource.selectedIndex].value;
    //var newCriteria = fieldID  + "|" + operatorID + "|" + searchText;
	} else {
		var newCriteria = fieldID + "|" + operatorID + "|" + typeValue + "|" + document.searchForm.contactSource.options[document.searchForm.contactSource.selectedIndex].value;
    //var newCriteria = fieldID + "|" + operatorID + "|" + typeValue;
	}
	
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

function removeValue(index) {
	var searchList = document.searchForm.searchCriteria;
	var tempArray = new Array();
	var offset = 0;
	var count = 0;
	
	
	if (searchCriteria.length != searchList.length) {
		for (count=0; count<(searchList.length); count++) {
			searchCriteria[count] = searchList.options[count].value;
		}
	}      
  
    searchCriteria[index] = "skip";
		searchList.options[index] = null;
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
		
    for (j=0; j<searchCriteria.length; j++) {
      //searchCriteria[j] = null;
      delete searchCriteria[j];
    }
    
    searchCriteria = new Array();
    
		for (i=0; i < tempArray.length; i++){
			if (tempArray[i] != null) {
				searchCriteria[i] = tempArray[i];
			}
		}
}
  
  

function removeValues(){
	var searchList = document.searchForm.searchCriteria;
	var tempArray = new Array();
	var offset = 0;
	var count = 0;
	
	
	if (searchCriteria.length != searchList.length) {
		for (count=0; count<(searchList.length); count++) {
			searchCriteria[count] = searchList.options[count].value;
		}
	}
	
	if (searchList.length == 0) {
		alert("Nothing to remove");
	}	else if (searchList.selectedIndex == -1) {
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
		
    for (j=0; j<searchCriteria.length; j++) {
      delete searchCriteria[j];
    }
    
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
          
          for (j=0; j<searchCriteria.length; j++) {
            //searchCriteria[j] = null;
            delete searchCriteria[j];
          }
          
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
