<jsp:useBean id="database" class="org.aspcfs.modules.setup.beans.DatabaseBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupUser.do?command=ConfigureUserCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 3 of 4)<br>
      Database Settings &amp; Installation
    </th>
  </tr>
  <tr>
    <td>
      Database Creation Successful!<br>
      <br>
      Dark Horse CRM now has all of the necessary database tables as well
      as initial data installed.<br>
      <br>
      The final step is to create an administrative user login.<br>
      <br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
