<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Documents</a> >
Document Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <%@ include file="accounts_details_header_include.jsp" %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="documents" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="7">
      <strong>All Versions of this Document</strong>
    </td>
  </tr>
  <tr class="title2">
    <td width="10" align="center">Action</td>
    <td>Item</td>
    <td>Size</td>
    <td>Version</td>
    <td>Submitted</td>
    <td>Sent By</td>
    <td>D/L</td>
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
        <a href="AccountsDocuments.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>">Download</a>
      </td>
      <td width="100%">
        <%= FileItem.getImageTag() %><%= thisVersion.getClientFilename() %>
      </td>
      <td align="right" nowrap>
        <%= thisVersion.getRelativeSize() %> k&nbsp;
      </td>
      <td align="right" nowrap>
        <%= thisVersion.getVersion() %>&nbsp;
      </td>
      <td nowrap>
        <%= thisVersion.getEnteredDateTimeString() %>
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
</td>
</tr>
</table>
