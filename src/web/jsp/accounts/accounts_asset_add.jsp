<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.io.*, java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="assetStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="responseModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="phoneModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="emailModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="asset" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="categoryList1" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList2" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList3" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function setDefaultDateListedField(){
    <%if ((asset.getDateListed() == null) && (request.getAttribute("dateListedError") == null)){%>
      document.forms['addAccountAsset'].dateListed.value= document.forms['addAccountAsset'].currentDate.value;
    <%}%>
  }
</script>
<body onLoad="javascript:document.forms[0].vendor.focus();setDefaultDateListedField()" >
<form name="addAccountAsset" action="AccountsAssets.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="Accounts.do?command=Search">Search Results</a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="AccountsAssets.do?command=List&orgId=<%= OrgDetails.getOrgId() %>">Assets</a> >
  Add Asset
</td>
</tr>
</table>
<%-- End Trails --%>
  <%@ include file="accounts_details_header_include.jsp" %>
  <dhv:container name="accounts" selected="assets" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
      <input type="button" value="Cancel" onClick="window.location.href='AccountsAssets.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
      <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
      <br />
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<%@ include file="accountasset_include.jsp" %>
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<br />
  <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
  <input type="button" value="Cancel" onClick="window.location.href='AccountsAssets.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
  <input type="hidden" name="dosubmit" value="true" />
  </td>
  </tr>
</table>
</form>
</body>
