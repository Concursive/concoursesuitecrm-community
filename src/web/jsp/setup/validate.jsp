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
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    if (form.license.value.length == 0) {
      alert(label("check.licensekey","Enter the license key in the field to continue"));
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.register.license.focus();">
<dhv:formMessage showSpace="false" />
<form name="register" action="Setup.do?command=Validate" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.information">Information</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.ifRegisteredLicenceByEmail.text">If you have already registered for Centric CRM, you should have received a license file by email.</dhv:label><br>
      <ul>
      <li><dhv:label name="setup.licenseOnlyUsedOnSystem.text">The license can only be used on the system that requested it</dhv:label></li>
      <li><dhv:label name="setup.installationIssues.license.text" param="license=<a href=\"Setup.do?command=Register\">|endLicense=</a>">If you have misplaced your license or you are installing Centric CRM on a different server, then you can <a href="Setup.do?command=Register">request a new license</a> to be sent by email</dhv:label></li>
      </ul>
    </td>
  </tr>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.validation">Validation</dhv:label></th>
  </tr>
  <tr>
    <td nowrap>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.licenseKey">License Key</dhv:label>
          </td>
          <td nowrap>
            <dhv:label name="setup.pasteYourLicenseToContinue.text">Paste your license into the text field to continue:</dhv:label><br>
            <textarea cols="60" rows="5" name="license"></textarea>
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
</body>
