<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="APP_VERSION" class="java.lang.String" scope="application"/>
<jsp:useBean id="server" class="org.aspcfs.modules.setup.beans.ServerBean" scope="request"/>
<jsp:useBean id="userAddress" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<form name="inputForm" method="POST" action="#" onSubmit="javascript:return false;">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRM.step2of4">Concourse Suite Community Edition Configuration (Step 2 of 4)<br />Server Settings</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.settingsSaved">Settings saved!</dhv:label><br />
      &nbsp;<br />
    </td>
  </tr>
<dhv:evaluate if="<%= hasText(APP_VERSION) %>">
  <dhv:evaluate if="<%= server.getLdapEnabled() %>">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.ldap">LDAP Server</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.ldapTest.text">You should try to login to make certain the LDAP settings are configured correctly.</dhv:label><br />
      <br />
      <b><dhv:label name="setup.loginToLDAP">Login to LDAP...</dhv:label></b><br />
      <br />
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.ldap.url">LDAP Server URL:</dhv:label>
          </td>
          <td>
            <%= toHtml(server.getLdapUrl()) %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="ldap.username">LDAP Username:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="ldapUsername" value="" />
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="ldap.password">LDAP Password:</dhv:label>
          </td>
          <td>
            <input type="password" size="30" name="ldapPassword" value="" />
          </td>
        </tr>
      </table>
      <br />
      <input type="button" value="Test Login" onClick="javascript:popURL('SetupServerDetails.do?command=TestLDAP&username=' + document.inputForm.ldapUsername.value + '&password=' + document.inputForm.ldapPassword.value,'CRM_LDAPTest','275','325','yes','yes')"><br />
    </td>
  </tr>
  <tr>
    <td>&nbsp;<br /></td>
  </tr>
  </dhv:evaluate>
  <tr class="sectionTitle">
    <th>Mail Server</th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.emailTest.text">You should send a test email to make certain the mail settings are configured.</dhv:label><br />
      <br />
      <b><dhv:label name="setup.sentATestMessage">Send a test message...</dhv:label></b><br />
      <br />
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="mail.label.to">To:</dhv:label>
          </td>
          <td>
            <%= userAddress %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="campaign.from.colon" param="from=">From:</dhv:label>
          </td>
          <td>
            <%= toHtml(server.getEmailAddress()) %>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.server.colon">Server:</dhv:label>
          </td>
          <td>
            <%= toHtml(server.getEmail()) %>
          </td>
        </tr>
      </table>
      <br />
      <input type="button" value="Test Email" onClick="javascript:popURL('SetupServerDetails.do?command=TestEmail&from=<%= toHtml(server.getEmailAddress()) %>&to=<%= userAddress %>&server=<%= toHtml(server.getEmail()) %>','CRM_EmailTest','275','325','yes','yes')"><br />
    </td>
  </tr>
</dhv:evaluate>
  <tr>
    <td>
      <dhv:label name="setup.nextStepDatabaseConnection.text">The next step is to configure and verify the Concourse Suite Community Edition database connection.</dhv:label><br>
      <br />
      <input type="button" value="<dhv:label name="button.backL">< Back</dhv:label>" onClick="javascript:window.location.href='SetupServerDetails.do?command=ConfigureServerCheck'" />
      <input type="button" value="<dhv:label name="button.continueR">Continue ></dhv:label>" onClick="javascript:window.location.href='SetupDatabase.do?command=ConfigureDatabaseCheck'" />
    </td>
  </tr>
</table>
</form>