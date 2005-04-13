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
<jsp:useBean id="LoginBean" class="org.aspcfs.modules.login.beans.LoginBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    valid = true;
    message = "";
    if ((form.username.value == "")) { 
      message += label("check.username","- Username is a required field\r\n");
      valid = false;
    }
    if ((form.password.value == "")) { 
      message += label("check.password","- Password is a required field\r\n");
      valid = false;
    }
    if (valid == false) {
      alert(label("check.form","Form could not be submitted, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="setup.installedVersionNeedsUpgrade.text">The installed version of Centric CRM needs to be upgraded.</dhv:label></td>
  </tr>
</table>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRMUpgrade">Centric CRM Upgrade</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.reasonsToReceiveMessage.label">You are receiving this message for one of several reasons:</dhv:label>
      <dhv:label name="setup.reasonsToReceiveMessage.text"><ul><li>You are an administrator in the process of upgrading Centric CRM and you are ready to proceed with the upgrade.</li><li>You are a user expecting to be able to login, however, at this time it appears the software is being upgraded by an administrator. If this screen persists, you might review any emails that you may have received from your administrator or you might contact your administrator for more information.</li><li>If an upgrade was not planned, then there might be a configuration issue that needs to be resolved. If this is the case, then you might try proceeding with the upgrade process to verify your installation.</li></ul></dhv:label>
      <dhv:label name="setup.administrativeAccessAvailable.label">If you have administrative access, then you will be required to login to proceed with the upgrade process. To protect your data, you should perform the following steps before continuing:</dhv:label>
      <dhv:label name="setup.administrativeAccessAvailable.text"><ul><li>Backup the Centric CRM database</li><li>Backup the Centric CRM file library</li></ul></dhv:label>
    </td>
  </tr>
</table>
<form name="configure" method="POST" action="Login.do?command=Login&auto-populate=true" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.loginWithAdminAccount">Login with an administrator account</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.afterBackupContinueUpgrade.text">If you have backed up your system and you are an administrator, continue with the upgrade process.</dhv:label><br />
      <br />
      <table border="0" class="empty">
<dhv:evaluate if="<%= hasText(LoginBean.getMessage()) %>">
        <tr>
          <td align="center" colspan="2">
            <center><font size="2" color='red'><%= LoginBean.getMessage() %></font></center>
          </td>
        </tr>
</dhv:evaluate>
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.accounts_contacts_portal_view.UserName">User Name</dhv:label>:
          </td>
          <td>
            <input type="text" size="20" maxlength="80" name="username" /><font color="red">*</font>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.password.colon">Password:</dhv:label>
          </td>
          <td>
            <input type="password" size="20" maxlength="20" name="password" /><font color="red">*</font>
          </td>
        </tr>
      </table>
      <br />
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
