<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Components</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>">Opportunity Details</a> >
Documents<br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); %>      
<dhv:container name="opportunities" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:permission name="pipeline-opportunities-documents-add"><a href="LeadsDocuments.do?command=Add&headerId=<%= opportunityHeader.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="10" align="center">Action</th>
    <th>Item</th>
    <th align="center">Ext</th>
    <th align="center">Size</th>
    <th align="center">Version</th>
    <dhv:permission name="pipeline-opportunities-documents-add">
      <th>&nbsp;</th>
    </dhv:permission>
    <th>Submitted</th>
  </tr>
<%
  Iterator j = FileItemList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem)j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" valign="middle" align="center" nowrap>
        <a href="LeadsDocuments.do?command=Download&headerId=<%= opportunityHeader.getId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
        <dhv:permission name="pipeline-opportunities-documents-edit"><a href="LeadsDocuments.do?command=Modify&fid=<%= thisFile.getId() %>&headerId=<%= opportunityHeader.getId() %>">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-documents-edit,pipeline-opportunities-documents-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-documents-delete"><a href="javascript:confirmDelete('LeadsDocuments.do?command=Delete&fid=<%=thisFile.getId() %>&headerId=<%= opportunityHeader.getId() %>');">Del</a></dhv:permission>
      </td>
      <td valign="middle" width="100%">
        <a href="LeadsDocuments.do?command=Details&headerId=<%= opportunityHeader.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
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
        [<a href="LeadsDocuments.do?command=AddVersion&headerId=<%= opportunityHeader.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
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
      <td colspan="7">
        No documents found.
      </td>
    </tr>
  </table>
<%}%>
</td>
</tr>
</table>
