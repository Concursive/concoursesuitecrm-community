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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="user" class="org.aspcfs.modules.setup.beans.UserSetupBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    valid = true;
    message = "";
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
    if ((form.username.value == "")) { 
      message += "- Username is a required field\r\n";
      valid = false;
    }
    if ((form.password1.value == "") || (form.password2.value == "") || (form.password1.value != form.password2.value)) { 
      message += "- Check that both Passwords are entered correctly\r\n";
      valid = false;
    }
    if (valid == false) {
      alert("Form could not be submitted, please check the following:\r\n\r\n" + message);
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.forms[0].company.focus();">
<dhv:formMessage showSpace="false" />
<form name="configure" action="SetupUser.do?command=ConfigureUser&auto-populate=true" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Centric CRM Configuration (Step 4 of 4)<br>
      User Settings
    </th>
  </tr>
  <tr>
    <td>
      An administrative user is required to manage Centric CRM.  The administrator
      is primarily responsible for adding users, configuring user permissions,
      and tailoring Centric CRM to your organization.<br>
      <br>
      The following information will appear in Centric CRM under Employees and can
      be modified later if necessary.<br>
      &nbsp;<br>
    </td>
  </tr>
  <tr class="sectionTitle">
    <th>Create a Centric CRM administrator account</th>
  </tr>
  <tr>
    <td nowrap>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Company Name:
          </td>
          <td>
            <input type="text" size="30" maxlength="255" name="company" value="<%= toHtmlValue(user.getCompany()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "companyError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            First Name:
          </td>
          <td>
            <input type="text" size="20" maxlength="80" name="nameFirst" value="<%= toHtmlValue(user.getNameFirst()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameFirstError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Last Name:
          </td>
          <td>
            <input type="text" size="20" maxlength="80" name="nameLast" value="<%= toHtmlValue(user.getNameLast()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameLastError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Email Address:
          </td>
          <td>
            <input type="text" size="40" maxlength="256" name="email" value="<%= toHtmlValue(user.getEmail()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "emailError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            User Name:
          </td>
          <td>
            <input type="text" size="20" maxlength="80" name="username" value="<%= toHtmlValue(user.getUsername()) %>"/><font color="red">*</font>
            (case sensitive)
            <%= showAttribute(request, "usernameError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Password:
          </td>
          <td>
            <input type="password" size="20" maxlength="20" name="password1"><font color=red>*</font>
            (case sensitive)
            <%= showAttribute(request, "password1Error") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Verify Password:
          </td>
          <td>
            <input type="password" size="20" maxlength="20" name="password2"><font color=red>*</font>
            (must match previous password)
            <%= showAttribute(request, "password2Error") %>
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
</body>
