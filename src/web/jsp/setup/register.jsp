<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="registration" class="org.aspcfs.modules.setup.beans.RegistrationBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    valid = true;
    message = "";
    if (form.profile.value.length == 0) {
      message += "- Profile is a required field\r\n";
      valid = false;
    }
    if (form.nameFirst.value.length == 0) {
      message += "- First name is a required field\r\n";
      valid = false;
    }
    if (form.nameLast.value.length == 0) {
      message += "- Last name is a required field\r\n";
      valid = false;
    }
    if (form.company.value.length == 0) {
      message += "- Company is a required field\r\n";
      valid = false;
    }
    if (form.email.value.length == 0) {
      message += "- Email address is a required field\r\n";
      valid = false;
    }
    if (!checkEmail(form.email.value)) {
      message += "- Email address is invalid.  Make sure there are no invalid characters\r\n";
      valid = false;
    }
    if (valid == false) {
      alert("Form could not be submitted, please check the following:\r\n\r\n" + message);
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.forms[0].nameFirst.focus();">
<%= showError(request, "actionError", false) %>
<form name="register" action="Setup.do?command=SendReg&auto-populate=true" method="post" onSubmit="return checkForm(this)">
<input type="hidden" name="configured" value="1"/>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>Information</th>
  </tr>
  <tr>
    <td>
      To request a registration key, fill out the following information.<br>
      <br>
      The information will be sent to Dark Horse Ventures and processed.<br>
      <br>
      A key will be sent by email to the address specified below.<br>
      <br>
      In good faith, Dark Horse Ventures provides this software and
      entitles you to use it with up to five (5) users.
      In return, anonymous email addresses will not be accepted
      when processing registrations.
      Your email address will not be provided to others without
      your consent.<br>
      <br>
      - The Dark Horse Ventures Team
      <br>&nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th>Registration</th>
  </tr>
  <tr>
    <td nowrap>
      A profile will allow you to manage this account online and is simply a name
      that you would like to refer to for this system.<br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Profile:
          </td>
          <td>
            <input type="text" size="40" name="profile" value="<%= toHtmlValue(registration.getProfile()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "profileError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            First Name:
          </td>
          <td>
            <input type="text" size="20" name="nameFirst" value="<%= toHtmlValue(registration.getNameFirst()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameFirstError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Last Name:
          </td>
          <td>
            <input type="text" size="20" name="nameLast" value="<%= toHtmlValue(registration.getNameLast()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameLastError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Company Name:
          </td>
          <td>
            <input type="text" size="30" name="company" value="<%= toHtmlValue(registration.getCompany()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "companyError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Email Address:
          </td>
          <td>
            <input type="text" size="40" maxlength="255" name="email" value="<%= toHtmlValue(registration.getEmail()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "emailError") %>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <dhv:checkbox name="ssl" checked="<%= registration.getSsl() %>"/>
            Use SSL (port 443) for sending information
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <input type="button" value="< Back" onClick="javascript:window.location.href='index.jsp'"/>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
</body>
