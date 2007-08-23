<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.utils.DateUtils,org.aspcfs.utils.web.HtmlSelect,java.text.DateFormat" %>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="action" class="java.lang.String" scope="request"/>
<jsp:useBean id="ActionContacts" class="org.aspcfs.modules.actionlist.base.ActionContactsList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="followupContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
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
    if (form.subject && checkNullString(form.subject.value)) { 
      message += label("specify.blank.records","- Blank records cannot be saved\r\n");
      formTest = false;
    }
    if (form.callTypeId && form.callTypeId.value == "0") { 
      message += label("specify.type","- Please specify a type\r\n");
      formTest = false;
    }
    if(form.callLength &&!checkNullString(form.callLength.value) && !checkNumber(form.callLength.value)){
      message += label("specify.activity.length.format","- Check that the activity length is entered correctly\r\n");
      formTest = false;
    }
    if (form.callStartDate && checkNullString(form.callStartDate.value)) { 
      message += label("specify.activity.start.date", "- Please specify an activity start date\r\n");
      formTest = false;
    }
    if (form.callEndDate && checkNullString(form.callEndDate.value)) { 
      message += label("specify.activity.end.date", "- Please specify an activity end date\r\n");
      formTest = false;
    }
    if(form.callStartDate && form.callEndDate && !checkNullString(form.callStartDate.value) && !checkNullString(form.callEndDate.value)){
      if(!checkDate(form.callStartDate.value)){
        message += label("specify.activity.start.date.format","- Check that the activity start date is entered correctly\r\n");
        formTest = false;
      }
      if(!checkDate(form.callEndDate.value)){
        message += label("specify.activity.end.date.format","- Check that the activity end date is entered correctly\r\n");
        formTest = false;
      }
      if(checkDate(form.callStartDate.value) && checkDate(form.callEndDate.value)){
        if(compareDates(form.callStartDate.value, form.callEndDate.value) == 1){
          message += label("activity.end.must.later.start.date", "- Activity end date must be at a later date than start date!\r\n");
          formTest = false;
        }
      }
    }
    if(form.emailParticipants && form.emailParticipants.checked && (form.participantId[0].value == 'none' || form.participantId[0].value == '-1')){
      message += label("select.activity.participant.or.remove.notification", "- Please select at least one activity participant or remove email notification!\r\n");
      formTest = false;
    }
    if(form.hasFollowup != null && form.hasFollowup.checked || form.action.value=='schedule'){
      if (checkNullString(form.alertDate.value)) { 
        message += label("specify.followup.start.date", "- Please specify a followup start date\r\n");
        formTest = false;
      }
      if (checkNullString(form.followupEndDate.value)) { 
        message += label("specify.followup.end.date", "- Please specify a followup end date\r\n");
        formTest = false;
      }
      if(!checkNullString(form.alertDate.value) && !checkNullString(form.followupEndDate.value)){
        if(!checkAlertDate(form.alertDate.value)){
          message += label("specify.followup.start.date.format","- Check that the followup start date is entered correctly\r\n");
          formTest = false;
        }
        if(!checkAlertDate(form.followupEndDate.value)){
          message += label("specify.followup.end.date.format","- Check that the followup end date is entered correctly\r\n");
          formTest = false;
        }
        if(checkDate(form.alertDate.value) && checkDate(form.followupEndDate.value)){
          if(compareDates(form.alertDate.value, form.followupEndDate.value) == 1){
            message += label("followup.end.must.later.start.date", "- Followup end date must be at a later date than start date!\r\n");
            formTest = false;
          }
        }
      }
      if (form.alertText && checkNullString(form.alertText.value)) { 
        message += label("specify.alert.description", "- Please specify an alert description\r\n");
        formTest = false;
      }
      if(form.followupLength && !checkNullString(form.followupLength.value) && !checkNumber(form.followupLength.value)){
        message += label("specify.followup.length.format","- Check that the followup length is entered correctly\r\n");
        formTest = false;
      }
      if (form.alertCallTypeId && form.alertCallTypeId.value == "0") { 
        message += label("specify.alert.type","- Please specify an alert type\r\n");
        formTest = false;
      }
      //if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
      //  alertMessage += label("AlertDate.before.today", "Alert Date is before today's date\r\n");
      //}
      if (form.reminder[1].checked && !checkNumber(form.reminderId.value)) { 
        message += label("check.reminder","- Check that the reminder is entered correctly\r\n");
        formTest = false;
      }
      if(form.emailFollowupParticipants && form.emailFollowupParticipants.checked && (form.followupParticipantId[0].value == 'none' || form.followupParticipantId[0].value == '-1')){
        message += label("select.followup.participant.or.remove.notification", "- Please select at least one followup participant or remove email notification!\r\n");
        formTest = false;
      }
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = document.addCall.participant;
      if (test != null) {
        selectAllOptions(document.addCall.participant);
      } else{
        var test = document.addCall.followupparticipant;
        if (test != null) {
          selectAllOptions(document.addCall.followupparticipant);
      }
      }
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
      form.priorityId.value = '-1';
    }else{
      if(form.reminder[0].checked){
        form.reminderId.value = '-1';
        form.reminderTypeId.value = '0';
      }
    }
  }
  
  function toggleSpan(cb, tag) {
    var form = document.addCall;
    if (cb.checked) {
      if (form.alertText.value == "") {
        form.alertText.value = form.subject.value;
      }
      if (form.followupLocation.value == "") {
        form.followupLocation.value = form.callLocation .value;
      }
      showSpan(tag);
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
    var form = document.addCall;
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
<dhv:evaluate if='<%= !("schedule".equals(action)) %>'>
<%@ include file="../contacts/call_completed_include.jsp" %>
</dhv:evaluate>
<dhv:evaluate if='<%= !("cancel".equals(request.getParameter("action"))) || CallDetails.getStatusId() != Call.CANCELED %>'>
<dhv:evaluate if='<%= !("schedule".equals(action)) %>'>
<span name="nextActionSpan" id="nextActionSpan" <%= CallDetails.getHasFollowup() ? "" : "style=\"display:none\"" %>>
</dhv:evaluate>
<%-- include pending activity form --%>
<%@ include file="../contacts/call_followup_include.jsp" %>
<dhv:evaluate if='<%= !("schedule".equals(action)) %>'>
</span>
</dhv:evaluate>
</dhv:evaluate>
<input type="hidden" name="action" value="<%=action %>">
<%= addHiddenParams(request, "popup|popupType|actionId|view|actionSource") %>