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
      Centric CRM Configuration (Step 4 of 4)<br>
      User Settings
    </th>
  </tr>
  <tr>
    <td>
      Login account created!<br>
      <br>
      Installation is complete and you can now begin to use Centric CRM.<br>
      <br>
      Further configuration can be completed by clicking on the System Administration tab in Centric CRM.<br>
      <br>
      Since the JavaServerPages have not yet been compiled, you should choose to precompile the
      JSPs first so that the application works without compile delays.
      Precompiling will occur in the background and you can continue to use Centric CRM.<br>
      <br>
      <input type="button" value="Precompile JSPs" onClick="javascript:popURL('setup/precompile.html','CRM_Precompile','500','325','yes','yes')"/><br>
      <br>
      The next step is to login!<br>
      <br>
      <input type="button" value="Continue >" onClick="javascript:window.location.href='index.jsp'"/>
    </td>
  </tr>
</table>
