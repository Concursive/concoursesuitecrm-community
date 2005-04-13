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
<jsp:useBean id="database" class="org.aspcfs.modules.setup.beans.DatabaseBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function showProgress() {
    hideSpan("buttons");
    showSpan("progress");
    return true;
  }
  function setPort() {
    if (document.configure.type.value == "PostgreSQL" && document.configure.port.value == "1433") {
      document.configure.port.value = "5432";
    }
    if (document.configure.type.value == "MSSQL" && document.configure.port.value == "5432") {
      document.configure.port.value = "1433";
    }
  }
</script>
<body onLoad="javascript:document.configure.ip.focus();">
<dhv:formMessage showSpace="false" />
<form name="configure" action="SetupDatabase.do?command=ConfigureDatabase&auto-populate=true" method="post" onSubmit="return showProgress()">
<input type="hidden" name="configured" value="1"/>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.configuration.step3to4">Centric CRM Configuration (Step 3 of 4)<br />Database Settings &amp; Installation</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.centricCRM.usesDatabase.text">Centric CRM stores and retrieves data using a database.</dhv:label><br>
      <br>
      <dhv:label name="setup.configureDatabase.url.text" param="postgres=<a href=\"http://www.postgresql.org\" target=\"_new\">|end=</a>|mssql=<a href=\"http://www.microsoft.com/sql\" target=\"_new\">">Centric CRM works best with the Open Source database server <a href="http://www.postgresql.org" target="_new">PostgreSQL</a>. You can also use <a href="http://www.microsoft.com/sql" target="_new">Microsoft SQL Server</a>.</dhv:label>
      <dhv:label name="setup.databaseServerInstallation.text"><ul><li>Before continuing, the database server must be installed and functional</li><li>The database administrator should create a user called "centric_crm"</li><li>The database administrator should create a new database, with UNICODE encoding, called "centric_crm"</li><li>The centric_crm user must have full permissions on the centric_crm database</li><li>Once configured, enter the database connection information below, then press continue to verify the settings</li></ul></dhv:label>
    </td>
  </tr>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.databaseConnection">Database Connection</dhv:label></th>
  </tr>
  <tr>
    <td nowrap>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.databaseType.colon">Database Type:</dhv:label>
          </td>
          <td>
            <select name="type" onChange="javascript:setPort();">
              <option value="PostgreSQL"<%= "PostgreSQL".equals(database.getType()) ? " selected" : "" %>><dhv:label name="setup.postgreSQL">PostgreSQL</dhv:label></option>
              <option value="MSSQL"<%= "MSSQL".equals(database.getType()) ? " selected" : "" %>><dhv:label name="setup.sqlServer">Microsoft SQL Server</dhv:label></option>
            </select>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="campaign.ipAddress.colon">IP Address:</dhv:label>
          </td>
          <td>
            <input type="text" size="20" name="ip" value="<%= toHtmlValue(database.getIp()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "ipError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.databasePort.colon">Database Port:</dhv:label>
          </td>
          <td>
            <input type="text" size="20" name="port" value="<%= toHtmlValue(database.getPort()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "portError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.databaseName.colon">Database Name:</dhv:label>
          </td>
          <td>
            <input type="text" size="20" name="name" value="<%= toHtmlValue(database.getName()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.databaseUserName.colon">Database User Name:</dhv:label>
          </td>
          <td>
            <input type="text" size="20" maxlength="255" name="user" value="<%= toHtmlValue(database.getUser()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "userError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.userPassword.colon">Database User Password:</dhv:label>
          </td>
          <td>
            <input type="password" size="20" maxlength="255" name="password" value="<%= toHtmlValue(database.getPassword()) %>"/>
            <%= showAttribute(request, "passwordError") %>
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <span id="buttons" name="buttons">
        <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
      </span>
      <span id="progress" name="progress" style="display:none">
        <font color="blue"><b><dhv:label name="setup.pleaseWaitToConnectToDatabase.text">Please Wait... connecting to the database!</dhv:label></b></font>
      </span>
    </td>
  </tr>
</table>
</form>
</body>
