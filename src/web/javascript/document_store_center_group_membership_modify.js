  function updateCategory() {
    var sel = document.forms['documentStoreMemberForm'].elements['selDirectory'];
    if (sel.options.length > 0 && sel.options.selectedIndex != -1) {
      items = "";
      var value = sel.options[sel.selectedIndex].value;
      hideSpan("select2Span");
      if (value.indexOf("email|") == 0) {
        hideSpan("listSpan");
        hideSpan("select1SpanDocumentStore");
        hideSpan("select1SpanDepartment");
        document.documentStoreMemberForm.email.focus();
      } else {
        var sel2 = document.forms['documentStoreMemberForm'].elements['selDepartment'];
        sel2.options.length = 0;
        showSpan("listSpan");
        if (value.indexOf("dept|") == 0) {
          hideSpan("select1SpanRole");
          showSpan("select1SpanDepartment");
        } else {
          hideSpan("select1SpanDepartment");
          showSpan("select1SpanRole");
        }
        var url = "DocumentStoreManagementTeamList.do?command=DocumentStore&memberType=group&source=" 
                  + escape(value);
        window.frames['server_commands'].location.href=url;
      }
    }
  }
  function initList(userId) {
    items += "|" + userId + "|";
  }
  function setGroupType(type){
    groupType=type;
  }
  
  function addList(form) {
    if (form.selDepartment.options.length > 0 && form.selDepartment.options.selectedIndex != -1) {
      var index = form.selDepartment.selectedIndex;
      var copyValue = form.selDepartment.options[index].value;
      var copyText = form.selDepartment.options[index].text;
      if (groupType.indexOf("role") > -1) {
        if (copyText.indexOf(label("Documents.team.Role","(Role)")) == -1){
          copyText+= " " + label("Documents.team.role","(Role)");
        }
      }else{
        if (copyText.indexOf(label("Documents.team.Dept","(Dept)")) == -1){
          copyText+= " " + label("Documents.team.dept","(Dept)");
        }
      }
      //add to list
      form.selDepartment.options[index] = null;
      form.selRoleList.options.length += 1;
      form.selRoleList.options[form.selRoleList.options.length - 1] = new Option(copyText, copyValue);
      form.selDepartment.selectedIndex = -1;
      //update the array
      for (i = 0; i < vectorUserId.length; i++) {
        if (copyValue == vectorUserId[i]) {
          vectorState[i] = "1";
        }
      }
    }
  }
  
  function removeList(form) {
    if (form.selRoleList.options.length > 0 && form.selRoleList.options.selectedIndex != -1) {
      var index = form.selRoleList.selectedIndex;
      var copyValue = form.selRoleList.options[index].value;
      var copyText = form.selRoleList.options[index].text;
      var tmpItemType = "";
      if (copyValue.indexOf("-R") > -1) {
        tmpItemType = "role";
      }else{
        tmpItemType = "department";
      }
      //if exists in team list then move it, otherwise delete it
      form.selRoleList.options[index] = null;
      if ((items.indexOf("|" + copyValue + "|") > -1) && (tmpItemType.indexOf(groupType) > -1)){
        form.selDepartment.options.length += 1;
        form.selDepartment.options[form.selDepartment.options.length - 1] = new Option(copyText, copyValue);
      }
      form.selRoleList.selectedIndex = -1;
      //update the array
      for (i = 0; i < vectorUserId.length; i++) {
        if (copyValue == vectorUserId[i]) {
          vectorState[i] = "0";
          break;
        }
      }
    }
  }
  
  function resetValues(form) {
    alert("Ask the server to build select and clear values");
  }
  
  function checkForm(form) {
    form.insertMembers.value = "";
    form.deleteMembers.value = "";
    //add only if not on list already
    for (i = 0; i < form.selRoleList.options.length; i++) {
      var found = false;
      for (j = 0; j < vectorUserId.length; j++) {
        if (form.selRoleList.options[i].value == vectorUserId[j]) {
          found = true;
        }
      }
      if (!found) {
        if (form.insertMembers.value.length > 0) {
          form.insertMembers.value += "|";
        }
        if (form.selRoleList.options[i].value == -1) {
          form.insertMembers.value += form.selRoleList.options[i].text;
        } else {
          form.insertMembers.value += form.selRoleList.options[i].value;
        }
      }
    }
    //check deletes
    for (j = 0; j < vectorUserId.length; j++) {
      if (vectorState[j] == "0") {
        if (form.deleteMembers.value.length > 0) {
          form.deleteMembers.value += "|";
        }
        form.deleteMembers.value += vectorUserId[j];
      }
    }
    return true;
  }
