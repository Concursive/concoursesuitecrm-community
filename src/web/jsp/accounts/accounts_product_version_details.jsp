<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="AccountsProducts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="product.products">Products</dhv:label></a> >
<a href="AccountsProducts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>&adId=<%= request.getAttribute("adId") %>&fid=<%= FileItem.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
<dhv:label name="product.versionDetails">Version Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%= showError(request, "actionError", false) %>
<dhv:container name="accounts" selected="products" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th colspan="7">
        <strong><dhv:label name="accounts.accounts_documents_details.AllVersionsDocument">All Versions of this Document</dhv:label></strong>
      </th>
    </tr>
    <tr class="title2">
      <td width="8">&nbsp;</td>
      <td><dhv:label name="accounts.accounts_documents_details.Item">Item</dhv:label></td>
      <td><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></td>
      <td><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></td>
      <td><dhv:label name="documents.documents.submitted">Submitted</dhv:label></td>
      <td><dhv:label name="accounts.accounts_documents_details.SentBy">Sent By</dhv:label></td>
      <td><dhv:label name="documents.documents.DL">D/L</dhv:label></td>
    </tr>
<%
  Iterator versionList = FileItem.getVersionList().iterator();
  int rowid = 0;
  while (versionList.hasNext()) {
    rowid = (rowid != 1?1:2);
    FileItemVersion thisVersion = (FileItemVersion)versionList.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" align="center" rowspan="2" nowrap>
        <a href="AccountsProducts.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= request.getAttribute("adId") %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>"><dhv:label name="button.download">Download</dhv:label></a>
      </td>
      <td width="100%">
        <%= FileItem.getImageTag() %><%= thisVersion.getClientFilename() %>
      </td>
      <td align="right" nowrap>
        <%= thisVersion.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
      </td>
      <td align="right" nowrap>
        <%= thisVersion.getVersion() %>&nbsp;
      </td>
      <td nowrap>
        <dhv:tz timestamp="<%= thisVersion.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
      </td>
      <td>
        <dhv:username id="<%= thisVersion.getEnteredBy() %>"/>
      </td>
      <td align="right">
        <%= thisVersion.getDownloads() %>
      </td>
    </tr>
    <tr class="row<%= rowid %>">
      <td colspan="6">
        <i><%= thisVersion.getSubject() %></i>
      </td>
    </tr>
  <%}%>
</table>
</dhv:container>