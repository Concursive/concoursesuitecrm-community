<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupServer.do?command=ConfigureServerCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      CFS Configuration (Step 1 of 4)<br>
      File Library Settings
    </th>
  </tr>
  <tr>
    <td>
      File Library Created!<br>
      <br>
      CFS will now store system and user data at:<br>
      <b><%= toHtml(request.getParameter("fileLibrary")) %></b><br>
      <br>
      Now it's time to configure the CFS server settings.<br>
      <br>
      <input type="button" value="< Back" onClick="javascript:window.location.href='SetupDirectory.do?command=ConfigureDirectoryCheck'"/>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
