<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.utils.DateUtils,org.aspcfs.utils.web.HtmlSelect,java.text.DateFormat" %>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    
    if (form.subject.value == "") { 
      message += "- Blank records cannot be saved\r\n";
      formTest = false;
    }
    
    if (form.callTypeId.value == "0") { 
      message += "- Please specify a type\r\n";
      formTest = false;
    }
    
    if(form.hasFollowup != null && form.hasFollowup.checked){
    if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is entered correctly\r\n";
      formTest = false;
    }
    if ((!form.alertText.value == "") && (form.alertDate.value == "")) { 
      message += "- Please specify an alert date\r\n";
      formTest = false;
    }
    if (form.alertText.value == "") { 
      message += "- Please specify an alert description\r\n";
      formTest = false;
    }
    if (form.alertCallTypeId.value == "0") { 
      message += "- Please specify an alert type\r\n";
      formTest = false;
    }
    //if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
    //  alertMessage += "Alert Date is before today's date\r\n";
    //}
    if (form.reminder[1].checked && !checkNumber(form.reminderId.value)) { 
      message += "- Check that the reminder is entered correctly\r\n";
      formTest = false;
    }
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      if(alertMessage != ""){
        return confirmAction(alertMessage);
      }
      checkFollowup(form);
      return true;
    }
  }
  
  function checkFollowup(form){
    if(form.hasFollowup != null && !form.hasFollowup.checked){
      form.alertText.value = '';
      form.followupNotes.value = '';
      form.alertCallTypeId.value = '0';
      form.alertDate.value = '';
      form.reminderId.value = '-1';
      form.reminderTypeId.value = '0';
      form.owner.value = '-1';
      form.priorityId.value = '-1';
    }else{
      if(form.reminder[0].checked){
        form.reminderId.value = '-1';
        form.reminderTypeId.value = '0';
      }
    }
  }
  
  function toggleSpan(cb, tag) {
    var form = document.forms[0];
    if (cb.checked) {
      if (form.alertText.value == "") {
        form.alertText.value = form.subject.value;
      }
      showSpan(tag);
      if (window.scrollTo) {
        window.scrollTo(0, 1000);
      }
      form.alertCallTypeId.focus();
    } else {
      hideSpan(tag);
    }
  }
  
  function makeSuggestion(){
    if(document.getElementById('resultId').value > -1){
      window.frames['server_commands'].location.href='AccountContactsCalls.do?command=SuggestCall&resultId=' + document.getElementById('resultId').value;
    }
  }
  
  function addFollowup(hours, typeId){
    var form = document.forms[0];
    var selectedIndex = 0;
    var callTypes = form.alertCallTypeId;
    
    for(i = 0; i < callTypes.options.length; i++){
      if(callTypes.options[i].value == typeId){
        selectedIndex = i;
      }
    }
    callTypes.selectedIndex = selectedIndex;
    form.alertDate.value = hours;
    form.hasFollowup.checked = 't';
    toggleSpan(form.hasFollowup, 'nextActionSpan');
  }
</script>
<%-- include completed activity form --%>
<%@ include file="../contacts/call_completed_include.jsp" %>
<dhv:evaluate if="<%= !("cancel".equals(request.getParameter("action"))) || CallDetails.getStatusId() != Call.CANCELED %>">
<span name="nextActionSpan" id="nextActionSpan" <%= CallDetails.getHasFollowup() ? "" : "style=\"display:none\"" %>>
<br>
<%-- include pending activity form --%>
<%@ include file="../contacts/call_followup_include.jsp" %>
</span>
</dhv:evaluate>
<%= addHiddenParams(request, "popup|popupType|actionId|view") %>
