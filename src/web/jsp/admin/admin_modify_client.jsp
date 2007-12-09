<%-- 
  - Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id: admin_modify_client.jsp 13721 2005-12-29 20:43:02 -0500 (Thu, 29 Dec 2005) ananth $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="syncClient" class="org.aspcfs.modules.service.base.SyncClient"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/spanDisplay.js"></script>

<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addNewClient.type.focus();">
<form name="addNewClient"
      action="AdminClientManager.do?command=AddClient&auto-populate=true"
      onSubmit="return doCheck(this);" method="post">
  <%-- Trails --%>
  <table class="trails" cellspacing="0">
    <tr>
      <td>
        <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a>
        >
        <a href="AdminConfig.do?command=ListGlobalParams"><dhv:label
            name="admin.configureSystem">Configure System</dhv:label></a> >
        <a href="AdminClientManager.do?command=ShowClients"><dhv:label
            name="admin.hTTP-XMLClientManager">HTTP-XML Client
          Manager</dhv:label></a> >
        <dhv:label name="admin.modifyClient">Modify Client</dhv:label>
      </td>
    </tr>
  </table>
  <%-- End Trails --%>
  <%
    String title = "Modify Sync Client";
    String titleLabel = "admin.modifyClient";
    java.util.Date entered = syncClient.getEntered();
    java.util.Date modified = syncClient.getModified();
  %>
  <input type="submit"
         value="<dhv:label name="global.button.update">Update</dhv:label>"
         name="Save" onClick="this.form.dosubmit.value='true';">
  <input type="submit"
         value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
         onClick="javascript:this.form.action='AdminClientManager.do?command=ShowClients';this.form.dosubmit.value='false';">
  <%@ include file="admin_modify_client_include.jsp" %>
  <input type="submit"
         value="<dhv:label name="global.button.update">Update</dhv:label>"
         name="Save" onClick="this.form.dosubmit.value='true';">
  <input type="submit"
         value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
         onClick="javascript:this.form.action='AdminClientManager.do?command=ShowClients';this.form.dosubmit.value='false';">
  <input type="hidden" name="dosubmit" value="true">
</form>
</body>

