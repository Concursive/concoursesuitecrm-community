<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupDirectory.do?command=ConfigureDirectoryCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>Status</th>
  </tr>
  <tr>
    <td>
      License accepted!<br>
      <br>
      Now it's time to configure some of the system settings before you
      can begin using Dark Horse CRM.<br>
      <br>
      Configuration includes the following steps:<br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Step 1:
          </td>
          <td>
            Setup the file library
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Step 2:
          </td>
          <td>
            Setup external servers (mail, fax)
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Step 3:
          </td>
          <td>
            Setup the database
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Step 4:
          </td>
          <td>
            Setup the Dark Horse CRM administrative user account
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
