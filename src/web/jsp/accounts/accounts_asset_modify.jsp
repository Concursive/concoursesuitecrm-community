<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.io.*, java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*, java.text.DateFormat" %>
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
<%@ include file="../initPage.jsp" %>
<form name="addAccountAsset" action="AccountsAssets.do?command=Update&auto-populate=true&return<%= request.getParameter("return") %>" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Accounts.do">Accounts</a> > 
  <a href="Accounts.do?command=Search">Search Results</a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Account Details</a> >
  <a href="AccountsAssets.do?command=List&orgId=<%= OrgDetails.getOrgId() %>">Assets</a> >
  Modify Asset
</td>
</tr>
</table>
<%-- End Trails --%>
  <%@ include file="accounts_details_header_include.jsp" %>
    <dhv:container name="accounts" selected="assets" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
      <input type=submit value="Update" onClick="this.form.dosubmit.value='true';" />
      <%if ("list".equals(request.getParameter("return"))) { %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountsAssets.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
      <%}else{ %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountsAssets.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%>';this.form.dosubmit.value='false';" />
      <%}%>
      <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
      <input type="hidden" name="id" value="<%= asset.getId() %>" />
      <input type="hidden" name="return" value="<%= request.getParameter("return") %>" />
      <br />
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<%@ include file="accountasset_include.jsp" %>
<br>
  <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';" />
  <%if ("list".equals(request.getParameter("return"))) { %>
    <input type="button" value="Cancel" onClick="window.location.href='AccountsAssets.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
  <%}else{ %>
    <input type="button" value="Cancel" onClick="window.location.href='AccountsAssets.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%>';this.form.dosubmit.value='false';" />
  <%}%>
  <input type="hidden" name="dosubmit" value="true" />
  </td>
  </tr>
</table>
</form>

