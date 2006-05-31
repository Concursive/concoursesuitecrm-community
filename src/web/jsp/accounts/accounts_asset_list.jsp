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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="assetList" class="org.aspcfs.modules.assets.base.AssetList" scope="request"/>
<jsp:useBean id="parent" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="assetManufacturerList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AssetListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="categoryList1" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList2" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList3" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="assetStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="asset_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:evaluate if="<%= parent != null && parent.getId() != -1%>">
  <a href="AccountsAssets.do?command=List&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.Assets">Assets</dhv:label></a> >
  <dhv:label name="accounts.subAssets">Sub-Assets</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= parent == null || parent.getId() == -1 %>">
  <dhv:label name="accounts.Assets">Assets</dhv:label>
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="assets" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="sidetabs">
<%
  if (parent != null && parent.getId() != -1 && parent.getParentList() != null && parent.getParentList().size() > 0) {
    Iterator iter = (Iterator) parent.getParentList().iterator();
    while (iter.hasNext()) {
      Asset parentAsset = (Asset) iter.next(); 
      String param1 = "id=" + parentAsset.getId() + "|parentId="+parentAsset.getId()+"|orgId="+OrgDetails.getOrgId();
%>
    <dhv:container name="accountsassets" selected="billofmaterials" object="parentAsset" item="<%= parentAsset %>" param="<%= param1 %>" />
<% }} %>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    <dhv:permission name="accounts-assets-add">
      <a href="AccountsAssets.do?command=Add&orgId=<%=OrgDetails.getOrgId()%>&parentId=<%= (parent != null?parent.getId():-1) %>"><dhv:label name="accounts.accounts_asset_list.AddAnAsset">Add an Asset</dhv:label></a>
    </dhv:permission>
  </dhv:evaluate>
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AssetListInfo"/>
  <%@ include file="accounts_asset_list_include.jsp" %>
  <br />
  <dhv:pagedListControl object="AssetListInfo"/>
</dhv:container>
