<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
    if (confirm("Are you sure you want to upgrade the system now?")) {
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
    <th>Dark Horse CRM Upgrade</th>
  </tr>
  <tr>
    <td nowrap>
      You are now logged in as administrator.<br />
      Make sure you have backed up your database and file library.<br />
      <br />
      Please review the following information<br />
      <br />
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Previous version:
          </td>
          <td>
            <%= toHtml(installedVersion) %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Upgrade to:
          </td>
          <td>
            <%= toHtml(newVersion) %>
          </td>
        </tr>
      </table>
      <br />
      <dhv:evaluate if="<%= "0".equals(status) %>">
        <table class="highlight" cellspacing="0">
          <tr>
            <td>This system appears to already have been upgraded. Continuing will
              mark this system as upgraded, allowing users to login.</td>
          </tr>
        </table>
        <br />
      </dhv:evaluate>
      <span id="buttons" name="buttons">
        <input type="submit" value="Upgrade >"/>
      </span>
      <span id="progress" name="progress" style="display:none">
        <font color="blue"><b>Please Wait... upgrading Dark Horse CRM!</b></font>
      </span>
    </td>
  </tr>
</table>
</form>
