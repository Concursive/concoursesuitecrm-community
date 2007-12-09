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
<jsp:useBean id="server" class="org.aspcfs.modules.setup.beans.ServerBean" scope="request"/>
<jsp:useBean id="userAddress" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRM.step4of4">Concourse Suite Community Edition Configuration (Step 4 of 4)<br />User Settings</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.loginAccountCreated">Login account created!</dhv:label><br />
      <br />
      <dhv:label name="setup.installationComplete.text">Installation is complete and you can now begin to use Concourse Suite Community Edition.</dhv:label><br />
      <br />
      <dhv:label name="setup.furtherConfiguration.text">Further configuration can be completed by clicking on the System Administration tab in Concourse Suite Community Edition.</dhv:label><br />
      <br />
      <dhv:label name="setup.jspPrecompile.note">Since the JavaServerPages have not yet been compiled, you should choose to precompile the JSPs so that the application works without compile delays. Compiling will occur in the background and you can continue to use Concourse Suite Community Edition.</dhv:label><br />
      <br />
      <input type="button" value="Compile JSPs" onClick="javascript:popURL('setup/precompile.html','CRM_Compile','500','325','yes','yes')"/><br />
      <br />
      <dhv:label name="setup.nextStepLogin.text">The next step is to login!</dhv:label><br />
      <br />
      <input type="button" value="<dhv:label name="button.continueR">Continue ></dhv:label>" onClick="javascript:window.location.href='index.jsp'"/>
    </td>
  </tr>
</table>
