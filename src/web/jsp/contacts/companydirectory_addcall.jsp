<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].subject.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
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
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
</script>
<form name="addCall" action="ExternalContactsCalls.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>">Calls</a> >
Add Call<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
      <input type="reset" value="Reset">
      <br>
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Log a New Call</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= CallTypeList.getHtmlSelect("callTypeId", CallDetails.getCallTypeId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Subject
    </td>
    <td>
      <input type="text" size="50" name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Notes
    </td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(CallDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Length
    </td>
    <td>
      <input type="text" size="5" name="length" value="<%= toHtmlValue(CallDetails.getLengthString()) %>"> minutes  <%= showAttribute(request, "lengthError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Alert Description
    </td>
    <td>
      <input type="text" size="50" name="alertText" value="<%= toHtmlValue(CallDetails.getAlertText()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Alert Date
    </td>
    <td>
      <input type="text" size="10" name="alertDate" value="<%= toHtmlValue(CallDetails.getAlertDateString()) %>"> 
      <a href="javascript:popCalendar('addCall', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
&nbsp;
<br>
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
<br>
&nbsp;
</td>
</tr>
</table>
</form>
</body>
