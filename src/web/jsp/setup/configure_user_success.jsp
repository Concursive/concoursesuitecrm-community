<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
      Dark Horse CRM Configuration (Step 4 of 4)<br>
      User Settings
    </th>
  </tr>
  <tr>
    <td>
      Login account created!<br>
      <br>
      Installation is complete and you can now begin to use Dark Horse CRM.<br>
      <br>
      Further configuration can be completed by clicking on the System Administration tab in Dark Horse CRM.<br>
      <br>
      Since the JavaServerPages have not yet been compiled, you should choose to precompile the
      JSPs first so that the application works without compile delays.
      Precompiling will occur in the background and you can continue to use Dark Horse CRM.<br>
      <br>
      <input type="button" value="Precompile JSPs" onClick="javascript:popURL('setup/precompile.html','CRM_Precompile','500','325','yes','yes')"/><br>
      <br>
      The next step is to login!<br>
      <br>
      <input type="button" value="Continue >" onClick="javascript:window.location.href='index.jsp'"/>
    </td>
  </tr>
</table>
