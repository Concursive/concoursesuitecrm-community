<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
      An error occurred during the upgrade!
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Upgrade Failed!
    </th>
  </tr>
  <tr>
    <td>
      Your database could be in an inconsistent state. You might want to seek assistance
      with what could have gone wrong. This page might contain some useful information
      during that analysis.
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Suggestions
    </th>
  </tr>
  <tr>
    <td>
      If you are in a hurry to get the system working, you could restore the database 
      to your last backup and then
      put the previous version of Dark Horse CRM back online.
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Error log
    </th>
  </tr>
  <tr>
    <td>
      While trying to upgrade from '<%= installedVersion %>' to '<%= newVersion %>' an error occurred.<br />
      The following error message was provided:<br />
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
      Upgrade Log
    </th>
  </tr>
<dhv:evaluate if="<%= installLog.size() == 0 %>">
  <tr>
    <td>
      No scripts completed successfully
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

