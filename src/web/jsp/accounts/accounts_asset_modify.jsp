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
<%@ page import="java.io.*, java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*, java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="assetStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="responseModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="phoneModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="emailModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="asset" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="parent" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="assetVendorList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="assetManufacturerList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="assetMaterialValue" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="assetMaterialQty" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="categoryList1" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList2" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList3" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<form name="addAccountAsset" action="AccountsAssets.do?command=Update&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="AccountsAssets.do?command=List&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.Assets">Assets</dhv:label></a> >
  <dhv:label name="accounts.accounts_asset_modify.ModifyAsset">Modify Asset</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="assets" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if='<%= asset.getParentList() != null && asset.getParentList().size() > 0 %>'>
<%
    Iterator iter = asset.getParentList().iterator();
    while (iter.hasNext()) {
      Asset parentAsset = (Asset) iter.next();
      String param1 = "id=" + parentAsset.getId() + "|parentId="+parentAsset.getId()+"|orgId="+OrgDetails.getOrgId();
%>
      <dhv:container name='accountsassets' selected='billofmaterials' object='parentAsset' item='<%= parentAsset %>' param='<%= param1 %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' />
<%  } %>
  </dhv:evaluate>
  <% String param2 = "id=" + asset.getId() + "|parentId="+asset.getId()+"|orgId="+OrgDetails.getOrgId(); %>
  <dhv:container name='accountsassets' selected='details' object='asset' param='<%= param2 %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <input type=submit value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';" />
    <%if ("list".equals(request.getParameter("return"))) { %>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountsAssets.do?command=List&orgId=<%=OrgDetails.getOrgId()%>&parentId=<%= asset.getParentId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';" />
    <%}else{ %>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountsAssets.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%>&parentId=<%= asset.getParentId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';" />
    <%}%>
    <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
    <input type="hidden" name="id" value="<%= asset.getId() %>" />
    <input type="hidden" name="ret" value="<%= request.getParameter("ret") %>" />
    <br />
    <dhv:formMessage />
    <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
    <%@ include file="accountasset_include.jsp" %>
    <br>
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';" />
    <%if ("list".equals(request.getParameter("return"))) { %>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountsAssets.do?command=List&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';" />
    <%}else{ %>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountsAssets.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';" />
    <%}%>
    <input type="hidden" name="dosubmit" value="true" />
  </dhv:container>
</dhv:container>
</form>
<script type="text/javascript">
  refreshMaterials();
</script>
