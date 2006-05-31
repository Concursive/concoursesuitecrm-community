<!--
// need to set datatype for editing (searchField[index].type)
var itemList = new Array();
function addValues(){
  var mode = document.getElementById("addButton").value;
  var insertMode = (label("button.addR","Add >") == document.modifyList.addButton.value);
  var searchList = document.modifyList.selectedList;
  var searchText = document.modifyList.newValue.value;
  var count = 0;
  var x = 0;
  if (checkNullString(searchText)) {
    alert (label("provide.optionvalue","You must provide a value for the new option"));
    document.modifyList.newValue.focus();
    return;
  } else if (checkDuplicate(searchText, mode)) {
    alert(label("label.entryalreadyexists","Entry already exists"));
    document.modifyList.newValue.focus();
    return;
  } else if(searchText.indexOf("\"") > -1){
      alert(label("caution.doublequotes.notallowed",'Double Quotes are not allowed in the description'));
      return;
  }
  if (searchList.length == 0 || searchList.options[0].value == "-1"){
    searchList.options[0] = new Option(searchText, "*"+searchText);
  }	else {
    if (insertMode) {
      searchList.options[searchList.length] = new Option(searchText, ("*" + searchText));
    } else {
      searchList.options[searchList.selectedIndex].text = searchText;
    }
  }
  if (insertMode) {
    itemList[searchList.length-1] = new category('*-1', searchText, 'true');
  } else {
    var temp1 = itemList[searchList.selectedIndex].id;
    itemList[searchList.selectedIndex] = new category(temp1, searchText, 'true');
  }
  document.modifyList.newValue.value = "";
  document.modifyList.addButton.value  = label("button.addR","Add >");
  document.modifyList.newValue.focus();
  return true;
}

function checkDuplicate(description, mode){
  for(i = 0; i < itemList.length ; i++){
    if(itemList[i].description == description){
      if(mode == label("button.addR","Add >") || (mode == label("button.updateR","Update >") && document.modifyList.selectedList.selectedIndex != i)){
        return true;
      }
    }
  }
  return false;
}

function category(id, description, enabled){
  this.id = id;
  this.description = description;
  this.enabled = enabled;
}

function printAll() {
  var message = "";
  var i=0;
  for(i=0;i<itemList.length;i++) {
    message += 'itemList['+i+']:: id = '+itemList[i].id+', desc = '+ itemList[i].description +'\r\n';
  }
  alert(message);
}

function isDuplicate(){
  var searchList = document.modifyList.selectedList;
    for (count=0; count<(searchList.length); count++) {
      if((searchList.options[count].text).toLowerCase() == (document.modifyList.newValue.value).toLowerCase()){
        return true;
      }
    }
  return false;
}

function resetOptions(){
  var searchList = document.modifyList.selectedList;
  if (searchList.options.length > 0) {
    var selectedOption = searchList.options[searchList.selectedIndex];
    var selected = selectedOption.selected;
    document.getElementById("addButton").value  = label("button.addR","Add >");
    document.getElementById("newValue").value = "";
    selectedOption.selected = selected;
  }
}

function removeValues(){
	var searchList = document.modifyList.selectedList;
	
	var tempArray = new Array();
	var offset = 0;
	var count = 0;
	
	if (itemList.length != searchList.length) {
    for (count=0; count<(searchList.length); count++) {
      itemList[count] = new category(searchList.options[count].value, searchList.options[count].text, 'true');
    }
	}
	
	if (searchList.length == 0) {
    alert(label("caution.nothingtoremove","Nothing to remove"));
	} else if (searchList.options.selectedIndex == -1) {
    alert(label("caution.itemneedstobe.selected","An item needs to be selected before it can be removed"));
  } else {
    itemList[searchList.selectedIndex].enabled = 'false';
    searchList.options[searchList.selectedIndex] = null;
    for (i=0; i < itemList.length; i++){
      if (itemList[i].enabled == 'false') {
        offset = 1;
        delete itemList[i];
        if (i+offset != itemList.length) {
          tempArray[i] = itemList[i+offset];
        }
      } else if (i+offset == itemList.length) {
        break;
      } else {
        tempArray[i] = itemList[i+offset];
      }
    }
    delete itemList
    itemList = new Array();
    for (i=0; i < tempArray.length; i++){
      if (tempArray[i] != null) {
        itemList[i] = tempArray[i];
      }
    }
  }
}

function switchToRename() {
  var searchList = document.modifyList.selectedList;
  if (searchList.length == 0) {
    alert(label("label.nothingtorename","Nothing to rename"));
	} else if (searchList.options.selectedIndex == -1) {
    alert(label("select.torename","An item needs to be selected before it can be renamed"));
  } else {
    document.modifyList.newValue.value = searchList.options[searchList.selectedIndex].text;
    document.modifyList.addButton.value  = "Update >";
    document.modifyList.newValue.focus();
  }
}

// -------------------------------------------------------------------
// swapOptions(select_object,option1,option2)
//  Swap positions of two options in a select list
// -------------------------------------------------------------------
function swapOptions(obj,i,j) {
  var o = obj.options;
  if(o.selectedIndex > -1){

  var i_selected = o[i].selected;
  var j_selected = o[j].selected;
  var temp = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
  var temp2= new Option(o[j].text, o[j].value, o[j].defaultSelected, o[j].selected);
  var temp3 = itemList[i].id;
  var temp4 = itemList[i].description;
  o[i] = temp2;
  o[j] = temp;
  o[i].selected = j_selected;
  o[j].selected = i_selected;
  itemList[i] = new category(itemList[j].id,itemList[j].description,'true');
  itemList[j] = new category(temp3,temp4,'true');
  }else{
    alert(label("caution.item.needed","An item needs to be selected"));
  }
}
	
// -------------------------------------------------------------------
//  Select All options
// -------------------------------------------------------------------
function selectAllOptions(obj) {
  var size = obj.options.length;
  var i = 0;
  
  if (size == 0) {
    alert (label("required.oneiteminlist","You must have at least one item in this list."));
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
  if (selectedCount != 1) {
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
  if (selectedCount != 1) {
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
  for (var i = 0; i < options.length; i++) {
    select.options[i] = options[i];
    itemList[i] = new category(select.options[i].value, options[i].text, 'true');
  }
}

function compareText (option1, option2) {
  return option1.text < option2.text ? -1 : option1.text > option2.text ? 1 : 0;
}

function editValues(){
 var tmpList = document.modifyList.selectedList;
  var selectedCount=0;
  for (i=0; i<tmpList.options.length; i++) {
    if (tmpList.options[i].selected) {
      selectedCount++;
    }
  }
  if (selectedCount != 1) {
   alert(label("caution.item.needed","An item needs to be selected"));
    return;
  }
  if(selectedCount > 1){
   alert(label("caution.singleitem.needed","A single item needs to be selected"));
   return;
  }
  if(tmpList.options[0].value != "-1"){
    var selectedValue = tmpList.options[tmpList.selectedIndex].value;
    var itemIndex = 0;
    for(i=0; i < itemList.length;i++){
      if (selectedValue == itemList[i].id) {
        document.getElementById("newValue").value = itemList[i].description;
        break;
      }
    }
    document.getElementById("addButton").value  = label("button.updateR","Update >");
  }
}

-->
