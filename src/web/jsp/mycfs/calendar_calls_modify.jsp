<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.DateUtils" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
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
<% if("pending".equals(request.getParameter("view"))){ %>
  if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is entered correctly\r\n";
      formTest = false;
    }
    if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is on or after today's date\r\n";
      formTest = false;
    }
    if ((!form.alertText.value == "") && (form.alertDate.value == "")) { 
      message += "- Please specify an alert date\r\n";
      formTest = false;
    }
    if ((!form.alertDate.value == "") && (form.alertText.value == "")) { 
      message += "- Please specify an alert description\r\n";
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
  
<% }else{ %>
  if (form.subject.value == "") { 
      message += "- Blank records cannot be saved\r\n";
      formTest = false;
    }
    
    if (form.callTypeId.value == "0") { 
      message += "- Please specify a type\r\n";
      formTest = false;
    }
    
    <% if(CallDetails.getAlertDate() == null){ %>
    if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is entered correctly\r\n";
      formTest = false;
    }
    if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is on or after today's date\r\n";
      formTest = false;
    }
    if ((!form.alertText.value == "") && (form.alertDate.value == "")) { 
      message += "- Please specify an alert date\r\n";
      formTest = false;
    }
    if ((!form.alertDate.value == "") && (form.alertText.value == "")) { 
      message += "- Please specify an alert description\r\n";
      formTest = false;
    }
    <% } %>
<% } %>
  if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
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
      if(form.reminder != null && form.reminder[0].checked){
        form.reminderId.value = '-1';
        form.reminderTypeId.value = '0';
      }
    }
  }
  
  <% if(!"pending".equals(request.getParameter("view")) && CallDetails.getAlertDate() == null){ %>
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
      form.alertText.focus();
    } else {
      hideSpan(tag);
    }
  }
  
  function makeSuggestion(){
    if(document.getElementById('resultId').value > -1){
      window.frames['server_commands'].location.href='AccountContactCalls.do?command=SuggestCall&resultId=' + document.getElementById('resultId').value;
    }
  }
  
  function addFollowup(hours, typeId){
    var form = document.forms[0];
    var selectedIndex = 0;
    var callTypes = form.alertCallTypeId;
    
    for(i = 0; i < callTypes.options.length; i++){
      if(frm.options[i].value == typeId){
        selectedIndex = i;
      }
    }
    callTypes.selectedIndex = selectedIndex;
    form.reminderId.value = hours;
    form.nextAction.checked = 't';
    toggleSpan(form.nextAction, 'nextActionSpan');
  }
<% } %>
function showHistory() {
    popURL('CalendarCalls.do?command=View&contactId=<%= ContactDetails.getId() %>&popup=true&source=calendar','CONTACT_HISTORY','650','500','yes','yes');
  }
</script>
<% if("pending".equals(request.getParameter("view"))){ %>
  <body onLoad="javascript:document.forms[0].alertText.focus();">
<%}else if(CallDetails.getStatusId() != Call.CANCELED){ %>
  <body onLoad="javascript:document.forms[0].subject.focus();">
<% } %>
<% request.setAttribute("includeDetails", "true"); %>
<%@ include file="../contacts/contact_details_header_include.jsp" %><br>
<form name="addCall" action="AccountContactsCalls.do?command=Save&auto-populate=true&actionSource=CalendarCalls" onSubmit="return doCheck(this);" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <td class="containerBack">
      <%-- include call update form --%>
      <dhv:evaluate if="<%= CallDetails.getStatusId() != Call.CANCELED %>">
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
      </dhv:evaluate>
      <input type="button" value="Cancel" onClick="javascript:window.close();">
      <input type="reset" value="Reset">&nbsp;
      [<a href="javascript:showHistory();">View Contact History</a>]
      <br>
      <%= showError(request, "actionError") %>
      <% if("pending".equals(request.getParameter("view"))){ %>
        <%-- include pending activity form --%>
        <%@ include file="../contacts/call_followup_include.jsp" %>
        &nbsp;
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
      <% }else{ %>
        <% if(CallDetails.getStatusId() != Call.CANCELED){ %>
        <%-- include completed activity form --%>
        <%@ include file="../contacts/call_completed_include.jsp" %>
        <% }else{ %>
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
        <% } %>
        &nbsp;
        <% if(CallDetails.getAlertDate() != null){ %>
          <%-- include followup activity details --%>
          <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
        <% }else{ %>
          <span name="nextActionSpan" id="nextActionSpan" style="display:none">
          <br>
          <%-- include pending activity form --%>
          <%@ include file="../contacts/call_followup_include.jsp" %>
          </span>
      <% 
          }
        }
      %>
      &nbsp;
      <br>
      <dhv:evaluate if="<%= CallDetails.getStatusId() != Call.CANCELED %>">
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
      </dhv:evaluate>
      <input type="button" value="Cancel" onClick="javascript:window.close();">
      <input type="reset" value="Reset">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
      <input type="hidden" name="modified" value="<%= CallDetails.getModified() %>">
      <input type="hidden" name="id" value="<%= CallDetails.getId() %>">
      <input type="hidden" name="previousId" value="<%= PreviousCallDetails.getId() %>">
      <br>
      &nbsp;
    </td>
  </tr>
</table>
<% if("pending".equals(request.getParameter("view"))){ %>
  <%-- include completed activity values --%>
  <input type="hidden" name="callTypeId" value="<%= CallDetails.getCallTypeId() %>">
  <input type="hidden" name="length" value="<%= CallDetails.getLength() %>">
  <input type="hidden" name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>">
  <input type="hidden" name="notes" value="<%= toString(CallDetails.getNotes()) %>">
  <input type="hidden" name="resultId" value="<%= CallDetails.getResultId() %>">
<% }else if(!(CallDetails.getStatusId() == Call.COMPLETE && CallDetails.getAlertDate() == null)){ %>
  <%-- include pending activity values --%>
  <input type="hidden" name="alertText" value="<%= toHtmlValue(CallDetails.getAlertText()) %>">
  <input type="hidden" name="alertCallTypeId" value="<%= CallDetails.getAlertCallTypeId() %>">
  <zeroio:dateSelect field="alertDate" timestamp="<%= CallDetails.getAlertDate() %>" hidden="true" />
  <zeroio:timeSelect baseName="alertDate" value="<%= CallDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" hidden="true"/>
  <input type="hidden" name="owner" value="<%= CallDetails.getOwner() %>">
  <input type="hidden" name="reminderId" value="<%= CallDetails.getReminderId() %>">
  <input type="hidden" name="reminderTypeId" value="<%= CallDetails.getReminderTypeId() %>">
  <input type="hidden" name="followupNotes" value="<%= toString(CallDetails.getFollowupNotes()) %>">
  <input type="hidden" name="priorityId" value="<%= CallDetails.getPriorityId() %>">
<% } %>
<input type="hidden" name="statusId" value="<%= CallDetails.getStatusId() %>">
<input type="hidden" name="return" value="calendar">
<%= addHiddenParams(request, "view|popup") %>
</form>
</body>
