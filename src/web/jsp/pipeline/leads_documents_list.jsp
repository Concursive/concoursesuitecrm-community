<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityDetails" class="org.aspcfs.modules.pipeline.base.Opportunity" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="Leads.do?command=DetailsOpp&id=<%=OpportunityDetails.getId()%>">Opportunity Details</a> >
Documents<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;
      	<dhv:evaluate exp="<%=(OpportunityDetails.getAccountEnabled() && OpportunityDetails.getAccountLink() > -1)%>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]</dhv:permission>
	</dhv:evaluate>
	  
	<dhv:evaluate exp="<%=(OpportunityDetails.getContactLink() > -1)%>">
	<dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]</dhv:permission>
	</dhv:evaluate>
  
        <% if (OpportunityDetails.hasFiles()) { %>
      <% FileItem thisFile = new FileItem(); %>
      <%= thisFile.getImageTag()%>
      <%}%>    
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + OpportunityDetails.getId(); %>      
      <dhv:container name="opportunities" selected="documents" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <dhv:permission name="pipeline-opportunities-documents-add"><a href="LeadsDocuments.do?command=Add&oppId=<%= OpportunityDetails.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="10" align="center">Action</td>
    <td>Item</td>
    <td align="center">Ext</td>
    <td align="center">Size</td>
    <td align="center">Version</td>
    <dhv:permission name="pipeline-opportunities-documents-add">
      <td>&nbsp;</td>
    </dhv:permission>
    <td>Submitted</td>
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
        <a href="LeadsDocuments.do?command=Download&oppId=<%= OpportunityDetails.getId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
        <dhv:permission name="pipeline-opportunities-documents-edit"><a href="LeadsDocuments.do?command=Modify&fid=<%= thisFile.getId() %>&oppId=<%= OpportunityDetails.getId()%>">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-documents-edit,pipeline-opportunities-documents-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-documents-delete"><a href="javascript:confirmDelete('LeadsDocuments.do?command=Delete&fid=<%=thisFile.getId() %>&oppId=<%= OpportunityDetails.getId() %>');">Del</a></dhv:permission>
      </td>
      <td valign="middle" width="100%">
        <a href="LeadsDocuments.do?command=Details&oppId=<%= OpportunityDetails.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
      </td>
      <td align="center"><%= thisFile.getExtension() %>&nbsp;</td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> k&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        <%= thisFile.getVersion() %>&nbsp;
      </td>
      <dhv:permission name="pipeline-opportunities-documents-add">
      <td align="right" valign="middle" nowrap>
        [<a href="LeadsDocuments.do?command=AddVersion&oppId=<%= OpportunityDetails.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
      </td>
      </dhv:permission>
      <td nowrap>
        <%= thisFile.getModifiedDateTimeString() %><br>
        <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
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
