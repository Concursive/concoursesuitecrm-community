  function updateCategory() {
    var sel = document.forms['documentStoreMemberForm'].elements['selDirectory'];
    if (sel.options.length > 0 && sel.options.selectedIndex != -1) {
      items = "";
      var value = sel.options[sel.selectedIndex].value;
      hideSpan("select2Span");
      var sel2 = document.forms['documentStoreMemberForm'].elements['selDepartment'];
      sel2.options.length = 0;
      var sel3 = document.forms['documentStoreMemberForm'].elements['selTotalList'];
      sel3.options.length = 0;
      showSpan("listSpan");
      showSpan("listSpan2");
      hideSpan("searchSpan");
      if (value.indexOf("dept|") == 0) {
        hideSpan("select1SpanDocumentStore");
        hideSpan("select1SpanAccountType");
        showSpan("select1SpanDepartment");
      } else if (value.indexOf("acct|") == 0){
        hideSpan("select1SpanDocumentStore");
        hideSpan("select1SpanDepartment");
        showSpan("select1SpanAccountType");
        hideSpan("listSpan");
        showSpan("searchSpan");
      } else {
        hideSpan("select1SpanDepartment");
        hideSpan("select1SpanAccountType");
        showSpan("select1SpanDocumentStore");
      }
      var url = "DocumentStoreManagementTeamList.do?command=DocumentStore&memberType=user&source=" + escape(value);
      window.frames['server_commands'].location.href=url;
    }
  }
  
  function updateItemList() {
    items = "";
    var sel = document.forms['documentStoreMemberForm'].elements['selDirectory'];
    var sel2 = document.forms['documentStoreMemberForm'].elements['selDepartment'];
    if (sel.options.length > 0 && sel.options.selectedIndex != -1 &&
        sel2.options.length > 0 && sel2.options.selectedIndex != -1) {
      var value = sel.options[sel.selectedIndex].value;
      var value2 = sel2.options[sel2.selectedIndex].value;
      var url = "DocumentStoreManagementTeamList.do?command=Items&source=" + escape(value) + "|" + value2;
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
      form.selDocumentStoreList.options.length += 1;
      form.selDocumentStoreList.options[form.selDocumentStoreList.options.length - 1] = new Option(form.email.value, "-1");
      form.email.value = "";
      document.documentStoreMemberForm.email.focus();
    }
  }
  
  function addList(form) {
    if (form.selTotalList.options.length > 0 && form.selTotalList.options.selectedIndex != -1) {
      var index = form.selTotalList.selectedIndex;
      var copyValue = form.selTotalList.options[index].value;
      var copyText = form.selTotalList.options[index].text;
	    var sel2 = form.elements['selAccountList'];
  	  if (sel2.options.length > 0 && sel2.options.selectedIndex != -1) {
  	  	var text2 = sel2.options[sel2.selectedIndex].text;
  	  	copyText = copyText + '(' + text2 + ')';
  	  }
      //add to list
      form.selTotalList.options[index] = null;
      form.selDocumentStoreList.options.length += 1;
      form.selDocumentStoreList.options[form.selDocumentStoreList.options.length - 1] = new Option(copyText, copyValue);
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
    if (form.selDocumentStoreList.options.length > 0 && form.selDocumentStoreList.options.selectedIndex != -1) {
      var index = form.selDocumentStoreList.selectedIndex;
      var copyValue = form.selDocumentStoreList.options[index].value;
      var copyText = form.selDocumentStoreList.options[index].text;
      //if exists in team list then move it, otherwise delete it
      form.selDocumentStoreList.options[index] = null;
      if (items.indexOf("|" + copyValue + "|") > -1) {
        form.selTotalList.options.length += 1;
        form.selTotalList.options[form.selTotalList.options.length - 1] = new Option(copyText, copyValue);
      }
      form.selDocumentStoreList.selectedIndex = -1;
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
    for (i = 0; i < form.selDocumentStoreList.options.length; i++) {
      var found = false;
      for (j = 0; j < vectorUserId.length; j++) {
        if (form.selDocumentStoreList.options[i].value == vectorUserId[j]) {
          found = true;
        }
      }
      if (!found) {
        if (form.insertMembers.value.length > 0) {
          form.insertMembers.value += "|";
        }
        if (form.selDocumentStoreList.options[i].value == -1) {
          form.insertMembers.value += form.selDocumentStoreList.options[i].text;
        } else {
          form.insertMembers.value += form.selDocumentStoreList.options[i].value;
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
  function searchAccounts(form) {
  	if (form.search.value.length == 0) {
  		alert("Please enter account search string");
  	} else {
	  	var sel = document.forms['documentStoreMemberForm'].elements['selDirectory'];
	    if (sel.options.length > 0 && sel.options.selectedIndex != -1) {
  			var value = sel.options[sel.selectedIndex].value;
	  		var url = "DocumentStoreManagementTeamList.do?command=DocumentStore&source=" + escape(value)+"&search=" + escape(form.accountSearch.value);
  	  	window.frames['server_commands'].location.href=url;
  	  }
  	}
  }
  
  function updateContactList() {
    items = "";
    var sel = document.forms['documentStoreMemberForm'].elements['selDirectory'];
    var sel2 = document.forms['documentStoreMemberForm'].elements['selAccountList'];
    if (sel.options.length > 0 && sel.options.selectedIndex != -1 &&
        sel2.options.length > 0 && sel2.options.selectedIndex != -1) {
      var value = sel.options[sel.selectedIndex].value;
      var value2 = sel2.options[sel2.selectedIndex].value;
      var id = document.forms['documentStoreMemberForm'].elements['documentStoreId'].value;
      var url = "DocumentStoreManagementTeamList.do?command=Items&source=" + escape(value) + "|" + id + "|" + value2;
      window.frames['server_commands'].location.href=url;
    }
    showSpan("select2Span");
  }
  
  