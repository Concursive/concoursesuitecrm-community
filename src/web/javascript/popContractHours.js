function popContractHours(hiddenFieldId1, displayFieldId1, hiddenFieldId2, displayFieldId2, hiddenFieldId3, displayFieldId3) {
  title  = 'AdjustmentHours';
  width  =  '575';
  height =  '280';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  
  var firstParam = 'ServiceContractHoursAdjustor.do?command=AdjustHours';
  firstParam = firstParam + '&displayFieldId1='+displayFieldId1+'&hiddenFieldId1='+hiddenFieldId1;
  if (hiddenFieldId2)
    firstParam = firstParam + '&displayFieldId2='+displayFieldId2+'&hiddenFieldId2='+hiddenFieldId2;
  if (hiddenFieldId3)
    firstParam = firstParam + '&displayFieldId3='+displayFieldId3+'&hiddenFieldId3='+hiddenFieldId3;

  var newwin=window.open( firstParam, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function setHours(hiddenValues, hiddenFields, displayValues, displayFields){
  var i = 0;
  for(i = 0; ; i++){
    if (!hiddenFields[i])
      break;
    opener.document.getElementById(hiddenFields[i]).value = hiddenValues[i];
    if (hiddenFields[i] != "totalHoursRemaining"){
      opener.changeDivContent(displayFields[i], displayValues[i]);
    }else{
      opener.document.getElementById(displayFields[i]).value = displayValues[i];
    }
  }
  
  if (opener.document.getElementById('netRemainingHours')){
    var netHours = 0;
    netHours = parseFloat(opener.document.getElementById('totalHoursRemaining').value) + parseFloat(opener.document.getElementById('adjustmentHours').value);
    opener.changeDivContent('netRemainingHours', netHours);
  }
}
