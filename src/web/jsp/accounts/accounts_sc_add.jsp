<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
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
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].serviceContractNumber.focus();" >
<form name="addServiceContract" action="AccountsServiceContracts.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post" >
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Accounts.do">Accounts</a> > 
  <a href="Accounts.do?command=Search">Search Results</a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Account Details</a> >
  <a href="AccountsServiceContracts.do?command=List&orgId=<%= OrgDetails.getOrgId() %>">Service Contracts</a> >
  Add Service Contract
</td>
</tr>
</table>
<%-- End Trails --%>
  <%@ include file="accounts_details_header_include.jsp" %>
    <dhv:container name="accounts" selected="servicecontracts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
        <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
        <input type="button" value="Cancel" onClick="window.location.href='AccountsServiceContracts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
        <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
        <br />
<%= showError(request, "actionError") %>
<%--  include basic contact form --%>
<%@ include file="servicecontract_include.jsp" %>
        <br />
        <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
        <input type="button" value="Cancel" onClick="window.location.href='AccountsServiceContracts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
        <input type="hidden" name="dosubmit" value="true" />
      </td>
    </tr>
  </table>
</form>
</body>
