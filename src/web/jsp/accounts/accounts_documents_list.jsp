<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="DocListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="/javascript/confirmDelete.js"></script>

<a href="/Accounts.do">Account Management</a> > 
<a href="/Accounts.do?command=View">View Accounts</a> >
<a href="/Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Documents<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
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
<dhv:permission name="accounts-accounts-documents-add"><a href="AccountsDocuments.do?command=Add&orgId=<%= OrgDetails.getOrgId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
<center><%= DocListInfo.getAlphabeticalPageLinks() %></center>
<%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="10" align="center">Action</td>
    <td>
    <strong><a href="/AccountsDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=subject">Item</a></strong>
      <%= DocListInfo.getSortIcon("subject") %>
      </td>
    <td align="center">Ext</td>
    <td align="center">Size</td>
    <td align="center">Version</td>
    <dhv:permission name="accounts-accounts-documents-add">
      <td>&nbsp;</td>
    </dhv:permission>
    <td align="center">Submitted</td>
  </tr>
<%
  Iterator j = FileItemList.iterator();
  
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      if (rowid != 1) rowid = 1; else rowid = 2;
      FileItem thisFile = (FileItem)j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" valign="middle" align="center" nowrap>
        <a href="AccountsDocuments.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
        <dhv:permission name="accounts-accounts-documents-edit"><a href="AccountsDocuments.do?command=Modify&fid=<%= thisFile.getId() %>&orgId=<%= OrgDetails.getOrgId()%>">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-documents-edit,accounts-accounts-documents-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-documents-delete"><a href="javascript:confirmDelete('AccountsDocuments.do?command=Delete&fid=<%= thisFile.getId() %>&orgId=<%= OrgDetails.getOrgId()%>');">Del</a></dhv:permission>
      </td>
      <td valign="middle" width="100%">
        <a href="AccountsDocuments.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
      </td>
      <td align="center"><%= toHtml(thisFile.getExtension()) %>&nbsp;</td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> k&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        <%= thisFile.getVersion() %>&nbsp;
      </td>
    <dhv:permission name="accounts-accounts-documents-add">
      <td align="right" valign="middle" nowrap>
        [<a href="AccountsDocuments.do?command=AddVersion&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
      </td>
    </dhv:permission>
      <td nowrap>
        <%= thisFile.getModifiedDateTimeString() %><br>
        <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="DocListInfo"/>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7" valign="center">
        No documents found.
      </td>
    </tr>
  </table>
<%}%>
</td>
</tr>
</table>

