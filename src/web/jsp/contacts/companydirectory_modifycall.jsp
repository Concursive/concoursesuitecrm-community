<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
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
    
    if(form.hasFollowup != null && form.hasFollowup.checked){
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
     } 
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
      window.frames['server_commands'].location.href='ExternalContactsCalls.do?command=SuggestCall&resultId=' + document.getElementById('resultId').value;
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
<% } %>
</script>
<% if("pending".equals(request.getParameter("view"))){ %>
<body onLoad="javascript:document.forms[0].alertText.focus();">
<%}else{%>
<body onLoad="javascript:document.forms[0].subject.focus();">
<%}%>
<form name="addCall" action="ExternalContactsCalls.do?command=Save&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>">Activities</a> >
 <%if (request.getParameter("return") == null) { %>
	<a href="ExternalContactsCalls.do?command=Details&id=<%=CallDetails.getId()%>&contactId=<%=ContactDetails.getId()%>">Activity Details</a> >
<%}%>
Modify Activity
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<dhv:evaluate if="<%= (!isPopup(request) || isInLinePopup(request)) %>"> 
  <% String param1 = "id=" + ContactDetails.getId(); 
      String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
  <dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  <td class="containerBack">
    <dhv:evaluate if="<%= CallDetails.getStatusId() != Call.CANCELED %>">
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
    </dhv:evaluate>
    <dhv:evaluate exp="<%= !isPopup(request) || isInLinePopup(request) %>">
    <% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|view|actionId") %>';this.form.dosubmit.value='false';">
    <%}else {%>
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|view|actionId") %>';this.form.dosubmit.value='false';">
    <%}%>
    </dhv:evaluate>
    <dhv:evaluate exp="<%= isPopup(request)  && !isInLinePopup(request) %>">
    <input type="button" value="Cancel" onclick="javascript:window.close();"> 
    </dhv:evaluate>
<br />
<%= !"&nbsp;".equals(showError(request, "actionError").trim())? showError(request, "actionError"):showWarning(request, "actionWarning")%><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if("pending".equals(request.getParameter("view"))){ %>
        <%-- include pending activity form --%>
        <%@ include file="call_followup_include.jsp" %>
        &nbsp;
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
      <% }else{ %>
        <% if(CallDetails.getStatusId() != Call.CANCELED){ %>
        <%-- include completed activity form --%>
        <%@ include file="call_completed_include.jsp" %>
        <% }else{ %>
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
        <% } %>
        &nbsp;
        <% if((CallDetails.getAlertDate() != null) && (request.getAttribute("alertDateWarning") == null) && request.getParameter("hasFollowup") == null){ %>
          <%-- include followup activity details --%>
          <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
        <% }else{ %>
          <span name="nextActionSpan" id="nextActionSpan" <%= CallDetails.getHasFollowup() ? "" : "style=\"display:none\"" %>>
          <br>
          <%-- include pending activity form --%>
          <%@ include file="call_followup_include.jsp" %>
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
    <dhv:evaluate exp="<%= !isPopup(request) || isInLinePopup(request) %>">
    <% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|view|actionId") %>';this.form.dosubmit.value='false';">
    <%}else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|view|actionId") %>';this.form.dosubmit.value='false';">
    <%}%>
    </dhv:evaluate>
    <dhv:evaluate exp="<%= isPopup(request)  && !isInLinePopup(request) %>">
    <input type="button" value="Cancel" onclick="javascript:window.close();"> 
    </dhv:evaluate>
</td>
</tr>
</table>
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
<input type="hidden" name="modified" value="<%= CallDetails.getModified() %>">
<input type="hidden" name="id" value="<%= CallDetails.getId() %>">
<input type="hidden" name="previousId" value="<%= PreviousCallDetails.getId() %>">
<input type="hidden" name="statusId" value="<%= CallDetails.getStatusId() %>">
<%= addHiddenParams(request, "return|view|popup|popupType|actionId") %>
<% if("pending".equals(request.getParameter("view"))){ %>
  <%-- include completed activity values --%>
  <input type="hidden" name="callTypeId" value="<%= CallDetails.getCallTypeId() %>">
  <input type="hidden" name="length" value="<%= CallDetails.getLength() %>">
  <input type="hidden" name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>">
  <input type="hidden" name="notes" value="<%= toString(CallDetails.getNotes()) %>">
  <input type="hidden" name="resultId" value="<%= CallDetails.getResultId() %>">
<% }else if(!(CallDetails.getStatusId() == Call.COMPLETE && CallDetails.getAlertDate() == null)&& (request.getAttribute("alertDateWarning") == null)){ %>
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
</form>
</body>
