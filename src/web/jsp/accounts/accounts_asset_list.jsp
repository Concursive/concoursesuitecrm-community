<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="assetList" class="org.aspcfs.modules.assets.base.AssetList" scope="request"/>
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
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Assets
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="assets" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<%-- Begin the container contents --%>
<dhv:permission name="accounts-assets-add">
<a href="AccountsAssets.do?command=Add&orgId=<%=OrgDetails.getOrgId()%>">Add an Asset</a>
</dhv:permission>
<dhv:permission name="accounts-assets-add" none="true"></dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AssetListInfo"/>
<%@ include file="accounts_asset_list_include.jsp" %>
<br />
<dhv:pagedListControl object="AssetListInfo"/>
</td>
</tr>
</table>

