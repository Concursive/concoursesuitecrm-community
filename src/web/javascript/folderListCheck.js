function collectValues()
     {
         var searchList = document.getElementById("selectedList");
         var listLength = searchList.length;
         var graphListLength = document.fieldDisplay.graphType.length;
         var listvalue ="";
         var listText = "";
         var chartType = "";
         var graphId="";
         var totalText = "";
         var totalValue = "";
         var duplicate = false;
         for(k=0; k<graphListLength; k++)
         {
         	if(document.fieldDisplay.graphType.options[k].selected)
         	{
         		graphId = document.fieldDisplay.graphType.options[k].value;
         		chartType = document.fieldDisplay.graphType.options[k].text;
         	}

         }
         for(i=1; i<size; i++){
            if(document.getElementById('checkelement'+i).checked){
                listvalue =  document.getElementById('checkelement'+i).value;
                listText =   document.getElementById('hiddenelement'+i).value;
                if(totalText.length>0){
                   	listText = totalText+", "+listText;
                    listvalue = listvalue+", "+totalValue;
                }
                totalText = listText;
                totalValue = listvalue;
              }
         }
         if(totalText.length > 0 && totalValue.length > 0 &&  chartType.length > 0) {
             listText = totalText+" ("+	chartType + ")";
             listvalue = "{"+totalValue+"}:"+graphId;
                 for(j=0; j<searchList.length; j++){
                      if(searchList.options[j].text == listText){
                          duplicate = true;
                          break;
                      }
                 }
             if(duplicate==false){
                searchList.options[searchList.options.length] = new Option(listText, listvalue);
                 }
             resetCheckBox();
         }else{
             alert("Please select at least one field to add");
         }

  }

  function resetCheckBox(){
              for(i=1; i<size; i++){
              if(document.getElementById('checkelement'+i).checked){
                document.getElementById('checkelement'+i).checked = false;
              }
          }
   }
  function removeValues(){
     var searchList = document.getElementById("selectedList");
     if (searchList.length == 0) {
        alert("Nothing to remove");
     } else if (searchList.selectedIndex == -1) {
        alert("An item needs to be selected before it can be removed");
     } else {
        //resetValue();
        while(searchList.length>0){
               searchList.options[searchList.selectedIndex] = null;
        }
    }
     return true;
  }
 function returnToParent(){
	var searchList = document.getElementById("selectedList");
    var hiddenFieldId = document.getElementById("hiddenFieldId").value;
    var minorAxisParamValues = "";
    for(k=0; k<searchList.length; k++) {
        if(minorAxisParamValues!=null && minorAxisParamValues.length > 0 ){
            minorAxisParamValues = minorAxisParamValues+";"+searchList.options[k].value;
        }else{
              minorAxisParamValues = searchList.options[k].value;
        }
        opener.childToParent(searchList.options[k].text, searchList.options[k].value);
    }
    opener.setMinorAxisValues(minorAxisParamValues, hiddenFieldId);
    window.self.close();
}

function childToParent(popUptext, popUpvalue){
    var minorAxisList =  document.getElementById("minoraxisparam");
    minorAxisList.options[minorAxisList.options.length] = new Option(popUptext, popUpvalue);
}

function setMinorAxisValues(minorAxisValues, hiddenFieldId){
    var minorAxisHiddenField =  document.getElementById(hiddenFieldId);
    minorAxisHiddenField.value= minorAxisValues;
}
function setMajorAxis(hiddenFieldId){
     var selectedValue="";
    for(i=0; i < document.forms[0].majoraxisselect.length; i++){
        if(document.forms[0].majoraxisselect[i].checked){
            selectedValue=document.forms[0].majoraxisselect[i].value;
        }
    }
   document.getElementById(hiddenFieldId).value= selectedValue;
}
function clearValues() {
    if (document.getElementById("minoraxisparam") != null) {
        var minorAxisList = document.getElementById("minoraxisparam");
        while (minorAxisList.length > 0) {
            minorAxisList.options[minorAxisList.options.length - 1] = null;
        }
    }
}

function resetFormValues(){
    var majorAxis = document.getElementById("hiddenMajorAxis");
    var majorAxisDiv = document.getElementById("hiddenMajorAxisDiv");
    if(majorAxis!=null && majorAxisDiv!=null){
        var majorAxisField = document.getElementById("hiddenMajorAxis").value;
        var divToChange = document.getElementById("hiddenMajorAxisDiv").value;
        document.getElementById(majorAxisField).value = -1;
        document.getElementById(divToChange).innerHTML = 'None Selected';
    }
    var minorAxisList =  document.getElementById("minoraxisparam");
    if(minorAxisList!=null){
        while(minorAxisList.length > 0){
           minorAxisList.options[minorAxisList.options.length-1] =null;
        }
     }

  var displayLists =  document.getElementById("displayArea");
   if(displayLists!=null){
       document.getElementById("displayArea").value ="";

  }
 }

