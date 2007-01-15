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
<jsp:useBean id="APP_VERSION" class="java.lang.String" scope="application"/>
<jsp:useBean id="found" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    <dhv:evaluate if="<%= hasText(APP_VERSION) %>">
    if (form.doReg[0].checked == 0 && form.doReg[1].checked == 0 && form.doReg[2].checked == 0) {
      alert(label("check.registration.option","Please select a registration option to continue"));
      return false;
    }
    </dhv:evaluate>
    return true;
  }
</script>
<form name="register" action="Setup.do?command=Register" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.welcomeToCentricCRM.label">Welcome to Centric CRM</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.welcome.setupProcessGuide.notes">In order to begin using Centric CRM, the setup process will guide you through several steps.<br /><br />Although installation can be completed in just a few minutes, you will have the option at any time during the setup to continue at a later time.<br /></dhv:label><br />
<dhv:evaluate if="<%= hasText(APP_VERSION) %>">
      <dhv:label name="setup.welcome.setupProcessGuide.text">Registering this application with Dark Horse Ventures is required before installation, whether you already have a license or not, so we have made the registration process very simple.<br /><ul><li>An internet connection is required before continuing</li><li>The freely available version of Centric CRM will entitle this system to a maximum of five (5) users</li><li>You must agree to the Centric CRM License Agreement</li><li>The Centric CRM administrator will receive information about software updates as they become available</li></ul></dhv:label>
    </td>
  </tr>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.registration">Registration</dhv:label></th>
  </tr>
  <tr>
    <td nowrap>
      <input type="radio" name="doReg" value="need" <%= !"true".equals(found) ? "checked" : "" %> />
      <dhv:label name="setup.requestNewLicense.text">Request a <b>new</b> license for this installation<br /></dhv:label>
      <input type="radio" name="doReg" value="have" <%= !"true".equals(found) ? "disabled" : "checked" %> />
      <dhv:evaluate if='<%= !"true".equals(found) %>'><font color="#888888"></dhv:evaluate><dhv:label name="setup.continueSetupFromPreviousSession.text">Continue setup from a previously started session</dhv:label><dhv:evaluate if='<%= !"true".equals(found) %>'></font></dhv:evaluate><br />
      <input type="radio" name="doReg" value="restore" />
      <dhv:label name="setup.restoreBackup">Restore an existing backup<br /></dhv:label>
      <br />
</dhv:evaluate>
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
