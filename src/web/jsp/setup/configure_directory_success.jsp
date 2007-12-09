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
<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupServerDetails.do?command=ConfigureServerCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRM.step1of4.text">Concourse Suite Community Edition Configuration (Step 1 of 4)<br />File Library Settings</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.fileLibraryCreated">File Library Created!</dhv:label><br>
      <br>
      <dhv:label name="setup.centricCRMstoresUserDataAt.colon">Concourse Suite Community Edition will now store system and user data at:</dhv:label><br>
      <b><%= toHtml(request.getParameter("fileLibrary")) %></b><br>
      <br>
      <dhv:label name="setup.timeToConfigureServerSettings.text">Now it's time to configure the Concourse Suite Community Edition server settings.</dhv:label><br>
      <br>
      <input type="button" value="<dhv:label name="button.backL">< Back</dhv:label>" onClick="javascript:window.location.href='SetupDirectory.do?command=ConfigureDirectoryCheck'"/>
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
