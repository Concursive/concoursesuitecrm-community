<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<a href="Leads.do?command=ViewOpp">Back to Opportunities List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;&nbsp;
      
      	<% 
	  if (OpportunityDetails.getAccountLink() != -1) { %>
	  	[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]
	  <%} else { %>
	  	[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]
	  <%}%>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>"><font color="#000000">Details</font></a> | 
      <a href="LeadsCalls.do?command=View&oppId=<%= OpportunityDetails.getId() %>"><font color="#000000">Calls</font></a> |
      <a href="LeadsDocuments.do?command=View&oppId=<%= OpportunityDetails.getId() %>"><font color="#0000FF">Documents</font></a>
   </td>
  </tr>
  <tr>
    <td class="containerBack">
      <a href="LeadsDocuments.do?command=Add&oppId=<%= OpportunityDetails.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br>
      <%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="10" align="center">Action</td>
    <td>Item</td>
    <td>&nbsp;</td>
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
      <td valign="middle" align="center" rowspan="2" nowrap>
        <a href="LeadsCalls.do?command=Modify&id=<%= thisFile.getId() %>&oppId=<%= OpportunityDetails.getId() %>">Edit</a>|<a href="javascript:confirmDelete('LeadsDocuments.do?command=Delete&id=<%= thisFile.getId() %>&oppId=<%= OpportunityDetails.getId() %>');">Del</a></td>
      <td valign="top">
        <a href="LeadsDocuments.do?command=Details&pid=<%= OpportunityDetails.getId() %>&fid=<%= thisFile.getId() %>"><%= toHtml(thisFile.getClientFilename()) %></a>
      </td>
      <td valign=center valign="middle">
        [<a href="LeadsDocuments.do?command=AddVersion&pid=<%= OpportunityDetails.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
      </td>
      <td align="center" valign="middle">
        <%= thisFile.getRelativeSize() %> k&nbsp;
      </td>
      <td align="center" valign="middle">
        <%= thisFile.getVersion() %>&nbsp;
      </td>
      <td valign="middle" nowrap>
        <%= thisFile.getModifiedDateTimeString() %>
      </td>
      <td valign="middle">
        <%= toHtml(thisFile.getEnteredByString()) %>
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
