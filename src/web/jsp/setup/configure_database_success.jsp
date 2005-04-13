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
<jsp:useBean id="database" class="org.aspcfs.modules.setup.beans.DatabaseBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function showProgress() {
    hideSpan("buttons");
    showSpan("progress");
    return true;
  }
</script>
<form name="configure" action="SetupDatabase.do?command=ConfigureDatabaseData" method="post" onSubmit="return showProgress()">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.configuration.step3to4">Centric CRM Configuration (Step 3 of 4)<br />Database Settings &amp; Installation</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.databaseConnectionSuccessful">Database Connection Successful!</dhv:label><br>
      <br>
      <dhv:label name="setup.createDatabaseTables.text">Centric CRM will now create all of the necessary database tables and initial data in the following database:</dhv:label><br>
      <br>
      <b><%= toHtmlValue(database.getUrl()) %></b><br>
      <br>
      <dhv:label name="setup.takesFewMinutes.text">This can take a few minutes depending on connectivity to the database, select Continue to begin.</dhv:label><br>
      <br>
      <span id="buttons" name="buttons">
        <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
      </span>
      <span id="progress" name="progress" style="display:none">
        <font color="blue"><b><dhv:label name="setup.pleaseWait.creatingDatabase.text">Please Wait... creating the database!</b> This could take up to 5 minutes...</dhv:label></font>
      </span>
    </td>
  </tr>
</table>
</form>
