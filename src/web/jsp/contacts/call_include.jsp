<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
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
    
    if (form.notes.value == "" && form.subject.value == "") { 
      message += "- Blank records cannot be saved\r\n";
      formTest = false;
    }
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
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= CallDetails.getId() > 0 ? "Call Details" : "Log a New Call" %></strong>
    </th>
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
      <a href="javascript:popCalendar('addCall', 'alertDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
