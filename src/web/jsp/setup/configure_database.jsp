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
</script>
<body onLoad="javascript:document.forms[0].ip.focus();">
<%= showError(request, "actionError", false) %>
<form name="configure" action="SetupDatabase.do?command=ConfigureDatabase&auto-populate=true" method="post" onSubmit="return showProgress()">
<input type="hidden" name="configured" value="1"/>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 3 of 4)<br>
      Database Settings &amp; Installation
    </th>
  </tr>
  <tr>
    <td>
      Dark Horse CRM stores and retrieves data using a database.<br>
      <br>
      Dark Horse CRM is intended to work with the Open Source database <a href="http://www.postgresql.org" target="_new">PostgreSQL</a>.
      Before continuing, PostgreSQL must be installed.  Have the database administrator
      create a database for Dark Horse CRM called "darkhorse_crm" and a database user
      called "darkhorse_crm" then
      enter the database connection information below:<br>
      &nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th>Database Connection</th>
  </tr>
  <tr>
    <td nowrap>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Database Type:
          </td>
          <td>
            <%= toHtml(database.getType()) %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            IP Address:
          </td>
          <td>
            <input type="text" size="20" name="ip" value="<%= toHtmlValue(database.getIp()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "ipError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Database Port:
          </td>
          <td>
            <input type="text" size="20" name="port" value="<%= toHtmlValue(database.getPort()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "portError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Database Name:
          </td>
          <td>
            <input type="text" size="20" name="name" value="<%= toHtmlValue(database.getName()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "nameError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Database User Name:
          </td>
          <td>
            <input type="text" size="20" maxlength="255" name="user" value="<%= toHtmlValue(database.getUser()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "userError") %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Database User Password:
          </td>
          <td>
            <input type="password" size="20" maxlength="255" name="password" value="<%= toHtmlValue(database.getPassword()) %>"/>
            <%= showAttribute(request, "passwordError") %>
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <span id="buttons" name="buttons">
        <input type="submit" value="Continue >"/>
      </span>
      <span id="progress" name="progress" style="display:none">
        <font color="blue"><b>Please Wait... connecting to the database!</b></font>
      </span>
    </td>
  </tr>
</table>
</form>
</body>
