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
<jsp:useBean id="installLog" class="java.util.ArrayList" scope="request" />
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Centric CRM Upgrade Complete!
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
  <tr class="sectionTitle">
    <th>
      Next Steps
    </th>
  </tr>
  <tr>
    <td>
      Since the JavaServerPages have not yet been compiled, you should choose to precompile the
      JSPs first so that the application works without compile delays.
      Precompiling will occur in the background and you can continue to use Centric CRM.<br>
      <br>
      <input type="button" value="Precompile JSPs" onClick="javascript:popURL('setup/precompile.html','CRM_Precompile','500','325','yes','yes')"/><br>
      <br>
      <input type="button" value="Continue to Centric CRM >" onClick="window.location.href='MyCFS.do?command=Home'" />
    </td>
  </tr>
</table>

