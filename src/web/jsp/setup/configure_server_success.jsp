<jsp:useBean id="server" class="org.aspcfs.modules.setup.beans.ServerBean" scope="request"/>
<jsp:useBean id="userAddress" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 2 of 4)<br />
      Server Settings
    </th>
  </tr>
  <tr>
    <td>
      Settings saved!<br />
      <br />
      You should send a test email to make certain the mail settings are configured.<br />
      <br />
      <b>Send a test message...</b><br />
      <br />
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            To:
          </td>
          <td>
            <%= userAddress %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            From:
          </td>
          <td>
            <%= toHtml(server.getEmailAddress()) %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Server:
          </td>
          <td>
            <%= toHtml(server.getEmail()) %>
          </td>
        </tr>
      </table>
      <br />
      <input type="button" value="Test Email" onClick="javascript:popURL('SetupServerDetails.do?command=TestEmail&from=<%= toHtml(server.getEmailAddress()) %>&to=<%= userAddress %>&server=<%= toHtml(server.getEmail()) %>','CRM_EmailTest','275','325','yes','yes')"><br />
      <br />
      The next step is to configure and verify the Dark Horse CRM database connection.<br>
      <br />
      <input type="button" value="< Back" onClick="javascript:window.location.href='SetupServerDetails.do?command=ConfigureServerCheck'" />
      <input type="button" value="Continue >" onClick="javascript:window.location.href='SetupDatabase.do?command=ConfigureDatabaseCheck'" />
    </td>
  </tr>
</table>
