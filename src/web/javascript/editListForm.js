<!--
// need to set datatype for editing (searchField[index].type)
function addValues(){
    var searchList = document.modifyList.selectedList;
    var count = 0;
    var x = 0;
    
    if (document.modifyList.newValue.value != null && document.modifyList.newValue.value.length > 0) {
            searchText = document.modifyList.newValue.value;
            var newOption = searchText;
            searchList.options[searchList.length] = new Option(newOption, ("*" + searchText));
    } else {
             alert ("You must provide a value for the new option");
    }
    
    document.modifyList.newValue.value = "";
    document.modifyList.newValue.focus();
    
    return true;
}

function removeValues(){
	var searchList = document.modifyList.selectedList;
	
	var tempArray = new Array();
	var offset = 0;
	var count = 0;
	var searchCriteria = new Array();
	
	if (searchCriteria.length != searchList.length) {
          for (count=0; count<(searchList.length); count++) {
                  searchCriteria[count] = searchList.options[count].value;
          }
	}
	
	if (searchList.length == 0) {
          alert("Nothing to remove");
	} else if (searchList.options.selectedIndex == -1) {
          alert("An item needs to be selected before it can be removed");
  	} else {
          searchCriteria[searchList.selectedIndex] = "skip";
          searchList.options[searchList.selectedIndex] = null;
          for (i=0; i < searchCriteria.length; i++){
            if (searchCriteria[i] == "skip") {
              offset = 1;
              delete searchCriteria[i];
              tempArray[i] = searchCriteria[i+offset];
            } else if (i+offset == searchCriteria.length) {
              break;
            } else {
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
//  Select All options
// -------------------------------------------------------------------
function selectAllOptions(obj) {
  var size = obj.options.length;
  var i = 0;
  
  if (size == 0) {
    alert ("You must have at least one item in this list.");
    return false;
  }
  
  for (i=0;i<size;i++) {
    obj.options[i].selected = true;
    document.modifyList.selectNames.value = document.modifyList.selectNames.value + "^" + obj.options[i].text;
  }
  
  return true;
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
	
function sortSelect (select, compareFunction) {
  if (!compareFunction) compareFunction = compareText;
  var options = new Array (select.options.length);
  for (var i = 0; i < options.length; i++)
    options[i] = new Option (
          select.options[i].text,
          select.options[i].value,
          select.options[i].defaultSelected,
          select.options[i].selected
    );
  
  options.sort(compareFunction);
  select.options.length = 0;
  for (var i = 0; i < options.length; i++) select.options[i] = options[i];
}

function compareText (option1, option2) {
  return option1.text < option2.text ? -1 : option1.text > option2.text ? 1 : 0;
}
	
-->
