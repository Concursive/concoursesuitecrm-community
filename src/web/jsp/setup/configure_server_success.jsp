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
<jsp:useBean id="server" class="org.aspcfs.modules.setup.beans.ServerBean" scope="request"/>
<jsp:useBean id="userAddress" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Centric CRM Configuration (Step 2 of 4)<br />
      Server Settings
    </th>
  </tr>
  <tr>
    <td>
      Settings saved!<br />
      <br />
      <%-- BEGIN DHV CODE ONLY --%>
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
      <%-- END DHV CODE ONLY --%>
      The next step is to configure and verify the Centric CRM database connection.<br>
      <br />
      <input type="button" value="< Back" onClick="javascript:window.location.href='SetupServerDetails.do?command=ConfigureServerCheck'" />
      <input type="button" value="Continue >" onClick="javascript:window.location.href='SetupDatabase.do?command=ConfigureDatabaseCheck'" />
    </td>
  </tr>
</table>
