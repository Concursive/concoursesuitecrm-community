<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="installLog" class="java.util.ArrayList" scope="request" />
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Upgrade Complete!
    </th>
  </tr>
  <tr>
    <td>
      The system has been upgraded and users can now login. Please follow these
      steps to make certain the system is usable:
      <ul>
        <li>Review the roles, under the Admin module, since the upgrade may have added
        additional features in which you can now provide access to users by enabling permissions</li>
        <li>Click on various modules and make sure everything appears to work</li>
      </ul>
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
      No scripts executed
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
<br />
<table border="0" width="100%">
  <tr>
    <td>
      Since the JavaServerPages have not yet been compiled, you should choose to precompile the
      JSPs first so that the application works without compile delays.
      Precompiling will occur in the background and you can continue to use Dark Horse CRM.<br>
      <br>
      <input type="button" value="Precompile JSPs" onClick="javascript:popURL('setup/precompile.html','CRM_Precompile','500','325','yes','yes')"/><br>
      <br>
      <input type="button" value="Continue to Dark Horse CRM >" onClick="window.location.href='MyCFS.do?command=Home'" />
    </td>
  </tr>
</table>

