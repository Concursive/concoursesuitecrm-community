  function updateCategory() {
    var sel = document.forms['projectMemberForm'].elements['selDirectory'];
    if (sel.options.length > 0 && sel.options.selectedIndex != -1) {
      items = "";
      var value = sel.options[sel.selectedIndex].value;
      hideSpan("select2Span");
      if (value.indexOf("email|") == 0) {
        showSpan("emailSpan");
        showSpan("emailSpan2");
        hideSpan("listSpan");
        hideSpan("listSpan2");
        hideSpan("select1SpanProject");
        hideSpan("select1SpanDepartment");
        document.projectMemberForm.email.focus();
      } else {
        hideSpan("emailSpan");
        hideSpan("emailSpan2");
        var sel2 = document.forms['projectMemberForm'].elements['selDepartment'];
        sel2.options.length = 0;
        var sel3 = document.forms['projectMemberForm'].elements['selTotalList'];
        sel3.options.length = 0;
        showSpan("listSpan");
        showSpan("listSpan2");
        if (value.indexOf("dept|") == 0) {
          hideSpan("select1SpanProject");
          hideSpan("select1SpanAccountType");
          showSpan("select1SpanDepartment");
        } 
        if (value.indexOf("acct|") == 0){
          hideSpan("select1SpanDepartment");
          hideSpan("select1SpanProject");
          showSpan("select1SpanAccountType");
        }
        if (value.indexOf("my|") == 0) {
          hideSpan("select1SpanDepartment");
          hideSpan("select1SpanAccountType");
          showSpan("select1SpanProject");
        }
        var url = "ProjectManagementTeamList.do?command=Projects&source=" + escape(value);
        window.frames['server_commands'].location.href=url;
      }
    }
  }
  
  function updateItemList() {
    items = "";
    var sel = document.forms['projectMemberForm'].elements['selDirectory'];
    var sel2 = document.forms['projectMemberForm'].elements['selDepartment'];
    if (sel.options.length > 0 && sel.options.selectedIndex != -1 &&
        sel2.options.length > 0 && sel2.options.selectedIndex != -1) {
      var value = sel.options[sel.selectedIndex].value;
      var value2 = sel2.options[sel2.selectedIndex].value;
      var url = "ProjectManagementTeamList.do?command=Items&source=" + escape(value) + "|" + value2;
      window.frames['server_commands'].location.href=url;
    }
    showSpan("select2Span");
  }
  
  function initList(userId) {
    items += "|" + userId + "|";
  }
  
  function addEmail(form) {
    if (form.email.value.length == 0 || !checkEmail(form.email.value)) {
      alert("Email address could not be added.\r\n" +
            "Please make sure the email address is entered correctly");
    } else {
      form.selProjectList.options.length += 1;
      form.selProjectList.options[form.selProjectList.options.length - 1] = new Option(form.email.value, "-1");
      form.email.value = "";
      document.projectMemberForm.email.focus();
    }
  }
  
  function addList(form) {
    if (form.selTotalList.options.length > 0 && form.selTotalList.options.selectedIndex != -1) {
      var index = form.selTotalList.selectedIndex;
      var copyValue = form.selTotalList.options[index].value;
      var copyText = form.selTotalList.options[index].text;
      //add to list
      form.selTotalList.options[index] = null;
      form.selProjectList.options.length += 1;
      form.selProjectList.options[form.selProjectList.options.length - 1] = new Option(copyText, copyValue);
      form.selTotalList.selectedIndex = -1;
      //update the array
      for (i = 0; i < vectorUserId.length; i++) {
        if (copyValue == vectorUserId[i]) {
          vectorState[i] = "1";
        }
      }
    }
  }
  
  function removeList(form) {
    if (form.selProjectList.options.length > 0 && form.selProjectList.options.selectedIndex != -1) {
      var index = form.selProjectList.selectedIndex;
      var copyValue = form.selProjectList.options[index].value;
      var copyText = form.selProjectList.options[index].text;
      //if exists in team list then move it, otherwise delete it
      form.selProjectList.options[index] = null;
      if (items.indexOf("|" + copyValue + "|") > -1) {
        form.selTotalList.options.length += 1;
        form.selTotalList.options[form.selTotalList.options.length - 1] = new Option(copyText, copyValue);
      }
      form.selProjectList.selectedIndex = -1;
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
    for (i = 0; i < form.selProjectList.options.length; i++) {
      var found = false;
      for (j = 0; j < vectorUserId.length; j++) {
        if (form.selProjectList.options[i].value == vectorUserId[j]) {
          found = true;
        }
      }
      if (!found) {
        if (form.insertMembers.value.length > 0) {
          form.insertMembers.value += "|";
        }
        if (form.selProjectList.options[i].value == -1) {
          form.insertMembers.value += form.selProjectList.options[i].text;
        } else {
          form.insertMembers.value += form.selProjectList.options[i].value;
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
