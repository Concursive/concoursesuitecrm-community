<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.Campaign" scope="request"/>
<jsp:useBean id="CampaignDocListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="/javascript/confirmDelete.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="/CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
Documents
<hr color="#BFBFBB" noshade>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td colspan="2">
      <% String param1 = "id=" + Campaign.getId(); %>
      <dhv:container name="communications" selected="documents" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="campaign-campaigns-edit"><a href="CampaignDocuments.do?command=Add&id=<%= Campaign.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
<center><%= CampaignDocListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="CampaignDocListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="10" align="center">Action</td>
    <td>
    <strong><a href="/CampaignDocuments.do?command=View&id=<%= Campaign.getId() %>&column=subject">Item</a></strong>
      <%= CampaignDocListInfo.getSortIcon("subject") %>
      </td>
    <td align="center">Ext</td>
    <td align="center">Size</td>
    <td align="center">Version</td>
    <dhv:permission name="campaign-campaigns-edit">
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
        <a href="CampaignDocuments.do?command=Download&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
        <dhv:permission name="campaign-campaigns-edit"><a href="CampaignDocuments.do?command=Modify&fid=<%= thisFile.getId() %>&id=<%= Campaign.getId()%>">Edit</a></dhv:permission><dhv:permission name="campaign-campaigns-edit,campaign-campaigns-delete" all="true">|</dhv:permission><dhv:permission name="campaign-campaigns-delete"><a href="javascript:confirmDelete('CampaignDocuments.do?command=Delete&fid=<%= thisFile.getId() %>&id=<%= Campaign.getId()%>');">Del</a></dhv:permission>
      </td>
      <td valign="middle" width="100%">
        <a href="CampaignDocuments.do?command=Details&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
      </td>
      <td align="center"><%= toHtml(thisFile.getExtension()) %>&nbsp;</td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> k&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        <%= thisFile.getVersion() %>&nbsp;
      </td>
    <dhv:permission name="campaign-campaigns-edit">
      <td align="right" valign="middle" nowrap>
        [<a href="CampaignDocuments.do?command=AddVersion&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
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
  <dhv:pagedListControl object="CampaignDocListInfo"/>
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

