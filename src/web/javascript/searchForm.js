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

function popPreview() {
  saveValues();
  title  = '_contact_preview';
  width  =  '700';
  height =  '450';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if (document.searchForm.searchCriteriaText.value.length == 0) {
    alert('Please add contacts or criteria then try again.\r\n'+
      'A group without any criteria will return all contacts');
  } else {
    var newwin=window.open('CampaignManagerGroup.do?command=PopPreview&criteria='+escape(document.searchForm.searchCriteriaText.value)+'&popup=true&reset=true', title, params);
    if (newwin != null) {
      if (newwin.opener == null)
        newwin.opener = self;
    }
  }
}

//this function just sets the search value to the one selected in the list of options
function setText(obj) {
	document.searchForm.searchValue.value = obj.options[obj.selectedIndex].text;
	return true;
}

//apparently used for resetting the form
function reset(form){
	var fieldList = document.searchForm.fieldSelect;
	var operatorList = document.searchForm.operatorSelect;
	var searchValueTxt = document.searchForm.searchValue
	
	fieldList.options.selectedIndex = 0;
	operatorList.options.selectedIndex = 0;
	searchValueTxt.value = searchValueTxt.defaultValue;
}

//used for adding search values to the criteria box
//also manipulates a hidden field that is used to populate the SCL upon submit
function addValues() {
	var fieldList = document.searchForm.fieldSelect;
	var operatorList = document.searchForm.operatorSelect;
	var searchList = document.searchForm.searchCriteria;
	var count = 0;
  var fieldType = "select";
  if (document.searchForm.fieldSelect.selectedIndex != 7 && document.searchForm.fieldSelect.selectedIndex != 8) {
    fieldType = "text";
  }
  
  if (document.searchForm.fieldSelect.selectedIndex == 3) {
    if(!checkDate(document.searchForm.searchValue.value)){
      alert('Please enter a valid Date to continue');
      return;
    }
  }
  
  if (fieldType == "text" && document.searchForm.searchValue.value.length == 0) {
    alert("You must specify search text in order to add criteria.");
    return false;
  }
  
	fieldName = fieldList.options[fieldList.selectedIndex].text;
	fieldID = fieldList.options[fieldList.selectedIndex].value;
	operatorName = operatorList.options[operatorList.selectedIndex].text;
	operatorID = operatorList.options[operatorList.selectedIndex].value
	searchText = document.searchForm.searchValue.value;
	
	var typeValue = document.searchForm.idSelect.value;
	var newOption = fieldName + " (" + operatorName + ") " + searchText + " [" + document.searchForm.contactSource.options[document.searchForm.contactSource.selectedIndex].text + "]";
	
  //TODO: replace these numbers with code values rather than select indexes
	if (document.searchForm.fieldSelect.selectedIndex != 7 && document.searchForm.fieldSelect.selectedIndex != 8) {
		var newCriteria = fieldID  + "|" + operatorID + "|" + searchText + "|" + document.searchForm.contactSource.options[document.searchForm.contactSource.selectedIndex].value;
	} else {
		var newCriteria = fieldID + "|" + operatorID + "|" + typeValue + "|" + document.searchForm.contactSource.options[document.searchForm.contactSource.selectedIndex].value;
	}
  
  for (j=0; j<searchCriteria.length; j++) {
    if(newCriteria.toLowerCase() == searchCriteria[j].toLowerCase()){
      alert('Entry already exists');
      return;
    }
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
	document.searchForm.searchValue.value = document.searchForm.searchValue.defaultValue;
	//document.searchForm.searchValue.focus();
}

//removes the value at a particular index within the criteria select box
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
    } else if (i+offset == searchCriteria.length) {
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
  
//also used to remove value(s) from the search criteria select box
//makes use of the "newer" remove function (above)
function removeValues(){
	var searchList = document.searchForm.searchCriteria;
	if (searchList.length == 0) {
		alert("Nothing to remove");
	}	else if (searchList.selectedIndex == -1) {
    alert("An item needs to be selected before it can be removed");
  } else {
		var index = searchList.selectedIndex;
    removeValue(index);
  }
}

//finalizes and saves all the SCL criteria information before submitting
function saveValues(){
  var criteria = "";
  var count=0;
  var searchList = document.searchForm.searchCriteria;
  if (searchList.options.length == 0 || searchList.options[0].value == "-1"){
    criteria = "";
  } else {
    if (searchCriteria.length != searchList.length) {
      for (j=0; j<searchCriteria.length; j++) {
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
