<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*, java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="serviceContract" class="org.aspcfs.modules.servicecontracts.base.ServiceContract" scope="request"/>
<jsp:useBean id="serviceContractHoursHistory" class="org.aspcfs.modules.servicecontracts.base.ServiceContractHoursList" scope="request"/>
<jsp:useBean id="serviceContractProductList" class="org.aspcfs.modules.servicecontracts.base.ServiceContractProductList" scope="request"/>
<jsp:useBean id="serviceContractCategoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="serviceContractTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="responseModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="phoneModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="emailModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="hoursReasonList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].serviceContractNumber.focus();">
<form name="addServiceContract" action="AccountsServiceContracts.do?command=Update&auto-populate=true&return=<%= request.getParameter("return") %>" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="Accounts.do?command=Search">Search Results</a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="AccountsServiceContracts.do?command=List&orgId=<%= OrgDetails.getOrgId() %>">Service Contracts</a> >
  Modify Service Contract
</td>
</tr>
</table>
<%-- End Trails --%>
  <%@ include file="accounts_details_header_include.jsp" %>
    <dhv:container name="accounts" selected="servicecontracts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
      <input type=submit value="Update" onClick="this.form.dosubmit.value='true';" />
      <%if ("list".equals(request.getParameter("return"))) { %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountsServiceContracts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
      <%}else{ %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>';this.form.dosubmit.value='false';" />
      <%}%>
      <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
      <input type="hidden" name="id" value="<%= serviceContract.getId() %>" />
      <input type="hidden" name="return" value="<%= request.getParameter("return") %>" />
      <br />
<%= showError(request, "actionError") %>
<%--  include basic service contract form --%>
<%@ include file="servicecontract_include.jsp" %>
  <br />
  <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';" />
  <%if ("list".equals(request.getParameter("return"))) { %>
    <input type="button" value="Cancel" onClick="window.location.href='AccountsServiceContracts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
  <%}
  else{ %>
    <input type="button" value="Cancel" onClick="window.location.href='AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>';this.form.dosubmit.value='false';" />
  <%
  }
  %>
  <input type="hidden" name="dosubmit" value="true" />
  </td>
  </tr>
</table>
</form>
</body>
