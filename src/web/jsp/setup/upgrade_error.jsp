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
<%@ page import="java.util.*" %>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />
<jsp:useBean id="installLog" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="installedVersion" class="java.lang.String" scope="request"/>
<jsp:useBean id="newVersion" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%">
  <tr>
    <td>
      <img src="images/error.gif" border="0" align="absmiddle"/>
      <dhv:label name="setup.errorOccuredDuringUpgrade">An error occurred during the upgrade!</dhv:label>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.upgradeFailed">Centric CRM Upgrade Failed!</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.databaseInconsistentState.text">Your database could be in an inconsistent state. You might want to seek assistance with what could have gone wrong. This page might contain some useful information during that analysis.</dhv:label>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.suggestions">Suggestions</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.restoreDatabaseToBackup.text">If you are in a hurry to get the system working, you could restore the database to your last backup and then put the previous version of Centric CRM back online.</dhv:label>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.errorLog">Error log</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
    <% String temp = "installedVersion="+installedVersion+"|newVersion="+newVersion; %>
      <dhv:label name="setup.errorDetails.fromVersionToVersion.text" param="<%= temp %>">While trying to upgrade from '<%= installedVersion %>' to '<%= newVersion %>' an error occurred.<br />The following error message was provided:</dhv:label><br />
      &nbsp;
    </td>
  </tr>
  <tr>
    <td>
      <%= toHtml(errorMessage) %>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.upgradeLog">Upgrade Log</dhv:label>
    </th>
  </tr>
<dhv:evaluate if="<%= installLog.size() == 0 %>">
  <tr>
    <td>
      <dhv:label name="setup.noScriptsCompletedSuccessfully">No scripts completed successfully</dhv:label>
    </td>
  </tr>
</dhv:evaluate>
<%
Iterator installs = installLog.iterator();
while (installs.hasNext()) {
String step = (String) installs.next();
%>
  <tr>
    <td>
      <%= toHtml(step) %>
    </td>
  </tr>
<%}%>
</table>

