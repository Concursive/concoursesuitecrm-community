<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="registration" class="org.aspcfs.modules.setup.beans.RegistrationBean" scope="request"/>
<jsp:useBean id="server" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkCheckbox.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
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
      message += "- Organization is a required field\r\n";
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
    if (getSelectedCheckbox(form.proxy).length > 0) {
      if (form.proxyHost.value.length == 0) {
        message += "- Proxy host is required field when proxy is checked\r\n";
        valid = false;
      }
      if (form.proxyPort.value.length == 0) {
        message += "- Proxy port is required field when proxy is checked\r\n";
        valid = false;
      }
      if (form.proxyPort.value.length > 0 && !checkNumber(form.proxyPort.value)) {
        message += "- Proxy port must be a number\r\n";
        valid = false;
      }
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
      To request a registration license, fill out the following information.<br>
      <ul>
      <li>The information below will be sent to Dark Horse Ventures and processed</li>
      <li>A license file will be sent by email to the address specified below</li>
      <li>In good faith, Dark Horse Ventures provides this software and
      entitles you to use it according to the license agreement</li>
      <li>Anonymous email addresses will not be accepted
      when processing registrations</li>
      <li>Your email address and contact information will not be provided to others without
      your consent</li>
      </ul>
      The Dark Horse Ventures Team
      <br>&nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th>Registration</th>
  </tr>
  <tr>
    <td>
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
            Organization Name:
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
            <input type="text" size="40" maxlength="255" name="email" value="<%= toHtmlValue(registration.getEmail()) %>" /><font color="red">*</font>
            <%= showAttribute(request, "emailError") %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            O/S
          </td>
          <td>
            <%= System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            JVM
          </td>
          <td>
            <%= System.getProperty("java.version") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Server
          </td>
          <td>
            <%= toHtml(server) %>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <dhv:checkbox name="ssl" value="true" checked="<%= registration.getSsl() %>"/>
            Use SSL (port 443) for sending information
          </td>
        </tr>
        <%-- Proxy Server config --%>
        <tr>
          <td colspan="2">
            <dhv:checkbox name="proxy" value="true" checked="<%= registration.getProxy() %>" />
            Use proxy server to make internet connection
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Proxy Host
          </td>
          <td>
            <input type="text" size="30" name="proxyHost" value="<%= toHtmlValue(registration.getProxyHost()) %>" />
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Proxy Port
          </td>
          <td>
            <input type="text" size="5" name="proxyPort" value="<%= toHtmlValue(registration.getProxyPort()) %>" />
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
