<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="leads_documents_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard">Dashboard</a> >
<% }else{ %>
	<a href="Leads.do?command=Search">Search Results</a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Opportunity Details</a> >
Documents
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>
<dhv:container name="opportunities" selected="documents" param="<%= param1 %>" appendToUrl="<%= param2 %>"  style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:permission name="pipeline-opportunities-documents-add"><a href="LeadsDocuments.do?command=Add&headerId=<%= opportunityHeader.getId() %>&folderId=<%= FileItemList.getFolderId() %><%= addLinkParams(request, "viewSource") %>">Add a Document</a><br></dhv:permission>
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
    int i =0;
    while (j.hasNext()) {
      i++;
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem)j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" valign="middle" align="center" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuFile', '<%= opportunityHeader.getId() %>', '<%= thisFile.getId() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuFile');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="middle" width="100%">
        <a href="LeadsDocuments.do?command=Details&headerId=<%= opportunityHeader.getId() %>&fid=<%= thisFile.getId() %><%= addLinkParams(request, "viewSource") %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
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
        [<a href="LeadsDocuments.do?command=AddVersion&headerId=<%= opportunityHeader.getId() %>&fid=<%= thisFile.getId() %><%= addLinkParams(request, "viewSource") %>">Add Version</a>]
      </td>
      </dhv:permission>
      <td nowrap>
        <zeroio:tz timestamp="<%= thisFile.getModified() %>" /><br>
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
