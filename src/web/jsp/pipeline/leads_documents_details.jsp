<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityDetails" class="org.aspcfs.modules.Opportunity" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<a href="/Leads.do">Pipeline Management</a> > 
<a href="/Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="/Leads.do?command=DetailsOpp&id=<%=OpportunityDetails.getId()%>">Opportunity Details</a> >
<a href="/Leads.do?command=DetailsOpp&id=<%=OpportunityDetails.getId()%>">Documents</a> > 
Document Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;
      	<dhv:evaluate exp="<%=(OpportunityDetails.getAccountEnabled() && OpportunityDetails.getAccountLink() > -1)%>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]</dhv:permission>
	</dhv:evaluate>
	  
	<dhv:evaluate exp="<%=(OpportunityDetails.getContactLink() > -1)%>">
	<dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]</dhv:permission>
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
      <a href="LeadsDocuments.do?command=View&oppId=<%= OpportunityDetails.getId() %>">Back to Documents List</a><br>
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="7">
      <strong>All Versions of this Document</strong>
    </td>
  </tr>
  <tr class="pagedlist">
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
    if (rowid != 1) rowid = 1; else rowid = 2;
    FileItemVersion thisVersion = (FileItemVersion)versionList.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" align="center" rowspan="2" nowrap>
        <a href="LeadsDocuments.do?command=Download&oppId=<%= OpportunityDetails.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>">Download</a>
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
