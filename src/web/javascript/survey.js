function popItems(qid,items){
    popURL('CampaignManagerSurvey.do?command=ViewItems&questionid='+qid+'&items='+items,'Item_List','540','250','yes','yes');
}

function processButton(formName, typeSelected){
  if(typeSelected == 4){
    document.forms[formName].itemsButton.disabled = false;
  }else{
    document.forms[formName].itemsButton.disabled = true;
  }
}

function editQuestion(questionid){
  document.forms['survey'].questionid.value = questionid;
  document.forms['survey'].action = 'CampaignManagerSurvey.do?command=Modify&auto-populate=true&pg=2' ;
  document.forms['survey'].submit();
}

function delQuestion(questionid){
  if (confirm('Are you sure?')) {
  document.forms['survey'].questionid.value = questionid ;
  document.forms['survey'].action = 'CampaignManagerSurvey.do?command=DeleteQuestion&pg=1' ;
  document.forms['survey'].submit();
  }
}

function moveQuestion(questionid,direction){
  document.forms['survey'].questionid.value = questionid;
  document.forms['survey'].action = 'CampaignManagerSurvey.do?command=MoveQuestion&direction=' + direction + '&pg=1' ;
  document.forms['survey'].submit();
}


function setActionSubmit(actionURL){
  document.forms['survey'].action = actionURL;
  document.forms['survey'].submit();
}


