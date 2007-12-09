<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
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
      message += label("check.profile","- Profile is a required field\r\n");
      valid = false;
    }
    if (form.nameFirst.value.length == 0) {
      message += label("check.firstname","- First name is a required field\r\n");
      valid = false;
    }
    if (form.nameLast.value.length == 0) {
      message += label("check.lastname","- Last name is a required field\r\n");
      valid = false;
    }
    if (form.company.value.length == 0) {
      message += label("check.organization","- Organization is a required field\r\n");
      valid = false;
    }
    if (form.email.value.length == 0) {
      message += label("check.emailaddress","- Email address is a required field\r\n");
      valid = false;
    }
    if (!checkEmail(form.email.value)) {
      message += label("check.emailaddress.invalid","- Email address is invalid.  Make sure there are no invalid characters\r\n");
      valid = false;
    }
    if (getSelectedCheckbox(form.proxy).length > 0) {
      if (form.proxyHost.value.length == 0) {
        message += label("check.proxyhost","- Proxy host is required field when proxy is checked\r\n");
        valid = false;
      }
      if (form.proxyPort.value.length == 0) {
        message += label("check.proxyhost","- Proxy host is required field when proxy is checked\r\n");
        valid = false;
      }
      if (form.proxyPort.value.length > 0 && !checkNumber(form.proxyPort.value)) {
        message += label("check.proxyport.number","- Proxy port must be a number\r\n");
        valid = false;
      }
    }
    if (valid == false) {
      alert(label("check.form","Form could not be submitted, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.register.nameFirst.focus();">
<dhv:formMessage showSpace="false" />
<form name="register" action="Setup.do?command=SendReg&auto-populate=true" method="post" onSubmit="return checkForm(this)">
<input type="hidden" name="configured" value="1"/>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.information">Information</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.requestRegistrationLicense.label">To request a registration license, fill out the following information.</dhv:label><br>
      <dhv:label name="setup.requestRegistrationLicense.text"><ul><li>The information below will be sent to Concursive Corporation and processed</li><li>A license file will be sent by email to the address specified below</li><li>In good faith, Concursive Corporation provides this software and entitles you to use it according to the license agreement</li><li>Anonymous email addresses will not be accepted when processing registrations</li><li>Your email address and contact information will not be provided to others without your consent</li></ul></dhv:label>
      <dhv:label name="setup.darkHorseVenturesTeam">The Concursive Corporation Team</dhv:label>
      <br>&nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.registration">Registration</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.profile.description">A profile will allow you to manage this account online and is simply a name that you would like to refer to for this system.</dhv:label><br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.profile.colon">Profile:</dhv:label>
          </td>
          <td>
            <input type="text" size="40" name="profile" value="<%= toHtmlValue(registration.getProfile()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "profileError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.accounts_add.FirstName.colon">First Name:</dhv:label>
          </td>
          <td>
            <input type="text" size="20" name="nameFirst" value="<%= toHtmlValue(registration.getNameFirst()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameFirstError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.accounts_add.LastName.colon">Last Name:</dhv:label>
          </td>
          <td>
            <input type="text" size="20" name="nameLast" value="<%= toHtmlValue(registration.getNameLast()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameLastError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.organizationName.colon">Organization Name:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="company" value="<%= toHtmlValue(registration.getCompany()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "companyError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="documents.team.emailAddress.colon">Email Address:</dhv:label>
          </td>
          <td>
            <input type="text" size="40" maxlength="255" name="email" value="<%= toHtmlValue(registration.getEmail()) %>" /><font color="red">*</font>
            <%= showAttribute(request, "emailError") %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.operatingSystem.abbreviation">O/S</dhv:label>
          </td>
          <td>
            <%= System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.jvm">JVM</dhv:label>
          </td>
          <td>
            <%= System.getProperty("java.version") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.server">Server</dhv:label>
          </td>
          <td>
            <%= toHtml(server) %>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <dhv:checkbox name="ssl" value="true" checked="<%= registration.getSsl() %>"/>
            <dhv:label name="setup.useSSLportForSendingInformation.text">Use SSL (port 443) for sending information</dhv:label>
          </td>
        </tr>
        <%-- Proxy Server config --%>
        <tr>
          <td colspan="2">
            <dhv:checkbox name="proxy" value="true" checked="<%= registration.getProxy() %>" />
            <dhv:label name="setup.useProxyServer.text">Use proxy server to make internet connection</dhv:label>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.proxyHost">Proxy Host</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="proxyHost" value="<%= toHtmlValue(registration.getProxyHost()) %>" />
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.proxyPort">Proxy Port</dhv:label>
          </td>
          <td>
            <input type="text" size="5" name="proxyPort" value="<%= toHtmlValue(registration.getProxyPort()) %>" />
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <input type="button" value="<dhv:label name="button.backL">< Back</dhv:label>" onClick="javascript:window.location.href='index.jsp'"/>
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
</body>
