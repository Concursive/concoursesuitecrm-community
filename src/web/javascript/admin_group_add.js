  function updateCategory() {
    var sel = document.forms['addUserGroup'].elements['selDirectory'];
    if (sel.options.length > 0 && sel.options.selectedIndex != -1) {
      items = "";
      var value = sel.options[sel.selectedIndex].value;
      hideSpan("select2Span");
      var sel2 = document.forms['addUserGroup'].elements['selDepartment'];
      sel2.options.length = 0;
      var sel3 = document.forms['addUserGroup'].elements['selTotalList'];
      sel3.options.length = 0;
      showSpan("listSpan");
      showSpan("listSpan2");
      if (value.indexOf("dept|") == 0) {
        hideSpan("select1SpanDocumentStore");
        hideSpan("select1SpanAccountType");
        showSpan("select1SpanDepartment");
      } else if (value.indexOf("acct|") == 0){
        hideSpan("select1SpanDocumentStore");
        hideSpan("select1SpanDepartment");
        showSpan("select1SpanAccountType");
      }
      var url = "UserGroups.do?command=Users&source=" + escape(value);
      window.frames['server_commands'].location.href=url;
    }
  }
  
  function updateItemList() {
    items = "";
    var sel = document.forms['addUserGroup'].elements['selDirectory'];
    var sel2 = document.forms['addUserGroup'].elements['selDepartment'];
    var siteId = document.forms['addUserGroup'].siteId.value;
    if (sel.options.length > 0 && sel.options.selectedIndex != -1 &&
        sel2.options.length > 0 && sel2.options.selectedIndex != -1) {
      var value = sel.options[sel.selectedIndex].value;
      var value2 = sel2.options[sel2.selectedIndex].value;
      var url = "UserGroups.do?command=Items&siteId="+siteId+"&source=" + escape(value) + "|" + value2;
      window.frames['server_commands'].location.href=url;
    }
    showSpan("select2Span");
  }
  
  function initList(userId) {
    items += "|" + userId + "|";
  }
  
  function addList(form) {
    if (form.selTotalList.options.length > 0 && form.selTotalList.options.selectedIndex != -1) {
      var index = form.selTotalList.selectedIndex;
      var copyValue = form.selTotalList.options[index].value;
      var copyText = form.selTotalList.options[index].text;
      //add to list
      form.selTotalList.options[index] = null;
      form.selectedUserList.options.length += 1;
      form.selectedUserList.options[form.selectedUserList.options.length - 1] = new Option(copyText, copyValue);
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
    if (form.selectedUserList.options.length > 0 && form.selectedUserList.options.selectedIndex != -1) {
      var index = form.selectedUserList.selectedIndex;
      var copyValue = form.selectedUserList.options[index].value;
      var copyText = form.selectedUserList.options[index].text;
      //if exists in team list then move it, otherwise delete it
      form.selectedUserList.options[index] = null;
      if (items.indexOf("|" + copyValue + "|") > -1) {
        form.selTotalList.options.length += 1;
        form.selTotalList.options[form.selTotalList.options.length - 1] = new Option(copyText, copyValue);
      }
      form.selectedUserList.selectedIndex = -1;
      //update the array
      for (i = 0; i < vectorUserId.length; i++) {
        if (copyValue == vectorUserId[i]) {
          vectorState[i] = "0";
          break;
        }
      }
    }
  }
  
  function resetGroupUsers(form) {
    if (form.selectedUserList.options.length > 0) {
      form.selectedUserList.options.length = 0;
    }
  }

  function resetValues(form) {
    alert("Ask the server to build select and clear values");
  }

