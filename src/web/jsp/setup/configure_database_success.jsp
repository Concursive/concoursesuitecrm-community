<jsp:useBean id="database" class="org.aspcfs.modules.setup.beans.DatabaseBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function showProgress() {
    showSpan("progress");
    return true;
  }
</script>
<form name="configure" action="SetupDatabase.do?command=ConfigureDatabaseData" method="post" onSubmit="return showProgress()">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 3 of 4)<br>
      Database Settings &amp; Installation
    </th>
  </tr>
  <tr>
    <td>
      Database Connection Successful!<br>
      <br>
      Dark Horse CRM will now create all of the necessary database tables
      and initial data in the following database:<br>
      <br>
      <b><%= toHtmlValue(database.getUrl()) %></b><br>
      <br>
      This can take a few minutes depending on connectivity to the database,
      select Continue to begin.<br>
      <br>
      <input type="submit" value="Continue >"/>
      <span id="progress" name="progress" style="display:none">
        <b>Please Wait... creating the database!</b>
        This could take up to 5 minutes...
      </span>
    </td>
  </tr>
</table>
</form>
