<%-- 
  - Copyright
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="callClient" class="org.aspcfs.modules.dialer.beans.CallClient" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="javascript">
   function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    if (form.extension.value == "") {
      message += label("","- Please enter a valid extension\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
</script>
<form name="inputForm" action="OutboundDialer.do?command=Call&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="calls.confirmExtension">Confirm Extension</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="calls.extension">Extension</dhv:label>
    </td>
    <td>
      <input type="text" name="extension" value="<%= toHtmlValue(callClient.getExtension()) %>">
      <font color="red">*</font>
    </td>
  </tr>
</table>
<br>
<input type="hidden" name="validationDate" value="<%= System.currentTimeMillis() %>" />
<input type="submit" value="<dhv:label name="calls.call">Call</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
</form>
<script language="javascript">
  document.inputForm.extension.focus();
</script>