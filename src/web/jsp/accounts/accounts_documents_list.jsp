<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="/javascript/confirmDelete.js"></script>
<a href="Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
      <td>
      <a href="/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Details</font></a><dhv:permission name="accounts-accounts-folders-view"> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Folders</font></a></dhv:permission><dhv:permission name="accounts-accounts-contacts-view"> |
      <a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Contacts</font></a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-view"> | 
      <a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Opportunities</font></a></dhv:permission><dhv:permission name="accounts-accounts-tickets-view"> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Tickets</font></a></dhv:permission><dhv:permission name="accounts-accounts-documents-view"> |
      <a href="AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#0000FF">Documents</font></a></dhv:permission>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
			<a href="AccountsDocuments.do?command=Add&orgId=<%= OrgDetails.getOrgId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br>
      <%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="10" align="center">Action</td>
    <td colspan="2">Item</td>
    <td>Size</td>
    <td>Version</td>
    <td>Submitted</td>
    <td>Sent By</td>
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
      <td width="10" valign="middle" align="center" rowspan="2" nowrap>
        <a href="AccountsDocuments.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
				<a href="AccountsDocuments.do?command=Modify&fid=<%= thisFile.getId() %>&orgId=<%= OrgDetails.getOrgId() %>">Edit</a>|<a href="javascript:confirmDelete('AccountsDocuments.do?command=Delete&fid=<%= thisFile.getId() %>&orgId=<%= OrgDetails.getOrgId() %>');">Del</a>
			</td>
      <td valign="top" width="100%">
        <a href="AccountsDocuments.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getClientFilename()) %></a>
      </td>
      <td align="right" valign="middle" nowrap>
        [<a href="AccountsDocuments.do?command=AddVersion&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
      </td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> k&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        <%= thisFile.getVersion() %>&nbsp;
      </td>
      <td valign="middle" nowrap>
        <%= thisFile.getModifiedDateTimeString() %>
      </td>
      <td valign="middle">
        <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
      </td>
    </tr>
    <tr class="row<%= rowid %>">
      <td valign="middle" align="left" colspan="6">
        <i><%= toHtml(thisFile.getSubject()) %></i>
      </td>
    </tr>
<%}%>
  </table>
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

