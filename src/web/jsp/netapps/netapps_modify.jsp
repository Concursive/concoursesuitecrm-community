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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.utils.web.*, java.text.DateFormat" %>
<jsp:useBean id="contractExpiration" class="org.aspcfs.modules.netapps.base.ContractExpiration" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<body onLoad="javascript:document.forms[0].quoteGeneratedDate.focus();">
<form name="modifyContractExpiration" action="NetworkApplications.do?command=UpdateQuote&auto-populate=true&expirationId=<%=contractExpiration.getId()%>&return=<%= request.getParameter("return") %>" onSubmit="return doCheck(this);" method="post">
<script language="JavaScript">
  onLoad = 1;
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";

    if (form.quoteGeneratedDate.value == "" &&
       form.quoteAcceptedDate.value == "" &&
       form.quoteRejectedDate.value == ""){
      message = message + "Atleast one of Quote Generated (or) Accepted (or) Rejected Date is required\r\n";
      formTest = false;
    }

    if (form.quoteAcceptedDate.value != "" && form.quoteRejectedDate.value != "" ){
      message = message + "Only one of Quote Accepted or Quote Rejected may be entered\r\n";  
      formTest = false;
    }
    
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }    
    return true;
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="NetworkApplications.do">NetApp</a> >
    <% if (contractExpiration.isApproved()) {%>
      <a href="NetworkApplications.do?command=Search">Contract Expiration List</a> >
    <%}else{%>
      <a href="NetworkApplicationsImports.do?command=View">View Imports</a> >
    <%}%>
    <%if ((contractExpiration.isApproved()) && (!"list".equals(request.getParameter("return")))) { %>
      <a href="NetworkApplications.do?command=View&expirationId=<%=contractExpiration.getId()%>">Contract Expiration Details</a> >
    <%}%>
    Modify Contract
  </td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="netapps" selected="details" param="<%= "expirationId=" + contractExpiration.getId() %>" style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
<dhv:permission name="netapps_expiration_contracts-edit">
<dhv:evaluate if="<%= contractExpiration.isApproved()  %>">
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
<%if ("list".equals(request.getParameter("return"))) { %>
  <input type="button" value="Cancel" onClick="window.location.href='NetworkApplications.do?command=List';this.form.dosubmit.value='false';" />
<%}else{ %>
  <input type="button" value="Cancel" onClick="window.location.href='NetworkApplications.do?command=View&expirationId=<%=contractExpiration.getId()%>';this.form.dosubmit.value='false';" />
<%}%>
<br><br>
</dhv:evaluate>
</dhv:permission>
<%@ include file="netapps_modify_include.jsp" %>
<dhv:permission name="netapps_expiration_contracts-edit">
<dhv:evaluate if="<%= contractExpiration.isApproved()  %>">
<br>
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
<%if ("list".equals(request.getParameter("return"))) { %>
  <input type="button" value="Cancel" onClick="window.location.href='NetworkApplications.do?command=List';this.form.dosubmit.value='false';" />
<%}else{ %>
  <input type="button" value="Cancel" onClick="window.location.href='NetworkApplications.do?command=View&expirationId=<%=contractExpiration.getId()%>';this.form.dosubmit.value='false';" />
<%}%>
<input type="hidden" name="dosubmit" value="true" />
</dhv:evaluate>
</dhv:permission>
</td>
  </tr>
</table>
</form>
