<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupServerDetails.do?command=ConfigureServerCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 1 of 4)<br>
      File Library Settings
    </th>
  </tr>
  <tr>
    <td>
      File Library Created!<br>
      <br>
      Dark Horse CRM will now store system and user data at:<br>
      <b><%= toHtml(request.getParameter("fileLibrary")) %></b><br>
      <br>
      Now it's time to configure the Dark Horse CRM server settings.<br>
      <br>
      <input type="button" value="< Back" onClick="javascript:window.location.href='SetupDirectory.do?command=ConfigureDirectoryCheck'"/>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
