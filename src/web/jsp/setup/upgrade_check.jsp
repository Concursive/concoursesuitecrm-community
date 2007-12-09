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
<jsp:useBean id="server" class="org.aspcfs.modules.setup.beans.ServerBean" scope="request"/>
<jsp:useBean id="status" class="java.lang.String" scope="request"/>
<jsp:useBean id="installedVersion" class="java.lang.String" scope="request"/>
<jsp:useBean id="newVersion" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (confirm(label("check.upgradesystem","Are you sure you want to upgrade the system now?"))) {
      showProgress();
      return true;
    } else {
      return false;
    }
  }
  function showProgress() {
    hideSpan("buttons");
    showSpan("progress");
    return true;
  }
</script>
<form name="configure" method="POST" action="Upgrade.do?command=PerformUpgrade" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.centricCRMUpgrade">Concourse Suite Community Edition Upgrade</dhv:label></th>
  </tr>
  <tr>
    <td nowrap>
      <dhv:label name="setup.loggedInAsAdmin.text">You are now logged in as administrator.<br />Make sure you have backed up your database and file library.</dhv:label><br />
      <br />
      <dhv:label name="setup.reviewInformation.request">Please review the following information</dhv:label><br />
      <br />
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.previousVersion.colon">Previous version:</dhv:label>
          </td>
          <td>
            <%= toHtml(installedVersion) %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.upgradeTo.colon">Upgrade to:</dhv:label>
          </td>
          <td>
            <%= toHtml(newVersion) %>
          </td>
        </tr>
      </table>
      <br />
      <dhv:evaluate if='<%= "0".equals(status) %>'>
        <table class="highlight" cellspacing="0">
          <tr>
            <td><dhv:label name="setup.systemAlreadyUpgraded.text">This system appears to already have been upgraded. Continuing will mark this system as upgraded, allowing users to login.</dhv:label></td>
          </tr>
        </table>
        <br />
      </dhv:evaluate>
      <span id="buttons" name="buttons">
        <input type="submit" value="Upgrade >"/>
      </span>
      <span id="progress" name="progress" style="display:none">
        <font color="blue"><b><dhv:label name="setup.pleaseWaitUpgrading.text">Please Wait... upgrading Concourse Suite Community Edition!</dhv:label></b></font>
      </span>
    </td>
  </tr>
</table>
</form>
